package net.nettape.dal.object;
// Generated 11-jan-2012 20:37:05 by Hibernate Tools 3.4.0.CR1


import java.util.HashSet;
import java.util.Set;

/**
 * Module generated by hbm2java
 */
public class Module  implements java.io.Serializable {


     private Integer moduleid;
     private String name;
     private Set reluserModules = new HashSet(0);
     private Set trialmodulesettingses = new HashSet(0);
     private Set reluserModules_1 = new HashSet(0);
     private Set trialmodulesettingses_1 = new HashSet(0);

    public Module() {
    }

    public Module(String name, Set reluserModules, Set trialmodulesettingses, Set reluserModules_1, Set trialmodulesettingses_1) {
       this.name = name;
       this.reluserModules = reluserModules;
       this.trialmodulesettingses = trialmodulesettingses;
       this.reluserModules_1 = reluserModules_1;
       this.trialmodulesettingses_1 = trialmodulesettingses_1;
    }
   
    public Integer getModuleid() {
        return this.moduleid;
    }
    
    public void setModuleid(Integer moduleid) {
        this.moduleid = moduleid;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public Set getReluserModules() {
        return this.reluserModules;
    }
    
    public void setReluserModules(Set reluserModules) {
        this.reluserModules = reluserModules;
    }
    public Set getTrialmodulesettingses() {
        return this.trialmodulesettingses;
    }
    
    public void setTrialmodulesettingses(Set trialmodulesettingses) {
        this.trialmodulesettingses = trialmodulesettingses;
    }
    public Set getReluserModules_1() {
        return this.reluserModules_1;
    }
    
    public void setReluserModules_1(Set reluserModules_1) {
        this.reluserModules_1 = reluserModules_1;
    }
    public Set getTrialmodulesettingses_1() {
        return this.trialmodulesettingses_1;
    }
    
    public void setTrialmodulesettingses_1(Set trialmodulesettingses_1) {
        this.trialmodulesettingses_1 = trialmodulesettingses_1;
    }




}


