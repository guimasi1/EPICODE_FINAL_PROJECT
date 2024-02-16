package guidomasi.Final.Project.security;

import guidomasi.Final.Project.entities.Patient;
import guidomasi.Final.Project.entities.Physiotherapist;
import guidomasi.Final.Project.enums.Role;
import guidomasi.Final.Project.exceptions.UnauthorizedException;
import guidomasi.Final.Project.services.PatientsService;
import guidomasi.Final.Project.services.PhysiotherapistsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwe;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PhysiotherapistsService physiotherapistsService;

    @Autowired
    private PatientsService patientsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Per favore metti il token nell'Authorization header");
        } else {
            String accessToken = authHeader.substring(7);
            jwtTools.verifyToken(accessToken);
            String id = jwtTools.extractIdFromToken(accessToken);
            String role = jwtTools.extractRoleFromToken(accessToken);
            if(role.equals(Role.PHYSIOTHERAPIST.name())){
                Physiotherapist physiotherapist = physiotherapistsService.findById(UUID.fromString(id));
                Authentication authentication = new UsernamePasswordAuthenticationToken(physiotherapist, null, physiotherapist.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            } else if (role.equals(Role.PATIENT.name())) {
                Patient patient = patientsService.findById(UUID.fromString(id));
                Authentication authentication = new UsernamePasswordAuthenticationToken(patient, null, patient.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            }
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }

}
