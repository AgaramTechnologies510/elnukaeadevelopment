package com.agaram.eln.primary.controller.instrumentDetails;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.ClientAbortException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.agaram.eln.primary.fetchmodel.getorders.LogilabOrderDetails;
import com.agaram.eln.primary.fetchmodel.getorders.LogilabOrdermastersh;
import com.agaram.eln.primary.fetchmodel.getorders.Logilabordermaster;
import com.agaram.eln.primary.fetchmodel.getorders.Logilaborders;
import com.agaram.eln.primary.fetchmodel.getorders.Logilaborderssh;
import com.agaram.eln.primary.model.cfr.LSactivity;
import com.agaram.eln.primary.model.fileManipulation.Fileimages;
import com.agaram.eln.primary.model.fileManipulation.Fileimagestemp;
import com.agaram.eln.primary.model.general.Response;
import com.agaram.eln.primary.model.instrumentDetails.LSSelectedTeam;
import com.agaram.eln.primary.model.instrumentDetails.LSSheetOrderStructure;
import com.agaram.eln.primary.model.instrumentDetails.LSlogilablimsorderdetail;
import com.agaram.eln.primary.model.instrumentDetails.LSprotocolfolderfiles;
import com.agaram.eln.primary.model.instrumentDetails.LSsheetfolderfiles;
import com.agaram.eln.primary.model.instrumentDetails.LsOrderLinks;
import com.agaram.eln.primary.model.instrumentDetails.LsOrderSampleUpdate;
import com.agaram.eln.primary.model.instrumentDetails.LsOrderattachments;
import com.agaram.eln.primary.model.instrumentDetails.LsSheetorderlimsrefrence;
import com.agaram.eln.primary.model.instrumentDetails.Lsordersharedby;
import com.agaram.eln.primary.model.instrumentDetails.Lsordershareto;
import com.agaram.eln.primary.model.instrumentDetails.Lsprotocolorderstructure;
import com.agaram.eln.primary.model.instrumentDetails.Lsresultfororders;
import com.agaram.eln.primary.model.masters.Lsrepositoriesdata;
import com.agaram.eln.primary.model.methodsetup.CloudParserFile;
import com.agaram.eln.primary.model.methodsetup.ELNFileAttachments;
import com.agaram.eln.primary.model.methodsetup.Method;
import com.agaram.eln.primary.model.protocols.LSlogilabprotocoldetail;
import com.agaram.eln.primary.model.protocols.LSprotocolselectedteam;
import com.agaram.eln.primary.model.sheetManipulation.LSsamplefile;
import com.agaram.eln.primary.model.sheetManipulation.LSsamplefileversion;
import com.agaram.eln.primary.model.sheetManipulation.LSworkflow;
import com.agaram.eln.primary.model.usermanagement.LSSiteMaster;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;
import com.agaram.eln.primary.model.usermanagement.LSusergroup;
import com.agaram.eln.primary.repository.methodsetup.CloudParserFileRepository;
import com.agaram.eln.primary.repository.sheetManipulation.LSsamplefileRepository;
import com.agaram.eln.primary.repository.usermanagement.LSprojectmasterRepository.ProjectOrTaskOrMaterialView;
import com.agaram.eln.primary.service.instrumentDetails.InstrumentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/Instrument")
public class InstrumentController {

	@Autowired
	private InstrumentService instrumentService;

	@Autowired
	private LSsamplefileRepository lssamplefileRepository;

	@Autowired
	CloudParserFileRepository cloudparserfilerepository;

	@PostMapping("/GetInstrumentParameters")
	public Map<String, Object> getInstrumentparameters(@RequestBody LSSiteMaster lssiteMaster)throws Exception {
		return instrumentService.getInstrumentparameters(lssiteMaster);
	}

	@PostMapping("/InsertELNOrder")
	public LSlogilablimsorderdetail InsertELNOrder(@RequestBody LSlogilablimsorderdetail objorder)throws Exception {
		
		return instrumentService.InsertELNOrder(objorder);
	}
	
//	@PostMapping("/InsertAutoRegisterOrder")
//	public LSlogilablimsorderdetail InsertAutoRegisterOrder(LSlogilablimsorderdetail objorder)throws Exception {
//
//		return instrumentService.InsertAutoRegisterOrder(objorder);
//	}
	
	@PostMapping("/GetOrderonClose")
	public Logilabordermaster GetOrderonClose(@RequestBody LSlogilablimsorderdetail objorder) {

		return instrumentService.GetOrderonClose(objorder);
	}
	
	@PostMapping("/GetdOrderCount")
	public Map<String, Object> getdOrderCount(@RequestBody LSuserMaster objuser)throws Exception {
		return instrumentService.getdOrderCount(objuser);
	}

	@PostMapping("/InsertActivities")
	public LSactivity InsertActivities(@RequestBody LSactivity objActivity)throws Exception {
		return instrumentService.InsertActivities(objActivity);
	}
	
	@PostMapping("/Getorderbytype")
	public Map<String, Object> Getorderbytype(@RequestBody LSlogilablimsorderdetail objorder)throws Exception {
		Map<String, Object> mapOrders = new HashMap<String, Object>();
		objorder.setOrderflag("N");
		if (objorder.getLsuserMaster().getUsername().trim().toLowerCase().equals("administrator")) {
			mapOrders.put("Pending", instrumentService.Getorderbytype(objorder));
		} else {
			if (objorder.getFiletype().equals(0)) {
				mapOrders.put("Pending", instrumentService.Getorderbytype(objorder));
			} else {
				mapOrders.put("Pending", instrumentService.Getorderbytypeanduser(objorder));
			}
		}
		objorder.setOrderflag("R");
		if (objorder.getLsuserMaster().getUsername().trim().toLowerCase().equals("administrator")) {
			mapOrders.put("Completed", instrumentService.Getorderbytype(objorder));
		} else {
			if (objorder.getFiletype().equals(0)) {
				mapOrders.put("Completed", instrumentService.Getorderbytype(objorder));
			} else {
				mapOrders.put("Completed", instrumentService.Getorderbytypeanduser(objorder));
			}
		}
		return mapOrders;
	}

	@PostMapping("/Getorderbytypeandflag")
	public Map<String, Object> Getorderbytypeandflag(@RequestBody LSlogilablimsorderdetail objorder)throws Exception {
		Map<String, Object> mapOrders = new HashMap<String, Object>();
		if (objorder.getLsuserMaster().getUsername() != null
				&& objorder.getLsuserMaster().getUsername().trim().toLowerCase().equals("administrator")) {

			instrumentService.GetorderbytypeandflagOrdersonly(objorder, mapOrders);
		} else {
			if (objorder.getFiletype().equals(0)) {

				instrumentService.GetorderbytypeandflagOrdersonly(objorder, mapOrders);
			} else {

				instrumentService.GetorderbytypeandflaganduserOrdersonly(objorder, mapOrders);
			}
		}

		mapOrders.put("Orderflag", objorder.getOrderflag());

		return mapOrders;
	}

	@PostMapping("/Getorderbytypeandflaglazy")
	public Map<String, Object> Getorderbytypeandflaglazy(@RequestBody LSlogilablimsorderdetail objorder)throws Exception {
		Map<String, Object> mapOrders = new HashMap<String, Object>();
		if (objorder.getLsuserMaster().getUsername().trim().toLowerCase().equals("administrator")) {
			instrumentService.Getorderbytypeandflaglazy(objorder, mapOrders);
		} else {
			if (objorder.getFiletype().equals(0)) {
				instrumentService.Getorderbytypeandflaglazy(objorder, mapOrders);
			} else {
				instrumentService.Getorderbytypeandflaganduserlazy(objorder, mapOrders);
			}
		}

		mapOrders.put("Orderflag", objorder.getOrderflag());

		return mapOrders;
	}

	@PostMapping("/GetsharedordersonFilter")
	public List<Logilaborders> GetsharedordersonFilter(@RequestBody LSSheetOrderStructure objdir)throws Exception
	{
		List<Logilaborders> lssheet = new ArrayList<Logilaborders>();
		
		return instrumentService.GetordersondirectoryFilter(objdir,lssheet);
	}

	@PostMapping("/GetmyordersonFilter")
	public List<Logilaborders> GetmyordersonFilter(@RequestBody LSSheetOrderStructure objdir)throws Exception
	{
		List<Logilaborders> lssheet = new ArrayList<Logilaborders>();
		
		return instrumentService.GetordersondirectoryFilter(objdir,lssheet);
	}
	
	@PostMapping("/Getorderallbytypeandflaglazy")
	public Map<String, Object> Getorderallbytypeandflaglazy(@RequestBody LSlogilablimsorderdetail objorder)throws Exception {
		Map<String, Object> mapOrders = new HashMap<String, Object>();
		if (objorder.getLsuserMaster().getUsername().trim().toLowerCase().equals("administrator")) {
			instrumentService.Getorderallbytypeandflaglazy(objorder, mapOrders);
		} else {
			if (objorder.getFiletype().equals(0)) {
				instrumentService.Getorderallbytypeandflaglazy(objorder, mapOrders);
			} else {
				instrumentService.Getorderallbytypeandflaganduserlazy(objorder, mapOrders);
			}
		}

		mapOrders.put("Orderflag", objorder.getOrderflag());

		return mapOrders;
	}

	@PostMapping("/GetWorkflowonUser")
	public List<LSworkflow> GetWorkflowonUser(@RequestBody LSuserMaster objuser)throws Exception {
		return instrumentService.GetWorkflowonUser(objuser);
	}

	@PostMapping("/GetWorkflowanduseronUsercode")
	public Map<String, Object> GetWorkflowanduseronUsercode(@RequestBody LSuserMaster usercode)throws Exception {
		return instrumentService.GetWorkflowanduseronUsercode(usercode);
	}

	@PostMapping("/GetorderStatus")
	public LogilabOrderDetails GetorderStatus(@RequestBody LSlogilablimsorderdetail objorder)throws Exception {
		return instrumentService.GetorderStatus(objorder);
	}
	
