package com.groupfour.snb.controllers;

import com.groupfour.snb.models.listing.Order;
import com.groupfour.snb.services.PaymentService;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/pay")
public class PaymentController extends MainController {


    private final PaymentService payService;

    @PostMapping("/checkout")
    public Payment payment(@RequestBody Order order) throws PayPalRESTException {
        return payService.createPayment(order);
    }

    @GetMapping("/success/{payment-id}/{payer-id}")
    public Payment successPayment(@PathVariable("payment-id") String paymentId, @PathVariable("payer-id") String payerId) throws PayPalRESTException {
        return payService.executePayment(paymentId, payerId);
    }


}
