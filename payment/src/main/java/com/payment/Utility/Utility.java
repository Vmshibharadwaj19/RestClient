package com.payment.Utility;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;


public class Utility {

    public static String generayeTransactionId()
    {
        return "txn"+ LocalDateTime.now().getYear() +"-"+ UUID.randomUUID().toString().substring(16);
    }
}
