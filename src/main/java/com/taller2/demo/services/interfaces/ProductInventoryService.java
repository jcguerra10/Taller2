package com.taller2.demo.services.interfaces;

import com.taller2.demo.model.prod.Productinventory;
import com.taller2.demo.model.prod.ProductinventoryPK;

public interface ProductInventoryService {
    public Productinventory saveProductInventory(Productinventory proInventory);
    public Productinventory editProductInventory(Productinventory proInventory, ProductinventoryPK id);
	Productinventory editProductInventory(Productinventory proInventory, Integer id);
}
