package study.commerceservice.domain.order;

import lombok.*;

import javax.persistence.Embeddable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Receiver {
    private String name;
    private String clphNo;
}
