import org.example.Controller.Project1Controller;
import org.example.Exception.SellerException;
import org.example.Exception.ProductException;
import org.example.Model.Product;
import org.example.Model.Seller;
import org.example.Service.ProductService;
import org.example.Service.SellerService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import util.CommandLine;

import io.javalin.Javalin;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.List;


public class Project1Test {

    SellerService sellerService;
    ProductService productService;

//    Project1Controller project1Controller = new Project1Controller(productService, sellerService);

//    Javalin app = project1Controller.getAPI();
//    HttpClient webClient;
//    ObjectMapper objectMapper;

    @Before
    public void beforeEach() throws InterruptedException{

        sellerService = new SellerService();
        productService = new ProductService(sellerService);


    }

//    @After
//    public void afterEach(){
//
//    }

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
    public void sellerTest1(){

        HashSet<Seller> actual = new HashSet<Seller>();
        Seller expected = new Seller("seller1");

        try {
            sellerService.insertSeller(expected);
            actual = sellerService.getSellerList();

                if (!actual.contains(expected)) {
                    Assert.fail("The expected value is not the same as the actual value");
                }
        }
        catch (SellerException e) {
            Assert.fail("Seller does not match");
        }
    }

    @Test
    public void productTest1(){

        Seller seller = new Seller("seller1");
        long id = 0;
        String expectedName = "First Product";
        double expectedPrice = 3.47;
        String expectedSeller = "seller1";

        Product product = new Product(id, expectedName, expectedPrice, expectedSeller);

        try {
            sellerService.insertSeller(seller);
            productService.insertProduct(product);
//      Assuming there is only 1 product entered for this test even though I will loop through the list
            List<Product> productList = productService.getAllProducts();

            for (int i = 0; i < productList.size(); i++) {
                if (id == productList.get(i).id) {
                    Assert.fail("Product ID not set as expected");
                }
                if (!(expectedName.equals(productList.get(i).name))) {
                    Assert.fail("Product name does not match expected value");
                }
                if (expectedPrice != productList.get(i).price) {
                    Assert.fail("Price is not expected value");
                }
                if (!(expectedSeller.equals(productList.get(i).seller))) {
                    Assert.fail("Seller does not match expected seller");
                }
            }


        }
        catch (SellerException e) {
            Assert.fail("Seller does not match");
        }
        catch (ProductException e2) {
            Assert.fail("Product failed to be added");
        }
    }
    @Test
    public void productTest2(){

        Seller seller = new Seller("seller1");
        long id = 0;
        String expectedName = "First Product";
        double expectedPrice = 3.47;
        String expectedSeller = "seller2";

        Product product = new Product(id, expectedName, expectedPrice, expectedSeller);

        try {
            sellerService.insertSeller(seller);
            productService.insertProduct(product);
//          If this completes, then the test failed since the exception for invalid seller did not get thrown
            Assert.fail("Product incorrectly added.");


        }
        catch (SellerException e) {
            Assert.fail("Seller does not match");
        }
        catch (ProductException e2) {

        }
    }

    @Test
    public void productTest3(){

        Seller seller = new Seller("seller1");
        long id = 0;
        String expectedName = "First Product";
        double expectedPrice = 3.47;
        String updateName = "Updated Product";
        String expectedSeller = "seller1";

        Product product = new Product(id, expectedName, expectedPrice, expectedSeller);

        try {
            sellerService.insertSeller(seller);
            productService.insertProduct(product);
            List<Product> updateProduct = productService.getAllProducts();

            long updateID = updateProduct.get(0).getId();

            try {
                Product updatedProduct = new Product(updateID, updateName, expectedPrice, expectedSeller);
                productService.updateProduct(updateID, updatedProduct);

                if (!productService.getAllProducts().get(0).getName().equals(updateName)) {
                    Assert.fail("Product update test failed");
                }
            }
            catch (ProductException e) {
                Assert.fail("Product update failed");
            }




        }
        catch (SellerException e) {
            Assert.fail("Seller does not match");
        }
        catch (ProductException e2) {

        }
    }

    @Test
    public void productTest4(){

        Seller seller = new Seller("seller1");
        long id = 0;
        String expectedName1 = "First Product";
        double expectedPrice1 = 3.47;
        String expectedSeller1 = "seller1";

        String expectedName2 = "Second Product";
        double expectedPrice2 = 3.95;
        String expectedSeller2 = "seller1";

        Product product = new Product(id, expectedName1, expectedPrice1, expectedSeller1);
        Product product2 = new Product(id, expectedName2, expectedPrice2, expectedSeller2);

        try {
            sellerService.insertSeller(seller);
            productService.insertProduct(product);
            productService.insertProduct(product2);
            List<Product> productList = productService.getAllProducts();
            long deleteId = productList.get(0).getId();

            productService.deleteProduct(deleteId);

            if (productService.getAllProducts().size() != 1) {
                Assert.fail("Product not deleted");

            }
        }
        catch (ProductException e) {
            Assert.fail("Product update failed");
        }
        catch (SellerException e2) {
            Assert.fail("Seller insert failed before we even got to the product insertion");
        }

    }
}
