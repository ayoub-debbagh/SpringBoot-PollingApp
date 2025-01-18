package com.polling.polling_api.repositories;


import com.polling.polling_api.models.Option;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {

    @Query("SELECT o FROM Option o JOIN FETCH o.poll WHERE o.id = :optionId")
    Optional<Option> findByIdWithPoll(@Param("optionId") Long optionId);

    @Modifying
    @Transactional
    @Query("UPDATE Option o SET o.voteCount = o.voteCount + 1 WHERE o.id = :optionId")
    void incrementVoteCount(@Param("optionId") Long optionId);
}
