package homework.week4.Security.Userdetails;

import homework.week4.User.entity.User;
import homework.week4.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
        // 내가 직접 만든 CustomUserDetails로 반환
        return new CustomUserDetails(user);
    }

}
