package TD;

import java.sql.SQLException;
import java.time.Instant;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        DataRetriever dataRetriever = new DataRetriever();

        System.out.println("=== Categories ===");
        List<Category> categories = dataRetriever.getAllCategories();
        for (Category c : categories) {
            System.out.println(c);
        }

        System.out.println("=== ProductList page 1 size 10 ===");
        for (Product p : dataRetriever.getProductList(1, 10)) {
            System.out.println(p);
        }

        System.out.println("=== ProductList page 1 size 5 ===");
        for (Product p : dataRetriever.getProductList(1, 5)) {
            System.out.println(p);
        }

        System.out.println("=== ProductList page 1 size 3 ===");
        for (Product p : dataRetriever.getProductList(1, 3)) {
            System.out.println(p);
        }

        System.out.println("=== ProductList page 2 size 2 ===");
        for (Product p : dataRetriever.getProductList(2, 2)) {
            System.out.println(p);
        }

        System.out.println("=== Filter by product name: Dell ===");
        for (Product p : dataRetriever.getProductsByCriteria("Dell", null, null, null)) {
            System.out.println(p);
        }

        System.out.println("=== Filter by category: info ===");
        for (Product p : dataRetriever.getProductsByCriteria(null, "info", null, null)) {
            System.out.println(p);
        }


        System.out.println("=== Filter by name : iPhone + category : mobile ===");
        for (Product p : dataRetriever.getProductsByCriteria("iPhone", "mobile", null, null)) {
            System.out.println(p);
        }

        System.out.println("=== Filter by dates ===");
        for (Product p : dataRetriever.getProductsByCriteria(
                null,
                null,
                Instant.parse("2024-02-01T00:00:00Z"),
                Instant.parse("2024-03-01T00:00:00Z")
        )) {
            System.out.println(p);
        }

        System.out.println("=== Filter by name : Samsung + category : bureau ===");
        for (Product p : dataRetriever.getProductsByCriteria("Samsung", "bureau", null, null)) {
            System.out.println(p);
        }

        System.out.println("=== Filter by name : Sony + category : informatique ===");
        for (Product p : dataRetriever.getProductsByCriteria("Sony", "informatique", null, null)) {
            System.out.println(p);
        }

        System.out.println("=== Filter by category + dates ===");
        for (Product p : dataRetriever.getProductsByCriteria(
                null,
                "audio",
                Instant.parse("2024-02-01T00:00:00Z"),
                Instant.parse("2024-12-01T00:00:00Z")
        )) {
            System.out.println(p);
        }


        System.out.println("=== Filter all null ===");
        for (Product p : dataRetriever.getProductsByCriteria(null, null, null, null)) {
            System.out.println(p);
        }

        System.out.println("=== Filter + Pagination 1 ===");
        for (Product p : dataRetriever.getProductsByCriteria(null, null, null, null, 1, 10)) {
            System.out.println(p);
        }

        System.out.println("=== Filter + Pagination 2 ===");
        for (Product p : dataRetriever.getProductsByCriteria("Dell", null, null, null, 1, 5)) {
            System.out.println(p);
        }

        System.out.println("=== Filter + Pagination 3 ===");
        for (Product p : dataRetriever.getProductsByCriteria(null, "informatique", null, null, 1, 10)) {
            System.out.println(p);
        }
    }

}