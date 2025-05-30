package org.codewithcuong.hamora.model;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Review {
    private int reviewId;           // review_id
    private int bookingId;          // booking_id
    private int reviewerId;         // reviewer_id
    private int rating;             // rating (0–5)
    private String comment;         // comment text
    private boolean isPublic;       // is_public (bit)
    private LocalDateTime createdAt; // created_at (datetime)

    // for User
    private String fullName;
    private String avatarUrl;
    private String bio;



}
