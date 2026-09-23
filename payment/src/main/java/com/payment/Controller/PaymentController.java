package com.payment.Controller;

import com.payment.Dto.PaymentRequest;
import com.payment.Dto.PaymentResponse;
import com.payment.Service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Tag(
        name = "Payment Management",
        description = "APIs for processing and retrieving payments"
)
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @Operation(
            summary = "Process payment",
            description = "Creates and processes payment for an order"
    )
    public ResponseEntity<PaymentResponse> processPayment(
            @Valid @RequestBody PaymentRequest request) {

        PaymentResponse response =
                paymentService.processPayment(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/order/{orderId}")
    @Operation(
            summary = "Get payment by order ID",
            description = "Returns payment details for the given order"
    )
    public ResponseEntity<PaymentResponse> getPaymentByOrderId(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(
                paymentService.getPaymentByOrderId(orderId)
        );
    }
}