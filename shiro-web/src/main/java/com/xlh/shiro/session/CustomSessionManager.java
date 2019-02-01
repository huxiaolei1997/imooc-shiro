package com.xlh.shiro.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;

import javax.servlet.ServletRequest;
import java.io.Serializable;

/**
 * @author xiaolei hu
 * @date 2019/1/27 20:08
 **/
public class CustomSessionManager extends DefaultWebSessionManager {

    @Override
    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
        // 第一次从redis中读取session的时候把session放到 request对象里面，这样就不用每次都去redis中去读取session了
        Serializable sessionId = getSessionId(sessionKey);

        ServletRequest request = null;

        if (sessionKey instanceof WebSessionKey) {
            request = ((WebSessionKey) sessionKey).getServletRequest();
        }

        if (null != request && null != sessionId) {
            return (Session) request.getAttribute(sessionId.toString());
        }

        // 如果从 request 对象中获取不到session，那么从redis中去读取session
        Session session = super.retrieveSession(sessionKey);

        if (null != request && null != sessionId) {
            request.setAttribute(sessionId.toString(), session);
        }

        return session;
    }
}