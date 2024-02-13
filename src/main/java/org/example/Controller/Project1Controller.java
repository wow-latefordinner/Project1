package org.example.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import io.javalin.Javalin;
import org.example.Exception.ProductException;
import org.example.Exception.SellerException;
import org.example.Model.Product;
import org.example.Model.Seller;
import org.example.Service.ProductService;
import org.example.Service.SellerService;

import java.util.List;
import java.util.Set;

public class Project1Controller {

    ProductService productService;
    SellerService sellerService;

    public Project1Controller(ProductService productService, SellerService sellerService) {
        this.productService = productService;
        this.sellerService = sellerService;
    }

    public Javalin getAPI(){
        Javalin api = Javalin.create();

        api.get("health", context -> {
            context.result("Server is UP");
        });

        api.get("seller", context -> {
            Set<Seller> sellerHashSet = sellerService.getSellerList();
            context.json(sellerHashSet);
        });

        api.post("seller", context -> {
            try {
                ObjectMapper om = new ObjectMapper();


                try {
                    Seller seller = om.readValue(context.body(), Seller.class);
                    sellerService.insertSeller(seller);
                    context.status(201);

                } catch(MismatchedInputException e) {
                    context.result("Missing Content - can't add a seller if info is not provided");
                    context.status(500);
                }


            } catch(SellerException e) {
                context.result(e.getMessage());
                context.status(400);
            }

        });

        api.get("product", context -> {
            List<Product> productList  = productService.getAllProducts();
            context.json(productList);
        });

        api.get("product/{id}", context -> {
            long id = Long.parseLong(context.pathParam("id"));
            try {
                Product p = productService.getProductbyID(id);
                context.status(200);
                context.json(p);
            }
            catch (ProductException e) {
                context.status(404);
            }

        });

        api.post("product", context -> {
            try {
                ObjectMapper om = new ObjectMapper();


                try {
                    Product product = om.readValue(context.body(), Product.class);
                    productService.insertProduct(product);
                    context.status(201);

                } catch(MismatchedInputException e) {
                    context.result("Missing Content - can't add a product if info is not provided");
                    context.status(500);
                }


            } catch(ProductException e) {
                context.result(e.getMessage());
                context.status(400);
            }

        });

        api.put("product/{id}", context -> {
            long id = Long.parseLong(context.pathParam("id"));
            try {
                ObjectMapper om = new ObjectMapper();


                try {
                    Product product = om.readValue(context.body(), Product.class);
                    productService.updateProduct(id, product);
                    context.status(201);

                } catch(MismatchedInputException e) {
                    context.result("Missing Content - can't add a product if info is not provided");
                    context.status(500);
                }


            } catch(ProductException e) {
                context.result(e.getMessage());
                context.status(400);
            }

        });

        api.delete("product/{id}", context -> {
            long id = Long.parseLong(context.pathParam("id"));
            try {

                    productService.deleteProduct(id);
                    context.status(200);

            } catch(ProductException e) {
                context.result(e.getMessage());
                context.status(200);
            }

        });

        return api;
    }
}
