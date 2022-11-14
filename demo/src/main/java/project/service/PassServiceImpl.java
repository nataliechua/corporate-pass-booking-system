package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.entity.*;
import project.repository.*;
import project.exception.*;
import project.dto.*;
import java.util.*;

@Service
public class PassServiceImpl implements PassService {
    @Autowired
    private PassRepository passRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Override
    public List<Pass> getAllPasses() {
        return passRepository.findAll();
    }

    @Override
    public Pass getPassById(Long passId) {
       Optional<Pass> pass = passRepository.findById(passId); 

        if (!pass.isPresent()) {
            throw new ObjectNotFoundException("Pass does not exist in the database");
        }

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

    public Map<String, Integer> getMapOfPassAvailabilityByDate(String date) {
        List<Pass> passes = passRepository.findAvailablePassesForADate(date);
        
        Map<String, Integer> map = new HashMap<>();

        for (Pass onePass : passes) {
            if (map.containsKey(onePass.getPassType())) {
                map.put(onePass.getPassType(), map.get(onePass.getPassType()) + 1);
            } else {
                map.put(onePass.getPassType(),1);
            }
        }
        
        return map;
    } 

    public List<Pass> getAvailablePassesForPassTypeAndDate(String passType, String date) {
        List<Pass> passes = passRepository.findAvailablePassesForPassTypeAndDate(passType, date);
        
        return passes;
    } 

    public Map<String, PassDTO> getPassTypeInfoWithAvailableAndTotalCount(String date) {
        Map<String, PassDTO> map = new HashMap<>();

        List<Pass> passList = passRepository.findActivePasses();

        for (Pass onePass : passList) {
            String type = onePass.getPassType();

            PassDTO dto;
            
            if (!map.containsKey(type)) {
                dto = new PassDTO(type, onePass.getAttractions(), onePass.getPeoplePerPass(), onePass.getIsDigital(), onePass.getReplacementFee());
                map.put(type, dto);
            }

            dto = map.get(type);
            dto.setNumTotal(dto.getNumTotal() + 1);
            
        }

        List<Pass> availablePasses = passRepository.findAvailablePassesForADate(date);

        for (Pass availPass : availablePasses) {
            String type = availPass.getPassType();
            PassDTO dto = map.get(type);
            dto.setNumAvailable(dto.getNumAvailable() + 1);
        }

        return map;
    }
    
    public void reportLostPass(Long passId) {
        Pass pass = passRepository.findById(passId).get();
        List<Long> loanIdList = loanRepository.findLoanByPass(passId);

        pass.setIsPassActive("FALSE");
        passRepository.save(pass);

        for (Long loanId : loanIdList) {
            Loan loan = loanRepository.findById(loanId).get();
            if ((loan.getLoanStatus()).equals("collected")) {
                loan.setLoanStatus("lost");
                loanRepository.save(loan);

                // need to make this a separate method
                // Staff staff = loan.getStaff();
                // staff.setIsAdminHold("TRUE");
                // staffRepository.save(staff);
            }
        }
    }

    public void foundPass(Long passId) {
        Pass pass = passRepository.findById(passId).get();
        List<Long> loanIdList = loanRepository.findLoanByPass(passId);

        pass.setIsPassActive("TRUE");
        passRepository.save(pass);

        for (Long loanId : loanIdList) {
            Loan loan = loanRepository.findById(loanId).get();

            loan.setLoanStatus("returned");
            loanRepository.save(loan);
        }
    }

    public Loan getLoanByPassAndDate(Pass p, String date) {
        return passRepository.findLoanForAPassAndDate(p, date);
    }

}
