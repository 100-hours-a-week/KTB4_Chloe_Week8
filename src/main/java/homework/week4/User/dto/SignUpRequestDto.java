package homework.week4.User.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {

    @Email (message = "이메일 형식이 맞지 않습니다.")
    @NotBlank(message = "이메일은 필수값입니다.")
    private String email;

    @NotBlank (message = "비밀번호는 필수값입니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;

    @NotBlank(message = "닉네임은 필수값입니다.")
    @Pattern(regexp = "[^\\s].{1,10}",
            message = "닉네임은 10자 아하 입니다.")
    private String nickname;

    @NotNull(message = "프로필 이미지는 필수값 입니다.")
    private MultipartFile profile_image;

}