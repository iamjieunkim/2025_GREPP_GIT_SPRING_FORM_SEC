package io.jieun.sec_form.dao;

import io.jieun.sec_form.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> { //entity객체에서 id붙은 타입형

    Optional<Member> findByUsername(String username);

}
