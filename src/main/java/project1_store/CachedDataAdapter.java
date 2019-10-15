package project1_store;

import java.util.HashMap;
import java.util.Map;

public class CachedDataAdapter implements IDataAccess { //implements IDataAccess {
    Map<Integer, ProductModel> cachedProducts = new HashMap<>();
    Map<Integer, CustomerModel> cachedCustomers = new HashMap<>();
    Map<Integer, PurchaseModel> cachedPurchases = new HashMap<>();
    IDataAccess adapter;
    int errorCode = 0;

    public CachedDataAdapter(IDataAccess adapter) {
        this.adapter = adapter;
    }

    @Override
    public boolean connect(String path) {
        return this.adapter.connect(path);
    }

    @Override
    public boolean disconnect() {
        return false;
    }

    @Override
    public int getErrorCode() {
        return this.adapter.getErrorCode();
    }

    @Override
    public String getErrorMessage() {
        //return null;
        switch (errorCode) {
            case CONNECTION_OPEN_FAILED:
                return "Connection is not opened!";
            case PRODUCT_LOAD_FAILED:
                return "Cannot load the product!";
        };
        return "OK";
    }

    // load products
    @Override
    public ProductModel loadProduct(int id) {
        if (cachedProducts.containsKey(id))
            return cachedProducts.get(id);
        else {
            ProductModel product = adapter.loadProduct(id);
            cachedProducts.put(id, product);
            return product;
        }
    }

    @Override
    public boolean saveProduct(ProductModel product) {
        adapter.saveProduct(product);
        cachedProducts.put(product.mProductID, product);
        return true;
    }

    // load customers
    @Override
    public CustomerModel loadCustomer(int id) {
        if (cachedCustomers.containsKey(id))
            return cachedCustomers.get(id);
        else {
            CustomerModel customer = adapter.loadCustomer(id);
            cachedCustomers.put(id, customer);
            return customer;
        }
    }

    @Override
    public boolean saveCustomer(CustomerModel customer) {
        adapter.saveCustomer(customer);
        cachedCustomers.put(customer.mCustomerID, customer);
        return true;
    }

    @Override
    public PurchaseModel loadPurchase(int id) {
        if (cachedPurchases.containsKey(id))
            return cachedPurchases.get(id);
        else {
            PurchaseModel purchases = adapter.loadPurchase(id);
            cachedPurchases.put(id, purchases);
            return purchases;
        }
    }

    @Override
    public boolean savePurchase(PurchaseModel purchase) {
        adapter.savePurchase(purchase);
        cachedPurchases.put(purchase.mPurchaseID, purchase);
        return true;
    }
}
