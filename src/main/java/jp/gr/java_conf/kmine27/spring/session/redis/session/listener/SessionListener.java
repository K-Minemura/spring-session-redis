package jp.gr.java_conf.kmine27.spring.session.redis.session.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.session.events.SessionCreatedEvent;
import org.springframework.session.events.SessionExpiredEvent;
import org.springframework.stereotype.Component;

@Component
public class SessionListener {
    
    private static Logger log = LoggerFactory.getLogger(SessionListener.class);

    @Component
    public static class SessionCreateListener implements ApplicationListener<SessionCreatedEvent> {
        @Override
        public void onApplicationEvent(SessionCreatedEvent event) {
            log.info("create  session[" + event.getSessionId() + "].");
        }
    }

    @Component
    public static class SessionDestroyListener implements ApplicationListener<SessionDestroyedEvent> {
        @Override
        public void onApplicationEvent(SessionDestroyedEvent event) {
            log.info("destroy session[" + event.getId() + "].");
        }
    }

    @Component
    public static class SessionExpireListener implements ApplicationListener<SessionExpiredEvent> {
        @Override
        public void onApplicationEvent(SessionExpiredEvent event) {
            log.info("expired session[" + event.getSessionId() + "].");
        }
        
    }
}
