package com.meoying.ai.ielts.web;

import jakarta.servlet.http.HttpSession;

public class AbstractController {
    public static final String uidKey="uid";
    protected long getUid(HttpSession sess) {
        return (long) sess.getAttribute(uidKey);
    }
}
