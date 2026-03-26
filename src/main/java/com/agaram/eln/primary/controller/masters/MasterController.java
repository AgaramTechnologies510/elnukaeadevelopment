package com.agaram.eln.primary.controller.masters;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.agaram.eln.primary.model.archieve.LsProjectarchieve;
import com.agaram.eln.primary.model.general.Response;
import com.agaram.eln.primary.model.instrumentDetails.LsOrderSampleUpdate;
import com.agaram.eln.primary.model.masters.Lslogbooks;
import com.agaram.eln.primary.model.masters.Lslogbooksdata;
import com.agaram.eln.primary.model.masters.Lsrepositories;
import com.agaram.eln.primary.model.masters.Lsrepositoriesdata;
import com.agaram.eln.primary.model.usermanagement.LSSiteMaster;
import com.agaram.eln.primary.model.usermanagement.LSprojectmaster;
import com.agaram.eln.primary.service.masters.MasterService;

@RestController
@RequestMapping(value = "/Master", method = RequestMethod.POST)
public class MasterController {
	@Autowired
	MasterService masterService;
	
	@PostMapping("/Getallrepositories")
	public List<Lsrepositories> Getallrepositories(@RequestBody Lsrepositories lsrepositories)throws Exception
	{
		return masterService.Getallrepositories(lsrepositories);
	}
	
	@PostMapping("/Getallrepositoriesondashboard")
	public List<Lsrepositories> Getallrepositoriesondashboard(@RequestBody Lsrepositories lsrepositories)throws Exception
	{
		return masterService.Getallrepositoriesondashboard(lsrepositories);
	}
	
	@PostMapping("/Saverepository")
	public Lsrepositories Saverepository(@RequestBody Lsrepositories lsrepositories)throws Exception
	{
		return masterService.Saverepository(lsrepositories);
	}
	
	@PostMapping("/Getallrepositoriesdata")
	public List<Lsrepositoriesdata> Getallrepositoriesdata(@RequestBody Lsrepositoriesdata lsrepositoriesdata)throws Exception
	{
		return masterService.Getallrepositoriesdata(lsrepositoriesdata);
	}
	
	@PostMapping("/Saverepositorydata")
	public Lsrepositoriesdata Saverepositorydata(@RequestBody Lsrepositoriesdata lsrepositoriesdata)throws Exception
	{
		return masterService.Saverepositorydata(lsrepositoriesdata);
	}
	
	@PostMapping("/GetupdatedRepositorydata")
	public Lsrepositoriesdata GetupdatedRepositorydata(@RequestBody Lsrepositoriesdata lsrepositoriesdata)throws Exception
	{
		return masterService.GetupdatedRepositorydata(lsrepositoriesdata);
	}
	
	@PostMapping("/DeleteRepositorydata")
	public Lsrepositoriesdata DeleteRepositorydata(@RequestBody Lsrepositoriesdata lsrepositoriesdata)throws Exception
	{
		return masterService.DeleteRepositorydata(lsrepositoriesdata);
	}
	
	@PostMapping("/getinventoryhistory")
	public List<Object> getinventoryhistory(@RequestBody LsOrderSampleUpdate lsinventoryhistory)throws Exception
	{
		return masterService.getinventoryhistory(lsinventoryhistory);
	}
	
	@PostMapping("/pushnotificationforinventory")
	public Response pushnotificationforinventory(@RequestBody List<Lsrepositoriesdata>  lsrepositoriesdata)throws Exception
	{
		return masterService.pushnotificationforinventory(lsrepositoriesdata);
	}
	
	@PostMapping("/getrepositoryfields")
	public Map<String,Object> getrepositoryfields(@RequestBody Lsrepositories repositorymaster)throws Exception
	{
		return masterService.getrepositoryfields(repositorymaster);
	}
	
