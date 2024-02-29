package org.example.Service;

import org.example.DAO.SellerDAO;
import org.example.Exception.SellerException;
import org.example.Model.Seller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.List;
//import java.util.Set;

/**
 * This class is deprecated.
 */

public class SellerService {

//    List<Seller> sellerList;
    SellerDAO sellerDAO;

    public static Logger log = LoggerFactory.getLogger(SellerService.class);

    public SellerService (SellerDAO sellerDAO){
//        sellerList = new ArrayList<>();
        this.sellerDAO = sellerDAO;
    }

    public void insertSeller(Seller seller) throws SellerException{

        if (seller.getSeller() == null || seller.getSeller().isEmpty()) {
            log.warn("Seller not provided");
            throw new SellerException("No seller provided");
        }

        try {
            sellerDAO.insertSeller(seller);
        }
        catch (SellerException e)
        {
            throw new SellerException("Seller already exists");
        }
    }

    public List<Seller> getSellerList() {
        return sellerDAO.getAllSellers();
    }

    public int sellerExists(String sellerName) {
        return sellerDAO.sellerExists(sellerName);
    }

    public int sellerIdExists(int sellerId) {
        return sellerDAO.sellerIdExists(sellerId);
    }
}
