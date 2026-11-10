//*************************************************************************************
/*
Project 07.2: follow me
Car follows the object
*/ 
//电机
const int left_ctrl = 15;//定义左电机方向控制引脚GPIO15
const int left_pwm = 17;//定义左电机速度控制引脚GPIO17
const int right_ctrl = 14;//定义右电机方向控制引脚GPIO14
const int right_pwm = 16;//定义右电机速度控制引脚GPIO16

//超声波传感器
#include <UltrasonicSensor.h> //定义超声波模块函数库
#define TRIG_PIN 10// 定义超声波的信号输入在GPIO10
#define ECHO_PIN 11//定义超声波的信号输出在GPIO11
UltrasonicSensor ultrasonic(10, 11);//连接Trigger和Echo引脚
long distance;

 //舵机
const int servopin = 9;//定义舵机的脚位在GPIO9
int myangle;
int pulsewidth;

void setup() {
  Serial1.begin(9600);
  pinMode(left_ctrl,OUTPUT);//设置左电机方向控制引脚为输出
  pinMode(left_pwm,OUTPUT);//设置左电机pwm控制速度引脚为输出
  pinMode(right_ctrl,OUTPUT);//设置右电机方向控制引脚为输出
  pinMode(right_pwm,OUTPUT);//设置右电机pwm控制速度引脚为输出
  pinMode(TRIG_PIN,OUTPUT);//设置TRIG_PIN为输出
  pinMode(ECHO_PIN,INPUT);//设置ECHO_PIN为输入
  
  servopulse(servopin,90);//设置舵机初始角度为90
  delay(300);
}

void loop() {
  distance = ultrasonic.distanceInCentimeters();//超声波测量的距离
  Serial.println(distance);
  if(distance<8)//如果距离小于8
  {
    back();//后退
  }
  else if((distance>=8)&&(distance<13))//如果距离大于等于8，小于13
  {
    Stop();//停止
  }
  else if((distance>=13)&&(distance<35))//如果距离大于等于13，小于35
  {
    front();//跟随
  }
  else//如果以上都不是
  {
    Stop();//停止
  }
}

void servopulse(int servopin,int myangle)//舵机运行角度
{
  for(int i=0; i<20; i++)
  {
    pulsewidth = (myangle*11)+500;
    digitalWrite(servopin,HIGH);
    delayMicroseconds(pulsewidth);
    digitalWrite(servopin,LOW);
    delay(20-pulsewidth/1000);
  }  
}

void front()//定义前进的状态
{
  digitalWrite(left_ctrl,LOW); //左电机方向控制引脚低电平
  analogWrite(left_pwm,150); //左电机PWM控制速度150
  digitalWrite(right_ctrl,LOW); //右电机方向控制引脚低电平
  analogWrite(right_pwm,150); //右电机PWM控制速度150
}
void back()//定义后退的状态
{
  digitalWrite(left_ctrl,HIGH); //左电机方向控制引脚高电平
  analogWrite(left_pwm,150); //左电机PWM控制速度150
  digitalWrite(right_ctrl,HIGH); //右电机方向控制引脚高电平
  analogWrite(right_pwm,150); //右电机PWM控制速度150
}
void Stop()//定义停止的状态
{
  digitalWrite(left_ctrl,LOW);//左电机方向控制引脚低电平
  analogWrite(left_pwm,0);//左电机PWM控制速度0
  digitalWrite(right_ctrl,LOW);//右电机方向控制引脚低电平
  analogWrite(right_pwm,0);//右电机PWM控制速度0
}
//*************************************************************************************
