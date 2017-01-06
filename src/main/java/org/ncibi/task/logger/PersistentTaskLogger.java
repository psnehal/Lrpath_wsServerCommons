package org.ncibi.task.logger;

import java.util.Date;

import org.hibernate.Session;
import org.ncibi.db.PersistenceSession;
import org.ncibi.db.ws.Task;
import org.ncibi.db.ws.TaskLog;
import org.ncibi.hibernate.SessionProcedure;
import org.ncibi.hibernate.Sessions;

public class PersistentTaskLogger implements TaskLogger
{
    private final PersistenceSession persistence;

    public PersistentTaskLogger(PersistenceSession persistence)
    {
        this.persistence = persistence;
    }

    @Override
    public void logStart(Task task)
    {
        writeLogEntry(task.getUuid(), "Task " + task.getTaskType() + " started.");
    }

    @Override
    public void logFinish(Task task)
    {
        writeLogEntry(task.getUuid(), "Task " + task.getTaskType() + " finished.");
    }
    
    @Override
    public void logMessage(Task task, String message)
    {
        writeLogEntry(task.getUuid(), message);
    }
    
    @Override
    public void logError(Task task, Exception e)
    {
        writeLogEntry(task.getUuid(), "Task errored with exception: " + e.toString());
    }
    
    @Override 
    public void logError(Task task, String message)
    {
        writeLogEntry(task.getUuid(), "Task errored with message: " + message);
    }
    
    @Override
    public void logMessage(String message)
    {
        return;
    }

    private void writeLogEntry(String uuid, String message)
    {
        final TaskLog taskLog = new TaskLog();
        taskLog.setTaskUuid(uuid);
        taskLog.setEntryTimestamp(new Date());
        taskLog.setLogEntry(message);

        Sessions.withSession(persistence.session(), new SessionProcedure()
        {
            @Override
            public void apply(Session session)
            {
                session.saveOrUpdate(taskLog);
            }
        });
    }
}
