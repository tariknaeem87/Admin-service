package com.dell.tsp.admin.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
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

import com.dell.tsp.admin.entity.SubscriberEntity;
import com.dell.tsp.admin.service.SubscriberService;

public class SubscriberControllerTest {

	@InjectMocks
	private AdminBaseController subscriberController;

	@Mock
	private SubscriberService subscriberService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		subscriberController = new AdminBaseController(subscriberService);
	}
	
	@Test
	public void testGetSubscriber() throws Exception {
		SubscriberEntity entity = new SubscriberEntity(17, "Gaurav", "2036 2756 1222", "20, Sharda Vihar Gwalior", "Upadhyay", "gaurav.u02@gmail.com", 9893657326L);
		when(subscriberService.getSubscriber(9893657326l)).thenReturn(entity);
		ResponseEntity<SubscriberEntity> returnedSubscriber = subscriberController.getSubscriber(9893657326L);
		verify(subscriberService, times(2)).getSubscriber(9893657326L);
		assertThat(returnedSubscriber.getStatusCode(), is(HttpStatus.OK));
		SubscriberEntity resposeValue = returnedSubscriber.getBody();
		assertThat(resposeValue, equalTo(entity));
		verifyNoMoreInteractions(subscriberService);

		
	}
}
