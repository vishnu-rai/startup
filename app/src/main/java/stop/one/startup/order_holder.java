package stop.one.startup;

public class order_holder {

    public order_holder(){}

    String prod_name,prod_brand,prod_price,Delivered;

    public order_holder(String prod_name, String prod_brand, String prod_price, String delivered) {
        this.prod_name = prod_name;
        this.prod_brand = prod_brand;
        this.prod_price = prod_price;
        Delivered = delivered;
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

    public void setProd_price(String prod_prce) {
        this.prod_price = prod_prce;
    }

    public String getDelivered() {
        return Delivered;
    }

    public void setDelivered(String delivered) {
        Delivered = delivered;
    }
}
