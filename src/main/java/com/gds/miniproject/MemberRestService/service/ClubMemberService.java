package com.gds.miniproject.MemberRestService.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gds.miniproject.MemberRestService.controller.ClubMember;
import com.gds.miniproject.MemberRestService.controller.DisplayClubMember;
import com.gds.miniproject.MemberRestService.controller.SearchParams;
import com.gds.miniproject.MemberRestService.repository.ClubMemberRepositoryCustom;

@Service
public class ClubMemberService {

	@Autowired
	ClubMemberRepositoryCustom clubMemRepoCustom;
	
	ClubMember clubMem = new ClubMember();
	

	public List<DisplayClubMember> queryBasedOnParams(SearchParams params) {
		List<DisplayClubMember> memList = clubMemRepoCustom.findAllByParams(params);
		return memList;
	}
	
	public boolean addUserToDB(List<ClubMember> memList) {
		boolean insertToDBSuccessful = clubMemRepoCustom.insertToDB(memList);
		return insertToDBSuccessful;
		
	}

}
