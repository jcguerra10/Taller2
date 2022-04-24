package com.taller2.demo.services.interfaces;

import com.taller2.demo.model.prod.Productsubcategory;

public interface ProductsubcategoryService {
    public Productsubcategory saveProductsubcategory(Productsubcategory ps);
    public Productsubcategory editProductsubcategory(Productsubcategory ps, Integer i);
}
