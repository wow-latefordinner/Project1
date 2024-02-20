package org.example.Service;

import org.example.Exception.ProductException;
import org.example.Model.Product;
import org.example.Model.Seller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ProductService {

    List<Product> productList;
    List<Seller> sellerList;

    public static Logger log = LoggerFactory.getLogger(ProductService.class);

    public ProductService(SellerService sellerService) {
        productList = new ArrayList<>();
        sellerList = sellerService.getSellerList();
    }

    public List<Product> getAllProducts() {
        return productList;
    }

    public boolean checkProductValues(Product product) throws ProductException {
//        Determine if the seller exists before adding product


        if (sellerList.isEmpty() /*|| !sellerList.contains(new Seller(product.getSeller()))*/) {
            log.warn("Seller does not exist.");
            throw new ProductException("Seller doesn't exist.  Add seller first then add product.");
        }
//        Determine if any of the field values are missing, throw exception
        if (product.getName().isEmpty()) {
            log.warn("Product name is empty");
            throw new ProductException("Product name not provided");
        }
//        Determine if the price was included or > 0
        if (product.getPrice() <= 0) {
            log.warn("Product price <= 0");
            throw new ProductException("Product price must be provided and be greater than $0.00.");
        }

//       Exception not thrown, so return true if we make this point
        return true;
    }

    public boolean checkProductId(Product product) throws ProductException {

//        Determine if the product ID already exists in the list
        for (int i = 0; i < productList.size(); i++) {
            if (product.id == productList.get(i).id) {
                log.warn("Product ID already exists");
                throw new ProductException("Product ID already exists, please try again");
            }
        }

        return true;
    }

    public void insertProduct(Product product) throws ProductException {

//        Assume it's a new product, so we need to set the product ID before we validate the values prior to adding it
        product.setId(this.getProductId());

//        Check the product ID for uniqueness and other values

        if (this.checkProductValues(product)) {

            productList.add(product);
            log.info("Product successfully added");
        } else {
            log.warn("Failure to add product");
            throw new ProductException(("Something is really wrong..."));
        }
    }


    private long getProductId() {
        log.info("Getting unique Product ID");
        return (long) (Math.random() * Long.MAX_VALUE);
    }

    public Product getProductbyID(long id) throws ProductException {
        for (int i = 0; i < productList.size(); i++) {
            Product currentProduct = productList.get(i);
            if (currentProduct.getId() == id) {
                return currentProduct;
            }
        }
//        If we reach this point, then throw exception since no product found
        log.warn("No product found in search by ID");
        throw new ProductException("No product found.");

    }

    public void updateProduct(long id, Product product) throws ProductException {

//        Check the product ID for uniqueness and other values

        for (int i = 0; i < productList.size(); i++) {
            if (id == productList.get(i).id) {
                if (this.checkProductValues(product)) {
                    product.setId(id);
                    productList.set(i, product);
                    log.info("Product successfully updated");
                } else {
                    log.warn("Failure to update product");
                    throw new ProductException(("Something is really wrong..."));
                }
            }

        }
    };

    public void deleteProduct(long id) throws ProductException {

//        Check the product ID for uniqueness and other values

        for (int i = 0; i < productList.size(); i++) {
            if (id == productList.get(i).id) {
                    productList.remove(i);
                    log.info("Product successfully removed");
                } else {
                    log.warn("Failure to delete product");
                    throw new ProductException(("unable to delete..."));
                }
            }
    };

}

