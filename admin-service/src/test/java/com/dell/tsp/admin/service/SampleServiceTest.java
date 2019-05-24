package com.dell.tsp.admin.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dell.tsp.admin.repository.SampleRepository;

public class SampleServiceTest {
	
	@InjectMocks
	private SampleService sampleServiceMock;
	
	@Mock
	private SampleRepository sampleRepositoryMock;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		sampleServiceMock = new SampleService(sampleRepositoryMock);
	}
	
	@Test
	public void testPaymentOptions() {
		
		when(sampleRepositoryMock.paymentOptions("CASH")).thenReturn("CASH");
		
		String returnedOption = sampleServiceMock.paymentOptions("CASH");
		verify(sampleRepositoryMock).paymentOptions("CASH");
    	assertThat(returnedOption,equalTo("CASH"));
    	
    	verifyNoMoreInteractions(sampleRepositoryMock);
		
	}

}
