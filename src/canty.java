import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/* inspired by Langton's ant
 * created by polkacode
 */
public class canty extends JComponent implements ActionListener {

    // lenght of the field
    int dim = 80;

    // fill up field with "0"
    int[][][] fieldXYC = new int[dim][dim][2];


    // define field color (not used right now
    private int r = 255;
    private int g = 255;
    private int b = 255;

    // edge lenght of one square
    private int kante = 9;

    // iterations counter
    private int runs = 0;

    // start postion of the ant
    private int aposx = dim /2;
    private int aposy = dim /2;

    // start orientation of 1st ant
    private char aorient = 's';
    // 2nd ant

    // boarder around the field
    private int boarder = 10;

    // build window
    public static void main(String[] args) {
        JFrame window = new JFrame("Ant Fight!");
        canty ant = new canty();
        window.add(ant);
        window.pack();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // create timer for animation thousandth part of a sec
        Timer t = new Timer(10, ant);
        t.start();
    }

    // size of the window in pixels
    public Dimension getPreferredSize() {
        return new Dimension(dim *10, dim *10);
    }


    @Override
    protected void paintComponent(Graphics graphics) {
        // draw background
        graphics.setColor(new Color(0, 0, 0));
        graphics.fillRect(0, 0, dim*10, dim*10);

        // draw the status of the field which is represented by fieldXY[][] all the time:
        for (int y = 0; y < dim; y = y + 1) {
            for (int x = 0; x < dim; x = x + 1) {
                if (fieldXYC[x][y][0] >= 0) {
                    if (fieldXYC[x][y][1] >0 ) {
                        if (fieldXYC[x][y][1]<=10) {
                            graphics.setColor(new Color(0, 0, 0 + fieldXYC[x][y][1] * 25));
                        }
                        if (fieldXYC[x][y][1]>10 && fieldXYC[x][y][1]<20) {
                            graphics.setColor(new Color(0,(fieldXYC[x][y][1]-10) * 25,255));
                        }
                        if (fieldXYC[x][y][1]>=20 && fieldXYC[x][y][1]<30) {
                            graphics.setColor(new Color((fieldXYC[x][y][1]-20) * 25,255,155));
                        }
                        if (fieldXYC[x][y][1]>=30 && fieldXYC[x][y][1]<40) {
                            graphics.setColor(new Color(255,255-(((fieldXYC[x][y][1]-30) * 25)),255-((fieldXYC[x][y][1]-30) * 25)));
                        }
                        graphics.fillRect(x * kante, y * kante, kante - 1, kante - 1);
                    }
                }
            }
        }

        // draw  ant
        graphics.setColor(new Color(255, 157, 50));
        graphics.fillRect(aposx * kante + 2, aposy * kante + 2, kante - 4, kante - 4);


        // draw number of iterations
        graphics.setColor(new Color(22, 21, 85));
        graphics.fillRect(0, 0, 409, 19);
        graphics.setColor(new Color(227, 241, 255));
        graphics.drawString("Iterations: " + String.valueOf(runs)+" | X: "+aposx+"Y: "+aposy+" c-count: "+ fieldXYC[aposx][aposy][1], 8, 15);
    }

    // calculate the field
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        g = 255;
        b = 255;

        // make sure no one is falling off the edge ...
        if (aposx == dim-1) {
            aposx = 2;
        }
        if (aposx<=0) {
            aposx = dim-2;
        }
        if (aposy == dim-1) {
            aposy = 2;
        }
        if (aposy <=0) {
            aposy = dim-2;
        }

        if (fieldXYC[aposx][aposy][0] == 0) {
            fieldXYC[aposx][aposy][0] = 1;
            fieldXYC[aposx][aposy][1] = fieldXYC[aposx][aposy][1] + 1;
            switch (aorient) {
                case 'n':
                    aorient = 'o';
                    aposx = aposx + 1;
                    break;
                case 'o':
                    aorient = 's';
                    aposy = aposy + 1;
                    break;
                case 's':
                    aorient = 'w';
                    aposx = aposx - 1;
                    break;
                case 'w':
                    aorient = 'n';
                    aposy = aposy - 1;
                    break;
            }
        }
        if (fieldXYC[aposx][aposy][0] == 1) {
            fieldXYC[aposx][aposy][0] = 0;
            switch (aorient) {
                case 'n':
                    aorient = 'w';
                    aposx = aposx - 1;
                    break;
                case 'w':
                    aorient = 's';
                    aposy = aposy + 1;
                    break;
                case 's':
                    aorient = 'o';
                    aposx = aposx + 1;
                    break;
                case 'o':
                    aorient = 'n';
                    aposy = aposy - 1;
                    break;
            }
        }
        runs++;
        // let paintComponent do it's job and draw the field
        repaint();
        }
    }


