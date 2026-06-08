package homework.week4.User.service;

import homework.week4.User.dto.*;
import homework.week4.User.entity.User;
import homework.week4.User.repository.UserRepository;
import homework.week4.exception.UnauthorizedException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void createUser(@Valid SignUpRequestDto request) {

        User user = new User(
                request.getEmail(),
                request.getPassword(),
                request.getNickname(),
                request.getProfile_image(),
                true
        );

       userRepository.saveUser(user);

    }

    public UserGetResponseDto lookupUser(Long user_id) {

        User user = userRepository.getUser(user_id);
        return new UserGetResponseDto(user.getEmail(),user.getNickname(),user.getProfile_image());
    }

    public UserDeleteResponseDto deleteUser(Long user_id){

        boolean is_member = userRepository.softdeleteUser(user_id);
        User user = userRepository.getUser(user_id);
        return new UserDeleteResponseDto(user.getNickname(),is_member);
    }

    public UserChangeResponseDto changeUser(Long user_id,@Valid UserChangeRequestDto request){
        User user = userRepository.changeInfoUser(
                user_id,
                request.getNickname(),
                request.getProfile_image()
        );

        return new UserChangeResponseDto(user.getNickname(), user.getProfile_image());
    }

    public void changePassWord(Long user_id, @Valid UserPasswordRequestDto request){
        userRepository.changePassword(user_id,request.getPassword());
    }

    //사용자 여부 확인
    public void checkUser(Long user_id){

        if(userRepository.checkUser(user_id)){
            return;
        }

        throw new UnauthorizedException("인증되지 않은 사용자 입니다.");
    }


}
