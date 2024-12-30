package pl.wmsdev.usos.model;


import lombok.*;

import java.math.BigDecimal;
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
    private Integer Ects;
}