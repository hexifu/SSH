package com.itlike.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter@Setter
public class PageListRes {
    private Long total;
    private List<?> rows = new ArrayList<>();
}
