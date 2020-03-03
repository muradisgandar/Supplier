/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.services.inter;

import java.util.List;
import main.dto.GoodsDTO;
import main.entities.Goods;

/**
 *
 * @author murad_isgandar
 */
public interface GoodsServiceInter {
    
    public List<Goods> getAllGoods();
    
    public Goods getGoodsById(Integer id);
    
    public boolean addGoods(GoodsDTO goodsDTO);
    
    public boolean updateGoods(Integer id,GoodsDTO goodsDTO);
    
    public boolean deleteGoods(Integer id);
    
    public Integer sendGoods(String name,Integer quantity);
    
}
