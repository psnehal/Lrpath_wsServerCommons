package org.ncibi.mqueue.poll;

import org.ncibi.commons.ipc.ThreadUtil;
import org.ncibi.mqueue.Message;
import org.ncibi.mqueue.MessageQueue;

public class AdaptivePoller implements Poller
{
    private int delayInSeconds = 0;
    private final int maxWaitInSeconds;
    private final int delayIncrementInSeconds;
    private final MessageQueue queue;
    
    public AdaptivePoller(MessageQueue queue, int maxWaitInSeconds)
    {
        this.queue = queue;
        this.maxWaitInSeconds = maxWaitInSeconds;
        this.delayIncrementInSeconds = 2;
    }
    
    public AdaptivePoller(MessageQueue queue, int maxWaitInSeconds, int delayIncrementInSeconds)
    {
        this.queue = queue;
        this.maxWaitInSeconds = maxWaitInSeconds;
        this.delayIncrementInSeconds = delayIncrementInSeconds;
    } 
    
    @Override
    public Message retrieveAndDeleteNextMessage()
    {
        while (true)
        {
            Message m = queue.retrieveAndDeleteNextMessage();
            if (m == null)
            {
                int possibleNewDelay = delayInSeconds + delayIncrementInSeconds;
                if (possibleNewDelay < maxWaitInSeconds)
                {
                    delayInSeconds = possibleNewDelay;
                }
                ThreadUtil.waitSeconds(delayInSeconds);
            }
            else
            {
                delayInSeconds = 0;
                return m;
            }
        }
    }
}
