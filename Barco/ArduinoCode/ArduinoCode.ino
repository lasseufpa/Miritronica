#include<Servo.h> 
#include<MsTimer2.h>
Servo leme;
int pos;
unsigned long anterior=0, atual=0;
int entrada1=4,entrada2=5;

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
      }else{
      if (a=='u'){
      praFrente();
      }else{
      if(a=='d'){
      praTras();
      }
      }
      }
          
  }
    anterior=millis();
  }
  
leme.write(pos);
atual=millis();
 if(atual>anterior+2000){
   MsTimer2::start();
   paraMotor();
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
  
  void paraMotor()  
{  
  digitalWrite(entrada1, HIGH);  
  digitalWrite(entrada2, HIGH);  
 // delay(500);  
}

void praFrente()
{
  digitalWrite(entrada2, LOW);  
  digitalWrite(entrada1, HIGH);  
  //delay(490);  
}

void praTras()
{  
  digitalWrite(entrada2, HIGH);  
   digitalWrite(entrada1, LOW); 
  //delay(490); 
}
