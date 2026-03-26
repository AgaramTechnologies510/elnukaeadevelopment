package com.agaram.eln.primary.controller.protocol;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.agaram.eln.primary.commonfunction.commonfunction;
import com.agaram.eln.primary.fetchmodel.getorders.LogilabProtocolOrderssh;
import com.agaram.eln.primary.fetchmodel.getorders.Logilabprotocolorders;
import com.agaram.eln.primary.model.cloudProtocol.LSprotocolstepInformation;
import com.agaram.eln.primary.model.general.Response;
import com.agaram.eln.primary.model.instrumentDetails.Lsprotocolordersharedby;
import com.agaram.eln.primary.model.instrumentDetails.Lsprotocolordershareto;
import com.agaram.eln.primary.model.masters.Lsrepositoriesdata;
import com.agaram.eln.primary.model.protocols.ElnprotocolTemplateworkflow;
import com.agaram.eln.primary.model.protocols.LSlogilabprotocoldetail;
import com.agaram.eln.primary.model.protocols.LSlogilabprotocolsteps;
import com.agaram.eln.primary.model.protocols.LSprotocolfiles;
import com.agaram.eln.primary.model.protocols.LSprotocolfilesContent;
import com.agaram.eln.primary.model.protocols.LSprotocolmaster;
import com.agaram.eln.primary.model.protocols.LSprotocolmastertest;
import com.agaram.eln.primary.model.protocols.LSprotocolorderfiles;
import com.agaram.eln.primary.model.protocols.LSprotocolordersampleupdates;
import com.agaram.eln.primary.model.protocols.LSprotocolorderstephistory;
import com.agaram.eln.primary.model.protocols.LSprotocolorderworkflowhistory;
import com.agaram.eln.primary.model.protocols.LSprotocolsampleupdates;
import com.agaram.eln.primary.model.protocols.LSprotocolstep;
import com.agaram.eln.primary.model.protocols.LSprotocolupdates;
import com.agaram.eln.primary.model.protocols.LSprotocolworkflow;
import com.agaram.eln.primary.model.protocols.LSprotocolworkflowhistory;
import com.agaram.eln.primary.model.protocols.Lsprotocolsharedby;
import com.agaram.eln.primary.model.protocols.Lsprotocolshareto;
import com.agaram.eln.primary.model.protocols.ProtocolImage;
import com.agaram.eln.primary.model.protocols.ProtocolorderImage;
import com.agaram.eln.primary.model.protocols.Protocolordervideos;
import com.agaram.eln.primary.model.protocols.Protocolvideos;
import com.agaram.eln.primary.model.sheetManipulation.LStestmasterlocal;
import com.agaram.eln.primary.model.usermanagement.LSSiteMaster;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;
import com.agaram.eln.primary.model.usermanagement.LoggedUser;
import com.agaram.eln.primary.repository.protocol.ElnprotocolTemplateworkflowRepository;
import com.agaram.eln.primary.repository.protocol.LSProtocolMasterRepository;
import com.agaram.eln.primary.repository.protocol.LSprotocolworkflowhistoryRepository;
import com.agaram.eln.primary.repository.usermanagement.LSuserMasterRepository.UserProjection;
import com.agaram.eln.primary.service.protocol.ProtocolService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/protocol", method = RequestMethod.POST)
public class ProtocolController {

	@Autowired
	ProtocolService ProtocolMasterService;
	
	@Autowired
	LSProtocolMasterRepository LSProtocolMasterRepositoryObj;
	
	@Autowired
	private LSprotocolworkflowhistoryRepository lsprotocolworkflowhistoryRepository;
	
	@Autowired
	private ElnprotocolTemplateworkflowRepository elnprotocolTemplateworkflowRepository;

	@PostMapping("/getProtocolMasterInit")
	protected Map<String, Object> getProtocolMasterInit(@RequestBody Map<String, Object> argObj)throws Exception {
		Map<String, Object> objMap = new HashMap<String, Object>();
		objMap = ProtocolMasterService.getProtocolMasterInit(argObj);
		return objMap;
	}

	@PostMapping(value = "/addProtocolMaster")
	protected Map<String, Object> addProtocolMaster(@RequestBody Map<String, Object> argObj)throws Exception {

		return ProtocolMasterService.addProtocolMaster(argObj);

	}

	@PostMapping(value = "/deleteProtocolMaster")
	protected Map<String, Object> deleteProtocolMaster(@RequestBody Map<String, Object> argObj)throws Exception {

		return ProtocolMasterService.deleteProtocolMaster(argObj);
	}

	@PostMapping(value = "/getProtocolMasterLst")
	protected Map<String, Object> getProtocolMasterLst(@RequestBody Map<String, Object> argObj)throws Exception {
		Map<String, Object> objMap = new HashMap<String, Object>();
		objMap = ProtocolMasterService.getLSProtocolMasterLst(argObj);
		return objMap;
	}

	@PostMapping(value = "/getApprovedprotocolLst")
	protected Map<String, Object> getApprovedprotocolLst(@RequestBody LSSiteMaster site)throws Exception {
		Map<String, Object> objMap = new HashMap<String, Object>();
		objMap = ProtocolMasterService.getApprovedprotocolLst(site);
		return objMap;
	}

	@PostMapping(value = "/getProtocolStepLst")
	protected Map<String, Object> getProtocolStepLst(@RequestBody Map<String, Object> argObj)throws Exception {
		Map<String, Object> objMap = new HashMap<String, Object>();
		objMap = ProtocolMasterService.getProtocolStepLst(argObj);
		return objMap;
	}
	
	@PostMapping(value = "/getProtocolStepLstForShare")
	protected Map<String, Object> getProtocolStepLstForShare(@RequestBody Map<String, Object> argObj)throws Exception {
		Map<String, Object> objMap = new HashMap<String, Object>();
		objMap = ProtocolMasterService.getProtocolStepLstForShare(argObj);
		return objMap;
	}

	@PostMapping(value = "/getAllProtocolStepLst")
	protected Map<String, Object> getAllProtocolStepLst(@RequestBody Map<String, Object> argObj)throws Exception {
		Map<String, Object> objMap = new HashMap<String, Object>();
		objMap = ProtocolMasterService.getAllProtocolStepLst(argObj);
		return objMap;
	}

	@PostMapping(value = "/getOrdersLinkedToProtocol")
	protected Map<String, Object> getOrdersLinkedToProtocol(@RequestBody Map<String, Object> argObj)throws Exception {
		Map<String, Object> objMap = new HashMap<String, Object>();
		objMap = ProtocolMasterService.getOrdersLinkedToProtocol(argObj);
		return objMap;
	}

	@PostMapping(value = "/addProtocolStep")
	protected Map<String, Object> addProtocolStep(@RequestBody Map<String, Object> argObj)throws Exception {

		return ProtocolMasterService.addProtocolStep(argObj);
	}

	@PostMapping(value = "/deleteProtocolStep")
	protected Map<String, Object> deleteProtocolStep(@RequestBody Map<String, Object> argObj)throws Exception {
		Map<String, Object> objMap = new HashMap<String, Object>();
		objMap = ProtocolMasterService.deleteProtocolStep(argObj);
		return objMap;
	}

	@PostMapping(value = "/sharewithteam")
	protected Map<String, Object> sharewithteam(@RequestBody Map<String, Object> argObj)throws Exception {

		return ProtocolMasterService.sharewithteam(argObj);
	}

