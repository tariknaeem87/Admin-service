package com.dell.tsp.admin.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.dell.tsp.admin.DTO.OfferDTO;
import com.dell.tsp.admin.DTO.ServiceDTO;
import com.dell.tsp.admin.DTO.ServiceGroupDTO;
import com.dell.tsp.admin.entity.OfferEntity;
import com.dell.tsp.admin.entity.ServiceEntity;
import com.dell.tsp.admin.entity.ServiceGroupEntity;
import com.dell.tsp.admin.entity.SubscriberEntity;
import com.dell.tsp.admin.model.Subscriber;
import com.dell.tsp.admin.repository.AdminRepository;
import com.dell.tsp.admin.repository.OfferRepository;
import com.dell.tsp.admin.repository.ServiceGroupRepository;
import com.dell.tsp.admin.repository.ServiceRepository;
import com.dell.tsp.admin.repository.SubscriberRepository;

@Service
public class SubscriberServiceImpl implements SubscriberService {

	private static Logger LOG = LoggerFactory.getLogger(SubscriberServiceImpl.class);
	
	private SubscriberRepository subscriberRepository;
	private SubscriberEntity subscriberEntity;
	
	EntityManager entityManager;
	
//	@Autowired
	JdbcTemplate jdbcTemplate;
	
//	@Autowired
	AdminRepository adminRepository;
	
//	@Autowired
	OfferRepository offerRepository;
	
//	@Autowired
	ServiceRepository serviceRepository;
	
//	@Autowired
	ServiceGroupRepository serviceGroupRepository;

//	@Autowired
	public SubscriberServiceImpl(SubscriberRepository subscriberRepository) {
		this.subscriberRepository = subscriberRepository;
	}

	@Autowired
	public ArrayList<SubscriberEntity> getAllSubscribers() {
		return (ArrayList<SubscriberEntity>) subscriberRepository.findAll();
	}

	@Autowired
	public SubscriberServiceImpl(SubscriberRepository subscriberRepository,
			EntityManager entityManager, JdbcTemplate jdbcTemplate,
			AdminRepository adminRepository, OfferRepository offerRepository, ServiceRepository serviceRepository,
			ServiceGroupRepository serviceGroupRepository) {
		super();
		this.subscriberRepository = subscriberRepository;
		this.entityManager = entityManager;
		this.jdbcTemplate = jdbcTemplate;
		this.adminRepository = adminRepository;
		this.offerRepository = offerRepository;
		this.serviceRepository = serviceRepository;
		this.serviceGroupRepository = serviceGroupRepository;
	}
	
	public SubscriberServiceImpl() {}

	public void verifySubscriber(Subscriber subscriber) {
		
	}

	public SubscriberEntity enrollSubscriber(Subscriber subscriber) {
		LOG.info("Inside Service: enrollSubscriber()");
		SubscriberEntity subscribers = null;
		try {
			subscribers =  subscriberRepository.save(convertToSubscriberEntity(subscriber));
		}catch(ConstraintViolationException e){
			return subscribers;
		}
		return subscribers;
	}
	public SubscriberEntity convertToSubscriberEntity(Subscriber subscriber) {
		LOG.debug("Converting subscriberDTO to Entity");
		subscriberEntity = new SubscriberEntity();
		subscriberEntity.setFirstName(subscriber.getFirstName());
		subscriberEntity.setLastName(subscriber.getLastName());
		subscriberEntity.setMobileNo(subscriber.getMobileNo());
		subscriberEntity.setEmail(subscriber.getEmail());
		subscriberEntity.setAddress(subscriber.getAddress());
		subscriberEntity.setAdharNo(subscriber.getAdharNo());
		return subscriberEntity;
	}

	public SubscriberEntity getSubscriber(long mobileNo) {
		LOG.info("Inside Service: getSubscriber()");
		 return subscriberRepository.findByMobileNo(mobileNo);
		
	}
	public void addOfferData(ArrayList<OfferDTO> offerList) {
		LOG.info("Inside Service: addOfferData()");
		for(OfferDTO offer : offerList) {
			offerRepository.save(new OfferEntity(offer.getOfferId(), offer.getOfferName(), 
					offer.getValidityInDays(), offer.getServiceGroupId(), offer.getPrice()));
		}
	}

	public ServiceEntity addServiceData(ServiceDTO serviceDTO) {
		LOG.info("Inside Service: addServiceData()");
			return serviceRepository.save(new ServiceEntity(serviceDTO.getServiceId(),
					serviceDTO.getServiceName(), serviceDTO.getServiceDesc()));		
	}

	public void addServiceGroupData(ArrayList<ServiceGroupDTO> serviceGroupList) {
		LOG.info("Inside Service: addServiceGroupData()");
		for(ServiceGroupDTO service : serviceGroupList) {
			serviceGroupRepository.save(new ServiceGroupEntity(service.getServiceGroupId(),
					service.getServices()));
		}
	}

	public OfferEntity createSingleOffer(OfferDTO offer) {
		LOG.info("Inside Service: createSingleOffer()");
		return offerRepository.save(new OfferEntity(offer.getOfferId(), offer.getOfferName(), 
				offer.getValidityInDays(), offer.getServiceGroupId(), offer.getPrice()));
	}

	public OfferEntity modifySingleOffer(OfferDTO offer) {
		LOG.info("Inside Service: modifyOfferData()");
		return offerRepository.save(new OfferEntity(offer.getOfferId(), offer.getOfferName(), 
				offer.getValidityInDays(), offer.getServiceGroupId(), offer.getPrice()));
	}

	public boolean deleteSingleOffer(long id) {
		try {
			LOG.info("Inside Service: deleteSingleOffer()");
			offerRepository.deleteById(id);
			/*
			 * offerRepository.delete(new OfferEntity(offer.getOfferId(),
			 * offer.getOfferName(), offer.getValidityInDays(), offer.getServiceGroupId(),
			 * offer.getPrice())) ;
			 */
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public ServiceEntity modifyService(ServiceDTO serviceDTO) {
		try {
			LOG.info("Inside Service: modifyService()");
			return serviceRepository.save(new ServiceEntity(serviceDTO.getServiceId(),
					serviceDTO.getServiceName(), serviceDTO.getServiceDesc()));
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return null;
		}
	}

	public boolean deleteService(ServiceDTO serviceDTO) {
		try {
			LOG.info("Inside Service: deleteService()");
			serviceRepository.delete(new ServiceEntity(serviceDTO.getServiceId(),
					serviceDTO.getServiceName(), serviceDTO.getServiceDesc()));
			return true;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return false;
		}
	}

	@Override
	public ServiceGroupEntity addServiceGroup(ServiceGroupDTO serviceGroupDTO) {
		LOG.info("Inside Service: addServiceGroup()");
		return serviceGroupRepository.save(new ServiceGroupEntity(serviceGroupDTO.getServiceGroupId(), serviceGroupDTO.getServices()));
	}

	@Override
	public List<OfferEntity> getAllOffers() {
		LOG.info("Inside Service: getAllOffers()");
		return offerRepository.findAll();
	}


	
}


