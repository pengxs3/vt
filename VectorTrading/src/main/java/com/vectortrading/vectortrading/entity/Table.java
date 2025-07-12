package com.vectortrading.vectortrading.entity;

import lombok.Data;

import java.util.List;

@Data
public class Table {
    private String marketCategory;
    private int pricetype;
    private String thscode;
    private List<String> time;
    private TableData table;
}