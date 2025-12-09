package TD;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {

    private final DBConnection dbConnection;

    public DataRetriever(){
        this.dbConnection = new DBConnection();
    }
    public List<Category> getAllCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();

        String sql = "SELECT id, name FROM product_category";

        try (Connection conn = dbConnection.getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Category c = new Category(
                        rs.getInt("id"),
                        rs.getString("name")
                );
                categories.add(c);
            }
        } catch (SQLException e) {
            throw e;
        }

        return categories;
    }

    public List<Product> getProductList(int page, int size) throws SQLException {
        List<Product> products = new ArrayList<>();

        int offset = (page - 1) * size;

        String sql = """
                SELECT p.id AS p_id, p.name AS p_name, p.price, p.creation_datetime,
                       c.id AS c_id, c.name AS c_name
                FROM product p
                LEFT JOIN product_category c ON p.id = c.product_id
                ORDER BY p.id
                LIMIT ? OFFSET ?
                """;

        try (Connection conn = dbConnection.getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, size);
            ps.setInt(2, offset);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Category category = null;

                if (rs.getObject("c_id") != null) {
                    category = new Category(
                            rs.getInt("c_id"),
                            rs.getString("c_name")
                    );
                }

                Product p = new Product(
                        rs.getInt("p_id"),
                        rs.getString("p_name"),
                        rs.getTimestamp("creation_datetime").toInstant(),
                        category
                );

                products.add(p);
            }

        } catch (SQLException e) {
            throw e;
        }

        return products;
    }

    public List<Product> getProductsByCriteria(
            String productName,
            String categoryName,
            Instant creationMin,
            Instant creationMax
    ) throws SQLException {

        List<Product> products = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
                SELECT p.id AS p_id, p.name AS p_name, p.price, p.creation_datetime,
                       c.id AS c_id, c.name AS c_name
                FROM product p
                LEFT JOIN product_category c ON p.id = c.product_id
                WHERE 1=1
                """);

        List<Object> params = new ArrayList<>();

        if (productName != null) {
            sql.append(" AND p.name ILIKE ? ");
            params.add("%" + productName + "%");
        }

        if (categoryName != null) {
            sql.append(" AND c.name ILIKE ? ");
            params.add("%" + categoryName + "%");
        }

        if (creationMin != null) {
            sql.append(" AND p.creation_datetime >= ? ");
            params.add(Timestamp.from(creationMin));
        }

        if (creationMax != null) {
            sql.append(" AND p.creation_datetime <= ? ");
            params.add(Timestamp.from(creationMax));
        }

        try (Connection conn = dbConnection.getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Category category = null;

                if (rs.getObject("c_id") != null) {
                    category = new Category(
                            rs.getInt("c_id"),
                            rs.getString("c_name")
                    );
                }

                Product p = new Product(
                        rs.getInt("p_id"),
                        rs.getString("p_name"),
                        rs.getTimestamp("creation_datetime").toInstant(),
                        category
                );
                products.add(p);
            }

        } catch (SQLException e) {
            throw e;
        }

        return products;
    }

    public List<Product> getProductsByCriteria(
            String productName,
            String categoryName,
            Instant creationMin,
            Instant creationMax,
            int page,
            int size
    ) throws SQLException {

        List<Product> products = new ArrayList<>();
        int offset = (page - 1) * size;

        StringBuilder sql = new StringBuilder("""
                SELECT p.id AS p_id, p.name AS p_name, p.price, p.creation_datetime,
                       c.id AS c_id, c.name AS c_name
                FROM product p
                LEFT JOIN product_category c ON p.id = c.product_id
                WHERE 1=1
                """);

        List<Object> params = new ArrayList<>();

        if (productName != null) {
            sql.append(" AND p.name ILIKE ? ");
            params.add("%" + productName + "%");
        }

        if (categoryName != null) {
            sql.append(" AND c.name ILIKE ? ");
            params.add("%" + categoryName + "%");
        }

        if (creationMin != null) {
            sql.append(" AND p.creation_datetime >= ? ");
            params.add(Timestamp.from(creationMin));
        }

        if (creationMax != null) {
            sql.append(" AND p.creation_datetime <= ? ");
            params.add(Timestamp.from(creationMax));
        }

        sql.append(" ORDER BY p.id LIMIT ? OFFSET ? ");
        params.add(size);
        params.add(offset);

        try (Connection conn = dbConnection.getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Category category = null;
                if (rs.getObject("c_id") != null) {
                    category = new Category(
                            rs.getInt("c_id"),
                            rs.getString("c_name")
                    );
                }

                Product p = new Product(
                        rs.getInt("p_id"),
                        rs.getString("p_name"),
                        rs.getTimestamp("creation_datetime").toInstant(),
                        category
                );
                products.add(p);
            }

        } catch (SQLException e) {
            throw e;
        }

        return products;
    }
}

