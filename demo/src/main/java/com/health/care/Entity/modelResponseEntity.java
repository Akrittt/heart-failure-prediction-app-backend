package com.health.care.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class modelResponseEntity {
    private String message;
    private double prediction;
    private double probability;
    private String verdict;

    @Override
    public String toString() {
        return "ModelResponseEntity{" +
                "message='" + message + '\'' +
                ", prediction=" + prediction +
                ", probability=" + probability +
                ", verdict=" + verdict +
                '}';
    }
}
