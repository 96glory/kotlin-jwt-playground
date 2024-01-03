package com.glory.kotlinjwt.member.service

import com.glory.kotlinjwt.common.authority.JwtTokenProvider
import com.glory.kotlinjwt.common.authority.TokenInfo
import com.glory.kotlinjwt.common.exception.InvalidInputException
import com.glory.kotlinjwt.common.status.Role
import com.glory.kotlinjwt.member.dto.LoginDto
import com.glory.kotlinjwt.member.dto.MemberDtoRequest
import com.glory.kotlinjwt.member.dto.MemberDtoResponse
import com.glory.kotlinjwt.member.entity.Member
import com.glory.kotlinjwt.member.entity.MemberRole
import com.glory.kotlinjwt.member.repository.MemberRepository
import com.glory.kotlinjwt.member.repository.MemberRoleRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.stereotype.Service

@Transactional
@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtTokenProvider: JwtTokenProvider
) {

    fun signUp(memberDtoRequest: MemberDtoRequest): String {
        // ID 중복 검사
        var member: Member? = memberRepository.findByLoginId(memberDtoRequest.loginId)
        if (member != null) {
            throw InvalidInputException("loginId", "이미 등록된 ID입니다.")
        }

        member = memberDtoRequest.toEntity()

        memberRepository.save(member)

        val memberRole: MemberRole = MemberRole(null, Role.MEMBER, member)
        memberRoleRepository.save(memberRole)

        return "회원가입이 완료되었습니다."
    }

    fun login(loginDto: LoginDto): TokenInfo {
        val authenticationToken =
            UsernamePasswordAuthenticationToken(loginDto.loginId, loginDto.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)

        return jwtTokenProvider.createToken(authentication)
    }

    fun searchMyInfo(id: Long): MemberDtoResponse {
        val member: Member = memberRepository.findByIdOrNull(id) ?: throw InvalidInputException(
            "id",
            "회원번호(${id})가 존재하지 않는 유저입니다."
        )

        return member.toDto()
    }

    fun saveMyInfo(memberDtoRequest: MemberDtoRequest): String {
        val member: Member = memberDtoRequest.toEntity()
        memberRepository.save(member)
        return "수정 완료되었습니다."
    }
}
