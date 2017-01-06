package org.ncibi.task;

import org.ncibi.db.ws.Task;
import org.ncibi.commons.config.ProjectConfiguration;
import org.ncibi.commons.io.FileUtilities;

public class TaskFileUtil
{
    private TaskFileUtil() {}
    
    private static final String statusDir = ProjectConfiguration.getProjectProperty("status.dir");
    
    public static String taskFile(Task task)
    {
        return taskFile(task.getUuid());
    }
    
    public static String taskFile(String uuid)
    {
        String separator = FileUtilities.pathSeparator();
        String dir = statusDir;
        if (! statusDir.endsWith(separator))
        {
            dir += separator;
        }
        return dir + uuid + ".done";
    }
}
