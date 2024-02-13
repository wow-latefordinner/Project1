package org.example.Exception;

import org.example.Service.ProductService;

public class ProductException extends Exception {
    public ProductException() {
    }

    public ProductException(String message) {
        super(message);
    }

}
