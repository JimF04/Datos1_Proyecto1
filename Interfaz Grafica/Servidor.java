import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.io.DataOutputStream;
import java.util.List;

public class Servidor {
    private JTextArea areadetexto;

    public Servidor() {
        JFrame canal = new JFrame();
        canal.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Cambiamos la operación de cierre por defecto
        canal.setSize(600, 600);
        canal.setLayout(null);

        JPanel laminaserver = new JPanel();
        laminaserver.setLayout(null);
        laminaserver.setSize(600, 600);
        laminaserver.setBackground(Color.white);
        laminaserver.setVisible(true);

        canal.add(laminaserver);

        areadetexto = new JTextArea(null, null, 20, 20);
        areadetexto.setBounds(100, 100, 400, 400);
        areadetexto.setEditable(false);
        laminaserver.add(areadetexto);

        // Agregar confirmación al cerrar la ventana
        canal.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "¿Seguro que deseas salir?", "Salir", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        canal.setVisible(true);
    }

    public void appendMessage(String message) {
        areadetexto.append(message + "\n");
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            Servidor servidor = new Servidor();
            new Thread(() -> {
                try {
                    ServerSocket socketserv = new ServerSocket(9991);
                    while (true) {
                        Socket socket = socketserv.accept();
                        ClientHandler handler = new ClientHandler(socket, servidor);
                        servidor.addClientHandler(handler);
                        handler.start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        });
    }
    
    private List<ClientHandler> clientHandlers = new ArrayList<>();

    public synchronized void addClientHandler(ClientHandler handler) {
        clientHandlers.add(handler);
    }

    public synchronized void removeClientHandler(ClientHandler handler) {
        clientHandlers.remove(handler);
    }

    public synchronized void sendToAll(String message) {
        for(ClientHandler handler : clientHandlers) {
            handler.sendMessage(message);
        }
    }


}

class ClientHandler extends Thread {
    private Socket socket;
    private Servidor servidor;
    private DataOutputStream out;
    
    public ClientHandler(Socket socket, Servidor servidor) {
        this.socket = socket;
        this.servidor = servidor;
        try {
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            String coordenadas;
            while (true) {
                coordenadas = in.readUTF();  // Recibes el mensaje del cliente
                servidor.appendMessage(coordenadas);  // Agrega el mensaje a tu área de texto en el servidor
                servidor.sendToAll(coordenadas);     // Envía a todos los clientes
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            servidor.removeClientHandler(this);
        }
    }
}