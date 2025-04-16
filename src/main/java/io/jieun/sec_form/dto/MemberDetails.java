package io.jieun.sec_form.dto;

import io.jieun.sec_form.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class MemberDetails implements UserDetails {

    private String username; //getter로 있다는거는 필더가 존재해도 되니깡
    private String password;
    private String role;

    public MemberDetails(Member member) {
        this.username = member.getUsername();
        this.password = member.getPassword();
        this.role = member.getRole();
    }

    @Override
    public String getPassword() { //사용자가 입력한 비밀번호랑, 이걸통해서 꺼낸 비밀번호가 같은지 검사를 해야되서 이거 중요!
        return this.password;
    }

    @Override
    public String getUsername() { //user의 id를 꺼낼 수 있어야 함
        return this.username;
    }


    //인가 검사를 위해 필요함
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        //SimpleGrantedAuthority authority = new SimpleGrantedAuthority(this.role);
        //return List.of(authority);
        return List.of(new SimpleGrantedAuthority(this.role));

        /*
        GrantedAuthority grantedAuthority = new GrantedAuthority() {
            String authority = "ROLE_MEMBER";

            @Override
            public String getAuthority() {
                return authority;
            }
        };
        */
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
