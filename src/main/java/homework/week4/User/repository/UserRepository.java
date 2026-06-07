package homework.week4.User.repository;
import homework.week4.User.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@Getter
public class UserRepository {

    private final Map<Long, User> users = new HashMap<>();
    private Long user_id = 0L;


    //사용자 저장
    public Long saveUser(User user){
        user_id ++;
        users.put(user_id,user);

        return user_id;
    }


    public User getUser(Long user_id){
        return users.get(user_id);
    }

    //사용자 삭제 표시
    public boolean softdeleteUser(Long user_id){

        User user = users.get(user_id);
        return user.deleteMark();

    }


    // 사용자 정보 수정
    public User changeInfoUser(Long user_id,String changeNickname,String changeImage){
        User user = users.get(user_id);

        user.changeNickname(changeNickname);
        user.changeProfileImgae(changeImage);

        return users.get(user_id);
    }

    //비밀번호 수정
    public void changePassword(Long user_id, String changePassword){
        User user = users.get(user_id);

        user.changePassword(changePassword);
    }

    //사용자 여부 확인
    public Optional<Long> verifyUser(String email, String password) {

        for (Long user_id : users.keySet()) {

            if (users.get(user_id).getEmail().equals(email) && users.get(user_id).getIs_member()) {
                if (users.get(user_id).getPassword().equals(password)) {
                    return Optional.ofNullable(user_id);
                }
                else{
                    return Optional.empty();
                }
            }
        }

        return Optional.empty();
    }

    //사용자 여부 확인
    public Boolean checkUser(Long user_id){
        if(users.containsKey(user_id)){
            return users.get(user_id).getIs_member();
        }
        return false;
    }

}
