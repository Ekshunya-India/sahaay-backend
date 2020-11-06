Feature: Sahaay Backend Ticket Update Feature
  Background:
    * def ticketCreate = call read('classpath:ticket-create.feature')
    * def authToken = ticketCreate.authToken
    * def ticketId = ticketCreate.id

    Given path 'echo', 'jwt', 'resource'
    And url sahaayBackendUrl
    And path '/ticket/' + ticketId
    And header Authorization = 'Bearer ' + authToken
    When method GET
    Then status 200
    And match response contains ticketId
    #TODO : The response needs to be matched correctly with the incoming input. the current matching is wrong.