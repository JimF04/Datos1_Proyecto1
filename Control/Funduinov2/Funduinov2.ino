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
    
    // Imprimir en Serial Monitor los valores leidos
    Serial.print("Joystick X: ");
    Serial.print(joystickXValue);
    Serial.print("  Y: ");
    Serial.print(joystickYValue);
    Serial.print("  Button: ");
    Serial.println(joystickButton);

    // Imprimir si se han oprimido los botones
    if (buttonA) {
        Serial.println("Button_A");
    }
    if (buttonB) {
        Serial.println("Button_B");
    }
    if (buttonC) {
        Serial.println("Button_C");
    }
    if (buttonD) {
        Serial.println("Button_D");
    }
    if (buttonE) {
        Serial.println("Button_E");
    }
    if (buttonF) {
        Serial.println("Button_F");
    }

    delay(100);
}