// Code Arduino
// Projet Pimp My Fridge - Exia.Cesi 2015

// L'arduino va envoyer sur la liaison série des trames de données sous cette forme :
// D:<Taux humidité interne>;<Temperature interne>;<Temperature externe>;<CRC>\n
// Chaque ligne lue contient une trame.
// Le CRC est simplement la somme des valeurs.
// Toutes les valeurs peuvent renvoyer "nan" (Not a Number) en cas d'erreur.

// Il est possible de contrôle l'allumage et l'extinction du réfrigérateur en envoyant
// des commandes à l'arduino via la liaison série.
// Envoyer 1 va allumer le réfrigérateur.
// Envoyer 2 va eteindre le réfrigérateur.
// L'arduino réponds toujours pour valider la commande. Il renvoie :
// R:1   quand il allume le réfrigérateur.
// R:2   quand il coupe le réfrigérateur.
// R:0   en cas d'erreur

// CONFIGURATION
#define PIN_DHT 2       // Pin de commande du capteur DTH interne
#define DHTTYPE DHT22   // Type de DTH : DHT22 (AM2302), AM2321
int PIN_FRIGO = 8;      // Pin de contrôle de l'alimentation du frigo
int R_THERMIS = 10000;  // Résistance en ohm de la thermistance externe
// FIN DE LA CONFIGURATION

// Chargement de la librairie du capteur DHT
#include "DHT.h"
DHT dht(PIN_DHT, DHTTYPE);

// Variables de travail
int readVal = 0;
bool writting = false;

// Désigne la petite LED contrôlable sur la carte Arduino
int PIN_ONBOARD_LED = 13;

/**
 * Setup de l'application.
 */
void setup() {
  // On ouvre la liaison série
  Serial.begin(9600);
  // On passe le pin du frigo en écriture
  pinMode(PIN_FRIGO, OUTPUT);
  // On passe la led de l'arduino en écriture
  pinMode(PIN_ONBOARD_LED, OUTPUT);
  // On active la librairie du sensor DHT
  dht.begin();
}

/**
 * Contrôle de cohérence.
 * Basé sur l'algo CRC8 de Dallas et Maxim
 */
byte CRC8(const byte *data, int len) {
  byte crc = 0x00;
  while (len--) {
    byte extract = *data++;
    for (byte tempI = 8; tempI; tempI--) {
      byte sum = (crc ^ extract) & 0x01;
      crc >>= 1;
      if (sum) {
        crc ^= 0x8C;
      }
      extract >>= 1;
    }
  }
  return crc;
}

/**
 * Boucle de travail.
 */
void loop() {
  
  // Délais entre chaque mesure
  delay(500);

  // CAPTEUR DHT INERNE AU FRIGO
  // Reading temperature or humidity takes about 250 milliseconds!
  // Sensor readings may also be up to 2 seconds 'old' (its a very slow sensor)
  float Hin = dht.readHumidity();
  // Read temperature as Celsius (the default)
  float Tin = dht.readTemperature();
  // Read temperature as Fahrenheit (isFahrenheit = true)
  //float f = dht.readTemperature(true);
  // Compute heat index in Fahrenheit (the default)
  //float hif = dht.computeHeatIndex(f, h);
  // Compute heat index in Celsius (isFahreheit = false)
  //float hic = dht.computeHeatIndex(t, h, false);

  // CAPTEUR PT100 TEMPERATURE EXTERNE AU FRIGO
  // Lecture de la tension
  float lecturethermis = analogRead(A0);
  float tensionthermis = lecturethermis * 5.0 / 1023.0;
  float resistance = (5.0 - tensionthermis) * R_THERMIS / tensionthermis;
  float Tout = (1/(1.258935919*pow(10, -3)
    + 2.144918397*pow(10, -4)*log(resistance)
    + 1.513438730*pow(10, -7)*pow(log(resistance), 3)))
    - 273.15;

  // On envoie une trame de données sur la liaison série
  while (writting) {} // synchro
  writting = true;
  Serial.print("D:");
  Serial.print(Hin);
  Serial.print(';');
  Serial.print(Tin);
  Serial.print(';');
  Serial.print(Tout);
  Serial.print(';');
  Serial.println(Hin + Tin + Tout); // CRC


  //int H1 = (int)(Hin * 100);
  //int T1 = (int)(Hin * 100);
  //int T2 = (int)(Hin * 100);
  

  //String var = String(Hin) + ';' + String(Tin) + ';' + String(Tout);
  //unsigned char test[sizeof(var)];
  //var.getBytes(test, sizeof(var));
  
  //char* val = {};
  //String.getBytes(var, val, sizeof(var));
  
  //Serial.print(var);
  //Serial.println('=');
  //Serial.println(CRC8(test, sizeof(var)));
  
  writting = false;
}

/**
 * Quand une données est reçue sur la liaison série.
 */
void serialEvent()
{
  // Des données sont disponibles
  while (Serial.available())
  {
    // Lecture de la commande, sous forme d'entier
    readVal = Serial.parseInt();
    // Allumage du réfrigérateur
    if (readVal == 1)
    {
      digitalWrite(PIN_ONBOARD_LED, HIGH); // On allume la LED de l'arduino pour indiquer l'activité
      digitalWrite(PIN_FRIGO, HIGH);
    }
    // Extinction du réfrigérateur
    else if (readVal == 2)
    {
      digitalWrite(PIN_ONBOARD_LED, LOW); // On eteint la LED de l'arduino pour indiquer la coupure
      digitalWrite(PIN_FRIGO, LOW);
    }
    else {
      readVal = 0;
    }
    // Réponse (boucle)
    while (writting) {} // synchro
    writting = true;
    Serial.print("R:");
    Serial.println(readVal);
    writting = false;
  }
}
