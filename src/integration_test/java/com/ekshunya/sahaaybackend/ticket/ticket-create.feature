Feature: Sahaay Backend Ticket Get Feature
  Background:
    * def signIn = call read('classpath:my-signin.feature')
    * def authToken = signIn.authToken

    Given path 'echo', 'jwt', 'resource'
    And url sahaayBackendUrl
    And path /ticket
    And header Authorization = 'Bearer ' + authToken
    And request read('ticket_create.json')
    When method POST
    Then status 201
    And match response == 'created'