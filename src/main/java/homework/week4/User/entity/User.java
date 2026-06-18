package homework.week4.User.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    private String email;
    private String password;
    private String nickname;
    private String profile_image;

    //회원 탈퇴 여부의 기본값은 true
    private Boolean is_member = true;

    public User(String email, String password, String nickname,String profile_image,Boolean is_member) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profile_image = profile_image;
        //this.is_member = is_member;
    }

    // 회원 탙퇴 표시 -> soft deleted
    public Boolean deleteMark(){
        this.is_member = false;
        return is_member;
    }


    //닉네임 변경
    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    //프로필 이미지 변경
    public void changeProfileImgae (String profile_image){
        this.profile_image = profile_image;
    }

    //비밀번호 변경
    public void changePassword(String password){
        this.password = password;
    }

}
