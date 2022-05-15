/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationForm;

import DBC.Validations;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import static jdk.nashorn.internal.objects.NativeString.substring;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
//import org.apache.struts.upload.FormFile;

/**
 *
 * @author SUBBAREDDY
 */
public class UpdateApplicationPersonalDetailsForm_withoutphoto extends org.apache.struts.action.ActionForm {

    private String name;
    private String fname;
//    private FormFile photo;
    private String regid;
    private String date_of_birth;

    public String getDate_of_birth() {
        return beans.MyUtil.filterBad(date_of_birth);
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getRegid() {
        return beans.MyUtil.filterBad(regid);
    }

    public void setRegid(String regid) {
        this.regid = regid;
    }

    public String getFname() {
        return beans.MyUtil.filterBad(fname);
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

//    public FormFile getPhoto() {
//        return photo;
//    }
//
//    public void setPhoto(FormFile photo) {
//        this.photo = photo;
//    }
    public String getName() {
        return beans.MyUtil.filterBad(name);
    }

    public void setName(String string) {
        name = string;
    }

    public UpdateApplicationPersonalDetailsForm_withoutphoto() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        Connection con = DBC.DBConnection.getConnection(request);
//        System.out.println("11111111111111111");

        String val = "";
        try {
            val = beans.MyUtil.getOptionValue("iti_params", "code", "value", "11", con);
//            System.out.println("222222222222222");

        } catch (SQLException ex) {
            //Logger.getLogger(UpdateApplicationPersonalDetailsForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (regid == null || regid == "") {
            errors.add(regid, new ActionMessage("UpdateApplicationPersonalDetailsForm.regid.required"));
        } else if (val.equalsIgnoreCase("application")) {
            if (!Validations.Validate(regid, 15)) {
                //errors.add(regid, new ActionMessage("error.adm_num.notmatch"));
            }
        }
//        System.out.println("333333333333333333");
        /*
         if (name == null || name.length() < 1) {
         errors.add(name, new ActionMessage("error.name.required"));
         } else if (!Validations.Validate(name, 3)) {
         errors.add(name, new ActionMessage("error.name.notmatch"));
         } else if (name.length() > 50) {
         errors.add(name, new ActionMessage("error.name.lenerror"));
         }
         if (fname == null || fname.length() < 1) {
         errors.add(fname, new ActionMessage("error.fname.required"));
         } else if (!Validations.Validate(fname, 3)) {
         errors.add(fname, new ActionMessage("error.fname.notmatch"));
         } else if (fname.length() > 50) {
         errors.add(fname, new ActionMessage("error.fname.lenerror"));
         } */
        if (date_of_birth == null || date_of_birth.length() < 1) {
            errors.add(date_of_birth, new ActionMessage("error.dob.required"));
        } else if (!Validations.Validate(date_of_birth, 8)) {
            errors.add(date_of_birth, new ActionMessage("error.dob.notmatch"));
        } else if (date_of_birth.length() > 50) {
            errors.add(date_of_birth, new ActionMessage("error.dob.lenerror"));
        }
        try {
            HttpSession ses = request.getSession();
//            System.out.println("44444444444444" + ses.getAttribute("ins_code") + "***" + regid);
            String dist_code = "";
            if (ses.getAttribute("ins_code") != null) {
                dist_code = ses.getAttribute("ins_code").toString();
            }
//            System.out.println("555555555" + dist_code + "***");

            //String dist_code1 = substring(regid, 0, 2);
            String dist_code1 = regid.substring(0, 2);
//            System.out.println("Distcode 1 issss" + dist_code1);
            String nm = "";
            String role_id = ses.getAttribute("role_id").toString();
            if (val.equalsIgnoreCase("application")) {
                if (role_id.equals("3")) {
                    nm = beans.MyUtil.getOptionValue("application_check", "regid", "name", regid, "dist_code", dist_code, con);
                } else if (role_id.equals("4")) {
                    nm = beans.MyUtil.getOptionValue("application_check", "regid", "name", regid, "iti_code", dist_code, con);
                } else if (role_id.equals("2")) {
                    nm = beans.MyUtil.getOptionValue("application_check", "regid", "name", regid, "dist_code", dist_code1, con);
                } else if (role_id.equals("1")) {
                    nm = "";
                }
                if (!(nm.length() > 1)) {
                    errors.add(dist_code, new ActionMessage("error.regid.otherdist"));
                }
                /*HttpSession session = request.getSession(true);
                 String ins_code = session.getAttribute("ins_code").toString();
                 String report = "";
                 if (!regid.substring(0, 2).equalsIgnoreCase(ins_code)) {
                 errors.add(regid, new ActionMessage("error.adm_num.notmatch"));
                 }*/
            }
//            } else {
//
//                HttpSession session = request.getSession(true);
//                String ins_code = session.getAttribute("ins_code").toString();
//                String olddist_code = "";
//                String year = DBC.RoleBasedOperations.getAdmissionYear(regid);
//                if (Integer.parseInt(year) < 13) {
//                    String temp_dist_code = regid.substring(0, 1);
//                    olddist_code = beans.MyUtil.getOptionValue("dist_mst", "dist_code", "itidist_code", ins_code, "statecode", "28", con);
//                    if (!temp_dist_code.equals(olddist_code)) {
//                        errors.add(regid, new ActionMessage("error.adm_num.notmatch"));
//                        request.setAttribute("adm_num_wrong", "<font color=red><b><h2>You are trying to access another District Data... Admission Number is Invalid..");
//                        if (con != null) {
//                            con.close();
//                        }
//                        //return mapping.findForward("adm_num_wrong");
//                    }
//                } else {
//                    String temp_dist_code = regid.substring(0, 2);
//                    if (!temp_dist_code.equals(ins_code)) {
//                        request.setAttribute("adm_num_wrong", "<font color=red><b><h2>You are trying to access another District Data... Admission Number is Invalid..");
//                        if (con != null) {
//                            con.close();
//                        }
//                        errors.add(regid, new ActionMessage("error.adm_num.notmatch"));
//                    }
//                }
//                if (con != null) {
//                    con.close();
//                    con = null;
//                }
//            }
        } catch (Exception ex) {
        }
//        try {
//            if (getPhoto().getFileName() != "") {
//                String filename = getPhoto().getFileName();
//                if (!(filename.endsWith(".png") || filename.endsWith(".jpg") || filename.endsWith(".gif") || filename.endsWith(".jpeg"))) {
//                    errors.add("photo", new ActionMessage("error.photo2.notmatch"));
//                }
//
//                if (getPhoto().getFileSize() / 1024 > 20) {
//                    errors.add("photo", new ActionMessage("error.photo.notmatch"));
//                }
//                if (!(getPhoto().getContentType().equals("image/gif") || getPhoto().getContentType().equals("image/jpg") || getPhoto().getContentType().equals("image/png") || getPhoto().getContentType().equals("image/jpeg"))) {
//                    errors.add("photo", new ActionMessage("error.photo2.notmatch"));
//                }
//            }
//        } catch (Exception ex) {
//        }
        return errors;
    }
}
