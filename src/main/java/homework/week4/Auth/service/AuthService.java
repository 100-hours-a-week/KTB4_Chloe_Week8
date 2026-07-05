package homework.week4.Auth.service;

import homework.week4.Auth.dto.LoginRequestDto;
import homework.week4.Auth.dto.LoginResponseDto;
import homework.week4.Security.JWT.JwtToken;
import homework.week4.Security.JWT.JwtTokenProvider;
import homework.week4.User.entity.User;
import homework.week4.User.repository.UserRepository;
import homework.week4.exception.NotFoundException;
import homework.week4.exception.UnauthorizedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
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
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public LoginResponseDto LoginUser (@Valid @RequestBody LoginRequestDto request){
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
            JwtToken jwtToken = jwtTokenProvider.createToken(authentication);
            return new LoginResponseDto(jwtToken);
        } catch (BadCredentialsException e) {
            throw new UnauthorizedException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }




    }
}
