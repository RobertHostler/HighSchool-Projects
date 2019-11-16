public class CelestialBody{
    private final static double G = 6.666667/Math.pow(10, 11);
    public double [] position = new double[2];
    private double [] velocity = new double[2];
    private double [] acceleration = new double[2];
    private double [] NetForce = new double[2];
    private final double Mass;
    private final int Radius;
    public int [] planetColourRGB = new int[3];
    
    public CelestialBody(double x1, double y1, double vx, double vy, double M, int R, int[] col){
        position[0] = x1;
        position[1] = y1;
        velocity[0] = vx;
        velocity[1] = vy;
        Mass = M;
        Radius = R;
        planetColourRGB = col;
    }

    public int getRadius(){
        return this.Radius;
    }
    
    public void AccumulateForce(double [] interactionForce){
        NetForce[0] += interactionForce[0];
        NetForce[1] += interactionForce[1];
    }

    public void ResetTotalForce(){
        NetForce[0] = 0.0;
        NetForce[1] = 0.0;
    }

    public void UpdatePlanet(double timeDelta){
        acceleration[0] = NetForce[0]/Mass;
        acceleration[1] = NetForce[1]/Mass;

        position[0] = position[0] + timeDelta*velocity[0] + 0.5*acceleration[0]*(timeDelta*timeDelta);
        position[1] = position[1] + timeDelta*velocity[1] + 0.5*acceleration[1]*(timeDelta*timeDelta);
        
        velocity[0] = velocity[0] + timeDelta*acceleration[0];
        velocity[1] = velocity[1] + timeDelta*acceleration[1];

        this.ResetTotalForce();
    }

    public double [] GravitationalForce(CelestialBody otherPlanet){
        double x = (otherPlanet.position[0] - this.position[0]);
        double y = (otherPlanet.position[1] - this.position[1]);

        double r = Math.sqrt (x*x + y*y);
        double m1 = this.Mass;
        double m2 = otherPlanet.Mass;
        double [] F = {-G*m1*m2*x/(r*r*r), -G*m1*m2*y/(r*r*r)};
        return F;
    }
}