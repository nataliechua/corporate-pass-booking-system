package project.service;

import java.util.*;

import project.entity.*;
import project.dto.*;

public interface PassService {

    public List<Pass> getAllPasses();

    public Pass getPassById(Long passId);

    public Pass savePass(Pass pass);

    public Pass updatePass(Long passId, Pass pass);

    public Map<String, Integer> getTotalPassNum();

    public Map<String, Integer> getMapOfPassAvailabilityByDate(String date);

    public List<Pass> getAvailablePassesForPassTypeAndDate(String passType, String date);

    public Map<String, PassDTO> getPassTypeInfoWithAvailableAndTotalCount(String date);
}
