package com.crisil.trino.udf;

import io.trino.spi.function.Description;
import io.trino.spi.function.ScalarFunction;
import io.trino.spi.function.SqlNullable;
import io.trino.spi.function.SqlType;
import io.trino.spi.type.StandardTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class ValidateDate {
    private static final Logger LOG = LoggerFactory.getLogger(ValidateDate.class);
    // Regex to check basic format before parsing (YYYY-MM-DD)
    private static final String DATE_REGEX = "^(?:(?:19|20)\\d{2})-(?:(?:0[1-9]|1[0-2]))-(?:(?:0[1-9]|1\\d|2\\d|3[01]))$";
    private static final Pattern PATTERN = Pattern.compile(DATE_REGEX);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Description("UDF to validate the date format")
    @ScalarFunction("date_check")
    @SqlType(StandardTypes.BOOLEAN)
    public static Boolean DATE_CHECK(
            @SqlNullable @SqlType(StandardTypes.VARCHAR) String date) {
        if(date==null){
            LOG.error("Date is null");
            return false;
        }
        // 1. Check format using regex
        if(!PATTERN.matcher(date.toString().trim()).matches()){
            LOG.error("Regex not satisfied");
            return false; //regex condition not satisfied
        }

        try {
            // 2. Parse and validate using LocalDate (Handles leap year and invalid days)
            LocalDate.parse(date,FORMATTER);
            LOG.info("Date parsed successfully");
            return true;
        } catch (DateTimeParseException e) {
            LOG.error("Something went wrong",e);
            return false;
        }

    }
}
