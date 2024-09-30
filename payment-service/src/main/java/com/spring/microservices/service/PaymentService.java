package com.spring.microservices.service;

import com.spring.microservices.manager.PaymentManager;
import com.spring.microservices.model.Payment;
import com.spring.microservices.model.Payments;
import com.spring.microservices.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final KafkaProducerService kafkaProducerService;

    public Payments processPayment(Payment payment) {
        PaymentManager paymentManager = new PaymentManager();
        Payments payments = generatePayment(payment);
        if(paymentManager.validatePayment(payment)){
            payments.setPaymentStatus("SUCCESS");
        } else {
            payments.setPaymentStatus("FAILED");
        }
        kafkaProducerService.sendMessage("payment-events", payments);
        return paymentRepository.save(payments);
    }

    private Payments generatePayment(Payment payment) {
        Payments payments = new Payments();
        payments.setOrderId(payment.getOrderId());
        payments.setUserId(payment.getUserId());
        payments.setPaymentAmount(payment.getOrderTotal());
        payments.setPaymentStatus("PENDING");
        payments.setPaymentMethod(payment.getPaymentMethod());
        payments.setPaymentDate(Instant.now());
        return payments;
    }

    public Payments retrievePayment(Long orderId) {
        return paymentRepository.findByOrderId(orderId);
    }

    public List<Payments> retrievePaymentByUserId(String userId) {
        return paymentRepository.findByUserId(userId);
    }

    public List<Payments> retrieveAllPayments() {
        return paymentRepository.findAll();
    }

    public Payments updatePayment(Payments payment, Long orderId) {
        Payments availablePayment = retrievePayment(orderId);
        availablePayment.setPaymentStatus(payment.getPaymentStatus());
        return paymentRepository.save(availablePayment);
    }
}
