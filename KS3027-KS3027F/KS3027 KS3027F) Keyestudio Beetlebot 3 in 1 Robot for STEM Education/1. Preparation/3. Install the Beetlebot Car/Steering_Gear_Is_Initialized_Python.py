from machine import Pin, PWM
import time
#Define GPIO9’s output frequency as 50Hz and assign them to PWM.
pwm = PWM(Pin(9)) 
pwm.freq(50)
'''
#Duty cycle corresponding to steering gear Angle
0°----2.5%----1638
45°----5%----3276
90°----7.5%----4915
135°----10%----6553
180°----12.5%----8192
'''
#steering gear Angle are fit to its duty cycle. 
angle_0 = 1638
angle_45 = 3276
angle_90 = 4915
angle_135 = 6553
angle_180 = 8192
while True:
    pwm.duty_u16(angle_90)
    time.sleep(1)