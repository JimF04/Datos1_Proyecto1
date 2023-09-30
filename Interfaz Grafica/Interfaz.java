import org.json.JSONException;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase representa la interfaz principal del juego "Connect the Dots".
 * Permite a los usuarios jugar el juego, acceder a la información y administrar las ventanas.
 */
public class Interfaz {

    private BufferedImage backgroundImage;

/**
* Constructor de la clase Interfaz. Carga la imagen de fondo y configura la interfaz principal.
*/
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

/**
 * Método principal para iniciar la aplicación.
 * @param args Los argumentos de línea de comandos (no utilizados en este caso).
 */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Interfaz());
    }
}

/**
 * Clase que representa la segunda ventana de la aplicación "Connect the Dots".
 * Permite a los usuarios ingresar su nombre de usuario y comenzar el juego.
 */
class Ventana2 extends JFrame {
/**
 * Constructor de la clase Ventana2. Configura la interfaz de la segunda ventana.
 */
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
                String jugador = areadenick.getText();
                if (jugador.trim().isEmpty() || jugador.contains(" ")) {
                    JOptionPane.showMessageDialog(Ventana2.this, "Sin espacios", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (jugador.contains(" ")){
                    JOptionPane.showMessageDialog(Ventana2.this, "Está vacío!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Ventanagame ventanaGame = new Ventanagame(jugador);
                    dispose();
                }
            }

        });
        

        milaminagame.add(gamebtn);

        setVisible(true);
    }
}
/**
 * Clase que representa la tercera ventana de la aplicación "Connect the Dots".
 * Muestra información o instrucciones del juego con una imagen de fondo.
 */
class Ventana3 extends JFrame {
    private BufferedImage backgroundImage;

/**
 * Constructor de la clase Ventana3. Configura la interfaz de la tercera ventana.
 */
    public Ventana3() {
        setLayout(null);
        setTitle("Informacion");
        setSize(1200, 675);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            // Cargar la imagen de fondo
            backgroundImage = ImageIO.read(getClass().getResource("instrucciones.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanel milaminainfo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Dibuja la imagen de fondo
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        milaminainfo.setLayout(null);
        milaminainfo.setSize(1200, 675);

        JButton returnbtni = new JButton();
        returnbtni.setBounds(20, 600, 100, 30);
        returnbtni.setText("RETURN");
        returnbtni.addActionListener(e -> {
            new Interfaz();
            dispose();
        });
        milaminainfo.add(returnbtni);

        add(milaminainfo);

        setVisible(true);
    }
/**
 * Método principal que crea una instancia de la tercera ventana.
 * @param args Los argumentos de la línea de comandos (no se utilizan en este caso).
 */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Ventana3();
        });
    }
}

/**
 * Clase que representa la ventana del juego "Connect the Dots".
 * Muestra el nombre del jugador, el estado del turno y un panel de juego.
 */
class Ventanagame extends JFrame{

    private JLabel labelJugador;
    private JLabel labelTurno;
    private String nombreJugador;

/**
 * Constructor de la clase Ventanagame. Configura la interfaz de la ventana del juego.
 * @param jugador El nombre del jugador que se mostrará en la ventana.
 */
    public Ventanagame(String jugador){
        this.nombreJugador = jugador;

        setLayout(null);
        setSize(1000,1000);
        setTitle("CONNECT THE DOTS");
        setResizable(false);

        labelJugador = new JLabel("Jugador: " + jugador);
        labelJugador.setFont(new Font("MV Boli", Font.PLAIN, 20));
        labelJugador.setForeground(Color.BLACK); // O el color que prefieras
        labelJugador.setBounds(10, 10, 300, 30); // Ajusta la posición y tamaño según lo necesites

        add(labelJugador);

        labelTurno = new JLabel("Esperando turno...");
        labelTurno.setFont(new Font("MV Boli", Font.PLAIN, 20));
        labelTurno.setForeground(Color.RED); // El color inicial es rojo, lo cambiaremos a verde cuando sea el turno del jugador
        labelTurno.setBounds(600, 10, 300, 30);
        add(labelTurno);
        

        PanelDePuntos panelDePuntos = new PanelDePuntos(8, 8,labelTurno, nombreJugador);
        panelDePuntos.setBounds(130, 100, 720, 720);
        panelDePuntos.setBackground(Color.white);
        ClienteThread clienteThread = new ClienteThread(panelDePuntos);
        clienteThread.start();

        

        add(panelDePuntos, BorderLayout.CENTER);

        setVisible(true);

    }

}

