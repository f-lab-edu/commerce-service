package study.domain.order;

import javax.persistence.*;

@Entity
public class Option {

    @Id
    @GeneratedValue
    @Column(name = "option_id")
    private Long id;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private OrderLine orderLine;
}
