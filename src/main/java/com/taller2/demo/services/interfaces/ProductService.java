package com.taller2.demo.services.interfaces;

import com.taller2.demo.model.prod.Product;

public interface ProductService {
    public Product saveProduct(Product pro);
    public Product editProduct(Product pro, Integer id);
}
