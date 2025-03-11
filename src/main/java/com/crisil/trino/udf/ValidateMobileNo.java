package com.crisil.trino.udf;

import io.trino.spi.function.Description;
import io.trino.spi.function.ScalarFunction;
import io.trino.spi.function.SqlNullable;
import io.trino.spi.function.SqlType;
import io.trino.spi.type.StandardTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.regex.Pattern;


public class ValidateMobileNo {
    private static final Logger LOG = LoggerFactory.getLogger(ValidateMobileNo.class);
    private static final String MOBILE_REGEX="^\\(?\\+?[1-9][0-9]{0,2}\\)?[-.\\s]?[1-9][0-9]{7,14}$";
    private static final Pattern PATTERN=Pattern.compile(MOBILE_REGEX);
    @Description("UDF to validate the mobile number")
    @ScalarFunction("mobile_check")
    @SqlType(StandardTypes.BOOLEAN)
    public static Boolean MOBILE_CHECK(
            @SqlNullable @SqlType(StandardTypes.VARCHAR) String mobile) {
        try {
            if (mobile == null || mobile.isEmpty()) {
                LOG.error("mobile number is null");
                return false;
            } else {
                return PATTERN.matcher(mobile.toString().trim()).matches();
            }

        } catch (Exception e) {
            LOG.error("Something went wrong",e);

            throw new RuntimeException(e);
        }
    }
}
