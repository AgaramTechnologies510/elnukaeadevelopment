package com.agaram.eln.primary.controller.barcode;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.print.PrintException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.agaram.eln.primary.model.PrinterSettings.PrintJob;
import com.agaram.eln.primary.model.PrinterSettings.printerdetails;
import com.agaram.eln.primary.model.barcode.BarcodeMaster;
import com.agaram.eln.primary.model.fileManipulation.ProfilePicture;
import com.agaram.eln.primary.model.instrumentsetup.InstrumentCategory;
import com.agaram.eln.primary.model.sheetManipulation.LSfile;
import com.agaram.eln.primary.model.sheetManipulation.LsfilemapBarcode;
import com.agaram.eln.primary.model.usermanagement.LSnotification;
import com.agaram.eln.primary.model.usermanagement.LSusergrouprights;
import com.agaram.eln.primary.model.usermanagement.LoggedUser;
import com.agaram.eln.primary.service.barcode.BarcodeMasterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/Barcode", method = RequestMethod.POST)
public class BarcodeMasterController {
	@Autowired
	private BarcodeMasterService barcodemasterservice;

	@PostMapping("/InsertBarcode")
	public ResponseEntity<Object> InsertBarcode(MultipartHttpServletRequest request)
			throws JsonMappingException, JsonProcessingException, ParseException {
		return barcodemasterservice.InsertBarcode(request);
	}

	@PostMapping("/GetBarcodemaster")
	public List<BarcodeMaster> GetBarcodemaster(@RequestBody LoggedUser objuser) {
		return barcodemasterservice.GetBarcodemaster(objuser);
	}

	@GetMapping("/Getbarcodefileoncode/{barcode}/{ismultitenant}/{tenant}/{screen}/{primarykey}/{path}/{username}/{density}/{height}/{width}")
	public ResponseEntity<InputStreamResource> Getbarcodefileoncode(@PathVariable String barcode,
			@PathVariable String ismultitenant, @PathVariable String tenant, @PathVariable String screen,
			@PathVariable String primarykey, @PathVariable String path, @PathVariable String username,
			@PathVariable String density, @PathVariable String height, @PathVariable String width)
			throws ParseException, IOException {
		return barcodemasterservice.getbarcodefileoncode(barcode, ismultitenant, tenant, screen, primarykey, path,
				username,density,height,width);
	}

	@PostMapping(value = "/getbarcodeContent")
	public ResponseEntity<String> getbarcodeContent(@RequestBody BarcodeMaster Obj) throws ParseException, IOException {
		return barcodemasterservice.getbarcodeContent(Obj.getBarcodeno(), Obj.getIsmultitenant(),
				Obj.getCommonstring());
	}

//	@PostMapping("/UpdateBarcode")
//	public BarcodeMaster UpdateBarcode(MultipartHttpServletRequest request)
//		throws ParseException, IOException {
//	//	throws JsonMappingException, JsonProcessingException, ParseException {
//		return barcodemasterservice.UpdateBarcode(request);
//	}

	@PostMapping("/RetiredBarcode")
	public BarcodeMaster RetiredBarcode(@RequestBody Map<String, Object> objClass) throws Exception {
		return barcodemasterservice.RetiredBarcode(objClass);
	}

	@PostMapping("/printBarcode")
	public Map<String, Object> printBarcode(@RequestBody Map<String, Object> inputMap)
			throws NumberFormatException, IOException, ParseException, PrintException {
		return barcodemasterservice.printBarcode(inputMap);
	}
	
	@PostMapping("/printBarcodeonMultipleSample")
	public Map<String, Object> printBarcodeonMultipleSample(@RequestBody Map<String, Object> inputMap) 
			throws NumberFormatException, IOException, ParseException, PrintException
	{
		return barcodemasterservice.printBarcodeonMultipleSample(inputMap);
	}

	@PostMapping("/updateBarcodeMaster")
	public ResponseEntity<Object> updateBarcodeMaster(@RequestBody Map<String, Object> inputMap) throws Exception {

		ObjectMapper objMapper = new ObjectMapper();
		BarcodeMaster objBarcode = objMapper.convertValue(inputMap.get("Barcode"), new TypeReference<BarcodeMaster>() {
		});

		return barcodemasterservice.updateBarcodeMaster(objBarcode);
	}

