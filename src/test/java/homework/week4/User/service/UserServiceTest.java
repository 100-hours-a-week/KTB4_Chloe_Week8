package homework.week4.User.service;

import homework.week4.User.dto.SignUpRequestDto;
import homework.week4.User.entity.User;
import homework.week4.User.repository.UserRepository;
import homework.week4.exception.DuplicateResourceException;
import homework.week4.handler.GlobalExceptionHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private GlobalExceptionHandler globalExceptionHandler;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("사용자의 이메일, 비밀번호,유저 닉네임, 프로필 이미지를 정상적으로 요청하면 회원 가입을 성공한다. ")
    void signUpTest(){

        //준비
        MockMultipartFile profileImage = new MockMultipartFile(
                "profileImage",              // 파라미터 이름 (필드명과 맞추면 좋음)
                "profile.jpg",                // 원본 파일명
                "image/jpeg",                 // 컨텐츠 타입
                "test image content".getBytes() // 실제 파일 내용 (바이트 배열)
        );

        SignUpRequestDto request = new SignUpRequestDto(
                "chloe@test.com",
                "Chloe1234**",
                "chloe",
                profileImage
        );

        given(userRepository.existsByEmail("chloe@test.com")).willReturn(false);
        given(userRepository.existsByNickname("chloe")).willReturn(false);
        given(passwordEncoder.encode("Chloe1234**")).willReturn("encodedPassword");

        //실행
        userService.createUser(request);

        // 검증
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(captor.capture());

        User savedUser = captor.getValue();
        assertThat(savedUser.getEmail()).isEqualTo("chloe@test.com");
        assertThat(savedUser.getNickname()).isEqualTo("chloe");
        assertThat(savedUser.getPassword()).isEqualTo("encodedPassword"); // 암호화된 값으로 들어갔는지까지 확인
    }

    @Test
    @DisplayName("중복된 이메일로 회원가입 요청을 하면 예외가 발생한다.")
    void DuplicateEmailTest(){
        //준비
        MockMultipartFile profileImage = new MockMultipartFile(
                "profileImage",              // 파라미터 이름 (필드명과 맞추면 좋음)
                "profile.jpg",                // 원본 파일명
                "image/jpeg",                 // 컨텐츠 타입
                "test image content".getBytes() // 실제 파일 내용 (바이트 배열)
        );

        SignUpRequestDto request = new SignUpRequestDto(
                "chloe@test.com",
                "Chloe1234**",
                "chloe",
                profileImage
        );

        given(userRepository.existsByEmail("chloe@test.com")).willReturn(true);

        //실행 및 준비
        assertThrows(DuplicateResourceException.class,
                () -> userService.createUser(request));
    }

    @Test
    @DisplayName("중복된 닉네임으로 회원가입 요청을 하면 예외가 발생한다.")
    void DuplicateNicknameTest(){
        //준비
        MockMultipartFile profileImage = new MockMultipartFile(
                "profileImage",              // 파라미터 이름 (필드명과 맞추면 좋음)
                "profile.jpg",                // 원본 파일명
                "image/jpeg",                 // 컨텐츠 타입
                "test image content".getBytes() // 실제 파일 내용 (바이트 배열)
        );

        SignUpRequestDto request = new SignUpRequestDto(
                "chloe@test.com",
                "Chloe1234**",
                "chloe",
                profileImage
        );

        given(userRepository.existsByEmail("chloe@test.com")).willReturn(false);
        given(userRepository.existsByNickname("chloe")).willReturn(true);

        //실행 및 준비
        assertThrows(DuplicateResourceException.class,
                () -> userService.createUser(request));
    }

}
