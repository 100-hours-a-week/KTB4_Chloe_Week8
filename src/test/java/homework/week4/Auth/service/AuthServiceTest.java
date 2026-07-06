package homework.week4.Auth.service;


import homework.week4.Auth.dto.LoginRequestDto;
import homework.week4.Auth.dto.LoginResponseDto;
import homework.week4.Security.JWT.JwtToken;
import homework.week4.Security.JWT.JwtTokenProvider;
import homework.week4.exception.UnauthorizedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("이메일,비밀번호를 담아 정상적으로 로그인 요청을 하면 성공한다.")
    void loginTest(){

        //준비
        LoginRequestDto request = new LoginRequestDto(
                "chloe@test.com",
                "Chloe1234**"
        );

        // Authentication authenticatioequn = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),rest.getPassword()));
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        "chloe@test.com",
                        "Chloe1234**"
                );

        given(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .willReturn(authentication);

        //JwtToken jwtToken = jwtTokenProvider.createToken(authentication);
        JwtToken expectedToken = new JwtToken("Bearer","accessToken", "refreshToken");
        given(jwtTokenProvider.createToken(authentication)).willReturn(expectedToken);

        //실행
        LoginResponseDto response = authService.LoginUser(request);

        //검증
        assertThat(response.getJwtToken()).isEqualTo(expectedToken);
        verify(jwtTokenProvider,times(1)).createToken(authentication);

    }

    @Test
    @DisplayName("로그인한 이메일이 존재하지 않아서 예외가 발생한다.")
    void login_EmailNotFoundTest(){
        //준비
        LoginRequestDto request = new LoginRequestDto(
                "chloe@test.com",
                "Chloe1234**"
        );
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        "chloe@test.com",
                        "Chloe1234**"
                );

        given(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .willThrow(new BadCredentialsException("User not found"));

        //실횅 및 검증
        assertThrows(UnauthorizedException.class,
                () -> authService.LoginUser(request));
    }
}