	@PostMapping("/GetBarcodemasterOnScreenbased")
	public List<BarcodeMaster> GetBarcodemasterOnScreenbased(@RequestBody BarcodeMaster objuser) {
		return barcodemasterservice.GetBarcodemasterOnScreenbased(objuser);
	}

	@PostMapping("/getmappedbarcode")
	public List<LsfilemapBarcode> getmappedbarcode(@RequestBody LSfile objuser) {
		return barcodemasterservice.getmappedbarcode(objuser);
	}

	@PostMapping("/onupdateSheetmapbarcode")
	public List<LsfilemapBarcode> onupdateSheetmapbarcode(@RequestBody LsfilemapBarcode[] objOrder) {
		return barcodemasterservice.onupdateSheetmapbarcode(objOrder);
	}

	@PostMapping("/getmappedbarcodeOnsheetorder")
	public Map<String, Object> getmappedbarcodeOnsheetorder(@RequestBody LSfile objuser) {
		return barcodemasterservice.getmappedbarcodeOnsheetorder(objuser);
	}

	@PostMapping(value = "/GetbarcodefilecodeonOrderscreen")
	public ResponseEntity<InputStreamResource> GetbarcodefilecodeonOrderscreen(
	    @RequestBody Map<String, Object> requestBody) throws ParseException, IOException {

	    String barcode =requestBody.get("barcode").toString();
	    String ismultitenant =requestBody.get("ismultitenant").toString();
	    String tenant =  requestBody.get("tenant").toString();
	    String screen = requestBody.get("screen").toString();
	    String username =requestBody.get("username").toString();
	    String density =  requestBody.get("density").toString();
	    String height = requestBody.get("height").toString();
	    String width =requestBody.get("width").toString();
	    @SuppressWarnings("unchecked")
		List<Map<String, Object>> barcodedata = (List<Map<String, Object>>) requestBody.get("barcodedata");

	    return barcodemasterservice.GetbarcodefilecodeonOrderscreen(barcode, ismultitenant, tenant, screen, username, barcodedata,density,height,width);
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/PrintBarcodeorders")
	public Map<String, Object> PrintBarcodeorders(
			@RequestBody Map<String, Object> requestBody) throws ParseException, IOException {
		
		String barcode =requestBody.get("barcode").toString();
		String ismultitenant =requestBody.get("ismultitenant").toString();
		String tenant =  requestBody.get("tenant").toString();
		String screen = requestBody.get("screen").toString();
		String username =requestBody.get("username").toString();
		List<Map<String, Object>> barcodedata = (List<Map<String, Object>>) requestBody.get("barcodedata");
		
		return barcodemasterservice.PrintBarcodeorders(barcode, ismultitenant, tenant, screen, username, barcodedata);
	}
	
	@PostMapping("/insertprints")
	public List<printerdetails> insertprints(@RequestBody printerdetails[]  print){
		List<printerdetails> lsprint = Arrays.asList(print);
		return barcodemasterservice.insertprints(lsprint);
	}
	
	@PostMapping("/insertprintjob")
    public PrintJob insertprintjob(@RequestParam MultipartFile file,
    		@RequestParam Integer usercode, @RequestParam String printer, @RequestParam Integer isMultitenant)throws Exception {
		return barcodemasterservice.insertprintjob(file,usercode, printer,isMultitenant);
	}
	
	@PostMapping("/getprinterdetails")
	public List<printerdetails> getprinterdetails(@RequestBody printerdetails print){
		
		return barcodemasterservice.getPrinterlist();
	}

	@PostMapping("/getprinterJobdetails")
	public List<Map<String, Object>> getPrintJob(@RequestBody Map<String, Object> printjob) throws IOException{
		   
		return barcodemasterservice.getPrinterjoblist(printjob);
	}
	@PostMapping("/insertnotificationonprint")
	public LSnotification insertnotificationonprint(@RequestBody LSnotification  printnotification){
		
		return barcodemasterservice.insertnotificationonprint(printnotification);
	}
}
