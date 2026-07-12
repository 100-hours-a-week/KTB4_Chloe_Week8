package homework.week4.Auth.dto;

import homework.week4.Security.JWT.JwtToken;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private JwtToken jwtToken;
    private String profileImage;
    private String link;

    public LoginResponseDto(
            JwtToken jwtToken,
            String profileImage
    ) {

        this.jwtToken = jwtToken;
        this.profileImage = profileImage;
    }
}
