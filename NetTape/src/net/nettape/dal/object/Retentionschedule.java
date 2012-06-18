package net.nettape.dal.object;
// Generated 11-jan-2012 20:37:05 by Hibernate Tools 3.4.0.CR1


import java.util.Date;

/**
 * Retentionschedule generated by hbm2java
 */
public class Retentionschedule  implements java.io.Serializable {


     private Integer retentionscheduleid;
     private Backupset backupset;
     private Integer datetype;
     private Integer copies;
     private Integer daysofweek;
     private Integer day;
     private Integer monthsofyear;
     private Date date;

    public Retentionschedule() {
    }

	
    public Retentionschedule(Backupset backupset) {
        this.backupset = backupset;
    }
    public Retentionschedule(Backupset backupset, Integer datetype, Integer copies, Integer daysofweek, Integer day, Integer monthsofyear, Date date) {
       this.backupset = backupset;
       this.datetype = datetype;
       this.copies = copies;
       this.daysofweek = daysofweek;
       this.day = day;
       this.monthsofyear = monthsofyear;
       this.date = date;
    }
   
    public Integer getRetentionscheduleid() {
        return this.retentionscheduleid;
    }
    
    public void setRetentionscheduleid(Integer retentionscheduleid) {
        this.retentionscheduleid = retentionscheduleid;
    }
    public Backupset getBackupset() {
        return this.backupset;
    }
    
    public void setBackupset(Backupset backupset) {
        this.backupset = backupset;
    }
    public Integer getDatetype() {
        return this.datetype;
    }
    
    public void setDatetype(Integer datetype) {
        this.datetype = datetype;
    }
    public Integer getCopies() {
        return this.copies;
    }
    
    public void setCopies(Integer copies) {
        this.copies = copies;
    }
    public Integer getDaysofweek() {
        return this.daysofweek;
    }
    
    public void setDaysofweek(Integer daysofweek) {
        this.daysofweek = daysofweek;
    }
    public Integer getDay() {
        return this.day;
    }
    
    public void setDay(Integer day) {
        this.day = day;
    }
    public Integer getMonthsofyear() {
        return this.monthsofyear;
    }
    
    public void setMonthsofyear(Integer monthsofyear) {
        this.monthsofyear = monthsofyear;
    }
    public Date getDate() {
        return this.date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }




}

