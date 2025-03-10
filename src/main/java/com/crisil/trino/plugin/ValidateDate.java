package com.crisil.trino.plugin;

import io.trino.spi.function.Description;
import io.trino.spi.function.ScalarFunction;
import io.trino.spi.function.SqlNullable;
import io.trino.spi.function.SqlType;
import io.trino.spi.type.StandardTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

public class ValidateDate {
    private static final Logger LOG = LoggerFactory.getLogger(ValidateDate.class);
    private static final String DATE_REGEX="^\\(?\\+?[1-9][0-9]{0,2}\\)?[-.\\s]?[1-9][0-9]{7,14}$";
    private static final Pattern PATTERN=Pattern.compile(DATE_REGEX);
    @Description("UDF to validate the date format")
    @ScalarFunction("date_check")
    @SqlType(StandardTypes.BOOLEAN)
    public static Boolean DATE_CHECK(
            @SqlNullable @SqlType(StandardTypes.VARCHAR) String date) {
        try {
            if (date == null || date.isEmpty()) {
                return false;
            } else {
                return PATTERN.matcher(date.toString().trim()).matches();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
