package com.quiz.backend.repository;

import com.quiz.backend.entity.LeaderboardEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface LeaderboardEntryRepository extends JpaRepository<LeaderboardEntry, UUID> {
    List<LeaderboardEntry> findByQuizIdOrderByScoreDesc(UUID quizId);
}
