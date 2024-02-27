import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

class Product {

    String name;
    String maker;


    public Product () {}
    public Product (String name, String maker) {
        this.name = name;
        this.maker = maker;

    }

}