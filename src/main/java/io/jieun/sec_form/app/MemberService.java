package io.jieun.sec_form.app;

import io.jieun.sec_form.dao.MemberRepository;
import io.jieun.sec_form.domain.Member;
import io.jieun.sec_form.dto.MemberDetails;
import io.jieun.sec_form.dto.SignUpForm;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService { //얘가 진짜 비지니스 로직임

    private final PasswordEncoder passwordEncoder; //BCryptPasswordEncoder에는 PasswordEncoder이게 포함되어 있음, 비밀번호 인코딩을 위해 불러옴
    private final MemberRepository memberRepository;

    //가입할때 호출이 됨
    public void save(SignUpForm signUpForm) {

        Member member = Member.builder()
                .username(signUpForm.getUsername())
                .password(passwordEncoder.encode(signUpForm.getPassword())) //비밀번호를 인코딩 해줘야함
                .email(signUpForm.getEmail())
                .build();

        memberRepository.save(member);
    }

    //로그인할떄 호출
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //memberRepository.findByUs
        Optional<Member> memberOptional = memberRepository.findByUsername(username);
        Member findMember = memberOptional.orElseThrow(
                () -> new UsernameNotFoundException("Username " + username + " not found")
        );

        //new User(findMember.getUsername(), findMember.getPassword(), List.of(new SimpleGrantedAuthority(findMember.getRole())));
        return new MemberDetails(findMember);
    }
}
