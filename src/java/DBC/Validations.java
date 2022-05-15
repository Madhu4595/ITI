/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DBC;

import java.util.regex.Pattern;

/**
 *
 * @author Administrator
 */
public class Validations {

    public static boolean Validate(String input, int type) {
        /*
         *   Type:
         *
         *  1:  Password : "^[a-zA-Z0-9@!#$%]*$"
         *  2:  Numeric
         *  3:  String with spaces
         *  4:  Alphanumeric
         *  5:  Financial year
         *  6:  String without spaces
         *  7:  Year / iti_code
         *  8:  DOB
         *  9:  Alphanumeric with spaces
         *  10: Mobile number
         *  11: Pincode
         *  12: Address
         *  13. Real number
         *  14. Caste
         *  15. Admission Number
         *  16. Time
         *  17. space and Address
         *  18. SCVT/NCVT Admission number format for DCPS
         *  19. APP Admission number format for DCPS
         *  20. SCVT/NCVT Admission number format for DCPS
            21. COE Admission number format for DCPS
            22. NCVT Private Candidates
            25. Registration Id
            28. phase
        ->  29. only one character capital/small
        ->  30. only two characters capital/small
        ->  31. district code - only two characters capital/small
        ->  32. Aadhaar number
        ->  33. E-Mail
        -> 35. Trade short name
         */
        input = input.replaceAll("%", ";");

        if (input.isEmpty() || input == null || input.length() <= 0) {
            return false;
        }

        String regex[] = {
            "^[a-zA-Z0-9@#$%]*$", //1
            "^[0-9]*$", //2 
            "^[a-zA-Z ]*$", //3
            "^[0-9a-zA-Z]*$", //4
            "^[0-9]{4}-[0-9]{2}$", //5
            "^[a-zA-Z]*$", //6
            "^[1-2][0-9]{3}$", //7 
            "^[0-9]{2}[-]?[//]?[0-9]{2}[-]?[//]?[0-9]{4}$", //8
            "^[0-9a-zA-Z ]*$",//9
            "^[0-9]{10}", //10
            "^[0-9]{6}", //11,
            "^[a-zA-Z0-9 -./,:]*$", //12,
            "^[0-9]*[.]?[0-9]*$",//13
            "^[A-Za-z]*[-]?[A-Za-z]*$",//14
            "^[0-9]{4}[A-Za-z]{2}[0-1]{1}[3-4]{1}[0-9]{3}",// [A-Z]?[A-Z]? 15
            "^[0-2]{1}[0-9]{1}[:]?[0-5]{1}[0-9]{1}$",//16   //:[0-5]?[0-9]{1}
            "^[0-9]*[/]*[0-9]*$",//17
            "^[0-9]{4}[A-Za-z]{2}[0-1]{1}[0-9]{1}[0-9]{3}[R|S]{1}",//18
            "^[9]{1}[4-9]{1}[-]{1}[0-9]{2}[-]{1}[A-Z]{2}{1}[-]{1}[0-9]{3}[P?B?]{1}",//19
            "^[0-9]{4}[A-Za-z]{2}[0-1]{1}[0-9]{1}[0-9]{3}[R?S?]{1}",//20
            "^[0-9]{4}[A-Za-z]{2}[0-1]{1}[0-5]{1}[0-9]{3}[A-Z]{2}",//21
            "^[0-9]{4}[A-Za-z]{2}[0-1]{1}[1-5]{1}[0-9]{3}[P?]",//22
            "^[0-9]{4}[A-Za-z]{2}[0-1]{1}[0-2]{1}[0-9]{3}",//23
            "^[0-9A-Z]{10,15}", //24
            "^[0-9]{8}", //25
            "^[0-9A-Z]{10,15}", //26
            "^[0-9]{4}", //27
            "^[1-6]{1}", //28
            "^[a-zA-z]{1}",//29
            "^[a-zA-z0-9]{2}",//30
            "^[0-9]{2}", //31
            "^[0-9]{12}", //32
            "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", //33
        //   "^[2]{1}[0-9]{3}[-]{1}[0-9]{2}$",
                "^[0-9A-Z]{8}", //34
                "^[A-Z]{2}" //35
        };
        return Pattern.matches(regex[type - 1], input);
    }
}
