package pl.wmsdev.data.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;
import pl.wmsdev.data.model.*;
import pl.wmsdev.data.values.StudentStatsCategory;
import pl.wmsdev.data.values.StudentStatsChartType;
import pl.wmsdev.usos.cards.service.UsosCardService;
import pl.wmsdev.usos.model.Course;
import pl.wmsdev.usos.model.PersonalData;
import pl.wmsdev.usos.model.Semester;
import pl.wmsdev.usos.model.Studies;
import pl.wmsdev.usos.values.UsosStudentStatus;
import pl.wmsdev.utils.internationalization.LocalizedMessageService;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.context.i18n.LocaleContextHolder.getLocaleContext;

@RequiredArgsConstructor
@Service
public class StudentStatsServiceImpl implements StudentStatsService {

    private final UsosCardService cardService;
    private final PersonalDataServiceImpl personalDataService;
    private final LocalizedMessageService msgService;

    @Override
    public StudentStatsData getData() {
        var personalData = personalDataService.getPersonalData();

        return StudentStatsData.builder()
                .personalData(personalData)
                .content(cardService.getCards(personalData, getLocaleContext()))
                .build();
    }

    @Override
    public String getStudentStatisticsCsv() {
        return generateCsv(personalDataService.getPersonalData().getStudies());
    }

    @Override
    public String getMockStudentStatisticsCsv() {
        var mockStudies = List.of(
                Studies.builder()
                        .semesters(List.of(
                                        Semester.builder()
                                                .name("2024/25-Z")
                                                .courses(List.of(Course.builder()
                                                                .name("Zespołowe przedsięwzięcie inżynierskie")
                                                                .mark(BigDecimal.valueOf(5.0))
                                                                .Ects(20)
                                                                .build(),
                                                        Course.builder()
                                                                .name("Hurtownie danych")
                                                                .mark(BigDecimal.valueOf(4.5))
                                                                .Ects(5)
                                                                .build()))
                                                .build(),
                                        Semester.builder()
                                                .name("2023/24-L")
                                                .courses(List.of(Course.builder()
                                                                .name("Cyberbezpieczeństwo")
                                                                .mark(BigDecimal.valueOf(5.0))
                                                                .Ects(2)
                                                                .build(),
                                                        Course.builder()
                                                                .name("Wprowadzenie do zarządzania projektami informatycznymi")
                                                                .mark(BigDecimal.valueOf(5.5))
                                                                .Ects(1)
                                                                .build(),
                                                        Course.builder()
                                                                .name("Praktyka")
                                                                .mark(BigDecimal.valueOf(5.0))
                                                                .Ects(5)
                                                                .build(),
                                                        Course.builder()
                                                                .name("Techniki prezentacji")
                                                                .mark(BigDecimal.valueOf(3.0))
                                                                .Ects(3)
                                                                .build()))
                                                .build()
                                )
                        )
                        .build());

        return generateCsv(mockStudies);
    }

