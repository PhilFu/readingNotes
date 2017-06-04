package com.philfu.mavenInAction;

import javax.mail.MessagingException;

/**
 * Created by fuweiwei02 on 2017/6/4.
 */
public class AccountEmailException extends RuntimeException {
    protected AccountEmailException(String s, MessagingException e) {
        super(s, e);
    }
}
