package com.polling.polling_api.controllers;


import com.polling.polling_api.dtos.PollRequestDTO;
import com.polling.polling_api.dtos.PollResponseDTO;
import com.polling.polling_api.mappers.Mapper;
import com.polling.polling_api.models.Poll;
import com.polling.polling_api.models.User;
import com.polling.polling_api.services.PollService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/polls")
public class PollController {
    @Autowired
    private PollService pollService;

    @Autowired
    private Mapper<Poll, PollResponseDTO> pollMapper;

    @PostMapping("/create")
    public ResponseEntity<PollResponseDTO> createPoll(@Valid  @RequestBody PollRequestDTO pollRequest,
                                                      @AuthenticationPrincipal User user) {
        System.out.println("User: " + user);
        if(user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Poll poll = pollService.createPoll(pollRequest, user);

        return ResponseEntity.ok(pollMapper.mapToDto(poll));

    }

    @GetMapping("/public")
    public ResponseEntity<List<PollResponseDTO>> getAllPolls(@AuthenticationPrincipal User user) {
        if(user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<Poll> polls = pollService.getAllPublishedPolls(user);
        return ResponseEntity.ok(polls.stream().map(pollMapper::mapToDto).collect(Collectors.toList()));
    }

    @GetMapping("/user")
    public ResponseEntity<List<PollResponseDTO>> getAllUserPolls(@AuthenticationPrincipal User user) {
        if(user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<Poll> polls = pollService.getAllUserPolls(user);
        return ResponseEntity.ok(polls.stream().map(pollMapper::mapToDto).collect(Collectors.toList()));
    }

    @GetMapping("/{pollId}")
    public ResponseEntity<PollResponseDTO> getPoll(@PathVariable Long pollId, @AuthenticationPrincipal User user) {
        if(user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Poll poll = pollService.getPollById(pollId, user);
        return ResponseEntity.ok(pollMapper.mapToDto(poll));
    }


    @DeleteMapping("/{pollId}")
    public ResponseEntity<String> deletePoll(@PathVariable Long pollId, @AuthenticationPrincipal User user) {
        if(user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }


        pollService.deletePollById(pollId, user);
        return ResponseEntity.ok("Poll deleted successfully");
    }
}
