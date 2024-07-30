package com.ead.payment.specifications;

import com.ead.payment.models.PaymentModel;
import com.ead.payment.models.UserModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.UUID;

public class SpecificationTemplate {

    @And({
            @Spec(path = "paymentControl", spec = Equal.class),
            @Spec(path = "valuePaid", spec = Equal.class),
            @Spec(path = "lastDigitsCreditCard", spec = Like.class),
            @Spec(path = "paymentMessage", spec = Like.class)
    })
    public interface PaymentSpec extends Specification<PaymentModel> {}
}
