/*
 * Copyright (c) 2018 MyCompany, Inc. All rights reserved.
 */

package com.mycompany.onprem;

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
            senseHat.ledMatrix.showMessage("Connection Test");
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

    @RequestMapping(path = "/humidity", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> readHumidity() throws Exception {

        Map<String, Object> responseData = new HashMap<String, Object>();
        SenseHat senseHat = new SenseHat();
        float humidity = senseHat.environmentalSensor.getHumidity();
        responseData.put("humidity", humidity); 
        return responseData;
    }

    @RequestMapping(path = "/temperature", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> readTemperature() throws Exception {

        Map<String, Object> responseData = new HashMap<String, Object>();
        SenseHat senseHat = new SenseHat();
        float temperature = senseHat.environmentalSensor.getTemperature();
        responseData.put("temperature", temperature); 
        return responseData;
    }

    @RequestMapping(path = "/displayMessage", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> displayMessage(@RequestBody Map<String, Object> body) throws Exception {
        
        Map<String, Object> responseData = new HashMap<String, Object>();
        String message = (String) body.get("message");
        SenseHat senseHat = new SenseHat();        
        senseHat.ledMatrix.showMessage(message);
        responseData.put("status","message displayed");        
        return responseData;       
    }

    @RequestMapping(path = "/color", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> colorLED(@RequestBody Map<String, Object> body) throws Exception {

        Map<String, Object> responseData = new HashMap<String, Object>();
        int r = Integer.parseInt((String) body.get("r"));
        int g = Integer.parseInt((String) body.get("g"));
        int b = Integer.parseInt((String) body.get("b"));
        SenseHat senseHat = new SenseHat();
        senseHat.ledMatrix.clear(Color.of(r, g, b));
        responseData.put("status","Color displayed");                
        return responseData;
    }

}