/**
 * La clase PanelDePuntos representa el panel de juego de "Connect the Dots".
 * Permite a los jugadores conectar puntos para formar cuadrados.
 */
class PanelDePuntos extends JPanel{
    private ListaEnlazada<Punto> puntosTotales = new ListaEnlazada<>();
    private List<Punto> puntosSeleccionados = new ArrayList<>();
    private List<Linea> lineasDibujadas = new ArrayList<>();
    private ListaEnlazada<List<Punto>> cuadradosCompletados = new ListaEnlazada<>();
    public boolean esMiTurno = false;
    private JLabel labelTurno;
    private ClienteThread clienteThread;

    private String nombreJugador;

    private int totalCuadrados;
    private int cuadradosCompletadosCount;


/**
 * Verifica si se ha formado un cuadrado después de dibujar una línea.
 *
 * @param nuevaLinea La nueva línea dibujada por un jugador.
 */
    private void verificarCuadrado(Linea nuevaLinea) {
        List<Linea> adyacentes = obtenerAdyacentes(nuevaLinea);
        for (Linea linea1 : adyacentes) {
            for (Linea linea2 : obtenerAdyacentes(linea1)) {
                for (Linea linea3 : obtenerAdyacentes(linea2)) {
                    // Verificar que las líneas son distintas
                    if (linea1.equals(nuevaLinea) || linea2.equals(nuevaLinea) || linea3.equals(nuevaLinea)
                            || linea1.equals(linea2) || linea1.equals(linea3) || linea2.equals(linea3)) {
                        continue;
                    }
                    // Verificar que las líneas forman un cuadrado
                    if (formaCuadrado(nuevaLinea, linea1, linea2, linea3)) {
                        ListaEnlazada<Punto> cuadrado = new ListaEnlazada<>();
                        agregarSiNoExiste(cuadrado, nuevaLinea.getPunto1());
                        agregarSiNoExiste(cuadrado, nuevaLinea.getPunto2());
                        agregarSiNoExiste(cuadrado, linea1.getPunto1());
                        agregarSiNoExiste(cuadrado, linea1.getPunto2());
                        agregarSiNoExiste(cuadrado, linea2.getPunto1());
                        agregarSiNoExiste(cuadrado, linea2.getPunto2());
                        agregarSiNoExiste(cuadrado, linea3.getPunto1());
                        agregarSiNoExiste(cuadrado, linea3.getPunto2());
                        if (cuadrado.getAll().size() == 4) {
                            cuadradosCompletados.add(cuadrado.getAll());
                            cuadradosCompletadosCount++;
                            if (cuadradosCompletadosCount == totalCuadrados) {
                                // Aquí puedes mostrar un mensaje de finalización del juego
                                JOptionPane.showMessageDialog(this, "¡Juego completado!");
                            }
                        }
                    }
                }
            }
        }
    }



/**
 * Obtiene las líneas adyacentes a una línea dada.
 *
 * @param linea La línea para la cual se buscan líneas adyacentes.
 * @return Una lista de líneas adyacentes.
 */
    private List<Linea> obtenerAdyacentes(Linea linea) {
        List<Linea> adyacentes = new ArrayList<>();
        for(Linea l : lineasDibujadas) {
            if(l.esAdyacente(linea) && !l.equals(linea)) {
                adyacentes.add(l);
            }
        }
        return adyacentes;
    }

/**
 * Verifica si cuatro líneas forman un cuadrado.
 *
 * @param l1 Primera línea.
 * @param l2 Segunda línea.
 * @param l3 Tercera línea.
 * @param l4 Cuarta línea.
 * @return true si las líneas forman un cuadrado; de lo contrario, false.
 */
    private boolean formaCuadrado(Linea l1, Linea l2, Linea l3, Linea l4) {
        ListaEnlazada<Punto> puntos = new ListaEnlazada<>();
        agregarSiNoExiste(puntos, l1.getPunto1());
        agregarSiNoExiste(puntos, l1.getPunto2());
        agregarSiNoExiste(puntos, l2.getPunto1());
        agregarSiNoExiste(puntos, l2.getPunto2());
        agregarSiNoExiste(puntos, l3.getPunto1());
        agregarSiNoExiste(puntos, l3.getPunto2());
        agregarSiNoExiste(puntos, l4.getPunto1());
        agregarSiNoExiste(puntos, l4.getPunto2());
        return puntos.getAll().size() == 4;
    }

/**
 * Agrega un punto a una lista si no existe ya en ella.
 *
 * @param lista La lista en la que se desea agregar el punto.
 * @param punto El punto que se va a agregar.
 */
    private void agregarSiNoExiste(ListaEnlazada<Punto> lista, Punto punto) {
        for (Punto p : lista.getAll()) {
            if (p.getX() == punto.getX() && p.getY() == punto.getY()) {
                return;
            }   
        }
        lista.add(punto);
    }


/**
 * Constructor de la clase PanelDePuntos.
 *
 * @param filas     Número de filas de puntos en el panel.
 * @param columnas  Número de columnas de puntos en el panel.
 * @param labelturno JLabel que muestra el estado del turno.
 * @param jugador   El nombre del jugador que usa este panel.
 */
    public PanelDePuntos(int filas, int columnas, JLabel labelturno, String jugador){

        this.labelTurno = labelturno;
        this.nombreJugador = jugador;

        totalCuadrados = (filas - 1) * (columnas - 1);
        cuadradosCompletadosCount = 0;


        for (int i = 0; i<filas;i++){
            for (int j = 0; j<columnas; j++){
                Punto punto = new Punto(i*100+5,j*100+5,i,j);
                puntosTotales.add(punto);
            }
        }

        addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                if (!esMiTurno) {
                    return; // Si no es el turno del cliente, no hacer nada
                }
                int x= e.getX();
                int y= e.getY();
                Punto puntoseleccionado = getPuntoCercano(x,y);
                 if (puntoseleccionado !=null){
                    puntosSeleccionados.add(puntoseleccionado);
                        if (puntosSeleccionados.size()==2){
                            Punto p1 = puntosSeleccionados.get(0);
                            Punto p2 = puntosSeleccionados.get(1);
                            
                            if(esLineaValida(p1,p2)){
                                Linea linea = new Linea(p1,p2);
                                lineasDibujadas.add(linea);
                                verificarCuadrado(linea);
                                enviarCoordenadasServidor(p1, p2);
                            }else{
                                System.out.println("Solo se pueden hacer lineas verticales, horizontales y con un espacio de 100 entre punto");

                            }
                            puntosSeleccionados.clear();
                            repaint();
                                   
                        }  
                }
                
            }

        });
    }

