package com.groupfour.sps.controllers;

import com.groupfour.sps.models.interfaces.Validator;
import com.groupfour.sps.models.listing.Order;
import com.groupfour.sps.services.PaymentService;
import com.groupfour.sps.models.Response;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.function.Function;

/**
 * <h1>Payment Controller</h1>
 * This controller handles the transactional
 * information withe the paypal api and our client server
 *
 * @author Chang Fuxinyang
 */
@RestController
@RequestMapping("/pay")
public class PaymentController extends MainController {

    private PaymentService payService;
    private final Function<String, Response> CHECKOUT_CART = (userId) -> payService.checkoutCart(userId);
    private final Function<Validator, Response> CREATE_ORDER = (order) -> payService.createOrder((Order)order);

    public PaymentController(PaymentService payService){
        this.payService = payService;
    }

    @PostMapping("/create-order")
    public ResponseEntity<Response> createOrder(@RequestBody Order order){
        return genericRequest(CREATE_ORDER, order);
    }

    @GetMapping("/checkout-cart")
    public ResponseEntity<Response> checkoutCart(Authentication user){
        return genericGetByParameter(CHECKOUT_CART, user.getName());
    }



//    @PostMapping("/checkout")
//    public Payment payment(@RequestBody Order order) throws PayPalRESTException {
//        return payService.createPayment(order);
//    }

    @GetMapping("/success/{payment-id}/{payer-id}")
    public Payment successPayment(@PathVariable("payment-id") String paymentId, @PathVariable("payer-id") String payerId) throws PayPalRESTException {
        return payService.executePayment(paymentId, payerId);
    }

}
