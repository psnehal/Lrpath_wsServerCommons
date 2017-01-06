package org.ncibi.mqueue.persistent;

import org.junit.Test;
import org.ncibi.db.EntityManagers;
import org.ncibi.db.PersistenceSession;
import org.ncibi.db.PersistenceUnit;
import org.ncibi.mqueue.Message;

public class PersistentMessageQueueTest
{
    private static PersistenceSession persistence = new PersistenceUnit(EntityManagers
                .newEntityManagerFromProject("task"));

    @Test
    public void testPutMessage()
    {
        AnyMessagePersistentMessageQueue queue = new AnyMessagePersistentMessageQueue(persistence, "test-queue1");
        Message m = Message.newUuidMessage();
        System.out.println("Inserting message: " + m);
        queue.putMessage(m);
        m = Message.newUuidMessage();
        System.out.println("Inserting second message: " + m);
        queue.putMessage(m);
        m = queue.retrieveAndDeleteNextMessage();
        System.out.println("Retrieved and deleted message: " + m);
        m = queue.retrieveNextMessage();
        System.out.println("Retrieved message " + m);
        System.out.println("  and now deleting the message.");
        queue.deleteMessage(m);
        queue.deleteQueue();
    }
    
    @Test
    public void testPurgeQueue()
    {
        AnyMessagePersistentMessageQueue queue = new AnyMessagePersistentMessageQueue(persistence, "test-queue1");
        Message m = Message.newUuidMessage();
        queue.putMessage(m);
        queue.purgeQueue();
        queue.deleteQueue();
    }
}
