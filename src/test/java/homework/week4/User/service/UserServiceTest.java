package homework.week4.User.service;

import homework.week4.User.dto.SignUpRequestDto;
import homework.week4.User.dto.UserChangeRequestDto;
import homework.week4.User.dto.UserChangeResponseDto;
import homework.week4.User.dto.UserGetResponseDto;
import homework.week4.User.entity.User;
import homework.week4.User.repository.UserRepository;
import homework.week4.exception.DuplicateResourceException;
import homework.week4.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

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

    @Test
    @DisplayName("로그인 후 발급된 토큰을 헤더에 붙이고 회원 정보 조회를 요청하면 성공한다.")
    void userGetTest(){
        //준비
        Long userId = 1L;
        LocalDateTime createdAt = LocalDateTime.of(2026, 7, 6, 18, 30, 0);

        User user = new User(
            "chloe@test.com",
            "Chloe1234**",
            "chloe",
            "이미지 경로",
             createdAt
        );

        UserGetResponseDto response = new UserGetResponseDto(
                "chloe@test.com",
                "chloe",
                "이미지 경로"
        );

        given(userRepository.findByuserIdAndIsMemberTrue(userId))
                .willReturn(Optional.of(user));


        //실행
        UserGetResponseDto result = userService.lookupUser(userId);

        //검증
        assertThat(result.getEmail()).isEqualTo(response.getEmail());
        assertThat(result.getNickname()).isEqualTo(response.getNickname());
        assertThat(result.getProfileImage()).isEqualTo(response.getProfileImage());

    }

    @Test
    @DisplayName("회원 정보가 존재하지 않는다.")
    void usetGet_NotFound(){
        //준비
        Long userId = 1L;

        given(userRepository.findByuserIdAndIsMemberTrue(userId))
                .willReturn(Optional.empty());

        //실행 및 검증
        assertThrows(NotFoundException.class,
                () -> userService.lookupUser(userId));
    }

    @Test
    @DisplayName("닉네임,이미지를 담아 정상적으로 요청하면 회원 정보 수정 성공한다.")
    void changeUserTest(){
        //준비
        Long userId = 1L;
        LocalDateTime createdAt = LocalDateTime.of(2026, 7, 6, 18, 30, 0);

        User user = new User(
                "chloe@test.com",
                "Chloe1234**",
                "chloe",
                "이미지 경로",
                createdAt
        );

        MockMultipartFile profileImage = new MockMultipartFile(
                "profileImage",              // 파라미터 이름 (필드명과 맞추면 좋음)
                "profile.jpg",                // 원본 파일명
                "image/jpeg",                 // 컨텐츠 타입
                "test image content".getBytes() // 실제 파일 내용 (바이트 배열)
        );

        UserChangeRequestDto request = new UserChangeRequestDto(
                "chloe1",
                profileImage
        );

        UserChangeResponseDto response = new UserChangeResponseDto(
                "chloe1",
                "/UploadPhoto/ProfileImage/profile.jpg"

        );

        given(userRepository.existsByNickname(request.getNickname()))
                .willReturn(false);

        given(userRepository.findByuserIdAndIsMemberTrue(userId))
                .willReturn(Optional.of(user));

        //실행
        UserChangeResponseDto result = userService.changeUser(userId,request);

        //검증
        assertThat(result.getNickname()).isEqualTo(response.getNickname());
        assertThat(result.getProfileImage()).isEqualTo(response.getProfileImage());


    }

    @Test
    @DisplayName("수정 가능한 회원 정보가 존재하지 않아서 예외가 발생한다.")
    void changeUser_NotFoundTest(){
        //준비
        Long userId = 1L;

        MockMultipartFile profileImage = new MockMultipartFile(
                "profileImage",              // 파라미터 이름 (필드명과 맞추면 좋음)
                "profile.jpg",                // 원본 파일명
                "image/jpeg",                 // 컨텐츠 타입
                "test image content".getBytes() // 실제 파일 내용 (바이트 배열)
        );

        UserChangeRequestDto request = new UserChangeRequestDto(
                "chloe1",
                profileImage
        );

        given(userRepository.existsByNickname(request.getNickname()))
                .willReturn(false);
        given(userRepository.findByuserIdAndIsMemberTrue(userId))
                .willReturn(Optional.empty());

        //실행 및 검증
        assertThrows(NotFoundException.class,() -> userService.changeUser(userId,request));


    }

}
