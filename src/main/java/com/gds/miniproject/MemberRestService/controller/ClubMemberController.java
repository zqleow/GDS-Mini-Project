package com.gds.miniproject.MemberRestService.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.gds.miniproject.MemberRestService.service.ClubMemberService;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

@RestController
public class ClubMemberController {

	@Autowired
	ClubMemberService service;
	
	private static final Logger Logger = LoggerFactory.getLogger(ClubMemberController.class);


	@GetMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GetResponse> getUsersByParams(
			@RequestParam(value = "min", defaultValue = "0.0", required = false) String min,
			@RequestParam(value = "max", defaultValue = "4000.0", required = false) String max,
			@RequestParam(value = "offset", defaultValue = "0", required = false) String offset,
			@RequestParam(value = "limit", defaultValue = "0", required = false) String limit,
			@RequestParam(value = "sort", defaultValue = "No", required = false) String sort) {
		SearchParams params = new SearchParams();
		try {
			params.setMin(Double.parseDouble(min));
			params.setMax(Double.parseDouble(max));
			params.setOffset(Integer.parseInt(offset));
			params.setLimit(Integer.parseInt(limit));
			params.setSort(sort);
		} catch (Exception e) {
			Logger.error("Error with setting values of input parameters.");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "error: Error processing input parameters.", e);
		}
		try {
			List<DisplayClubMember> resultList = service.queryBasedOnParams(params);
			GetResponse response = new GetResponse();
			response.setResults(resultList);
			Logger.info("Querying of database successful");
			return new ResponseEntity<GetResponse>(response, HttpStatus.OK);
		} catch (Exception e) {
			Logger.error("Error querying Database.");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "error: Error querying database.", e);
		}
	}

	@PostMapping(path = "/upload", consumes = { "multipart/form-data" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> uploadCSVFile(@RequestParam("file") MultipartFile file) {
		ErrorResponse error = new ErrorResponse();
		List<ClubMember> memList = new ArrayList<>();
		if (file.isEmpty()) {
			Logger.error("No CSV file uploaded");
			error.setError("Please select a CSV file to upload");
			return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
		} else {
			Reader reader;
			List<String[]> list = new ArrayList<>();
			try {
				reader = new InputStreamReader(file.getInputStream());
				CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
				list = csvReader.readAll();
				reader.close();
				Logger.info("Successfully read CSV file");
			} catch (IOException e) {
				e.printStackTrace();
				Logger.error("IOException encountered while trying to read CSV file");
				error.setError("Error with uploading of CSV file.");
				return new ResponseEntity<ErrorResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
			} catch (CsvException e) {
				e.printStackTrace();
				Logger.error("CsvException encountered while trying to read CSV file");
				error.setError("Error with CSV file.");
				return new ResponseEntity<ErrorResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			for (String[] strArray : list) {
				if (strArray.length > 2) {
					Logger.info("CSV file rejected because incorrect number of columns");
					error.setError("CSV file rejected because incorrect number of columns");
					return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_ACCEPTABLE);
				}
				if (!strArray[0].isEmpty() && strArray[0] != null && !NumberUtils.isParsable(strArray[0])) {
					String memberName = strArray[0];
					if (!strArray[1].isEmpty() && strArray[1] != null && NumberUtils.isParsable(strArray[1])) {
						double memberSalary = Double.parseDouble(strArray[1]);
						if (memberSalary < 0) {
							memberSalary = 0;
						}
						ClubMember member = new ClubMember();
						member.setName(memberName);
						member.setSalary(memberSalary);
						memList.add(member);
					}
				} else {
					Logger.info("CSV file rejected because Name/Salary of user is invalid.");
					error.setError("CSV file rejected because Name/Salary of user is invalid.");
					return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_ACCEPTABLE);
				}
			}

			Boolean result = service.addUserToDB(memList);
			SuccessResponse response = new SuccessResponse();
			if (result) {
				response.setSuccess(1);
				Logger.info("Entries in csv file successfully inserted to database");
				return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
			} else {
				response.setSuccess(0);
				Logger.info("No entries in csv file inserted to database");
				return new ResponseEntity<SuccessResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

		}
	}

}
