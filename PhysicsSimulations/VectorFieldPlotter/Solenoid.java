public class Solenoid{
    private double I;
    private double Ri;
    private double Rf;
    private double Dr;
    private double l;
    private double Dl;
    private int Nr;
    private int Nl;
    CurrentLoop [][] Windings;
    
    public Solenoid(double i, double ri, double rf, double L, int nr, int nl){
        I = i;
        Ri = ri;
        Rf = rf;
        l = L;
        Nr = nr; 
        Dr = (Rf - Ri)/Nr;
        Nl = nl;
        Dl = l/Nl;
        
        Windings = new CurrentLoop [Nr][Nl];
        
        for (int j = 0; j < Nr; j = j + 1){
            for (int k = 0; k < Nl; k = k + 1){
                Windings[j][k] = new CurrentLoop (I/(Nr*Nl), (Ri + j*Dr));
            }
        }
    }
    
    public double [] Bxyz (double r, double y, double z){
        double [] BVector = new double [3];
        double [] b = new double [2];
        BVector [0] = 0;
        BVector [1] = 0;
        BVector [2] = 0;
        
        for (int j = 0; j < Nr; j = j + 1){
            for (int k = 0; k < Nl; k = k + 1){
                //b = (Windings [j][k].GetBComponents((z - k*Dl), r));
                b = Windings[j][k].Bxyz(r, 0, (z - k*Dl));
                BVector [0] = BVector [0] + b [0];
                BVector [1] = BVector [1] + b [1];
                BVector [2] = BVector [2] + b [2];
            }
        }
        return(BVector);
    }
    
}