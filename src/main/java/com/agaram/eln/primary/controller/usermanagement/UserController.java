package com.agaram.eln.primary.controller.usermanagement;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.agaram.eln.config.SMTPMailvalidation;
import com.agaram.eln.primary.fetchmodel.getmasters.Listofallmaster;
import com.agaram.eln.primary.model.cloudFileManip.CloudProfilePicture;
import com.agaram.eln.primary.model.cloudFileManip.CloudUserSignature;
import com.agaram.eln.primary.model.fileManipulation.ProfilePicture;
import com.agaram.eln.primary.model.fileManipulation.UserSignature;
import com.agaram.eln.primary.model.general.Response;
import com.agaram.eln.primary.model.usermanagement.LSPasswordPolicy;
import com.agaram.eln.primary.model.usermanagement.LSSiteMaster;
import com.agaram.eln.primary.model.usermanagement.LSactiveUser;
import com.agaram.eln.primary.model.usermanagement.LScentralisedUsers;
import com.agaram.eln.primary.model.usermanagement.LSnotification;
import com.agaram.eln.primary.model.usermanagement.LSprojectmaster;
import com.agaram.eln.primary.model.usermanagement.LSuserActions;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;
import com.agaram.eln.primary.model.usermanagement.LSusergroup;
import com.agaram.eln.primary.model.usermanagement.LSusergroupedcolumns;
import com.agaram.eln.primary.model.usermanagement.LSusergrouprights;
import com.agaram.eln.primary.model.usermanagement.LSusershowhidecolumns;
import com.agaram.eln.primary.model.usermanagement.LSusersteam;
import com.agaram.eln.primary.model.usermanagement.LoggedUser;
import com.agaram.eln.primary.model.usermanagement.Lsusersettings;
import com.agaram.eln.primary.service.cfr.AuditService;
import com.agaram.eln.primary.service.cloudFileManip.CloudFileManipulationservice;
import com.agaram.eln.primary.service.fileManipulation.FileManipulationservice;
import com.agaram.eln.primary.service.usermanagement.UserService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/User", method = RequestMethod.POST)
public class UserController {

	@Autowired
	private org.springframework.core.env.Environment env;
	@Autowired
    private UserService userService;
	@Autowired
	private AuditService auditService;
	
	@Autowired
	private FileManipulationservice fileManipulationservice;
	
	@Autowired
	private CloudFileManipulationservice cloudFileManipulationservice;
	/**
	 * UserGroup
	 * 
	 * @param objusergroup
	 * @return
	 */
	Map<String, Object> map=new HashMap<>();
	
	@PostMapping("/InsertUpdateUserGroup")
	public LSusergroup InsertUpdateUserGroup(@RequestBody LSusergroup objusergroup)throws Exception
	{
		return userService.InsertUpdateUserGroup(objusergroup);
	}
	
	@PostMapping("/InsertUpdateUserGroupFromSDMS")
	public LSusergroup InsertUpdateUserGroupFromSDMS(@RequestBody LSusergroup objusergroup)throws Exception
	{
		if(objusergroup.getObjuser() != null) {
			
			LSuserMaster userClass = auditService.CheckUserPassWord(objusergroup.getObjuser());
			
			if(userClass.getObjResponse().getStatus()) {
				
				objusergroup.setLSuserMaster(userClass);
				
				return userService.InsertUpdateUserGroupFromSDMS(objusergroup);
			}
			else
			{
				objusergroup.setResponse(new Response());
				objusergroup.getResponse().setStatus(false);
				objusergroup.getResponse().setInformation("ID_VALIDATION");
				return objusergroup;
			}
			
		}
		return userService.InsertUpdateUserGroupFromSDMS(objusergroup);
	}
	
	@PostMapping("/GetUserGroup")
	public List<LSusergroup> GetUserGroup(@RequestBody LSuserMaster objusermaster)throws Exception
	{
	  return userService.GetUserGroup(objusermaster);
	}
	
	@PostMapping("/getMultiUserGroup")
	public Map<String, Object> getMultiUserGroup(@RequestBody LSuserMaster objusermaster)throws Exception
	{
	  return userService.getMultiUserGroup(objusermaster);
	}
	
