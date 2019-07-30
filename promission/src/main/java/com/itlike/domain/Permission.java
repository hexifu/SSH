package com.itlike.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@ToString
public class Permission {
    private Long pid;

    private String pname;

    private String presource;

}