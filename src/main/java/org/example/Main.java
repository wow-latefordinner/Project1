package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import io.javalin.Javalin;
import org.example.Controller.Project1Controller;
import org.example.DAO.ProductDAO;
import org.example.DAO.SellerDAO;
//import org.example.Service.ProductService;
//import org.example.Service.SellerService;
import org.example.Util.ConnectionSingleton;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

        Connection conn = ConnectionSingleton.getConnection();
//        SellerService sellerService = new SellerService();
        SellerDAO sellerDAO = new SellerDAO(conn);
        ProductDAO productDAO = new ProductDAO(conn, sellerDAO);

        Project1Controller project1Controller = new Project1Controller(productDAO, sellerDAO);

        Javalin api  = project1Controller.getAPI();

        api.start(7070);
    }
}