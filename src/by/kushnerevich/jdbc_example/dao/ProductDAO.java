package by.kushnerevich.jdbc_example.dao;

import by.kushnerevich.jdbc_example.model.Brand;
import by.kushnerevich.jdbc_example.model.Entity;
import by.kushnerevich.jdbc_example.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO extends AbstractDAO<Integer, Product> {

    final Connection connection;

    public ProductDAO(final Connection connection) {
        this.connection = connection;
    }

    @Override
    public List findAll() {
        List<Product> list = new ArrayList();
        try (PreparedStatement statement = connection.prepareStatement("select * from product")) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                list.add(getInitializedBrand(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return list;
    }

    @Override
    public Product findEntityById(Integer id) {
        Product result = null;

        try (PreparedStatement statement = connection.prepareStatement("select * from product where id=?")) {
            statement.setInt(1, id);
            final ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                result = getInitializedBrand(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    @Override
    public boolean delete(Integer id) {
        try (PreparedStatement statement = connection.prepareStatement("delete from product where id = ?")) {
            statement.setInt(1, id);
            int rs = statement.executeUpdate();
            System.out.println(rs + " row(s) affected by the query");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean create(Product entity) {
        try (PreparedStatement statement = connection.prepareStatement("insert into product (title, cost, brand_id)" +
                "values (?, ?, ?)")) {
            setAllParamsToStatement(statement, entity);
            int rs = statement.executeUpdate();
            System.out.println(rs + " row(s) affected by the query");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Product update(Product entity) {
        try (PreparedStatement statement = connection.prepareStatement("update product " +
                "set title = ?, cost = ?, brand_id = ?")) {
            setAllParamsToStatement(statement, entity);
            int rs = statement.executeUpdate();
            System.out.println(rs + " row(s) affected by the query");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return entity;
    }

    public Product getMostExpensiveProduct() {
        Product result = null;

        try (PreparedStatement statement = connection.prepareStatement("select * " +
                "from product " +
                "order by cost desc " +
                "limit 1")) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                result = getInitializedBrand(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    public List<Product> getProductsFromOneBrand(String name){
        List<Product> result = new ArrayList<>();
        String query = "select product.* " +
                "from product inner join brand on product.brand_id = brand.id" +
                "where brand.name = ?";
        try (PreparedStatement statement=connection.prepareStatement(query)){
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                result.add(getInitializedBrand(rs));
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
        return result;
    }

    public List<Product> getProductInCostRange(double min, double max){
        List<Product> result = new ArrayList<>();
        String query="select * from product where cost between ? and ?";
        try (PreparedStatement statement=connection.prepareStatement(query)){
            statement.setDouble(1, min);
            statement.setDouble(2, max);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                result.add(getInitializedBrand(rs));
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
        return result;
    }

    public List<Product> findWithBrands(){
        List<Product> result = new ArrayList<>();
        String query = "select products.* from products inner join brand on products.brand_id = brand.id";
        try (PreparedStatement statement = connection.prepareStatement(query)){
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                result.add(getInitializedBrand(rs));
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    private Product getInitializedBrand(ResultSet resultSet) throws SQLException {
        return new Product.Builder()
                .setTitle(resultSet.getString("title"))
                .setCost(resultSet.getDouble("cost"))
                .setBrandId(resultSet.getInt("brand_id"))
                .build();
    }

    private void setAllParamsToStatement(PreparedStatement statement, Product entity)
            throws SQLException {
        statement.setString(1, entity.getTitle());
        statement.setDouble(2, entity.getCost());
        statement.setInt(3, entity.getBrand_id());
        statement.setInt(4, entity.getId());
    }
}
