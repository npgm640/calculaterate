package com.adhoc.demo.domain;

import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.util.Locale;

public class SLCSPlanDetails extends AbstractDomain {
    private String zipcode;
    private String rate;

    public SLCSPlanDetails() {
    }

    public SLCSPlanDetails(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public void populateValues(String[] fileRecord) {


        if (fieldExists(0, fileRecord))
            this.setZipcode(fileRecord[0]);
        else
            this.setZipcode("");

        if (fileRecord.length == 1)
            this.setRate("");
    }

    @Override
    public String toString() {
        if (StringUtils.isEmpty(this.getRate())) {
            return (this.zipcode + COMMA);
        }
        return (this.zipcode + COMMA + formatRate(this.rate));
    }

    private String formatRate(String rate) {

        DecimalFormat df = new DecimalFormat("0.00##");
        String result = df.format(Double.valueOf(rate));
        return result;
    }
}
