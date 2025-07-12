package com.vectortrading.vectortrading.entity;

import lombok.Data;

import java.util.List;

@Data
public class TableData {
    private List<Double> open;
    private List<Double> high;
    private List<Double> volume;
    private List<Double> turnoverRatio;
}