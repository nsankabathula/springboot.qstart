package io.java.springboot.mail;

import microsoft.exchange.webservices.data.autodiscover.IAutodiscoverRedirectionUrl;

public class RedirectionUrlCallback implements IAutodiscoverRedirectionUrl {
    public boolean autodiscoverRedirectionUrlValidationCallback(
            String redirectionUrl) {
        return redirectionUrl.toLowerCase().startsWith("https://");
    }
}
