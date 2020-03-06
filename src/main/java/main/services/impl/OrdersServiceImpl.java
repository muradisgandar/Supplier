/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.services.impl;

import java.util.List;
import main.dao.GoodsRepository;
import main.dao.OrdersRepository;
import main.entities.Goods;
import main.entities.Orders;
import main.services.inter.OrdersServiceInter;
import main.tcpconnection.TCPServer;
import org.springframework.stereotype.Service;

/**
 *
 * @author murad_isgandar
 */
@Service
public class OrdersServiceImpl implements OrdersServiceInter {

    private final GoodsRepository goodsRepository;

    private final OrdersRepository ordersRepository;

    public OrdersServiceImpl(GoodsRepository goodsRepository, OrdersRepository ordersRepository) {
        this.goodsRepository = goodsRepository;
        this.ordersRepository = ordersRepository;
    }

    @Override
    public List<Orders> getAllOrders() {
        try {
            List<Orders> orders = ordersRepository.findAll();
            return orders;
        } catch (Exception e) {
            System.out.println("There are not any order in database");
        }

        return null;
    }

    @Override
    public Orders getOrderById(Integer id) {
        try {
            Orders order = ordersRepository.findById(id).get();
            return order;
        } catch (Exception e) {
            System.out.println("No such element founded");
        }

        return null;
    }

    @Override
    public boolean addOrder(Goods goods, Integer quantity) {
        Orders order = new Orders();
        order.setGoodsId(goods);
        order.setOQuantity(quantity);

        System.out.println("goods id " + order.getGoodsId().getId() + "quantity " + order.getOQuantity());

        ordersRepository.save(order);

        return true;
    }

    @Override
    public Integer sendGoods(String name, Integer quantity) {
        try {
            TCPServer.recieveMessage();   // recieve order message from client
        } catch (Exception ex) {
            ex.getStackTrace();
        }
        Goods goods = goodsRepository.findByName(name);
        int q = goods.getQuantity();
        if (q >= quantity) {
            int newQuantity = q - quantity;
            goods.setQuantity(newQuantity);
            goodsRepository.save(goods);

            try {
                TCPServer.sendMessage("We are sending " + quantity + " " + name + " computers\n");
            } catch (Exception ex) {
                ex.getStackTrace();
            }
            return quantity;
        } else if (q < quantity && q > 0) {
            int newQuantity = quantity - q;

            addOrder(goods, newQuantity);
            goods.setQuantity(0);

            try {
                TCPServer.sendMessage("We are sending only " + q + " of " + name + " computers ," + " others will be sent as soon as we get them\n");
            } catch (Exception ex) {
                ex.getStackTrace();
            }
            goodsRepository.save(goods);
            return q; // if q < quantity , then send all goods which are in warehouse 
        } else if (q == 0) {
            addOrder(goods, quantity);
            try {
                TCPServer.sendMessage("We don't have any " + name + " computers in warehouse ," + "  we will send the goods as soon as we get them\n ");
            } catch (Exception ex) {
                ex.getStackTrace();
            }
            return 0;
        }

        return 0;
    }

    @Override
    public boolean deleteOrder(Integer id) {
        ordersRepository.deleteById(id);
        return true;
    }

}
