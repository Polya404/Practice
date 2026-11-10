from machine import Pin, ADC, PWM
import time

#Define GPIO9’s output frequency as 50Hz and its duty cycle as 4915, and assign them to PWM.
servoPin = PWM(Pin(9))
servoPin.freq(50)
servoPin.duty_u16(4915)
time.sleep(1)

# Initialize the left photoresistance to pin 26 (ADC function)
adc1 = ADC(Pin(26))

# Initialize the right photoresistance to pin 27 (ADC function)
adc2 = ADC(Pin(27))

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
  pin2.duty_u16(18000) 
  pin3.value(0)
  pin4.duty_u16(18000) 

# As a function of the car going left.
def car_left(): 
  pin1.value(0)
  pin2.duty_u16(18000)  
  pin3.value(1)
  pin4.duty_u16(50000) 

# As a function of the car going right.
def car_right(): 
  pin1.value(1)
  pin2.duty_u16(50000)  
  pin3.value(0)
  pin4.duty_u16(18000)

# As a function of the car stopping.
def car_stop():
  pin2.deinit()
  pin4.deinit()
  pin1.value(0)
  pin2.duty_u16(0)  
  pin3.value(0)
  pin4.duty_u16(0)  

while True:
    adcValue1 = adc1.read_u16() # read the ADC value of the left photoresistance.
    adcValue2 = adc2.read_u16() # read the ADC value of the right photoresistance.
    print("ADC Value1:", adcValue1 ,"ADC Value2:", adcValue2)
    time.sleep(0.5)
    if adcValue1 > 50000 and adcValue2 > 50000: #Range values measured by left and right photoresistances.
        pin2=PWM(Pin(16))
        pin2.freq(50)
        pin4=PWM(Pin(17))
        pin4.freq(50)
        car_forward() #Car ahead
    elif adcValue1 > 50000 and adcValue2 <= 50000:
        pin2=PWM(Pin(16))
        pin2.freq(50)
        pin4=PWM(Pin(17))
        pin4.freq(50)
        car_left()
    elif adcValue1 <= 50000 and adcValue2 > 50000:
        pin2=PWM(Pin(16))
        pin2.freq(50)
        pin4=PWM(Pin(17))
        pin4.freq(50)
        car_right()
    else:
        car_stop()