/**
 * Verifica si una línea es válida antes de agregarla.
 *
 * @param p1 Punto inicial de la línea.
 * @param p2 Punto final de la línea.
 * @return true si la línea es válida; de lo contrario, false.
 */    
    private boolean esLineaValida(Punto p1, Punto p2) {
        for (Linea linea : lineasDibujadas) {
            if (linea.equals(new Linea(p1, p2))) {
                return false;
            }
        }
        return (p1.getX() == p2.getX() || p1.getY() == p2.getY()) && calcularDistancia(p1, p2) == 100;
    }

/**
 * Calcula la distancia entre dos puntos utilizando el teorema de Pitágoras.
 *
 * @param p1 Primer punto.
 * @param p2 Segundo punto.
 * @return La distancia entre los dos puntos.
 */
    private double calcularDistancia(Punto p1, Punto p2){
        return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
    }

/**
 * Obtiene el punto más cercano a las coordenadas especificadas.
 *
 * @param x Coordenada x del punto de referencia.
 * @param y Coordenada y del punto de referencia.
 * @return El punto más cercano a las coordenadas especificadas.
 */
    private Punto getPuntoCercano(int x,int y){
        Punto puntoCercano = null;
        double distanciaMinima = Double.MAX_VALUE;
        for(Punto punto : puntosTotales.getAll()){
                double distancia = Math.sqrt(Math.pow(punto.getX() - x, 2) + Math.pow(punto.getY() - y,2));
                if (distancia<35 && distancia<distanciaMinima){
                        distanciaMinima=distancia;
                        puntoCercano = punto;
                    
                    }
                
            }
            return puntoCercano;
        }

