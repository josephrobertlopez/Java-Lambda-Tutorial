package acceptancetest.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class HelloWorldStepDefinitions {

    private String apiUrl;
    private String endpoint;
    private Response response;

    @Given("the API is running at {string}")
    public void theAPIIsRunningAt(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    @When("a GET request is made to {string}")
    public void aGETRequestIsMadeTo(String endpoint) {
        this.endpoint = endpoint;
        RequestSpecification request = given();
        response = request.when().get(apiUrl + endpoint);
    }
    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        assertNotNull("Response is null", response);
        assertEquals("Unexpected status code", expectedStatusCode, response.getStatusCode());
    }

    @Then("the response body should contain a JSON object with the following properties:")
    public void theResponseBodyShouldContainAJSONObjectWithTheFollowingProperties(io.cucumber.datatable.DataTable dataTable) {
        // Convert DataTable to Map for easy comparison
        java.util.Map<Object, Object> expectedJsonMap = dataTable.asMap(String.class, String.class);

        // Convert JSON response body to Map
        java.util.Map<Object, Object> responseBodyMap = response.jsonPath().getMap("");

        // Compare the expected and actual Maps
        assertTrue("Response body does not contain expected properties", responseBodyMap.entrySet().containsAll(expectedJsonMap.entrySet()));
    }
}
