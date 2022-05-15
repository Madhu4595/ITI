  package Password;

import DBC.Validations;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class ChangeUserPwd_Form extends ActionForm
{
  private String username;
  private String password;

  public String getUsername()
  {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

   
  @Override
  public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
  {
    ActionErrors errors = new ActionErrors();
    
    if ((getUsername() == null) || (getUsername().length() < 1)) {
      errors.add("username", new ActionMessage("error.username.required"));
    }else if(!Validations.Validate(getUsername(), 4)){
        errors.add("username", new ActionMessage("error.username.notmatch"));
    }

    if ((getPassword() == null) || (getPassword().length() < 1)) {
      errors.add("password", new ActionMessage("error.password.required"));
    }else if(!Validations.Validate(getPassword(), 1)){
        errors.add("password", new ActionMessage("error.password.notmatch"));
    }

    return errors;
  }
}