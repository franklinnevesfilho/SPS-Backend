package com.groupfour.sps.services;

import com.groupfour.sps.models.listing.Listing;
import com.groupfour.sps.models.listing.Order;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PaymentService extends MainService{
    private final static String SUCCESS_URL = "";
    private final static String CANCEL_URL = "";

    private final APIContext context;

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(context, paymentExecute);
    }

    public Payment createPayment(Order order) throws PayPalRESTException {
        Amount amount = new Amount();
        Transaction transaction = new Transaction();
        Payer payer = new Payer();
        Payment payment = new Payment();


        amount.setCurrency(order.getCurrency());
        // show only up to 2 decimal places
        amount.setTotal(String.format("%.2f", order.getPrice()));

        transaction.setDescription(order.getDescription());
        transaction.setAmount(amount);

        payer.setPaymentMethod(order.getMethod());

        payment.setIntent(order.getIntent());

        payment.setPayer(payer);
        payment.setTransactions(List.of(transaction));

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(CANCEL_URL);
        redirectUrls.setReturnUrl(SUCCESS_URL);

        payment.setRedirectUrls(redirectUrls);

        return payment.create(context);
    }

    public List<Transaction> getTransactions(List<Listing> cart){
        List<Transaction> transactions = new LinkedList<>();

        for(Listing listing: cart){
            Amount amount = getAmount(listing.getPrice());
            Transaction transaction = new Transaction();

            transaction.setDescription(listing.getDescription());
            transaction.setAmount(amount);
            transactions.add(transaction);
        }

        return transactions;
    }

    private Amount getAmount(Double price){
        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(String.format("%.2f", price));
        return amount;
    }
}
