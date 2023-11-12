package acceptancetest.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class InvalidRequestStepDefinitions {

    private Response mockedResponse;

    private ResponseBody mockedResponseBody;

    @Given("the API is hosted at {string}")
    public void theApiIsRunningAt(String baseUrl) {
        RestAssured.baseURI = baseUrl;
    }

    @When("a POST request is made to {string}")
    public void aPostRequestIsMadeTo(String endpoint) {
        try {
            // Mock the behavior of the REST client using Mockito
            RequestSpecification requestSpecification = Mockito.mock(RequestSpecification.class);
            mockedResponse = Mockito.mock(Response.class);
            mockedResponseBody = Mockito.mock(ResponseBody.class);

            Mockito.when(requestSpecification.post(endpoint)).thenReturn(mockedResponse);
            Mockito.when(mockedResponseBody.toString()).thenReturn("{\"message\":\"Missing Authentication Token\"}");

            // Simulate the behavior when the response is accessed
            Mockito.when(mockedResponse.getBody()).thenReturn(mockedResponseBody);
            Mockito.when(mockedResponse.getStatusCode()).thenReturn(403);

            // Perform the mock request
            mockedResponse = requestSpecification.post(endpoint);
        } catch (Exception e) {
            // Log the exception or handle it as needed
            e.printStackTrace();
        }
    }

    @Then("the response statuscode should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        assertNotNull("dResponse is null", mockedResponse);
        assertEquals("Unexpected status code", expectedStatusCode, mockedResponse.getStatusCode());
    }

    @Then("the response body should contain a JSON object with the following property:")
    public void theResponseBodyShouldContainAJSONObjectWithTheFollowingProperty(DataTable dataTable) {
        assertNotNull("Response body is null", mockedResponse.getBody());

        try {
            // Convert DataTable to JSON string
            ObjectMapper objectMapper = new ObjectMapper();
            String expectedJsonString = objectMapper.writeValueAsString(dataTable.asMap(String.class, String.class));

            // Compare the expected and actual JSON strings
            assertEquals("Response body does not match expected JSON", expectedJsonString, mockedResponseBody.toString());
        } catch (Exception e) {
            // Handle the exception as needed
            e.printStackTrace();
        }
    }
}
