package homework.week4.Auth.service;

import homework.week4.Auth.dto.LoginRequestDto;
import homework.week4.Auth.dto.LoginResponseDto;
import homework.week4.User.entity.User;
import homework.week4.User.repository.UserRepository;
import homework.week4.exception.UnauthorizedException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Validated
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    public LoginResponseDto LoginUser (@Valid @RequestBody LoginRequestDto request){
        Long user_id = userRepository.verifyUser(request.getEmail(), request.getPassword())
                .orElseThrow(() -> new UnauthorizedException("이메일 또는 비밀번호가 틀렸습니다."));

        return new LoginResponseDto(user_id);
    }
}
