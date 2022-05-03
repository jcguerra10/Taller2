package com.taller2.demo.services.interfaces;

import com.taller2.demo.model.prod.Productcosthistory;

public interface ProductcosthistoryService {
    public Productcosthistory saveProductcosthistory(Productcosthistory pch);
    public Productcosthistory editProductcosthistory(Productcosthistory pch, Integer id);
}
