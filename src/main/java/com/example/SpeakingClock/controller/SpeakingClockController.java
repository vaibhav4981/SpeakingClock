package com.example.SpeakingClock.controller;

import com.example.SpeakingClock.config.ResponseConstant;
import com.example.SpeakingClock.models.response.SpeakingClockResponse;
import com.example.SpeakingClock.service.SpeakingClockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@RestController
@Slf4j
public class SpeakingClockController {

    @Autowired
    SpeakingClockService speakingClockService;

    @GetMapping("/api/v1/speaking-clock")
    @Operation(tags = "Speaking Clock",
            summary = "Tha API can be used to get the words from given time in 24 hours format.",
            operationId = "v1-speaking-clock",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(
                                    examples = { @ExampleObject(name = "Success Example",
                                            value = ResponseConstant.SUCCESS_RESPONSE) },
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = SpeakingClockResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(
                                    examples = { @ExampleObject(name = "Bad Request Example",
                                            value = ResponseConstant.BAD_REQUEST_RESPONSE) },
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = SpeakingClockResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error Example",
                            content = @Content(
                                    examples = { @ExampleObject(name = "Internal Server Error Example",
                                            value = ResponseConstant.INTERNAL_SERVER_ERROR_RESPONSE) },
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = SpeakingClockResponse.class)))
             })
    @ResponseBody
    ResponseEntity<SpeakingClockResponse> getWordsOfTime(@RequestParam(value = "time", defaultValue = "08:34") String time) {
        SpeakingClockResponse response = speakingClockService.getWordOfTime(time);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
