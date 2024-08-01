package com.ead.payment.service;


import com.ead.payment.dtos.PaymentRequestDto;
import com.ead.payment.models.PaymentModel;
import com.ead.payment.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface PaymentService {
    PaymentModel requestPayment(PaymentRequestDto paymentRequestDto, UserModel userModel);

    Optional<PaymentModel> findLastPaymentByUser(UserModel userModel);

    Page<PaymentModel> findAllByUser(Specification<PaymentModel> spec, Pageable pageable);
}
