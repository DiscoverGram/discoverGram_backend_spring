package com.ssafy.enjoytrip.global.auth;

import com.ssafy.enjoytrip.global.error.CommonErrorCode;
import com.ssafy.enjoytrip.error.exception.NotFoundUserException;
import com.ssafy.enjoytrip.domain.member.domain.Member;
import com.ssafy.enjoytrip.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepository.findById(username).orElseThrow(() -> new NotFoundUserException(CommonErrorCode.NOT_FOUND_USER));

		return new PrincipalDetails(member);
	}
}
