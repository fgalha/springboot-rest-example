package br.com.fgalha.pocs.admintool.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.fgalha.pocs.admintool.controller.dto.DirectoryItem;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class FileBrowserService {

	public List<DirectoryItem> listAll(String directory) {
		try {
			Path dir = Paths.get(directory);
			if (Files.notExists(dir)) {
				throw new IllegalArgumentException("Diretorio " + directory + " nao existe.");
			}
			return Files.list(dir).map(i -> (new DirectoryItem(i.getFileName().toString(),i.getParent().toString(), Files.isDirectory(i)))).collect(Collectors.toList());
		} catch (IOException e) {
			log.error("Erro ao ler diretorio: " + directory, e);
			throw new RuntimeException("Erro ao ler diretorio: " + directory);
		}
	}
}
