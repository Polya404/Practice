from machine import Pin,I2C,PWM
import time
import json
import matrix_fonts
from ht16k33_matrix import ht16k33_matrix

'''
#Duty cycle corresponding to steering gear Angle
0°----2.5%----1638
45°----5%----3276
90°----7.5%----4915
135°----10%----6553
180°----12.5%----8192
'''
#Define GPIO9’s output frequency as 50Hz and its duty cycle as 4915, and assign them to PWM.
servoPin = PWM(Pin(9))
servoPin.freq(50)
servoPin.duty_u16(4915)
time.sleep(1)

#Set the pin and sound speed of the ultrasonic sensor.
trigPin=Pin(10,Pin.OUT,0)
echoPin=Pin(11,Pin.IN,0)
soundVelocity=340
distance=0

# right wheel
pin1=Pin(14,Pin.OUT)
pin2=PWM(Pin(16))
pin2.freq(50)
# left wheel
pin3=Pin(15,Pin.OUT)
pin4=PWM(Pin(17))
pin4.freq(50)

# As a function of the car going forward.
def car_forward(): 
  pin1.value(0)
  pin2.duty_u16(20000) 
  pin3.value(0)
  pin4.duty_u16(20000)

# As a function of the car going left.
def car_left(): 
  pin1.value(0)
  pin2.duty_u16(20000)  
  pin3.value(1)
  pin4.duty_u16(40000)

# As a function of the car going right.
def car_right(): 
  pin1.value(1)
  pin2.duty_u16(40000)  
  pin3.value(0)
  pin4.duty_u16(20000)

# As a function of the car stopping.
def car_stop():
  pin2.deinit()
  pin4.deinit()
  pin1.value(0)
  pin2.duty_u16(0)  
  pin3.value(0)
  pin4.duty_u16(0) 
    
#Subfunction getSonar() is used to start the Ultrasonic Module to begin measurements,
#and return the measured distance in centimeters. In this function, first let trigPin
#send 10us high level to start the Ultrasonic Module. Then use pulseIn() to read
#the Ultrasonic Module and return the duration time of high level.
#Finally, the measured distance according to the time is calculated.  
def getSonar(): 
    trigPin.value(1)
    time.sleep_us(10)
    trigPin.value(0)
    while not echoPin.value():
        pass
    pingStart=time.ticks_us()
    while echoPin.value():
        pass
    pingStop=time.ticks_us()
    pingTime=time.ticks_diff(pingStop,pingStart)
    distance=pingTime*soundVelocity//2//10000
    time.sleep_ms(10)
    return int(distance)

## Tool To Make Sprites https://gurgleapps.com/tools/matrix
#i2c config
clock_pin = 21
data_pin = 20
bus = 0
i2c_addr_left = 0x70
use_i2c = True

def scan_for_devices():
    i2c = machine.I2C(bus,sda=machine.Pin(data_pin),scl=machine.Pin(clock_pin))
    devices = i2c.scan()
    if devices:
        for d in devices:
            print(hex(d))
    else:
        print('no i2c devices')

if use_i2c:
    scan_for_devices()
    left_eye = ht16k33_matrix(data_pin, clock_pin, bus, i2c_addr_left)

def show_char(left):
    if use_i2c:
        left_eye.show_char(left)
        
while True:
    Distance=getSonar() #Get the distance measured by ultrasound.
    if Distance>0 and Distance<10: #If the distance is greater than 0, it's less than 10.
        car_stop() # Car stop
        show_char(matrix_fonts.textFont1['!']) #Show the punctuation !
        time.sleep(0.2)
        servoPin.duty_u16(8192)
        time.sleep(0.3)
        a1=getSonar()
        time.sleep(0.2)
        servoPin.duty_u16(1638)
        time.sleep(0.3)
        a2=getSonar()
        time.sleep(0.2)
        if a1>a2:
            pin2=PWM(Pin(16))
            pin2.freq(50)
            pin4=PWM(Pin(17))
            pin4.freq(50)
            car_left()
            show_char(matrix_fonts.textFont1['>'])
            servoPin.duty_u16(4915)
            time.sleep(0.3)
            show_char(matrix_fonts.textFont1['^'])
        else:
            pin2=PWM(Pin(16))
            pin2.freq(50)
            pin4=PWM(Pin(17))
            pin4.freq(50)
            car_right()
            show_char(matrix_fonts.textFont1['<'])
            servoPin.duty_u16(4915)
            time.sleep(0.3)
            show_char(matrix_fonts.textFont1['^'])
    else:
        pin2=PWM(Pin(16))
        pin2.freq(50)
        pin4=PWM(Pin(17))
        pin4.freq(50)
        car_forward() #Car ahead
        show_char(matrix_fonts.textFont1['^'])