package homework.week4.User.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    private String email;
    private String password;
    private String nickname;

    @Column(name = "profile_image")
    private String profileImage;

    //회원 탈퇴 여부의 기본값은 true
    @Column(name = "is_member")
    private Boolean isMember = true;

    public User(String email, String password, String nickname,String profileImage) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    // 회원 탙퇴 표시 -> soft deleted
    public Boolean deleteMark(){
        this.isMember = false;
        return isMember;
    }


    //닉네임 변경
    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    //프로필 이미지 변경
    public void changeProfileImgae (String profileImage){
        this.profileImage = profileImage;
    }

    //비밀번호 변경
    public void changePassword(String password){
        this.password = password;
    }

}
