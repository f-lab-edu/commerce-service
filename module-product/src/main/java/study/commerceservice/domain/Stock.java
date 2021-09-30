package study.commerceservice.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.commerceservice.domain.common.CommonTimeEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Stock extends CommonTimeEntity {

    @Id @GeneratedValue
    @Column(name = "stock_id")
    private Long id;

    private long quantity;

    public Stock(long quantity) {
        this.quantity = quantity;
    }
}
