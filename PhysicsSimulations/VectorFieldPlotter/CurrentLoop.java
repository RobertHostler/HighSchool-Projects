import java.lang.Math;

public class CurrentLoop{
    private final double pi = Math.PI;
    private final double u0 = 4*pi/(Math.pow(10, 7));
    private double current;
    private double radius;
    private double mainFieldValue;
    private double Theta;
    
    public CurrentLoop(double I1, double R1) {
        current = I1;
        radius = R1;  
        mainFieldValue = u0*current/(2*radius);
    }
    
    public double [] Bxyz(double x, double y, double z){
        double [] B = new double [3];
        double Br = 0, Bz = 0;
        double r = Math.sqrt (x*x + y*y);
        double Theta = 0;
        
        if (x == 0 & y != 0){
            Theta = pi/2;
        }else{
            Theta = Math.atan2 (y, x);
        }
        
        kQ Kq = new kQ(radius);
        EIC Elliptic = new EIC();
        Kq.SetkQ(z , r);
        double k = Kq.Getk();
        double Q = Kq.GetQ();
        Elliptic.SetKkEk(k);
        double Kk = Elliptic.GetKk();
        double Ek = Elliptic.GetEk();
        
        if (z == 0){
            Br = (2*u0*current/(4*pi*Math.sqrt(Q)))*((-1*Kk) + Ek*(radius*radius + r*r + z*z)/(Q - 4*radius*r));
        } else {
            Br = ((z)/Math.abs(z))*(2*u0*current/(4*pi*Math.sqrt(Q)))*((-1*Kk) + Ek*(radius*radius + r*r + z*z)/(Q - 4*radius*r));
        }
        Bz = (2*u0*current/(4*pi*Math.sqrt(Q)))*(Kk + Ek*(radius*radius - r*r - z*z)/(Q - 4*radius*r));
        
        B [0] = Br*Math.cos (Theta);
        B [1] = Br*Math.sin (Theta);
        B [2] = Bz;
        return (B);
    }
    
    public double [] Broz (double r, double o, double z){
        double [] B = new double [3];
        double Br = 0, Bz = 0;
        
        kQ Kq = new kQ(radius);
        EIC Elliptic = new EIC();
        Kq.SetkQ(z , r);
        double k = Kq.Getk();
        double Q = Kq.GetQ();
        Elliptic.SetKkEk(k);
        double Kk = Elliptic.GetKk();
        double Ek = Elliptic.GetEk();
        
        if (z == 0){
            Br = (2*u0*current/(4*pi*Math.sqrt(Q)))*((-1*Kk) + Ek*(radius*radius + r*r + z*z)/(Q - 4*radius*r));
        } else {
            Br = ((z)/Math.abs(z))*(2*u0*current/(4*pi*Math.sqrt(Q)))*((-1*Kk) + Ek*(radius*radius + r*r + z*z)/(Q - 4*radius*r));
        }
        Bz = (2*u0*current/(4*pi*Math.sqrt(Q)))*(Kk + Ek*(radius*radius - r*r - z*z)/(Q - 4*radius*r));
        
        B [0] = Br;
        B [1] = 0;
        B [2] = Bz;
        return (B);
    }
    
    public double BrLoop (double x, double y, double z, double R1, double I1){
        double Br = 0;
        double r = Math.sqrt (x*x + y*y);
        double Theta = 0;
        
        if (x == 0 & y != 0){
            Theta = pi/2;
        }else{
            Theta = Math.atan2 (y, x);
        }
        
        kQ Kq = new kQ(R1);
        EIC Elliptic = new EIC();
        Kq.SetkQ(z , r);
        double k = Kq.Getk();
        double Q = Kq.GetQ();
        Elliptic.SetKkEk(k);
        double Kk = Elliptic.GetKk();
        double Ek = Elliptic.GetEk();
        
        if (z == 0){
            Br = (2*u0*I1/(4*pi*Math.sqrt(Q)))*((-1*Kk) + Ek*(R1*R1 + r*r + z*z)/(Q - 4*R1*r));
        } else {
            Br = ((z)/Math.abs(z))*(2*u0*I1/(4*pi*Math.sqrt(Q)))*((-1*Kk) + Ek*(R1*R1 + r*r + z*z)/(Q - 4*R1*r));
        }
        
        return (Br);
    }
    
    public double BzLoop (double x, double y, double z, double R1, double I1){
        double Bz = 0;
        double r = Math.sqrt (x*x + y*y);
        double Theta = 0;
        
        if (x == 0 & y != 0){
            Theta = pi/2;
        }else{
            Theta = Math.atan2 (y, x);
        }
        
        kQ Kq = new kQ(R1);
        EIC Elliptic = new EIC();
        Kq.SetkQ(z , r);
        double k = Kq.Getk();
        double Q = Kq.GetQ();
        Elliptic.SetKkEk(k);
        double Kk = Elliptic.GetKk();
        double Ek = Elliptic.GetEk();
        
        Bz = (2*u0*current/(4*pi*Math.sqrt(Q)))*(Kk + Ek*(radius*radius - r*r - z*z)/(Q - 4*radius*r));
        
        return (Bz);
    }
    
