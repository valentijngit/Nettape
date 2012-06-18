package net.nettape.dal.object;
// Generated 11-jan-2012 20:37:05 by Hibernate Tools 3.4.0.CR1


import java.util.HashSet;
import java.util.Set;

/**
 * Language generated by hbm2java
 */
public class Language  implements java.io.Serializable {


     private Integer languageid;
     private String code;
     private String name;
     private Set settingses = new HashSet(0);
     private Set languagesettingses = new HashSet(0);
     private Set languagesettingses_1 = new HashSet(0);
     private Set settingses_1 = new HashSet(0);

    public Language() {
    }

    public Language(String code, String name, Set settingses, Set languagesettingses, Set languagesettingses_1, Set settingses_1) {
       this.code = code;
       this.name = name;
       this.settingses = settingses;
       this.languagesettingses = languagesettingses;
       this.languagesettingses_1 = languagesettingses_1;
       this.settingses_1 = settingses_1;
    }
   
    public Integer getLanguageid() {
        return this.languageid;
    }
    
    public void setLanguageid(Integer languageid) {
        this.languageid = languageid;
    }
    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public Set getSettingses() {
        return this.settingses;
    }
    
    public void setSettingses(Set settingses) {
        this.settingses = settingses;
    }
    public Set getLanguagesettingses() {
        return this.languagesettingses;
    }
    
    public void setLanguagesettingses(Set languagesettingses) {
        this.languagesettingses = languagesettingses;
    }
    public Set getLanguagesettingses_1() {
        return this.languagesettingses_1;
    }
    
    public void setLanguagesettingses_1(Set languagesettingses_1) {
        this.languagesettingses_1 = languagesettingses_1;
    }
    public Set getSettingses_1() {
        return this.settingses_1;
    }
    
    public void setSettingses_1(Set settingses_1) {
        this.settingses_1 = settingses_1;
    }




}

