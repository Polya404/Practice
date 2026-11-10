//**********************************************************************************
/*
Project 07.1 Ultrasonic Ranging
Ultrasonic detection of distance from objects
*/
#define trigPin 10 // define trigPin gpio10.
#define echoPin 11 // define echoPin gpio11.
#define MAX_DISTANCE 700 // Maximum sensor distance is rated at 400-500cm.
//timeOut= 2*MAX_DISTANCE /100 /340 *1000000 = MAX_DISTANCE*58.8
float timeOut = MAX_DISTANCE * 60; 
int soundVelocity = 340; // define sound speed=340m/s
void setup() {
 pinMode(trigPin,OUTPUT);// set trigPin to output mode
 pinMode(echoPin,INPUT); // set echoPin to input mode
 Serial.begin(115200); // Open serial monitor at 115200 baud to see ping results.
}
void loop() {
 delay(100); // Wait 100ms between pings (about 20 pings/sec). 
 Serial.print("Distance: ");
 Serial.print(getSonar()); // Send ping, get distance in cm and print result 
 Serial.println("cm");
}
float getSonar() {
 unsigned long pingTime;
 float distance;
 // make trigPin output high level lasting for 10us to trigger HC_SR04
 digitalWrite(trigPin, HIGH); 
 delayMicroseconds(10);
 digitalWrite(trigPin, LOW);
 // Wait HC-SR04 returning to the high level and measure out this waiting time
 pingTime = pulseIn(echoPin, HIGH, timeOut); 
 // calculate the distance according to the time
 distance = (float)pingTime * soundVelocity / 2 / 10000; 
 return distance; // return the distance value
}
//**********************************************************************************
