package com.nextech.erp.util;

import java.io.File;
import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;

import com.nextech.erp.constants.UploadImageConstants;
import com.nextech.erp.dto.FileInfo;

public class ImageUploadUtil {
	
	public static String imgaeUpload(MultipartFile inputFile) {
		FileInfo fileInfo = new FileInfo();
		String destFile = "";
		HttpHeaders headers = new HttpHeaders();
		String originalFilename = inputFile.getOriginalFilename();
		String fileName = UploadImageConstants.UPLOAD_IMAGE_PATH;
		File destinationFile = new File((fileName)+ File.separator + originalFilename);
		try {
			inputFile.transferTo(destinationFile);
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fileInfo.setFileName(destinationFile.getPath());
		fileInfo.setFileSize(inputFile.getSize());
		headers.add("File Uploaded Successfully - ", originalFilename);
		destFile = String.valueOf(destinationFile);
		return destFile;
	}

}
