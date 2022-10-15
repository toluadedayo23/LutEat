package com.practise.luteat.event;

import com.practise.luteat.model.User;
import org.springframework.context.ApplicationEvent;

public class UserRegistrationEvent extends ApplicationEvent {

    private static  final Long serialVersionUID = -25363637272L;

    private User user;

    public UserRegistrationEvent(User user) {
        super(user);
        this.user = user;
    }

    public User getUser(){
        return this.user;
    }
}
