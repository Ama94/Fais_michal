import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.*;
import java.util.zip.GZIPInputStream;

class AnimationGUI {
    int current_speed = 1;
    JTextArea cur_speed_text_area = new JTextArea(2,1);
    CircleManager circManager = new CircleManager();
    JFrame ramka;

    private class PanelWithCircles extends JPanel {
        PanelWithCircles() {
            setVisible(true);
            setSize(1000,800);
        }
        public void paintComponent( Graphics g ) {
            super.paintComponent(g);
            Graphics2D graphic = (Graphics2D) g;
            if(circManager.circles == null) return;
            for(int i = 0; i < circManager.circles.length; i++) {
                Circle current_circle = circManager.circles[i];

                double x, y;

                x = current_circle.get_position()[0]*(getWidth()-7)/circManager.max_x;
                y = (getHeight() - 7) - current_circle.get_position()[1]*(getHeight()-7)/circManager.max_y;

                Ellipse2D c = new Ellipse2D.Double(x, y, current_circle.radius, current_circle.radius);
                graphic.draw(c);
            }
        }
    }
    void work() {
        JButton speed_up_button;
        JButton speed_down_button;

        ramka = new JFrame();
        ramka.setResizable(true);
        cur_speed_text_area.setEditable( false );
        speed_up_button = new JButton( "Faster" );
        speed_up_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                current_speed += 1;
                cur_speed_text_area.setText( "\nSpeedUp: " + current_speed );
            }
        });

        speed_down_button = new JButton( "Slower" );
        speed_down_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if( current_speed == 0 ) return;
                current_speed -= 1;

                cur_speed_text_area.setText( "\nSpeedUp: " + current_speed );
            }
        });

        cur_speed_text_area.setText( "\nSpeedUp: " + current_speed );

        JMenuBar menu_bar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem m_open = new JMenuItem("Open");

        menu.add(m_open);
        menu_bar.add(menu);
        m_open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obsługa otwierania plikow.
                JFileChooser fileChooser = new JFileChooser();
                if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    circManager.setFile(fileChooser.getSelectedFile()); // zapisanie pliku do zmiennej w circmanager
                    circManager.decodeFile();
                    circManager.create_circles();
                }
            }
        });
        PanelWithCircles panel = new PanelWithCircles();
        ramka.setJMenuBar(menu_bar);
        ramka.setSize(1000,800);
        ramka.getContentPane().add( BorderLayout.EAST, speed_up_button );
        ramka.getContentPane().add( BorderLayout.WEST, speed_down_button );
        ramka.getContentPane().add( BorderLayout.NORTH, cur_speed_text_area );
        ramka.getContentPane().add( BorderLayout.CENTER, panel );
        ramka.setVisible(true);
        ramka.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        while(!this.circManager.ready) {
            try {
                Thread.sleep(50);
            }
            catch(Exception e) {
                System.out.println("Error while sleeping");
            }
        }


        while(this.circManager.ready) {
            ramka.repaint();
            circManager.next_step(current_speed);
            try {
                Thread.sleep(30);
            }
            catch(Exception e) {
                System.out.println("Error while sleeping");
            }
        }

    }
}

class CircleManager {
    private BufferedReader reader;
    int amount_of_circles;
    private int dimensions;
    private double[][][] sequence;
    private int current_step = 0;
    Circle[] circles;
    double max_x;
    double max_y;
    boolean ready = false;

    void setFile(File f) {
        try {
            this.reader = new BufferedReader( new InputStreamReader( new GZIPInputStream( new FileInputStream( f ) ) ) );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void decodeFile() {
        if(this.reader == null) return;

        try {
            int lines = 0;
            reader.mark(1000000000);
            while (reader.readLine() != null) lines++;
            this.sequence = new double[lines-2][][];
            reader.reset();
            String line = reader.readLine();
            int i = -2;
            while (line != null) {
                if(i == -2) { this.amount_of_circles = Integer.parseInt(line); System.out.println(this.amount_of_circles);}
                if(i == -1) {this.dimensions = Integer.parseInt(line); System.out.println(this.dimensions); }
                if(i >= 0) {
                    String[] positions = line.split(" ");
                    this.sequence[i] = new double[(positions.length - 1)/2][2];
                    int index = 0;
                    boolean is_x = true;
                    for(String data: positions) {
                        if(index > 0) {
                            if(is_x) {
                                this.sequence[i][index - 1][0] = Double.parseDouble(data);
                                is_x = false;
                            }
                            else {
                                this.sequence[i][index - 1][1] = Double.parseDouble(data);
                                is_x = true;
                                index++;
                            }
                        } else index++;
                    }
                }
                i++;
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Blad podczas odczytu z pliku.");
        }
        ready = true;

    }

    void create_circles() {
        this.circles = new Circle[amount_of_circles];
        int index = 0;
        for(double[] seq: this.sequence[0]) {
            Circle c = new Circle(index, seq[0], seq[1]);
            this.circles[index] = c;
            if(seq[0] > max_x) max_x = seq[0];
            if(seq[1] > max_y) max_y = seq[1];
            index++;
        }
    }
    boolean next_step(int x) {
        this.current_step += x;
        while(this.current_step >= sequence.length) {
            current_step -= sequence.length - 1;
        }
        int index = 0;
        for( Circle cir: this.circles ) {
            cir.change_position( this.sequence[current_step][index][0], this.sequence[current_step][index][1]);
            index++;
        }
        return true;
    }
}

class Circle {
    int id;
    int radius = 6;
    private double[] position = new double[2];
    //
    // c-tor
    Circle( int id, double x, double y ) {
        this.position[0] = x;
        this.position[1] = y;
        this.id = id;
    }
    void change_position( double x, double y ) {
        this.position = new double[] {x, y};
    }

    double[] get_position() { return this.position; }

}

public class Start {
    public static void main(String[] args) {
        new AnimationGUI().work();
    }
}
