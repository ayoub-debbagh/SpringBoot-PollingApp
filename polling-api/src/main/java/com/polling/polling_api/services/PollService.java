package com.polling.polling_api.services;


import com.polling.polling_api.dtos.PollRequestDTO;
import com.polling.polling_api.dtos.PollResponseDTO;
import com.polling.polling_api.dtos.PollResultsDTO;
import com.polling.polling_api.mappers.impl.PollMapper;
import com.polling.polling_api.models.Poll;
import com.polling.polling_api.models.User;
import com.polling.polling_api.repositories.PollRepository;
import com.polling.polling_api.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PollService {
    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PollMapper pollMapper;

    @Transactional
    public PollResponseDTO createPoll(PollRequestDTO pollRequest, User user) {
            Poll poll = new Poll();
            poll.setTitle(pollRequest.getTitle());
            poll.setDescription(pollRequest.getDescription());
            poll.setCreatedBy(user);
            poll.setOptions(pollRequest.getOptions().stream().map(option -> {
                option.setPoll(poll);
                return option;
            }).collect(Collectors.toList()));
            poll.setIsPublished(true);
            poll.setExpirationDate(pollRequest.getExpirationDate());
            Poll createdPoll =  pollRepository.save(poll);
            return pollMapper.mapToDto(createdPoll);
    }

    public Page<PollResponseDTO> getAllPublishedPolls(User user, Pageable pageable) {
        Page<Poll> polls =  pollRepository.findAllPublishedPolls(pageable);
        return polls.map(pollMapper::mapToDto);
    }

    public Page<PollResponseDTO> getAllUserPolls(User user, Pageable pageable) {
        Page<Poll> polls = pollRepository.findAllUserPolls(user.getId(), pageable);
        return polls.map(pollMapper::mapToDto);
    }

    public PollResponseDTO getPollById(Long pollId, User user){
        Poll foundPoll = pollRepository.findById(pollId).orElse(null);
        return pollMapper.mapToDto(foundPoll);
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

    public List<PollResultsDTO> getPollResults(Long pollId) {
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new EntityNotFoundException("Poll not found"));

        return poll.getOptions().stream()
                .map(option -> {
                    PollResultsDTO result = new PollResultsDTO();
                    result.setOptionId(option.getId());
                    result.setOptionText(option.getText());
                    result.setVoteCount(option.getVoteCount());
                    return result;
                })
                .collect(Collectors.toList());
    }

    public Page<PollResponseDTO> searchPolls (String query, Pageable pageable) {
        Page<Poll> polls = pollRepository.searchPolls(query, pageable);
        return polls.map(pollMapper::mapToDto);
    }

}
