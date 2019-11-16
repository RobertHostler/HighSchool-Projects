public class TrueHalbach{
    private double I;
    private double R;
    CurrentLoop B1;
    CurrentLoop B2;
    CurrentLoop B3;
    
    public TrueHalbach(double i, double r){
        I = i;
        R = r;
        B1 = new CurrentLoop(I/3, R);
        B2 = new CurrentLoop(I/3, R);
        B3 = new CurrentLoop(I/3, R);
    }
    
    public double [] Bxyz(double r, double y, double z){
        double [] BVector = new double[2];
        double [] b1 = new double[2];
        double [] b2 = new double[2];
        double [] b3 = new double[2];
        
        b1 = B1.Bxyz((r + 1.75*R), z);    // b1 is above the xy - plane or the r - axis
        b2 = B2.Bxyz(z, r);
        b3 = B3.Bxyz((r - 1.75*R), z);
        
        BVector[0] = b1[1] + b2[0] - b3[1];
        BVector[1] = b1[0] + b2[1] - b3[0];
        return BVector;
    }
}