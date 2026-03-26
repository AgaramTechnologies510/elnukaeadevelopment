package com.agaram.eln.primary.service.cloudFileManip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.agaram.eln.primary.commonfunction.commonfunction;
import com.agaram.eln.primary.config.TenantContext;
import com.agaram.eln.primary.model.cfr.LScfttransaction;
import com.agaram.eln.primary.model.cloudFileManip.CloudOrderAttachment;
import com.agaram.eln.primary.model.cloudFileManip.CloudProfilePicture;
import com.agaram.eln.primary.model.cloudFileManip.CloudUserSignature;
import com.agaram.eln.primary.model.instrumentDetails.LsOrderattachments;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;
import com.agaram.eln.primary.repository.cfr.LScfttransactionRepository;
import com.agaram.eln.primary.repository.cloudFileManip.CloudOrderAttachmentRepository;
import com.agaram.eln.primary.repository.cloudFileManip.CloudProfilePictureRepository;
import com.agaram.eln.primary.repository.cloudFileManip.CloudUserSignatureRepository;
import com.agaram.eln.primary.repository.usermanagement.LSuserMasterRepository;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.OperationContext;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.BlobContainerPublicAccessType;
import com.microsoft.azure.storage.blob.BlobRequestOptions;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import io.minio.BucketExistsArgs;
import io.minio.CopyObjectArgs;
import io.minio.CopySource;
import io.minio.GetObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.MinioException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.http.Method;

@Service
@Transactional
public class CloudFileManipulationservice {

	@Autowired
	private CloudOrderAttachmentRepository cloudOrderAttachmentRepository;

	@Autowired
	private CloudProfilePictureRepository cloudProfilePictureRepository;

	@Autowired
	private LScfttransactionRepository lscfttransactionRepository;

	@Autowired
	private LSuserMasterRepository lsuserMasterRepository;

	@Autowired
	private CloudUserSignatureRepository cloudusersignatureRepository;

	@Autowired
	private Environment env;

