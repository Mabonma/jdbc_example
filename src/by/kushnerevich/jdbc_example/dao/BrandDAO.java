package by.kushnerevich.jdbc_example.dao;

import by.kushnerevich.jdbc_example.model.Brand;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BrandDAO extends AbstractDAO<Integer, Brand> {

    private final Connection connection;

    public BrandDAO(final Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Brand> findAll() {
        return executeQuery(new ArrayList<>(), "select * from brand");
    }

    @Override
    public Brand findEntityById(Integer id) {
        Brand result = null;
        try (PreparedStatement statement = connection.prepareStatement("select * from brand where id=(?)")) {
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
        try (PreparedStatement statement = connection.prepareStatement("delete from brand where id = ?")) {
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
    public boolean create(Brand entity) {
        try (PreparedStatement statement=connection.prepareStatement("insert into brand (name) values (?)")){
            setAllParamsToStatement(statement, entity);
            int rs = statement.executeUpdate();
            System.out.println(rs + " row(s) affected by the query");
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Brand update(Brand entity) {
        try (PreparedStatement statement=connection.prepareStatement("update brand set name = ? where id = ?")){
            setAllParamsToStatement(statement, entity);
            int rs = statement.executeUpdate();
            System.out.println(rs + " row(s) affected by query");
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
        return entity;
    }

    private List<Brand> executeQuery(List<Brand> result, String query) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            final ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Brand model = getInitializedBrand(rs);
                result.add(model);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    private Brand getInitializedBrand(ResultSet resultSet) throws SQLException {
        return new Brand.Builder()
                .setName(resultSet.getString("name"))
                .build();
    }

    private void setAllParamsToStatement(PreparedStatement statement, Brand entity)
            throws SQLException {
        statement.setString(1, entity.getName());
        statement.setInt(2, entity.getId());
    }
}
