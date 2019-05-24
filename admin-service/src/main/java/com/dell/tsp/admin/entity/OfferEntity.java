package com.dell.tsp.admin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "offer")
public class OfferEntity {
	
	
	

			  private long offerId;
              private String offerName;
              private int validityInDays;
              private int serviceGroupId;
              private int price;
             
	            public OfferEntity(long offerId, String offerName, int validityInDays, int serviceGroupId, int price) {
				super();
				this.offerId = offerId;
				this.offerName = offerName;
				this.validityInDays = validityInDays;
				this.serviceGroupId = serviceGroupId;
				this.price = price;
	            }

				public OfferEntity() {
	          		super();
	          	}
			
            @Id
            @GeneratedValue(strategy = GenerationType.AUTO)
            public long getOfferId() {
				return offerId;
			}
            
           
			public void setOfferId(long offerId) {
				this.offerId = offerId;
			}
			
			@Column(name="OFFER_NAME", nullable = false, unique = true)
			public String getOfferName() {
				return offerName;
			}
			public void setOfferName(String offerName) {
				this.offerName = offerName;
			}
			
			@Column(name="VALIDITY_IN_DAYS", nullable = false)
			public int getValidityInDays() {
				return validityInDays;
			}
			public void setValidityInDays(int validityInDays) {
				this.validityInDays = validityInDays;
			}

			@Column(name="SERVICE_GROUP_ID", nullable = false)
			public int getServiceGroupId() {
				return serviceGroupId;
			}


			public void setServiceGroupId(int serviceGroupId) {
				this.serviceGroupId = serviceGroupId;
			}

			@Column(name="PRICE", nullable = false)
			public int getPrice() {
				return price;
			}


			public void setPrice(int price) {
				this.price = price;
			}

}
