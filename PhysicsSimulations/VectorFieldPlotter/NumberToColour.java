import java.awt.Color;

public class NumberToColour{
    private double t0, t1, t2, t3, t4, t5, t6;
    private double k1, k2, k3, k4, k5, k6;
    
    public NumberToColour (double [] controlValues){
        t0 = controlValues[0];
        t1 = controlValues[1];
        t2 = controlValues[2];
        t3 = controlValues[3];
        t4 = controlValues[4];
        t5 = controlValues[5];
        t6 = controlValues[6];
        
        k1 = (t1 - t0)/255;
        k2 = (t2 - t1)/255;
        k3 = (t3 - t2)/255;
        k4 = (t4 - t3)/255;
        k5 = (t5 - t4)/127;
        k6 = (t6 - t5)/127;
    }
    
    public Color GetColourPredator(double T){
        int R = 0, G = 0, B = 0;
        if (T <= t0){
            R = 0;                           // Black
            G = 0;
            B = 0;
        }else if (T > t0 & T <= t1){
            R = 0;                           // Black to full Blue
            G = 0;
            B = (int)((T - t0)/k1);
        }else if (T > t1 & T <= t2){
            R = 0;                           // Full Blue to Aqua
            G = (int)((T - t1)/k2);
            B = 255;
        }else if (T > t2 & T <= t3){
            R = 0;                           // Aqua to full Green
            G = 255;
            B = 255 - (int)((T - t2)/k3);
        }else if (T > t3 & T <= t4){
            R = (int)((T - t3)/k4);          // Full Green to Yellow
            G = 255;
            B = 0;
        }else if (T > t4 & T <= t5){
            R = 255;                         // Yellow to Orange
            G = 255 - (int)((T - t4)/k5);
            B = 0;
        }else if (T > t5 & T <= t6){
            R = 255;                         // Orange to Red
            G = 127 - (int)((T - t5)/k6);
            B = 0;
        }else if (T > t6){
            R = 255;
            G = 0;
            B = 0;
        }

        Color predatorColour = new Color(R, G, B);
        return predatorColour;
    }
    
    public Color GetColourLogarithm(double T){
        double t = Math.log10(T);
        double tm = Math.abs(t);
        int R = 0, G = 0, B = 0;
        if (t <= -4){
            R = 0;                           // Black
            G = 0;
            B = 0;
        }else if (t > -4 & t <= -3){
            R = 0;                           // Black to full Blue
            G = 0;
            B = (int)(255*(4 + t));
        }else if (t > -3 & t <= -2){
            R = 0;                           // Full Blue to Aqua
            G = (int) (255*(3 + t));
            B = 255;
        }else if (t > -2 & t <= -1){
            R = 0;                           // Aqua to full Green
            G = 255;
            B = 255 - (int)(255*(2 + t));
        }else if (t > -1 & t <= 0){
            R = (int)(255*(1 + t));          // Full Green to Yellow
            G = 255;
            B = 0;
        }else if (t > 0 & t <= 1){
            R = 255;                         // Yellow to Orange
            G = 255 - (int)(128*(0 + t));
            B = 0;
        }else if (t > 1 & t <= 2){
            R = 255;                         // Orange to Red
            G = 127 - (int)(127*(-1 + t));
            B = 0;
        }else if (t > 2){
            R = 255;
            G = 0;
            B = 0;
        }
        
        Color logarithmicColour = new Color(R, G, B);
        return logarithmicColour;
    }
}