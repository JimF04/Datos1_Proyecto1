import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;
import org.json.JSONException;
import org.json.JSONObject;

public class Servidor {
    private JFrame ventana;
    private JTextArea areadetexto;
    private ServerSocket serverSocket;
    private int clientIDCounter = 0;

    private List<ClientHandler> activeClients = new LinkedList<>(); // Lista enlazada para manejar a los ClientHandler conectados

    public Servidor() {
        ventana = new JFrame("Servidor");

        areadetexto = new JTextArea();
        ventana.add(areadetexto);

        ventana.setSize(500, 500);
        ventana.setVisible(true);

        try {
            serverSocket = new ServerSocket(9991);

            while (true) {
                Socket socket = serverSocket.accept();
                int clientID = getNextClientID();
                ClientHandler clientHandler = new ClientHandler(socket, this, clientID);
                activeClients.add(clientHandler); // Añade el nuevo ClientHandler a la lista
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized int getNextClientID() { // Método sincronizado para obtener el siguiente ID de cliente
        return clientIDCounter++;
    }

    public void appendMessage(String msg) {
        areadetexto.append(msg + "\n");
    }
    public void sendToAll(String msg) {
        for (ClientHandler client : activeClients) {
            client.sendMessage(msg);
        }
    }

    public void removeClientHandler(ClientHandler clientHandler) {
        activeClients.remove(clientHandler); // Elimina el ClientHandler de la lista
    }

    public static void main(String[] args) {
        new Servidor();
    }
}

class ClientHandler extends Thread {
    private Socket socket;
    private Servidor servidor;
    private DataInputStream in;
    private int clientID; // Nuevo atributo para almacenar el ID del cliente

    public ClientHandler(Socket socket, Servidor servidor, int clientID) {
        this.socket = socket;
        this.servidor = servidor;
        this.clientID = clientID; // Establecer el ID del cliente
        try {
            in = new DataInputStream(socket.getInputStream());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void sendMessage(String msg) {
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void run() {
        try {
            while (true) {
                String mensaje = in.readUTF();
                JSONObject jsonObj = new JSONObject(mensaje);
                if (jsonObj.has("completedSquare") && jsonObj.getBoolean("completedSquare")) { 
                    // Si el mensaje indica que se ha completado un cuadrado, solo lo enviamos a ese cliente específico
                    sendToClient(jsonObj.toString());
                } else {
                    servidor.appendMessage(mensaje);
                    servidor.sendToAll(mensaje);
                }
            }
        } catch (IOException | JSONException e) {
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

    private void sendToClient(String message) {
        // Envía un mensaje solo a este cliente en particular
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getClientID() {
        return clientID;
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
}
