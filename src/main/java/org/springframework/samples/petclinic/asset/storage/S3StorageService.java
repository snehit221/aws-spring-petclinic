package org.springframework.samples.petclinic.asset.storage;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class S3StorageService implements IS3StorageService {

	private S3Client s3Client;

	private static final String s3_storage_prefix = "https://spring-pet-assets.s3.us-east-1.amazonaws.com/";

	public static final String image_prefix = "images/";

	@Autowired
	public S3StorageService(S3Client s3Client) {
		this.s3Client = s3Client;
	}

	/**
	 * @param file
	 * @param bucketName
	 * @return the to be uploaded image url for s3
	 * @throws IOException
	 */
	@Override
	public String uploadFile(MultipartFile file, String bucketName) throws IOException {
		String key = image_prefix + System.currentTimeMillis() + file.getOriginalFilename();
		System.out.println("generated key for s3 object: " + key);
		PutObjectRequest putObjectRequest = PutObjectRequest.builder()
			.bucket(bucketName)
			.key(key)
			.contentType(file.getContentType())
			.build();

		s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

		return s3_storage_prefix + key;
	}

}
