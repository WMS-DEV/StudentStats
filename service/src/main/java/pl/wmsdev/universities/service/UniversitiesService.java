package pl.wmsdev.universities.service;

import pl.wmsdev.universities.dto.UsosUniversityAuthenticationDetails;
import pl.wmsdev.universities.dto.UniversityDisplayable;

import java.util.List;

public interface UniversitiesService {
    List<UsosUniversityAuthenticationDetails> getUniversitiesDetailed();

    List<UniversityDisplayable> getUniversitiesDisplayable();

    String getUsersUniversityName();

    String getUsersUniversityId();

    UsosUniversityAuthenticationDetails getDetailedUniversity(String id);
}
