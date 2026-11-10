//*************************************************************************************
/*
Project 07.3: avoid obstacles
*/  
#include <Matrix_pico.h>
Matrix myMatrix(20,21);//定义点阵的引脚在GPIO20,GPIO21
//数组，用于储存图案的数据，可以自己算也可以从取摸工具中得到
uint8_t matrix_front[8]={0x18,0x24,0x42,0x99,0x24,0x42,0x81,0x00};
uint8_t matrix_back[8]={0x00,0x81,0x42,0x24,0x99,0x42,0x24,0x18};
uint8_t matrix_left[8]={0x12,0x24,0x48,0x90,0x90,0x48,0x24,0x12};
uint8_t matrix_right[8]={0x48,0x24,0x12,0x09,0x09,0x12,0x24,0x48};
uint8_t matrix_stop[8]={0x18,0x18,0x18,0x18,0x18,0x00,0x18,0x18};
uint8_t  LEDArray[8];

//电机
const int left_ctrl = 15;//定义左电机方向控制引脚GPIO15
const int left_pwm = 17;//定义左电机速度控制引脚GPIO17
const int right_ctrl = 14;//定义右电机方向控制引脚GPIO14
const int right_pwm = 16;//定义右电机速度控制引脚GPIO16

//超声波传感器
#include <UltrasonicSensor.h> //定义超声波模块函数库.
#define TRIG_PIN 10 // 定义超声波的信号输入在gpio10.
#define ECHO_PIN 11 //定义超声波的信号输出在gpio11.
UltrasonicSensor ultrasonic(10, 11);//定义超声波对象并关联引脚.
long distance,a1,a2;//定义三个距离变量

//舵机
const int servopin = 9;//定义舵机的脚位在GPIO9
int myangle;
int pulsewidth;

void setup() {
  pinMode(left_ctrl,OUTPUT);//设置左电机方向控制引脚为输出
  pinMode(left_pwm,OUTPUT);//设置左电机pwm控制速度引脚为输出
  pinMode(right_ctrl,OUTPUT);//设置右电机方向控制引脚为输出
  pinMode(right_pwm,OUTPUT);//设置右电机pwm控制速度引脚为输出
  pinMode(TRIG_PIN,OUTPUT);//设置TRIG_PIN为输出
  pinMode(ECHO_PIN,INPUT);//设置ECHO_PIN为输入
  
  servopulse(servopin,90);//设置舵机初始角度为90
  delay(300);
  
  myMatrix.begin(0x70);//初始化点阵
  myMatrix.clear(); //点阵清屏
  myMatrix.write();
}
 
void loop()
 {
  avoid();//运行避障主程序
}

void avoid()
{
  distance = ultrasonic.distanceInCentimeters(); //获取超声波距离的值
  if((distance < 10)&&(distance != 0))//如果距离小于10且大于0
  {
    car_Stop();//停止
    myMatrix.clear();
    matrix_display(matrix_stop);//点阵显示停止图案
    myMatrix.write();//点阵显示停止图案
    delay(200);
    servopulse(servopin,180);//设置舵机初始角度为90°
    delay(300);
    a1=ultrasonic.distanceInCentimeters();//测量距离
    delay(200);
    servopulse(servopin,0);//设置舵机初始角度为0
    delay(300);
    a2=ultrasonic.distanceInCentimeters();//测量距离
    delay(200);
    if(a1 > a2)//比较距离，如果左边大于右边
    {
      car_left();//向左转
      myMatrix.clear();
      matrix_display(matrix_left);//点阵显示向左图案
      myMatrix.write();//点阵显示向左图案
      servopulse(servopin,90);//设置舵机初始角度为90°
      delay(50);
      myMatrix.clear();
      matrix_display(matrix_front);//点阵显示向前图案
      myMatrix.write();//点阵显示向前图案
    }
    else//如果右边大于左边
    {
      car_right();//向右转
      myMatrix.clear();
      matrix_display(matrix_right);//点阵显示向右图案
      myMatrix.write();//点阵显示向右图案
      servopulse(servopin,90);//设置舵机初始角度为90°
      //delay(50);
      myMatrix.clear();
      matrix_display(matrix_front);//点阵显示前进图案
      myMatrix.write();//点阵显示前进图案
    }
  }
  else//如果以上都不是
  {
    car_front();//前进
    myMatrix.clear();
    matrix_display(matrix_front);//点阵显示前进图案
    myMatrix.write();//点阵显示前进图案
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

//Dot matrix display pattern function
void matrix_display(unsigned char matrix_value[])
{
  for(int i=0; i<8; i++)
    {
      LEDArray[i]=matrix_value[i];
      for(int j=7; j>=0; j--)
      {
        if((LEDArray[i]&0x01)>0)
        myMatrix.drawPixel(j, i,1);
        LEDArray[i] = LEDArray[i]>>1;
      }
    } 
    myMatrix.write();
}

void car_front()//定义前进的状态
{
  digitalWrite(left_ctrl,LOW); //左电机方向控制引脚低电平
  analogWrite(left_pwm,100); //左电机PWM控制速度100
  digitalWrite(right_ctrl,LOW); //右电机方向控制引脚低电平
  analogWrite(right_pwm,100); //右电机PWM控制速度100
}
void car_back()//定义后退的状态
{
  digitalWrite(left_ctrl,HIGH); //左电机方向控制引脚高电平.
  analogWrite(left_pwm,155); //左电机PWM控制速度155
  digitalWrite(right_ctrl,HIGH); //右电机方向控制引脚高电平.
  analogWrite(right_pwm,155); //右电机PWM控制速度155
}
void car_left()//定义左转的状态
{
  digitalWrite(left_ctrl,HIGH); //左电机方向控制引脚高电平
  analogWrite(left_pwm,155); //左电机PWM控制速度155
  digitalWrite(right_ctrl,LOW); //右电机方向控制引脚低电平
  analogWrite(right_pwm,100); //右电机PWM控制速度100
}
void car_right()//定义右转的状态
{
  digitalWrite(left_ctrl,LOW); //左电机方向控制引脚低电平
  analogWrite(left_pwm,100); //左电机PWM控制速度100
  digitalWrite(right_ctrl,HIGH); //右电机方向控制引脚高电平
  analogWrite(right_pwm,155); //右电机PWM控制速度155
}
void car_Stop()//定义停止的状态
{
  digitalWrite(left_ctrl,LOW);//左电机方向控制引脚低电平
  analogWrite(left_pwm,0);//左电机PWM控制速度0
  digitalWrite(right_ctrl,LOW);//右电机方向控制引脚低电平
  analogWrite(right_pwm,0);//右电机PWM控制速度0
}
//*************************************************************************************
