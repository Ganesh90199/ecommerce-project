package com.ganesh.ecommerce.config;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        System.out.println("PATH -> " + path);

        // ✅ Skip auth APIs
        if (path.startsWith("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Missing Token");
            return;
        }

        String token = header.substring(7).trim();

        // ✅ Validate token
        if (!jwtUtil.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Invalid Token");
            return;
        }

        String role = jwtUtil.extractRole(token);
        System.out.println("ROLE -> " + role);

        if (path.startsWith("/api/products/admin")) {

            if (role == null || !role.equals("ADMIN")) {
                response.setStatus(403);
                response.getWriter().write("Admin only");
                return;
            }
        }
        
//        // 🔥 ADMIN ONLY APIs
//        if ((path.startsWith("/api/products/admin") || path.startsWith("/api/admin"))
//                && !"ADMIN".equals(role)) {
//
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            response.getWriter().write("Admin only");
//            return;
//        }

        filterChain.doFilter(request, response);
    }
}