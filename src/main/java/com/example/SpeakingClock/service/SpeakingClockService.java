package com.example.SpeakingClock.service;

import com.example.SpeakingClock.models.response.SpeakingClockResponse;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

public interface SpeakingClockService {
    SpeakingClockResponse getWordOfTime(String time);
}
