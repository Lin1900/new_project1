package project1_store;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TXTDataAdapter implements IDataAccess{ // implements IDataAccess {
    Map<Integer, CustomerModel> customers = new HashMap<Integer, CustomerModel>();
    Map<Integer, ProductModel> products = new HashMap<Integer, ProductModel>();
    Map<Integer, PurchaseModel> purchase = new HashMap<Integer, PurchaseModel>();
    int errorCode = 0;

    public boolean connect(String path) {
        try {
            Scanner scanner = new Scanner(new FileReader(new File(path)));
            ProductModel product = new ProductModel();
            CustomerModel customer = new CustomerModel();
            PurchaseModel purchase = new PurchaseModel();

            while (scanner.hasNext()) {
                product.mProductID = scanner.nextInt(); scanner.nextLine();
                product.mName = scanner.nextLine();
                product.mPrice = scanner.nextDouble();
                product.mQuantity = scanner.nextDouble();

                customer.mCustomerID = scanner.nextInt(); scanner.nextLine();
                customer.mName = scanner.nextLine();
                customer.mAddress = scanner.nextLine();
                customer.mPhone = scanner.nextLine();

                purchase.mPurchaseID = scanner.nextInt();
                purchase.mCustomerID = scanner.nextInt();
                purchase.mProductID = scanner.nextInt();
                purchase.mCost = scanner.nextDouble();
                purchase.mQuantity = scanner.nextDouble();
                //purchase.mTax = scanner.nextDouble();





                //                scanner.next(); // empty line

                System.out.println(product);
                products.put(product.mProductID, product);
                customers.put(customer.mCustomerID, customer);
            }

            scanner.close();
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean disconnect() {
        return false;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        switch (errorCode) {
            case CONNECTION_OPEN_FAILED:
                return "Connection is not opened!";
            case PRODUCT_LOAD_FAILED:
                return "Cannot load the product!";
        };
        return "OK";
    }

    //    @Override
    public ProductModel loadProduct(int id) {
        if (products.containsKey(id))
            return products.get(id);
        else
            return null;
    }

    //    @Override
    public boolean saveProduct(ProductModel product) {

        return false;
    }

    @Override
    public CustomerModel loadCustomer(int id) {
        if (customers.containsKey(id))
            return customers.get(id);
        else
            return null;
    }

    @Override
    public boolean saveCustomer(CustomerModel customer) {
        return false;
    }

    @Override
    public PurchaseModel loadPurchase(int id) {
        if (purchase.containsKey(id))
            return purchase.get(id);
        else
            return null;
    }

    @Override
    public boolean savePurchase(PurchaseModel purchase) {
        return false;
    }

}

