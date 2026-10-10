package oo_design.class_problems;

import java.util.ArrayList;
import java.util.List;

/**
 * Payment Processing for a Shopping System
 *
 * UML relationships:
 *   PaymentMethod <|.. CreditCardPayment, PayPalPayment, BankTransferPayment   realization
 *   Customer 1 --- 0..* Order          association
 *   Order    1 *-- 1..* OrderItem      composition (items live and die with the order)
 *   OrderItem 0..* --- 1 Product       association
 *   Order ..> PaymentMethod            depends on the abstraction, so a new payment
 *                                      method needs no change to Order or CheckoutService
 */
public class P5_PaymentProcessing {

    enum OrderStatus {
        PENDING("Pending"), PAID("Paid");

        final String label;

        OrderStatus(String label) {
            this.label = label;
        }
    }

    static class Customer {
        private final String name;

        Customer(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }
    }

    static class Product {
        private final String name;
        private final double price;

        Product(String name, double price) {
            this.name = name;
            this.price = price;
        }

        String getName() {
            return name;
        }

        double getPrice() {
            return price;
        }
    }

    static class OrderItem {
        private final Product product;
        private final int quantity;

        OrderItem(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        double getSubtotal() {
            return product.getPrice() * quantity;
        }
    }

    interface PaymentMethod {
        String getName();

        /** Returns true only if the payment went through. */
        boolean processPayment(double amount);
    }

    static class CreditCardPayment implements PaymentMethod {
        private final double creditLimit;

        CreditCardPayment(double creditLimit) {
            this.creditLimit = creditLimit;
        }

        @Override
        public String getName() {
            return "Credit Card";
        }

        @Override
        public boolean processPayment(double amount) {
            return amount <= creditLimit;
        }
    }

    static class PayPalPayment implements PaymentMethod {
        private final double balance;

        PayPalPayment(double balance) {
            this.balance = balance;
        }

        @Override
        public String getName() {
            return "PayPal";
        }

        @Override
        public boolean processPayment(double amount) {
            return amount <= balance;
        }
    }

    static class BankTransferPayment implements PaymentMethod {
        private final double accountBalance;

        BankTransferPayment(double accountBalance) {
            this.accountBalance = accountBalance;
        }

        @Override
        public String getName() {
            return "Bank Transfer";
        }

        @Override
        public boolean processPayment(double amount) {
            return amount <= accountBalance;
        }
    }

    static class Order {
        private final Customer customer;
        private final List<OrderItem> items = new ArrayList<OrderItem>();
        private OrderStatus status = OrderStatus.PENDING;

        Order(Customer customer) {
            this.customer = customer;
        }

        Customer getCustomer() {
            return customer;
        }

        OrderStatus getStatus() {
            return status;
        }

        void addItem(Product product, int quantity) {
            if (quantity <= 0) {
                throw new IllegalArgumentException("Quantity must be at least 1.");
            }
            items.add(new OrderItem(product, quantity));
        }

        boolean isEmpty() {
            return items.isEmpty();
        }

        double getTotal() {
            double total = 0;
            for (OrderItem item : items) {
                total += item.getSubtotal();
            }
            return total;
        }

        /** Marks the order Paid only if the payment succeeds. */
        boolean pay(PaymentMethod method) {
            if (items.isEmpty()) {
                throw new IllegalStateException("Cannot process payment for an empty order.");
            }
            if (status == OrderStatus.PAID) {
                throw new IllegalStateException("Order is already paid.");
            }
            boolean success = method.processPayment(getTotal());
            if (success) {
                status = OrderStatus.PAID;
            }
            return success;
        }
    }

    static class CheckoutService {
        void checkout(Order order, PaymentMethod method) {
            String label = order.getCustomer().getName();
            try {
                if (!order.isEmpty()) {
                    System.out.println("Payment initiated via " + method.getName() + " for Order " + label + ".");
                }
                boolean ok = order.pay(method); // throws for an empty or already-paid order
                if (ok) {
                    System.out.println("Payment for Order " + label + " successful. Order status: "
                            + order.getStatus().label + ".");
                } else {
                    System.out.println("Payment for Order " + label + " failed. Order status: "
                            + order.getStatus().label + ".");
                }
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        CheckoutService checkout = new CheckoutService();
        Product a = new Product("Product A", 20.0);
        Product b = new Product("Product B", 15.0);
        Product c = new Product("Product C", 500.0);

        Customer x = new Customer("X");
        Order orderX = new Order(x);
        orderX.addItem(a, 2);
        orderX.addItem(b, 1);
        System.out.println("Order created for Customer X.");
        checkout.checkout(orderX, new CreditCardPayment(1000.0));

        Customer y = new Customer("Y");
        Order orderY = new Order(y);
        System.out.println("Order created for Customer Y.");
        checkout.checkout(orderY, new CreditCardPayment(1000.0));

        Customer z = new Customer("Z");
        Order orderZ = new Order(z);
        orderZ.addItem(c, 1);
        System.out.println("Order created for Customer Z.");
        checkout.checkout(orderZ, new PayPalPayment(100.0));
    }
}
