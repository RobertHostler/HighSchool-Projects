import java.awt.*;
import javax.swing.*;
import java.net.URL;
import java.util.Random;

public class CoulombSim extends JFrame{
    private static final int CANVAS_WIDTH = 1280;
    private static final int CANVAS_HEIGHT = 720;
    private static int UPDATE_INTERVAL = 16;
    private DrawCanvas canvas;    
    final int xCenter = CANVAS_WIDTH/2;
    final int yCenter = CANVAS_HEIGHT/2;
    final double Scale = 15.0*Math.pow(10, 2);

    final int NumParticles = 2000;
    Particle [] particles = new Particle [NumParticles];
    double time = 0.0;
    final double timeDelta = 1.0/Math.pow(10, 9);
    double electricFieldStrength = 0.0001;
    double magneticFieldStrength = 0.1;
    
    public CoulombSim(){
        canvas = new DrawCanvas();
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        this.setContentPane(canvas);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setTitle("Field Animations");
        this.setVisible(true);

        CircularDistribution();
        //RingDistribution();
        //LineDistribution();
        //SDistribution();
        //SpiralDistribution();

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
        for (int i = 0; i < NumParticles; i++){
            for (int j = i + 1; j < NumParticles; j++){
                double [] coulombInteraction = new double[2];

                coulombInteraction = particles[i].CoulombForce(particles[j]);
                particles[i].AccumulateForce(coulombInteraction);

                coulombInteraction[0] = -coulombInteraction[0];
                coulombInteraction[1] = -coulombInteraction[1];
                particles[j].AccumulateForce(coulombInteraction);
            }
            double [] electricField = Fields.ConstantElectricField(particles[i].getPosition(), electricFieldStrength);
            double [] electricForce = particles[i].ElectricForce(electricField);

            double magneticField = Fields.ConstantMagneticField(particles[i].getPosition(), electricFieldStrength);
            double [] magneticForce = particles[i].MagneticForce(magneticField);

            particles[i].AccumulateForce(electricForce);
            particles[i].AccumulateForce(magneticForce);
        }
        for (int i = 0; i < NumParticles; i++){
            particles[i].UpdateParticle(timeDelta);
        }

        time = time + timeDelta;
    }

    public void DrawParticle(Particle particle, Graphics2D graphicsContext){
        int X = (int)(Scale*particle.position[0]) + xCenter;
        int Y = (int)(Scale*particle.position[1]) + yCenter;
        Color colour = new Color(particle.particleColourRGB[0], particle.particleColourRGB[1], particle.particleColourRGB[2]);
        graphicsContext.setColor(colour);
        graphicsContext.fillOval(X, Y, 3, 3);
    }

    public void CircularDistribution(){
        Random random = new Random();
        final double Q = 1.60217662/Math.pow(10, 19);
        final double M = 9.11/Math.pow(10, 31);
        double vMax = 500000;
        int r = 0, g = 0, b = 0;
        double m, q;

        for (int i = 0; i < NumParticles; i++){
            if (i % 2 == 0){
                r = 255; g = 0; b = 0;
                m = 1836.0*M;
                q = Q;
            } else {
                r = 0; g = 127; b = 255;
                m = M;
                q = -Q;
            }
            double vx = 2*vMax*(random.nextDouble() - 0.5);
            double vy = 2*vMax*(random.nextDouble() - 0.5);
            double Radius = 0.1;
            double randX = 2*Radius*(random.nextDouble() - 0.5);
            double randY = 2*Math.sqrt(Radius*Radius - randX*randX)*(random.nextDouble() - 0.5);
            int [] colour = {r, g, b};
            particles[i] = new Particle(randX, randY, vx, vy, q, m, colour);
        }
    }

