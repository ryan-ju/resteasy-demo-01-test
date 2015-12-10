package org.itechet.resteasy.demo01;

import com.jayway.restassured.RestAssured;

/**
 * Created by ryan on 10/12/15.
 */
public class Base {
    public Base() {
        String base = System.getProperty("host", "http://localhost");
        int port = Integer.getInteger("port", 8080);
        String context = System.getProperty("context", "/resteasy/api");
        RestAssured.baseURI =
                base
                + (port == 80 || port == 443 ? "" : (":" + port))
                + context;
    }
}
