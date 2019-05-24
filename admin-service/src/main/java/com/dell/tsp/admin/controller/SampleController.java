package com.dell.tsp.admin.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dell.tsp.admin.service.SampleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RestController
@Api(value = "SameApp")
public class SampleController {
	
	  private static Logger LOG = LoggerFactory.getLogger(SampleController.class);
	
	private SampleService sampleService;
	
	@Autowired
	public SampleController(SampleService sampleService) {
		this.sampleService = sampleService;
	}

	@ApiOperation(value = "View a list of Payment Options", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved Payment Option"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping(value = "/v1/payment/{paymentoptions}")
	public ResponseEntity<String> getPaymentOptions(@PathVariable("paymentoptions") String options) {
		LOG.info("paymentoptions: controller : fetch value with {} " , options);
		return ResponseEntity.ok().body(sampleService.paymentOptions(options));
	}
}
