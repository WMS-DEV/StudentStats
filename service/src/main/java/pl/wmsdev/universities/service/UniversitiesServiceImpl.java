package pl.wmsdev.universities.service;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.wmsdev.security.JwtService;
import pl.wmsdev.universities.dto.UniversityDisplayable;
import pl.wmsdev.universities.dto.UsosUniversityAuthenticationDetails;
import pl.wmsdev.universities.exception.UnsupportedUniversityException;
import pl.wmsdev.usos4j.model.common.UsosLocalizedString;
import pl.wmsdev.utils.internationalization.LocalizedMessageServiceImpl;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class UniversitiesServiceImpl implements UniversitiesService {

	private final JwtService jwtService;
	private final LocalizedMessageServiceImpl msgService;
	private final List<UsosUniversityAuthenticationDetails> detailedUniversities;

	public UniversitiesServiceImpl(@Value("${supported-universities.path}") String supportedUniversitiesPath,
	                               JwtService jwtService, LocalizedMessageServiceImpl msgService) {
		this.jwtService = jwtService;
		this.msgService = msgService;
		this.detailedUniversities = loadUniversitiesDetailed(supportedUniversitiesPath);
	}

	@SneakyThrows
	private List<UsosUniversityAuthenticationDetails> loadUniversitiesDetailed(String supportedUniversitiesPath) {
		try (Reader reader = Files.newBufferedReader(getPath(supportedUniversitiesPath))) {
			return new CsvToBeanBuilder<UsosUniversityAuthenticationDetails>(reader)
					.withType(UsosUniversityAuthenticationDetails.class)
					.build()
					.parse();
		}
	}

	@SneakyThrows
	private Path getPath(String resourcePath) {
		return Paths.get(Thread.currentThread().getContextClassLoader().getResource(resourcePath).toURI());
	}

	@Override
	public List<UsosUniversityAuthenticationDetails> getUniversitiesDetailed() {
		return detailedUniversities;
	}

	@Override
	public List<UniversityDisplayable> getUniversitiesDisplayable() {
		return detailedUniversities.stream()
				.map(detailedUni -> UniversityDisplayable.builder()
					.id(detailedUni.getId())
					.name(getLocalizedUniversityName(detailedUni))
					.build()
		).toList();
	}

	private String getLocalizedUniversityName(UsosUniversityAuthenticationDetails detailedUniversity) {
		switch (msgService.getLanguageFromContextOrDefault()) {
			case EN -> { return detailedUniversity.getEn(); }
			default -> { return detailedUniversity.getPl(); }
		}
	}

	@Override
	public String getUsersUniversityName() {
		String universityId = jwtService.getUsersUniversityId();
		var university = getDetailedUniversity(universityId);

		return msgService.getLocalized(new UsosLocalizedString(university.getPl(), university.getEn()));
	}

	@Override
	public String getUsersUniversityId() {
		return jwtService.getUsersUniversityId();
	}

	@Override
	public UsosUniversityAuthenticationDetails getDetailedUniversity(String id) {
		var university = detailedUniversities.stream().filter(uni -> uni.getId().equals(id)).findFirst();

		if (university.isEmpty()) {
			throw new UnsupportedUniversityException(id);
		}

		return university.get();
	}
}
