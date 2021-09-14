package study.domain.order;


import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
public class Orderer {

    @Id
    @GeneratedValue
    @Column(name = "orderer_id")
    private Long id;

    private String name;
    private String userId;
    private String clphNo;

    @OneToMany(mappedBy = "orderer")
    private List<Order> orders;

    public Orderer(String name, String userId, String clphNo) {
        this.name = name;
        this.userId = userId;
        this.clphNo = clphNo;
    }
}
