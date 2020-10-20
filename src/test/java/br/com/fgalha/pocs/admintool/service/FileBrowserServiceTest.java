package br.com.fgalha.pocs.admintool.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.fgalha.pocs.admintool.controller.dto.DirectoryItem;

public class FileBrowserServiceTest {

	private FileBrowserService service = new FileBrowserService();
	
	@Test
	public void listAllTest() {
		List<DirectoryItem> items = service.listAll("/");
//		items.forEach(System.out::println);
		Assertions.assertTrue(!items.isEmpty());
	}
	
}
