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

    
    /** 
     * @return List<Pass>
     */
    @Override
    public List<Pass> getAllPasses() {
        return passRepository.findAll();
    }

    
    /** 
     * @param passId
     * @return Pass
     */
    @Override
    public Pass getPassById(Long passId) {
       Optional<Pass> pass = passRepository.findById(passId); 

        if (!pass.isPresent()) {
            throw new ObjectNotFoundException("Pass does not exist in the database");
        }

       return pass.get();
    }

    
    /** 
     * @param pass
     * @return Pass
     */
    @Override
    public Pass savePass(Pass pass) {
        return passRepository.save(pass);
    }

    
    /** 
     * @param passId
     * @param pass
     * @return Pass
     */
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

    
    /** 
     * @return Map<String, Integer>
     */
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

    
    /** 
     * @param date
     * @return Map<String, Integer>
     */
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

    
    /** 
     * @param passType
     * @param date
     * @return List<Pass>
     */
    public List<Pass> getAvailablePassesForPassTypeAndDate(String passType, String date) {
        List<Pass> passes = passRepository.findAvailablePassesForPassTypeAndDate(passType, date);
        
        return passes;
    } 

    
    /** 
     * @param date
     * @return Map<String, PassDTO>
     */
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
    
    
    /** 
     * @param passId
     * @param loanId
     */
    public void reportLostPass(Long passId, Long loanId) {
        Loan loan = loanRepository.findById(loanId).get();
        Set<Pass> passList = loan.getPassList();
        Staff staff = loan.getStaff();

        for(Pass pass:passList){
            if((pass.getPassId()).equals(passId)) {
                pass.setIsPassActive("Lost");
                passRepository.save(pass);
            }
        }
        // loan.setLoanStatus("lost");
        // loanRepository.save(loan);
        cancelLoanForLostPass(passId);
        
        staff.setIsAdminHold("TRUE");
        staffRepository.save(staff);
    }

    
    /** 
     * @param passId
     */
    @Override
    public void cancelLoanForLostPass(Long passId) {
        List<Long> loanIdList = loanRepository.findLoanByPass(passId);

        for(Long loanId:loanIdList) {
            Loan loan = loanRepository.findById(loanId).get();
            if ((loan.getLoanStatus()).equals("not collected")) {
                loan.setLoanStatus("canceled");
                loanRepository.save(loan);
            }
        }
    }

    
    /** 
     * @param passId
     */
    public void foundPass(Long passId) {
        Pass pass = passRepository.findById(passId).get();
        // List<Long> loanIdList = loanRepository.findLoanByPass(passId);

        pass.setIsPassActive("Active");
        passRepository.save(pass);

        // for (Long loanId : loanIdList) {
        //     Loan loan = loanRepository.findById(loanId).get();

        //     loan.setLoanStatus("returned");
        //     loanRepository.save(loan);
        // }
    }

    
    /** 
     * @param p
     * @param date
     * @return Loan
     */
    public Loan getLoanByPassAndDate(Pass p, String date) {
        return passRepository.findLoanForAPassAndDate(p, date);
    }

}
