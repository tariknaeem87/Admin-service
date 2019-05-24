package com.dell.tsp.admin.DTO;

public class OfferDTO {
	
	private int offerId;
    private String offerName;
    private int validityInDays;
    private int serviceGroupId;
    private int price;
	
	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public int getValidityInDays() {
		return validityInDays;
	}

	public void setValidityInDays(int validityInDays) {
		this.validityInDays = validityInDays;
	}

	public int getServiceGroupId() {
		return serviceGroupId;
	}

	public void setServiceGroupId(int serviceGroupId) {
		this.serviceGroupId = serviceGroupId;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getOfferId() {
		return offerId;
	}

	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}
}
