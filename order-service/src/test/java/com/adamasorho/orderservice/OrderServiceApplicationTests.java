package com.adamasorho.orderservice;

import com.adamasorho.orderservice.contrats.OrderLineItemsDTO;
import com.adamasorho.orderservice.contrats.OrderRequest;
import com.adamasorho.orderservice.repositories.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class OrderServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private OrderRepository orderRepository;

	@Container
	static MySQLContainer mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0-debian"));

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
		dynamicPropertyRegistry.add("spring.datasource.username", mySQLContainer::getUsername);
		dynamicPropertyRegistry.add("spring.datasource.password", mySQLContainer::getPassword);
		dynamicPropertyRegistry.add("spring.datasource.driver-class-name", mySQLContainer::getDriverClassName);
	}

	@Test
	void shouldPlaceOrder() throws Exception {
		String  orderRequestString = objectMapper.writeValueAsString(getOrderRequest());

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/order")
				.contentType(MediaType.APPLICATION_JSON)
				.content(orderRequestString))
				.andExpect(status().isCreated());

		Assertions.assertEquals(1, orderRepository.findAll().size());
	}

	private OrderRequest getOrderRequest() {
		List<OrderLineItemsDTO> orderLineItemsDTOList = Collections.singletonList(
				OrderLineItemsDTO.builder()
						.skuCode("Iphone_13")
						.price(BigDecimal.valueOf(1200))
						.quantity(1)
						.build()
		);

		return OrderRequest.builder()
				.orderLineItemsDTOList(orderLineItemsDTOList)
				.build();
	}

}
