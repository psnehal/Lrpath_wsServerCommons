package org.ncibi.task.logger;

import org.ncibi.db.ws.Task;

public interface TaskLogger
{
    public void logStart(Task task);
    public void logFinish(Task task);
    public void logError(Task task, Exception e);
    public void logError(Task task, String reason);
    public void logMessage(Task task, String message);
    public void logMessage(String message);
}
