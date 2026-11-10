from machine import Pin, PWM
import time

#Define GPIO9’s output frequency as 50Hz and its duty cycle as 4915, and assign them to PWM.
servoPin = PWM(Pin(9))
servoPin.freq(50)
servoPin.duty_u16(4915)
time.sleep(1)

#Set the pin of the tracking sensor
tracking_left = Pin(7, Pin.IN)
tracking_right = Pin(8, Pin.IN)

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
  pin4.duty_u16(4000) 

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

while True:
    L_value = tracking_left.value() #Left infrared tracking value is assigned to the variable L_value.
    R_value = tracking_right.value() #Right infrared tracking value is assigned to the variable R_value.
    if L_value == 1 and R_value == 1: #Black lines were detected in both left and right infrared tracking.
        pin2=PWM(Pin(16))
        pin2.freq(50)
        pin4=PWM(Pin(17))
        pin4.freq(50) 
        car_forward() #Car ahead
    elif L_value == 1 and R_value == 0:
        pin2=PWM(Pin(16))
        pin2.freq(50)
        pin4=PWM(Pin(17))
        pin4.freq(50) 
        car_left()
    elif L_value == 0 and R_value == 1:
        pin2=PWM(Pin(16))
        pin2.freq(50)
        pin4=PWM(Pin(17))
        pin4.freq(50) 
        car_right()
    else:
        car_stop() 