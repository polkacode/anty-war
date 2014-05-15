import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.Random;

/* inspired by Langton's ant
 * created by polkacode
 */
public class anty extends JComponent implements ActionListener {

    // lenght of the field
    int dim = 80;

    // fill up field with "0"
    int[][] fieldXY = new int[dim][dim];


    // define field color (not used right now
    private int r = 255;
    private int g = 255;
    private int b = 255;

    // edge lenght of one square
    private int kante = 10;

    // iterations counter
    private int runs = 0;

    // start postion of the ant
    private int aposx = dim /2;
    private int aposy = dim /2;
    private int bposx = dim / 2 + 25;
    private int bposy = dim / 2 ;
    // start orientation of 1st ant
    private char aorient = 's';
    // 2nd ant
    private char borient = 's';

    // boarder around the field
    private int boarder = 10;

    // build window
    public static void main(String[] args) {
        JFrame window = new JFrame("Ant Fight!");
        anty ant = new anty();
        window.add(ant);
        window.pack();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // create timer for animation thousandth part of a sec
        Timer t = new Timer(32, ant);
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
                if  (fieldXY[x][y] == 1) {
                    graphics.setColor(new Color(53, 209, 147));
                    graphics.fillRect(x * kante, y * kante, kante - 1, kante - 1);
                }
                if (fieldXY[x][y] == 2) {
                    graphics.setColor(new Color(4, 104, 67));
                    graphics.fillRect(x * kante, y * kante, kante - 1, kante - 1);
                }
                if (fieldXY[x][y] == 3) {
                    graphics.setColor(new Color(12, 255, 250));
                    graphics.fillRect(x * kante, y * kante, kante - 1, kante - 1);
                }

                if  (fieldXY[x][y] == 11) {
                    graphics.setColor(new Color(209, 24, 63));
                    graphics.fillRect(x * kante, y * kante, kante - 1, kante - 1);
                }
                if (fieldXY[x][y] == 12) {
                    graphics.setColor(new Color(126, 2, 0));
                    graphics.fillRect(x * kante, y * kante, kante - 1, kante - 1);
                }
                if (fieldXY[x][y] == 13) {
                    graphics.setColor(new Color(255, 186, 188));
                    graphics.fillRect(x * kante, y * kante, kante - 1, kante - 1);
                }
            }
        }

        // draw 1st ant
        graphics.setColor(new Color(51, 255, 1));
        graphics.fillRect(aposx * kante + 2, aposy * kante + 2, kante - 4, kante - 4);

        // draw 2nd ant
        graphics.setColor(new Color(255, 250, 254));
        graphics.fillRect(bposx * kante + 2, bposy * kante + 2, kante - 4, kante - 4);

        // draw number of iterations
        graphics.setColor(new Color(22, 21, 85));
        graphics.fillRect(0, 0, 109, 19);
        graphics.setColor(new Color(227, 241, 255));
        graphics.drawString("Iterations: " + String.valueOf(runs), 8, 15);
    }

    // calculate the field
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
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

        // ...same here
        if (bposx >= dim-1) {
            bposx = 2;
        }
        if (bposx <=0) {
            bposx = dim-2;
        }
        if (bposy >= dim-1) {
            bposy = 2;
        }
        if (bposy <=0) {
            bposy = dim-2;
        }


        //  What if there is another field flipped already?
        //if (runs < 10) {
        //    fieldXY[40][40] = 2;
        // }

        // green ant: aggressive pac-man attitude
        //
        if (fieldXY[aposx][aposy] == 11 ||fieldXY[aposx][aposy] == 12|| fieldXY[aposx][aposy] == 13 ) {
            fieldXY[aposx][aposy] =1;
            if (fieldXY[aposx-1][aposy] == 11 ||fieldXY[aposx-1][aposy] == 12|| fieldXY[aposx-1][aposy] == 13 ){
                aposx=aposx-1;
            }
            if (fieldXY[aposx+1][aposy] == 11 ||fieldXY[aposx+1][aposy] == 12|| fieldXY[aposx+1][aposy] == 13 ){
                aposx=aposx+1;
            }
            if (fieldXY[aposx][aposy-1] == 11 ||fieldXY[aposx][aposy-1] == 12|| fieldXY[aposx][aposy-1] == 13 ){
                aposy=aposy-1;
            }
            if (fieldXY[aposx][aposy+1] == 11 ||fieldXY[aposx][aposy+1] == 12|| fieldXY[aposx][aposy+1] == 13 ){
                aposy=aposy+1;
            }
        }
        // red ant: escape teleport feature
        //
        if (fieldXY[bposx][bposy] == 1 ||fieldXY[bposx][bposy] == 2|| fieldXY[bposx][bposy] == 3 ) {
            fieldXY[bposx][bposy] =11;
            if (fieldXY[bposx-1][bposy] == 1 ||fieldXY[bposx-1][bposy] == 2|| fieldXY[bposx-1][bposy] == 3 ){
                bposx=bposx-1;
            }
            if (fieldXY[bposx+1][bposy] == 1 ||fieldXY[bposx+1][bposy] == 2|| fieldXY[bposx+1][bposy] == 3 ){
                bposx=bposx+1;
            }
            if (fieldXY[bposx][bposy-1] == 1 ||fieldXY[bposx][bposy-1] == 2|| fieldXY[bposx][bposy-1] == 3 ){
                bposy=bposy-1;
            }
            if (fieldXY[bposx][bposy+1] == 1 ||fieldXY[bposx][bposy+1] == 2|| fieldXY[bposx][bposy+1] == 3 ){
                bposy=bposy+1;
            }
        }

        // calculate movement for 1st ant
        if (fieldXY[aposx][aposy] == 11 ||fieldXY[aposx][aposy] == 12|| fieldXY[aposx][aposy] == 13 ) {
        fieldXY[aposx][aposy] =0;
        }
            if (fieldXY[aposx][aposy] == 0) {
                fieldXY[aposx][aposy] = 1;
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
            if (fieldXY[aposx][aposy] == 1) {
                fieldXY[aposx][aposy] = 2;
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
            if (fieldXY[aposx][aposy] == 2) {
                fieldXY[aposx][aposy] = 3;
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
            if (fieldXY[aposx][aposy] == 3) {
                fieldXY[aposx][aposy] = 0;
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
            if (fieldXY[bposx][bposy] == 0) {
                fieldXY[bposx][bposy] = 11;
                switch (borient) {
                    case 'n':
                        borient = 'o';
                        bposx = bposx + 1;
                        break;
                    case 'o':
                        borient = 's';
                        bposy = bposy + 1;
                        break;
                    case 's':
                        borient = 'w';
                        bposx = bposx - 1;
                        break;
                    case 'w':
                        borient = 'n';
                        bposy = bposy - 1;
                        break;
                }
            }
            if (fieldXY[bposx][bposy] == 11) {
                fieldXY[bposx][bposy] = 12;
                switch (borient) {
                    case 'n':
                        borient = 'w';
                        bposx = bposx - 1;
                        break;
                    case 'w':
                        borient = 's';
                        bposy = bposy + 1;
                        break;
                    case 's':
                        borient = 'o';
                        bposx = bposx + 1;
                        break;
                    case 'o':
                        borient = 'n';
                        bposy = bposy - 1;
                        break;
                }
            }
            if (fieldXY[bposx][bposy] == 12) {
                fieldXY[bposx][bposy] = 13;
                switch (borient) {
                    case 'n':
                        borient = 'w';
                        bposx = bposx - 1;
                        break;
                    case 'w':
                        borient = 's';
                        bposy = bposy + 1;
                        break;
                    case 's':
                        borient = 'o';
                        bposx = bposx + 1;
                        break;
                    case 'o':
                        borient = 'n';
                        bposy = bposy - 1;
                        break;
                }
            }
        if (fieldXY[bposx][bposy] == 13) {
            fieldXY[bposx][bposy] = 0;
            switch (borient) {
                case 'n':
                    borient = 'o';
                    bposx = bposx + 1;
                    break;
                case 'o':
                    borient = 's';
                    bposy = bposy + 1;
                    break;
                case 's':
                    borient = 'w';
                    bposx = bposx - 1;
                    break;
                case 'w':
                    borient = 'n';
                    bposy = bposy - 1;
                    break;
            }
        }
        runs++;
        repaint(); // let paintComponent do it's job and draw the field
        }
    }


