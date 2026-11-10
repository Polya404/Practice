#define ML 15
#define ML_PWM 17
#define MR 14
#define MR_PWM 16
#define lightL_pin 26
#define lightR_pin 27
//超声波传感器
#include <UltrasonicSensor.h> //定义超声波模块函数库
#define Trig 10
#define Echo 11
UltrasonicSensor ultrasonic(10, 11);//连接Trigger和Echo引脚
const int servopin = 9;
#include<music.h>
#define buz 12  //蜂鸣器引脚
music Music(buz);
#include <Adafruit_NeoPixel.h>
Adafruit_NeoPixel rgb_display = Adafruit_NeoPixel(4,13,NEO_GRB + NEO_KHZ800);
boolean neo_state = 0;
int neo_flag=0;
int left_val=255;
int right_val=255;
String left_str;
String right_str;
char val;
#define trackL 7
#define trackR 8
#include <Matrix_pico.h>
Matrix myMatrix(20,21);
int matrix_flag;
boolean face1_flag = 0;
boolean face2_flag = 0;
int face_count=0;
uint8_t  LEDArray[8];
uint8_t matrix_smile[8]={0x42, 0xa5, 0xa5, 0x00, 0x00, 0x24, 0x18, 0x00};
uint8_t matrix_front[8]={0x18, 0x3c, 0x5a, 0x99, 0x18, 0x18, 0x18, 0x18};
uint8_t matrix_back[8]={0x18, 0x18, 0x18, 0x18, 0x99, 0x5a, 0x3c, 0x18};
uint8_t matrix_left[8]={0x08, 0x04, 0x02, 0xff, 0xff, 0x02, 0x04, 0x08};
uint8_t matrix_right[8]={0x10, 0x20, 0x40, 0xff, 0xff, 0x40, 0x20, 0x10};
uint8_t matrix_stop[8]={0xff, 0x81, 0xbd, 0xa5, 0xa5, 0xbd, 0x81, 0xff};
uint8_t matrix_tsundere[8]={0x00, 0xf7, 0x00, 0x08, 0x14, 0x20, 0x00, 0x00};
uint8_t matrix_squinting[8]={0x00, 0x41, 0x22, 0x14, 0x22, 0x41, 0x1c, 0x00};
uint8_t matrix_despise1[8]={0x00, 0x11, 0x77, 0x00, 0x1c, 0x00, 0x00, 0x00};
uint8_t matrix_speechless[8]={0x00, 0x77, 0x00, 0x1c, 0x14, 0x1c, 0x00, 0x00};
uint8_t matrix_heart[8]={0x00, 0x66, 0x99, 0x81, 0x81, 0x42, 0x24, 0x18};
uint8_t matrix_clear[8]={0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};


void setup() {
  Serial1.setRX(1);
  Serial1.setTX(0);
  Serial1.begin(9600);
  pinMode(ML, OUTPUT);
  pinMode(ML_PWM, OUTPUT);
  pinMode(MR, OUTPUT);
  pinMode(MR_PWM, OUTPUT);
  pinMode(lightL_pin, INPUT);
  pinMode(lightR_pin, INPUT);
  pinMode(Trig, OUTPUT);
  pinMode(Echo, INPUT);
  pinMode(buz, OUTPUT);
  rgb_display.begin();
  myMatrix.begin(0x70);
  myMatrix.clear();
  myMatrix.write();
  pinMode(servopin, OUTPUT);
}

