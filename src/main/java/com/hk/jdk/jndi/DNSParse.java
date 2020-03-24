package com.hk.jdk.jndi;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

public class DNSParse {

    private static void dns() {

        try {
            DirContext ictx = new InitialDirContext(new Hashtable<>());
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }
}
