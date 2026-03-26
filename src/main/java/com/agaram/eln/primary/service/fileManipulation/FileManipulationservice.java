package com.agaram.eln.primary.service.fileManipulation;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bson.BsonBinarySubType;
import org.bson.BsonInt32;
import org.bson.Document;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.agaram.eln.primary.commonfunction.commonfunction;
import com.agaram.eln.primary.model.cfr.LScfttransaction;
import com.agaram.eln.primary.model.fileManipulation.OrderAttachment;
import com.agaram.eln.primary.model.fileManipulation.ProfilePicture;
import com.agaram.eln.primary.model.fileManipulation.ResultorderlimsRefrence;
import com.agaram.eln.primary.model.fileManipulation.SheetorderlimsRefrence;
import com.agaram.eln.primary.model.fileManipulation.UserSignature;
import com.agaram.eln.primary.model.instrumentDetails.LsOrderattachments;
import com.agaram.eln.primary.model.instrumentDetails.LsResultlimsOrderrefrence;
import com.agaram.eln.primary.model.instrumentDetails.LsSheetorderlimsrefrence;
import com.agaram.eln.primary.model.methodsetup.ELNFileAttachments;
import com.agaram.eln.primary.model.protocols.LSprotocolfilesContent;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;
import com.agaram.eln.primary.repository.cfr.LScfttransactionRepository;
import com.agaram.eln.primary.repository.fileManipulation.OrderAttachmentRepository;
import com.agaram.eln.primary.repository.fileManipulation.ProfilePictureRepository;
import com.agaram.eln.primary.repository.fileManipulation.ResultorderlimsRefrenceRepository;
import com.agaram.eln.primary.repository.fileManipulation.SheetorderlimsRefrenceRepository;
import com.agaram.eln.primary.repository.fileManipulation.UserSignatureRepository;
import com.agaram.eln.primary.repository.protocol.LSprotocolfilesContentRepository;
import com.agaram.eln.primary.repository.usermanagement.LSuserMasterRepository;
import com.agaram.eln.primary.service.protocol.ProtocolService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;

@Service
public class FileManipulationservice {
	@Autowired
	private ProfilePictureRepository profilePictureRepository;

	@Autowired
	private OrderAttachmentRepository orderAttachmentRepository;

	@Autowired
	private SheetorderlimsRefrenceRepository sheetorderlimsRefrenceRepository;

	@Autowired
	private GridFsTemplate gridFsTemplate;

	@Autowired
	private LScfttransactionRepository lscfttransactionRepository;

	@Autowired
	private LSuserMasterRepository lsuserMasterRepository;
	
	@Autowired
	private ResultorderlimsRefrenceRepository ResultorderlimsRefrenceRepository;

	@Autowired
	private UserSignatureRepository UserSignatureRepository;
	
	@Autowired
	private LSprotocolfilesContentRepository lsprotocolfilesContentRepository;
	
