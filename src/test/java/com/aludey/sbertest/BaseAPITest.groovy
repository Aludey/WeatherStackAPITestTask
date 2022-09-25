package com.aludey.sbertest

import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException

import spock.lang.Specification

class BaseAPITest extends Specification {

    final String accessKey = "d6cc6c38939008842b1fb19c87e6287f"
    final String baseURL = "http://api.weatherstack.com/"

    def requestCurrentWeatherInCity(String city) {
        StringBuilder result = new StringBuilder()
        URL url = new URL(baseURL + "current?access_key=" + accessKey + "&query=" + city)
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection()
            connection.setRequestProperty("accept", "application/json")
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()))

            String line
            while ((line = rd.readLine()) != null){
                result.append(line)
            }
            rd.close()

            return result.toString()

        } catch (IOException e){
            e.printStackTrace()
            return ""
        }
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