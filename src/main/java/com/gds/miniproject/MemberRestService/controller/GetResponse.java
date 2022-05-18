package com.gds.miniproject.MemberRestService.controller;

import java.util.List;

public class GetResponse {

	private List<DisplayClubMember> results;

	public List<DisplayClubMember> getResults() {
		return results;
	}

	public void setResults(List<DisplayClubMember> results) {
		this.results = results;
	}
}
