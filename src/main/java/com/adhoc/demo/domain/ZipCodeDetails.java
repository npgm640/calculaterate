package com.adhoc.demo.domain;

import java.util.List;
import java.util.stream.Collectors;

public class ZipCodeDetails extends AbstractDomain {

    private String zipcode;
    private String state;
    private String countyCode;
    private String name;
    private String rateArea;


    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRateArea() {
        return rateArea;
    }

    public void setRateArea(String rateArea) {
        this.rateArea = rateArea;
    }

    public void populateValues(String[] fileRecord) {
        if (fieldExists(0, fileRecord)) {
            this.setZipcode(fileRecord[0]);
        }
        if (fieldExists(1, fileRecord)) {
            this.setState(fileRecord[1]);
        }
        if (fieldExists(2, fileRecord)) {
            this.setCountyCode(fileRecord[2]);
        }
        if (fieldExists(3, fileRecord)) {
            this.setName(fileRecord[3]);
        }
        if (fieldExists(4, fileRecord)) {
            this.setRateArea(fileRecord[4]);
        }
    }

    public String getUniqueId() {
        return (this.zipcode);
    }

    public boolean isAmbiguousLocation(List<ZipCodeDetails> mapZipValueList) {
        int count = mapZipValueList.size();
        boolean result = false; 
        List<ZipCodeDetails> countyList = mapZipValueList.stream().filter(e -> e.getCountyCode().equals(this.getCountyCode())).collect(Collectors.toList());
        result = (countyList.size()==count);

        if (!result) return false;

        List<ZipCodeDetails> rateAreaList = mapZipValueList.stream().filter(e -> e.getRateArea().equals(this.getRateArea())).collect(Collectors.toList());
        result = (rateAreaList.size() == count ) ;
        return result;
    }
}
