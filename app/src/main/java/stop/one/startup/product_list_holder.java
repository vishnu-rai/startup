package stop.one.startup;

public class product_list_holder {

    public product_list_holder(){}

    String Image,Name,Price,Brand,Description;

    public product_list_holder(String image, String name, String price, String brand, String description) {
        Image = image;
        Name = name;
        Price = price;
        Brand = brand;
        Description = description;
    }

    public product_list_holder(String image, String name, String price, String brand) {
        Image = image;
        Name = name;
        Price = price;
        Brand = brand;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
