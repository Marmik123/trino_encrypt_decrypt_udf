package com.crisil.trino.plugin;



import com.google.common.collect.ImmutableSet;
import io.trino.spi.Plugin;

import java.util.Set;

    public class EncryptDecryptPlugin
            implements Plugin
    {
        @Override
        public Set<Class<?>> getFunctions()
        {
            return ImmutableSet.<Class<?>>builder()
                    .add(EncryptDecryptUDF.class)
                    .build();
        }
    }
