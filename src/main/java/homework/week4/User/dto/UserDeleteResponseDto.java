package homework.week4.User.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class UserDeleteResponseDto {
    private String nickname;
    private Boolean is_member;

    private String link;


    public UserDeleteResponseDto(String nickname, Boolean isMember) {
        this.nickname = nickname;
        this.is_member = isMember;
    }
}
