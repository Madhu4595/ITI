package Master.Trade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

 
public class DgetTrade_AllocationForm extends org.apache.struts.action.ActionForm {
  

    private String trade_code[], sem_no[], dget_tradecode[], engg_nonengg[];

    public String[] getTrade_code() {
        return trade_code;
    }

  

    public void setTrade_code(String[] trade_code) {
        this.trade_code = trade_code;
    }

    public String[] getSem_no() {
        return sem_no;
    }

    public void setSem_no(String[] sem_no) {
        this.sem_no = sem_no;
    }

    public String[] getDget_tradecode() {
        return dget_tradecode;
    }

    public void setDget_tradecode(String[] dget_tradecode) {
        this.dget_tradecode = dget_tradecode;
    }

    public String[] getEngg_nonengg() {
        return engg_nonengg;
    }

    public void setEngg_nonengg(String[] engg_nonengg) {
        this.engg_nonengg = engg_nonengg;
    }

    public DgetTrade_AllocationForm() {
        super();
    }
    
      
    

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        System.out.println("UR IN DgetTrade_AllocationForm.java"+getEngg_nonengg().length);
        
        ActionErrors errors = new ActionErrors();
        
        //engg_nonengg
        if(getEngg_nonengg().length == 0){
            errors.add("engg_nonengg", new ActionMessage("error.engg_nonengg.required"));
        }else if(!getEngg_nonengg().equals("E") || !getEngg_nonengg().equals("N")){
            errors.add("engg_nonengg", new ActionMessage("error.engg_nonengg.notmatch"));
        }
        
        return errors;
    }
}
