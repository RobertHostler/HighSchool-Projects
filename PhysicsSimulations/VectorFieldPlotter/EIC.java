import java.lang.Math;

public class EIC {
     
     private double Ek;
     private double Kk;
     private final double pi = Math.PI;
     
     public EIC () {
          Ek = 0;
          Kk = 0;
     }
     
     public void SetKkEk(double k) {
          double c;
          c = 0;
          double a0, g0, a1, g1;
          a0 = 1;
          g0 = Math.sqrt(1 - (k * k));
          a1 = a0;
          g1 = g0;
          c = c + (a0 * a0 - g0 * g0);
          c = c + 2 * ((a0 * a0 - 2 * a0 * g0 + g0 * g0)/4);
          for (int i = 0; i <= 1; i = i + 1){
               a1 = 0.5 * (a0 + g0);
               g1 = Math.sqrt(a0 * g0);
               a0 = a1;
               g0 = g1;
               /*
               if (i >= 2){
                    c = c + (Math.pow(2 , i))*((a1 - g1)*(a1 - g1)/4);
               }
               */
          }
          Kk = pi / (a1 + g1);          
          Ek = Kk * (1 - 0.5 * c);
     } 
     
     public double GetKk(){
          return(Kk);
     }
     
     public double GetEk(){
          return(Ek);
     }
     
}