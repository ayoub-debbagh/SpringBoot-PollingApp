package com.polling.polling_api.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "votes")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @ManyToOne()
    @JoinColumn(name = "option_id", nullable = false)
    private Option option;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
