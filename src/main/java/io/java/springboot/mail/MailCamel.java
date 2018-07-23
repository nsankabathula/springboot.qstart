package io.java.springboot.mail;


import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.service.folder.Folder;
import microsoft.exchange.webservices.data.core.service.item.Conversation;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.FolderId;
import microsoft.exchange.webservices.data.search.ConversationIndexedItemView;
import org.apache.camel.language.Bean;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class MailCamel {

    private List messages = new ArrayList();
    ExchangeService service;

    public MailCamel(){
        //System.out.println("in MailCamel constructor" + messages.toString());
        //service = new ExchangeService(ExchangeVersion.Exchange2010_SP1.Exchange2010_SP2);
        //ExchangeCredentials credentials = new WebCredentials("xxx", "xxx");
        //service.setCredentials(credentials);


    }

    public String hello(String msg) throws Exception{
        String helloMsg = "Hello " + msg;
        messages.add(helloMsg);
        //service.setUrl(new URI("https://mail.riskcare.com"));
        //service.autodiscoverUrl("Naveen.Sankabathula@riskcare.com", new RedirectionUrlCallback ());

        //Folder inbox = Folder.bind(service, WellKnownFolderName.Inbox);
        //System.out.println(" in hello " + messages.toString() + inbox.getTotalCount());
        //messages.add(inbox.getTotalCount());
        /*
        Collection<Conversation> conversations = service.findConversation(
                new ConversationIndexedItemView(10),
                new FolderId(WellKnownFolderName.Inbox));

        for (Conversation conversation : conversations) {
            System.out.println("Conversation Id : "+conversation.getId());
            System.out.println("Conversation Importance : "+conversation.getImportance());
            System.out.println("Conversation Has Attachments : "+conversation.getHasAttachments());
            System.out.println("Conversation UnreadCount : "+conversation.getUnreadCount());
        }
        */

        return helloMsg;
    }


    public String toString() {
        //System.out.println("in toString" + messages.toString());
        return messages.toString();
    }
}
