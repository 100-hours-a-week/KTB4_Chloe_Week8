package homework.week4.Post.repository;

import homework.week4.Post.entity.Like;
import homework.week4.Post.entity.Post;
import homework.week4.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRespoitory extends JpaRepository<Like,Long> {


    Long CountByPostPostId (Long postId);

    Optional<Like> findByUserUserIdAndPostPostId(Long userId, Long postId);
}
