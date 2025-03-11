package com.crisil.trino.udf;

import io.trino.spi.function.Description;
import io.trino.spi.function.ScalarFunction;
import io.trino.spi.function.SqlNullable;
import io.trino.spi.function.SqlType;
import io.trino.spi.type.StandardTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.regex.Pattern;


public class ValidatePAN {
    private static final Logger LOG = LoggerFactory.getLogger(ValidatePAN.class);
    private static final String PAN_REGEX="^[A-Z]{5}[0-9]{4}[A-Z]$";
    private static final Pattern PATTERN=Pattern.compile(PAN_REGEX);
    @Description("UDF to validate the PAN number")
    @ScalarFunction("pan_check")
    @SqlType(StandardTypes.BOOLEAN)
    public static Boolean PAN_CHECK(
            @SqlNullable @SqlType(StandardTypes.VARCHAR) String pan) {
        try {
            if (pan == null || pan.isEmpty()) {
                LOG.error("PAN is null");

                return false;
            } else {
                return PATTERN.matcher(pan).matches();
            }

        } catch (Exception e) {
            LOG.error("Something went wrong",e);
            throw new RuntimeException(e);
        }
    }
}
