package by.kushnerevich.jdbc_example.model;

public class Brand extends Entity{

    private String name;

    public Brand() { }

    public Brand(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "name='" + name + '\'' +
                '}';
    }

    public static class Builder{
        private Brand brand;

        public Builder() { brand=new Brand(); }

        public Builder setName(String name){
            brand.name=name;
            return this;
        }
    }
}
