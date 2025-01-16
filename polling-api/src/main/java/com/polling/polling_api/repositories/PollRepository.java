package com.polling.polling_api.repositories;

import com.polling.polling_api.models.Poll;
import com.polling.polling_api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {
    @Query("SELECT p FROM Poll p WHERE p.isPublished = true")
    List<Poll> findAllPublishedPolls();

    @Query("SELECT p FROM Poll p WHERE p.createdBy.id = ?1")
    List<Poll> findAllUserPolls(Long userId);
}
