package com.polling.polling_api.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "options")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private Long VoteCount = 0L;

    @ManyToOne()
    @JoinColumn(name = "poll_id", nullable = false)
    @JsonBackReference
    private Poll poll;
}
