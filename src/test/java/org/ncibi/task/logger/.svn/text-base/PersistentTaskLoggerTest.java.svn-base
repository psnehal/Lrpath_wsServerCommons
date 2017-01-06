package org.ncibi.task.logger;

import org.junit.Ignore;
import org.junit.Test;
import org.ncibi.db.EntityManagers;
import org.ncibi.db.PersistenceSession;
import org.ncibi.db.PersistenceUnit;
import org.ncibi.db.ws.TaskType;
import org.ncibi.db.ws.Task;
import org.ncibi.task.TaskStatus;

public class PersistentTaskLoggerTest
{
    @Test
    @Ignore
    public void testLogging()
    {
        PersistenceSession persistence = new PersistenceUnit(EntityManagers
                    .newEntityManagerFromProject("task"));
        TaskLogger logger = new PersistentTaskLogger(persistence);
        Task task = new Task();
        task.setUuid("ABC123");
        task.setTaskType(TaskType.LRPATH);
        task.setStatus(TaskStatus.QUEUED);
        logger.logStart(task);
    }
}
