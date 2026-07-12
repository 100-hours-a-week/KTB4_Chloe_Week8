package homework.week4.User.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
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

    @Column(name="created_at")
    private LocalDateTime createdAt;
    @Column(name="updated_at")
    private LocalDateTime updatedAt;
    @Column(name="deleted_at")
    private LocalDateTime deletedAt;

    public User(String email, String password, String nickname,String profileImage,LocalDateTime createdAt) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.createdAt = createdAt;
    }

    // 회원 탙퇴 표시 -> soft deleted
    public Boolean deleteMark(LocalDateTime deletedAt){
        this.isMember = false;
        this.deletedAt = deletedAt;
        return isMember;
    }


    //닉네임 변경 + 정보 수정 시각도 변경
    public void changeNickname(String nickname,LocalDateTime updatedAt) {

        this.nickname = nickname;
        this.updatedAt = updatedAt;
    }

    //프로필 이미지 변경
    public void changeProfileImage(String profileImage){
        this.profileImage = profileImage;
    }

    //비밀번호 변경 + 정보 수정 시각도 변경
    public void changePassword(String password,LocalDateTime updatedAt){
        this.password = password;
        this.updatedAt = updatedAt;
    }

}
