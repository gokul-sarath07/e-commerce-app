package com.ecommerce.order.service;

import com.ecommerce.order.dto.CatalogWebClientObject;
import com.ecommerce.order.dto.UserDetailsDTO;
import com.ecommerce.order.exception.OrderException;
import com.ecommerce.order.model.Catalog;
import com.ecommerce.order.model.Order;
import com.ecommerce.order.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private WebClient webClient;

    public List<Order> getAllOrders() {
        List<Catalog> catalogList = getAllCatalog().getCatalogList();
        if (catalogList.isEmpty()) {
            throw new OrderException("Catalog list is empty.");
        }

        UserDetailsDTO usernameDTO = getUsernames(catalogList);
        Map<String, Object> userDetailsList = getUserDetails(usernameDTO).getUserMap();

        return getOrderDetails(catalogList, userDetailsList);
    }

    private List<Order> getOrderDetails(List<Catalog> catalogList, Map<String, Object> userDetailsList) {
        List<Order> orderList = new ArrayList<>();
        catalogList.forEach(catalog -> {
            Order order = new Order();

            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.convertValue(userDetailsList.get(catalog.getUsername()), new TypeReference<User>(){});

            order.setUser(user);
            order.setCatalog(catalog);
            order.setTotalAmount(catalog.getCount() * catalog.getProduct().getPrice());

            orderList.add(order);
        });

        return orderList;
    }

    private UserDetailsDTO getUserDetails(UserDetailsDTO usernameDTO) {
        return webClient.post()
                .uri("http://localhost:8080/api/user")
                .body(Mono.just(usernameDTO), UserDetailsDTO.class)
                .retrieve()
                .bodyToMono(UserDetailsDTO.class)
                .block();
    }

    private CatalogWebClientObject getAllCatalog() {
        return webClient.get()
                .uri("http://localhost:8082/api/catalog")
                .retrieve()
                .bodyToMono(CatalogWebClientObject.class)
                .block();
    }

    private UserDetailsDTO getUsernames(List<Catalog> catalogList) {
        return new UserDetailsDTO(
                catalogList.stream().map(Catalog::getUsername)
                .collect(Collectors.toMap(username -> username,
                                          username -> username,
                                          (userOne, userTwo) -> userOne)
                )
        );
    }

}
