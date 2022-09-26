package com.aludey.sbertest

import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException

import spock.lang.Specification

class BaseAPITest extends Specification {

    private static final String accessKey = "9c1369f431be74f40b6072213d33c3c6"
    static final String baseURL = "http://api.weatherstack.com/"

    def requestWithOnlyURL(URL url) {
        StringBuilder result = new StringBuilder()
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection()
            connection.setRequestProperty("accept", "application/json")
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()))

            String line
            while ((line = rd.readLine()) != null) {
                result.append(line)
            }
            rd.close()

            return result.toString()

        } catch (IOException e) {
            e.printStackTrace()
            return ""
        }
    }

    def requestWithFunctionAndParameters(String function, String parameters) {
        URL url = new URL(baseURL + function + "?access_key=" + accessKey + parameters)
        return requestWithOnlyURL(url)
    }

    def findInString(String whatToParse, String whatToFind) {
        try {
            JSONParser jsonParser = new JSONParser()
            JSONObject jsonObject = (JSONObject) jsonParser.parse(whatToParse)

            return jsonObject.get(whatToFind).toString()

        } catch (ParseException e) {
            e.printStackTrace()
            return ""
        }
    }
}