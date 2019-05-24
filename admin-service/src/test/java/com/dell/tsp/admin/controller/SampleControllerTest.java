package com.dell.tsp.admin.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.dell.tsp.admin.service.SampleService;

public class SampleControllerTest {

	@InjectMocks
	private SampleController sampleController;

	@Mock
	private SampleService sampleService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		sampleController = new SampleController(sampleService);
	}

	@Test
	public void testGetPaymentOptions() throws Exception {

		when(sampleService.paymentOptions("CASH")).thenReturn("CASH");

		ResponseEntity<String> returnedOption = sampleController.getPaymentOptions("CASH");
		verify(sampleService).paymentOptions("CASH");
		assertThat(returnedOption.getStatusCode(), is(HttpStatus.OK));
		String resposeValue = returnedOption.getBody();
		assertThat(resposeValue, equalTo("CASH"));
		verifyNoMoreInteractions(sampleService);

	}

}
