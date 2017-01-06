package org.ncibi.aws.sqs;

import org.ncibi.mqueue.Message;
import org.ncibi.mqueue.MessageQueue;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.DeleteQueueRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;

public class SQSQueue implements MessageQueue
{
    private final AmazonSQS sqs;
    private final String queueUrl;
    private boolean queueDeleted = false;
    
    public SQSQueue(String queueName, AWSCredentials credentials)
    {
        sqs = new AmazonSQSClient(credentials);
        CreateQueueRequest request = new CreateQueueRequest(queueName);
        queueUrl = sqs.createQueue(request).getQueueUrl();
    }
    
    @Override
    public void putMessage(final Message message)
    {
        checkIfDeleted();
        sqs.sendMessage(new SendMessageRequest(queueUrl, message.getMessage()));
    }
    
    @Override
    public Message retrieveAndDeleteNextMessage()
    {
        com.amazonaws.services.sqs.model.Message message = SQSUtil.retrieveNextMessage(sqs, queueUrl);
        if (message == null)
        {
            return  null;
        }
        deleteAwsMessage(message);
        return new Message(message.getBody());
    }
    
    @Override
    public Message retrieveNextMessage()
    {
        checkIfDeleted();
        com.amazonaws.services.sqs.model.Message awsMessage = SQSUtil.retrieveNextMessage(sqs, queueUrl);
        Message m = new Message(awsMessage.getBody());
        return m;
    }
    
    @Override
    public void deleteMessage(Message message)
    {
        throw new UnsupportedOperationException("Not supported.");
    }
    
    private void deleteAwsMessage(com.amazonaws.services.sqs.model.Message awsMessage)
    {
      checkIfDeleted();
      sqs.deleteMessage(new DeleteMessageRequest(queueUrl, awsMessage.getReceiptHandle()));
    }
    
    @Override
    public void deleteQueue()
    {
        checkIfDeleted();
        sqs.deleteQueue(new DeleteQueueRequest(queueUrl));
        queueDeleted = true;
    }
    
    private void checkIfDeleted()
    {
        if (queueDeleted)
        {
            throw new IllegalStateException("Operation attempted on deleted queue");
        }
    }
    
    @Override
    public void purgeQueue()
    {
        throw new UnsupportedOperationException("purge queue not supported.");
    }
}
