package com.lam.migrationservice.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lam.migrationservice.deserializer.CustomLocalDateTimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteResponseDto {
    private String comments;
    private String guid;
    private LocalDateTime modifiedDateTime;
    private String clientGuid;
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime datetime;
    private String loggedUser;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDateTime;
}
