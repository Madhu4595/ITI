/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author Ramesh Naidu
 */
public class Iti_Registration_Action extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
            Iti_Registration_Form ref=(Iti_Registration_Form)form;
            FormFile photo=ref.getPhoto();
            String desig_code[]=ref.getDesig_code();
            int strength[]=ref.getStrength();
            int vacant[]=ref.getVacant();
            int fill[]=ref.getFill();
            String keys="Array['";
            String strength_values="Array['";
            String vacant_values="Array['";
            String fill_values="Array['";
           if(desig_code!=null){
            for(int i=0;i<desig_code.length;i++){
            ////System.out.println("values are "+desig_code[i]+strength[i]+vacant[i]+fill[i]);
                keys=keys+desig_code[i]+"','";
                strength_values=strength_values+strength[i]+"','";
                vacant_values=vacant_values+vacant[i]+"','";
                fill_values=fill_values+fill[i]+"','";
        }
            strength_values=strength_values.substring(0,strength_values.length()-2 )+"]";
            vacant_values=vacant_values.substring(0,vacant_values.length()-2 )+"]";
            fill_values=fill_values.substring(0,fill_values.length()-2 )+"]";
            keys=keys.substring(0,keys.length()-2 )+"]";
    }
            HttpSession session=request.getSession(true);
            String iti_code=session.getAttribute("ins_code").toString();
            //System.out.println("iti_code issssssssssss:"+iti_code);
            String iti_name = ref.getIti_name();
            int pin_code = Integer.parseInt(ref.getPin_code());
            int capacity = Integer.parseInt(ref.getCapacity());
            String mobile = ref.getMobile();
            String email = ref.getEmail();
            String landlinenumber = ref.getLandlineNumber();
            String principalname = ref.getPrincipalName();
            String govt = ref.getGovt();
            String iti_noniti = ref.getIti_noniti();
            int tot_strength = Integer.parseInt(ref.getTot_strength());
            String city_town = ref.getCity_town();
            String dist_code = ref.getDist_code();
            String land = ref.getLand();
            String description = ref.getDescription();
            String address = ref.getAddress();
            String year_est = ref.getYear_est();
            String vtp_regno = ref.getVtpregno();
            String builtup_area = ref.getBuiltarea();
            int noof_toilets = ref.getNooftoilets();
            
            int noof_labs = ref.getNooflabs();
            int noof_classrooms =ref.getNoofclassrooms();
            int examconducting_strength =ref.getExamroomcapacity();
            String query=null;
             if(desig_code!=null){
             query="update iti set iti_name='"+iti_name+"',pin_code='"+pin_code+"',capacity='"+capacity+"',mobile='"+mobile+"',email='"+email+"',landlinenumber='"+landlinenumber+"',principalname='"+principalname+"',govt='"+govt+"',iti_noniti='"+iti_noniti+"',tot_strength='"+tot_strength+"',city_town='"+city_town+"',dist_code='"+dist_code+"',land='"+land+"',description='"+description+"',address='"+address+"',year_est='"+year_est+"',vtp_regno='"+vtp_regno+"',builtup_area='"+builtup_area+"',noof_toilets='"+noof_toilets+"',noof_labs='"+noof_labs+"',noof_classrooms='"+noof_classrooms+"',examconducting_strength='"+examconducting_strength+"',strength=strength||hstore("+keys+","+strength_values+"),strength_vacant=strength_vacant||hstore("+keys+","+vacant_values+"),strength_fill=strength_fill||hstore("+keys+","+fill_values+") where iti_code='"+iti_code+"'";
             }else{
                 query="update iti set iti_name='"+iti_name+"',pin_code='"+pin_code+"',capacity='"+capacity+"',mobile='"+mobile+"',email='"+email+"',landlinenumber='"+landlinenumber+"',principalname='"+principalname+"',govt='"+govt+"',iti_noniti='"+iti_noniti+"',tot_strength='"+tot_strength+"',city_town='"+city_town+"',dist_code='"+dist_code+"',land='"+land+"',description='"+description+"',address='"+address+"',year_est='"+year_est+"',vtp_regno='"+vtp_regno+"',builtup_area='"+builtup_area+"',noof_toilets='"+noof_toilets+"',noof_labs='"+noof_labs+"',noof_classrooms='"+noof_classrooms+"',examconducting_strength='"+examconducting_strength+"' where iti_code='"+iti_code+"'";
             }
            //System.out.println("query isssssssssssssssss:"+query);
            Connection con=null;
             PreparedStatement ps=null;
             ResultSet rs=null;
           String sql=null;
             try{
                 con=DBC.DBConnection.getConnection(request);
                 ps=con.prepareStatement(query);
                ps.executeUpdate();
                String sql1="select count(*) from iti where iti_code='"+iti_code+"'";
                ps=con.prepareStatement(sql1);
                rs=ps.executeQuery();
                if(rs.next()){
                 sql = "update images_iti_profile set iti_code=?,image=? where iti_code='"+iti_code+"'";
                }else{
                 sql="insert into images_iti_profile(iti_code,image) values(?,?)";
                }
                 //System.out.println("sql isssssssssssssssssss:"+sql);
                 ps = con.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(iti_code));
                ps.setBinaryStream(2, photo.getInputStream(), (int) photo.getFileSize());
                ps.executeUpdate();
                request.setAttribute("success","<h1 align='center'><font color='red'>updated successfully</font></h1>");
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                if(ps!=null){
                    ps.close();
                }
                if(con!=null){
                    con.close();
                }
            }
            return mapping.findForward(SUCCESS);
    }
}


