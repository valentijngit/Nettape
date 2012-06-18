package net.nettape.dal.object;
// Generated 11-jan-2012 20:37:05 by Hibernate Tools 3.4.0.CR1


import java.util.HashSet;
import java.util.Set;

/**
 * Server generated by hbm2java
 */
public class Server  implements java.io.Serializable {


     private Integer serverid;
     private String ip;
     private String name;
     private String login;
     private String password;
     private String backuppath;
     private Boolean default_;
     private Set users = new HashSet(0);
     private Set users_1 = new HashSet(0);

    public Server() {
    }

    public Server(String ip, String name, String login, String password, String backuppath, Boolean default_, Set users, Set users_1) {
       this.ip = ip;
       this.name = name;
       this.login = login;
       this.password = password;
       this.backuppath = backuppath;
       this.default_ = default_;
       this.users = users;
       this.users_1 = users_1;
    }
   
    public Integer getServerid() {
        return this.serverid;
    }
    
    public void setServerid(Integer serverid) {
        this.serverid = serverid;
    }
    public String getIp() {
        return this.ip;
    }
    
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getLogin() {
        return this.login;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    public String getBackuppath() {
        return this.backuppath;
    }
    
    public void setBackuppath(String backuppath) {
        this.backuppath = backuppath;
    }
    public Boolean getDefault_() {
        return this.default_;
    }
    
    public void setDefault_(Boolean default_) {
        this.default_ = default_;
    }
    public Set getUsers() {
        return this.users;
    }
    
    public void setUsers(Set users) {
        this.users = users;
    }
    public Set getUsers_1() {
        return this.users_1;
    }
    
    public void setUsers_1(Set users_1) {
        this.users_1 = users_1;
    }




}

