/*
 * Created by Ken Ng on 07/27/2021
 * Uses rpi.sensehat.api - https://github.com/cinci/rpi-sense-hat-java
 */

package com.knyc.opaextension;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;

import rpi.sensehat.api.SenseHat;
import rpi.sensehat.api.dto.Color;
import rpi.sensehat.api.dto.Rotation;

import org.apache.commons.codec.binary.Hex;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SenseHatExtension {

    @Inject
    private Environment env;

    @RequestMapping(path = "/ping", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> testConnection() throws Exception {

        Map<String, Object> responseData = new HashMap<String, Object>();
        try {
            SenseHat senseHat = new SenseHat();
            senseHat.ledMatrix.showMessage("Hi!");
            responseData.put("status","success");                
        } catch (Exception e) {
            responseData.put("status","failed");
        }
        return responseData;
    }

    @RequestMapping(path = "/clear", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> clearLED() throws Exception {

        Map<String, Object> responseData = new HashMap<String, Object>();
        SenseHat senseHat = new SenseHat();
        senseHat.ledMatrix.clear();
        responseData.put("status","LED cleared");                
        return responseData;
    }

    @RequestMapping(path = "/environmentalSensor", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> readHumidity() throws Exception {

        Map<String, Object> responseData = new HashMap<String, Object>();
        SenseHat senseHat = new SenseHat();
        float humidity = senseHat.environmentalSensor.getHumidity();
        float temperature = senseHat.environmentalSensor.getTemperature();
        float temperatureFromPressure = senseHat.environmentalSensor.getTemperatureFromPressure();
        float pressure = senseHat.environmentalSensor.getPressure();
        responseData.put("humidity", humidity);
        responseData.put("temperature", temperature);
        responseData.put("temperatureFromPressure", temperatureFromPressure);
        responseData.put("pressure", pressure);
        return responseData;
    }

    @RequestMapping(path = "/displayMessage", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> displayMessage(@RequestBody Map<String, Object> body) throws Exception {
        
        Map<String, Object> responseData = new HashMap<String, Object>();
        String message = (String) body.get("message");
        String messageColor = (String) body.get("messageColor");
        String backgroundColor = (String) body.get("backgroundColor");
        float scrollSpeed = new Float(0.1f);

        SenseHat senseHat = new SenseHat();        
        senseHat.ledMatrix.showMessage(message,scrollSpeed,Color.get(messageColor),Color.get(backgroundColor));
        senseHat.ledMatrix.clear();
        responseData.put("status","message displayed");        
        return responseData;       
    }

    @RequestMapping(path = "/displayLetter", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> displayLetter(@RequestBody Map<String, Object> body) throws Exception {
        
        Map<String, Object> responseData = new HashMap<String, Object>();
        String letter = (String) body.get("letter");
        String letterColor = (String) body.get("letterColor");
        String backgroundColor = (String) body.get("backgroundColor");
        
        SenseHat senseHat = new SenseHat();
        senseHat.ledMatrix.showLetter(letter,Color.get(letterColor),Color.get(backgroundColor));
        responseData.put("status","letter displayed");        
        return responseData;
    }

    @RequestMapping(path = "/displayColor", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> displayColor(@RequestBody Map<String, Object> body) throws Exception {

        Map<String, Object> responseData = new HashMap<String, Object>();
        String color = (String) body.get("color");

        SenseHat senseHat = new SenseHat();
        senseHat.ledMatrix.clear(Color.get(color));
        responseData.put("status","Color displayed");                
        return responseData;
    }

    @RequestMapping(path = "/displayStopIcon", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> displayStopIcon() throws Exception {

        Map<String, Object> responseData = new HashMap<String, Object>();
        SenseHat senseHat = new SenseHat();

        final Color X = Color.RED;
        final Color O = Color.WHITE;

        Color[] stopIcon = {
                O, O, X, X, X, X, O, O,
                O, X, X, X, X, X, X, O,
                X, X, O, O, X, X, X, X,
                X, X, O, X, X, X, X, X,
                X, X, X, X, X, O, X, X,
                X, X, X, X, O, O, X, X,
                O, X, X, X, X, X, X, O,
                O, O, X, X, X, X, O, O
        };
        senseHat.ledMatrix.setPixels(stopIcon);

        responseData.put("status","Stop icon displayed");                
        return responseData;
    }

    @RequestMapping(path = "/displayCheckIcon", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> displayCheckIcon() throws Exception {

        Map<String, Object> responseData = new HashMap<String, Object>();
        SenseHat senseHat = new SenseHat();

        final Color X = Color.GREEN;
        final Color O = Color.WHITE;

        Color[] checkIcon = {
                O, O, O, O, O, O, O, X,
                O, O, O, O, O, O, X, X,
                O, O, O, O, O, X, X, X,
                O, O, O, O, X, X, X, O,
                X, O, O, X, X, X, O, O,
                X, X, X, X, X, O, O, O,
                O, X, X, X, O, O, O, O,
                O, O, X, O, O, O, O, O
        };
        senseHat.ledMatrix.setPixels(checkIcon);

        responseData.put("status","Check icon displayed");                
        return responseData;
    }

}
