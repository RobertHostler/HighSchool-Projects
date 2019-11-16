public class HalbachTriple{
    private double I;
    private double R;
    private double k;
    private double a;
    CurrentLoop B1;
    CurrentLoop B2;
    CurrentLoop B3;
    
    public HalbachTriple(double i, double r, double K, double A){
        I = i;
        R = r;
        k = K;
        a = A;
        B1 = new CurrentLoop (I/3, R);
        B2 = new CurrentLoop (I/3, R/a);
        B3 = new CurrentLoop (I/3, R);
    }
    
    public double [] Bxyz(double r, double y, double z){
        double [] BVector = new double [3];
        double [] b1 = new double [3];
        double [] b2 = new double [3];
        double [] b3 = new double [3];
        
        b1 = B1.Bxyz(r, 0, (z - R/k));
        b2 = B2.Bxyz(r, 0, z);
        b3 = B3.Bxyz(r, 0, (z + R/k));
        
        BVector[0] = b1 [0] + b2 [0] - b3 [0];
        BVector[1] = b1 [1] + b2 [1] - b3 [1];
        BVector[2] = b1 [2] + b2 [2] - b3 [2];
        return BVector;
    }
}