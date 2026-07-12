package homework.week4.User.repository;
import homework.week4.User.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    boolean existsByNicknameAndUserIdNot(String nickname, Long userId);

    Optional<User> findByuserIdAndIsMemberTrue(Long userId);

    Optional<User> findByEmailAndIsMemberTrue(String email);

    boolean existsByuserIdAndIsMemberTrue(Long userId);

    Optional<User> findByEmail(String email);





}
