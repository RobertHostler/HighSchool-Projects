public class Particle{
    public double [] position = new double[2];
    private double [] velocity = new double[2];
    private double [] acceleration = new double[2];
    private double [] NetForce = new double[2];
    private final static double k = 8987551787.3681764;
    private final static double damping = 1.0/Math.pow(10, 15);
    private final double Charge;
    private final double Mass;
    public int [] particleColourRGB = new int[3];
    
    public Particle(double x1, double y1, double vx, double vy, double q, double M, int[] col){
        position[0] = x1;
        position[1] = y1;
        velocity[0] = vx;
        velocity[1] = vy;
        Charge = q;
        Mass = M;
        
        particleColourRGB = col;
    }
    
    public void AccumulateForce(double [] interactionForce){
        NetForce[0] += interactionForce[0];
        NetForce[1] += interactionForce[1];
    }

    public void ResetTotalForce(){
        NetForce[0] = 0.0;
        NetForce[1] = 0.0;
    }

    public void UpdateParticle(double timeDelta){
        acceleration[0] = NetForce[0]/Mass;
        acceleration[1] = NetForce[1]/Mass;

        position[0] = position[0] + timeDelta*velocity[0] + 0.5*acceleration[0]*(timeDelta*timeDelta);
        position[1] = position[1] + timeDelta*velocity[1] + 0.5*acceleration[1]*(timeDelta*timeDelta);
        
        velocity[0] = velocity[0] + timeDelta*acceleration[0];
        velocity[1] = velocity[1] + timeDelta*acceleration[1];

        this.ResetTotalForce();
    }

    public double [] CoulombForce(Particle interactingParticle){
        double x = (interactingParticle.position[0] - this.position[0]);
        double y = (interactingParticle.position[1] - this.position[1]);

        double r = Math.sqrt (x*x + y*y);
        double q1 = this.Charge;
        double q2 = interactingParticle.Charge;
        double [] F = {-k*q1*q2*x/(r*r*r + damping), -k*q1*q2*y/(r*r*r + damping)};
        return (F);
    }
}