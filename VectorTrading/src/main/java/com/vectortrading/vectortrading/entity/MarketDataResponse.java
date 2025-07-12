package com.vectortrading.vectortrading.entity;

import lombok.Data;
import java.util.List;

@Data
public class MarketDataResponse {
    private int errorcode;
    private String errmsg;
    private List<Table> tables;
    private List<DataType> datatype;
    private InputParams inputParams;
    private int dataVol;
    private int perf;
}





