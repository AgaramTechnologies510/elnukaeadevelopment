package com.agaram.eln.primary.repository.reports.reportviewer;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agaram.eln.primary.model.reports.reportviewer.ReportViewerStructure;
import com.agaram.eln.primary.model.reports.reportviewer.Reports;
import com.agaram.eln.primary.model.usermanagement.LSSiteMaster;
import com.agaram.eln.primary.model.usermanagement.LSprojectmaster;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;

public interface ReportsRepository extends JpaRepository<Reports, Long> {
	public List<Reports> findByReportviewerstructure(ReportViewerStructure reportviewerstructure);

	public Optional<Reports> findByReportnameIgnoreCase(String reportname);

	public List<Reports> findBySitemasterAndViewoptionAndTemplatetypeAndCreatedbyInAndDatecreatedBetweenOrSitemasterAndViewoptionAndTemplatetypeAndCreatedbyAndDatecreatedBetweenOrSitemasterAndViewoptionAndTemplatetypeAndCreatedbyInAndDatecreatedBetweenOrderByReportcodeDesc(
			LSSiteMaster sitemaster, int i, Integer templatetype, List<LSuserMaster> lstuserMaster, Date fromdate,
			Date todate, LSSiteMaster sitemaster2, int j, Integer templatetype2, LSuserMaster createdby, Date fromdate2,
			Date todate2, LSSiteMaster sitemaster3, int k, Integer templatetype3, List<LSuserMaster> lstuserMaster2,
			Date fromdate3, Date todate3);
	
	public List<Reports> findBySitemasterAndViewoptionAndTemplatetypeAndCreatedbyInAndDatecreatedBetweenOrSitemasterAndViewoptionAndTemplatetypeAndCreatedbyAndDatecreatedBetweenOrSitemasterAndViewoptionAndTemplatetypeAndCreatedbyInAndDatecreatedBetweenOrderByDatemodifiedDesc(
			LSSiteMaster sitemaster, int i, Integer templatetype, List<LSuserMaster> lstuserMaster, Date fromdate,
			Date todate, LSSiteMaster sitemaster2, int j, Integer templatetype2, LSuserMaster createdby, Date fromdate2,
			Date todate2, LSSiteMaster sitemaster3, int k, Integer templatetype3, List<LSuserMaster> lstuserMaster2,
			Date fromdate3, Date todate3);

	public Reports findTopByReportnameIgnoreCaseAndSitemaster(String reportname, LSSiteMaster sitemaster);

	public List<Reports> findByReportcodeIn(List<Long> templateCodes);


	public List<Reports> findBySitemasterAndViewoptionAndTemplatetypeAndCreatedbyInAndDatecreatedBetweenOrSitemasterAndViewoptionAndTemplatetypeAndCreatedbyInAndDatecreatedBetweenOrderByReportcodeDesc(
			LSSiteMaster sitemaster, int i, Integer templatetype, List<LSuserMaster> lstuserMaster, Date fromdate,
			Date todate, LSSiteMaster sitemaster2, int j, Integer templatetype2, List<LSuserMaster> lstuserMaster2,
			Date fromdate2, Date todate2);

	public List<Reports> findByReportviewerstructure_DirectorycodeAndSitemasterAndViewoptionAndDatecreatedBetweenOrReportviewerstructure_DirectorycodeAndSitemasterAndViewoptionAndCreatedbyInAndDatecreatedBetweenOrderByReportcodeDesc(
			Long directorycode, LSSiteMaster sitemaster, int i, Date fromdate, Date todate, Long directorycode2,
			LSSiteMaster sitemaster2, int j, List<LSuserMaster> lstuserMaster, Date fromdate2, Date todate2);

