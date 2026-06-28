package homework.week4.Auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private Long user_id;
    private String link;

    public LoginResponseDto(Long userId) {
        this.user_id = userId;
    }
}
