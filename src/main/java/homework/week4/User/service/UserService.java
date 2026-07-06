package homework.week4.User.service;

import homework.week4.Security.SecurityConfig;
import homework.week4.User.dto.*;
import homework.week4.User.entity.User;
import homework.week4.User.repository.UserRepository;
import homework.week4.exception.DuplicateResourceException;
import homework.week4.exception.NotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;


@Service
@Validated
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //이메일 중복 검사
    public void emailDuplicateCheck(String email){
        if(userRepository.existsByEmail(email)){
            throw new DuplicateResourceException("중복된 이메일이 존재합니다.");
        }
    }

    //닉네임 중복 검사
    public void nicknameDuplicateCheck(String email){
        if(userRepository.existsByNickname(email)){
            throw new DuplicateResourceException("중복된 닉네임이 존재합니다.");
        }
    }

    //사용자 등록
    @Transactional
    public SignUpResponseDto createUser(@Valid @ModelAttribute SignUpRequestDto request) {

        //중복 검사
        emailDuplicateCheck(request.getEmail());
        nicknameDuplicateCheck(request.getNickname());

        LocalDateTime createdDateTime = LocalDateTime.now();

        MultipartFile file = request.getProfile_image();

        String profileImagePath = null;

        if (file != null && !file.isEmpty()) {

            String uploadDir = "src/main/resources/static/UploadPhoto/ProfileImage";

            Path directoryPath = Paths.get(uploadDir).toAbsolutePath();

            Path savePath = directoryPath.resolve(file.getOriginalFilename());

            try {
                Files.createDirectories(directoryPath);
                System.out.println("저장 경로 = " + savePath.toAbsolutePath());

                profileImagePath = "/UploadPhoto/ProfileImage/" + file.getOriginalFilename();

                file.transferTo(savePath.toFile());

                System.out.println("파일 저장 성공");
            } catch (IOException | IllegalStateException e) {
                e.printStackTrace();
                throw new RuntimeException("파일 저장에 실패했습니다.", e);
            }
        }

        String HashPassword = passwordEncoder.encode(request.getPassword());

        User user = new User(
                request.getEmail(),
                HashPassword,
                request.getNickname(),
                profileImagePath,
                createdDateTime
        );
        userRepository.save(user);
        return new SignUpResponseDto(user.getUserId());
    }

    //사용자 여부 확인
    public void checkUser(Long userId){
        if(!userRepository.existsByuserIdAndIsMemberTrue(userId)){
            throw new NotFoundException("해당 사용자가 존재하지 않습니다.");
        }
    }


    //사용자 확인 및 반환
    public User getValidUser(Long userId){
        User user = userRepository.findByuserIdAndIsMemberTrue(userId).orElseThrow(
                () -> new NotFoundException("해당 사용자 정보가 존재하지 않습니다."));

        return user;
    }



    //사용자 조회
    @Transactional(readOnly = true)
    public UserGetResponseDto lookupUser(Long userId){

        User user = getValidUser(userId);

        return new UserGetResponseDto(
                user.getEmail(),
                user.getNickname(),
                user.getProfileImage()
        );

    }



    //사용자 삭제 (soft delete)
    @Transactional
    public UserDeleteResponseDto deleteUser(Long userId){

        User user = getValidUser(userId);
        LocalDateTime deletedDateTime = LocalDateTime.now();

        user.deleteMark(deletedDateTime);
        return new UserDeleteResponseDto(user.getNickname(),user.getIsMember());
    }



    //사용자 정보 수정
    @Transactional
    public UserChangeResponseDto changeUser(Long userId, @Valid UserChangeRequestDto request){
        nicknameDuplicateCheck(request.getNickname());
        User user = getValidUser(userId);

        LocalDateTime updatedDateTime = LocalDateTime.now();

        MultipartFile file = request.getProfileImage();

        String profileImagePath = null;

        if (file != null && !file.isEmpty()) {

            String uploadDir = "src/main/resources/static/UploadPhoto/ProfileImage";
            Path directoryPath = Paths.get(uploadDir).toAbsolutePath();
            Path savePath = directoryPath.resolve(file.getOriginalFilename());

            try {
                Files.createDirectories(directoryPath);
                profileImagePath = "/UploadPhoto/ProfileImage/" + file.getOriginalFilename();
                file.transferTo(savePath.toFile());

            } catch (IOException | IllegalStateException e) {
                throw new RuntimeException("파일 저장에 실패했습니다.", e);
            }
        }

        //여기서 JPA 알아서 변경 감지!!
        user.changeNickname(request.getNickname(),updatedDateTime);
        user.changeProfileImgae(profileImagePath);


        return new UserChangeResponseDto(user.getNickname(), user.getProfileImage());
    }


    //사용자 비밀번호 수정
    @Transactional
    public void changePassWord(Long userId, @Valid UserPasswordRequestDto request){
        User user = getValidUser(userId);
        LocalDateTime updatedDateTime = LocalDateTime.now();

        user.changePassword(request.getPassword(),updatedDateTime);
    }




}
