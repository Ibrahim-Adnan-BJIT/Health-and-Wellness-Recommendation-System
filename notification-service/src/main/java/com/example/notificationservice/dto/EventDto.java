package com.example.notificationservice.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Data
public class EventDto {
    private long id;
    private String name;
    private String venue;
    private String speaker;
    private String topics;
}
