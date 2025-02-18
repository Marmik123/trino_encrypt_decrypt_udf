package com.crisil.trino.plugin;

import javax.crypto.SecretKey;
import java.security.SecureRandom;

import com.crisil.trino.util.EncryptDecrypt;
import io.airlift.slice.Slice;
import io.airlift.slice.Slices;
import io.trino.spi.function.Description;
import io.trino.spi.function.ScalarFunction;
import io.trino.spi.function.SqlNullable;
import io.trino.spi.function.SqlType;
import io.trino.spi.type.StandardTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.crisil.trino.util.EncryptDecrypt.generateKey;
import static io.airlift.slice.Slices.utf8Slice;

public class EncryptDecryptUDF {
    private static final Logger LOG = LoggerFactory.getLogger(EncryptDecryptUDF.class);
    /*Currently hardcoded password value, will fetch it from Azure Key vault once it is configured.*/
    private static final String password = "test@123";
    byte[] salt=new byte[16];
    private static SecretKey key;

    //Constructor
    private EncryptDecryptUDF(){
        try {
            init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //Initialization method.
    private void init() throws  Exception{
        salt = SecureRandom.getInstanceStrong().generateSeed(16);
        key = generateKey(password.toCharArray(), salt);
        setKey(key);
    }

    //Getters and Setters
    public static SecretKey getKey() {
        return key;
    }
    public static void setKey(SecretKey key) {
        EncryptDecryptUDF.key = key;
    }

    @Description("UDF to encrypt a value with a given password")
    @ScalarFunction("encrypt")
    @SqlType(StandardTypes.VARCHAR)
    public static Slice encrypt(
            @SqlNullable @SqlType(StandardTypes.VARCHAR) Slice value)
    {
        try {
            return utf8Slice(EncryptDecrypt.encrypt(value.toStringUtf8(), getKey()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Description("UDF to decrypt a value with a given password")
    @ScalarFunction("decrypt")
    @SqlType(StandardTypes.VARCHAR)
    public static Slice decrypt(
            @SqlNullable @SqlType(StandardTypes.VARCHAR) Slice value)
    {
        try {
            return utf8Slice(EncryptDecrypt.decrypt(value.toStringUtf8(), getKey()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String args[]){
        Slice encrypted = encrypt(Slices.utf8Slice("Hello! Trino"));
        Slice decrypted = decrypt(encrypted);
        LOG.info(decrypted.toString());

    }
}
