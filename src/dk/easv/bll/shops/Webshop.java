package dk.easv.bll.shops;

import dk.easv.be.Country;
import dk.easv.be.Order;
import dk.easv.bll.orders.EmailManager;
import dk.easv.bll.orders.PaymentManager;
import dk.easv.bll.orders.PriceManager;
import dk.easv.bll.orders.WarehouseManager;

public class Webshop extends Shop {
    private EmailManager emailManager = new EmailManager();
    private PaymentManager paymentManager = new PaymentManager();
    private WarehouseManager warehouseManager = new WarehouseManager();
    private PriceManager priceManager = new PriceManager();

    @Override
    public void placeOrder(String productNumber, int quantity, double unitPrice, Country deliveryCountry, String notificationMail) {
        Order order = new Order(productNumber, quantity, unitPrice);
        priceManager.calculateTotalPrice(order);
        priceManager.calculateVAT(order, deliveryCountry);
        paymentManager.withDrawFromCreditCard(order);
        emailManager.sendPaymentReceipt(order, notificationMail);
        warehouseManager.orderFromWarehouse(order);
        emailManager.sendOrderConfirmation(order, notificationMail);
    }
}
