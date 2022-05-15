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
public class iti_images_upload_form extends org.apache.struts.action.ActionForm {
    
    String iti_code;
    private FormFile photo;

    
    
    public iti_images_upload_form() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getIti_code() {
        return iti_code;
    }

    public void setIti_code(String iti_code) {
        this.iti_code = iti_code;
    }

    public FormFile getPhoto() {
        return photo;
    }

    public void setPhoto(FormFile photo) {
        this.photo = photo;
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
