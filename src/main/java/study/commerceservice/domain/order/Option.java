package study.commerceservice.domain.order;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Option {

    @Id
    @GeneratedValue
    @Column(name = "option_id")
    private Long id;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderline_id")
    private OrderLine orderLine;

    public Option(String description) {
        this.description = description;
    }
}