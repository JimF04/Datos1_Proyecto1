import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Servidor {
    private JTextArea areadetexto;

    public Servidor(){

        JFrame canal = new JFrame();
        canal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canal.setSize(600,600);
        canal.setLayout(null);



        JPanel laminaserver = new JPanel();
        laminaserver.setLayout(null);
        laminaserver.setSize(600,600);
        laminaserver.setBackground(Color.white);
        laminaserver.setVisible(true);


        canal.add(laminaserver);


        areadetexto = new JTextArea(null,null, 10, 10);
        areadetexto.setBounds(100, 100, 200, 200);
        areadetexto.setEditable(false);
        laminaserver.add(areadetexto);












        canal.setVisible(true);


    

        }
        public void appendMessage(String message){
            areadetexto.append(message + "\n");
        }

        

    public static void main(String args[]){

        SwingUtilities.invokeLater(() -> {
        Servidor servidor = new Servidor();
        new Thread(() ->{

            try {

            ServerSocket socketserv = new ServerSocket(9991);

            while(true){

                Socket socket = socketserv.accept();

                new ClientHandler(socket,servidor).start();


            }


        } catch (IOException e){
            e.printStackTrace();
        }

        }).start();
    });

    }
}

class ClientHandler extends Thread{
    private Socket socket;
    private Servidor servidor;

    public ClientHandler(Socket socket, Servidor servidor){
        this.socket = socket;
        this.servidor = servidor;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String coordenadas;
            while ((coordenadas = in.readLine()) != null) {
                servidor.appendMessage("Recibido" + coordenadas);
            }
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}