    public void RingDistribution(){
        Random random = new Random();
        final double Q = 1.60217662/Math.pow(10, 19);
        final double M = 9.11/Math.pow(10, 31);
        double vMax = 500000;
        int r = 0, g = 0, b = 0;
        double m, q;
        double Radius;

        for (int i = 0; i < NumParticles; i++){
            if (i % 2 == 0){
                r = 255; g = 0; b = 0;
                m = 1836.0*M;
                q = Q;
                Radius = 0.1;
            } else {
                r = 0; g = 127; b = 255;
                m = M;
                q = -Q;
                Radius = 0.05;
            }
            double vx = 2*vMax*(random.nextDouble() - 0.5);
            double vy = 2*vMax*(random.nextDouble() - 0.5);
            double randX = Radius*Math.cos(2*Math.PI*i/NumParticles);
            double randY = Radius*Math.sin(2*Math.PI*i/NumParticles);
            int [] colour = {r, g, b};
            particles[i] = new Particle(randX, randY, vx, vy, q, m, colour);
        }
    }

    public void LineDistribution(){
        Random random = new Random();
        final double Q = 1.60217662/Math.pow(10, 19);
        final double M = 9.11/Math.pow(10, 31);
        double vMax = 500000;
        int r = 0, g = 0, b = 0;
        double m, q;

        for (int i = 0; i < NumParticles; i++){
            if (i % 2 == 0){
                r = 255; g = 0; b = 0;
                m = 1836.0*M;
                q = Q;
            } else {
                r = 0; g = 127; b = 255;
                m = M;
                q = -Q;
            }
            double vx = 2*vMax*(random.nextDouble() - 0.5);
            double vy = 2*vMax*(random.nextDouble() - 0.5);
            double Radius = 0.1;
            double rand = 2*(random.nextDouble() - 0.5);
            double randX = rand*Radius;
            double randY = rand*Radius;
            int [] colour = {r, g, b};
            particles[i] = new Particle(randX, randY, vx, vy, q, m, colour);
        }
    }

    public void SDistribution(){
        Random random = new Random();
        final double Q = 1.60217662/Math.pow(10, 19);
        final double M = 9.11/Math.pow(10, 31);
        double vMax = 500000;
        int r = 0, g = 0, b = 0;
        double m, q;

        for (int i = 0; i < NumParticles; i++){
            if (i % 2 == 0){
                r = 255; g = 0; b = 0;
                m = 1836.0*M;
                q = Q;
            } else {
                r = 0; g = 127; b = 255;
                m = M;
                q = -Q;
            }
            double vx = 2*vMax*(random.nextDouble() - 0.5);
            double vy = 2*vMax*(random.nextDouble() - 0.5);
            double Radius = 0.1;
            double rand = 2*(random.nextDouble() - 0.5);
            double randX = rand*Radius;
            double randY = rand*Math.sqrt(Radius*Radius - randX*randX);
            int [] colour = {r, g, b};
            particles[i] = new Particle(randX, randY, vx, vy, q, m, colour);
        }
    }

    public void SpiralDistribution(){
        Random random = new Random();
        final double Q = 1.60217662/Math.pow(10, 19);
        final double M = 9.11/Math.pow(10, 31);
        double vMax = 500000;
        int r = 0, g = 0, b = 0;
        double m, q;
        double Radius;

        for (int i = 0; i < NumParticles; i++){
            if (i % 2 == 0){
                r = 255; g = 0; b = 0;
                m = 1836.0*M;
                q = Q;
                Radius = 0.2;
            } else {
                r = 0; g = 127; b = 255;
                m = M;
                q = -Q;
                Radius = 0.1;
            }
            double vx = 2*vMax*(random.nextDouble() - 0.5);
            double vy = 2*vMax*(random.nextDouble() - 0.5);
            double randX = Radius*((double)i/NumParticles)*Math.cos(Math.PI*i/NumParticles);
            double randY = Radius*((double)i/NumParticles)*Math.sin(Math.PI*i/NumParticles);
            int [] colour = {r, g, b};
            particles[i] = new Particle(randX, randY, vx, vy, q, m, colour);
        }
    }

    class DrawCanvas extends JPanel {
        @Override
        public void paintComponent(Graphics g){
            Graphics2D g2 = (Graphics2D) g;
            super.paintComponent(g);
            setBackground(Color.BLACK);
            g.setColor(Color.WHITE);
            g.setColor(Color.GRAY);
            g2.translate(0, CANVAS_HEIGHT);
            g2.scale(1.0, -1.0);
            
            for (int i = 0; i < NumParticles; i = i + 1){
                DrawParticle(particles[i], g2);
            }
        }
    }
    
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                new CoulombSim();
            }
        });
    }
}