package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import io.javalin.Javalin;
import org.example.Controller.Project1Controller;
import org.example.DAO.ProductDAO;
import org.example.DAO.SellerDAO;
import org.example.Service.ProductService;
import org.example.Service.SellerService;
import org.example.Util.ConnectionSingleton;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

        Connection conn = ConnectionSingleton.getConnection();

        SellerDAO sellerDAO = new SellerDAO(conn);
        SellerService sellerService = new SellerService(sellerDAO);
        ProductDAO productDAO = new ProductDAO(conn, sellerDAO);
        ProductService productService = new ProductService(sellerService, productDAO);

        Project1Controller project1Controller = new Project1Controller(productService, sellerService);

        Javalin api  = project1Controller.getAPI();

        api.start(7070);
    }
}