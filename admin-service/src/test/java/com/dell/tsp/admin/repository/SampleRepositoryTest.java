package com.dell.tsp.admin.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class SampleRepositoryTest {
	
	@InjectMocks
	private SampleRepository sampleRepositoryMock;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		sampleRepositoryMock = new SampleRepository();
	}
	
	@Test
	public void testPaymentOptions() {
		String returnedOption = sampleRepositoryMock.paymentOptions("CASH");
    	assertThat(returnedOption,equalTo("CASH"));
		
	}

}
