package com.agaram.eln.primary.controller.report;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.agaram.eln.primary.model.cfr.LScfttransaction;
import com.agaram.eln.primary.model.general.OrderCreation;
import com.agaram.eln.primary.model.general.Response;
import com.agaram.eln.primary.model.instrumentDetails.LSlogilablimsorderdetail;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;
import com.agaram.eln.primary.model.usermanagement.LoggedUser;
import com.agaram.eln.primary.service.cfr.AuditService;
import com.agaram.eln.primary.service.report.ReportsService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.storage.StorageException;

@RestController
@RequestMapping(value = "/reports", method = RequestMethod.POST)
public class ReportsController {

	@Autowired
	private ReportsService ObjReportsService;

	@Autowired
	private AuditService auditService;
	
	

	@PostMapping("/createNewReportDocx")
	protected Map<String, Object> createNewDocxs(@RequestBody Map<String, Object> argObj) throws ServletException, IOException {
		Map<String, Object> mapObj = new HashMap<String, Object>();
		try {
			LoggedUser objuser = new LoggedUser();
			ObjectMapper mapper = new ObjectMapper();
			Response objResponse = new Response();
			LScfttransaction objsilentaudit = new LScfttransaction();
			LScfttransaction objmanualaudit = new LScfttransaction();
			if(argObj.containsKey("objuser"))
			{
				objuser = mapper.convertValue(argObj.get("objuser"),LoggedUser.class);
			}
			
			if(argObj.containsKey("objsilentaudit"))
			{
				objsilentaudit = mapper.convertValue(argObj.get("objsilentaudit"),LScfttransaction.class);
			}
			if(argObj.containsKey("objmanualaudit"))
			{
				objmanualaudit = mapper.convertValue(argObj.get("objmanualaudit"),LScfttransaction.class);
			}
			
			if(objuser.getsUsername() != null) {
				
				LSuserMaster userClass = auditService.CheckUserPassWord(objuser);
				
				if(userClass.getObjResponse().getStatus()) {
					mapObj = ObjReportsService.createNewReportDocx(argObj);
				}
				else
				{
					objsilentaudit.setComments("Entered invalid username and password");
					Map<String, Object> map=new HashMap<>();
					map.put("objsilentaudit",objsilentaudit);
					map.put("objmanualaudit",objmanualaudit);
					map.put("objUser",objuser);
					auditService.AuditConfigurationrecord(map);
					objResponse.setStatus(false);
					objResponse.setInformation("ID_VALIDATION");
					mapObj.put("objResponse", objResponse);
				}
				
			}else {
			mapObj = ObjReportsService.createNewReportDocx(argObj);
			}
		} catch (Exception e) {
			
			mapObj.put("error", e.getMessage());
		}
		return mapObj;
	}

