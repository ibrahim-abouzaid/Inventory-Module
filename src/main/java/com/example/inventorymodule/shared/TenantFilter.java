package com.example.inventorymodule.shared;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TenantFilter  extends OncePerRequestFilter {
    private static final String TENANT_HEADER = "X-Tenant-Id";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tenantId = request.getHeader(TENANT_HEADER);

        if (tenantId == null || tenantId.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write("""
                {"error": "BAD_REQUEST", "message": "X-Tenant-Id header is required"}
                """);
            System.out.println("tenant filter");
            return;                  // do NOT call filterChain.doFilter — stop here
        }

        try {
            TenantContext.set(tenantId.trim());
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();   // always runs — even on exception
        }
    }
    }
