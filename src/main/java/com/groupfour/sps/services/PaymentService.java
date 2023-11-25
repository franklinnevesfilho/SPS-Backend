package com.groupfour.sps.services;

import com.groupfour.sps.models.listing.Listing;
import com.groupfour.sps.models.listing.Order;
import com.groupfour.sps.models.user.User;
import com.groupfour.sps.repositories.UserRepository;
import com.groupfour.sps.utils.responses.Response;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PaymentService extends MainService{
    private final static String SUCCESS_URL = "";
    private final static String CANCEL_URL = "";
    private final UserRepository userRepository;
    private final APIContext context;

    public Response checkoutCart(String userId){
        Optional<User> user = userRepository.findById(userId);
        List<Transaction> transactions = new LinkedList<>();
        List<String> errors = new LinkedList<>();
        if(user.isPresent()){
            user.get().getCart().forEach((listing ->{
                Transaction transaction = new Transaction();
                transaction.setAmount(getAmount(listing.getPrice()));
                transaction.setPayee(getPayee(listing));
                transactions.add(transaction);
            }));
        }else{
            errors.add("User not found");
        }

        return Response.builder().node(mapToJson(transactions)).errors(errors).build();
    }

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

    private Payee getPayee(Listing listing){
        return new Payee().setEmail(listing.getSeller().getEmail());
    }
    private Amount getAmount(Double price){
        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(String.format("%.2f", price));
        return amount;
    }
}
