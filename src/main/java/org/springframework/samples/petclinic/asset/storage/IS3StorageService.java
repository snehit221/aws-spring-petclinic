package org.springframework.samples.petclinic.asset.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IS3StorageService {

	String uploadFile(MultipartFile file, String bucketName) throws IOException;

}
