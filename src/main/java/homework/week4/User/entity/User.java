package homework.week4.User.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class User {

    private Long user_id;

    private String email;
    private String password;
    private String nickname;
    private String profile_image;

    private Boolean is_member;

    public User(String email, String password, String nickname,String profile_image,Boolean is_membwer) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profile_image = profile_image;
        this.is_member = is_membwer;
    }

    public Boolean deleteMark(){
        this.is_member = false;
        return is_member;
    }


    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changeProfileImgae (String profile_image){
        this.profile_image = profile_image;
    }

    public void changePassword(String password){
        this.password = password;
    }

}
