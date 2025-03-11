package com.crisil.trino;
import com.crisil.trino.udf.ValidateDate;
import com.crisil.trino.udf.ValidateMobileNo;
import com.crisil.trino.udf.ValidatePAN;

public class TestUDF {
//    private static final Logger LOG=LoggerFactory.getLogger(TestUDF.class);
//    boolean isValid=false;
    public static void main(String[] args) {

//        boolean isValidP=ValidatePAN.PAN_CHECK("EWWPM9111S");
        boolean isValidD= ValidateDate.DATE_CHECK("2024-11-02");
//        boolean isValidM= ValidateMobileNo.MOBILE_CHECK("EWWPM9111S");
//        System.out.println("Date validation "+isValidD);
    }
}
