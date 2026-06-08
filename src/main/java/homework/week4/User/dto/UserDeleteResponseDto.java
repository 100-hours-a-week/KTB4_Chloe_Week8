package homework.week4.User.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class UserDeleteResponseDto {
    private String nickname;
    private Boolean is_member;


}
