package com.mlbyl.usermanagementservicetask.config;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class SimpleRateLimitInterceptor implements HandlerInterceptor {

    private final ConcurrentHashMap<String, RateLimitInfo> clients = new ConcurrentHashMap<>();

    // Sabitler
    private static final int MAX_REQUESTS_PER_SECOND = 5;
    private static final long WINDOW_MILLIS = 1000;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        String clientIp = request.getRemoteAddr();
        long now = Instant.now().toEpochMilli();

        RateLimitInfo info = clients.computeIfAbsent(clientIp, k -> new RateLimitInfo(now, new AtomicInteger(0)));

        if (now - info.timestamp() > WINDOW_MILLIS) {
            info = new RateLimitInfo(now, new AtomicInteger(0));
            clients.put(clientIp, info);
        }

        if (info.counter().incrementAndGet() > MAX_REQUESTS_PER_SECOND) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value()); // 429
            response.getWriter().write("So many requests per second.");
            return false;
        }

        return true;
    }

    private record RateLimitInfo(long timestamp, AtomicInteger counter) {
    }
}