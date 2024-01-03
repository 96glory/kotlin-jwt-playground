package com.glory.kotlinjwt.member.repository

import com.glory.kotlinjwt.member.entity.MemberRole
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRoleRepository : JpaRepository<MemberRole, Long>