    private String generateCsv(List<Studies> studies) {
        StringWriter writer = new StringWriter();

        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.builder()
                .setHeader("ID", "Semester", "Course name", "ECTS", "Grade").build())) {

            AtomicInteger idCounter = new AtomicInteger(1);

            studies.forEach(study -> {
                study.semesters().forEach(semester -> {
                    semester.courses().forEach(course -> {
                        try {
                            csvPrinter.printRecord(
                                    idCounter.getAndIncrement(),
                                    semester.name(),
                                    course.getName(),
                                    course.getEcts(),
                                    course.getMark()
                            );
                        } catch (Exception e) {
                            throw new RuntimeException("Error writing CSV record", e);
                        }
                    });
                });
            });

        } catch (Exception e) {
            throw new RuntimeException("Error generating CSV", e);
        }

        return writer.toString();
    }

    @Override
    public StudentStatsData getStaticMockedData() {
        PersonalData mockedPersonalData = PersonalData.builder()
                .firstName("Jan")
                .lastName("Kowalski")
                .currentFaculty("Wydział Informatyki i Telekomunikacji")
                .currentMajor("Informatyka Stosowana")
                .semester(4)
                .currentStageOfStudies("pierwszego stopnia")
                .studentStatus(UsosStudentStatus.ACTIVE_STUDENT)
                .phdStudentStatus(UsosStudentStatus.NOT_A_STUDENT)
                .studiesType("stacjonarne")
                .indexNumber(123456)
                .universityName("Politechnika Wrocławska")
                .photoUrl("https://apps.usos.pwr.edu.pl/res/up/200x250/blank-male-4.jpg")
                .build();

        ArrayList<StudentStatsObject> studentStatsMockContent = new ArrayList<>();

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.PAYMENTS)
                .content(StudentStatsText.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.sum-of-money"))
                        .subtitle("")
                        .value("522zł")
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.GPA)
                .content(StudentStatsDoubleText.builder()
                        .title(msgService.getMessageFromContext("msg.avg-grades-weighted-not-weighted.all"))
                        .subtitle(msgService.getMessageFromContext("msg.avg-grades.good-job"))
                        .value1("3.55")
                        .value2("3.70")
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.GPA)
                .content(StudentStatsText.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.percentile.all"))
                        .subtitle("")
                        .value("60")
                        .build())
                .build());


        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.GPA)
                .content(StudentStatsDoubleText.builder()
                        .title(msgService.getMessageFromContext("msg.avg-grades-weighted-not-weighted.last-sem"))
                        .subtitle(msgService.getMessageFromContext("msg.avg-grades.good-job"))
                        .value1("4.19")
                        .value2("3.82")
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.GPA)
                .content(StudentStatsText.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.percentile.last-sem"))
                        .subtitle("")
                        .value("40")
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.GPA)
                .content(StudentStatsChart.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.avg-as-graph"))
                        .chartType(StudentStatsChartType.BAR)
                        .subtitle("")
                        .values(List.of(new StudentStatsChartValue(4.15, "2022/2023-Z"),
                                new StudentStatsChartValue(4.80, "2022/2023-L"),
                                new StudentStatsChartValue(4.10, "2023/2024-Z"),
                                new StudentStatsChartValue(3.55, "2023/2024-L")))
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.PROGRESS_OF_SEMESTER)
                .content(StudentStatsText.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.ects.sem"))
                        .subtitle("")
                        .value("26")
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.PROGRESS_OF_SEMESTER)
                .content(StudentStatsText.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.days-passed-percent"))
                        .subtitle("")
                        .value("33%")
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.PROGRESS_OF_SEMESTER)
                .content(StudentStatsText.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.days-until-exam"))
                        .subtitle(msgService.getMessageFromContext("msg.StudentStatsService.days-until-exam-with-weekends"))
                        .value("59")
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.PROGRESS_OF_STUDIES)
                .content(StudentStatsText.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.ects-balance"))
                        .subtitle("")
                        .value("116")
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.PROGRESS_OF_STUDIES)
                .content(StudentStatsText.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.studies-completion-percent"))
                        .subtitle("")
                        .value("45%")
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.PROGRESS_OF_STUDIES)
                .content(StudentStatsText.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.messages-count"))
                        .subtitle("")
                        .value("210")
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.PROGRESS_OF_STUDIES)
                .content(StudentStatsText.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.days-at-uni"))
                        .subtitle("")
                        .value("459")
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.PROGRESS_OF_STUDIES)
                .content(StudentStatsText.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.semesters-of-wf"))
                        .subtitle("")
                        .value("1")
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.PROGRESS_OF_STUDIES)
                .content(StudentStatsText.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.semesters-of-lang"))
                        .subtitle("")
                        .value("0")
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.PROGRESS_OF_STUDIES)
                .content(StudentStatsText.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.semesters-of-humanities"))
                        .subtitle("")
                        .value("1")
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.SCHOLARSHIPS)
                .content(StudentStatsFlag.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.has-scholarship"))
                        .value(true)
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.SCHOLARSHIPS)
                .content(StudentStatsText.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.semesters-with-scholarship"))
                        .subtitle("")
                        .value("3")
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.SCHOLARSHIPS)
                .content(StudentStatsText.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.money-from-scholarships"))
                        .subtitle("")
                        .value("4500zł")
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.SCHOLARSHIPS)
                .content(StudentStatsChart.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.graph-of-scholarship-thresholds"))
                        .subtitle("")
                        .chartType(StudentStatsChartType.LINE)
                        .values(List.of(new StudentStatsChartValue(4.80, "2022/23-Z"),
                                new StudentStatsChartValue(4.90, "2022/23-L"),
                                new StudentStatsChartValue(4.85, "2023/24-Z"),
                                new StudentStatsChartValue(4.95, "2023/24-L")))
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.SCHOLARSHIPS)
                .content(StudentStatsText.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.num-of-fields-of-studies-with-scholarship"))
                        .subtitle("")
                        .value("110/135")
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.UNIVERSITY_STAFF)
                .content(StudentStatsChart.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.best-grading-teacher"))
                        .subtitle("Witold Jacak")
                        .chartType(StudentStatsChartType.LINE)
                        .values(List.of(new StudentStatsChartValue(0.0, "2.0"),
                                new StudentStatsChartValue(0.0, "3.0"),
                                new StudentStatsChartValue(0.0, "3.5"),
                                new StudentStatsChartValue(15.0, "4.0"),
                                new StudentStatsChartValue(20.0, "4.5"),
                                new StudentStatsChartValue(40.0, "5.0"),
                                new StudentStatsChartValue(25.0, "5.5")))
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.UNIVERSITY_STAFF)
                .content(StudentStatsChart.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.worst-grading-teacher"))
                        .subtitle("Jezy sas")
                        .chartType(StudentStatsChartType.LINE)
                        .values(List.of(new StudentStatsChartValue(50.0, "2.0"),
                                new StudentStatsChartValue(15.0, "3.0"),
                                new StudentStatsChartValue(15.0, "3.5"),
                                new StudentStatsChartValue(10.0, "4.0"),
                                new StudentStatsChartValue(5.0, "4.5"),
                                new StudentStatsChartValue(3.0, "5.0"),
                                new StudentStatsChartValue(2.0, "5.5")))
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.UNIVERSITY_STAFF)
                .content(StudentStatsChart.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.best-polwro-teacher"))
                        .subtitle("Witold Jacak")
                        .chartType(StudentStatsChartType.LINE)
                        .values(List.of(new StudentStatsChartValue(0.0, "2.0"),
                                new StudentStatsChartValue(0.0, "3.0"),
                                new StudentStatsChartValue(0.0, "3.5"),
                                new StudentStatsChartValue(0.0, "4.0"),
                                new StudentStatsChartValue(0.0, "4.5"),
                                new StudentStatsChartValue(60.0, "5.0"),
                                new StudentStatsChartValue(40.0, "5.5")))
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.UNIVERSITY_STAFF)
                .content(StudentStatsChart.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.worst-polwro-teacher"))
                        .subtitle("Jezy sas")
                        .chartType(StudentStatsChartType.LINE)
                        .values(List.of(new StudentStatsChartValue(50.0, "2.0"),
                                new StudentStatsChartValue(20.0, "3.0"),
                                new StudentStatsChartValue(10.0, "3.5"),
                                new StudentStatsChartValue(10.0, "4.0"),
                                new StudentStatsChartValue(10.0, "4.5"),
                                new StudentStatsChartValue(0.0, "5.0"),
                                new StudentStatsChartValue(0.0, "5.5")))
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.LIBRARY)
                .content(StudentStatsText.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.number-of-borrowed-books"))
                        .subtitle("")
                        .value("24")
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.LIBRARY)
                .content(StudentStatsText.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.sum-of-fines-for-books"))
                        .subtitle("")
                        .value("10.50zł")
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.COURSES)
                .content(StudentStatsDoubleText.builder()
                        .title(msgService.getMessageFromContext("msg.UsosCoursesCardCreator.teachers-gender-count"))
                        .subtitle("")
                        .value1("9")
                        .value2("33")
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.COURSES)
                .content(StudentStatsChart.builder()
                        .title(msgService.getMessageFromContext("msg.UsosCoursesCardCreator.course-types-graph"))
                        .subtitle("")
                        .chartType(StudentStatsChartType.BAR)
                        .values(List.of(new StudentStatsChartValue(22.0, "Wykład"),
                                new StudentStatsChartValue(19.0, "Ćwiczenia"),
                                new StudentStatsChartValue(15.0, "Zajęcia laboratoryjne"),
                                new StudentStatsChartValue(8.0, "Projekt"),
                                new StudentStatsChartValue(2.0, "Seminarium")))
                        .build())
                .build());

        return StudentStatsData.builder()
                .personalData(mockedPersonalData)
                .content(studentStatsMockContent)
                .build();
    }


    @Override
    public StudentStatsData getDynamicMockedData(Integer numOfBlocks) {
        SecureRandom random = new SecureRandom();

        ArrayList<String> firstNames = new ArrayList<>(Arrays.asList("Adam", "Barbara", "Cezary", "Dorota", "Edward", "Filip",
                "Gosia", "Henryk", "Igor", "Jan"));
        List<String> lastNames = Arrays.asList("Kowalski", "Nowak", "Wójcik", "Lewandowski", "Kamiński", "Dąbrowski",
                "Zieliński", "Szymański", "Woźniak", "Kozłowski");

        PersonalData mockedPersonalData = PersonalData.builder()
                .firstName(firstNames.get(random.nextInt(firstNames.size())))
                .lastName(lastNames.get(random.nextInt(lastNames.size())))
                .currentFaculty("W04n")
                .currentMajor("IST")
                .semester(random.nextInt(7) + 1)
                .currentStageOfStudies("I stopnia")
                .studentStatus(UsosStudentStatus.ACTIVE_STUDENT)
                .indexNumber(123456)
                .build();

        ArrayList<StudentStatsObject> studentStatsMockContent = new ArrayList<>();

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.PAYMENTS)
                .content(StudentStatsText.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.sum-of-money"))
                        .subtitle("")
                        .value(String.format("%.2f", (random.nextDouble() * 1000)))
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.GPA)
                .content(StudentStatsDoubleText.builder()
                        .title(msgService.getMessageFromContext("msg.avg-grades-weighted-not-weighted.all"))
                        .subtitle("")
                        .value1(String.format("%.2f", (random.nextDouble() + 4)))
                        .value2(String.format("%.2f", (random.nextDouble() + 4)))
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.GPA)
                .content(StudentStatsText.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.percentile.all"))
                        .subtitle("")
                        .value(String.valueOf(random.nextInt(101)))
                        .build())
                .build());


        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.GPA)
                .content(StudentStatsDoubleText.builder()
                        .title(msgService.getMessageFromContext("msg.avg-grades-weighted-not-weighted.last-sem"))
                        .subtitle("")
                        .value1(String.format("%.2f", (random.nextDouble() + 4)))
                        .value2(String.format("%.2f", (random.nextDouble() + 4)))
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.GPA)
                .content(StudentStatsText.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.percentile.last-sem"))
                        .subtitle("")
                        .value(String.valueOf(random.nextInt(101)))
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.GPA)
                .content(StudentStatsText.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.percentile.last-sem"))
                        .subtitle("")
                        .value(String.valueOf(random.nextInt(101)))
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.GPA)
                .content(StudentStatsChart.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.avg-as-graph"))
                        .chartType(StudentStatsChartType.LINE)
                        .subtitle("")
                        .values(List.of(new StudentStatsChartValue(Math.round(random.nextDouble()*100 + 400)/100.0, "sem 1"),
                                new StudentStatsChartValue(Math.round(random.nextDouble()*100 + 400)/100.0, "sem 2"),
                                new StudentStatsChartValue(Math.round(random.nextDouble()*100 + 400)/100.0, "sem 3"),
                                new StudentStatsChartValue(Math.round(random.nextDouble()*100 + 400)/100.0, "sem 4")))
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.GPA)
                .content(StudentStatsChart.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.avg-as-graph"))
                        .chartType(StudentStatsChartType.PIE)
                        .subtitle("")
                        .values(List.of(new StudentStatsChartValue(Math.round(random.nextDouble()*100 + 400)/100.0, "sem 1"),
                                new StudentStatsChartValue(Math.round(random.nextDouble()*100 + 400)/100.0, "sem 2"),
                                new StudentStatsChartValue(Math.round(random.nextDouble()*100 + 400)/100.0, "sem 3"),
                                new StudentStatsChartValue(Math.round(random.nextDouble()*100 + 400)/100.0, "sem 4")))
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.PROGRESS_OF_SEMESTER)
                .content(StudentStatsText.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.ects.sem"))
                        .subtitle("")
                        .value(String.valueOf(random.nextInt(5)+26))
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.PROGRESS_OF_SEMESTER)
                .content(StudentStatsText.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.days-passed-percent"))
                        .subtitle("")
                        .value((random.nextInt(81))  + "%")
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.PROGRESS_OF_SEMESTER)
                .content(StudentStatsText.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.days-until-exam"))
                        .subtitle(msgService.getMessageFromContext("msg.StudentStatsService.days-until-exam-with-weekends"))
                        .value(String.valueOf(random.nextInt(100)))
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.PROGRESS_OF_STUDIES)
                .content(StudentStatsText.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.ects-balance"))
                        .subtitle("")
                        .value(String.valueOf(random.nextInt(250)))
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.PROGRESS_OF_STUDIES)
                .content(StudentStatsText.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.studies-completion-percent"))
                        .subtitle("")
                        .value((random.nextInt(91)) + "%")
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.PROGRESS_OF_STUDIES)
                .content(StudentStatsText.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.messages-count"))
                        .subtitle("")
                        .value(String.valueOf(random.nextInt(5)+26))
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.PROGRESS_OF_STUDIES)
                .content(StudentStatsText.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.days-at-uni"))
                        .subtitle(msgService.getMessageFromContext("msg.StudentStatsService.lecture-assumption"))
                        .value(String.valueOf(random.nextInt(1000)+500))
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.PROGRESS_OF_STUDIES)
                .content(StudentStatsText.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.semesters-of-wf"))
                        .subtitle("")
                        .value(String.valueOf(random.nextInt(3)))
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.PROGRESS_OF_STUDIES)
                .content(StudentStatsText.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.semesters-of-lang"))
                        .subtitle("")
                        .value(String.valueOf(random.nextInt(3)))
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.PROGRESS_OF_STUDIES)
                .content(StudentStatsText.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.semesters-of-humanities"))
                        .subtitle("")
                        .value(String.valueOf(random.nextInt(2)))
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.SCHOLARSHIPS)
                .content(StudentStatsFlag.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.has-scholarship"))
                        .value(random.nextBoolean())
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.SCHOLARSHIPS)
                .content(StudentStatsText.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.semesters-with-scholarship"))
                        .subtitle("")
                        .value(String.valueOf(random.nextInt(5)))
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.SCHOLARSHIPS)
                .content(StudentStatsText.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.money-from-scholarships"))
                        .subtitle("")
                        .value((random.nextInt(10000)) + "zl")
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.SCHOLARSHIPS)
                .content(StudentStatsChart.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.graph-of-scholarship-thresholds"))
                        .subtitle("")
                        .chartType(StudentStatsChartType.LINE)
                        .values(List.of(new StudentStatsChartValue(Math.round(random.nextDouble()*100 + 400)/100.0, "semestr letni 2021/2022"),
                                new StudentStatsChartValue(Math.round(random.nextDouble()*100 + 400)/100.0, "semestr zimowy 2021/2022"),
                                new StudentStatsChartValue(Math.round(random.nextDouble()*100 + 400)/100.0, "semestr letni 2022/2023"),
                                new StudentStatsChartValue(Math.round(random.nextDouble()*100 + 400)/100.0, "semestr zimowy 2022/2023")))
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.SCHOLARSHIPS)
                .content(StudentStatsText.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.num-of-fields-of-studies-with-scholarship"))
                        .subtitle("")
                        .value((random.nextInt(100)) + "/" + (random.nextInt(50)+100))
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.UNIVERSITY_STAFF)
                .content(StudentStatsChart.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.best-grading-teacher"))
                        .subtitle("Witold Jacak")
                        .chartType(StudentStatsChartType.LINE)
                        .values(List.of(new StudentStatsChartValue(0.0, "2.0"),
                                new StudentStatsChartValue(0.0, "3.0"),
                                new StudentStatsChartValue(0.0, "3.5"),
                                new StudentStatsChartValue(15.0, "4.0"),
                                new StudentStatsChartValue(20.0, "4.5"),
                                new StudentStatsChartValue(40.0, "5.0"),
                                new StudentStatsChartValue(25.0, "5.5")))
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.UNIVERSITY_STAFF)
                .content(StudentStatsChart.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.worst-grading-teacher"))
                        .subtitle("Jezy sas")
                        .chartType(StudentStatsChartType.LINE)
                        .values(List.of(new StudentStatsChartValue(50.0, "2.0"),
                                new StudentStatsChartValue(15.0, "3.0"),
                                new StudentStatsChartValue(15.0, "3.5"),
                                new StudentStatsChartValue(10.0, "4.0"),
                                new StudentStatsChartValue(5.0, "4.5"),
                                new StudentStatsChartValue(3.0, "5.0"),
                                new StudentStatsChartValue(2.0, "5.5")))
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.UNIVERSITY_STAFF)
                .content(StudentStatsChart.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.worst-grading-teacher"))
                        .subtitle("Jezy sas")
                        .chartType(StudentStatsChartType.PIE)
                        .values(List.of(new StudentStatsChartValue(50.0, "2.0"),
                                new StudentStatsChartValue(15.0, "3.0"),
                                new StudentStatsChartValue(15.0, "3.5"),
                                new StudentStatsChartValue(10.0, "4.0"),
                                new StudentStatsChartValue(5.0, "4.5"),
                                new StudentStatsChartValue(3.0, "5.0"),
                                new StudentStatsChartValue(2.0, "5.5")))
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.UNIVERSITY_STAFF)
                .content(StudentStatsChart.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.best-polwro-teacher"))
                        .subtitle("Witold Jacak")
                        .chartType(StudentStatsChartType.LINE)
                        .values(List.of(new StudentStatsChartValue(0.0, "2.0"),
                                new StudentStatsChartValue(0.0, "3.0"),
                                new StudentStatsChartValue(0.0, "3.5"),
                                new StudentStatsChartValue(0.0, "4.0"),
                                new StudentStatsChartValue(0.0, "4.5"),
                                new StudentStatsChartValue(60.0, "5.0"),
                                new StudentStatsChartValue(40.0, "5.5")))
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.UNIVERSITY_STAFF)
                .content(StudentStatsChart.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.worst-polwro-teacher"))
                        .subtitle("Jezy sas")
                        .chartType(StudentStatsChartType.LINE)
                        .values(List.of(new StudentStatsChartValue(50.0, "2.0"),
                                new StudentStatsChartValue(20.0, "3.0"),
                                new StudentStatsChartValue(10.0, "3.5"),
                                new StudentStatsChartValue(10.0, "4.0"),
                                new StudentStatsChartValue(10.0, "4.5"),
                                new StudentStatsChartValue(0.0, "5.0"),
                                new StudentStatsChartValue(0.0, "5.5")))
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.LIBRARY)
                .content(StudentStatsText.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.number-of-borrowed-books"))
                        .subtitle("")
                        .value(String.valueOf(random.nextInt(100)))
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.LIBRARY)
                .content(StudentStatsText.builder()
                        .title(msgService.getMessageFromContext("msg.StudentStatsService.sum-of-fines-for-books"))
                        .subtitle("")
                        .value(String.format("%.2f", (random.nextDouble()*100)) + "zl")
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.COURSES)
                .content(StudentStatsDoubleText.builder()
                        .title(msgService.getMessageFromContext("msg.UsosCoursesCardCreator.teachers-gender-count"))
                        .subtitle("")
                        .value1(String.format("%d", random.nextInt(101)))
                        .value2(String.format("%d", random.nextInt(101)))
                        .build())
                .build());

        studentStatsMockContent.add(StudentStatsObject.builder()
                .category(StudentStatsCategory.COURSES)
                .content(StudentStatsChart.builder()
                        .title(msgService.getMessageFromContext("msg.UsosCoursesCardCreator.course-types-graph"))
                        .subtitle("")
                        .chartType(StudentStatsChartType.BAR)
                        .values(List.of(new StudentStatsChartValue(22.0, "Wykład"),
                                new StudentStatsChartValue(19.0, "Ćwiczenia"),
                                new StudentStatsChartValue(15.0, "Zajęcia laboratoryjne"),
                                new StudentStatsChartValue(8.0, "Projekt"),
                                new StudentStatsChartValue(2.0, "Seminarium")))
                        .build())
                .build());

        return StudentStatsData.builder()
                .personalData(mockedPersonalData)
                .content(getRandomElements(studentStatsMockContent, numOfBlocks))
                .build();
    }

    private <T> List<T> getRandomElements(List<T> list, Integer numOfItems) {
        List<T> randomElements = new ArrayList<>();
        if(numOfItems <= list.size()) {
            Collections.shuffle(list);
            for (int i = 0; i < numOfItems; i++) {
                randomElements.add(list.get(i));
            }
        } else {
            for (int i = 0; i < numOfItems; i++) {
                if(i%28 == 0) {
                    Collections.shuffle(list);
                }
                randomElements.add(list.get(i%28));
            }
        }
        return randomElements;
    }
}