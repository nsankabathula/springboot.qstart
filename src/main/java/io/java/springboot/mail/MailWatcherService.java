package io.java.springboot.mail;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;

@Service
public class MailWatcherService {

    private Properties getServerProperties(String protocol, String host, String port){
        Properties props = new Properties();

        props.put(String.format("mail.%s.host", protocol), host);
        props.put(String.format("mail.%s.port", protocol), port);
        props.setProperty(
                String.format("mail.%s.socketFactory.class",
                        protocol), "javax.net.ssl.SSLSocketFactory");
        props.setProperty(
                String.format("mail.%s.socketFactory.fallback",
                        protocol), "false");
        props.setProperty(
                String.format("mail.%s.socketFactory.port",
                        protocol), String.valueOf(port));

        return props;
    }

    public List<Message> getNewEmails(String protocol, String host,
                                     String port, String userName, String password) {
        Properties properties = getServerProperties(protocol,
                host, port);
        System.out.println(properties);
        Session session = Session.getDefaultInstance(properties);
        Message[] messages = new Message[0];

        try {
            Store store = session.getStore(protocol);
            store.connect(userName, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);

            int count = inbox.getMessageCount();
            messages = inbox.getMessages(1, count);
            for (Message message : messages) {
                if (!message.getFlags().contains(Flags.Flag.SEEN)) {
                    Address[] fromAddresses = message.getFrom();
                    System.out.println("...................");
                    System.out.println("\t From: "
                            + fromAddresses[0].toString());
                    System.out.println("\t To: "
                            + parseAddresses(message
                            .getRecipients(RecipientType.TO)));
                    System.out.println("\t CC: "
                            + parseAddresses(message
                            .getRecipients(RecipientType.CC)));
                    System.out.println("\t Subject: "
                            + message.getSubject());
                    System.out.println("\t Sent Date:"
                            + message.getSentDate().toString());
                    try {
                        System.out.println(message.getContent().toString());
                    } catch (Exception ex) {
                        System.out.println("Error reading content!!");
                        ex.printStackTrace();
                    }
                }

            }

            inbox.close(false);
            store.close();
        } catch (NoSuchProviderException ex) {
            System.out.println("No provider for protocol: "
                    + protocol);
            ex.printStackTrace();
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store");
                    ex.printStackTrace();
        }
        return Arrays.asList(messages);
    }

    private String parseAddresses(Address[] address) {

        String listOfAddress = "";
        if ((address == null) || (address.length < 1))
            return null;
        if (!(address[0] instanceof InternetAddress))
            return null;

        for (int i = 0; i < address.length; i++) {
            InternetAddress internetAddress =
                    (InternetAddress) address[0];
            listOfAddress += internetAddress.getAddress()+",";
        }
        return listOfAddress;
    }

    public List<Message> getMessages(){
        return getNewEmails("imap", "imap-mail.outlook.com", "993", "nsankabathula@riskcare.com", "May)%2018");
    }

}