	@PostMapping("/GetorderStatusFromBatchID")
	public LSlogilablimsorderdetail GetorderStatusFromBatchID(@RequestBody LSlogilablimsorderdetail objorder)throws Exception {
		return instrumentService.GetorderStatusFromBatchID(objorder);
	}

	@PostMapping("/GetdetailorderStatus")
	public LSlogilablimsorderdetail GetdetailorderStatus(@RequestBody LSlogilablimsorderdetail objupdatedorder)throws Exception {
		return instrumentService.GetdetailorderStatus(objupdatedorder);
	}

	
//	@PostMapping("/GetResults")
//	public Map<String, Object> GetResults(@RequestBody LSlogilablimsorderdetail objorder)throws Exception {
//		return instrumentService.GetResults(objorder);
//	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/GetResults")
	public Map<String, Object> GetResults(@RequestBody Map<String, Object> mapObject)throws Exception {
		
//		final ObjectMapper mapper = new ObjectMapper();
		
		
		Map<String, Object> obj = (Map<String, Object>) mapObject.get("protoobj");
		if (obj == null) {
		//	return instrumentService.GetResults(objorder);
			LSlogilablimsorderdetail orderobj = new LSlogilablimsorderdetail();
			
			Object batcode=mapObject.get("batchcode");
			Long batchcode = ((Integer) batcode).longValue();
			orderobj.setBatchcode(batchcode);
			orderobj.setBatchid((String) mapObject.get("batchid"));
			orderobj.setFiletype((Integer)mapObject.get("filetype"));
			
		//	LSlogilablimsorderdetail orderobj = mapper.convertValue(mapObject.get("batchcode"), LSlogilablimsorderdetail.class);
		//	orderobj=mapper.convertValue(mapObject.get("batchid"), LSlogilablimsorderdetail.class);
		//	orderobj=mapper.convertValue(mapObject.get("filetype"), LSlogilablimsorderdetail.class);
			return instrumentService.GetResults(orderobj);
		}
		else {
			final String Protocolordername = (String) obj.get("Protocolordername");
			Object procode = obj.get("Protocolordercode");
	        Long Protocolordercode = ((Integer) procode).longValue();
	        
		    LSlogilabprotocoldetail proobj = new LSlogilabprotocoldetail();
		    proobj.setProtoclordername(Protocolordername);
		    proobj.setProtocolordercode(Protocolordercode);
		    
		    return instrumentService.GetResultsproto(proobj);
		}
		
	}
	
	
	@PostMapping("/SaveResultfile")
	public LSsamplefile SaveResultfile(MultipartHttpServletRequest objfile)throws Exception {

		return instrumentService.SaveResultfile(objfile);
	}
	
	@PostMapping("/onGetResultValuesFromSelectedOrder")
	public List<Lsresultfororders> onGetResultValuesFromSelectedOrder(@RequestBody LSlogilablimsorderdetail objorder)throws Exception {

		return instrumentService.onGetResultValuesFromSelectedOrder(objorder);
	}
	
	@PostMapping("/UpdateLimsOrder")
	public LSlogilablimsorderdetail UpdateLimsOrder(@RequestBody LSlogilablimsorderdetail objorder)throws Exception {
		
		return instrumentService.UpdateLimsOrder(objorder);
	}
	
	@PostMapping("/SheetChangeForLimsOrder")
	public LSlogilablimsorderdetail SheetChangeForLimsOrder(@RequestBody LSlogilablimsorderdetail objorder)throws Exception {
		
		return instrumentService.SheetChangeForLimsOrder(objorder);
	}

	@PostMapping("/Getupdatedorder")
	public LSlogilablimsorderdetail Getupdatedorder(@RequestBody LSlogilablimsorderdetail objorder)throws Exception {
		return instrumentService.Getupdatedorder(objorder);
	}

	@PostMapping("/Getorderforlink")
	public Map<String, Object> Getorderforlink(@RequestBody LSlogilablimsorderdetail objorder)throws Exception {
		return instrumentService.Getorderforlink(objorder);
	}

	@PostMapping("/CompleteOrder")
	public LSlogilablimsorderdetail CompleteOrder(@RequestBody LSlogilablimsorderdetail objorder)throws Exception {
		return instrumentService.CompleteOrder(objorder);
	}

	@PostMapping("/updateworflowforOrder")
	public LSlogilablimsorderdetail updateworflowforOrder(@RequestBody LSlogilablimsorderdetail objorder)throws Exception {
		return instrumentService.updateworflowforOrder(objorder);
	}

	@PostMapping("/Getfileversions")
	public List<LSsamplefileversion> Getfileversions(@RequestBody LSsamplefile objfile)throws Exception {
		return instrumentService.Getfileversions(objfile);
	}

	@PostMapping("/GetfileverContent")
	public String GetfileverContent(@RequestBody LSsamplefile objfile)throws Exception {
		return instrumentService.GetfileverContent(objfile);
	}

	@PostMapping("/GetResultfileverContent")
	public LSsamplefile GetResultfileverContent(@RequestBody LSsamplefile objfile)throws Exception {
		return instrumentService.GetResultfileverContent(objfile);
	}

	@PostMapping("/Getorderbyfile")
	public List<LSlogilablimsorderdetail> Getorderbyfile(@RequestBody LSlogilablimsorderdetail objorder)throws Exception {
		return instrumentService.Getorderbyfile(objorder);
	}

	@PostMapping("/GetOrdersByLinkedFiles")
	public Map<String, Object> GetOrdersByLinkedFiles(@RequestBody Map<String, Object> mapObject)throws Exception {
		return instrumentService.GetOrdersByLinkedFiles(mapObject);
	}
	
	@PostMapping("/GetorderForLINKsheet")
	public LogilabOrderDetails GetorderForLINKsheet(@RequestBody LSlogilablimsorderdetail objorder)throws Exception {
		return instrumentService.GetorderForLINKsheet(objorder);
	}
	
	@PostMapping("/Getexcelorder")
	public List<LogilabOrderDetails> Getexcelorder(@RequestBody LSlogilablimsorderdetail objorder)throws Exception {
		return instrumentService.Getexcelorder(objorder);
	}

	@PostMapping("/updateVersionandWorkflowhistory")
	public LSlogilablimsorderdetail updateVersionandWorkflowhistory(@RequestBody LSlogilablimsorderdetail objorder)throws Exception {
		return instrumentService.updateVersionandWorkflowhistory(objorder);
	}

	@PostMapping("/Uploadattachments")
	public LSlogilablimsorderdetail Uploadattachments(@RequestParam MultipartFile file,
			@RequestParam("order") Long batchcode, @RequestParam String filename,
			@RequestParam String fileexe, @RequestParam Integer usercode,
			@RequestParam("date") Date currentdate, @RequestParam Integer islargefile)
			throws IOException {
		return instrumentService.Uploadattachments(file, batchcode, filename, fileexe, usercode, currentdate,
				islargefile);
	}

	@PostMapping("/CloudUploadattachments")
	public LSlogilablimsorderdetail CloudUploadattachments(@RequestParam MultipartFile file,
			@RequestParam("order") Long batchcode, @RequestParam String filename,
			@RequestParam String fileexe, @RequestParam Integer usercode,
			@RequestParam("date") Date currentdate, @RequestParam Integer islargefile)
			throws IOException, InvalidKeyException, NoSuchAlgorithmException, IllegalArgumentException {
		return instrumentService.CloudUploadattachments(file, batchcode, filename, fileexe, usercode, currentdate,
				islargefile);
	}
	@PostMapping("/CloudELNFileUploadattachments")
	public Map<String, Object> CloudELNFileUploadattachments( @RequestParam MultipartFile[] file,
			@RequestParam("order") Long batchcode, @RequestParam String[] filename,
			@RequestParam String[] fileexe, @RequestParam Integer usercode,@RequestParam Integer methodkey,
			@RequestParam("date") Date currentdate, @RequestParam Integer islargefile)
			throws IOException, InvalidKeyException, NoSuchAlgorithmException, IllegalArgumentException {
		
			System.out.println(file);
			System.out.println(filename);
			System.out.println(fileexe);
			
		return instrumentService.CloudELNFileUploadattachments(file, batchcode, filename, fileexe, usercode, currentdate,
				islargefile,methodkey);
	}

	@PostMapping("/Uploadelnfileattachments")
	public Map<String, Object> Uploadelnfileattachments(@RequestParam MultipartFile file[],
			@RequestParam("order") Long batchcode, @RequestParam String[] filename,
			@RequestParam String[] fileexe, @RequestParam Integer usercode,@RequestParam Integer methodkey,
			@RequestParam("date") Date currentdate, @RequestParam Integer islargefile)
			throws IOException {
//		return instrumentService.Uploadelnfileattachments(file, batchcode, filename, fileexe, usercode, currentdate,
//				islargefile);
		return instrumentService.Uploadelnfileattachments(file, batchcode, filename, fileexe, usercode, currentdate,
				islargefile,methodkey);
	}

	@PostMapping("/downloadattachments")
	public LsOrderattachments downloadattachments(@RequestBody LsOrderattachments objattachments)
			throws IllegalStateException, IOException {
		return instrumentService.downloadattachments(objattachments);
	}
	
	@PostMapping("/downloadparserattachments")
	public ELNFileAttachments downloadparserattachments(@RequestBody ELNFileAttachments objattachments)
			throws IllegalStateException, IOException {
		return instrumentService.downloadparserattachments(objattachments);
	}

	@PostMapping("/Clouddownloadattachments")
	public LsOrderattachments Clouddownloadattachments(@RequestBody LsOrderattachments objattachments)
			throws IllegalStateException, IOException {
		return instrumentService.Clouddownloadattachments(objattachments);
	}
	
	@PostMapping("/Cloudparserdownloadattachments")
	public LsOrderattachments Cloudparserdownloadattachments(@RequestBody LsOrderattachments objattachments)
			throws IllegalStateException, IOException {
		return instrumentService.Cloudparserdownloadattachments(objattachments);
	}

