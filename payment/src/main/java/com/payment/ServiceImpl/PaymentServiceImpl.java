package com.payment.ServiceImpl;

import com.payment.Dto.PaymentRequest;
import com.payment.Dto.PaymentResponse;
import com.payment.Entity.Payment;
import com.payment.Enums.PaymentStatus;
import com.payment.Repository.PaymentRepo;
import com.payment.Service.PaymentService;
import com.payment.Utility.Utility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepo paymentRepo;

    @Override
    @Transactional
    public PaymentResponse processPayment(PaymentRequest request) {

        if (paymentRepo.existsByOrderId(request.orderId())) {
            throw new RuntimeException(
                    "Payment already exists for order: " + request.orderId()
            );
        }

        Payment payment = new Payment();

        payment.setOrderId(request.orderId());
        payment.setAmount(request.amount());
        payment.setPaymentMethod(request.paymentMethod());



        payment.setTransactionId(Utility.generayeTransactionId());

        // For now simulate successful payment
        payment.setPaymentStatus(PaymentStatus.SUCCESS);

        Payment savedPayment = paymentRepo.save(payment);

        log.info(
                "Payment successful for order {} with transaction {}",
                savedPayment.getOrderId(),
                savedPayment.getTransactionId()
        );

        return mapToResponse(savedPayment);
    }

    @Override
    public PaymentResponse getPaymentByOrderId(Long orderId) {

        Payment payment = paymentRepo.findByOrderId(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Payment not found for order: " + orderId
                        )
                );

        return mapToResponse(payment);
    }

    private PaymentResponse mapToResponse(Payment payment) {

        return new PaymentResponse(
                payment.getId(),
                payment.getOrderId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getPaymentStatus(),
                payment.getTransactionId(),
                payment.getCreatedAt(),
                payment.getUpdatedAt()
        );
    }
}