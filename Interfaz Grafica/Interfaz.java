import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;


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



        JButton gamebtn = new JButton();
        gamebtn.setBounds(470, 200, 150 , 30);
        gamebtn.setText("GAME");
        gamebtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new Ventanagame();
                dispose();
            }

        });
        

        milaminagame.add(gamebtn);

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

class Ventanagame extends JFrame{

    public Ventanagame(){
        setLayout(null);
        setSize(1000,675);
        setTitle("CONNECT THE DOTS");
        

        PanelDePuntos panelDePuntos = new PanelDePuntos(10, 10);
        panelDePuntos.setBounds(0, 0, 1200, 675);


        add(panelDePuntos);

        setVisible(true);

    }

}

class PanelDePuntos extends JPanel{
    private ListaEnlazada<Punto> puntosTotales = new ListaEnlazada<>();
    private List<Punto> puntosSeleccionados = new ArrayList<>();
    private List<Linea> lineasDibujadas = new ArrayList<>();

    public PanelDePuntos(int filas, int columnas){


        for (int i = 0; i<filas;i++){
            for (int j = 0; j<columnas; j++){
                Punto punto = new Punto(i*100,j*100,i,j);
                puntosTotales.add(punto);
            }
        }

        addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                int x= e.getX();
                int y= e.getY();
                Punto puntoseleccionado = getPuntoCercano(x,y);
                 if (puntoseleccionado !=null){
                    puntosSeleccionados.add(puntoseleccionado);
                        if (puntosSeleccionados.size()==2){
                            Punto p1 = puntosSeleccionados.get(0);
                            Punto p2 = puntosSeleccionados.get(1);

                            if ((p1.getX() == p2.getX() || p1.getY() == p2.getY()) && (calcularDistancia(p1, p2) == 100)==true ){
                                lineasDibujadas.add(new Linea(p1, p2));
                        repaint();
                            } else{
                                System.out.println("Solo se pueden hacer lineas verticales, horizontales y con un espacio de 100 entre punto");
                            }
                            puntosSeleccionados.clear();
                            
                        

                            
                        }
                    
                    
                    
                }

                
            }

        });
    }

    private double calcularDistancia(Punto p1, Punto p2){
        return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
    }

    private Punto getPuntoCercano(int x,int y){
        Punto puntoCercano = null;
        double distanciaMinima = Double.MAX_VALUE;
        for(Punto punto : puntosTotales.getAll()){
                double distancia = Math.sqrt(Math.pow(punto.getX() - x, 2) + Math.pow(punto.getY() - y,2));
                if (distancia<20 && distancia<distanciaMinima){
                        distanciaMinima=distancia;
                        puntoCercano = punto;
                    
                    }
                
            }
            return puntoCercano;
        }
        




    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        for (Punto punto : puntosTotales.getAll()) {
        g.drawOval(punto.getX(), punto.getY(), 5, 5);
    }
         synchronized(puntosSeleccionados) {
        if (puntosSeleccionados.size() == 2) {
            puntosSeleccionados.clear();
        }
        }
        for(Linea linea : lineasDibujadas) {
        Punto p1 = linea.getPunto1();
        Punto p2 = linea.getPunto2();
        g.drawLine(p1.getX() + 2, p1.getY() +2 , p2.getX() + 2, p2.getY() + 2);
    }
    }


}

 class Punto {
    private int x;
    private int y;

    public Punto(int x, int y,int fila, int columna) {
        this.x = x;
        this.y = y;
    }

    // Getters y setters
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}

class Linea {
    private Punto punto1;
    private Punto punto2;

    public Linea(Punto punto1, Punto punto2) {
        this.punto1 = punto1;
        this.punto2 = punto2;
    }

    public Punto getPunto1() {
        return punto1;
    }

    public Punto getPunto2() {
        return punto2;
    }
}


class Nodo<T> {
    T data;
    Nodo<T> next;

    public Nodo(T data) {
        this.data = data;
        this.next = null;
    }
}

class ListaEnlazada<T> {
    private Nodo<T> head;

    public ListaEnlazada() {
        this.head = null;
    }

    // Agregar un nuevo nodo al final
    public void add(T data) {
        Nodo<T> newNode = new Nodo<>(data);
        if (head == null) {
            head = newNode;
            return;
        }

        Nodo<T> current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = newNode;
    }

    // Eliminar un nodo (por el dato)
    public void remove(T data) {
        if (head == null) return;

        if (head.data.equals(data)) {
            head = head.next;
            return;
        }

        Nodo<T> current = head;
        while (current.next != null && !current.next.data.equals(data)) {
            current = current.next;
        }

        if (current.next != null) {
            current.next = current.next.next;
        }
    }

    // Buscar un nodo por el dato y devolverlo
    public Nodo<T> search(T data) {
        Nodo<T> current = head;
        while (current != null) {
            if (current.data.equals(data)) {
                return current;
            }
            current = current.next;
        }
        return null;
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