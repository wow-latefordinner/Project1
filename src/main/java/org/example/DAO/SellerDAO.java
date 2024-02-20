package org.example.DAO;

import org.example.Exception.SellerException;
import org.example.Model.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class SellerDAO {

    Connection conn;

    public SellerDAO(Connection conn){
        this.conn = conn;
    }

    public void insertSeller(Seller seller) throws SellerException {

        if (sellerExists(seller.getSeller()) == 0) {
            try {
                PreparedStatement ps1 = conn.prepareStatement(
                        "insert into seller(seller) values (?)");
                ps1.setString(1, seller.getSeller());
                ps1.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            throw new SellerException("Seller already exists");
        }
    }

    public List<Seller> getAllSellers(){
        List<Seller> sellerResults = new ArrayList<>();
        try {
            PreparedStatement ps2 = conn.prepareStatement(
                    "select * from seller order by id asc"
            );
            ResultSet rs = ps2.executeQuery();
            while (rs.next()){
               long id = rs.getLong("id") ;
               String sellerName = rs.getString("seller");
               Seller seller = new Seller(id, sellerName);
               sellerResults.add(seller);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sellerResults;
    }

    public int sellerExists(String sellerName){
       int numRecords = 0;
       try {
           PreparedStatement ps = conn.prepareStatement(
                   "select count(*) as numRecords from seller where seller = ?"
           );
           ps.setString(1,sellerName);
           ResultSet rs = ps.executeQuery();
           while (rs.next()){
               numRecords = rs.getInt("numRecords") ;
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return numRecords;
    }

    public int sellerIdExists(int sellerId){
        int numRecords = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "select count(*) as numRecords from seller where id = ?"
            );
            ps.setInt(1,sellerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                numRecords = rs.getInt("numRecords") ;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numRecords;
    }
}
