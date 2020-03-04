/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.mapper;

import java.util.List;
import java.util.stream.Collectors;
import main.dto.OrdersDTO;
import main.entities.Orders;
import org.springframework.stereotype.Component;

/**
 *
 * @author murad_isgandar
 */
@Component
public class OrderMapper {
    
    public OrdersDTO mapEntityToDto(Orders orders){
        return OrdersDTO.builder()
                .id(orders.getId())
                .oQuantity(orders.getOQuantity())
                .build();
        
    }
    
    public List<OrdersDTO> mapEntityListToDtoList(List<Orders> orders){
        return orders.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
        
    }
}
