package net.ljw.backend.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@Data
public class MemberDto {

    private int id;
    private String email;
    private String password;
}
