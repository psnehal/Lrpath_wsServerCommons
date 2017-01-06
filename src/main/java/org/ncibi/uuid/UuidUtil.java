package org.ncibi.uuid;

import java.util.UUID;

import org.ncibi.commons.io.FileUtilities;

public class UuidUtil
{
    private UuidUtil() {}
    
    public static String tempUuidFile()
    {
        String uuid = UUID.randomUUID().toString();
        String tmpDir = FileUtilities.tmpDir();
        
        return tmpDir + "/" + uuid;
    }
    
    public static String tempUuidFile(String baseName)
    {
        String uuidFile = tempUuidFile() + "." + baseName ;
        return uuidFile;
    }
}
