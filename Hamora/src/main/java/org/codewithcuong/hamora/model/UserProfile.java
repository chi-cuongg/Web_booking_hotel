package org.codewithcuong.hamora.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {
    private int profileId;
    private int userId;
    private String avatarUrl;
    private String bio;
    private String address;
}
