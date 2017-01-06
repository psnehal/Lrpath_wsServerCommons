package org.ncibi.ws.resource;

public enum ResponseFormat
{
    JSON("json"), XML("xml"), RPC_XML("rpcxml");
    
    private final String formatName;
    
    private ResponseFormat(String formatName)
    {
        this.formatName = formatName;
    }
    
    public String formatName()
    {
        return this.formatName;
    }
    
    public static ResponseFormat toResponseFormat(String format)
    {
        for (ResponseFormat rf : ResponseFormat.values())
        {
            if (rf.formatName.equalsIgnoreCase(format))
            {
                return rf;
            }
        }
        
        throw new IllegalArgumentException("Unknown type: " + format);
    }
    
    public static ResponseFormat toResponseFormatWithDefault(String format, ResponseFormat defaultValue)
    {
        try
        {
            return toResponseFormat(format);
        }
        catch (IllegalArgumentException e)
        {
            return defaultValue;
        }
    }
}
