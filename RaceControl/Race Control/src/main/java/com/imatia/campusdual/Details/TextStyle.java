package com.imatia.campusdual.Details;

public class TextStyle {
    public static String bold(String s){
        return "\u001B[1m" + s + "\u001B[0m";
    }

    public static String underline(String s){
        return "\033[4m" + s + "\033[0m";
    }

    public static String golden(String s){
        return "\033[38;5;178m" + s + "\u001B[0m";
    }

    public static String silver(String s){
        return "\033[38;5;250m" + s + "\u001B[0m";
    }

    public static String bronze(String s){
        return "\033[38;5;136m" + s + "\u001B[0m";
    }
}
