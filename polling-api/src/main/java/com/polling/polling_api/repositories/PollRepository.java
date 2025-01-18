package com.polling.polling_api.repositories;

import com.polling.polling_api.models.Poll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {
    @Query("SELECT p FROM Poll p WHERE p.isPublished = true")
    Page<Poll> findAllPublishedPolls(Pageable pageable);

    @Query("SELECT p FROM Poll p WHERE p.createdBy.id = :userId")
    Page<Poll> findAllUserPolls(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT p FROM Poll p WHERE (p.title LIKE %:query% OR p.description LIKE %:query%) AND p.isPublished = true")
    Page<Poll> searchPolls(@Param("query") String query, Pageable pageable);
}
