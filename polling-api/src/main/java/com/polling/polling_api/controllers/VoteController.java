package com.polling.polling_api.controllers;


import com.polling.polling_api.models.User;
import com.polling.polling_api.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/votes")
public class VoteController {
    @Autowired
    private VoteService voteService;

    @PostMapping("/{optionId}")
    public ResponseEntity<String> vote (@PathVariable Long optionId, @AuthenticationPrincipal User user) {
        if(user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        voteService.vote(optionId, user);
        return ResponseEntity.ok("You have successfully voted");
    }
}