	public CloudProfilePicture addPhoto(Integer usercode, MultipartFile file, Date currentdate) throws IOException {

		LSuserMaster username = lsuserMasterRepository.findByusercode(usercode);
		String name = username.getUsername();
		LScfttransaction list = new LScfttransaction();
		list.setModuleName("UserManagement");
		list.setComments(name + " " + "Uploaded the profile picture successfully");
		list.setActions("View / Load");
		list.setSystemcoments("System Generated");
		list.setTableName("profile");
//		list.setTransactiondate(currentdate);
		list.setLsuserMaster(usercode);
		try {
			list.setTransactiondate(commonfunction.getCurrentUtcTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lscfttransactionRepository.save(list);
		deletePhoto(usercode, list);

		CloudProfilePicture profile = new CloudProfilePicture();
		profile.setId(usercode);
		profile.setName(file.getName());
		profile.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
		profile = cloudProfilePictureRepository.save(profile);

		return profile;
	}

	public CloudUserSignature addusersignature(Integer usercode, String username, MultipartFile file, Date currentdate)
			throws IOException {
		CloudUserSignature usersignature = new CloudUserSignature();
		usersignature.setId(usercode);
		usersignature.setName(file.getName());
		usersignature.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
		usersignature = cloudusersignatureRepository.save(usersignature);
		return usersignature;
	}

	public CloudProfilePicture getPhoto(Integer id) {

		return cloudProfilePictureRepository.findTop1ById(id);
	}

	public CloudUserSignature getSignature(Integer id) {

		return cloudusersignatureRepository.findTop1ById(id);
	}

	public void deletePhoto(Integer id, LScfttransaction list) {
		list.setTableName("ProfilePicture");
		try {
			list.setTransactiondate(commonfunction.getCurrentUtcTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lscfttransactionRepository.save(list);
		cloudProfilePictureRepository.deleteById(id);
	}

	public CloudOrderAttachment storeattachment(MultipartFile file) throws IOException {
		UUID objGUID = UUID.randomUUID();
		String randomUUIDString = objGUID.toString();

		CloudOrderAttachment objattachment = new CloudOrderAttachment();
		objattachment.setFile(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
		objattachment.setFileid(randomUUIDString);

		objattachment = cloudOrderAttachmentRepository.save(objattachment);

		return objattachment;
	}

	public String storeLargeattachment(String title, MultipartFile file) throws IOException, InvalidKeyException, NoSuchAlgorithmException, IllegalArgumentException {

		if(env.getProperty("minio.url") != null)
		{
			return storeLargeattachmentminio(title, file);
		}
		else
		{
			return storeLargeattachmentazure(title, file);
		}
	}
	
	public String storeLargeattachmentazure(String title, MultipartFile file) throws IOException {
		String bloburi = "";
		CloudStorageAccount storageAccount;
		CloudBlobClient blobClient = null;
		CloudBlobContainer container = null;
		String storageConnectionString = env.getProperty("azure.storage.ConnectionString");

		UUID objGUID = UUID.randomUUID();
		String randomUUIDString = objGUID.toString();

		try {
			// Parse the connection string and create a blob client to interact with Blob
			// storage
			storageAccount = CloudStorageAccount.parse(storageConnectionString);
			blobClient = storageAccount.createCloudBlobClient();
			container = blobClient.getContainerReference(TenantContext.getCurrentTenant());

			// Create the container if it does not exist with public access.
			System.out.println("Creating container: " + container.getName());
			container.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(),
					new OperationContext());

			File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + randomUUIDString);
			file.transferTo(convFile);

			// Getting a blob reference
			CloudBlockBlob blob = container.getBlockBlobReference(convFile.getName());

			// Creating blob and uploading file to it
			System.out.println("Uploading the sample file ");
			blob.uploadFromFile(convFile.getAbsolutePath());

			bloburi = blob.getName();

		} catch (StorageException ex) {
			System.out.println(String.format("Error returned from the service. Http code: %d and error code: %s",
					ex.getHttpStatusCode(), ex.getErrorCode()));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			System.out.println("The program has completed successfully.");
			System.out.println(
					"Press the 'Enter' key while in the console to delete the sample files, example container, and exit the application.");

		}

		return bloburi; // id.toString();
	}
	
	public String storeLargeattachmentminio(String title, MultipartFile file) throws IOException, InvalidKeyException, NoSuchAlgorithmException, IllegalArgumentException {
		
		UUID objGUID = UUID.randomUUID();
		String randomUUIDString = objGUID.toString();
		try {
		      
		      MinioClient minioClient =
		          MinioClient.builder()
		              .endpoint(env.getProperty("minio.url"))
		              .credentials(env.getProperty("minio.accessKey"), env.getProperty("minio.secretKey"))
		              .build();

		      
		      boolean found =
		          minioClient.bucketExists(BucketExistsArgs.builder().bucket(TenantContext.getCurrentTenant()).build());
		      if (!found) {
		        
		        minioClient.makeBucket(MakeBucketArgs.builder().bucket(TenantContext.getCurrentTenant()).build());
		      } else {
		        System.out.println("Bucket already exists.");
		      }
//		      File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + randomUUIDString);
//			  file.transferTo(convFile);
//			
//			PutObjectArgs putObjectArgs = PutObjectArgs.builder()
//			           .bucket(TenantContext.getCurrentTenant())
//			           .object(randomUUIDString) // Name of the object in the bucket
//			           .stream(file.getInputStream(), file.getInputStream().available(), -1) // Pass the input stream, size, and part size (set to -1 for auto-calculation)
//			           .contentType(file.getContentType()) // Set the content type
//			           .build();
//			minioClient.putObject(putObjectArgs);
		      
		      PutObjectArgs putObjectArgs = PutObjectArgs.builder()
			           .bucket(TenantContext.getCurrentTenant())
			           .object(randomUUIDString) // Name of the object in the bucket
			           .stream(file.getInputStream(), file.getInputStream().available(), -1) // Pass the input stream, size, and part size (set to -1 for auto-calculation)
			           .contentType(file.getContentType()) // Set the content type
			           .build();
			minioClient.putObject(putObjectArgs);
		      
		     
		    } catch (MinioException e) {
		      System.out.println("Error occurred: " + e);
		      System.out.println("HTTP trace: " + e.httpTrace());
		    }
		
		return randomUUIDString;
	}
	
	public String storeFileWithTags(String title, MultipartFile file, String tagName) throws IOException {
		if(env.getProperty("minio.url") != null)
		{
			try {
				return storeFileWithTagsminio(title, file,tagName);
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		else
		{
			return storeFileWithTagsazure(title, file, tagName);
		}
	}

	public String storeFileWithTagsazure(String title, MultipartFile file, String tagName) throws IOException {

		String bloburi = "";
		CloudStorageAccount storageAccount;
		CloudBlobClient blobClient = null;
		CloudBlobContainer container = null;
		String storageConnectionString = env.getProperty("azure.storage.ConnectionString");

		UUID objGUID = UUID.randomUUID();
		String randomUUIDString = objGUID.toString();

		try {
			// Parse the connection string and create a blob client to interact with Blob
			// storage
			storageAccount = CloudStorageAccount.parse(storageConnectionString);
			blobClient = storageAccount.createCloudBlobClient();
			container = blobClient.getContainerReference(TenantContext.getCurrentTenant());

			// Create the container if it does not exist with public access.
			System.out.println("Creating container: " + container.getName());
			container.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(),
					new OperationContext());

			File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + randomUUIDString);
			file.transferTo(convFile);

			// Getting a blob reference
			CloudBlockBlob blob = container.getBlockBlobReference(convFile.getName());

			Map<String, Object> metadata = new HashMap<>();
			metadata.put(tagName, convFile);
//			metadata.put(tagName, "value1");

			// Convert metadata HashMap to Map<String, String>
			HashMap<String, String> metadataString = new HashMap<>();
			for (Map.Entry<String, Object> entry : metadata.entrySet()) {
				metadataString.put(entry.getKey(), entry.getValue().toString());
			}

			blob.setMetadata(metadataString);

			blob.uploadFromFile(convFile.getAbsolutePath());

			bloburi = blob.getName();

		} catch (StorageException ex) {
			System.out.println(String.format("Error returned from the service. Http code: %d and error code: %s",
					ex.getHttpStatusCode(), ex.getErrorCode()));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			System.out.println("The program has completed successfully.");
			System.out.println(
					"Press the 'Enter' key while in the console to delete the sample files, example container, and exit the application.");
		}

		return bloburi; // id.toString();
	}
	
	public String storeFileWithTagsminio(String title, MultipartFile file, String tagName) throws IOException, InvalidKeyException, NoSuchAlgorithmException, IllegalArgumentException {
		UUID objGUID = UUID.randomUUID();
		String randomUUIDString = objGUID.toString();
		try {
		      
		      MinioClient minioClient =
		          MinioClient.builder()
		              .endpoint(env.getProperty("minio.url"))
		              .credentials(env.getProperty("minio.accessKey"), env.getProperty("minio.secretKey"))
		              .build();

		      
		      boolean found =
		          minioClient.bucketExists(BucketExistsArgs.builder().bucket(TenantContext.getCurrentTenant()).build());
		      if (!found) {
		        minioClient.makeBucket(MakeBucketArgs.builder().bucket(TenantContext.getCurrentTenant()).build());
		      } else {
		        System.out.println("Bucket already exists.");
		      }
		      File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + randomUUIDString);
			  file.transferTo(convFile);
			  
			  InputStream stream =
		    		     minioClient.getObject(
		    		   GetObjectArgs.builder()
		    		     .bucket(TenantContext.getCurrentTenant())
		    		     .object(convFile.getName())
		    		     .build());
			
				Map<String, Object> metadata = new HashMap<>();
				metadata.put(tagName, convFile);

				HashMap<String, String> metadataString = new HashMap<>();
				for (Map.Entry<String, Object> entry : metadata.entrySet()) {
					metadataString.put(entry.getKey(), entry.getValue().toString());
				}

			PutObjectArgs putObjectArgs = PutObjectArgs.builder()
			           .bucket(TenantContext.getCurrentTenant())
			           .object(randomUUIDString) // Name of the object in the bucket
			           .stream(stream, stream.available(), -1) // Pass the input stream, size, and part size (set to -1 for auto-calculation)
			           .contentType(file.getContentType()) // Set the content type
			           .headers(metadataString)
			           .build();
			minioClient.putObject(putObjectArgs);
		      
		     
		    } catch (MinioException e) {
		      System.out.println("Error occurred: " + e);
		      System.out.println("HTTP trace: " + e.httpTrace());
		    }
		
		return randomUUIDString;
	}

	public CloudOrderAttachment retrieveFile(LsOrderattachments objattachment) {

		CloudOrderAttachment objfile = cloudOrderAttachmentRepository.findByFileid(objattachment.getFileid());

		if (objfile == null) {
			cloudOrderAttachmentRepository.findById(Integer.parseInt(objattachment.getFileid()));
		}

		return objfile;
	}
	
	public InputStream retrieveLargeFile(String fileid) throws IOException {
		if(env.getProperty("minio.url") != null)
		{
			try {
				return retrieveLargeFileminio(fileid);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		else
		{
			return retrieveLargeFileazure(fileid);
		}
	}

	public InputStream retrieveLargeFileazure(String fileid) throws IOException {

		CloudStorageAccount storageAccount;
		CloudBlobClient blobClient = null;
		CloudBlobContainer container = null;
		CloudBlockBlob blob = null;
		String storageConnectionString = env.getProperty("azure.storage.ConnectionString");
		try {
			// Parse the connection string and create a blob client to interact with Blob
			// storage
			storageAccount = CloudStorageAccount.parse(storageConnectionString);
			blobClient = storageAccount.createCloudBlobClient();
			container = blobClient.getContainerReference(TenantContext.getCurrentTenant());

			blob = container.getBlockBlobReference(fileid);
			return blob.openInputStream();
		} catch (StorageException ex) {
			System.out.println(String.format("Error returned from the service. Http code: %d and error code: %s",
					ex.getHttpStatusCode(), ex.getErrorCode()));
			throw new IOException(String.format("Error returned from the service. Http code: %d and error code: %s",
					ex.getHttpStatusCode(), ex.getErrorCode()));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return null;

	}
	
	public InputStream retrieveLargeFileminio(String fileid) throws IOException {

		MinioClient minioClient =
		          MinioClient.builder()
		              .endpoint(env.getProperty("minio.url"))
		              .credentials(env.getProperty("minio.accessKey"), env.getProperty("minio.secretKey"))
		              .build();
		     
		     
		      
		      InputStream stream;
			try {
				stream = minioClient.getObject(
 		   GetObjectArgs.builder()
				 .bucket(TenantContext.getCurrentTenant())
				 .object(fileid)
//		    		     .offset(offset)
//		    		     .length(len)
//		    		     .ssec(ssec)
				 .build());
				return stream;
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ErrorResponseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InsufficientDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InternalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidResponseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      return null;
		     
	}

	public Long deleteattachments(String id) {
		Long count = cloudOrderAttachmentRepository.deleteByFileid(id);
		if (count <= 0) {
			count = cloudOrderAttachmentRepository.deleteById(Integer.parseInt(id));
		}
		return count;
	}

	public void deletelargeattachments(String id) {
		if(env.getProperty("minio.url") != null)
		{
			try {
				 deletelargeattachmentsminio(id);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			deletelargeattachmentsazure(id);
		}
	}
	
	public void deletelargeattachmentsazure(String id) {
		CloudStorageAccount storageAccount;
		CloudBlobClient blobClient = null;
		CloudBlobContainer container = null;
		CloudBlockBlob blob = null;
		String storageConnectionString = env.getProperty("azure.storage.ConnectionString");
		try {
			// Parse the connection string and create a blob client to interact with Blob
			// storage
			storageAccount = CloudStorageAccount.parse(storageConnectionString);
			blobClient = storageAccount.createCloudBlobClient();
			container = blobClient.getContainerReference(TenantContext.getCurrentTenant());

			blob = container.getBlockBlobReference(id);
			blob.deleteIfExists();
		} catch (StorageException ex) {
			System.out.println(String.format("Error returned from the service. Http code: %d and error code: %s",
					ex.getHttpStatusCode(), ex.getErrorCode()));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void deletelargeattachmentsminio(String id) {
		 MinioClient minioClient =
		          MinioClient.builder()
		              .endpoint(env.getProperty("minio.url"))
		              .credentials(env.getProperty("minio.accessKey"), env.getProperty("minio.secretKey"))
		              .build();
		 try {
			minioClient.removeObject(
			            RemoveObjectArgs.builder()
			                .bucket(TenantContext.getCurrentTenant())
			                .object(id)
			                .build());
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErrorResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InsufficientDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String storeReportFile(String title, File file) throws IOException {
		if(env.getProperty("minio.url") != null)
		{
			
				try {
					return storeReportFileminio(title,file);
				} catch (InvalidKeyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				} catch (ErrorResponseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				} catch (InsufficientDataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				} catch (InternalException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				} catch (InvalidResponseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				} catch (ServerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				} catch (XmlParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			
		}
		else
		{
			return storeReportFileazure(title,file);
		}
	}
	
	public String storeReportFileazure(String title, File file) throws IOException {

		String bloburi = "";
		CloudStorageAccount storageAccount;
		CloudBlobClient blobClient = null;
		CloudBlobContainer container = null;
		String storageConnectionString = env.getProperty("azure.storage.ConnectionString");

		try {
			// Parse the connection string and create a blob client to interact with Blob
			// storage
			storageAccount = CloudStorageAccount.parse(storageConnectionString);
			blobClient = storageAccount.createCloudBlobClient();
			container = blobClient.getContainerReference(TenantContext.getCurrentTenant());

			// Create the container if it does not exist with public access.
			System.out.println("Creating container: " + container.getName());
			container.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(),
					new OperationContext());

//			File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+title);
//			file.transferTo(convFile);

			// Getting a blob reference
			CloudBlockBlob blob = container.getBlockBlobReference(file.getName());

			// Creating blob and uploading file to it
			System.out.println("Uploading the sample file ");
			blob.uploadFromFile(file.getAbsolutePath());

			bloburi = blob.getName();

		} catch (StorageException ex) {
			System.out.println(String.format("Error returned from the service. Http code: %d and error code: %s",
					ex.getHttpStatusCode(), ex.getErrorCode()));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			System.out.println("The program has completed successfully.");
			System.out.println(
					"Press the 'Enter' key while in the console to delete the sample files, example container, and exit the application.");

		}

		return bloburi; // id.toString();
	}
	
	public String storeReportFileminio(String title, File file) throws IOException, InvalidKeyException, ErrorResponseException, 
	InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException {
		MinioClient minioClient =
		          MinioClient.builder()
		              .endpoint(env.getProperty("minio.url"))
		              .credentials(env.getProperty("minio.accessKey"), env.getProperty("minio.secretKey"))
		              .build();
		
		boolean found =
		          minioClient.bucketExists(BucketExistsArgs.builder().bucket(TenantContext.getCurrentTenant()).build());
		      if (!found) {
		        minioClient.makeBucket(MakeBucketArgs.builder().bucket(TenantContext.getCurrentTenant()).build());
		      } else {
		        System.out.println("Bucket already exists.");
		      }
		      InputStream stream = new FileInputStream(file);
		      String type = Files.probeContentType(file.toPath());
		PutObjectArgs putObjectArgs = PutObjectArgs.builder()
		           .bucket(TenantContext.getCurrentTenant())
		           .object(file.getName()) // Name of the object in the bucket
		           .stream(stream, stream.available(), -1) // Pass the input stream, size, and part size (set to -1 for auto-calculation)
		           .contentType(type) // Set the content type
		           .build();
		minioClient.putObject(putObjectArgs);
	      
		
		return file.getName();
	}
	
	public InputStream retrieveReportFiles(String fileid) throws IOException {
		if(env.getProperty("minio.url") != null)
		{
			return retrieveReportFilesminio(fileid);
		}
		else
		{
			return retrieveReportFilesazure(fileid);
		}
	}

	public InputStream retrieveReportFilesazure(String fileid) throws IOException {

		CloudStorageAccount storageAccount;
		CloudBlobClient blobClient = null;
		CloudBlobContainer container = null;
		CloudBlockBlob blob = null;
		String storageConnectionString = env.getProperty("azure.storage.ConnectionString");
		try {
			// Parse the connection string and create a blob client to interact with Blob
			// storage
			storageAccount = CloudStorageAccount.parse(storageConnectionString);
			blobClient = storageAccount.createCloudBlobClient();
			container = blobClient.getContainerReference(TenantContext.getCurrentTenant());

			blob = container.getBlockBlobReference(fileid);
			return blob.openInputStream();
		} catch (StorageException ex) {
			System.out.println(String.format("Error returned from the service. Http code: %d and error code: %s",
					ex.getHttpStatusCode(), ex.getErrorCode()));
			throw new IOException(String.format("Error returned from the service. Http code: %d and error code: %s",
					ex.getHttpStatusCode(), ex.getErrorCode()));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return null;

	}
	
	public InputStream retrieveReportFilesminio(String fileid) throws IOException {
		MinioClient minioClient =
		          MinioClient.builder()
		              .endpoint(env.getProperty("minio.url"))
		              .credentials(env.getProperty("minio.accessKey"), env.getProperty("minio.secretKey"))
		              .build();
		     
		     
		      
		      InputStream stream = null;
				try {
					stream = minioClient.getObject(
   GetObjectArgs.builder()
					 .bucket(TenantContext.getCurrentTenant())
					 .object(fileid)
//		    		     .offset(offset)
//		    		     .length(len)
//		    		     .ssec(ssec)
					 .build());
				} catch (InvalidKeyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ErrorResponseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InsufficientDataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InternalException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidResponseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ServerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (XmlParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return stream;
	}

	public void deleteReportFile(String id) {
		if(env.getProperty("minio.url") != null)
		{
			deleteReportFileminio(id);
		}
		else
		{
			deleteReportFileazure(id);
		}
	}
	
	public void deleteReportFileazure(String id) {
		CloudStorageAccount storageAccount;
		CloudBlobClient blobClient = null;
		CloudBlobContainer container = null;
		CloudBlockBlob blob = null;
		String storageConnectionString = env.getProperty("azure.storage.ConnectionString");
		try {
			// Parse the connection string and create a blob client to interact with Blob
			// storage
			storageAccount = CloudStorageAccount.parse(storageConnectionString);
			blobClient = storageAccount.createCloudBlobClient();
			container = blobClient.getContainerReference(TenantContext.getCurrentTenant());

			blob = container.getBlockBlobReference(id);
			blob.deleteIfExists();
		} catch (StorageException ex) {
			System.out.println(String.format("Error returned from the service. Http code: %d and error code: %s",
					ex.getHttpStatusCode(), ex.getErrorCode()));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void deleteReportFileminio(String id) {
		 MinioClient minioClient =
		          MinioClient.builder()
		              .endpoint(env.getProperty("minio.url"))
		              .credentials(env.getProperty("minio.accessKey"), env.getProperty("minio.secretKey"))
		              .build();
		 
			try {
				minioClient.removeObject(
				            RemoveObjectArgs.builder()
				                .bucket(TenantContext.getCurrentTenant())
				                .object(id)
				                .build());
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ErrorResponseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InsufficientDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InternalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidResponseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public String storecloudfilesreturnUUID(MultipartFile file, String containername) throws IOException, InvalidKeyException, NoSuchAlgorithmException, IllegalArgumentException {

		if(env.getProperty("minio.url") != null)
		{
			return storecloudfilesreturnUUIDminio(file, containername);
		}
		else
		{
			return storecloudfilesreturnUUIDazure(file, containername);
		}
	}
	
	public String storecloudfileswithUUID(MultipartFile file, String containername, String fileid) throws IOException, InvalidKeyException, NoSuchAlgorithmException, IllegalArgumentException {

		if(env.getProperty("minio.url") != null)
		{
			return storecloudfileswithUUIDminio(file, containername, fileid);
		}
		else
		{
			return storecloudfileswithUUIDazure(file, containername, fileid);
		}
	}
	
	public String storecloudfileswithUUIDazure(MultipartFile file, String containername, String fileid) throws IOException {

		System.out.print("entering storecloudfilesreturnUUID function");
		int multitenant = env.getProperty("app.datasource.eln.url").toLowerCase().contains("elntrail") == true ? 2 : -1;
		// String bloburi="";
		CloudStorageAccount storageAccount;
		CloudBlobClient blobClient = null;
		CloudBlobContainer container = null;
		String storageConnectionString = env.getProperty("azure.storage.ConnectionString");

		try {
			// Parse the connection string and create a blob client to interact with Blob
			// storage
			storageAccount = CloudStorageAccount.parse(storageConnectionString);
			blobClient = storageAccount.createCloudBlobClient();
			if(multitenant == 2) {
				container = blobClient.getContainerReference(commonfunction.getcontainername(multitenant, TenantContext.getCurrentTenant()) + containername);
			}else {
				container = blobClient.getContainerReference(TenantContext.getCurrentTenant() + containername);	
			}	
			// Create the container if it does not exist with public access.
			System.out.println("Creating container: " + container.getName());
			container.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(),
					new OperationContext());

			File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + fileid);
			file.transferTo(convFile);

			// Getting a blob reference
			CloudBlockBlob blob = container.getBlockBlobReference(convFile.getName());

			// Creating blob and uploading file to it
			System.out.println("Uploading the sample file in container " + container.getName());
			blob.uploadFromFile(convFile.getAbsolutePath());

			// bloburi = blob.getName();

		} catch (StorageException ex) {
			System.out.println(String.format("Error returned from the service. Http code: %d and error code: %s",
					ex.getHttpStatusCode(), ex.getErrorCode()));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			System.out.println("The program has completed successfully.");
			System.out.println(
					"Press the 'Enter' key while in the console to delete the sample files, example container, and exit the application.");

		}

		return fileid; // id.toString();
	}
	
	public String storecloudfileswithUUIDminio(MultipartFile file, String containername, String fileid) throws IOException, InvalidKeyException, NoSuchAlgorithmException, IllegalArgumentException {

		System.out.print("entering storecloudfilesreturnUUID function");
		int multitenant = env.getProperty("app.datasource.eln.url").toLowerCase().contains("elntrail") == true ? 2 : -1;
		
		try {
		      
		      MinioClient minioClient =
		          MinioClient.builder()
		              .endpoint(env.getProperty("minio.url"))
		              .credentials(env.getProperty("minio.accessKey"), env.getProperty("minio.secretKey"))
		              .build();
		      String bucket ="";
			if(multitenant == 2) {
				bucket = commonfunction.getcontainername(multitenant, TenantContext.getCurrentTenant()) + containername;
			}else {
				bucket = TenantContext.getCurrentTenant() + containername;	
			}	
		      
		      boolean found =
		          minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
		      if (!found) {
		        
		        minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
		      } else {
		        System.out.println("Bucket already exists.");
		      }

			PutObjectArgs putObjectArgs = PutObjectArgs.builder()
			           .bucket(bucket)
			           .object(fileid) // Name of the object in the bucket
			           .stream(file.getInputStream(), file.getInputStream().available(), -1) // Pass the input stream, size, and part size (set to -1 for auto-calculation)
			           .contentType(file.getContentType()) // Set the content type
			           .build();
			minioClient.putObject(putObjectArgs);
		      
		     
		    } catch (MinioException e) {
		      System.out.println("Error occurred: " + e);
		      System.out.println("HTTP trace: " + e.httpTrace());
		    }

		return fileid; // id.toString();
	}
	
	
	public String storecloudfilesreturnUUIDazure(MultipartFile file, String containername) throws IOException {

		System.out.print("entering storecloudfilesreturnUUID function");
		int multitenant = env.getProperty("app.datasource.eln.url").toLowerCase().contains("elntrail") == true ? 2 : -1;
		// String bloburi="";
		CloudStorageAccount storageAccount;
		CloudBlobClient blobClient = null;
		CloudBlobContainer container = null;
		String storageConnectionString = env.getProperty("azure.storage.ConnectionString");

		UUID objGUID = UUID.randomUUID();
		String randomUUIDString = objGUID.toString();

		try {
			// Parse the connection string and create a blob client to interact with Blob
			// storage
			storageAccount = CloudStorageAccount.parse(storageConnectionString);
			blobClient = storageAccount.createCloudBlobClient();
			if(multitenant == 2) {
				container = blobClient.getContainerReference(commonfunction.getcontainername(multitenant, TenantContext.getCurrentTenant()) + containername);
			}else {
				container = blobClient.getContainerReference(TenantContext.getCurrentTenant() + containername);	
			}	
			// Create the container if it does not exist with public access.
			System.out.println("Creating container: " + container.getName());
			container.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(),
					new OperationContext());

			File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + randomUUIDString);
			file.transferTo(convFile);

			// Getting a blob reference
			CloudBlockBlob blob = container.getBlockBlobReference(convFile.getName());

			// Creating blob and uploading file to it
			System.out.println("Uploading the sample file in container " + container.getName());
			blob.uploadFromFile(convFile.getAbsolutePath());

			// bloburi = blob.getName();

		} catch (StorageException ex) {
			System.out.println(String.format("Error returned from the service. Http code: %d and error code: %s",
					ex.getHttpStatusCode(), ex.getErrorCode()));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			System.out.println("The program has completed successfully.");
			System.out.println(
					"Press the 'Enter' key while in the console to delete the sample files, example container, and exit the application.");

		}

		return randomUUIDString; // id.toString();
	}
	
	public String storecloudfilesreturnUUIDminio(MultipartFile file, String containername) throws IOException, InvalidKeyException, NoSuchAlgorithmException, IllegalArgumentException {

		System.out.print("entering storecloudfilesreturnUUID function");
		int multitenant = env.getProperty("app.datasource.eln.url").toLowerCase().contains("elntrail") == true ? 2 : -1;
		

		UUID objGUID = UUID.randomUUID();
		String randomUUIDString = objGUID.toString();
		
		try {
		      
		      MinioClient minioClient =
		          MinioClient.builder()
		              .endpoint(env.getProperty("minio.url"))
		              .credentials(env.getProperty("minio.accessKey"), env.getProperty("minio.secretKey"))
		              .build();
		      String bucket ="";
			if(multitenant == 2) {
				bucket = commonfunction.getcontainername(multitenant, TenantContext.getCurrentTenant()) + containername;
			}else {
				bucket = TenantContext.getCurrentTenant() + containername;	
			}	
		      
		      boolean found =
		          minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
		      if (!found) {
		        
		        minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
		      } else {
		        System.out.println("Bucket already exists.");
		      }

			PutObjectArgs putObjectArgs = PutObjectArgs.builder()
			           .bucket(bucket)
			           .object(randomUUIDString) // Name of the object in the bucket
			           .stream(file.getInputStream(), file.getInputStream().available(), -1) // Pass the input stream, size, and part size (set to -1 for auto-calculation)
			           .contentType(file.getContentType()) // Set the content type
			           .build();
			minioClient.putObject(putObjectArgs);
		      
		     
		    } catch (MinioException e) {
		      System.out.println("Error occurred: " + e);
		      System.out.println("HTTP trace: " + e.httpTrace());
		    }

		return randomUUIDString; // id.toString();
	}

	public String storecloudfilesreturnwithpreUUID(MultipartFile file, String containername, String uuid,
			Integer ismultitenant) throws IOException {

		if(env.getProperty("minio.url") != null)
		{
			try {
				return storecloudfilesreturnwithpreUUIDminio(file, containername,uuid,ismultitenant);
			} catch (InvalidKeyException e) {
				
				e.printStackTrace();
				return "";
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "";
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "";
			}
		}
		else
		{
			return storecloudfilesreturnwithpreUUIDazure(file, containername,uuid,ismultitenant);
		}
	}
	
	public String storecloudfilesreturnwithpreUUIDazure(MultipartFile file, String containername, String uuid,
			Integer ismultitenant) throws IOException {

		// String bloburi="";
		CloudStorageAccount storageAccount;
		CloudBlobClient blobClient = null;
		CloudBlobContainer container = null;
		String storageConnectionString = env.getProperty("azure.storage.ConnectionString");

		try {
			// Parse the connection string and create a blob client to interact with Blob
			// storage
			storageAccount = CloudStorageAccount.parse(storageConnectionString);
			blobClient = storageAccount.createCloudBlobClient();
			container = blobClient.getContainerReference(
					commonfunction.getcontainername(ismultitenant, TenantContext.getCurrentTenant()) + containername);

			// Create the container if it does not exist with public access.
			System.out.println("Creating container: " + container.getName());
			container.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(),
					new OperationContext());

			File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + uuid);
			file.transferTo(convFile);

			// Getting a blob reference
			CloudBlockBlob blob = container.getBlockBlobReference(convFile.getName());

			// Creating blob and uploading file to it
			System.out.println("Uploading the sample file ");
			blob.uploadFromFile(convFile.getAbsolutePath());

			// bloburi = blob.getName();

		} catch (StorageException ex) {
			System.out.println(String.format("Error returned from the service. Http code: %d and error code: %s",
					ex.getHttpStatusCode(), ex.getErrorCode()));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			System.out.println("The program has completed successfully.");
			System.out.println(
					"Press the 'Enter' key while in the console to delete the sample files, example container, and exit the application.");

		}

		return uuid; // id.toString();
	}
	
	public String storecloudfilesreturnwithpreUUIDminio(MultipartFile file, String containername, String uuid,
			Integer ismultitenant)
			throws IOException, InvalidKeyException, NoSuchAlgorithmException, IllegalArgumentException {

		try {

			MinioClient minioClient = MinioClient.builder().endpoint(env.getProperty("minio.url"))
					.credentials(env.getProperty("minio.accessKey"), env.getProperty("minio.secretKey")).build();
			String bucket = "";
			if (ismultitenant == 2) {
				bucket = commonfunction.getcontainername(ismultitenant, TenantContext.getCurrentTenant())
						+ containername;
			} else {
				bucket = TenantContext.getCurrentTenant() + containername;
			}

			boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
			if (!found) {

				minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
			} else {
				System.out.println("Bucket already exists.");
			}
//			File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + uuid);
//			file.transferTo(convFile);
//
//			PutObjectArgs putObjectArgs = PutObjectArgs.builder().bucket(bucket).object(uuid) // Name of the object in
//																								// the bucket
//					.stream(file.getInputStream(), file.getInputStream().available(), -1) // Pass the input stream,
//																							// size, and part size (set
//																							// to -1 for
//																							// auto-calculation)
//					.contentType(file.getContentType()) // Set the content type
//					.build();
//			minioClient.putObject(putObjectArgs);
			
			PutObjectArgs putObjectArgs = PutObjectArgs.builder()
			           .bucket(bucket)
			           .object(uuid) // Name of the object in the bucket
			           .stream(file.getInputStream(), file.getInputStream().available(), -1) // Pass the input stream, size, and part size (set to -1 for auto-calculation)
			           .contentType(file.getContentType()) // Set the content type
			           .build();
			minioClient.putObject(putObjectArgs);

		} catch (MinioException e) {
			System.out.println("Error occurred: " + e);
			System.out.println("HTTP trace: " + e.httpTrace());
		}

		return uuid; // id.toString();
	}


	public InputStream retrieveCloudFile(String fileid, String containername) throws IOException {

		if(env.getProperty("minio.url") != null)
		{
			try {
				return retrieveCloudFileminio(fileid, containername);
			} catch (InvalidKeyException e) {
				
				e.printStackTrace();
				return null;
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		else
		{
			return retrieveCloudFileazure(fileid, containername);
		}
	}
	
	public InputStream retrieveCloudFileazure(String fileid, String containername) throws IOException {

		CloudStorageAccount storageAccount;
		CloudBlobClient blobClient = null;
		CloudBlobContainer container = null;
		CloudBlockBlob blob = null;
		String storageConnectionString = env.getProperty("azure.storage.ConnectionString");
		try {
			// Parse the connection string and create a blob client to interact with Blob
			// storage
			storageAccount = CloudStorageAccount.parse(storageConnectionString);
			blobClient = storageAccount.createCloudBlobClient();
			container = blobClient.getContainerReference(containername);

			blob = container.getBlockBlobReference(fileid);
			return blob.openInputStream();
		} catch (StorageException ex) {
			System.out.println(String.format("Error returned from the service. Http code: %d and error code: %s",
					ex.getHttpStatusCode(), ex.getErrorCode()));
			throw new IOException(String.format("Error returned from the service. Http code: %d and error code: %s",
					ex.getHttpStatusCode(), ex.getErrorCode()));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return null;

	}
	
	public InputStream retrieveCloudFileminio(String fileid, String containername) throws IOException, InvalidKeyException, NoSuchAlgorithmException, IllegalArgumentException {

		try {
		      
		      MinioClient minioClient =
		          MinioClient.builder()
		              .endpoint(env.getProperty("minio.url"))
		              .credentials(env.getProperty("minio.accessKey"), env.getProperty("minio.secretKey"))
		              .build();
		     
		     
		      
		      InputStream stream =
		    		     minioClient.getObject(
		    		   GetObjectArgs.builder()
		    		     .bucket(containername)
		    		     .object(fileid)
//		    		     .offset(offset)
//		    		     .length(len)
//		    		     .ssec(ssec)
		    		     .build());
		      
		     return stream;
		    } catch (MinioException e) {
		      System.out.println("Error occurred: " + e);
		      System.out.println("HTTP trace: " + e.httpTrace());
		    }
		return null;
	}

	public InputStream updateversionCloudFile(String fileid, String containername, String newfieldid) throws IOException
	{
		if(env.getProperty("minio.url") != null)
		{
			try {
				return updateversionCloudFileminio(fileid, containername,newfieldid);
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (ErrorResponseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (InsufficientDataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (InternalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (InvalidResponseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (ServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (XmlParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		else
		{
			return updateversionCloudFileazure(fileid, containername,newfieldid);
		}
	}
	
	public InputStream updateversionCloudFileazure(String fileid, String containername, String newfieldid)
			throws IOException {

		CloudStorageAccount storageAccount;
		CloudBlobClient blobClient = null;
		CloudBlobContainer container = null;
		CloudBlockBlob blob = null;
		String storageConnectionString = env.getProperty("azure.storage.ConnectionString");
		try {
			// Parse the connection string and create a blob client to interact with Blob
			// storage
			storageAccount = CloudStorageAccount.parse(storageConnectionString);
			blobClient = storageAccount.createCloudBlobClient();
			container = blobClient.getContainerReference(containername);

			blob = container.getBlockBlobReference(fileid);

			File targetFile = new File(System.getProperty("java.io.tmpdir") + "/" + newfieldid);

			FileUtils.copyInputStreamToFile(blob.openInputStream(), targetFile);

			// Getting a blob reference
			CloudBlockBlob blob1 = container.getBlockBlobReference(targetFile.getName());

			// Creating blob and uploading file to it
			System.out.println("Uploading the sample file ");
			blob1.uploadFromFile(targetFile.getAbsolutePath());

			return blob.openInputStream();
		} catch (StorageException ex) {
			System.out.println(String.format("Error returned from the service. Http code: %d and error code: %s",
					ex.getHttpStatusCode(), ex.getErrorCode()));
			throw new IOException(String.format("Error returned from the service. Http code: %d and error code: %s",
					ex.getHttpStatusCode(), ex.getErrorCode()));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return null;

	}
	
	public InputStream updateversionCloudFileminio(String fileid, String containername, String newfieldid)
			throws IOException, InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, 
			InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IllegalArgumentException {
		 MinioClient minioClient =
		          MinioClient.builder()
		              .endpoint(env.getProperty("minio.url"))
		              .credentials(env.getProperty("minio.accessKey"), env.getProperty("minio.secretKey"))
		              .build();
		     
		      boolean found =
		          minioClient.bucketExists(BucketExistsArgs.builder().bucket(containername).build());
		      if (!found) {
		        
		        minioClient.makeBucket(MakeBucketArgs.builder().bucket(containername).build());
		      } else {
		        System.out.println("Bucket already exists.");
		      }
		      
		      InputStream stream =
		    		     minioClient.getObject(
		    		   GetObjectArgs.builder()
		    		     .bucket(containername)
		    		     .object(fileid)
//		    		     .offset(offset)
//		    		     .length(len)
//		    		     .ssec(ssec)
		    		     .build());
		      
		      StatObjectResponse stat = minioClient.statObject(
	                    StatObjectArgs.builder()
	                            .bucket(containername)
	                            .object(fileid)
	                            .build()
	            );

	            // Retrieve and print the content type
	            String contentType = stat.contentType();
	            
		      PutObjectArgs putObjectArgs = PutObjectArgs.builder()
			           .bucket(containername)
			           .object(newfieldid) // Name of the object in the bucket
			           .stream(stream, stream.available(), -1) // Pass the input stream, size, and part size (set to -1 for auto-calculation)
			           .contentType(contentType) // Set the content type
			           .build();
			minioClient.putObject(putObjectArgs);
			
			return stream;
	}

	public InputStream movestreamstocontainer(InputStream InputStream, String containername, String fileid)
			throws IOException {
		if(env.getProperty("minio.url") != null)
		{
			try {
				return movestreamstocontainermino(InputStream,containername,fileid);
			} catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
					| InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
					| IllegalArgumentException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		else
		{
			return movestreamstocontainerazure(InputStream,containername,fileid);
		}
	}
	
	public InputStream movestreamstocontainerazure(InputStream InputStream, String containername, String fileid)
			throws IOException {

		CloudStorageAccount storageAccount;
		CloudBlobClient blobClient = null;
		CloudBlobContainer container = null;
		CloudBlockBlob blob = null;
		String storageConnectionString = env.getProperty("azure.storage.ConnectionString");
		try {
			// Parse the connection string and create a blob client to interact with Blob
			// storage
			storageAccount = CloudStorageAccount.parse(storageConnectionString);
			blobClient = storageAccount.createCloudBlobClient();
			container = blobClient.getContainerReference(containername);

			System.out.println("Creating container: " + container.getName());
			container.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(),
					new OperationContext());

			File targetFile = new File(System.getProperty("java.io.tmpdir") + "/" + fileid);

			FileUtils.copyInputStreamToFile(InputStream, targetFile);

			// Getting a blob reference
			blob = container.getBlockBlobReference(targetFile.getName());

			// Creating blob and uploading file to it
			System.out.println("Uploading the sample file ");
			blob.uploadFromFile(targetFile.getAbsolutePath());

			return blob.openInputStream();
		} catch (StorageException ex) {
			System.out.println(String.format("Error returned from the service. Http code: %d and error code: %s",
					ex.getHttpStatusCode(), ex.getErrorCode()));
			throw new IOException(String.format("Error returned from the service. Http code: %d and error code: %s",
					ex.getHttpStatusCode(), ex.getErrorCode()));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return null;

	}
	
	public InputStream movestreamstocontainermino(InputStream InputStream, String containername, String fileid)
			throws IOException, InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, 
			InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IllegalArgumentException {
		MinioClient minioClient =
		          MinioClient.builder()
		              .endpoint(env.getProperty("minio.url"))
		              .credentials(env.getProperty("minio.accessKey"), env.getProperty("minio.secretKey"))
		              .build();
		     
		      boolean found =
		          minioClient.bucketExists(BucketExistsArgs.builder().bucket(containername).build());
		      if (!found) {
		        
		        minioClient.makeBucket(MakeBucketArgs.builder().bucket(containername).build());
		      } else {
		        System.out.println("Bucket already exists.");
		      }
		      
//		      File targetFile = new File(System.getProperty("java.io.tmpdir") + "/" + fileid);
//
//		      FileUtils.copyInputStreamToFile(InputStream, targetFile);
//		      
//		      try (InputStream inputStream = new FileInputStream(targetFile)) {
//		    	  String contentType = URLConnection.guessContentTypeFromStream(inputStream);
//	                minioClient.putObject(
//	                        PutObjectArgs.builder()
//	                                .bucket(containername)
//	                                .object(targetFile.getName())
//	                                .stream(inputStream, inputStream.available(), -1)
//	                                .contentType(contentType) // Set appropriate content type
//	                                .build()
//	                );
//		      };
		      
		      byte[] data = StreamUtils.copyToByteArray(InputStream);
			    try (ByteArrayInputStream bais = new ByteArrayInputStream(data)) {
			        String contentType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(data));
			        if (contentType == null) {
			            contentType = "image/png"; // fallback
			        }

			        minioClient.putObject(PutObjectArgs.builder()
			                .bucket(containername)
			                .object(fileid)
			                .stream(bais, data.length, -1)
			                .contentType(contentType)
			                .build());
			    }
		      
		      return InputStream;
	}

	public boolean movefiletoanothercontainerandremove(String fromcontainer, String tocontainer, String fileid,String moveFileid) {
		try {
			InputStream stream = retrieveCloudFile(fileid, fromcontainer);
			movestreamstocontainer(stream, tocontainer, moveFileid);
			deleteFile(fileid, fromcontainer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

	public void deletecloudFile(String id, String containername) {
		if(env.getProperty("minio.url") != null)
		{
			deletecloudFileminio(id, containername);
		}
		else
		{
			deletecloudFileazure(id, containername);
		}
	}
	public void deletecloudFileazure(String id, String containername) {
		CloudStorageAccount storageAccount;
		CloudBlobClient blobClient = null;
		CloudBlobContainer container = null;
		CloudBlockBlob blob = null;
		String storageConnectionString = env.getProperty("azure.storage.ConnectionString");
		try {
			// Parse the connection string and create a blob client to interact with Blob
			// storage
			storageAccount = CloudStorageAccount.parse(storageConnectionString);
			blobClient = storageAccount.createCloudBlobClient();
			container = blobClient.getContainerReference(TenantContext.getCurrentTenant() + containername);

			blob = container.getBlockBlobReference(id);
			blob.deleteIfExists();
		} catch (StorageException ex) {
			System.out.println(String.format("Error returned from the service. Http code: %d and error code: %s",
					ex.getHttpStatusCode(), ex.getErrorCode()));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void deletecloudFileminio(String id, String containername) {
		MinioClient minioClient =
		          MinioClient.builder()
		              .endpoint(env.getProperty("minio.url"))
		              .credentials(env.getProperty("minio.accessKey"), env.getProperty("minio.secretKey"))
		              .build();
		 
			
				try {
					minioClient.removeObject(
					            RemoveObjectArgs.builder()
					                .bucket(containername)
					                .object(id)
					                .build());
				} catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
						| InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
						| IllegalArgumentException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}

	public void deleteFile(String id, String containername) {
		if(env.getProperty("minio.url") != null)
		{
			deleteFileminio(id, containername);
		}
		else
		{
			deleteFileazure(id, containername);
		}
	}
	
	public void deleteFileazure(String id, String containername) {
		CloudStorageAccount storageAccount;
		CloudBlobClient blobClient = null;
		CloudBlobContainer container = null;
		CloudBlockBlob blob = null;
		String storageConnectionString = env.getProperty("azure.storage.ConnectionString");
		try {
			// Parse the connection string and create a blob client to interact with Blob
			// storage
			storageAccount = CloudStorageAccount.parse(storageConnectionString);
			blobClient = storageAccount.createCloudBlobClient();
			container = blobClient.getContainerReference(containername);

			blob = container.getBlockBlobReference(id);
			blob.deleteIfExists();
		} catch (StorageException ex) {
			System.out.println(String.format("Error returned from the service. Http code: %d and error code: %s",
					ex.getHttpStatusCode(), ex.getErrorCode()));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void deleteFileminio(String id, String containername) {
		MinioClient minioClient =
		          MinioClient.builder()
		              .endpoint(env.getProperty("minio.url"))
		              .credentials(env.getProperty("minio.accessKey"), env.getProperty("minio.secretKey"))
		              .build();
		 
			
				try {
					minioClient.removeObject(
					            RemoveObjectArgs.builder()
					                .bucket(containername)
					                .object(id)
					                .build());
				} catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
						| InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
						| IllegalArgumentException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	
	public Map<String, Object> storecloudReportfile(byte[] documentBytes, String containerName, String documentName)
	        throws IOException {
		if(env.getProperty("minio.url") != null)
		{
			try {
				return storecloudReportfileminio(documentBytes, containerName, documentName);
			} catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
					| InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
					| IllegalArgumentException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		else
		{
			return storecloudReportfileazure(documentBytes, containerName, documentName);
		}
	}

	public Map<String, Object> storecloudReportfileazure(byte[] documentBytes, String containerName, String documentName)
	        throws IOException {
	    Map<String, Object> objMap = new HashMap<>();
	    CloudStorageAccount storageAccount;
	    CloudBlobClient blobClient;
	    CloudBlobContainer container;
	    String storageConnectionString = env.getProperty("azure.storage.ConnectionString");
	    try {
	        storageAccount = CloudStorageAccount.parse(storageConnectionString);
	        blobClient = storageAccount.createCloudBlobClient();
	        container = blobClient.getContainerReference(containerName);
	        System.out.println("Creating container: " + container.getName());
	        container.createIfNotExists();
	        CloudBlockBlob blob = container.getBlockBlobReference(documentName);
	        InputStream dataStream = new ByteArrayInputStream(documentBytes);
	        blob.getProperties().setContentType("application/json");
	        blob.upload(dataStream, documentBytes.length);
	        System.out.println("File uploaded to Azure Blob Storage successfully.");
	        objMap.put("blobName", documentName);
	        objMap.put("blobUri", blob.getUri());
	    } catch (StorageException ex) {
	        System.out.println(String.format("Error returned from the service. Http code: %d and error code: %s",
	                ex.getHttpStatusCode(), ex.getErrorCode()));
	    } catch (Exception ex) {
	        System.out.println(ex.getMessage());
	    }

	    return objMap;
	
	}
	
	public Map<String, Object> storecloudReportfileminio(byte[] documentBytes, String containerName, String documentName)
	        throws IOException, InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException, 
	        NoSuchAlgorithmException, ServerException, XmlParserException, IllegalArgumentException {
		Map<String, Object> objMap = new HashMap<>();
		
		MinioClient minioClient =
		          MinioClient.builder()
		              .endpoint(env.getProperty("minio.url"))
		              .credentials(env.getProperty("minio.accessKey"), env.getProperty("minio.secretKey"))
		              .build();
		     
		      boolean found =
		          minioClient.bucketExists(BucketExistsArgs.builder().bucket(containerName).build());
		      if (!found) {
		        
		        minioClient.makeBucket(MakeBucketArgs.builder().bucket(containerName).build());
		      } else {
		        System.out.println("Bucket already exists.");
		      }
		      
		      InputStream dataStream = new ByteArrayInputStream(documentBytes);
		      
		      PutObjectArgs putObjectArgs = PutObjectArgs.builder()
			           .bucket(containerName)
			           .object(documentName) // Name of the object in the bucket
			           .stream(dataStream, dataStream.available(), -1) // Pass the input stream, size, and part size (set to -1 for auto-calculation)
			           .contentType("application/json") // Set the content type
			           .build();
			minioClient.putObject(putObjectArgs);
			
			String	url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET) // HTTP method (GET, PUT, etc.)
                            .bucket(containerName)
                            .object(documentName)
//                            .expiry(7 * 24 * 60 * 60) // Expiry time in seconds (7 days)
                            .build()
            );
		      
		    objMap.put("blobName", documentName);
		    objMap.put("blobUri", url);
		
		return objMap;
	}

	public Map<String, Object> storecloudSheetsreturnwithpreUUID(String Content, String containerName)
			throws IOException {

		if(env.getProperty("minio.url") != null)
		{
			try {
				return storecloudSheetsreturnwithpreUUIDminio(Content, containerName);
			} catch (InvalidKeyException e) {
				
				e.printStackTrace();
				return null;
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		else
		{
			return storecloudSheetsreturnwithpreUUIDazure(Content, containerName);
		}
	}
	
	public Map<String, Object> storecloudSheetsreturnwithpreUUIDazure(String Content, String containerName)
			throws IOException {

		Map<String, Object> objMap = new HashMap<String, Object>();

		CloudStorageAccount storageAccount;
		CloudBlobClient blobClient = null;
		CloudBlobContainer container = null;

		String storageConnectionString = env.getProperty("azure.storage.ConnectionString");
		String randomUUIDString = UUID.randomUUID().toString();

		try {
			// Parse the connection string and create a blob client to interact with Blob
			storageAccount = CloudStorageAccount.parse(storageConnectionString);
			blobClient = storageAccount.createCloudBlobClient();
			container = blobClient.getContainerReference(containerName);

			// Create the container if it does not exist with public access.
			System.out.println("Creating container: " + container.getName());
			container.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(),
					new OperationContext());

			// Getting a blob reference
			CloudBlockBlob blob = container.getBlockBlobReference(randomUUIDString);

			// uploading text
			blob.uploadText(Content);

			objMap.put("uuid", randomUUIDString);
			objMap.put("uri", blob.getUri());

			System.out.println("Uploading the file ");

		} catch (StorageException ex) {
			System.out.println(String.format("Error returned from the service. Http code: %d and error code: %s",
					ex.getHttpStatusCode(), ex.getErrorCode()));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			System.out.println("The program has completed successfully.");
			System.out.println(
					"Press the 'Enter' key while in the console to delete the sample files, example container, and exit the application.");

		}

		return objMap;
	}
	
	public Map<String, Object> storecloudSheetsreturnwithpreUUIDminio(String Content, String containerName)
			throws IOException, InvalidKeyException, NoSuchAlgorithmException, IllegalArgumentException {

		Map<String, Object> objMap = new HashMap<String, Object>();

		String randomUUIDString = UUID.randomUUID().toString();
		String url = "";
		try {
		      
		      MinioClient minioClient =
		          MinioClient.builder()
		              .endpoint(env.getProperty("minio.url"))
		              .credentials(env.getProperty("minio.accessKey"), env.getProperty("minio.secretKey"))
		              .build();
		      
		      boolean found =
		          minioClient.bucketExists(BucketExistsArgs.builder().bucket(containerName).build());
		      if (!found) {
		        
		        minioClient.makeBucket(MakeBucketArgs.builder().bucket(containerName).build());
		      } else {
		        System.out.println("Bucket already exists.");
		      }
		      
		      byte[] contentBytes = Content.getBytes("UTF-8");
	           InputStream inputStream = new ByteArrayInputStream(contentBytes);

	           minioClient.putObject(io.minio.PutObjectArgs.builder()
	                   .bucket(containerName)
	                   .object(randomUUIDString)
	                   .stream(inputStream, contentBytes.length, -1)
	                   .build());
	           
	           url = minioClient.getPresignedObjectUrl(
	                    GetPresignedObjectUrlArgs.builder()
	                            .method(Method.GET) // HTTP method (GET, PUT, etc.)
	                            .bucket(containerName)
	                            .object(randomUUIDString)
//	                            .expiry(7 * 24 * 60 * 60) // Expiry time in seconds (7 days)
	                            .build()
	            );
		     
		    } catch (MinioException e) {
		      System.out.println("Error occurred: " + e);
		      System.out.println("HTTP trace: " + e.httpTrace());
		    }
		objMap.put("uuid", randomUUIDString);
		objMap.put("uri", url);
		return objMap;
	}
	
	public Map<String, Object> storecloudSheets(String Content, String containerName, String uuid)
			throws IOException {

		if(env.getProperty("minio.url") != null)
		{
			try {
				return storecloudSheetsminio(Content, containerName,uuid);
			} catch (InvalidKeyException e) {
				
				e.printStackTrace();
				return null;
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		else
		{
			return storecloudSheetsazure(Content, containerName,uuid);
		}
	}
	
	public Map<String, Object> storecloudSheetsazure(String Content, String containerName, String uuid)
			throws IOException {

		Map<String, Object> objMap = new HashMap<String, Object>();

		CloudStorageAccount storageAccount;
		CloudBlobClient blobClient = null;
		CloudBlobContainer container = null;

		String storageConnectionString = env.getProperty("azure.storage.ConnectionString");
		String randomUUIDString = uuid;

		try {
			// Parse the connection string and create a blob client to interact with Blob
			storageAccount = CloudStorageAccount.parse(storageConnectionString);
			blobClient = storageAccount.createCloudBlobClient();
			container = blobClient.getContainerReference(containerName);

			// Create the container if it does not exist with public access.
			System.out.println("Creating container: " + container.getName());
			container.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(),
					new OperationContext());

			// Getting a blob reference
			CloudBlockBlob blob = container.getBlockBlobReference(randomUUIDString);

			// uploading text
			blob.uploadText(Content);

			objMap.put("uuid", randomUUIDString);
			objMap.put("uri", blob.getUri());

			System.out.println("Uploading the file ");

		} catch (StorageException ex) {
			System.out.println(String.format("Error returned from the service. Http code: %d and error code: %s",
					ex.getHttpStatusCode(), ex.getErrorCode()));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			System.out.println("The program has completed successfully.");
			System.out.println(
					"Press the 'Enter' key while in the console to delete the sample files, example container, and exit the application.");

		}

		return objMap;
	}
	
	public Map<String, Object> storecloudSheetsminio(String Content, String containerName, String uuid)
			throws IOException, InvalidKeyException, NoSuchAlgorithmException, IllegalArgumentException {

		Map<String, Object> objMap = new HashMap<String, Object>();

		String randomUUIDString = uuid;
		String url = "";
		try {
		      
		      MinioClient minioClient =
		          MinioClient.builder()
		              .endpoint(env.getProperty("minio.url"))
		              .credentials(env.getProperty("minio.accessKey"), env.getProperty("minio.secretKey"))
		              .build();
		      
		      boolean found =
		          minioClient.bucketExists(BucketExistsArgs.builder().bucket(containerName).build());
		      if (!found) {
		        
		        minioClient.makeBucket(MakeBucketArgs.builder().bucket(containerName).build());
		      } else {
		        System.out.println("Bucket already exists.");
		      }
		      
		      byte[] contentBytes = Content.getBytes("UTF-8");
	           InputStream inputStream = new ByteArrayInputStream(contentBytes);

	           minioClient.putObject(io.minio.PutObjectArgs.builder()
	                   .bucket(containerName)
	                   .object(randomUUIDString)
	                   .stream(inputStream, contentBytes.length, -1)
	                   .build());
	           url = minioClient.getPresignedObjectUrl(
	                    GetPresignedObjectUrlArgs.builder()
	                            .method(Method.GET) // HTTP method (GET, PUT, etc.)
	                            .bucket(containerName)
	                            .object(randomUUIDString)
//	                            .expiry(7 * 24 * 60 * 60) // Expiry time in seconds (7 days)
	                            .build());
		     
		    } catch (MinioException e) {
		      System.out.println("Error occurred: " + e);
		      System.out.println("HTTP trace: " + e.httpTrace());
		    }
		objMap.put("uuid", randomUUIDString);
		objMap.put("uri", url);
		return objMap;
	}

	public String retrieveCloudSheets(String fileid, String containername) throws IOException {

		if(env.getProperty("minio.url") != null)
		{
			try {
				return retrieveCloudSheetsminio(fileid, containername);
			} catch (InvalidKeyException e) {
				
				e.printStackTrace();
				return null;
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		else
		{
			return retrieveCloudSheetsazure(fileid, containername);
		}

	}
	
	public String retrieveCloudSheetsazure(String fileid, String containername) throws IOException {

		CloudStorageAccount storageAccount;
		CloudBlobClient blobClient = null;
		CloudBlobContainer container = null;
		CloudBlockBlob blob = null;
		String storageConnectionString = env.getProperty("azure.storage.ConnectionString");
		try {
			// Parse the connection string and create a blob client to interact with Blob
			// storage
			storageAccount = CloudStorageAccount.parse(storageConnectionString);
			blobClient = storageAccount.createCloudBlobClient();
			container = blobClient.getContainerReference(containername);
			blob = container.getBlockBlobReference(fileid);
			return blob.downloadText();
		} catch (StorageException ex) {
			System.out.println(String.format("Error returned from the service. Http code: %d and error code: %s",
					ex.getHttpStatusCode(), ex.getErrorCode()));
			throw new IOException(String.format("Error returned from the service. Http code: %d and error code: %s",
					ex.getHttpStatusCode(), ex.getErrorCode()));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return null;

	}
	
	public String retrieveCloudSheetsminio(String fileid, String containername) throws IOException, InvalidKeyException, NoSuchAlgorithmException, IllegalArgumentException {

		try {
		      
		      MinioClient minioClient =
		          MinioClient.builder()
		              .endpoint(env.getProperty("minio.url"))
		              .credentials(env.getProperty("minio.accessKey"), env.getProperty("minio.secretKey"))
		              .build();
		     
		     
		      
		      InputStream stream =
		    		     minioClient.getObject(
		    		   GetObjectArgs.builder()
		    		     .bucket(containername)
		    		     .object(fileid)
//		    		     .offset(offset)
//		    		     .length(len)
//		    		     .ssec(ssec)
		    		     .build());
		      
		      String text = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
		      
		     return text;
		    } catch (MinioException e) {
		      System.out.println("Error occurred: " + e);
		      System.out.println("HTTP trace: " + e.httpTrace());
		    }
		return null;

	}


	public boolean tocopyoncontainertoanothercontainer(List<String> fileIds, String sourceContainerName,
			String destinationContainerName) throws IOException {
		if(env.getProperty("minio.url") != null)
		{
			return tocopyoncontainertoanothercontainerminio(fileIds, sourceContainerName, destinationContainerName);
		}
		else
		{
			return tocopyoncontainertoanothercontainerazure(fileIds, sourceContainerName, destinationContainerName);
		}
	}
	
	public boolean tocopyoncontainertoanothercontainerazure(List<String> fileIds, String sourceContainerName,
			String destinationContainerName) throws IOException {
		CloudStorageAccount storageAccount;
		CloudBlobClient blobClient = null;
		CloudBlobContainer sourceContainer = null;
		CloudBlobContainer destinationContainer = null;
		String storageConnectionString = env.getProperty("azure.storage.ConnectionString");
		try {
			// Parse the connection string and create a blob client to interact with Blob
			// storage
			storageAccount = CloudStorageAccount.parse(storageConnectionString);
			blobClient = storageAccount.createCloudBlobClient();
			sourceContainer = blobClient.getContainerReference(sourceContainerName);
			destinationContainer = blobClient.getContainerReference(destinationContainerName);
			destinationContainer.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(),
					new OperationContext());

			for (String fileId : fileIds) {
				CloudBlockBlob sourceBlob = sourceContainer.getBlockBlobReference(fileId);
				CloudBlockBlob destinationBlob = destinationContainer.getBlockBlobReference(fileId);
				destinationBlob.startCopy(sourceBlob);
			}
			return true;
		} catch (StorageException ex) {
			System.out.println(String.format("Error returned from the service. Http code: %d and error code: %s",
					ex.getHttpStatusCode(), ex.getErrorCode()));
//		        throw new IOException(String.format("Error returned from the service. Http code: %d and error code: %s",
//		                ex.getHttpStatusCode(), ex.getErrorCode()));
			return false;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
//			return storageConnectionString;

	}
	
	public boolean tocopyoncontainertoanothercontainerminio(List<String> fileIds, String sourceContainerName,
			String destinationContainerName)
	{
		 MinioClient minioClient =
		          MinioClient.builder()
		              .endpoint(env.getProperty("minio.url"))
		              .credentials(env.getProperty("minio.accessKey"), env.getProperty("minio.secretKey"))
		              .build();
		 for (String fileId : fileIds) {
			 CopySource source = CopySource.builder()
	                    .bucket(sourceContainerName) // Source bucket name
	                    .object(fileId) // Source object name
	                    .build();
			 CopyObjectArgs copyObjectArgs = CopyObjectArgs.builder()
	                    .bucket(destinationContainerName) // Destination bucket name
	                    .object(fileId) // Destination object name
	                    .source(source) // Source object
	                    .build();

	            // Perform the copy operation
			 try {
				minioClient.copyObject(copyObjectArgs);
			} catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
					| InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
					| IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			 
		 }
		 return true;
	}

	public byte[] retrieveCloudReportFile(String containerName, String documentName) {
		if(env.getProperty("minio.url") != null)
		{
			return retrieveCloudReportFileminio(containerName, documentName);
		}
		else
		{
			return retrieveCloudReportFileazure(containerName, documentName);
		}
	}
	
	public byte[] retrieveCloudReportFileazure(String containerName, String documentName) {
        CloudStorageAccount storageAccount;
        CloudBlobClient blobClient;
        CloudBlobContainer container;
        byte[] documentBytes = null;
    	String storageConnectionString = env.getProperty("azure.storage.ConnectionString");
        try {
            storageAccount = CloudStorageAccount.parse(storageConnectionString);
            blobClient = storageAccount.createCloudBlobClient();
            container = blobClient.getContainerReference(containerName);
            CloudBlockBlob blob = container.getBlockBlobReference(documentName);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            blob.download(outputStream);
            documentBytes = outputStream.toByteArray();
            System.out.println("File downloaded from Azure Blob Storage successfully.");

        } catch (StorageException ex) {
            System.out.println(String.format("Error returned from the service. Http code: %d and error code: %s",
                    ex.getHttpStatusCode(), ex.getErrorCode()));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return documentBytes;

	}
	
	public byte[] retrieveCloudReportFileminio(String containerName, String documentName) {
		byte[] documentBytes = null;
		MinioClient minioClient =
		          MinioClient.builder()
		              .endpoint(env.getProperty("minio.url"))
		              .credentials(env.getProperty("minio.accessKey"), env.getProperty("minio.secretKey"))
		              .build();
		
		try {
			InputStream stream =
			     minioClient.getObject(
			   GetObjectArgs.builder()
			     .bucket(containerName)
			     .object(documentName)
//   		     .offset(offset)
//   		     .length(len)
//   		     .ssec(ssec)
			     .build());
			
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = stream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }

            // Close the InputStream
            stream.close();
            
			documentBytes = byteArrayOutputStream.toByteArray();
		} catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
				| InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
				| IllegalArgumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return documentBytes;
	}

	public InputStream retrieveClouddatasteam(String fileid, String containername) throws IOException {

		if (env.getProperty("minio.url") != null) {
			try {
				return retrieveCloudSheetsminiostrams(fileid, containername);
			} catch (InvalidKeyException e) {

				e.printStackTrace();
				return null;
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		} else {
			return retrieveCloudFile(fileid, containername);
		}

	}
	
	public InputStream retrieveCloudSheetsminiostrams(String fileid, String containername)
			throws IOException, InvalidKeyException, NoSuchAlgorithmException, IllegalArgumentException {

		try {

			MinioClient minioClient = MinioClient.builder().endpoint(env.getProperty("minio.url"))
					.credentials(env.getProperty("minio.accessKey"), env.getProperty("minio.secretKey")).build();
			InputStream stream = minioClient
					.getObject(GetObjectArgs.builder().bucket(containername).object(fileid).build());
			return stream;
		} catch (MinioException e) {
			System.out.println("Error occurred: " + e);
			System.out.println("HTTP trace: " + e.httpTrace());
		}
		return null;

	}

}