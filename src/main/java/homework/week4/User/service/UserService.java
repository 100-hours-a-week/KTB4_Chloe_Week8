package homework.week4.User.service;

import homework.week4.User.dto.*;
import homework.week4.User.entity.User;
import homework.week4.User.repository.UserRepository;
import homework.week4.exception.DuplicateResourceException;
import homework.week4.exception.NotFoundException;

import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;


@Service
@Validated
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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
    public SignUpResponseDto createUser(@Valid SignUpRequestDto request) {

        //중복 검사
        emailDuplicateCheck(request.getEmail());
        nicknameDuplicateCheck(request.getNickname());

        LocalDateTime createdDateTime = LocalDateTime.now();

        User user = new User(
                request.getEmail(),
                request.getPassword(),
                request.getNickname(),
                request.getProfile_image(),
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
        User user = getValidUser(userId);

        LocalDateTime updatedDateTime = LocalDateTime.now();

        //여기서 JPA 알아서 변경 감지!!
        user.changeNickname(request.getNickname(),updatedDateTime);
        user.changeProfileImgae(request.getProfileImage());


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
