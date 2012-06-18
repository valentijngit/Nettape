package net.nettape.dal.object;
// Generated 11-jan-2012 20:37:05 by Hibernate Tools 3.4.0.CR1


import java.util.Date;

/**
 * Schedule generated by hbm2java
 */
public class Schedule  implements java.io.Serializable {


     private Integer scheduleid;
     private Backupset backupset;
     private String name;
     private Long daysofweek;
     private Integer length;
     private Date time;
     private Integer datetype;
     private Integer day;
     private Date date;
     private Boolean running;
     private Date lastruntime;
     private Date lastrundate;

    public Schedule() {
    }

    public Schedule(Backupset backupset, String name, Long daysofweek, Integer length, Date time, Integer datetype, Integer day, Date date, Boolean running, Date lastruntime, Date lastrundate) {
       this.backupset = backupset;
       this.name = name;
       this.daysofweek = daysofweek;
       this.length = length;
       this.time = time;
       this.datetype = datetype;
       this.day = day;
       this.date = date;
       this.running = running;
       this.lastruntime = lastruntime;
       this.lastrundate = lastrundate;
    }
   
    public Integer getScheduleid() {
        return this.scheduleid;
    }
    
    public void setScheduleid(Integer scheduleid) {
        this.scheduleid = scheduleid;
    }
    public Backupset getBackupset() {
        return this.backupset;
    }
    
    public void setBackupset(Backupset backupset) {
        this.backupset = backupset;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public Long getDaysofweek() {
        return this.daysofweek;
    }
    
    public void setDaysofweek(Long daysofweek) {
        this.daysofweek = daysofweek;
    }
    public Integer getLength() {
        return this.length;
    }
    
    public void setLength(Integer length) {
        this.length = length;
    }
    public Date getTime() {
        return this.time;
    }
    
    public void setTime(Date time) {
        this.time = time;
    }
    public Integer getDatetype() {
        return this.datetype;
    }
    
    public void setDatetype(Integer datetype) {
        this.datetype = datetype;
    }
    public Integer getDay() {
        return this.day;
    }
    
    public void setDay(Integer day) {
        this.day = day;
    }
    public Date getDate() {
        return this.date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    public Boolean getRunning() {
        return this.running;
    }
    
    public void setRunning(Boolean running) {
        this.running = running;
    }
    public Date getLastruntime() {
        return this.lastruntime;
    }
    
    public void setLastruntime(Date lastruntime) {
        this.lastruntime = lastruntime;
    }
    public Date getLastrundate() {
        return this.lastrundate;
    }
    
    public void setLastrundate(Date lastrundate) {
        this.lastrundate = lastrundate;
    }




}

