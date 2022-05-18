package com.gds.miniproject.MemberRestService.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.gds.miniproject.MemberRestService.controller.ClubMember;

@Repository
public interface ClubMemberRepository extends MongoRepository<ClubMember, String>{

	
}
