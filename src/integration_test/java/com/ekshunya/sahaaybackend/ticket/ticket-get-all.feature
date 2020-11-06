Feature: Sahaay Backend Ticket Update Feature
  Background:
    #TODO this is also wrong. Here each call to the ticketCreate feature creates a new token every time. That would break the parllel run of tests.
    * def ticketCreate = call read('classpath:ticket-create.feature')
    * def authToken = ticketCreate.authToken
    * def ticketId = ticketCreate.id
    * def ticketAnotherCreate = call read('classpath:ticket-create.feature')
    * def ticketAnotherId = ticketAnotherCreate.id

    Given path 'echo', 'jwt', 'resource'
    And url sahaayBackendUrl
    And path '/ticket/' + ticketId
    And header Authorization = 'Bearer ' + authToken
    When method GET
    Then status 200
    And match response contains ticketId
    And match response contains ticketAnotherId
    #TODO : The response needs to be matched correctly with the incoming input. the response needs to contain