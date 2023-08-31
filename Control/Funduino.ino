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
        Serial.println("Botón A oprimido");
    }
    if (buttonB) {
        Serial.println("Botón B oprimido");
    }
    if (buttonC) {
        Serial.println("Botón C oprimido");
    }
    if (buttonD) {
        Serial.println("Botón D oprimido");
    }
    if (buttonE) {
        Serial.println("Botón E oprimido");
    }
    if (buttonF) {
        Serial.println("Botón F oprimido");
    }

    // Acciónes de los botones
    if (buttonA) {
        // Acción para el botón A
    }
}