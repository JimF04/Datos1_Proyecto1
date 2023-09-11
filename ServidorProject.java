import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ServidorProject extends JFrame {
    private ServerSocket serverSocket;
    private boolean isRunning = false;
    private int numClientesConectados = 0; // Variable para llevar un registro de la cantidad de clientes
    private JButton startButton;
    private JButton stopButton;
    private JTextArea logTextArea; // Agregamos un JTextArea para mostrar información del servidor

    public ServidorProject() {
        super("Servidor");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(600, 400);
        setResizable(false);
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "¿Seguro que deseas salir?", "Salir", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        startButton = new JButton("Iniciar Servidor");
        stopButton = new JButton("Detener Servidor");
        stopButton.setEnabled(false);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startServer();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopServer();
            }
        });

        // Configuramos el JTextArea
        logTextArea = new JTextArea();
        logTextArea.setEditable(false); // No permitimos editar el texto
        JScrollPane scrollPane = new JScrollPane(logTextArea);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(startButton);
        panel.add(stopButton);

        // Agregamos el JTextArea al panel principal
        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER); // Agregamos el JTextArea con scroll al centro

        setVisible(true);
    }

    private void startServer() {
        if (!isRunning) {
            try {
                int puerto = 8080; // Define el puerto aquí o en cualquier otro lugar
                serverSocket = new ServerSocket(puerto);
                isRunning = true;
                startButton.setEnabled(false);
                stopButton.setEnabled(true);

                // Actualiza el JTextArea con información del servidor, incluida la cantidad de clientes
                logTextArea.append("Servidor iniciado en el puerto " + puerto + "\n");
                logTextArea.append("Clientes Conectados: " + numClientesConectados + "\n");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al iniciar el servidor.");
            }
        }
    }    

    private void stopServer() {
        if (isRunning) {
            try {
                serverSocket.close();
                isRunning = false;
                startButton.setEnabled(true);
                stopButton.setEnabled(false);

                // Actualiza el JTextArea con información del servidor, incluida la cantidad de clientes
                logTextArea.append("Servidor detenido\n");
                logTextArea.append("Total de clientes conectados: " + numClientesConectados + "\n");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al detener el servidor.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ServidorProject();
            }
        });
    }
}
