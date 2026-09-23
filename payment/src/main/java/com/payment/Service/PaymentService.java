package com.payment.Service;



import com.payment.Dto.PaymentRequest;
import com.payment.Dto.PaymentResponse;

public interface PaymentService {

    PaymentResponse processPayment(PaymentRequest request);

    PaymentResponse getPaymentByOrderId(Long orderId);
}