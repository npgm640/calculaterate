package com.adhoc.demo;

import com.adhoc.demo.domain.PlanDetails;
import com.adhoc.demo.domain.SLCSPlanDetails;
import com.adhoc.demo.domain.ZipCodeDetails;
import com.adhoc.demo.util.Utils;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class CalculateRate {

    public static Map<String, PlanDetails> planDetailMap = new HashMap<>();
    public static Map<String, List<ZipCodeDetails>> zipCodeDetailsMap = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("************************Calculating rate for Second Lowest Cost Silver plan ***************************");

        List<SLCSPlanDetails> slcspList;

        try {
            //load csv to objects
            mapPlanDetailsCSV();
            mapZipCodeDetailsCSV();
            slcspList = mapSlcspCSV();

            //process and calculate Rate
            processSecondLowestPlanRates(slcspList, planDetailMap, zipCodeDetailsMap);

            //displays the result in stdout
            System.out.println("zipcode,rate");
            slcspList.stream().forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.gc();
        }
        System.out.println("************** Program Execution COMPLETED ************************");
    }

    private static Function<String, PlanDetails> csv2PlanDetails = (line) -> {
        String[] fileRecord = line.split(",");
        PlanDetails planDetail = new PlanDetails();

        planDetail.populateValues(fileRecord);

        planDetailMap.put(planDetail.getUniqueId(), planDetail);
        return planDetail;
    };
    private static Function<String, SLCSPlanDetails> csv2SlcspDetails = (line) -> {
        String[] fileRecord = line.split(",");
        SLCSPlanDetails slcsPlanDetails = new SLCSPlanDetails();
        slcsPlanDetails.populateValues(fileRecord);
        return slcsPlanDetails;
    };
    private static Function<String, ZipCodeDetails> csv2ZipDetails = (line) -> {
        String[] fileRecord = line.split(",");
        ZipCodeDetails zipCodeDetail = new ZipCodeDetails();
        zipCodeDetail.populateValues(fileRecord);

        if (zipCodeDetailsMap.containsKey(zipCodeDetail.getUniqueId())) {
            List<ZipCodeDetails> valueList = new ArrayList<>();
            valueList.addAll(zipCodeDetailsMap.get(zipCodeDetail.getUniqueId()));
            valueList.addAll(Arrays.asList(zipCodeDetail));
            zipCodeDetailsMap.put(zipCodeDetail.getUniqueId(), valueList);
        } else
            zipCodeDetailsMap.put(zipCodeDetail.getUniqueId(), Arrays.asList(zipCodeDetail));
        return zipCodeDetail;
    };

    private static void processSecondLowestPlanRates(List<SLCSPlanDetails> slcspList, Map<String, PlanDetails> planDetailsMap, Map<String, List<ZipCodeDetails>> zipCodeDetailMap) {

        Map<String, List<Double>> resultMap = new HashMap<>();
        Predicate<Integer> greaterThanOne = (i) -> i > 1;

        slcspList.stream().forEach(e -> {
            List<ZipCodeDetails> mapZipValueList = zipCodeDetailMap.get(e.getZipcode());
            List<Double> rateList = new ArrayList<>();
            mapZipValueList.stream().forEach(zipDetail -> {
                List<PlanDetails> matchedPlanDetailList = getMatchedPlanDetail(planDetailMap, zipDetail);
                if (greaterThanOne.test(mapZipValueList.size())) {
                    //check for ambigious locations
                    matchedPlanDetailList.stream().forEach(planDetail -> {
                        if (zipDetail.isAmbiguousLocation(mapZipValueList))
                            e.setRate("");
                        else
                            matchedPlanDetailList.stream().forEach(pd -> rateList.add((pd.getFormattedRate())));
                    });
                    Set<Double> rateValuesSet = new HashSet<>(rateList);
                    List<Double> sortedRateList = new ArrayList<>(rateValuesSet);
                    Collections.sort(sortedRateList);
                    if (sortedRateList.isEmpty())
                        e.setRate("");
                    else if (sortedRateList.size() == 1)
                        e.setRate("");
                    else
                        e.setRate(sortedRateList.get(1).toString());
                } else {
                    List<Double> rateList1 = new ArrayList<>();
                    matchedPlanDetailList.stream().forEach(planDetail -> rateList1.add((planDetail.getFormattedRate())));
                    Set<Double> rateValuesSet = new HashSet<>(rateList1);
                    List<Double> sortedRateList = new ArrayList<>(rateValuesSet);
                    Collections.sort(sortedRateList);

                    if (sortedRateList.isEmpty())
                        e.setRate("");
                    else if (sortedRateList.size() == 1)
                        e.setRate("");
                    else
                        e.setRate(sortedRateList.get(1).toString());
                }
            });
        });
    }

    private static List<PlanDetails> getMatchedPlanDetail(Map<String, PlanDetails> planDetailsMap, ZipCodeDetails zipCodeDetail) {
        List<PlanDetails> returnList = new ArrayList<>();
        planDetailsMap.entrySet().stream().forEach(e -> {
            if (e.getValue().isMatched(zipCodeDetail))
                returnList.add(e.getValue());
        });
        Collections.sort(returnList);
        return returnList;
    }

    private static List<SLCSPlanDetails> mapSlcspCSV() throws Exception {
        List<SLCSPlanDetails> slcspPlanDetails;
        File resourceFile = Utils.getResourceFile("classpath:slcsp.csv");
        InputStream inputStream = new FileInputStream(resourceFile);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        slcspPlanDetails = bufferedReader.lines().skip(1).map(csv2SlcspDetails).collect(Collectors.toList());
        return slcspPlanDetails;
    }

    private static List<ZipCodeDetails> mapZipCodeDetailsCSV() throws Exception {
        List<ZipCodeDetails> zipCodeDetails;
        File resourceFile = Utils.getResourceFile("classpath:zips.csv");
        InputStream inputStream = new FileInputStream(resourceFile);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        zipCodeDetails = bufferedReader.lines().skip(1).map(csv2ZipDetails).collect(Collectors.toList());
        return zipCodeDetails;
    }

    private static List<PlanDetails> mapPlanDetailsCSV() throws Exception {
        Map<String, PlanDetails> planDetailMap = new HashMap<>();
        List<PlanDetails> planDetails;
        File resourceFile = Utils.getResourceFile("classpath:plans.csv");
        InputStream inputStream = new FileInputStream(resourceFile);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        planDetails = bufferedReader.lines().skip(1).map(csv2PlanDetails).collect(Collectors.toList());

        return planDetails;
    }
}

