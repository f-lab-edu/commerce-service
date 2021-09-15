package study.domain.order;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Option {

    @Id
    @GeneratedValue
    @Column(name = "option_id")
    private Long id;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private OrderLine orderLine;

    public Option(String description) {
        this.description = description;
    }
}
