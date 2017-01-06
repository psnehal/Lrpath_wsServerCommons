package org.ncibi.mqueue.persistent;

import org.ncibi.db.PersistenceSession;
import org.ncibi.db.ws.Queue;

public class AnyMessagePersistentMessageQueue extends AbstractPersistentMessageQueue
{
    public AnyMessagePersistentMessageQueue(PersistenceSession persistence, String queueName)
    {
        super(persistence, queueName);
    }
    
    @Override
    protected String hqlForNextTask(Queue queue)
    {
        String hql = "from ws.QMessage where queueid = " + queue.getId();
        return hql;
    }
}