	@PostMapping("/GetActiveUserGroup")
	public List<LSusergroup> GetActiveUserGroup(@RequestBody LSuserMaster objusermaster)throws Exception
	{
	  return userService.GetActiveUserGroup(objusermaster);
	}

	@PostMapping("/GetSiteWiseUserGroup")
	public List<LSusergroup> GetSiteWiseUserGroup(@RequestBody LSSiteMaster objclass)throws Exception
	{
	  return userService.GetSiteWiseUserGroup(objclass);
	}
	
	@PostMapping("/GetSiteWiseActiveUserGroup")
	public List<LSusergroup> GetSiteWiseActiveUserGroup(@RequestBody LSSiteMaster Objclass)throws Exception {
		return userService.GetSiteWiseActiveUserGroup(Objclass);
	}
	
	@PostMapping("/GetLoggedUsersTeamSitewise")
	public List<LSusersteam> GetLoggedUsersTeamSitewise(@RequestBody Map<String, Object> mapObject)throws Exception {
		final ObjectMapper mapper = new ObjectMapper();
		final LSuserMaster userobj = mapper.convertValue(mapObject.get("lsusermaster"), LSuserMaster.class);
		final LSSiteMaster site = mapper.convertValue(mapObject.get("lssitemaster"), LSSiteMaster.class);

		return userService.GetLoggedUsersTeamSitewise(site,userobj);
	}
	
	@PostMapping("/GetUserGroupSiteWise")
	public List<LSusergroup> GetUserGroupSiteWise(@RequestBody LSSiteMaster objclass)throws Exception
	{
	  return userService.GetUserGroupSiteWise(objclass);
	}
		
	
//	@PostMapping("/ActDeactUserGroup")
//	public LSusergroup ActDeactUserGroup(@RequestBody LSusergroup objusergroup)
//	{
//		return userService.ActDeactUserGroup(objusergroup);
//	}

	/**
	 * UserMaster
	 * 
	 * @param objuser
	 * @return
	 * @throws MessagingException
	 * @throws com.aspose.pdf.internal.imaging.internal.Exceptions.IO.IOException 
	 * @throws javax.mail.MessagingException 
	 */
	@PostMapping("/InsertUpdateUser")
	public LSuserMaster InsertUpdateUser(@RequestBody LSuserMaster objusermaster) throws MessagingException, JsonParseException, JsonMappingException, IOException, com.aspose.pdf.internal.imaging.internal.Exceptions.IO.IOException, MessagingException {

		return userService.InsertUpdateUser(objusermaster);
	}
	
	@PostMapping("/InsertUpdateUserfromSDMS")
	public LSuserMaster InsertUpdateUserfromSDMS(@RequestBody LSuserMaster objusermaster)throws Exception
	{
		if(objusermaster.getObjuser() != null) {
			if(objusermaster.getUserstatus().trim() == "Active") {
				objusermaster.setUserstatus("A");
			} else if(objusermaster.getUserstatus().trim() == "Deactive") {
				objusermaster.setUserstatus("D");
			}
			else {
				objusermaster.setUserstatus("Locked");
			}
			LSuserMaster userClass = auditService.CheckUserPassWord(objusermaster.getObjuser());
			
			if(userClass.getObjResponse().getStatus()) {
				
//				objusermaster.setUserClass(userClass);
				
				return userService.InsertUpdateUserfromSDMS(objusermaster);
			}
			else
			{
				objusermaster.setResponse(new Response());
				objusermaster.getResponse().setStatus(false);
				objusermaster.getResponse().setInformation("ID_VALIDATION");
				return objusermaster;
			}
			
		}
		return userService.InsertUpdateUserfromSDMS(objusermaster);
	}
	
	@PostMapping("/ResetPassword")
	public LSuserMaster ResetPassword(@RequestBody LSuserMaster objuser)throws Exception
	{
		return userService.ResetPassword(objuser);
	}
	
	@PostMapping("/GetUsers")
	public List<LSuserMaster> GetUsers(@RequestBody LSuserMaster objusermaster)throws Exception
	{
		return userService.GetUsers(objusermaster);
	}
	
