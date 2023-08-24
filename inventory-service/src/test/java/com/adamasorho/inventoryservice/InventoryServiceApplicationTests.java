package com.adamasorho.inventoryservice;

import com.adamasorho.inventoryservice.models.Inventory;
import com.adamasorho.inventoryservice.repositories.InventoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class InventoryServiceApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private InventoryRepository inventoryRepository;

	@Container
	static MySQLContainer mySQLContainer = new MySQLContainer(DockerImageName.parse("mysql:8.0"));

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
		dynamicPropertyRegistry.add("spring.datasource.username", mySQLContainer::getUsername);
		dynamicPropertyRegistry.add("spring.datasource.password", mySQLContainer::getPassword);
		dynamicPropertyRegistry.add("spring.datasource.driver-class-name", mySQLContainer::getDriverClassName);
	}

	@Test
	void productShouldBeInStock() throws Exception {
//		Inventory inventory = getInventory("Iphone_13",100);
//		inventoryRepository.save(inventory);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/inventory/Iphone_13"))
				.andExpect(status().isOk())
				.andExpect(content().string("true"));
	}


	@Test
	void productShouldNotBeInStock() throws Exception {
//		Inventory inventory = getInventory("Iphone_12", 0);
//		inventoryRepository.save(inventory);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/inventory/Iphone_12"))
				.andExpect(status().isOk())
				.andExpect(content().string("false"));
	}

	private Inventory getInventory(String skuCode, int quantity) {
		return Inventory.builder()
				.skuCode(skuCode)
				.quantity(quantity)
				.build();
	}

}
