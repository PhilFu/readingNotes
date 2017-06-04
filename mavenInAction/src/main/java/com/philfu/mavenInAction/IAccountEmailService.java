package com.philfu.mavenInAction;

/**
 * Created by fuweiwei02 on 2017/6/4.
 */
public interface IAccountEmailService {
    void sendMail(String to, String subject, String htmlText) throws AccountEmailException;
}
