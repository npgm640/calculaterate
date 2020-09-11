package com.beth.demo.domain;


import org.springframework.util.StringUtils;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class PlanDetails extends AbstractDomain implements  Comparable <PlanDetails> {

    private String planId;
    private String state;
    private String metalLevel;
    private double rate;
    private String rateArea;
    private double formattedRate;
    private List<String> zipCodeList;
    private List<String> countyCodeList;

    private static Function<String, PlanDetails> csv2PlanDetails = (line) -> {
        String[] fileRecord = line.split(",");
        PlanDetails planDetails = new PlanDetails();
        planDetails.populateValues(fileRecord);
        return planDetails;
    };

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMetalLevel() {
        return metalLevel;
    }

    public void setMetalLevel(String metalLevel) {
        this.metalLevel = metalLevel;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getRateArea() {
        return rateArea;
    }

    public void setRateArea(String rateArea) {
        this.rateArea = rateArea;
    }

    public double getFormattedRate() {
        return formattedRate;
    }

    public void setFormattedRate(double formattedRate) {
        if (StringUtils.isEmpty(formattedRate))
            this.formattedRate = formattedRate;
        else this.formattedRate = format2DigitsRate(formattedRate);
    }

    public List<String> getZipCodeList() {
        if (zipCodeList == null) {
            return new ArrayList<>();
        }
        return zipCodeList;
    }

    public void setZipCodeList(List<String> zipCodeList) {
        this.zipCodeList = zipCodeList;
    }

    public List<String> getCountyCodeList() {
        if (countyCodeList == null) {
            return new ArrayList<>();
        }
        return countyCodeList;
    }

    public void setCountyCodeList(List<String> countyCodeList) {
        this.countyCodeList = countyCodeList;
    }

    private double format2DigitsRate(double formattedRate) {
        DecimalFormat df = new DecimalFormat("#.000");
        String num = df.format(formattedRate);
        num = num.substring(0, num.length() - 1);
        return Double.parseDouble(num);
    }

    public void populateValues(String[] fileRecord) {
        if (fieldExists(0, fileRecord)) {
            this.setPlanId(fileRecord[0]);
        }
        if (fieldExists(1, fileRecord)) {
            this.setState(fileRecord[1]);
        }
        if (fieldExists(2, fileRecord)) {
            this.setMetalLevel(fileRecord[2]);
        }
        if (fieldExists(3, fileRecord)) {
            this.setRate(Double.parseDouble(fileRecord[3]));
            this.setFormattedRate(Double.parseDouble(fileRecord[3]));
        }
        if (fieldExists(4, fileRecord)) {
            this.setRateArea(fileRecord[4]);
        }
    }

    public boolean isSilverPlan() {
        return (this.getMetalLevel().equals("Silver"));
    }

    public String getUniqueId() {
        return(this.getPlanId()+ COMMA + this.getState() + COMMA + this.getRateArea());
    }

    public boolean isMatched(ZipCodeDetails zipCodeDetail) {
        boolean result = ((this.getRateArea().equals(zipCodeDetail.getRateArea()))
                && (this.getState().equals(zipCodeDetail.getState())));
        return result;
    }


    @Override
    public int compareTo(PlanDetails planDetails) {
        if (this.getRate() > planDetails.getRate())
            return 1;
        else if (this.getRate() == planDetails.getRate())
            return 0;
        else
            return -1;
    }
}
