#include <Arduino.h>

// Pines de los botones
const int buttonAPin = 2;
const int buttonBPin = 3;
const int buttonCPin = 4;
const int buttonDPin = 5;
const int buttonEPin = 6;
const int buttonFPin = 7;

// Pines analógicos del jotstick
const int joystickXPin = A0;
const int joystickYPin = A1;
const int joystickButtonPin = 8;

void setup() {
    // Configurar pines de los botones
    pinMode(buttonAPin, INPUT_PULLUP);
    pinMode(buttonBPin, INPUT_PULLUP);
    pinMode(buttonCPin, INPUT_PULLUP);
    pinMode(buttonDPin, INPUT_PULLUP);
    pinMode(buttonEPin, INPUT_PULLUP);
    pinMode(buttonFPin, INPUT_PULLUP);

    // Configurar pines analógicos del joystick
    pinMode(joystickXPin, INPUT);
    pinMode(joystickYPin, INPUT);
    pinMode(joystickButtonPin, INPUT_PULLUP);

    // Inicialización de comunicación serial
    Serial.begin(9600);
}

void loop() {
    // Leer el estado de los botones
    bool buttonA = !digitalRead(buttonAPin);
    bool buttonB = !digitalRead(buttonBPin);
    bool buttonC = !digitalRead(buttonCPin);
    bool buttonD = !digitalRead(buttonDPin);
    bool buttonE = !digitalRead(buttonEPin);
    bool buttonF = !digitalRead(buttonFPin);

    // Leer valores analógicos del joystick
    int joystickXValue = analogRead(joystickXPin);
    int joystickYValue = analogRead(joystickYPin);
    bool joystickButton = !digitalRead(joystickButtonPin);
    
    // Acciones de los botones
    if (digitalRead(buttonAPin) == LOW){
      Serial.write('A');
      delay(500);
    }
    if (digitalRead(buttonBPin) == LOW){
      Serial.write('B');
      delay(500);
    }
    if (digitalRead(buttonCPin) == LOW){
      Serial.write('C');
      delay(500);
    }
    if (digitalRead(buttonDPin) == LOW){
      Serial.write('D');
      delay(500);
    }
    if (digitalRead(buttonEPin) == LOW){
      Serial.write('E');
      delay(500);
    }
    if (digitalRead(buttonFPin) == LOW){
      Serial.write('F');
      delay(500);
    }

}