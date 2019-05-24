@Completed  @Accepted @Api
Feature: Demoing use of API testing.

  Background: The api url
    Given I visit "${api.url}"

  Scenario: 001 - Verify the health of the api
    Given I send the following headers:
      |name | value |
      |Accept|application/json|
    When I request GET to "actuator/health"
    Then response code should be 200
    Then response content path "status" should be "UP"

  Scenario: 002 - check the info endpoint exists in the api
    Given I send the following headers:
      |name | value |
      |Accept|application/json|
    When I request GET to "actuator/info"
    Then response code should be 200
