//**********************************************************************************
/*
Project 11.2 WiFi control dot matrix
*/
#include <Matrix_pico.h>
Matrix myMatrix(20,21);//Define the dot matrix pins in GPIO20,GPIO21.
//Array, used to store the data of the pattern, can be calculated yourself 
//or retrieved from the touch tool
uint8_t Heart_LedArray[8]={0x00, 0x66, 0x99, 0x81, 0x42, 0x24, 0x18, 0x00};
uint8_t Ten_LedArray[8]={0x18, 0x18, 0x18, 0xff, 0xff, 0x18, 0x18, 0x18};
uint8_t Smile_LedArray[8]={0x00, 0xc3, 0x00, 0x00, 0x18, 0x81, 0x42, 0x3c};
uint8_t  LEDArray[8];
char wifiData;

void setup() {
  Serial1.begin(9600);
  myMatrix.begin(0x70);
  myMatrix.clear();
  myMatrix.write();
}

void loop() {
  if(Serial1.available() > 0)
  {
    wifiData = Serial1.read();
    Serial.print(wifiData);
    if(wifiData == '#')
    {
      Serial.println("");
    }
    delay(100);
    
    if(wifiData == 'i')
    {
      myMatrix.clear();
      myMatrix.write();
      matrix_display(Smile_LedArray); 
    }
    else if(wifiData == 'j')
    {
      myMatrix.clear();
      myMatrix.write();
      matrix_display(Ten_LedArray);
    }
    else if(wifiData == 'k')
    {
      myMatrix.clear();
      myMatrix.write();
      matrix_display(Heart_LedArray);
    }
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
//**********************************************************************************
