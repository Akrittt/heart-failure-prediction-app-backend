package com.health.care.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PredictionEntity {
    private String patientName;
    private String patientId;
    private String admitTime;
    private String dischargeTime;
    private double creatinine;
    private double ureaNitrogen;
    private double sodium;
    private double potassium;
    private double albumin;
    private double hemoglobin;
    private double hematocrit;
    private double magnesium;
    private String creatinineFlag;
    private String ureaNitrogenFlag;
    private String sodiumFlag;
    private String potassiumFlag;
    private String albuminFlag;
    private String hemoglobinFlag;
    private String hematocritFlag;
    private String magnesiumFlag;
    private String admissionType;
    private String dischargeLocation;
    private String insurance;

    @Override
    public String toString() {
        return "PatientData{" +
                "patientName='" + patientName + '\'' +
                ", patientId='" + patientId + '\'' +
                ", admitTime='" + admitTime + '\'' +
                ", dischargeTime='" + dischargeTime + '\'' +
                ", creatinine=" + creatinine +
                ", ureaNitrogen=" + ureaNitrogen +
                ", sodium=" + sodium +
                ", potassium=" + potassium +
                ", albumin=" + albumin +
                ", hemoglobin=" + hemoglobin +
                ", hematocrit=" + hematocrit +
                ", magnesium=" + magnesium +
                ", creatinineFlag='" + creatinineFlag + '\'' +
                ", ureaNitrogenFlag='" + ureaNitrogenFlag + '\'' +
                ", sodiumFlag='" + sodiumFlag + '\'' +
                ", potassiumFlag='" + potassiumFlag + '\'' +
                ", albuminFlag='" + albuminFlag + '\'' +
                ", hemoglobinFlag='" + hemoglobinFlag + '\'' +
                ", hematocritFlag='" + hematocritFlag + '\'' +
                ", magnesiumFlag='" + magnesiumFlag + '\'' +
                ", admissionType='" + admissionType + '\'' +
                ", dischargeLocation='" + dischargeLocation + '\'' +
                ", insurance='" + insurance + '\'' +
                '}';
    }
}
