package com.example.aps;

public abstract class Temperature
{
    // Fahrenheit to Celsius
    private static double fromFtoC(double value) { return (value - 32) * 5 / 9; }

    // Fahrenheit to Kelvin
    private static double fromFtoK(double value) { return fromFtoC(value) + 273.15; }

    // Celsius to Kelvin
    private static double fromCtoK(double value) { return value + 273.15; }

    // Celsius to Fahrenheit
    private static double fromCtoF(double value) { return value * 1.8 + 32; }

    // Kelvin to Celsius
    private static double fromKtoC(double value) { return value - 273.15; }

    // Kelvin to Fahrenheit
    private static double fromKtoF(double value) { return (value - 273.15) * 1.8 + 32; }

    public static double[] calcTemperature(TemperatureType temperature,double value) {
        double[] temperatures = new double[2];

        switch(temperature) {
            case CELSIUS:
                temperatures[0] = fromCtoF(value);
                temperatures[1] = fromCtoK(value);
                return temperatures;
            case FAHRENHEIT:
                temperatures[0] = fromFtoC(value);
                temperatures[1] = fromFtoK(value);
                return temperatures;
            case KELVIN:
                temperatures[0] = fromKtoC(value);
                temperatures[1] = fromKtoF(value);
                return temperatures;
            default:
                return temperatures;
        }
    }
}