	@GetMapping(value = "/saveDocxsReport")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			ObjReportsService.saveDocxsReport(request, response);
			
		} catch (Exception e) {
			
		}
	}

	@GetMapping(value = "/cloudsaveDocxsReport")
	protected void clouddoPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			ObjReportsService.cloudsaveDocxsReport(request, response);
			
		} catch (Exception e) {
			
		}
	}
	
	@PostMapping(value = "/uploadDocxFile")
	protected Map<String, Object> uploadDocxFile(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
//	protected Map<String, Object> uploadDocxFile(MultipartHttpServletRequest request,@RequestBody Map<String, Object> argObj) throws Exception {
		Map<String, Object> map = new HashMap<>();
		ObjectMapper objMap = new ObjectMapper();
		Map<String, Object> argObj = objMap.readValue(request.getParameter("serviceObj"), new TypeReference<Map<String, Object>>() {}) ;
		System.out.println(request.getParameter("serviceObj"));
		map = ObjReportsService.uploadDocxFile(request, argObj);
		
		return map;
	}

	@PostMapping(value = "/updateReportDocxName")
	protected Map<String, Object> updateReportDocxName(@RequestBody Map<String, Object> obj)
			throws ServletException, IOException {
		Map<String, Object> mapObj = new HashMap<String, Object>();
		try {
			LoggedUser objuser = new LoggedUser();
			ObjectMapper mapper = new ObjectMapper();
			Response objResponse = new Response();
			LScfttransaction objsilentaudit = new LScfttransaction();
			LScfttransaction objmanualaudit = new LScfttransaction();
			
			if(obj.containsKey("objsilentaudit"))
			{
				objsilentaudit = mapper.convertValue(obj.get("objsilentaudit"),LScfttransaction.class);
			}
			if(obj.containsKey("objmanualaudit"))
			{
				objmanualaudit = mapper.convertValue(obj.get("objmanualaudit"),LScfttransaction.class);
			}
			if(obj.containsKey("objuser"))
			{
				objuser = mapper.convertValue(obj.get("objuser"),LoggedUser.class);
			}
			if(objuser.getsUsername() != null) {
				
				LSuserMaster userClass = auditService.CheckUserPassWord(objuser);
				
				if(userClass.getObjResponse().getStatus()) {
					mapObj = ObjReportsService.updateReportDocxName(obj);
				}
				else
				{
					objsilentaudit.setComments("Entered invalid username and password");
					Map<String, Object> map=new HashMap<>();
					map.put("objsilentaudit",objsilentaudit);
					map.put("objmanualaudit",objmanualaudit);
					map.put("objUser",objuser);
					auditService.AuditConfigurationrecord(map);
					objResponse.setStatus(false);
					objResponse.setInformation("ID_VALIDATION");
					mapObj.put("objResponse", objResponse);
				}
				
			}else {
			mapObj = ObjReportsService.updateReportDocxName(obj);
			}
		} catch (Exception e) {
			
			mapObj.put("error", e.getMessage());
		}
		return mapObj;
	}

	@PostMapping(value = "/getLSdocreportsLst")
	protected Map<String, Object> getLSdocreportsLst(@RequestBody Map<String, Object> argObj) throws IOException {
		Map<String, Object> mapObj = new HashMap<String, Object>();
		mapObj = ObjReportsService.getLSdocreportsLst("all");
		ObjReportsService.insertSilentAudit(argObj);
		return mapObj;
	}

	@PostMapping(value = "/getDocxDirectoryLst")
	protected Map<String, Object> getDocxDirectoryLst(@RequestBody Map<String, Object> argObj) throws IOException {
		Map<String, Object> mapObj = new HashMap<String, Object>();
		mapObj = ObjReportsService.getDocxDirectoryLst();
//		ObjReportsService.insertSilentAudit(argObj);
		return mapObj;
	}

	@PostMapping(value = "/getDownloadReportsInitRequest")
	protected Map<String, Object> getDownloadReportsInitRequest(@RequestBody Map<String, Object> argMap) throws IOException {
		Map<String, Object> mapObj = new HashMap<String, Object>();
		mapObj.putAll(ObjReportsService.getDownloadReportsInitRequest(argMap));
		return mapObj;
	}
	
	@PostMapping(value = "/getDownloadReportsTemplateInitRequest")
	protected Map<String, Object> getDownloadReportsTemplateInitRequest() throws IOException {
		Map<String, Object> mapObj = new HashMap<String, Object>();
		mapObj.put("DocxDirectoryLst", ObjReportsService.getDocxDirectoryLst());
		mapObj.put("DocxReportLst", ObjReportsService.getLSdocreportsLst("isTemplate"));
		return mapObj;
	}

	@PostMapping(value = "/addDocxDirectory")
	protected Map<String, Object> addDocxDirectory(@RequestBody Map<String, Object> Obj) throws IOException {
		Map<String, Object> mapObj = new HashMap<String, Object>();
		mapObj = ObjReportsService.addDocxDirectory(Obj);
		return mapObj;
	}
	
	@PostMapping(value = "/updateDocxDirectory")
	protected Map<String, Object> updateDocxDirectory(@RequestBody Map<String, Object> Obj) throws IOException {
		Map<String, Object> mapObj = new HashMap<String, Object>();
		mapObj = ObjReportsService.updateDocxDirectory(Obj);
		return mapObj;
	}

	@PostMapping(value = "/renameDeleteDocxDirectory")
	protected Map<String, Object> renameDeleteDocxDirectory(@RequestBody Map<String, Object> Obj) throws IOException {
		Map<String, Object> mapObj = new HashMap<String, Object>();
		mapObj = ObjReportsService.renameDeleteDocxDirectory(Obj);
		return mapObj;
	}
	
	@PostMapping(value = "/getReportDocxInfo")
	protected Map<String, Object> getReportDocxInfo(@RequestBody Map<String, Object> Obj) throws IOException {
		Map<String, Object> mapObj = new HashMap<String, Object>();
		mapObj = ObjReportsService.getReportDocxInfo(Obj);
		return mapObj;
	}
	
	@PostMapping(value = "/getCloudReportDocxInfo")
	protected Map<String, Object> getCloudReportDocxInfo(@RequestBody Map<String, Object> Obj) throws IOException {
		Map<String, Object> mapObj = new HashMap<String, Object>();
		mapObj = ObjReportsService.getCloudReportDocxInfo(Obj);
		return mapObj;
	}

	@PostMapping(value = "/test")
	protected String simple() throws IOException {
		return "working";
	}

	@PostMapping(value = "/testloadftp")
	protected String testloadftp() throws IOException {
		Map<String, Object> ObjMap = ObjReportsService.handleFTP("FTP Docx test", "load", "reports");
		if ((boolean)ObjMap.get("status")) {
			return "working";
		} else {
			return "not working";
		}
	}
	
	@PostMapping(value = "/testsaveftp")
	protected String testsaveftp() throws IOException {
		Map<String, Object> ObjMap = ObjReportsService.handleFTP("test.docx", "save", "reports");
		if ((boolean)ObjMap.get("status")) {
			return "working";
		} else {
			return "not working";
		}
	}

	@PostMapping("/Getorderbytype")
	//public Map<String, Object> Getorderbytype(@RequestBody LSlogilablimsorderdetail objorder)
	public Map<String, Object> Getorderbytype(@RequestBody Map<String, Object> objorder)throws Exception
	{
		Map<String, Object> mapOrders = ObjReportsService.Getorderbytype(objorder);
		return mapOrders;
	}
	
	@PostMapping(value = "/getFilecontentforSheet")
	protected List<OrderCreation> getFilecontentforSheet(@RequestBody Map<String, Object> selectedObj) throws IOException {
		List<LSlogilablimsorderdetail> LSlogilablimsorderdetailObj = new ObjectMapper().convertValue(selectedObj.get("getFileContent"), new TypeReference<List<LSlogilablimsorderdetail>>() {});
		List<OrderCreation> ObjMap = ObjReportsService.getFilecontentforSheet(LSlogilablimsorderdetailObj);
		return ObjMap;
	}
	
	@PostMapping(value = "/getSheetLSfileLst")
	protected Map<String, Object> getSheetLSfileLst(@RequestBody Map<String , Object> argMap) throws IOException, InvalidKeyException, URISyntaxException, StorageException {
		Map<String, Object> ObjMap = ObjReportsService.getSheetLSfileLst(argMap);
		return ObjMap;
	}
	
	@PostMapping(value = "/getSheetLSfileWithFileCode")
	protected Map<String, Object> getSheetLSfileWithFileCode(@RequestBody Map<String , Object> argMap) throws IOException {
		Map<String, Object> ObjMap = ObjReportsService.getSheetLSfileUsingFilecode(argMap);
		return ObjMap;
	}
	
	@PostMapping(value = "/getTemplatesLst")
	protected Map<String, Object> getTemplatesLst(@RequestBody Map<String , Object> argMap) throws IOException {
		Map<String, Object> ObjMap = ObjReportsService.getTemplatesLst(argMap);
		return ObjMap;
	}
	
	@PostMapping(value = "/handleOrderTemplate")
	public Map<String, Object> handleOrderTemplate(@RequestBody Map<String, Object> obj)throws Exception {
		Map<String, Object> ObjMap = new HashMap<String, Object>();
		try {
			Thread.sleep(2000);
			LoggedUser objuser = new LoggedUser();
			ObjectMapper mapper = new ObjectMapper();
			Response objResponse = new Response();
			
			LScfttransaction objsilentaudit = new LScfttransaction();
			LScfttransaction objmanualaudit = new LScfttransaction();
			if(obj.containsKey("objsilentaudit"))
			{
				objsilentaudit = mapper.convertValue(obj.get("objsilentaudit"),LScfttransaction.class);
			}
			if(obj.containsKey("objmanualaudit"))
			{
				objmanualaudit = mapper.convertValue(obj.get("objmanualaudit"),LScfttransaction.class);
			}
			
			if(obj.containsKey("objuser"))
			{
				objuser = mapper.convertValue(obj.get("objuser"),LoggedUser.class);
			}
			if(objuser.getsUsername() != null) {
				
				LSuserMaster userClass = auditService.CheckUserPassWord(objuser);
				
				if(userClass.getObjResponse().getStatus()) {
					ObjMap = ObjReportsService.handleOrderTemplate(obj);
				}
				else
				{
					objsilentaudit.setComments("Entered invalid username and password");
					Map<String, Object> map=new HashMap<>();
					map.put("objsilentaudit",objsilentaudit);
					map.put("objmanualaudit",objmanualaudit);
					map.put("objUser",objuser);
					auditService.AuditConfigurationrecord(map);
					objResponse.setStatus(false);
					objResponse.setInformation("ID_VALIDATION");
					ObjMap.put("objResponse", objResponse);
				}
				
			}else {
			ObjMap = ObjReportsService.handleOrderTemplate(obj);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return ObjMap;
	}
	
	@PostMapping(value = "/cloudHandleOrderTemplate")
	public Map<String, Object> cloudHandleOrderTemplate(@RequestBody Map<String, Object> obj)throws Exception {
		Map<String, Object> ObjMap = new HashMap<String, Object>();
		try {
			Thread.sleep(2000);
			LoggedUser objuser = new LoggedUser();
			ObjectMapper mapper = new ObjectMapper();
			Response objResponse = new Response();
			
			LScfttransaction objsilentaudit = new LScfttransaction();
			LScfttransaction objmanualaudit = new LScfttransaction();
			if(obj.containsKey("objsilentaudit"))
			{
				objsilentaudit = mapper.convertValue(obj.get("objsilentaudit"),LScfttransaction.class);
			}
			if(obj.containsKey("objmanualaudit"))
			{
				objmanualaudit = mapper.convertValue(obj.get("objmanualaudit"),LScfttransaction.class);
			}
			
			if(obj.containsKey("objuser"))
			{
				objuser = mapper.convertValue(obj.get("objuser"),LoggedUser.class);
			}
			if(objuser.getsUsername() != null) {
				
				LSuserMaster userClass = auditService.CheckUserPassWord(objuser);
				
				if(userClass.getObjResponse().getStatus()) {
					ObjMap = ObjReportsService.cloudHandleOrderTemplate(obj);
				}
				else
				{
					objsilentaudit.setComments("Entered invalid username and password");
					Map<String, Object> map=new HashMap<>();
					map.put("objsilentaudit",objsilentaudit);
					map.put("objmanualaudit",objmanualaudit);
					map.put("objUser",objuser);
					auditService.AuditConfigurationrecord(map);
					objResponse.setStatus(false);
					objResponse.setInformation("ID_VALIDATION");
					ObjMap.put("objResponse", objResponse);
				}
				
			}else {
			ObjMap = ObjReportsService.cloudHandleOrderTemplate(obj);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return ObjMap;
	}
	
	@PostMapping(value = "/getReportDocxonVersion")
	public Map<String, Object> getReportDocxonVersion(@RequestBody Map<String, Object> obj)throws Exception {
		Map<String, Object> ObjMap = new HashMap<String, Object>();
		ObjMap = ObjReportsService.getReportDocxonVersion(obj);
		return ObjMap;
	}
	
	@PostMapping(value = "/createFIle")
	public void createFIle()throws Exception {
		ObjReportsService.createFIle();
	}
	
}
