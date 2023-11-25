package com.groupfour.sps.controllers;

import com.groupfour.sps.models.listing.Order;
import com.groupfour.sps.services.PaymentService;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * <h1>Payment Controller</h1>
 * This controller handles the transactional information withe the paypal api and our client server
 */
@AllArgsConstructor
@RestController
@RequestMapping("/pay")
public class PaymentController extends MainController {

    private final PaymentService payService;

    /**
     * @param order
     * @return
     * @throws PayPalRESTException
     */
    @PostMapping("/checkout")
    public Payment payment(@RequestBody Order order) throws PayPalRESTException {
        return payService.createPayment(order);
    }

    @GetMapping("/success/{payment-id}/{payer-id}")
    public Payment successPayment(@PathVariable("payment-id") String paymentId, @PathVariable("payer-id") String payerId) throws PayPalRESTException {
        return payService.executePayment(paymentId, payerId);
    }


}
