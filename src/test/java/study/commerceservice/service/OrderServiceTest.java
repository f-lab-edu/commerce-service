package study.commerceservice.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.commerceservice.domain.order.Orderer;

import javax.persistence.EntityManager;

import static study.commerceservice.domain.order.QOrderer.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;
    JPAQueryFactory queryFactory;

    @BeforeEach
    public void init() {
        queryFactory = new JPAQueryFactory(em);
    }

    @Test
    public void ordererTest() {
        Orderer orderer1 = Orderer.builder()
                .name("욱상")
                .userId("today8934")
                .clphNo("01026688161")
                .build();
        System.out.println("orderer1.getName() = " + orderer1.getName());
        
        em.persist(orderer1);

        System.out.println("orderer1.getId() = " + orderer1.getId());
        
        Orderer findOrderer = queryFactory
                .select(orderer)
                .from(orderer)
                .where(orderer.name.eq("욱상"))
                .fetchOne();

        System.out.println("findOrderer.getId() = " + findOrderer.getId());
        Assertions.assertThat(orderer1.getId()).isEqualTo(findOrderer.getId());

    }
}