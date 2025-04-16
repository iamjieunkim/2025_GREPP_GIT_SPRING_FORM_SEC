package io.jieun.sec_form.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    private String email;

    private String role = "MEMBER"; //MEMBER가 들어있는 상태로 인스턴스 생성, 엔터티에는 final이 오면 절대 안됨!

    private LocalDateTime signedAt = LocalDateTime.now();

    @Setter
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Builder
    public Member(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
