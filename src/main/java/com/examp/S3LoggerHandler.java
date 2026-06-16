package com.examp;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification.S3EventNotificationRecord;

public class S3LoggerHandler implements RequestHandler<S3Event, String> {

    @Override
    public String handleRequest(S3Event s3event, Context context) {
        // Prendiamo il primo record dell'evento (il file caricato)
        S3EventNotificationRecord record = s3event.getRecords().get(0);

        String bucketName = record.getS3().getBucket().getName();
        String fileName = record.getS3().getObject().getKey();

        String message = "Nuovo file caricato! Bucket: " + bucketName + " | Nome File: " + fileName;
        // Logghiamo il messaggio (lo vedremo su CloudWatch)
        context.getLogger().log(message);

        return message;
    }
}