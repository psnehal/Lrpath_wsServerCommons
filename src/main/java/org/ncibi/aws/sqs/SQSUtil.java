package org.ncibi.aws.sqs;

import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;

final class SQSUtil
{
    private SQSUtil() {}
    
    public static Message retrieveNextMessage(AmazonSQS sqs, String queueUrl)
    {
        try
        {
            return retrieveNextSQSMessage(sqs, queueUrl);
        }
        catch (AmazonClientException e)
        {
            e.printStackTrace(); // log failure
            return null;
        }
    }
    
    private static Message retrieveNextSQSMessage(AmazonSQS sqs, String queueUrl)
    {
        ReceiveMessageRequest request = new ReceiveMessageRequest(queueUrl);
        request.setMaxNumberOfMessages(1);
        ReceiveMessageResult result = sqs.receiveMessage(request);
        List<Message> messages = result.getMessages();
        if (messages.isEmpty())
        {
            return null;
        }
        else
        {
            return messages.get(0);
        }
    }
    
    public static void waitSeconds(int seconds)
    {
        try
        {
            Thread.sleep(seconds * 1000);
        }
        catch (InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
