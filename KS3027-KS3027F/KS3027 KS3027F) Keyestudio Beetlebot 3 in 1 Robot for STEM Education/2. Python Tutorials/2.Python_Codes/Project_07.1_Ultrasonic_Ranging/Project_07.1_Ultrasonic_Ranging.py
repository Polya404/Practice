from machine import Pin 
import time

#Define the control pins of the ultrasonic ranging module.  
trigPin=Pin(10,Pin.OUT,0)
echoPin=Pin(11,Pin.IN,0)
#Set the speed of sound.
soundVelocity=340
distance=0

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
    return int(distance)

#Delay for 2 seconds and wait for the ultrasonic module to stabilize,
#Print data obtained from ultrasonic module every 500 milliseconds.
time.sleep_ms(2000)
while True:
    time.sleep_ms(500)
    print('Distance: ',getSonar(),'cm' ) 