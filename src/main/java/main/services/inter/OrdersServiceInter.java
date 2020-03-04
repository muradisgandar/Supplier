/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.services.inter;

import java.util.List;
import main.entities.Goods;
import main.entities.Orders;

/**
 *
 * @author murad_isgandar
 */
public interface OrdersServiceInter {
    
    public List<Orders> getAllOrders();
    
    public Orders getOrderById(Integer id);
    
    public Integer sendGoods(String name,Integer quantity);
    
    public boolean addOrder(Goods goods,Integer quantity);
    
    public boolean deleteOrder(Integer id);
}
