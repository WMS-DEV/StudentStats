package pl.wmsdev.data.service;

import pl.wmsdev.data.model.StudentStatsData;

public interface StudentStatsService {
    StudentStatsData getStaticMockedData();

    StudentStatsData getDynamicMockedData(Integer numOfBlocks);

    StudentStatsData getData();

    String getStudentStatisticsCsv();

    String getMockStudentStatisticsCsv();
}
