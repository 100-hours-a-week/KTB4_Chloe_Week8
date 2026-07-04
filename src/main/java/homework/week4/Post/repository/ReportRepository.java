package homework.week4.Post.repository;

import homework.week4.Post.entity.PostReportHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<PostReportHistory,Long> {

    int countByPostPostId (Long PostId);

    boolean existsByUserUserIdAndPostPostId(Long userId, Long postId);

}
