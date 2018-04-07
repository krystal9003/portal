package com.tuandai.ms.portal.request;  
  
import org.springframework.transaction.annotation.Transactional;

import com.tuandai.ms.portal.domain.InventoryProduct;
import com.tuandai.ms.portal.service.InventoryProductBiz;  
  
/** 
 ********************************************** 
 *  描述：更新库存信息 
 *  1、先删除缓存中的数据 
 *  2、更新数据库中的数据 
 * Simba.Hua 
 * 2017年8月30日 
 ********************************************** 
**/  
public class InventoryUpdateDBRequest implements Request{  
    private InventoryProductBiz inventoryProductBiz;  
    private InventoryProduct inventoryProduct;  
      
    public InventoryUpdateDBRequest(InventoryProduct inventoryProduct,InventoryProductBiz inventoryProductBiz){  
        this.inventoryProduct = inventoryProduct;  
        this.inventoryProductBiz = inventoryProductBiz;  
    }  
    @Override  
    @Transactional  
    public void process() {  
        inventoryProductBiz.removeInventoryProductCache(inventoryProduct.getProductId());  
        inventoryProductBiz.updateInventoryProduct(inventoryProduct);  
    }  
    @Override  
    public Integer getProductId() {  
        // TODO Auto-generated method stub  
        return inventoryProduct.getProductId();  
    }  
    @Override  
    public boolean isForceFefresh() {  
        // TODO Auto-generated method stub  
        return false;  
    }  
  
} 