package project.service;

import java.util.*;

import project.entity.*;

public interface PassService {

    public List<Pass> getAllPasses();

    public Pass getPassById(Long passId);

    public Pass savePass(Pass pass);

    public Pass updatePass(Long passId, Pass pass);
}
