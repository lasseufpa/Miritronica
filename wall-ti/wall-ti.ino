/* Observações: 
 * As LED's usadas no projeto são de ânodo comum, portanto, apresentam a lógica inversa de ativação comparadas às leds mais usuais
 * O sensor ultrassônico utilizado é do tipo ping que usa apenas uma porta como echo e trigger.
*/
//Include da biblioteca dos servos motores
#include <Servo.h>

//Declaração dos objetos do tipo Servo que controlarão os motores
Servo braco1;
Servo braco2;
Servo cabeca;

//Declaração do pino do sensor ultrassônico
int sensor = 12;
//Pino que ativa o segundo arduino responsável pelo áudio
int ar = 11;
//Pinos dos vermelhos, do azul e do verde das LEDs RGB
int v = 2;
int r = 4;
int b = 3;
int g = 5;

int p = 0;

void setup() {
  //Definição de tipo de pino das LEDs e do pino de comunicação com o segundo arduino (todos saída/escrita)
  pinMode(ar, OUTPUT);
  pinMode(g, OUTPUT);
  pinMode(r, OUTPUT);
  pinMode(b, OUTPUT);
  pinMode(v, OUTPUT);
  
  //Definição de qual pino cada objeto Servo irá controlar
  braco1.attach(6);
  braco2.attach(7);
  cabeca.attach(8);
}

void loop() {
  //Mantém o pino ligado ao outro arduino no nível lógico alto desativando a função de áudio
  digitalWrite(ar, HIGH);
  
  //Liga os pinos azul e verde das LEDs
  digitalWrite(g, LOW);
  digitalWrite(b, LOW);

  //Ativa o gatilho do sensor que manda uma onda ultrassônica e lê a quantidade de microssegundos que a onda demorou para ir e voltar
  long tempo, cm;
  pinMode(sensor, OUTPUT);
  digitalWrite(sensor, LOW);
  delayMicroseconds(10);
  digitalWrite(sensor, HIGH);
  delayMicroseconds(10);
  digitalWrite(sensor, LOW);
  pinMode(sensor, INPUT);
  tempo = pulseIn(sensor, HIGH);
  
  //Transforma a quantidade de tempo que a onda demorou em seu trajeto em distância (centímetros)
  cm = microsecondsToCentimeters(tempo);

  //Checa se o obstáculo mais próximo está a pelo menos 15 centímetros 
  if ( cm < 15 && cm!=0 ) {
    
    //Escreve na saída ligada ao outro arduino nível lógico baixo, ativando a função de áudio do segundo arduino
    digitalWrite(ar, LOW);
    
    //Desliga as LEDs azul e verde
    digitalWrite(g, HIGH);
    digitalWrite(b, HIGH);
    
    //Laço responsável por mover os braços de 0 até 90 graus e a cabeça de 45 até 135 graus
    for (p = 0; p < 90; p += 15)
    {
      braco1.write(p);
      braco2.write(p);
      cabeca.write(p+45);
      //Pisca as LEDs vermelhas
      digitalWrite(r, HIGH);
      digitalWrite(v, HIGH);
      delay(100);
      digitalWrite(r, LOW);
      digitalWrite(v, LOW);
      delay(100);
    }


  //Laço responsável por mover os braços de 90 até 0 graus e a cabeça de 135 até 45 graus
    for (p = 90; p > 0; p -= 15)
    {
      braco1.write(p);
      braco2.write(p);
      cabeca.write(p+45);
      //Pisca as LEDs vermelhas
      digitalWrite(r, HIGH);
      digitalWrite(v, HIGH);
      delay(100);
      digitalWrite(r, LOW);
      digitalWrite(v, LOW);
      delay(100);
    }

  }

}

//Função responsável por transformar o tempo de viagem da onda em centímetros
long microsecondsToCentimeters(long microseconds) {
  return microseconds /  29 / 2;
}

