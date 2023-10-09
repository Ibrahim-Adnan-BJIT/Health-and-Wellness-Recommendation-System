package com.example.progress.external;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {

    private long id;

    private long user_id;

    private String address;

    private String mobile;

    private String nationality;

    private String homeDistrict;
}
