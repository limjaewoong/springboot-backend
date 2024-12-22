package net.ljw.backend.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Data
public class OrderDto {

    private int id;
    private int memberId;
    private String name;
    private String address;
    private String payment;
    private String cardNumber;
    private String items;
}
