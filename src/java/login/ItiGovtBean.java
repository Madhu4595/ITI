/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

/**
 *
 * @author APDCA
 */
public class ItiGovtBean {
    private String iti_name;
    private String address;
    private String dist_name;
    private String govt;

    public String getGovt() {
        return govt;
    }

    public void setGovt(String govt) {
        this.govt = govt;
    }
    
    

    public String getIti_name() {
        return iti_name;
    }

    public void setIti_name(String iti_name) {
        this.iti_name = iti_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDist_name() {
        return dist_name;
    }

    public void setDist_name(String dist_name) {
        this.dist_name = dist_name;
    }
    
}