	public List<Reports> findByReportviewerstructure_DirectorycodeAndSitemasterAndProjectAndViewoptionAndDatecreatedBetweenOrReportviewerstructure_DirectorycodeAndSitemasterAndProjectAndViewoptionAndCreatedbyInAndDatecreatedBetweenOrderByReportcodeDesc(
			Long directorycode, LSSiteMaster sitemaster, LSprojectmaster project, int i, Date fromdate, Date todate,
			Long directorycode2, LSSiteMaster sitemaster2, LSprojectmaster project2, int j,
			List<LSuserMaster> lstuserMaster, Date fromdate2, Date todate2);

	public List<Reports> findByReportviewerstructure_DirectorycodeAndSitemasterAndTemplatetypeAndProjectAndViewoptionAndDatecreatedBetweenOrReportviewerstructure_DirectorycodeAndSitemasterAndTemplatetypeAndProjectAndViewoptionAndCreatedbyInAndDatecreatedBetweenOrderByReportcodeDesc(
			Long directorycode, LSSiteMaster sitemaster, Integer templatetype, LSprojectmaster project, int i,
			Date fromdate, Date todate, Long directorycode2, LSSiteMaster sitemaster2, Integer templatetype2,
			LSprojectmaster project2, int j, List<LSuserMaster> lstuserMaster, Date fromdate2, Date todate2);

	public List<Reports> findByReportviewerstructure_DirectorycodeAndSitemasterAndTemplatetypeAndViewoptionAndDatecreatedBetweenOrReportviewerstructure_DirectorycodeAndSitemasterAndTemplatetypeAndViewoptionAndCreatedbyInAndDatecreatedBetweenOrderByReportcodeDesc(
			Long directorycode, LSSiteMaster sitemaster, Integer templatetype, int i, Date fromdate, Date todate,
			Long directorycode2, LSSiteMaster sitemaster2, Integer templatetype2, int j,
			List<LSuserMaster> lstuserMaster, Date fromdate2, Date todate2);

	public List<Reports> findByReportviewerstructure_DirectorycodeAndSitemasterAndViewoptionAndDatecreatedBetweenAndReporttemplate_TemplatenameContainingIgnoreCaseOrReportviewerstructure_DirectorycodeAndSitemasterAndViewoptionAndCreatedbyInAndDatecreatedBetweenAndReporttemplate_TemplatenameContainingIgnoreCaseOrderByReportcodeDesc(
			Long directorycode, LSSiteMaster sitemaster, int i, Date fromdate, Date todate, String searchtemplatename,
			Long directorycode2, LSSiteMaster sitemaster2, int j, List<LSuserMaster> lstuserMaster, Date fromdate2,
			Date todate2, String searchtemplatename2);

	public List<Reports> findByReportviewerstructure_DirectorycodeAndSitemasterAndProjectAndViewoptionAndDatecreatedBetweenAndReporttemplate_TemplatenameContainingIgnoreCaseOrReportviewerstructure_DirectorycodeAndSitemasterAndProjectAndViewoptionAndCreatedbyInAndDatecreatedBetweenAndReporttemplate_TemplatenameContainingIgnoreCaseOrderByReportcodeDesc(
			Long directorycode, LSSiteMaster sitemaster, LSprojectmaster project, int i, Date fromdate, Date todate,
			String searchtemplatename, Long directorycode2, LSSiteMaster sitemaster2, LSprojectmaster project2, int j,
			List<LSuserMaster> lstuserMaster, Date fromdate2, Date todate2, String searchtemplatename2);

	public List<Reports> findByReportviewerstructure_DirectorycodeAndSitemasterAndTemplatetypeAndProjectAndViewoptionAndDatecreatedBetweenAndReporttemplate_TemplatenameContainingIgnoreCaseOrReportviewerstructure_DirectorycodeAndSitemasterAndTemplatetypeAndProjectAndViewoptionAndCreatedbyInAndDatecreatedBetweenAndReporttemplate_TemplatenameContainingIgnoreCaseOrderByReportcodeDesc(
			Long directorycode, LSSiteMaster sitemaster, Integer templatetype, LSprojectmaster project, int i,
			Date fromdate, Date todate, String searchtemplatename, Long directorycode2, LSSiteMaster sitemaster2,
			Integer templatetype2, LSprojectmaster project2, int j, List<LSuserMaster> lstuserMaster, Date fromdate2,
			Date todate2, String searchtemplatename2);

