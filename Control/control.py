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
                pyautogui.click()
            elif data == 'C':
                pyautogui.press('C')
            elif data == 'D':
                pyautogui.press('D')
            elif data == 'E':
                pyautogui.press('E')
            elif data == 'F':
                pyautogui.press('F')
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

    serComm.close()

serial_thread = threading.Thread(target=serial_thread)
serial_thread.daemon = True
serial_thread.start()
            