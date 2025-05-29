package org.codewithcuong.hamora.model;

import lombok.*;

@ToString
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserProfile {
    private int profileId;
    private int userId;
    private String avatarUrl;
    private String bio;
    private String address;
}