/**
 * Sobrescribe el método paintComponent para dibujar puntos, líneas y cuadrados en el panel.
 *
 * @param g El contexto de gráficos en el que se realiza el dibujo.
 */
    protected void paintComponent(Graphics g){
    super.paintComponent(g);

    g.setColor(Color.BLACK);  // Color de los puntos
    for (Punto punto : puntosTotales.getAll()) {
        g.fillOval(punto.getX() - 5, punto.getY() - 5, 9,9 );  // Dibuja un círculo con radio 5 en cada punto
    }

    for (List<Punto> cuadrado : cuadradosCompletados.getAll()) {     
        cuadrado.sort((p1, p2) -> {
            if(p1.getX() != p2.getX()) return p1.getX() - p2.getX();
            return p1.getY() - p2.getY();
        });

        Punto p1 = cuadrado.get(0);
        Punto p2 = cuadrado.get(1);
        Punto p3 = cuadrado.get(2);
        Punto p4 = cuadrado.get(3);

        int[] xPoints = {p1.getX(), p2.getX(), p4.getX(), p3.getX()};
        int[] yPoints = {p1.getY(), p2.getY(), p4.getY(), p3.getY()};

        g.setColor(Color.pink);
        g.fillPolygon(xPoints, yPoints, 4);
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
        g.setColor(Color.BLACK);
    }
    

    }

/**
 * Envia las coordenadas de una línea al servidor.
 *
 * @param p1 Punto inicial de la línea.
 * @param p2 Punto final de la línea.
 */
    private void enviarCoordenadasServidor(Punto p1, Punto p2) {
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("x1", p1.getX());
            jsonObj.put("y1", p1.getY());
            jsonObj.put("x2", p2.getX());
            jsonObj.put("y2", p2.getY());
            jsonObj.put("jugador", nombreJugador);

            Socket socketclient = new Socket("localhost", 9991);
            DataOutputStream dos = new DataOutputStream(socketclient.getOutputStream());
            dos.writeUTF(jsonObj.toString());
            dos.flush();   // Asegúrate de que el mensaje se envíe antes de cerrar el socket.
            dos.close();
            socketclient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        esMiTurno = false; // Luego de enviar coordenadas, ya no es el turno del cliente
        actualizarLabelTurno();
    }

/**
 * Asigna el turno al cliente y actualiza la etiqueta de estado del turno.
 */
    public void asignarTurno() {
        esMiTurno = true; // Asignar el turno al cliente
        actualizarLabelTurno();
    }

/**
 * Actualiza la etiqueta de estado del turno en la interfaz de usuario.
 */
    public void actualizarLabelTurno() {
        SwingUtilities.invokeLater(() -> {
            if (esMiTurno) {
                labelTurno.setText("Tu turno!");
                labelTurno.setForeground(Color.GREEN);
            } else {
                labelTurno.setText("Esperando turno...");
                labelTurno.setForeground(Color.RED);
            }
        });
    }

/**
 * Conecta dos puntos al recibir una solicitud del servidor y verifica si se forma un cuadrado.
 *
 * @param x1 Coordenada x del primer punto.
 * @param y1 Coordenada y del primer punto.
 * @param x2 Coordenada x del segundo punto.
 * @param y2 Coordenada y del segundo punto.
 */
    public void conectarPuntos(int x1, int y1, int x2, int y2) {
        Punto punto1 = getPuntoCercano(x1, y1);
        Punto punto2 = getPuntoCercano(x2, y2);

        if (punto1 != null && punto2 != null) {
            if (esLineaValida(punto1, punto2)) {
                Linea linea = new Linea(punto1, punto2);
                lineasDibujadas.add(linea);
                verificarCuadrado(linea);
                repaint();
            } else {
                System.out.println("Solo se pueden hacer lineas verticales, horizontales y con un espacio de 100 entre puntos");
            }
        }
    }


}

