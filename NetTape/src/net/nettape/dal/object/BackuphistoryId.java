package net.nettape.dal.object;
// Generated 11-jan-2012 20:37:05 by Hibernate Tools 3.4.0.CR1



/**
 * BackuphistoryId generated by hbm2java
 */
public class BackuphistoryId  implements java.io.Serializable {


     private String path;
     private int backupsetid;

    public BackuphistoryId() {
    }

    public BackuphistoryId(String path, int backupsetid) {
       this.path = path;
       this.backupsetid = backupsetid;
    }
   
    public String getPath() {
        return this.path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    public int getBackupsetid() {
        return this.backupsetid;
    }
    
    public void setBackupsetid(int backupsetid) {
        this.backupsetid = backupsetid;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof BackuphistoryId) ) return false;
		 BackuphistoryId castOther = ( BackuphistoryId ) other; 
         
		 return ( (this.getPath()==castOther.getPath()) || ( this.getPath()!=null && castOther.getPath()!=null && this.getPath().equals(castOther.getPath()) ) )
 && (this.getBackupsetid()==castOther.getBackupsetid());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getPath() == null ? 0 : this.getPath().hashCode() );
         result = 37 * result + this.getBackupsetid();
         return result;
   }   


}


