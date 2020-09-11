package com.beth.demo.domain;

public class AbstractDomain {

    protected static final String COMMA = ",";

    protected boolean fieldExists(int i, String[] fileRecord) {

        return (fileRecord[i] != null && fileRecord[i].trim().length() > 0);
    }
}
