/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nic;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author Ramesh Naidu
 */
public class Iti_Registration_Form extends org.apache.struts.action.ActionForm {
    
    String iti_code;
    String iti_name;
    String userName;
    String password;
    String govt;
    String iti_noniti;
    String address;
    String city_town;
    String dist_code;
    String mand_code;
    String pin_code;
    String mobile;
    String landlineNumber;
    String email;
    String principalName;
    String tot_strength;
    String capacity;
    String year_est;
    String description;
    String old_iti_code;
    String land;
    String vtpregno;
    FormFile photo;
    String designation_code[];
String desig_code[];
int strength[],vacant[],fill[];
String builtarea;
boolean dkwateravailable;
    
int nooftoilets,nooflabs,noofclassrooms,examroomcapacity;

    public String getIti_code() {
        return iti_code;
    }

    public void setIti_code(String iti_code) {
        this.iti_code = iti_code;
    }

    public String getIti_name() {
        return iti_name;
    }

    public void setIti_name(String iti_name) {
        this.iti_name = iti_name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGovt() {
        return govt;
    }

    public void setGovt(String govt) {
        this.govt = govt;
    }

    public String getIti_noniti() {
        return iti_noniti;
    }

    public void setIti_noniti(String iti_noniti) {
        this.iti_noniti = iti_noniti;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity_town() {
        return city_town;
    }

    public void setCity_town(String city_town) {
        this.city_town = city_town;
    }

    public String getDist_code() {
        return dist_code;
    }

    public void setDist_code(String dist_code) {
        this.dist_code = dist_code;
    }

    public String getMand_code() {
        return mand_code;
    }

    public void setMand_code(String mand_code) {
        this.mand_code = mand_code;
    }

    public String getPin_code() {
        return pin_code;
    }

    public void setPin_code(String pin_code) {
        this.pin_code = pin_code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLandlineNumber() {
        return landlineNumber;
    }

    public void setLandlineNumber(String landlineNumber) {
        this.landlineNumber = landlineNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public String getTot_strength() {
        return tot_strength;
    }

    public void setTot_strength(String tot_strength) {
        this.tot_strength = tot_strength;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getYear_est() {
        return year_est;
    }

    public void setYear_est(String year_est) {
        this.year_est = year_est;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOld_iti_code() {
        return old_iti_code;
    }

    public void setOld_iti_code(String old_iti_code) {
        this.old_iti_code = old_iti_code;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public String getVtpregno() {
        return vtpregno;
    }

    public void setVtpregno(String vtpregno) {
        this.vtpregno = vtpregno;
    }

    public FormFile getPhoto() {
        return photo;
    }

    public void setPhoto(FormFile photo) {
        this.photo = photo;
    }

    public String[] getDesignation_code() {
        return designation_code;
    }

    public void setDesignation_code(String[] designation_code) {
        this.designation_code = designation_code;
    }

    public String[] getDesig_code() {
        return desig_code;
    }

    public void setDesig_code(String[] desig_code) {
        this.desig_code = desig_code;
    }

    public int[] getStrength() {
        return strength;
    }

    public void setStrength(int[] strength) {
        this.strength = strength;
    }

    public int[] getVacant() {
        return vacant;
    }

    public void setVacant(int[] vacant) {
        this.vacant = vacant;
    }

    public int[] getFill() {
        return fill;
    }

    public void setFill(int[] fill) {
        this.fill = fill;
    }

    public String getBuiltarea() {
        return builtarea;
    }

    public void setBuiltarea(String builtarea) {
        this.builtarea = builtarea;
    }

    public boolean isDkwateravailable() {
        return dkwateravailable;
    }

    public void setDkwateravailable(boolean dkwateravailable) {
        this.dkwateravailable = dkwateravailable;
    }

    public int getNooftoilets() {
        return nooftoilets;
    }

    public void setNooftoilets(int nooftoilets) {
        this.nooftoilets = nooftoilets;
    }

    public int getNooflabs() {
        return nooflabs;
    }

    public void setNooflabs(int nooflabs) {
        this.nooflabs = nooflabs;
    }

    public int getNoofclassrooms() {
        return noofclassrooms;
    }

    public void setNoofclassrooms(int noofclassrooms) {
        this.noofclassrooms = noofclassrooms;
    }

    public int getExamroomcapacity() {
        return examroomcapacity;
    }

    public void setExamroomcapacity(int examroomcapacity) {
        this.examroomcapacity = examroomcapacity;
    }


   
    public Iti_Registration_Form() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param request The HTTP Request we are processing.
     * @return
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        
        return errors;
    }
}
