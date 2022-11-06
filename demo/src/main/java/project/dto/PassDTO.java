package project.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PassDTO {
    private String passType;
    private String attractions;
    private int peoplePerPass;
    private String isDigital;
    private float replacementFee;
    private int numAvailable;
    private int numTotal;

    public PassDTO(String type, String attractions, int peoplePerPass, String isDigital, float replacementFee) {
        this.passType = type;
        this.attractions = attractions;
        this.peoplePerPass = peoplePerPass;
        this.isDigital = isDigital;
        this.replacementFee = replacementFee;
        this.numAvailable=0;
        this.numTotal=0;
    }
}
