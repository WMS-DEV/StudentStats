package pl.wmsdev.data.values;

import lombok.AllArgsConstructor;
import pl.wmsdev.usos4j.model.common.UsosLocalizedString;

@AllArgsConstructor
public enum StudentStatsCategory {
    COURSES(new UsosLocalizedString("Kursy", "Courses")),
    PAYMENTS(new UsosLocalizedString("Płatności", "Payments")),
    GPA(new UsosLocalizedString("Średnia ocen", "GPA")),
    PROGRESS_OF_SEMESTER(new UsosLocalizedString("Progres semestru", "Semester progress")),
    PROGRESS_OF_STUDIES(new UsosLocalizedString("Progres studiów", "Studies progress")),
    SCHOLARSHIPS(new UsosLocalizedString("Stypendia", "Scholarships")),
    UNIVERSITY_STAFF(new UsosLocalizedString("Kadra uczelni", "University staff")),
    LIBRARY(new UsosLocalizedString("Biblioteka", "Library"));

    private final UsosLocalizedString localizedString;

    public UsosLocalizedString value() {
        return localizedString;
    }
}