	@PostMapping("/GetUsersregister")
	public List<LSuserMaster> GetUsersregister(@RequestBody LSuserMaster objusermaster)throws Exception
	{
		return userService.GetUsersregister(objusermaster);
	}
	
	@PostMapping("/GetUsersOnsite")
	public List<LSuserMaster> GetUsersOnsite(@RequestBody LSSiteMaster objclass)throws Exception
	{
		return userService.GetUsersOnsite(objclass);
	}
	/**
	 * Project Team
	 * 
	 * @param objteam
	 * @return
	 */
	@PostMapping("/InsertUpdateTeam")
	public LSusersteam InsertUpdateTeam(@RequestBody LSusersteam objteam)throws Exception
	{
		return userService.InsertUpdateTeam(objteam);
	}
	
	@PostMapping("/GetUserTeam")
	public List<LSusersteam> GetUserTeam(@RequestBody LSuserMaster LSuserMaster)throws Exception
	{
		return userService.GetUserTeam(LSuserMaster);
	}
	
	@PostMapping("/GetActiveUserTeam")
	public List<LSusersteam> GetActiveUserTeam(@RequestBody LSuserMaster LSuserMaster)throws Exception
	{
		return userService.GetActiveUserTeam(LSuserMaster);
	}
	
	@PostMapping("/GetUserTeamonSitevise")
	public  Map<String,Object> GetUserTeamonSitevise(@RequestBody LSSiteMaster objclass)throws Exception
	{
		return userService.GetUserTeamonSitevise(objclass);
	}
	
	@PostMapping("/GetUserRightsonGroup")
	public Map<String, Object> GetUserRightsonGroup(@RequestBody LSusergroup lsusergroup)throws Exception
	{
		return userService.GetUserRightsonGroup(lsusergroup);
	}
	
	@PostMapping("/GetUserRightsonUser")
	public Map<String, Object> GetUserRightsonUser(@RequestBody LSuserMaster objUser)throws Exception
	{
		return userService.GetUserRightsonUser(objUser);
	}
	
	@PostMapping("/GetUserActiveStatus")
	public Integer GetUserActiveStatus(@RequestBody Map<String, Integer> body)throws Exception
	{
		Integer usercode = body.get("usercode");
		return userService.GetUserActiveStatus(usercode);
	}
	
	@PostMapping("/SaveUserRights")
	public List<LSusergrouprights> SaveUserRights(@RequestBody LSusergrouprights[] lsrights)throws Exception
	{
		return userService.SaveUserRights(lsrights);
	}
	
	@PostMapping("/GetActiveUsers")
	public List<LSactiveUser> GetActiveUsers(@RequestBody LSuserMaster lsuserMaster)throws Exception
	{
		return userService.GetActiveUsers(lsuserMaster);
	}
	
	@PostMapping("/GetActiveUsersOnsitewise")
	public List<LSactiveUser> GetActiveUsersOnsitewise(@RequestBody LSSiteMaster objclass)throws Exception
	{
		return userService.GetActiveUsersOnsitewise(objclass);
	}
	
	@PostMapping("/ValidateSignature")
	public LSuserMaster ValidateSignature(@RequestBody LoggedUser objuser)throws Exception {
		return userService.ValidateSignature(objuser);
	}

	
	@PostMapping("/PasswordpolicySave")
	public LSPasswordPolicy PasswordpolicySave(@RequestBody LSPasswordPolicy lspasswordpolicy)throws Exception
	{
		return userService.PasswordpolicySave(lspasswordpolicy);
	}
	
	@PostMapping("/GetPasswordPolicy")
	public LSPasswordPolicy GetPasswordPolicy(@RequestBody LSPasswordPolicy lspasswordpolicy)throws Exception
	{
		return userService.GetPasswordPolicy(lspasswordpolicy);
	}
	
