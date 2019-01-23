import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.*;
import java.util.zip.GZIPInputStream;

class Kropka {
    int promien;
    private double[] miejsce = new double[2];
    Kropka(double x, double y, int promien) {
        this.miejsce[0] = x;
        this.miejsce[1] = y;
        this.promien = promien;
    }
    void zmien_miejsce(double x, double y ) {
        this.miejsce = new double[] {x, y};
    }
    double[] gdzie_jestes() { return this.miejsce; }

}


class Zarzadzaj_Kropka {
    private BufferedReader rd;
    private int ile_kol;
    private double[][][] sekwencja;
    private int krok = 0;
    Kropka[] kropki;
    double x_max;
    double y_max;
    boolean czy_gotowy = false;

    void setFile(File f) throws IOException {
        this.rd = new BufferedReader( new InputStreamReader( new GZIPInputStream( new FileInputStream( f ))));
    }

    void odczyt_pliku() throws IOException {
        if(this.rd == null) return;
        int linie = 0;
        rd.mark(1000000000);
        while (rd.readLine() != null) linie++;
        this.sekwencja = new double[linie -2][][];
        rd.reset();
        String linia = rd.readLine();
        int i = -2;
        while (linia != null) {
            if(i == -2) { this.ile_kol = Integer.parseInt(linia); System.out.println(this.ile_kol);}
            if(i == -1) {
                int dimensions = Integer.parseInt(linia);
                System.out.println(dimensions); }
            if(i >= 0) {
                String[] positions = linia.split(" ");
                this.sekwencja[i] = new double[(positions.length - 1)/2][2];
                int index = 0;
                boolean is_x = true;
                for(String argument : positions) {
                    if(index > 0) {
                        if(is_x) {
                            this.sekwencja[i][index - 1][0] = Double.parseDouble(argument);
                            is_x = false;
                        }
                        else {
                            this.sekwencja[i][index - 1][1] = Double.parseDouble(argument);
                            is_x = true;
                            index++;
                        }
                    } else index++;
                }
            }
            i++;
            linia = rd.readLine();
        }
        rd.close();
        czy_gotowy = true;
    }

    void co_dalej(int x) {
        this.krok += x;
        while(this.krok >= sekwencja.length) {
            krok -= sekwencja.length - 1;
        }
        int index = 0;
        for( Kropka cir: this.kropki) {
            cir.zmien_miejsce( this.sekwencja[krok][index][0], this.sekwencja[krok][index][1]);
            index++;
        }
    }

    void stworz_kropke() {
        this.kropki = new Kropka[ile_kol];
        int idx = 0;
        for(double[] seq: this.sekwencja[0]) {
            Kropka c = new Kropka(seq[0], seq[1],10);
            this.kropki[idx] = c;
            if(seq[0] > x_max) x_max = seq[0];
            if(seq[1] > y_max) y_max = seq[1];
            idx++;
        }
    }

}



class Animacja {
    private int predkosc;
    private JTextArea predkosc_text;
    private Zarzadzaj_Kropka cM;

    Animacja(int current_speed, JTextArea cur_speed_text_area, Zarzadzaj_Kropka cM) {
        this.predkosc = current_speed;
        this.predkosc_text = cur_speed_text_area;
        this.cM = cM;
    }

    private class Obsluga extends JPanel {
        public void paintComponent( Graphics g ) {
            super.paintComponent(g);
            Graphics2D grafika = (Graphics2D) g;
            RenderingHints RendHints = new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            ((Graphics2D) g).setRenderingHints(RendHints);
            if(cM.kropki == null) return;
            for(int i = 0; i < cM.kropki.length; i++) {
                Kropka kolo = cM.kropki[i];
                Ellipse2D c = new Ellipse2D.Double(kolo.gdzie_jestes()[0]*(getWidth()-7)/ cM.x_max, (getHeight() - 7) - kolo.gdzie_jestes()[1]*(getHeight()-7)/ cM.y_max, kolo.promien, kolo.promien);
                grafika.draw(c);
            }
        }
    }
    void wykonaj() throws InterruptedException {
        JFrame ekran = new JFrame();
        JMenuBar obs = new JMenuBar();
        JButton szybciej = new JButton();
        JButton wolniej = new JButton();

        JMenu menu = new JMenu("File");
        JMenuItem m_open = new JMenuItem("Open");
        ekran.setResizable(true);
        predkosc_text.setEditable( false );

        szybciej.setText("Faster");
        szybciej.addActionListener(e -> {
            predkosc++;
            predkosc_text.setText( "SpeedUp: " + predkosc);
        });

        wolniej.setText("Slower");
        wolniej.addActionListener(e -> {
            if( predkosc == 0 ) return;
            predkosc--;
            predkosc_text.setText( "SpeedUp: " + predkosc);
        });
        predkosc_text.setText( "SpeedUp: " + predkosc);

        menu.add(m_open);
        obs.add(menu);
        m_open.addActionListener(e -> {
            // Obsługa otwierania plikow.
            JFileChooser fileChooser = new JFileChooser();
            if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                try {
                    cM.setFile(fileChooser.getSelectedFile()); // zapisanie pliku do zmiennej w circmanager
                    cM.odczyt_pliku();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                cM.stworz_kropke();
            }
        });
        Obsluga panel = new Obsluga();
        ekran.setJMenuBar(obs);
        ekran.setSize(800,600);
        ekran.getContentPane().add( BorderLayout.EAST, szybciej);
        ekran.getContentPane().add( BorderLayout.WEST, wolniej);
        ekran.getContentPane().add( BorderLayout.NORTH, predkosc_text);
        ekran.getContentPane().add( BorderLayout.CENTER, panel );
        ekran.setVisible(true);
        ekran.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        while(!this.cM.czy_gotowy) {
                Thread.sleep(50);
        }

        while(this.cM.czy_gotowy) {
            ekran.repaint();
            cM.co_dalej(predkosc);
            try {
                Thread.sleep(30);
            }
            catch(Exception e) {
                System.out.println("Error while sleeping");
            }
        }

    }
}




public class Start {
    public static void main(String[] args) throws InterruptedException {
        Animacja animation = new Animacja(0,new JTextArea(2,1),new Zarzadzaj_Kropka());
        animation.wykonaj();
    }
}
