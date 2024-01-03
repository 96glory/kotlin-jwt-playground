package com.glory.kotlinjwt.member.controller

import com.glory.kotlinjwt.common.authority.TokenInfo
import com.glory.kotlinjwt.common.dto.BaseResponse
import com.glory.kotlinjwt.common.dto.CustomUser
import com.glory.kotlinjwt.member.dto.LoginDto
import com.glory.kotlinjwt.member.dto.MemberDtoRequest
import com.glory.kotlinjwt.member.dto.MemberDtoResponse
import com.glory.kotlinjwt.member.service.MemberService
import jakarta.validation.Valid
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/member")
@RestController
class MemberController(
    private val memberService: MemberService
) {

    @PostMapping("/signup")
    fun signUp(@RequestBody @Valid memberDtoRequest: MemberDtoRequest): BaseResponse<Unit> {
        val resultMsg: String = memberService.signUp(memberDtoRequest)
        return BaseResponse(message = resultMsg)
    }

    @PostMapping("/login")
    fun login(@RequestBody @Valid loginDto: LoginDto): BaseResponse<TokenInfo> {
        val tokenInfo = memberService.login(loginDto)
        return BaseResponse(data = tokenInfo)
    }

    @GetMapping("/info")
    fun searchMyInfo(): BaseResponse<MemberDtoResponse> {
        val userId =
            (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        val response = memberService.searchMyInfo(userId)
        return BaseResponse(data = response)
    }

    @PutMapping("/info")
    fun saveMyInfo(@RequestBody @Valid memberDtoRequest: MemberDtoRequest): BaseResponse<Unit> {
        val userId =
            (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        memberDtoRequest.id = userId

        val resultMsg = memberService.saveMyInfo(memberDtoRequest)
        return BaseResponse(message = resultMsg)
    }
}
