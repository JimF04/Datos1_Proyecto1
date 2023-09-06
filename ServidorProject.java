import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ServidorProject extends JFrame {
    private ServerSocket serverSocket;
    private boolean isRunning = false;
    private JButton startButton;
    private JButton stopButton;

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

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(startButton);
        panel.add(stopButton);

        add(panel);

        setVisible(true);
    }

    private void startServer() {
        if (!isRunning) {
            try {
                serverSocket = new ServerSocket(8080);
                isRunning = true;
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
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
