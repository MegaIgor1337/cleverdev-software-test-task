package com.lam.migrationservice.service.impl;

import com.lam.migrationservice.dto.*;
import com.lam.migrationservice.scheduler.configuration.MigrationProps;
import com.lam.migrationservice.service.WebService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON;


@Slf4j
@Service
public class RestTemplateService implements WebService {
    @Value("${migration.dateFrom}")
    private LocalDate dateFrom;
    @Value("${migration.dateTo}")
    private LocalDate dateTo;
    @Autowired
    private RestTemplate restTemplateClient;
    @Autowired
    private RestTemplate restTemplateNote;
    @Autowired
    private MigrationProps migrationProps;

    public List<ClientResponseDto> findClients(Set<String> oldClientGuids) {
        HttpHeaders headers = getHeaders();
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<InternalClientsResponse> response = restTemplateClient.exchange(
                migrationProps.getClient().getUrl(),
                HttpMethod.POST,
                requestEntity,
                InternalClientsResponse.class
        );
        if (response.getStatusCode().isError() || response.getBody() == null) {
            log.error("Error during getting clients");
            return Collections.emptyList();
        } else {
            List<ClientResponseDto> clients = response.getBody().getClientResponseDtoList();
            log.info("Founded {} clients", clients);
            return clients.stream().filter(c -> oldClientGuids.contains(c.getGuid()))
                    .collect(Collectors.toList());
        }
    }

    public List<NoteResponseDto> findNotes(List<NoteRequestDto> requests) {
        List<NoteResponseDto> responses = new ArrayList<>();
        requests.forEach(request -> {
            HttpHeaders headers = getHeaders();
            HttpEntity<NoteRequestDto> requestEntity = new HttpEntity<>(request, headers);
            ResponseEntity<InternalNotesResponse> response = restTemplateNote.exchange(
                    migrationProps.getClient().getUrl(),
                    HttpMethod.POST,
                    requestEntity,
                    InternalNotesResponse.class
            );
            if (response.getStatusCode().isError() || response.getBody() == null) {
                log.info("Error during find notes - {}", response.getStatusCode());
            } else {
                responses.addAll(response.getBody().getNoteResponseDtoList());
            }
        });
        log.info("Received {} notes", responses.size());
        return responses;
    }

    public List<NoteRequestDto> createNoteRequests(List<ClientResponseDto> clients) {
        return clients.stream()
                .map(client -> NoteRequestDto.builder()
                        .agency(client.getAgency())
                        .dateFrom(dateFrom)
                        .dateTo(dateTo)
                        .clientGuid(client.getGuid())
                        .build())
                .toList();
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        return headers;
    }
}