	@PostMapping(value = "/updateworkflowforProtocol")
	protected Map<String, Object> updateworkflowforProtocol(@RequestBody LSprotocolmaster objClass)throws Exception {
//		Map<String, Object> objMap = new HashMap<String, Object>();
//		objMap = ProtocolMasterService.updateworkflowforProtocol(objClass);
		Map<String, Object> mapObj = new HashMap<String, Object>();

		int approved = 0;

		if (objClass.getApproved() != null) {
			approved = objClass.getApproved();
		}

		LSProtocolMasterRepositoryObj.updateFileWorkflow(objClass.getElnprotocoltemplateworkflow(), approved,
				objClass.getRejected(), objClass.getProtocolmastercode(),objClass.getPreviousapprovedworkflow(),objClass.getWorkflowapprovedusercode());

		LSprotocolmaster LsProto = LSProtocolMasterRepositoryObj
				.findFirstByProtocolmastercode(objClass.getProtocolmastercode());

		LsProto.setElnprotocoltemplateworkflow(objClass.getElnprotocoltemplateworkflow());
		if (LsProto.getApproved() == null) {
			LsProto.setApproved(0);
		}
		List<LSprotocolworkflowhistory> obj = objClass.getLsprotocolworkflowhistory();
		obj = obj.stream().map(workflowhistory -> {
			try {
				if (workflowhistory.getHistorycode() == null) {
					workflowhistory.setCreatedate(commonfunction.getCurrentUtcTime());
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return workflowhistory;
		}).collect(Collectors.toList());
		lsprotocolworkflowhistoryRepository.saveAll(obj);
		LsProto.setLsprotocolworkflowhistory(obj);
		mapObj.put("ProtocolObj", LsProto);
		mapObj.put("status", "success");
		if (objClass.getViewoption() == null || objClass.getViewoption() != null && objClass.getViewoption() != 2) {
			if (objClass.getProtocolmastername() != null) {
//				LSsheetworkflow objlastworkflow = lssheetworkflowRepository
//						.findTopByAndLssitemasterOrderByWorkflowcodeDesc(objClass.getIsfinalstep().getLssitemaster());
				ElnprotocolTemplateworkflow objlastworkflow = elnprotocolTemplateworkflowRepository
						.findTopByAndLssitemasterAndStatusOrderByWorkflowcodeDesc(objClass.getIsfinalstep().getLssitemaster(),1);
				if (objlastworkflow != null
						&& objClass.getCurrentStep().getWorkflowcode() == objlastworkflow.getWorkflowcode()) {
					objClass.setFinalworkflow(1);
					;
				} else {
					objClass.setFinalworkflow(0);
					;
				}
			}

			try {
				ProtocolMasterService.updatenotificationforprotocolworkflowapproval(objClass, LsProto.getElnprotocoltemplateworkflow());
				ProtocolMasterService.updatenotificationforprotocol(objClass, LsProto.getElnprotocoltemplateworkflow());
			} catch (Exception e) {

			}
		}
//		return mapObj;
		return mapObj;
	}
	
	@PostMapping(value = "/updateworkflowforProtocolorder")
	protected Map<String, Object> updateworkflowforProtocolorder(@RequestBody LSlogilabprotocoldetail objClass)throws Exception {
		Map<String, Object> objMap = new HashMap<String, Object>();
		objMap = ProtocolMasterService.updateworkflowforProtocolorder(objClass);
		return objMap;
	}

	@PostMapping("/GetProtocolWorkflow")
	public List<LSprotocolworkflow> GetProtocolWorkflow(@RequestBody LSprotocolworkflow objclass)throws Exception {
		return ProtocolMasterService.GetProtocolWorkflow(objclass);
	}
	


	@PostMapping("/InsertUpdatesheetWorkflow")
	public List<LSprotocolworkflow> InsertUpdatesheetWorkflow(@RequestBody LSprotocolworkflow[] protocolworkflow)throws Exception {
		return ProtocolMasterService.InsertUpdatesheetWorkflow(protocolworkflow);
	}

	@PostMapping("/Deletesheetworkflow")
	public Response Deletesheetworkflow(@RequestBody LSprotocolworkflow objflow)throws Exception {
		return ProtocolMasterService.Deletesheetworkflow(objflow);
	}

	@PostMapping(value = "/getProtocolMasterList")
	protected List<LSprotocolmaster> getProtocolMasterList(@RequestBody LSuserMaster objClass)throws Exception {

		return ProtocolMasterService.getProtocolMasterList(objClass);
	}

	@PostMapping(value = "/addProtocolOrder")
	protected Map<String, Object> addProtocolOrder(@RequestBody LSlogilabprotocoldetail LSlogilabprotocoldetail)throws Exception {

		return ProtocolMasterService.addProtocolOrder(LSlogilabprotocoldetail);

	}
		
	@PostMapping(value = "/addProtocolOrderafter")
	protected Map<String, Object> addProtocolOrderafter(@RequestBody LSlogilabprotocoldetail LSlogilabprotocoldetail)throws Exception {

		return ProtocolMasterService.addProtocolOrderafter(LSlogilabprotocoldetail);

	}

	@PostMapping(value = "/getProtocolOrderList")
	protected Map<String, Object> getProtocolOrderList(@RequestBody LSlogilabprotocoldetail LSlogilabprotocoldetail)throws Exception {
		return ProtocolMasterService.getProtocolOrderList(LSlogilabprotocoldetail);
	}
	
	
	@PostMapping(value = "/getProtocolOrderListfortabchange")
	protected Map<String, Object> getProtocolOrderListfortabchange(@RequestBody LSlogilabprotocoldetail LSlogilabprotocoldetail)throws Exception {
		return ProtocolMasterService.getProtocolOrderListfortabchange(LSlogilabprotocoldetail);
	}
	
	@PostMapping(value = "/getreminProtocolOrderList")
	protected Map<String, Object> getreminProtocolOrderList(@RequestBody LSlogilabprotocoldetail LSlogilabprotocoldetail)throws Exception {
		return ProtocolMasterService.getreminProtocolOrderList(LSlogilabprotocoldetail);
	}
	
	@PostMapping(value = "/getreminProtocolOrderListontab")
	protected Map<String, Object> getreminProtocolOrderListontab(@RequestBody LSlogilabprotocoldetail LSlogilabprotocoldetail)throws Exception {
		return ProtocolMasterService.getreminProtocolOrderListontab(LSlogilabprotocoldetail);
	}

	@PostMapping(value = "/updateProtocolOrderStep")
	protected Map<String, Object> updateProtocolOrderStep(@RequestBody Map<String, Object> argObj)throws Exception {
		return ProtocolMasterService.updateProtocolOrderStep(argObj);
	}

	@PostMapping(value = "/getProtocolOrderStepLst")
	protected Map<String, Object> getProtocolOrderStepLst(@RequestBody Map<String, Object> argObj)throws Exception {
		Map<String, Object> objMap = new HashMap<String, Object>();
		objMap = ProtocolMasterService.getProtocolOrderStepLst(argObj);
		return objMap;
	}

	@PostMapping(value = "/getAllMasters")
	protected Map<String, Object> getAllMasters(@RequestBody LSuserMaster objuser)throws Exception {
		Map<String, Object> objMap = new HashMap<String, Object>();
		objMap = ProtocolMasterService.getAllMasters(objuser);
		return objMap;
	}

	@PostMapping(value = "/startStep")
	protected LSprotocolorderstephistory startStep(@RequestBody LSprotocolorderstephistory objuser)throws Exception {
	
		return  ProtocolMasterService.startStep(objuser);
	}

	@PostMapping(value = "/updateStepStatus")
	protected Map<String, Object> updateStepStatus(@RequestBody Map<String, Object> argMap)throws Exception {
		Map<String, Object> objMap = new HashMap<String, Object>();
		objMap = ProtocolMasterService.updateStepStatus(argMap);
		return objMap;
	}

	@PostMapping(value = "/updateOrderStatus")
	protected Map<String, Object> updateOrderStatus(@RequestBody LSlogilabprotocoldetail argMap)throws Exception {
		Map<String, Object> objMap = new HashMap<String, Object>();
		objMap = ProtocolMasterService.updateOrderStatus(argMap);
		return objMap;
	}

	@PostMapping(value = "/getLsrepositoriesLst")
	protected Map<String, Object> getLsrepositoriesLst(@RequestBody Map<String, Object> argMap)throws Exception {
		Map<String, Object> objMap = new HashMap<String, Object>();
		objMap = ProtocolMasterService.getLsrepositoriesLst(argMap);
		return objMap;
	}

	@PostMapping(value = "/getLsrepositoriesDataLst")
	protected Map<String, Object> getLsrepositoriesDataLst(@RequestBody Map<String, Object> argMap)throws Exception {
		Map<String, Object> objMap = new HashMap<String, Object>();
		objMap = ProtocolMasterService.getLsrepositoriesDataLst(argMap);
		return objMap;
	}

	@PostMapping("/GetProtocolTransactionDetails")
	public Map<String, Object> GetProtocolTransactionDetails(@RequestBody LSprotocolmaster LSprotocolmaster)throws Exception {
		Map<String, Object> objMap = new HashMap<String, Object>();
		objMap = ProtocolMasterService.GetProtocolTransactionDetails(LSprotocolmaster);
		return objMap;
	}
	
	@PostMapping("/GetProtocolorderVersionDetails")
	public Map<String, Object> GetProtocolorderVersionDetails(@RequestBody LSlogilabprotocoldetail LSlogilabprotocoldetail)throws Exception {
		Map<String, Object> objMap = new HashMap<String, Object>();
		objMap = ProtocolMasterService.GetProtocolorderVersionDetails(LSlogilabprotocoldetail);
		return objMap;
	}
	
	@PostMapping(value = "/addProtocolStepforsaveas")
	protected Map<String, Object> addProtocolStepforsaveas(@RequestBody Map<String, Object> argObj)throws Exception {
		Map<String, Object> objMap = new HashMap<String, Object>();
		objMap = ProtocolMasterService.addProtocolStepforsaveas(argObj);
		return objMap;
	}

	@PostMapping("/GetProtocolResourcesQuantitylst")
	public Map<String, Object> GetProtocolResourcesQuantitylst(@RequestBody LSprotocolstep LSprotocolstep)throws Exception {
		return ProtocolMasterService.GetProtocolResourcesQuantitylst(LSprotocolstep);
	}

	@PostMapping("/GetProtocolVersionDetails")
	public Map<String, Object> GetProtocolVersionDetails(@RequestBody Map<String, Object> argObj)throws Exception {
		return ProtocolMasterService.GetProtocolVersionDetails(argObj);
	}

	@PostMapping("/GetProtocolorderResourcesQuantitylst")
	public  Map<String, Object> GetProtocolorderResourcesQuantitylst(
			@RequestBody LSlogilabprotocolsteps LSlogilabprotocolsteps) throws Exception{
		return ProtocolMasterService.GetProtocolorderResourcesQuantitylst(LSlogilabprotocolsteps);
	}

	@PostMapping("/GetProtocolTemplateVerionLst")
	public Map<String, Object> GetProtocolTemplateVerionLst(@RequestBody Map<String, Object> argObj)throws Exception {
		return ProtocolMasterService.GetProtocolTemplateVerionLst(argObj);
	}
	
	@PostMapping("/GetProtocolorderVerionLst")
	public Map<String, Object> GetProtocolorderVerionLst(@RequestBody Map<String, Object> argObj)throws Exception {
		return ProtocolMasterService.GetProtocolorderVerionLst(argObj);
	}
	
	@PostMapping("/getprotocols")
	public List <LSprotocolmaster> getprotocols(@RequestBody LSuserMaster objusers)throws Exception
	{
		return ProtocolMasterService.getprotocol(objusers);
	}
	
	@PostMapping("/getProtocolcount")
	public  Map<String, Object> getProtocolcount(@RequestBody LSuserMaster objusers)throws Exception
	{
		return ProtocolMasterService.getProtocolcount(objusers);
	}
	
	@PostMapping("/getsingleprotocolorder")
	public List<LSlogilabprotocoldetail> getsingleprotocolorder(@RequestBody LSlogilabprotocoldetail objusers)throws Exception
	{
		return ProtocolMasterService.getsingleprotocolorder(objusers);
	}
	
	@PostMapping("/Getinitialorders")
	public Map<String, Object> Getinitialorders(@RequestBody LSlogilabprotocoldetail objorder)throws Exception
	{
		return ProtocolMasterService.Getinitialorders(objorder);
	}
	
	@PostMapping("/Getremainingorders")
	public List<Logilabprotocolorders> Getremainingorders(@RequestBody LSlogilabprotocoldetail objorder)throws Exception
	{
		return ProtocolMasterService.Getremainingorders(objorder);
	}
	
	@PostMapping("/Getinitialtemplates")
	public Map<String, Object> Getinitialtemplates(@RequestBody LSprotocolmaster objorder)throws Exception
	{
		return ProtocolMasterService.Getinitialtemplates(objorder);
	}
	
	@PostMapping("/Getremainingtemplates")
	public List<LSprotocolmaster> Getremainingtemplates(@RequestBody LSprotocolmaster objorder)throws Exception
	{
		return ProtocolMasterService.Getremainingtemplates(objorder);
	}
	
	@PostMapping("/uploadprotocols")
	public Map<String, Object> uploadprotocols(@RequestBody Map<String, Object> body)throws Exception
	{
		return ProtocolMasterService.uploadprotocols(body);
//		return true;
	}
	
	@PostMapping("/loadprotocolfiles")
	public List<LSprotocolfiles> loadprotocolfiles(@RequestParam Map<String, String> body)throws Exception
	{
		return ProtocolMasterService.loadprotocolfiles(body);
	}
	
	@PostMapping("/uploadprotocolsfile")
	public Map<String, Object> uploadprotocolsfile(@RequestParam MultipartFile file,
			@RequestParam Integer protocolstepcode, 
			@RequestParam Integer protocolmastercode, 
			@RequestParam Integer stepno,
			@RequestParam String protocolstepname,
			@RequestParam String originurl,
			@RequestParam String editoruuid)throws Exception
	{
	
		return ProtocolMasterService.uploadprotocolsfile(file, protocolstepcode,protocolmastercode,stepno,protocolstepname,originurl,editoruuid );
	}
	
	@PostMapping("/uploadprotocolsfileondrag")
	public Map<String, Object> uploadprotocolsfileondrag(@RequestParam MultipartFile file,
			@RequestParam Integer protocolstepcode, 
			@RequestParam Integer protocolmastercode, 
			@RequestParam Integer stepno,
			@RequestParam String protocolstepname,
			@RequestParam String originurl,
			@RequestParam String editoruuid)throws Exception
	{
	
		return ProtocolMasterService.uploadprotocolsfile(file, protocolstepcode,protocolmastercode,stepno,protocolstepname,originurl,editoruuid );
	}
	
	@PostMapping("/Uploadprotocolimage")
	public Map<String, Object> Uploadprotocolimage(@RequestParam MultipartFile file,
			@RequestParam Integer protocolstepcode, 
			@RequestParam Integer protocolmastercode, 
			@RequestParam Integer stepno,
			@RequestParam String protocolstepname,
			@RequestParam String originurl
			)throws Exception
	{
		return ProtocolMasterService.Uploadprotocolimage(file, protocolstepcode,protocolmastercode,stepno,protocolstepname,originurl );
	}
	
	@PostMapping("/Uploadprotocolimageondrag")
	public Map<String, Object> Uploadprotocolimageondrag(@RequestParam MultipartFile file,
			@RequestParam Integer protocolstepcode, 
			@RequestParam Integer protocolmastercode, 
			@RequestParam Integer stepno,
			@RequestParam String protocolstepname,
			@RequestParam String originurl)throws Exception
	{
		return ProtocolMasterService.Uploadprotocolimage(file, protocolstepcode,protocolmastercode,stepno,protocolstepname,originurl );
	}
	
	@GetMapping("downloadprotocolimage/{fileid}/{tenant}/{filename}/{extension}")
	public ResponseEntity<InputStreamResource> downloadprotocolimage(@PathVariable String fileid
			, @PathVariable String tenant, @PathVariable String filename, @PathVariable String extension) throws IllegalStateException, IOException {
		
		ByteArrayInputStream bis = ProtocolMasterService.downloadprotocolimage(fileid, tenant);
		
	    HttpHeaders header = new HttpHeaders();
	    header.setContentType(MediaType.parseMediaType("image/png"));
	    header.set("Content-Disposition", "attachment; filename="+filename+"."+extension);
	    
	    return new ResponseEntity<>(new InputStreamResource(bis), header, HttpStatus.OK);
	}
	
	@GetMapping(value = "downloadprotocolfile/{fileid}/{tenant}/{filename}/{extension}/{ontabkey}")
	public ResponseEntity<InputStreamResource> downloadprotocolfile(@PathVariable String fileid
			, @PathVariable String tenant, @PathVariable String filename, @PathVariable String extension,@PathVariable String ontabkey) throws IllegalStateException, IOException {
		
		ByteArrayInputStream bis = ProtocolMasterService.downloadprotocolfile(fileid, tenant,ontabkey);
		
	    HttpHeaders header = new HttpHeaders();
	    String mediatype = commonfunction.getMIMEtypeonextension(extension);
	    header.setContentType(MediaType.parseMediaType(mediatype));
	    header.set("Content-Disposition", "attachment; filename="+filename+"."+extension);
	    
	    return new ResponseEntity<>(new InputStreamResource(bis), header, HttpStatus.OK);
	}
	
	@GetMapping(value = "downloadprotocolfilewithstepname/{tenantid}/{screen}/{filetype}/{protocolname}/{sectionname}/{stepname}/{editorname}/{filename}/{extension}")
	public ResponseEntity<InputStreamResource> downloadprotocolfilewithstepname(@PathVariable String tenantid, @PathVariable String screen, @PathVariable String filetype,
		@PathVariable String protocolname, @PathVariable String sectionname, @PathVariable String stepname, 
		@PathVariable String editorname, @PathVariable String filename, @PathVariable String extension) throws IllegalStateException, IOException {
		
		ByteArrayInputStream bis = ProtocolMasterService.downloadprotocolfilewithstepname(tenantid, screen, filetype, protocolname, sectionname, stepname, editorname, filename, extension);
		
	    HttpHeaders header = new HttpHeaders();
	    String mediatype = commonfunction.getMIMEtypeonextension(extension);
	    header.setContentType(MediaType.parseMediaType(mediatype));
	    header.set("Content-Disposition", "attachment; filename=" + filename + "." + extension);
	    
	    return new ResponseEntity<>(new InputStreamResource(bis), header, HttpStatus.OK);
	}
	
	@GetMapping(value = "downloadprotocolfilewithoutstepname/{tenantid}/{screen}/{filetype}/{protocolname}/{filename}/{extension}")
	public ResponseEntity<InputStreamResource> downloadprotocolfilewithoutstepname(@PathVariable String tenantid, @PathVariable String screen, @PathVariable String filetype,
		@PathVariable String protocolname, @PathVariable String filename, @PathVariable String extension) throws IllegalStateException, IOException {
		
		ByteArrayInputStream bis = ProtocolMasterService.downloadprotocolfilewithoutstepname(tenantid, screen, filetype, protocolname, filename, extension);
		
	    HttpHeaders header = new HttpHeaders();
	    String mediatype = commonfunction.getMIMEtypeonextension(extension);
	    header.setContentType(MediaType.parseMediaType(mediatype));
	    header.set("Content-Disposition", "attachment; filename=" + filename + "." + extension);
	    
	    return new ResponseEntity<>(new InputStreamResource(bis), header, HttpStatus.OK);
	}
	
	@PostMapping("/removeprotocolimage")
	public boolean removeprotocolimage(@RequestBody Map<String, String> body)throws Exception
	{
		return ProtocolMasterService.removeprotocolimage(body);
	}
	
	@PostMapping("/reducecunsumablefield")
	public List<Lsrepositoriesdata> reducecunsumablefield(@RequestBody Lsrepositoriesdata[] lsrepositoriesdata)throws Exception
	{
		return ProtocolMasterService.reducecunsumablefield(lsrepositoriesdata);
	}
	
	@PostMapping(value = "/protocolsampleupdates")
	protected Map<String, Object> protocolsampleupdates(@RequestBody LSprotocolsampleupdates lsprotocolsampleupdates)throws Exception {

		return ProtocolMasterService.protocolsampleupdates(lsprotocolsampleupdates);
	}
	
	@PostMapping(value = "/protocolordersampleupdates")
	protected Map<String, Object> protocolordersampleupdates(@RequestBody LSprotocolordersampleupdates lsprotocolordersampleupdates)throws Exception {

		return ProtocolMasterService.protocolordersampleupdates(lsprotocolordersampleupdates);
	}
	@PostMapping(value = "/consumableinventorynotificationprotocol")
	protected Map<String, Object> consumableinventorynotificationprotocol(@RequestBody LSprotocolordersampleupdates lsprotocolordersampleupdates)throws Exception {

		return ProtocolMasterService.consumableinventorynotificationprotocol(lsprotocolordersampleupdates);
	}
	@PostMapping(value = "/getrepositoriesdata")
	protected List<Lsrepositoriesdata> getrepositoriesdata(@RequestBody Integer[] lsrepositoriesdata)throws Exception {

		return ProtocolMasterService.getrepositoriesdata(lsrepositoriesdata);
	}
	
	@PostMapping(value = "/updateprotocolsampleupdates")
	protected Map<String, Object> updateprotocolsampleupdates(@RequestBody LSprotocolsampleupdates[] lsprotocolsampleupdates)throws Exception {

		return ProtocolMasterService.updateprotocolsampleupdates(lsprotocolsampleupdates);
	}
	
	@PostMapping(value = "/updateprotocolordersampleupdates")
	protected Map<String, Object> updateprotocolordersampleupdates(@RequestBody LSprotocolordersampleupdates[] lsprotocolordersampleupdates)throws Exception {

		return ProtocolMasterService.updateprotocolordersampleupdates(lsprotocolordersampleupdates);
	}
	
	@PostMapping("/uploadprotocolsordersstep")
	public Map<String, Object> uploadprotocolsordersstep(@RequestBody Map<String, Object> body)throws Exception
	{
		return ProtocolMasterService.uploadprotocolsordersstep(body);
//		return true;
	}
	
	@GetMapping("downloadprotocolorderimage/{fileid}/{tenant}/{filename}/{extension}")
	public ResponseEntity<InputStreamResource> downloadprotocolorderimage(@PathVariable String fileid
			, @PathVariable String tenant, @PathVariable String filename, @PathVariable String extension) throws IllegalStateException, IOException {
		
		ByteArrayInputStream bis = ProtocolMasterService.downloadprotocolorderimage(fileid, tenant);
		
	    HttpHeaders header = new HttpHeaders();
	    header.setContentType(MediaType.parseMediaType("image/png"));
	    header.set("Content-Disposition", "attachment; filename="+filename+"."+extension);
	    
	    return new ResponseEntity<>(new InputStreamResource(bis), header, HttpStatus.OK);
	}
	
	@GetMapping("downloadprotocolorderfiles/{fileid}/{tenant}/{filename}/{extension}")
	public ResponseEntity<InputStreamResource> downloadprotocolorderfiles(@PathVariable String fileid
			, @PathVariable String tenant, @PathVariable String filename, @PathVariable String extension) throws IllegalStateException, IOException {
		
		ByteArrayInputStream bis = ProtocolMasterService.downloadprotocolorderfiles(fileid, tenant);
		
	    HttpHeaders header = new HttpHeaders();
	    header.setContentType(MediaType.parseMediaType("image/png"));
	    header.set("Content-Disposition", "attachment; filename="+filename+"."+extension);
	    
	    return new ResponseEntity<>(new InputStreamResource(bis), header, HttpStatus.OK);
	}

	@PostMapping("/Getprotocollinksignature")
	public Map<String, Object> Getprotocollinksignature(@RequestBody Map<String, String> body) throws ParseException, InvalidKeyException, NoSuchAlgorithmException, IllegalArgumentException
	{
		return ProtocolMasterService.Getprotocollinksignature(body);
	}
	
	@PostMapping("/Uploadprotocolorderimage")
	public Map<String, Object> Uploadprotocolorderimage(@RequestParam MultipartFile file,
			@RequestParam Integer protocolorderstepcode, 
			@RequestParam Long protocolordercode, 
			@RequestParam Integer stepno,
			@RequestParam String protocolstepname,
			@RequestParam String originurl)throws Exception
	{
		return ProtocolMasterService.Uploadprotocolorderimage(file, protocolorderstepcode,protocolordercode,stepno,protocolstepname,originurl );
	}

	@PostMapping("/Uploadprotocolorderimagesql")
	public Map<String, Object> Uploadprotocolorderimagesql(@RequestParam MultipartFile file,
			@RequestParam Integer protocolorderstepcode, 
			@RequestParam Long protocolordercode, 
			@RequestParam Integer stepno,
			@RequestParam String protocolstepname,
			@RequestParam String originurl) throws IOException 
	{
		return ProtocolMasterService.Uploadprotocolorderimagesql(file, protocolorderstepcode,protocolordercode,stepno,protocolstepname,originurl );
	}
	
	@PostMapping("/uploadprotocolsorderfile")
	public Map<String, Object> uploadprotocolsorderfile(@RequestParam MultipartFile file,
			@RequestParam Integer protocolorderstepcode, 
			@RequestParam Long protocolordercode, 
			@RequestParam Integer stepno,
			@RequestParam String protocolstepname,
			@RequestParam String originurl)throws Exception
	{
	
		return ProtocolMasterService.uploadprotocolsorderfile(file, protocolorderstepcode,protocolordercode,stepno,protocolstepname,originurl );
	}
	
	@PostMapping("/uploadprotocolsorderfilesql")
	public Map<String, Object> uploadprotocolsfilesql(@RequestParam MultipartFile file,
			@RequestParam Integer protocolorderstepcode, 
			@RequestParam Long protocolordercode, 
			@RequestParam Integer stepno,
			@RequestParam String protocolstepname,
			@RequestParam String originurl) throws IOException
	{
	
		return ProtocolMasterService.uploadprotocolsorderfilesql(file, protocolorderstepcode,protocolordercode,stepno,protocolstepname,originurl );
	}
	
	@PostMapping("/removeprotocoorderlimage")
	public boolean removeprotocoorderlimage(@RequestBody Map<String, String> body)throws Exception
	{
		return ProtocolMasterService.removeprotocoorderlimage(body);
	}
	
	@PostMapping("/loadprotocolorderfiles")
	public List<LSprotocolorderfiles> loadprotocolorderfiles(@RequestParam Map<String, String> body)throws Exception
	{
		return ProtocolMasterService.loadprotocolorderfiles(body);
	}
	@PostMapping("/Insertshareprotocolorder")
	public Lsprotocolordershareto Insertshareprotocolorder(@RequestBody Lsprotocolordershareto objprotocolordershareto)throws Exception {
		return ProtocolMasterService.Insertshareprotocolorder(objprotocolordershareto);
	}

	@PostMapping("/Insertshareprotocolorderby")
	public Map<String, Object> Insertshareprotocolorderby(@RequestBody Lsprotocolordersharedby objprotocolordersharedby)throws Exception {
		return ProtocolMasterService.Insertshareprotocolorderby(objprotocolordersharedby);
	}
	
	@PostMapping("/Getprotocolordersharedbyme")
	public List<Lsprotocolordersharedby> Getprotocolordersharedbyme(@RequestBody Lsprotocolordersharedby objprotocolordersharedby)throws Exception {
		return ProtocolMasterService.Getprotocolordersharedbyme(objprotocolordersharedby);
	}
	
	@PostMapping("/Getprotocolordersharedtome")
	public List<Lsprotocolordershareto> Getprotocolordersharedtome(@RequestBody Lsprotocolordershareto objprotocolordershareto)throws Exception {
		return ProtocolMasterService.Getprotocolordersharedtome(objprotocolordershareto);
	}
	
	@PostMapping("/Insertshareprotocol")
	public Lsprotocolshareto Insertshareprotocol(@RequestBody Lsprotocolshareto objprotocolordershareto)throws Exception {
		return ProtocolMasterService.Insertshareprotocol(objprotocolordershareto);
	}
	
	@PostMapping("/Insertshareprotocolby")
	public Map<String, Object> Insertshareprotocolby(@RequestBody Lsprotocolsharedby objprotocolordersharedby)throws Exception {
		return ProtocolMasterService.Insertshareprotocolby(objprotocolordersharedby);
	}

	@PostMapping("/Getprotocolsharedbyme")
	public List<Lsprotocolsharedby> Getprotocolsharedbyme(@RequestBody Lsprotocolsharedby objprotocolordersharedby)throws Exception {
		return ProtocolMasterService.Getprotocolsharedbyme(objprotocolordersharedby);
	}
	
	@PostMapping("/Getprotocolsharedtome")
	public List<Lsprotocolshareto> Getprotocolsharedtome(@RequestBody Lsprotocolshareto objprotocolordershareto)throws Exception {
		return ProtocolMasterService.Getprotocolsharedtome(objprotocolordershareto);
	}
	
	@PostMapping("/Unshareorderby")
	public Lsprotocolsharedby Unshareprotocolby(@RequestBody Lsprotocolshareto objordershareby)throws Exception {
		return ProtocolMasterService.Unshareprotocolby(objordershareby);
	}
	
	@PostMapping("/Unshareorderto")
	public Lsprotocolshareto Unshareorderto(@RequestBody Lsprotocolshareto lsordershareto)throws Exception {
		return ProtocolMasterService.Unshareorderto(lsordershareto);
	}
	
	@PostMapping("/Unshareprotocolorderby")
	public Lsprotocolordersharedby Unshareprotocolorderby(@RequestBody Lsprotocolordersharedby objprotocolordersharedby)throws Exception {
		return ProtocolMasterService.Unshareprotocolorderby(objprotocolordersharedby);
	}

	@PostMapping("/Unshareprotocolorderto")
	public Lsprotocolordershareto Unshareprotocolorderto(@RequestBody Lsprotocolordershareto objprotocolordershareto)throws Exception {
		return ProtocolMasterService.Unshareprotocolorderto(objprotocolordershareto);
	}
	
	@PostMapping("/countsherorders")
	public Map<String, Object> countsherorders(@RequestBody Lsprotocolordersharedby Lsprotocolordersharedby)throws Exception {
		return ProtocolMasterService.countsherorders(Lsprotocolordersharedby);
	}
	
	@PostMapping("/UpdateProtocoltest")
	public LSprotocolmastertest UpdateProtocoltest(@RequestBody LSprotocolmastertest objtest)throws Exception
	{
		return ProtocolMasterService.UpdateProtocoltest(objtest);
	}
	
	@PostMapping(value = "/getProtocolOnTestcode")
	protected List<LSprotocolmaster> getProtocolOnTestcode(@RequestBody LSprotocolmastertest objClass)throws Exception {

		return ProtocolMasterService.getProtocolOnTestcode(objClass);
	}
	
	@PostMapping("/Uploadprotocolimagesql")
	public Map<String, Object> Uploadprotocolimagesql(@RequestParam MultipartFile file,
			@RequestParam Integer protocolstepcode, 
			@RequestParam Integer protocolmastercode, 
			@RequestParam Integer stepno,
			@RequestParam String protocolstepname,
			@RequestParam String originurl) throws IOException
	{
		return ProtocolMasterService.Uploadprotocolimagesql(file, protocolstepcode,protocolmastercode,stepno,protocolstepname,originurl );
	}
	
	@GetMapping("downloadprotocolimagesql/{fileid}/{filename}/{extension}")
	public ResponseEntity<InputStreamResource> downloadprotocolimagesql(@PathVariable String fileid
			, @PathVariable String filename, @PathVariable String extension) throws IllegalStateException, IOException {
		
		ProtocolImage objprofile =  ProtocolMasterService.downloadprotocolimagesql(fileid);
		
		byte[] data = null;
		
		if(objprofile != null)
		{
			data = objprofile.getImage().getData();
			ByteArrayInputStream bis = new ByteArrayInputStream(data);	
		    HttpHeaders header = new HttpHeaders();
		    header.setContentType(MediaType.parseMediaType("image/png"));
		    header.set("Content-Disposition", "attachment; filename="+filename+"."+extension);
		    
		    return new ResponseEntity<>(new InputStreamResource(bis), header, HttpStatus.OK);
		}else {
			
		
		return null;
		}
	
	}
	
	@PostMapping("/uploadprotocolsfilesql")
	public Map<String, Object> uploadprotocolsfilesql(@RequestParam MultipartFile file,
			@RequestParam Integer protocolstepcode, 
			@RequestParam Integer protocolmastercode, 
			@RequestParam Integer stepno,
			@RequestParam String protocolstepname,
			@RequestParam String originurl, @RequestParam  String editoruuid) throws IOException
	{
	
		return ProtocolMasterService.uploadprotocolsfilesql(file, protocolstepcode,protocolmastercode,stepno,protocolstepname,originurl,editoruuid );
	}
	
	@GetMapping("downloadprotocolfilesql/{fileid}/{filename}/{extension}/{ontabkey}")
	public ResponseEntity<InputStreamResource> downloadprotocolfilesql(@PathVariable String fileid
		, @PathVariable String filename, @PathVariable String extension) throws IllegalStateException, IOException {
		
		LSprotocolfilesContent lsprotocolfilesContent = ProtocolMasterService.downloadprotocolfilesql(fileid);
		
		byte[] data = null;
		
		if(lsprotocolfilesContent != null)
		{
			data = lsprotocolfilesContent.getFile().getData();
			ByteArrayInputStream bis = new ByteArrayInputStream(data);	
		    HttpHeaders header = new HttpHeaders();
		    header.setContentType(MediaType.parseMediaType("image/png"));
		    header.set("Content-Disposition", "attachment; filename="+filename+"."+extension);
		    
		    return new ResponseEntity<>(new InputStreamResource(bis), header, HttpStatus.OK);
		}else {
			
		
		return null;
		}
	}
	
	@PostMapping("/removeprotocolimagesql")
	public boolean removeprotocolimagesql(@RequestBody Map<String, String> body)throws Exception
	{
		return ProtocolMasterService.removeprotocolimagesql(body);
	}
	
	@PostMapping("/removeprotocoorderlimagesql")
	public boolean removeprotocoorderlimagesql(@RequestBody Map<String, String> body)throws Exception
	{
		return ProtocolMasterService.removeprotocoorderlimagesql(body);
	}
	
	@GetMapping("downloadprotocolorderimagesql/{fileid}/{filename}/{extension}")
	public ResponseEntity<InputStreamResource> downloadprotocolorderimagesql(@PathVariable String fileid
			, @PathVariable String filename, @PathVariable String extension) throws IllegalStateException, IOException {
		
		ProtocolorderImage objprofile =  ProtocolMasterService.downloadprotocolorderimagesql(fileid);
		
		byte[] data = null;
		
		if(objprofile != null)
		{
			data = objprofile.getImage().getData();
			ByteArrayInputStream bis = new ByteArrayInputStream(data);	
		    HttpHeaders header = new HttpHeaders();
		    header.setContentType(MediaType.parseMediaType("image/png"));
		    header.set("Content-Disposition", "attachment; filename="+filename+"."+extension);
		    
		    return new ResponseEntity<>(new InputStreamResource(bis), header, HttpStatus.OK);
		}else {
			
		
		return null;
		}
	
	}
	
	@GetMapping("downloadprotocolorderfilesql/{fileid}/{filename}/{extension}")
	public ResponseEntity<InputStreamResource> downloadprotocolorderfilesql(@PathVariable String fileid
		, @PathVariable String filename, @PathVariable String extension) throws IllegalStateException, IOException {
		
		LSprotocolfilesContent lsprotocolfilesContent = ProtocolMasterService.downloadprotocolorderfilesql(fileid);
		
		byte[] data = null;
		
		if(lsprotocolfilesContent != null)
		{
			data = lsprotocolfilesContent.getFile().getData();
			ByteArrayInputStream bis = new ByteArrayInputStream(data);	
		    HttpHeaders header = new HttpHeaders();
		    header.setContentType(MediaType.parseMediaType("image/png"));
		    header.set("Content-Disposition", "attachment; filename="+filename+"."+extension);
		    
		    return new ResponseEntity<>(new InputStreamResource(bis), header, HttpStatus.OK);
		}else {
			
		
		return null;
		}
	}
	
	@PostMapping(value = "/getProtocolOrderworkflowhistoryList")
	protected List<LSprotocolorderworkflowhistory> getProtocolOrderworkflowhistoryList
	(@RequestBody LSprotocolorderworkflowhistory lsprotocolorderworkflowhistory)throws Exception {
		return ProtocolMasterService.getProtocolOrderworkflowhistoryList(lsprotocolorderworkflowhistory);
	}
	
	
	@PostMapping("/uploadvideo")
	public Map<String, Object> uploadvideo(@RequestParam MultipartFile file,
			@RequestParam Integer protocolstepcode, 
			@RequestParam Integer protocolmastercode, 
			@RequestParam Integer stepno,
			@RequestParam String protocolstepname,
			@RequestParam String originurl) throws InvalidKeyException, NoSuchAlgorithmException, IllegalArgumentException
	{
	
		return ProtocolMasterService.uploadvideo(file, protocolstepcode,protocolmastercode,stepno,protocolstepname,originurl );

	}
	
	@PostMapping("/uploadprotocolordervideo")
	public Map<String, Object> uploadprotocolordervideo(@RequestParam MultipartFile file,
			@RequestParam Integer protocolorderstepcode, 
			@RequestParam Long protocolordercode, 
			@RequestParam Integer stepno,
			@RequestParam String protocolstepname,
			@RequestParam String originurl)throws Exception
	{
		return ProtocolMasterService.uploadprotocolordervideo(file, protocolorderstepcode,protocolordercode,stepno,protocolstepname,originurl );
	}
	
	
	@PostMapping("/uploadprotocolordervideosql")
	public Map<String, Object> uploadprotocolordervideosql(@RequestParam MultipartFile file,
			@RequestParam Integer protocolorderstepcode, 
			@RequestParam Long protocolordercode, 
			@RequestParam Integer stepno,
			@RequestParam String protocolstepname,
			@RequestParam String originurl) throws IOException
	{
		return ProtocolMasterService.uploadprotocolordervideosql(file, protocolorderstepcode,protocolordercode,stepno,protocolstepname,originurl );
	}
	
	
	@GetMapping("downloadprotocolordervideo/{fileid}/{tenant}/{filename}/{extension}")
	public ResponseEntity<InputStreamResource> downloadprotocolordervideo(@PathVariable String fileid
			, @PathVariable String tenant, @PathVariable String filename, @PathVariable String extension) throws IllegalStateException, IOException {
		
		ByteArrayInputStream bis = ProtocolMasterService.downloadprotocolordervideo(fileid, tenant);
		
	    HttpHeaders header = new HttpHeaders();
	    header.setContentType(MediaType.parseMediaType("image/png"));
	    header.set("Content-Disposition", "attachment; filename="+filename+"."+extension);
	    
	    return new ResponseEntity<>(new InputStreamResource(bis), header, HttpStatus.OK);
	}
	
	@GetMapping("downloadprotocolvideo/{fileid}/{tenant}/{filename}/{extension}")
	public ResponseEntity<InputStreamResource> downloadprotocolvideo(@PathVariable String fileid
			, @PathVariable String tenant, @PathVariable String filename, @PathVariable String extension) throws IllegalStateException, IOException {
		
		ByteArrayInputStream bis = ProtocolMasterService.downloadprotocolvideo(fileid, tenant);
		
	    HttpHeaders header = new HttpHeaders();
	    String mediatype = commonfunction.getMIMEtypeonextension(extension);
	    header.setContentType(MediaType.parseMediaType(mediatype));
	    header.set("Content-Disposition", "attachment; filename="+filename+"."+extension);
	    
	    return new ResponseEntity<>(new InputStreamResource(bis), header, HttpStatus.OK);
	}
	
	@PostMapping("/removeprotocolvideo")
	public boolean removeprotocolvideo(@RequestBody Map<String, String> body)throws Exception
	{
		return ProtocolMasterService.removeprotocolvideo(body);
	}
	
	@PostMapping("/removeprotocolordervideo")
	public boolean removeprotocolordervideo(@RequestBody Map<String, String> body)throws Exception
	{
		return ProtocolMasterService.removeprotocolordervideo(body);
	}
	@PostMapping("/uploadvideosql")
	public Map<String, Object> uploadvideosql(@RequestParam MultipartFile file,
			@RequestParam Integer protocolstepcode, 
			@RequestParam Integer protocolmastercode, 
			@RequestParam Integer stepno,
			@RequestParam String protocolstepname,
			@RequestParam String originurl) throws IOException
	{
		return ProtocolMasterService.uploadvideosql(file, protocolstepcode,protocolmastercode,stepno,protocolstepname,originurl );
	}
	
	
	@GetMapping("downloadprotocolordervideosql/{fileid}/{filename}/{extension}")
	public ResponseEntity<InputStreamResource> downloadprotocolordervideosql(@PathVariable String fileid
			, @PathVariable String filename, @PathVariable String extension) throws IllegalStateException, IOException {
		
		Protocolordervideos objprofile =  ProtocolMasterService.downloadprotocolordervideosql(fileid);
		
		byte[] data = null;
		
		if(objprofile != null)
		{
			data = objprofile.getVideo().getData();
			ByteArrayInputStream bis = new ByteArrayInputStream(data);	
		    HttpHeaders header = new HttpHeaders();
		    header.setContentType(MediaType.parseMediaType("image/png"));
		    header.set("Content-Disposition", "attachment; filename="+filename+"."+extension);
		    
		    return new ResponseEntity<>(new InputStreamResource(bis), header, HttpStatus.OK);
		}else {
			
		
		return null;
		}
	
	}
	
	@GetMapping("downloadprotocolvideosql/{fileid}/{filename}/{extension}")
	public ResponseEntity<InputStreamResource> downloadprotocolvideosql(@PathVariable String fileid
			, @PathVariable String filename, @PathVariable String extension) throws IllegalStateException, IOException {
		
		Protocolvideos objprofile =  ProtocolMasterService.downloadprotocolvideosql(fileid);
		
		byte[] data = null;
		
		if(objprofile != null)
		{
			data = objprofile.getVideo().getData();
			ByteArrayInputStream bis = new ByteArrayInputStream(data);	
		    HttpHeaders header = new HttpHeaders();
		    header.setContentType(MediaType.parseMediaType("image/png"));
		    header.set("Content-Disposition", "attachment; filename="+filename+"."+extension);
		    
		    return new ResponseEntity<>(new InputStreamResource(bis), header, HttpStatus.OK);
		}else {
			
		
		return null;
		}
	
	}
	
	@PostMapping("/removeprotocolvideossql")
	public boolean removeprotocolvideossql(@RequestBody Map<String, String> body)throws Exception
	{
		return ProtocolMasterService.removeprotocolvideossql(body);
	}

	@PostMapping("/removeprotocolordervideossql")
	public boolean removeprotocolordervideossql(@RequestBody Map<String, String> body)throws Exception
	{
		return ProtocolMasterService.removeprotocolordervideossql(body);
	}
	
	@PostMapping(value = "/getprojectteam")
	protected boolean getprojectteam(@RequestBody LSuserMaster objClass)throws Exception {

		return ProtocolMasterService.getprojectteam(objClass);
	}
	
	@PostMapping(value = "/skipprotocolstep")
	protected LSlogilabprotocolsteps skipprotocolstep(@RequestBody LSlogilabprotocolsteps lslogilabprotocolsteps)throws Exception {

		return ProtocolMasterService.skipprotocolstep(lslogilabprotocolsteps);
	}
	
	@GetMapping("/gettaskmaster")
	public List<LStestmasterlocal> gettaskmaster(HttpServletRequest request)throws Exception {
		return ProtocolMasterService.gettaskmaster();
	}
	
	@PostMapping(value = "/getswitchdata")
	protected Map<String,Object> getswitchdata(@RequestBody LSlogilabprotocoldetail lslogilabprotocoldetail )throws Exception {

		return ProtocolMasterService.getswitchdata(lslogilabprotocoldetail);
	}
	
	
	@PostMapping(value = "/updatesharetomeorder")
	protected  Map<String,Object> updatesharetomeorder(@RequestBody Lsprotocolordershareto Lsprotocolordershareto )throws Exception {

		return ProtocolMasterService.updatesharetomeorder(Lsprotocolordershareto);
	}
	
	@PostMapping(value = "/updatesharebymemeorder")
	protected  Map<String,Object> updatesharebymemeorder(@RequestBody Lsprotocolordersharedby Lsprotocolordersharedby )throws Exception {

		return ProtocolMasterService.updatesharebymemeorder(Lsprotocolordersharedby);
	}
	
	@PostMapping("/Getprotocollinksignaturesql")
	public Map<String, Object> Getprotocollinksignaturesql(@RequestBody Map<String, String> body)throws Exception
	{
		return ProtocolMasterService.Getprotocollinksignaturesql(body);
	}
	
	@PostMapping(value = "/addProtocolOrderafterfirstofter")
	protected Map<String, Object> addProtocolOrderafterfirstofter(@RequestBody LSlogilabprotocoldetail LSlogilabprotocoldetail)throws Exception {

		return ProtocolMasterService.addProtocolOrderafterfirstofter(LSlogilabprotocoldetail);

	}
	
	@PostMapping(value = "/updateprotocolstepno")
	protected List<LSprotocolstep> updateprotocolstepno(@RequestBody LSprotocolstep[] lSprotocolstep)throws Exception {

		return ProtocolMasterService.updateprotocolstepno(lSprotocolstep);

	}
	
	@PostMapping(value = "/updateprotocolstepnoonorder")
	protected List<LSlogilabprotocolsteps> updateprotocolstepnoonorder(@RequestBody LSlogilabprotocolsteps[] LSlogilabprotocolsteps)throws Exception {

		return ProtocolMasterService.updateprotocolstepnoonorder(LSlogilabprotocolsteps);

	}
	@GetMapping("/getprotocolperticulerstep/{protocolstepcodeorordercode}/{multitenant}/{protocoltype}")
	protected LSprotocolstepInformation getprotocolperticulerstep(@PathVariable Integer protocolstepcodeorordercode,@PathVariable Integer multitenant,@PathVariable Integer protocoltype)throws Exception {

		return ProtocolMasterService.getprotocolperticulerstep(protocolstepcodeorordercode,multitenant,protocoltype);

	}
	

	@PostMapping("/Getprotocolordersonassignedandmyorders")
	public Map<String,Object> Getprotocolordersonassignedandmyorders(@RequestBody Map<String, Object>  objorder)throws Exception
	{
		return ProtocolMasterService.Getprotocolordersonassignedandmyorders(objorder);
	}
	
	@PostMapping("/Getprotocolordersonshared")
	public Map<String,Object> Getprotocolordersonshared(@RequestBody Map<String, Object>  objorder)throws Exception
	{
		return ProtocolMasterService.Getprotocolordersonshared(objorder);
	}
	
	@PostMapping(value = "/impoertjsonforprotocol")
	protected Map<String, Object> impoertjsonforprotocol(@RequestBody LSprotocolstep[] argObj)throws Exception {

		return ProtocolMasterService.impoertjsonforprotocol(argObj);
	
	}
	
	@PostMapping("/deleteprotocolstepversion")
	public Map<String, Object> deleteprotocolstepversion(@RequestBody LSprotocolstep body)throws Exception
	{
		return ProtocolMasterService.deleteprotocolstepversion(body);
//		return true;
	}
	
	
	@PostMapping("/cancelprotocolorder")
	public LSlogilabprotocoldetail cancelprotocolorder(@RequestBody LSlogilabprotocoldetail body)throws Exception
	{
		return ProtocolMasterService.cancelprotocolorder(body);
//		return true;
	}
	
	
	@PostMapping(value = "/getprotocolstephistory")
	protected  List<LSprotocolorderstephistory> getprotocolstephistory(@RequestBody LSprotocolorderstephistory objuser)throws Exception {
	
		return  ProtocolMasterService.getprotocolstephistory(objuser);
	}
	
	@PostMapping(value = "/updatetransactionhistory")
	protected LSprotocolorderstephistory updatetransactionhistory(@RequestBody LSprotocolorderstephistory objuser)throws Exception {
	
		return  ProtocolMasterService.updatetransactionhistory(objuser);
	}
	
	@PostMapping(value = "/Outofstockinventorynotificationprotocol")
	protected Map<String, Object>  Outofstockinventorynotificationprotocol(@RequestBody LSprotocolordersampleupdates lsprotocolordersampleupdates)throws Exception {
	
		return  ProtocolMasterService.Outofstockinventorynotificationprotocol(lsprotocolordersampleupdates);
	}
	@PostMapping(value = "/getsingleprotocol")
	protected  Map<String, Object> getsingleprotocol(@RequestBody Map<String, Object> objuser)throws Exception {
	
		return  ProtocolMasterService.getsingleprotocol(objuser);
	}
	
	@PostMapping("/protocolTemplateSave")
	public Map<String, Object> protocolTemplateSave(@RequestBody Map<String, Object> body)throws Exception
	{
		return ProtocolMasterService.protocolTemplateSave(body);
//		return true;
	}
	
	@PostMapping("/protocolOrderSave")
	public Map<String, Object> protocolOrderSave(@RequestBody Map<String, Object> body)throws Exception
	{
		return ProtocolMasterService.protocolOrderSave(body);
//		return true;
	}

	@PostMapping("/insertlinkimages")
	public Map<String,Object> insertlinkimages(@RequestBody Map<String,Object> obj)throws Exception
	{
		return ProtocolMasterService.insertlinkimages(obj);
	}
	@PostMapping("/insertlinkfiles")
	public Map<String,Object> insertlinkfiles(@RequestBody Map<String,Object> obj)throws Exception
	{
		return ProtocolMasterService.insertlinkfiles(obj);
	}

	@PostMapping("/onStartProtocolOrder")
	public Map<String, Object> onStartProtocolOrder(@RequestBody Map<String, Object> body)throws Exception
	{
		return ProtocolMasterService.onStartProtocolOrder(body);
	}
	
	@PostMapping("/getProtocolTemplateVersionChanges")
	public Map<String, Object> getProtocolTemplateVersionChanges(@RequestBody Map<String, Object> body)throws Exception
	{
		return ProtocolMasterService.getProtocolTemplateVersionChanges(body);
	}
	
	@PostMapping("/getProtocolOrderVersionChanges")
	public Map<String, Object> getProtocolOrderVersionChanges(@RequestBody Map<String, Object> body)throws Exception
	{
		return ProtocolMasterService.getProtocolOrderVersionChanges(body);
	}
	
	@PostMapping("/getProtocolTemplateVersionsForCompare")
	public Map<String, Object> getProtocolTemplateVersionsForCompare(@RequestBody Map<String, Object> body)throws Exception
	{
		return ProtocolMasterService.getProtocolTemplateVersionsForCompare(body);
	}
	
	@PostMapping("/getProtocolOrderVersionsForCompare")
	public Map<String, Object> getProtocolOrderVersionsForCompare(@RequestBody Map<String, Object> body)throws Exception
	{
		return ProtocolMasterService.getProtocolOrderVersionsForCompare(body);
	}
	
	@PostMapping("/LockUnlockprotocolorders")
	public LSlogilabprotocoldetail LockUnlockprotocolorders(@RequestBody LSlogilabprotocoldetail protocolorders) throws ParseException {
		return ProtocolMasterService.LockUnlockprotocolorders(protocolorders);
	}
	@PostMapping("/GetUnlockscreendata")
	public List<Logilabprotocolorders> GetUnlockscreendata(@RequestBody LSuserMaster protocolorders) {
		return ProtocolMasterService.GetUnlockscreendata(protocolorders);
	}
	
	@PostMapping("/Unloackprotocolorders")
	public Boolean Unloackprotocolorders(@RequestBody Long[] protocolorders) {
		return ProtocolMasterService.Unloackprotocolorders(protocolorders);
	}
	
	@PostMapping(value = "/updatetProtocolTemplateTransaction")
	protected LSprotocolupdates updatetProtocolTemplateTransaction(@RequestBody LSprotocolupdates objuser)throws Exception {
	
		return  ProtocolMasterService.updatetProtocolTemplateTransaction(objuser);
	}
	
	@PostMapping(value = "/updateprotocolordertransactions")
	protected List<LSprotocolorderstephistory> updateprotocolordertransactions(@RequestBody LSprotocolorderstephistory[] objuser)throws Exception {
	
		return  ProtocolMasterService.updateprotocolordertransactions(objuser);
	}
	
	@PostMapping("/protocolTemplateContentMove")
	public Map<String, Object> protocolTemplateContentMove(@RequestBody Map<String, Object> body)throws Exception
	{
		return ProtocolMasterService.protocolTemplateContentMove(body);
//		return true;
	}
	
	@PostMapping("/Getprotocolcancelledorders")
	public Map<String,Object> Getprotocolcancelledorders(@RequestBody LSlogilabprotocoldetail  objorder)throws Exception
	{
		return ProtocolMasterService.Getprotocolcancelledorders(objorder);
	}
	
	@PostMapping(value = "/getprotocolcode")
	protected List<Integer> getprotocolcode(@RequestBody LSprotocolmaster objuser)throws Exception {
	
		return  ProtocolMasterService.getprotocolcode(objuser);
	}
	@PostMapping(value = "/RetireProtocolMaster")
	public Map<String, Object> RetireProtocolMaster(@RequestBody Map<String, Object> argObj)throws Exception {

		return ProtocolMasterService.RetireProtocolMaster(argObj);

	}
	
	@PostMapping(value = "/getusercodeandusername")
	public List<UserProjection> getusercodeandusername(@RequestBody LSSiteMaster argObj)throws Exception {

		return ProtocolMasterService.getusercodeandusername(argObj);

	}

	@PostMapping("/sendapprovel")
	public LSlogilabprotocoldetail sendapprovel(@RequestBody LSlogilabprotocoldetail objdir)
	{
		return ProtocolMasterService.sendapprovel(objdir);
	}
	@PostMapping("/acceptapprovel")
	public LSlogilabprotocoldetail acceptapprovel(@RequestBody LSlogilabprotocoldetail objdir)throws Exception
	{
		return ProtocolMasterService.acceptapprovel(objdir);
	}
	
	@PostMapping("/Validateprotocolcountforfreeuser")
	public boolean Validateprotocolcountforfreeuser(@RequestBody LSSiteMaster lssitemaster)
	{
		return ProtocolMasterService.Validateprotocolcountforfreeuser(lssitemaster);
	}
	
	@PostMapping(value="/stopprotoautoregister")
	public LSlogilabprotocoldetail stopregister(@RequestBody LSlogilabprotocoldetail proobj)
			throws Exception {

		return ProtocolMasterService.stopprotoautoregister(proobj);
	}

	@PostMapping("/Exportwithgroupdocs")
	public ResponseEntity<InputStreamResource> Exportwithgroupdocs(@RequestBody LSprotocolmaster protocol) throws IOException
	{
		ByteArrayInputStream bis = ProtocolMasterService.Exportwithgroupdocs(protocol);

		HttpHeaders header = new HttpHeaders();
//		header.setContentType(MediaType.parseMediaType("image/png"));
		header.set("Content-Disposition", "attachment; filename=" + protocol.getProtocolmastername() + ".docx");
		return new ResponseEntity<>(new InputStreamResource(bis), header, HttpStatus.OK);
	}
	
	@PostMapping("/Exportwithspire")
	public ResponseEntity<InputStreamResource> Exportwithspire(@RequestBody LSprotocolmaster protocol) throws IOException
	{
		ByteArrayInputStream bis = ProtocolMasterService.Exportwithspire(protocol);

		HttpHeaders header = new HttpHeaders();
//		header.setContentType(MediaType.parseMediaType("image/png"));
		header.set("Content-Disposition", "attachment; filename=" + protocol.getProtocolmastername() + ".docx");
		return new ResponseEntity<>(new InputStreamResource(bis), header, HttpStatus.OK);
	}
	
	@PostMapping("/GetAllorders")
	public ResponseEntity<Object> GetAllorders(@RequestBody LSuserMaster objuser)
	{
		return ProtocolMasterService.GetAllorders(objuser);
	}
	
	@PostMapping("/GetAllprotocols")
	public ResponseEntity<Object> GetAllprotocols(@RequestBody LoggedUser objuser)
	{
		return ProtocolMasterService.GetAllprotocols(objuser);
	}
	
	@PostMapping("/getprotocoloncode")
	public LSprotocolmaster getprotocoloncode(@RequestBody LSprotocolmaster protocol)throws Exception
	{
		return ProtocolMasterService.getprotocoloncode(protocol);
	}
	
	@PostMapping("/GetProtocolOrderByCode")
	public Map<String, Object> GetProtocolOrderByCode(@RequestBody LSlogilabprotocoldetail order)
	{
		return ProtocolMasterService.GetProtocolOrderByCode(order);
	}
	
	@PostMapping("/registerDuplicateOrder")
	public Map<String, Object> registerDuplicateOrder(@RequestBody LSlogilabprotocoldetail proobj)
			throws Exception {

		return ProtocolMasterService.registerDuplicateOrder(proobj);
	}
	
	@PostMapping(value = "/updateTransactionHistoryList")
	protected List<LSprotocolorderstephistory> updateTransactionHistoryList(@RequestBody LSprotocolorderstephistory[] userList) throws Exception {
	
		return  ProtocolMasterService.updateTransactionHistoryList(userList);
	}
	
	@PostMapping("/GetprotocolRejectededorders")
	public Map<String,Object> GetprotocolRejectededorders(@RequestBody LSlogilabprotocoldetail  objorder)throws Exception
	{
		return ProtocolMasterService.GetprotocolRejectededorders(objorder);
	}
	
	@GetMapping("/getSingleProtocolcontent")
	public LSlogilabprotocoldetail getSingleProtocolcontent(@RequestBody LSlogilabprotocoldetail objuser) throws IOException
	{
		return ProtocolMasterService.getSingleProtocolcontent(objuser);
	}
	
	@PostMapping("/saveProtocolFile")
	public LSprotocolmaster saveProtocolFile(MultipartHttpServletRequest objfile)throws Exception {
		return ProtocolMasterService.saveProtocolFile(objfile);
	}
	
	@PostMapping("/downloadreadablefiles")
	public ResponseEntity<InputStreamResource> downloadreadablefiles(@RequestBody LSprotocolmaster protocol) throws IOException
	{
		ByteArrayInputStream bis = ProtocolMasterService.downloadreadablefiles(protocol);

//		HttpHeaders header = new HttpHeaders();
//		header.set("Content-Disposition", "attachment; filename=" + protocol.getProtocolmastername() + ".pdf");
//		return new ResponseEntity<>(new InputStreamResource(bis), header, HttpStatus.OK);
		
		HttpHeaders header = new HttpHeaders();
	    String mediatype = commonfunction.getMIMEtypeonextension("pdf");
	    header.setContentType(MediaType.parseMediaType(mediatype));
	    header.set("Content-Disposition", "attachment; filename="+protocol.getProtocolmastername()+".pdf");
	    
	    return new ResponseEntity<>(new InputStreamResource(bis), header, HttpStatus.OK);
	}
}