#include <Servo.h>
Servo lgservo;
#define ML 15
#define ML_PWM 17
#define MR 14
#define MR_PWM 16
#define servo2 3

char val;
char wifiData;

void setup() {
  Serial1.begin(9600);
  pinMode(ML, OUTPUT);
  pinMode(ML_PWM, OUTPUT);
  pinMode(MR, OUTPUT);
  pinMode(MR_PWM, OUTPUT);
  
  lgservo.attach(3);
  lgservo.write(180);
}

void loop() {
  if(Serial1.available() > 0)
  {
    val = Serial1.read();
    Serial.print(val);
  }
  switch(val)
  {
    case 'F': car_forward(); break;
    case 'B': car_back(); break;
    case 'L': car_left(); break;
    case 'R': car_right(); break;
    case 'S': car_stop(); break;
    case 'p': lgservo.write(110); break;
    case 'x': lgservo.write(180); break;
  }
}


void car_forward()
{
  digitalWrite(ML,LOW);
  analogWrite(ML_PWM,127);
  digitalWrite(MR,LOW);
  analogWrite(MR_PWM,127);
}

void car_back()
{
  digitalWrite(ML,HIGH);
  analogWrite(ML_PWM,127);
  digitalWrite(MR,HIGH);
  analogWrite(MR_PWM,127);
}

void car_left()
{
  digitalWrite(ML,HIGH);
  analogWrite(ML_PWM,150);
  digitalWrite(MR,LOW);
  analogWrite(MR_PWM,105);
}

void car_right()
{
  digitalWrite(ML,LOW);
  analogWrite(ML_PWM,105);
  digitalWrite(MR,HIGH);
  analogWrite(MR_PWM,150);
}

void car_stop()
{
  digitalWrite(ML,LOW);
  analogWrite(ML_PWM,0);
  digitalWrite(MR,LOW);
  analogWrite(MR_PWM,0);
}
