package stop.one.startup;

public class Category_holder {

    public  Category_holder(){}

    String type,image;

//    public Category_holder(String type,String image) {
//        this.type = type;
//        this.image=image;
//    }

    public Category_holder(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
