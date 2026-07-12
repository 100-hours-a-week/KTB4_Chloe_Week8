package homework.week4.User.service;

import homework.week4.FileUpload.FileStorageService;
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

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;
    private final PasswordEncoder passwordEncoder;

    //이메일 중복 검사
    public void emailDuplicateCheck(String email){
        if(userRepository.existsByEmail(email)){
            throw new DuplicateResourceException("중복된 이메일이 존재합니다.","email");
        }
    }

    //닉네임 중복 검사
    public void nicknameDuplicateCheck(String nickname){
        if(userRepository.existsByNickname(nickname)){
            throw new DuplicateResourceException("중복된 닉네임이 존재합니다.","nickname");
        }
    }

    //사용자 등록
    @Transactional
    public SignUpResponseDto createUser(@ModelAttribute SignUpRequestDto request) {

        //중복 검사
        emailDuplicateCheck(request.getEmail());
        nicknameDuplicateCheck(request.getNickname());

        LocalDateTime createdDateTime = LocalDateTime.now();

        String profileImagePath = fileStorageService.fileStore(request.getProfile_image());

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


    //닉네임 변경 시 중복 검사
    public void nicknameChangeDuplicateCheck(String nickname,Long userId){
        if(userRepository.existsByNicknameAndUserIdNot(nickname,userId)){
            throw new DuplicateResourceException("중복된 닉네임이 존재합니다.","nickname");
        }
    }


    //사용자 정보 수정
    @Transactional
    public UserChangeResponseDto changeUser(Long userId,UserChangeRequestDto request){

        User user = getValidUser(userId);
        nicknameChangeDuplicateCheck(request.getNickname(),userId);

        LocalDateTime updatedDateTime = LocalDateTime.now();

        //여기서 JPA 알아서 변경 감지!!
        user.changeNickname(request.getNickname(),updatedDateTime);


        if(request.getProfileImage() != null && !request.getProfileImage().isEmpty()){
            String profileImagePath = fileStorageService.fileStore(request.getProfileImage());
            user.changeProfileImage(profileImagePath);
        }


        return new UserChangeResponseDto(user.getNickname(), user.getProfileImage());
    }


    //사용자 비밀번호 수정
    @Transactional
    public void changePassWord(Long userId,UserPasswordRequestDto request){
        User user = getValidUser(userId);
        LocalDateTime updatedDateTime = LocalDateTime.now();

        String HashPassword = passwordEncoder.encode(request.getPassword());

        user.changePassword(HashPassword,updatedDateTime);
    }




}
