package homework.week4.User.service;

import homework.week4.User.dto.*;
import homework.week4.User.entity.User;
import homework.week4.User.repository.UserRepository;
import homework.week4.exception.DuplicateResourceException;
import homework.week4.exception.NotFoundException;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


@Service
@Validated
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //이메일 중복 검사
    public void emailDuplicateCheck(String email){
        if(!userRepository.existsByEmail(email)){
            throw new DuplicateResourceException("중복된 이메일이 존재합니다.");
        }
    }

    //닉네임 중복 검사
    public void nicknameDuplicateCheck(String email){
        if(!userRepository.existsByNickname(email)){
            throw new DuplicateResourceException("중복된 닉네임이 존재합니다.");
        }
    }

    //사용자 등록
    @Transactional
    public SignUpResponseDto createUser(String email, String password, String nickname,String  profile_image) {

        //중복 검사
        emailDuplicateCheck(email);
        nicknameDuplicateCheck(nickname);

        User user = new User(email, password, nickname,profile_image);
        userRepository.save(user);
        return new SignUpResponseDto(user.getUserId());
    }


    //사용자 여부 확인
    public User checkUser(Long userId){
        User user = userRepository.findByIdAndIsMemberTrue(userId).orElseThrow(
                () -> new NotFoundException("해당 사용자 정보가 존재하지 않습니다."));

        return user;
    }



    //사용자 조회
    public UserGetResponseDto lookupUser(Long userId){

        User user = checkUser(userId);

        return new UserGetResponseDto(
                user.getEmail(),
                user.getNickname(),
                user.getProfileImage()
        );

    }



    //사용자 삭제 (soft delete)
    @Transactional
    public UserDeleteResponseDto deleteUser(Long userId){

        User user = checkUser(userId);

        user.deleteMark();
        return new UserDeleteResponseDto(user.getNickname(),user.getIsMember());
    }



    //사용자 정보 수정
    @Transactional
    public UserChangeResponseDto changeUser(Long userId, @Valid UserChangeRequestDto request){
        User user = checkUser(userId);

        user.changeNickname(request.getNickname());
        user.changeProfileImgae(request.getProfileImage());


        return new UserChangeResponseDto(user.getNickname(), user.getProfileImage());
    }


    //사용자 비밀번호 수정
    @Transactional
    public void changePassWord(Long userId, @Valid UserPasswordRequestDto request){
        User user = checkUser(userId);
        user.changePassword(request.getPassword());
    }




}
