package beans;

import java.lang.String;
import java.lang.Integer;
import java.lang.Character;
import java.lang.Exception;
import java.util.*;

import java.util.StringTokenizer;

public final class Validatebean {

    // It contains the error message corresponding to the recent verify() operation.
    // If no error occured i.e., the given data is valid then it's value is null.
    private String errorMessage = "hh ";
    // It contains the validity (true / false) of the recently verified data.
    // If the given data is valid then it's value true, otherwise false.
    private boolean validity = false;
    // It contains the character, which caused error in the recently verified data string.
    // If the given data contains no error then it's value is null.
    private char errorChar = ' ';
    // It contains index of the character, which caused error in the recently verified data string.
    // If the given the data is valid then it's value is -1.
    private int errorIndex = -1;
    // Codes for the listed conditions. 
    private final int NOTNULL = 0;
    private final int AGE = 1;
    private final int ALPHANUMERIC = 2;
    private final int CHAR = 3;
    private final int DATE = 4;
    private final int EMAIL_ID = 5;
    private final int FAX = 6;
    private final int LOWER = 7;
    private final int NAME = 8;	// ask the use of .
    private final int NEGATIVE = 9;
    private final int NUMBER = 10;
    private final int PHONE = 11;
    private final int POSITIVE = 12;
    private final int RANGE = 13;
    private final int REAL = 14;
    private final int SALUTATION = 15;
    private final int UPPER = 16;
    private final int URI = 17;
    private final int DATETIME = 18;
    private final int NOTZERO = 19;
    // Ranges for certain conditions.
    private final int MAX_AGE = 120;
    private final int MIN_AGE = 1;
    private final int MAX_FAX = 10;
    private final int MAX_PHONE = 10;
    // Valid salutation strings.
    private final String SALUTATION_STRING = ",Mr,Ms,Sri.,Smt,";
    // Maximum & minimum number of days in a month.
    private final int[] MAX_DAYS = {29, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private final int MIN_DAY = 1;
    // Maximum & minimum values for years.
    private final int MAX_YEAR = 2100;
    private final int MIN_YEAR = 1900;
    // Maximum & minimum values for months.
    private final int MAX_MONTH = 12;
    private final int MIN_MONTH = 1;
    // Maximum & minimum values for hour,minute & sec.
    private final int MAX_HOUR = 23;
    private final int MIN_HOUR = 0;
    private final int MAX_MIN = 59;
    private final int MIN_MIN = 0;
    private final int MAX_SEC = 59;
    private final int MIN_SEC = 0;
    // Minimum & maximum values for range condition. These values must be set before
    // calling the 'verify()' method. These can be set using the 'setRange()' method.
    private int MIN_RANGE = 0;
    private int MAX_RANGE = 100;

    public Validatebean() {
    }

    // This method returns the contents of 'errorMessage' string to the caller.
    public String getErrorMessage() {
        //System.out.println("errorMessgeeeee" + errorMessage);
        return errorMessage;
    }

    // This method returns the contents of 'validity' variable to the caller.
    public boolean getValidity() {
        return validity;
    }

    // This method returns the contents of 'errorChar' variable to the caller.
    public char getErrorChar() {
        return errorChar;
    }

    // This method returns the contents of 'errorIndex' variable to the caller.
    public int getErrorIndex() {
        return errorIndex;
    }

    // This method assigns the given min & max values to range variables.
    public void setRange(int min, int max) {
        MIN_RANGE = min;
        MAX_RANGE = max;
    }

    // This method verifies the contents of the 'data' string against the condition in
    // the 'condition' string. It returns true if the data is valid, otherwise returns false.
    public boolean verify(String data, int condition, String label) {
        String errMsg = "";
        if (data == null) {
            data = "";
        }
        data = data.trim();
        char[] dataChars = data.toCharArray();
        int len = dataChars.length;
        validity = true;
        switch (condition) {
            case NOTNULL:
                if (len > 0 && !data.equalsIgnoreCase("NULL")) {
                    validity = true;
                    errorMessage = null;
                    errorChar = ' ';
                    errorIndex = -1;
                } else {
                    validity = false;
                    errorMessage = "NULL VALUE FOUND";
                    errorChar = ' ';
                    errorIndex = -1;
                }
                break;
            case NOTZERO:
                if (len > 0 && !data.equalsIgnoreCase("0")) {
                    validity = true;
                    errorMessage = null;
                    errorChar = ' ';
                    errorIndex = -1;
                } else {
                    validity = false;
                    errorMessage = "ZERO VALUE FOUND";
                    errorChar = ' ';
                    errorIndex = -1;
                }
                break;

            case AGE:
                validity = true;
                for (int i = 0; i < len; i++) {
                    if (!Character.isDigit(dataChars[i]) && dataChars[i] == ' ') {
                        validity = false;
                        errorIndex = i;
                        errorChar = dataChars[i];
                        break;
                    }
                }
                if (validity) {
                    int age = Integer.parseInt(data);
                    if ((age >= MIN_AGE) && (age <= MAX_AGE)) {
                        errorMessage = null;
                    } else {
                        errorMessage = "INVALID AGE";
                        validity = false;
                    }
                    errorChar = ' ';
                    errorIndex = -1;
                } else {
                    errorMessage = "CHARACTER NOT ALLOWED HERE";
                }
                break;

            case ALPHANUMERIC:
                for (int i = 0; i < len; i++) {
                    if (!Character.isLetterOrDigit(dataChars[i])) {
                        validity = false;
                        errorIndex = i;
                        errorChar = dataChars[i];
                        break;
                    }
                }
                if (validity) {
                    errorMessage = null;
                    errorChar = ' ';
                    errorIndex = -1;
                } else {
                    errorMessage = "INVALID ALPHA NUMERIC";
                }
                break;

            case CHAR:
                validity = true;
                for (int i = 0; i < len; i++) {
                    if (!((dataChars[i] >= 'a' && dataChars[i] <= 'z') || (dataChars[i] >= 'A' && dataChars[i] <= 'Z'))) {
                        validity = false;
                        errorIndex = i;
                        errorChar = dataChars[i];
                        break;
                    }
                }
                if (validity) {
                    errorMessage = null;
                    errorChar = ' ';
                    errorIndex = -1;
                } else {
                    errorMessage = "INVALID CHARACTER";
                }
                break;

            case DATE:
                validity = true;
                if (len != 0) {
                    int day = 0;
                    int month = 0;
                    int year = 0;

                    int MAX_DAY = 0;

                    try {
                        StringTokenizer st = new StringTokenizer(data, "-");
                        if (st.countTokens() != 3) {
                            throw new Exception();
                        } else {
                            day = Integer.parseInt(st.nextToken());
                            month = Integer.parseInt(st.nextToken());
                            year = Integer.parseInt(st.nextToken());

                            if ((year % 4) == 0 && (month == 2)) {
                                MAX_DAY = MAX_DAYS[0];
                            } else {
                                MAX_DAY = MAX_DAYS[month];
                            }

                            if (year < MIN_YEAR || year > MAX_YEAR) {
                                throw new Exception();
                            }
                            if (month < MIN_MONTH || month > MAX_MONTH) {
                                throw new Exception();
                            }
                            if (day < MIN_DAY || day > MAX_DAY) {
                                throw new Exception();
                            }
                        }
                    } catch (Exception e) {
                        validity = false;
                    }
                }
                if (validity) {
                    errorMessage = null;
                } else {
                    errorMessage = "INVALID DATE";
                }
                errorChar = ' ';
                errorIndex = -1;
                break;

            case EMAIL_ID:
                StringTokenizer st = new StringTokenizer(data, "@");
                validity = true;
                errorMessage = null;
                errorChar = ' ';
                errorIndex = -1;
                try {
                    if (st.countTokens() != 2) {
                        throw new Exception();
                    }
                    char[] buffer = st.nextToken().toCharArray();
                    len = buffer.length;
                    for (int i = 0; i < len; i++) {
                        if (Character.isLetterOrDigit(buffer[i])
                                || buffer[i] == '_') {
                            continue;
                        } else {
                            errorChar = (char) buffer[i];
                            errorIndex = i;
                            throw new Exception();
                        }
                    }
                    StringTokenizer stk = new StringTokenizer(st.nextToken(), ".");
                    if (stk.countTokens() < 2) {
                        throw new Exception();
                    }
                    buffer = stk.nextToken().toCharArray();
                    len = buffer.length;
                    for (int i = 0; i < len; i++) {
                        if (Character.isLetterOrDigit(buffer[i])) {
                            continue;
                        } else {
                            errorChar = (char) buffer[i];
                            errorIndex = i;
                            throw new Exception();
                        }
                    }
                    String domain = "," + stk.nextToken() + ",";
                    String domainNames = ",com,edu,gov,int,mil,net,org,co,nic,in,";
                    if (domainNames.indexOf(domain) == -1) {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    validity = false;
                    errorMessage = "INVALID EMAIL-ID";
                }
                break;

            case FAX:
                for (int i = 0; i < len; i++) {
                    if (!Character.isDigit(dataChars[i])) {
                        validity = false;
                        errorIndex = i;
                        errorChar = dataChars[i];
                        break;
                    }
                }
                if (validity) {
                    if (len <= MAX_FAX) {
                        errorMessage = null;
                    } else {
                        errorMessage = "INVALID FAX NUMBER";
                    }
                    errorChar = ' ';
                    errorIndex = -1;
                } else {
                    errorMessage = "CHARACTER NOT ALLOWED HERE";
                }
                break;

            case LOWER:
                for (int i = 0; i < len; i++) {
                    if (!Character.isLowerCase(dataChars[i])) {
                        validity = false;
                        errorIndex = i;
                        errorChar = dataChars[i];
                        break;
                    }
                }
                if (validity) {
                    errorMessage = null;
                    errorChar = ' ';
                    errorIndex = -1;
                } else {
                    errorMessage = "NOT A LOWERCASE CHARACTER";
                }
                break;

            case NAME:
                for (int i = 0; i < len; i++) {
                    if (!Character.isLetter(dataChars[i])
                            && !Character.isSpaceChar(dataChars[i])
                            && dataChars[i] != '.') {
                        validity = false;
                        errorIndex = i;
                        errorChar = dataChars[i];
                        break;
                    }
                }
                if (validity) {
                    errorMessage = null;
                    errorChar = ' ';
                    errorIndex = -1;
                } else {
                    errorMessage = "INVALID CHARACTER";
                }
                break;

            case NEGATIVE:
                for (int i = 0; i < len; i++) {
                    if (i == 0) {
                        if (dataChars[i] != '-') {
                            validity = false;
                            errorMessage = "NOT A NEGATIVE NUMBER";
                            errorIndex = i;
                            errorChar = dataChars[i];
                            break;
                        }
                    } else if (!Character.isDigit(dataChars[i])) {
                        validity = false;
                        errorMessage = "CHARACTER NOT ALLOWED HERE";
                        errorIndex = i;
                        errorChar = dataChars[i];
                        break;
                    }
                }
                if (validity) {
                    errorMessage = null;
                    errorChar = ' ';
                    errorIndex = -1;
                }
                break;

            case NUMBER:
                for (int i = 0; i < len; i++) {
                    if (dataChars[i] == '-') {
                        if (i != 0) {
                            validity = false;
                            errorMessage = "CHARACTER NOT ALLOWED HERE";
                            errorIndex = i;
                            errorChar = dataChars[i];
                            break;
                        }
                    } else if (!Character.isDigit(dataChars[i]) && dataChars[i] == ' ') {
                        validity = false;
                        errorMessage = "CHARACTER NOT ALLOWED HERE";
                        errorIndex = i;
                        errorChar = dataChars[i];
                        break;
                    }
                }
                if (validity) {
                    errorMessage = null;
                    errorChar = ' ';
                    errorIndex = -1;
                }
                break;

            case PHONE:
                for (int i = 0; i < len; i++) {
                    if (!Character.isDigit(dataChars[i])) {
                        validity = false;
                        errorMessage = "CHARACTER NOT ALLOWED HERE";
                        errorIndex = i;
                        errorChar = dataChars[i];
                        break;
                    }
                }
                if (validity) {
                    if (len <= MAX_PHONE) {
                        errorMessage = null;
                    } else {
                        errorMessage = "INVALID PHONE NUMBER";
                    }
                    errorChar = ' ';
                    errorIndex = -1;
                }
                break;

            case POSITIVE:
                for (int i = 0; i < len; i++) {
                    if (!Character.isDigit(dataChars[i])) {
                        validity = false;
                        errorMessage = "CHARACTER NOT ALLOWED HERE";
                        errorIndex = i;
                        errorChar = dataChars[i];
                        break;
                    }
                }
                if (validity) {
                    errorMessage = null;
                    errorChar = ' ';
                    errorIndex = -1;
                }
                break;

            case RANGE:
                int value = 0;
                if (verify(data, NUMBER, "")) {
                    value = Integer.parseInt(data);
                    if (value < MIN_RANGE || value > MAX_RANGE) {
                        validity = false;
                        errorMessage = "VALUE OUT OF BOUNDS";
                    } else {
                        validity = true;
                        errorMessage = null;
                    }
                    errorChar = ' ';
                    errorIndex = -1;
                }
                break;

            case REAL:
                boolean isDotAllowed = true;
                for (int i = 0; i < len; i++) {
                    if (dataChars[i] == '-') {
                        if (i != 0) {
                            validity = false;
                            errorMessage = "CHARACTER NOT ALLOWED HERE";
                            errorIndex = i;
                            errorChar = dataChars[i];
                            break;
                        }
                    } else if (dataChars[i] == '.' && isDotAllowed) {
                        isDotAllowed = false;
                    } else if (!Character.isDigit(dataChars[i])) {
                        validity = false;
                        errorMessage = "CHARACTER NOT ALLOWED HERE";
                        errorIndex = i;
                        errorChar = dataChars[i];
                        break;
                    }
                }
                if (validity) {
                    errorMessage = null;
                    errorChar = ' ';
                    errorIndex = -1;
                }
                break;

            case SALUTATION:
                if (SALUTATION_STRING.indexOf("," + data + ",") != -1) {
                    validity = true;
                    errorMessage = null;
                    errorChar = ' ';
                    errorIndex = -1;
                } else {
                    validity = false;
                    errorMessage = "INVALID SALUTATON";
                }
                break;

            case UPPER:
                for (int i = 0; i < len; i++) {
                    if (!Character.isUpperCase(dataChars[i])) {
                        validity = false;
                        errorIndex = i;
                        errorChar = dataChars[i];
                        break;
                    }
                }
                if (validity) {
                    errorMessage = null;
                    errorChar = ' ';
                    errorIndex = -1;
                } else {
                    errorMessage = "NOT AN UPPERCASE CHARACTER";
                }
                break;

            case URI:
                StringTokenizer uri = new StringTokenizer(data, ".");
                if (uri.countTokens() < 3) {
                    validity = false;
                } else {
                    validity = true;
                }
                if (validity) {
                    errorMessage = null;
                } else {
                    errorMessage = "INVALID URI";
                }
                errorChar = ' ';
                errorIndex = -1;
                break;
            case DATETIME:
                if (len > 19) {
                    validity = false;
                } else {
                    validity = true;
                    int day = 0;
                    int month = 0;
                    int year = 0;
                    int hour = 0;
                    int min = 0;
                    int sec = 0;

                    String date = " ";
                    String time = " ";

                    int MAX_DAY = 0;

                    try {
                        StringTokenizer st1 = new StringTokenizer(data, " ");
                        while (st1.hasMoreTokens()) {
                            date = st1.nextToken();
                            time = st1.nextToken();
                        }

                        st = new StringTokenizer(date, "-");
                        if (st.countTokens() != 3) {
                            throw new Exception();
                        } else {
                            day = Integer.parseInt(st.nextToken());
                            month = Integer.parseInt(st.nextToken());
                            year = Integer.parseInt(st.nextToken());

                            if ((year % 4) == 0 && (month == 2)) {
                                MAX_DAY = MAX_DAYS[0];
                            } else {
                                MAX_DAY = MAX_DAYS[month];
                            }

                            if (year < MIN_YEAR || year > MAX_YEAR) {
                                throw new Exception();
                            }
                            if (month < MIN_MONTH || month > MAX_MONTH) {
                                throw new Exception();
                            }
                            if (day < MIN_DAY || day > MAX_DAY) {
                                throw new Exception();
                            }

                        }
                        StringTokenizer st2 = new StringTokenizer(time, ":");
                        if (st2.countTokens() != 3) {
                            throw new Exception();
                        } else {
                            hour = Integer.parseInt(st2.nextToken());
                            min = Integer.parseInt(st2.nextToken());
                            sec = Integer.parseInt(st2.nextToken());

                            if (hour < MIN_HOUR || hour > MAX_HOUR) {
                                throw new Exception();
                            }
                            if (min < MIN_MIN || min > MAX_MIN) {
                                throw new Exception();
                            }
                            if (sec < MIN_SEC || sec > MAX_SEC) {
                                throw new Exception();
                            }

                        }

                    } catch (Exception e) {
                        validity = false;
                    }
                }
                if (validity) {
                    errorMessage = null;
                } else {
                    errorMessage = "INVALID DATE";
                }
                errorChar = ' ';
                errorIndex = -1;
                break;

            default:
                validity = true;
                errorMessage = null;
                errorChar = ' ';
                errorIndex = -1;

        }

        if (errorMessage != null) {
            errorMessage = label + ":&nbsp; " + errorMessage;
        }

        return validity;
    }

    public boolean NumberRange(String value, String rangefrom, String rangeto) {
        /*
         This method is used to check the value is in NUMBER format or not and the value is in given Number RANGE or not;
         Parameters to this method is value( which you want to check), range from value and range to value;
		
         */
        char[] dataChars1 = value.toCharArray();
        int len = dataChars1.length;
        boolean flag = true;
        for (int i = 0; i < len; i++) {
            if (!Character.isDigit(dataChars1[i])) {
                flag = false;
                validity = false;
                errorMessage = "VALUE IS NOT IN NUMBER FORMAT";
                errorIndex = i;
                errorChar = dataChars1[i];

            }

        }
        if (flag) {
            int val = Integer.parseInt(value);
            int rfrom = Integer.parseInt(rangefrom);
            int rto = Integer.parseInt(rangeto);
            if (val < rfrom || val > rto) {
                validity = false;
                errorMessage = "THE VALUE IS OUT OF RANGE";
                errorChar = ' ';
                errorIndex = -1;
            } else {
                validity = true;
            }
        }
        return validity;

    }

    public boolean DateRange(String value, String rangefrom, String rangeto) {
        /*
         THIS METHOD IS USED TO CHECK DATE VALIDATION IN DIFFERENT CONDITIONS THOSE ARE 
         1.BETWEEN TWO DATES;
         2.SYSTEM DATE TO USER DEFINED DATE;
         3.USER DEFINED DATE TO SYSTEM DATE;
         4.GRETER THAN THE SYSTEM DATE;
         5.LESS THEN THE SYSTEM DATE;
         6.ONLY ACCEPT SYSTEMDATE

	
         */
        if (verify(value, 4, "")) {
            Calendar c = Calendar.getInstance();
            String dt = "", mn = "";
            int month = c.get(Calendar.MONTH) + 1;
            if (c.get(Calendar.DAY_OF_MONTH) < 10) {
                dt = "0" + c.get(Calendar.DAY_OF_MONTH);
            } else {
                dt = "" + c.get(Calendar.DAY_OF_MONTH);
            }
            if (month < 10) {
                mn = "0" + month;
            } else {
                mn = "" + month;
            }
            String sysdt = dt + "-" + mn + "-" + c.get(Calendar.YEAR);

            if (rangefrom.trim().equalsIgnoreCase("sysdate")) {
                rangefrom = sysdt;
            }
            if (rangeto.trim().equalsIgnoreCase("sysdate")) {
                rangeto = sysdt;
            }
            boolean eflg = false;
            if (!rangefrom.trim().equals("")) {
                if (verify(rangefrom, 4, "")) {
                    validity = DtCheck(rangefrom, value);
                } else {
                    eflg = true;
                }
            }
            if (!rangeto.trim().equals("")) {
                if (verify(rangeto, 4, "")) {
                    validity = DtCheck(value, rangeto);
                } else {
                    eflg = true;
                }
            }

            if (eflg == true) {
                errorMessage = "RANGE DATES ARE NOT IN DD_MM_YYYY FORMAT. PLEASE GIVE THE VALUES IN THID FORMAT ONLY";
                validity = false;
            } else if (validity == false) {
                errorMessage = "GIVEN DATE IS NOT IN SPECIFIED RANGE";
            }

        } else {
            errorMessage = "RANGE DATES ARE NOT IN DD_MM_YYYY FORMAT> PLEASE GIVE THE VALUES IN THID FORMAT ONLY";
            validity = false;
        }
        return validity;
    }

    public boolean DtCheck(String a, String b) {
        int y1 = Integer.parseInt(a.substring(6, 10));
        int y2 = Integer.parseInt(b.substring(6, 10));
        int m1 = Integer.parseInt(a.substring(3, 5));
        int m2 = Integer.parseInt(b.substring(3, 5));
        int d1 = Integer.parseInt(a.substring(0, 2));
        int d2 = Integer.parseInt(b.substring(0, 2));
        boolean flag = false;
        if (y2 > y1) {
            flag = true;
        } else if (y2 == y1) {
            if (m2 > m1) {
                flag = true;
            } else if (m2 == m1) {
                if (d2 >= d1) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    public boolean WidthCheck(String value, int width) {
        if (value.length() <= width) {
            return true;
        } else {
            return false;
        }

    }
}
