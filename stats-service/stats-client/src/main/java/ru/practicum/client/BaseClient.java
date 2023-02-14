package ru.practicum.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.practicum.EndpointHitDto;
import ru.practicum.ViewStatsDto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class BaseClient {
    private static final String STATS_SERVER_URL = "http://localhost:9090";
    private static RestTemplate template = new RestTemplate();
    public BaseClient(RestTemplate template) {BaseClient.template = template;}

    public ResponseEntity<Object> postHit(EndpointHitDto endpointHitDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EndpointHitDto> request = new HttpEntity<>(endpointHitDto, headers);
        ResponseEntity<EndpointHitDto> response = template.exchange(STATS_SERVER_URL + "/hit",
                HttpMethod.POST, request, EndpointHitDto.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("POST saveHit statusCode: {}", response.getStatusCode());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            log.debug("POST saveHit incorrect statusCode: {}", response.getStatusCode());
            throw new RuntimeException();
        }
    }

    public static List<ViewStatsDto> getStats(String start, String end, List<String> uris, Boolean unique) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));


        Map<String, ?> parameters = parameters(start, end, uris, unique);
        final String path = "?start={start}&end={end}&uris={uris}&unique={unique}";
        HttpEntity<List<ViewStatsDto>> request = new HttpEntity<>(null, headers);
        ResponseEntity<List<ViewStatsDto>> response = template.exchange(
                STATS_SERVER_URL + "/stats" + path,
                HttpMethod.GET, request,
                new ParameterizedTypeReference<>() {
                },
                parameters
        );
        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("GET statusCode: {}", response.getStatusCode());
            return response.getBody();
        } else {
            log.debug("GET statusCode: {}", response.getStatusCode());
            throw new RuntimeException();
        }
    }

    private static Map<String, ?> parameters(String start, String end, List<String> uris, Boolean unique) {
        String urisSt = uris.stream()
                .map(uri -> uri.equals(uris.get(uris.size() - 1)) ? uri : uri + ",")
                .collect(Collectors.joining());
        return Map.of(
                "start", start,
                "end", end,
                "uris", urisSt,
                "unique", unique
        );
    }
}
