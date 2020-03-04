/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.services.impl;

import java.util.List;
import main.dao.GoodsRepository;
import main.dao.OrdersRepository;
import main.dto.GoodsDTO;
import main.entities.Goods;
import main.entities.Orders;
import main.services.inter.GoodsServiceInter;
import org.springframework.stereotype.Service;

/**
 *
 * @author murad_isgandar
 */
@Service
public class GoodsServiceImpl implements GoodsServiceInter {

    private final GoodsRepository goodsRepository;

    private final OrdersRepository ordersRepository;

    public GoodsServiceImpl(GoodsRepository goodsRepository, OrdersRepository ordersRepository) {
        this.goodsRepository = goodsRepository;
        this.ordersRepository = ordersRepository;
    }

    @Override
    public List<Goods> getAllGoods() {
        try {
            List<Goods> goods = goodsRepository.findAll();
            return goods;
        } catch (Exception e) {
            System.out.println("There are not any goods in database");
        }

        return null;
    }

    @Override
    public Goods getGoodsById(Integer id) {
        try {
            Goods goods = goodsRepository.findById(id).get();
            return goods;
        } catch (Exception e) {
            System.out.println("No such element founded");
        }

        return null;
    }

    @Override
    public boolean addGoods(GoodsDTO goodsDTO) {
        try {
            Goods g = new Goods();
            g.setName(goodsDTO.getName());
            g.setQuantity(goodsDTO.getQuantity());
            g.setCost(goodsDTO.getCost());

            goodsRepository.save(g);
            return true;

        } catch (Exception e) {
            System.out.println("Failed in insert operation ");
        }

        return false;

    }

    @Override
    public boolean updateGoods(Integer id, GoodsDTO goodsDTO) {
        try {
            Goods g = goodsRepository.findById(id).get();
            if (goodsDTO.getName() != null && !goodsDTO.getName().isEmpty()) {
                g.setName(goodsDTO.getName());
            }
            if (goodsDTO.getQuantity() != null) {
                g.setQuantity(goodsDTO.getQuantity());
            }
            if (goodsDTO.getCost() != null) {
                g.setCost(goodsDTO.getCost());
            }

            goodsRepository.save(g);
            return true;
        } catch (Exception e) {
            System.out.println("Failed in update operation");
        }

        return false;
    }

    @Override
    public boolean deleteGoods(Integer id) {
        try {
            goodsRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            System.out.println("Failed in delete operation");
        }

        return false;
    }

    @Override
    public Integer sendGoods(String name, Integer quantity) {
        Goods goods = goodsRepository.findByName(name);
        int q = goods.getQuantity();
        if (q >= quantity) {
            int newQuantity = q - quantity;
            goods.setQuantity(newQuantity);
            goodsRepository.save(goods);
            return quantity;
        } else if (q < quantity && q != 0) {
            int newQuantity = quantity - q;
            Orders order = new Orders();
            order.setGoodsId(goods);
            order.setOQuantity(newQuantity);

            ordersRepository.save(order);
            goods.setQuantity(0);
            
            goodsRepository.save(goods);
            return q; // if q < quantity , then send all goods which are in warehouse 
        } else if (q == 0) {
            Orders order = new Orders();
            order.setGoodsId(goods);
            order.setOQuantity(quantity);

            System.out.println("goods id " + order.getGoodsId().getId() + "quantity " + order.getOQuantity());
            ordersRepository.save(order);
            return 0;
        }

        return 0;
    }

}
