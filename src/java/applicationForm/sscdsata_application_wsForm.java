/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationForm;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author Narendra
 */
public class sscdsata_application_wsForm extends org.apache.struts.action.ActionForm {
     String name;
     String dist_code;

    public String getDist_code() {
        return dist_code;
    }

    public void setDist_code(String dist_code) {
        this.dist_code = dist_code;
    }

    public String getRec() {
        return rec;
    }

    public void setRec(String rec) {
        this.rec = rec;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getL1p1pc() {
        return l1p1pc;
    }

    public void setL1p1pc(String l1p1pc) {
        this.l1p1pc = l1p1pc;
    }

    public String getL1p2pc() {
        return l1p2pc;
    }

    public void setL1p2pc(String l1p2pc) {
        this.l1p2pc = l1p2pc;
    }

    public String getL1mrk() {
        return l1mrk;
    }

    public void setL1mrk(String l1mrk) {
        this.l1mrk = l1mrk;
    }

    public String getL1flg() {
        return l1flg;
    }

    public void setL1flg(String l1flg) {
        this.l1flg = l1flg;
    }

    public String getL1grade() {
        return l1grade;
    }

    public void setL1grade(String l1grade) {
        this.l1grade = l1grade;
    }

    public String getL1points() {
        return l1points;
    }

    public void setL1points(String l1points) {
        this.l1points = l1points;
    }

    public String getL2p1pc() {
        return l2p1pc;
    }

    public void setL2p1pc(String l2p1pc) {
        this.l2p1pc = l2p1pc;
    }

    public String getL2mrk() {
        return l2mrk;
    }

    public void setL2mrk(String l2mrk) {
        this.l2mrk = l2mrk;
    }

    public String getL2flg() {
        return l2flg;
    }

    public void setL2flg(String l2flg) {
        this.l2flg = l2flg;
    }

    public String getL2grade() {
        return l2grade;
    }

    public void setL2grade(String l2grade) {
        this.l2grade = l2grade;
    }

    public String getL2points() {
        return l2points;
    }

    public void setL2points(String l2points) {
        this.l2points = l2points;
    }

    public String getL3p1pc() {
        return l3p1pc;
    }

    public void setL3p1pc(String l3p1pc) {
        this.l3p1pc = l3p1pc;
    }

    public String getL3mrk() {
        return l3mrk;
    }

    public void setL3mrk(String l3mrk) {
        this.l3mrk = l3mrk;
    }

    public String getL3flg() {
        return l3flg;
    }

    public void setL3flg(String l3flg) {
        this.l3flg = l3flg;
    }

    public String getL3grade() {
        return l3grade;
    }

    public void setL3grade(String l3grade) {
        this.l3grade = l3grade;
    }

    public String getL3points() {
        return l3points;
    }

    public void setL3points(String l3points) {
        this.l3points = l3points;
    }

    public String getMatmrk() {
        return matmrk;
    }

    public void setMatmrk(String matmrk) {
        this.matmrk = matmrk;
    }

    public String getMatflg() {
        return matflg;
    }

    public void setMatflg(String matflg) {
        this.matflg = matflg;
    }

    public String getMatgrade() {
        return matgrade;
    }

    public void setMatgrade(String matgrade) {
        this.matgrade = matgrade;
    }

    public String getMatpoints() {
        return matpoints;
    }

    public void setMatpoints(String matpoints) {
        this.matpoints = matpoints;
    }

    public String getScimrk() {
        return scimrk;
    }

    public void setScimrk(String scimrk) {
        this.scimrk = scimrk;
    }

    public String getSciflg() {
        return sciflg;
    }

    public void setSciflg(String sciflg) {
        this.sciflg = sciflg;
    }

    public String getScigrade() {
        return scigrade;
    }

    public void setScigrade(String scigrade) {
        this.scigrade = scigrade;
    }

    public String getScipoints() {
        return scipoints;
    }

    public void setScipoints(String scipoints) {
        this.scipoints = scipoints;
    }

    public String getSocmrk() {
        return socmrk;
    }

    public void setSocmrk(String socmrk) {
        this.socmrk = socmrk;
    }

    public String getSocflg() {
        return socflg;
    }

    public void setSocflg(String socflg) {
        this.socflg = socflg;
    }

    public String getSocgrade() {
        return socgrade;
    }

    public void setSocgrade(String socgrade) {
        this.socgrade = socgrade;
    }

    public String getSocpoints() {
        return socpoints;
    }

    public void setSocpoints(String socpoints) {
        this.socpoints = socpoints;
    }

    public String getGtot() {
        return gtot;
    }

    public void setGtot(String gtot) {
        this.gtot = gtot;
    }

    public String getGpa() {
        return gpa;
    }

    public void setGpa(String gpa) {
        this.gpa = gpa;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDist_name() {
        return dist_name;
    }

    public void setDist_name(String dist_name) {
        this.dist_name = dist_name;
    }
     String rec; 
     
    String sex=null; String fname=null; String mname=null; String dob=null;String l1p1pc=null; 
       String l1p2pc=null;String l1mrk=null;String l1flg=null;String l1grade=null;String l1points=null;
       String l2p1pc=null;String l2mrk=null;String l2flg=null;String l2grade=null;
       String l2points=null;String l3p1pc=null;String l3mrk=null;String l3flg=null;String l3grade=null;
       String l3points=null;String matmrk=null;String matflg=null;String matgrade=null;String matpoints=null;
       String scimrk=null;String sciflg=null;String scigrade=null;String scipoints=null;String socmrk=null;
       String socflg=null;String socgrade=null;String socpoints=null;String gtot=null;String gpa=null;String result=null;
       String dist_name=null;
    private FormFile photo, imgqul, imgcaste;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FormFile getPhoto() {
        return photo;
    }

    public void setPhoto(FormFile photo) {
        this.photo = photo;
    }

    public FormFile getImgqul() {
        return imgqul;
    }

    public void setImgqul(FormFile imgqul) {
        this.imgqul = imgqul;
    }

    public FormFile getImgcaste() {
        return imgcaste;
    }

    public void setImgcaste(FormFile imgcaste) {
        this.imgcaste = imgcaste;
    }
    
    
    
    public sscdsata_application_wsForm() {
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
    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        
        return errors;
    }
}
