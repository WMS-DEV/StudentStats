package pl.wmsdev.data.service.ects;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.wmsdev.usos.model.Course;
import pl.wmsdev.usos.model.Semester;
import pl.wmsdev.usos4j.client.UsosUserAPI;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class EctsServiceImpl implements EctsService {

	@Async
	@Override
	public CompletableFuture<Void> addEcts(UsosUserAPI userApi, List<Semester> semesters) {
		Map<String, String> allEcts = getCoursesToEctsDictionary(userApi);

		for (Semester semester : semesters) {
			addEctsToCourses(allEcts, semester.courses());
		}

		return CompletableFuture.completedFuture(null);
	}

	private void addEctsToCourses(Map<String, String> ects, List<Course> courses) {
		for (Course course : courses) {
			addEctsToCourse(ects, course);
		}
	}

	private void addEctsToCourse(Map<String, String> ects, Course course) {
		try {
			course.setEcts(convertToEcts(ects.get(course.getCode())));
		} catch (NullPointerException | NumberFormatException ignored) {
			course.setEcts(0); // no Ects
		}
	}

	private Integer convertToEcts(String stringToConvert) {
		return Integer.parseInt(stringToConvert.split("\\.")[0]);
	}

	private Map<String, String> getCoursesToEctsDictionary(UsosUserAPI userApi) {
		return userApi.courses().userEctsPoints().values().stream()
				.flatMap(map -> map.entrySet().stream())
				.filter(entry -> entry.getValue() != null)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> newValue));
	}
}
