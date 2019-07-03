package com.deloitte.ta.smartbuy.it;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static com.deloitte.ta.smartbuy.TestConstants.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.*;

import com.deloitte.ta.smartbuy.SmartbuyApplication;
import com.github.tomakehurst.wiremock.WireMockServer;

import io.restassured.path.json.JsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmartbuyApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
public class SmartBuyIntegrationTests {

	private static WireMockServer productMockServer = new WireMockServer(wireMockConfig().port(8090));
	private static WireMockServer orderMockServer = new WireMockServer(wireMockConfig().port(8000));

	@BeforeClass
	public static void setup() {
		// Wire Mock Server Settings
		productMockServer.start();
		orderMockServer.start();

		// Stubbing 3rd Party Services Using Wire Mock Server
		productMockServer.stubFor(
				get(urlPathMatching("/productsprovider/getproductslist")).willReturn(aResponse().withStatus(200)
						.withHeader("Content-Type", APPLICATION_JSON).withBodyFile(GET_ALL_PRODUCTS_MOCK_RESPONSE)));

		orderMockServer.stubFor(get(urlPathMatching("/ordersprovider/getorders")).willReturn(aResponse().withStatus(200)
				.withHeader("Content-Type", APPLICATION_JSON).withBodyFile(ORDERS_MOCK_RESPONSE)));

		// Rest Assured Settings
		baseURI = "http://localhost:8900/smartbuy";
	}

	// Test the status code
	@Test
	public void testStatusCode() {
		given().when().get("/getallproducts").then().statusCode(200);
	}

	// Request with a Path parameter
	@Test
	public void testARequestWhichNeedsPathParam_1() {
		given().pathParam("productID", "1").when().get("/getdetails/{productID}").then().statusCode(200);
	}

	@Test
	public void testARequestWhichNeedsPathParam_2() {
		given().when().get("/getdetails/{productID}", "1").then().statusCode(200);
	}

	// Request with a Query parameter
	@Test
	public void testARequestWhichNeedsQueryParam() {
		given().queryParam("orderID", "222").when().get("/cancelorder").then().statusCode(200);
	}

	// Test the header
	@Test
	public void testHeader() {
		given().when().get("/getallproducts").thenReturn().getHeader("Content-Type").equals("application/json");
	}

	// Test a single field in Response Body
	@Test
	public void testSingleFieldInBody() {
		given().pathParam("productID", "1").when().get("/getdetails/{productID}").then().body("name",
				equalTo("iPhone X"));
	}

	// Test all elements in Response Body
	@Test
	public void testAllElementsInBody() {
		String response = given().pathParam("productID", "3").when().get("/getdetails/{productID}").asString();

		JsonPath responseJson = new JsonPath(response);
		assertEquals("Dell Lattitude E7560", responseJson.get("name"));
		assertEquals("Electronics", responseJson.get("category"));
		assertEquals(Integer.valueOf(15), responseJson.get("availableQuantity"));
		assertEquals(Float.valueOf(67500f), responseJson.get("price"));
	}

	// Test array elements in Response Body
	@Test
	public void testArrayElementsInBody() {
		given().when().get("/getallproducts").then().body("product.productID", hasItem("1"));
	}

	// Compare Response Body with Schema
	@Test
	public void testResponseSchemaWithSingleObjectSchema() {
		given().pathParam("productID", "1").when().get("/getdetails/{productID}").then().assertThat()
				.body(matchesJsonSchemaInClasspath("__files/SingleProduct.json"));
	}

	@Test
	public void testResponseSchemaWithListOfObjectsSchema() {
		given().when().get("/getallproducts").then().assertThat()
				.body(matchesJsonSchemaInClasspath("__files/Products.json"));
	}

	// Test the response time
	@Test
	public void testResponseTime() {
		given().when().get("/getallproducts").then().time(lessThan(5000L));
	}

}
