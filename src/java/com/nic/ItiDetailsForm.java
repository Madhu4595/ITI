/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.validator.ValidatorForm;
import DBC.Validations;

/**
 *
 * @author SUBBAREDDY
 */
public class ItiDetailsForm extends ValidatorForm {

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

    public ItiDetailsForm() {
    }

    public String getIti_code() {
        return beans.MyUtil.filterBad(iti_code);
    }

    public void setIti_code(String iti_code) {
        this.iti_code = iti_code;
    }

    public String getIti_name() {
        return beans.MyUtil.filterBad(iti_name);
    }

    public void setIti_name(String iti_name) {
        this.iti_name = iti_name;
    }

    public String getUserName() {
        return beans.MyUtil.filterBad(userName);
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return beans.MyUtil.filterBad(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return beans.MyUtil.filterBad(address);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity_town() {
        return beans.MyUtil.filterBad(city_town);
    }

    public void setCity_town(String city_town) {
        this.city_town = city_town;
    }

    public String getDist_code() {
        return beans.MyUtil.filterBad(dist_code);
    }

    public void setDist_code(String dist_code) {
        this.dist_code = dist_code;
    }

    public String getMand_code() {
        return beans.MyUtil.filterBad(mand_code);
    }

    public void setMand_code(String mand_code) {
        this.mand_code = mand_code;
    }

    public String getPin_code() {
        return beans.MyUtil.filterBad(pin_code);
    }

    public void setPin_code(String pin_code) {
        this.pin_code = pin_code;
    }

    public String getMobile() {
        return beans.MyUtil.filterBad(mobile);
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLandlineNumber() {
        return beans.MyUtil.filterBad(landlineNumber);
    }

    public void setLandlineNumber(String landlineNumber) {
        this.landlineNumber = landlineNumber;
    }

    public String getEmail() {
        return beans.MyUtil.filterBad(email);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrincipalName() {
        return beans.MyUtil.filterBad(principalName);
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public String getTot_strength() {
        return beans.MyUtil.filterBad(tot_strength);
    }

    public void setTot_strength(String tot_strength) {
        this.tot_strength = tot_strength;
    }

    ;
public String getCapacity() {
        return beans.MyUtil.filterBad(capacity);
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getYear_est() {
        return beans.MyUtil.filterBad(year_est);
    }

    public void setYear_est(String year_est) {
        this.year_est = year_est;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return beans.MyUtil.filterBad(description);
    }

    public String getGovt() {
        return beans.MyUtil.filterBad(govt);
    }

    public void setGovt(String govt) {
        this.govt = govt;
    }

    public void setIti_noniti(String iti_noniti) {
        this.iti_noniti = iti_noniti;
    }

    public String getIti_noniti() {
        return beans.MyUtil.filterBad(iti_noniti);
    }

//validations
    @Override
    public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {

        //DBC.Validations v=new DBC.Validations();
        ActionErrors errors = new ActionErrors();

        //iti_code
        if (getIti_code() == null || getIti_code().length() < 1) {
            errors.add("iti_code", new ActionMessage("RegistrationForm.iti_code.required"));
        }
        if (!Validations.Validate(iti_code, 2)) {
            errors.add("iti_code", new ActionMessage("RegistrationForm.iti_code.invalid"));
        } else if (getIti_code().length() > 4) {
            errors.add("iti_code", new ActionMessage("RegistrationForm.iti_code.length"));
        }

        //iti_name
        if (getIti_name() == null || getIti_name().length() < 1) {
            errors.add("iti_name", new ActionMessage("RegistrationForm.iti_name.required"));
        } else if (getIti_name().length() > 60) {
            errors.add("iti_name", new ActionMessage("RegistrationForm.iti_name.length"));
        }

        //username
        if (getUserName() == null || getUserName().length() < 1) {
            errors.add("userName", new ActionMessage("RegistrationForm.userName.required"));
        } else if (!DBC.Validations.Validate(userName, 4)) {
            errors.add("userName", new ActionMessage("RegistrationForm.userName.invalid"));
        } else if (getUserName().length() > 50) {
            errors.add("userName", new ActionMessage("RegistrationForm.userName.length"));
        }

        //password
        if (password == null || password.length() < 1) {
            errors.add("password", new ActionMessage("RegistrationForm.password.required"));
        } else if (getPassword().length() > 15) {
            errors.add("password", new ActionMessage("RegistrationForm.password.length1"));
        }

        //address
        if (address == null || address.length() < 1) {
            errors.add("address", new ActionMessage("RegistrationForm.address.required"));
        } else if (address.length() > 100) {
            errors.add("address", new ActionMessage("RegistrationForm.address.length"));
        } else if (!DBC.Validations.Validate(address, 12)) {
            errors.add("address", new ActionMessage("RegistrationForm.address.invalid"));
        }

        //city_town
        if (city_town == null || city_town.length() < 1) {
            errors.add("city_town", new ActionMessage("RegistrationForm.city_town.required"));
        } else if (city_town.length() > 50) {
            errors.add("city_town", new ActionMessage("RegistrationForm.city_town.length"));
        } else if (!DBC.Validations.Validate(city_town, 3)) {
            errors.add("city_town", new ActionMessage("RegistrationForm.city_town.invalid"));
        }

        //dist_code
        if (dist_code == null || dist_code.length() < 1) {
            errors.add("dist_code", new ActionMessage("RegistrationForm.dist_code.required"));
        } else if (dist_code.length() > 2) {
            errors.add("dist_code", new ActionMessage("RegistrationForm.dist_code.maxlength"));
        }

        /* //mand_code
        
         if(mand_code==null||mand_code.length()<1){
         errors.add("mand_code", new ActionMessage("RegistrationForm.mand_code.required"));
         }*/
        //pin_code        
        if (pin_code == null || pin_code.length() < 1) {
            errors.add("pin_code", new ActionMessage("RegistrationForm.pin_code.required"));
        } else if (!DBC.Validations.Validate(pin_code, 2)) {
            errors.add("pin_code", new ActionMessage("RegistrationForm.pin_code.invalid"));
        } else if (pin_code.length() > 6) {
            errors.add("pin_code", new ActionMessage("RegistrationForm.pin_code.maxlength"));
        }

        if (govt == null || govt.length() < 1) {
            errors.add(govt, new ActionMessage("RegistrationForm.govt.required"));
        } else if (!DBC.Validations.Validate(govt, 6)) {
            errors.add(govt, new ActionMessage("RegistrationForm.govt.invalid"));
        } else if (govt.length() > 1) {
            errors.add(govt, new ActionMessage("RegistrationForm.govt.maxlength"));
        }

        if (iti_noniti == null || iti_noniti.length() < 1) {
            errors.add(iti_noniti, new ActionMessage("RegistrationForm.iti_noniti.required"));
        } else if (!DBC.Validations.Validate(iti_noniti, 6)) {
            errors.add(iti_noniti, new ActionMessage("RegistrationForm.iti_noniti.invalid"));
        } else if (iti_noniti.length() > 6) {
            errors.add(iti_noniti, new ActionMessage("RegistrationForm.iti_noniti.maxlength"));
        }

        if (mobile != null && mobile.length() > 0) {
            if (!DBC.Validations.Validate(mobile, 10)) {
                errors.add(mobile, new ActionMessage("RegistrationForm.mobile.invalid"));
            }
        }

        if (landlineNumber != null && landlineNumber.length() > 0) {
            if (!DBC.Validations.Validate(landlineNumber, 2)) {
                errors.add(mobile, new ActionMessage("RegistrationForm.landlineNumber.invalid"));
            } else if (landlineNumber.length() < 10 || landlineNumber.length() > 15) {
                errors.add(mobile, new ActionMessage("RegistrationForm.landlineNumber.invalid"));
            }
        }
        if (email != null && email.length() > 0) {
            Pattern pattern = Pattern.compile("^([a-z]+\\w*)+\\@+(\\w+\\w*)+\\.+(\\w+\\w*)*$");
            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                errors.add(email, new ActionMessage("RegistrationForm.email.invalid"));
            } else if (email.length() > 50) {
                errors.add(email, new ActionMessage("RegistrationForm.email.length"));
            }
        }

        if (principalName == null || principalName.length() < 1) {
            errors.add(principalName, new ActionMessage("RegistrationForm.principalName.required"));
        } else if (!DBC.Validations.Validate(principalName, 3)) {
            errors.add(principalName, new ActionMessage("RegistrationForm.principalName.invalid"));
        } else if (principalName.length() > 50) {
            errors.add(principalName, new ActionMessage("RegistrationForm.principalName.length"));
        }

        if (tot_strength != null && tot_strength.length() > 1) {
            if (!DBC.Validations.Validate(tot_strength, 2)) {
                errors.add(tot_strength, new ActionMessage("RegistrationForm.tot_strength.invalid"));
            } else if (tot_strength.length() > 3) {
                errors.add(tot_strength, new ActionMessage("RegistrationForm.tot_strength.length"));
            }
        }

        if (capacity != null && capacity.length() > 1) {
            if (!DBC.Validations.Validate(capacity, 2)) {
                errors.add(capacity, new ActionMessage("RegistrationForm.capacity.invalid"));
            } else if (capacity.length() > 3) {
                errors.add(capacity, new ActionMessage("RegistrationForm.capacity.length"));
            }
        }

        if (year_est == null && year_est.length() < 1) {
            errors.add(year_est, new ActionMessage("RegistrationForm.year_est.required"));
        } else if (year_est.length() > 4) {
            errors.add(year_est, new ActionMessage("RegistrationForm.year_est.length"));
        } else if (!DBC.Validations.Validate(year_est, 2)) {
            errors.add(year_est, new ActionMessage("RegistrationForm.year_est.invalid"));
        }

        if (description != null && description.length() > 1) {
            if (!DBC.Validations.Validate(description, 12)) {
                errors.add(description, new ActionMessage("RegistrationForm.description.invalid"));
            } else if (description.length() > 1000) {
                errors.add(description, new ActionMessage("RegistrationForm.description.length"));
            }
        }

        return errors;
    }
}
