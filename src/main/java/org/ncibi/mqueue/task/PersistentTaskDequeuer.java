package org.ncibi.mqueue.task;

import org.hibernate.Session;
import org.ncibi.db.PersistenceSession;
import org.ncibi.db.ws.Task;
import org.ncibi.db.ws.TaskType;
import org.ncibi.hibernate.SessionProcedure;
import org.ncibi.hibernate.Sessions;
import org.ncibi.mqueue.Message;
import org.ncibi.mqueue.poll.Poller;
import org.ncibi.task.TaskStatus;

public class PersistentTaskDequeuer implements TaskDequeuer
{
    private final Poller poller;
    private final PersistenceSession persistence;
    //private final EnumMap<TaskType, AbstractTaskArgRetriever> argRetriever = new EnumMap<TaskType, AbstractTaskArgRetriever>();

    public PersistentTaskDequeuer(Poller poller, PersistenceSession persistence)
    {
        this.poller = poller;
        this.persistence = persistence;
    }
    
    @Override
    public Task dequeueNextTask(TaskType taskType)
    {
        return dequeueHandlingNull();
    }
    
    @Override 
    public Task dequeueNextTask()
    {
        return dequeueHandlingNull();
    }

    public Task dequeue()
    {
        return dequeueHandlingNull();
    }

    private Task dequeueHandlingNull()
    {
        String uuid = retrieveNextTasksUuidFromQueue();
        
        if (uuid == null)
        {
            return null;
        }

        Task task = retrieveAndMarkAsRunningQueuedTaskMatchingUuidFromDatabase(uuid);
        return task;
    }

    private String retrieveNextTasksUuidFromQueue()
    {
        Message m = poller.retrieveAndDeleteNextMessage();
        if (m == null)
        {
            return null;
        }
        String uuid = m.getMessage();
        return uuid;
    }

    private Task retrieveAndMarkAsRunningQueuedTaskMatchingUuidFromDatabase(String uuid)
    {
        Task task = retrieveQueuedTaskMatchingUuidFromDatabaseHandlingExceptions(uuid);
        
        if (task != null)
        {
            markTaskAs(task, TaskStatus.RUNNING);
        }

        return task;
    }

    private Task retrieveQueuedTaskMatchingUuidFromDatabaseHandlingExceptions(String uuid)
    {
        final String hql = "from ws.Task where uuid = '" + uuid + "' and status = 'QUEUED'";
        Task task = null;

        try
        {
            task = persistence.hqlQuery(hql).single();
        }
        catch (Exception e)
        {
            task = null;
        }

        return task;
    }

    public void markTaskAs(final Task task, final TaskStatus taskStatus)
    {
        Sessions.withSession(persistence.session(), new SessionProcedure()
        {
            @Override
            public void apply(Session session)
            {
                session.refresh(task);
                task.setStatus(taskStatus);
                session.saveOrUpdate(task);
            }
        });
    }

    public void remove(final Task task)
    {
        Sessions.withSession(persistence.session(), new SessionProcedure()
        {
            @Override
            public void apply(Session session)
            {
                session.refresh(task);
                session.delete(task);
            }
        });
    }
}
