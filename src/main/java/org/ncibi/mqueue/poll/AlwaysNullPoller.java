package org.ncibi.mqueue.poll;

import org.ncibi.mqueue.Message;

public class AlwaysNullPoller implements Poller
{
    @Override
    public Message retrieveAndDeleteNextMessage()
    {
        return null;
    }
}
