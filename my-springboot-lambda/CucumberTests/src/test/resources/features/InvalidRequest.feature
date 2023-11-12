Feature: Hello World API

  Scenario: Attempting to access "/hello" without authentication token
    Given the API is running at "http://127.0.0.1:3000"
    When a POST request is made to "/hello"
    Then the response status code should be 403
    And the response body should contain a JSON object with the following property:
      | message | Missing Authentication Token |
