package com.aludey.sbertest

import spock.lang.Unroll

import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

import static io.qameta.allure.Allure.step

class PositiveWeatherTests extends BaseAPITest {

    @Unroll("Check weather and location info in #city city")
    def requestCurrentWeatherTest() {

        when:
        step "Get current information in city from API Request"
        def response = requestCurrentWeatherInCity(city)
        step("Find weather information")
        def current = findInString(response, "current")
        step("Find location information")
        def location = findInString(response, "location")

        then:
        verifyAll {
            step "Check that city name is correct"
            def actualCity = findInString(location, "name")
            actualCity == city

            step "Check that country is correct"
            def actualCountry = findInString(location, "country")
            actualCountry == expectedCountry

            step "Check that utc_offset is correct"
            def actualUTCOffset = findInString(location, "utc_offset")
            actualUTCOffset == expectedUTCOffset

            step "Check that local time is correct"
            def actualLocalTime = findInString(location, "localtime")
            actualLocalTime == expectedLocalTime

            step "Check that day status is correct"
            def actualDayStatus = findInString(current, "is_day")
            actualDayStatus == expectedDaySatus

            step "Check that temperature is correct"
            def actualTemperature = findInString(current, "temperature")
            actualTemperature == expectedTemperature

            step "Check that wind speed is correct"
            def actualWindSpeed = findInString(current, "wind_speed")
            actualWindSpeed == expectedWindSpeed

            step "Check that pressure is correct"
            def actualPressure = findInString(current, "pressure")
            actualPressure == expectedPressure
        }

        where:
        city               | expectedTemperature | expectedUTCOffset | expectedLocalTime              | expectedCountry            | expectedDaySatus | expectedWindSpeed | expectedPressure
        "New York"         | "17"                | "-4.0"            | getDateAndTimeViaUTCOffset(-4) | "United States of America" | isDay(-4)        | "13"              | "1004"
        "London"           | "19"                | "1.0"             | getDateAndTimeViaUTCOffset(1)  | "United Kingdom"           | isDay(1)         | "13"              | "1001"
        "Saint Petersburg" | "12"                | "3.0"             | getDateAndTimeViaUTCOffset(3)  | "Russia"                   | isDay(3)         | "4"               | "1009"
        "Beijing"          | "23"                | "8.0"             | getDateAndTimeViaUTCOffset(8)  | "China"                    | isDay(8)         | "7"               | "1013"
        "Tokyo"            | "25"                | "9.0"             | getDateAndTimeViaUTCOffset(9)  | "Japan"                    | isDay(9)         | "13"              | "1015"
    }

    def requestCurrentWeatherInCity(String city) {
        return requestWithFunctionAndParameters("current", "&query=" + city)
    }

    def isDay(int utcOffset) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH")
        def time = dtf.format(OffsetDateTime.now(ZoneOffset.ofHours(utcOffset))).toString() as int
        if (time < 7 || time > 17) return "no"
        else return "yes"
    }

    def getDateAndTimeViaUTCOffset(int utcOffset) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        OffsetDateTime date = OffsetDateTime.now(ZoneOffset.ofHours(utcOffset))
        return dtf.format(date)
    }

}

