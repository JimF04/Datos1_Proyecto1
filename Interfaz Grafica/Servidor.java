import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.io.DataOutputStream;
import java.util.List;

/**
 * La clase Servidor representa el servidor de un chat simple. Administra la comunicación con múltiples clientes
 * y coordina los turnos de los clientes para enviar y recibir mensajes.
 */
public class Servidor {
    private JTextArea areadetexto;
    private Cola<ClientHandler> colaDeTurnos = new Cola<>();
    private ClientHandler clienteActual = null;
    private LinkedList<ClientHandler> clientHandlers = new LinkedList<>();

/**
 * Obtiene la lista de manejadores de clientes (ClientHandler) conectados al servidor.
 *
 * @return La lista de ClientHandler conectados al servidor.
 */    
    public List<ClientHandler> getClientHandlers() {
        return clientHandlers;
    }


/**
 * Constructor de la clase Servidor. Inicializa la interfaz gráfica del servidor.
 */    
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

/**
 * Agrega un mensaje al área de texto de la interfaz gráfica del servidor.
 *
 * @param message El mensaje que se va a agregar.
 */    
    public void appendMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            areadetexto.append(message + "\n");
        });
    }

/**
 * Indica que es el turno del servidor para enviar mensajes.
 */
    
    public synchronized void serverTurn() {
        SwingUtilities.invokeLater(() -> {
            areadetexto.append("[Server] Es mi turno. Puedes escribir ahora.\n");
           
        });
    }

/**
 * Pasa al siguiente turno de cliente.
 */    
    public synchronized void nextTurn() {
        if(clienteActual == null) {
            serverTurn(); // Si no hay cliente actual, es el turno del servidor
        } else {
            clienteActual = clientHandlers.remove(0);
            clienteActual.asignarTurno();
            clientHandlers.add(clienteActual);
        }
    }

/**
 * Método principal del servidor. Inicia el servidor y acepta conexiones entrantes de clientes.
 *
 * @param args Los argumentos de la línea de comandos (no se utilizan).
 */
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
    
/**
 * Agrega un manejador de cliente (ClientHandler) a la lista de clientes conectados.
 *
 * @param handler El manejador de cliente que se va a agregar.
 */
    public synchronized void addClientHandler(ClientHandler handler) {
        clientHandlers.add(handler);
        colaDeTurnos.enqueue(handler);
        if (clienteActual == null) { // Si no hay cliente actual, asignar el turno al nuevo cliente.
            clienteActual = colaDeTurnos.peek();
            clienteActual.asignarTurno();
        }
    }

/**
 * Elimina un manejador de cliente (ClientHandler) de la lista de clientes conectados.
 *
 * @param handler El manejador de cliente que se va a eliminar.
 */    
    public synchronized void removeClientHandler(ClientHandler handler) {
        clientHandlers.remove(handler);
    }

/**
 * Envía un mensaje a todos los clientes conectados.
 *
 * @param message El mensaje que se va a enviar.
 */    
    public synchronized void sendToAll(String message) {
        for(ClientHandler handler : clientHandlers) {
            handler.sendMessage(message);
        }
    }

/**
 * Indica que un cliente ha finalizado su turno y gestiona el siguiente turno si es necesario.
 *
 * @param handler El manejador de cliente que ha finalizado su turno.
 */    
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

/**
 * Pide el turno para un cliente específico y lo agrega a la cola de turnos.
 *
 * @param cliente El manejador de cliente que solicita el turno.
 */    
    public synchronized void pedirTurno(ClientHandler cliente) {
        colaDeTurnos.enqueue(cliente);
        // Si solo hay un cliente en la cola, le damos el turno
        if (colaDeTurnos.size() == 1) {
            cliente.asignarTurno();
        }
    }

/**
 * Envía un mensaje a todos los clientes conectados excepto al remitente especificado.
 *
 * @param message El mensaje que se va a enviar.
 * @param sender  El manejador de cliente que envía el mensaje.
 */    
    public synchronized void sendToAllExcept(String message, ClientHandler sender) {
        for(ClientHandler handler : clientHandlers) {
            if (handler != sender) {
                handler.sendMessage(message);
            }
        }
    }

/**
 * Finaliza el turno de un cliente específico.
 *
 * @param cliente El manejador de cliente cuyo turno se va a finalizar.
 */    
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


/**
 * La clase ClientHandler representa un manejador de cliente que gestiona la comunicación con un cliente
 * conectado al servidor de chat. Cada instancia de ClientHandler se ejecuta en un hilo separado para
 * atender a un cliente específico.
 */
class ClientHandler extends Thread {
    private Socket socket;
    private Servidor servidor;
    private DataOutputStream out;
    private boolean tieneTurno = false;
    
/**
 * Constructor de la clase ClientHandler.
 *
 * @param socket   El socket de comunicación con el cliente.
 * @param servidor La instancia del servidor al que está conectado el cliente.
 */    
    public ClientHandler(Socket socket, Servidor servidor) {
        this.socket = socket;
        this.servidor = servidor;
        try {
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

/**
 * Envía un mensaje al cliente actual.
 *
 * @param msg El mensaje que se va a enviar al cliente.
 */    
    public void sendMessage(String msg) {
    try {
        out.writeUTF(msg);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
/**
 * Asigna el turno al cliente actual.
 */
     public void asignarTurno() {
        sendMessage("TU TURNO");
    }

/**
 * Ejecuta el hilo del cliente para recibir y procesar mensajes.
 */    
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

/**
 * La clase Cola representa una cola genérica que se utiliza para almacenar elementos.
 * Los elementos se encolan (agregan) al final y se desencolan (eliminan) desde el frente.
 *
 * @param <T> El tipo de elementos que se almacenarán en la cola.
 */
class Cola<T> {
    private Nodo<T> head;
    private Nodo<T> tail; // Necesitamos un nodo final para encolar de manera eficiente

/**
 * Constructor de la clase Cola. Inicializa una cola vacía.
 */    
    public Cola() {
        this.head = null;
        this.tail = null;
    }

/**
 * Encola un nuevo elemento al final de la cola.
 *
 * @param data El elemento que se va a encolar.
 */
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

/**
 * Desencola el elemento del frente de la cola.
 *
 * @return El elemento desencolado o `null` si la cola está vacía.
 */
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

/**
 * Obtiene el elemento del frente de la cola sin desencolarlo.
 *
 * @return El elemento del frente de la cola o `null` si la cola está vacía.
 */
    public T peek() {
        if (head == null) {
            return null; // La cola está vacía
        }
        return head.data;
    }

/**
 * Verifica si la cola está vacía.
 *
 * @return `true` si la cola está vacía, `false` de lo contrario.
 */
    public boolean isEmpty() {
        return head == null;
    }
/**
 * Obtiene todos los elementos de la cola como una lista para facilitar el manejo.
 *
 * @return Una lista que contiene todos los elementos de la cola.
 */
    public List<T> getAll() {
        List<T> list = new ArrayList<>();
        Nodo<T> current = head;
        while (current != null) {
            list.add(current.data);
            current = current.next;
        }
        return list;
    }

/**
 * Clase interna que representa un nodo en la cola.
 *
 * @param <T> El tipo de datos almacenado en el nodo.
 */
    private static class Nodo<T> {
        T data;
        Nodo<T> next;

        public Nodo(T data) {
            this.data = data;
            this.next = null;
        }
    }
/**
 * Obtiene el tamaño actual de la cola.
 *
 * @return El número de elementos en la cola.
 */
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