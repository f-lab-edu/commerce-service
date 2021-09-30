package study.commerceservice.domain;

import lombok.*;

import javax.persistence.Embeddable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Address {

    private String zipCode;
    private String address1;
    private String address2;
}
