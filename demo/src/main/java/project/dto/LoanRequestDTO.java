package project.dto;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoanRequestDTO {
    private String date;
    private String attraction;
    private int noOfPasses;
    private Long staffId;
}

