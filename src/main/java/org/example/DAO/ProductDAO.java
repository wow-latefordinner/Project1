package org.example.DAO;

import org.example.Exception.ProductException;
import org.example.Model.Product;
//import org.example.Model.Seller;
//import org.example.Service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    Connection conn;
    SellerDAO sellerDAO;
    public static Logger log = LoggerFactory.getLogger(ProductDAO.class);

    public ProductDAO(Connection conn, SellerDAO sellerDAO) {
        this.conn = conn;
        this.sellerDAO = sellerDAO;
    }

    public List<Product> getAllProducts() {
        List<Product> productResults = new ArrayList<>();
        try {
            PreparedStatement ps2 = conn.prepareStatement(
                    "select * from product order by id asc"
            );
            ResultSet rs = ps2.executeQuery();
            while (rs.next()){
                long id = rs.getLong("id") ;
                String productName = rs.getString("name");
                double productPrice = rs.getDouble("price");
                int sellerId = rs.getInt("seller_id");
                Product product = new Product(id, productName, productPrice, sellerId);
                productResults.add(product);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productResults;
    }

    public List<Product> getProductById(long id) throws ProductException {
        List<Product> productResults = new ArrayList<>();
        if (productExists(id) > 0) {
            try {
                PreparedStatement ps2 = conn.prepareStatement(
                        "select * from product where id = ?"
                );
                ps2.setLong(1, id);
                ResultSet rs = ps2.executeQuery();
                while (rs.next()) {

                    String productName = rs.getString("name");
                    double productPrice = rs.getDouble("price");
                    int sellerId = rs.getInt("seller_id");
                    Product product = new Product(id, productName, productPrice, sellerId);
                    productResults.add(product);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            throw new ProductException("Product does not exist");
        }

        return productResults;
    }

    public int productExists(long productId){
        int numRecords = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "select count(*) as numRecords from product where id = ?"
            );
            ps.setLong(1, productId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                numRecords = rs.getInt("numRecords") ;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numRecords;
    }

    public void insertProduct (Product product) throws ProductException {
//        Assuming a new request, generate a new long product ID
        product.setId(this.getProductId());

        if (checkProductValues(product) && productExists(product.getId())==0) {
            try {
                PreparedStatement ps1 = conn.prepareStatement(
                        "insert into product(id, name, price, seller_id) values (?, ?, ?, ?)");
                ps1.setLong(1, product.getId());
                ps1.setString(2, product.getName());
                ps1.setDouble(3, product.getPrice());
                ps1.setInt(4, product.getSellerId());
                ps1.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
                throw new ProductException("Product Insert failed");
            }
        }
    }

    public boolean checkProductValues(Product product) throws ProductException {
//        Determine if the seller exists before adding product


        if (sellerDAO.sellerIdExists(product.getSellerId()) == 0) {
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

    private long getProductId() {
        log.info("Getting unique Product ID");
        return (long) (Math.random() * Long.MAX_VALUE);
    }
    public void updateProduct (Product product) throws ProductException {
//        Confirm the product exists and all other values are provided

        if (productExists(product.getId())==1 && checkProductValues(product)) {
            try {
                PreparedStatement ps1 = conn.prepareStatement(
                        "update product set name = ?, price = ?, seller_id = ? where id = ?");

                ps1.setString(1, product.getName());
                ps1.setDouble(2, product.getPrice());
                ps1.setInt(3, product.getSellerId());
                ps1.setLong(4, product.getId());
                ps1.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
                log.warn("Product update failed");
                throw new ProductException("Product Update failed");
            }
        }
    }

    public void deleteProduct (long id) throws ProductException {
//        Confirm the product exists and all other values are provided


            try {
                PreparedStatement ps1 = conn.prepareStatement(
                        "delete from product where id = ?");

                ps1.setLong(1, id);
                ps1.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
                log.warn("Product deletion failed");
                throw new ProductException("Product deletion failed");
            }

    }

}
