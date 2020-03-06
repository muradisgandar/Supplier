/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.controller;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import main.dto.GoodsDTO;
import main.dto.OrdersDTO;
import main.dto.ResponseDTO;
import main.entities.Goods;
import main.mapper.GoodsMapper;
import main.mapper.OrderMapper;
import main.services.inter.GoodsServiceInter;
import main.services.inter.OrdersServiceInter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author murad_isgandar
 */
// Rest Api server
@RestController
@RequestMapping("/goods")
public class GoodsController {

    private final GoodsServiceInter goodsServiceInter;
    
    private final OrdersServiceInter ordersServiceInter;

    public GoodsController(GoodsServiceInter goodsServiceInter,OrdersServiceInter ordersServiceInter) {
        this.goodsServiceInter = goodsServiceInter;
        this.ordersServiceInter = ordersServiceInter;
    }

    @GetMapping
    @ApiOperation(value = "get all goods from database")
    public ResponseEntity goods() {
        List<Goods> goods = goodsServiceInter.getAllGoods();
        if (goods != null && !goods.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Goods are founded", 200, new GoodsMapper().mapEntityListToDtoList(goods)));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO("There are not any goods in database", 500, null));
        }

    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "get goods which are defined by id from database")
    public ResponseEntity goodsById(@PathVariable(value = "id") Integer id) {
        Goods goods = goodsServiceInter.getGoodsById(id);
        if (goods != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Goods are founded", 200, new GoodsMapper().mapEntityToDto(goods)));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO("No such element founded", 500, null));
        }
    }

    @PostMapping
    @ApiOperation(value = "add goods to database")
    public ResponseEntity addGoods(@RequestBody GoodsDTO goodsDTO) {
        if (goodsServiceInter.addGoods(goodsDTO)) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("Goods are inserted successfully", 200, goodsDTO));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO("Failed in insert operation", 500, goodsDTO));
        }

    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "update goods which are identified by sending id")
    public ResponseEntity updateGoods(@PathVariable(value = "id") Integer id, @RequestBody GoodsDTO goodsDTO) {
        if (goodsServiceInter.updateGoods(id, goodsDTO)) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(("Goods by id = " + id + " are updated successfully"), 200, new GoodsMapper().mapEntityToDto(goodsServiceInter.getGoodsById(id))));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO("Failed in update operation", 500, goodsDTO));
        }
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "delete goods which are identified by sending id")
    public ResponseEntity deleteGoods(@PathVariable(value = "id") Integer id) {
        if (goodsServiceInter.deleteGoods(id)) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(("Goods by id = " + id + " are deleted successfully"), 200, ("Goods' id = " + id)));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO("Failed in delete operation", 500, null));
        }
    }

    @GetMapping(value = "/gName/{quantity}")
    @ApiOperation(value = "get goods from database for sending to buyer")
    public Integer sendGoods(@RequestParam(value = "name") String name, @PathVariable(value = "quantity") Integer quantity) {
        return ordersServiceInter.sendGoods(name, quantity);
    }

}
