package hello.java8Factory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ElectronicsFactory {
    final static Map<String, Supplier<Product>> map = new HashMap<>();
    static {
        map.put("phone", Phone::new);
        map.put("computer", Computer::new);
    }

    public static Product getProduct(String name) {
        Supplier<Product> productSupplier = map.get(name);
        if (productSupplier == null) {
            throw new IllegalArgumentException("no such product");
        } else {
            return productSupplier.get();
        }
    }
}
