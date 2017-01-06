package org.ncibi.mqueue;

import org.apache.commons.lang.math.NumberUtils;
import org.ncibi.commons.config.Configuration;
import org.ncibi.commons.config.ProjectConfiguration;

public class MessageQueueConfiguration
{
    private static final Configuration configuration = ProjectConfiguration.getProject().getConfiguration();
    
    public static int pollingMaxWait()
    {
        return getDefaultedIntegerProperty("queue.poll.maxwait", 120);
    }
    
    public static int pollingDelayIncrement()
    {
        return getDefaultedIntegerProperty("queue.poll.delayincrement", 20);
    }
    
    public static int pollingWait()
    {
        return getDefaultedIntegerProperty("queue.poll.wait", 5);
    }
    
    private static int getDefaultedIntegerProperty(String property, int defaultValue)
    {
        return NumberUtils.toInt(configuration.getProperty(property), defaultValue);
    }
    
    public static String queueName()
    {
        return configuration.getDefaultedProperty("queue.name", "NCIBI-TASK-QUEUE");
    }
    
    public static String queueHost()
    {
        return configuration.getDefaultedProperty("queue.host", "localhost");
    }
}
