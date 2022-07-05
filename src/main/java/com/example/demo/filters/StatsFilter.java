package com.example.demo.filters;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@WebFilter
public class StatsFilter implements Filter{
    public static Logger log = LoggerFactory.getLogger(StatsFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
           Instant start = Instant.now();
           try{
            chain.doFilter(request, response);     
           } finally{
               Instant finish = Instant.now();
               Duration timeDuration = Duration.between(start, finish);
               log.info("[RESPONSE]:{}\t\t{}\t\t\t{} ms", ((HttpServletRequest)request).getHeader("User-Agent"),((HttpServletRequest)request).getRequestURI(),timeDuration.toMillis());
           }
    }
    
}
