package com.dell.tsp.admin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.dell.tsp.admin.DTO.AdminDTO;
import com.dell.tsp.admin.DTO.OfferDTO;
import com.dell.tsp.admin.DTO.ServiceDTO;
import com.dell.tsp.admin.DTO.ServiceGroupDTO;
import com.dell.tsp.admin.entity.AdminEntity;
import com.dell.tsp.admin.entity.OfferEntity;
import com.dell.tsp.admin.entity.ServiceEntity;
import com.dell.tsp.admin.entity.ServiceGroupEntity;
import com.dell.tsp.admin.entity.SubscriberEntity;
import com.dell.tsp.admin.model.Subscriber;
import com.dell.tsp.admin.service.CustomMessage;
import com.dell.tsp.admin.service.CustomMessageSender;
import com.dell.tsp.admin.service.LoginService;
import com.dell.tsp.admin.service.SubscriberService;


@RestController
@RequestMapping({ "/" })
public class AdminBaseController {
	
	private SubscriberService subscriberService;
	private static Logger LOG = LoggerFactory.getLogger(AdminBaseController.class);

	/*
	 * @Autowired public SubscriberController(SubscriberServiceImpl
	 * subscriberService) { super(); this.subscriberService = subscriberService; }
	 */
	@Value("${subscriberBaseUrl}")
	private String subscriberBaseURL;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private CustomMessageSender customMessageSender;
	
	@Autowired
	private CustomMessage customMessage;
	
	@Value("${TSPMailAddress}")
	private String TSP_Mail_Address;

	@Autowired
	public AdminBaseController(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}
	
	@RequestMapping(path = "/v1/signin", method = RequestMethod.POST)
	public String signIn(@RequestBody AdminDTO subscriber) {
		LOG.info("Sign-in request from user: " + subscriber.getUserName());
		return loginService.getLoginDetails(subscriber.getUserName(), subscriber.getPassWord());
	}

	/* @GetMapping("/v1/subscribers") */
	@RequestMapping(path = "/v1/subscribers", method = RequestMethod.GET)
	public ResponseEntity<List<SubscriberEntity>> getAllSubscriber() {
		LOG.info("Getting all subscriber details");
		return ResponseEntity.ok().body(subscriberService.getAllSubscribers());
	}

	
	/*
	 * @RequestMapping(path = "/v1/subscriber/{mobileNo}", method =
	 * RequestMethod.GET) public ResponseEntity<SubscriberEntity>
	 * getSubscriber(@PathVariable("mobileNo") long mobileNo) { SubscriberEntity
	 * subscriberEntity = subscriberService.getSubscriber(mobileNo);
	 * LOG.info("\n subscriber:                                    ************\n"
	 * +subscriberEntity); return subscriberEntity==null ?
	 * ResponseEntity.notFound().build() :
	 * ResponseEntity.ok(subscriberService.getSubscriber(mobileNo)); }
	 */
	
	@RequestMapping(path = "/v1/subscriber/{mobileNo}", method = RequestMethod.GET)
	public ResponseEntity<SubscriberEntity> getSubscriber(@PathVariable("mobileNo") long mobileNo) {
		SubscriberEntity subscriberEntity = subscriberService.getSubscriber(mobileNo);		
		 LOG.info("Get subscriber details with mobile no: "+ mobileNo);
		return subscriberEntity==null ? ResponseEntity.notFound().build() : ResponseEntity.ok(subscriberService.getSubscriber(mobileNo));
	} 

	@RequestMapping(path = "/v1/enroll", method = RequestMethod.POST)
	public ResponseEntity<SubscriberEntity> enrollSubscriber(@RequestBody Subscriber subscriber) {
		SubscriberEntity subscriberEntity = subscriberService.enrollSubscriber(subscriber);
		LOG.info("Enrolling a subscriber with mobile number: " , subscriber.getMobileNo());
		customMessage.setFrom(TSP_Mail_Address);
		customMessage.setTo(subscriber.getEmail());
		customMessage.setSubject("Subscriber "+subscriber.getFirstName()+" "+subscriber.getLastName()+" with subscriber id: " +subscriber.getSubscriberId()+" is Enrolled Successfully");
		customMessage.setBody("Subscriber "+subscriber.getFirstName()+" "+subscriber.getLastName()+" got enrolled successfully.");
		customMessage.setSubscriberId(subscriber.getSubscriberId());
		customMessage.setDate(new Date().toString());
		sendMessage(customMessage);
		return subscriberEntity==null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(subscriberEntity);
	}

