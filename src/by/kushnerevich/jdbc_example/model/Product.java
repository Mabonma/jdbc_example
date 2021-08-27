package by.kushnerevich.jdbc_example.model;

public class Product extends Entity {

    private String title;
    private double cost;
    private int brandId;

    public Product() {
    }

    public Product(String title, double cost, int brandId) {
        this.title = title;
        this.cost = cost;
        this.brandId = brandId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getBrand_id() {
        return brandId;
    }

    public void setBrand_id(int brandId) {
        this.brandId = brandId;
    }

    public static class Builder {

        private Product product;

        public Builder() {
            product = new Product();
        }

        public Builder setTitle(String title) {
            product.title = title;
            return this;
        }

        public Builder setCost(double cost) {
            product.cost = cost;
            return this;
        }

        public Builder setBrandId(int brandId) {
            product.brandId = brandId;
            return this;
        }

        public Product build(){
            return product;
        }
    }
}
