package pl.wmsdev.usos.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.wmsdev.security.JwtService;
import pl.wmsdev.universities.service.UniversitiesService;
import pl.wmsdev.usos4j.client.Usos;
import pl.wmsdev.usos4j.client.UsosServerAPI;
import pl.wmsdev.usos4j.client.UsosUserAPI;
import pl.wmsdev.usos4j.model.auth.UsosAccessToken;
import pl.wmsdev.usos4j.model.auth.UsosScope;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class UsosFactory {

	@Value("${frontend.url}")
	private String frontendUrl;
	private final UniversitiesService universitiesService;
	private final JwtService jwtService;

	public UsosUserAPI getUsosUserApi(UsosAccessToken accessToken, String universityId) {
		var usos = createUsos(universityId);

		return usos.getUserApi(accessToken);
	}

	public UsosUserAPI getUsosUserApi() {
		var usos = createUsos(jwtService.getUsersUniversityId());

		return usos.getUserApi(jwtService.getAccessToken());
	}

	public UsosServerAPI getUsosServerApi() {
		var usos = createUsos(jwtService.getUsersUniversityId());

		return usos.getServerApi();
	}

	public Usos createUsos(String universityId) {
		return createUsos(universityId, frontendUrl);
	}

	public Usos createUsos(String universityId, String callbackUrl) {
		var university = universitiesService.getDetailedUniversity(universityId);
		var scopes = Set.of(
				UsosScope.GRADES,
				UsosScope.STAFF_PERSPECTIVE,
				UsosScope.STUDIES
		);

		return Usos.builder()
				.baseUrl(university.getBaseUrl())
				.callbackUrl(callbackUrl)
				.consumerKey(university.getConsumerKey())
				.consumerSecret(university.getConsumerSecret())
				.debug(false)
				.scopes(scopes)
				.build();
	}

}
