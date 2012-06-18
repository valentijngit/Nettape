package net.nettape.dal.object;
// Generated 11-jan-2012 20:37:05 by Hibernate Tools 3.4.0.CR1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Restore generated by hbm2java
 */
public class Restore  implements java.io.Serializable {


     private Integer restoreid;
     private Status status;
     private Date datetime;
     private Date datetimeended;
     private String restorepath;
     private Boolean overwrite;
     private Boolean restorepermissions;
     private String ipaddress;
     private Set filters = new HashSet(0);
     private Set filters_1 = new HashSet(0);
     private Set restoreitems = new HashSet(0);
     private Set restoreitems_1 = new HashSet(0);

    public Restore() {
    }

    public Restore(Status status, Date datetime, Date datetimeended, String restorepath, Boolean overwrite, Boolean restorepermissions, String ipaddress, Set filters, Set filters_1, Set restoreitems, Set restoreitems_1) {
       this.status = status;
       this.datetime = datetime;
       this.datetimeended = datetimeended;
       this.restorepath = restorepath;
       this.overwrite = overwrite;
       this.restorepermissions = restorepermissions;
       this.ipaddress = ipaddress;
       this.filters = filters;
       this.filters_1 = filters_1;
       this.restoreitems = restoreitems;
       this.restoreitems_1 = restoreitems_1;
    }
   
    public Integer getRestoreid() {
        return this.restoreid;
    }
    
    public void setRestoreid(Integer restoreid) {
        this.restoreid = restoreid;
    }
    public Status getStatus() {
        return this.status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    public Date getDatetime() {
        return this.datetime;
    }
    
    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
    public Date getDatetimeended() {
        return this.datetimeended;
    }
    
    public void setDatetimeended(Date datetimeended) {
        this.datetimeended = datetimeended;
    }
    public String getRestorepath() {
        return this.restorepath;
    }
    
    public void setRestorepath(String restorepath) {
        this.restorepath = restorepath;
    }
    public Boolean getOverwrite() {
        return this.overwrite;
    }
    
    public void setOverwrite(Boolean overwrite) {
        this.overwrite = overwrite;
    }
    public Boolean getRestorepermissions() {
        return this.restorepermissions;
    }
    
    public void setRestorepermissions(Boolean restorepermissions) {
        this.restorepermissions = restorepermissions;
    }
    public String getIpaddress() {
        return this.ipaddress;
    }
    
    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }
    public Set getFilters() {
        return this.filters;
    }
    
    public void setFilters(Set filters) {
        this.filters = filters;
    }
    public Set getFilters_1() {
        return this.filters_1;
    }
    
    public void setFilters_1(Set filters_1) {
        this.filters_1 = filters_1;
    }
    public Set getRestoreitems() {
        return this.restoreitems;
    }
    
    public void setRestoreitems(Set restoreitems) {
        this.restoreitems = restoreitems;
    }
    public Set getRestoreitems_1() {
        return this.restoreitems_1;
    }
    
    public void setRestoreitems_1(Set restoreitems_1) {
        this.restoreitems_1 = restoreitems_1;
    }




}


