package homework.week4.Post.repository;

import homework.week4.Post.entity.PostChangeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChangeRepository extends JpaRepository<PostChangeHistory,Long> {

}
