package com.lam.migrationservice.util;

import com.lam.migrationservice.dto.NoteResponseDto;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.List;

@UtilityClass
public class TestData {

    public static List<NoteResponseDto> getNoteResponseDtoList() {
        return List.of(
                NoteResponseDto.builder()
                        .createdDateTime(LocalDateTime.of(2024, 7, 25, 16, 20, 47))
                        .guid("412ds-fdsfaw31-312sa-1d2aw")
                        .clientGuid("423fsdf-432fdsf-1sadf")
                        .comments("Changed comment")
                        .modifiedDateTime(LocalDateTime.now())
                        .loggedUser("v.ivanov")
                        .build(),
                NoteResponseDto.builder()
                        .createdDateTime(LocalDateTime.of(2022, 7, 25, 16, 20, 47))
                        .guid("412ds-fdsfawsewr1-312sa-1d2aw")
                        .clientGuid("423fsdf-432fdsf-1sadf")
                        .modifiedDateTime(LocalDateTime.of(2022, 7, 25, 16, 20, 47))
                        .comments("New comment")
                        .loggedUser("i.newuser")
                        .build(),
                NoteResponseDto.builder()
                        .createdDateTime(LocalDateTime.of(2022, 7, 25, 16, 20, 47))
                        .guid("41fsddfs-fdsfaw31-gdf12sa-1d2aw")
                        .clientGuid("fosdfs32-12412-412sd-12")
                        .comments("New comment")
                        .modifiedDateTime(LocalDateTime.of(2024, 3, 22,12,29,47))
                        .loggedUser("i.vnykov")
                        .build(),
                NoteResponseDto.builder()
                        .createdDateTime(LocalDateTime.of(2022, 7, 25, 16, 20, 47))
                        .guid("412dfs-fdsfgdfgdf1-gdf12sa-1d2aw")
                        .clientGuid("dsfalfla123-12312-3412-af")
                        .comments("New comment")
                        .modifiedDateTime(LocalDateTime.of(2024, 3, 22,12,29,47))
                        .loggedUser("p.sidarov")
                        .build(),
                NoteResponseDto.builder()
                        .createdDateTime(LocalDateTime.of(2022, 7, 25, 16, 20, 47))
                        .guid("412dfs-fdsfaw31-gdf12sa-1d2aw")
                        .clientGuid("dsfalfla123-12312-3412-af")
                        .comments("Last modified comment")
                        .modifiedDateTime(LocalDateTime.of(2023, 3, 22,12,29,47))
                        .loggedUser("p.sidarov")
                        .build()

        );
    }
}
