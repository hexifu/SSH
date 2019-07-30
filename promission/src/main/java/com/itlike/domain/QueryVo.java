package com.itlike.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@ToString
public class QueryVo {
    private int page;
    private int rows;
    private String keyword;
}
