package study.commerceservice.common;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.commerceservice.domain.order.PaymentLine;
import study.commerceservice.domain.order.PaymentType;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CommonEntityTest {

    @Autowired
    EntityManager entityManager;

    @Test
    @Transactional
    void commonEntityTest() {
        // given
        LocalDateTime now = LocalDateTime.of(2021, 9, 21, 0, 0, 0);

        PaymentLine paymentLine = new PaymentLine(PaymentType.ACCOUNT);
        entityManager.persist(paymentLine);

        // when
        PaymentLine savedPaymentLine = entityManager.find(PaymentLine.class, 1L);

        // then
        assertThat(savedPaymentLine.getCreateDate()).isAfter(now);
    }
}