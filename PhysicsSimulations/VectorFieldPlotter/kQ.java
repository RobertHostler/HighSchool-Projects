import java.lang.Math;

public class kQ {
     private double R;
     private double k;
     private double Q;

     public kQ(double newR) {
          R = newR;
          k = 0;
          Q = 0;
     }

     // We could alter this class so that it just returns k and Q as the components of a vector, rather than
     // using a void method to set their values and then using two more functions to return them. It might not
     // affect performance very much, but it makes the code cleaner.
     
     // The new method GetkQ does exactly this. It is ready to be implemented.
     
     public void SetkQ(double z, double r){
          r = Math.abs(r);
          Q = ((R + r) * (R + r)) + ((z) * (z));
          k = Math.sqrt(4 * R * r / Q);
     }
     
     public double Getk(){
          return(k);
     }
     
     public double GetQ(){
          return(Q);
     }
     
     public double [] GetkQ (double z, double r){
          r = Math.abs(r);
          Q = ((R + r) * (R + r)) + ((z) * (z));
          k = Math.sqrt(4 * R * r / Q);
          
          double [] kQPair = {k, Q};
          return(kQPair);
     }
     
     public static double [] FindkQ (double z, double r, double R){
          r = Math.abs(r);
          double Q = ((R + r) * (R + r)) + ((z) * (z));
          double k = Math.sqrt(4 * R * r / Q);
          
          double [] kQPair = {k, Q};
          return(kQPair);
     }
     
     public static void main (String [] args){
          double [] a = kQ.FindkQ (0.01, 0.0045, 0.025);
          System.out.println ("k = " + a[0] + " and Q = " + a[1] + ".");
     }
}