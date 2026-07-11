package homework.week4.User.service;

import homework.week4.FileUpload.FileStorageService;
import homework.week4.User.dto.UserPasswordRequestDto;
import homework.week4.User.entity.User;
import homework.week4.User.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

//회원가입 & 비밀번호 변경에서
@ExtendWith(MockitoExtension.class)
public class UserServicePasswordEncodingTest {
    @Mock
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // 진짜 구현체

    @Mock
    // 이렇게 사용하지 않는 것은 가짜 객체로 만들어 주면 된다..
    private FileStorageService fileStorageService;

    private Long userId;

    @InjectMocks
    private UserService userService;


    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, fileStorageService ,passwordEncoder);

        userId = 1L;

    }

    @Test
    @DisplayName("비밀번호 변경 시 새로운 비밀번호가 암호화되어 저장된다")
    void changePasswordEncodingTest(){
        //준비
        String rawPassword = "newPassword123!";
        User user = User.builder().password("oldEncodedPassword").build();

        given(userRepository.findByuserIdAndIsMemberTrue(userId)).willReturn(Optional.of(user));

        UserPasswordRequestDto request = new UserPasswordRequestDto(rawPassword);

        //실행
        userService.changePassWord(userId, request);

        //검증

        //평문 그대로 저장하지 않았는지
        assertThat(user.getPassword()).isNotEqualTo(rawPassword);
        //평문을 해시하고 저장되어 있는 해시값이랑 같은지 비교
        assertThat(passwordEncoder.matches(rawPassword, user.getPassword())).isTrue();

    }
}
