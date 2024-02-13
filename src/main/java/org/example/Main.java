package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import io.javalin.Javalin;
import org.example.Controller.Project1Controller;
import org.example.Service.ProductService;
import org.example.Service.SellerService;

public class Main {
    public static void main(String[] args) {

        SellerService sellerService = new SellerService();
        ProductService productService = new ProductService(sellerService);

        Project1Controller project1Controller = new Project1Controller(productService, sellerService);

        Javalin api  = project1Controller.getAPI();

        api.start(7070);
    }
}