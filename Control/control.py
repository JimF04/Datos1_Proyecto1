import serial
import pyautogui
import threading
import sys

def serial_thread():
    try:
        #Configurar la comunicación serial con arduino
        serComm = serial.Serial('COM7', 9600)
        while True:
            try:
                #Leer datos del puerto serial de Arduino
                data = serComm.readline().decode().strip()

                #Realizar las acciones según los datos recibidos
                if data == 'B':
                    pyautogui.click()
                elif data == 'I':
                    pyautogui.move(0, -15)  # Arriba
                elif data == 'K':
                    pyautogui.move(0, 15)  # Abajo
                elif data == 'J':
                    pyautogui.move(-15, 0)  # Izquierda
                elif data == 'L':
                    pyautogui.move(15, 0)  # Derecha

            except KeyboardInterrupt:
                break
    except serial.SerialException:
        print("¡Error: El control se desconectó!")
    finally:
        serComm.close()
        sys.exit()

serial_thread = threading.Thread(target=serial_thread)
serial_thread.daemon = True
serial_thread.start()
            