package pl.wmsdev.usos.cards.creators;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wmsdev.data.model.StudentStatsObject;
import pl.wmsdev.data.model.StudentStatsText;
import pl.wmsdev.data.values.StudentStatsCategory;
import pl.wmsdev.usos.model.Studies;
import pl.wmsdev.utils.common.DateUtils;
import pl.wmsdev.utils.internationalization.LocalizedMessageService;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsosTimeCardCreator implements UsosCardCreator {

   private final LocalizedMessageService msgService;

   @Override
   public List<StudentStatsObject> getAlwaysPresentCards(List<Studies> studies) {
       return List.of(getFirstDayOfStudiesCard(studies),
               getTotalTimeAtUniversityCard(studies),
               getProgressOfSemester(studies),
               getProgressOfStudies(studies));
   }

   private StudentStatsObject getFirstDayOfStudiesCard(List<Studies> studies) {
       var result = getDateOfFirstDayOfStudies(studies);
       return StudentStatsText.asObject(StudentStatsCategory.PROGRESS_OF_STUDIES,
               msgService.getMessageWithArgsFromContext("msg.UsosTimeCardCreator.first-day"), null,
               result);
   }

   private StudentStatsObject getTotalTimeAtUniversityCard(List<Studies> studies) {
       var result = getTotalTimeAtUniversity(studies);
       return StudentStatsText.asObject(StudentStatsCategory.PROGRESS_OF_STUDIES,
               msgService.getMessageWithArgsFromContext("msg.UsosTimeCardCreator.time-from-first-day"),
               msgService.getMessageWithArgsFromContext("msg.UsosTimeCardCreator.time-from-first-day.description", getDateOfFirstDayOfStudies(studies)),
               msgService.getMessageWithArgsFromContext("msg.UsosTimeCardCreator.time-from-first-day.unit", result.toString()));
   }

   private StudentStatsObject getProgressOfSemester(List<Studies> studies) {
       var result = getAsPercent(studies.get(0).currentSemesterProgress());
       return StudentStatsText.asObject(StudentStatsCategory.PROGRESS_OF_SEMESTER,
               msgService.getMessageWithArgsFromContext("msg.StudentStatsService.days-passed-percent"),
               "",
               result
               );
   }

    private StudentStatsObject getProgressOfStudies(List<Studies> studies) {
        var result = getAsPercent(studies.get(0).studiesProgress());
        return StudentStatsText.asObject(StudentStatsCategory.PROGRESS_OF_STUDIES,
                msgService.getMessageWithArgsFromContext("msg.StudentStatsService.studies-completion-percent"),
                "",
                result
        );
    }

   private String getDateOfFirstDayOfStudies(List<Studies> studies){
       var firstDayOfStudies = getFirstDayOfStudiesDate(studies);
       return DateUtils.formatToLocalizedDate(
                       firstDayOfStudies,
                       FormatStyle.SHORT,
                       msgService.getLanguageFromContextOrDefault().getLocale()
               );
   }

   private Long getTotalTimeAtUniversity(List<Studies> studies){
       var firstDayOfStudies = getFirstDayOfStudiesDate(studies);
       return Duration.between(
                               firstDayOfStudies.atStartOfDay(),
                               LocalDate.now().atStartOfDay())
                       .toDays();
   }

   private LocalDate getFirstDayOfStudiesDate(List<Studies> studies) {
       return getFirstMondayOf(studies.get(0).firstDayOfFirstSemester());
   }

   private LocalDate getFirstMondayOf(LocalDate date){
       return LocalDate.of(date.getYear(), date.getMonth(), 1).with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
   }

   private String getAsPercent(Double value) {
       return String.format("%.1f%%", value * 100);
   }
}
