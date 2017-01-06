package org.ncibi.task.logger;

import org.ncibi.db.PersistenceSession;
import org.ncibi.db.ws.Task;

public class PersistentConsoleTaskLogger extends PersistentTaskLogger implements TaskLogger 
{
    public PersistentConsoleTaskLogger(PersistenceSession persistence)
    {
        super(persistence);
    }

    @Override
    public void logStart(Task task)
    {
        super.logStart(task);
        System.out.println("\nStarting task with uuid:" + task.getUuid() + ", CommandType: "
                + task.getTaskType());
    }

    @Override
    public void logFinish(Task task)
    {
        super.logFinish(task);
        System.out.println("Finished task with uuid: " + task.getUuid() + ", CommandType: " + task.getTaskType() + "\n");
    }

    @Override
    public void logMessage(Task task, String message)
    {
        super.logMessage(task, message);
        System.out.println("Message for task with uuid: " + task.getUuid() + ", CommandType: " + task.getTaskType() + ", Message: " + message);
    }
    
    @Override
    public void logError(Task task, Exception e)
    {
        super.logError(task, e);
        System.out.println("\nError for task with uuid: " + task.getUuid() + " threw exception: " + e.toString());
    }
    
    @Override
    public void logError(Task task, String message)
    {
        super.logError(task, message);
        System.out.println("\nError for task with uuid: " + task.getUuid() + " with message: " + message);
    }
    
    @Override
    public void logMessage(String message)
    {
        super.logMessage(message);
        System.out.printf("%s%n", message);
    }

}
