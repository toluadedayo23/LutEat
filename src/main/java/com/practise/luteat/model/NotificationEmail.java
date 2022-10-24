package com.practise.luteat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEmail {

    @NotEmpty(message = "Subject cannnot be null")
    public String subject;
    @NotEmpty(message = "Subject cannnot be null")
    public String recipient;
    public String body;
}