void loop() {
  if(Serial1.available() > 0)
  {
    val = Serial1.read();
    Serial.print(val);
    if(val == 'u')
    {
      Serial.println("left speed : ");
      left_str = Serial1.readStringUntil('#');
      left_val = String(left_str).toInt();
      Serial.println(left_val);
    }
    if(val == 'v')
    {
      Serial.println("right speed : ");
      right_str = Serial1.readStringUntil('#');
      right_val = String(right_str).toInt();
      Serial.println(right_val);
    }
  }
  switch(val)
  {
    case 'F': car_forward(); break;
    case 'B': car_back(); break;
    case 'L': car_left(); break;
    case 'R': car_right(); break;
    case 'S': car_stop(); break;
    case 'a': tone(buz, 294); delay(200); break;
    case 'b': noTone(buz); break;
    case 'c': Music.birthday(); break;
    case 'd': noTone(buz); break;
    case 'e': func_neo1(); break;
    case 'f': neo_stop(); break;
    case 'g': func_neo2(); break;
    case 'z': neo_state = 0; break;
    case 'i': face1(); break;
    case 'j': face_stop(); break;
    case 'k': face2(); break;
    case 'y': face1_flag=0; break;
    case 'l': tracking(); break;
    case 'm': avoid(); break;
    case 'n': followLightCar(); break;
    case 'o': followCar(); break;
  }
}


void followLightCar()
{
  int lightL = analogRead(lightL_pin);
  int lightR = analogRead(lightR_pin);
  Serial.print(lightL);
  Serial.print("  ");
  Serial.println(lightR);
  if((lightL > 3100) && (lightR > 3100))
  {
    digitalWrite(ML,LOW);
    analogWrite(ML_PWM,180);
    digitalWrite(MR,LOW);
    analogWrite(MR_PWM,150);
  }
  else if((lightL > 3100) && (lightR <= 3100))
  {
    car_left();
  }
  else if((lightL <= 3100) && (lightR > 3100))
  {
    car_right();
  }
  else
  {
    car_stop();
  }
}

void followCar()
{
  int distance = ultrasonic.distanceInCentimeters();//超声波测量的距离
  Serial.print("distance = ");
  Serial.println(distance);
  if((distance > 10) && (distance < 35))
  {
    digitalWrite(ML,LOW);
    analogWrite(ML_PWM,180);
    digitalWrite(MR,LOW);
    analogWrite(MR_PWM,150);
  }
  else if((distance > 6) && (distance <= 10))
  {
    car_stop();
  }
  else if(distance <= 6)
  {
    digitalWrite(ML,HIGH);
    analogWrite(ML_PWM,70);
    digitalWrite(MR,HIGH);
    analogWrite(MR_PWM,100);
  }
  else
  {
    car_stop();
  }
  
}


void avoid()
{
  int distance = ultrasonic.distanceInCentimeters();
  Serial.print("distance = ");
  Serial.println(distance);
  if(distance <= 8)
  {
    car_stop();
    delay(300);
    servopulse(servopin,180);
    delay(100);
    int distanceL = ultrasonic.distanceInCentimeters();
    delay(50);
    servopulse(servopin,0);
    delay(100);
    int distanceR = checkdistance();
    delay(50);
    if(distanceL > distanceR)
    {
      car_left();
      servopulse(servopin,90);
      delay(500);
    }
    else
    {
      car_right();
      servopulse(servopin,90);
      delay(500);
    }
  }
  else
  {
    digitalWrite(ML,LOW);
    analogWrite(ML_PWM,180);
    digitalWrite(MR,LOW);
    analogWrite(MR_PWM,150);
  }
}

float checkdistance() {
  digitalWrite(Trig, LOW);
  delayMicroseconds(2);
  digitalWrite(Trig, HIGH);
  delayMicroseconds(10);
  digitalWrite(Trig, LOW);
  float distance = pulseIn(Echo, HIGH) / 58.00;
  delay(10);
  return distance;
}

void servopulse(int servopin,int myangle)//舵机运行角度
{
  for(int i=0; i<20; i++)
  {
    int pulsewidth = (myangle*11)+500;
    digitalWrite(servopin,HIGH);
    delayMicroseconds(pulsewidth);
    digitalWrite(servopin,LOW);
    delay(20-pulsewidth/1000);
  }  
}