	@PostMapping("/GetUserslocalnonRetired")
	public List<LSuserMaster> GetUserslocalnonRetired(@RequestBody LSuserMaster objusermaster)throws Exception
	{
		return userService.GetUserslocalnonRetired(objusermaster);
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	@PostMapping("/GetLoginPasswordPolicy")
	public LSPasswordPolicy GetLoginPasswordPolicy(@RequestBody Map<String, Object> mapObject)throws Exception
	{
		final ObjectMapper mapper = new ObjectMapper();	
        Map<String, Object> obj = (Map<String, Object>) mapObject.get("passObjDet");
  
    
        String sitecode = (String) mapObject.get("sitecode");
		  

		  final int sitecodekey = Integer.parseInt(sitecode);
		  
		return userService.GetLoginPasswordPolicy(sitecodekey);
	}
	
	@PostMapping("/GetPasswordPolicySitewise")
	public LSPasswordPolicy GetPasswordPolicySitewise(@RequestBody LSPasswordPolicy objpwd)throws Exception {

		
		return userService.GetPasswordPolicySitewise(objpwd);
		
	}

	@PostMapping("/Uploadprofilepic")
    public ProfilePicture Uploadprofilepic(@RequestParam MultipartFile file,
    		@RequestParam Integer usercode, @RequestParam("date") Date currentdate)throws Exception {
        
		ProfilePicture profilePicture = new ProfilePicture();
        try {
        	profilePicture = fileManipulationservice.addPhoto(usercode, file,currentdate);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
        return profilePicture;
    }
	
	
	@PostMapping("/CloudUploadprofilepic")
    public CloudProfilePicture CloudUploadprofilepic(@RequestParam MultipartFile file,
    		@RequestParam Integer usercode, @RequestParam("date") Date currentdate)throws Exception {
        
		CloudProfilePicture profilePicture = new CloudProfilePicture();
        try {
        	profilePicture = cloudFileManipulationservice.addPhoto(usercode, file,currentdate);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
        return profilePicture;
    }
	
	@PostMapping("/CloudUploadusersignature")
    public CloudUserSignature CloudUploadusersignature(@RequestParam MultipartFile file, @RequestParam Integer usercode
    		, @RequestParam String username , @RequestParam("date") Date currentdate)throws Exception {
        
		CloudUserSignature usersignature = new CloudUserSignature();
        try {
        	usersignature = cloudFileManipulationservice.addusersignature(usercode, username, file,currentdate);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
        return usersignature;
    }


	@PostMapping("/Getprofilepic")
    public ProfilePicture Getprofilepic(@RequestBody ProfilePicture profilePicture)throws Exception
    {
		return fileManipulationservice.getPhoto(profilePicture.getId());
    }
	
	@PostMapping("/CloudGetprofilepic")
    public CloudProfilePicture CloudGetprofilepic(@RequestBody CloudProfilePicture profilePicture)throws Exception
    {
		return cloudFileManipulationservice.getPhoto(profilePicture.getId());
    }
	
	@PostMapping("/DeleteProfilepic")
    public ProfilePicture DeleteProfilepic(@RequestBody ProfilePicture profilePicture)throws Exception
    {
		fileManipulationservice.deletePhoto(profilePicture.getId(),profilePicture.getObjsilentaudit());
		return profilePicture;
    }
	
	@PostMapping("/CloudDeleteProfilepic")
    public CloudProfilePicture CloudDeleteProfilepic(@RequestBody CloudProfilePicture profilePicture)throws Exception
    {
		cloudFileManipulationservice.deletePhoto(profilePicture.getId(),profilePicture.getObjsilentaudit());
		return profilePicture;
    }
	
	@GetMapping("profile/{fileid}")
	public ResponseEntity<InputStreamResource> downloadlargeattachment(@PathVariable Integer fileid) throws IllegalStateException, IOException {
		ProfilePicture objprofile = fileManipulationservice.getPhoto(fileid);
		
		byte[] data = null;
		
		if(objprofile != null)
		{
			data = objprofile.getImage().getData();
		}
		else
		{
			data = StreamUtils.copyToByteArray(new ClassPathResource("images/userimg.jpg").getInputStream());
		}
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		
	    HttpHeaders header = new HttpHeaders();
	    header.setContentType(MediaType.parseMediaType("image/png"));
	    header.set("Content-Disposition", "attachment; filename=gg.pdf");
	    
	    return new ResponseEntity<>(new InputStreamResource(bis), header, HttpStatus.OK);
	}
	
	@GetMapping("Cloudprofile/{fileid}")
	public ResponseEntity<InputStreamResource> Clouddownloadlargeattachment(@PathVariable Integer fileid) throws IllegalStateException, IOException {
		CloudProfilePicture objprofile = cloudFileManipulationservice.getPhoto(fileid);
		
		byte[] data = null;
		
		if(objprofile != null)
		{
			data = objprofile.getImage().getData();
		}
		else
		{
			data = StreamUtils.copyToByteArray(new ClassPathResource("images/userimg.jpg").getInputStream());
		}
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		
	    HttpHeaders header = new HttpHeaders();
	    header.setContentType(MediaType.parseMediaType("image/png"));
	    header.set("Content-Disposition", "attachment; filename=gg.pdf");
	    
	    return new ResponseEntity<>(new InputStreamResource(bis), header, HttpStatus.OK);
	}
	
	@GetMapping("Cloudsignatur/{fileid}")
	public ResponseEntity<InputStreamResource> Cloudsignatur(@PathVariable Integer fileid) throws IllegalStateException, IOException {
		CloudUserSignature objsignature = cloudFileManipulationservice.getSignature(fileid);
		
		byte[] data = null;
		
		if(objsignature != null)
		{
			data = objsignature.getImage().getData();
		}
		else
		{
			data = StreamUtils.copyToByteArray(new ClassPathResource("images/nosignature.png").getInputStream());
		}
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		
	    HttpHeaders header = new HttpHeaders();
	    header.setContentType(MediaType.parseMediaType("image/png"));
	    header.set("Content-Disposition", "attachment; filename=gg.pdf");
	    
	    return new ResponseEntity<>(new InputStreamResource(bis), header, HttpStatus.OK);
	}
	
	@GetMapping("ELNSignature/{fileid}")
	public ResponseEntity<InputStreamResource> ELNSignature(@PathVariable Integer fileid) throws IllegalStateException, IOException {
		UserSignature objprofile = fileManipulationservice.getsignature(fileid);
		
		byte[] data = null;
		
		if(objprofile != null)
		{
			data = objprofile.getImage().getData();
		}
		else
		{
			data = StreamUtils.copyToByteArray(new ClassPathResource("images/userimg.jpg").getInputStream());
		}
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		
	    HttpHeaders header = new HttpHeaders();
	    header.setContentType(MediaType.parseMediaType("image/png"));
	    header.set("Content-Disposition", "attachment; filename=gg.pdf");
	    
	    return new ResponseEntity<>(new InputStreamResource(bis), header, HttpStatus.OK);
	}
	
	@PostMapping("/UploadELNUserSignature")
    public UserSignature UploadELNUserSignature1(@RequestParam MultipartFile file,
    		@RequestParam Integer usercode, @RequestParam("date") Date currentdate)throws Exception {
        
		UserSignature UserSignature = new UserSignature();
        try {
        	UserSignature = fileManipulationservice.addsignature(usercode, file,currentdate);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
        return UserSignature;
    }
	
	@PostMapping("/Getnotification")
	public Map<String, Object> Getnotification(@RequestBody LSnotification lsnotification)throws Exception
	{
		return userService.Getnotification(lsnotification);
	}
	
	@PostMapping("/Updatenotificationread")
	public Map<String, Object> Updatenotificationread(@RequestBody LSnotification lsnotification)throws Exception
	{
		return userService.Updatenotificationread(lsnotification);
	}
	
	@PostMapping("/GetnotificationonLazyload")
	public Map<String, Object> GetnotificationonLazyload(@RequestBody LSnotification lsnotification)throws Exception
	{
		return userService.GetnotificationonLazyload(lsnotification);
	}
	
	@PostMapping("/GetLatestnotification")
	public Map<String, Object> GetLatestnotification(@RequestBody LSnotification lsnotification)throws Exception
	{
		return userService.GetLatestnotification(lsnotification);
	}
	
	@PostMapping("/GetLatestnotificationcount")
	public Map<String, Object> GetLatestnotificationcount(@RequestBody LSnotification lsnotification)throws Exception
	{
		return userService.GetLatestnotificationcount(lsnotification);
	}
	
	@PostMapping("/UpdateUseraction")
	public LSuserActions UpdateUseraction(@RequestBody LSuserActions objuseractions)throws Exception {
		return userService.UpdateUseraction(objuseractions);
	}
	
	@PostMapping("/UpdatefreshUseraction")
	public LSuserActions UpdatefreshUseraction(@RequestBody LSuserActions objuseractions)throws Exception {
		return userService.UpdatefreshUseraction(objuseractions);
	}
	
	@GetMapping("/Loadtenantusergroups")
	public List<LSusergroup> Loadtenantusergroups(HttpServletRequest request)throws Exception {
		return userService.Loadtenantusergroups();
	}
	
	@GetMapping("/Createcentraliseduser")
	public LScentralisedUsers Createcentraliseduser(@RequestBody LScentralisedUsers objctrluser)throws Exception
	{
		return userService.Createcentraliseduser(objctrluser);
	}

	@PostMapping("/Usersendpasswormail")
	public LSuserMaster Usersendpasswormail(@RequestBody LSuserMaster objusermaster) throws MessagingException, MessagingException
	{
		return userService.Usersendpasswormail(objusermaster);
		
	}

	@GetMapping("/Getallcentraliseduser")
	public List<LScentralisedUsers> Getallcentraliseduser(@RequestBody LScentralisedUsers objctrluser)throws Exception
	{
		return userService.Getallcentraliseduser(objctrluser);
	}
	
	@GetMapping("/Getcentraliseduserbyid")
	public LScentralisedUsers Getcentraliseduserbyid(@RequestBody LScentralisedUsers objctrluser)throws Exception {
		return userService.Getcentraliseduserbyid(objctrluser);
	}
	
	@PostMapping("/GetUserslocal")
	public List<LSuserMaster> GetUserslocal(@RequestBody LSuserMaster objusermaster)throws Exception
	{
		return userService.GetUserslocal(objusermaster);
	}
	
	@PostMapping("/getUserOnCode")
	public LSuserMaster getUserOnCode(@RequestBody LSuserMaster objuser)throws Exception
	{
		return userService.getUserOnCode(objuser);
	}
	
	@PostMapping("/validatemailaddress")
	public Response validatemailaddress(@RequestBody String mailaddress)throws Exception
	{
		Response objresponse =  new Response();
		objresponse.setStatus(SMTPMailvalidation.isAddressValid(mailaddress));
		return objresponse;
	}
	
	@PostMapping("/updateUserDateFormat")
	public Lsusersettings updateUserDateFormat(@RequestBody Lsusersettings objuser)throws Exception
	{
		return userService.updateUserDateFormat(objuser);
	}
	
	@PostMapping("/GetAllActiveUsers")
	public List<LSuserMaster> GetAllActiveUsers(@RequestBody LSuserMaster objusergroup)throws Exception
	{
		return userService.GetAllActiveUsers(objusergroup);
	}
	
	@PostMapping("/getUserPrefrences")
	public Lsusersettings getUserPrefrences(@RequestBody LSuserMaster objuser)throws Exception
	{
		return userService.getUserPrefrences(objuser);
	}
	
	@PostMapping("/setGroupedcolumn")
	public LSusergroupedcolumns setGroupedcolumn(@RequestBody LSusergroupedcolumns objgroupped)throws Exception
	{
		return userService.setGroupedcolumn(objgroupped);
	}
	
	@PostMapping("/getGroupedcolumn")
	public LSusergroupedcolumns getGroupedcolumn(@RequestBody LSusergroupedcolumns objgroupped)throws Exception
	{
		return userService.getGroupedcolumn(objgroupped);
	}
	
	@PostMapping("/getUsersManinFormLicenseStatus")
	public Boolean getUsersManinFrameLicenseStatus(@RequestBody LSSiteMaster objsite)throws Exception
	{
		return userService.getUsersManinFrameLicenseStatus(objsite);
	}
	
	@PostMapping("/Notificationmarkallasread")
	public Boolean Notificationmarkallasread(@RequestBody LSuserMaster lsuserMaster)throws Exception
	{
		return userService.Notificationmarkallasread(lsuserMaster);
	}
	
	@PostMapping("/getActiveUserCount")
	public Map<String, Object> getActiveUserCount(@RequestBody Map<String, Object> objMap) {
	 return userService.getActiveUserCount(objMap);
	}
	
	@PostMapping("/InsertImportedlist")
	public Listofallmaster InsertImportedlist(@RequestBody Listofallmaster listofallmaster) throws MessagingException {

		return userService.InsertImportedlist(listofallmaster);
	}
	
	@PostMapping("/GetUsersonprojectbased")
	public List<LSuserMaster> GetUsersonprojectbased(@RequestBody LSprojectmaster objusermaster)throws Exception
	{
		return userService.GetUsersonprojectbased(objusermaster);
	}
	
	@PostMapping("/GetSiteWiseUserGrouplist")
	public Map<String, Object> GetSiteWiseUserGrouplist(@RequestBody LSuserMaster objclass)throws Exception
	{
	  return userService.GetSiteWiseUserGrouplist(objclass);
	}
	
	@PostMapping("/getUsersinglOnCode")
	public Map<String, Object> getUsersinglOnCode(@RequestBody LSuserMaster objuser)throws Exception
	{
		return userService.getUsersinglOnCode(objuser);
	}
	
	@PostMapping("/getmultiusergroup")
	public List<LSusergroup> getmultiusergroup(@RequestBody LSuserMaster objuser)throws Exception
	{
		return userService.getmultiusergroup(objuser);
	}
	
	@PostMapping("/getTemplateStatus")
	public Map<String, Object> GetTemplateStatus(@RequestBody Map<String, Object> obj) {
		return userService.GetTemplateStatus(obj);
	}
	
	@PostMapping("/updateProfileDetails")
	public Map<String, Object> updateProfileDetails(@RequestBody Map<String, Object> obj) {
		return userService.updateProfileDetails(obj);
	}
	
	@PostMapping("/setShowHideolumn")
	public LSusershowhidecolumns setShowHideolumn(@RequestBody LSusershowhidecolumns objgroupped)throws Exception
	{
		return userService.setShowHideolumn(objgroupped);
	}
	
	@PostMapping("/getShowHideolumn")
	public LSusershowhidecolumns getShowHideolumn(@RequestBody LSusershowhidecolumns objgroupped)throws Exception
	{
		return userService.getShowHideolumn(objgroupped);
	}
	
	@PostMapping("/InsertImportUserMaster")
	public ResponseEntity<List<LSuserMaster>> InsertImportUserMaster(@RequestBody Map<String, Object> mapObjects) throws ParseException{
		return userService.InsertImportUserMaster(mapObjects);
	}
	
	@PostMapping("/sendEmailListWithBatch")
	public List<LSuserMaster> sendEmailListWithBatch(@RequestBody Map<String, Object> objusermaster) throws MessagingException, MessagingException
	{
		return userService.sendEmailListWithBatch(objusermaster);
		
	}
	
	@PostMapping("/insertImportUserGroup")
	public List<LSusergroup> insertImportUserGroup (@RequestBody Map<String, Object> objusergroup) throws ParseException{
		return userService.insertImportUserGroup(objusergroup);
	}
	
	@PostMapping("/getDesignation")
	public List<LSuserMaster> getDesignation(@RequestBody Map<String, Object> obj) throws Exception {
		return userService.getDesignation(obj);
	}
	
//	@GetMapping("/testmail")
//    public String sendTestMail() {	
//	        try {
//	        	
//	        	 String from = env.getProperty("spring.mail.username");
//	        	    
//	        	    // ✅ FIX 1: Properties BEFORE JavaMailSender
//	        	    Properties props = new Properties();
//	        	    props.put("mail.smtp.host", "smtp.office365.com");
//	        	    props.put("mail.smtp.port", "587");
//	        	    props.put("mail.smtp.auth", "true");
//	        	    props.put("mail.smtp.starttls.enable", "true");     // CRITICAL
//	        	    props.put("mail.smtp.starttls.required", "true");
//	        	    props.put("mail.smtp.ssl.protocols", "TLSv1.2");
//	        	    props.put("mail.debug", "true");
//	        	    
//	        	    JavaMailSenderImpl mailSender12 = new JavaMailSenderImpl();
//	        	    mailSender12.setJavaMailProperties(props);  // Apply BEFORE host/port
//	        	    mailSender12.setUsername("saas@agaramtech.net");
//	        	    mailSender12.setPassword("fhxxsmnxrbtyqwvc");
//	        	    
//	        	    // ✅ FIX 2: Use mailSender1 consistently (not mailSender)
//	        	    MimeMessage message = mailSender12.createMimeMessage();
//	        	    MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//	        	    helper.setFrom("saas@agaramtech.net");
//	        	    helper.setTo("santhoshkumar.s@agaramtech.com");
//	        	    helper.setSubject("git mail");
//	        	    helper.setText("mail content ok ", true);
//	        	    
//	        	    mailSender12.send(message);  // ✅ Fixed variable name
////	        	    EmailRepository.save(email);
////	        	    return email;
//	        	    
//	        	    
//	            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//	            // 🔹 SMTP BASIC CONFIG
//	            mailSender.setHost("smtp.office365.com");
//	            mailSender.setPort(587);
//	            mailSender.setUsername("saas@agaramtech.com");
//	            mailSender.setPassword("bvcmkjxlygnppjf");
//	            // 🔹 SMTP PROPERTIES
//	            Properties props1 = mailSender.getJavaMailProperties();
//	            props1.put("mail.transport.protocol", "smtp");
//	            props1.put("mail.smtp.auth", "true");
//	            props1.put("mail.smtp.starttls.enable", "true");
//	            props1.put("mail.smtp.starttls.required", "true");
//	            props1.put("mail.smtp.ssl.protocols", "TLSv1.2");
////	            props.put("mail.smtp.ssl.enable","false");
//	            props1.put("mail.debug", "true");
//	            // 🔹 CREATE MAIL
//	            MimeMessage message1 = mailSender.createMimeMessage();
//	            MimeMessageHelper helper1 =
//	                    new MimeMessageHelper(message1, true, "UTF-8");
//
//	            helper1.setFrom("saas@agaramtech.com");
//	            helper1.setTo("santhoshkumar.s@agaramtech.com");
//	            helper1.setSubject("INLINE SMTP TEST");
//	            helper1.setText("""
//	                    <h2>Mail Test Successful ✅</h2>
//	                    <p>This mail was sent using inline SMTP config.</p>
//	                    """, true);
//
//	            mailSender.send(message1);
//	            
//	            String to = "santhoshkumar.s@agaramtech.com";
//				String from1 = env.getProperty("spring.mail.username");
//				String password = env.getProperty("spring.mail.password");
//				String host = env.getProperty("spring.mail.host");
//				String port = env.getProperty("spring.mail.port");
//
//				// ✅ FIX 1: Properties BEFORE JavaMailSender
//				Properties props11 = new Properties();
//				props11.put("mail.smtp.host", host);
//				props11.put("mail.smtp.port", port);
//				props11.put("mail.smtp.auth", "true");
//				props11.put("mail.smtp.starttls.enable", "true"); // CRITICAL
//				props11.put("mail.smtp.starttls.required", "true");
//				props11.put("mail.smtp.ssl.protocols", "TLSv1.2");
//				props11.put("mail.debug", "true");
//
//				JavaMailSenderImpl mailSender1 = new JavaMailSenderImpl();
//				mailSender1.setJavaMailProperties(props11); // Apply BEFORE host/port
//				mailSender1.setUsername(from1);
//				mailSender1.setPassword(password);
//
//				// ✅ FIX 2: Use mailSender consistently (not mailSender)
//				MimeMessage message11 = mailSender1.createMimeMessage();
//				MimeMessageHelper helper11 = new MimeMessageHelper(message11, true, "UTF-8");
//				helper11.setFrom(from1);
//				helper11.setTo(to);
//				helper11.setSubject("INLINE SMTP TEST");
//				helper11.setText("saas user created", true);
//
//				mailSender1.send(message11); // ✅ Fixed variable name
////				EmailRepository.save(email);
//				return " Mail done";
//
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            return "❌ Mail failed: " + e.getMessage();
//	        }
//	    
//	}
}

