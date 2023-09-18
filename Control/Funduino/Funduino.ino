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
    // Inicialización de comunicación serial
    Serial.begin(9600);
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
    if (buttonA){
      Serial.write('A');
      Serial.write('\n');
      delay(200);
    }
    if (buttonB){
      Serial.write('B');
      Serial.write('\n');
      delay(200);
    }
    if (buttonC){
      Serial.write('C');
      Serial.write('\n');
      delay(200);
    }
    if (buttonD){
      Serial.write('D');
      Serial.write('\n');
      delay(200);
    }
    if (buttonE){
      Serial.write('E');
      Serial.write('\n');
      delay(200);
    }
    if (buttonF){
      Serial.write('F');
      Serial.write('\n');
      delay(200);
    }

    // Acciones del Joystick
    if (joystickYValue < 400){
      Serial.write('K');
      Serial.write('\n');
      delay(200);
    } 
    else if (joystickYValue > 600){
      Serial.write('I');
      Serial.write('\n');
      delay(200);
    }
    if (joystickXValue < 400){
      Serial.write('J');
      Serial.write('\n');
      delay(200);
    }
    else if (joystickXValue > 600){
      Serial.write('L');
      Serial.write('\n');
      delay(200);
    }
}