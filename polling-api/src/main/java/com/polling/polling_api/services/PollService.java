package com.polling.polling_api.services;


import com.polling.polling_api.dtos.PollRequestDTO;
import com.polling.polling_api.models.Poll;
import com.polling.polling_api.models.User;
import com.polling.polling_api.repositories.PollRepository;
import com.polling.polling_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PollService {
    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private UserRepository userRepository;

    public Poll createPoll(PollRequestDTO pollRequest, User user) {
            Poll poll = new Poll();
            poll.setTitle(pollRequest.getTitle());
            poll.setDescription(pollRequest.getDescription());
            poll.setCreatedBy(user);
            poll.setOptions(pollRequest.getOptions().stream().map(option -> {
                option.setPoll(poll);
                return option;
            }).collect(Collectors.toList()));
            poll.setIsPublished(true);
            return pollRepository.save(poll);
    }

    public List<Poll> getAllPublishedPolls(User user) {
        return pollRepository.findAllPublishedPolls();
    }

    public List<Poll> getAllUserPolls(User user) {
        return pollRepository.findAllUserPolls(user.getId());
    }

    public Poll getPollById(Long pollId, User user) {
        return pollRepository.findById(pollId).orElse(null);
    }


    public void deletePollById(Long pollId, User user) {
        Poll poll = pollRepository.findById(pollId).orElse(null);

        if(poll == null) {
            throw new IllegalArgumentException("Poll not found");
        }

        if(!poll.getCreatedBy().getId().equals(user.getId())) {
            throw new IllegalArgumentException("You are not authorized to delete this poll");
        }

        pollRepository.deleteById(pollId);
    }
}
