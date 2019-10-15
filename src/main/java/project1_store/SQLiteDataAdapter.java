package project1_store;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDataAdapter implements IDataAccess {
    Connection conn = null;
    int errorCode = 0;

    public boolean connect(String path) {
        try {
            // db parameters
            //String url = "jdbc:sqlite:" + path;
            String url = "jdbc:sqlite:" + path;
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            errorCode = CONNECTION_OPEN_FAILED;
            return false;
        }

    }

    @Override
    public boolean disconnect() {
        return true;
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

    public ProductModel loadProduct(int productID) {
        try {
            ProductModel product = new ProductModel();

            String sql = "SELECT ProductId, Name, Price, Quantity FROM Products WHERE ProductId = " + productID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            product.mProductID = rs.getInt("ProductId");
            product.mName = rs.getString("Name");
            product.mPrice = rs.getDouble("Price");
            product.mQuantity = rs.getDouble("Quantity");
            return product;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            errorCode = PRODUCT_LOAD_FAILED;
            return null;
        }
    }

    public CustomerModel loadCustomer(int id) {
        try {
            CustomerModel customer = new CustomerModel();

            String sql = "SELECT * FROM Customers WHERE CustomerID = " + id;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            customer.mCustomerID = rs.getInt("CustomerID");
            customer.mName = rs.getString("Name");
            customer.mPhone = rs.getString("Phone");
            customer.mAddress = rs.getString("Address");
            return customer;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            errorCode = CUSTOMER_LOAD_FAILED;
            return null;
        }
    }

    @Override
    public boolean saveCustomer(CustomerModel customer) {
        try {
            String sql = "INSERT INTO Customers (CustomerID, Name, Phone, Address)" +
                    "VALUES(" + customer.mCustomerID + ",'" + customer.mName + "','" + customer.mPhone + "','" + customer.mAddress
                    + "')";
            System.out.println(sql);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.print(rs);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    @Override
    public PurchaseModel loadPurchase(int id) {
        return null;
    }

    @Override
    public boolean savePurchase(PurchaseModel purchase) {
        try {
            String sql = "INSERT INTO Orders (OrderID, CustomerID, ProductID, Quantity, TotalCost)" +
                    "VALUES(" + purchase.mPurchaseID + ",'" + purchase.mProductID + "',"
                    + purchase.mCustomerID + ',' + purchase.mQuantity + ',' + purchase.mTotalCost
                    + ")";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.print(rs);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //return true;

        return true;
    }

    public boolean saveProduct(ProductModel product) {
        try {
            String sql = "INSERT INTO Products (ProductID, Name, Price, Quantity)" +
                    "VALUES(" + product.mProductID + ",'" + product.mName + "'," + product.mPrice + ',' + product.mQuantity
                    + ")";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.print(rs);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

}

