Feature: Sahaay Backend Ticket Delete Feature
  Background:
    * def ticketCreate = call read('classpath:ticket-create.feature')
    * def authToken = ticketCreate.authToken
    * def ticketId = ticketCreate.id

    Given path 'echo', 'jwt', 'resource'
    And url sahaayBackendUrl
    And path '/ticket/' + ticketId
    And header Authorization = 'Bearer ' + authToken
    And request read('ticket_details_update.json')
    When method DELETE
    Then status 200
    And match response == 'success'