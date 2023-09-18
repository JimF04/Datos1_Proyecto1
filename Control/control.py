import serial
import pyautogui
import threading

def serial_thread():
#Configurar la comunicación serial con arduino
    serComm = serial.Serial('/dev/cu.usbmodem11201', 9600)
    while True:
        try:
            #Leer datos del puerto serial de Arduino
            data = serComm.readline().decode().strip()

            #Realizar las acciones según los datos recibidos
            if data == 'A':
                pyautogui.press('A')
            elif data == 'B':
                pyautogui.press('B')
            elif data == 'C':
                pyautogui.press('C')
            elif data == 'D':
                pyautogui.press('D')
            elif data == 'E':
                pyautogui.press('E')
            elif data == 'F':
                pyautogui.press('F')
            elif data == 'I':
                pyautogui.press('I')  # Arriba
            elif data == 'K':
                pyautogui.press('K')  # Abajo
            elif data == 'J':
                pyautogui.press('J')  # Izquierda
            elif data == 'L':
                pyautogui.press('L')  # Derecha

        except KeyboardInterrupt:
            break

    serComm.close()

serial_thread = threading.Thread(target=serial_thread)
serial_thread.daemon = True
serial_thread.start()
            