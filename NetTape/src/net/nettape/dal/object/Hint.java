package net.nettape.dal.object;
// Generated 11-jan-2012 20:37:05 by Hibernate Tools 3.4.0.CR1



/**
 * Hint generated by hbm2java
 */
public class Hint  implements java.io.Serializable {


     private String key;
     private String value;

    public Hint() {
    }

	
    public Hint(String key) {
        this.key = key;
    }
    public Hint(String key, String value) {
       this.key = key;
       this.value = value;
    }
   
    public String getKey() {
        return this.key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    public String getValue() {
        return this.value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }




}


