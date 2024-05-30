package org.zerock.safefast.service.login;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zerock.safefast.entity.Member;
import org.zerock.safefast.repository.MemberRepository;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String empNumber) throws UsernameNotFoundException {
        Member member = Optional.ofNullable(memberRepository.findByEmpNumber(empNumber))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with empNumber: " + empNumber));
        return new CustomUserDetails(member);
    }

}
