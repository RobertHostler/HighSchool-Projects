import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TopDownFieldPlotter extends JPanel{
     static int ScreenWidth = 1280;
     static int ScreenHeight = 720;
     
     // Overloaded constructor uses default values for screen size
     public TopDownFieldPlotter(){
     }
     
     // Overloaded constructor reassigns screen size using parameters passed to it
     public TopDownFieldPlotter (int x, int y){
          ScreenWidth = x;
          ScreenHeight = y;
     }
     
     public void paint(Graphics g) {
          Graphics2D g2 = (Graphics2D) g;
          g2.translate(0, ScreenHeight - 40);
          g2.scale(1.0, -1.0);
          g2.setColor(Color.BLUE);
          
          double I = 1000000.0;
          double R = 75;
          BField B = new BField(I , R);
          
          NumberToColour field = new NumberToColour (1, 2, 3, 4, 5, 6, 7);
          Color colour = new Color (0, 0, 0);
          int R1 = 0, G1 = 0, B1 = 0;
          
          final int Ymax = 300, Xmax = 300;
          final int X0 = 640, Y0 = 360;
          int Dy = 1, Dx = 1;
          int x = -Xmax, y = -Ymax, z = 1;
          int x1, y1;
          
          double Bx = 0;
          double By = 0; 
          double b;
          double [] Bcomponents;
          int bx, by;
          int b1, b2;
          int [] rgb = new int [3];
          
          int S1 = 7;
          int S2 = 1;
          double O = 0;
          
          while (y < Ymax){
               while (x < Xmax){
                    Bcomponents = B.Bxyz ((double)x, (double)y, (double)z);
                    Bx = Bcomponents [0];
                    By = Bcomponents [1];
                    //b = Math.sqrt (Bx * Bx + By*By);
                    b = Math.abs(Bcomponents[2]);;
                    
                    rgb = field.GetColourLogarithm (b);
                    colour = new Color (rgb [0], rgb [1], rgb [2]);
                    g.setColor(colour);
                    
                    x1 = (int)((x)*(Math.cos(O)) - (y)*(Math.sin(O)));
                    y1 = (int)((x)*(Math.sin(O)) + (y)*(Math.cos(O)));
                    g.fillRect (x1 + X0, y1 + Y0, 1, 1);
                    
                    x = x + Dx;
               }
               x = -Xmax;
               y = y + Dy;
          }
          
          x = -Xmax;
          y = -Ymax;
          Dy = 15;
          Dx = 15;
          g2.setColor(Color.GRAY);
          g.fillOval(X0 + (int)R, Y0, 3,3);
          g.fillOval(X0 - (int)R, Y0, 3,3);
          
          while (y < Ymax){
               while (x < Xmax){
                    Bcomponents = B.Bxyz ((double)x, (double)y, (double)z);
                    Bx = Bcomponents [0];
                    By = Bcomponents [1];
                    
                    g.setColor(Color.BLACK);
                    //g.setColor(Color.GRAY);
                    
                    bx = (int)Math.round(S1*(S2*Bx)/Math.sqrt(Bx*Bx + By*By));
                    by = (int)Math.round(S1*(S2*By)/Math.sqrt(Bx*Bx + By*By));
                    
                    x1 = (int)((x)*(Math.cos(O)) - (y)*(Math.sin(O)));
                    y1 = (int)((x)*(Math.sin(O)) + (y)*(Math.cos(O)));
                    
                    b1 = (int)((bx)*(Math.cos(O)) - (by)*(Math.sin(O)));
                    b2 = (int)((bx)*(Math.sin(O)) + (by)*(Math.cos(O)));
                    
                    g.drawLine (x1 + X0,  y1 + Y0,  x1 + X0 + bx,  y1 + Y0 + by);
                    g.fillRect (x1 + X0,  y1 + Y0,  2,  2);
                    
                    x = x + Dx;
               }
               x = -Xmax;
               y = y + Dy;
          }
     }
     
     public static void main(String[] args) {
          JFrame frame = new JFrame();
          frame.getContentPane().add(new TopDownFieldPlotter());
          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          frame.setSize(ScreenWidth, ScreenHeight);
          frame.setVisible(true);
     }
}