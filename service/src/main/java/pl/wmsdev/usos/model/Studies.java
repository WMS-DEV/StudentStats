package pl.wmsdev.usos.model;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record Studies(String name, String level, String type, Double currentSemesterProgress,
                      Double studiesProgress, LocalDate firstDayOfFirstSemester, List<Semester> semesters) {

    public Semester lastSemester() {
        return semesters.get(0);
    }

    public Semester firstSemester() {
        return semesters.get(semesters.size()-1);
    }

    public Integer numberOfSemesters() {
        return semesters.size();
    }

}
