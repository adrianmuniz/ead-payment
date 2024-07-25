package com.ead.payment.controller;

import com.ead.payment.dtos.PaymentRequestDto;
import com.ead.payment.enums.PaymentControl;
import com.ead.payment.models.PaymentModel;
import com.ead.payment.models.UserModel;
import com.ead.payment.service.PaymentService;
import com.ead.payment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class PaymentController {

    @Autowired
    UserService userService;

    @Autowired
    PaymentService paymentService;

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/users/{userId}/payments")
    public ResponseEntity<Object> requestPayment(@PathVariable(value="userId") UUID userId,
                                                 @RequestBody @Valid PaymentRequestDto paymentRequestDto){
        Optional<UserModel> userModelOptional = userService.findById(userId);
        if(userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        Optional<PaymentModel> paymentModelOptional = paymentService.findLastPaymentByUser(userModelOptional.get());
        if(paymentModelOptional.isPresent()){
            if(paymentModelOptional.get().getPaymentControl().equals(PaymentControl.REQUESTED)){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Payment already requested.");
            }
            if(paymentModelOptional.get().getPaymentControl().equals(PaymentControl.EFFECTED) &&
                    paymentModelOptional.get().getPaymentExpirationDate().isAfter(LocalDateTime.now(ZoneId.of("UTC")))){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Payment already made.");
            }
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(paymentService.requestPayment(paymentRequestDto, userModelOptional.get()));
    }
}
