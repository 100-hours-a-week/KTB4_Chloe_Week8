package homework.week4.Security;

import homework.week4.User.entity.User;
import homework.week4.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String useremail) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(useremail)
                // 여기서 던지는 에러 메세지는 클라이언트까지 가지 않음!
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Spring Security의 UserDetails 객체로 변환
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

}
