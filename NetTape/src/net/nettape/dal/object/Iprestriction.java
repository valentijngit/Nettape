package net.nettape.dal.object;
// Generated 11-jan-2012 20:37:05 by Hibernate Tools 3.4.0.CR1



/**
 * Iprestriction generated by hbm2java
 */
public class Iprestriction  implements java.io.Serializable {


     private Integer iprestrictionid;
     private Backupset backupset;
     private String fromip;
     private String toip;

    public Iprestriction() {
    }

    public Iprestriction(Backupset backupset, String fromip, String toip) {
       this.backupset = backupset;
       this.fromip = fromip;
       this.toip = toip;
    }
   
    public Integer getIprestrictionid() {
        return this.iprestrictionid;
    }
    
    public void setIprestrictionid(Integer iprestrictionid) {
        this.iprestrictionid = iprestrictionid;
    }
    public Backupset getBackupset() {
        return this.backupset;
    }
    
    public void setBackupset(Backupset backupset) {
        this.backupset = backupset;
    }
    public String getFromip() {
        return this.fromip;
    }
    
    public void setFromip(String fromip) {
        this.fromip = fromip;
    }
    public String getToip() {
        return this.toip;
    }
    
    public void setToip(String toip) {
        this.toip = toip;
    }




}


