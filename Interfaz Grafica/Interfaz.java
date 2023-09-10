import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Interfaz {

    private BufferedImage backgroundImage;

    public Interfaz(){
        
        try {
            // Cargar la imagen de fondo
            backgroundImage = ImageIO.read(getClass().getResource("connectbackground.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(1200, 675);
        frame.setTitle("Connect the Dots");
        frame.setLayout(null);

        JPanel milamina = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Dibuja la imagen en el JPanel
                g.drawImage(backgroundImage, 0, 0, this);
            }
        };
        milamina.setLayout(null);
        milamina.setSize(1200, 675);
        milamina.setBackground(Color.darkGray);
        frame.add(milamina);

        JLabel label = new JLabel("WELCOME TO THE GAME");
        label.setBounds(450,10,350,30);
        label.setFont(new Font("MV Boli",Font.PLAIN,25));
        label.setForeground(Color.white);
        milamina.add(label); 

        JButton playbtn = new JButton();
        playbtn.setBounds(525, 100, 150 , 50);
        playbtn.setText("PLAY");
        playbtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new Ventana2();
                frame.dispose();
            }
        });
        milamina.add(playbtn);

        JButton infobtn = new JButton();
        infobtn.setBounds(525, 200, 150, 50);
        infobtn.setText("INFO");
        infobtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                new Ventana3();
                frame.dispose();
            }
        });
        milamina.add(infobtn);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Interfaz());
    }
}

class Ventana2 extends JFrame {
    public Ventana2(){
        setTitle("Connect the dots");
        setSize(1200,675);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);

        JPanel milaminagame = new JPanel();
        milaminagame.setLayout(null);
        milaminagame.setSize(1200,675);
        milaminagame.setBackground(Color.black);
        add(milaminagame);

        JLabel ingresanick = new JLabel();
        ingresanick.setText("INGRESA TU USUARIO");
        ingresanick.setBounds(450, 10, 300, 30);
        ingresanick.setFont(new Font("MV Boli",Font.PLAIN,20));
        ingresanick.setForeground(Color.white);
        milaminagame.add(ingresanick);

        JTextField areadenick = new JTextField();
        areadenick.setBounds(450, 60, 220, 30);
        milaminagame.add(areadenick);

        JButton returnbtn = new JButton();
        returnbtn.setBounds(30, 30, 100 , 30);
        returnbtn.setText("RETURN");
        returnbtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new Interfaz();
                dispose();
            }
        });
        milaminagame.add(returnbtn);
        setVisible(true);
    }
}

class Ventana3 extends JFrame {
    public Ventana3(){
        setLayout(null);
        setTitle("Informacion");
        setSize(1200, 675);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel milaminainfo = new JPanel();
        milaminainfo.setLayout(null);
        milaminainfo.setSize(1200,675);
        milaminainfo.setBackground(Color.black);

        JLabel infor = new JLabel();
        infor.setText("<html>Este proyecto fue elaborado por:<br>Jimmy Feng Feng<br>Gabriel Fernandez Vargas<br>y Emanuel Rojas Fernandez</html>");
        infor.setBounds(400,10,600,200);
        infor.setFont(new Font("MV Boli",Font.PLAIN,30));
        infor.setForeground(Color.white);
        milaminainfo.add(infor);

        JButton returnbtni = new JButton();
        returnbtni.setBounds(20, 550, 100 , 30);
        returnbtni.setText("RETURN");
        returnbtni.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new Interfaz();
                dispose();
            }
        });
        milaminainfo.add(returnbtni);

        add(milaminainfo);
        setVisible(true);
    }
}   
//Hola