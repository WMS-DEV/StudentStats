package pl.wmsdev.data.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import pl.wmsdev.data.model.StudentStatsData;
import pl.wmsdev.data.service.StudentStatsService;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
public class DataController {

    @Value("${student-stats.file.name}")
    private String studentStatsFileName;
    private final StudentStatsService studentStatsService;

    @GetMapping("/getStaticMockedData")
    @Operation(summary = "Returns static mocked data [DEBUG] TEST FOR CI")
    @Deprecated(forRemoval = true)
    public ResponseEntity<StudentStatsData> getStaticMockedData(
            @Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(type = "string", allowableValues = {"pl", "en"}))
            @RequestHeader(name = "Accept-Language", required = false) String language) {
        return ResponseEntity.ok().body(studentStatsService.getStaticMockedData());
    }

    @GetMapping("/getData")
    @Operation(summary = "Get student stats")
    public ResponseEntity<StudentStatsData> getStudentStatsData(
            @Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(type = "string", allowableValues = {"pl", "en"}))
            @RequestHeader(name = "Accept-Language", required = false) String language) {
        return ResponseEntity.ok().body(studentStatsService.getData());
    }

    @GetMapping("/getDynamicMockedData/{numOfBlocks}")
    @Operation(summary = "Returns dynamic mocked data [DEBUG] (number of objects in result is random, and values are also dynamic). " +
            "Parameter numOfBlocks allows to get exact number of content as it needed [0-100], but to num over 28 cards may occur duplications")
    @Deprecated(forRemoval = true)
    public ResponseEntity<StudentStatsData> getDynamicMockedData(
            @PathVariable @Min(0) @Max(100) Integer numOfBlocks,
            @Parameter(name = "Accept-Language", in = ParameterIn.HEADER, schema = @Schema(type = "string", allowableValues = {"pl", "en"}))
            @RequestHeader(name = "Accept-Language", required = false) String language) {

        return ResponseEntity.ok().body(studentStatsService.getDynamicMockedData(numOfBlocks));
    }

    @GetMapping("/downloadCsv")
    @Operation(summary = "Download data as CSV")
    public ResponseEntity<String> getDataCSV() {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "text/csv")
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", studentStatsFileName))
                .body(studentStatsService.getStudentStatisticsCsv());
    }

    @GetMapping("/downloadMockCsv")
    @Operation(summary = "Download mock data as CSV")
    public ResponseEntity<String> getMockDataCSV() {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "text/csv")
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", studentStatsFileName))
                .body(studentStatsService.getMockStudentStatisticsCsv());
    }
}