	public List<Reports> findByReportviewerstructure_DirectorycodeAndSitemasterAndTemplatetypeAndViewoptionAndDatecreatedBetweenAndReporttemplate_TemplatenameContainingIgnoreCaseOrReportviewerstructure_DirectorycodeAndSitemasterAndTemplatetypeAndViewoptionAndCreatedbyInAndDatecreatedBetweenAndReporttemplate_TemplatenameContainingIgnoreCaseOrderByReportcodeDesc(
			Long directorycode, LSSiteMaster sitemaster, Integer templatetype, int i, Date fromdate, Date todate,
			String searchtemplatename, Long directorycode2, LSSiteMaster sitemaster2, Integer templatetype2, int j,
			List<LSuserMaster> lstuserMaster, Date fromdate2, Date todate2, String searchtemplatename2);

	public List<Reports> findByReportviewerstructure_DirectorycodeAndSitemasterAndViewoptionAndDatecreatedBetweenAndKeywordContainingIgnoreCaseOrReportviewerstructure_DirectorycodeAndSitemasterAndViewoptionAndCreatedbyInAndDatecreatedBetweenAndKeywordContainingIgnoreCaseOrderByReportcodeDesc(
			Long directorycode, LSSiteMaster sitemaster, int i, Date fromdate, Date todate, String searchKeyword,
			Long directorycode2, LSSiteMaster sitemaster2, int j, List<LSuserMaster> lstuserMaster, Date fromdate2,
			Date todate2, String searchKeyword2);

	public List<Reports> findByReportviewerstructure_DirectorycodeAndSitemasterAndProjectAndViewoptionAndDatecreatedBetweenAndKeywordContainingIgnoreCaseOrReportviewerstructure_DirectorycodeAndSitemasterAndProjectAndViewoptionAndCreatedbyInAndDatecreatedBetweenAndKeywordContainingIgnoreCaseOrderByReportcodeDesc(
			Long directorycode, LSSiteMaster sitemaster, LSprojectmaster project, int i, Date fromdate, Date todate,
			String searchKeyword, Long directorycode2, LSSiteMaster sitemaster2, LSprojectmaster project2, int j,
			List<LSuserMaster> lstuserMaster, Date fromdate2, Date todate2, String searchKeyword2);

	public List<Reports> findByReportviewerstructure_DirectorycodeAndSitemasterAndTemplatetypeAndProjectAndViewoptionAndDatecreatedBetweenAndKeywordContainingIgnoreCaseOrReportviewerstructure_DirectorycodeAndSitemasterAndTemplatetypeAndProjectAndViewoptionAndCreatedbyInAndDatecreatedBetweenAndKeywordContainingIgnoreCaseOrderByReportcodeDesc(
			Long directorycode, LSSiteMaster sitemaster, Integer templatetype, LSprojectmaster project, int i,
			Date fromdate, Date todate, String searchKeyword, Long directorycode2, LSSiteMaster sitemaster2,
			Integer templatetype2, LSprojectmaster project2, int j, List<LSuserMaster> lstuserMaster, Date fromdate2,
			Date todate2, String searchKeyword2);

	public List<Reports> findByReportviewerstructure_DirectorycodeAndSitemasterAndTemplatetypeAndViewoptionAndDatecreatedBetweenAndKeywordContainingIgnoreCaseOrReportviewerstructure_DirectorycodeAndSitemasterAndTemplatetypeAndViewoptionAndCreatedbyInAndDatecreatedBetweenAndKeywordContainingIgnoreCaseOrderByReportcodeDesc(
			Long directorycode, LSSiteMaster sitemaster, Integer templatetype, int i, Date fromdate, Date todate,
			String searchKeyword, Long directorycode2, LSSiteMaster sitemaster2, Integer templatetype2, int j,
			List<LSuserMaster> lstuserMaster, Date fromdate2, Date todate2, String searchKeyword2);

