package com.gds.miniproject.MemberRestService.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.gds.miniproject.MemberRestService.controller.ClubMember;
import com.gds.miniproject.MemberRestService.controller.DisplayClubMember;
import com.gds.miniproject.MemberRestService.controller.SearchParams;

@Component
public class ClubMemberRepositoryDBImpl implements ClubMemberRepositoryCustom {

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	ClubMemberRepository memRepo;

	@Override
	public List<DisplayClubMember> findAllByParams(SearchParams params) {
		Query query = new Query();
		query.fields().exclude("id");
		query.addCriteria(Criteria.where("salary").lt(params.getMax()).gt(params.getMin()));
		if (params.getLimit() != 0 || params.getOffset() != 0) {
			final Pageable pageableRequest = PageRequest.of(params.getOffset(), params.getLimit());
			query.with(pageableRequest);
		} else {
			if (params.getSort() == "NAME") {
				query.with(Sort.by(Sort.Direction.ASC, "name"));
			} else if (params.getSort() == "SALARY") {
				query.with(Sort.by(Sort.Direction.ASC, "salary"));
			}
		}
		try {
			List<DisplayClubMember> memList = mongoTemplate.find(query, DisplayClubMember.class);
			return memList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public boolean insertToDB(List<ClubMember> memList) {
		try {
			for (ClubMember clubMem : memList) {
				Query query = new Query();
				query.addCriteria(Criteria.where("name").is(clubMem.getName()));
				ClubMember updateMem = mongoTemplate.findOne(query, ClubMember.class);
				if (updateMem != null) {
					updateMem.setSalary(clubMem.getSalary());
					memRepo.save(updateMem);
				} else {
					memRepo.save(clubMem);
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
