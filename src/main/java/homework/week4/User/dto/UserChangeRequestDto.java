package homework.week4.User.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserChangeRequestDto {


    @NotBlank(message = "닉네임은 필수값입니다.")
    @Pattern(regexp = "[^\\s].{1,10}",
            message = "닉네임은 최대 10자입니다.")
    private String nickname;

    @NotNull(message = "프로필 이미지는 필수값입니다.")
    private MultipartFile profileImage;

}