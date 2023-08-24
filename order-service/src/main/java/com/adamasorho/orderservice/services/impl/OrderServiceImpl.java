package com.adamasorho.orderservice.services.impl;

import com.adamasorho.orderservice.contrats.InventoryResponse;
import com.adamasorho.orderservice.contrats.OrderLineItemsDTO;
import com.adamasorho.orderservice.contrats.OrderRequest;
import com.adamasorho.orderservice.event.OrderPlacedEvent;
import com.adamasorho.orderservice.models.Order;
import com.adamasorho.orderservice.models.OrderLineItems;
import com.adamasorho.orderservice.repositories.OrderRepository;
import com.adamasorho.orderservice.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;
    @Autowired
    private KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    @Value("${inventory-service.name}")
    private String inventoryService;
    @Value("${inventory-service.protocol}")
    private String inventoryServiceProtocol;

    @Override
    public String placeOrder(OrderRequest orderRequest) {
        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsDTOList()
                .stream()
                .map(this::getOrderLineItemsFromOrderLineItemsDTO)
                .toList();
        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderLineItemsList(orderLineItemsList)
                .build();

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        // call inventory service to check if the product exist
        InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
                        .uri(inventoryServiceProtocol + "://" + inventoryService + "/api/v1/inventory",
                                uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                        .retrieve()
                        .bodyToMono(InventoryResponse[].class)
                        .block();

        assert inventoryResponseArray != null;
        boolean allProductsInStock = Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::isInStock);

        if (allProductsInStock) {
            orderRepository.save(order);
            kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));

            log.info("Order {} placed successfully", order.getId());

            return "Order placed successfully";
        }

        return "Product is not in stock, please try again later";
    }

    private OrderLineItems getOrderLineItemsFromOrderLineItemsDTO(OrderLineItemsDTO orderLineItemsDTO) {
        return OrderLineItems.builder()
                .id(orderLineItemsDTO.getId())
                .price(orderLineItemsDTO.getPrice())
                .skuCode(orderLineItemsDTO.getSkuCode())
                .quantity(orderLineItemsDTO.getQuantity())
                .build();
    }
}
