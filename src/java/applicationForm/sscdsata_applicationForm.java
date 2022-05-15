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
public class sscdsata_applicationForm extends org.apache.struts.action.ActionForm {
    
    String name;
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
    
    
    
    public sscdsata_applicationForm() {
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
