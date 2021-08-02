package rpi.sensehat.api;

/**
 * Created by jcincera on 04/07/2017.
 * Updated by knyc on 02/08/2021 to include LED Matrix rotation and low light settings
 */
public class SenseHat {
    public final EnvironmentalSensor environmentalSensor;
    public final IMU IMU;
    public final LEDMatrix ledMatrix;
    public final Joystick joystick;
    
    public SenseHat(String ledLowLight, String ledRotation) {
        this.environmentalSensor = new EnvironmentalSensor();
        this.IMU = new IMU();
        this.ledMatrix = new LEDMatrix(ledLowLight, ledRotation);
        this.joystick = new Joystick();
    }
}
