package com.example.SpeakingClock.service.impl;

import com.example.SpeakingClock.models.response.SpeakingClockResponse;
import com.example.SpeakingClock.service.SpeakingClockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@Service
@Slf4j
public class SpeakingClockServiceImpl implements SpeakingClockService {

    private static final String[] LESS_THAN_20 = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    private static final String[] TENS = {"", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};


    /**
     * The function performs below steps:
     * 1. Time validation using regex
     * 2. Check if its midnight
     * 3. Check if its midday
     * 4. Perform logic for rest of cases
     * @param time
     * @return
     */
    @Override
    public SpeakingClockResponse getWordOfTime(String time) {
        try {

            if (!time.matches("^([01][0-9]|2[0-3]):[0-5][0-9]$")) {
                return SpeakingClockResponse.builder()
                        .message("Please pass time in HH:mm format, time should be 24 hours format.")
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .build();
            }

            if (time.equalsIgnoreCase("00:00")) {
                return SpeakingClockResponse.builder()
                        .message("It's Midnight")
                        .statusCode(HttpStatus.OK.value())
                        .build();
            }

            if (time.equalsIgnoreCase("12:00")) {
                return SpeakingClockResponse.builder()
                        .message("It's Midday")
                        .statusCode(HttpStatus.OK.value())
                        .build();
            }

            //split the time based on colon
            String[] split = time.split(":");
            int hours = Integer.parseInt(split[0]);
            int minutes = Integer.parseInt(split[1]);

            //build the response string using string builder as string is immutable
            StringBuilder builder = new StringBuilder();

            //if minutes is zero then ignore the minutes section for text converion
            if(minutes!=0){
                builder.append("It's ").append(convert(hours)).append(" ").append(convert(minutes));
            }else{
                builder.append("It's ").append(convert(hours));
            }

            return SpeakingClockResponse.builder()
                    .message(builder.toString())
                    .statusCode(HttpStatus.OK.value())
                    .build();

        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return SpeakingClockResponse.builder()
                    .message(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
    }

    /**
     * Convert the number into text
     * Reusable function for hours and minutes
     * @param num
     * @return
     */
    public static String convert(int num) {
        if (num == 0) {
            return "Zero";
        }

        StringBuilder result = new StringBuilder();

        if (num < 20) {
            result.append(LESS_THAN_20[num]);
        } else {
            result.append(TENS[num / 10]).append(" ").append(LESS_THAN_20[num % 10]);
        }

        return result.toString().trim();
    }
}
