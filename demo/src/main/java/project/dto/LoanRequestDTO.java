package project.dto;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoanRequestDTO {
    private String loanDate;
    private String passType;
    private String attraction;
    private int numOfPasses;
    private Long staffId;
}

