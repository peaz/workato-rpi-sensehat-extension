package rpi.sensehat.api.dto;

/**
 * Created by jcincera on 04/07/2017.
 * Modified by knyc on 07/24/2021 to add a standard color name to Color converter 
 */
public class Color {

    private String r;
    private String g;
    private String b;

    public static Color RED = Color.of(255, 0, 0);
    public static Color GREEN = Color.of(0, 255, 0);
    public static Color BLUE = Color.of(0, 0, 255);
    public static Color BLACK = Color.of(0, 0, 0);
    public static Color WHITE = Color.of(255, 255, 255);
    public static Color YELLOW = Color.of(255, 255, 0);
    public static Color PURPLE = Color.of(127, 0, 255);
    public static Color ORANGE = Color.of(255, 128, 0);
    public static Color CYAN = Color.of(0, 255, 255);
    public static Color PINK = Color.of(255, 0, 255);

    private Color(Integer r, Integer g, Integer b) {
        this.r = String.valueOf(r);
        this.g = String.valueOf(g);
        this.b = String.valueOf(b);
    }

    public static Color of(Integer r, Integer g, Integer b) {
        return new Color(r, g, b);
    }

    public String r() {
        return r;
    }

    public String g() {
        return g;
    }

    public String b() {
        return b;
    }

    public static Color get(String name) {
        switch (name.toUpperCase()) {
            case "RED":
                return RED;                
            case "GREEN":
                return GREEN;                
            case "BLUE":
                return BLUE;                
            case "BLACK":
                return BLACK;                
            case "WHITE":
                return WHITE;                
            case "YELLOW":
                return YELLOW;                
            case "ORANGE":
                return ORANGE;                
            case "PURPLE":
                return PURPLE;                
            case "CYAN":
                return CYAN;                
            case "PINK":
                return PINK;               
            default:
                return WHITE;
        }
    }
}
