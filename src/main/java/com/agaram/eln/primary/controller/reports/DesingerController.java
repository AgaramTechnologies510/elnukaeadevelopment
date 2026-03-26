package com.agaram.eln.primary.controller.reports;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.agaram.eln.primary.model.general.Response;
import com.agaram.eln.primary.model.reports.reportdesigner.ReportDesignerStructure;
import com.agaram.eln.primary.model.reports.reportdesigner.ReportTemplateVersion;
import com.agaram.eln.primary.model.reports.reportdesigner.Reporttemplate;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;
import com.agaram.eln.primary.service.reports.DesingerService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/designereports", method = RequestMethod.POST)
public class DesingerController {

	@Autowired
	private DesingerService desingerservice;
	
	@PostMapping("/getreportsource")
	protected Map<String, Object> getreportsource(@RequestBody Map<String, Object> argObj) throws ServletException, IOException {
		return desingerservice.getreportsource(argObj);
	}
	
	@PostMapping("/savereporttemplate")
	protected Reporttemplate savereporttemplate(@RequestBody Reporttemplate template) throws ServletException, IOException {
		return desingerservice.savereporttemplate(template);
	}
	
	@PostMapping("/gettemplatedata")
	protected Reporttemplate gettemplatedata(@RequestBody Reporttemplate template) throws ServletException, IOException {
		return desingerservice.gettemplatedata(template);
	}
	
	@PostMapping("/getfolders")
	public Map<String, Object> getfolders(@RequestBody ReportDesignerStructure objdir) throws ServletException, IOException {
		return desingerservice.getfolders(objdir);
	}
	
	@PostMapping("/insertdirectory")
	public ReportDesignerStructure insertdirectory(@RequestBody ReportDesignerStructure objdir)throws Exception {
		return desingerservice.insertdirectory(objdir);
	}
	
	@PostMapping("/insertnewdirectory")
	public ReportDesignerStructure insertnewdirectory(@RequestBody ReportDesignerStructure objdir)throws Exception {
		return desingerservice.insertnewdirectory(objdir);
	}
	
	@PostMapping("/gettemplateonfolder")
	public List<Reporttemplate> gettemplateonfolder(@RequestBody ReportDesignerStructure objdir)throws Exception {
		return desingerservice.gettemplateonfolder(objdir);
	}
	
	@PostMapping("/getordersonreport")
	public Map<String,Object> getordersonreport (@RequestBody Map<String, Object> objMap){
		return desingerservice.getordersonreport(objMap);
	}
	
	@PostMapping("/approvereporttemplate")
	public Reporttemplate approvereporttemplate(@RequestBody Reporttemplate objdir)throws Exception {
		System.out.println(objdir);
		return desingerservice.approvereporttemplate(objdir);
	}
	
	@PostMapping("/gettemplateonfoldermapping")
	public List<Reporttemplate> gettemplateonfoldermapping(@RequestBody ReportDesignerStructure objdir)throws Exception {
		return desingerservice.gettemplateonfoldermapping(objdir);
	}
	
	@PostMapping("/updatereporttemplatemapping")
	public Reporttemplate updatereporttemplatemapping(@RequestBody Reporttemplate objdir)throws Exception {
		return desingerservice.updatereporttemplatemapping(objdir);
	}
	
	@PostMapping("/onGetReportTemplateBasedOnProject")
	public Map<String, Object> onGetReportTemplateBasedOnProject(@RequestBody Map<String, Object> objMap)throws Exception {
		return desingerservice.onGetReportTemplateBasedOnProject(objMap);
	}
	
	@PostMapping(value = "/gettemplateversiondata")
	protected ReportTemplateVersion gettemplateversiondata(@RequestBody ReportTemplateVersion template) throws ServletException, IOException {
		return desingerservice.gettemplateversiondata(template);
	}
	
	@PostMapping("/UpdateFolderforReportDesignerStructure")
	public ReportDesignerStructure UpdateFolderforReportDesignerStructure(@RequestBody ReportDesignerStructure folders)throws Exception
	{
		return desingerservice.UpdateFolderforReportDesignerStructure(folders);
	}
	
	@PostMapping("/DeletedirectoriesonReportDesigner")
	public List<ReportDesignerStructure> DeletedirectoriesonReportDesigner(@RequestBody ReportDesignerStructure[] directories)throws Exception
	{
		return desingerservice.DeletedirectoriesonReportDesigner(directories);
	}
	
	@PostMapping("/getMoveDirectoryonReportDesigner")
	public ReportDesignerStructure getMoveDirectoryonReportDesigner(@RequestBody ReportDesignerStructure objdir)throws Exception {
		return desingerservice.getMoveDirectoryonReportDesigner(objdir);
	}
	
	@PostMapping("/UpdateReporttemplate")
	public List<Reporttemplate> UpdateReporttemplate(@RequestBody Reporttemplate[] files)throws Exception
	{
		return desingerservice.UpdateReporttemplate(files);
	}
	
	@PostMapping("/UpdateReporttemplateforsinglefile")
	public Reporttemplate UpdateReporttemplateforsinglefile(@RequestBody Reporttemplate file)throws Exception
	{
		return desingerservice.UpdateReporttemplateforsinglefile(file);
	}
	
	@PostMapping("/MovedirectoryonReporttemplate")
	public ReportDesignerStructure MovedirectoryonReporttemplate(@RequestBody ReportDesignerStructure directory)throws Exception
	{
		return desingerservice.MovedirectoryonReporttemplate(directory);
	}
	
	@PostMapping("/RenameReporttemplate")
	public Reporttemplate RenameReporttemplate(@RequestBody Reporttemplate Template) throws ParseException {
		return desingerservice.RenameReporttemplate(Template);
	}
	
	@PostMapping("/TocheckTemplateexist")
	public Response TocheckTemplateexist(@RequestBody Reporttemplate Template) {
		return desingerservice.TocheckTemplateexist(Template);
	}
	
	@PostMapping("/MoveReportTemplate")
	public List<Reporttemplate> MoveReportTemplate(@RequestBody Reporttemplate[] Template) {
		System.out.println(Template);
		System.out.println(Template);

		return desingerservice.MoveReportTemplate(Template);
	}
	
	@PostMapping("/GetUnlockscreendata")
	public List<Reporttemplate> GetUnlockscreendata(@RequestBody LSuserMaster protocolorders) {
		return desingerservice.GetUnlockscreendata(protocolorders);
	}
	
	@PostMapping("/UnloackReporttemplate")
	public Boolean UnloackReporttemplate(@RequestBody Long[] Reporttemplate) {
		return desingerservice.UnloackReporttemplate(Reporttemplate);
	}
}
