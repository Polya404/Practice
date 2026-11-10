#Import libraries
from machine import Pin,I2C,PWM
import time
import json
import matrix_fonts
from ht16k33_matrix import ht16k33_matrix
from irrecvdata import irGetCMD 

recvPin = irGetCMD(6) #Associate the infrared decoder with Pin(6).
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

# right wheel
pin1=Pin(14,Pin.OUT)
pin2=PWM(Pin(16))
pin2.freq(50)
# left wheel
pin3=Pin(15,Pin.OUT)
pin4=PWM(Pin(17))
pin4.freq(50)

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
    
# As a function of the car going forward.
def car_forward(): 
  pin1.value(0)
  pin2.duty_u16(50000) 
  pin3.value(0)
  pin4.duty_u16(50000)

# As a function of the car going backwards.
def car_back(): 
  pin1.value(1)
  pin2.duty_u16(10000)  
  pin3.value(1)
  pin4.duty_u16(10000)

# As a function of the car going left.
def car_left(): 
  pin1.value(0)
  pin2.duty_u16(50000)  
  pin3.value(1)
  pin4.duty_u16(32768)

# As a function of the car going right.
def car_right(): 
  pin1.value(1)
  pin2.duty_u16(32768)  
  pin3.value(0)
  pin4.duty_u16(50000)

# As a function of the car stopping.
def car_stop():
  pin2.deinit()
  pin4.deinit()
  pin1.value(0)
  pin2.duty_u16(0)  
  pin3.value(0)
  pin4.duty_u16(0) 
  
def handleControl(value): 
    if value == '0xff629d':
        pin2=PWM(Pin(16))
        pin2.freq(50)
        pin4=PWM(Pin(17))
        pin4.freq(50)
        car_forward() #Car ahead
   # Dot matrix shows a forward pattern 
        show_char(matrix_fonts.textFont1['^'])
    elif value == '0xffa857':
        pin2=PWM(Pin(16))
        pin2.freq(50)
        pin4=PWM(Pin(17))
        pin4.freq(50)
        car_back() # Car goes back
    # Dot matrix shows a backward pattern
        show_char(matrix_fonts.textFont1['v'])
    elif value == '0xff22dd': 
        pin2=PWM(Pin(16))
        pin2.freq(50)
        pin4=PWM(Pin(17))
        pin4.freq(50)
        car_left() # Car to the left
    # Dot matrix shows a left pattern
        show_char(matrix_fonts.textFont1['>'])
    elif value == '0xffc23d': 
        pin2=PWM(Pin(16))
        pin2.freq(50)
        pin4=PWM(Pin(17))
        pin4.freq(50)
        car_right() # Car to the right
    # Dot matrix displays a right pattern
        show_char(matrix_fonts.textFont1['<'])
    elif value == '0xff02fd':
        car_stop() # Car to the right
    # Dot matrix displays a stop pattern
        show_char(matrix_fonts.textFont1['!']) 
try:
    while True:
        irValue = recvPin.ir_read() #Call ir_read() to read the value of the pressed key and assign it to IRValue.
        if irValue:
            print(irValue)
            handleControl(irValue)
except:
    pass