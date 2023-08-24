package com.adamasorho.inventoryservice;

import com.adamasorho.inventoryservice.models.Inventory;
import com.adamasorho.inventoryservice.repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class InventoryServiceApplication implements CommandLineRunner {
	@Autowired
	private InventoryRepository inventoryRepository;

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Inventory inventory1 = Inventory.builder()
				.skuCode("Iphone_13")
				.quantity(100)
				.build();
		Inventory inventory2 = Inventory.builder()
				.skuCode("Iphone_12")
				.quantity(0)
				.build();

		inventoryRepository.save(inventory1);
		inventoryRepository.save(inventory2);
	}
}
