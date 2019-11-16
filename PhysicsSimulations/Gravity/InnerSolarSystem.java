import java.awt.*;
import javax.swing.*;
import java.net.URL;
import java.util.Random;

public class InnerSolarSystem extends JFrame{
    private static final int CANVAS_WIDTH = 1280;
    private static final int CANVAS_HEIGHT = 720;
    private static int UPDATE_INTERVAL = 1;
    private DrawCanvas canvas;    
    final int xCenter = CANVAS_WIDTH/2;
    final int yCenter = CANVAS_HEIGHT/2;
    final double Scale = Math.pow(10, -9);

    final int NumBodies = 5;
    CelestialBody [] bodies = new CelestialBody [NumBodies];
    double time = 0.0;
    int numDays = 0;
    final double timeDelta = 300;
    
    public InnerSolarSystem(){
        canvas = new DrawCanvas();
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        this.setContentPane(canvas);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setTitle("Field Animations");
        this.setVisible(true);

        // Instantiate the planets:
        Random random = new Random();
        double theta;
        int [] colour = new int[3];

        // The Sun
        theta = 2*Math.PI*random.nextDouble();
        colour = new int[]{255, 200, 0};
        CelestialBody TheSun = new CelestialBody(
            0.0,
            0.0,
            0.0,
            0.0,
            1989000*Math.pow(10, 24), 
            10,
            colour
        );
        bodies[0] = TheSun;

        // Mercury
        theta = 2*Math.PI*random.nextDouble();
        colour = new int[]{127, 127, 127};
        CelestialBody Mercury = new CelestialBody(
            57.9*Math.pow(10, 9)*Math.cos(theta),
            57.9*Math.pow(10, 9)*Math.sin(theta),
            -47.4*Math.pow(10, 3)*Math.sin(theta),
            47.4*Math.pow(10, 3)*Math.cos(theta),
            0.330*Math.pow(10, 24),
            5,
            colour
        );
        bodies[1] = Mercury;

        // Venus
        theta = 2*Math.PI*random.nextDouble();
        colour = new int[]{0, 200, 255};
        CelestialBody Venus = new CelestialBody(
            108.2*Math.pow(10, 9)*Math.cos(theta),
            108.2*Math.pow(10, 9)*Math.sin(theta),
            -35.0*Math.pow(10, 3)*Math.sin(theta),
            35.0*Math.pow(10, 3)*Math.cos(theta),
            4.87*Math.pow(10, 24),
            5,
            colour
        );
        bodies[2] = Venus;

        // Earth
        theta = 2*Math.PI*random.nextDouble();
        colour = new int[]{0, 127, 255};
        CelestialBody Earth = new CelestialBody(
            149.6*Math.pow(10, 9)*Math.cos(theta),
            149.6*Math.pow(10, 9)*Math.sin(theta),
            -29.8*Math.pow(10, 3)*Math.sin(theta),
            29.8*Math.pow(10, 3)*Math.cos(theta),
            5.97*Math.pow(10, 24),
            5,
            colour
        );
        bodies[3] = Earth;

        // Mars
        theta = 2*Math.PI*random.nextDouble();
        colour = new int[]{255, 64, 0};
        CelestialBody Mars = new CelestialBody(
            227.9*Math.pow(10, 9)*Math.cos(theta),
            227.9*Math.pow(10, 9)*Math.sin(theta),
            -24.1*Math.pow(10, 3)*Math.sin(theta),
            24.1*Math.pow(10, 3)*Math.cos(theta),
            0.642*Math.pow(10, 24),
            5,
            colour
        );
        bodies[4] =  Mars;

        Thread updateThread = new Thread(){
            @Override
            public void run(){
                while (true){
                    update();
                    repaint();
                    try {
                        Thread.sleep(UPDATE_INTERVAL);
                    } catch (InterruptedException ignore){}
                }
            }
        };
        updateThread.start();
    }
    
    public void update() {
        for (int i = 0; i < NumBodies; i++){
            for (int j = i + 1; j < NumBodies; j++){
                double [] gravitationalForce = new double[2];

                gravitationalForce = bodies[i].GravitationalForce(bodies[j]);
                bodies[i].AccumulateForce(gravitationalForce);

                gravitationalForce[0] = -gravitationalForce[0];
                gravitationalForce[1] = -gravitationalForce[1];
                bodies[j].AccumulateForce(gravitationalForce);
            }
        }
        for (int i = 0; i < NumBodies; i++){
            bodies[i].UpdatePlanet(timeDelta);
        }

        time = time + timeDelta;
        if (time % 86400 == 0){
            numDays++;
        }
    }

    public void DrawBody(CelestialBody body, Graphics2D graphicsContext){
        int X = (int)(Scale*body.position[0]) + xCenter;
        int Y = (int)(Scale*body.position[1]) + yCenter;
        Color colour = new Color(body.planetColourRGB[0], body.planetColourRGB[1], body.planetColourRGB[2]);
        graphicsContext.setColor(colour);
        graphicsContext.fillOval(X, Y, body.getRadius(), body.getRadius());
    }

    class DrawCanvas extends JPanel {
        @Override
        public void paintComponent(Graphics g){
            Graphics2D g2 = (Graphics2D) g;
            super.paintComponent(g);
            setBackground(Color.BLACK);
            g.setColor(Color.WHITE);
            g.setColor(Color.GRAY);
            g.drawString("Days Elapsed: " + numDays, 10, CANVAS_HEIGHT);
            g2.translate(0, CANVAS_HEIGHT);
            g2.scale(1.0, -1.0);
            
            for (int i = 0; i < NumBodies; i = i + 1){
                DrawBody(bodies[i], g2);
            }
        }
    }
    
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                new InnerSolarSystem();
            }
        });
    }
}