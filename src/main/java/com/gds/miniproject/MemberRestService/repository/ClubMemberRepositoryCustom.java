package com.gds.miniproject.MemberRestService.repository;

import java.util.List;

import com.gds.miniproject.MemberRestService.controller.ClubMember;
import com.gds.miniproject.MemberRestService.controller.DisplayClubMember;
import com.gds.miniproject.MemberRestService.controller.SearchParams;

public interface ClubMemberRepositoryCustom {

	List<DisplayClubMember> findAllByParams(SearchParams params);

	boolean insertToDB(List<ClubMember> memList);

}
