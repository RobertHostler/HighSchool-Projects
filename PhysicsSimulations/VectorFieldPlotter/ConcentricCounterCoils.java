public class ConcentricCounterCoils{
    private double Ii, Io, Ri, Ro;
    CurrentLoop b1;
    CurrentLoop b2;
    
    public ConcentricCounterCoils(double i1, double r1, double i2, double r2){
        Ii = i1;
        Io = i2;
        Ri = r1;
        Ro = r2;
        
        b1 = new CurrentLoop(Ii, Ri);
        b2 = new CurrentLoop(Io, Ro);
    }
    
    public double [] Bxyz(double r, double y, double z){
        double [] BVector = new double [3];
        
        double [] c1 = b1.Bxyz(r, 0, z);
        double [] c2 = b2.Bxyz(r, 0, z);
        
        BVector [0] = c1[0] - c2[0];
        BVector [1] = c1[1] - c2[1];
        BVector [2] = c1[2] - c2[2];
        
        return BVector;
    }
}