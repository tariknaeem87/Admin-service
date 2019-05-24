@Completed @Accepted @Api 
Feature: Sample Payments API testing. 

Background: The api url 
	Given I visit "${api.url}" 
	And I relax https validation 
	
Scenario: 001 - Verify Payments API 
	Given I send the following headers: 
		|name | value |
		|Accept|application/json|
	Given I set path parameter "paymentoptions" to "CASH" 
	When I request GET to "/v1/payment/{paymentoptions}" 
	Then response code should be 200 
	
