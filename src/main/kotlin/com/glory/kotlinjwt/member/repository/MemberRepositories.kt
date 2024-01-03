package com.glory.kotlinjwt.member.repository

import com.glory.kotlinjwt.member.entity.Member
import com.glory.kotlinjwt.member.entity.MemberRole
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByLoginId(loginId: String): Member?
}

interface MemberRoleRepository : JpaRepository<MemberRole, Long>
