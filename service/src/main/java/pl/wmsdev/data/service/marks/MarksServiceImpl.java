package pl.wmsdev.data.service.marks;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.wmsdev.usos.model.Course;
import pl.wmsdev.usos.model.Semester;
import pl.wmsdev.usos4j.client.UsosUserAPI;
import pl.wmsdev.usos4j.model.grades.UsosGrade;
import pl.wmsdev.usos4j.model.grades.UsosGradesCourseEdition;
import pl.wmsdev.usos4j.model.grades.UsosGradesTermsParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class MarksServiceImpl implements MarksService {

	@Async
	@Override
	public CompletableFuture<Void> addMarks(UsosUserAPI userApi, List<Semester> semesters, String[] semesterIds) {
		Map<String, Map<String, UsosGradesCourseEdition>> courseIdToCourseGrades = userApi.grades().terms2(UsosGradesTermsParam.builder(semesterIds).build());

		for (Semester semester : semesters) {
			addMarksToCourses(courseIdToCourseGrades.get(semester.name()), semester.courses());
		}

		return CompletableFuture.completedFuture(null);
	}

	private void addMarksToCourses(Map<String, UsosGradesCourseEdition> courseIdToCourseGrades,
	                               List<Course> courses) {
		for (Course course : courses) {
			addMarkToCourse(courseIdToCourseGrades, course);
		}
	}

	private void addMarkToCourse(Map<String, UsosGradesCourseEdition> courseIdToCourseGrades,
	                             Course course) {
		try {
			var finalMark = getFinalMark(courseIdToCourseGrades, course.getCode());
			BigDecimal mark = convertMark(finalMark);
			course.setMark(mark);
		} catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException ignored) {
			course.setMark(BigDecimal.ZERO); // Course has invalid grade
		}
	}

	private UsosGrade getFinalMark(Map<String, UsosGradesCourseEdition> courseIdToCourseGrades, String courseId) {
		var courseGrades = courseIdToCourseGrades.get(courseId).courseGrades();
		int amountOfCourseGrades = courseGrades.length;
		int courseGradeIndex = amountOfCourseGrades - 1;

		return courseIdToCourseGrades.get(courseId).courseGrades()[courseGradeIndex].get(String.valueOf(amountOfCourseGrades));
	}

	private BigDecimal convertMark(UsosGrade mark) {
		String markAsString = mark.valueSymbol();
		String markFormatted = markAsString.replace(',', '.');
		return new BigDecimal(markFormatted);
	}
}
