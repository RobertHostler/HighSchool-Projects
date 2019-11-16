import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FieldPlotter extends JPanel{
    final static int ScreenWidth = 1280, ScreenHeight = 720;
    final int maxHeight = 600, maxWidth = 600;
    final int xCenter = ScreenWidth/2, yCenter = ScreenHeight/2;
    final int Dy = 30, Dx = 30;

    double current = 500000.0;
    double coilRadius = 50;
    //CurrentLoop FieldCoil = new CurrentLoop(current, coilRadius);
    //SpiralCoil FieldCoil = new SpiralCoil (current, coilRadius/100, 2*coilRadius, 100);
    //Solenoid FieldCoil = new Solenoid(current, coilRadius, coilRadius, 5*coilRadius, 1, 50);
    //HelmholtzCoil FieldCoil = new HelmholtzCoil(current, coilRadius);
    //HalbachTriple FieldCoil = new HalbachTriple (current, coilRadius, 4, 1.5);
    //TrueHalbach FieldCoil = new TrueHalbach (current, coilRadius);   //Problematic for the time
    ConcentricCounterCoils FieldCoil = new ConcentricCounterCoils(current, coilRadius, current, 1.5*coilRadius);

    double T0 = 0.001, T1 = 2, T2 = 7, T3 = 25;
    double [] controlValues = {T0, (T0 + T1)/2, T1, (T1 + T2)/2, T2, (T2 + T3)/2, T3};
    NumberToColour colourMap = new NumberToColour(controlValues);
    Color coordinateColour = new Color(0, 0, 0);
    Color vectorColour = new Color(180, 180, 180, 128);
    int scaleUnitVector = 10;

    public FieldPlotter(){
    }
    
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.fillRect (0, 0, ScreenWidth, ScreenHeight);
        g2.translate(0, ScreenHeight - 40);
        g2.scale(1.0, -1.0);
        
        graphVectorField(g);
        
        g2.setColor(Color.GRAY);
        g.fillOval(xCenter + (int)coilRadius, yCenter, 3,3);
        g.fillOval(xCenter - (int)coilRadius, yCenter, 3,3);
    }

    public void graphVectorField(Graphics graphicsContext){
        double x = -maxWidth, y = -maxHeight;
        int xTransform, yTransform;
        double theta = 0;
        
        double fieldX = 0, fieldY = 0, fieldStrength = 0;
        double [] fieldComponents;
        int bx, by;

        while (y < maxHeight){
            while (x < maxWidth){
                fieldComponents = FieldCoil.Bxyz(x, 0, y);
                fieldX = fieldComponents[0];
                fieldY = fieldComponents[2];
                fieldStrength = Math.sqrt(fieldX*fieldX + fieldY*fieldY);

                xTransform = (int)(x*Math.cos(theta) - y*Math.sin(theta));
                yTransform = (int)(x*Math.sin(theta) + y*Math.cos(theta));

                coordinateColour = colourMap.GetColourLogarithm(fieldStrength);
                graphicsContext.setColor(coordinateColour);
                graphicsContext.fillRect (xTransform + xCenter, yTransform + yCenter, 1, 1);

                if (x % Dx == 0 && y % Dy == 0){
                    bx = (int)(scaleUnitVector*fieldX/fieldStrength);
                    by = (int)(scaleUnitVector*fieldY/fieldStrength);
                    
                    graphicsContext.setColor(vectorColour);
                    graphicsContext.drawLine(xTransform + xCenter, yTransform + yCenter, xTransform + xCenter + bx, yTransform + yCenter + by);
                    graphicsContext.fillOval(xTransform + xCenter, yTransform + yCenter, 3, 3);
                }
                
                x++;
            }
            x = -maxWidth;
            y++;
        }
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.getContentPane().add(new FieldPlotter());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(ScreenWidth, ScreenHeight);
        frame.setVisible(true);
    }
}