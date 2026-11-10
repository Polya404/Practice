from machine import ADC, Pin
import time
# Initialize the photoresistance to pin 26 (ADC function)
adc = ADC(Pin(26))

# Print the current adc value of the photoresistance cyclically 
try:
    while True:
        adcValue = adc.read_u16() # read the ADC value of photoresistance
        print("ADC Value:", adcValue) #Send the ADC value of photoresistance and print the result.
        time.sleep(0.5)
except:
    pass