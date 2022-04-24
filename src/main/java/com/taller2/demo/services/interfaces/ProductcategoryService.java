package com.taller2.demo.services.interfaces;

import com.taller2.demo.model.prod.Productcategory;

public interface ProductcategoryService {
    public Productcategory saveProductcategory(Productcategory pc);
    public Productcategory editProductcategory(Productcategory pc, Integer i);
}
