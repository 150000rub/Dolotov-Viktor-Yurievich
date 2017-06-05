package com.exigenservices.lectures;

import com.exigenservices.lectures.utils.*;

import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by Ti_g_programmist(no) on 03.12.2016.
 */
@MessageDriven(mappedName = "Topic1")
public class Consumer implements MessageListener {

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            putToStorage(textMessage.getText());
        } catch (JMSException ex) {
            putToStorage(ex.toString());
        }
    }

    protected void putToStorage(String message) {
        InMemoryStorage.add(getClass().getName() + " - " + message);
    }
}
