package com.hotel.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Dasha on 04.05.2015.
 */
public class EmailSender {
    private static Log log = LogFactory.getLog(EmailSender.class);

    public static boolean send(String email, String msgBody) {
        try {
            Properties props = new Properties();
            if (loadPropertiesFile(props)) {
                return false;
            }
            final String username = props.getProperty("username");
            final String password = props.getProperty("password");
            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
            Transport transport = session.getTransport();
            try {
                Message msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress(username));
                msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                msg.setSubject("Hotel");
                msg.setText(msgBody);
                transport.send(msg);
            } catch (Exception e) {
                log.error("Email sending failed due to exception:", e);
                return false;
            }

        } catch (NoSuchProviderException e) {
            log.error("Email sending failed due to exception:", e);
            return false;
        }
        return true;
    }

    private static boolean loadPropertiesFile(Properties props) {
        InputStream input = null;
        try {
            String filename = "properties/sending.properties";
            input = EmailSender.class.getClassLoader().getResourceAsStream(filename);
            if (input == null) {
                log.error("Sorry, unable to find " + filename);
                return true;
            }
            props.load(input);
        } catch (IOException e) {
            log.error("Can not load file with properties for sending email", e);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    log.error("Can not close file with properties for sending email", e);
                }
            }
        }
        return false;
    }
}

