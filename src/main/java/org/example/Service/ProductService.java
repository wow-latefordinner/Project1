package org.example.Service;

import org.example.DAO.ProductDAO;
import org.example.Exception.ProductException;
import org.example.Model.Product;
import org.example.Model.Seller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is calling the DAO classes to manipulate the Product entity
 */
public class ProductService {

    List<Product> productList;
    List<Seller> sellerList;
    ProductDAO productDAO;
    SellerService sellerService;

    public static Logger log = LoggerFactory.getLogger(ProductService.class);

    public ProductService(SellerService sellerService, ProductDAO productDAO) {
        this.productDAO = productDAO;
//        sellerList = sellerService.getSellerList();
        this.sellerService = sellerService;
    }

    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

//    public boolean checkProductValues(Product product) throws ProductException {
////        Determine if the seller exists before adding product
//
//
//        if (sellerService.sellerIdExists(product.sellerId) != 0 /*|| !sellerList.contains(new Seller(product.getSeller()))*/) {
//            log.warn("Seller does not exist.");
//            throw new ProductException("Seller doesn't exist.  Add seller first then add product.");
//        }
////        Determine if any of the field values are missing, throw exception
//        if (product.getName().isEmpty()) {
//            log.warn("Product name is empty");
//            throw new ProductException("Product name not provided");
//        }
////        Determine if the price was included or > 0
//        if (product.getPrice() <= 0) {
//            log.warn("Product price <= 0");
//            throw new ProductException("Product price must be provided and be greater than $0.00.");
//        }

    public boolean checkProductValues(Product product) throws ProductException{
        return productDAO.checkProductValues(product);
    }

    public boolean checkProductId(Product product) throws ProductException {
        return (productDAO.productExists(product.getId()) != 0);
    }

    public void insertProduct(Product product) throws ProductException {
        productDAO.insertProduct(product);

    }


    private long getProductId() {
        log.info("Getting unique Product ID");
        return (long) (Math.random() * Long.MAX_VALUE);
    }

    public List<Product> getProductByID(long id) throws ProductException {
        return productDAO.getProductById(id);
    }

    public void updateProduct(Product product) throws ProductException {

        productDAO.updateProduct(product);
    };

    public void deleteProduct(long id) throws ProductException {
        productDAO.deleteProduct(id);
    };

}