	public List<Reports> findBySitemasterAndViewoptionAndTemplatetypeAndDatecreatedBetweenOrSitemasterAndViewoptionAndTemplatetypeAndCreatedbyInAndDatecreatedBetweenOrderByReportcodeDesc(
			LSSiteMaster sitemaster, int i, Integer templatetype, Date fromdate, Date todate, LSSiteMaster sitemaster2,
			int j, Integer templatetype2, List<LSuserMaster> lstuserMaster, Date fromdate2, Date todate2);

	public List<Reports> findBySitemasterAndViewoptionAndTemplatetypeAndProjectAndDatecreatedBetweenOrSitemasterAndViewoptionAndTemplatetypeAndProjectAndCreatedbyInAndDatecreatedBetweenOrderByReportcodeDesc(
			LSSiteMaster sitemaster, int i, Integer templatetype, LSprojectmaster project, Date fromdate, Date todate,
			LSSiteMaster sitemaster2, int j, Integer templatetype2, LSprojectmaster project2,
			List<LSuserMaster> lstuserMaster, Date fromdate2, Date todate2);

	public List<Reports> findBySitemasterAndViewoptionAndTemplatetypeAndDatecreatedBetweenAndReporttemplate_TemplatenameContainingIgnoreCaseOrSitemasterAndViewoptionAndTemplatetypeAndCreatedbyInAndDatecreatedBetweenAndReporttemplate_TemplatenameContainingIgnoreCaseOrderByReportcodeDesc(
			LSSiteMaster sitemaster, int i, Integer templatetype, Date fromdate, Date todate, String searchtemplatename,
			LSSiteMaster sitemaster2, int j, Integer templatetype2, List<LSuserMaster> lstuserMaster, Date fromdate2,
			Date todate2, String searchtemplatename2);

	public List<Reports> findBySitemasterAndViewoptionAndTemplatetypeAndProjectAndDatecreatedBetweenAndReporttemplate_TemplatenameContainingIgnoreCaseOrSitemasterAndViewoptionAndTemplatetypeAndProjectAndCreatedbyInAndDatecreatedBetweenAndReporttemplate_TemplatenameContainingIgnoreCaseOrderByReportcodeDesc(
			LSSiteMaster sitemaster, int i, Integer templatetype, LSprojectmaster project, Date fromdate, Date todate,
			String searchtemplatename, LSSiteMaster sitemaster2, int j, Integer templatetype2, LSprojectmaster project2,
			List<LSuserMaster> lstuserMaster, Date fromdate2, Date todate2, String searchtemplatename2);

	public List<Reports> findBySitemasterAndViewoptionAndTemplatetypeAndDatecreatedBetweenAndKeywordContainingIgnoreCaseOrSitemasterAndViewoptionAndTemplatetypeAndCreatedbyInAndDatecreatedBetweenAndKeywordContainingIgnoreCaseOrderByReportcodeDesc(
			LSSiteMaster sitemaster, int i, Integer templatetype, Date fromdate, Date todate, String searchKeyword,
			LSSiteMaster sitemaster2, int j, Integer templatetype2, List<LSuserMaster> lstuserMaster, Date fromdate2,
			Date todate2, String searchKeyword2);

	public List<Reports> findBySitemasterAndViewoptionAndTemplatetypeAndProjectAndDatecreatedBetweenAndKeywordContainingIgnoreCaseOrSitemasterAndViewoptionAndTemplatetypeAndProjectAndCreatedbyInAndDatecreatedBetweenAndKeywordContainingIgnoreCaseOrderByReportcodeDesc(
			LSSiteMaster sitemaster, int i, Integer templatetype, LSprojectmaster project, Date fromdate, Date todate,
			String searchKeyword, LSSiteMaster sitemaster2, int j, Integer templatetype2, LSprojectmaster project2,
			List<LSuserMaster> lstuserMaster, Date fromdate2, Date todate2, String searchKeyword2);

	
	
}