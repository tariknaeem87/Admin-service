package com.dell.tsp.admin.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class SampleRepository {
	private static Logger LOG = LoggerFactory.getLogger(SampleRepository.class);
	public String paymentOptions(String paymentoptions) {
		LOG.info("paymentoptions: Repository : fetch value with {} " , paymentoptions);
		return paymentoptions;
}

}
