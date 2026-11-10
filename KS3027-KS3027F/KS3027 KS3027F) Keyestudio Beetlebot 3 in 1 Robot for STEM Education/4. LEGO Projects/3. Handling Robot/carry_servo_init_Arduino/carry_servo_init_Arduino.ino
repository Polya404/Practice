#include <Servo.h>
Servo lgservo;  // create servo object to control a servo

void setup() {
  lgservo.attach(3);  // attaches the servo on pin 3 to the servo object
}

void loop() {
  lgservo.write(180); // tell servo to go to position
}
