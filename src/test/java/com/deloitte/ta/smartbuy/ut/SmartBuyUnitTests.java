package com.deloitte.ta.smartbuy.ut;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.deloitte.ta.smartbuy.controller.SmartBuyController;
import com.deloitte.ta.smartbuy.model.Products;
import com.deloitte.ta.smartbuy.service.SmartBuyService;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@RunWith(MockitoJUnitRunner.class)
public class SmartBuyUnitTests {

	@Mock
	private SmartBuyService smartBuyService;

	@InjectMocks
	private SmartBuyController smartBuyController;

	@Before
	public void initialiseRestAssuredMockMvcStandalone() {
		RestAssuredMockMvc.standaloneSetup(smartBuyController);
	}

	@Test
	public void test1() {
		when(smartBuyService.getAllProducts()).thenReturn(new Products());
		given().log().all().when().get("/getallproducts").then().statusCode(200);
	}

}