    public double [] OffsetCoils (CurrentLoop b, double deltaX, double h){
        double t = 0, dt = pi/1000;     //0.00005; // pi/2500 = 0.0012566, 25 times faster than if dt = 0.000 05
        double x, y, z;
        double radius = b.radius, current = b.current;
        double [] L = new double [3];
        double [] B = new double [3];
        double [] force = new double [3];
        double fx = 0, fy = 0, fz = 0;
        
        while (t <= (2*pi)){
            x = deltaX + radius * (Math.cos (t));
            y = radius * Math.sin (t);
            z = h;
            
            L [0] = -1 * radius * (Math.sin (t)) * dt;
            L [1] = radius * (Math.cos (t)) * dt;
            L [2] = 0;
            
            B = this.Bxyz (x, y, z);
            
            fx = fx + current * (L[1] * B[2] - L[2] * B[1]);
            fy = fy - current * (L[0] * B[2] - L[2] * B[0]);
            fz = fz + current * (L[0] * B[1] - L[1] * B[0]);
            
            t = t + dt;
        }
        
        force [0] = fx;
        force [1] = fy;
        force [2] = fz;
        return (force);
    }
    
    public double LoopInductance (double r0){
        CurrentLoop b = new CurrentLoop (1.0, r0);
        double Mf = 0, dMf;
        double r = 0;
        
        while (r < r0){
            dMf = (pi*r*r)*(b.BzLoop(r, 0, 0, r0, 1.0));
            Mf = Mf + dMf;
            r = r + 0.00001;
        }
        double L = Mf;
        return (L);
    }
    
    public static void main (String [] args){
        CurrentLoop b1 = new CurrentLoop (1000000, 0.005);     // b1 is the field coil
        //CurrentLoop b2 = new CurrentLoop (1000000, 0.005);     // b2 is the floating coil
        
        double x = 0.002, y = 0, z = 0.003;
        
        double [] b = b1.Bxyz (x, y, z);
        double [] a = b1.Bxyz (x + 0.001, y, z + 0.001);
        
        System.out.println ("Bz = " + b[0] + " , By = " + b[1] + " , Bz = " + b[2]);
        System.out.println ("Bz = " + a[0] + " , By = " + a[1] + " , Bz = " + a[2]);
        
        //double deltaX = 0.00, z = 0.015;
        //double [] force = b1.OffsetCoils (b2, deltaX, z);
        
        //System.out.println ("Net Fx = " + force [0]);
        //System.out.println ("Net Fy = " + force [1]);
        //System.out.println ("Net Fz = " + force [2]);
        
        //System.out.println ();
        //System.out.println ("parallel Fr = " + b1.GetForceParallelCoils (b2, z) [0]);
        //System.out.println ("parallel Fz = " + b1.GetForceParallelCoils (b2, z) [1]);
    }
}

// For a 25 mm coil:
// at 0.1 mm, the force is positive

// For a 5 mm coil:
//
// At 1 mm, f is negative
// At 2 mm, f is negative
// At 3 mm, f is negative
// At 4 mm, f is negative
// At 5 mm, f is negative
// At 6 mm, f is negative
// At 7 mm, f is negative
// At 8 mm, f is negative
// At 9 mm, f is negative
// At 10 mm, f is negative
// At 11 mm, f is negative
// At 12 mm, f is negative
// At 13 mm, f is negative
// At 14 mm, f is positive

// The point at which the force reverses direction is between 13 mm and 14 mm radial offset.

/*
The Phoenix Program - about the pacification of civilians by the CIA in Vietnam, compared with modern War on Terror
The Lords of Creation - about the consolidation of the wealthy aristocracy, i.e. J.P Morgan
Blowback - about the Operation Paperclip and how the US government tries to bury its own past actions
*/

/*
F# is just as fast as java in most cases, and sometimes faster. It is also usually faster than Haskell and sometimes
faster than C#.

C++ beats everything, and is sometimes even faster than C itself.

Go is faster than java sometimes, but for very intensive programs it can be 2 -3 times slower than java.

So, Go ~ Java ~ F# ~ C# < C ~ C++.

However, there is a functional language called Rust that is comparably fast to C and C++ - in some cases it can beat
them.
A new language called Crystal can compete with Rust and C/C++. It is faster than any of the languages above.

Rust is definitely worth a look, but C++ is more important. It can interface directly with CUDA, which is what current need.
Right now my main concern is not the ~ 2 or 3 fold reduction in runtime that C++ and Rust will both offer; the big
concern is that current have exhausted the usefulness of ordinary CPUs for computation and current need to leverage the power of
GPUs for floating - point computations. Rust will not perform considerably better than C++ on a CPU, but since current can
run C++ on a GPU, current could get at least a ~ 20 fold performance boost out of C++, and a theoretical best case scenario
of ~ 150 fold. All of that without having to purchase any extra hardware.

So while all of these languages are great, and will be good to know in the future for certain applications, C++ and CUDA
will serve me well for all of my simulations as well as any future simulations of different devices, machine learning,
etc. C++ and CUDA are what current need to focus on for now.

On the other hand, for developing the UI for my simulations, current already know Java which is used for exactly that. current know
everything current need at the moment. 

*/




/* 
#include <math.h>

double sqrt(double x) {
if (x <= 0)
    return 0;       // if negative number throw an exception?
int exp = 0;
x = frexp(x, &exp); // extract binary exponent from x
if (exp & 1) {      // we want exponent to be even
    exp--;
    x *= 2;
}
double y = (1+x)/2; // first approximation
double z = 0;
while (y != z) {    // yes, we CAN compare doubles here!
    z = y;
    y = (y + x/y) / 2;
}
return ldexp(y, exp/2); // multiply answer by 2^(exp/2)
}
*/