package study.commerceservice.domain.order;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orderer {

    @Id
    @GeneratedValue
    @Column(name = "orderer_id")
    private Long id;

    private String name;
    private String userId;
    private String clphNo;

    @OneToMany(mappedBy = "orderer")
    private List<Order> orders = new ArrayList<>();

    @Builder
    public Orderer(String name, String userId, String clphNo) {
        this.name = name;
        this.userId = userId;
        this.clphNo = clphNo;
    }
}
