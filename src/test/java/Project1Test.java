import org.example.Controller.Project1Controller;
import org.example.Exception.SellerException;
import org.example.Exception.ProductException;
import org.example.Model.Product;
import org.example.Model.Seller;
//import org.example.Service.ProductService;
//import org.example.Service.SellerService;
import org.example.Util.ConnectionSingleton;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.example.DAO.SellerDAO;
import org.example.DAO.ProductDAO;


//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import util.CommandLine;

import io.javalin.Javalin;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.example.Util.ConnectionSingleton.resetTestDatabase;


public class Project1Test {

//    SellerService sellerService;
//    ProductService productService;

    SellerDAO sellerDAO;
    ProductDAO productDAO;

    Connection conn;

//    Project1Controller project1Controller = new Project1Controller(productService, sellerService);

//    Javalin app = project1Controller.getAPI();
//    HttpClient webClient;
//    ObjectMapper objectMapper;

    @Before
    public void beforeEach() throws InterruptedException{
        conn = ConnectionSingleton.getConnection();

//        sellerService = new SellerService();
//        productService = new ProductService(sellerService);

        sellerDAO = new SellerDAO(conn);
        productDAO = new ProductDAO(conn, sellerDAO);
    }

    @After
    public void afterEach(){
        try {
            resetTestDatabase();
        }
        catch (Exception e) {
            Assert.fail("Exception while cleaning up code");
        };

    }

    /**
     * The response for endpoint echo should contain an Object matching the data contained within the following
     * JSON request body:
     * {
     *     "songName": "Let it be",
     *     "artistName": "Beatles"
     * }
     * Meaning, the endpoint should respond with the same request body it was provided.
     */
//    @Test
//    public void checkHealth() throws IOException, InterruptedException {
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("http://localhost:7070/health"))
////                .POST(HttpRequest.BodyPublishers.ofString("{"+
////                        "\"songName\":\"Let it be\"," +
////                        "\"artistName\":\"Beatles\"}"))
////                .header("Content-Type", "application/json")
//                .build();
//        HttpResponse response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
////        Assert.assertFalse("Response body must not be empty",response.body().toString().length()==0);
////        Song expected = new Song("Let it be", "Beatles");
////        Song actual = objectMapper.readValue(response.body().toString(), Song.class);
//        Assert.assertEquals("Javalin health check", "Server is UP", response.body().toString());
//
//    }

    @Test
    public void sellerModelTest(){
//  Adding a seller that passes
        String newSellerName = "seller1";
        Seller expected = new Seller(0, newSellerName);

        try {
            Assert.assertEquals(0, expected.getId());
            Assert.assertEquals(newSellerName, expected.getSeller());
        }
        catch (Exception e) {
            Assert.fail("Seller object had unexpected issues");
        }
    }

    @Test
    public void productModel(){

        String newSellerName = "seller1";
        Seller expectedSeller = new Seller(0, newSellerName);
        String newProductName = "New product 1";
        double newProductPrice = 3.47;
        int newSellerId = 1;
        Product newProduct = new Product(0,newProductName, newProductPrice, newSellerId);

        try {
            Assert.assertEquals("Product ID added",0,newProduct.getId());
            Assert.assertEquals("Product Name", newProductName, newProduct.getName());
            Assert.assertEquals("Product Price",newProductPrice, newProduct.getPrice(), .01);
            Assert.assertEquals("Product Seller ID",newSellerId, newProduct.getSellerId());
        }
        catch (Exception e) {
            Assert.fail("Product model object had unexpected issues");
        }
    }
    @Test
    public void sellerTest1(){
//  Adding a seller that passes
        String newSellerName = "seller1";
        Seller expected = new Seller(0, newSellerName);

        try {
            sellerDAO.insertSeller(expected);


                if (sellerDAO.sellerExists(newSellerName) == 0) {
                    Assert.fail("The expected seller was not inserted");
                }
        }
        catch (SellerException e) {
            Assert.fail("Seller does not match");
        }
    }

    @Test
    public void sellerTest2(){
//  Adding a duplicate seller name
        String newSellerName = "seller1";
        Seller expected = new Seller(0, newSellerName);
        Seller expected2 = new Seller(0, newSellerName);

        try {
            sellerDAO.insertSeller(expected);


            if (sellerDAO.sellerExists(newSellerName) == 0) {
                Assert.fail("The expected seller was not inserted");
            }

            sellerDAO.insertSeller(expected2);
        }
        catch (SellerException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void productTest1(){

        String newSellerName = "seller1";
        Seller expectedSeller = new Seller(0, newSellerName);
        String newProductName = "New product 1";
        double newProductPrice = 3.47;
        int newSellerId = 1;
        Product newProduct = new Product(0,newProductName, newProductPrice, newSellerId);

        try {
            sellerDAO.insertSeller(expectedSeller);


            if (sellerDAO.sellerExists(newSellerName) == 1) {
                try {
                    productDAO.insertProduct(newProduct);
                }
                catch (ProductException e) {
                    Assert.fail("Product Exception thrown adding a product.");
                }
            }
        }
        catch (SellerException e) {
            Assert.fail("Seller does not match");
        }
    }

    @Test
    public void productTest2(){

        String newSellerName = "seller1";
        Seller expectedSeller = new Seller(0, newSellerName);
        String newProductName = "";
        double newProductPrice = 3.47;
        int newSellerId = 1;
        Product newProduct = new Product(0,newProductName, newProductPrice, newSellerId);

        try {
            sellerDAO.insertSeller(expectedSeller);


            if (sellerDAO.sellerExists(newSellerName) == 1) {
                try {
                    productDAO.insertProduct(newProduct);
                }
                catch (ProductException e) {
//                    Do nothing and return the test has passed.
                    Assert.assertTrue(true);
                }
            }
        }
        catch (SellerException e) {
            Assert.fail("Seller does not match");
        }
    }

    @Test
    public void productTest3(){

        String newSellerName = "seller1";
        Seller expectedSeller = new Seller(0, newSellerName);
        String newProductName = "Product 1";
        double newProductPrice = -3.47;
        int newSellerId = 1;
        Product newProduct = new Product(0,newProductName, newProductPrice, newSellerId);

        try {
            sellerDAO.insertSeller(expectedSeller);


            if (sellerDAO.sellerExists(newSellerName) == 1) {
                try {
                    productDAO.insertProduct(newProduct);
                }
                catch (ProductException e) {
//                    Do nothing and return the test has passed.
                    Assert.assertTrue(true);
                }
            }
        }
        catch (SellerException e) {
            Assert.fail("Seller does not match");
        }
    }


}