	@PostMapping("/GetrepositoriesdataonFilter")
	public List<Lsrepositoriesdata> GetrepositoriesdataonFilter(@RequestBody Lsrepositoriesdata lsrepositoriesdata)throws Exception
	{
		return masterService.GetrepositoriesdataonFilter(lsrepositoriesdata);
	}
	
	@PostMapping("/archiveproject")
	public LsProjectarchieve archiveproject(@RequestBody LSprojectmaster lsprojectmaster)throws Exception
	{
		return masterService.archiveproject(lsprojectmaster);
	}
	
	@PostMapping("/Importprojectarchieve")
	public LsProjectarchieve Importprojectarchieve(@RequestBody LsProjectarchieve lsprojectarchieve) throws IOException
	{
		return masterService.Importprojectarchieve(lsprojectarchieve);
	}
	
	@PostMapping("/GetArchievedprojectsonsite")
	public List<LsProjectarchieve> GetArchievedprojectsonsite(@RequestBody LSSiteMaster lssitemaster)throws Exception
	{
		return masterService.GetArchievedprojectsonsite(lssitemaster);
	}
	
	@PostMapping("/Downloadarchievedproject")
	public ResponseEntity<InputStreamResource> Downloadarchievedproject(
			@RequestBody LsProjectarchieve lsprojectarchieve) throws IllegalStateException, IOException {

		HttpHeaders header = new HttpHeaders();
		header.set("Content-Disposition", "attachment; filename="+lsprojectarchieve.getProjectname());
		return new ResponseEntity<>(
				new InputStreamResource(masterService.Downloadarchievedproject(lsprojectarchieve)), header,
				HttpStatus.OK);
	}
	
	@PostMapping("/Getalllogbooks")
	public List<Lslogbooks> Getalllogbooks(@RequestBody Lslogbooks lslogbooks)throws Exception
	{
		return masterService.Getalllogbooks(lslogbooks);
	}

	@PostMapping("/Savelogbook")
	public Lslogbooks Savelogbook(@RequestBody Lslogbooks lslogbooks)throws Exception
	{
		return masterService.Savelogbook(lslogbooks);
	}
	
	@PostMapping("/Getalllogbookdata")
	public List<Lslogbooksdata> Getalllogbookdata(@RequestBody Lslogbooksdata lslogbooksdata)throws Exception
	{
		return masterService.Getalllogbookdata(lslogbooksdata);
	}
	
	@PostMapping("/GetupdatedLogbookdata")
	public Lslogbooksdata GetupdatedLogbookdata(@RequestBody Lslogbooksdata lslogbooksdata)throws Exception
	{
		return masterService.GetupdatedLogbookdata(lslogbooksdata);
	}
	
	@PostMapping("/DeleteLogbookdata")
	public Lslogbooksdata DeleteLogbookdata(@RequestBody Lslogbooksdata lslogbooksdata)throws Exception
	{
		return masterService.DeleteLogbookdata(lslogbooksdata);
	}
	
	@PostMapping("/Savelogbookdata")
	public Lslogbooksdata Savelogbookdata(@RequestBody Lslogbooksdata lslogbooksdata)throws Exception
	{
		return masterService.Savelogbookdata(lslogbooksdata);
	}
	
	@PostMapping("/Reviewlogbook")
	public List<Lslogbooks> Reviewlogbook(@RequestBody Lslogbooks[] objreview)throws Exception {

		return masterService.Reviewlogbook(objreview);
	}
	
	@PostMapping("/Retirelogbook")
	public List<Lslogbooks> Retirelogbook(@RequestBody Lslogbooks[] objreview)throws Exception {

		return masterService.Retirelogbook(objreview);
	}
	
	@PostMapping("/GetalllogbooksOnSite")
	public List<Lslogbooks> GetalllogbooksOnSite(@RequestBody Lslogbooks lslogbooks)throws Exception
	{
		return masterService.GetalllogbooksOnSite(lslogbooks);
	}
	
	
}
