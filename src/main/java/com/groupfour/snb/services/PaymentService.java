package com.groupfour.snb.services;

import com.groupfour.snb.models.listing.Order;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PaymentService extends MainService{
    private final static String SUCCESS_URL = "";
    private final static String CANCEL_URL = "";

    private final APIContext context;


    public Payment createPayment(Order order) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency(order.getCurrency());
        // show only up to 2 decimal places
        amount.setTotal(String.format("%.2f", order.getPrice()));

        Transaction transaction = new Transaction();
        transaction.setDescription(order.getDescription());
        transaction.setAmount(amount);

        Payer payer = new Payer();
        payer.setPaymentMethod(order.getMethod());

        Payment payment = new Payment();
        payment.setIntent(order.getIntent());

        payment.setPayer(payer);
        payment.setTransactions(List.of(transaction));

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(CANCEL_URL);
        redirectUrls.setReturnUrl(SUCCESS_URL);

        payment.setRedirectUrls(redirectUrls);

        return payment.create(context);
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(context, paymentExecute);
    }

}
