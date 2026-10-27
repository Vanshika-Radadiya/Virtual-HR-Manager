package com.vanshika.springboot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

class RecruiterServiceTest {
	private final RecruiterService recruiterService =
			new RecruiterService(null, null, null, null, null, null, "http://localhost:4200");

	@Test
	void readsAndDeduplicatesCsvEmails() throws Exception {
		MockMultipartFile file = new MockMultipartFile("file", "candidates.csv", "text/csv",
				"email_id\r\nperson@example.com\r\nPERSON@example.com\r\n".getBytes(StandardCharsets.UTF_8));

		assertEquals(List.of("person@example.com"), recruiterService.readCandidateEmails(file));
	}

	@Test
	void readsSpreadsheetEmailsAndSkipsHeader() throws Exception {
		byte[] content;
		try (XSSFWorkbook workbook = new XSSFWorkbook();
				ByteArrayOutputStream output = new ByteArrayOutputStream()) {
			var sheet = workbook.createSheet();
			sheet.createRow(0).createCell(0).setCellValue("Email ID");
			sheet.createRow(1).createCell(0).setCellValue("person@example.com");
			workbook.write(output);
			content = output.toByteArray();
		}
		MockMultipartFile file = new MockMultipartFile("file", "candidates.xlsx",
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", content);

		assertEquals(List.of("person@example.com"), recruiterService.readCandidateEmails(file));
	}

	@Test
	void rejectsInvalidEmailsInUploadedFiles() {
		MockMultipartFile file = new MockMultipartFile("file", "candidates.csv", "text/csv",
				"not-an-email".getBytes(StandardCharsets.UTF_8));

		assertThrows(IllegalArgumentException.class, () -> recruiterService.readCandidateEmails(file));
	}
}
