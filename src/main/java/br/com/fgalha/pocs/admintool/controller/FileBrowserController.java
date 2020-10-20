package br.com.fgalha.pocs.admintool.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fgalha.pocs.admintool.controller.dto.DirectoryItem;
import br.com.fgalha.pocs.admintool.service.FileBrowserService;

@RestController
@RequestMapping("/file-browser")
public class FileBrowserController {

	@Autowired
	private FileBrowserService fileBrowserService;
	
    @GetMapping("/all")
    public ResponseEntity<List<DirectoryItem>> listDirectores(@RequestBody String directory) {
    	return ResponseEntity.ok(fileBrowserService.listAll(directory));
    }

	
}