/**
 * La clase Punto representa un punto en un sistema de coordenadas bidimensional.
 * Cada punto tiene coordenadas (x, y) que indican su posición en el plano.
 */
 class Punto {
    private int x;
    private int y;

/**
 * Constructor para la clase Punto.
 *
 * @param x La coordenada x del punto.
 * @param y La coordenada y del punto.
 */
    public Punto(int x, int y,int fila, int columna) {
        this.x = x;
        this.y = y;
    }

/**
 * Obtiene la coordenada x del punto.
 *
 * @return La coordenada x del punto.
 */
    public int getX() {
        return x;
    }


/**
 * Establece la coordenada x del punto.
 *
 * @param x La nueva coordenada x del punto.
 */
    public void setX(int x) {
        this.x = x;
    }

/**
 * Compara este punto con otro objeto para verificar si son iguales.
 *
 * @param obj El objeto con el que se compara este punto.
 * @return true si los puntos son iguales; de lo contrario, false.
 */
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Punto punto = (Punto) obj;
        return x == punto.x && y == punto.y;
    }

/**
 * Obtiene la coordenada y del punto.
 *
 * @return La coordenada y del punto.
 */
    public int getY() {
        return y;
    }

/**
 * Establece la coordenada y del punto.
 *
 * @param y La nueva coordenada y del punto.
 */
    public void setY(int y) {
        this.y = y;
    }


/**
 * Representación de cadena del punto en el formato "(x, y)".
 *
 * @return Una cadena que representa el punto.
 */    
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}

/**
 * La clase Linea representa una línea entre dos puntos en un sistema de coordenadas bidimensional.
 * Cada línea está definida por dos puntos de inicio y fin.
 */
class Linea {
    private Punto punto1;
    private Punto punto2;

/**
 * Constructor para la clase Linea.
 *
 * @param punto1 El primer punto de la línea.
 * @param punto2 El segundo punto de la línea.
 */
    public Linea(Punto punto1, Punto punto2) {
        this.punto1 = punto1;
        this.punto2 = punto2;
    }

/**
 * Obtiene el primer punto de la línea.
 *
 * @return El primer punto de la línea.
 */    
    public Punto getPunto1() {
        return punto1;
    }

/**
 * Obtiene el segundo punto de la línea.
 *
 * @return El segundo punto de la línea.
 */    
    public Punto getPunto2() {
        return punto2;
    }

/**
 * Verifica si esta línea es adyacente a otra línea.
 *
 * @param otra La otra línea con la que se verifica la adyacencia.
 * @return true si las líneas son adyacentes; de lo contrario, false.
 */    
    public boolean esAdyacente(Linea otra) {
        return this.punto1.equals(otra.punto1) || this.punto1.equals(otra.punto2) ||
               this.punto2.equals(otra.punto1) || this.punto2.equals(otra.punto2);
    }

/**
 * Verifica si esta línea es perpendicular a otra línea.
 *
 * @param otra La otra línea con la que se verifica la perpendicularidad.
 * @return true si las líneas son perpendiculares; de lo contrario, false.
 */
    public boolean esPerpendicular(Linea otra) {
        if (this.punto1.getX() == this.punto2.getX()) { // Si esta línea es vertical
            return otra.punto1.getY() == otra.punto2.getY(); // La otra debe ser horizontal
        } else if (this.punto1.getY() == this.punto2.getY()) { // Si esta línea es horizontal
            return otra.punto1.getX() == otra.punto2.getX(); // La otra debe ser vertical
        }
        return false;
    }

/**
 * Compara esta línea con otro objeto para verificar si son iguales.
 *
 * @param obj El objeto con el que se compara esta línea.
 * @return true si las líneas son iguales; de lo contrario, false.
 */    
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Linea linea = (Linea) obj;
        return (punto1.equals(linea.punto1) && punto2.equals(linea.punto2)) ||
            (punto1.equals(linea.punto2) && punto2.equals(linea.punto1));
    }
}

