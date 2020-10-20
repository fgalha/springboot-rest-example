package br.com.fgalha.pocs.admintool.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectoryItem {

	private String name;
	private String parent;
	private boolean directory;
	
}
