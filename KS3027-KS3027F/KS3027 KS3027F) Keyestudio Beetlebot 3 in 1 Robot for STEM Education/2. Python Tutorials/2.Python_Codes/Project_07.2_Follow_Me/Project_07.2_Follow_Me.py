from machine import Pin, PWM
import time

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

#Set the pin and sound speed of the ultrasonic sensor
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
  pin2.duty_u16(30000) 
  pin3.value(0)
  pin4.duty_u16(30000)

# As a function of the car going backwards.
def car_back(): 
  pin1.value(1)
  pin2.duty_u16(30000)  
  pin3.value(1)
  pin4.duty_u16(30000)
  
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

while True:
    Distance=getSonar() #Get the distance measured by ultrasound.
    print(Distance) #Send a pulse to calculate the distance in centimeters and print the result.
    if Distance<8: #If the distance is less than 8
        pin2=PWM(Pin(16))
        pin2.freq(50)
        pin4=PWM(Pin(17))
        pin4.freq(50) 
        car_back() #Car goes back
    elif Distance>=8 and Distance<13: #If the distance is greater than or equal to 8, it's less than 13.
        car_stop() # Car stop
    elif Distance>=13 and Distance<35: #If the distance is greater than or equal to 13, it's less than 35.
        pin2=PWM(Pin(16))
        pin2.freq(50)
        pin4=PWM(Pin(17))
        pin4.freq(50)
        car_forward() #Car ahead
    else:
       car_stop() 