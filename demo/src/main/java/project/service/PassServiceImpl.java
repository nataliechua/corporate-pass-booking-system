package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.entity.*;
import project.repository.*;
import java.util.*;

@Service
public class PassServiceImpl implements PassService {
    @Autowired
    private PassRepository passRepository;
    @Autowired
    private LoanRepository loanRepository;

    @Override
    public List<Pass> getAllPasses() {
        return passRepository.findAll();
    }

    @Override
    public Pass getPassById(Long passId) {
        // .get() to get value of department
       Optional<Pass> pass = passRepository.findById(passId); 

    //    if (!staff.isPresent()) {
    //     throw new DepartmentNotFoundException("Department Not Available");
    //    }

       return pass.get();
    }

    @Override
    public Pass savePass(Pass pass) {
        return passRepository.save(pass);
    }

    @Override
    public Pass updatePass(Long passId, Pass pass) {
        Pass passDB = passRepository.findById(passId).get();
        
        // Check if parameters are null
        if (Objects.nonNull(pass.getPassType()) && !("".equalsIgnoreCase(pass.getPassType()))) {
            passDB.setPassType(pass.getPassType());
        }

        // Check if parameters are null
        if (Objects.nonNull(pass.getAttractions()) && !("".equalsIgnoreCase(pass.getAttractions()))) {
            passDB.setAttractions(pass.getAttractions());
        }

        // Check if parameters are null
        if (Objects.nonNull(pass.getPeoplePerPass())) {
            passDB.setPeoplePerPass(pass.getPeoplePerPass());
        }

        // Check if parameters are null
        if (Objects.nonNull(pass.getReplacementFee())) {
            passDB.setReplacementFee(pass.getReplacementFee());
        }

        // Check if parameters are null
        if (Objects.nonNull(pass.getIsDigital()) && !("".equalsIgnoreCase(pass.getIsDigital()))) {
            passDB.setIsDigital(pass.getIsDigital());
        }
        
        return passRepository.save(passDB);
    }

    public Map<String, Integer> getTotalPassNum() {
        Map<String, Integer> map = new HashMap<>();

        List<Pass> passList = passRepository.findAll();

        for (Pass onePass : passList) {
            String type = onePass.getPassType();

            if (map.containsKey(type)) {
                map.put(type, map.get(type) + 1);
            } else {
                map.put(type,1);
            }
        }

        return map;
    }

    public Map<String, Integer> getPassAvailabilityByDate(String date) {
        List<Loan> loanList = loanRepository.findByLoanDate(date);
        Map<String, Integer> map = new HashMap<>();

        for (Loan oneLoan : loanList) {
            Set<Pass> passSet = oneLoan.getPassList();
            for (Pass pass : passSet) {
                String type = pass.getPassType();

                if (map.containsKey(type)) {
                    map.put(type, map.get(type) + 1);
                } else {
                    map.put(type, 1);
                }

            }
        }
        return map;
    } 

}
