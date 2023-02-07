package com.example.SpeakingClock;

import com.example.SpeakingClock.models.response.SpeakingClockResponse;
import com.example.SpeakingClock.service.SpeakingClockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class SpeakingClockApplicationTests {

	@Autowired
	SpeakingClockService speakingClockService;

	@Test
	public void testSuccess1(){
		SpeakingClockResponse response = speakingClockService.getWordOfTime("10:00");
		assertEquals("It's Ten", response.getMessage());
		assertEquals(200, response.getStatusCode());
	}

	@Test
	public void testSuccess2(){
		SpeakingClockResponse response = speakingClockService.getWordOfTime("10:10");
		assertEquals("It's Ten Ten", response.getMessage());
		assertEquals(200, response.getStatusCode());
	}

	@Test
	public void testSuccess3(){
		SpeakingClockResponse response = speakingClockService.getWordOfTime("00:00");
		assertEquals("It's Midnight", response.getMessage());
		assertEquals(200, response.getStatusCode());
	}

	@Test
	public void testSuccess4(){
		SpeakingClockResponse response = speakingClockService.getWordOfTime("12:00");
		assertEquals("It's Midday", response.getMessage());
		assertEquals(200, response.getStatusCode());
	}

	@Test
	public void testSuccess5(){
		SpeakingClockResponse response = speakingClockService.getWordOfTime("00:25");
		assertEquals("It's Zero Twenty Five", response.getMessage());
		assertEquals(200, response.getStatusCode());
	}


	@Test
	public void testValidation1(){
		SpeakingClockResponse response = speakingClockService.getWordOfTime("24:25");
		assertEquals(400, response.getStatusCode());
	}

	@Test
	public void testValidation2(){
		SpeakingClockResponse response = speakingClockService.getWordOfTime("12:0");
		assertEquals(400, response.getStatusCode());
	}


	@Test
	public void testValidation3(){
		SpeakingClockResponse response = speakingClockService.getWordOfTime("2:25");
		assertEquals(400, response.getStatusCode());
	}

	@Test
	public void testValidation4(){
		SpeakingClockResponse response = speakingClockService.getWordOfTime("12");
		assertEquals(400, response.getStatusCode());
	}

}