/**
 * La clase ClienteThread representa un hilo de cliente que se conecta al servidor y maneja la comunicación con el servidor.
 * Este hilo escucha continuamente los mensajes del servidor y realiza acciones en función de los mensajes recibidos.
 */
class ClienteThread extends Thread {
    private Socket socket;
    private PanelDePuntos panel;

/**
 * Constructor para la clase ClienteThread.
 *
 * @param panel El panel de puntos al que se vincula este hilo de cliente.
 */
    public ClienteThread(PanelDePuntos panel) {
        this.panel = panel;
        try {
            this.socket = new Socket("localhost", 9991);  // Dirección y puerto del servidor
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


/**
 * El método run() del hilo. Escucha continuamente los mensajes del servidor y los procesa.
 */    
    public void run() {
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());

            while (true) {
                String mensaje = in.readUTF();
                procesarMensaje(mensaje);
            }

        } catch(IOException e) {
            e.printStackTrace();
        }
    }
/**
 * Procesa un mensaje recibido del servidor y realiza acciones en función del contenido del mensaje.
 *
 * @param mensaje El mensaje recibido del servidor.
 */    
    private void procesarMensaje(String mensaje) {
        if ("TU TURNO".equals(mensaje)) {
            panel.asignarTurno();
        } else if ("ESPERANDO TURNO".equals(mensaje)) {
            panel.esMiTurno = false; // Asignar el turno al cliente
            panel.actualizarLabelTurno();
        } else {
            try {
                JSONObject jsonObj = new JSONObject(mensaje);
                int x1 = jsonObj.getInt("x1");
                int y1 = jsonObj.getInt("y1");
                int x2 = jsonObj.getInt("x2");
                int y2 = jsonObj.getInt("y2");

                SwingUtilities.invokeLater(() -> {
                    panel.conectarPuntos(x1, y1, x2, y2);
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}



/**
 * La clase Nodo<T> representa un nodo en una lista enlazada genérica. Cada nodo contiene un dato de tipo T y
 * una referencia al siguiente nodo en la lista.
 *
 * @param <T> El tipo de dato que se almacena en el nodo.
 */
class Nodo<T> {
    T data;
    Nodo<T> next;

/**
 * Constructor para la clase Nodo. Crea un nuevo nodo con el dato especificado y sin referencia al siguiente nodo.
 *
 * @param data El dato que se almacenará en el nodo.
 */    
    public Nodo(T data) {
        this.data = data;
        this.next = null;
    }
}


/**
 * La clase ListaEnlazada<T> representa una lista enlazada genérica. Proporciona operaciones para agregar, eliminar,
 * buscar y obtener datos de la lista.
 *
 * @param <T> El tipo de dato que se almacena en la lista.
 */
class ListaEnlazada<T> {
    private Nodo<T> head;

/**
 * Constructor para la clase ListaEnlazada. Inicializa una lista enlazada vacía con la cabeza (head) nula.
 */    
    public ListaEnlazada() {
        this.head = null;
    }

/**
 * Agrega un nuevo nodo con el dato especificado al final de la lista.
 *
 * @param data El dato que se agregará a la lista.
 */
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

/**
 * Elimina el nodo que contiene el dato especificado de la lista, si existe.
 *
 * @param data El dato que se desea eliminar de la lista.
 */
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

/**
 * Busca un nodo que contenga el dato especificado en la lista y devuelve dicho nodo.
 *
 * @param data El dato que se desea buscar en la lista.
 * @return El nodo que contiene el dato especificado, o null si no se encuentra.
 */
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

/**
 * Obtiene todos los datos de la lista como una lista de tipo List<T> para facilitar su manejo.
 *
 * @return Una lista que contiene todos los datos de la lista en el orden en que aparecen.
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
}