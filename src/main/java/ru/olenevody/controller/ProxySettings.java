package ru.olenevody.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Controller
public class ProxySettings {

    private boolean useProxy;
    private String server;
    private String port;

    @Autowired
    public ProxySettings(boolean useProxy, String server, String port) {

        this.useProxy = useProxy;
        this.server = server;
        this.port = port;

        if (useProxy) {
            System.setProperty("socksProxyHost", server);
            System.setProperty("socksProxyPort", port);
        }

    }
}
