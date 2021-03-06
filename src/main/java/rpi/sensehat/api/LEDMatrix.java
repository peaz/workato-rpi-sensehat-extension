package rpi.sensehat.api;

import rpi.sensehat.api.dto.Color;
import rpi.sensehat.api.dto.Rotation;
import rpi.sensehat.connector.Command;
import rpi.sensehat.utils.PythonUtils;

/**
 * Created by jcincera on 20/06/2017.
 * Updated by knyc on 02/08/2021 to include LED rotation and low light settings in the APIs as this needs to be set in each Python code execution.
 * Removed Rotation and LowLight API
 */
public class LEDMatrix extends APIBase {

    public final String lowLight;
    public final String rotation;

    public LEDMatrix(String lowLight, String rotation) {
        this.lowLight = lowLight;
        this.rotation = rotation;
    }

    /**
     * Set all pixels on display
     *
     * @param pixels array of 64 pixels which represents display 8x8 points
     */
    public void setPixels(Color[] pixels) {
        if (pixels == null || pixels.length != 64) {
            throw new IllegalArgumentException("Array must have 64 items -> 8x8 points!");
        }

        StringBuilder matrix = new StringBuilder();

        for (Color pixel : pixels) {
            matrix.append("[");
            matrix.append(pixel.r());
            matrix.append(", ");
            matrix.append(pixel.g());
            matrix.append(", ");
            matrix.append(pixel.b());
            matrix.append("],");
        }

        matrix.deleteCharAt(matrix.length() - 1); // remove last ","
        executeLED(lowLight, rotation, Command.SET_PIXELS, matrix.toString()).checkEmpty();
    }

    /**
     * Set specific pixel
     *
     * @param x     index 0-7
     * @param y     index 0-7
     * @param color color
     */
    public void setPixel(Integer x, Integer y, Color color) {
        executeLED(lowLight, rotation, Command.SET_PIXEL,
                String.valueOf(x), String.valueOf(y),
                color.r(), color.g(), color.b()).checkEmpty();
    }

    /**
     * Clear display (blank/off)
     */
    public void clear() {
        execute(Command.CLEAR).checkEmpty();
    }

    /**
     * Clear display - set all pixels to specific color
     *
     * @param color color
     */
    public void clear(Color color) {
        executeLED(lowLight, rotation, Command.CLEAR_WITH_COLOR, color.r(), color.g(), color.b()).checkEmpty();
    }

    /**
     * Show message
     *
     * @param message message
     */
    public void showMessage(String message) {
        if (message.contains("'")) {
            throw new IllegalArgumentException("Character: > ' < is not supported in message!");
        }

        executeLED(lowLight, rotation, Command.SHOW_MESSAGE, message).checkEmpty();
    }

    /**
     * Show message with specific parameters
     *
     * @param message     message
     * @param scrollSpeed speed
     * @param textColor   text color
     * @param backColor   background color
     */
    public void showMessage(String message, Float scrollSpeed, Color textColor, Color backColor) {
        if (message.contains("'")) {
            throw new IllegalArgumentException("Character: > ' < is not supported in message!");
        }

        executeLED(lowLight, rotation, Command.SHOW_MESSAGE_PARAMETRIZED,
                message,
                String.format("%.2f", scrollSpeed),
                textColor.r(), textColor.g(), textColor.b(),
                backColor.r(), backColor.g(), backColor.b()).checkEmpty();
    }

    /**
     * Wait for some message or event
     *
     * @param seconds seconds
     */
    public void waitFor(Integer seconds) {
        try {
            Thread.sleep(seconds * 1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Show letter
     *
     * @param letter letter
     */
    public void showLetter(String letter) {
        if (letter.contains("'")) {
            throw new IllegalArgumentException("Letter: > ' < is not supported!");
        }
        if (letter.length() != 1) {
            throw new IllegalArgumentException("Only one letter is supported!");
        }

        executeLED(lowLight, rotation, Command.SHOW_LETTER, letter).checkEmpty();
    }

    /**
     * Show letter with specific parameters
     *
     * @param letter      letter
     * @param letterColor letter color
     * @param backColor   background color
     */
    public void showLetter(String letter, Color letterColor, Color backColor) {
        executeLED(lowLight, rotation, Command.SHOW_LETTER_PARAMETRIZED,
                letter,
                letterColor.r(), letterColor.g(), letterColor.b(),
                backColor.r(), backColor.g(), backColor.b()).checkEmpty();
    }

    public void gamma() {
        throw new UnsupportedOperationException("Not supported yet");
    }

    public void gammaReset() {
        throw new UnsupportedOperationException("Not supported yet");
    }

    public void loadImage() {
        throw new UnsupportedOperationException("Not supported yet");
    }

    public void flipHorizontally() {
        throw new UnsupportedOperationException("Not supported yet");
    }

    public void flipVertically() {
        throw new UnsupportedOperationException("Not supported yet");
    }

    public void getPixels() {
        throw new UnsupportedOperationException("Not supported yet");
    }

    public void getPixel() {
        throw new UnsupportedOperationException("Not supported yet");
    }
}
