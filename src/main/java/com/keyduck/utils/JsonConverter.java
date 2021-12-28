package com.keyduck.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import springfox.documentation.spring.web.json.Json;

public class JsonConverter {

    public static JSONObject convert(String string) throws ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(string);
        JSONObject converted = (JSONObject) obj;
        return converted;
    }

}
