package com.meoying.ai.ielts.service.gpt;

public abstract class AbstractHandler implements Handler{
    protected Handler next;
    @Override
    public void setNext(Handler next) {
        this.next = next;
    }

    public Response retryHandle(Handler handler,Request req){
        return null;
    }
}
