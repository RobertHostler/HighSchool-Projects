public class HelmholtzCoil{
    private double R;
    private double I;
    CurrentLoop topCoil;
    CurrentLoop bottomCoil;
    
    public HelmholtzCoil(double i, double r){
        I = i;
        R = r;
        topCoil = new CurrentLoop (I/2, R);
        bottomCoil = new CurrentLoop (I/2, R);
    }
    
    public double [] Bxyz(double r, double y, double z){
        double [] BVector = new double [3];
        double [] b1 = new double [3];
        double [] b2 = new double [3];
        
        b1 = topCoil.Bxyz(r, 0, (z - R/2));
        b2 = bottomCoil.Bxyz(r, 0, (z + R/2));
        
        BVector[0] = b1[0] + b2[0];
        BVector[1] = b1[1] + b2[1];
        BVector[2] = b1[2] + b2[2];
        return BVector;
    }
}