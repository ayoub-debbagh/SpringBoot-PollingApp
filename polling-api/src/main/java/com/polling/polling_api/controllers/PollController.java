package com.polling.polling_api.controllers;


import com.polling.polling_api.dtos.PollRequestDTO;
import com.polling.polling_api.dtos.PollResponseDTO;
import com.polling.polling_api.dtos.PollResultsDTO;
import com.polling.polling_api.mappers.Mapper;
import com.polling.polling_api.models.Poll;
import com.polling.polling_api.models.User;
import com.polling.polling_api.services.PollService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        if(user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        PollResponseDTO poll = pollService.createPoll(pollRequest, user);

        return ResponseEntity.ok(poll);

    }

    @GetMapping("/public")
    public ResponseEntity<Page<PollResponseDTO>> getAllPolls(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @AuthenticationPrincipal User user
    ) {
        if(user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<PollResponseDTO> polls = pollService.getAllPublishedPolls(user, pageable);
        return ResponseEntity.ok(polls);
    }

    @GetMapping("/user")
    public ResponseEntity<Page<PollResponseDTO>> getAllUserPolls(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @AuthenticationPrincipal User user
    ) {
        if(user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<PollResponseDTO> polls = pollService.getAllUserPolls(user, pageable);

        return ResponseEntity.ok(polls);
    }

    @GetMapping("/{pollId}")
    public ResponseEntity<PollResponseDTO> getPoll(@PathVariable Long pollId, @AuthenticationPrincipal User user) {
        if(user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        PollResponseDTO poll = pollService.getPollById(pollId, user);
        return ResponseEntity.ok(poll);
    }

    @GetMapping("/{pollId}/results")
    public ResponseEntity<List<PollResultsDTO>> getPollResults(@PathVariable Long pollId,
                                                               @AuthenticationPrincipal User user) {
        if(user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<PollResultsDTO> results = pollService.getPollResults(pollId);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PollResponseDTO>> searchPolls (
            @RequestParam String query,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @AuthenticationPrincipal User user
    ) {
        if(user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<PollResponseDTO> polls = pollService.searchPolls(query, pageable);
        return ResponseEntity.ok(polls);
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
