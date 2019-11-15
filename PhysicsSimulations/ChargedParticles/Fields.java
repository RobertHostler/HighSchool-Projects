public class Fields{
    public static double [] ConstantElectricField(double [] position, double strength){
        double [] electricField = {0, strength};
        return electricField;
    }

    public static double ConstantMagneticField(double [] position, double strength){
        double magneticField = strength;
        return magneticField;
    }
}