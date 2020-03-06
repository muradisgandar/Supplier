/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.controller;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import main.dto.ResponseDTO;
import main.entities.Orders;
import main.mapper.OrderMapper;
import main.services.inter.OrdersServiceInter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author murad_isgandar
 */
// Rest Api server
@RestController
@RequestMapping("/orders")
public class OrdersController {

    
    private final OrdersServiceInter ordersServiceInter;

    public OrdersController(OrdersServiceInter ordersServiceInter) {
        this.ordersServiceInter = ordersServiceInter;
    }

    @GetMapping
    @ApiOperation(value = "get goods from database")
    public ResponseEntity orders() {
        List<Orders> orders = ordersServiceInter.getAllOrders();
        if (orders != null && !orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Orders are founded", 200, new OrderMapper().mapEntityListToDtoList(orders)));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO("There are not any order in database", 500, null));
        }

    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "get goods which are defined by id from database")
    public ResponseEntity orderById(@PathVariable(value = "id") Integer id) {
        Orders order = ordersServiceInter.getOrderById(id);
        if (order != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Order is founded", 200, new OrderMapper().mapEntityToDto(order)));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO("No such element founded", 500, null));
        }
    }
    
    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "delete goods which are identified by sending id")
    public ResponseEntity deleteGoods(@PathVariable(value = "id") Integer id) {
        if (ordersServiceInter.deleteOrder(id)) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(("Goods by id = " + id + " are deleted successfully"), 200, ("Goods' id = " + id)));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO("Failed in delete operation", 500, null));
        }
    }


}
