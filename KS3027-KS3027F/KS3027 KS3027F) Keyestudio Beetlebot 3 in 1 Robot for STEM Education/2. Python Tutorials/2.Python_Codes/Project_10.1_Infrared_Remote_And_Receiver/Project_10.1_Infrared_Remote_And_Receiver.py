from irrecvdata import irGetCMD #Import the infrared decoder.

recvPin = irGetCMD(6) #Associate the infrared decoder with Pin(6).

#When infrared key value is obtained, print it out in"Shell" repetitively.
try:
    while True:
        irValue = recvPin.ir_read() #Call ir_read() to read the value of the pressed key and assign it to IRValue.
        if irValue:
           print(irValue) # Send the irValue of Infrared Receiver and print the result.
except:
    pass