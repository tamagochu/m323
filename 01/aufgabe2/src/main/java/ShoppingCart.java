import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<String> cart;
    private boolean bookAdded;

    public ShoppingCart() {
        this.cart = new ArrayList<>();
        this.bookAdded = false;
    }

    public void addItem(String item) {
        cart.add(item);
        if (item.toLowerCase().contains("buch")) {
            bookAdded = true;
        }
    }

    public void removeItem(String item) {
        cart.remove(item);
        if (item.toLowerCase().contains("buch")) {
            bookAdded = cart.stream().anyMatch(i -> i.toLowerCase().contains("buch"));
        }
    }

    public List<String> getItems() {
        return new ArrayList<>(cart);
    }

    public double getDiscountPercentage() {
        return bookAdded ? 5.0 : 0.0;
    }
}