	@PostMapping("/GetsampleordersonFilter")
	public List<Logilaborders> GetsampleordersonFilter(@RequestBody LSlogilablimsorderdetail objdir)throws Exception
	{
		List<Logilaborders> lssheet = new ArrayList<Logilaborders>();
		
		return instrumentService.GetsampleordersonFilter(objdir,lssheet);
	}
		
	@GetMapping("attachment/{fileid}")
	public ResponseEntity<InputStreamResource> downloadlargeattachment(@PathVariable String fileid)
			throws IllegalStateException, IOException {
		GridFsResource gridFsFile = instrumentService.retrieveLargeFile(fileid);

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.parseMediaType(gridFsFile.getContentType()));
//		header.setContentLength(gridFsFile.getLength());
		header.set("Content-Disposition", "attachment; filename=gg.pdf");

		return new ResponseEntity<>(new InputStreamResource(gridFsFile.getInputStream()), header, HttpStatus.OK);
	}

	@PostMapping("/cloudattachment")
	public ResponseEntity<InputStreamResource> downloadlargecloudattachment(
			@RequestBody LsOrderattachments objattachments) throws IllegalStateException, IOException {


//		HttpHeaders header = new HttpHeaders();
//		header.set("Content-Disposition", "attachment; filename=gg.pdf");
		
		InputStream stream =  instrumentService.retrieveColudLargeFile(objattachments.getFileid());
		
		HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_PNG);
        headers.set(HttpHeaders.CONTENT_DISPOSITION,  "attachment; filename=gg.pdf");
        
        InputStreamResource resource = new InputStreamResource(stream);

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM) // important!
                .body(resource);
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(new InputStreamResource(resource));
        
//        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)  // or detect mime type
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=gg.pdf")
//                .body(resource);
		
