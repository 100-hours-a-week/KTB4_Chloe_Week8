package homework.week4.User.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDto {

    private String email;
    private String nickname;
    private String profile_image;

    private Boolean is_member;


    public UserResponseDto(String email,String nickname,String profile_image){
        this.email = email;
        this.nickname = nickname;
        this.profile_image = profile_image;
    }

    public UserResponseDto( String nickname, Boolean is_member){
        this.is_member = is_member;
        this.nickname = nickname;
    }
}