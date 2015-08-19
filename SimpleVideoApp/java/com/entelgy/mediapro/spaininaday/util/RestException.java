package com.entelgy.mediapro.spaininaday.util;


public class RestException extends Exception {

    private String messageInBody;

    public RestException() {
        super();
    }
    public RestException(String messageInBody) {
        new RestException();
        setMessageInBody(messageInBody);
    }

    public String getMessageInBody() {
        return messageInBody;
    }

    public void setMessageInBody(String messageInBody) {
        this.messageInBody = messageInBody;
    }
}
