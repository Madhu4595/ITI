/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Ramesh Naidu
 */
public class SpecialFeatures {
    public static String yearList(){
     GregorianCalendar cal = new GregorianCalendar();
      int year=cal.get(Calendar.YEAR);
       String val = "<option value='' selected>--select--</option>";
       int i=0;
       for (i = 2013; i<=year; i++) {

    val = val + "<option value='" + i + "' >" + i + "</option>";
}
       return val;
    }
    
}
