package homework.week4.Post.repository;

import homework.week4.Post.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRespoitory extends JpaRepository<Like,Long> {


    Long countByPostPostId(Long postId);

    Optional<Like> findByUserUserIdAndPostPostId(Long userId, Long postId);
}
