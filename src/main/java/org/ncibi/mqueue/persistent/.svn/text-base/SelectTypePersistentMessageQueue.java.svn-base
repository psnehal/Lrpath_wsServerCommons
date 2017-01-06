package org.ncibi.mqueue.persistent;

import java.util.ArrayList;
import java.util.List;

import org.ncibi.db.PersistenceSession;
import org.ncibi.db.ws.TaskType;
import org.ncibi.db.ws.Queue;

import com.google.common.base.Joiner;

public class SelectTypePersistentMessageQueue extends AbstractPersistentMessageQueue
{
    private final String commands;
    
    public SelectTypePersistentMessageQueue(PersistenceSession persistence, String queueName, TaskType... commands)
    {
        super(persistence, queueName);
        List<String> quotedCommands = new ArrayList<String>();
        
        for (TaskType c : commands)
        {
            quotedCommands.add("'" + c + "'");
        }
        
        this.commands = Joiner.on(',').join(quotedCommands);
    }

    @Override
    protected String hqlForNextTask(Queue queue)
    {
        String hql = " from ws.QMessage qm " +
                     "    join ws.Task t on qm.message = t.uuid and t.type in (" + commands + ")" +
                     " where qm.queueid = " + queue.getId();
        return hql;
    }
}