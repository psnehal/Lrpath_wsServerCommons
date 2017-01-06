package org.ncibi.aws.util;

import java.io.IOException;
import java.io.InputStream;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;

public class CredentialUtil
{
    private CredentialUtil() {}
    
    public static AWSCredentials getCredentials(String propertyFile)
    {
        try
        {
            final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            final InputStream in = classLoader.getResourceAsStream(propertyFile);
            return new PropertiesCredentials(in);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new IllegalStateException("Could not read credentials.");
        }
    }
}
