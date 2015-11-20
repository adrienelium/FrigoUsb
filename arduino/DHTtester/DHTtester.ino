int val = 0;
int led = 8;

#include "DHT.h"
#define DHTPIN 2     // what digital pin we're connected to
#define DHTTYPE DHT22   // DHT 22  (AM2302), AM2321
DHT dht(DHTPIN, DHTTYPE);








void setup() {
  Serial.begin(9600);
  pinMode(led, OUTPUT);
  dht.begin();
}

void loop() {
  // Wait a few seconds between measurements.
  delay(1000);
 float Tthermis = 0;
 float tensionthermis = 0;
 float lecturethermis =0;
 float T=0;
 float Rthermis = 10000;


  // Reading temperature or humidity takes about 250 milliseconds!
  // Sensor readings may also be up to 2 seconds 'old' (its a very slow sensor)
  float h = dht.readHumidity();
  // Read temperature as Celsius (the default)
  float t = dht.readTemperature();
  // Read temperature as Fahrenheit (isFahrenheit = true)
  float f = dht.readTemperature(true);

  // Compute heat index in Fahrenheit (the default)
  float hif = dht.computeHeatIndex(f, h);
  // Compute heat index in Celsius (isFahreheit = false)
  float hic = dht.computeHeatIndex(t, h, false);

//lecture de la tension
  lecturethermis = analogRead(A0);
  
  tensionthermis = 5.00- ((lecturethermis * 5.00)/1023.00);
  
  //Calcul resistance

  Tthermis = (1/(1.258935919*pow(10, -3) + 2.144918397*pow(10, -4)*log(Rthermis) + 1.513438730*pow(10, -7)*pow(log(Rthermis), 3)))-273,15;
  Serial.println(h);
  Serial.println(t);
  Serial.println(Tthermis);
}


 
void serialEvent() // To check if there is any data on the Serial line
{
    while (Serial.available())
    {
      val = Serial.parseInt();
      if(val == 1)   //Switch on the LED, if the received value is 1.
      {
        digitalWrite(led, HIGH);
      }
      else if(val == 0) //Switch off the LED, if the received value is 1.
      {
        digitalWrite(led, LOW);
      }
    }
     
    //Serial.println("Succesfully received.");
 }
