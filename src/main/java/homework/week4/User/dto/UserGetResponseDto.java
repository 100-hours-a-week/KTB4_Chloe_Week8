package homework.week4.User.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class UserGetResponseDto {

    private String email;
    private String nickname;
    private String profileImage;
}
