public class SpiralCoil{
    private double I;
    private double Ri;
    private double Rf;
    private double Dr;
    private int N;
    CurrentLoop [] Windings;
    
    public SpiralCoil(double i, double ri, double rf, int n){
        I = i;
        Ri = ri;
        Rf = rf;
        N = n; 
        Dr = (Rf - Ri)/N;
        Windings = new CurrentLoop [N];
        
        // Note that here, the current I is the total amount of Ampere - Turns in the whole coil, not the actual
        // current in the Windings.
        
        for (int j = 0; j < N; j = j + 1){
            Windings [j] = new CurrentLoop (I/N, (Ri + j*Dr));
        }
    }
    
    public double [] Bxyz(double r, double y, double z){
        double [] BVector = new double [3];
        double [] b = new double [3];
        
        BVector[0] = 0;
        BVector[1] = 0;
        BVector[2] = 0;
        
        for (int j = 0; j < N; j = j + 1){
            b = (Windings [j].Bxyz(r, 0, z));
            BVector[0] = BVector[0] + b [0];
            BVector[1] = BVector[1] + b [1];
            BVector[2] = BVector[2] + b[2];
        }
        return(BVector);
    }
}