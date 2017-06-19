package jp.gr.java_conf.kmine27.spring.session.redis.controller;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
@SessionAttributes("timestamp")
public class IndexController {

    private final static Logger log = LoggerFactory
            .getLogger(IndexController.class);

    @RequestMapping("/")
    public String index() {
        return "Hello World";
    }

    @RequestMapping("/set")
    public ResponseHolder setSession(Model model) {
        String ipAddress = getIpAddress();
        String timestamp = getTimestamp();
        model.addAttribute("timestamp", timestamp);
        return new ResponseHolder(ipAddress, timestamp);
    }

    @RequestMapping("/get")
    public ResponseHolder getSession(Model model, String timestamp) {
        String ipAddress = getIpAddress();
        return new ResponseHolder(ipAddress, timestamp);
    }

    @RequestMapping("/clear")
    public ResponseHolder clear(SessionStatus session) {
        session.setComplete();
        String ipAddress = getIpAddress();
        return new ResponseHolder(ipAddress, null);
    }

    private String getIpAddress() {
        String ipAddress = "unknown";
        try {
            ipAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("can't get local address", e);
        }
        return ipAddress;
    }
    
    private String getTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ResponseHolder implements Serializable {
        private static final long serialVersionUID = 1L;
        private String ipAddress;
        private String timestamp;
    }
}
