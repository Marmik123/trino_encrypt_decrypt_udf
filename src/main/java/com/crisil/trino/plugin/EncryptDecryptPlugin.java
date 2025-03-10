package com.crisil.trino.plugin;

import com.google.common.collect.ImmutableSet;
import io.trino.spi.Plugin;
import io.trino.spi.function.FunctionProvider;

import java.util.Set;

    public class EncryptDecryptPlugin
            implements Plugin, FunctionProvider
    {
        @Override
        public Set<Class<?>> getFunctions()
        {
            return ImmutableSet.<Class<?>>builder()
                    .add(EncryptDecryptUDF.class)
                    .add(ValidatePAN.class)
                    .add(ValidateMobileNo.class)
                    .add(ValidateDate.class)
                    .build();
        }
    }