	public ProfilePicture addPhoto(Integer usercode, MultipartFile file, Date currentdate) throws IOException {

		LSuserMaster username = lsuserMasterRepository.findByusercode(usercode);
		String name = username.getUsername();
		LScfttransaction list = new LScfttransaction();
		list.setModuleName("UserManagement");
		list.setComments(name + " " + "Uploaded the profile picture successfully");
		list.setActions("View / Load");
		list.setSystemcoments("System Generated");
		list.setTableName("profile");

		list.setLsuserMaster(usercode);			try {
			list.setTransactiondate(commonfunction.getCurrentUtcTime());
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		lscfttransactionRepository.save(list);
		deletePhoto(usercode, list);

		ProfilePicture profile = new ProfilePicture();
		profile.setId(usercode);
		profile.setName(file.getName());
		profile.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
		profile = profilePictureRepository.insert(profile);

		return profile;
	}

	
	public UserSignature addsignature(Integer usercode, MultipartFile file, Date currentdate) throws IOException {

		LScfttransaction list = new LScfttransaction();
		list.setModuleName("UserManagement");
		//list.setComments(name + " " + "Uploaded the profile picture successfully");
		list.setActions("View / Load");
		list.setSystemcoments("System Generated");
		list.setTableName("profile");
//		list.setTransactiondate(currentdate);
//	    	list.setLsuserMaster(lsuserMasterRepository.findByusercode(usercode));
		list.setLsuserMaster(usercode);
		try {
			list.setTransactiondate(commonfunction.getCurrentUtcTime());
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		lscfttransactionRepository.save(list);
		deleteSignature(usercode, list);

		UserSignature Signature = new UserSignature();
		Signature.setId(usercode);
		Signature.setName(file.getName());
		Signature.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
		Signature = UserSignatureRepository.insert(Signature);

		return Signature;
	}
	
	
	public void deleteSignature(Integer id, LScfttransaction list) {
		list.setTableName("UserSignature");
		try {
			list.setTransactiondate(commonfunction.getCurrentUtcTime());
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		lscfttransactionRepository.save(list);
		 UserSignatureRepository.deleteById(id);
	}

	public UserSignature getsignature(Integer id) {

		return UserSignatureRepository.findTop1ById(id);
	}
	
	public ProfilePicture getPhoto(Integer id) {

		return profilePictureRepository.findTop1ById(id);
	}

	public void deletePhoto(Integer id, LScfttransaction list) {
		list.setTableName("ProfilePicture");
		try {
			list.setTransactiondate(commonfunction.getCurrentUtcTime());
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		lscfttransactionRepository.save(list);
		 profilePictureRepository.deleteById(id);
	}

	public OrderAttachment storeattachment(MultipartFile file) throws IOException {
		UUID objGUID = UUID.randomUUID();
		String randomUUIDString = objGUID.toString();

		OrderAttachment objattachment = new OrderAttachment();
		objattachment.setId(randomUUIDString);
		objattachment.setFile(new Binary(BsonBinarySubType.BINARY, file.getBytes()));

		objattachment = orderAttachmentRepository.insert(objattachment);

		return objattachment;
	}

	public String storeLargeattachment(String title, MultipartFile file) throws IOException {
		DBObject metaData = new BasicDBObject();
		metaData.put("title", title);

		UUID objGUID = UUID.randomUUID();
		String randomUUIDString = objGUID.toString();

		gridFsTemplate.store(file.getInputStream(), randomUUIDString, file.getContentType(), metaData);

		return randomUUIDString;
	}
	
	public String storeLargeattachmentBarcode(String title, MultipartFile file,String randomUUIDString) throws IOException {
		DBObject metaData = new BasicDBObject();
		metaData.put("title", title);
		gridFsTemplate.store(file.getInputStream(), randomUUIDString, file.getContentType(), metaData);
		return randomUUIDString;
	}
	
	public String storeLargeattachmentwithpreuid(String title, MultipartFile file, String uuid) throws IOException {
		DBObject metaData = new BasicDBObject();
		metaData.put("title", title);

		gridFsTemplate.store(file.getInputStream(), uuid, file.getContentType(), metaData);

		return uuid;
	}

	public OrderAttachment retrieveFile(LsOrderattachments objattach) {

		OrderAttachment objfile = orderAttachmentRepository.findTop1ById(objattach.getFileid());

		return objfile;
	}
	public OrderAttachment retrieveFile(ELNFileAttachments objattach) {

		OrderAttachment objfile = orderAttachmentRepository.findTop1ById(objattach.getFileid());

		return objfile;
	}
	public GridFsResource retrieveLargeFile(String fileid) throws IllegalStateException, IOException {
		GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("filename").is(fileid)));
		if (file == null) {
			file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(fileid)));
			if(file==null) {
				 LSprotocolfilesContent lsprotocolfilesContent = lsprotocolfilesContentRepository.findByFileid(fileid);
				    if (lsprotocolfilesContent == null ||
				        lsprotocolfilesContent.getFile() == null ||
				        lsprotocolfilesContent.getFile().getData() == null) {
				        throw new FileNotFoundException("File not found in MongoDB GridFS or SQL DB : " + fileid);
				    }

				    byte[] data = lsprotocolfilesContent.getFile().getData();
				    InputStream inputStream = new ByteArrayInputStream(data);
				    Document metaData = new Document();
				    metaData.put("filename", fileid);
				    metaData.put("length", data.length);
				    GridFSFile fakeGridFile = new GridFSFile(
				            new BsonInt32(lsprotocolfilesContent.getId()), 
				            fileid,
				            data.length,
				            255,
				            new Date(),
				            metaData   
				    );

				    return new GridFsResource(fakeGridFile, inputStream);
			}
		}
		GridFsResource resource = gridFsTemplate.getResource(file.getFilename());
		return resource;
	}

	public void deleteattachments(String id) {
		orderAttachmentRepository.deleteById(id);
	}

	public void deletelargeattachments(String id) {
		gridFsTemplate.delete(Query.query(Criteria.where("filename").is(id)));
		gridFsTemplate.delete(Query.query(Criteria.where("_id").is(id)));
	}

	public SheetorderlimsRefrence storeLimsSheetRefrence(MultipartFile file) throws IOException {
		UUID objGUID = UUID.randomUUID();
		String randomUUIDString = objGUID.toString();

		SheetorderlimsRefrence objattachment = new SheetorderlimsRefrence();
		objattachment.setId(randomUUIDString);
		objattachment.setFile(new Binary(BsonBinarySubType.BINARY, file.getBytes()));

		objattachment = sheetorderlimsRefrenceRepository.insert(objattachment);

		return objattachment;
	}
	
	public ResultorderlimsRefrence storeResultLimsSheetRefrence(MultipartFile file) throws IOException {
		UUID objGUID = UUID.randomUUID();
		String randomUUIDString = objGUID.toString();

		ResultorderlimsRefrence objattachment = new ResultorderlimsRefrence();
		objattachment.setId(randomUUIDString);
		objattachment.setFile(new Binary(BsonBinarySubType.BINARY, file.getBytes()));

		objattachment = ResultorderlimsRefrenceRepository.insert(objattachment);

		return objattachment;
	}

	public SheetorderlimsRefrence LimsretrieveELNsheet(LsSheetorderlimsrefrence objattachment) {

		SheetorderlimsRefrence objfile = sheetorderlimsRefrenceRepository.findTop1ById(objattachment.getFileid());

		return objfile;
	}
	
	public ResultorderlimsRefrence LimsretrieveResultELNsheet(LsResultlimsOrderrefrence objattachment) {

		ResultorderlimsRefrence objfile = ResultorderlimsRefrenceRepository.findTop1ById(objattachment.getFileid());

		return objfile;
	}
	
	public List<GridFSFile> retrieveLargeFileinlist(List<String> fileid) {
	    Query query = new Query();
	    query.addCriteria(Criteria.where("filename").in(fileid));

	    List<GridFSFile> result = new ArrayList<>();
	    gridFsTemplate.find(query).forEach(result::add);

	    return result;
	}
}
