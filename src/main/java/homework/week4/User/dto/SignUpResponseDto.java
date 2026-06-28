package homework.week4.User.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpResponseDto {
    private Long user_id;
    private String link;

    public SignUpResponseDto(Long userId) {
        this.user_id = userId;
    }
}
