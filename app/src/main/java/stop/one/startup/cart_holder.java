package stop.one.startup;

public class cart_holder {

    public cart_holder(){}

    String prod_name,prod_brand,prod_price;

    public cart_holder(String prod_name, String prod_brand, String prod_price) {
        this.prod_name = prod_name;
        this.prod_brand = prod_brand;
        this.prod_price = prod_price;
    }

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public String getProd_brand() {
        return prod_brand;
    }

    public void setProd_brand(String prod_brand) {
        this.prod_brand = prod_brand;
    }

    public String getProd_price() {
        return prod_price;
    }

    public void setProd_price(String prod_price) {
        this.prod_price = prod_price;
    }
}
