import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

public class HelloWorldSerialApp {
    public static void main(String[] args) {
        SerialCommunication serialCommunication = new SerialCommunication();

        // Agrega un oyente de datos para escuchar los datos entrantes
        serialCommunication.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                if (event.getEventType() == SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                    byte[] newData = new byte[serialCommunication.getSerialPort().bytesAvailable()];
                    int numRead = serialCommunication.getSerialPort().readBytes(newData, newData.length);

                    // Convierte los datos recibidos a una cadena
                    String receivedData = new String(newData);

                    // Mensaje de depuración: Imprimir los datos recibidos
                    System.out.println("Datos recibidos: " + receivedData);

                    // Si se recibe 'A', imprime "Hello World"
                    if (receivedData.trim().equals("A")) {
                        System.out.println("Se recibió 'A'. Imprimiendo 'Hello World'");
                    }
                }
            }
        });

        // Mantén la aplicación en funcionamiento
        try {
            Thread.sleep(100000); // Puedes ajustar este tiempo según tus necesidades
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Cierra el puerto serial al finalizar la aplicación
        serialCommunication.closeSerialPort();
    }
}
