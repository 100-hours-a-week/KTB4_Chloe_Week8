package homework.week4.Auth.service;

import homework.week4.Auth.dto.LoginRequestDto;
import homework.week4.Auth.dto.LoginResponseDto;
import homework.week4.User.entity.User;
import homework.week4.User.repository.UserRepository;
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
                .orElseThrow(() -> new IllegalArgumentException("인증되지 않은 사용자 입니다."));

        return new LoginResponseDto(user_id);
    }
}
