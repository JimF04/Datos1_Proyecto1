import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;

public class SerialCommunication {
    private SerialPort serialPort;

    public SerialCommunication() {
        // Configura la comunicación serial
        serialPort = SerialPort.getCommPort("/dev/cu.usbmodem11201"); // Reemplaza "COMX" con el puerto COM correcto
        serialPort.setBaudRate(9600);
        serialPort.openPort();
    }

    public void closeSerialPort() {
        // Cierra el puerto serial al finalizar la aplicación
        serialPort.closePort();
    }

    public void addDataListener(SerialPortDataListener dataListener) {
        // Agrega el oyente para escuchar datos entrantes
        serialPort.addDataListener(dataListener);
    }
    
    public SerialPort getSerialPort() {
        return serialPort;
    }
}