//		return new ResponseEntity<>( new InputStreamResource(stream), header, HttpStatus.OK);
	}

	@PostMapping("/parserattachment")
	public ResponseEntity<InputStreamResource> parserattachment(
			@RequestBody Method objattachments) throws IllegalStateException, IOException {

//		HttpHeaders header = new HttpHeaders();
//		header.set("Content-Disposition", "attachment; filename=gg.pdf");
//		
//       	CloudParserFile objfileuuid = cloudparserfilerepository.findByFilename(objattachments.getInstrawdataurl());
//
//		return new ResponseEntity<>(
//				new InputStreamResource(instrumentService.retrieveColudParserFile(objfileuuid.getFileid(),objattachments.getTenantid())), header,
//				HttpStatus.OK);
		
	    CloudParserFile objfileuuid =
	            cloudparserfilerepository.findByFilename(objattachments.getInstrawdataurl());

	    InputStreamResource resource = new InputStreamResource(
	            instrumentService.retrieveColudParserFile(
	                    objfileuuid.getFileid(),
	                    objattachments.getTenantid()
	            )
	    );

	    HttpHeaders headers = new HttpHeaders();
	    headers.add(HttpHeaders.CONTENT_DISPOSITION,
	            "attachment; filename=\"" + objfileuuid.getFilename() + "\"");
	    headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

	    return ResponseEntity.ok()
	            .headers(headers)
	            .body(resource);
	}
	
	@PostMapping("/sqlparserattachment")
	public ResponseEntity<InputStreamResource> sqlparserattachment(
			@RequestBody Method objattachments) throws IllegalStateException, IOException {

		HttpHeaders header = new HttpHeaders();
		header.set("Content-Disposition", "attachment; filename=" + objattachments.getFilename());

		GridFsResource gridFsFile = instrumentService.retrieveLargeFile(objattachments.getInstrawdataurl());

	//	HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.parseMediaType(gridFsFile.getContentType()));
//		header.setContentLength(gridFsFile.getLength());
		header.set("Content-Disposition", "attachment; filename=gg.pdf");

		return new ResponseEntity<>(new InputStreamResource(gridFsFile.getInputStream()), header, HttpStatus.OK);
	}
	
	
	@PostMapping("/deleteattachments")
	public LsOrderattachments deleteattachments(@RequestBody LsOrderattachments objattachments)throws Exception {
		return instrumentService.deleteattachments(objattachments);
	}

	@PostMapping("/Clouddeleteattachments")
	public LsOrderattachments Clouddeleteattachments(@RequestBody LsOrderattachments objattachments)throws Exception {
		return instrumentService.Clouddeleteattachments(objattachments);
	}

	@GetMapping("/getmetatag")
	public String getmetatag()throws Exception {
		String jsonString = "[{\"1e89782a-1357-4108-928c-4c05e2731397-T2\":{\"ParsedData\":{\"MultiFileds\":{\"MultiParserFields\":{\"MultiParsedFields_0\":[{\"SampleID\":\"Baseline\",\"Value1\":\"0.005\",\"Value2\":\"0.000\",\"Value3\":\"0.000\"},{\"SampleID\":\"Primer\",\"Value1\":\"0.502\",\"Value2\":\"0.982\",\"Value3\":\"1.004\"},{\"SampleID\":\"Cal.\",\"Value1\":\"0.506\",\"Value2\":\"0.996\",\"Value3\":\"0.996\"},{\"SampleID\":\"Cal.\",\"Value1\":\"0.396\",\"Value2\":\"0.504\",\"Value3\":\"0.508\"},{\"SampleID\":\"Cal.\",\"Value1\":\"0.297\",\"Value2\":\"0.207\",\"Value3\":\"0.200\"},{\"SampleID\":\"Cal.\",\"Value1\":\"0.196\",\"Value2\":\"0.099\",\"Value3\":\"0.095\"},{\"SampleID\":\"0.257\",\"Value1\":\"0.399\",\"Value2\":\"0.402\",\"Value3\":\"\"},{\"SampleID\":\"0.252\",\"Value1\":\"0.400\",\"Value2\":\"0.399\",\"Value3\":\"\"},{\"SampleID\":\"Final\",\"Value1\":\"Base\",\"Value2\":\"0.005\",\"Value3\":\"0.000\"}]},\"MultiParsedFieldsTableInfo\":{\"MultiParsedFieldsTableInfo_0\":[{\"sFieldName\":\"Method1\",\"sFieldValue\":\"MBAS\"},{\"sFieldName\":\"Method2\",\"sFieldValue\":\"phenol\"},{\"sFieldName\":\"Method3\",\"sFieldValue\":\"CN\"}]}},\"SingleFields\":[{\"FieldName\":\"Method1\",\"FieldValue\":\"MBAS\"},{\"FieldName\":\"Method2\",\"FieldValue\":\"phenol\"},{\"FieldName\":\"Method3\",\"FieldValue\":\"CN\"}]},\"FileMetaTags\":[{\"Property\":\"Name\",\"Values\":\"200701DR1-RUN-02\"},{\"Property\":\"Size\",\"Values\":\"14.1 KB\"},{\"Property\":\"Itemtype\",\"Values\":\"Adobe Acrobat Document\"},{\"Property\":\"Date modified\",\"Values\":\"18-02-2021 18:08\"},{\"Property\":\"Date created\",\"Values\":\"19-02-2021 17:36\"},{\"Property\":\"Date accessed\",\"Values\":\"19-02-2021 17:36\"},{\"Property\":\"Attributes\",\"Values\":\"A\"},{\"Property\":\"Perceived type\",\"Values\":\"Unspecified\"},{\"Property\":\"Owner\",\"Values\":\"AGL66\\\\Pasupathi\"},{\"Property\":\"Kind\",\"Values\":\"Document\"},{\"Property\":\"Rating\",\"Values\":\"Unrated\"}],\"BatchID\":\"001\",\"FileLink\":\"http://AGL66:8081/SDMS_Web/Login.html?GUID=1e89782a-1357-4108-928c-4c05e2731397&TaskID=T2\",\"TransferID\":\"BF204D77-F1D3-49E7-883B-61611C5A9F31\",\"OrderID\":\"20210218124411210\",\"Tags\":{\"UnMappedTemplateTags\":[],\"MappedTemplateTags\":{\"QC\":[{\"TagName\":\"Sample\",\"TagValue\":\"Pantoprazole tablets IP\",\"CreatedBy\":\"Administrator\",\"CreatedOn\":\"2021-02-19 12:06:15\"},{\"TagName\":\"Test\",\"TagValue\":\"Dissolution\",\"CreatedBy\":\"Administrator\",\"CreatedOn\":\"2021-02-19 12:06:15\"}]}}}}]";

		jsonString = instrumentService.getsampledata();
		try {

			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jsonArray = jsonObject.getJSONArray("sheets");

			jsonArray.forEach(item -> {
				JSONObject obj = (JSONObject) item;
				JSONArray jsonrowsArray = obj.getJSONArray("rows");
				jsonrowsArray.forEach(rowitem -> {
					JSONObject rowobj = (JSONObject) rowitem;
					if (rowobj.getInt("index") == 2) {
						JSONArray jsoncellsArray = rowobj.getJSONArray("cells");
						jsoncellsArray.forEach(cellitem -> {
							JSONObject cellobj = (JSONObject) cellitem;
							if (cellobj.getInt("index") == 4) {
								cellobj.put("value", "feee");
							}
						});
					}

				});
			});

			jsonObject.put("sheets", jsonArray);

			jsonString = jsonObject.toString();

			System.out.println("\n\njsonArray: " + jsonArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jsonString;
	}

	@PostMapping("/Insertshareorder")
	public Lsordershareto Insertshareorder(@RequestBody Lsordershareto objordershareto)throws Exception {
		return instrumentService.Insertshareorder(objordershareto);
	}

	@PostMapping("/Insertshareorderby")
	public Map<String, Object> Insertshareorderby(@RequestBody Lsordersharedby objordersharedby)throws Exception {
		return instrumentService.Insertshareorderby(objordersharedby);
	}

	@PostMapping("/Getordersharedbyme")
	public List<Lsordersharedby> Getordersharedbyme(@RequestBody Lsordersharedby lsordersharedby)throws Exception {
		return instrumentService.Getordersharedbyme(lsordersharedby);
	}

	@PostMapping("/Getordersharetome")
	public List<Lsordershareto> Getordersharetome(@RequestBody Lsordershareto lsordershareto)throws Exception {
		return instrumentService.Getordersharetome(lsordershareto);
	}

	@PostMapping("/Unshareorderby")
	public Lsordersharedby Unshareorderby(@RequestBody Lsordersharedby objordershareby)throws Exception {
		return instrumentService.Unshareorderby(objordershareby);
	}

	@PostMapping("/Unshareorderto")
	public Lsordershareto Unshareorderto(@RequestBody Lsordershareto lsordershareto)throws Exception {
		return instrumentService.Unshareorderto(lsordershareto);
	}

	@PostMapping("/GetsharedorderStatus")
	public Lsordersharedby GetsharedorderStatus(@RequestBody Lsordersharedby objorder) throws IOException, ParseException {
		return instrumentService.GetsharedorderStatus(objorder);
	}

	@PostMapping("/GetsharedtomeorderStatus")
	public Lsordershareto GetsharedtomeorderStatus(@RequestBody Lsordershareto objorder)throws Exception {
		return instrumentService.GetsharedtomeorderStatus(objorder);
	}

	@PostMapping("/GetResultsharedfileverContent")
	public LSsamplefile GetResultsharedfileverContent(@RequestBody LSsamplefile objfile)throws Exception {
		return instrumentService.GetResultsharedfileverContent(objfile);
	}

	@PostMapping("/SaveSharedResultfile")
	public LSsamplefile SaveSharedResultfile(MultipartHttpServletRequest objfile)throws Exception {
		return instrumentService.SaveSharedResultfile(objfile);
	}

	@PostMapping("/SharedCloudUploadattachments")
	public LSlogilablimsorderdetail SharedCloudUploadattachments(@RequestParam MultipartFile file,
			@RequestParam("order") Long batchcode, @RequestParam String filename,
			@RequestParam String fileexe, @RequestParam Integer usercode,
			@RequestParam("date") Date currentdate, @RequestParam Integer islargefile)
			throws IOException, InvalidKeyException, NoSuchAlgorithmException, IllegalArgumentException {
		return instrumentService.SharedCloudUploadattachments(file, batchcode, filename, fileexe, usercode, currentdate,
				islargefile);
	}

	@PostMapping("/SharedUploadattachments")
	public LSlogilablimsorderdetail SharedUploadattachments(@RequestParam MultipartFile file,
			@RequestParam("order") Long batchcode, @RequestParam String filename,
			@RequestParam String fileexe, @RequestParam Integer usercode,
			@RequestParam("date") Date currentdate, @RequestParam Integer islargefile)
			throws IOException {
		return instrumentService.SharedUploadattachments(file, batchcode, filename, fileexe, usercode, currentdate,
				islargefile);
	}

	@PostMapping("/SharedClouddeleteattachments")
	public LsOrderattachments SharedClouddeleteattachments(@RequestBody LsOrderattachments objattachments)throws Exception {
		return instrumentService.SharedClouddeleteattachments(objattachments);
	}

	@PostMapping("/shareddeleteattachments")
	public LsOrderattachments shareddeleteattachments(@RequestBody LsOrderattachments objattachments)throws Exception {
		return instrumentService.shareddeleteattachments(objattachments);
	}

	@PostMapping("/SharedClouddownloadattachments")
	public LsOrderattachments SharedClouddownloadattachments(@RequestBody LsOrderattachments objattachments)
			throws IllegalStateException, IOException {
		return instrumentService.SharedClouddownloadattachments(objattachments);
	}

	@PostMapping("/Shareddownloadattachments")
	public LsOrderattachments Shareddownloadattachments(@RequestBody LsOrderattachments objattachments)
			throws IllegalStateException, IOException {
		return instrumentService.Shareddownloadattachments(objattachments);
	}

	@GetMapping("Sharedattachment/{fileid}")
	public ResponseEntity<InputStreamResource> Shareddownloadlargeattachment(@PathVariable String fileid)
			throws IllegalStateException, IOException {
		GridFsResource gridFsFile = instrumentService.sharedretrieveLargeFile(fileid);

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.parseMediaType(gridFsFile.getContentType()));
//		header.setContentLength(gridFsFile.getLength());
		header.set("Content-Disposition", "attachment; filename=gg.pdf");

		return new ResponseEntity<>(new InputStreamResource(gridFsFile.getInputStream()), header, HttpStatus.OK);
	}

	@GetMapping("Sharedcloudattachment/{fileid}")
	public ResponseEntity<InputStreamResource> Shareddownloadlargecloudattachment(@PathVariable String fileid)
			throws IllegalStateException, IOException {

		HttpHeaders header = new HttpHeaders();
		header.set("Content-Disposition", "attachment; filename=gg.pdf");
		return new ResponseEntity<>(new InputStreamResource(instrumentService.sharedretrieveColudLargeFile(fileid)),
				header, HttpStatus.OK);
	}

	// cloud
	@GetMapping("/downloadNonCloud/{param}/{tenant}/{fileid}")
	public ResponseEntity<InputStreamResource> download(@PathVariable String param,@PathVariable String tenant, @PathVariable String fileid
			) throws IOException {

		return instrumentService.downloadattachmentsNonCloud(param, fileid);
	}
	
	@GetMapping("/downloadparserNonCloud/{param}/{tenant}/{fileid}")
	public ResponseEntity<InputStreamResource> downloadparser(@PathVariable String param,@PathVariable String tenant, @PathVariable String fileid
			) throws IOException {

		return instrumentService.downloadparserattachmentsNonCloud(param, fileid);
	}

	// normal
	@GetMapping("/download/{param}/{fileid}")
	public ResponseEntity<InputStreamResource> downloadNonCloud(@PathVariable String param, @PathVariable String fileid)
			throws IOException {

		return instrumentService.downloadattachments(param, fileid);
	}
	
	@GetMapping("/downloadparser/{param}/{fileid}")
	public ResponseEntity<InputStreamResource> downloadparserNonCloud(@PathVariable String param, @PathVariable String fileid)
			throws IOException {

		return instrumentService.downloadelnparserattachments(param, fileid);
	}

	@PostMapping("/GetOrderResourcesQuantitylst")
	public List<LsOrderSampleUpdate> GetOrderResourcesQuantitylst(@RequestBody LsOrderSampleUpdate objorder)throws Exception {
		return instrumentService.GetOrderResourcesQuantitylst(objorder);
	}

	@PostMapping("/SaveOrderResourcesQuantity")
	public Map<String, Object> SaveOrderResourcesQuantity(@RequestBody Map<String, Object> argobj)throws Exception {
		return instrumentService.SaveOrderResourcesQuantity(argobj);
	}

	@PostMapping("/GetEditedOrderResources")
	public List<Lsrepositoriesdata> GetEditedOrderResources(@RequestBody Lsrepositoriesdata objorder)throws Exception {
		return instrumentService.GetEditedOrderResources(objorder);
	}

	@PostMapping("/getmetatagdata")
	public String getmetatagdata(@RequestParam("order") Long batchcode, @RequestParam Integer indexrow,
			@RequestParam Integer cellindex, @RequestParam Integer sheetval,
			@RequestParam String tagdata, @RequestParam String tagvalue,
			@RequestParam String samplevalue, @RequestParam String sampledetails,
			@RequestParam Integer lssamplefile, @RequestParam Integer multitenant)throws Exception {
		String jsonString = "[{\"1e89782a-1357-4108-928c-4c05e2731397-T2\":{\"ParsedData\":{\"MultiFileds\":{\"MultiParserFields\":{\"MultiParsedFields_0\":[{\"SampleID\":\"Baseline\",\"Value1\":\"0.005\",\"Value2\":\"0.000\",\"Value3\":\"0.000\"},{\"SampleID\":\"Primer\",\"Value1\":\"0.502\",\"Value2\":\"0.982\",\"Value3\":\"1.004\"},{\"SampleID\":\"Cal.\",\"Value1\":\"0.506\",\"Value2\":\"0.996\",\"Value3\":\"0.996\"},{\"SampleID\":\"Cal.\",\"Value1\":\"0.396\",\"Value2\":\"0.504\",\"Value3\":\"0.508\"},{\"SampleID\":\"Cal.\",\"Value1\":\"0.297\",\"Value2\":\"0.207\",\"Value3\":\"0.200\"},{\"SampleID\":\"Cal.\",\"Value1\":\"0.196\",\"Value2\":\"0.099\",\"Value3\":\"0.095\"},{\"SampleID\":\"0.257\",\"Value1\":\"0.399\",\"Value2\":\"0.402\",\"Value3\":\"\"},{\"SampleID\":\"0.252\",\"Value1\":\"0.400\",\"Value2\":\"0.399\",\"Value3\":\"\"},{\"SampleID\":\"Final\",\"Value1\":\"Base\",\"Value2\":\"0.005\",\"Value3\":\"0.000\"}]},\"MultiParsedFieldsTableInfo\":{\"MultiParsedFieldsTableInfo_0\":[{\"sFieldName\":\"Method1\",\"sFieldValue\":\"MBAS\"},{\"sFieldName\":\"Method2\",\"sFieldValue\":\"phenol\"},{\"sFieldName\":\"Method3\",\"sFieldValue\":\"CN\"}]}},\"SingleFields\":[{\"FieldName\":\"Method1\",\"FieldValue\":\"MBAS\"},{\"FieldName\":\"Method2\",\"FieldValue\":\"phenol\"},{\"FieldName\":\"Method3\",\"FieldValue\":\"CN\"}]},\"FileMetaTags\":[{\"Property\":\"Name\",\"Values\":\"200701DR1-RUN-02\"},{\"Property\":\"Size\",\"Values\":\"14.1 KB\"},{\"Property\":\"Itemtype\",\"Values\":\"Adobe Acrobat Document\"},{\"Property\":\"Date modified\",\"Values\":\"18-02-2021 18:08\"},{\"Property\":\"Date created\",\"Values\":\"19-02-2021 17:36\"},{\"Property\":\"Date accessed\",\"Values\":\"19-02-2021 17:36\"},{\"Property\":\"Attributes\",\"Values\":\"A\"},{\"Property\":\"Perceived type\",\"Values\":\"Unspecified\"},{\"Property\":\"Owner\",\"Values\":\"AGL66\\\\Pasupathi\"},{\"Property\":\"Kind\",\"Values\":\"Document\"},{\"Property\":\"Rating\",\"Values\":\"Unrated\"}],\"BatchID\":\"001\",\"FileLink\":\"http://AGL66:8081/SDMS_Web/Login.html?GUID=1e89782a-1357-4108-928c-4c05e2731397&TaskID=T2\",\"TransferID\":\"BF204D77-F1D3-49E7-883B-61611C5A9F31\",\"OrderID\":\"20210218124411210\",\"Tags\":{\"UnMappedTemplateTags\":[],\"MappedTemplateTags\":{\"QC\":[{\"TagName\":\"Sample\",\"TagValue\":\"Pantoprazole tablets IP\",\"CreatedBy\":\"Administrator\",\"CreatedOn\":\"2021-02-19 12:06:15\"},{\"TagName\":\"Test\",\"TagValue\":\"Dissolution\",\"CreatedBy\":\"Administrator\",\"CreatedOn\":\"2021-02-19 12:06:15\"}]}}}}]";

		jsonString = instrumentService.getsampledata(batchcode, indexrow, cellindex, sheetval, tagdata, tagvalue,
				samplevalue, sampledetails, lssamplefile, multitenant);

		LSsamplefile LSsamplefile = lssamplefileRepository.findByfilesamplecode(lssamplefile);
		try {
			// JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jsonArray = jsonObject.getJSONArray("sheets");

			jsonArray.forEach(item -> {
				JSONObject obj = (JSONObject) item;
				JSONArray jsonrowsArray = obj.getJSONArray("rows");
				jsonrowsArray.forEach(rowitem -> {
					JSONObject rowobj = (JSONObject) rowitem;
					if (rowobj.getInt("index") == indexrow) {
						JSONArray jsoncellsArray = rowobj.getJSONArray("cells");
						jsoncellsArray.forEach(cellitem -> {
							JSONObject cellobj = (JSONObject) cellitem;
							if (cellobj.getInt("index") == cellindex) {
								cellobj.put("tag", tagvalue);
								cellobj.put("value", sampledetails);
							}
						});
					}

				});
			});

			jsonObject.put("sheets", jsonArray);

			jsonString = jsonObject.toString();

			instrumentService.updateordercontent(jsonString, LSsamplefile, multitenant);
			// JSONObject addedObj = (JSONObject) jsonObject.get("Added");

			// JSONArray jsonArray = new JSONArray(jsonString);
			System.out.println("\n\njsonArray: " + jsonArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jsonString;
	}

	@PostMapping("/Getuserworkflow")
	public Map<String, List<Integer>> Getuserworkflow(@RequestBody LSusergroup lsusergroup)throws Exception {
		return instrumentService.Getuserworkflow(lsusergroup);
	}

	@PostMapping("/Getuserprojects")
	public Map<String, Object> Getuserprojects(@RequestBody LSuserMaster objuser)throws Exception {
		return instrumentService.Getuserprojects(objuser);
	}

	@PostMapping("/Getinitialorders")
	public Map<String, Object> Getinitialorders(@RequestBody LSlogilablimsorderdetail objorder)throws Exception {
		return instrumentService.Getinitialorders(objorder);
	}

	@PostMapping("/Getremainingorders")
	public List<Logilabordermaster> Getremainingorders(@RequestBody LSlogilablimsorderdetail objorder)throws Exception {
		return instrumentService.Getremainingorders(objorder);
	}

	@PostMapping("/uploadsheetimages")
	public Map<String, Object> uploadsheetimages(@RequestParam MultipartFile file,
			@RequestParam String originurl, @RequestParam String username,
			@RequestParam String sitecode)throws Exception {
		return instrumentService.uploadsheetimages(file, originurl, username, sitecode);
	}

	@GetMapping("downloadsheetimages/{fileid}/{tenant}/{filename}/{extension}")
	public ResponseEntity<InputStreamResource> downloadsheetimages(@PathVariable String fileid,
			@PathVariable String tenant, @PathVariable String filename, @PathVariable String extension)
			throws IllegalStateException, IOException {

//		ByteArrayInputStream bis = instrumentService.downloadsheetimages(fileid, tenant);
//
//		HttpHeaders header = new HttpHeaders();
//		header.setContentType(MediaType.parseMediaType("image/png"));
//		header.set("Content-Disposition", "attachment; filename=" + filename + "." + extension);
//
//		return new ResponseEntity<>(new InputStreamResource(bis), header, HttpStatus.OK);
		try {
	        ByteArrayInputStream bis = instrumentService.downloadsheetimages(fileid, tenant);
	        
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.IMAGE_PNG);
	        // Use traditional string-based Content-Disposition
	        headers.set(HttpHeaders.CONTENT_DISPOSITION, 
	                   "attachment; filename=\"" + filename + "." + extension + "\"");
	        
	        return ResponseEntity.ok()
	                .headers(headers)
	                .body(new InputStreamResource(bis));
	                
	    } catch (ClientAbortException e) {
	        return null; // Connection already closed
	    } catch (IOException e) {
	        return null;
	    }

	}

	@GetMapping("downloadsheetimagestemp/{fileid}/{tenant}/{filename}/{extension}")
	public ResponseEntity<InputStreamResource> downloadsheetimagestemp(@PathVariable String fileid,
			@PathVariable String tenant, @PathVariable String filename, @PathVariable String extension)
			throws IllegalStateException, IOException {

		ByteArrayInputStream bis = instrumentService.downloadsheetimagestemp(fileid, tenant);

		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.parseMediaType("image/png"));
		header.set("Content-Disposition", "attachment; filename=" + filename + "." + extension);

		return new ResponseEntity<>(new InputStreamResource(bis), header, HttpStatus.OK);

	}

	@PostMapping("/removesheetimage")
	public Response removesheetimage(@RequestBody Map<String, String> body)throws Exception {
		return instrumentService.removesheetimage(body);
	}

	@PostMapping("/updatesheetimagesforversion")
	public boolean updatesheetimagesforversion(@RequestBody List<Map<String, String>> lstfiles)throws Exception {
		return instrumentService.updatesheetimagesforversion(lstfiles);
	}

	@PostMapping("/deletesheetimagesforversion")
	public boolean deletesheetimagesforversion(@RequestBody List<Map<String, String>> lstfiles)throws Exception {
		return instrumentService.deletesheetimagesforversion(lstfiles);
	}

	@PostMapping("/uploadsheetimagesSql")
	public Map<String, Object> uploadsheetimagesSql(@RequestParam MultipartFile file,
			@RequestParam String originurl, @RequestParam String username,
			@RequestParam String sitecode) throws IOException {
		return instrumentService.uploadsheetimagesSql(file, originurl, username, sitecode);
	}

	@GetMapping("downloadsheetimagessql/{fileid}/{filename}/{extension}")
	public ResponseEntity<InputStreamResource> downloadsheetimagessql(@PathVariable String fileid,
			@PathVariable String filename, @PathVariable String extension) throws IllegalStateException, IOException {

		Fileimages objprofile = instrumentService.downloadsheetimagessql(fileid);

		byte[] data = null;

		if (objprofile != null) {
			data = objprofile.getFile().getData();
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.parseMediaType("image/png"));
			header.set("Content-Disposition", "attachment; filename=" + filename + "." + extension);

			return new ResponseEntity<>(new InputStreamResource(bis), header, HttpStatus.OK);
		} else {

			return null;
		}

	}

	@GetMapping("downloadsheetimagestempsql/{fileid}/{filename}/{extension}")
	public ResponseEntity<InputStreamResource> downloadsheetimagestempsql(@PathVariable String fileid,
			@PathVariable String filename, @PathVariable String extension) throws IllegalStateException, IOException {

		Fileimagestemp objprofile = instrumentService.downloadsheetimagestempsql(fileid);

		byte[] data = null;

		if (objprofile != null) {
			data = objprofile.getFile().getData();
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.parseMediaType("image/png"));
			header.set("Content-Disposition", "attachment; filename=" + filename + "." + extension);

			return new ResponseEntity<>(new InputStreamResource(bis), header, HttpStatus.OK);
		} else {

			return null;
		}

	}

	@PostMapping("/updatesheetimagesforversionSql")
	public boolean updatesheetimagesforversionSql(@RequestBody List<Map<String, String>> lstfiles) throws IOException {
		return instrumentService.updatesheetimagesforversionSql(lstfiles);
	}

	@PostMapping("/deletesheetimagesforversionSql")
	public boolean deletesheetimagesforversionSql(@RequestBody List<Map<String, String>> lstfiles)throws Exception {
		return instrumentService.deletesheetimagesforversionSql(lstfiles);
	}
	
	@PostMapping("/UploadLimsFile")
	public Map<String, Object> UploadLimsFile(@RequestParam MultipartFile file,
			@RequestParam("order") Long batchcode, @RequestParam String filename) throws IOException {
		return instrumentService.UploadLimsFile(file, batchcode, filename);
	}
	
	@PostMapping("/downloadSheetFromELN")
	public LsSheetorderlimsrefrence downloadSheetFromELN(@RequestBody LsSheetorderlimsrefrence objattachments)
			throws IllegalStateException, IOException {
		return instrumentService.downloadSheetFromELN(objattachments);
	}
	
	@PostMapping("/GetLimsorderid")
	public Map<String, Object> GetLimsorderid(@RequestBody String orderid)throws Exception {
		return instrumentService.GetLimsorderid(orderid);
	}
	
	@PostMapping("/GetorderforlinkLIMS")
	public Map<String, Object> GetorderforlinkLIMS(@RequestBody LSlogilablimsorderdetail objorder)throws Exception {
		return instrumentService.GetorderforlinkLIMS(objorder);
	}
	
	@PostMapping("/UploadLimsResultFile")
	public Map<String, Object> UploadLimsResultFile(@RequestParam MultipartFile file,
			@RequestParam("order") Long batchcode, @RequestParam String filename) throws IOException {
		return instrumentService.UploadLimsResultFile(file, batchcode, filename);
	}
	
	@PostMapping("/Insertdirectory")
	public LSSheetOrderStructure Insertdirectory(@RequestBody LSSheetOrderStructure objdir)throws Exception {
		return instrumentService.Insertdirectory(objdir);
	}
	
	@PostMapping("/Insertnewdirectory")
	public LSSheetOrderStructure Insertnewdirectory(@RequestBody LSSheetOrderStructure objdir)throws Exception {
		return instrumentService.Insertnewdirectory(objdir);
	}

	@PostMapping("/Getfoldersfororders")
	public Map<String, Object> Getfoldersfororders(@RequestBody LSlogilablimsorderdetail objorder)throws Exception {
		return instrumentService.Getfoldersfororders(objorder);
	}
	
	@PostMapping("/UpdateFolderfororder")
	public LSlogilablimsorderdetail UpdateFolderfororder(@RequestBody LSlogilablimsorderdetail order)throws Exception
	{
		return instrumentService.UpdateFolderfororder(order);
	}
	
	@PostMapping("/Getordersondirectory")
	public List<Logilaborders> Getordersondirectory(@RequestBody LSSheetOrderStructure objdir)throws Exception
	{
		return instrumentService.Getordersondirectory(objdir);
	}
	
	@PostMapping("/UpdateFolderfororders")
	public List<LSlogilablimsorderdetail> UpdateFolderfororders(@RequestBody LSlogilablimsorderdetail[] order)throws Exception
	{
		return instrumentService.UpdateFolderfororders(order);
	}
	
	@PostMapping("/Deletedirectories")
	public List<LSSheetOrderStructure> Deletedirectories(@RequestBody LSSheetOrderStructure[] directories)throws Exception
	{
		return instrumentService.Deletedirectories(directories);
	}
	
	@PostMapping("/Deletemultidirectories")
	public List<LSSheetOrderStructure> Deletemultidirectories(@RequestBody LSSheetOrderStructure[] directories)throws Exception
	{
		return instrumentService.Deletemultidirectories(directories);
	}
	
	@PostMapping("/getMoveDirectory")
	public LSSheetOrderStructure getMoveDirectory(@RequestBody LSSheetOrderStructure objdir)throws Exception {
		return instrumentService.getMoveDirectory(objdir);
	}
	
	@PostMapping("/Movedirectory")
	public LSSheetOrderStructure Movedirectory(@RequestBody LSSheetOrderStructure directory)throws Exception
	{
		return instrumentService.Movedirectory(directory);
	}
	
	@PostMapping("/getlsorderfileversion")
	public List<LSsamplefileversion> getlsorderfileversion(@RequestBody LSsamplefile objfile)throws Exception {
		return instrumentService.getlsorderfileversion(objfile);
	}
	
	@PostMapping("/GetAssignedtoUserorders")
	public List<LSlogilablimsorderdetail> GetAssignedtoUserorders(@RequestBody LSlogilablimsorderdetail order)throws Exception
	{
		return instrumentService.GetAssignedtoUserorders(order);
	}
	
	@PostMapping("/getLockedOrders")
	private List<LogilabOrdermastersh> GetLockedOrders(@RequestBody LSlogilablimsorderdetail objorder) {
		return instrumentService.GetLockedOrders(objorder);
	}
	
	@PostMapping("/unLockedOrders")
	private List<LSlogilablimsorderdetail> UnLockOrders(@RequestBody LSlogilablimsorderdetail[] lstOrder) {
		return instrumentService.UnLockOrders(lstOrder);
	}
	
	@PostMapping(value = "/GetSheetorderversions")
	public Map<String, Object> GetSheetorderversions(@RequestBody Map<String, Object> objMap) throws Exception {

		return instrumentService.GetSheetorderversions(objMap);
	}
	@PostMapping("/Getfoldersforprotocolorders")
	public Map<String, Object> Getfoldersforprotocolorders(@RequestBody LSlogilabprotocoldetail objusermaster)throws Exception {
		return instrumentService.Getfoldersforprotocolorders(objusermaster);
	}
	
	@PostMapping("/Insertdirectoryonprotocol")
	public Lsprotocolorderstructure Insertdirectoryonprotocol(@RequestBody Lsprotocolorderstructure objdir)throws Exception {
		return instrumentService.Insertdirectoryonprotocol(objdir);
	}
	
	@PostMapping("/Insertnewdirectoryonprotocol")
	public Lsprotocolorderstructure Insertnewdirectoryonprotocol(@RequestBody Lsprotocolorderstructure objdir)throws Exception {
		return instrumentService.Insertnewdirectoryonprotocol(objdir);
	}
	
	@PostMapping("/Deletedirectoriesonprotocol")
	public List<Lsprotocolorderstructure> Deletedirectoriesonprotocol(@RequestBody Lsprotocolorderstructure[] directories)throws Exception
	{
		return instrumentService.Deletedirectoriesonprotocol(directories);
	}
	
	@PostMapping("/Movedirectoryonprotocolorder")
	public Lsprotocolorderstructure Movedirectoryonprotocolorder(@RequestBody Lsprotocolorderstructure directory)throws Exception
	{
		return instrumentService.Movedirectoryonprotocolorder(directory);
	}
	
	@PostMapping("/getMoveDirectoryonprotocolorder")
	public Lsprotocolorderstructure getMoveDirectoryonprotocolorder(@RequestBody Lsprotocolorderstructure objdir)throws Exception {
		return instrumentService.getMoveDirectoryonprotocolorder(objdir);
	}
	
	@PostMapping("/UpdateFolderforprotocolorders")
	public List<LSlogilabprotocoldetail> UpdateFolderforprotocolorders(@RequestBody LSlogilabprotocoldetail[] order)throws Exception
	{
		return instrumentService.UpdateFolderforprotocolorders(order);
	}

	@PostMapping("/Getprotocolordersondirectory")
	public Map<String,Object> Getprotocolordersondirectory(@RequestBody Lsprotocolorderstructure objdir)throws Exception
	{
		return instrumentService.Getprotocolordersondirectory(objdir);
	}
	
	@PostMapping("/Getuserprotocolorders")
	public Map<String, Object> Getuserprotocolorders(@RequestBody Map<String, Object> objusers)throws Exception
	{
		return instrumentService.Getuserprotocolorders(objusers);
	}
	
	@PostMapping("/Getuserorders")
	public Map<String, Object> Getuserorders(@RequestBody Map<String, LSuserMaster> objusers)throws Exception {
		return instrumentService.Getuserorders(objusers);
	}
	
	@PostMapping("/UpdatesingleFolderforprotocolorder")
	public LSlogilabprotocoldetail UpdatesingleFolderforprotocolorder(@RequestBody LSlogilabprotocoldetail order)throws Exception
	{
		return instrumentService.UpdatesingleFolderforprotocolorder(order);
	}
	
	@PostMapping("/Getordersonproject")
	public List<Logilaborders> Getordersonproject(@RequestBody LSlogilablimsorderdetail objorder)throws Exception
	{
		return instrumentService.Getordersonproject(objorder);
	}
	
	@PostMapping("/Getordersonsample")
	public List<Logilaborders> Getordersonsample(@RequestBody LSlogilablimsorderdetail objorder)throws Exception
	{
		return instrumentService.Getordersonsample(objorder);
	}
	
	@PostMapping("/Getprotocolordersonproject")
	public Map<String,Object> Getprotocolordersonproject(@RequestBody LSlogilabprotocoldetail objorder)throws Exception
	{
		return instrumentService.Getprotocolordersonproject(objorder);
	}
	
	@PostMapping("/Getorderbyflaganduser")
	public ResponseEntity<Map<String, Object>> Getorderbyflaganduser(@RequestBody LSlogilablimsorderdetail objorder)throws Exception
	{
		try {
			Map<String, Object> returnMap = instrumentService.Getorderbyflaganduser(objorder);
			return new ResponseEntity<>(returnMap,HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping("/Getprotocolordersonsample")
	public Map<String,Object> Getprotocolordersonsample(@RequestBody LSlogilabprotocoldetail objorder)throws Exception
	{
		return instrumentService.Getprotocolordersonsample(objorder);
	}
	
	@PostMapping("/GetordersondirectoryFilter")
	public List<Logilaborders> GetordersondirectoryFilter(@RequestBody LSSheetOrderStructure objdir)throws Exception
	{
		List<Logilaborders> lssheet = new ArrayList<Logilaborders>();
		
		return instrumentService.GetordersondirectoryFilter(objdir,lssheet);
	}
	
	
	@PostMapping("/Getprotocolorderbyflaganduser")
	public Map<String,Object> Getprotocolorderbyflaganduser(@RequestBody LSlogilabprotocoldetail objorder)throws Exception
	{
		return instrumentService.Getprotocolorderbyflaganduser(objorder);
	}
	
	@PostMapping("/uploadfilessheetfolder/{directorycode}/{filefor}/{tenantid}/{ismultitenant}/{usercode}/{sitecode}/{createddate}/{fileviewfor}")
	public Map<String,Object> uploadfilessheetfolder(@RequestParam MultipartFile files,@RequestParam String uid
			,@PathVariable Long directorycode, @PathVariable String filefor, @PathVariable String tenantid
			,@PathVariable Integer ismultitenant, @PathVariable Integer usercode, @PathVariable Integer sitecode
			, @PathVariable Date createddate, @PathVariable Integer fileviewfor
			)throws Exception {
		return instrumentService.uploadfilessheetfolder(files,uid,directorycode,filefor,tenantid,
				ismultitenant,usercode,sitecode,createddate,fileviewfor);
	}
	
	@PostMapping("/Getfilesforfolder")
	public List<LSsheetfolderfiles> Getfilesforfolder(@RequestBody LSsheetfolderfiles objfiles)throws Exception
	{
		return instrumentService.Getfilesforfolder(objfiles);
	}
	
	@PostMapping("/removefilessheetfolder/{directorycode}/{filefor}/{tenantid}/{ismultitenant}/{usercode}/{sitecode}/{createddate}/{fileviewfor}")
	public Map<String, Object> removefilessheetfolder(@RequestParam String uid
			,@PathVariable Long directorycode, @PathVariable String filefor, @PathVariable String tenantid
			,@PathVariable Integer ismultitenant, @PathVariable Integer usercode, @PathVariable Integer sitecode
			, @PathVariable Date createddate, @PathVariable Integer fileviewfor
			)throws Exception {
		return instrumentService.removefilessheetfolder(uid,directorycode,filefor,tenantid,
				ismultitenant,usercode,sitecode,createddate,fileviewfor);
	}
	
	@PostMapping("/removefilesprotocolfolder/{directorycode}/{filefor}/{tenantid}/{ismultitenant}/{usercode}/{sitecode}/{createddate}/{fileviewfor}")
	public Map<String, Object> removefilesprotocolfolder(@RequestParam String uid
			,@PathVariable Long directorycode, @PathVariable String filefor, @PathVariable String tenantid
			,@PathVariable Integer ismultitenant, @PathVariable Integer usercode, @PathVariable Integer sitecode
			, @PathVariable Date createddate, @PathVariable Integer fileviewfor
			)throws Exception {
		return instrumentService.deleteprotocolfilesforfolder(uid,directorycode,filefor,tenantid,
				ismultitenant,usercode,sitecode,createddate,fileviewfor);
	}
	
	@PostMapping("/validatefileexistonfolder")
	public Response validatefileexistonfolder(@RequestBody LSsheetfolderfiles objfile)throws Exception
	{
		return instrumentService.validatefileexistonfolder(objfile);
	}
	
	@GetMapping("/downloadsheetfileforfolder/{multitenant}/{tenant}/{fileid}")
	public ResponseEntity<Resource> downloadsheetfileforfolder(@PathVariable Integer multitenant,
			@PathVariable String tenant, @PathVariable String fileid) throws IOException {

		return instrumentService.downloadsheetfileforfolder(multitenant,tenant,fileid);
	}
	
	@PostMapping("/downloadsheetfilefordocx")
	public Map<String, Object> downloadsheetfilefordocx(@RequestBody Map<String, Object> obj) throws Exception {
		int multitenant = (int) obj.get("multitenant");
		String tenant = (String) obj.get("tenant");
		String fileid = (String) obj.get("fileid");
		String screenname = (String) obj.get("screenname");
		String ontabkey = (String) obj.get("ontabkey");
		return instrumentService.downloadsheetfilefordocx(multitenant,tenant,fileid,screenname,ontabkey);
	}
	
	@PostMapping("/deletefilesforfolder/{directorycode}/{filefor}/{tenantid}/{ismultitenant}/{usercode}/{sitecode}/{createddate}/{fileviewfor}")
	public Map<String, Object> deletefilesforfolder(@RequestBody LSsheetfolderfiles objfiles,@PathVariable Long directorycode, @PathVariable String filefor, @PathVariable String tenantid
			,@PathVariable Integer ismultitenant, @PathVariable Integer usercode, @PathVariable Integer sitecode
			, @PathVariable Date createddate, @PathVariable Integer fileviewfor)throws Exception
	{
		return instrumentService.removefilessheetfolder(objfiles.getUuid(),directorycode,filefor,tenantid,
				ismultitenant,usercode,sitecode,createddate,fileviewfor);
	}
	
	@PostMapping("/deletemultifilesforfolder/{directorycode}/{filefor}/{tenantid}/{ismultitenant}/{usercode}/{sitecode}/{createddate}/{fileviewfor}")
	public Map<String, Object> deletemultifilesforfolder(@RequestBody LSsheetfolderfiles[] objfiles,@PathVariable Long directorycode, @PathVariable String filefor, @PathVariable String tenantid
			,@PathVariable Integer ismultitenant, @PathVariable Integer usercode, @PathVariable Integer sitecode
			, @PathVariable Date createddate, @PathVariable Integer fileviewfor)throws Exception
	{
		return instrumentService.removemultifilessheetfolder(objfiles,directorycode,filefor,tenantid,
				ismultitenant,usercode,sitecode,createddate,fileviewfor);
	}
	
	@PostMapping("/deletemultifilesforfolderonprotocol/{directorycode}/{filefor}/{tenantid}/{ismultitenant}/{usercode}/{sitecode}/{createddate}/{fileviewfor}")
	public Map<String, Object> deletemultifilesforfolderonprotocol(@RequestBody LSprotocolfolderfiles[] objfiles,@PathVariable Long directorycode, @PathVariable String filefor, @PathVariable String tenantid
			,@PathVariable Integer ismultitenant, @PathVariable Integer usercode, @PathVariable Integer sitecode
			, @PathVariable Date createddate, @PathVariable Integer fileviewfor)throws Exception
	{
		return instrumentService.removemultifilessheetfolderonprotocol(objfiles,directorycode,filefor,tenantid,
				ismultitenant,usercode,sitecode,createddate,fileviewfor);
	}
	
	@PostMapping("/Getaddedfilesforfolder")
	public List<LSsheetfolderfiles> Getaddedfilesforfolder(@RequestBody List<String> lstuuid)throws Exception
	
	{
		return instrumentService.Getaddedfilesforfolder(lstuuid);
	}
	
	@PostMapping("/UpdateFolderforfiles")
	public List<LSsheetfolderfiles> UpdateFolderforfiles(@RequestBody LSsheetfolderfiles[] files)throws Exception
	{
		return instrumentService.UpdateFolderforfiles(files);
	}
	
	@PostMapping("/UpdateFolderforfile")
	public LSsheetfolderfiles UpdateFolderforfile(@RequestBody LSsheetfolderfiles file)throws Exception
	{
		return instrumentService.UpdateFolderforfile(file);
	}
	
	@PostMapping("/Getordersonassignedandmyorders")
	public List<Logilaborderssh> Getordersonassignedandmyorders(@RequestBody Map<String, Object>  objorder)throws Exception
	{
		return instrumentService.Getordersonassignedandmyorders(objorder);
	}
	
	@PostMapping("/Getusersharedorders")
	public Map<String, Object> Getusersharedorders(@RequestBody Map<String, Object> objusers)throws Exception {
		return instrumentService.Getusersharedorders(objusers);
	}
	
	@PostMapping("/Updateparentforfolder")
	public List<LSSheetOrderStructure> Updateparentforfolder(@RequestBody LSSheetOrderStructure[] folders)throws Exception
	{
		return instrumentService.Updateparentforfolder(folders);
	}
	
	@PostMapping("/validateprotocolexistonfolder")
	public Response validateprotocolexistonfolder(@RequestBody LSprotocolfolderfiles objfile)throws Exception
	{
		return instrumentService.validateprotocolexistonfolder(objfile);
	}
	
	@PostMapping("/uploadfilesprotocolfolder/{directorycode}/{filefor}/{tenantid}/{ismultitenant}/{usercode}/{sitecode}/{createddate}/{fileviewfor}")
	public Map<String,Object> uploadfilesprotocolfolder(@RequestParam MultipartFile files,@RequestParam String uid
			,@PathVariable Long directorycode, @PathVariable String filefor, @PathVariable String tenantid
			,@PathVariable Integer ismultitenant, @PathVariable Integer usercode, @PathVariable Integer sitecode
			, @PathVariable Date createddate, @PathVariable Integer fileviewfor
			)throws Exception {
		return instrumentService.uploadfilesprotocolfolder(files,uid,directorycode,filefor,tenantid,
				ismultitenant,usercode,sitecode,createddate,fileviewfor);
	}
	
	@PostMapping("/Getfilesforprotocolfolder")
	public List<LSprotocolfolderfiles> Getfilesforprotocolfolder(@RequestBody LSprotocolfolderfiles objfiles)throws Exception
	{
		return instrumentService.Getfilesforprotocolfolder(objfiles);
	}
	
	@GetMapping("/downloadprotocolfileforfolder/{multitenant}/{tenant}/{fileid}")
	public ResponseEntity<InputStreamResource> downloadprotocolfileforfolder(@PathVariable Integer multitenant,
			@PathVariable String tenant, @PathVariable String fileid) throws IOException {

		return instrumentService.downloadprotocolfileforfolder(multitenant,tenant,fileid);
	}
	
	@PostMapping("/deleteprotocolfilesforfolder/{directorycode}/{filefor}/{tenantid}/{ismultitenant}/{usercode}/{sitecode}/{createddate}/{fileviewfor}")
	public Map<String, Object> deleteprotocolfilesforfolder(@RequestBody LSsheetfolderfiles objfiles,@PathVariable Long directorycode, @PathVariable String filefor, @PathVariable String tenantid
			,@PathVariable Integer ismultitenant, @PathVariable Integer usercode, @PathVariable Integer sitecode
			, @PathVariable Date createddate, @PathVariable Integer fileviewfor)throws Exception
	{
		return instrumentService.deleteprotocolfilesforfolder(objfiles.getUuid(),directorycode,filefor,tenantid,
				ismultitenant,usercode,sitecode,createddate,fileviewfor);
	}
	
	@PostMapping("/UpdateprotocolFolderforfiles")
	public List<LSprotocolfolderfiles> UpdateprotocolFolderforfiles(@RequestBody LSprotocolfolderfiles[] files)throws Exception
	{
		return instrumentService.UpdateprotocolFolderforfiles(files);
	}
	
	@PostMapping("/Updateprotocolparentforfolder")
	public List<Lsprotocolorderstructure> Updateprotocolparentforfolder(@RequestBody Lsprotocolorderstructure[] folders)throws Exception
	{
		return instrumentService.Updateprotocolparentforfolder(folders);
	}
	
	@PostMapping("/UpdateprotocolFolderforfile")
	public LSprotocolfolderfiles UpdateprotocolFolderforfile(@RequestBody LSprotocolfolderfiles file)throws Exception
	{
		return instrumentService.UpdateprotocolFolderforfile(file);
	}
	
	@PostMapping("/Getaddedprotocolfilesforfolder")
	public List<LSprotocolfolderfiles> Getaddedprotocolfilesforfolder(@RequestBody List<String> lstuuid)throws Exception
	{
		return instrumentService.Getaddedprotocolfilesforfolder(lstuuid);
	}
	
	@PostMapping("/cancelsheetorder")
	public LSlogilablimsorderdetail cancelsheetorder(@RequestBody LSlogilablimsorderdetail body)throws Exception
	{
		return instrumentService.cancelprotocolorder(body);

	}
	@PostMapping("/Consumableinventoryotification")
	public Map<String, Object> Consumableinventoryotification(@RequestBody Map<String, Object> argobj)throws Exception {
		return instrumentService.Consumableinventoryotification(argobj);

	}
	@PostMapping("/Outofstockinventoryotification")
	public Map<String, Object> Outofstockinventoryotification(@RequestBody Map<String, Object> argobj)throws Exception {
		return instrumentService.Outofstockinventoryotification(argobj);

	}
	@PostMapping("/Getfoldersfordashboard")
	public Map<String, Object> Getfoldersfordashboard(@RequestBody LSuserMaster objusermaster)throws Exception {
		return instrumentService.Getfoldersfordashboard(objusermaster);
	}
	
	@PostMapping("/onDeleteforCancel/{screen}")
	public void onDeleteforCancel(@RequestBody List<String> lstuuid, @PathVariable String screen)throws Exception	
	{
	 instrumentService.onDeleteforCancel(lstuuid,screen);
	}
	
	@PostMapping("/getimagesforlink")
	public Map<String,Object> getimagesforlink(@RequestBody Map<String,Object> site)throws Exception
	{
		return instrumentService.getimagesforlink(site);
	}
	
	@PostMapping("/Getordersonmaterial")
	public List<Logilaborders> Getordersonmaterial(@RequestBody LSlogilablimsorderdetail objorder)throws Exception
	{
		return instrumentService.Getordersonmaterial(objorder);
	}
	
	@PostMapping("/Getprotocolordersonmaterial")
	public Map<String,Object> Getprotocolordersonmaterial(@RequestBody LSlogilabprotocoldetail objorder)throws Exception
	{
		return instrumentService.Getprotocolordersonmaterial(objorder);
	}
	
	@PostMapping("/Getcancelledordes")
	public List<Logilaborderssh> Getcancelledordes(@RequestBody LSlogilablimsorderdetail objdir)throws Exception
	{
		return instrumentService.Getcancelledordes(objdir);
	}
	
	@PostMapping("/sendapprovel")
	public LSlogilablimsorderdetail sendapprovel(@RequestBody LSlogilablimsorderdetail objdir)throws Exception
	{
		return instrumentService.sendapprovel(objdir);
	}
	@PostMapping("/acceptapprovel")
	public LSlogilablimsorderdetail acceptapprovel(@RequestBody LSlogilablimsorderdetail objdir)throws Exception
	{
		return instrumentService.acceptapprovel(objdir);
	}

	@PostMapping("/stopautoregister")
	public LSlogilablimsorderdetail stopregister(@RequestBody LSlogilablimsorderdetail objdir)
			throws Exception {

		return instrumentService.stopautoregister(objdir);
	}
	
	@PostMapping("/getsingleorder")
	public List<LSlogilablimsorderdetail> getsingleorder(@RequestBody LSlogilablimsorderdetail body)throws Exception
	{
		return instrumentService.getsingleorder(body);

	}
	
	@PostMapping("/Getsingleorder")
	public LSlogilablimsorderdetail Getsingleorder(@RequestBody LSlogilablimsorderdetail objorder)throws Exception {
		return instrumentService.Getsingleorder(objorder);
	}
		
	@PostMapping("/Suggesionforfolder")
	public List<ProjectOrTaskOrMaterialView>  Suggesionforfolder(@RequestBody Map<String,Object> searchobj){
		return instrumentService.Suggesionforfolder(searchobj);
		
	}
	
	@PostMapping("/Getordersonfiles")
	public Map<String, Object> Getordersonfiles(@RequestBody Map<String,Object> mapObj)throws Exception
	{
		return instrumentService.Getordersonfiles(mapObj);
	}
	
	@PostMapping("/GetOrdersbyuseronDetailview")
	public List<LSlogilablimsorderdetail> GetOrdersbyuseronDetailview(@RequestBody LSlogilablimsorderdetail objorder)throws Exception{
		return instrumentService.GetOrdersbyuseronDetailview(objorder);
	}
	
	@PostMapping("/Getorderbyflaganduseronstart")
	public ResponseEntity<Map<String, Object>> Getorderbyflaganduseronstart(@RequestBody LSlogilablimsorderdetail objorder)throws Exception
	{
		try {
			Map<String, Object> returnMap = instrumentService.Getorderbyflaganduseronstart(objorder);
			return new ResponseEntity<>(returnMap,HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/insertOrderLink")
	public ResponseEntity<Object> insertOrderLink(@RequestBody LsOrderLinks objorder) throws Exception {
		return instrumentService.insertOrderLink(objorder);
	}

	@PostMapping("/getLinksOnOrder")
	public ResponseEntity<Object> getLinksOnOrder(@RequestBody LsOrderLinks objorder) throws Exception {
		return instrumentService.getLinksOnOrder(objorder);
	}
	
	@PostMapping("/deleteLinkforOrder")
	public ResponseEntity<Object> deleteLinkforOrder(@RequestBody LsOrderLinks objorder) throws Exception {
		return instrumentService.deleteLinkforOrder(objorder);
	}
	
	@PostMapping("/Getselectedteam")
	public List<LSSelectedTeam> Getselectedteam(@RequestBody Map<String, Object> mapObject)throws Exception
	{
		final ObjectMapper mapper = new ObjectMapper();
		final Long batchcode = mapper.convertValue(mapObject.get("batchcode"), Long.class);

		return instrumentService.Getselectedteam(batchcode);
	}
	
	@PostMapping("/Getprotoselectedteam")
	public List<LSprotocolselectedteam> Getprotoselectedteam(@RequestBody Map<String, Object> mapObject)throws Exception
	{
		final ObjectMapper mapper = new ObjectMapper();
		final Long protocolordercode = mapper.convertValue(mapObject.get("protocolordercode"), Long.class);

		return instrumentService.Getprotoselectedteam(protocolordercode);
	}
	
	@PostMapping("/GetAllorders")
	public ResponseEntity<Object> GetAllorders(@RequestBody LSuserMaster objuser)
	{
		return instrumentService.GetAllorders(objuser);
	}

	@GetMapping("/GetSheettagdataonOrdercode/{batchcode}")
	public List<Map<String, Object>> GetSheettagdataonOrdercode(@PathVariable Long batchcode) throws JsonMappingException, JsonProcessingException
	{
		return instrumentService.GetSheettagdataonOrdercode(batchcode);
	}
	
	@GetMapping("/GetSheettagdataonSequenceid/{batchcode}")
	public List<Map<String, Object>> GetSheettagdataonSequenceid(@PathVariable String batchcode) throws JsonMappingException, JsonProcessingException
	{
		return instrumentService.GetSheettagdataonSequenceid(batchcode);
	}
	
	@PostMapping("/GetorderlockStatus")
	public LogilabOrderDetails GetorderlockStatus(@RequestBody LSlogilablimsorderdetail objorder)throws Exception {
		return instrumentService.GetorderlockStatus(objorder);
	}
	
	@PostMapping("/getordercontent")
	public LSlogilablimsorderdetail getordercontent(@RequestBody LSlogilablimsorderdetail order) throws Exception {	
		return instrumentService.getordercontent(order);
	}
	
	@PostMapping("/GetRejectedorderbyflaganduser")
	public ResponseEntity<Map<String, Object>> GetRejectedorderbyflaganduser(@RequestBody LSlogilablimsorderdetail objorder)throws Exception
	{
		try {
			Map<String, Object> returnMap = instrumentService.GetRejectedorderbyflaganduser(objorder);
			return new ResponseEntity<>(returnMap,HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("downloadsheetimagesforbase64/{fileid}/{tenant}/{filename}/{extension}")
	public ResponseEntity<String> downloadsheetimagesforbase64(@PathVariable String fileid,
			@PathVariable String tenant, @PathVariable String filename, @PathVariable String extension)
			throws IllegalStateException, IOException {

//		ByteArrayInputStream bis = instrumentService.downloadsheetimages(fileid, tenant);
//
//		HttpHeaders header = new HttpHeaders();
//		header.setContentType(MediaType.parseMediaType("image/png"));
//		header.set("Content-Disposition", "attachment; filename=" + filename + "." + extension);
//
//		return new ResponseEntity<>(new InputStreamResource(bis), header, HttpStatus.OK);
		try {
	        ByteArrayInputStream bis = instrumentService.downloadsheetimages(fileid, tenant);
	        
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.IMAGE_PNG);
	        // Use traditional string-based Content-Disposition
	        headers.set(HttpHeaders.CONTENT_DISPOSITION, 
	                   "attachment; filename=\"" + filename + "." + extension + "\"");
	        
//	        return ResponseEntity.ok()
//	                .headers(headers)
//	                .body(new InputStreamResource(bis));
	        
	        byte[] bytes = bis.readAllBytes();
	        String base64 = Base64.getEncoder().encodeToString(bytes);

	     // Return as plain text or JSON
	     return ResponseEntity.ok()
	             .contentType(MediaType.TEXT_PLAIN)
	             .body(base64);
	                
	    } catch (ClientAbortException e) {
	        return null; // Connection already closed
	    } catch (IOException e) {
	        return null;
	    }

	}
	
}
