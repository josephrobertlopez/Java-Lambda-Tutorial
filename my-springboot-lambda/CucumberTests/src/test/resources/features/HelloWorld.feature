Feature: Hello World API

  Scenario: Retrieve Hello World message along with IP location
    Given the API is running at "http://127.0.0.1:3000"
    When a GET request is made to "/hello"
    Then the response status code should be 200
    And the response body should contain a JSON object with the following properties:
      | message  | hello world    |
      | location | 104.231.198.34 |
