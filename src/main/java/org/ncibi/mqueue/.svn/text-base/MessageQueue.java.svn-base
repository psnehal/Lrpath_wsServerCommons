package org.ncibi.mqueue;

public interface MessageQueue
{
    public void putMessage(Message m);
    public Message retrieveAndDeleteNextMessage();
    public Message retrieveNextMessage();
    public void deleteMessage(Message m);
    public void deleteQueue();
    public void purgeQueue();
}
