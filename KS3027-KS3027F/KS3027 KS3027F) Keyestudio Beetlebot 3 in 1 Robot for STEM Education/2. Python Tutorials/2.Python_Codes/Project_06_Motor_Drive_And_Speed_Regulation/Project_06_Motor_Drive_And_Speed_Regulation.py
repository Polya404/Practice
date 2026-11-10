from machine import Pin,PWM
import time

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
  pin1.value(0)
  pin2.duty_u16(0)  
  pin3.value(0)
  pin4.duty_u16(0)    
try:
    while True:
        car_forward() #Car ahead
        time.sleep(2) # delay 2s
        car_back() # Car goes back
        time.sleep(2)    
        car_left() # Car to the left
        time.sleep(2)    
        car_right() # Car to the right
        time.sleep(2)    
        car_stop() # Car stop
        time.sleep(2)     
except:
    pass 