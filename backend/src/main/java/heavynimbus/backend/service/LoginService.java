package heavynimbus.backend.service;

import heavynimbus.backend.util.JwtTokenUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public record LoginService(AuthenticationManager authenticationManager,
                           JwtUserDetailService jwtUserDetailService,
                           JwtTokenUtil jwtTokenUtil) {

    public String authenticate(String username, String password){
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return createToken(((User)auth.getPrincipal()).getUsername());
    }

    private String createToken(String username){
        final UserDetails userDetails = jwtUserDetailService
                .loadUserByUsername(username);
        return jwtTokenUtil.generateToken(userDetails);
    }
}
