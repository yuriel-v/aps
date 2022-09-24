package com.example.aps;

public abstract class Temperature
{
    // Fahrenheit to Celsius
    public static double fromFtoC(double value) {
        return (value - 32) * 5 / 9;
    }

    // Fahrenheit to Kelvin
    public static double fromFtoK(double value) {
        return fromFtoC(value) + 273.15;
    }

    // Celsius to Kelvin
    public static double fromCtoK(double value) {
        return value + 273.15;
    }

    // Celsius to Fahrenheit
    public static double fromCtoF(double value) {
        return value * 1.8 + 32;
    }

    // Kelvin to Celsius
    public static double fromKtoC(double value) {
        return value - 273.15;
    }

    // Kelvin to Fahrenheit
    public static double fromKtoF(double value) {
        return (value - 273.15) * 1.8 + 32;
    }
}
