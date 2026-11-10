from machine import Pin
import time

tracking_left = Pin(7, Pin.IN)
tracking_right = Pin(8, Pin.IN)

while True:
    L_value = tracking_left.value()
    R_value = tracking_right.value()
    print("L_value:%d, R_value:%d" %(L_value, R_value))
    time.sleep(0.5)