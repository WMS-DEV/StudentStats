package pl.wmsdev.universities.dto;

import lombok.*;

// WARNING: Should not be sent to frontend, backend development only
// Contains API keys credentials
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UsosUniversityAuthenticationDetails {
    private String id;
    private String pl;
    private String en;
    private String baseUrl;
    private String consumerKey;
    private String consumerSecret;
}
