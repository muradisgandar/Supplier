/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author murad_isgandar
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdersDTO {
    
    private Integer id;
    private int oQuantity;
    
}