void func_neo1()
{
  if(neo_state == 0)
  {
    neo_flag++;
    neo_state = 1;
  }
  if(neo_flag >= 6)
  {
    neo_flag = 6;
  }
  switch(neo_flag)
  {
    case 1: for (int i = 1; i <= 4; i = i + (1)) {
      rgb_display.setPixelColor(i-1, (((100 & 0xffffff) << 16) | ((0 & 0xffffff) << 8) | 0));rgb_display.show();
    }
    break;
    case 2: for (int i = 1; i <= 4; i = i + (1)) {
      rgb_display.setPixelColor(i-1, (((0 & 0xffffff) << 16) | ((100 & 0xffffff) << 8) | 0));rgb_display.show();
    }
    break;
    case 3: for (int i = 1; i <= 4; i = i + (1)) {
      rgb_display.setPixelColor(i-1, (((0 & 0xffffff) << 16) | ((0 & 0xffffff) << 8) | 100));rgb_display.show();
    }
    break;
    case 4: for (int i = 1; i <= 4; i = i + (1)) {
      rgb_display.setPixelColor(i-1, (((100 & 0xffffff) << 16) | ((100 & 0xffffff) << 8) | 0));rgb_display.show();
    }
    break;
    case 5: for (int i = 1; i <= 4; i = i + (1)) {
      rgb_display.setPixelColor(i-1, (((0 & 0xffffff) << 16) | ((100 & 0xffffff) << 8) | 100));rgb_display.show();
    }
    break;
    case 6: for (int i = 1; i <= 4; i = i + (1)) {
      rgb_display.setPixelColor(i-1, (((100 & 0xffffff) << 16) | ((100 & 0xffffff) << 8) | 100));rgb_display.show();
    }
    break;
  }
  
}

void func_neo2()
{
  if(neo_state == 0)
  {
    neo_flag--;
    neo_state = 1;
  }
  if(neo_flag <= 1)
  {
    neo_flag = 1;
  }
  switch(neo_flag)
  {
    case 1: for (int i = 1; i <= 4; i = i + (1)) {
      rgb_display.setPixelColor(i-1, (((100 & 0xffffff) << 16) | ((0 & 0xffffff) << 8) | 0));rgb_display.show();
    }
    break;
    case 2: for (int i = 1; i <= 4; i = i + (1)) {
      rgb_display.setPixelColor(i-1, (((0 & 0xffffff) << 16) | ((100 & 0xffffff) << 8) | 0)); rgb_display.show();
    }
    break;
    case 3: for (int i = 1; i <= 4; i = i + (1)) {
      rgb_display.setPixelColor(i-1, (((0 & 0xffffff) << 16) | ((0 & 0xffffff) << 8) | 100)); rgb_display.show();
    }
    break;
    case 4: for (int i = 1; i <= 4; i = i + (1)) {
      rgb_display.setPixelColor(i-1, (((100 & 0xffffff) << 16) | ((100 & 0xffffff) << 8) | 0));rgb_display.show();
    }
    break;
    case 5: for (int i = 1; i <= 4; i = i + (1)) {
      rgb_display.setPixelColor(i-1, (((0 & 0xffffff) << 16) | ((100 & 0xffffff) << 8) | 100)); rgb_display.show();
    }
    break;
    case 6: for (int i = 1; i <= 4; i = i + (1)) {
      rgb_display.setPixelColor(i-1, (((100 & 0xffffff) << 16) | ((100 & 0xffffff) << 8) | 100));rgb_display.show();
    }
    break;
  }
  
}

void neo_stop()
{
  neo_state = 0;
  for (int i = 1; i <= 4; i = i + (1)) {
    rgb_display.setPixelColor((i)-1, (((0 & 0xffffff) << 16) | ((0 & 0xffffff) << 8) | 0));rgb_display.show();
  }
}


