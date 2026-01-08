package com.substring.chat.coresdk;



import com.substring.chat.config.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Component
public class WebClientSDK {
    private final JwtService jwtService;
    private final RestTemplate restTemplate;

    public WebClientSDK(JwtService jwtService, RestTemplate restTemplate) {
        this.jwtService = jwtService;
        this.restTemplate = restTemplate;
    }


    public <T> T postForEntityDynamic(
            String url,
            Object requestBody,
            ParameterizedTypeReference<T> responseType,
            boolean isAuthenticated) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            if (isAuthenticated) {
                headers.setBearerAuth(jwtService.getAccessToken());
            }

            HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<T> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    responseType
            );

            return response.getBody();

        } catch (Exception e) {
            log.error("POST API failed: {}", url, e);
            throw new RuntimeException("API call failed", e);
        }
    }



    public <T> T getForEntityDynamic(String url, Object requestBody, Class<T> responseType, boolean isAuthenticated) {
        try {
            HttpHeaders headers = new HttpHeaders();
            if (isAuthenticated) {
                headers.setBearerAuth(jwtService.getAccessToken());
            }
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, entity, responseType);
            return response.getBody();
        } catch (Exception e) {
            log.info("Get For Entity Dynamic Exception API: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}



