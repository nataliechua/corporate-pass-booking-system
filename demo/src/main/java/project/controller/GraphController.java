package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import project.entity.*;
import project.service.*;

import java.text.ParseException;
import java.time.*;
import java.util.*; 

@Controller
// @RequestMapping("/loansPerMonth")
public class GraphController {
    @Autowired
    private LoanService loanService;

    
    /** 
     * @param model
     * @return String
     */
    @GetMapping("/")
    public String getDataForCharts(Model model) {
        List<Loan> loans = loanService.getAllNonCancelledLoans();
        Map<Integer, Integer> loansPerMonth = getLoansPerMonth(loans);
        Map<String, Integer> loansPerAttraction = getLoansPerAttraction(loans);
        String averageLoans = getAverageNumOfLoans(loans);
        // List<Map.Entry<Staff, Integer>> topBorrowers = getTopBorrowers(loans);

        model.addAttribute("loansPerMonth", loansPerMonth);
        model.addAttribute("loansPerAttraction", loansPerAttraction);
        model.addAttribute("totalNumOfLoans", loans.size());
        model.addAttribute("averageLoans", averageLoans);
        // model.addAttribute("topBorrowers", topBorrowers);

        return "dataCharts";
    }

    
    /** 
     * @param loans
     * @return Map<Integer, Integer>
     */
    public Map<Integer, Integer> getLoansPerMonth(List<Loan> loans) {
        Map<Integer, Integer> loanCountByMonth = new TreeMap<>();

        for (int i=0; i < 13; i++) {
            loanCountByMonth.put(i, 0);
        }


        for (Loan l : loans) {
            String date = l.getLoanDate();
            String[] splitDate = date.split("-");
            int idx = Integer.parseInt(splitDate[1]) -1;

            loanCountByMonth.put(idx, loanCountByMonth.get(idx) + 1);
        }

        // model.addAttribute("months", loanCountByMonth.keySet());
        // model.addAttribute("values", loanCountByMonth.values());

        return loanCountByMonth;

    }

    
    /** 
     * @param loans
     * @return Map<String, Integer>
     */
    // @GetMapping("/loansPerMonth")
    public Map<String, Integer> getLoansPerAttraction(List<Loan> loans) {
        Map<String, Integer> loansPerAttraction = new TreeMap<>();


        for (Loan l : loans) {
            String attraction = l.getAttraction();

            if (loansPerAttraction.get(attraction) == null) {
                loansPerAttraction.put(attraction, 1);
            } else {
                loansPerAttraction.put(attraction, loansPerAttraction.get(attraction) + 1);
            }

        }
        
        // for (int i=0; i<list.size(); i++) {
        //     Map.Entry<String, Integer> entry = list.get(i);
        //     keys.add(entry.getKey());
        //     values.add(entry.getValue());
        // // }

        // model.addAttribute("attractions", keys);
        // model.addAttribute("count", values);

        // return "dataCharts";

        return loansPerAttraction;
    }

    
    /** 
     * @param loans
     * @return String
     */
    public String getAverageNumOfLoans(List<Loan> loans) {

        Map<Long, Integer> map = new HashMap<>();

        for (Loan l : loans) {
            Long staffID = l.getStaff().getStaffId();

            if (map.get(staffID) == null) {
                map.put(staffID, 1);
            } else {
                map.put(staffID, map.get(staffID) + 1);
            }
        }

        int numEmployees = map.keySet().size();
        int numLoans = loans.size();

        float averageLoans = ((float) numLoans) / numEmployees;

        return String.format("%.2f", averageLoans);

    }

    
    /** 
     * @param loans
     * @return List<Entry<Staff, Integer>>
     */
    public List<Map.Entry<Staff, Integer>> getTopBorrowers(List<Loan> loans) {

        Map<Staff, Integer> map = new TreeMap<>();

        for (Loan l : loans) {
            Staff staff = l.getStaff();

            if (map.get(staff) == null) {
                map.put(staff, 1);
            } else {
                map.put(staff, map.get(staff) + 1);
            }
        }

        // Sort the treemap by its values, highest to lowest
        Set<Map.Entry<Staff, Integer>> entrySet = map.entrySet();
        List<Map.Entry<Staff, Integer>> list = new ArrayList<>(entrySet);

        Collections.sort(list, new Comparator<Map.Entry<Staff, Integer>>() {
            @Override
            public int compare(Map.Entry<Staff, Integer> o1, Map.Entry<Staff, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });

        List<Map.Entry<Staff, Integer>> finalList = new ArrayList<>();

        // Take only top 5
        if (list.size() > 5) {
            for (int i=0; i<5; i++) {
                finalList.add(list.get(i));
            }
        } else {
            finalList = list;
        }

        return finalList;

    }


}
