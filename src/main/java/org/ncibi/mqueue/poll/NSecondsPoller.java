package org.ncibi.mqueue.poll;

import org.ncibi.commons.ipc.ThreadUtil;
import org.ncibi.mqueue.Message;
import org.ncibi.mqueue.MessageQueue;

public class NSecondsPoller implements Poller
{
    private final int secondsToWait;
    private final MessageQueue queue;

    public NSecondsPoller(MessageQueue queue, int seconds)
    {
        this.queue = queue;
        this.secondsToWait = seconds;
    }
    
    @Override
    public Message retrieveAndDeleteNextMessage()
    {
        while (true)
        {
            Message m = queue.retrieveAndDeleteNextMessage();
            if (m == null)
            {
                ThreadUtil.waitSeconds(secondsToWait);
            }
            else
            {
                return m;
            }
        }
    }
}
