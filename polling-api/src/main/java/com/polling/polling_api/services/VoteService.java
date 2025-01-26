package com.polling.polling_api.services;


import com.polling.polling_api.dtos.OptionDTO;
import com.polling.polling_api.mappers.impl.OptionMapper;
import com.polling.polling_api.models.Option;
import com.polling.polling_api.models.Poll;
import com.polling.polling_api.models.User;
import com.polling.polling_api.models.Vote;
import com.polling.polling_api.repositories.OptionRepository;
import com.polling.polling_api.repositories.PollRepository;
import com.polling.polling_api.repositories.VoteRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VoteService {
    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private OptionMapper optionMapper;


    @Transactional
    public void vote(Long optionId, User user) {

        System.out.println("Option Id : " + optionId);

        Optional<Option> optionalOption = optionRepository.findByIdWithPoll(optionId);

        if(optionalOption.isEmpty()) {
            throw new EntityNotFoundException("Option not found");
        }
        OptionDTO optionDTO = optionMapper.mapToDto(optionalOption.get());

        System.out.println("Option found successfully" + optionDTO);

        Poll poll = pollRepository.findById(optionDTO.getPollId())
                .orElseThrow(() -> new EntityNotFoundException("Poll not found"));

        if(poll.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("This poll has expired");
        }

        if(voteRepository.existsByUserIdandOptionPollId(user.getId(), optionalOption.get().getId())) {
            throw new EntityExistsException("You have already voted in this poll");
        }


        Vote vote = new Vote();
        vote.setUser(user);
        vote.setOption(optionalOption.get());
        Vote createVote = voteRepository.save(vote);

        optionRepository.incrementVoteCount(optionId);

        System.out.println("Vote saved successfully" + vote);
        System.out.println("Option updated successfully" + optionalOption.get());
    }
}
