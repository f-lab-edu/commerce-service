package study.commerceservice.service.assembler;

import study.commerceservice.domain.Address;
import study.commerceservice.domain.Receiver;
import study.commerceservice.domain.ShippingInfo;
import study.commerceservice.controller.dto.ShippingInfoDto;

public class ShippingInfoAssembler {
    public static ShippingInfo toEntity(ShippingInfoDto shippingInfoDto) {
        Address address = Address.builder()
                .zipCode(shippingInfoDto.getZipcode())
                .address1(shippingInfoDto.getAddress1())
                .address2(shippingInfoDto.getAddress2())
                .build();

        Receiver receiver = Receiver.builder()
                .name(shippingInfoDto.getName())
                .clphNo(shippingInfoDto.getClphNo())
                .build();

        return ShippingInfo.builder()
                .message(shippingInfoDto.getMessage())
                .address(address)
                .receiver(receiver)
                .build();
    }

    public static ShippingInfoDto toDto(ShippingInfo shippingInfo) {
        return ShippingInfoDto.builder()
                .message(shippingInfo.getMessage())
                .zipcode(shippingInfo.getAddress().getZipCode())
                .address1(shippingInfo.getAddress().getAddress1())
                .address2(shippingInfo.getAddress().getAddress2())
                .name(shippingInfo.getReceiver().getName())
                .clphNo(shippingInfo.getReceiver().getClphNo())
                .build();
    }
}
