package com.sv.springboot.jpa.bill.app.models.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileServiceImpl implements IUploadFileService {

	private Logger log = LoggerFactory.getLogger(getClass()); // Show in console

	private final static String UPLOADS_FOLDER = "uploads";
	
	@Override
	public Resource load(String filename) throws MalformedURLException {
		// TODO Auto-generated method stub
		Path pathFoto = getPath(filename);
		this.log.info("pathFoto: " + pathFoto.toString());
		Resource recurso = null;

		recurso = new UrlResource(pathFoto.toUri());
		if (!recurso.exists() || !recurso.isReadable()) {
			throw new RuntimeException("Error: No se puede cargar la imagen: " + pathFoto.toString());
		}

		return recurso;
	}

	@Override
	public String copy(MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		
		Path rootPath = getPath(uniqueFileName);
		this.log.info("rootPath: " + rootPath.toString());
			
			Files.copy(file.getInputStream(),rootPath);
		
		return uniqueFileName;
	}

	@Override
	public boolean delete(String filename) {
		// TODO Auto-generated method stub
		Path rootPath = getPath(filename);
		File archivo = rootPath.toFile();

		if (archivo.exists() && archivo.canRead()) {
			if (archivo.delete()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Path getPath(String filename) {
		// TODO Auto-generated method stub
		return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toFile());
	}

	@Override
	public void init() throws IOException {
		// TODO Auto-generated method stub
		Files.createDirectory(Paths.get(UPLOADS_FOLDER));
	}

}
