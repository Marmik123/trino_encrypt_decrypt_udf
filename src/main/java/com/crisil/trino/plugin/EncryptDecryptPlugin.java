package com.crisil.trino.plugin;

import com.crisil.trino.udf.EncryptDecryptUDF;
import com.crisil.trino.udf.ValidateDate;
import com.crisil.trino.udf.ValidateMobileNo;
import com.crisil.trino.udf.ValidatePAN;
import io.trino.spi.Plugin;
import io.trino.spi.function.FunctionProvider;

import java.util.Set;

public class EncryptDecryptPlugin
        implements Plugin, FunctionProvider
{
    @Override
    public Set<Class<?>> getFunctions()
    {
        return Set.of(
                EncryptDecryptUDF.class,
                ValidatePAN.class,
                ValidateMobileNo.class,
                ValidateDate.class
        );

    }
}
