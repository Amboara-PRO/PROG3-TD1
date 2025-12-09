import TD.Category;
import TD.DataRetriever;
import TD.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DataRetrieverTest {

    private DataRetriever dataRetriever;
    @BeforeEach
    public void setUp() {
        dataRetriever = new DataRetriever();
    }

    @Test
     void testGetAllCategories() throws SQLException {
        List<Category> categories = dataRetriever.getAllCategories();
        assertNotNull(categories);
        assertEquals(12, categories.size());
    }
    @Test
    void testGetProductsByCriteria() throws SQLException {
        List<Product> products;

        products = dataRetriever.getProductList(1, 10);
        assertNotNull(products);
        assertEquals(10, products.size());

        products = dataRetriever.getProductList(1, 5);
        assertNotNull(products);
        assertEquals(5, products.size());

        products = dataRetriever.getProductList(1, 3);
        assertNotNull(products);
        assertEquals(3, products.size());

        products = dataRetriever.getProductList(2, 2);
        assertNotNull(products);
        assertEquals(2, products.size());
    }

    @Test
    void testGetProductsByCriteriaSimple() throws SQLException {
        List<Product> products;

        products = dataRetriever.getProductsByCriteria("Dell", null, null, null);
        assertNotNull(products);
        assertEquals(2, products.size());

        products = dataRetriever.getProductsByCriteria(null, "info", null, null);
        assertNotNull(products);
        assertEquals(4, products.size());

        products = dataRetriever.getProductsByCriteria("iPhone", "mobile", null, null);
        assertNotNull(products);
        assertEquals(1, products.size());

        products = dataRetriever.getProductsByCriteria(null, null,
                Instant.parse("2024-02-01T00:00:00Z"),
                Instant.parse("2024-03-01T00:00:00Z"));
        assertNotNull(products);
        assertEquals(5, products.size());

        products = dataRetriever.getProductsByCriteria("Samsung", "bureau", null, null);
        assertNotNull(products);
        assertEquals(1, products.size());

        products = dataRetriever.getProductsByCriteria("Sony", "informatique", null, null);
        assertNotNull(products);
        assertEquals(0, products.size());

        products = dataRetriever.getProductsByCriteria(null, "audio",
                Instant.parse("2024-01-01T00:00:00Z"),
                Instant.parse("2024-12-01T00:00:00Z"));
        assertNotNull(products);
        assertEquals(2, products.size());

        products = dataRetriever.getProductsByCriteria(null, null, null, null);
        assertNotNull(products);
        assertEquals(12, products.size());
    }

    @Test
    void testGetProductsByCriteriaWithPagination() throws SQLException {
        List<Product> products;

        products = dataRetriever.getProductsByCriteria(null, null, null, null, 1, 10);
        assertNotNull(products);
        assertEquals(10, products.size());

        products = dataRetriever.getProductsByCriteria("Dell", null, null, null, 1, 5);
        assertNotNull(products);
        assertEquals(2, products.size());

        products = dataRetriever.getProductsByCriteria(null, "informatique", null, null, 1, 10);
        assertNotNull(products);
        assertEquals(4, products.size());
    }
}
