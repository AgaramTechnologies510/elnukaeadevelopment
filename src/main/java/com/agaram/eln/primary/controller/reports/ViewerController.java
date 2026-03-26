package com.agaram.eln.primary.controller.reports;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.agaram.eln.primary.model.instrumentDetails.LSlogilablimsorderdetail;
//import com.agaram.eln.primary.model.reports.reportdesigner.ReportDesignerStructure;
import com.agaram.eln.primary.model.reports.reportviewer.Cloudreports;
import com.agaram.eln.primary.model.reports.reportviewer.ReportViewerStructure;
import com.agaram.eln.primary.model.reports.reportviewer.Reports;
import com.agaram.eln.primary.model.sheetManipulation.LSsamplefile;
import com.agaram.eln.primary.service.reports.ViewerService;

@RestController
@RequestMapping(value = "/viewreports", method = RequestMethod.POST)
public class ViewerController {
	
	@Autowired
	private ViewerService viewerservice;
	
	@PostMapping(value = "/getreportdata")
	protected Map<String, Object> getreportdata(@RequestBody Reports report) throws ServletException, IOException, URISyntaxException {
		return viewerservice.getreportdata(report);
	}
	
	@PostMapping(value = "/getordercontent")
	public String getordercontent(@RequestBody LSsamplefile lssample)throws ServletException, IOException
	{
		return viewerservice.getordercontent(lssample);
	}
	
	@PostMapping(value = "/getremainingorderdata")
	protected Map<String, Object> getremainingorderdata(@RequestBody LSlogilablimsorderdetail objorder) throws ServletException, IOException {
		return viewerservice.getremainingorderdata(objorder);
	}
	
	@PostMapping("/getfolders")
	public Map<String, Object> getfolders(@RequestBody ReportViewerStructure objdir) throws ServletException, IOException {
		return viewerservice.getfolders(objdir);
	}
	
	@PostMapping("/insertdirectory")
	public ReportViewerStructure insertdirectory(@RequestBody ReportViewerStructure objdir)throws Exception {
		return viewerservice.insertdirectory(objdir);
	}
	
	@PostMapping("/insertnewdirectory")
	public ReportViewerStructure insertnewdirectory(@RequestBody ReportViewerStructure objdir)throws Exception {
		return viewerservice.insertnewdirectory(objdir);
	}
	
	@PostMapping(value = "/savereporttemplate")
	protected Reports savereport(@RequestBody Reports report) throws ServletException, IOException {
		return viewerservice.savereport(report);
	}
	
	@PostMapping("/getreportsonfolder")
	public List<Reports> getreportsonfolder(@RequestBody ReportViewerStructure reportstructure)throws Exception {
		return viewerservice.getreportsonfolder(reportstructure);
	}
	
	@PostMapping(value = "/getreportcontent")
	protected Cloudreports getreportcontent(@RequestBody Reports report) throws Exception {
		return viewerservice.getreportcontent(report);
	}
	
	@PostMapping("/deleteContextClick")
	public List<ReportViewerStructure> deleteContextClick(@RequestBody ReportViewerStructure[] directories)throws Exception
	{
		return viewerservice.deleteContextClick(directories);
	}
}
