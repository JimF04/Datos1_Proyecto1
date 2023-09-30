import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.io.DataOutputStream;
import java.util.List;

public class Servidor {
    private JTextArea areadetexto;
    private Cola<ClientHandler> colaDeTurnos = new Cola<>();
    private ClientHandler clienteActual = null;
    private List<ClientHandler> clientHandlers = new ArrayList<>();

    public List<ClientHandler> getClientHandlers() {
        return clientHandlers;
    }

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
        SwingUtilities.invokeLater(() -> {
            areadetexto.append(message + "\n");
        });
    }
    public synchronized void serverTurn() {
        SwingUtilities.invokeLater(() -> {
            areadetexto.append("[Server] Es mi turno. Puedes escribir ahora.\n");
           
        });
    }
    
    public synchronized void nextTurn() {
        if(clienteActual == null) {
            serverTurn(); // Si no hay cliente actual, es el turno del servidor
        } else {
            clienteActual = clientHandlers.remove(0);
            clienteActual.asignarTurno();
            clientHandlers.add(clienteActual);
        }
    }
    

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            Servidor servidor = new Servidor();
            servidor.serverTurn();
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
    

    public synchronized void addClientHandler(ClientHandler handler) {
        clientHandlers.add(handler);
        colaDeTurnos.enqueue(handler);
        if (clienteActual == null) { // Si no hay cliente actual, asignar el turno al nuevo cliente.
            clienteActual = colaDeTurnos.peek();
            clienteActual.asignarTurno();
        }
    }
    public synchronized void removeClientHandler(ClientHandler handler) {
        clientHandlers.remove(handler);
    }
    public synchronized void sendToAll(String message) {
        for(ClientHandler handler : clientHandlers) {
            handler.sendMessage(message);
        }
    }
    public synchronized void clientFinished(ClientHandler handler) {
        if (clienteActual == handler) {
            colaDeTurnos.dequeue(); // Quita al cliente actual de la cola
            clienteActual = null;
            
            // Asignar el turno al siguiente cliente en la cola si lo hay
            if (!colaDeTurnos.isEmpty()) {
                clienteActual = colaDeTurnos.peek();
                clienteActual.asignarTurno();
            }
        }
    }
    public synchronized void pedirTurno(ClientHandler cliente) {
        colaDeTurnos.enqueue(cliente);
        // Si solo hay un cliente en la cola, le damos el turno
        if (colaDeTurnos.size() == 1) {
            cliente.asignarTurno();
        }
    }
    public synchronized void sendToAllExcept(String message, ClientHandler sender) {
        for(ClientHandler handler : clientHandlers) {
            if (handler != sender) {
                handler.sendMessage(message);
            }
        }
    }

    public synchronized void finalizarTurno(ClientHandler cliente) {
    if (colaDeTurnos.peek() == cliente) {
        colaDeTurnos.dequeue();
        // Le damos el turno al siguiente cliente en la cola si lo hay
        ClientHandler siguiente = colaDeTurnos.peek();
        if (siguiente != null) {
            siguiente.asignarTurno();
        }
    }
}
    

    

    
    


}

class ClientHandler extends Thread {
    private Socket socket;
    private Servidor servidor;
    private DataOutputStream out;
    private boolean tieneTurno = false;
    
    public ClientHandler(Socket socket, Servidor servidor) {
        this.socket = socket;
        this.servidor = servidor;
        try {
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String msg) {
    try {
        out.writeUTF(msg);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
     public void asignarTurno() {
        sendMessage("TU TURNO");
    }

    public void run() {
    try {
        DataInputStream in = new DataInputStream(socket.getInputStream());

        while (true) {
            String mensaje = in.readUTF();
            servidor.appendMessage(mensaje);

            // Reenviar el mensaje a todos los clientes, incluido el remitente
            for (ClientHandler ch : servidor.getClientHandlers()) {
                ch.sendMessage(mensaje);
            }

            // Luego de enviar la línea, cambiamos de turno
            servidor.nextTurn();
        }

    } catch(IOException e) {
        e.printStackTrace();
    } finally {
        try {
            if (out != null) out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        servidor.removeClientHandler(this);
    }
}


    
}


class Cola<T> {
    private Nodo<T> head;
    private Nodo<T> tail; // Necesitamos un nodo final para encolar de manera eficiente

    public Cola() {
        this.head = null;
        this.tail = null;
    }

    // Encolar un nuevo elemento al final
    public void enqueue(T data) {
        Nodo<T> newNode = new Nodo<>(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
            return;
        }

        tail.next = newNode;
        tail = newNode;
    }

    // Desencolar el elemento del inicio
    public T dequeue() {
        if (head == null) {
            return null; // La cola está vacía
        }

        T data = head.data;
        head = head.next;
        if (head == null) {
            tail = null; // Si desencolamos el último elemento, también restablecemos la cola
        }
        return data;
    }

    // Ver el elemento del frente sin desencolar
    public T peek() {
        if (head == null) {
            return null; // La cola está vacía
        }
        return head.data;
    }

    // Verificar si la cola está vacía
    public boolean isEmpty() {
        return head == null;
    }

    // Obtener todos los datos como una lista para facilitar el manejo
    public List<T> getAll() {
        List<T> list = new ArrayList<>();
        Nodo<T> current = head;
        while (current != null) {
            list.add(current.data);
            current = current.next;
        }
        return list;
    }
    private static class Nodo<T> {
    T data;
    Nodo<T> next;

    public Nodo(T data) {
        this.data = data;
        this.next = null;
    }
}
public int size() {
    int count = 0;
    Nodo<T> current = head;
    while (current != null) {
        count++;
        current = current.next;
    }
    return count;
}

}