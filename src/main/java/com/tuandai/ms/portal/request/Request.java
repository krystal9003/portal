package com.tuandai.ms.portal.request;
public interface Request {  
    public void process();  
    public Integer getProductId();  
    public boolean isForceFefresh();  
}  