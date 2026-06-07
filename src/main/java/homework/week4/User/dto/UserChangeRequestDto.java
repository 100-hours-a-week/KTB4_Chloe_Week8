package homework.week4.User.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


@Getter
@NoArgsConstructor
public class UserChangeRequestDto {


    @NotBlank(message = "닉네임은 필수값입니다.")
    @Pattern(regexp = "[^\\s].{1,10}",
            message = "닉네임은 최대 10자입니다.")
    private String nickname;

    private String profile_image;

}