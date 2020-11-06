Feature: Sahaay Backend Ticket Update Feature
  Background:
    * def ticketCreate = call read('classpath:ticket-create.feature')
    * def authToken = ticketCreate.authToken
    * def ticketId = ticketCreate.id

    Given path 'echo', 'jwt', 'resource'
    And url sahaayBackendUrl
    And path '/ticket/' + ticketId
    And header Authorization = 'Bearer ' + authToken
    When method DELETE
    Then status 200
    And match response == 'true'