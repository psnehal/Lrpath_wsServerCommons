package org.ncibi.mqueue;

import java.util.UUID;

public final class Message
{
    private final String message;
    
    public Message(String message)
    {
        this.message = message;
    }
    
    public static Message newUuidMessage()
    {
        UUID uuid = UUID.randomUUID();
        Message m = new Message(uuid.toString());
        return m;
    }
    
    public String getMessage()
    {
        return message;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "Message [message=" + this.message + "]";
    }
}
