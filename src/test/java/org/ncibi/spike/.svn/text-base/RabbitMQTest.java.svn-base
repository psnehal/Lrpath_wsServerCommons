package org.ncibi.spike;

import junit.framework.Assert;

import org.junit.Test;
import org.ncibi.mqueue.Message;
import org.ncibi.mqueue.MessageQueue;
import org.ncibi.rabbitmq.RabbitMQ;

public class RabbitMQTest
{
    @Test
    public void testQueueCreation()
    {
        MessageQueue queue = RabbitMQ.createQueueAsReceiver("TestQueue", "localhost");
        queue.putMessage(new Message("Hello World"));
        Message m = queue.retrieveAndDeleteNextMessage();
        Assert.assertNotNull(m);
        Assert.assertEquals("Hello World", m.getMessage());
    }
}