	/*
	 * @RequestMapping(path = "/v1/Offers", method = RequestMethod.POST) public
	 * String createOffers(@RequestBody ArrayList<OfferDTO> offerList) {
	 * subscriberService.addOfferData(offerList); return
	 * "Offer created successfully "; }
	 */
	@RequestMapping(path = "/v1/offer", method = RequestMethod.POST)
	public ResponseEntity<OfferEntity> createSingleOffer(@RequestBody OfferDTO offer) {
		LOG.info("creating a new offer of name: " + offer.getOfferName());
		OfferEntity offerEntity = subscriberService.createSingleOffer(offer);
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Content-Type", "application/json");
		HttpEntity<OfferEntity> request = new HttpEntity<OfferEntity>(offerEntity, headers);
		try {
		if(offerEntity!=null) {
			LOG.debug("calling subscriber to create new offer with name: " +offer.getOfferName());
			restTemplate.postForEntity(subscriberBaseURL + "/v1/offer", request, OfferEntity.class);
		}
		}catch (RestClientException e) {
			LOG.error(e.getMessage());
		}
		return offerEntity==null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(offerEntity);
	}

	@RequestMapping(path = "/v1/offer", method = RequestMethod.PUT)
	public ResponseEntity<OfferEntity> modifySingleOffer(@RequestBody OfferDTO offer) {
		LOG.info("updating an offer with name: " + offer.getOfferName());
		OfferEntity offerEntity = subscriberService.modifySingleOffer(offer);
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Content-Type", "application/json");
		HttpEntity<OfferEntity> request = new HttpEntity<OfferEntity>(offerEntity, headers);
		try {
		if(offerEntity!=null) {
			LOG.debug("calling subscriber to update an offer with name: " +offer.getOfferName());
			restTemplate.exchange(subscriberBaseURL + "/v1/offer", HttpMethod.PUT, request, OfferEntity.class);
		}
		}catch (RestClientException e) {
			LOG.error(e.getMessage());
		}
		return offerEntity==null ? ResponseEntity.notFound().build() : 
			new ResponseEntity<OfferEntity>(offerEntity, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/v1/offers", method = RequestMethod.GET)
	public ResponseEntity<List<OfferEntity>> getAllOffer() {
		LOG.info("Getting all offers");
		List<OfferEntity> offerEntity = subscriberService.getAllOffers();
		return offerEntity==null ? ResponseEntity.notFound().build() : ResponseEntity.ok(offerEntity);
	}
	

	@RequestMapping(path = "/v1/offer/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteSingleOffer(@PathVariable("id") int id) {
		LOG.info("Delete an offer with id: " + id);
		if(subscriberService.deleteSingleOffer(id)) {
			try {
				LOG.debug("Calling subscriber to delete offer with id: " +id);
				restTemplate.delete(subscriberBaseURL + "/v1/offer/" + id);
				}catch (RestClientException e) {
				LOG.error(e.getMessage());
			}
			return ResponseEntity.ok("Offer is deleted successfully");
		}
		else
		return ResponseEntity.notFound().build();
	}

	@RequestMapping(path = "/v1/service", method = RequestMethod.POST)
	public ResponseEntity<ServiceEntity> createService(@RequestBody ServiceDTO serviceDTO) {
		LOG.info("Create a new service offering: " + serviceDTO.getServiceName());
		ServiceEntity serviceEntity = subscriberService.addServiceData(serviceDTO);
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Content-Type", "application/json");
		HttpEntity<ServiceEntity> request = new HttpEntity<ServiceEntity>(serviceEntity, headers);
		try {
		if(serviceEntity!=null) {
			LOG.debug("calling subscriber to create a new service: " + serviceDTO.getServiceName());
			restTemplate.postForEntity(subscriberBaseURL + "/v1/service", request, ServiceEntity.class);
		}
		}catch (RestClientException e) {
			LOG.error(e.getMessage());
		}
		return serviceEntity==null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(serviceEntity);
	}
	
	@RequestMapping(path = "/v1/service", method = RequestMethod.PUT)
	public ResponseEntity<ServiceEntity> modifyService(@RequestBody ServiceDTO serviceDTO) {	
		LOG.info("Modify a service offering: " + serviceDTO.getServiceName());
		ServiceEntity serviceEntity = subscriberService.modifyService(serviceDTO);
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Content-Type", "application/json");
		HttpEntity<ServiceEntity> request = new HttpEntity<ServiceEntity>(serviceEntity, headers);
		try {
		if(serviceEntity!=null) {
			LOG.debug("calling subscriber to modify a service: " + serviceDTO.getServiceName());
			restTemplate.exchange(subscriberBaseURL + "/v1/service", HttpMethod.PUT,request, ServiceEntity.class);
		}
		}catch (RestClientException e) {
			LOG.error(e.getMessage());
		}
		return serviceEntity==null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(serviceEntity);
	}
	
	@RequestMapping(path = "/v1/service", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteService(@RequestBody ServiceDTO serviceDTO) {	
		LOG.info("Delete a service offering: " + serviceDTO.getServiceName());
		if(subscriberService.deleteService(serviceDTO)) {
			try {
				MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
				headers.add("Content-Type", "application/json");
				HttpEntity<ServiceDTO> request = new HttpEntity<ServiceDTO>(serviceDTO, headers);
				LOG.debug("calling subscriber to delete a service: " + serviceDTO.getServiceName());
				restTemplate.exchange(subscriberBaseURL + "/v1/service", HttpMethod.DELETE, request, String.class);
				}catch (RestClientException e) {
				LOG.error(e.getMessage());
			}
		
			return ResponseEntity.ok("Service is deleted successfully");
		}
		else
			return ResponseEntity.notFound().build();
	}


	@RequestMapping(path = "/v1/service-group-id", method = RequestMethod.POST)
	public String createServiceGroupId(@RequestBody ArrayList<ServiceGroupDTO> serviceGroupList) {
		LOG.info("creating a list of service-group-ids: " + serviceGroupList);
		subscriberService.addServiceGroupData(serviceGroupList);
		return "Service group created";
	}
	
	@RequestMapping(path = "/v1/service-group", method = RequestMethod.POST)
	public ResponseEntity<ServiceGroupEntity> createServiceGroup(@RequestBody ServiceGroupDTO serviceGroupDTO) {
		LOG.info("creating a new service-group-id: " + serviceGroupDTO.getServices());
		ServiceGroupEntity serviceGroupEntity = subscriberService.addServiceGroup(serviceGroupDTO);
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Content-Type", "application/json");
		HttpEntity<ServiceGroupEntity> request = new HttpEntity<ServiceGroupEntity>(serviceGroupEntity, headers);
		try {
		if(serviceGroupEntity!=null) {
			LOG.debug("calling subscriber to create a service group-id: " + serviceGroupDTO.getServices());
			restTemplate.postForEntity(subscriberBaseURL + "/v1/service-group", request, ServiceGroupEntity.class);
		}
		}catch (RestClientException e) {
			LOG.error(e.getMessage());
		}
		return serviceGroupEntity==null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(serviceGroupEntity);
	}
	
	@RequestMapping(path = "/v1/service-group", method = RequestMethod.PUT)
	public ResponseEntity<ServiceGroupEntity> modifyServiceGroup(@RequestBody ServiceGroupDTO serviceGroupDTO) {
		LOG.info("Modify a service group-id: " + serviceGroupDTO.getServices());
		ServiceGroupEntity serviceGroupEntity = subscriberService.addServiceGroup(serviceGroupDTO);
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Content-Type", "application/json");
		HttpEntity<ServiceGroupEntity> request = new HttpEntity<ServiceGroupEntity>(serviceGroupEntity, headers);
		try {
		if(serviceGroupEntity!=null) {
			LOG.debug("Calling subscriber to modify a service group-id: " + serviceGroupDTO.getServices());
			restTemplate.exchange(subscriberBaseURL + "/v1/service-group", HttpMethod.PUT,request, ServiceGroupEntity.class);
		}
		}catch (RestClientException e) {
			LOG.error(e.getMessage());
		}
		return serviceGroupEntity==null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(serviceGroupEntity);
	}
	
	
	@RequestMapping(path = "/v1/message", method = RequestMethod.POST)
	public String sendMessage(@RequestBody CustomMessage message) {
		LOG.info("calling message sender");
		customMessageSender.sendMessage(message);
		return "Successfully sent message";
	}
	
	
	@RequestMapping(path = "/v1/signUp", method = RequestMethod.POST)
	public ResponseEntity<AdminEntity> createAdmin(@RequestBody AdminDTO admin){
		LOG.info("creating an admin");
		return ResponseEntity.ok().body(loginService.createAdmin(admin));
		
	}
	

}
