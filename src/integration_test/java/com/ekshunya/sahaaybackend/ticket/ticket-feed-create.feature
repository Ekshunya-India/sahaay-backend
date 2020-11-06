Feature: Sahaay Backend Ticket Feed Features
  Background:
    * def ticketCreate = call read('classpath:ticket-create.feature')
    * def authToken = ticketCreate.authToken
    * def ticketId = ticketCreate.id

    Given path 'echo', 'jwt', 'resource'
    And url sahaayBackendUrl
    And path '/ticket/' + ticketId
    And header Authorization = 'Bearer ' + authToken
#    And request read('ticket_details_update.json')  TODO : The request body here needs Form Data which need more digging into as to how this can be achived? There is a file upload example
    When method PUT
    Then status 200
    And match response == 'success'