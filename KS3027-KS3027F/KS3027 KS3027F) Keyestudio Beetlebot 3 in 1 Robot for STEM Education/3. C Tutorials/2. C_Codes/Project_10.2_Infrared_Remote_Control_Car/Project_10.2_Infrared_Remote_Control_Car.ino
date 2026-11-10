//*************************************************************************************
/*
Project 09.2:Infrared remote control car
*/ 
//红外接收
#include "IR.h" //红外接收的库
#define IR_Pin 6  //红外接收引脚

//点阵
#include <Matrix_pico.h> //点阵的库
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

//舵机
const int servopin = 9;//定义舵机的脚位在GPIO9
int myangle;
int pulsewidth;

void setup() {
  Serial.begin(115200);//打开串口，波特率115200
  pinMode(left_ctrl,OUTPUT);//设置左电机方向控制引脚为输出
  pinMode(left_pwm,OUTPUT);//设置左电机pwm控制速度引脚为输出
  pinMode(right_ctrl,OUTPUT);//设置右电机方向控制引脚为输出
  pinMode(right_pwm,OUTPUT);//设置右电机pwm控制速度引脚为输出
  
  // In case the interrupt driver crashes on setup, give a clue
  // to the user what's going on.
  IR_Init(IR_Pin); // 开始接收信号
 
  servopulse(servopin,90);//设置舵机初始角度为90
  delay(300);
  
  myMatrix.begin(0x70);//初始化点阵
  myMatrix.clear(); //点阵清屏
  myMatrix.write();
}

void loop() {
    if(flagCode){
    int irValue = IR_Decode(flagCode);
    Serial.println(irValue, HEX);
    handleControl(irValue);
    IR_Release();
  }
}
void handleControl(unsigned long value) {

  // Handle the commands
  if (value == 0xFF629D){
     car_front(); //前进
     myMatrix.clear();
     matrix_display(matrix_front);//点阵显示前进图案
     myMatrix.write();//点阵显示前进图案
  }
  else if (value == 0xFFA857){
     car_back(); //后退
     myMatrix.clear();
     matrix_display(matrix_back);//点阵显示向后图案
     myMatrix.write();//点阵显示向后图案
  }
  else if (value == 0xFF22DD){ 
     car_left(); //向左转
     myMatrix.clear();
     matrix_display(matrix_left);//点阵显示向左图案
     myMatrix.write();//点阵显示向左图案
  } 
  else if (value == 0xFFC23D){
     car_right(); // 向右转
     myMatrix.clear();
     matrix_display(matrix_right);//点阵显示向右图案
     myMatrix.write();//点阵显示向右图案
  }
  else if (value == 0xFF02FD){ 
     car_Stop();
     myMatrix.clear();
     matrix_display(matrix_stop);//点阵显示停止图案
     myMatrix.write();//点阵显示停止图案 
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
  analogWrite(left_pwm,150); //左电机PWM控制速度150
  digitalWrite(right_ctrl,LOW); //右电机方向控制引脚低电平
  analogWrite(right_pwm,150); //右电机PWM控制速度150
}
void car_back()//定义后退的状态
{
  digitalWrite(left_ctrl,HIGH); //左电机方向控制引脚高电平.
  analogWrite(left_pwm,100); //左电机PWM控制速度100
  digitalWrite(right_ctrl,HIGH); //右电机方向控制引脚高电平.
  analogWrite(right_pwm,100); //右电机PWM控制速度100
}
void car_left()//定义左转的状态
{
  digitalWrite(left_ctrl,HIGH); //左电机方向控制引脚高电平
  analogWrite(left_pwm,100); //左电机PWM控制速度100
  digitalWrite(right_ctrl,LOW); //右电机方向控制引脚低电平
  analogWrite(right_pwm,100); //右电机PWM控制速度100
}
void car_right()//定义右转的状态
{
  digitalWrite(left_ctrl,LOW); //左电机方向控制引脚低电平
  analogWrite(left_pwm,150); //左电机PWM控制速度150
  digitalWrite(right_ctrl,HIGH); //右电机方向控制引脚高电平
  analogWrite(right_pwm,150); //右电机PWM控制速度150
}
void car_Stop()//定义停止的状态
{
  digitalWrite(left_ctrl,LOW);//左电机方向控制引脚低电平
  analogWrite(left_pwm,0);//左电机PWM控制速度0
  digitalWrite(right_ctrl,LOW);//右电机方向控制引脚低电平
  analogWrite(right_pwm,0);//右电机PWM控制速度0
}
//*************************************************************************************
