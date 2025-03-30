package pl.wmsdev.usos.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Builder
@Data
@Setter
@Getter
public class Course {
    private String name;
    private String code;
    private BigDecimal mark;
    private Set<String> teachers;
    private List<String> courseTypes;
    private Integer Ects;
}