void tracking()
{
  boolean trackL_val = digitalRead(trackL);
  boolean trackR_val = digitalRead(trackR);
  Serial.print(trackL_val);
  Serial.print("  ");
  Serial.println(trackR_val);
  if((trackL_val == 1) && (trackR_val == 1))
  {
    digitalWrite(ML,LOW);
    analogWrite(ML_PWM,140);
    digitalWrite(MR,LOW);
    analogWrite(MR_PWM,120);
  }
  else if((trackL_val == 1) && (trackR_val == 0))
  {
    digitalWrite(ML,HIGH);
    analogWrite(ML_PWM,200);
    digitalWrite(MR,LOW);
    analogWrite(MR_PWM,150);
  }
  else if((trackL_val == 0) && (trackR_val == 1))
  {
    digitalWrite(ML,LOW);
    analogWrite(ML_PWM,150);
    digitalWrite(MR,HIGH);
    analogWrite(MR_PWM,200);
  }
  else
  {
    car_stop();
  }
}


void face1()
{
  if(face1_flag==0){
    matrix_flag = 1;
  }
  if(matrix_flag == 1)
  {
    face_count++;
    if(face_count == 6)
    {
      face_count = 6;
    }
    matrix_flag = 0;
    face1_flag = 1;
  }
  switch(face_count)
  {
    case 1: myMatrix.clear();myMatrix.write();matrix_display(matrix_smile); break;
    case 2: myMatrix.clear();myMatrix.write();matrix_display(matrix_tsundere); break;
    case 3: myMatrix.clear();myMatrix.write();matrix_display(matrix_squinting); break;
    case 4: myMatrix.clear();myMatrix.write();matrix_display(matrix_despise1); break;
    case 5: myMatrix.clear();myMatrix.write();matrix_display(matrix_speechless); break;
    case 6: myMatrix.clear();myMatrix.write();matrix_display(matrix_heart); break;
  }
}

void face_stop()
{
  myMatrix.clear();myMatrix.write();
}

void face2()
{
  if(face1_flag==0){
    matrix_flag = 1;
  }
  if(matrix_flag == 1)
  {
    face_count--;
    if(face_count == 1)
    {
      face_count = 1;
    }
    matrix_flag = 0;
    face1_flag = 1;
  }
  switch(face_count)
  {
    case 1: myMatrix.clear();myMatrix.write();matrix_display(matrix_smile); break;
    case 2: myMatrix.clear();myMatrix.write();matrix_display(matrix_tsundere); break;
    case 3: myMatrix.clear();myMatrix.write();matrix_display(matrix_squinting); break;
    case 4: myMatrix.clear();myMatrix.write();matrix_display(matrix_despise1); break;
    case 5: myMatrix.clear();myMatrix.write();matrix_display(matrix_speechless); break;
    case 6: myMatrix.clear();myMatrix.write();matrix_display(matrix_heart); break;
  }
}

void matrix_display(uint8_t led_array[8]){
  for(int i=0; i<8; i++)
  {
    LEDArray[i]=led_array[i];
    for(int j=7; j>=0; j--)
    {
      if((LEDArray[i]&0x01)>0)
      myMatrix.drawPixel(j, i,1);
      LEDArray[i] = LEDArray[i]>>1;
    }
  }
  myMatrix.write();  // dot matrix shows
}


void car_forward()
{
  digitalWrite(ML,LOW);
  analogWrite(ML_PWM,left_val);
  digitalWrite(MR,LOW);
  analogWrite(MR_PWM,right_val);
}

void car_back()
{
  digitalWrite(ML,HIGH);
  analogWrite(ML_PWM,(255-left_val));
  digitalWrite(MR,HIGH);
  analogWrite(MR_PWM,(255-right_val));
}

void car_left()
{
  digitalWrite(ML,HIGH);
  analogWrite(ML_PWM,(255-left_val));
  digitalWrite(MR,LOW);
  analogWrite(MR_PWM,right_val);
}

void car_right()
{
  digitalWrite(ML,LOW);
  analogWrite(ML_PWM,left_val);
  digitalWrite(MR,HIGH);
  analogWrite(MR_PWM,(255-right_val));
}

void car_stop()
{
  digitalWrite(ML,LOW);
  analogWrite(ML_PWM,0);
  digitalWrite(MR,LOW);
  analogWrite(MR_PWM,0);
}
