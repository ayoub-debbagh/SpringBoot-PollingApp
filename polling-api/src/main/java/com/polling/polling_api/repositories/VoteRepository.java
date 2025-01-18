package com.polling.polling_api.repositories;


import com.polling.polling_api.models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Query("SELECT COUNT(v) > 0 FROM Vote v WHERE v.user.id = :userId AND v.option.id = :optionId")
    boolean existsByUserIdandOptionPollId(@Param("userId") Long userId, @Param("optionId") Long optionId);


}
