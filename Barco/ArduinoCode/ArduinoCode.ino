#include<Servo.h>
Servo leme;
int pos;
#include <MsTimer2.h>
unsigned long anterior=0, atual=0;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  MsTimer2::set(250, balanceamento); // 250ms period    
  MsTimer2::start();
  pos = 90;
  leme.attach(3);
  leme.write(pos);
  millis();
}

void loop() {
  if (Serial.available())
  {
    MsTimer2::stop();
    char a = Serial.read();
    Serial.println(a);
    if (a == 'l' && pos < 175) {
      pos +=  20;
    }
    else {
      if (a == 'r' && pos > 10) {
        pos -=  20;
      }
    }
    anterior=millis();
  }
  
leme.write(pos);
atual=millis();
 if(atual>anterior+1000){
   MsTimer2::start();
  }
}

 void balanceamento(){
  if (pos > 90) {
    pos = pos - 20;
  } else {
    if (pos < 90) {
      pos = pos + 20;
    }
   }
  }
