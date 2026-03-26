package com.agaram.eln.primary.service.instrumentDetails;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

//import javax.sql.DataSource;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.agaram.eln.primary.commonfunction.commonfunction;
import com.agaram.eln.primary.config.TenantContext;
import com.agaram.eln.primary.fetchmodel.getmasters.Projectmaster;
import com.agaram.eln.primary.fetchmodel.getorders.LogilabOrderDetails;
import com.agaram.eln.primary.fetchmodel.getorders.LogilabOrdermastersh;
import com.agaram.eln.primary.fetchmodel.getorders.LogilabProtocolOrderssh;
import com.agaram.eln.primary.fetchmodel.getorders.Logilabordermaster;
import com.agaram.eln.primary.fetchmodel.getorders.Logilaborders;
import com.agaram.eln.primary.fetchmodel.getorders.Logilaborderssh;
import com.agaram.eln.primary.fetchmodel.getorders.Logilabprotocolorders;
import com.agaram.eln.primary.fetchmodel.getorders.TagStructure;
import com.agaram.eln.primary.model.cfr.LSactivity;
//import com.agaram.eln.primary.model.cfr.LSaudittrailconfiguration;
import com.agaram.eln.primary.model.cfr.LScfttransaction;
import com.agaram.eln.primary.model.cloudFileManip.CloudOrderAttachment;
import com.agaram.eln.primary.model.cloudFileManip.CloudOrderCreation;
import com.agaram.eln.primary.model.cloudFileManip.CloudOrderVersion;
import com.agaram.eln.primary.model.cloudFileManip.CloudSheetCreation;
import com.agaram.eln.primary.model.equipment.Equipment;
import com.agaram.eln.primary.model.fileManipulation.Fileimages;
import com.agaram.eln.primary.model.fileManipulation.Fileimagestemp;
import com.agaram.eln.primary.model.fileManipulation.LSfileimages;
import com.agaram.eln.primary.model.fileManipulation.OrderAttachment;
import com.agaram.eln.primary.model.fileManipulation.ResultorderlimsRefrence;
import com.agaram.eln.primary.model.fileManipulation.SheetorderlimsRefrence;
import com.agaram.eln.primary.model.general.OrderCreation;
import com.agaram.eln.primary.model.general.Response;
import com.agaram.eln.primary.model.general.SearchCriteria;
import com.agaram.eln.primary.model.general.SheetCreation;
import com.agaram.eln.primary.model.instrumentDetails.LSOrderElnMethod;
import com.agaram.eln.primary.model.instrumentDetails.LSOrdernotification;
import com.agaram.eln.primary.model.instrumentDetails.LSSelectedTeam;
import com.agaram.eln.primary.model.instrumentDetails.LSSheetOrderStructure;
import com.agaram.eln.primary.model.instrumentDetails.LSfields;
import com.agaram.eln.primary.model.instrumentDetails.LSlogilablimsorder;
import com.agaram.eln.primary.model.instrumentDetails.LSlogilablimsorderdetail;
import com.agaram.eln.primary.model.instrumentDetails.LSlogilablimsorderdetail.OrderShareInterface;
import com.agaram.eln.primary.model.instrumentDetails.LSprotocolfolderfiles;
import com.agaram.eln.primary.model.instrumentDetails.LSresultdetails;
import com.agaram.eln.primary.model.instrumentDetails.LSsheetfolderfiles;
import com.agaram.eln.primary.model.instrumentDetails.LsAutoregister;
import com.agaram.eln.primary.model.instrumentDetails.LsMappedInstruments;
import com.agaram.eln.primary.model.instrumentDetails.LsMethodFields;
import com.agaram.eln.primary.model.instrumentDetails.LsOrderLinks;
import com.agaram.eln.primary.model.instrumentDetails.LsOrderSampleUpdate;
import com.agaram.eln.primary.model.instrumentDetails.LsOrderattachments;
import com.agaram.eln.primary.model.instrumentDetails.LsResultlimsOrderrefrence;
import com.agaram.eln.primary.model.instrumentDetails.LsSheetorderlimsrefrence;
import com.agaram.eln.primary.model.instrumentDetails.Lsbatchdetails;
import com.agaram.eln.primary.model.instrumentDetails.Lsordersharedby;
import com.agaram.eln.primary.model.instrumentDetails.Lsordershareto;
//import com.agaram.eln.primary.model.instrumentDetails.Lsordershareto.LsordersharetoProjection;
import com.agaram.eln.primary.model.instrumentDetails.Lsorderworkflowhistory;
import com.agaram.eln.primary.model.instrumentDetails.Lsprotocolordersharedby;
import com.agaram.eln.primary.model.instrumentDetails.Lsprotocolordershareto;
import com.agaram.eln.primary.model.instrumentDetails.Lsprotocolorderstructure;
import com.agaram.eln.primary.model.instrumentDetails.Lsresultfororders;
import com.agaram.eln.primary.model.instrumentsetup.InstrumentMaster;
import com.agaram.eln.primary.model.masters.Lsrepositories;
import com.agaram.eln.primary.model.masters.Lsrepositoriesdata;
import com.agaram.eln.primary.model.material.Elnmaterial;
import com.agaram.eln.primary.model.methodsetup.CloudParserFile;
import com.agaram.eln.primary.model.methodsetup.ELNFileAttachments;
import com.agaram.eln.primary.model.methodsetup.ELNResultDetails;
import com.agaram.eln.primary.model.methodsetup.LSResultFieldValues;
import com.agaram.eln.primary.model.methodsetup.Method;
import com.agaram.eln.primary.model.methodsetup.ParserBlock;
import com.agaram.eln.primary.model.methodsetup.ParserField;
import com.agaram.eln.primary.model.methodsetup.SubParserField;
import com.agaram.eln.primary.model.notification.Email;
import com.agaram.eln.primary.model.protocols.Elnprotocolworkflow;
import com.agaram.eln.primary.model.protocols.Elnprotocolworkflowgroupmap;
import com.agaram.eln.primary.model.protocols.LSlogilabprotocoldetail;
import com.agaram.eln.primary.model.protocols.LSlogilabprotocoldetail.ProtocolOrderShareInterface;
import com.agaram.eln.primary.model.protocols.LSprotocolselectedteam;
import com.agaram.eln.primary.model.reports.lsreportfile;
import com.agaram.eln.primary.model.sequence.SequenceTable;
import com.agaram.eln.primary.model.sequence.SequenceTableOrderType;
import com.agaram.eln.primary.model.sequence.SequenceTableProject;
import com.agaram.eln.primary.model.sequence.SequenceTableProjectLevel;
import com.agaram.eln.primary.model.sequence.SequenceTableSite;
import com.agaram.eln.primary.model.sequence.SequenceTableTask;
import com.agaram.eln.primary.model.sequence.SequenceTableTaskLevel;
import com.agaram.eln.primary.model.sheetManipulation.LSfile;
import com.agaram.eln.primary.model.sheetManipulation.LSfileelnmethod;
import com.agaram.eln.primary.model.sheetManipulation.LSfilemethod;
import com.agaram.eln.primary.model.sheetManipulation.LSsamplefile;
import com.agaram.eln.primary.model.sheetManipulation.LSsamplefileversion;
import com.agaram.eln.primary.model.sheetManipulation.LSsamplemaster;
import com.agaram.eln.primary.model.sheetManipulation.LSworkflow;
import com.agaram.eln.primary.model.sheetManipulation.LSworkflowgroupmapping;
import com.agaram.eln.primary.model.templates.LsMappedTemplate;
import com.agaram.eln.primary.model.templates.LsUnmappedTemplate;
import com.agaram.eln.primary.model.usermanagement.LSMultisites;
//import com.agaram.eln.primary.model.usermanagement.LSMultiusergroup;
import com.agaram.eln.primary.model.usermanagement.LSSiteMaster;
import com.agaram.eln.primary.model.usermanagement.LSactiveUser;
import com.agaram.eln.primary.model.usermanagement.LScentralisedUsers;
import com.agaram.eln.primary.model.usermanagement.LSnotification;
import com.agaram.eln.primary.model.usermanagement.LSprojectmaster;
import com.agaram.eln.primary.model.usermanagement.LSuserActions;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;
import com.agaram.eln.primary.model.usermanagement.LSusergroup;
import com.agaram.eln.primary.model.usermanagement.LSusersteam;
import com.agaram.eln.primary.model.usermanagement.LSuserteammapping;
import com.agaram.eln.primary.repository.cfr.LSactivityRepository;
import com.agaram.eln.primary.repository.cfr.LScfttransactionRepository;
import com.agaram.eln.primary.repository.cfr.LSpreferencesRepository;
import com.agaram.eln.primary.repository.cloudFileManip.CloudOrderAttachmentRepository;
import com.agaram.eln.primary.repository.cloudFileManip.CloudOrderCreationRepository;
import com.agaram.eln.primary.repository.cloudFileManip.CloudOrderVersionRepository;
import com.agaram.eln.primary.repository.cloudFileManip.CloudSheetCreationRepository;
import com.agaram.eln.primary.repository.dashboard.LsActiveWidgetsRepository;
import com.agaram.eln.primary.repository.equipment.EquipmentRepository;
import com.agaram.eln.primary.repository.fileManipulation.FileimagesRepository;
import com.agaram.eln.primary.repository.fileManipulation.FileimagestempRepository;
import com.agaram.eln.primary.repository.fileManipulation.LSfileimagesRepository;
import com.agaram.eln.primary.repository.instrumentDetails.LSOrderElnMethodRepository;
import com.agaram.eln.primary.repository.instrumentDetails.LSSelectedTeamRepository;
import com.agaram.eln.primary.repository.instrumentDetails.LSSheetOrderStructureRepository;
import com.agaram.eln.primary.repository.instrumentDetails.LSfieldsRepository;
import com.agaram.eln.primary.repository.instrumentDetails.LSlogilablimsorderRepository;
import com.agaram.eln.primary.repository.instrumentDetails.LSlogilablimsorderdetailRepository;
import com.agaram.eln.primary.repository.instrumentDetails.LSordernotificationRepository;
import com.agaram.eln.primary.repository.instrumentDetails.LSprotocolfolderfilesRepository;
import com.agaram.eln.primary.repository.instrumentDetails.LSresultdetailsRepository;
import com.agaram.eln.primary.repository.instrumentDetails.LSsheetfolderfilesRepository;
import com.agaram.eln.primary.repository.instrumentDetails.LogilablimsorderdetailsRepository;
import com.agaram.eln.primary.repository.instrumentDetails.LsAutoregisterRepository;
import com.agaram.eln.primary.repository.instrumentDetails.LsMappedInstrumentsRepository;
import com.agaram.eln.primary.repository.instrumentDetails.LsMethodFieldsRepository;
import com.agaram.eln.primary.repository.instrumentDetails.LsOrderLinkRepository;
import com.agaram.eln.primary.repository.instrumentDetails.LsOrderSampleUpdateRepository;
import com.agaram.eln.primary.repository.instrumentDetails.LsOrderSampleUpdateRepository.UserProjection;
import com.agaram.eln.primary.repository.instrumentDetails.LsOrderattachmentsRepository;
import com.agaram.eln.primary.repository.instrumentDetails.LsResultlimsOrderrefrenceRepository;
import com.agaram.eln.primary.repository.instrumentDetails.LsSheetorderlimsrefrenceRepository;
import com.agaram.eln.primary.repository.instrumentDetails.LsbatchdetailsRepository;
import com.agaram.eln.primary.repository.instrumentDetails.LselninstrumentmasterRepository;
import com.agaram.eln.primary.repository.instrumentDetails.LsordersharedbyRepository;
import com.agaram.eln.primary.repository.instrumentDetails.LsordersharetoRepository;
import com.agaram.eln.primary.repository.instrumentDetails.LsorderworkflowhistoryRepositroy;
import com.agaram.eln.primary.repository.instrumentDetails.LsprotocolOrderStructureRepository;
import com.agaram.eln.primary.repository.instrumentDetails.LsprotocolordersharedbyRepository;
import com.agaram.eln.primary.repository.instrumentDetails.LsprotocolordersharetoRepository;
import com.agaram.eln.primary.repository.instrumentDetails.LsresultforordersRepository;
import com.agaram.eln.primary.repository.instrumentsetup.InstMasterRepository;
import com.agaram.eln.primary.repository.masters.LsrepositoriesdataRepository;
import com.agaram.eln.primary.repository.material.ElnmaterialRepository;
import com.agaram.eln.primary.repository.methodsetup.CloudParserFileRepository;
import com.agaram.eln.primary.repository.methodsetup.ELNFileAttachmentsRepository;
import com.agaram.eln.primary.repository.methodsetup.ELNResultDetailsRepository;
import com.agaram.eln.primary.repository.methodsetup.MethodRepository;
import com.agaram.eln.primary.repository.methodsetup.ParserBlockRepository;
import com.agaram.eln.primary.repository.methodsetup.ParserFieldRepository;
import com.agaram.eln.primary.repository.methodsetup.SubParserFieldRepository;
import com.agaram.eln.primary.repository.notification.EmailRepository;
import com.agaram.eln.primary.repository.protocol.ElnprotocolworkflowRepository;
import com.agaram.eln.primary.repository.protocol.ElnprotocolworkflowgroupmapRepository;
import com.agaram.eln.primary.repository.protocol.LSlogilabprotocoldetailRepository;
import com.agaram.eln.primary.repository.protocol.LSprotocolselectedteamRepository;
import com.agaram.eln.primary.repository.protocol.LogilabprotocoldetailRepository;
import com.agaram.eln.primary.repository.reports.ReportfileRepository;
import com.agaram.eln.primary.repository.sequence.SequenceTableOrderTypeRepository;
import com.agaram.eln.primary.repository.sequence.SequenceTableProjectLevelRepository;
import com.agaram.eln.primary.repository.sequence.SequenceTableProjectRepository;
import com.agaram.eln.primary.repository.sequence.SequenceTableRepository;
import com.agaram.eln.primary.repository.sequence.SequenceTableSiteRepository;
import com.agaram.eln.primary.repository.sequence.SequenceTableTaskLevelRepository;
import com.agaram.eln.primary.repository.sequence.SequenceTableTaskRepository;
import com.agaram.eln.primary.repository.sheetManipulation.LSfileRepository;
import com.agaram.eln.primary.repository.sheetManipulation.LSfileelnmethodRepository;
import com.agaram.eln.primary.repository.sheetManipulation.LSfilemethodRepository;
import com.agaram.eln.primary.repository.sheetManipulation.LSfileparameterRepository;
import com.agaram.eln.primary.repository.sheetManipulation.LSparsedparametersRespository;
import com.agaram.eln.primary.repository.sheetManipulation.LSsamplefileRepository;
import com.agaram.eln.primary.repository.sheetManipulation.LSsamplefileversionRepository;
import com.agaram.eln.primary.repository.sheetManipulation.LSsamplemasterRepository;
import com.agaram.eln.primary.repository.sheetManipulation.LSsampleresultRepository;
import com.agaram.eln.primary.repository.sheetManipulation.LStestparameterRepository;
import com.agaram.eln.primary.repository.sheetManipulation.LSworkflowRepository;
import com.agaram.eln.primary.repository.sheetManipulation.LSworkflowgroupmappingRepository;
import com.agaram.eln.primary.repository.sheetManipulation.NotificationRepository;
import com.agaram.eln.primary.repository.templates.LsMappedTemplateRepository;
import com.agaram.eln.primary.repository.templates.LsUnmappedTemplateRepository;
import com.agaram.eln.primary.repository.usermanagement.LSMultisitesRepositery;
//import com.agaram.eln.primary.repository.usermanagement.LSMultiusergroupRepositery;
import com.agaram.eln.primary.repository.usermanagement.LSactiveUserRepository;
import com.agaram.eln.primary.repository.usermanagement.LSnotificationRepository;
import com.agaram.eln.primary.repository.usermanagement.LSprojectmasterRepository;
import com.agaram.eln.primary.repository.usermanagement.LSprojectmasterRepository.ProjectOrTaskOrMaterialView;
import com.agaram.eln.primary.repository.usermanagement.LSuserMasterRepository;
import com.agaram.eln.primary.repository.usermanagement.LSusersteamRepository;
import com.agaram.eln.primary.repository.usermanagement.LSuserteammappingRepository;
//import com.agaram.eln.primary.repository4mibatis.instrumentDetails.LSlogilablimsorderdetailMibatisRepository;
//import com.agaram.eln.primary.repository4mibatis.LSlogilablimsorderdetailMibatisRepository;
import com.agaram.eln.primary.service.cloudFileManip.CloudFileManipulationservice;
import com.agaram.eln.primary.service.fileManipulation.FileManipulationservice;
import com.agaram.eln.primary.service.fileuploaddownload.FileStorageService;
import com.agaram.eln.primary.service.protocol.Commonservice;
import com.agaram.eln.primary.service.protocol.ProtocolService;
import com.agaram.eln.primary.service.sheetManipulation.FileService;
import com.agaram.eln.primary.service.syncwordconverter.DocumenteditorService;
import com.agaram.eln.primary.service.usermanagement.LoginService;
import com.agaram.eln.primary.service.usermanagement.UserService;
import com.agaram.eln.primary.service.webParser.WebparserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.gridfs.model.GridFSFile;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Service
//@EnableJpaRepositories(basePackageClasses = LsMethodFieldsRepository.class)
public class InstrumentService {

	@Autowired
	private Environment env;
	@Autowired
	Commonservice commonservice;
	@Autowired
	private LsMethodFieldsRepository lsMethodFieldsRepository;
//	@Autowired
//	private LSinstrumentsRepository lSinstrumentsRepository;

	@Autowired
	private CloudParserFileRepository cloudparserfilerepository;
	@Autowired
	private InstMasterRepository lsInstMasterRepository;
	@Autowired
	private FileStorageService fileStorageService;
	@Autowired
	private LogilabprotocoldetailRepository logilabprotocoldetailrepository;
	@Autowired
	private MethodRepository lsMethodRepository;
	@Autowired
	private ParserBlockRepository lsParserBlockRepository;
	@Autowired
	private ParserFieldRepository lsParserRepository;
	@Autowired
	private SubParserFieldRepository lsSubParserRepository;
	@Autowired
	private LSfieldsRepository lSfieldsRepository;
	@Autowired
	private LSfilemethodRepository LSfilemethodRepository;
	@Autowired
	private LSfileparameterRepository lsFileparameterRepository;
	@Autowired
	private LSlogilablimsorderdetailRepository lslogilablimsorderdetailRepository;
	
	@Autowired
	private DocumenteditorService DocumenteditorService;

	@Autowired
	private LogilablimsorderdetailsRepository LogilablimsorderdetailsRepository;
//	@Autowired
//	private LsresulttagsRepository lsresulttagsRepository;
	@Autowired
	private LSworkflowRepository lsworkflowRepository;
	@Autowired
	private LSfileelnmethodRepository LSfileelnmethodRepository;

//	@Autowired
//	private LSlimsorderRepository lslimsorderRepository;
	@Autowired
	private LSlogilablimsorderRepository lslogilablimsorderRepository;
	@Autowired
	private LSsamplefileRepository lssamplefileRepository;
	@Autowired
	private LSresultdetailsRepository lsresultdetailsRepository;
	@Autowired
	private LSactivityRepository lsactivityRepository;
	@Autowired
	private LScfttransactionRepository lscfttransactionRepository;

	@Autowired
	private LSsampleresultRepository lssampleresultRepository;

	@Autowired
	private LSSelectedTeamRepository LSSelectedTeamRepository;

	@Autowired
	private LSparsedparametersRespository lsparsedparametersRespository;

	@Autowired
	private LSuserteammappingRepository lsuserteammappingRepository;

	@Autowired
	private LSusersteamRepository lsusersteamRepository;

	@Autowired
	private LSprojectmasterRepository lsprojectmasterRepository;

	@Autowired
	private LSworkflowgroupmappingRepository lsworkflowgroupmappingRepository;

	@Autowired
	private LSsamplefileversionRepository lssamplefileversionRepository;

	@Autowired
	private LselninstrumentmasterRepository lselninstrumentmasterRepository;

	@Autowired
	private LsorderworkflowhistoryRepositroy lsorderworkflowhistoryRepositroy;

	@Autowired
	private LStestparameterRepository lStestparameterRepository;

	@Autowired
	private LSuserMasterRepository lsuserMasterRepository;

	@Autowired
	private LsOrderattachmentsRepository lsOrderattachmentsRepository;

	@Autowired
	private ELNFileAttachmentsRepository elnFileattachmentsRepository;

	@Autowired
	private LSnotificationRepository lsnotificationRepository;

	@Autowired
	private LsMappedTemplateRepository LsMappedTemplateRepository;

	@Autowired
	private LsUnmappedTemplateRepository LsUnmappedTemplateRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private FileManipulationservice fileManipulationservice;

	@Autowired
	private CloudSheetCreationRepository cloudSheetCreationRepository;
	@Autowired
	private CloudFileManipulationservice objCloudFileManipulationservice;
	@Autowired
	private CloudOrderCreationRepository cloudOrderCreationRepository;
	@Autowired
	private ReportfileRepository reportfileRepository;

	@Autowired
	private LsresultforordersRepository lsresultforordersRepository;

	@Autowired
	private CloudOrderVersionRepository cloudOrderVersionRepository;

	@Autowired
	private CloudFileManipulationservice cloudFileManipulationservice;

	@Autowired
	private LsordersharetoRepository lsordersharetoRepository;

	@Autowired
	private EquipmentRepository EquipmentRepository;

	@Autowired
	private LsordersharedbyRepository lsordersharedbyRepository;

	@Autowired
	private LsOrderSampleUpdateRepository lsOrderSampleUpdateRepository;

	@Autowired
	private LsrepositoriesdataRepository LsrepositoriesdataRepository;

	@Autowired
	private CloudOrderAttachmentRepository CloudOrderAttachmentRepository;

	@Autowired
	private LSfileimagesRepository LSfileimagesRepository;

	@Autowired
	private FileimagesRepository FileimagesRepository;

	@Autowired
	private FileimagestempRepository FileimagestempRepository;

	@Autowired
	private LsSheetorderlimsrefrenceRepository lssheetorderlimsrefrenceRepository;

	@Autowired
	private LsResultlimsOrderrefrenceRepository LsResultlimsOrderrefrenceRepository;

	@Autowired
	private LSfileRepository LSfileRepository;

	@Autowired
	private LSpreferencesRepository LSpreferencesRepository;

	@Autowired
	private WebparserService parserService;

	@Autowired
	private ELNResultDetailsRepository ELNResultDetailsRepository;

	@Autowired
	private LSSheetOrderStructureRepository lsSheetOrderStructureRepository;

	@Autowired
	private LSsamplemasterRepository lssamplemasterrepository;

	@Autowired
	private GridFsTemplate gridFsTemplate;

	@Autowired
	private LSOrderElnMethodRepository LSOrderElnMethodRepository;

//	@Autowired
//	private UserService userService;

	@Autowired
	private LsprotocolOrderStructureRepository lsprotocolorderStructurerepository;

	@Autowired
	private LSlogilabprotocoldetailRepository LSlogilabprotocoldetailRepository;

//	@Autowired
//	private LStestmasterlocalRepository lstestmasterlocalRepository;

	@Autowired
	private FileService fileService;

	@Autowired
	private LsprotocolordersharedbyRepository lsprotocolordersharedbyRepository;

	@Autowired
	private LsprotocolordersharetoRepository lsprotocolordersharetoRepository;

	@Autowired
	private LSsheetfolderfilesRepository lssheetfolderfilesRepository;

	@Autowired
	private ProtocolService ProtocolMasterService;

	@Autowired
	LoginService loginservice;

	@Autowired
	UserService userService;

	@Autowired
	LoginService LoginService;

	@Autowired
	private LSprotocolfolderfilesRepository lsprotocolfolderfilesRepository;

//	@Autowired
//	private LSMultiusergroupRepositery lsMultiusergroupRepositery;

	@Autowired
	private LsbatchdetailsRepository LsbatchdetailsRepository;

	@Autowired
	private LsMappedInstrumentsRepository lsMappedInstrumentsRepository;

	@Autowired
	private LSactiveUserRepository lSactiveUserRepository;

//	@Autowired
//	private LSprotocolorderstephistoryRepository lsprotocolorderstephistoryRepository;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private ElnmaterialRepository elnmaterialRepository;

	@Autowired
	private EmailRepository EmailRepository;
	@Autowired
	private ElnprotocolworkflowRepository elnprotocolworkflowRepository;
	@Autowired
	private ElnprotocolworkflowgroupmapRepository elnprotocolworkflowgroupmapRepository;

	@Autowired
	private LSprotocolselectedteamRepository lsprotoselectedteamRepo;

	@Autowired
	@Qualifier("entityManagerFactory")
	private EntityManager entityManager;

	@Autowired
	private LSMultisitesRepositery LSMultisitesRepositery;

	@Autowired
	private LSordernotificationRepository lsordernotificationrepo;

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private LsActiveWidgetsRepository lsActiveWidgetsRepository;

	@Autowired
	private LsAutoregisterRepository lsautoregisterrepo;

	@Autowired
	private LogilablimsorderdetailsRepository logilablimsorderdetailsRepository;

	@Autowired
	private SequenceTableRepository sequencetableRepository;

	@Autowired
	private SequenceTableSiteRepository sequencetablesiteRepository;

	@Autowired
	private SequenceTableProjectRepository sequencetableprojectRepository;

	@Autowired
	private SequenceTableTaskRepository sequencetabletaskRepository;

	@Autowired
	private DocumenteditorService documenteditorService;

	@Autowired
	private SequenceTableOrderTypeRepository sequencetableordertyperepository;

	@Autowired
	private SequenceTableProjectLevelRepository sequencetableprojectlevelrepository;

	@Autowired
	private SequenceTableTaskLevelRepository sequencetabletasklevelrepository;

	@Autowired
	private Commonservice commonService;

	@Autowired
	private LsOrderLinkRepository lsorderlinkrepository;
	
	private Map<Integer, TimerTask> scheduledTasks = new HashMap<>();

	private static Map<String, Timer> timerMap = new HashMap<>();
	private static Map<String, Boolean> timerStatusMap = new HashMap<>();
	private ConcurrentHashMap<String, LSlogilablimsorderdetail> orderDetailMap = new ConcurrentHashMap<>();// public
																											// Map<String,
																											// Object>
																											// getInstrumentparameters(LSSiteMaster
																											// lssiteMaster)
																											// {
//		Map<String, Object> obj = new HashMap<>();
//		List<String> lsInst = new ArrayList<String>();
//		lsInst.add("INST000");
//		lsInst.add("LPRO");
//		List<LsMethodFields> Methods = lsMethodFieldsRepository.findByinstrumentidNotIn(lsInst);
//
//		if (lssiteMaster.getIsmultitenant() != 1) {
//			List<LSfields> Generalfields = lSfieldsRepository.findByisactive(1);
//			List<LSinstruments> Instruments = lSinstrumentsRepository.findAll();
//			List<InstrumentMaster> InstrMaster = lsInstMasterRepository.findAll();
//			List<LsMappedTemplate> MappedTemplate = LsMappedTemplateRepository.findAll();
//			List<LsUnmappedTemplate> UnmappedTemplate = LsUnmappedTemplateRepository.findAll();
//
//			List<Method> elnMethod = lsMethodRepository.findByStatus(1);
//			List<ParserBlock> ParserBlock = lsParserBlockRepository.findByStatus(1);
//			List<ParserField> ParserField = lsParserRepository.findByStatus(1);
//			List<SubParserField> SubParserField = lsSubParserRepository.findByStatus(1);
//			obj.put("Generalfields", Generalfields);
//			
//			List<ParserField> filteredList = ParserField.stream()
//					.filter(filterParser -> SubParserField.stream()
//							.anyMatch(filterSubParser -> filterParser.getParserfieldkey()
//									.equals(filterSubParser.getParserfield().getParserfieldkey())))
//					.collect(Collectors.toList());
//
//			ParserField.removeAll(filteredList);
//
//			obj.put("Instruments", Instruments);
//			obj.put("Instrmaster", InstrMaster);
//			obj.put("elninstrument", lselninstrumentmasterRepository
//					.findBylssitemasterAndStatusOrderByInstrumentcodeDesc(lssiteMaster, 1));
//			obj.put("Mappedtemplates", MappedTemplate);
//			obj.put("Unmappedtemplates", UnmappedTemplate);
//			obj.put("ELNMethods", elnMethod);
//			obj.put("ParserBlock", ParserBlock);
//			obj.put("ParserField", ParserField);
//			obj.put("SubParserField", SubParserField);
//		} else {
//
//			List<LSfields> Generalfields = lSfieldsRepository.findByisactiveAndMethodnameOrderByFieldordernoAsc(1, "ID_GENERAL");
//
//			List<InstrumentMaster> InstrMaster = lsInstMasterRepository.findByStatus(1);
//
//			List<Method> elnMethod = lsMethodRepository.findByStatus(1);
//			List<ParserBlock> ParserBlock = lsParserBlockRepository.findByStatus(1);
//			List<ParserField> ParserField = lsParserRepository.findByStatus(1);
//			// List<SubParserField> SubParserField = lsSubParserRepository.findAll();
//			List<SubParserField> SubParserField = lsSubParserRepository.findByStatus(1);
//			obj.put("Generalfields", Generalfields);
//
//			List<ParserField> filteredList = ParserField.stream()
//					.filter(filterParser -> SubParserField.stream()
//							.anyMatch(filterSubParser -> filterParser.getParserfieldkey()
//									.equals(filterSubParser.getParserfield().getParserfieldkey())))
//					.collect(Collectors.toList());
//
//			ParserField.removeAll(filteredList);
//
//			obj.put("Instrmaster", InstrMaster);
//			obj.put("ELNMethods", elnMethod);
//			obj.put("ParserBlock", ParserBlock);
//			obj.put("ParserField", ParserField);
//			obj.put("SubParserField", SubParserField);
//		}
//
//		LSpreferences objPrefrence = LSpreferencesRepository.findBySerialno(1);
//
//		if (objPrefrence.getValuesettings().equalsIgnoreCase("Active")) {
//
//			obj.put("Methods", parserService.getwebparsemethods());
//			obj.put("Instruments", parserService.getwebparserInstruments());
//		} else {
//			obj.put("Methods", Methods);
//		}
//
//		return obj;
//	}

	public Map<String, Object> getInstrumentparameters(LSSiteMaster lssiteMaster) {
		Map<String, Object> obj = new HashMap<>();
		List<String> lsInst = new ArrayList<String>();
		lsInst.add("INST000");
		lsInst.add("LPRO");
		List<LsMethodFields> Methods = lsMethodFieldsRepository.findByinstrumentidNotIn(lsInst);
		// List<SubParserField> SubParserField = new ArrayList<SubParserField>();

		if (lssiteMaster.getIsmultitenant() != 1) {
			List<LSfields> Generalfields = lSfieldsRepository.findByisactive(1);
			List<LsMappedInstruments> Instruments = lsMappedInstrumentsRepository.findAll();
			List<InstrumentMaster> InstrMaster = lsInstMasterRepository.findByStatusAndSite(1, lssiteMaster);

			List<Equipment> Equipment = EquipmentRepository
					.findByNsitecodeAndCmmsettingTrueAndNstatusOrderByNequipmentcodeDesc(lssiteMaster.getSitecode(), 1);

			List<Method> elnMethod = lsMethodRepository.findByStatusAndSiteAndEquipmentIsNotNull(1, lssiteMaster);
			List<ParserBlock> ParserBlock = lsParserBlockRepository.findByStatusAndMethodIn(1, elnMethod);
			List<ParserField> ParserField = lsParserRepository.findByStatusAndParserblockIn(1, ParserBlock);
			List<SubParserField> SubParserField = lsSubParserRepository.findByStatusAndParserfieldIn(1, ParserField);

			List<LsMappedTemplate> MappedTemplate = LsMappedTemplateRepository.findAll();
			List<LsUnmappedTemplate> UnmappedTemplate = LsUnmappedTemplateRepository.findAll();

			obj.put("Generalfields", Generalfields);

			List<ParserField> filteredList = ParserField.stream()
					.filter(filterParser -> SubParserField.stream()
							.anyMatch(filterSubParser -> filterParser.getParserfieldkey()
									.equals(filterSubParser.getParserfield().getParserfieldkey())))
					.collect(Collectors.toList());

			ParserField.removeAll(filteredList);

			obj.put("Instruments", Instruments);
			obj.put("Instrmaster", InstrMaster);

			obj.put("Equipment", Equipment);

			obj.put("elninstrument", lselninstrumentmasterRepository
					.findBylssitemasterAndStatusOrderByInstrumentcodeDesc(lssiteMaster, 1));
			obj.put("Mappedtemplates", MappedTemplate);
			obj.put("Unmappedtemplates", UnmappedTemplate);
			obj.put("ELNMethods", elnMethod);
			obj.put("ParserBlock", ParserBlock);
			obj.put("ParserField", ParserField);
			obj.put("SubParserField", SubParserField);

			Generalfields = null;
			Instruments = null;
			InstrMaster = null;
			elnMethod = null;
			ParserBlock = null;
			ParserField = null;
			// SubParserField = null;

		} else {
			List<LSfields> Generalfields = lSfieldsRepository.findByisactiveAndMethodname(1, "ID_GENERAL");

			List<InstrumentMaster> InstrMaster = lsInstMasterRepository.findByStatusAndSite(1, lssiteMaster);

			List<Equipment> Equipment = EquipmentRepository
					.findByNsitecodeAndCmmsettingTrueAndNstatusOrderByNequipmentcodeDesc(lssiteMaster.getSitecode(), 1);

			List<Method> elnMethod = lsMethodRepository.findByStatusAndSiteAndEquipmentIsNotNull(1, lssiteMaster);
			List<ParserBlock> ParserBlock = lsParserBlockRepository.findByStatusAndMethodIn(1, elnMethod);
			List<ParserField> ParserField = lsParserRepository.findByStatusAndParserblockIn(1, ParserBlock);

			List<SubParserField> SubParserField = lsSubParserRepository.findByStatusAndParserfieldIn(1, ParserField);

			obj.put("Generalfields", Generalfields);

			List<ParserField> filteredList = ParserField.stream()
					.filter(filterParser -> SubParserField.stream()
							.anyMatch(filterSubParser -> filterParser.getParserfieldkey()
									.equals(filterSubParser.getParserfield().getParserfieldkey())))
					.collect(Collectors.toList());

			ParserField.removeAll(filteredList);

			obj.put("Instrmaster", InstrMaster);
			obj.put("Equipment", Equipment);

			obj.put("ELNMethods", elnMethod);
			obj.put("ParserBlock", ParserBlock);
			obj.put("ParserField", ParserField);
			obj.put("SubParserField", SubParserField);

			Generalfields = null;
			InstrMaster = null;
			elnMethod = null;
			ParserBlock = null;
			ParserField = null;
		}
		if (LSpreferencesRepository.findByTasksettings("WebParser") != null && LSpreferencesRepository
				.findByTasksettings("WebParser").getValuesettings().equalsIgnoreCase("Active")) {
			obj.put("Methods", parserService.getwebparsemethods());
			obj.put("Instruments", parserService.getwebparserInstruments());
		} else {
			obj.put("Methods", Methods);
		}
		Methods = null;
		lsInst = null;
		return obj;
	}

	public static String generateUniqueTimerId() {
		return UUID.randomUUID().toString();
	}

//	@Autowired
//    private DataSource dataSource;

	@SuppressWarnings("resource")
	public LSlogilablimsorderdetail InsertAutoRegisterOrder(LSlogilablimsorderdetail objorderindex, String timerId1)
			throws IOException, ParseException, SQLException {

		List<LSlogilablimsorderdetail> oldorder = lslogilablimsorderdetailRepository
				.findBybatchcode(objorderindex.getBatchcode());
		LSlogilablimsorderdetail objorderindex1 = oldorder.get(0);

		List<LsAutoregister> autoorder = lsautoregisterrepo.findByBatchcodeAndScreen(objorderindex1.getBatchcode(),
				objorderindex1.getLsautoregisterorders().getScreen());
		Integer Ismultitenant = autoorder.get(0).getIsmultitenant();

		Integer autoregistercount = objorderindex1.getAutoregistercount();
		if (autoorder != null) {
			autoorder.get(0).setRepeat(false);
			lsautoregisterrepo.save(autoorder.get(0));
		}

		Long insertedBatchcode = null;
		Long autoOrderBatchcode = objorderindex1.getBatchcode();

		if (autoregistercount > 0) {
//			Integer autoregistercountObj = objorderindex1.getAutoregistercount() - 1;
//			objorderindex1.setRepeat(false);
//			objorderindex1.setAutoregistercount(0);
//			lslogilablimsorderdetailRepository.save(objorderindex1);
			lslogilablimsorderdetailRepository.updateRepeat(false,autoOrderBatchcode);
			insertedBatchcode = insertOrder(objorderindex1);

		}

		if (autoregistercount != null && autoregistercount > 0) {
			autoregistercount = autoregistercount - 1;
			List<LsAutoregister> listauto = new ArrayList<LsAutoregister>();

			if (autoorder.get(0).getBatchcode().equals(autoOrderBatchcode)) {

				Date currentdate = commonfunction.getCurrentUtcTime();
				if (autoorder.get(0).getDelayinminutes() != null) {
					Instant now = Instant.now();
					Instant futureInstant = now.plus(Duration.ofMillis(autoorder.get(0).getDelayinminutes()));
					Date futureDate = Date.from(futureInstant);
					autoorder.get(0).setAutocreatedate(futureDate);
				} else {
					if (autoorder.get(0).getTimespan().equals("Days")) {
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(currentdate);
						calendar.add(Calendar.DAY_OF_MONTH, autoorder.get(0).getInterval());

						Date futureDate = calendar.getTime();
						autoorder.get(0).setAutocreatedate(futureDate);
					} else if (autoorder.get(0).getTimespan().equals("Week")) {

						Calendar calendar = Calendar.getInstance();
						calendar.setTime(currentdate);
						calendar.add(Calendar.DAY_OF_MONTH, (autoorder.get(0).getInterval() * 7));

						Date futureDate = calendar.getTime();
						autoorder.get(0).setAutocreatedate(futureDate);
					} else {
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(currentdate);
						calendar.add(Calendar.MINUTE, (10));
						Date futureDate = calendar.getTime();
						autoorder.get(0).setAutocreatedate(futureDate);
					}

				}
				autoorder.get(0).setRegcode(null);
				autoorder.get(0).setBatchcode(null);
				autoorder.get(0).setStoptime(null);
				autoorder.get(0).setScreen("Sheet_Order");
				autoorder.get(0).setSequenceFormat(oldorder.get(0).getLsautoregisterorders().getSequenceFormat());
//				if (autoregistercount == 0) {
				autoorder.get(0).setIsautoreg(false);
//				} else {
//					autoorder.get(0).setIsautoreg(true);
//				}

				listauto.add(autoorder.get(0));
				objorderindex1.setSequenceid(oldorder.get(0).getLsautoregisterorders().getSequenceFormat());
				objorderindex1.setLsautoregisterorders(listauto.get(0));

			}

			listauto.get(0).setTimerIdname(timerId1);
			lsautoregisterrepo.saveAll(listauto);
			timerStatusMap.put(timerId1, true);

			SequenceTableProjectLevel objprojectseq = new SequenceTableProjectLevel();
			SequenceTableTaskLevel objtaskseq = new SequenceTableTaskLevel();
			SequenceTable seqorder = validateandupdatesheetordersequencenumber(objorderindex1, objprojectseq,
					objtaskseq);
			boolean isrest = false;
			seqorder = commonfunction.ResetSequence(seqorder, isrest);

			objorderindex1.setLsworkflow(lsworkflowRepository.findTopByAndLssitemasterAndStatusOrderByWorkflowcodeAsc(
					objorderindex1.getLsuserMaster().getLssitemaster(), 1));

			objorderindex1.setOrderflag("N");

			String Content = "";
			String defaultContent = "{\"activeSheet\":\"Sheet1\",\"sheets\":[{\"name\":\"Sheet1\",\"rows\":[],\"columns\":[],\"selection\":\"A1:A1\",\"activeCell\":\"A1:A1\",\"frozenRows\":0,\"frozenColumns\":0,\"showGridLines\":true,\"gridLinesColor\":null,\"mergedCells\":[],\"hyperlinks\":[],\"defaultCellStyle\":{\"fontFamily\":\"Arial\",\"fontSize\":\"12\"},\"drawings\":[]}],\"names\":[],\"columnWidth\":64,\"rowHeight\":20,\"images\":[],\"charts\":[],\"tags\":[],\"fieldcount\":0,\"Batchcoordinates\":{\"resultdirection\":1,\"parameters\":[]}}";

			if (objorderindex1.getLsfile() != null) {
				if ((objorderindex1.getLsfile().getApproved() != null && objorderindex1.getLsfile().getApproved() == 1)
						|| (objorderindex1.getFiletype() == 5)) {
					if (Ismultitenant == 1 || Ismultitenant == 2) {

						CloudSheetCreation objCreation = cloudSheetCreationRepository
								.findTop1ById((long) objorderindex1.getLsfile().getFilecode());

						if (objCreation != null) {
							if (objCreation.getContainerstored() == 0) {
								Content = cloudSheetCreationRepository
										.findTop1ById((long) objorderindex1.getLsfile().getFilecode()).getContent();
							} else {
								try {
									Content = objCloudFileManipulationservice.retrieveCloudSheets(
											objCreation.getFileuid(), commonfunction.getcontainername(Ismultitenant,
													TenantContext.getCurrentTenant()) + "sheetcreation");
								} catch (IOException e) {

									e.printStackTrace();
								}
							}
						} else {
							Content = objorderindex1.getLsfile().getFilecontent();
						}
					} else {

						GridFSFile largefile = gridFsTemplate.findOne(new Query(
								Criteria.where("filename").is("file_" + objorderindex1.getLsfile().getFilecode())));
						if (largefile == null) {
							largefile = gridFsTemplate.findOne(new Query(
									Criteria.where("_id").is("file_" + objorderindex1.getLsfile().getFilecode())));
						}

						if (largefile != null) {
							GridFsResource resource = gridFsTemplate.getResource(largefile.getFilename());
							Content = new BufferedReader(
									new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)).lines()
									.collect(Collectors.joining("\n"));
						} else {
							if (mongoTemplate.findById(objorderindex1.getLsfile().getFilecode(),
									SheetCreation.class) != null) {
								Content = mongoTemplate
										.findById(objorderindex1.getLsfile().getFilecode(), SheetCreation.class)
										.getContent();
							} else {
								Content = objorderindex1.getLsfile().getFilecontent();
							}
						}

					}
				} else {
					if (objorderindex1.getFiletype() != 4 && objorderindex1.getLsfile().getFilecode() != 1) {
						Integer lastapprovedvesrion = objorderindex1.getLsfile().getVersionno() > 1
								? (objorderindex1.getLsfile().getVersionno() - 1)
								: objorderindex1.getLsfile().getVersionno();
						objorderindex1.getLsfile().setVersionno(lastapprovedvesrion);
						objorderindex1.getLsfile().setIsmultitenant(Ismultitenant);
						objorderindex1.getLsfile().setIsmultitenant(Ismultitenant);
						try {
							Content = fileService.GetfileverContent(objorderindex1.getLsfile());
						} catch (IOException e) {

							e.printStackTrace();
						}
					}
				}

			}

			if (Content == null || Content.equals("") || (objorderindex1.getLsfile() != null && objorderindex1.getLsfile().getFilecode()  ==1 )) {
				Content = defaultContent;
			}
			LSsamplefileversion sampversion = lssamplefileversionRepository
					.findByBatchcode(objorderindex1.getBatchcode());
			objorderindex1.getLssamplefile().setFilesamplecode(null);
			objorderindex1.getLssamplefile().setBatchcode(0);

			if (sampversion != null) {
				sampversion.setBatchcode(0);
				sampversion.setFilesamplecode(null);

				String Contentversion = Content;
				lssamplefileversionRepository.save(sampversion);
				try {
					updateorderversioncontent(Contentversion, sampversion, Ismultitenant);
				} catch (IOException e) {

					e.printStackTrace();
				}

				Contentversion = null;
			} else {
				LSsamplefileversion sampversion1 = new LSsamplefileversion();
				sampversion1.setCreatebyuser(objorderindex1.getLsuserMaster());
				sampversion1.setCreateby(objorderindex1.getLsuserMaster().getUsercode());
				sampversion1.setCreatedate(commonfunction.getCurrentUtcTime());
				sampversion1.setVersionname("version_1");
				sampversion1.setVersionno(1);
				lssamplefileversionRepository.save(sampversion1);

				List<LSsamplefileversion> samplefileVersions = new ArrayList<LSsamplefileversion>();
				samplefileVersions.add(sampversion1);

				LSsamplefile samplefile = new LSsamplefile();
				samplefile.setCreatebyuser(objorderindex1.getLsuserMaster());
				samplefile.setLssamplefileversion(samplefileVersions);
//				samplefile.setCreatedate(commonfunction.getCurrentUtcTime());
				samplefile.setVersionno(1);

				objorderindex1.setLssamplefile(samplefile);
			}
			try {
				objorderindex1.getLssamplefile().setCreatedate(commonfunction.getCurrentUtcTime());
				if (sampversion != null) {
					sampversion.setCreatedate(commonfunction.getCurrentUtcTime());
				}
			} catch (ParseException e1) {

				e1.printStackTrace();
			}
			lssamplefileRepository.save(objorderindex1.getLssamplefile());

			try {
				objorderindex1.setCreatedtimestamp(commonfunction.getCurrentUtcTime());
			} catch (ParseException e) {

				e.printStackTrace();
			}

//			objorderindex1.setBatchcode(null);
			objorderindex1.setBatchcode(insertedBatchcode);
			objorderindex1.setBatchid(null);

			SequenceTable sqa = sequencetableRepository.findBySequencecodeOrderBySequencecode(1);
			String seqid = GetAutoSequences(objorderindex1, sqa, objprojectseq, objtaskseq);

//			lslogilablimsorderdetailRepository.save(objorderindex1);

			updatesequence(1, objorderindex1);

			if (objorderindex1.getLsautoregisterorders() != null) {
				objorderindex1.getLsautoregisterorders().setBatchcode(objorderindex1.getBatchcode());
				if (autoregistercount == 0) {
					autoorder.get(0).setIsautoreg(false);
				} else {
					autoorder.get(0).setIsautoreg(true);
				}
				lsautoregisterrepo.save(objorderindex1.getLsautoregisterorders());
			}

			String Batchid = "ELN" + objorderindex1.getBatchcode();
			if (objorderindex1.getFiletype() == 3) {
				Batchid = "RESEARCH" + objorderindex1.getBatchcode();
			} else if (objorderindex1.getFiletype() == 4) {
				Batchid = "EXCEL" + objorderindex1.getBatchcode();
			} else if (objorderindex1.getFiletype() == 5) {
				Batchid = "VALIDATE" + objorderindex1.getBatchcode();
			}
//			lslogilablimsorderdetailRepository.setbatchidBybatchcode(Batchid, objorderindex1.getBatchcode());
			objorderindex1.setBatchid(Batchid);
			objorderindex1.setRepeat(autoregistercount == 0 ? false : true);
			objorderindex1.setAutoregistercount(autoregistercount);
			seqid = sqa.getSequenceview().equals(1) ? Batchid : seqid;
			lslogilablimsorderdetailRepository.sevaluesBybatchcode(seqid, Batchid,
					autoregistercount == 0 ? false : true, autoregistercount, objorderindex1.getBatchcode(),
					objorderindex1.getLssamplefile().getFilesamplecode());
			List<LSlogilablimsorder> lsorder = new ArrayList<LSlogilablimsorder>();
			String Limsorder = objorderindex1.getBatchcode().toString();

			if (objorderindex1.getLsfile() != null) {
				objorderindex1.getLsfile().setLsmethods(LSfilemethodRepository
						.findByFilecodeOrderByFilemethodcode(objorderindex1.getLsfile().getFilecode()));
				if (objorderindex1.getLsfile().getLsmethods() != null
						&& objorderindex1.getLsfile().getLsmethods().size() > 0) {
					int methodindex = 0;
					for (LSfilemethod objmethod : objorderindex1.getLsfile().getLsmethods()) {
						LSlogilablimsorder objLimsOrder = new LSlogilablimsorder();
						String order = "";
						if (methodindex < 10) {
							order = Limsorder.concat("0" + methodindex);
						} else {
							order = Limsorder.concat("" + methodindex);
						}
						objLimsOrder.setOrderid(Long.parseLong(order));
						objLimsOrder.setBatchid(objorderindex1.getBatchid());
						objLimsOrder.setMethodcode(objmethod.getMethodid());
						objLimsOrder.setInstrumentcode(objmethod.getInstrumentid());
						objLimsOrder.setTestcode(
								objorderindex1.getTestcode() != null ? objorderindex1.getTestcode().toString() : null);
						objLimsOrder.setOrderflag("N");
						objLimsOrder.setCreatedtimestamp(objorderindex1.getCreatedtimestamp());

						lsorder.add(objLimsOrder);

						methodindex++;
					}

					lslogilablimsorderRepository.saveAll(lsorder);
				} else {

					LSlogilablimsorder objLimsOrder = new LSlogilablimsorder();
					if (LSfilemethodRepository.findByFilecode(objorderindex1.getLsfile().getFilecode()) != null) {
						objLimsOrder.setMethodcode(LSfilemethodRepository
								.findByFilecode(objorderindex1.getLsfile().getFilecode()).getMethodid());
						objLimsOrder.setInstrumentcode(LSfilemethodRepository
								.findByFilecode(objorderindex1.getLsfile().getFilecode()).getInstrumentid());
					}
					objLimsOrder.setOrderid(Long.parseLong(Limsorder.concat("00")));
					objLimsOrder.setBatchid(objorderindex1.getBatchid());
					objLimsOrder.setTestcode(
							objorderindex1.getTestcode() != null ? objorderindex1.getTestcode().toString() : null);
					objLimsOrder.setOrderflag("N");
					objLimsOrder.setCreatedtimestamp(objorderindex1.getCreatedtimestamp());

					lslogilablimsorderRepository.save(objLimsOrder);
					lsorder.add(objLimsOrder);

				}
			}

			lssamplefileRepository.setbatchcodeOnsamplefile(objorderindex1.getBatchcode(),
					objorderindex1.getLssamplefile().getFilesamplecode());

			if (objorderindex1.getIsDefault() != null && objorderindex1.getIsDefault()) {
				objorderindex1.setSequenceid(objorderindex1.getBatchid());
			}

			final List<LSOrdernotification> ordernotList = new ArrayList<>(1);
			if (objorderindex1.getCautiondate() != null && objorderindex1.getDuedate() != null) {
				LSOrdernotification notobj = new LSOrdernotification();

				notobj.setBatchcode(objorderindex1.getBatchcode());
				notobj.setBatchid(objorderindex1.getBatchid());
				notobj.setCautiondate(objorderindex1.getCautiondate());
				notobj.setCreatedtimestamp(objorderindex1.getCreatedtimestamp());
				notobj.setDuedate(objorderindex1.getDuedate());
				notobj.setPeriod(objorderindex1.getPeriod());
				notobj.setUsercode(objorderindex1.getLsuserMaster().getUsercode());
				notobj.setCautionstatus(1);
				notobj.setDuestatus(1);
				notobj.setOverduestatus(1);
				notobj.setScreen("sheetorder");
				// lsordernotificationrepo.save(notobj);
				ordernotList.add(lsordernotificationrepo.save(notobj));
				if (ordernotList.size() > 0) {
					objorderindex1.setLsordernotification(ordernotList.get(0));
				}
			}
//			objorderindex1.setOrdercancell(null);
//			lslogilablimsorderdetailRepository.save(objorderindex1);

			if (objorderindex1.getLssamplefile() != null) {
				try {
					updateordercontent(Content, objorderindex1.getLssamplefile(), Ismultitenant);
				} catch (IOException e) {

					e.printStackTrace();
				}
			}

			objorderindex1.setLstworkflow(objorderindex1.getLstworkflow());

			Content = null;
			defaultContent = null;
			Batchid = null;
			Limsorder = null;
			lsorder = null;
			updatenotificationfororder(objorderindex1);

			// return objorder1;
			LScfttransaction auditobj = new LScfttransaction();
			auditobj.setLsuserMaster(objorderindex1.getLsuserMaster().getUsercode());
			try {
				auditobj.setTransactiondate(commonfunction.getCurrentUtcTime());
			} catch (ParseException e) {

				e.printStackTrace();
			}
			auditobj.setModuleName("IDS_SCN_SHEETORDERS");
			auditobj.setActions("IDS_TSK_REGISTERED");
			auditobj.setManipulatetype("IDS_AUDIT_INSERTORDERS");
			auditobj.setComments("order: " + objorderindex1.getBatchid() + " is now autoregistered");
			auditobj.setLssitemaster(objorderindex1.getLsuserMaster().getLssitemaster().getSitecode());
			auditobj.setSystemcoments("Audittrail.Audittrailhistory.Audittype.IDS_AUDIT_SYSTEMGENERATED");
			lscfttransactionRepository.save(auditobj);

		} else {
			String timerId = autoorder.get(0).getTimerIdname();
			if (timerId != null) {
				stopTimer(timerId);
			}
		}

		return objorderindex1;
	}

	@Autowired
	private PlatformTransactionManager transactionManager;

	@Transactional
	public void updateOrderDetailSequence(Long nextValue) {
	    String sql = "ALTER SEQUENCE orderDetail_seq RESTART WITH " + nextValue;
	    entityManager.createNativeQuery(sql).executeUpdate();
	}

	@Transactional
	public Long insertOrder(LSlogilablimsorderdetail objorder1) throws SQLException, ParseException {
		Date currentdate = commonfunction.getCurrentUtcTime();
		TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
		LSlogilablimsorderdetail objbatchcode = lslogilablimsorderdetailRepository.findTopByOrderByBatchcodeDesc();
		return transactionTemplate.execute(status -> {
			
			String url = env.getProperty("app.datasource.eln.driver-class-name");
			String sql ="";
		   if(url != null && url.contains("SQLServerDriver")) {
			    sql = "INSERT INTO lslogilablimsorderdetail ("
						+ "repeat,filetype,lsworkflow_workflowcode,lsusermaster_usercode,"
						+ "lsfile_filecode,lsprojectmaster_projectcode,lssamplefile_filesamplecode,"
						+ "filecode,keyword,lockedusername,directorycode,orderdisplaytype,"
						+ "lstestmasterlocal_testcode,viewoption,ordercancell,teamcode,createdtimestamp,orderflag,"
						+ "lsautoregisterorders_regcode,testcode,testname,"
						+ "elnmaterialinventory_nmaterialinventorycode,elnmaterial_nmaterialcode,autoregistercount,"
						+ "sequenceid,applicationsequence,sitesequence,projectsequence,tasksequence,"
						+ "ordertypesequence,projectlevelsequence,tasklevelsequence,sitecode,assignedto_usercode,sheetversionno,batchcode,isclone) "
						+ "OUTPUT INSERTED.batchcode "
						+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";						

		   }else {
			    sql = "INSERT INTO lslogilablimsorderdetail ("
						+ "repeat,filetype,lsworkflow_workflowcode,lsusermaster_usercode,"
						+ "lsfile_filecode,lsprojectmaster_projectcode,lssamplefile_filesamplecode,"
						+ "filecode,keyword,lockedusername,directorycode,orderdisplaytype,"
						+ "lstestmasterlocal_testcode,viewoption,ordercancell,teamcode,createdtimestamp,orderflag,"
						+ "lsautoregisterorders_regcode,testcode,testname,"
						+ "elnmaterialinventory_nmaterialinventorycode,elnmaterial_nmaterialcode,autoregistercount,"
						+ "sequenceid,applicationsequence,sitesequence,projectsequence,tasksequence,"
						+ "ordertypesequence,projectlevelsequence,tasklevelsequence,sitecode,assignedto_usercode,sheetversionno,batchcode,isclone) "
						+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) "
						+ "RETURNING batchcode"; 
		   }

			

			jakarta.persistence.Query query = entityManager.createNativeQuery(sql);

			if (objorder1.getAutoregistercount() == 0) {
				query.setParameter(1, false);
			} else {
				query.setParameter(1, true);
			}
			query.setParameter(2, objorder1.getFiletype());
			query.setParameter(3, objorder1.getLsworkflow() != null ? objorder1.getLsworkflow().getWorkflowcode() : null);
			query.setParameter(4, objorder1.getLsuserMaster().getUsercode());
			query.setParameter(5, objorder1.getLsfile().getFilecode());
			query.setParameter(6,
					objorder1.getLsprojectmaster() != null ? objorder1.getLsprojectmaster().getProjectcode() : null);
			query.setParameter(7, objorder1.getLssamplefile().getFilesamplecode());
			query.setParameter(8, objorder1.getLsfile().getFilecode());
			query.setParameter(9, objorder1.getKeyword());
			query.setParameter(10, objorder1.getLockedusername());
			query.setParameter(11, objorder1.getDirectorycode());
			query.setParameter(12, objorder1.getOrderdisplaytype());
			query.setParameter(13,
					objorder1.getLstestmasterlocal() != null ? objorder1.getLstestmasterlocal().getTestcode() : null);
			query.setParameter(14, objorder1.getViewoption());
			query.setParameter(15, null);
			if (objorder1.getTeamcode() != null) {
				query.setParameter(16, objorder1.getTeamcode());
			} else {
				query.setParameter(16, null);
			}
			query.setParameter(17, new Timestamp(currentdate.getTime()));
			query.setParameter(18, "N");
			query.setParameter(19, objorder1.getLsautoregisterorders().getRegcode());
			query.setParameter(20, objorder1.getTestcode());
			query.setParameter(21, objorder1.getTestname());
			query.setParameter(22, null);
			if (objorder1.getElnmaterial() != null) {
				query.setParameter(23, objorder1.getElnmaterial().getNmaterialcode());
			} else {
				query.setParameter(23, null);
			}
			query.setParameter(24, objorder1.getAutoregistercount());
			query.setParameter(25, objorder1.getSequenceid());
			query.setParameter(26, objorder1.getApplicationsequence());
			query.setParameter(27, objorder1.getSitesequence());
			query.setParameter(28, objorder1.getProjectsequence());
			query.setParameter(29, objorder1.getTasksequence());
			query.setParameter(30, objorder1.getOrdertypesequence());
			query.setParameter(31, objorder1.getProjectlevelsequence());
			query.setParameter(32, objorder1.getTasklevelsequence());
			query.setParameter(33, objorder1.getSitecode());

			if (objorder1.getAssignedto() != null && objorder1.getAssignedto().getUsercode() != null
					&& objorder1.getAssignedto().getUsercode().intValue() != 0) {
				query.setParameter(34, objorder1.getAssignedto().getUsercode());
			} else {
				query.setParameter(34, null);
			}
			query.setParameter(35, objorder1.getSheetversionno());
			 if(url != null && url.contains("SQLServerDriver")) {
				 query.setParameter(37,0); 
			 }else {
				 query.setParameter(37,false);  
			 }			 
			query.setParameter(36, objbatchcode != null ? objbatchcode.getBatchcode() + 1 : 1000000);		

			Object batchcodeObj = query.getSingleResult();
			Long batchcode = ((Number) batchcodeObj).longValue();
			objorder1.setBatchcode(batchcode);
//			 if(url != null && url.contains("SQLServerDriver")) {
			//for update sequence latest 
			this.updateOrderDetailSequence(batchcode + 1);
//			 }
			return batchcode;
		});

	}

	public void stopTimer(String timerId) {
		Timer timer = timerMap.get(timerId);
		if (timer != null) {
			timer.cancel();
			timer.purge();
			timerMap.remove(timerId);
			timerStatusMap.remove(timerId);
			orderDetailMap.remove(timerId);
			System.out.println("Timer " + timerId + " stopped.");
		} else {
			System.out.println("No timer found with ID " + timerId);
		}
	}

	public SequenceTable validateandupdatesheetordersequencenumber(LSlogilablimsorderdetail objorder,
			SequenceTableProjectLevel objprojectseq, SequenceTableTaskLevel objtaskseq) throws ParseException

	{
		SequenceTable seqorder = new SequenceTable();
		int sequence = 1;
		seqorder = sequencetableRepository.findBySequencecode(sequence);

		if (seqorder != null && seqorder.getApplicationsequence() == -1) {
			long appcount = lslogilablimsorderdetailRepository.count();
			sequencetableRepository.setinitialapplicationsequence(appcount, sequence);
			seqorder.setApplicationsequence(appcount);
		}
		Date currentdate = commonfunction.getCurrentUtcTime();
		SimpleDateFormat day = new SimpleDateFormat("dd");
		SimpleDateFormat month = new SimpleDateFormat("mm");
		SimpleDateFormat year = new SimpleDateFormat("yyyy");
		if (seqorder.getSequenceday() == 0) {
			seqorder.setSequenceday(Integer.parseInt(day.format(currentdate)));
		}

		if (seqorder.getSequencemonth() == 0) {
			seqorder.setSequencemonth(Integer.parseInt(month.format(currentdate)));
		}

		if (seqorder.getSequenceyear() == 0) {
			seqorder.setSequenceyear(Integer.parseInt(year.format(currentdate)));
		}

//		if(sequencetablesiteRepository.findBySequencecodeAndSitecode(sequence,objorder.getLsuserMaster().getLssitemaster().getSitecode()) == null)
		if (sequencetablesiteRepository.findBySequencecodeAndSitecode(sequence, objorder.getSitecode()) == null) {
			SequenceTableSite objsiteseq = new SequenceTableSite();
			objsiteseq.setSequencecode(sequence);
			objsiteseq.setSitecode(objorder.getSitecode());

			LSSiteMaster site = new LSSiteMaster(objorder.getSitecode());

			List<LSuserMaster> lstuser = lsuserMasterRepository.findByLssitemasterOrderByCreateddateDesc(site);
			if (lstuser != null) {
				// objsiteseq.setSitesequence(logilablimsorderdetailsRepository.countByLsuserMasterIn(lstuser));
				objsiteseq.setSitesequence(logilablimsorderdetailsRepository.countBySitecode(objorder.getSitecode()));
			} else {
				objsiteseq.setSitesequence((long) 0);
			}
			sequencetablesiteRepository.save(objsiteseq);

			if (seqorder.getSequencetablesite() != null) {
				seqorder.getSequencetablesite().add(objsiteseq);
			} else {
				List<SequenceTableSite> lstseq = new ArrayList<SequenceTableSite>();
				lstseq.add(objsiteseq);
				seqorder.setSequencetablesite(lstseq);
			}
		}

		if (objorder.getLsprojectmaster() != null && sequencetableprojectRepository
				.findBySequencecodeAndProjectcode(sequence, objorder.getLsprojectmaster().getProjectcode()) == null) {
			SequenceTableProject objsiteseq = new SequenceTableProject();
			objsiteseq.setSequencecode(sequence);
			objsiteseq.setProjectcode(objorder.getLsprojectmaster().getProjectcode());

			if (objorder.getLsprojectmaster() != null) {
				objsiteseq.setProjectsequence(
						logilablimsorderdetailsRepository.countByLsprojectmaster(objorder.getLsprojectmaster()));
			} else {
				objsiteseq.setProjectsequence((long) 0);
			}
			sequencetableprojectRepository.save(objsiteseq);

			if (seqorder.getSequencetableproject() != null) {
				seqorder.getSequencetableproject().add(objsiteseq);
			} else {
				List<SequenceTableProject> lstseq = new ArrayList<SequenceTableProject>();
				lstseq.add(objsiteseq);
				seqorder.setSequencetableproject(lstseq);
			}
		}

		if (objorder.getLsprojectmaster() != null && sequencetableprojectlevelrepository
				.findByProjectcode(objorder.getLsprojectmaster().getProjectcode()) == null) {
			SequenceTableProjectLevel objprolevel = new SequenceTableProjectLevel();
			objprolevel.setProjectcode(objorder.getLsprojectmaster().getProjectcode());
			long projectseq = 0;

			projectseq = projectseq
					+ logilablimsorderdetailsRepository.countByLsprojectmaster(objorder.getLsprojectmaster());

			projectseq = projectseq
					+ LSlogilabprotocoldetailRepository.countByLsprojectmaster(objorder.getLsprojectmaster());

			objprolevel.setProjectsequence(projectseq);

			objprojectseq = sequencetableprojectlevelrepository.save(objprolevel);
		}

		if (objorder.getLstestmasterlocal() != null && sequencetabletasklevelrepository
				.findByTestcode(objorder.getLstestmasterlocal().getTestcode()) == null) {
			SequenceTableTaskLevel objtsklevel = new SequenceTableTaskLevel();
			objtsklevel.setTestcode(objorder.getLstestmasterlocal().getTestcode());
			long taskseq = 0;

			taskseq = taskseq
					+ logilablimsorderdetailsRepository.countByLstestmasterlocal(objorder.getLstestmasterlocal());

			taskseq = taskseq
					+ LSlogilabprotocoldetailRepository.countByLstestmasterlocal(objorder.getLstestmasterlocal());

			objtsklevel.setTasksequence(taskseq);

			objtaskseq = sequencetabletasklevelrepository.save(objtsklevel);
		}

		if (objorder.getLstestmasterlocal() != null && sequencetabletaskRepository
				.findBySequencecodeAndTestcode(sequence, objorder.getLstestmasterlocal().getTestcode()) == null) {
			SequenceTableTask objsiteseq = new SequenceTableTask();
			objsiteseq.setSequencecode(sequence);
			objsiteseq.setTestcode(objorder.getLstestmasterlocal().getTestcode());
			if (objorder.getLstestmasterlocal() != null) {
				objsiteseq.setTasksequence(
						logilablimsorderdetailsRepository.countByLstestmasterlocal(objorder.getLstestmasterlocal()));
			} else {
				objsiteseq.setTasksequence((long) 0);
			}
			sequencetabletaskRepository.save(objsiteseq);

			if (seqorder.getSequencesabletask() != null) {
				seqorder.getSequencesabletask().add(objsiteseq);
			} else {
				List<SequenceTableTask> lstseq = new ArrayList<SequenceTableTask>();
				lstseq.add(objsiteseq);
				seqorder.setSequencesabletask(lstseq);
			}
		}

//		if(objorder.getFiletype() != null && sequencetableordertyperepository.findBySequencecodeAndOrdertype(sequence,objorder.getFiletype()) == null)
//		{
//			SequenceTableOrderType objordertype = new SequenceTableOrderType();
//			objordertype.setSequencecode(sequence);
//			objordertype.setOrdertype(objorder.getFiletype());
//			if(objorder.getFiletype() != null)
//			{
//				objordertype.setOrdertypesequence(logilablimsorderdetailsRepository.countByFiletype(objorder.getFiletype()));
//			}
//			else
//			{
//				objordertype.setOrdertypesequence((long)0);
//			}
//			sequencetableordertyperepository.save(objordertype);
//			
//			if(seqorder.getSequencetableordertype() !=null)
//			{
//				seqorder.getSequencetableordertype().add(objordertype);
//			}
//			else
//			{
//				List<SequenceTableOrderType> lstseq= new ArrayList<SequenceTableOrderType>();
//				lstseq.add(objordertype);
//				seqorder.setSequencetableordertype(lstseq);
//			}
//		}

		return seqorder;
	}

	@SuppressWarnings("resource")
	public LSlogilablimsorderdetail InsertELNOrder(LSlogilablimsorderdetail objorder)
			throws IOException, ParseException {
		SequenceTableProjectLevel objprojectseq = new SequenceTableProjectLevel();
		SequenceTableTaskLevel objtaskseq = new SequenceTableTaskLevel();
		SequenceTable seqorder = validateandupdatesheetordersequencenumber(objorder, objprojectseq, objtaskseq);
		boolean isrest = false;
		seqorder = commonfunction.ResetSequence(seqorder, isrest);

		objorder.setLsworkflow(lsworkflowRepository.findTopByAndLssitemasterAndStatusOrderByWorkflowcodeAsc(
				objorder.getLsuserMaster().getLssitemaster(), 1));

		objorder.setOrderflag("N");

		String Content = "";
		String Sequenceformat = objorder.getSequenceid();

//		String defaultContent = "{\"activeSheet\":\"Sheet1\",\"sheets\":[{\"name\":\"Sheet1\",\"rows\":[],\"columns\":[],\"selection\":\"A1:A1\",\"activeCell\":\"A1:A1\",\"frozenRows\":0,\"frozenColumns\":0,\"showGridLines\":true,\"gridLinesColor\":null,\"mergedCells\":[],\"hyperlinks\":[],\"defaultCellStyle\":{\"fontFamily\":\"Arial\",\"fontSize\":\"12\"},\"drawings\":[]}],\"names\":[],\"columnWidth\":64,\"rowHeight\":20,\"images\":[],\"charts\":[],\"tags\":[],\"fieldcount\":0,\"Batchcoordinates\":{\"resultdirection\":1,\"parameters\":[]}}";

		String defaultContent = "{\"activeSheet\":\"Sheet1\",\"sheets\":[{\"name\":\"Sheet1\",\"rows\":[],\"columns\":[],\"selection\":\"A1:A1\",\"activeCell\":\"A1:A1\",\"frozenRows\":0,\"frozenColumns\":0,\"showGridLines\":true,\"gridLinesColor\":null,\"mergedCells\":[],\"hyperlinks\":[],\"defaultCellStyle\":{\"fontFamily\":\"Arial\",\"fontSize\":\"12\"},\"drawings\":[]},{\"name\":\"Chart\",\"rows\":[],\"columns\":[],\"selection\":\"A1:A1\",\"activeCell\":\"A1:A1\",\"frozenRows\":0,\"frozenColumns\":0,\"showGridLines\":true,\"gridLinesColor\":null,\"mergedCells\":[],\"hyperlinks\":[],\"defaultCellStyle\":{\"fontFamily\":\"Arial\",\"fontSize\":\"12\"},\"drawings\":[]}],\"names\":[],\"columnWidth\":64,\"rowHeight\":20,\"images\":[],\"charts\":[],\"tags\":[]}";

		if (objorder.getLsfile() != null) {
			if (objorder.getIsclone()) {
				Content = objorder.getLssamplefile().getFilecontent();
			} else {
				if ((objorder.getLsfile().getApproved() != null && objorder.getLsfile().getApproved() == 1)
						|| (objorder.getFiletype() == 5)) {
					if (objorder.getIsmultitenant() == 1 || objorder.getIsmultitenant() == 2) {

						CloudSheetCreation objCreation = cloudSheetCreationRepository
								.findTop1ById((long) objorder.getLsfile().getFilecode());

						if (objCreation != null) {
							if (objCreation.getContainerstored() == 0) {
								Content = cloudSheetCreationRepository
										.findTop1ById((long) objorder.getLsfile().getFilecode()).getContent();
							} else {
								Content = objCloudFileManipulationservice.retrieveCloudSheets(objCreation.getFileuid(),
										commonfunction.getcontainername(objorder.getIsmultitenant(),
												TenantContext.getCurrentTenant()) + "sheetcreation");
							}
						} else {
							Content = objorder.getLsfile().getFilecontent();
						}
					} else {

						GridFSFile largefile = gridFsTemplate.findOne(
								new Query(Criteria.where("filename").is("file_" + objorder.getLsfile().getFilecode())));
						if (largefile == null) {
							largefile = gridFsTemplate.findOne(
									new Query(Criteria.where("_id").is("file_" + objorder.getLsfile().getFilecode())));
						}

						if (largefile != null) {
							GridFsResource resource = gridFsTemplate.getResource(largefile.getFilename());
							Content = new BufferedReader(
									new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)).lines()
									.collect(Collectors.joining("\n"));
						} else {
							if (mongoTemplate.findById(objorder.getLsfile().getFilecode(),
									SheetCreation.class) != null) {
								Content = mongoTemplate
										.findById(objorder.getLsfile().getFilecode(), SheetCreation.class).getContent();
							} else {
								Content = objorder.getLsfile().getFilecontent();
							}
						}

					}
				} else {
					if (objorder.getFiletype() != 4 && objorder.getLsfile().getFilecode() != 1) {
						Integer lastapprovedvesrion = objorder.getLsfile().getVersionno() > 1
								? (objorder.getLsfile().getVersionno() - 1)
								: objorder.getLsfile().getVersionno();
						objorder.getLsfile().setVersionno(lastapprovedvesrion);
						objorder.getLsfile().setIsmultitenant(objorder.getIsmultitenant());
						objorder.getLsfile().setIsmultitenant(objorder.getIsmultitenant());
						Content = fileService.GetfileverContent(objorder.getLsfile());
					}
				}
			}

		}

		if (Content == null || Content.equals("")) {
			Content = defaultContent;
		}

		if (objorder.getLssamplefile().getLssamplefileversion() != null) {

			String Contentversion = Content;
			lssamplefileversionRepository.saveAll(objorder.getLssamplefile().getLssamplefileversion());
			updateorderversioncontent(Contentversion, objorder.getLssamplefile().getLssamplefileversion().get(0),
					objorder.getIsmultitenant());

			Contentversion = null;
		}
		try {
			objorder.getLssamplefile().setCreatedate(commonfunction.getCurrentUtcTime());
			if (objorder.getLssamplefile().getLssamplefileversion().get(0) != null) {
				objorder.getLssamplefile().getLssamplefileversion().get(0)
						.setCreatedate(commonfunction.getCurrentUtcTime());
			}
		} catch (ParseException e1) {

			e1.printStackTrace();
		}
		lssamplefileRepository.save(objorder.getLssamplefile());
		try {
			objorder.setCreatedtimestamp(commonfunction.getCurrentUtcTime());
		} catch (ParseException e) {

			e.printStackTrace();
		}
		GetSequences(objorder, seqorder, objprojectseq, objtaskseq);

		if (!objorder.getLsselectedTeam().isEmpty()) {
			objorder.getLsselectedTeam().forEach(item -> {
				item.setDirectorycode(objorder.getDirectorycode());
				try {
					item.setCreatedtimestamp(commonfunction.getCurrentUtcTime());
				} catch (ParseException e) {

					e.printStackTrace();
				}
			});
			if (objorder.getElnmaterial() != null) {
				objorder.getLsselectedTeam().forEach(item -> item.setElnmaterial(objorder.getElnmaterial()));
			}

			LSSelectedTeamRepository.saveAll(objorder.getLsselectedTeam());
		}

		lslogilablimsorderdetailRepository.save(objorder);

		if (objorder.getDirectorycode() != null) {
			List<LSSheetOrderStructure> structureobj = lsSheetOrderStructureRepository
					.findByDirectorycode(objorder.getDirectorycode());
			if (!structureobj.isEmpty()) {
				structureobj.get(0).setDateModified(commonfunction.getCurrentUtcTime());
				structureobj.get(0).setModifiedby(objorder.getLsuserMaster());
			}
			lsSheetOrderStructureRepository.saveAll(structureobj);
			objorder.setLssheetOrderStructure(structureobj.get(0));
		}

		updatesequence(1, objorder);
		long milliseconds = 0;
		String timerId1 = generateUniqueTimerId();
		if (objorder.getRepeat() != null && objorder.getLsautoregisterorderdetail() != null && objorder.getRepeat()) {
			final List<LsAutoregister> autoordernotList = new ArrayList<>(1);

			Map<String, Object> RtnObject = commonfunction.getdelaymillisecondforautoregister(
					objorder.getLsautoregisterorderdetail().getTimespan(),
					objorder.getLsautoregisterorderdetail().getInterval());
			Date updatedDate = (Date) RtnObject.get("Date");
			milliseconds = (long) RtnObject.get("delay");
//			milliseconds = (long) 180000;
			// objorder.getLsautoregisterorderdetail().setAutocreatedate(objorder.getLsautoregisterorderdetail().getAutocreatedate());

			objorder.getLsautoregisterorderdetail().setAutocreatedate(updatedDate);
			objorder.getLsautoregisterorderdetail().setBatchcode(objorder.getBatchcode());
			objorder.getLsautoregisterorderdetail().setRepeat(true);
			objorder.getLsautoregisterorderdetail().setDelayinminutes(milliseconds);
			objorder.getLsautoregisterorderdetail().setTimerIdname(timerId1);
			objorder.getLsautoregisterorderdetail().setSequenceFormat(Sequenceformat);

//			if(objorder.getAutoregistercount()!=null && objorder.getAutoregistercount() >0) {
//				objorder.getLsautoregisterorderdetail().setIsautoreg(true);
//			}
			LsAutoregister lstauto = lsautoregisterrepo.save(objorder.getLsautoregisterorderdetail());
			autoordernotList.add(lstauto);
			objorder.setLsautoregisterorders(autoordernotList.get(0));
			lslogilablimsorderdetailRepository.save(objorder);
		}

		String Batchid = "ELN" + objorder.getBatchcode();
		if (objorder.getFiletype() == 3) {
			Batchid = "RESEARCH" + objorder.getBatchcode();
		} else if (objorder.getFiletype() == 4) {
			Batchid = "EXCEL" + objorder.getBatchcode();
		} else if (objorder.getFiletype() == 5) {
			Batchid = "VALIDATE" + objorder.getBatchcode();
		}
		lslogilablimsorderdetailRepository.setbatchidBybatchcode(Batchid, objorder.getBatchcode());
		objorder.setBatchid(Batchid);

		lslogilablimsorderdetailRepository.save(objorder);

		lssamplefileRepository.setbatchcodeOnsamplefile(objorder.getBatchcode(),
				objorder.getLssamplefile().getFilesamplecode());

		if (objorder.getIsDefault() && objorder.getIsDefault()) {
			objorder.setSequenceid(objorder.getBatchid());
		}

		List<LSlogilablimsorder> lsorder = new ArrayList<LSlogilablimsorder>();
		String Limsorder = objorder.getBatchcode().toString();

		if (objorder.getLsfile() != null) {
			List<LSfileelnmethod> filemethodlist = LSfileelnmethodRepository
					.findByFilecode(objorder.getLsfile().getFilecode());
			if (filemethodlist != null) {

				List<LSOrderElnMethod> lsorderelnmethodobj = new ArrayList<LSOrderElnMethod>();
				for (int i = 0; i < filemethodlist.size(); i++) {
					LSOrderElnMethod ordermethod = new LSOrderElnMethod();
					ordermethod.setBatchcode(objorder.getBatchcode());
					ordermethod.setBatchid(Batchid);
					ordermethod.setCreatedby(objorder.getLsuserMaster());
					ordermethod.setCreatedtimestamp(commonfunction.getCurrentUtcTime());
					ordermethod.setMethod(filemethodlist.get(i).getMethod());
					ordermethod.setEquipment(filemethodlist.get(i).getEquipment());
					ordermethod.setInstrument(filemethodlist.get(i).getInstrument());
					ordermethod.setSequenceid(objorder.getSequenceid());
					lsorderelnmethodobj.add(ordermethod);
				}
				LSOrderElnMethodRepository.saveAll(lsorderelnmethodobj);
			}
		}

		if (objorder.getLsfile() != null) {
			objorder.getLsfile().setLsmethods(
					LSfilemethodRepository.findByFilecodeOrderByFilemethodcode(objorder.getLsfile().getFilecode()));
			if (objorder.getLsfile().getLsmethods() != null && objorder.getLsfile().getLsmethods().size() > 0) {
				int methodindex = 0;
				for (LSfilemethod objmethod : objorder.getLsfile().getLsmethods()) {
					LSlogilablimsorder objLimsOrder = new LSlogilablimsorder();
					String order = "";
					if (methodindex < 10) {
						order = Limsorder.concat("0" + methodindex);
					} else {
						order = Limsorder.concat("" + methodindex);
					}
					objLimsOrder.setOrderid(Long.parseLong(order));
					objLimsOrder.setBatchid(objorder.getBatchid());
					objLimsOrder.setMethodcode(objmethod.getMethodid());
					objLimsOrder.setInstrumentcode(objmethod.getInstrumentid());
					objLimsOrder.setTestcode(objorder.getTestcode() != null ? objorder.getTestcode().toString() : null);
					objLimsOrder.setOrderflag("N");
					objLimsOrder.setCreatedtimestamp(objorder.getCreatedtimestamp());

					lsorder.add(objLimsOrder);

					methodindex++;
				}

				lslogilablimsorderRepository.saveAll(lsorder);
			} else {

				LSlogilablimsorder objLimsOrder = new LSlogilablimsorder();
				if (LSfilemethodRepository.findByFilecode(objorder.getLsfile().getFilecode()) != null) {
					objLimsOrder.setMethodcode(
							LSfilemethodRepository.findByFilecode(objorder.getLsfile().getFilecode()).getMethodid());
					objLimsOrder.setInstrumentcode(LSfilemethodRepository
							.findByFilecode(objorder.getLsfile().getFilecode()).getInstrumentid());
				}
				objLimsOrder.setOrderid(Long.parseLong(Limsorder.concat("00")));
				objLimsOrder.setBatchid(objorder.getBatchid());
				objLimsOrder.setTestcode(objorder.getTestcode() != null ? objorder.getTestcode().toString() : null);
				objLimsOrder.setOrderflag("N");
				objLimsOrder.setCreatedtimestamp(objorder.getCreatedtimestamp());

				lslogilablimsorderRepository.save(objLimsOrder);
				lsorder.add(objLimsOrder);

			}
		}

		final List<LSOrdernotification> ordernotList = new ArrayList<>(1);
		if (objorder.getCautiondate() != null && objorder.getDuedate() != null) {
			LSOrdernotification notobj = new LSOrdernotification();

			notobj.setBatchcode(objorder.getBatchcode());
			notobj.setBatchid(objorder.getBatchid());
			notobj.setCautiondate(objorder.getCautiondate());
			notobj.setCreatedtimestamp(objorder.getCreatedtimestamp());
			notobj.setDuedate(objorder.getDuedate());
			notobj.setPeriod(objorder.getPeriod());
			notobj.setUsercode(objorder.getLsuserMaster().getUsercode());
			notobj.setCautionstatus(1);
			notobj.setDuestatus(1);
			notobj.setOverduestatus(1);
			notobj.setScreen("sheetorder");
			notobj.setIscompleted(false);

			ordernotList.add(lsordernotificationrepo.save(notobj));
			if (ordernotList.size() > 0) {
				objorder.setLsordernotification(ordernotList.get(0));
				try {
					loginservice.ValidateNotification(ordernotList.get(0));
				} catch (ParseException e) {

					e.printStackTrace();
				}
			}
// comment below code due date issue new record insert 22/12/2025 by santhosh
			
//			Notification notify = new Notification();
//			notify.setBatchid(objorder.getBatchid());
//			notify.setOrderid(objorder.getBatchcode());
//			notify.setLsusermaster(objorder.getLsuserMaster());
//			notify.setAddedby(objorder.getLsuserMaster().getUsername());
//			notify.setUsercode(objorder.getLsuserMaster().getUsercode());
//			notify.setSitecode(objorder.getLsuserMaster().getLssitemaster().getSitecode());
//			notify.setScreen("sheetorder");
//			notify.setCurrentdate(commonfunction.getCurrentUtcTime());
//			notify.setCautiondate(objorder.getCautiondate());
//			notify.setDuedate(objorder.getDuedate());
//			notify.setAddedon(commonfunction.getCurrentUtcTime());
//			notify.setStatus(1);
//			if (objorder.getSitecode() != null) {
//				notify.setSitecode(objorder.getSitecode());
//			}
//
//			notificationRepository.save(notify);
		}
		lslogilablimsorderdetailRepository.save(objorder);

		if (objorder.getRepeat() != null && objorder.getLsautoregisterorderdetail() != null && objorder.getRepeat()) {
			try {
				scheduleAutoregduringregister(objorder, milliseconds, timerId1);
			} catch (ParseException e) {

				e.printStackTrace();
			}
		}

		if (objorder.getLssamplefile() != null) {
			updateordercontent(Content, objorder.getLssamplefile(), objorder.getIsmultitenant());
		}

		objorder.setLstworkflow(objorder.getLstworkflow());

		final LSlogilablimsorderdetail objLSlogilablimsorder = objorder;

		new Thread(() -> {
			try {
				System.out.println("inside the thread SDMS order call");
				createLogilabLIMSOrder4SDMS(objLSlogilablimsorder);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		Content = null;
		defaultContent = null;
		Batchid = null;
		Limsorder = null;
		lsorder = null;
		Sequenceformat = null;
		if (!objorder.getIsclone()) {
			updatenotificationfororder(objorder);
		}
		return objorder;
	}

	/**
	 * added for auto registration of orders . since lazy loading issue arises while
	 * using collection . so seperate findby is added for table like
	 * sequencetablesite , task and all
	 * 
	 * @return
	 */

	public String GetAutoSequences(LSlogilablimsorderdetail objorder, SequenceTable seqorder,
			SequenceTableProjectLevel objprojectseq, SequenceTableTaskLevel objtaskseq) throws ParseException {
		SequenceTable sqa = seqorder;

		if (sqa != null) {
			objorder.setApplicationsequence(sqa.getApplicationsequence() + 1);

			if (objorder != null && objorder.getSitecode() != null) {
//				SequenceTableSite sqsite = sqa.getSequencetablesite().stream()
//				        .filter(sq -> sq.getSitecode().equals(objorder.getLsuserMaster().getLssitemaster().getSitecode())
//				        && sq.getSequencecode().equals(sqa.getSequencecode())).findFirst().orElse(null);

				SequenceTableSite sqsite = sequencetablesiteRepository.findBySequencecodeAndSitecode(1,
						objorder.getSitecode());

				if (sqsite != null) {
					objorder.setSitesequence(sqsite.getSitesequence() + 1);
				}
			}

			if (objorder != null && objorder.getLsprojectmaster() != null
					&& objorder.getLsprojectmaster().getProjectcode() != null) {
//				SequenceTableProject sqproject = sqa.getSequencetableproject().stream()
//				        .filter(sq -> sq.getProjectcode().equals(objorder.getLsprojectmaster().getProjectcode())
//				        && sq.getSequencecode().equals(sqa.getSequencecode())).findFirst().orElse(null);

				SequenceTableProject sqproject = sequencetableprojectRepository.findBySequencecodeAndProjectcode(1,
						objorder.getLsprojectmaster().getProjectcode());

				if (sqproject != null) {
					objorder.setProjectsequence(sqproject.getProjectsequence() + 1);
				}

				if (objprojectseq == null || objprojectseq.getProjectcode() == null) {
					objprojectseq = sequencetableprojectlevelrepository
							.findByProjectcode(objorder.getLsprojectmaster().getProjectcode());
				}

				if (objprojectseq != null) {
					objorder.setProjectlevelsequence(objprojectseq.getProjectsequence() + 1);
				}

			}

			if (objorder != null && objorder.getLstestmasterlocal() != null
					&& objorder.getLstestmasterlocal().getTestcode() != null) {
//				SequenceTableTask sqtask = sqa.getSequencesabletask().stream()
//				        .filter(sq -> sq.getTestcode().equals(objorder.getLstestmasterlocal().getTestcode())
//				        && sq.getSequencecode().equals(sqa.getSequencecode())).findFirst().orElse(null);

				SequenceTableTask sqtask = sequencetabletaskRepository.findBySequencecodeAndTestcode(1,
						objorder.getLstestmasterlocal().getTestcode());

				if (sqtask != null) {
					objorder.setTasksequence(sqtask.getTasksequence() + 1);
				}

				if (objtaskseq == null || objtaskseq.getTestcode() == null) {
					objtaskseq = sequencetabletasklevelrepository
							.findByTestcode(objorder.getLstestmasterlocal().getTestcode());
				}

				if (objtaskseq != null) {
					objorder.setTasklevelsequence(objtaskseq.getTasksequence() + 1);
				}
			}

			if (objorder != null && objorder.getFiletype() != null) {
//				SequenceTableOrderType sqordertype = sqa.getSequencetableordertype().stream()
//				        .filter(sq -> sq.getOrdertype().equals(objorder.getFiletype())
//				        && sq.getSequencecode().equals(sqa.getSequencecode())).findFirst().orElse(null);

				SequenceTableOrderType sqordertype = sequencetableordertyperepository.findBySequencecodeAndOrdertype(1,
						objorder.getFiletype());

				if (sqordertype != null) {
					objorder.setOrdertypesequence(sqordertype.getOrdertypesequence() + 1);
				}
			}

			String sequence = objorder.getSequenceid();
			String sequencetext = sequence;
			if (sequence.contains("{s&") && sequence.contains("$s}")) {
				sequencetext = sequence.substring(sequence.indexOf("{s&") + 3, sequence.indexOf("$s}"));
				String replacedseq = "";
				if (sqa.getSequenceview().equals(2) && objorder.getApplicationsequence() != null
						&& !sequencetext.equals("")) {
					replacedseq = String.format("%0" + sequencetext.length() + "d", objorder.getApplicationsequence());
				} else if (sqa.getSequenceview().equals(3) && objorder.getSitesequence() != null
						&& !sequencetext.equals("")) {
					replacedseq = String.format("%0" + sequencetext.length() + "d", objorder.getSitesequence());

				} else if (sqa.getSequenceview().equals(4) && objorder.getOrdertypesequence() != null
						&& !sequencetext.equals("")) {
					replacedseq = String.format("%0" + sequencetext.length() + "d", objorder.getOrdertypesequence());
				} else if (sqa.getSequenceview().equals(5) && objorder.getTasksequence() != null
						&& !sequencetext.equals("")) {
					replacedseq = String.format("%0" + sequencetext.length() + "d", objorder.getTasksequence());
				} else if (sqa.getSequenceview().equals(6) && objorder.getProjectsequence() != null
						&& !sequencetext.equals("")) {
					replacedseq = String.format("%0" + sequencetext.length() + "d", objorder.getProjectsequence());
				} else if (!sequencetext.equals("") && objorder.getApplicationsequence() != null) {
					replacedseq = String.format("%0" + sequencetext.length() + "d", objorder.getApplicationsequence());
				}

				if (!sequencetext.equals("") && !replacedseq.equals("")) {
					sequencetext = sequence.substring(0, sequence.indexOf("{s&")) + replacedseq
							+ sequence.substring(sequence.indexOf("$s}") + 3, sequence.length());
				}
			}

			if (sequence.contains("{sp&") && sequence.contains("$sp}")) {
				String projectleveltext = sequencetext.substring(sequencetext.indexOf("{sp&") + 4,
						sequencetext.indexOf("$sp}"));
				if (objorder.getProjectlevelsequence() != null && !projectleveltext.equals("")) {
					String replacedseq = String.format("%0" + projectleveltext.length() + "d",
							objorder.getProjectlevelsequence());
					sequencetext = sequencetext.substring(0, sequencetext.indexOf("{sp&")) + replacedseq
							+ sequencetext.substring(sequencetext.indexOf("$sp}") + 4, sequencetext.length());
				}
			}

			if (sequence.contains("{st&") && sequence.contains("$st}")) {
				String taskleveltext = sequencetext.substring(sequencetext.indexOf("{st&") + 4,
						sequencetext.indexOf("$st}"));
				if (objorder.getTasklevelsequence() != null && !taskleveltext.equals("")) {
					String replacedseq = String.format("%0" + taskleveltext.length() + "d",
							objorder.getTasklevelsequence());
					sequencetext = sequencetext.substring(0, sequencetext.indexOf("{st&")) + replacedseq
							+ sequencetext.substring(sequencetext.indexOf("$st}") + 4, sequencetext.length());
				}
			}

			sequencetext = commonfunction.Updatedatesinsequence(sequence, sequencetext);

			objorder.setSequenceid(sequencetext);
		}
		return objorder.getSequenceid();
	}

	public void GetSequences(LSlogilablimsorderdetail objorder, SequenceTable seqorder,
			SequenceTableProjectLevel objprojectseq, SequenceTableTaskLevel objtaskseq) throws ParseException {
		SequenceTable sqa = seqorder;

		if (sqa != null) {
			objorder.setApplicationsequence(sqa.getApplicationsequence() + 1);

//			if(objorder !=null && objorder.getLsuserMaster() != null&&
//					objorder.getLsuserMaster().getLssitemaster()!=null && 
//					objorder.getLsuserMaster().getLssitemaster().getSitecode()!=null)
			if (objorder != null && objorder.getSitecode() != null) {
//				SequenceTableSite sqsite = sqa.getSequencetablesite().stream()
//				        .filter(sq -> sq.getSitecode().equals(objorder.getLsuserMaster().getLssitemaster().getSitecode())
//				        && sq.getSequencecode().equals(sqa.getSequencecode())).findFirst().orElse(null);
				SequenceTableSite sqsite = sqa.getSequencetablesite().stream()
						.filter(sq -> sq.getSitecode().equals(objorder.getSitecode())
								&& sq.getSequencecode().equals(sqa.getSequencecode()))
						.findFirst().orElse(null);
				if (sqsite != null) {
					objorder.setSitesequence(sqsite.getSitesequence() + 1);
				}
			}

			if (objorder != null && objorder.getLsprojectmaster() != null
					&& objorder.getLsprojectmaster().getProjectcode() != null) {
				SequenceTableProject sqproject = sqa.getSequencetableproject().stream()
						.filter(sq -> sq.getProjectcode().equals(objorder.getLsprojectmaster().getProjectcode())
								&& sq.getSequencecode().equals(sqa.getSequencecode()))
						.findFirst().orElse(null);

				if (sqproject != null) {
					objorder.setProjectsequence(sqproject.getProjectsequence() + 1);
				}

				if (objprojectseq == null || objprojectseq.getProjectcode() == null) {
					objprojectseq = sequencetableprojectlevelrepository
							.findByProjectcode(objorder.getLsprojectmaster().getProjectcode());
				}

				if (objprojectseq != null) {
					objorder.setProjectlevelsequence(objprojectseq.getProjectsequence() + 1);
				}

			}

			if (objorder != null && objorder.getLstestmasterlocal() != null
					&& objorder.getLstestmasterlocal().getTestcode() != null) {
				SequenceTableTask sqtask = sqa.getSequencesabletask().stream()
						.filter(sq -> sq.getTestcode().equals(objorder.getLstestmasterlocal().getTestcode())
								&& sq.getSequencecode().equals(sqa.getSequencecode()))
						.findFirst().orElse(null);

				if (sqtask != null) {
					objorder.setTasksequence(sqtask.getTasksequence() + 1);
				}

				if (objtaskseq == null || objtaskseq.getTestcode() == null) {
					objtaskseq = sequencetabletasklevelrepository
							.findByTestcode(objorder.getLstestmasterlocal().getTestcode());
				}

				if (objtaskseq != null) {
					objorder.setTasklevelsequence(objtaskseq.getTasksequence() + 1);
				}
			}

			if (objorder != null && objorder.getFiletype() != null) {
				SequenceTableOrderType sqordertype = sqa.getSequencetableordertype().stream()
						.filter(sq -> sq.getOrdertype().equals(objorder.getFiletype())
								&& sq.getSequencecode().equals(sqa.getSequencecode()))
						.findFirst().orElse(null);

				if (sqordertype != null) {
					objorder.setOrdertypesequence(sqordertype.getOrdertypesequence() + 1);
				}
			}

			String sequence = objorder.getSequenceid();
			String sequencetext = sequence;
			if (sequence.contains("{s&") && sequence.contains("$s}")) {
				sequencetext = sequence.substring(sequence.indexOf("{s&") + 3, sequence.indexOf("$s}"));
				String replacedseq = "";
				if (sqa.getSequenceview().equals(2) && objorder.getApplicationsequence() != null
						&& !sequencetext.equals("")) {
					replacedseq = String.format("%0" + sequencetext.length() + "d", objorder.getApplicationsequence());
				} else if (sqa.getSequenceview().equals(3) && objorder.getSitesequence() != null
						&& !sequencetext.equals("")) {
					replacedseq = String.format("%0" + sequencetext.length() + "d", objorder.getSitesequence());

				} else if (sqa.getSequenceview().equals(4) && objorder.getOrdertypesequence() != null
						&& !sequencetext.equals("")) {
					replacedseq = String.format("%0" + sequencetext.length() + "d", objorder.getOrdertypesequence());
				} else if (sqa.getSequenceview().equals(5) && objorder.getTasksequence() != null
						&& !sequencetext.equals("")) {
					replacedseq = String.format("%0" + sequencetext.length() + "d", objorder.getTasksequence());
				} else if (sqa.getSequenceview().equals(6) && objorder.getProjectsequence() != null
						&& !sequencetext.equals("")) {
					replacedseq = String.format("%0" + sequencetext.length() + "d", objorder.getProjectsequence());
				} else if (!sequencetext.equals("") && objorder.getApplicationsequence() != null) {
					replacedseq = String.format("%0" + sequencetext.length() + "d", objorder.getApplicationsequence());
				}

				if (!sequencetext.equals("") && !replacedseq.equals("")) {
					sequencetext = sequence.substring(0, sequence.indexOf("{s&")) + replacedseq
							+ sequence.substring(sequence.indexOf("$s}") + 3, sequence.length());
				}
			}

			if (sequence.contains("{sp&") && sequence.contains("$sp}")) {
				String projectleveltext = sequencetext.substring(sequencetext.indexOf("{sp&") + 4,
						sequencetext.indexOf("$sp}"));
				if (objorder.getProjectlevelsequence() != null && !projectleveltext.equals("")) {
					String replacedseq = String.format("%0" + projectleveltext.length() + "d",
							objorder.getProjectlevelsequence());
					sequencetext = sequencetext.substring(0, sequencetext.indexOf("{sp&")) + replacedseq
							+ sequencetext.substring(sequencetext.indexOf("$sp}") + 4, sequencetext.length());
				}
			}

			if (sequence.contains("{st&") && sequence.contains("$st}")) {
				String taskleveltext = sequencetext.substring(sequencetext.indexOf("{st&") + 4,
						sequencetext.indexOf("$st}"));
				if (objorder.getTasklevelsequence() != null && !taskleveltext.equals("")) {
					String replacedseq = String.format("%0" + taskleveltext.length() + "d",
							objorder.getTasklevelsequence());
					sequencetext = sequencetext.substring(0, sequencetext.indexOf("{st&")) + replacedseq
							+ sequencetext.substring(sequencetext.indexOf("$st}") + 4, sequencetext.length());
				}
			}

			sequencetext = commonfunction.Updatedatesinsequence(sequence, sequencetext);

			objorder.setSequenceid(sequencetext);
		}
	}

	public void updatesequence(Integer sequenceno, LSlogilablimsorderdetail objorder) {

		if (objorder.getApplicationsequence() != null) {
			sequencetableRepository.setinitialapplicationsequence(objorder.getApplicationsequence(), sequenceno);
		}

//		if(objorder.getSitesequence() != null && objorder.getLsuserMaster() != null&&
//				objorder.getLsuserMaster().getLssitemaster()!=null && 
//				objorder.getLsuserMaster().getLssitemaster().getSitecode()!=null)
		if (objorder.getSitesequence() != null && objorder.getSitecode() != null) {
			sequencetablesiteRepository.setinitialsitesequence(objorder.getSitesequence(), sequenceno,
					objorder.getSitecode());
		}

		if (objorder.getProjectsequence() != null && objorder.getLsprojectmaster() != null
				&& objorder.getLsprojectmaster().getProjectcode() != null) {
			sequencetableprojectRepository.setinitialprojectsequence(objorder.getProjectsequence(), sequenceno,
					objorder.getLsprojectmaster().getProjectcode());
		}

		if (objorder.getTasksequence() != null && objorder.getLstestmasterlocal() != null
				&& objorder.getLstestmasterlocal().getTestcode() != null) {
			sequencetabletaskRepository.setinitialtasksequence(objorder.getTasksequence(), sequenceno,
					objorder.getLstestmasterlocal().getTestcode());
		}

		if (objorder.getOrdertypesequence() != null && objorder.getFiletype() != null) {
			sequencetableordertyperepository.setinitialordertypesequence(objorder.getOrdertypesequence(), sequenceno,
					objorder.getFiletype());
		}

		if (objorder.getProjectlevelsequence() != null && objorder.getLsprojectmaster() != null
				&& objorder.getLsprojectmaster().getProjectcode() != null) {
			sequencetableprojectlevelrepository.setinitialprojectsequence(objorder.getProjectlevelsequence(),
					objorder.getLsprojectmaster().getProjectcode());
		}

		if (objorder.getTasklevelsequence() != null && objorder.getLstestmasterlocal() != null
				&& objorder.getLstestmasterlocal().getTestcode() != null) {
			sequencetabletasklevelrepository.setinitialtasksequence(objorder.getTasklevelsequence(),
					objorder.getLstestmasterlocal().getTestcode());
		}
	}
//	public void ValidateAutoRegister(LSlogilablimsorderdetail objlogilaborderdetail,long milliseconds) throws ParseException {
//		// lsordernotificationrepo.save(objnotification);
//		scheduleAutoregduringregister(objlogilaborderdetail,milliseconds);
//	}

	public void scheduleAutoregduringregister(LSlogilablimsorderdetail objlogilaborderdetail, long milliseconds,
			String timerId1) throws ParseException {
		Date AutoCreateDate = objlogilaborderdetail.getLsautoregisterorders().getAutocreatedate();
		Instant autocreate = AutoCreateDate.toInstant();
		LocalDateTime AutoCreateTime = LocalDateTime.ofInstant(autocreate, ZoneId.systemDefault());
		LocalDateTime currentTime = LocalDateTime.now();

		if (AutoCreateTime.isAfter(currentTime) && objlogilaborderdetail != null) {
//			Duration duration = Duration.between(currentTime, AutoCreateTime);
//			long delay = duration.toMillis();
			long delay = milliseconds;
			scheduleAutoRegister(objlogilaborderdetail, delay, timerId1);
		}
	}

	private void scheduleAutoRegister(LSlogilablimsorderdetail objlogilaborderdetail, long delay, String timerId1) {
		Set<Integer> runningTasks = new HashSet<>();

		int batchcode = objlogilaborderdetail.getBatchcode().intValue();
		synchronized (runningTasks) {
			if (runningTasks.contains(batchcode)) {
				System.out.println("Task already scheduled or running for batch ID: " + batchcode);
				return;
			}
			if ((objlogilaborderdetail.getRepeat() != null && objlogilaborderdetail.getRepeat() != false)) {
				Timer timer = new Timer();
				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						try {
							LSlogilablimsorderdetail objlogilaborderdetailObject = objlogilaborderdetail;
							Timer timerobj = timerMap.get(timerId1);
							if (timerobj == null) {
								timerMap.put(timerId1, timer);
							} else {
								if (orderDetailMap.size() > 0) {
									objlogilaborderdetailObject = orderDetailMap.get(timerId1);
								}
								if (objlogilaborderdetailObject == null) {
									objlogilaborderdetailObject = objlogilaborderdetail;
								}

							}

							objlogilaborderdetailObject = InsertAutoRegisterOrder(objlogilaborderdetailObject,
									timerId1);

							orderDetailMap.put(timerId1, objlogilaborderdetailObject);
							System.out.println("kumu");
						} catch (IOException e) {

							e.printStackTrace();
						} catch (ParseException e) {

							e.printStackTrace();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally {
							synchronized (runningTasks) {
								runningTasks.remove(batchcode);
							}
							scheduledTasks.remove(batchcode);
						}
					}
				};
				runningTasks.add(batchcode);
				timerMap.put(timerId1, timer);

				timer.scheduleAtFixedRate(task, delay, delay);
				scheduledTasks.put(batchcode, task);
			}
		}
	}

	public void createLogilabLIMSOrder4SDMS(LSlogilablimsorderdetail objLSlogilablimsorder) throws IOException {

		List<LSlogilablimsorder> lstLSlogilablimsorder = lslogilablimsorderRepository
				.findBybatchid(objLSlogilablimsorder.getBatchid());

		List<Map<String, Object>> lstMaPObject = new ArrayList<Map<String, Object>>();

		System.out.println("inside createLogilabLIMSOrder4SDMS");
		lstLSlogilablimsorder.stream().peek(f -> {

			if (f.getInstrumentcode() != null) {

				Map<String, Object> objResMap = new HashMap<>();

				objResMap.put("batchid", f.getBatchid());
				objResMap.put("sampleid", f.getSampleid());
				objResMap.put("testcode", f.getTestcode());
				objResMap.put("methodcode", f.getMethodcode());
				objResMap.put("instrumentcode", f.getInstrumentcode());
				objResMap.put("instrumentname", f.getInstrumentname());
				objResMap.put("orderid", f.getOrderid());

				lstMaPObject.add(objResMap);
			}

		}).collect(Collectors.toList());

		if (!lstMaPObject.isEmpty())
			sdmsServiceCalling("IntegrationSDMS/createLogilabLIMSOrder", lstMaPObject);
	}

	private String sdmsServiceCalling(String uri, List<Map<String, Object>> lstMaPObject) {

		final String url = env.getProperty("sdms.template.service.url") + uri;

		System.out.println("inside sdmsServiceCalling");
		System.out.println("Passing objects :   " + lstMaPObject);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

		System.out.println("------- SDMS service IntegrationSDMS/createLogilabLIMSOrder calling -------");
		String result = restTemplate.postForObject(url, lstMaPObject, String.class);
		System.out.println("------- Response of IntegrationSDMS/createLogilabLIMSOrder from Webparser :  " + result);
		return result;
	}

	public Logilabordermaster GetOrderonClose(LSlogilablimsorderdetail objorder) {

		Logilabordermaster objOrder = lslogilablimsorderdetailRepository
				.findByBatchcodeOrderByBatchcodeAsc(objorder.getBatchcode());

//		List<Integer> lstworkflowcode = objorder.getLstworkflow().stream().map(LSworkflow::getWorkflowcode)
//				.collect(Collectors.toList());
		List<LSworkflow> workflowobj = lsworkflowRepository
				.findByLssitemasterAndStatusOrderByWorkflowcodeAsc(objorder.getLsuserMaster().getLssitemaster(), 1);
//		lslogilablimsorderdetailRepository
//		.findByBatchcodeOrderByBatchcodeAsc(objorder.getBatchcode()).getLstworkflow()
		objOrder.setLstworkflow(objorder.getLstworkflow(), workflowobj, objorder.getLsuserMaster());

		return objOrder;
	}

	public Map<String, Object> getdOrderCount(LSuserMaster objuser) {
		Date fromdate = objuser.getObjuser().getFromdate();
		Date todate = objuser.getObjuser().getTodate();
		Map<String, Object> mapOrders = new HashMap<String, Object>();

		if (objuser.getUsername().equals("Administrator") && objuser.getObjuser().getOrderfor() == 1) {

			mapOrders.put("pendingorder", lslogilablimsorderdetailRepository.countByOrderflag("N"));
			mapOrders.put("completedorder", lslogilablimsorderdetailRepository.countByOrderflag("R"));

		} else {
			long lstlimscompleted = 0;
			if (objuser.getLstproject() != null && objuser.getLstproject().size() > 0) {
				lstlimscompleted = lslogilablimsorderdetailRepository
						.countByOrderflagAndLsprojectmasterInAndCreatedtimestampBetween("R", objuser.getLstproject(),
								fromdate, todate);
			}

			long lstpending = 0;
			if (objuser.getLstproject() != null && objuser.getLstproject().size() > 0) {
				lstpending = lslogilablimsorderdetailRepository
						.countByOrderflagAndLsprojectmasterInAndCreatedtimestampBetween("N", objuser.getLstproject(),
								fromdate, todate);
			}

			mapOrders.put("pendingorder", (lstpending));
			mapOrders.put("completedorder", (lstlimscompleted));

		}

		fromdate = null;
		todate = null;

		return mapOrders;
	}

	public void updatenotificationfororder(LSlogilablimsorderdetail objorder) {

		String batchid = "";
		batchid = objorder.getSequenceid() != null ? objorder.getSequenceid() : objorder.getBatchid();

		try {
			String Details = "";
			String Notifiction = "";
			if (objorder != null && objorder.getLsprojectmaster() != null
					&& objorder.getLsprojectmaster().getLsusersteam() != null && objorder.getAssignedto() == null) {

				if (lsusersteamRepository.findByteamcode(objorder.getLsprojectmaster().getLsusersteam().getTeamcode())
						.getLsuserteammapping() != null
						&& lsusersteamRepository
								.findByteamcode(objorder.getLsprojectmaster().getLsusersteam().getTeamcode())
								.getLsuserteammapping().size() > 0) {

					if (objorder.getOrderflag().equalsIgnoreCase("R")) {
						Notifiction = "ORDERCOMPLETED";

						Details = "{\"ordercode\":\"" + objorder.getBatchcode() + "\", \"order\":\"" + batchid
								+ "\", \"previousworkflow\":\"" + "" + "\", \"previousworkflowcode\":\"" + -1
								+ "\", \"currentworkflow\":\"" + objorder.getLsworkflow().getWorkflowname()
								+ "\", \"completeduser\":\"" + objorder.getObjLoggeduser().getUsername()
								+ "\", \"currentworkflowcode\":\"" + objorder.getLsworkflow().getWorkflowcode() + "\"}";
					} else {
						Notifiction = "ORDERCREATION";

						Details = "{\"ordercode\":\"" + objorder.getBatchcode() + "\", \"order\":\"" + batchid
								+ "\", \"previousworkflow\":\"" + "" + "\", \"previousworkflowcode\":\"" + -1
								+ "\", \"currentworkflow\":\"" + objorder.getLsworkflow().getWorkflowname()
								+ "\", \"currentworkflowcode\":\"" + objorder.getLsworkflow().getWorkflowcode() + "\"}";
					}

					List<LSuserteammapping> lstusers = lsusersteamRepository
							.findByteamcode(objorder.getLsprojectmaster().getLsusersteam().getTeamcode())
							.getLsuserteammapping();
					List<LSnotification> lstnotifications = new ArrayList<LSnotification>();
					for (int i = 0; i < lstusers.size(); i++) {
						if (!(objorder.getObjLoggeduser().getUsercode()
								.equals(lstusers.get(i).getLsuserMaster().getUsercode()))) {
							LSnotification objnotify = new LSnotification();
							objnotify.setNotifationfrom(
									lsuserMasterRepository.findByusercode(objorder.getObjLoggeduser().getUsercode()));
							objnotify.setNotifationto(lstusers.get(i).getLsuserMaster());
							objnotify.setNotificationdate(commonfunction.getCurrentUtcTime());
							objnotify.setNotification(Notifiction);
							objnotify.setNotificationdetils(Details);
							objnotify.setIsnewnotification(1);
							objnotify.setNotificationpath("/registertask");
							objnotify.setNotificationfor(2);
							if (objorder.getSitecode() != null) {
								objnotify.setSitecode(objorder.getSitecode());
							}

							lstnotifications.add(objnotify);
						}
					}

					lsnotificationRepository.saveAll(lstnotifications);
				}
			} else if (objorder.getAssignedto() != null) {

				if (objorder.getOrderflag().equalsIgnoreCase("R")) {
					Notifiction = "ORDERCOMPLETEDASSIGN";

					Details = "{\"ordercode\":\"" + objorder.getBatchcode() + "\", \"order\":\"" + batchid
							+ "\", \"previousworkflow\":\"" + "" + "\", \"previousworkflowcode\":\"" + -1
							+ "\", \"currentworkflow\":\"" + objorder.getLsworkflow().getWorkflowname()
							+ "\", \"assignedto\":\"" + objorder.getAssignedto().getUsername()
							+ "\", \"completeduser\":\"" + objorder.getObjLoggeduser().getUsername()
							+ "\", \"currentworkflowcode\":\"" + objorder.getLsworkflow().getWorkflowcode() + "\"}";

				} else {

					Notifiction = "ORDERCREATIONANDASSIGN";
					Details = "{\"ordercode\":\"" + objorder.getBatchcode() + "\", \"order\":\"" + batchid
							+ "\", \"previousworkflow\":\"" + "" + "\", \"previousworkflowcode\":\"" + -1
							+ "\", \"currentworkflow\":\"" + objorder.getLsworkflow().getWorkflowname()
							+ "\", \"assignedto\":\"" + objorder.getAssignedto().getUsername() + "\", \"assignedby\":\""
							+ objorder.getObjLoggeduser().getUsername() + "\", \"currentworkflowcode\":\""
							+ objorder.getLsworkflow().getWorkflowcode() + "\"}";
				}

				LSnotification objnotify = new LSnotification();
				objnotify.setNotifationfrom(
						lsuserMasterRepository.findByusercode(objorder.getObjLoggeduser().getUsercode()));
				objnotify.setNotifationto(objorder.getAssignedto());
				objnotify.setNotificationdate(commonfunction.getCurrentUtcTime());
				objnotify.setNotification(Notifiction);
				objnotify.setNotificationdetils(Details);
				objnotify.setIsnewnotification(1);
				objnotify.setNotificationpath("/registertask");
				objnotify.setNotificationfor(1);
				if (objorder.getSitecode() != null) {
					objnotify.setSitecode(objorder.getSitecode());
				}
				lsnotificationRepository.save(objnotify);

			}
			Details = null;
			Notifiction = null;
			batchid = null;
		} catch (Exception e) {

		}
	}

	public List<OrderShareInterface> GetsharedordersonFilter(LSlogilablimsorderdetail objorder,
			List<OrderShareInterface> lstsharedorder) {
//		List<Logilaborders> lstorder = new ArrayList<Logilaborders>();

		List<OrderShareInterface> lstlogilab = new ArrayList<OrderShareInterface>();

		List<Long> lstBatchcode = new ArrayList<Long>();
		List<Integer> lstsamplefilecode = new ArrayList<Integer>();
		List<LSsamplefile> idList = new ArrayList<LSsamplefile>();

		List<Long> batchcode = lstsharedorder.stream().map(OrderShareInterface::getBatchcode)
				.collect(Collectors.toList());

		List<Integer> filetype = lstsharedorder.stream().map(OrderShareInterface::getFiletype)
				.collect(Collectors.toList());

		if (objorder.getSearchCriteria().getContentsearchtype() != null
				&& objorder.getSearchCriteria().getContentsearch() != null) {

			if (objorder.getFiletype() == -1) {
//				lstBatchcode = lslogilablimsorderdetailRepository.getBatchcodeAndcreateddate(objorder.getFromdate(),
//						objorder.getTodate());

				lstBatchcode = lslogilablimsorderdetailRepository.findByBatchcodeIn(batchcode);

			} else {
//				lstBatchcode = lslogilablimsorderdetailRepository.getBatchcodeonFiletypeAndcreateddate(
//						objorder.getFiletype(), objorder.getFromdate(), objorder.getTodate());

				lstBatchcode = lslogilablimsorderdetailRepository.findByBatchcodeInAndFiletypeIn(batchcode, filetype);
			}

			if (lstBatchcode != null && lstBatchcode.size() > 0) {
				lstsamplefilecode = lssamplefileRepository.getFilesamplecodeByBatchcodeIn(lstBatchcode);
			}

			if (lstsamplefilecode != null && lstsamplefilecode.size() > 0) {
				idList = getsamplefileIds(lstsamplefilecode, objorder.getSearchCriteria(), objorder.getIsmultitenant());
				if (idList != null && idList.size() > 0) {

					final List<LSsamplefile> idlistdata = idList;

					lstlogilab = lstsharedorder.stream()
							.filter(srow1 -> idlistdata.stream()
									.anyMatch(detailrow -> srow1.getBatchcode().equals(detailrow.getBatchcode())))
							.collect(Collectors.toList());
				}
			}

		}

		return lstlogilab;

	}

	public List<Logilaborderssh> GetmyordersonFilter(LSlogilablimsorderdetail objorder,
			List<Logilaborderssh> lstmyorders, String Orderflag) {
		List<Logilaborderssh> lstorder = new ArrayList<Logilaborderssh>();
		List<Long> lstBatchcode = new ArrayList<Long>();
		List<Integer> lstsamplefilecode = new ArrayList<Integer>();
		List<LSsamplefile> idList = new ArrayList<LSsamplefile>();

//		Integer filetype = objorder.getFiletype();

		List<Long> batchcode = lstmyorders.stream().map(Logilaborderssh::getBc).collect(Collectors.toList());

		List<Integer> filetypelist = lstmyorders.stream().map(Logilaborderssh::getFt).collect(Collectors.toList());

//		List<String> orderflag = lstmyorders.stream().map(Logilaborders::getOrderflag)
//				.collect(Collectors.toList());

		if (objorder.getSearchCriteria().getContentsearchtype() != null
				&& objorder.getSearchCriteria().getContentsearch() != null) {

			if (objorder.getFiletype() == -1) {
//				lstBatchcode = lslogilablimsorderdetailRepository.getBatchcodeAndcreateddate(objorder.getFromdate(),
//						objorder.getTodate());
				lstBatchcode = lslogilablimsorderdetailRepository.findByBatchcodeIn(batchcode);
			} else {
//				lstBatchcode = lslogilablimsorderdetailRepository.getBatchcodeonFiletypeAndcreateddate(
//						objorder.getFiletype(), objorder.getFromdate(), objorder.getTodate());
				lstBatchcode = lslogilablimsorderdetailRepository.findByBatchcodeInAndFiletypeIn(batchcode,
						filetypelist);
			}

			if (lstBatchcode != null && lstBatchcode.size() > 0) {
				lstsamplefilecode = lssamplefileRepository.getFilesamplecodeByBatchcodeIn(lstBatchcode);
			}

			if (lstsamplefilecode != null && lstsamplefilecode.size() > 0) {
				idList = getsamplefileIds(lstsamplefilecode, objorder.getSearchCriteria(), objorder.getIsmultitenant());
				if (idList != null && idList.size() > 0) {

					final List<LSsamplefile> idlistdata = idList;

					lstorder = lstmyorders.stream()
							.filter(srow1 -> idlistdata.stream()
									.anyMatch(detailrow -> srow1.getBc().equals(detailrow.getBatchcode())))
							.collect(Collectors.toList());

				}
			}

		}

		return lstorder;

	}

	public List<Logilaborders> GetsampleordersonFilter(LSlogilablimsorderdetail lstorderstr,
			List<Logilaborders> lstorder2) {
		List<Logilaborders> lstorder = new ArrayList<Logilaborders>();
		List<Long> lstBatchcode = new ArrayList<Long>();
		List<Integer> lstsamplefilecode = new ArrayList<Integer>();
		List<LSsamplefile> idList = new ArrayList<LSsamplefile>();

		List<Long> batchcode = lstorder2.stream().map(Logilaborders::getBatchcode).collect(Collectors.toList());

		List<Integer> filetypelist = lstorder2.stream().map(Logilaborders::getFiletype).collect(Collectors.toList());

		if (lstorderstr.getSearchCriteria().getContentsearchtype() != null
				&& lstorderstr.getSearchCriteria().getContentsearch() != null) {

			if (lstorderstr.getFiletype() == -1) {
//				lstBatchcode = lslogilablimsorderdetailRepository.getBatchcodeAndcreateddate(lstorderstr.getObjuser().getFromdate(),
//						lstorderstr.getObjuser().getTodate());
				lstBatchcode = lslogilablimsorderdetailRepository.findByBatchcodeIn(batchcode);
			} else {
//				lstBatchcode = lslogilablimsorderdetailRepository.getBatchcodeonFiletypeAndcreateddate(
//						lstorderstr.getFiletype(), lstorderstr.getObjuser().getFromdate(),
//						lstorderstr.getObjuser().getTodate());
				lstBatchcode = lslogilablimsorderdetailRepository.findByBatchcodeInAndFiletypeIn(batchcode,
						filetypelist);
			}

			if (lstBatchcode != null && lstBatchcode.size() > 0) {
				lstsamplefilecode = lssamplefileRepository.getFilesamplecodeByBatchcodeIn(lstBatchcode);
			}

			if (lstsamplefilecode != null && lstsamplefilecode.size() > 0) {
				idList = getsamplefileIds(lstsamplefilecode, lstorderstr.getSearchCriteria(),
						lstorderstr.getIsmultitenant());
				if (idList != null && idList.size() > 0) {

//					lstorder = lslogilablimsorderdetailRepository
//							.findByDirectorycodeAndCreatedtimestampBetweenAndLssamplefileIn(lstorderstr.getDirectorycode(),
//									lstorderstr.getObjuser().getFromdate(),lstorderstr.getObjuser().getTodate(), idList);

					final List<LSsamplefile> idlistdata = idList;

					lstorder = lstorder2.stream()
							.filter(srow1 -> idlistdata.stream()
									.anyMatch(detailrow -> srow1.getBatchcode().equals(detailrow.getBatchcode())))
							.collect(Collectors.toList());
				}
			}

		}

		return lstorder;

	}

	public List<Logilaborders> GetordersondirectoryFilter(LSSheetOrderStructure lstorderstr,
			List<Logilaborders> lstorder2) {
		List<Logilaborders> lstorder = new ArrayList<Logilaborders>();
		List<Long> lstBatchcode = new ArrayList<Long>();
		List<Integer> lstsamplefilecode = new ArrayList<Integer>();
		List<LSsamplefile> idList = new ArrayList<LSsamplefile>();

		List<Long> batchcode = lstorder2.stream().map(Logilaborders::getBatchcode).collect(Collectors.toList());

		List<Integer> filetypelist = lstorder2.stream().map(Logilaborders::getFiletype).collect(Collectors.toList());

		if (lstorderstr.getSearchCriteria().getContentsearchtype() != null
				&& lstorderstr.getSearchCriteria().getContentsearch() != null) {

			if (lstorderstr.getFiletype() == -1) {
//				lstBatchcode = lslogilablimsorderdetailRepository.getBatchcodeAndcreateddate(lstorderstr.getObjuser().getFromdate(),
//						lstorderstr.getObjuser().getTodate());
				lstBatchcode = lslogilablimsorderdetailRepository.findByBatchcodeIn(batchcode);
			} else {
//				lstBatchcode = lslogilablimsorderdetailRepository.getBatchcodeonFiletypeAndcreateddate(
//						lstorderstr.getFiletype(), lstorderstr.getObjuser().getFromdate(),
//						lstorderstr.getObjuser().getTodate());
				lstBatchcode = lslogilablimsorderdetailRepository.findByBatchcodeInAndFiletypeIn(batchcode,
						filetypelist);
			}

			if (lstBatchcode != null && lstBatchcode.size() > 0) {
				lstsamplefilecode = lssamplefileRepository.getFilesamplecodeByBatchcodeIn(lstBatchcode);
			}

			if (lstsamplefilecode != null && lstsamplefilecode.size() > 0) {
				idList = getsamplefileIds(lstsamplefilecode, lstorderstr.getSearchCriteria(),
						lstorderstr.getIsmultitenant());
				if (idList != null && idList.size() > 0) {

//					lstorder = lslogilablimsorderdetailRepository
//							.findByDirectorycodeAndCreatedtimestampBetweenAndLssamplefileIn(lstorderstr.getDirectorycode(),
//									lstorderstr.getObjuser().getFromdate(),lstorderstr.getObjuser().getTodate(), idList);

					final List<LSsamplefile> idlistdata = idList;

					lstorder = lstorder2.stream()
							.filter(srow1 -> idlistdata.stream()
									.anyMatch(detailrow -> srow1.getBatchcode().equals(detailrow.getBatchcode())))
							.collect(Collectors.toList());
				}
			}

		}

		return lstorder;

	}

	public void updatenotificationfororderworkflow(LSlogilablimsorderdetail objorder, LSworkflow previousworkflow) {

		String batchid = "";
//		SequenceTable seqobj =  sequenceTableRepository.findBySequencecodeOrderBySequencecode(1);
//		Boolean Applicationseq = seqobj.getSequenceview().equals(2) ? true : false;
//		batchid = Applicationseq 
//				?  objorder.getSequenceid() != null 
//					? objorder.getSequenceid() : objorder.getBatchid() 
//				: objorder.getBatchid();
		batchid = objorder.getSequenceid() != null ? objorder.getSequenceid() : objorder.getBatchid();

		try {
			String Details = "";
			String Notifiction = "";

			LSuserMaster obj = lsuserMasterRepository.findByusercode(objorder.getObjLoggeduser().getUsercode());

			if (objorder.getApprovelstatus() != null) {
				LSusersteam objteam = lsusersteamRepository
						.findByteamcode(objorder.getLsprojectmaster().getLsusersteam().getTeamcode());

				if (objorder.getApprovelstatus() == 1) {
					Notifiction = "ORDERAPPROVED";
				} else if (objorder.getApprovelstatus() == 2) {
					Notifiction = "ORDERRETERNED";
				} else if (objorder.getApprovelstatus() == 3) {
					Notifiction = "ORDERREJECTED";
				}

				int perviousworkflowcode = previousworkflow != null ? previousworkflow.getWorkflowcode() : -1;
				String previousworkflowname = previousworkflow != null ? previousworkflow.getWorkflowname() : "";

				if (previousworkflowname.equals(objorder.getLsworkflow().getWorkflowname())
						&& objorder.getApprovelstatus() == 1) {
					Notifiction = "ORDERFINALAPPROVAL";
				}

				Details = "{\"ordercode\":\"" + objorder.getBatchcode() + "\", \"order\":\"" + batchid
						+ "\", \"previousworkflow\":\"" + previousworkflowname + "\", \"previousworkflowcode\":\""
						+ perviousworkflowcode + "\", \"currentworkflow\":\""
						+ objorder.getLsworkflow().getWorkflowname() + "\", \"currentworkflowcode\":\""
						+ objorder.getLsworkflow().getWorkflowcode() + "\"}";

				List<LSuserteammapping> lstusers = objteam.getLsuserteammapping();
				List<LSnotification> lstnotifications = new ArrayList<LSnotification>();
				for (int i = 0; i < lstusers.size(); i++) {
					if (!(objorder.getObjLoggeduser().getUsercode()
							.equals(lstusers.get(i).getLsuserMaster().getUsercode()))) {
						LSnotification objnotify = new LSnotification();
						objnotify.setNotifationfrom(obj);
						objnotify.setNotifationto(lstusers.get(i).getLsuserMaster());
						objnotify.setNotificationdate(objorder.getModifidate());
						objnotify.setNotification(Notifiction);
						objnotify.setNotificationdetils(Details);
						objnotify.setIsnewnotification(1);
						objnotify.setNotificationpath("/registertask");
						objnotify.setNotificationfor(2);
						if (previousworkflow.getLssitemaster() != null
								&& previousworkflow.getLssitemaster().getSitecode() != null) {
							objnotify.setSitecode(previousworkflow.getLssitemaster().getSitecode());
						}
						lstnotifications.add(objnotify);
					}
				}

				lsnotificationRepository.saveAll(lstnotifications);
			}
		} catch (Exception e) {

		}
		batchid = null;
	}

	public void updatenotificationfororder(LSlogilablimsorderdetail objorder, String currentworkflow) {
		String batchid = "";
//		SequenceTable seqobj =  sequenceTableRepository.findBySequencecodeOrderBySequencecode(1);
//		Boolean Applicationseq = seqobj.getSequenceview().equals(2) ? true : false;
//		batchid = Applicationseq 
//				?  objorder.getSequenceid() != null 
//					? objorder.getSequenceid() : objorder.getBatchid() 
//				: objorder.getBatchid();
		batchid = objorder.getSequenceid() != null ? objorder.getSequenceid() : objorder.getBatchid();
		try {
			if (objorder != null && objorder.getLsprojectmaster() != null
					&& objorder.getLsprojectmaster().getLsusersteam() != null) {
				LSusersteam objteam = new LSusersteam();
				if (objorder.getLsprojectmaster() != null && objorder.getLsprojectmaster().getLsusersteam() != null
						&& objorder.getLsprojectmaster().getLsusersteam().getTeamcode() != null) {
					objteam = lsusersteamRepository
							.findByteamcode(objorder.getLsprojectmaster().getLsusersteam().getTeamcode());
				} else {
					LSprojectmaster obj = lsprojectmasterRepository
							.findByProjectcode(objorder.getLsprojectmaster().getProjectcode());
					objteam = lsusersteamRepository.findByteamcode(obj.getLsusersteam().getTeamcode());

				}

				if (objteam.getLsuserteammapping() != null && objteam.getLsuserteammapping().size() > 0) {
					String Details = "";
					String Notifiction = "";

					if (objorder.getApprovelstatus() != null) {

						if (objorder.getApprovelstatus() == 1) {
							Notifiction = "ORDERAPPROVED";
							Details = "{\"ordercode\":\"" + objorder.getBatchcode() + "\", \"order\":\"" + batchid
									+ "\", \"currentworkflow\":\"" + currentworkflow + "\", \"movedworkflow\":\""
									+ objorder.getLsworkflow().getWorkflowname() + "\"}";
						} else if (objorder.getApprovelstatus() == 3) {
							Notifiction = "ORDERREJECTED";
							Details = "{\"ordercode\":\"" + objorder.getBatchcode() + "\", \"order\":\"" + batchid
									+ "\", \"workflowname\":\"" + objorder.getLsworkflow().getWorkflowname() + "\"}";
						}
					}

					List<LSuserteammapping> lstusers = objteam.getLsuserteammapping();
					List<LSnotification> lstnotifications = new ArrayList<LSnotification>();
					for (int i = 0; i < lstusers.size(); i++) {
						if (!(objorder.getObjLoggeduser().getUsercode()
								.equals(lstusers.get(i).getLsuserMaster().getUsercode()))) {

							LSnotification objnotify = new LSnotification();
							objnotify.setNotifationfrom(objorder.getObjLoggeduser());
							objnotify.setNotifationto(lstusers.get(i).getLsuserMaster());
							objnotify.setNotificationdate(objorder.getCreatedtimestamp());
							objnotify.setNotification(Notifiction);
							objnotify.setNotificationdetils(Details);
							objnotify.setIsnewnotification(1);
							objnotify.setNotificationpath("/registertask");
							objnotify.setNotificationfor(2);
							if (objorder.getSitecode() != null) {
								objnotify.setSitecode(objorder.getSitecode());
							}
							lstnotifications.add(objnotify);
						}
					}

					lsnotificationRepository.saveAll(lstnotifications);
				}
			}
		} catch (Exception e) {

		}
		batchid = null;
	}

	public LSactivity InsertActivities(LSactivity objActivity) {
		try {
			objActivity.setActivityDate(commonfunction.getCurrentUtcTime());
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return lsactivityRepository.save(objActivity);
	}

	public List<LSlogilablimsorderdetail> Getorderbytype(LSlogilablimsorderdetail objorder) {

		if (objorder.getOrderflag().equals("N")) {
			return lslogilablimsorderdetailRepository
					.findByFiletypeAndOrderflagAndCreatedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
							objorder.getFiletype(), objorder.getOrderflag(), objorder.getFromdate(),
							objorder.getTodate());
		} else {
			return lslogilablimsorderdetailRepository
					.findByFiletypeAndOrderflagAndCompletedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
							objorder.getFiletype(), objorder.getOrderflag(), objorder.getFromdate(),
							objorder.getTodate());
		}
	}

	public List<LSlogilablimsorderdetail> Getorderbytypeanduser(LSlogilablimsorderdetail objorder) {
		List<LSuserteammapping> lstteammap = lsuserteammappingRepository.findBylsuserMaster(objorder.getLsuserMaster());
		List<LSusersteam> lstteam = lsusersteamRepository.findByLsuserteammappingInAndLssitemaster(lstteammap,
				objorder.getLsuserMaster().getLssitemaster());
		List<LSprojectmaster> lstproject = lsprojectmasterRepository.findByLsusersteamIn(lstteam);
		List<LSlogilablimsorderdetail> lstorder = new ArrayList<LSlogilablimsorderdetail>();

		if (objorder.getOrderflag().equals("N")) {
			lstorder = lslogilablimsorderdetailRepository
					.findByFiletypeAndOrderflagAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
							objorder.getFiletype(), objorder.getOrderflag(), lstproject, objorder.getFromdate(),
							objorder.getTodate());
		} else {
			lstorder = lslogilablimsorderdetailRepository
					.findByFiletypeAndOrderflagAndLsprojectmasterInAndCompletedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
							objorder.getFiletype(), objorder.getOrderflag(), lstproject, objorder.getFromdate(),
							objorder.getTodate());
		}

		lstteammap = null;
		lstteam = null;
		lstproject = null;

		return lstorder;
	}

	public List<LSlogilablimsorderdetail> Getorderbytypeandflag(LSlogilablimsorderdetail objorder,
			Map<String, Object> mapOrders) {
		List<LSlogilablimsorderdetail> lstorder = new ArrayList<LSlogilablimsorderdetail>();

		List<Long> lstBatchcode = new ArrayList<Long>();
		List<Integer> lstsamplefilecode = new ArrayList<Integer>();
		List<LSsamplefile> idList = new ArrayList<LSsamplefile>();
		if (objorder.getOrderflag().equals("N")) {

			if (objorder.getSearchCriteria() != null && objorder.getSearchCriteria().getContentsearch().trim() != "") {

				lstBatchcode = lslogilablimsorderdetailRepository.getBatchcodeonFiletypeAndFlagandcreateddate(
						objorder.getFiletype(), objorder.getOrderflag(), objorder.getFromdate(), objorder.getTodate());

				if (lstBatchcode != null && lstBatchcode.size() > 0) {
					lstsamplefilecode = lssamplefileRepository.getFilesamplecodeByBatchcodeIn(lstBatchcode);
				}

				if (lstsamplefilecode != null && lstsamplefilecode.size() > 0) {
					idList = getsamplefileIds(lstsamplefilecode, objorder.getSearchCriteria(),
							objorder.getIsmultitenant());
					if (idList != null && idList.size() > 0) {
						lstorder = lslogilablimsorderdetailRepository
								.findByFiletypeAndOrderflagAndCreatedtimestampBetweenAndLssamplefileIn(
										objorder.getFiletype(), objorder.getOrderflag(), objorder.getFromdate(),
										objorder.getTodate(), idList);
					}
				}
			} else {
				lstorder = lslogilablimsorderdetailRepository
						.findByFiletypeAndOrderflagAndCreatedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
								objorder.getFiletype(), objorder.getOrderflag(), objorder.getFromdate(),
								objorder.getTodate());
			}

		} else {
			if (objorder.getSearchCriteria() != null && objorder.getSearchCriteria().getContentsearch().trim() != "") {
				lstBatchcode = lslogilablimsorderdetailRepository.getBatchcodeonFiletypeAndFlagandCompletedtime(
						objorder.getFiletype(), objorder.getOrderflag(), objorder.getFromdate(), objorder.getTodate());

				if (lstBatchcode != null && lstBatchcode.size() > 0) {
					lstsamplefilecode = lssamplefileRepository.getFilesamplecodeByBatchcodeIn(lstBatchcode);
				}

				if (lstsamplefilecode != null && lstsamplefilecode.size() > 0) {
					idList = getsamplefileIds(lstsamplefilecode, objorder.getSearchCriteria(),
							objorder.getIsmultitenant());
					if (idList != null && idList.size() > 0) {
						lstorder = lslogilablimsorderdetailRepository
								.findByFiletypeAndOrderflagAndCompletedtimestampBetweenAndLssamplefileIn(
										objorder.getFiletype(), objorder.getOrderflag(), objorder.getFromdate(),
										objorder.getTodate(), idList);

					}
				}
			} else {
				lstorder = lslogilablimsorderdetailRepository
						.findByFiletypeAndOrderflagAndCompletedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
								objorder.getFiletype(), objorder.getOrderflag(), objorder.getFromdate(),
								objorder.getTodate());
			}
		}

		if (objorder.getObjsilentaudit() != null) {
			objorder.getObjsilentaudit().setTableName("LSlogilablimsorderdetail");
			try {
				objorder.getObjsilentaudit().setTransactiondate(commonfunction.getCurrentUtcTime());
			} catch (ParseException e) {

				e.printStackTrace();
			}
			lscfttransactionRepository.save(objorder.getObjsilentaudit());
		}

		long pendingcount = 0;
		long completedcount = 0;

		if (objorder.getSearchCriteria() != null && objorder.getSearchCriteria().getContentsearch().trim() != "") {
			if (objorder.getOrderflag().equals("N")) {
				if (idList != null) {
					pendingcount = idList.size();
				}

				completedcount = lslogilablimsorderdetailRepository
						.countByFiletypeAndOrderflagAndCompletedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
								objorder.getFiletype(), "R", objorder.getFromdate(), objorder.getTodate());
			} else {
				if (idList != null) {
					completedcount = idList.size();
				}

				pendingcount = lslogilablimsorderdetailRepository
						.countByFiletypeAndOrderflagAndCreatedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
								objorder.getFiletype(), "N", objorder.getFromdate(), objorder.getTodate());
			}

		} else {
			pendingcount = lslogilablimsorderdetailRepository
					.countByFiletypeAndOrderflagAndCreatedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
							objorder.getFiletype(), "N", objorder.getFromdate(), objorder.getTodate());

			completedcount = lslogilablimsorderdetailRepository
					.countByFiletypeAndOrderflagAndCompletedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
							objorder.getFiletype(), "R", objorder.getFromdate(), objorder.getTodate());
		}

		long sharedbycount = 0;
		long sharetomecount = 0;

		if (objorder.getObjLoggeduser() != null && objorder.getObjLoggeduser().getUnifieduserid() != null) {
			sharedbycount = lsordersharedbyRepository
					.countBySharebyunifiedidAndOrdertypeAndSharestatusOrderBySharedbycodeDesc(
							objorder.getObjLoggeduser().getUnifieduserid(), objorder.getFiletype(), 1);
			sharetomecount = lsordersharetoRepository
					.countBySharetounifiedidAndOrdertypeAndSharestatusOrderBySharetocodeDesc(
							objorder.getObjLoggeduser().getUnifieduserid(), objorder.getFiletype(), 1);
		}

		mapOrders.put("orders", lstorder);
		mapOrders.put("pendingcount", pendingcount);
		mapOrders.put("completedcount", completedcount);
		mapOrders.put("sharedbycount", sharedbycount);
		mapOrders.put("sharetomecount", sharetomecount);

		return lstorder;
	}

	public List<Logilaborders> GetorderbytypeandflagOrdersonly(LSlogilablimsorderdetail objorder,
			Map<String, Object> mapOrders) {

		List<Logilaborders> lstorder = new ArrayList<Logilaborders>();
		List<Long> lstBatchcode = new ArrayList<Long>();
		List<Integer> lstsamplefilecode = new ArrayList<Integer>();
		List<LSsamplefile> idList = new ArrayList<LSsamplefile>();
		if (objorder.getOrderflag().equals("N")) {

			if (objorder.getSearchCriteria() != null && objorder.getSearchCriteria().getContentsearch().trim() != "") {

				lstBatchcode = lslogilablimsorderdetailRepository.getBatchcodeonFiletypeAndFlagandcreateddate(
						objorder.getFiletype(), objorder.getOrderflag(), objorder.getFromdate(), objorder.getTodate());

				if (lstBatchcode != null && lstBatchcode.size() > 0) {
					lstsamplefilecode = lssamplefileRepository.getFilesamplecodeByBatchcodeIn(lstBatchcode);
				}

				if (lstsamplefilecode != null && lstsamplefilecode.size() > 0) {
					idList = getsamplefileIds(lstsamplefilecode, objorder.getSearchCriteria(),
							objorder.getIsmultitenant());
					if (idList != null && idList.size() > 0) {

						lstorder = lslogilablimsorderdetailRepository
								.findByOrderflagAndFiletypeAndCreatedtimestampBetweenAndLssamplefileIn(
										objorder.getOrderflag(), objorder.getFiletype(), objorder.getFromdate(),
										objorder.getTodate(), idList);
					}
				}
			} else {

				lstorder = lslogilablimsorderdetailRepository
						.findByOrderflagAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
								objorder.getOrderflag(), objorder.getFiletype(), objorder.getFromdate(),
								objorder.getTodate());
			}

		} else {
			if (objorder.getSearchCriteria() != null && objorder.getSearchCriteria().getContentsearch().trim() != "") {
				lstBatchcode = lslogilablimsorderdetailRepository.getBatchcodeonFiletypeAndFlagandCompletedtime(
						objorder.getFiletype(), objorder.getOrderflag(), objorder.getFromdate(), objorder.getTodate());

				if (lstBatchcode != null && lstBatchcode.size() > 0) {
					lstsamplefilecode = lssamplefileRepository.getFilesamplecodeByBatchcodeIn(lstBatchcode);
				}

				if (lstsamplefilecode != null && lstsamplefilecode.size() > 0) {
					idList = getsamplefileIds(lstsamplefilecode, objorder.getSearchCriteria(),
							objorder.getIsmultitenant());
					if (idList != null && idList.size() > 0) {

						lstorder = lslogilablimsorderdetailRepository
								.findByOrderflagAndFiletypeAndCompletedtimestampBetweenAndLssamplefileIn(
										objorder.getOrderflag(), objorder.getFiletype(), objorder.getFromdate(),
										objorder.getTodate(), idList);
					}
				}
			} else {

				lstorder = lslogilablimsorderdetailRepository
						.findByOrderflagAndFiletypeAndCompletedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
								objorder.getOrderflag(), objorder.getFiletype(), objorder.getFromdate(),
								objorder.getTodate());
			}
		}

		long pendingcount = 0;
		long completedcount = 0;

		if (objorder.getSearchCriteria() != null && objorder.getSearchCriteria().getContentsearch().trim() != "") {
			if (objorder.getOrderflag().equals("N")) {
				if (idList != null) {
					pendingcount = idList.size();
				}

				completedcount = lslogilablimsorderdetailRepository
						.countByFiletypeAndOrderflagAndCompletedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
								objorder.getFiletype(), "R", objorder.getFromdate(), objorder.getTodate());
			} else {
				if (idList != null) {
					completedcount = idList.size();
				}

				pendingcount = lslogilablimsorderdetailRepository
						.countByFiletypeAndOrderflagAndCreatedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
								objorder.getFiletype(), "N", objorder.getFromdate(), objorder.getTodate());
			}

		} else {
			pendingcount = lslogilablimsorderdetailRepository
					.countByFiletypeAndOrderflagAndCreatedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
							objorder.getFiletype(), "N", objorder.getFromdate(), objorder.getTodate());

			completedcount = lslogilablimsorderdetailRepository
					.countByFiletypeAndOrderflagAndCompletedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
							objorder.getFiletype(), "R", objorder.getFromdate(), objorder.getTodate());
		}

		long sharedbycount = 0;
		long sharetomecount = 0;

		if (objorder.getObjLoggeduser() != null && objorder.getObjLoggeduser().getUnifieduserid() != null) {
			sharedbycount = lsordersharedbyRepository
					.countBySharebyunifiedidAndOrdertypeAndSharestatusOrderBySharedbycodeDesc(
							objorder.getObjLoggeduser().getUnifieduserid(), objorder.getFiletype(), 1);
			sharetomecount = lsordersharetoRepository
					.countBySharetounifiedidAndOrdertypeAndSharestatusOrderBySharetocodeDesc(
							objorder.getObjLoggeduser().getUnifieduserid(), objorder.getFiletype(), 1);
		}

		mapOrders.put("orders", lstorder);
		mapOrders.put("pendingcount", pendingcount);
		mapOrders.put("completedcount", completedcount);
		mapOrders.put("sharedbycount", sharedbycount);
		mapOrders.put("sharetomecount", sharetomecount);

		idList = null;

		return lstorder;
	}

	public List<LSsamplefile> getsamplefileIds(List<Integer> lstsamplefilecode, SearchCriteria searchCriteria,
			Integer ismultitenant) {

		List<Long> idList = new ArrayList<Long>();
		String searchtext = searchCriteria.getContentsearch().replace("[", "\\[").replace("]", "\\]");
		if (ismultitenant == 0) {
			Query query = new Query();
			if (searchCriteria.getContentsearchtype() == 1 || searchCriteria.getContentsearchtype() == 3) {
				query.addCriteria(Criteria.where("contentvalues").regex(searchtext, "i"));
			} else if (searchCriteria.getContentsearchtype() == 2) {
				query.addCriteria(Criteria.where("contentparameter").regex(searchtext, "i"));
			}

			// query.addCriteria(Criteria.where("id").in(lstsamplefilecode)).with(new
			// PageRequest(0, 5));

			List<OrderCreation> orders = mongoTemplate.find(query, OrderCreation.class);
			idList = orders.stream().map(OrderCreation::getId).collect(Collectors.toList());
		} else {
			List<CloudOrderCreation> orders = new ArrayList<CloudOrderCreation>();
			if (searchCriteria.getContentsearchtype() == 1 || searchCriteria.getContentsearchtype() == 3) {
				orders = cloudOrderCreationRepository.findByContentvaluesequal("%" + searchtext + "%",
						lstsamplefilecode);
			} else if (searchCriteria.getContentsearchtype() == 2) {
				orders = cloudOrderCreationRepository.findByContentparameterequal("%" + searchtext + "%",
						lstsamplefilecode);
			}
			idList = orders.stream().map(CloudOrderCreation::getId).collect(Collectors.toList());
		}

		List<LSsamplefile> lstsample = new ArrayList<LSsamplefile>();

		if (idList != null && idList.size() > 0) {
			List<Integer> lssample = new ArrayList<Integer>();
			idList.forEach((n) -> lssample.add(Math.toIntExact(n)));

			lstsample = lssamplefileRepository.findByfilesamplecodeIn(lssample);
		}

		return lstsample;
	}

	public List<LSlogilablimsorderdetail> Getorderbytypeandflaganduser(LSlogilablimsorderdetail objorder,
			Map<String, Object> mapOrders) {
		List<LSuserteammapping> lstteammap = lsuserteammappingRepository.findBylsuserMaster(objorder.getLsuserMaster());
		List<LSusersteam> lstteam = lsusersteamRepository.findByLsuserteammappingInAndLssitemaster(lstteammap,
				objorder.getLsuserMaster().getLssitemaster());
		List<LSprojectmaster> lstproject = lsprojectmasterRepository.findByLsusersteamIn(lstteam);
		List<LSlogilablimsorderdetail> lstorder = new ArrayList<LSlogilablimsorderdetail>();
//		List<Logilaborders> lstorder = new ArrayList<Logilaborders>();
		List<Long> lstBatchcode = new ArrayList<Long>();

		long pendingcount = 0;
		long completedcount = 0;
		long Assignedcount = 0;
		long Assignedpendingcount = 0;
		long Assignedcompletedcount = 0;
		long myordercount = 0;
		long myorderpendingcount = 0;
		long myordercompletedcount = 0;

		if (lstproject.size() > 0) {
			List<Integer> lstprojectcode = lsprojectmasterRepository.findProjectcodeByLsusersteamIn(lstteam);

			if (objorder.getOrderflag().equals("N")) {
				if (objorder.getSearchCriteria() != null
						&& objorder.getSearchCriteria().getContentsearch().trim() != "") {

					lstBatchcode = lslogilablimsorderdetailRepository
							.getBatchcodeonFiletypeAndFlagandProjectandcreateddate(objorder.getFiletype(),
									objorder.getOrderflag(), lstprojectcode, objorder.getFromdate(),
									objorder.getTodate());

					lstorder = getordersonsamplefileid(lstBatchcode, objorder);
//					lstorder = getordersonsamplefileidlsorder(lstBatchcode, objorder);

				} else {
					lstorder = lslogilablimsorderdetailRepository
							.findByFiletypeAndOrderflagAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
									objorder.getFiletype(), objorder.getOrderflag(), lstproject, objorder.getFromdate(),
									objorder.getTodate());

				}
			} else if (objorder.getOrderflag().equals("A")) {
				if (objorder.getSearchCriteria() != null
						&& objorder.getSearchCriteria().getContentsearch().trim() != "") {
					lstBatchcode = lslogilablimsorderdetailRepository
							.getBatchcodeonFiletypeAndLsuserMasterAndAssignedtoNotAndCreatedtimestamp(
									objorder.getFiletype(), objorder.getLsuserMaster().getUsercode(),
									objorder.getLsuserMaster().getUsercode(), objorder.getFromdate(),
									objorder.getTodate());

					lstorder = getordersonsamplefileid(lstBatchcode, objorder);
//					lstorder = getordersonsamplefileidlsorder(lstBatchcode, objorder);
					Assignedcount = lstorder.size();

					Assignedpendingcount = lstorder.stream()
							.filter(obj -> "N".equals(obj.getOrderflag() != null ? obj.getOrderflag().trim() : ""))
							.count();
					Assignedcompletedcount = lstorder.stream()
							.filter(obj -> "R".equals(obj.getOrderflag() != null ? obj.getOrderflag().trim() : ""))
							.count();

					if (objorder.getLsuserMaster() != null && objorder.getLsuserMaster().getLsuserActions() != null) {
						LSuserActions objaction = objorder.getLsuserMaster().getLsuserActions();
						if (objaction.getAssignedordershowpending() != null && objaction.getAssignedordershowall() != 1
								&& objaction.getAssignedordershowpending() == 1) {
							lstorder = lstorder.stream().filter(
									obj -> "N".equals(obj.getOrderflag() != null ? obj.getOrderflag().trim() : ""))
									.collect(Collectors.toList());
						} else if (objaction.getAssignedordershowcompleted() != null
								&& objaction.getAssignedordershowall() != 1
								&& objaction.getAssignedordershowcompleted() == 1) {
							lstorder = lstorder.stream().filter(
									obj -> "R".equals(obj.getOrderflag() != null ? obj.getOrderflag().trim() : ""))
									.collect(Collectors.toList());
						}
					}

				} else {
					if (objorder.getLsuserMaster() != null && objorder.getLsuserMaster().getLsuserActions() != null) {
						LSuserActions objaction = objorder.getLsuserMaster().getLsuserActions();
						if (objaction.getAssignedordershowall() != null && objaction.getAssignedordershowall() == 1) {
							lstorder = lslogilablimsorderdetailRepository
									.findByFiletypeAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullOrderByBatchcodeDesc(
											objorder.getFiletype(), objorder.getLsuserMaster(),
											objorder.getLsuserMaster(), objorder.getFromdate(), objorder.getTodate());

//							lstorder = lslogilablimsorderdetailRepository.findByLsuserMasterAndFiletypeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullOrderByBatchcodeDesc(
//									objorder.getLsuserMaster(),objorder.getFiletype(),objorder.getLsuserMaster(), objorder.getFromdate(), objorder.getTodate());
						} else if (objaction.getAssignedordershowpending() != null
								&& objaction.getAssignedordershowpending() == 1) {
							lstorder = lslogilablimsorderdetailRepository
									.findByFiletypeAndOrderflagAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullOrderByBatchcodeDesc(
											objorder.getFiletype(), "N", objorder.getLsuserMaster(),
											objorder.getLsuserMaster(), objorder.getFromdate(), objorder.getTodate());

//							lstorder = lslogilablimsorderdetailRepository.findByOrderflagAndFiletypeAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullOrderByBatchcodeDesc(
//									"N",objorder.getFiletype(),objorder.getLsuserMaster(),objorder.getLsuserMaster(), objorder.getFromdate(), objorder.getTodate());
						} else if (objaction.getAssignedordershowcompleted() != null
								&& objaction.getAssignedordershowcompleted() == 1) {
							lstorder = lslogilablimsorderdetailRepository
									.findByFiletypeAndOrderflagAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullOrderByBatchcodeDesc(
											objorder.getFiletype(), "R", objorder.getLsuserMaster(),
											objorder.getLsuserMaster(), objorder.getFromdate(), objorder.getTodate());

						}
					} else {
						lstorder = lslogilablimsorderdetailRepository
								.findByFiletypeAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullOrderByBatchcodeDesc(
										objorder.getFiletype(), objorder.getLsuserMaster(), objorder.getLsuserMaster(),
										objorder.getFromdate(), objorder.getTodate());

					}

				}
			} else if (objorder.getOrderflag().equals("M")) {
				if (objorder.getSearchCriteria() != null
						&& objorder.getSearchCriteria().getContentsearch().trim() != "") {
					lstBatchcode = lslogilablimsorderdetailRepository
							.getBatchcodeonFiletypeAndFlagandAssignedtoAndCreatedtimestamp(objorder.getFiletype(),
									objorder.getLsuserMaster().getUsercode(), objorder.getFromdate(),
									objorder.getTodate());

					lstorder = getordersonsamplefileid(lstBatchcode, objorder);
//					lstorder = getordersonsamplefileidlsorder(lstBatchcode, objorder);
				}

				// kumaresan
				else {
					if (objorder.getLsuserMaster() != null && objorder.getLsuserMaster().getLsuserActions() != null) {
						LSuserActions objaction = objorder.getLsuserMaster().getLsuserActions();
						if (objaction.getMyordershowall() != null && objaction.getMyordershowall() == 1) {
							lstorder = lslogilablimsorderdetailRepository
									.findByFiletypeAndAssignedtoAndCreatedtimestampBetweenOrderByBatchcodeDesc(
											objorder.getFiletype(), objorder.getLsuserMaster(), objorder.getFromdate(),
											objorder.getTodate());

//							lstorder = lslogilablimsorderdetailRepository.findByAssignedtoAndFiletypeAndCreatedtimestampBetweenOrderByBatchcodeDesc(
//									objorder.getLsuserMaster(),objorder.getFiletype(), objorder.getFromdate(), objorder.getTodate());
						} else if (objaction.getMyordershowpending() != null
								&& objaction.getMyordershowpending() == 1) {
							lstorder = lslogilablimsorderdetailRepository
									.findByFiletypeAndOrderflagAndAssignedtoAndCreatedtimestampBetweenOrderByBatchcodeDesc(
											objorder.getFiletype(), "N", objorder.getLsuserMaster(),
											objorder.getFromdate(), objorder.getTodate());

//							lstorder = lslogilablimsorderdetailRepository.findByOrderflagAndFiletypeAndAssignedtoAndCreatedtimestampBetweenOrderByBatchcodeDesc(
//									"N",objorder.getFiletype(),objorder.getLsuserMaster(), objorder.getFromdate(), objorder.getTodate());
						} else if (objaction.getMyordershowcompleted() != null
								&& objaction.getMyordershowcompleted() == 1) {
							lstorder = lslogilablimsorderdetailRepository
									.findByFiletypeAndOrderflagAndAssignedtoAndCreatedtimestampBetweenOrderByBatchcodeDesc(
											objorder.getFiletype(), "R", objorder.getLsuserMaster(),
											objorder.getFromdate(), objorder.getTodate());

						}
					} else {
						lstorder = lslogilablimsorderdetailRepository
								.findByFiletypeAndAssignedtoAndCreatedtimestampBetweenOrderByBatchcodeDesc(
										objorder.getFiletype(), objorder.getLsuserMaster(), objorder.getFromdate(),
										objorder.getTodate());

					}
				}

			} else {

				if (objorder.getSearchCriteria() != null
						&& objorder.getSearchCriteria().getContentsearch().trim() != "") {
					lstBatchcode = lslogilablimsorderdetailRepository
							.getBatchcodeonFiletypeAndFlagandProjectandCompletedtime(objorder.getFiletype(),
									objorder.getOrderflag(), lstprojectcode, objorder.getFromdate(),
									objorder.getTodate());

					lstorder = getordersonsamplefileid(lstBatchcode, objorder);
//					lstorder = getordersonsamplefileidlsorder(lstBatchcode, objorder);
				} else {
					lstorder = lslogilablimsorderdetailRepository
							.findByFiletypeAndOrderflagAndLsprojectmasterInAndCompletedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
									objorder.getFiletype(), objorder.getOrderflag(), lstproject, objorder.getFromdate(),
									objorder.getTodate());
				}
			}

			if (objorder.getSearchCriteria() != null && objorder.getSearchCriteria().getContentsearch().trim() != "") {

				if (objorder.getOrderflag().equals("N")) {
					pendingcount = lstorder.size();

					completedcount = lslogilablimsorderdetailRepository
							.countByFiletypeAndOrderflagAndLsprojectmasterInAndCompletedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
									objorder.getFiletype(), "R", lstproject, objorder.getFromdate(),
									objorder.getTodate());

					Assignedcount = lslogilablimsorderdetailRepository
							.countByFiletypeAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullOrderByBatchcodeDesc(
									objorder.getFiletype(), objorder.getLsuserMaster(), objorder.getLsuserMaster(),
									objorder.getFromdate(), objorder.getTodate());
					myordercount = lslogilablimsorderdetailRepository
							.countByFiletypeAndAssignedtoAndCreatedtimestampBetweenOrderByBatchcodeDesc(
									objorder.getFiletype(), objorder.getLsuserMaster(), objorder.getFromdate(),
									objorder.getTodate());

				} else if (objorder.getOrderflag().equals("A")) {
					pendingcount = lslogilablimsorderdetailRepository
							.countByFiletypeAndOrderflagAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
									objorder.getFiletype(), "N", lstproject, objorder.getFromdate(),
									objorder.getTodate());
					completedcount = lslogilablimsorderdetailRepository
							.countByFiletypeAndOrderflagAndLsprojectmasterInAndCompletedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
									objorder.getFiletype(), "R", lstproject, objorder.getFromdate(),
									objorder.getTodate());

					myordercount = lslogilablimsorderdetailRepository
							.countByFiletypeAndAssignedtoAndCreatedtimestampBetweenOrderByBatchcodeDesc(
									objorder.getFiletype(), objorder.getLsuserMaster(), objorder.getFromdate(),
									objorder.getTodate());
				} else if (objorder.getOrderflag().equals("M")) {
					pendingcount = lslogilablimsorderdetailRepository
							.countByFiletypeAndOrderflagAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
									objorder.getFiletype(), "N", lstproject, objorder.getFromdate(),
									objorder.getTodate());
					completedcount = lslogilablimsorderdetailRepository
							.countByFiletypeAndOrderflagAndLsprojectmasterInAndCompletedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
									objorder.getFiletype(), "R", lstproject, objorder.getFromdate(),
									objorder.getTodate());

					Assignedcount = lslogilablimsorderdetailRepository
							.countByFiletypeAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullOrderByBatchcodeDesc(
									objorder.getFiletype(), objorder.getLsuserMaster(), objorder.getLsuserMaster(),
									objorder.getFromdate(), objorder.getTodate());

					myordercount = lstorder.size();
				} else {
					completedcount = lstorder.size();

					pendingcount = lslogilablimsorderdetailRepository
							.countByFiletypeAndOrderflagAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
									objorder.getFiletype(), "N", lstproject, objorder.getFromdate(),
									objorder.getTodate());
					Assignedcount = lslogilablimsorderdetailRepository
							.countByFiletypeAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullOrderByBatchcodeDesc(
									objorder.getFiletype(), objorder.getLsuserMaster(), objorder.getLsuserMaster(),
									objorder.getFromdate(), objorder.getTodate());

					myordercount = lslogilablimsorderdetailRepository
							.countByFiletypeAndAssignedtoAndCreatedtimestampBetweenOrderByBatchcodeDesc(
									objorder.getFiletype(), objorder.getLsuserMaster(), objorder.getFromdate(),
									objorder.getTodate());
				}
			} else {
				pendingcount = lslogilablimsorderdetailRepository
						.countByFiletypeAndOrderflagAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
								objorder.getFiletype(), "N", lstproject, objorder.getFromdate(), objorder.getTodate());

				completedcount = lslogilablimsorderdetailRepository
						.countByFiletypeAndOrderflagAndLsprojectmasterInAndCompletedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
								objorder.getFiletype(), "R", lstproject, objorder.getFromdate(), objorder.getTodate());

				Assignedcount = lslogilablimsorderdetailRepository
						.countByFiletypeAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullOrderByBatchcodeDesc(
								objorder.getFiletype(), objorder.getLsuserMaster(), objorder.getLsuserMaster(),
								objorder.getFromdate(), objorder.getTodate());

				if (objorder.getOrderflag().equals("A")) {
					Assignedpendingcount = lslogilablimsorderdetailRepository
							.countByFiletypeAndOrderflagAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullOrderByBatchcodeDesc(
									objorder.getFiletype(), "N", objorder.getLsuserMaster(), objorder.getLsuserMaster(),
									objorder.getFromdate(), objorder.getTodate());

					Assignedcompletedcount = lslogilablimsorderdetailRepository
							.countByFiletypeAndOrderflagAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullOrderByBatchcodeDesc(
									objorder.getFiletype(), "R", objorder.getLsuserMaster(), objorder.getLsuserMaster(),
									objorder.getFromdate(), objorder.getTodate());
				} else if (objorder.getOrderflag().equals("M")) {
					// kumaresan
					myorderpendingcount = lslogilablimsorderdetailRepository
							.countByFiletypeAndOrderflagAndAssignedtoAndCreatedtimestampBetweenOrderByBatchcodeDesc(
									objorder.getFiletype(), "N", objorder.getLsuserMaster(), objorder.getFromdate(),
									objorder.getTodate());

					myordercompletedcount = lslogilablimsorderdetailRepository
							.countByFiletypeAndOrderflagAndAssignedtoAndCreatedtimestampBetweenOrderByBatchcodeDesc(
									objorder.getFiletype(), "R", objorder.getLsuserMaster(), objorder.getFromdate(),
									objorder.getTodate());
				}
				myordercount = lslogilablimsorderdetailRepository
						.countByFiletypeAndAssignedtoAndCreatedtimestampBetweenOrderByBatchcodeDesc(
								objorder.getFiletype(), objorder.getLsuserMaster(), objorder.getFromdate(),
								objorder.getTodate());
			}

		}
		if (objorder.getObjsilentaudit() != null) {
			objorder.getObjsilentaudit().setTableName("LSlogilablimsorderdetail");
			try {
				objorder.getObjsilentaudit().setTransactiondate(commonfunction.getCurrentUtcTime());
			} catch (ParseException e) {

				e.printStackTrace();
			}
			lscfttransactionRepository.save(objorder.getObjsilentaudit());
		}

		long sharedbycount = 0;
		long sharetomecount = 0;

		if (objorder.getLsuserMaster() != null && objorder.getLsuserMaster().getUnifieduserid() != null) {
			sharedbycount = lsordersharedbyRepository
					.countBySharebyunifiedidAndOrdertypeAndSharestatusOrderBySharedbycodeDesc(
							objorder.getLsuserMaster().getUnifieduserid(), objorder.getFiletype(), 1);
			sharetomecount = lsordersharetoRepository
					.countBySharetounifiedidAndOrdertypeAndSharestatusOrderBySharetocodeDesc(
							objorder.getLsuserMaster().getUnifieduserid(), objorder.getFiletype(), 1);
		}

		mapOrders.put("orders", lstorder);
		mapOrders.put("pendingcount", pendingcount);
		mapOrders.put("completedcount", completedcount);
		mapOrders.put("Assignedcount", Assignedcount);
		mapOrders.put("myordercount", myordercount);

		mapOrders.put("Assignedpendingcount", Assignedpendingcount);
		mapOrders.put("Assignedcompletedcount", Assignedcompletedcount);
		mapOrders.put("myorderpendingcount", myorderpendingcount);
		mapOrders.put("myordercompletedcount", myordercompletedcount);

		mapOrders.put("sharedbycount", sharedbycount);
		mapOrders.put("sharetomecount", sharetomecount);

		return lstorder;
	}

	public List<Logilaborderssh> GetorderbytypeandflaganduserOrdersonly(LSlogilablimsorderdetail objorder,
			Map<String, Object> mapOrders) {

//		List<LSprojectmaster> lstproject = objorder.getLstproject();
		List<Logilaborderssh> lstorder = new ArrayList<Logilaborderssh>();
		List<Long> lstBatchcode = new ArrayList<Long>();

//		List<LSworkflow> lstworkflow = GetWorkflowonuser(objorder.getLsuserMaster().getLsusergrouptrans());

//		List<LSworkflow> lstworkflow = objorder.getLstworkflow();

		long pendingcount = 0;
		long completedcount = 0;
		long Assignedcount = 0;
		long Assignedpendingcount = 0;
		long Assignedcompletedcount = 0;
		long myordercount = 0;
		long myorderpendingcount = 0;
		long myordercompletedcount = 0;

		if (objorder.getLstproject() != null && objorder.getLstproject().size() > 0) {
			List<Integer> lstprojectcode = objorder.getLstproject().stream().map(LSprojectmaster::getProjectcode)
					.collect(Collectors.toList());

			if (objorder.getOrderflag().equals("N")) {
				if (objorder.getSearchCriteria() != null
						&& objorder.getSearchCriteria().getContentsearch().trim() != "") {

					lstBatchcode = lslogilablimsorderdetailRepository
							.getBatchcodeonFiletypeAndFlagandProjectandcreateddate(objorder.getFiletype(),
									objorder.getOrderflag(), lstprojectcode, objorder.getFromdate(),
									objorder.getTodate());

					lstorder = getordersonsamplefileidlsorder(lstBatchcode, objorder);

				} else {

					lstorder = lslogilablimsorderdetailRepository
							.findByOrderflagAndFiletypeAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
									objorder.getOrderflag(), objorder.getFiletype(), objorder.getLstproject(),
									objorder.getFromdate(), objorder.getTodate());
				}
			} else if (objorder.getOrderflag().equals("A")) {
				if (objorder.getSearchCriteria() != null
						&& objorder.getSearchCriteria().getContentsearch().trim() != "") {
					lstBatchcode = lslogilablimsorderdetailRepository
							.getBatchcodeonFiletypeAndLsuserMasterAndAssignedtoNotAndCreatedtimestamp(
									objorder.getFiletype(), objorder.getLsuserMaster().getUsercode(),
									objorder.getLsuserMaster().getUsercode(), objorder.getFromdate(),
									objorder.getTodate());

					lstorder = getordersonsamplefileidlsorder(lstBatchcode, objorder);
					Assignedcount = lstorder.size();

					Assignedpendingcount = lstorder.stream()
							.filter(obj -> "N".equals(obj.getOf() != null ? obj.getOf().trim() : "")).count();
					Assignedcompletedcount = lstorder.stream()
							.filter(obj -> "R".equals(obj.getOf() != null ? obj.getOf().trim() : "")).count();

					if (objorder.getLsuserMaster() != null && objorder.getLsuserMaster().getLsuserActions() != null) {
						LSuserActions objaction = objorder.getLsuserMaster().getLsuserActions();
						if (objaction.getAssignedordershowpending() != null && objaction.getAssignedordershowall() != 1
								&& objaction.getAssignedordershowpending() == 1) {
							lstorder = lstorder.stream()
									.filter(obj -> "N".equals(obj.getOf() != null ? obj.getOf().trim() : ""))
									.collect(Collectors.toList());
						} else if (objaction.getAssignedordershowcompleted() != null
								&& objaction.getAssignedordershowall() != 1
								&& objaction.getAssignedordershowcompleted() == 1) {
							lstorder = lstorder.stream()
									.filter(obj -> "R".equals(obj.getOf() != null ? obj.getOf().trim() : ""))
									.collect(Collectors.toList());
						}
					}

				} else {
					if (objorder.getLsuserMaster() != null && objorder.getLsuserMaster().getLsuserActions() != null) {
						LSuserActions objaction = objorder.getLsuserMaster().getLsuserActions();
						if (objaction.getAssignedordershowall() != null && objaction.getAssignedordershowall() == 1) {

//							lstorder = lslogilablimsorderdetailRepository
//									.findByLsuserMasterAndFiletypeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullOrderByBatchcodeDesc(
//											objorder.getLsuserMaster(), objorder.getFiletype(),
//											objorder.getLsuserMaster(), objorder.getFromdate(), objorder.getTodate());
						} else if (objaction.getAssignedordershowpending() != null
								&& objaction.getAssignedordershowpending() == 1) {

							lstorder = lslogilablimsorderdetailRepository
									.findByOrderflagAndFiletypeAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullOrderByBatchcodeDesc(
											"N", objorder.getFiletype(), objorder.getLsuserMaster(),
											objorder.getLsuserMaster(), objorder.getFromdate(), objorder.getTodate());
						} else if (objaction.getAssignedordershowcompleted() != null
								&& objaction.getAssignedordershowcompleted() == 1) {

							lstorder = lslogilablimsorderdetailRepository
									.findByOrderflagAndFiletypeAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullOrderByBatchcodeDesc(
											"R", objorder.getFiletype(), objorder.getLsuserMaster(),
											objorder.getLsuserMaster(), objorder.getFromdate(), objorder.getTodate());
						}
					} else {

//						lstorder = lslogilablimsorderdetailRepository
//								.findByLsuserMasterAndFiletypeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullOrderByBatchcodeDesc(
//										objorder.getLsuserMaster(), objorder.getFiletype(), objorder.getLsuserMaster(),
//										objorder.getFromdate(), objorder.getTodate());
					}

				}
			} else if (objorder.getOrderflag().equals("M")) {
				if (objorder.getSearchCriteria() != null
						&& objorder.getSearchCriteria().getContentsearch().trim() != "") {
					lstBatchcode = lslogilablimsorderdetailRepository
							.getBatchcodeonFiletypeAndFlagandAssignedtoAndCreatedtimestamp(objorder.getFiletype(),
									objorder.getLsuserMaster().getUsercode(), objorder.getFromdate(),
									objorder.getTodate());

					lstorder = getordersonsamplefileidlsorder(lstBatchcode, objorder);
				}

				// kumaresan
				else {
					if (objorder.getLsuserMaster() != null && objorder.getLsuserMaster().getLsuserActions() != null) {
						LSuserActions objaction = objorder.getLsuserMaster().getLsuserActions();
						if (objaction.getMyordershowall() != null && objaction.getMyordershowall() == 1) {

//							lstorder = lslogilablimsorderdetailRepository
//									.findByAssignedtoAndFiletypeAndCreatedtimestampBetweenOrderByBatchcodeDesc(
//											objorder.getLsuserMaster(), objorder.getFiletype(), objorder.getFromdate(),
//											objorder.getTodate());
						} else if (objaction.getMyordershowpending() != null
								&& objaction.getMyordershowpending() == 1) {

							lstorder = lslogilablimsorderdetailRepository
									.findByOrderflagAndFiletypeAndAssignedtoAndCreatedtimestampBetweenOrderByBatchcodeDesc(
											"N", objorder.getFiletype(), objorder.getLsuserMaster(),
											objorder.getFromdate(), objorder.getTodate());
						} else if (objaction.getMyordershowcompleted() != null
								&& objaction.getMyordershowcompleted() == 1) {

							lstorder = lslogilablimsorderdetailRepository
									.findByOrderflagAndFiletypeAndAssignedtoAndCreatedtimestampBetweenOrderByBatchcodeDesc(
											"R", objorder.getFiletype(), objorder.getLsuserMaster(),
											objorder.getFromdate(), objorder.getTodate());
						}
					} else {

//						lstorder = lslogilablimsorderdetailRepository
//								.findByAssignedtoAndFiletypeAndCreatedtimestampBetweenOrderByBatchcodeDesc(
//										objorder.getLsuserMaster(), objorder.getFiletype(), objorder.getFromdate(),
//										objorder.getTodate());
					}
				}

			} else {

				if (objorder.getSearchCriteria() != null
						&& objorder.getSearchCriteria().getContentsearch().trim() != "") {
					lstBatchcode = lslogilablimsorderdetailRepository
							.getBatchcodeonFiletypeAndFlagandProjectandCompletedtime(objorder.getFiletype(),
									objorder.getOrderflag(), lstprojectcode, objorder.getFromdate(),
									objorder.getTodate());

					lstorder = getordersonsamplefileidlsorder(lstBatchcode, objorder);
				} else {

					lstorder = lslogilablimsorderdetailRepository
							.findByOrderflagAndFiletypeAndLsprojectmasterInAndCompletedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
									objorder.getOrderflag(), objorder.getFiletype(), objorder.getLstproject(),
									objorder.getFromdate(), objorder.getTodate());
				}
			}

			if (objorder.getSearchCriteria() != null && objorder.getSearchCriteria().getContentsearch().trim() != "") {

				if (objorder.getOrderflag().equals("N")) {
					pendingcount = lstorder.size();

					completedcount = lslogilablimsorderdetailRepository
							.countByFiletypeAndOrderflagAndLsprojectmasterInAndCompletedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
									objorder.getFiletype(), "R", objorder.getLstproject(), objorder.getFromdate(),
									objorder.getTodate());

					Assignedcount = lslogilablimsorderdetailRepository
							.countByFiletypeAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullOrderByBatchcodeDesc(
									objorder.getFiletype(), objorder.getLsuserMaster(), objorder.getLsuserMaster(),
									objorder.getFromdate(), objorder.getTodate());
					myordercount = lslogilablimsorderdetailRepository
							.countByFiletypeAndAssignedtoAndCreatedtimestampBetweenOrderByBatchcodeDesc(
									objorder.getFiletype(), objorder.getLsuserMaster(), objorder.getFromdate(),
									objorder.getTodate());

				} else if (objorder.getOrderflag().equals("A")) {
					pendingcount = lslogilablimsorderdetailRepository
							.countByFiletypeAndOrderflagAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
									objorder.getFiletype(), "N", objorder.getLstproject(), objorder.getFromdate(),
									objorder.getTodate());
					completedcount = lslogilablimsorderdetailRepository
							.countByFiletypeAndOrderflagAndLsprojectmasterInAndCompletedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
									objorder.getFiletype(), "R", objorder.getLstproject(), objorder.getFromdate(),
									objorder.getTodate());

					myordercount = lslogilablimsorderdetailRepository
							.countByFiletypeAndAssignedtoAndCreatedtimestampBetweenOrderByBatchcodeDesc(
									objorder.getFiletype(), objorder.getLsuserMaster(), objorder.getFromdate(),
									objorder.getTodate());
				} else if (objorder.getOrderflag().equals("M")) {
					pendingcount = lslogilablimsorderdetailRepository
							.countByFiletypeAndOrderflagAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
									objorder.getFiletype(), "N", objorder.getLstproject(), objorder.getFromdate(),
									objorder.getTodate());
					completedcount = lslogilablimsorderdetailRepository
							.countByFiletypeAndOrderflagAndLsprojectmasterInAndCompletedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
									objorder.getFiletype(), "R", objorder.getLstproject(), objorder.getFromdate(),
									objorder.getTodate());

					Assignedcount = lslogilablimsorderdetailRepository
							.countByFiletypeAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullOrderByBatchcodeDesc(
									objorder.getFiletype(), objorder.getLsuserMaster(), objorder.getLsuserMaster(),
									objorder.getFromdate(), objorder.getTodate());

					myordercount = lstorder.size();
				} else {
					completedcount = lstorder.size();

					pendingcount = lslogilablimsorderdetailRepository
							.countByFiletypeAndOrderflagAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
									objorder.getFiletype(), "N", objorder.getLstproject(), objorder.getFromdate(),
									objorder.getTodate());
					Assignedcount = lslogilablimsorderdetailRepository
							.countByFiletypeAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullOrderByBatchcodeDesc(
									objorder.getFiletype(), objorder.getLsuserMaster(), objorder.getLsuserMaster(),
									objorder.getFromdate(), objorder.getTodate());

					myordercount = lslogilablimsorderdetailRepository
							.countByFiletypeAndAssignedtoAndCreatedtimestampBetweenOrderByBatchcodeDesc(
									objorder.getFiletype(), objorder.getLsuserMaster(), objorder.getFromdate(),
									objorder.getTodate());
				}
			} else {
				pendingcount = lslogilablimsorderdetailRepository
						.countByFiletypeAndOrderflagAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
								objorder.getFiletype(), "N", objorder.getLstproject(), objorder.getFromdate(),
								objorder.getTodate());

				completedcount = lslogilablimsorderdetailRepository
						.countByFiletypeAndOrderflagAndLsprojectmasterInAndCompletedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
								objorder.getFiletype(), "R", objorder.getLstproject(), objorder.getFromdate(),
								objorder.getTodate());

				Assignedcount = lslogilablimsorderdetailRepository
						.countByFiletypeAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullOrderByBatchcodeDesc(
								objorder.getFiletype(), objorder.getLsuserMaster(), objorder.getLsuserMaster(),
								objorder.getFromdate(), objorder.getTodate());

				if (objorder.getOrderflag().equals("A")) {
					Assignedpendingcount = lslogilablimsorderdetailRepository
							.countByFiletypeAndOrderflagAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullOrderByBatchcodeDesc(
									objorder.getFiletype(), "N", objorder.getLsuserMaster(), objorder.getLsuserMaster(),
									objorder.getFromdate(), objorder.getTodate());

					Assignedcompletedcount = lslogilablimsorderdetailRepository
							.countByFiletypeAndOrderflagAndLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullOrderByBatchcodeDesc(
									objorder.getFiletype(), "R", objorder.getLsuserMaster(), objorder.getLsuserMaster(),
									objorder.getFromdate(), objorder.getTodate());
				} else if (objorder.getOrderflag().equals("M")) {
					// kumaresan
					myorderpendingcount = lslogilablimsorderdetailRepository
							.countByFiletypeAndOrderflagAndAssignedtoAndCreatedtimestampBetweenOrderByBatchcodeDesc(
									objorder.getFiletype(), "N", objorder.getLsuserMaster(), objorder.getFromdate(),
									objorder.getTodate());

					myordercompletedcount = lslogilablimsorderdetailRepository
							.countByFiletypeAndOrderflagAndAssignedtoAndCreatedtimestampBetweenOrderByBatchcodeDesc(
									objorder.getFiletype(), "R", objorder.getLsuserMaster(), objorder.getFromdate(),
									objorder.getTodate());
				}
				myordercount = lslogilablimsorderdetailRepository
						.countByFiletypeAndAssignedtoAndCreatedtimestampBetweenOrderByBatchcodeDesc(
								objorder.getFiletype(), objorder.getLsuserMaster(), objorder.getFromdate(),
								objorder.getTodate());
			}

		}

		if (objorder.getObjsilentaudit() != null) {
			objorder.getObjsilentaudit().setTableName("LSlogilablimsorderdetail");
		}

		long sharedbycount = 0;
		long sharetomecount = 0;

		if (objorder.getLsuserMaster() != null && objorder.getLsuserMaster().getUnifieduserid() != null) {
			sharedbycount = lsordersharedbyRepository
					.countBySharebyunifiedidAndOrdertypeAndSharestatusOrderBySharedbycodeDesc(
							objorder.getLsuserMaster().getUnifieduserid(), objorder.getFiletype(), 1);
			sharetomecount = lsordersharetoRepository
					.countBySharetounifiedidAndOrdertypeAndSharestatusOrderBySharetocodeDesc(
							objorder.getLsuserMaster().getUnifieduserid(), objorder.getFiletype(), 1);
		}

		lstorder.forEach(objorderDetail -> objorderDetail.setLw(objorder.getLstworkflow()));

		mapOrders.put("orders", lstorder);
		mapOrders.put("pendingcount", pendingcount);
		mapOrders.put("completedcount", completedcount);
		mapOrders.put("Assignedcount", Assignedcount);
		mapOrders.put("myordercount", myordercount);

		mapOrders.put("Assignedpendingcount", Assignedpendingcount);
		mapOrders.put("Assignedcompletedcount", Assignedcompletedcount);
		mapOrders.put("myorderpendingcount", myorderpendingcount);
		mapOrders.put("myordercompletedcount", myordercompletedcount);

		mapOrders.put("sharedbycount", sharedbycount);
		mapOrders.put("sharetomecount", sharetomecount);

		lstBatchcode = null;

		return lstorder;
	}

	public List<LSlogilablimsorderdetail> getordersonsamplefileid(List<Long> lstBatchcode,
			LSlogilablimsorderdetail objorder) {
		List<LSsamplefile> idList = new ArrayList<LSsamplefile>();
		List<LSlogilablimsorderdetail> lstorder = new ArrayList<LSlogilablimsorderdetail>();
		List<Integer> lstsamplefilecode = new ArrayList<Integer>();

		if (lstBatchcode != null && lstBatchcode.size() > 0) {
			lstsamplefilecode = lssamplefileRepository.getFilesamplecodeByBatchcodeIn(lstBatchcode);
		}

		if (lstsamplefilecode != null && lstsamplefilecode.size() > 0) {
			idList = getsamplefileIds(lstsamplefilecode, objorder.getSearchCriteria(), objorder.getIsmultitenant());
			if (idList != null && idList.size() > 0) {
				lstorder = lslogilablimsorderdetailRepository
						.findByFiletypeAndLssamplefileInOrderByBatchcodeDesc(objorder.getFiletype(), idList);
			}
		}

		return lstorder;
	}

	public List<Logilaborderssh> getordersonsamplefileidlsorder(List<Long> lstBatchcode,
			LSlogilablimsorderdetail objorder) {
		List<LSsamplefile> idList = new ArrayList<LSsamplefile>();
		List<Logilaborderssh> lstorder = new ArrayList<Logilaborderssh>();
		List<Integer> lstsamplefilecode = new ArrayList<Integer>();

		if (lstBatchcode != null && lstBatchcode.size() > 0) {
			lstsamplefilecode = lssamplefileRepository.getFilesamplecodeByBatchcodeIn(lstBatchcode);
		}

		if (lstsamplefilecode != null && lstsamplefilecode.size() > 0) {
			idList = getsamplefileIds(lstsamplefilecode, objorder.getSearchCriteria(), objorder.getIsmultitenant());
			if (idList != null && idList.size() > 0) {
				lstorder = lslogilablimsorderdetailRepository
						.findByLssamplefileInAndFiletypeOrderByBatchcodeDesc(idList, objorder.getFiletype());
			}
		}

		return lstorder;
	}

	public List<LSlogilablimsorderdetail> Getorderbytypeandflaglazy(LSlogilablimsorderdetail objorder,
			Map<String, Object> mapOrders) {
//		List<LSlogilablimsorderdetail> lstorder = new ArrayList<LSlogilablimsorderdetail>();
		if (objorder.getOrderflag().equals("N")) {
			return lslogilablimsorderdetailRepository
					.findFirst10ByFiletypeAndOrderflagAndBatchcodeLessThanAndCreatedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
							objorder.getFiletype(), objorder.getOrderflag(), objorder.getBatchcode(),
							objorder.getFromdate(), objorder.getTodate());
		} else {
			return lslogilablimsorderdetailRepository
					.findFirst10ByFiletypeAndOrderflagAndBatchcodeLessThanAndCompletedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
							objorder.getFiletype(), objorder.getOrderflag(), objorder.getBatchcode(),
							objorder.getFromdate(), objorder.getTodate());
		}
	}

	public List<LSlogilablimsorderdetail> Getorderbytypeandflaganduserlazy(LSlogilablimsorderdetail objorder,
			Map<String, Object> mapOrders) {
		List<LSuserteammapping> lstteammap = lsuserteammappingRepository.findBylsuserMaster(objorder.getLsuserMaster());
		List<LSusersteam> lstteam = lsusersteamRepository.findByLsuserteammappingInAndLssitemaster(lstteammap,
				objorder.getLsuserMaster().getLssitemaster());
//		List<LSprojectmaster> lstproject = lsprojectmasterRepository.findByLsusersteamIn(lstteam);

		if (objorder.getOrderflag().equals("N")) {
			return lslogilablimsorderdetailRepository
					.findByFiletypeAndOrderflagAndBatchcodeLessThanAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
							objorder.getFiletype(), objorder.getOrderflag(), objorder.getBatchcode(),
							lsprojectmasterRepository.findByLsusersteamIn(lstteam), objorder.getFromdate(),
							objorder.getTodate());
		} else {
			return lslogilablimsorderdetailRepository
					.findByFiletypeAndOrderflagAndBatchcodeLessThanAndLsprojectmasterInAndCompletedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
							objorder.getFiletype(), objorder.getOrderflag(), objorder.getBatchcode(),
							lsprojectmasterRepository.findByLsusersteamIn(lstteam), objorder.getFromdate(),
							objorder.getTodate());
		}

//		mapOrders.put("orders", lstorder);

//		return lstorder;
	}

	public List<LSlogilablimsorderdetail> Getorderallbytypeandflaglazy(LSlogilablimsorderdetail objorder,
			Map<String, Object> mapOrders) {
		List<LSlogilablimsorderdetail> lstorder = new ArrayList<LSlogilablimsorderdetail>();
		if (objorder.getOrderflag().equals("N")) {
			lstorder = lslogilablimsorderdetailRepository
					.findByFiletypeAndOrderflagAndBatchcodeLessThanAndCreatedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
							objorder.getFiletype(), objorder.getOrderflag(), objorder.getBatchcode(),
							objorder.getFromdate(), objorder.getTodate());
		} else {
			lstorder = lslogilablimsorderdetailRepository
					.findByFiletypeAndOrderflagAndBatchcodeLessThanAndCompletedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
							objorder.getFiletype(), objorder.getOrderflag(), objorder.getBatchcode(),
							objorder.getFromdate(), objorder.getTodate());
		}

		mapOrders.put("orders", lstorder);

		return lstorder;
	}

	public List<LSlogilablimsorderdetail> Getorderallbytypeandflaganduserlazy(LSlogilablimsorderdetail objorder,
			Map<String, Object> mapOrders) {
		List<LSuserteammapping> lstteammap = lsuserteammappingRepository.findBylsuserMaster(objorder.getLsuserMaster());
		List<LSusersteam> lstteam = lsusersteamRepository.findByLsuserteammappingInAndLssitemaster(lstteammap,
				objorder.getLsuserMaster().getLssitemaster());
		List<LSprojectmaster> lstproject = lsprojectmasterRepository.findByLsusersteamIn(lstteam);
		List<LSlogilablimsorderdetail> lstorder = new ArrayList<LSlogilablimsorderdetail>();

		if (objorder.getOrderflag().equals("N")) {
			lstorder = lslogilablimsorderdetailRepository
					.findFirst10ByFiletypeAndOrderflagAndBatchcodeLessThanAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
							objorder.getFiletype(), objorder.getOrderflag(), objorder.getBatchcode(), lstproject,
							objorder.getFromdate(), objorder.getTodate());
		} else {
			lstorder = lslogilablimsorderdetailRepository
					.findFirst10ByFiletypeAndOrderflagAndBatchcodeLessThanAndLsprojectmasterInAndCompletedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
							objorder.getFiletype(), objorder.getOrderflag(), objorder.getBatchcode(), lstproject,
							objorder.getFromdate(), objorder.getTodate());
		}

		mapOrders.put("orders", lstorder);

		return lstorder;
	}

	public List<LSworkflow> GetWorkflowonUser(LSuserMaster objuser) {
//		List<LSworkflowgroupmapping> lsworkflowgroupmapping = lsworkflowgroupmappingRepository
//				.findBylsusergroup(objuser.getLsusergroup());
		List<LSworkflow> lsworkflow = lsworkflowRepository.findByLsworkflowgroupmappingInOrderByWorkflowcodeDesc(
				lsworkflowgroupmappingRepository.findBylsusergroup(objuser.getLsusergroup()));

		return lsworkflow;
	}

	public Map<String, Object> GetWorkflowanduseronUsercode(LSuserMaster usercode) {
//		LSuserMaster objuser = lsuserMasterRepository.findByusercode(usercode.getUsercode());
//
//		LSusergroup lsusergroup = usercode.getLsusergrouptrans();

		Map<String, Object> maplogindetails = new HashMap<String, Object>();
		maplogindetails.put("workflow", GetWorkflowonuser(usercode.getLsusergrouptrans()));
		maplogindetails.put("user", lsuserMasterRepository.findByusercode(usercode.getUsercode()));
		return maplogindetails;
	}

	public List<LSworkflow> GetWorkflowonuser(LSusergroup lsusergroup) {

		return lsworkflowRepository.findByLsworkflowgroupmappingInOrderByWorkflowcodeDesc(
				lsworkflowgroupmappingRepository.findBylsusergroup(lsusergroup));
	}

	@SuppressWarnings("resource")
	public LogilabOrderDetails GetorderStatus(LSlogilablimsorderdetail objorder) throws IOException, ParseException {

		LogilabOrderDetails objupdatedorder = logilablimsorderdetailsRepository
				.findByBatchcode(objorder.getBatchcode());
		objupdatedorder.setResponse(new Response());

		if (objupdatedorder.getLockeduser() != null) {

			if (objupdatedorder.getFiletype() != 1 && objupdatedorder.getFiletype() != 0
					&& !objupdatedorder.getOrderflag().trim().equalsIgnoreCase("R")
					&& objupdatedorder.getAssignedto() == null
					&& objupdatedorder.getLockeduser().equals(objorder.getObjLoggeduser().getUsercode())) {

				objupdatedorder.getResponse().setInformation("IDS_SAME_USER_OPEN");
				objupdatedorder.getResponse().setStatus(false);

				return objupdatedorder;
			} else if (!objorder.getIsmultitenant().equals(2) && objupdatedorder.getFiletype() != 0) {
				LSuserMaster user = new LSuserMaster();
				user.setUsercode(objupdatedorder.getLockeduser());
				List<LSactiveUser> LSactiveUsr = lSactiveUserRepository.findByLsusermaster(user);
				if (LSactiveUsr.isEmpty()) {
					objupdatedorder.setLockeduser(objorder.getObjLoggeduser().getUsercode());
					objupdatedorder.setLockedusername(objorder.getObjLoggeduser().getUsername());
					objupdatedorder.setActiveuser(objorder.getActiveuser());
					logilablimsorderdetailsRepository.UpdateOrderData(objorder.getObjLoggeduser().getUsercode(),
							objorder.getObjLoggeduser().getUsername(), objorder.getActiveuser(),
							objupdatedorder.getBatchcode());
				}
				objupdatedorder.setIsLock(1);
//					lslogilablimsorderdetailRepository.save(objupdatedorder);

			}

		} else if (!objorder.getIsmultitenant().equals(2)) {
			objupdatedorder.setLockeduser(objorder.getObjLoggeduser().getUsercode());
			objupdatedorder.setLockedusername(objorder.getObjLoggeduser().getUsername());
			objupdatedorder.setActiveuser(objorder.getActiveuser());
			objupdatedorder.setIsLock(1);
//				lslogilablimsorderdetailRepository.save(objupdatedorder);
			logilablimsorderdetailsRepository.UpdateOrderData(objorder.getObjLoggeduser().getUsercode(),
					objorder.getObjLoggeduser().getUsername(), objorder.getActiveuser(),
					objupdatedorder.getBatchcode());
		}
//			System.out.println("Locked User End  " + dtf.format(LocalDateTime.now()));
//			if (lsLogilaborders != null && lsLogilaborders.size() > 0) {
//				int i = 0;
//				while (lsLogilaborders.size() > i) {
//					lsorderno.add(lsLogilaborders.get(i).getOrderid().toString());
//					i++;
//				}
//			}
//			objupdatedorder.setLsLSlogilablimsorder(lsLogilaborders);
//			System.out.println("Work Flow Start  " + dtf.format(LocalDateTime.now()));

		// kumu
//		if (objupdatedorder.getLsprojectmaster() != null && objorder.getLstworkflow() != null) {
//			List<Integer> lstworkflowcode = objorder.getLstworkflow().stream().map(LSworkflow::getWorkflowcode)
//					.collect(Collectors.toList());
//			if (objorder.getLstworkflow() != null && objupdatedorder.getLsworkflow() != null
//					&& lstworkflowcode.contains(objupdatedorder.getLsworkflow().getWorkflowcode())) {
//				objupdatedorder.setCanuserprocess(true);
//			} else {
//				objupdatedorder.setCanuserprocess(false);
//			}
//		}

//		}

//			System.out.println("Work Flow ENd  " + dtf.format(LocalDateTime.now()));
		List<LsOrderattachments> lstattach = lsOrderattachmentsRepository
				.findByBatchcodeOrderByAttachmentcodeDesc(objorder.getBatchcode());
		objupdatedorder.setLsOrderattachments(lstattach);

		List<ELNFileAttachments> lstelnfileattach = elnFileattachmentsRepository
				.findByBatchcodeOrderByAttachmentcodeDesc(objorder.getBatchcode());
		objupdatedorder.setElnfileAttachments(lstelnfileattach);

		List<LSlogilablimsorder> lsLogilaborders = lslogilablimsorderRepository
				.findBybatchid(objupdatedorder.getBatchid());
		objupdatedorder.setLsLSlogilablimsorder(lsLogilaborders);

		if (objupdatedorder.getFiletype() == 0) {
			List<Lsbatchdetails> lstbatch = LsbatchdetailsRepository.findByBatchcode(objupdatedorder.getBatchcode());
			objupdatedorder.setLsbatchdetails(lstbatch);
			objupdatedorder.setCanuserprocess(true);
			objupdatedorder
					.setLstestparameter(lStestparameterRepository.findByntestcode(objupdatedorder.getTestcode()));
		}
		lsLogilaborders = null;

		if (objupdatedorder.getLockeduser() != null && objorder.getObjLoggeduser() != null
				&& objupdatedorder.getLockeduser().equals(objorder.getObjLoggeduser().getUsercode())) {
			objupdatedorder.setIsLockbycurrentuser(1);
		} else {
			objupdatedorder.setIsLockbycurrentuser(0);
		}
//			System.out.println("Work Flow Final Step Start  " + dtf.format(LocalDateTime.now()));
		if (objupdatedorder.getFiletype() != 0 && objupdatedorder.getOrderflag().toString().trim().equals("N")) {
			LSworkflow objlastworkflow = lsworkflowRepository.findTopByAndLssitemasterAndStatusOrderByWorkflowcodeDesc(
					objorder.getObjLoggeduser().getLssitemaster(), 1);
			if (objlastworkflow != null && objupdatedorder.getLsworkflow() != null
					&& objupdatedorder.getLsworkflow().equals(objlastworkflow)) {
				objupdatedorder.setIsFinalStep(1);
			} else {
				objupdatedorder.setIsFinalStep(0);
			}
		}
//			System.out.println("Work Flow Final Step End  " + dtf.format(LocalDateTime.now()));
//			System.out.println("Get File Content Start  " + dtf.format(LocalDateTime.now()));
		if (objupdatedorder.getLssamplefile() != null) {
			if (objorder.getIsmultitenant() == 1 || objorder.getIsmultitenant() == 2) {
				CloudOrderCreation objCreation = cloudOrderCreationRepository
						.findTop1ById((long) objupdatedorder.getLssamplefile().getFilesamplecode());
				if (objCreation != null && objCreation.getContainerstored() == 0) {
					objupdatedorder.getLssamplefile().setFilecontent(objCreation.getContent());
				} else {
					objupdatedorder.getLssamplefile().setFilecontent(
							objCloudFileManipulationservice.retrieveCloudSheets(objCreation.getFileuid(),
									commonfunction.getcontainername(objorder.getIsmultitenant(),
											TenantContext.getCurrentTenant()) + "ordercreation"));
				}
			} else {

				GridFSFile largefile = gridFsTemplate.findOne(new Query(Criteria.where("filename")
						.is("order_" + objupdatedorder.getLssamplefile().getFilesamplecode())));
				if (largefile == null) {
					largefile = gridFsTemplate.findOne(new Query(Criteria.where("_id")
							.is("order_" + objupdatedorder.getLssamplefile().getFilesamplecode())));
				}

				if (largefile != null) {
					GridFsResource resource = gridFsTemplate.getResource(largefile.getFilename());
					objupdatedorder.getLssamplefile().setFilecontent(
							new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))
									.lines().collect(Collectors.joining("\n")));
				} else {

					if (mongoTemplate.findById(objupdatedorder.getLssamplefile().getFilesamplecode(),
							OrderCreation.class) != null) {
						objupdatedorder.getLssamplefile().setFilecontent(mongoTemplate
								.findById(objupdatedorder.getLssamplefile().getFilesamplecode(), OrderCreation.class)
								.getContent());
					}
				}
			}
		}

//		List<Integer> lstworkflowcode = objorder.getLstworkflow().stream().map(LSworkflow::getWorkflowcode)
//				.collect(Collectors.toList());
		List<LSworkflow> workflowobj = lsworkflowRepository
				.findByLssitemasterAndStatusOrderByWorkflowcodeAsc(objorder.getObjLoggeduser().getLssitemaster(), 1);
		objupdatedorder.setLstworkflow(objorder.getLstworkflow(), workflowobj, objorder.getObjLoggeduser());

		if (objorder.getLsuserMaster() != null && objorder.getLsuserMaster().getUnifieduserid() != null) {
			LScentralisedUsers objUserId = new LScentralisedUsers();
			objUserId.setUnifieduserid(objorder.getLsuserMaster().getUnifieduserid());
			objupdatedorder.setLscentralisedusers(userService.Getcentraliseduserbyid(objUserId));
		}
		objupdatedorder.getResponse().setStatus(true);
		objupdatedorder.setLsorderworkflowhistory(
				lsorderworkflowhistoryRepositroy.findByBatchcodeOrderByHistorycode(objupdatedorder.getBatchcode()));
		objupdatedorder.setSharetome(lsordersharetoRepository.findBySharebatchcodeAndSharestatusAndUsersharedon(
				objupdatedorder.getBatchcode(), 1, objorder.getObjLoggeduser()));

//		if (lstworkflowcode.contains(objorder.getLsworkflow().getWorkflowcode())) {
//			int previousworkflowIndex = -1;
//			if (objupdatedorder.getPrevwfw() != null) {
//				previousworkflowIndex = IntStream.range(0, workflowobj.size()).filter(
//						i -> workflowobj.get(i).getWorkflowcode() == objupdatedorder.getPrevwfw().getWorkflowcode())
//						.findFirst().orElse(-1);
//			}
//			int currentworkflowIndex = -1;
//			if (objupdatedorder.getLsworkflow() != null) {
//				currentworkflowIndex = IntStream.range(0, workflowobj.size()).filter(
//						i -> workflowobj.get(i).getWorkflowcode() == objupdatedorder.getLsworkflow().getWorkflowcode())
//						.findFirst().orElse(-1);
//			}
//			boolean selfapproval = false;
//			if (objupdatedorder.getLsworkflow() != null
//					&& Boolean.TRUE.equals(objupdatedorder.getLsworkflow().getSelfapproval())) {
//				selfapproval = true;
//			} else if (objupdatedorder.getLsworkflow() != null
//					&& Boolean.FALSE.equals(objupdatedorder.getLsworkflow().getSelfapproval())) {
//				Integer wau = objupdatedorder.getWau();
//				int usercode = objorder.getObjLoggeduser().getUsercode();
//				if (((wau != null && wau == usercode && (objupdatedorder.getApproved()==2 ?previousworkflowIndex - 1 != currentworkflowIndex:previousworkflowIndex + 1 != currentworkflowIndex))
//						|| (wau != null && wau != usercode)) || (previousworkflowIndex == -1 || wau == null)) {
//					selfapproval = true;
//				}
//			}
//			objupdatedorder.setCanuserprocess(selfapproval);
//		} else {
//			objupdatedorder.setCanuserprocess(false);
//		}

		return objupdatedorder;
	}

	@SuppressWarnings("resource")
	public LSlogilablimsorderdetail GetorderStatusFromBatchID(LSlogilablimsorderdetail objorder)
			throws IllegalStateException, IOException {

		LSlogilablimsorderdetail objupdatedorder = lslogilablimsorderdetailRepository
				.findByBatchid(objorder.getBatchid());

		List<LSlogilablimsorder> lsLogilaborders = lslogilablimsorderRepository
				.findBybatchid(objupdatedorder.getBatchid());
		List<String> lsorderno = new ArrayList<String>();

		if (lsLogilaborders != null && lsLogilaborders.size() > 0) {
			int i = 0;

			while (lsLogilaborders.size() > i) {
				lsorderno.add(lsLogilaborders.get(i).getOrderid().toString());
				i++;
			}
		}
		objupdatedorder.setLsLSlogilablimsorder(lsLogilaborders);
		if (objupdatedorder.getLockeduser() != null) {
			objupdatedorder.setIsLock(1);
		} else {
			objupdatedorder.setIsLock(0);
		}

		if (objupdatedorder.getLockeduser() != null && objorder.getObjLoggeduser() != null
				&& objupdatedorder.getLockeduser().equals(objorder.getObjLoggeduser().getUsercode())) {
			objupdatedorder.setIsLockbycurrentuser(1);
		} else {
			objupdatedorder.setIsLockbycurrentuser(0);
		}

		if (objupdatedorder.getFiletype() != 0 && objupdatedorder.getOrderflag().toString().trim().equals("N")) {
			LSworkflow objlastworkflow = lsworkflowRepository.findTopByAndLssitemasterAndStatusOrderByWorkflowcodeDesc(
					objorder.getObjLoggeduser().getLssitemaster(), 1);
			if (objlastworkflow != null && objupdatedorder.getLsworkflow().equals(objlastworkflow)) {
				objupdatedorder.setIsFinalStep(1);
			} else {
				objupdatedorder.setIsFinalStep(0);
			}
		}

		if (objupdatedorder.getFiletype() == 0) {
			objupdatedorder
					.setLstestparameter(lStestparameterRepository.findByntestcode(objupdatedorder.getTestcode()));
		}

		if (objupdatedorder.getLsprojectmaster() != null && objorder.getLstworkflow() != null) {
			List<Integer> lstworkflowcode = objorder.getLstworkflow().stream().map(LSworkflow::getWorkflowcode)
					.collect(Collectors.toList());
			if (objorder.getLstworkflow() != null
					&& lstworkflowcode.contains(objupdatedorder.getLsworkflow().getWorkflowcode())) {
				objupdatedorder.setCanuserprocess(true);
			} else {
				objupdatedorder.setCanuserprocess(false);
			}
		} else {
			objupdatedorder.setCanuserprocess(true);
		}

		if (objupdatedorder.getFiletype() == 0) {
			objupdatedorder.setCanuserprocess(true);
		}

		if (objupdatedorder.getLssamplefile() != null) {
			if (objorder.getIsmultitenant() == 1) {
//				CloudOrderCreation file = cloudOrderCreationRepository
//						.findById((long) objupdatedorder.getLssamplefile().getFilesamplecode());
				if (cloudOrderCreationRepository
						.findById((long) objupdatedorder.getLssamplefile().getFilesamplecode()) != null) {
					objupdatedorder.getLssamplefile().setFilecontent(cloudOrderCreationRepository
							.findTop1ById((long) objupdatedorder.getLssamplefile().getFilesamplecode()).getContent());
				}
			} else {

//				String fileid = "order_" + objupdatedorder.getLssamplefile().getFilesamplecode();
				GridFSFile largefile = gridFsTemplate.findOne(new Query(Criteria.where("filename")
						.is("order_" + objupdatedorder.getLssamplefile().getFilesamplecode())));
				if (largefile == null) {
					largefile = gridFsTemplate.findOne(new Query(Criteria.where("_id")
							.is("order_" + objupdatedorder.getLssamplefile().getFilesamplecode())));
				}

				if (largefile != null) {
					GridFsResource resource = gridFsTemplate.getResource(largefile.getFilename());
					objupdatedorder.getLssamplefile().setFilecontent(
							new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))
									.lines().collect(Collectors.joining("\n")));
				} else {

					if (mongoTemplate.findById(objupdatedorder.getLssamplefile().getFilesamplecode(),
							OrderCreation.class) != null) {
						objupdatedorder.getLssamplefile().setFilecontent(mongoTemplate
								.findById(objupdatedorder.getLssamplefile().getFilesamplecode(), OrderCreation.class)
								.getContent());
					}
				}
			}
		}

		objorder = null;
		return objupdatedorder;
	}

	private String GetSamplefileconent(LSsamplefile lssamplefile, Integer ismultitenant) throws IOException {
		String content = "";

		if (lssamplefile != null) {
			if (ismultitenant == 1 || ismultitenant == 2) {
				CloudOrderCreation objCreation = cloudOrderCreationRepository
						.findTop1ById((long) lssamplefile.getFilesamplecode());
				if (objCreation != null && objCreation.getContainerstored() == 0) {
					content = objCreation.getContent();
				} else {
					content = objCloudFileManipulationservice.retrieveCloudSheets(objCreation.getFileuid(),
							commonfunction.getcontainername(ismultitenant, TenantContext.getCurrentTenant())
									+ "ordercreation");
				}
			} else {
				if (mongoTemplate.findById(lssamplefile.getFilesamplecode(), OrderCreation.class) != null) {
					content = mongoTemplate.findById(lssamplefile.getFilesamplecode(), OrderCreation.class)
							.getContent();
				}
			}
		}

		return content;
	}

//	public LSlogilablimsorderdetail GetdetailorderStatus(LSlogilablimsorderdetail objupdatedorder) {
//
//		objupdatedorder.setLsLSlimsorder(lSlimsorderRepository.findBybatchid(objupdatedorder.getBatchid()));
//
//		LSsamplefile LSsamplefile = objupdatedorder.getLssamplefile();
//		if (LSsamplefile != null) {
//			if (LSsamplefile.getFilesamplecode() != null) {
//				List<LSsamplefileversion> LSsamplefileversion = lssamplefileversionRepository
//						.findByFilesamplecodeOrderByVersionnoDesc(LSsamplefile);
//				objupdatedorder.getLssamplefile().setLssamplefileversion(LSsamplefileversion);
//			}
//		}
//
//		if (objupdatedorder.getFiletype() != 0 && objupdatedorder.getOrderflag().toString().trim().equals("N")) {
//			if (objupdatedorder.getLsworkflow()
//					.equals(lsworkflowRepository.findTopByAndLssitemasterOrderByWorkflowcodeDesc(
//							objupdatedorder.getObjLoggeduser().getLssitemaster()))) {
//				objupdatedorder.setIsFinalStep(1);
//			} else {
//				objupdatedorder.setIsFinalStep(0);
//			}
//		}
//
//		if (objupdatedorder.getFiletype() == 0) {
//			objupdatedorder
//					.setLstestparameter(lStestparameterRepository.findByntestcode(objupdatedorder.getTestcode()));
//		}
//
//		return objupdatedorder;
//	}

	public LSlogilablimsorderdetail GetdetailorderStatus(LSlogilablimsorderdetail objupdatedorder) {

		objupdatedorder
				.setLsLSlogilablimsorder(lslogilablimsorderRepository.findBybatchid(objupdatedorder.getBatchid()));

		LSsamplefile LSsamplefile = objupdatedorder.getLssamplefile();
		if (LSsamplefile != null) {
			if (LSsamplefile.getFilesamplecode() != null) {
				List<LSsamplefileversion> LSsamplefileversion = lssamplefileversionRepository
						.findByFilesamplecodeOrderByVersionnoDesc(LSsamplefile);
				objupdatedorder.getLssamplefile().setLssamplefileversion(LSsamplefileversion);
			}
		}

		if (objupdatedorder.getFiletype() != 0 && objupdatedorder.getOrderflag().toString().trim().equals("N")) {
			if (objupdatedorder.getLsworkflow()
					.equals(lsworkflowRepository.findTopByAndLssitemasterAndStatusOrderByWorkflowcodeDesc(
							objupdatedorder.getObjLoggeduser().getLssitemaster(), 1))) {
				objupdatedorder.setIsFinalStep(1);
			} else {
				objupdatedorder.setIsFinalStep(0);
			}
		}

		if (objupdatedorder.getFiletype() == 0) {
			objupdatedorder
					.setLstestparameter(lStestparameterRepository.findByntestcode(objupdatedorder.getTestcode()));
		}

		return objupdatedorder;
	}

//	public Map<String, Object> GetResults(LSlogilablimsorderdetail objorder) {
//		Map<String, Object> mapOrders = new HashMap<String, Object>();
//
//		List<LSresultdetails> lsResults = new ArrayList<LSresultdetails>();
//		List<LSlimsorder> lsLogilaborders = lslimsorderRepository.findBybatchid(objorder.getBatchid());
//		List<String> lsorderno = new ArrayList<String>();
//
//		if (lsLogilaborders != null && lsLogilaborders.size() > 0) {
//			int i = 0;
//
//			while (lsLogilaborders.size() > i) {
//				lsorderno.add(lsLogilaborders.get(i).getOrderid().toString());
//				i++;
//			}
//		}
//		lsResults = lsresultdetailsRepository.findBylimsreferencecodeIn(lsorderno);
//
//		mapOrders.put("SDMSResults", lsResults);
//
//		List<ELNResultDetails> ELNResults = new ArrayList<ELNResultDetails>();
//		List<LSlogilablimsorderdetail> lslogilablimsorderdetail = lslogilablimsorderdetailRepository
//				.findBybatchcode(objorder.getBatchcode());
//		List<Long> batchcode = new ArrayList<Long>();
//
//		if (lslogilablimsorderdetail != null && lslogilablimsorderdetail.size() > 0) {
//			int i = 0;
//
//			while (lslogilablimsorderdetail.size() > i) {
//				batchcode.add(lslogilablimsorderdetail.get(i).getBatchcode());
//				i++;
//			}
//		}
//		ELNResults = ELNResultDetailsRepository.findBybatchcode(batchcode);
//
//		mapOrders.put("ELNResults", ELNResults);
//
//		return mapOrders;
//	}

	public Map<String, Object> GetResults(LSlogilablimsorderdetail objorder) {
		Map<String, Object> mapOrders = new HashMap<String, Object>();

		List<LSresultdetails> lsResults = new ArrayList<LSresultdetails>();
		List<LSlogilablimsorder> lsLogilaborders = lslogilablimsorderRepository.findBybatchid(objorder.getBatchid());
		List<String> lsorderno = new ArrayList<String>();

		if (lsLogilaborders != null && lsLogilaborders.size() > 0) {
			int i = 0;

			while (lsLogilaborders.size() > i) {
				lsorderno.add(lsLogilaborders.get(i).getOrderid().toString());
				i++;
			}
		}
		lsResults = lsresultdetailsRepository.findBylimsreferencecodeIn(lsorderno);

		List<ELNResultDetails> ELNResults = new ArrayList<ELNResultDetails>();
		List<LSlogilablimsorderdetail> lslogilablimsorderdetail = lslogilablimsorderdetailRepository
				.findBybatchcode(objorder.getBatchcode());
		List<Long> batchcode = new ArrayList<Long>();

		if (lslogilablimsorderdetail != null && lslogilablimsorderdetail.size() > 0) {
			int i = 0;

			while (lslogilablimsorderdetail.size() > i) {
				batchcode.add(lslogilablimsorderdetail.get(i).getBatchcode());
				i++;
			}
		}
		ELNResults = ELNResultDetailsRepository.findBybatchcodeIn(batchcode);

		if (ELNResults != null && !ELNResults.isEmpty()) {

		    ELNResults.forEach(result -> {

		        if (result != null &&
		            result.getLsresultfieldvalues() != null &&
		            !result.getLsresultfieldvalues().isEmpty()) {

		        	result.getLsresultfieldvalues()
		            .sort(Comparator.comparingInt(LSResultFieldValues::getResseqno));
		        }
		    });
		}
		
		mapOrders.put("SDMSResults", lsResults);
		mapOrders.put("ELNResults", ELNResults);

		lsLogilaborders = null;
		lslogilablimsorderdetail = null;
		lsResults = null;
		ELNResults = null;

		return mapOrders;
	}

	public Map<String, Object> GetResultsproto(LSlogilabprotocoldetail protoobj) {
		Map<String, Object> mapOrders = new HashMap<String, Object>();

//		List<LSresultdetails> lsResults = new ArrayList<LSresultdetails>();
//		List<LSlogilablimsorder> lsLogilaborders = lslogilablimsorderRepository.findBybatchid(objorder.getBatchid());
//		List<String> lsorderno = new ArrayList<String>();
//
//		if (lsLogilaborders != null && lsLogilaborders.size() > 0) {
//			int i = 0;
//
//			while (lsLogilaborders.size() > i) {
//				lsorderno.add(lsLogilaborders.get(i).getOrderid().toString());
//				i++;
//			}
//		}
//		lsResults = lsresultdetailsRepository.findBylimsreferencecodeIn(lsorderno);

		List<ELNResultDetails> ELNResults = new ArrayList<ELNResultDetails>();
//		List<LSlogilablimsorderdetail> lslogilablimsorderdetail = lslogilablimsorderdetailRepository
//				.findBybatchcode(objorder.getBatchcode());
		List<LSlogilabprotocoldetail> lslogilabpro = LSlogilabprotocoldetailRepository
				.findByProtocolordercodeOrderByProtocolordercodeAsc(protoobj.getProtocolordercode());

		List<Long> protocolordercode = new ArrayList<Long>();

		if (lslogilabpro != null && lslogilabpro.size() > 0) {
			int i = 0;

			while (lslogilabpro.size() > i) {
				protocolordercode.add(lslogilabpro.get(i).getProtocolordercode());
				i++;
			}
		}
		ELNResults = ELNResultDetailsRepository.findBybatchcodeIn(protocolordercode);

		// mapOrders.put("SDMSResults", lsResults);
		mapOrders.put("ELNResults", ELNResults);

		lslogilabpro = null;
		ELNResults = null;

		return mapOrders;
	}

	public void updateorderversioncontent(String Content, LSsamplefileversion objfile, Integer ismultitenant)
			throws IOException {
		if (ismultitenant == 1 || ismultitenant == 2) {
			CloudOrderVersion objsavefile = new CloudOrderVersion();

			if (objfile.getFilesamplecodeversion() != null) {
				objsavefile = cloudOrderVersionRepository.findTop1ById((long) objfile.getFilesamplecodeversion());
			}

			if (objsavefile == null) {
				objsavefile = new CloudOrderVersion();
			}

			Map<String, Object> objMap;

			if (objsavefile.getFileuid() != null) {
				objMap = objCloudFileManipulationservice.storecloudSheets(Content,
						commonfunction.getcontainername(ismultitenant, TenantContext.getCurrentTenant())
								+ "orderversion",
						objsavefile.getFileuid());
			} else {
				objMap = objCloudFileManipulationservice.storecloudSheetsreturnwithpreUUID(Content,
						commonfunction.getcontainername(ismultitenant, TenantContext.getCurrentTenant())
								+ "orderversion");
			}

			String fileUUID = (String) objMap.get("uuid");
			String fileURI = objMap.get("uri").toString();

			if (objfile.getFilesamplecodeversion() != null) {
				objsavefile.setId((long) objfile.getFilesamplecodeversion());
			} else {
				objsavefile.setId(1);
			}

			objsavefile.setFileuri(fileURI);
			objsavefile.setFileuid(fileUUID);
			objsavefile.setContainerstored(1);

			cloudOrderVersionRepository.save(objsavefile);

			objsavefile = null;
		} else {

			GridFSFile largefile = gridFsTemplate.findOne(
					new Query(Criteria.where("filename").is("orderversion_" + objfile.getFilesamplecodeversion())));
			if (largefile != null) {
				gridFsTemplate.delete(
						new Query(Criteria.where("filename").is("orderversion_" + objfile.getFilesamplecodeversion())));
			}

			ByteArrayInputStream inputStream = new ByteArrayInputStream(Content.getBytes(StandardCharsets.UTF_8));
			String contentType = "text/plain; charset=UTF-8";
			gridFsTemplate.store(inputStream, "orderversion_" + objfile.getFilesamplecodeversion(), contentType);

//			gridFsTemplate.store(new ByteArrayInputStream(Content.getBytes(StandardCharsets.UTF_8)),
//					"orderversion_" + objfile.getFilesamplecodeversion(), StandardCharsets.UTF_16);

		}
	}

	@SuppressWarnings("unused")
	public LSsamplefile SaveResultfile(MultipartHttpServletRequest request) throws IOException {

		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		final LSsamplefile objfile = mapper.readValue(request.getParameter("file"), LSsamplefile.class);
		MultipartFile content = request.getFile("content");

		String Content = IOUtils.toString(content.getInputStream(), StandardCharsets.UTF_8);

		List<LSsamplefileversion> lstSampleVer = objfile.getLssamplefileversion();
		Integer target = objfile.getVersionno() == null ? 1 : objfile.getVersionno();

		Integer lastversionindex = IntStream.range(0, lstSampleVer.size())
				.filter(i -> target.equals(lstSampleVer.get(i).getVersionno())).findFirst().orElse(0);

		boolean versionexist = true;
		if (objfile.getLssamplefileversion().size() <= 0) {
			versionexist = false;
			lastversionindex = 0;
			LSsamplefileversion lsversion = new LSsamplefileversion();
			lsversion.setVersionname("1");
			lsversion.setVersionno(1);
			lsversion.setBatchcode(objfile.getBatchcode());
			lsversion.setTestid(objfile.getTestid());
			lsversion.setCreateby(objfile.getCreateby());
			lsversion.setModifiedby(objfile.getModifiedby());
			objfile.getLssamplefileversion().add(lsversion);

			lssamplefileversionRepository.saveAll(objfile.getLssamplefileversion());

		}

		LSlogilablimsorderdetail orderDetail = lslogilablimsorderdetailRepository
				.findByBatchcodeOrderByBatchcodeDesc(objfile.getBatchcode());
		if (orderDetail.getOrdersaved() != null && orderDetail.getOrdersaved().equals(0)) {
			orderDetail.setOrdersaved(1);
			lslogilablimsorderdetailRepository.save(orderDetail);
		}
		if (orderDetail.getBatchcode() != null) {
			try {
				orderDetail.setModifieddate(commonfunction.getCurrentUtcTime());
				orderDetail.setModifiedby(objfile.getModifieduser());
			} catch (ParseException e) {

				e.printStackTrace();
			}
			lslogilablimsorderdetailRepository.save(orderDetail);
		}

		if (objfile.isDoversion() && versionexist) {

			Integer perviousversion = -1;
			if (objfile.getVersionno() != null && objfile.getVersionno() >= 2) {
				perviousversion = objfile.getVersionno() - 2;
			}

			String Contentversion = GetSamplefileconent(objfile, objfile.getIsmultitenant());
			objfile.getLssamplefileversion().get(lastversionindex).setFilecontent(null);
			for (int k = 0; k < objfile.getLssamplefileversion().size(); k++) {
				if (objfile.getLssamplefileversion().get(k).getFilesamplecodeversion() == null) {
					try {
						objfile.getLssamplefileversion().get(k).setCreatedate(commonfunction.getCurrentUtcTime());
					} catch (ParseException e) {

						e.printStackTrace();
					}
				}
			}
			objfile.setLsuserMaster(null);
			lssamplefileversionRepository.saveAll(objfile.getLssamplefileversion());
			updateorderversioncontent(Content, objfile.getLssamplefileversion().get(lastversionindex),
					objfile.getIsmultitenant());

		} else {
			updateorderversioncontent(Content, objfile.getLssamplefileversion().get(lastversionindex),
					objfile.getIsmultitenant());
		}

		objfile.setProcessed(1);

		objfile.setFilecontent(null);
		objfile.setLssampleresult(null);
		String tagvalues = objfile.getTagvalues();
		String resultvalues = objfile.getResultvalues();
		try {
			objfile.setModifieddate(commonfunction.getCurrentUtcTime());
		} catch (ParseException e) {

			e.printStackTrace();
		}
		objfile.setLsuserMaster(null);
		lssamplefileRepository.save(objfile);
		updateordercontent(Content, objfile, objfile.getIsmultitenant());
		updateordertagvalues(objfile, tagvalues);
		if (resultvalues != null && resultvalues != "" && !resultvalues.equalsIgnoreCase("[]")) {
			updateorderresultvalues(objfile, resultvalues);
		}
		objfile.setFilecontent(Content);

		if (objfile.getObjActivity() != null) {
			try {
				objfile.getObjActivity().setActivityDate(commonfunction.getCurrentUtcTime());
			} catch (ParseException e) {

				e.printStackTrace();
			}
			lsactivityRepository.save(objfile.getObjActivity());
		}

		objfile.setResponse(new Response());
		objfile.getResponse().setStatus(true);
		objfile.getResponse().setInformation("ID_DUMMY1");

		return objfile;
	}

	private void updateordertagvalues(LSsamplefile objfile, String tagvalues) {

		lsreportfile objreport = new lsreportfile();
		objreport.setId((long) objfile.getFilesamplecode());
		objreport.setContent(tagvalues);
		objreport.setBatchcode(objfile.getBatchcode());
		objreport.setContentstored(1);
		reportfileRepository.save(objreport);
		objreport = null;
	}

	private void updateorderresultvalues(LSsamplefile objfile, String tagvalues) {

		Lsresultfororders objreport = new Lsresultfororders();
		objreport.setId((long) objfile.getFilesamplecode());
		objreport.setBatchcode(objfile.getBatchcode());
		objreport.setContent(tagvalues);
		objreport.setContentstored(1);
		lsresultforordersRepository.save(objreport);
		objreport = null;
	}

	public List<Lsresultfororders> onGetResultValuesFromSelectedOrder(LSlogilablimsorderdetail objOrder) {

		List<Lsresultfororders> objResultList = lsresultforordersRepository.findByBatchcode(objOrder.getBatchcode());

		return objResultList;
	}

	public void updateordercontent(String Content, LSsamplefile objfile, Integer ismultitenant) throws IOException {

		String contentParams = "";
		String contentValues = "";

		Map<String, Object> objContent = commonfunction.getParamsAndValues(Content);

		contentValues = (String) objContent.get("values");
		contentParams = (String) objContent.get("parameters");

		if (ismultitenant == 1 || ismultitenant == 2) {

			Map<String, Object> objMap;

			CloudOrderCreation objsavefile = new CloudOrderCreation();

			if (objfile.getFilesamplecode() != null) {
				objsavefile = cloudOrderCreationRepository.findTop1ById((long) objfile.getFilesamplecode());
			}

			if (objsavefile == null) {
				objsavefile = new CloudOrderCreation();
			}

			if (objsavefile.getFileuid() != null) {
				objMap = objCloudFileManipulationservice.storecloudSheets(Content,
						commonfunction.getcontainername(ismultitenant, TenantContext.getCurrentTenant())
								+ "ordercreation",
						objsavefile.getFileuid());
			} else {
				objMap = objCloudFileManipulationservice.storecloudSheetsreturnwithpreUUID(Content,
						commonfunction.getcontainername(ismultitenant, TenantContext.getCurrentTenant())
								+ "ordercreation");
			}

			String fileUUID = (String) objMap.get("uuid");
			String fileURI = objMap.get("uri").toString();

			objsavefile.setId((long) objfile.getFilesamplecode());
//			objsavefile.setContent(Content);
			objsavefile.setFileuri(fileURI);
			objsavefile.setFileuid(fileUUID);
			objsavefile.setContainerstored(1);
			objsavefile.setContentvalues(contentValues);
			objsavefile.setContentparameter(contentParams);

			cloudOrderCreationRepository.save(objsavefile);

			objsavefile = null;
		} else {
			OrderCreation objsavefile = new OrderCreation();
			objsavefile.setId((long) objfile.getFilesamplecode());
			objsavefile.setContentvalues(contentValues);
			objsavefile.setContentparameter(contentParams);

			Query query = new Query(Criteria.where("id").is(objsavefile.getId()));

			Boolean recordcount = mongoTemplate.exists(query, OrderCreation.class);

			if (!recordcount) {
				mongoTemplate.insert(objsavefile);
			} else {
				Update update = new Update();
				update.set("contentvalues", contentValues);
				update.set("contentparameter", contentParams);

				mongoTemplate.upsert(query, update, OrderCreation.class);
			}

			GridFSFile largefile = gridFsTemplate
					.findOne(new Query(Criteria.where("filename").is("order_" + objfile.getFilesamplecode())));
			if (largefile != null) {
				gridFsTemplate.delete(new Query(Criteria.where("filename").is("order_" + objfile.getFilesamplecode())));
			}

			ByteArrayInputStream inputStream = new ByteArrayInputStream(Content.getBytes(StandardCharsets.UTF_8));
			String contentType = "text/plain; charset=UTF-8";
			gridFsTemplate.store(inputStream, "order_" + objfile.getFilesamplecode(), contentType);

//			gridFsTemplate.store(new ByteArrayInputStream(Content.getBytes(StandardCharsets.UTF_8)),
//					"order_" + objfile.getFilesamplecode(), StandardCharsets.UTF_16);

			objsavefile = null;
		}

		contentParams = null;
		contentValues = null;
		objContent = null;
	}

	@SuppressWarnings("resource")
	public LSlogilablimsorderdetail UpdateLimsOrder(LSlogilablimsorderdetail objorder) throws IOException {
		List<LSlogilablimsorder> lstorder = lslogilablimsorderRepository.findBybatchid(objorder.getBatchid());
		List<Lsbatchdetails> lstbatch = LsbatchdetailsRepository.findByBatchcode(objorder.getBatchcode());

		if (!LSfilemethodRepository.findByFilecodeOrderByFilemethodcode(objorder.getLsfile().getFilecode()).isEmpty()) {
			objorder.setInstrumentcode(LSfilemethodRepository
					.findByFilecodeOrderByFilemethodcode(objorder.getLsfile().getFilecode()).get(0).getInstrumentid());
			objorder.setMethodcode(LSfilemethodRepository
					.findByFilecodeOrderByFilemethodcode(objorder.getLsfile().getFilecode()).get(0).getMethodid());
		} else {
			objorder.setInstrumentcode(objorder.getMethodcode());
			objorder.setMethodcode(objorder.getMethodcode());
		}
		objorder.getLsfile().setLsparameter(
				lsFileparameterRepository.findByFilecodeOrderByFileparametercode(objorder.getLsfile().getFilecode()));

		lstorder.forEach((orders) -> orders.setMethodcode(objorder.getMethodcode()));
		lstorder.forEach((orders) -> orders.setInstrumentcode(objorder.getInstrumentcode()));

		objorder.setLsbatchdetails(lstbatch);

		objorder.getLssamplefile().setBatchcode(objorder.getBatchcode());

//		List<LSlogilablimsorder> lsorder = new ArrayList<LSlogilablimsorder>();
		Long Limsorder = lstorder.get(0).getOrderid();

		if (objorder.getLsfile() != null) {
			objorder.getLsfile().setLsmethods(
					LSfilemethodRepository.findByFilecodeOrderByFilemethodcode(objorder.getLsfile().getFilecode()));
			if (objorder.getLsfile().getLsmethods() != null && objorder.getLsfile().getLsmethods().size() > 0) {
				int methodindex = 1;
				for (LSfilemethod objmethod : objorder.getLsfile().getLsmethods()) {
					OptionalInt methodExist = IntStream.range(0, lstorder.size())
							.filter(i -> lstorder.get(i).getInstrumentcode() != null
									&& lstorder.get(i).getMethodcode() != null
									&& lstorder.get(i).getInstrumentcode().equals(objmethod.getInstrumentid())
									&& lstorder.get(i).getMethodcode().equals(objmethod.getMethodid()))
							.findFirst();
					if (!methodExist.isPresent()) {
						LSlogilablimsorder objLimsOrder = new LSlogilablimsorder();
						objLimsOrder.setOrderid(Limsorder + methodindex);
						objLimsOrder.setBatchid(objorder.getBatchid());
						objLimsOrder.setMethodcode(objmethod.getMethodid());
						objLimsOrder.setInstrumentcode(objmethod.getInstrumentid());
						objLimsOrder
								.setTestcode(objorder.getTestcode() != null ? objorder.getTestcode().toString() : null);
						objLimsOrder.setOrderflag("N");
						objLimsOrder.setCreatedtimestamp(objorder.getCreatedtimestamp());

						lstorder.add(objLimsOrder);

						methodindex++;
					}
				}

				// lslogilablimsorderRepository.saveAll(lsorder);
			}
		}

		lslogilablimsorderRepository.saveAll(lstorder);

		objorder.getLsfile().setIsmultitenant(objorder.getIsmultitenant());

		String contString = getfileoncode(objorder.getLsfile());
		objorder.getLsfile().setFilecontent(contString);

		objorder.setFilecode(objorder.getLsfile().getFilecode());

		objorder.getLssamplefile().setFilecontent(null);
		lssamplefileRepository.save(objorder.getLssamplefile());
//		LSfileRepository.save(objorder.getLsfile());

		lslogilablimsorderdetailRepository.updateOrderFields(objorder.getInstrumentcode(), objorder.getMethodcode(),
				objorder.getFilecode(), objorder.getLssamplefile().getFilesamplecode(), objorder.getBatchcode());

//		lslogilablimsorderdetailRepository.save(objorder);

//		LSsamplefileversion objverionfile = lssamplefileversionRepository
//				.findByBatchcodeAndVersionno(objorder.getBatchcode(), 1);

		LSsamplefile objfile = objorder.getLssamplefile();

		if (lssamplefileversionRepository.findByBatchcodeAndVersionno(objorder.getBatchcode(), 1) == null
				&& !contString.equals("") && contString != null) {

			LSsamplefileversion objVersion = new LSsamplefileversion();
			objVersion.setVersionname("1");
			objVersion.setVersionno(1);
			objVersion.setBatchcode(objfile.getBatchcode());
			objVersion.setTestid(objfile.getTestid());
			objVersion.setCreatebyuser(objorder.getLsuserMaster());
			objVersion.setModifiedby(objorder.getLsuserMaster());
			objVersion.setFilesamplecode(objfile);
			objVersion.setCreatedate(objorder.getCreatedtimestamp());
			lssamplefileversionRepository.save(objVersion);
			objfile.getLssamplefileversion().add(objVersion);
			updateorderversioncontent(contString, objVersion, objorder.getIsmultitenant());

		}

		if (objorder.getLssamplefile() != null && objorder.getLssamplefile().getProcessed() != null
				&& objorder.getLssamplefile().getProcessed() == 0) {
			updateordercontent(contString, objorder.getLssamplefile(), objorder.getIsmultitenant());
			objorder.getLssamplefile().setFilecontent(contString);
		} else {

			objorder.getLsfile().setFilecontent(null);

			if (objorder.getIsmultitenant() == 1) {

				if (cloudOrderCreationRepository
						.findById((long) objorder.getLssamplefile().getFilesamplecode()) != null) {
					objorder.getLssamplefile().setFilecontent(cloudOrderCreationRepository
							.findTop1ById((long) objorder.getLssamplefile().getFilesamplecode()).getContent());
				}
			} else {

				GridFSFile largefile = gridFsTemplate.findOne(new Query(
						Criteria.where("filename").is("order_" + objorder.getLssamplefile().getFilesamplecode())));
				if (largefile == null) {
					largefile = gridFsTemplate.findOne(new Query(
							Criteria.where("_id").is("order_" + objorder.getLssamplefile().getFilesamplecode())));
				}

				if (largefile != null) {
					GridFsResource resource = gridFsTemplate.getResource(largefile.getFilename());
					objorder.getLssamplefile().setFilecontent(
							new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))
									.lines().collect(Collectors.joining("\n")));
				} else {

					if (mongoTemplate.findById(objorder.getLssamplefile().getFilesamplecode(),
							OrderCreation.class) != null) {
						objorder.getLssamplefile()
								.setFilecontent(mongoTemplate
										.findById(objorder.getLssamplefile().getFilesamplecode(), OrderCreation.class)
										.getContent());
					}
				}
			}

		}

		final LSlogilablimsorderdetail objLSlogilablimsorder = objorder;

		new Thread(() -> {
			try {
				System.out.println("inside the thread SDMS order call");
				createLogilabLIMSOrder4SDMS(objLSlogilablimsorder);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		return objorder;
	}

	public LSlogilablimsorderdetail SheetChangeForLimsOrder(LSlogilablimsorderdetail objorder) throws IOException {
		List<LSlogilablimsorder> lstorder = lslogilablimsorderRepository
				.findByBatchidOrderByOrderidDesc(objorder.getBatchid());
		List<Lsbatchdetails> lstbatch = LsbatchdetailsRepository.findByBatchcode(objorder.getBatchcode());

		objorder.getLsfile().setLsparameter(
				lsFileparameterRepository.findByFilecodeOrderByFileparametercode(objorder.getLsfile().getFilecode()));

		List<LSlogilablimsorder> lsorder = new ArrayList<LSlogilablimsorder>();
		Long Limsorder = lstorder.get(0).getOrderid();

		if (objorder.getLsfile() != null) {
			objorder.getLsfile().setLsmethods(
					LSfilemethodRepository.findByFilecodeOrderByFilemethodcode(objorder.getLsfile().getFilecode()));
			if (objorder.getLsfile().getLsmethods() != null && objorder.getLsfile().getLsmethods().size() > 0) {
				int methodindex = 1;
				for (LSfilemethod objmethod : objorder.getLsfile().getLsmethods()) {
					OptionalInt methodExist = IntStream.range(0, lstorder.size())
							.filter(i -> lstorder.get(i).getInstrumentcode() != null
									&& lstorder.get(i).getMethodcode() != null
									&& lstorder.get(i).getInstrumentcode().equals(objmethod.getInstrumentid())
									&& lstorder.get(i).getMethodcode().equals(objmethod.getMethodid()))
							.findFirst();
					if (!methodExist.isPresent()) {
						LSlogilablimsorder objLimsOrder = new LSlogilablimsorder();
						objLimsOrder.setOrderid(Limsorder + methodindex);
						objLimsOrder.setBatchid(objorder.getBatchid());
						objLimsOrder.setMethodcode(objmethod.getMethodid());
						objLimsOrder.setInstrumentcode(objmethod.getInstrumentid());
						objLimsOrder
								.setTestcode(objorder.getTestcode() != null ? objorder.getTestcode().toString() : null);
						objLimsOrder.setOrderflag("N");
						objLimsOrder.setCreatedtimestamp(objorder.getCreatedtimestamp());

						lsorder.add(objLimsOrder);

						methodindex++;
					}
				}

				lslogilablimsorderRepository.saveAll(lsorder);
			}
		}

		objorder.setLsbatchdetails(lstbatch);
		objorder.getLssamplefile().setBatchcode(objorder.getBatchcode());
		objorder.getLsfile().setIsmultitenant(objorder.getIsmultitenant());

		String contString = getfileoncode(objorder.getLsfile());
		objorder.getLsfile().setFilecontent(contString);
		objorder.setFilecode(objorder.getLsfile().getFilecode());
		objorder.setFilename(objorder.getLsfile().getFilenameuser());

		objorder.getLssamplefile().setFilecontent(null);
		lssamplefileRepository.save(objorder.getLssamplefile());

		LSsamplefile objfile = objorder.getLssamplefile();

		if (lssamplefileversionRepository.findByBatchcodeAndVersionno(objorder.getBatchcode(), 1) != null
				&& !contString.equals("") && contString != null) {

			LSsamplefileversion objVersion = lssamplefileversionRepository
					.findByBatchcodeAndVersionno(objorder.getBatchcode(), 1);
			objVersion.setModifiedby(objorder.getLsuserMaster());
			objVersion.setFilesamplecode(objfile);
			lssamplefileversionRepository.save(objVersion);
			updateorderversioncontent(contString, objVersion, objorder.getIsmultitenant());

		}

		if (objorder.getLssamplefile() != null && objorder.getLssamplefile().getProcessed() != null
				&& objorder.getLssamplefile().getProcessed() == 1) {
			updateordercontent(contString, objorder.getLssamplefile(), objorder.getIsmultitenant());
			objorder.getLssamplefile().setFilecontent(contString);
		} else {

			objorder.getLsfile().setFilecontent(null);

			if (objorder.getIsmultitenant() == 1) {
				if (cloudOrderCreationRepository
						.findById((long) objorder.getLssamplefile().getFilesamplecode()) != null) {
					objorder.getLssamplefile().setFilecontent(cloudOrderCreationRepository
							.findTop1ById((long) objorder.getLssamplefile().getFilesamplecode()).getContent());
				}
			} else {

				String contentParams = "";
				String contentValues = "";

				Map<String, Object> objContent = commonfunction.getParamsAndValues(contString);

				contentValues = (String) objContent.get("values");
				contentParams = (String) objContent.get("parameters");

				OrderCreation objsavefile = new OrderCreation();
				objsavefile.setId((long) objfile.getFilesamplecode());
				objsavefile.setContentvalues(contentValues);
				objsavefile.setContentparameter(contentParams);

				Query query = new Query(Criteria.where("id").is(objsavefile.getId()));

				Boolean recordcount = mongoTemplate.exists(query, OrderCreation.class);

				if (!recordcount) {
					mongoTemplate.insert(objsavefile);
				} else {
					Update update = new Update();
					update.set("contentvalues", contentValues);
					update.set("contentparameter", contentParams);

					mongoTemplate.upsert(query, update, OrderCreation.class);
				}

				GridFSFile largefile = gridFsTemplate
						.findOne(new Query(Criteria.where("filename").is("order_" + objfile.getFilesamplecode())));
				if (largefile != null) {
					gridFsTemplate
							.delete(new Query(Criteria.where("filename").is("order_" + objfile.getFilesamplecode())));
				}
				ByteArrayInputStream inputStream = new ByteArrayInputStream(
						contString.getBytes(StandardCharsets.UTF_8));
				String contentType = "text/plain; charset=UTF-8";
				gridFsTemplate.store(inputStream, "order_" + objfile.getFilesamplecode(), contentType);

//				gridFsTemplate.store(new ByteArrayInputStream(contString.getBytes(StandardCharsets.UTF_8)),
//						"order_" + objfile.getFilesamplecode(), StandardCharsets.UTF_16);

				objorder.getLssamplefile().setFilecontent(contString);

			}

		}

		lslogilablimsorderdetailRepository.updateOrderFields(objorder.getInstrumentcode(), objorder.getMethodcode(),
				objorder.getFilecode(), objorder.getLssamplefile().getFilesamplecode(), objorder.getBatchcode());
//		lslogilablimsorderdetailRepository.save(objorder);
		final LSlogilablimsorderdetail objLSlogilablimsorder = objorder;

		new Thread(() -> {
			try {
				System.out.println("inside the thread SDMS order call");
				createLogilabLIMSOrder4SDMS(objLSlogilablimsorder);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		return objorder;
	}

	@SuppressWarnings("resource")
	public String getfileoncode(LSfile objfile) throws IllegalStateException, IOException {

		String content = "";

		LSfile objreturnfile = LSfileRepository.findByfilecode(objfile.getFilecode());

		if (objreturnfile != null) {
			if (objfile.getIsmultitenant() == 1) {

				if (cloudSheetCreationRepository.findById((long) objfile.getFilecode()) != null) {
					content = commonfunction.getsheetdatawithfirstsheet(
							cloudSheetCreationRepository.findTop1ById((long) objfile.getFilecode()).getContent());
				}
			} else {

				GridFSFile largefile = gridFsTemplate
						.findOne(new Query(Criteria.where("filename").is("file_" + objfile.getFilecode())));
				if (largefile == null) {
					largefile = gridFsTemplate
							.findOne(new Query(Criteria.where("_id").is("file_" + objfile.getFilecode())));
				}

				if (largefile != null) {
					GridFsResource resource = gridFsTemplate.getResource(largefile.getFilename());
					content = new BufferedReader(
							new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)).lines()
							.collect(Collectors.joining("\n"));
				} else {

					if (mongoTemplate.findById(objfile.getFilecode(), SheetCreation.class) != null) {
						content = mongoTemplate.findById(objfile.getFilecode(), SheetCreation.class).getContent();
					}
				}
			}
		}

		return content;
	}

	public LSlogilablimsorderdetail Getupdatedorder(LSlogilablimsorderdetail objorder) {
		LSlogilablimsorderdetail objupdated = lslogilablimsorderdetailRepository
				.findByBatchcodeOrderByBatchcodeDesc(objorder.getBatchcode());
		objupdated.setLsLSlogilablimsorder(lslogilablimsorderRepository.findBybatchid(objorder.getBatchid()));

		if (objorder.getFiletype() == 0) {
			objupdated.setLstestparameter(lStestparameterRepository.findByntestcode(objupdated.getTestcode()));
		}

		if (objorder.getLssamplefile() != null) {
			if (objorder.getIsmultitenant() == 1) {

				if (cloudOrderCreationRepository
						.findById((long) objorder.getLssamplefile().getFilesamplecode()) != null) {
					objupdated.getLssamplefile().setFilecontent(cloudOrderCreationRepository
							.findTop1ById((long) objorder.getLssamplefile().getFilesamplecode()).getContent());
				}
			} else {

				if (mongoTemplate.findById(objorder.getLssamplefile().getFilesamplecode(),
						OrderCreation.class) != null) {
					objupdated.getLssamplefile()
							.setFilecontent(mongoTemplate
									.findById(objorder.getLssamplefile().getFilesamplecode(), OrderCreation.class)
									.getContent());
				}
			}
		}

		return objupdated;
	}

	@SuppressWarnings("resource")
	public Map<String, Object> Getorderforlink(LSlogilablimsorderdetail objorder)
			throws IllegalStateException, IOException {
		Map<String, Object> mapOrder = new HashMap<String, Object>();
		LSlogilablimsorderdetail objupdated = lslogilablimsorderdetailRepository
				.findByBatchcodeOrderByBatchcodeDesc(objorder.getBatchcode());

		if (objupdated.getLsprojectmaster() != null) {
			List<Integer> lstworkflowcode = objorder.getLstworkflow().stream().map(LSworkflow::getWorkflowcode)
					.collect(Collectors.toList());
			if (objorder.getLstworkflow() != null
					&& lstworkflowcode.contains(objupdated.getLsworkflow().getWorkflowcode())) {
				objupdated.setCanuserprocess(true);
			} else {
				objupdated.setCanuserprocess(false);
			}
		} else {
			objupdated.setCanuserprocess(true);
		}

		if (objupdated.getLockeduser() != null && objorder.getObjLoggeduser() != null
				&& objupdated.getLockeduser().equals(objorder.getObjLoggeduser().getUsercode())) {
			objupdated.setIsLockbycurrentuser(1);
		} else {
			objupdated.setIsLockbycurrentuser(0);
		}

		if (objupdated.getFiletype() != 0 && objupdated.getOrderflag().toString().trim().equals("N")) {
			if (objupdated.getLsworkflow()
					.equals(lsworkflowRepository.findTopByAndLssitemasterAndStatusOrderByWorkflowcodeDesc(
							objorder.getObjLoggeduser().getLssitemaster(), 1))) {
				objupdated.setIsFinalStep(1);
			} else {
				objupdated.setIsFinalStep(0);
			}
		}

		if (objupdated.getFiletype() == 0) {
			objupdated.setLstestparameter(lStestparameterRepository.findByntestcode(objupdated.getTestcode()));
		}

		if (objupdated.getLssamplefile() != null) {

			if (objorder.getIsmultitenant() == 1 || objorder.getIsmultitenant() == 2) {
				CloudOrderCreation file = cloudOrderCreationRepository
						.findTop1ById((long) objupdated.getLssamplefile().getFilesamplecode());
				if (file != null) {
					objupdated.getLssamplefile().setFilecontent(file.getContent());
				}
			} else {

				String fileid = "order_" + objupdated.getLssamplefile().getFilesamplecode();
				GridFSFile largefile = gridFsTemplate.findOne(new Query(Criteria.where("filename").is(fileid)));
				if (largefile == null) {
					largefile = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(fileid)));
				}

				if (largefile != null) {
					GridFsResource resource = gridFsTemplate.getResource(largefile.getFilename());
					String filecontent = new BufferedReader(
							new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)).lines()
							.collect(Collectors.joining("\n"));
					objupdated.getLssamplefile().setFilecontent(filecontent);
				} else {

					OrderCreation file = mongoTemplate.findById(objupdated.getLssamplefile().getFilesamplecode(),
							OrderCreation.class);
					if (file != null) {
						objupdated.getLssamplefile().setFilecontent(file.getContent());
					}
				}
			}

		}
		objupdated.setLstworkflow(objorder.getLstworkflow());
		mapOrder.put("order", objupdated);
		mapOrder.put("version",
				lssamplefileversionRepository.findByFilesamplecodeOrderByVersionnoDesc(objupdated.getLssamplefile()));

		return mapOrder;
	}

	public LSlogilablimsorderdetail CompleteOrder(LSlogilablimsorderdetail objorder) throws IOException {

		LSlogilablimsorderdetail orderobj = lslogilablimsorderdetailRepository
				.findByBatchcodeOrderByBatchcodeDesc(objorder.getBatchcode());
		objorder.setElnmaterial(orderobj.getElnmaterial());
		objorder.setElnmaterialinventory(orderobj.getElnmaterialinventory());

		if (objorder.getLssamplefile().getLssamplefileversion() != null) {
			lssamplefileversionRepository.saveAll(objorder.getLssamplefile().getLssamplefileversion());
		}
		lssampleresultRepository.saveAll(objorder.getLssamplefile().getLssampleresult());
//		String Content = objorder.getLssamplefile().getFilecontent();
		objorder.getLssamplefile().setFilecontent(null);
		lssamplefileRepository.save(objorder.getLssamplefile());
		if (objorder.getLsparsedparameters() != null) {
			objorder.getLsparsedparameters().forEach((param) -> param.setBatchcode(objorder.getBatchcode()));
			lsparsedparametersRespository.saveAll(objorder.getLsparsedparameters());
		}
//		lsorderworkflowhistoryRepositroy.save(objorder.getLsorderworkflowhistory());
		List<LsOrderattachments> lstattach = lsOrderattachmentsRepository
				.findByBatchcodeOrderByAttachmentcodeDesc(objorder.getBatchcode());
		objorder.setLsOrderattachments(lstattach);
		try {
			objorder.setCompletedtimestamp(commonfunction.getCurrentUtcTime());
			if (objorder.getLsordernotification() != null) {
				objorder.getLsordernotification().setIscompleted(true);
				lsordernotificationrepo.save(objorder.getLsordernotification());
			}
		} catch (ParseException e) {

			e.printStackTrace();
		}
		List<Lsorderworkflowhistory> objLstLsorderworkflowhistories = lsorderworkflowhistoryRepositroy
				.findByBatchcodeOrderByHistorycode(objorder.getBatchcode());
		lsorderworkflowhistoryRepositroy.saveAll(objLstLsorderworkflowhistories);

		objorder.setLsorderworkflowhistory(objLstLsorderworkflowhistories);

		List<LSSelectedTeam> teamobj = LSSelectedTeamRepository.findByBatchcode(objorder.getBatchcode());
		objorder.setLsselectedTeam(teamobj);

		lslogilablimsorderdetailRepository.save(objorder);

		updatenotificationfororder(objorder);
		objorder.setResponse(new Response());
		objorder.getResponse().setStatus(true);
		objorder.getResponse().setInformation("IDS_MSG_ORDERCMPLT");
		return objorder;
	}

	public LSlogilablimsorderdetail updateworflowforOrder(LSlogilablimsorderdetail objorder) throws ParseException {

		LSlogilablimsorderdetail objDbOrder = lslogilablimsorderdetailRepository
				.findByBatchcodeOrderByBatchcodeDesc(objorder.getBatchcode());

		updatenotificationfororderworkflow(objorder, lslogilablimsorderdetailRepository
				.findByBatchcodeOrderByBatchcodeDesc(objorder.getBatchcode()).getLsworkflow());

		// for email notify
		String email = env.getProperty("spring.emailnotificationconfig");
		if (email != null && email.equals("true")) {
			try {
				updateemailnotificationorderworkflow(objorder, lslogilablimsorderdetailRepository
						.findByBatchcodeOrderByBatchcodeDesc(objorder.getBatchcode()).getLsworkflow());

			} catch (Exception e) {

				System.out.println(e);
			}

		}

		for (int k = 0; k < objorder.getLsorderworkflowhistory().size(); k++) {
			if (objorder.getLsorderworkflowhistory().get(k).getHistorycode() == null) {
				try {
					objorder.getLsorderworkflowhistory().get(k).setCreatedate(commonfunction.getCurrentUtcTime());
				} catch (ParseException e) {

					e.printStackTrace();
				}
			}
		}

		lsorderworkflowhistoryRepositroy.saveAll(objorder.getLsorderworkflowhistory());
		try {
			objorder.setModifidate(commonfunction.getCurrentUtcTime());
		} catch (ParseException e) {

			e.printStackTrace();
		}

		objorder.setLockeduser(objDbOrder.getLockeduser());
		objorder.setLockedusername(objDbOrder.getLockedusername());
		objorder.setActiveuser(objDbOrder.getActiveuser());
		objorder.setSitecode(objDbOrder.getSitecode());

		lslogilablimsorderdetailRepository.save(objorder);

		updatenotificationforworkflowapproval(objorder);
		LSlogilablimsorderdetail rtnobjDbOrder = lslogilablimsorderdetailRepository
				.findByBatchcodeOrderByBatchcodeDesc(objorder.getBatchcode());

		if (objorder.getFiletype() != 0 && objorder.getOrderflag().toString().trim().equals("N")) {
			LSworkflow objlastworkflow = lsworkflowRepository.findTopByAndLssitemasterAndStatusOrderByWorkflowcodeDesc(
					objorder.getObjLoggeduser().getLssitemaster(), 1);
			if (objlastworkflow != null
					&& objorder.getLsworkflow().getWorkflowcode() == objlastworkflow.getWorkflowcode()) {
				rtnobjDbOrder.setIsFinalStep(1);
			} else {
				rtnobjDbOrder.setIsFinalStep(0);
			}
		}
		// for eln trail order complete
		if (objorder.getIsmultitenant().equals(2) && objorder.getAccouttype().equals(1)) {
			objorder.setOrderflag("R");
			try {
				CompleteOrder(objorder);
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		if (objorder.getApprovelstatus() != null && objorder.getApprovelstatus() == 3) {
			objDbOrder.setRepeat(false);
			objDbOrder.setBatchcode(objorder.getBatchcode());
			objDbOrder.setBatchid(objorder.getBatchid());
			stopautoregister(objDbOrder);
		}

		return rtnobjDbOrder;
	}

	private void updateemailnotificationorderworkflow(LSlogilablimsorderdetail objorder, LSworkflow previousworkflow) {

		try {

			String Content = "";
			LSuserMaster obj = lsuserMasterRepository.findByusercode(objorder.getObjLoggeduser().getUsercode());

			if (objorder.getApprovelstatus() != null) {
				LSusersteam objteam;
				if(objorder.getLsprojectmaster().getLsusersteam() == null) {
					LSprojectmaster obj1 = lsprojectmasterRepository
							.findByProjectcode(objorder.getLsprojectmaster().getProjectcode());
					objteam = lsusersteamRepository.findByteamcode(obj1.getLsusersteam().getTeamcode());
				}else {
					 objteam= lsusersteamRepository.findByteamcode(objorder.getLsprojectmaster().getLsusersteam().getTeamcode());
				}	

				String previousworkflowname = previousworkflow != null ? previousworkflow.getWorkflowname() : "";

				if (objorder.getApprovelstatus() == 1) {
					Content = "Sheet Order: " + objorder.getBatchid() + " was sent from the workflow level : "
							+ previousworkflowname + " to : " + objorder.getLsworkflow().getWorkflowname() + " by "
							+ obj.getUsername() + "";
				} else if (objorder.getApprovelstatus() == 2) {
					Content = "Sheet Order: " + objorder.getBatchid()
							+ " was returned to the previous level of workflow from : " + previousworkflowname
							+ " to : " + objorder.getLsworkflow().getWorkflowname() + " by " + obj.getUsername() + "";
				} else if (objorder.getApprovelstatus() == 3) {
					Content = "Sheet Order: " + objorder.getBatchid() + " was rejected by " + obj.getUsername()
							+ "  upon review";
				}
				if (previousworkflowname.equals(objorder.getLsworkflow().getWorkflowname())
						&& objorder.getApprovelstatus() == 1) {
					Content = "Sheet Order: " + objorder.getBatchid() + " was approved by the " + obj.getUsername()
							+ " on the Workflow: " + objorder.getLsworkflow().getWorkflowname() + "";
				}
				List<LSuserteammapping> lstusers = objteam.getLsuserteammapping();
				List<Email> lstemail = new ArrayList<Email>();
				for (int i = 0; i < lstusers.size(); i++) {
					if (!(objorder.getObjLoggeduser().getUsercode()
							.equals(lstusers.get(i).getLsuserMaster().getUsercode()))) {
						Email objemail = new Email();
						jakarta.mail.internet.MimeMessage message = mailSender.createMimeMessage();
						MimeMessageHelper helper = new MimeMessageHelper(message);

						if (lstusers.get(i).getLsuserMaster().getEmailid() != null) {
							objemail.setMailto(lstusers.get(i).getLsuserMaster().getEmailid());
							helper.setTo(lstusers.get(i).getLsuserMaster().getEmailid());
						}
						// email notification
						String subject = "Auto message generation for Sheet Order";
						String from = env.getProperty("spring.mail.username");
						objemail.setMailfrom(from);
						objemail.setMailcontent(Content);
						objemail.setSubject(subject);
						lstemail.add(objemail);
						helper.setSubject(subject);
						helper.setFrom(from);
						helper.setText("<p>" + Content + "</p><br><p> Order : </p><a href='" + objorder.getOrderlink()
								+ "'><b>" + objorder.getBatchid() + "</b></a>", true);

						try {
							mailSender.send(message);
							System.out.println("---email notification sent---");
						} catch (MailSendException e) {

							System.out.println("---email notification log---");
							System.out.println(e);
						}

					}
				}

				EmailRepository.saveAll(lstemail);
				lstemail.removeAll(lstemail);

			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	private void updatenotificationforworkflowapproval(LSlogilablimsorderdetail objorder) {
		String Details = "";
		String Notification = "";

		List<LSnotification> lstnotifications = new ArrayList<LSnotification>();
		LSuserMaster createby = lsuserMasterRepository.findByusercode(objorder.getLsuserMaster().getUsercode());
		LSuserMaster obj = lsuserMasterRepository.findByusercode(objorder.getObjLoggeduser().getUsercode());
		LSnotification objnotify = new LSnotification();

		String batchid = "";
//		SequenceTable seqobj =  sequenceTableRepository.findBySequencecodeOrderBySequencecode(1);
//		Boolean Applicationseq = seqobj.getSequenceview().equals(2) ? true : false;
//		batchid = Applicationseq 
//				?  objorder.getSequenceid() != null 
//					? objorder.getSequenceid() : objorder.getBatchid() 
//				: objorder.getBatchid();
		batchid = objorder.getSequenceid() != null ? objorder.getSequenceid() : objorder.getBatchid();

		try {
			for (int k = 0; k < objorder.getLsworkflow().getLsworkflowgroupmapping().size(); k++) {
//				List<LSMultiusergroup> userobj = lsMultiusergroupRepositery
//						.findBylsusergroup(objorder.getLsworkflow().getLsworkflowgroupmapping().get(k).getLsusergroup());
//	
//				List<Integer> objnotifyuser = userobj.stream().map(LSMultiusergroup::getUsercode) .collect(Collectors.toList());
//				
//				List<LSuserMaster> objuser = lsuserMasterRepository.findByUsercodeInAndUserretirestatusNot(objnotifyuser, 1);

//				LSusersteam objteam = lsusersteamRepository
//						.findByteamcode(objorder.getLsprojectmaster().getLsusersteam().getTeamcode());
				LSusersteam objteam = new LSusersteam();
				if (objorder.getLsprojectmaster() != null && objorder.getLsprojectmaster().getLsusersteam() != null
						&& objorder.getLsprojectmaster().getLsusersteam().getTeamcode() != null) {
					objteam = lsusersteamRepository
							.findByteamcode(objorder.getLsprojectmaster().getLsusersteam().getTeamcode());
				} else {
					LSprojectmaster obj1 = lsprojectmasterRepository
							.findByProjectcode(objorder.getLsprojectmaster().getProjectcode());
					objteam = lsusersteamRepository.findByteamcode(obj1.getLsusersteam().getTeamcode());

				}
				List<LSuserteammapping> lstusers = objteam.getLsuserteammapping();

				if (objorder.getApprovelstatus() != null && objorder.getIsFinalStep() != 1) {

					for (int i = 0; i < lstusers.size(); i++) {
						if (!(objorder.getObjLoggeduser().getUsercode()
								.equals(lstusers.get(i).getLsuserMaster().getUsercode()))) {

							if (objorder.getApprovelstatus() == 1) {
								Notification = "USERAPPROVAL";
								objnotify.setNotifationto(lstusers.get(i).getLsuserMaster());
							} else if (objorder.getApprovelstatus() == 2) {
								Notification = "USERORDERRETURN";
								objnotify.setNotifationto(lstusers.get(i).getLsuserMaster());

							} else if (objorder.getApprovelstatus() == 3) {
								Notification = "USERREJECT";
								objnotify.setNotifationto(createby);
							}

							Details = "{\"ordercode\":\"" + objorder.getBatchcode() + "\", \"order\":\"" + batchid
									+ "\", \"user\":\"" + obj.getUsername() + "\", \"comment\":\""
									+ objorder.getComment() + "\", \"workflowname\":\""
									+ objorder.getLsworkflow().getWorkflowname() + "\"}";

							objnotify.setNotifationfrom(obj);
							objnotify.setNotificationdate(commonfunction.getCurrentUtcTime());
							objnotify.setNotification(Notification);
							objnotify.setNotificationdetils(Details);
							objnotify.setIsnewnotification(1);
							objnotify.setNotificationpath("/registertask");
							objnotify.setNotificationfor(1);
							if (objorder.getLsworkflow().getLssitemaster() != null
									&& objorder.getLsworkflow().getLssitemaster().getSitecode() != null) {
								objnotify.setSitecode(objorder.getLsworkflow().getLssitemaster().getSitecode());
							}
							lstnotifications.add(objnotify);
						}
					}
				} else {
					for (int i = 0; i < lstusers.size(); i++) {

						if (!(objorder.getObjLoggeduser().getUsercode()
								.equals(lstusers.get(i).getLsuserMaster().getUsercode()))) {

							if (objorder.getApprovelstatus() == 3 && objorder.getApproved() == null) {
								Notification = "USERREJECT";
								objnotify.setNotifationto(createby);
							} else if (objorder.getApproved() == 1 && objorder.getRejected() == null) {
								Notification = "SHEETORDERAPPROVED";
								objnotify.setNotifationto(createby);
							} else if (objorder.getApprovelstatus() == 2) {
								Notification = "USERRETURN";
								objnotify.setNotifationto(createby);
							}

							Details = "{\"ordercode\":\"" + objorder.getBatchcode() + "\", \"order\":\"" + batchid
									+ "\", \"user\":\"" + obj.getUsername() + "\", \"comment\":\""
									+ objorder.getComment() + "\"}";
							objnotify.setNotifationfrom(obj);
							objnotify.setNotificationdate(commonfunction.getCurrentUtcTime());
							objnotify.setNotification(Notification);
							objnotify.setNotificationdetils(Details);
							objnotify.setIsnewnotification(1);
							objnotify.setNotificationpath("/registertask");
							objnotify.setNotificationfor(1);
							if (objorder.getLsworkflow().getLssitemaster() != null
									&& objorder.getLsworkflow().getLssitemaster().getSitecode() != null) {
								objnotify.setSitecode(objorder.getLsworkflow().getLssitemaster().getSitecode());
							}
							lstnotifications.add(objnotify);
						}
					}

				}
				lsnotificationRepository.saveAll(lstnotifications);

				lstnotifications.removeAll(lstnotifications);
			}
		} catch (Exception e) {

		}
		Details = null;
		Notification = null;
		batchid = null;

	}

	public boolean Updatesamplefileversion(LSsamplefile objfile) {
		int Versionnumber = 0;
		LSsamplefileversion objLatestversion = lssamplefileversionRepository
				.findFirstByFilesamplecodeOrderByVersionnoDesc(objfile);
		if (objLatestversion != null) {
			Versionnumber = objLatestversion.getVersionno();
		}

		Versionnumber++;

		LSsamplefile objesixting = lssamplefileRepository.findByfilesamplecode(objfile.getFilesamplecode());
		if (objesixting == null) {
			objesixting = objfile;
		}
		LSsamplefileversion objversion = new LSsamplefileversion();

		objversion.setBatchcode(objesixting.getBatchcode());
		objversion.setCreateby(objesixting.getCreateby());
		objversion.setCreatebyuser(objesixting.getCreatebyuser());
		objversion.setCreatedate(objesixting.getCreatedate());
		objversion.setModifiedby(objesixting.getModifiedby());
		objversion.setModifieddate(objesixting.getModifieddate());
		objversion.setFilecontent(objesixting.getFilecontent());
		objversion.setProcessed(objesixting.getProcessed());
		objversion.setResetflag(objesixting.getResetflag());
		objversion.setTestid(objesixting.getTestid());
		objversion.setVersionno(Versionnumber);
		objversion.setFilesamplecode(objesixting);

		lssamplefileversionRepository.save(objversion);
		if (objfile.getObjActivity().getObjsilentaudit() != null) {
			// objpwd.getObjsilentaudit().setModuleName("UserManagement");
			objfile.getObjActivity().getObjsilentaudit()
					.setComments("Sheet" + " " + objfile.getFilesamplecode() + " " + " was versioned to version_"
							+ Versionnumber + " " + "by the user" + " " + objfile.getLsuserMaster().getUsername());
			objfile.getObjActivity().getObjsilentaudit().setTableName("LSfile");
			try {
				objfile.getObjActivity().getObjsilentaudit().setTransactiondate(commonfunction.getCurrentUtcTime());
			} catch (ParseException e) {

				e.printStackTrace();
			}
			lscfttransactionRepository.save(objfile.getObjActivity().getObjsilentaudit());
		}

		objesixting = null;
		objLatestversion = null;
		objversion = null;

		return true;
	}

	public List<LSsamplefileversion> Getfileversions(LSsamplefile objfile) {
		return lssamplefileversionRepository.findByFilesamplecodeOrderByVersionnoDesc(objfile);
	}

	@SuppressWarnings("resource")
	public String GetfileverContent(LSsamplefile objfile) throws IOException {
		String Content = "";
		if (objfile.getVersionno() == null || objfile.getVersionno() == 0) {
			Content = lssamplefileRepository.findByfilesamplecode(objfile.getFilesamplecode()).getFilecontent();
			if (objfile != null) {
				if (objfile.getIsmultitenant() == 1) {
					CloudOrderCreation objCreation = cloudOrderCreationRepository
							.findTop1ById((long) objfile.getFilesamplecode());
					if (objCreation != null && objCreation.getContainerstored() == 0) {
						Content = objCreation.getContent();
					} else {
						Content = objCloudFileManipulationservice.retrieveCloudSheets(objCreation.getFileuid(),
								TenantContext.getCurrentTenant() + "ordercreation");
					}
				} else {
					GridFSFile largefile = gridFsTemplate
							.findOne(new Query(Criteria.where("filename").is("order_" + objfile.getFilesamplecode())));
					if (largefile == null) {
						largefile = gridFsTemplate
								.findOne(new Query(Criteria.where("_id").is("order_" + objfile.getFilesamplecode())));
					}

					if (largefile != null) {
						GridFsResource resource = gridFsTemplate.getResource(largefile.getFilename());
						Content = new BufferedReader(
								new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)).lines()
								.collect(Collectors.joining("\n"));
					} else {
						if (mongoTemplate.findById(objfile.getFilesamplecode(), OrderCreation.class) != null) {
							Content = mongoTemplate.findById(objfile.getFilesamplecode(), OrderCreation.class)
									.getContent();
						}
					}
				}
			}
		} else {
			LSsamplefileversion objVersion = lssamplefileversionRepository
					.findByFilesamplecodeAndVersionnoOrderByVersionnoDesc(objfile, objfile.getVersionno());
			Content = objVersion.getFilecontent();

			if (objVersion != null) {
				if (objfile.getIsmultitenant() == 1 || objfile.getIsmultitenant() == 2) {
					CloudOrderVersion objCreation = cloudOrderVersionRepository
							.findTop1ById((long) objVersion.getFilesamplecodeversion());
					if (objCreation != null && objCreation.getContainerstored() == 0) {
						Content = objCreation.getContent();
					} else {
						Content = objCloudFileManipulationservice.retrieveCloudSheets(objCreation.getFileuid(),
								commonfunction.getcontainername(objfile.getIsmultitenant(),
										TenantContext.getCurrentTenant()) + "orderversion");
					}
				} else {

					GridFSFile largefile = gridFsTemplate.findOne(new Query(
							Criteria.where("filename").is("orderversion_" + objVersion.getFilesamplecodeversion())));
					if (largefile == null) {
						largefile = gridFsTemplate.findOne(new Query(
								Criteria.where("_id").is("orderversion_" + objVersion.getFilesamplecodeversion())));
					}

					if (largefile != null) {
						GridFsResource resource = gridFsTemplate.getResource(largefile.getFilename());
						Content = new BufferedReader(
								new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)).lines()
								.collect(Collectors.joining("\n"));
					} else {

						if (mongoTemplate.findById(objVersion.getFilesamplecodeversion(),
								OrderCreation.class) != null) {
							Content = mongoTemplate.findById(objVersion.getFilesamplecodeversion(), OrderCreation.class)
									.getContent();
						}
					}
				}
			}
		}

		return Content;
	}

	public List<LSlogilablimsorderdetail> Getorderbyfile(LSlogilablimsorderdetail objorder) {

		List<LSlogilablimsorderdetail> lstorder = new ArrayList<LSlogilablimsorderdetail>();

		if (objorder.getLsuserMaster().getUsername().trim().toLowerCase().equals("administrator")) {
			lstorder = lslogilablimsorderdetailRepository
					.findByFiletypeAndLsfileOrderByBatchcodeDesc(objorder.getFiletype(), objorder.getLsfile());
		} else {
			List<LSuserteammapping> lstteammap = lsuserteammappingRepository
					.findBylsuserMaster(objorder.getLsuserMaster());
			List<LSusersteam> lstteam = lsusersteamRepository.findByLsuserteammappingInAndLssitemaster(lstteammap,
					objorder.getLsuserMaster().getLssitemaster());
			List<LSprojectmaster> lstproject = lsprojectmasterRepository.findByLsusersteamIn(lstteam);
//			List<LSsamplemaster> lstsample = lssamplemasterrepository.findByLssitemasterAndStatus(objorder.getLsuserMaster().getLssitemaster(), 1);

			List<Integer> lstsampleint = lssamplemasterrepository.getDistinctByLssitemasterSitecodeAndStatus(
					objorder.getLsuserMaster().getLssitemaster().getSitecode(), 1);
			List<LSsamplemaster> lstsample = new ArrayList<>();
			LSsamplemaster sample = null;
			if (lstsampleint.size() > 0) {
				for (Integer item : lstsampleint) {
					sample = new LSsamplemaster();
					sample.setSamplecode(item);
					lstsample.add(sample);
					sample = null; // Set sample to null after adding it to the list
				}
			}
//			lstorder = lslogilablimsorderdetailRepository
//					.findByFiletypeAndLsfileAndLsprojectmasterInOrderByBatchcodeDesc(objorder.getFiletype(),
//							objorder.getLsfile(), lstproject);

//			lstorder = lslogilablimsorderdetailRepository.
//			findByLsprojectmasterInAndFiletypeAndAssignedtoIsNullAndLsfileOrLsprojectmasterIsNullAndLssamplemasterIsNullAndFiletypeAndAssignedtoIsNullAndLsfileOrLsprojectmasterIsNullAndLssamplemasterInAndFiletypeAndAssignedtoIsNullAndViewoptionAndLsfileOrLsprojectmasterIsNullAndLssamplemasterInAndFiletypeAndAssignedtoIsNullAndViewoptionAndLsfileOrLsprojectmasterIsNullAndLssamplemasterInAndFiletypeAndAssignedtoIsNullAndViewoptionAndLsfile(
//			lstproject, objorder.getFiletype(), objorder.getLsfile(),
//			objorder.getFiletype(), objorder.getLsfile(),
//			lstsample, objorder.getFiletype(), 1,objorder.getLsfile(),
//			lstsample, objorder.getFiletype(), 2,objorder.getLsfile(),
//			lstsample, objorder.getFiletype(), 3,objorder.getLsfile());

			lstorder = lslogilablimsorderdetailRepository
					.findByLsprojectmasterInAndFiletypeAndAssignedtoIsNullAndLsfile(lstproject, objorder.getFiletype(),
							objorder.getLsfile());

			int chunkSize = Integer.parseInt(env.getProperty("lssamplecount"));
			int totalSamples = lstsample.size();
			List<LSlogilablimsorderdetail> lstorderlimsobj = IntStream
					.range(0, (totalSamples + chunkSize - 1) / chunkSize).parallel().mapToObj(i -> {
						int startIndex = i * chunkSize;
						int endIndex = Math.min(startIndex + chunkSize, totalSamples);
						List<LSsamplemaster> currentChunk = lstsample.subList(startIndex, endIndex);
						List<LSlogilablimsorderdetail> orderChunk = new ArrayList<>();
						orderChunk.addAll(logilablimsorderdetailsRepository
								.findByLsprojectmasterIsNullAndLssamplemasterIsNullAndFiletypeAndAssignedtoIsNullAndLsfileOrderByBatchcodeDesc(
										objorder.getFiletype(), objorder.getLsfile()));
						orderChunk.addAll(logilablimsorderdetailsRepository
								.findByLsprojectmasterIsNullAndLssamplemasterInAndFiletypeAndAssignedtoIsNullAndViewoptionAndLsfileOrderByBatchcodeDesc(
										currentChunk, objorder.getFiletype(), 1, objorder.getLsfile()));
						orderChunk.addAll(logilablimsorderdetailsRepository
								.findByLsprojectmasterIsNullAndLssamplemasterInAndFiletypeAndAssignedtoIsNullAndViewoptionAndLsfileOrderByBatchcodeDesc(
										currentChunk, objorder.getFiletype(), 2, objorder.getLsfile()));
						orderChunk.addAll(logilablimsorderdetailsRepository
								.findByLsprojectmasterIsNullAndLssamplemasterInAndFiletypeAndAssignedtoIsNullAndViewoptionAndLsfileOrderByBatchcodeDesc(
										currentChunk, objorder.getFiletype(), 3, objorder.getLsfile()));
						return orderChunk;
					}).flatMap(List::stream).collect(Collectors.toList());
			System.out.print(lstorderlimsobj);
			lstorderlimsobj.addAll(lstorder);
			lstorderlimsobj.forEach(objorderDetail -> objorderDetail.setLstworkflow(objorder.getLstworkflow()));
			return lstorderlimsobj;
		}

		return lstorder;

	}

	public Map<String, Object> GetOrdersByLinkedFiles(Map<String, Object> mapObj) {
		ObjectMapper objectMapper = new ObjectMapper();

		int filetype = objectMapper.convertValue(mapObj.get("filetype"), Integer.class);
		LSuserMaster lsusMaster = objectMapper.convertValue(mapObj.get("lsuserMaster"), LSuserMaster.class);
		List<LSfile> lSfiles = objectMapper.convertValue(mapObj.get("lstLSfiles"), new TypeReference<List<LSfile>>() {
		});

		List<LogilabOrderDetails> lstallorders;

		if ("administrator".equalsIgnoreCase(lsusMaster.getUsername().trim())) {
			lstallorders = logilablimsorderdetailsRepository
					.findByFiletypeAndLsfileInAndApprovelstatusNotOrFiletypeAndLsfileInAndApprovelstatusIsNullAndOrdercancellIsNullOrderByBatchcodeDesc(
							filetype, lSfiles, 3, filetype, lSfiles);
		} else {
			List<LSuserteammapping> teamMappings = lsuserteammappingRepository.findBylsuserMaster(lsusMaster);
			List<LSusersteam> teams = lsusersteamRepository.findByLsuserteammappingInAndLssitemaster(teamMappings,
					lsusMaster.getLssitemaster());
			List<LSprojectmaster> projects = lsprojectmasterRepository.findByLsusersteamIn(teams);
			List<Elnmaterial> materials = elnmaterialRepository
					.findByNsitecode(lsusMaster.getLssitemaster().getSitecode());

			lstallorders = Stream.concat(logilablimsorderdetailsRepository
					.findByLsprojectmasterInAndFiletypeAndLsfileInAndApprovelstatusNotAndOrdercancellIsNullOrLsprojectmasterInAndFiletypeAndAssignedtoIsNullAndLsfileInAndApprovelstatusIsNullAndOrdercancellIsNullOrderByBatchcodeDesc(
							projects, filetype, lSfiles, 3, projects, filetype, lSfiles)
					.stream(),

					Stream.concat(logilablimsorderdetailsRepository
							.findByLsprojectmasterIsNullAndLssamplemasterIsNullAndFiletypeAndAssignedtoIsNullAndLsfileInOrderByBatchcodeDesc(
									filetype, lSfiles)
							.stream(),
							materials.isEmpty() ? Stream.empty()
									: Stream.concat(logilablimsorderdetailsRepository
											.findByLsprojectmasterIsNullAndElnmaterialInAndFiletypeAndAssignedtoIsNullAndViewoptionAndLsfileInOrderByBatchcodeDesc(
													materials, filetype, 1, lSfiles)
											.stream(),
											Stream.concat(logilablimsorderdetailsRepository
													.findByLsprojectmasterIsNullAndElnmaterialInAndFiletypeAndAssignedtoIsNullAndViewoptionAndLsfileInOrderByBatchcodeDesc(
															materials, filetype, 2, lSfiles)
													.stream(),
													logilablimsorderdetailsRepository
															.findByLsprojectmasterIsNullAndElnmaterialInAndFiletypeAndAssignedtoIsNullAndViewoptionAndLsfileInOrderByBatchcodeDesc(
																	materials, filetype, 3, lSfiles)
															.stream()))))
					.collect(Collectors.toList());
		}

		mapObj.put("orders", lstallorders);
		return mapObj;
	}

	@SuppressWarnings("resource")
	public LogilabOrderDetails GetorderForLINKsheet(LSlogilablimsorderdetail objorder) throws IOException {

		LogilabOrderDetails objupdatedorder = logilablimsorderdetailsRepository
				.findByBatchcode(objorder.getBatchcode());
//		List<LSlogilablimsorder> lsLogilaborders = lslogilablimsorderRepository
//				.findBybatchid(objupdatedorder.getBatchid());
//		List<String> lsorderno = new ArrayList<String>();
//		objupdatedorder.setResponse(new Response());
//
//		if (lsLogilaborders != null && lsLogilaborders.size() > 0) {
//			int i = 0;
//			while (lsLogilaborders.size() > i) {
//				lsorderno.add(lsLogilaborders.get(i).getOrderid().toString());
//				i++;
//			}
//		}
//		objupdatedorder.setLsLSlogilablimsorder(lsLogilaborders);
//
//		if (objupdatedorder.getLsprojectmaster() != null && objorder.getLstworkflow() != null) {
//			List<Integer> lstworkflowcode = objorder.getLstworkflow().stream().map(LSworkflow::getWorkflowcode)
//					.collect(Collectors.toList());
//			if (objorder.getLstworkflow() != null && objupdatedorder.getLsworkflow() != null
//					&& lstworkflowcode.contains(objupdatedorder.getLsworkflow().getWorkflowcode())) {
//				objupdatedorder.setCanuserprocess(true);
//			} else {
//				objupdatedorder.setCanuserprocess(false);
//			}
//		}
//
//		if (objupdatedorder.getFiletype() != 0 && objupdatedorder.getOrderflag().toString().trim().equals("N")) {
//			LSworkflow objlastworkflow = lsworkflowRepository
//					.findTopByAndLssitemasterOrderByWorkflowcodeDesc(objorder.getObjLoggeduser().getLssitemaster());
//			if (objlastworkflow != null && objupdatedorder.getLsworkflow() != null
//					&& objupdatedorder.getLsworkflow().equals(objlastworkflow)) {
//				objupdatedorder.setIsFinalStep(1);
//			} else {
//				objupdatedorder.setIsFinalStep(0);
//			}
//		}
//
//		if (objupdatedorder.getFiletype() == 0) {
//			objupdatedorder
//					.setLstestparameter(lStestparameterRepository.findByntestcode(objupdatedorder.getTestcode()));
//		}

		if (objupdatedorder.getLssamplefile() != null) {
			if (objorder.getIsmultitenant() == 1) {
				CloudOrderCreation objCreation = cloudOrderCreationRepository
						.findTop1ById((long) objupdatedorder.getLssamplefile().getFilesamplecode());
				if (objCreation != null && objCreation.getContainerstored() == 0) {
					objupdatedorder.getLssamplefile().setFilecontent(objCreation.getContent());
				} else {
					objupdatedorder.getLssamplefile().setFilecontent(
							objCloudFileManipulationservice.retrieveCloudSheets(objCreation.getFileuid(),
									TenantContext.getCurrentTenant() + "ordercreation"));
				}
			} else {

				GridFSFile largefile = gridFsTemplate.findOne(new Query(Criteria.where("filename")
						.is("order_" + objupdatedorder.getLssamplefile().getFilesamplecode())));
				if (largefile == null) {
					largefile = gridFsTemplate.findOne(new Query(Criteria.where("_id")
							.is("order_" + objupdatedorder.getLssamplefile().getFilesamplecode())));
				}

				if (largefile != null) {
					GridFsResource resource = gridFsTemplate.getResource(largefile.getFilename());
					objupdatedorder.getLssamplefile().setFilecontent(
							new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))
									.lines().collect(Collectors.joining("\n")));
				} else {

					if (mongoTemplate.findById(objupdatedorder.getLssamplefile().getFilesamplecode(),
							OrderCreation.class) != null) {
						objupdatedorder.getLssamplefile().setFilecontent(mongoTemplate
								.findById(objupdatedorder.getLssamplefile().getFilesamplecode(), OrderCreation.class)
								.getContent());
					}
				}
			}
		}

//		lsLogilaborders = null;
//		objupdatedorder.setLstworkflow(objorder.getLstworkflow());
//
//		if (objorder.getLsuserMaster() != null && objorder.getLsuserMaster().getUnifieduserid() != null) {
//			LScentralisedUsers objUserId = new LScentralisedUsers();
//			objUserId.setUnifieduserid(objorder.getLsuserMaster().getUnifieduserid());
//			objupdatedorder.setLscentralisedusers(userService.Getcentraliseduserbyid(objUserId));
//		}
//		objupdatedorder.getResponse().setStatus(true);
		return objupdatedorder;
	}

	public List<LogilabOrderDetails> Getexcelorder(LSlogilablimsorderdetail objorder) {
		List<LogilabOrderDetails> lstorder = new ArrayList<>();

		if ("administrator".equalsIgnoreCase(objorder.getLsuserMaster().getUsername().trim())) {
			lstorder = logilablimsorderdetailsRepository
					.findByFiletypeAndApprovelstatusNotAndOrdercancellIsNullOrFiletypeAndApprovelstatusIsNullAndOrdercancellIsNullOrderByBatchcodeDesc(
							objorder.getFiletype(), 3, objorder.getFiletype());
		} else {
			List<LSuserteammapping> lstteammap = lsuserteammappingRepository
					.findBylsuserMaster(objorder.getLsuserMaster());
			List<LSusersteam> lstteam = lsusersteamRepository.findByLsuserteammappingInAndLssitemaster(lstteammap,
					objorder.getLsuserMaster().getLssitemaster());
			List<LSprojectmaster> lstproject = lsprojectmasterRepository.findByLsusersteamIn(lstteam);
			List<Elnmaterial> nmaterialcode = new ArrayList<>();

			nmaterialcode = elnmaterialRepository
					.getmaterialonsitecodebased(objorder.getLsuserMaster().getLssitemaster().getSitecode());

//				 nmaterialcode = elnmaterialRepository
//							.findByNsitecode(objorder.getLsuserMaster().getLssitemaster().getSitecode());

			List<LogilabOrderDetails> projectOrders = logilablimsorderdetailsRepository
					.findByFiletypeAndLsprojectmasterInAndApprovelstatusNotAndOrdercancellIsNullOrFiletypeAndLsprojectmasterInAndApprovelstatusIsNullAndOrdercancellIsNullOrderByBatchcodeDesc(
							objorder.getFiletype(), lstproject, 3, objorder.getFiletype(), lstproject);

			Stream<LogilabOrderDetails> ordersStream = Stream.empty();

			if (!nmaterialcode.isEmpty()) {
//				ordersStream = Stream.concat(ordersStream,
//						logilablimsorderdetailsRepository
//								.findByLsprojectmasterIsNullAndElnmaterialInAndFiletypeAndAssignedtoIsNullAndViewoption(
//										nmaterialcode, objorder.getFiletype(), 1)
//								.stream());
//
//				ordersStream = Stream.concat(ordersStream,
//						logilablimsorderdetailsRepository
//								.findByLsprojectmasterIsNullAndElnmaterialInAndFiletypeAndAssignedtoIsNullAndViewoption(
//										nmaterialcode, objorder.getFiletype(), 2)
//								.stream());
//
//				ordersStream = Stream.concat(ordersStream,
//						logilablimsorderdetailsRepository
//								.findByLsprojectmasterIsNullAndElnmaterialInAndFiletypeAndAssignedtoIsNullAndViewoption(
//										nmaterialcode, objorder.getFiletype(), 3)
//								.stream());
				ordersStream = Stream.concat(ordersStream, logilablimsorderdetailsRepository
						.findByLsprojectmasterIsNullAndElnmaterialInAndFiletypeAndAssignedtoIsNullAndViewoptionAndApprovelstatusNotAndOrdercancellIsNullAndSitecode(
								nmaterialcode, objorder.getFiletype(), 1, 3,
								objorder.getLsuserMaster().getLssitemaster().getSitecode())
						.stream());

				ordersStream = Stream.concat(ordersStream, logilablimsorderdetailsRepository
						.findByLsprojectmasterIsNullAndElnmaterialInAndFiletypeAndAssignedtoIsNullAndViewoptionAndApprovelstatusNotAndOrdercancellIsNullAndSitecode(
								nmaterialcode, objorder.getFiletype(), 2, 3,
								objorder.getLsuserMaster().getLssitemaster().getSitecode())
						.stream());

				ordersStream = Stream.concat(ordersStream, logilablimsorderdetailsRepository
						.findByLsprojectmasterIsNullAndElnmaterialInAndFiletypeAndAssignedtoIsNullAndViewoptionAndApprovelstatusNotAndOrdercancellIsNullAndSitecodeAndLsuserMaster(
								nmaterialcode, objorder.getFiletype(), 3, 3,
								objorder.getLsuserMaster().getLssitemaster().getSitecode(), objorder.getLsuserMaster())
						.stream());
			}

			ordersStream = Stream.concat(ordersStream, logilablimsorderdetailsRepository
					.findByLsprojectmasterIsNullAndElnmaterialIsNullAndFiletypeAndAssignedtoIsNullAndViewoptionAndApprovelstatusNotAndOrdercancellIsNullAndSitecodeOrLsprojectmasterIsNullAndElnmaterialIsNullAndFiletypeAndAssignedtoIsNullAndViewoptionAndApprovelstatusIsNullAndOrdercancellIsNullAndSitecodeOrLsprojectmasterIsNullAndElnmaterialIsNullAndFiletypeAndAssignedtoIsNullAndViewoptionAndApprovelstatusNotAndOrdercancellIsNullAndSitecodeOrLsprojectmasterIsNullAndElnmaterialIsNullAndFiletypeAndAssignedtoIsNullAndViewoptionAndApprovelstatusIsNullAndOrdercancellIsNullAndSitecode(
							objorder.getFiletype(), 1, 3, objorder.getLsuserMaster().getLssitemaster().getSitecode(),
							objorder.getFiletype(), 1, objorder.getLsuserMaster().getLssitemaster().getSitecode(),
							objorder.getFiletype(), 2, 3, objorder.getLsuserMaster().getLssitemaster().getSitecode(),
							objorder.getFiletype(), 2, objorder.getLsuserMaster().getLssitemaster().getSitecode())
					.stream());

			lstorder = Stream.concat(projectOrders.stream(), ordersStream).collect(Collectors.toList());
		}

		return lstorder;
	}

	public LSlogilablimsorderdetail updateVersionandWorkflowhistory(LSlogilablimsorderdetail objorder) throws ParseException {
		Date currentDate = commonfunction.getCurrentUtcTime();
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		Calendar calto = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		Date fromDate =currentDate;
		Date toDate = currentDate;
		String info = objorder.getResponse().getInformation();
		if ("1".equals(info)) {
		    cal.setTime(currentDate);
		} else if ("2".equals(info)) {
		    cal.setTime(currentDate);
		    cal.add(Calendar.DAY_OF_MONTH, -7);
		} else if ("3".equals(info)) {
		    cal.setTime(currentDate);
		    cal.add(Calendar.DAY_OF_MONTH, -30);
		}else {
			fromDate=objorder.getObjsilentaudit().getFromdate();
			toDate=objorder.getObjsilentaudit().getTodate();
		}
		if(!"4".equals(info)) {
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			fromDate = cal.getTime();
			calto.set(Calendar.HOUR_OF_DAY, 23);
			calto.set(Calendar.MINUTE, 59);
			calto.set(Calendar.SECOND, 59);
			calto.set(Calendar.MILLISECOND, 999);
			toDate = calto.getTime();
		}
		objorder = lslogilablimsorderdetailRepository.findByBatchcodeAndCreatedtimestampBetweenOrderByBatchcodeDesc(objorder.getBatchcode(),fromDate,toDate);
		objorder.setObjsilentaudit(new LScfttransaction());
		objorder.setObjmanualaudit(new LScfttransaction());
		return objorder;
	}

	public LSsamplefile GetResultfileverContent(LSsamplefile objfile) throws IOException {

		LSsamplefile objesixting = lssamplefileRepository.findByfilesamplecode(objfile.getFilesamplecode());
		if (objesixting != null) {
			if (objfile.getIsmultitenant() == 1) {
				CloudOrderCreation objCreation = cloudOrderCreationRepository
						.findTop1ById((long) objfile.getFilesamplecode());
				if (objCreation != null && objCreation.getContainerstored() == 0) {
					lssamplefileRepository.findByfilesamplecode(objfile.getFilesamplecode())
							.setFilecontent(objCreation.getContent());
				} else {
					lssamplefileRepository.findByfilesamplecode(objfile.getFilesamplecode()).setFilecontent(
							objCloudFileManipulationservice.retrieveCloudSheets(objCreation.getFileuid(),
									TenantContext.getCurrentTenant() + "ordercreation"));
				}
			} else {
				if (mongoTemplate.findById(objesixting.getFilesamplecode(), OrderCreation.class) != null) {
					lssamplefileRepository.findByfilesamplecode(objfile.getFilesamplecode()).setFilecontent(
							mongoTemplate.findById(objesixting.getFilesamplecode(), OrderCreation.class).getContent());
				}
			}
		}
		return objesixting;
	}

	public LSlogilablimsorderdetail Uploadattachments(MultipartFile file, Long batchcode, String filename,
			String fileexe, Integer usercode, Date currentdate, Integer islargefile) throws IOException {

		LSlogilablimsorderdetail objorder = lslogilablimsorderdetailRepository
				.findByBatchcodeOrderByBatchcodeDesc(batchcode);

		LsOrderattachments parentobjattachment = lsOrderattachmentsRepository
				.findFirst1ByFilenameAndBatchcodeOrderByAttachmentcodeDesc(filename, batchcode);

		LsOrderattachments objattachment = new LsOrderattachments();

		if (islargefile == 0) {
//			OrderAttachment objfile = fileManipulationservice.storeattachment(file);
			if (fileManipulationservice.storeattachment(file) != null) {
				objattachment.setFileid(fileManipulationservice.storeattachment(file).getId());
			}
		} else {
//			String id = fileManipulationservice.storeLargeattachment(filename, file);
			if (fileManipulationservice.storeLargeattachment(filename, file) != null) {
				objattachment.setFileid(fileManipulationservice.storeLargeattachment(filename, file));
			}
		}

		if (parentobjattachment != null && filename != null && filename.lastIndexOf(".") > -1) {
			Integer versiondata = parentobjattachment.getVersion() != null ? parentobjattachment.getVersion() + 1 : 1;
			String originalname = filename.substring(0, filename.lastIndexOf("."));
			String extension = filename.substring(filename.lastIndexOf("."), filename.length());
			objattachment.setFilename(originalname + "_V" + (versiondata) + extension);
			parentobjattachment.setVersion(versiondata);
			lsOrderattachmentsRepository.save(parentobjattachment);
		} else {
			objattachment.setFilename(filename);
		}

		objattachment.setFileextension(fileexe);
		objattachment.setCreateby(lsuserMasterRepository.findByusercode(usercode));
		objattachment.setCreatedate(currentdate);
		objattachment.setBatchcode(objorder.getBatchcode());
		objattachment.setIslargefile(islargefile);
		objattachment.setVersion(0);
		objattachment.setFilesize(commonService.formatFileSize(file.getSize()));

		LSuserMaster username = lsuserMasterRepository.findByusercode(usercode);
		String name = username.getUsername();
		LScfttransaction list = new LScfttransaction();
		list.setModuleName("Register Task Orders & Execute");
		list.setComments(
				name + " " + "Uploaded the attachement in Order ID: " + objorder.getBatchid() + " " + "successfully");
		list.setActions("Insert");
		list.setSystemcoments("System Generated");
		list.setTableName("profile");
//		list.setTransactiondate(currentdate);
		try {
			list.setTransactiondate(commonfunction.getCurrentUtcTime());
		} catch (ParseException e) {

			e.printStackTrace();
		}
		list.setLsuserMaster(usercode);
		lscfttransactionRepository.save(list);

		if (objorder != null) {
			if (objorder.getLsOrderattachments() == null) {
				objorder.setLsOrderattachments(new ArrayList<LsOrderattachments>());
			}
			objorder.getLsOrderattachments().add(objattachment);
			objorder.getLsOrderattachments().sort((a1, a2) -> {
				Long code1 = a1.getAttachmentcode();
				Long code2 = a2.getAttachmentcode();

				if (code1 == null && code2 == null) {
					return 0; // Both are null, considered equal
				} else if (code1 == null) {
					return -1; // Null values are placed first
				} else if (code2 == null) {
					return 1; // Null values are placed first
				} else {
					return code2.compareTo(code1); // Regular comparison, descending order
				}
			});
		}

		lsOrderattachmentsRepository.saveAll(objorder.getLsOrderattachments());

		username = null;

		return objorder;
	}

	public LSlogilablimsorderdetail CloudUploadattachments(MultipartFile file, Long batchcode, String filename,
			String fileexe, Integer usercode, Date currentdate, Integer islargefile)
			throws IOException, InvalidKeyException, NoSuchAlgorithmException, IllegalArgumentException {

		LSlogilablimsorderdetail objorder = lslogilablimsorderdetailRepository
				.findByBatchcodeOrderByBatchcodeDesc(batchcode);

		LsOrderattachments parentobjattachment = lsOrderattachmentsRepository
				.findFirst1ByFilenameAndBatchcodeOrderByAttachmentcodeDesc(filename, batchcode);

		LsOrderattachments objattachment = new LsOrderattachments();

		if (islargefile == 0) {
			CloudOrderAttachment objfile = cloudFileManipulationservice.storeattachment(file);
			if (objfile != null) {
				objattachment.setFileid(objfile.getFileid());
			}
		}

		if (parentobjattachment != null && filename != null && filename.lastIndexOf(".") > -1) {
			Integer versiondata = parentobjattachment.getVersion() != null ? parentobjattachment.getVersion() + 1 : 1;
			String originalname = filename.substring(0, filename.lastIndexOf("."));
			String extension = filename.substring(filename.lastIndexOf("."), filename.length());
			objattachment.setFilename(originalname + "_V" + (versiondata) + extension);
			parentobjattachment.setVersion(versiondata);
			lsOrderattachmentsRepository.save(parentobjattachment);
		} else {
			objattachment.setFilename(filename);
		}
		objattachment.setFileextension(fileexe);
		objattachment.setCreateby(lsuserMasterRepository.findByusercode(usercode));
		objattachment.setCreatedate(currentdate);
		objattachment.setBatchcode(objorder.getBatchcode());
		objattachment.setIslargefile(islargefile);
		objattachment.setVersion(0);
		objattachment.setFilesize(commonService.formatFileSize(file.getSize()));

		LSuserMaster username = lsuserMasterRepository.findByusercode(usercode);
		String name = username.getUsername();
		LScfttransaction list = new LScfttransaction();
		list.setModuleName("Register Task Orders & Execute");
		list.setComments(
				name + " " + "Uploaded the attachement in Order ID: " + objorder.getBatchid() + " " + "successfully");
		list.setActions("Insert");
		list.setSystemcoments("System Generated");
		list.setTableName("profile");
//		list.setTransactiondate(currentdate);
		try {
			list.setTransactiondate(commonfunction.getCurrentUtcTime());
		} catch (ParseException e) {

			e.printStackTrace();
		}
		list.setLsuserMaster(usercode);
		lscfttransactionRepository.save(list);

		if (objorder != null) {
			if (objorder.getLsOrderattachments() == null) {
				objorder.setLsOrderattachments(new ArrayList<LsOrderattachments>());
			}
			objorder.getLsOrderattachments().add(objattachment);
			objorder.getLsOrderattachments().sort((a1, a2) -> {
				Long code1 = a1.getAttachmentcode();
				Long code2 = a2.getAttachmentcode();

				if (code1 == null && code2 == null) {
					return 0; // Both are null, considered equal
				} else if (code1 == null) {
					return -1; // Null values are placed first
				} else if (code2 == null) {
					return 1; // Null values are placed first
				} else {
					return code2.compareTo(code1); // Regular comparison, descending order
				}
			});
		}

		lsOrderattachmentsRepository.saveAll(objorder.getLsOrderattachments());

		if (islargefile == 1) {
			String filenameval = "attach_"
					+ objorder.getBatchcode() + "_" + objorder.getLsOrderattachments()
							.get(objorder.getLsOrderattachments().lastIndexOf(objattachment)).getAttachmentcode()
					+ "_" + filename;
			String id = cloudFileManipulationservice.storeLargeattachment(filenameval, file);
			if (id != null) {
				objattachment.setFileid(id);
			}

			lsOrderattachmentsRepository.saveAll(objorder.getLsOrderattachments());
		}

		username = null;

		return objorder;
	}

//	public LSlogilablimsorderdetail CloudELNFileUploadattachments(MultipartFile file, Long batchcode, String filename,
//			String fileexe, Integer usercode, Date currentdate, Integer islargefile, Integer methodkey)
//			throws IOException {
//
//		LSlogilablimsorderdetail objorder = lslogilablimsorderdetailRepository.findOne(batchcode);
//
//		ELNFileAttachments objattachment = new ELNFileAttachments();
//
//		if (islargefile == 0) {
//			CloudOrderAttachment objfile = cloudFileManipulationservice.storeattachment(file);
//			if (objfile != null) {
//				objattachment.setFileid(objfile.getFileid());
//			}
//		}
//
//		objattachment.setFilename(filename);
//		objattachment.setFileextension(fileexe);
//		objattachment.setCreateby(lsuserMasterRepository.findByusercode(usercode));
//		objattachment.setCreatedate(currentdate);
//		objattachment.setBatchcode(objorder.getBatchcode());
//		objattachment.setIslargefile(islargefile);
//		objattachment.setMethodkey(methodkey);
//
//		LSuserMaster username = lsuserMasterRepository.findByusercode(usercode);
//		String name = username.getUsername();
//		LScfttransaction list = new LScfttransaction();
//		list.setModuleName("Register Task Orders & Execute");
//		list.setComments(
//				name + " " + "Uploaded the attachement in Order ID: " + objorder.getBatchid() + " " + "successfully");
//		list.setActions("Insert");
//		list.setSystemcoments("System Generated");
//		list.setTableName("profile");
//		list.setTransactiondate(currentdate);
//		list.setLsuserMaster(usercode);
//		lscfttransactionRepository.save(list);
//		if (objorder != null && objorder.getELNFileAttachments() != null) {
//			objorder.getELNFileAttachments().add(objattachment);
//		} else {
//			objorder.setELNFileAttachments(new ArrayList<ELNFileAttachments>());
//			objorder.getELNFileAttachments().add(objattachment);
//		}
//
//		elnFileattachmentsRepository.save(objorder.getELNFileAttachments());
//
//		if (islargefile == 1) {
//			@SuppressWarnings("unlikely-arg-type")
//			String filenameval = "attach_"
//					+ objorder.getBatchcode() + "_" + objorder.getLsOrderattachments()
//							.get(objorder.getLsOrderattachments().lastIndexOf(objattachment)).getAttachmentcode()
//					+ "_" + filename;
//			String id = cloudFileManipulationservice.storeLargeattachment(filenameval, file);
//			if (id != null) {
//				objattachment.setFileid(id);
//			}
//
//			elnFileattachmentsRepository.save(objorder.getELNFileAttachments());
//		}
//
//		return objorder;
//	}
//
//	public LSlogilablimsorderdetail Uploadelnfileattachments(MultipartFile file, Long batchcode, String filename,
//			String fileexe, Integer usercode, Date currentdate, Integer islargefile) throws IOException {
//
//		LSlogilablimsorderdetail objorder = lslogilablimsorderdetailRepository.findOne(batchcode);
//
//		ELNFileAttachments objattachment = new ELNFileAttachments();
//
//		if (islargefile == 0) {
//			OrderAttachment objfile = fileManipulationservice.storeattachment(file);
//			if (objfile != null) {
//				objattachment.setFileid(objfile.getId());
//			}
//		} else {
//			String id = fileManipulationservice.storeLargeattachment(filename, file);
//			if (id != null) {
//				objattachment.setFileid(id);
//			}
//		}
//
//		objattachment.setFilename(filename);
//		objattachment.setFileextension(fileexe);
//		objattachment.setCreateby(lsuserMasterRepository.findByusercode(usercode));
//		objattachment.setCreatedate(currentdate);
//		objattachment.setBatchcode(objorder.getBatchcode());
//		objattachment.setIslargefile(islargefile);
//
//		LSuserMaster username = lsuserMasterRepository.findByusercode(usercode);
//		String name = username.getUsername();
//		LScfttransaction list = new LScfttransaction();
//		list.setModuleName("Register Task Orders & Execute");
//		list.setComments(
//				name + " " + "Uploaded the attachement in Order ID: " + objorder.getBatchid() + " " + "successfully");
//		list.setActions("Insert");
//		list.setSystemcoments("System Generated");
//		list.setTableName("profile");
//		list.setTransactiondate(currentdate);
//		list.setLsuserMaster(usercode);
//		lscfttransactionRepository.save(list);
//		if (objorder != null && objorder.getELNFileAttachments() != null) {
//			objorder.getELNFileAttachments().add(objattachment);
//		} else {
//			objorder.setELNFileAttachments(new ArrayList<ELNFileAttachments>());
//			objorder.getELNFileAttachments().add(objattachment);
//		}
//
//		elnFileattachmentsRepository.save(objorder.getELNFileAttachments());
//
//		return objorder;
//	}

	public Map<String, Object> CloudELNFileUploadattachments(MultipartFile[] files, Long batchcode, String[] filenames,
			String[] fileexes, Integer usercode, Date currentdate, Integer islargefile, Integer methodkey)
			throws IOException, InvalidKeyException, NoSuchAlgorithmException, IllegalArgumentException {

		System.out.println(files);
		System.out.println(filenames);
		System.out.println(fileexes);

		Map<String, Object> map = new HashMap<>();

		List<Method> methobj = lsMethodRepository.findByMethodkey(methodkey);

		LSlogilablimsorderdetail objorder = lslogilablimsorderdetailRepository
				.findByBatchcodeOrderByBatchcodeDesc(batchcode);
		LSlogilabprotocoldetail objproto = LSlogilabprotocoldetailRepository.findByProtocolordercode(batchcode);

		List<CloudParserFile> parserfile = new ArrayList<CloudParserFile>();

		@SuppressWarnings("unused")
		List<ELNFileAttachments> objattachment = IntStream.range(0, files.length).mapToObj((int i) -> {
			MultipartFile file = files[i];
			String name = filenames[i];
			String ext = fileexes[i];

			if (islargefile == 0) {

				String id = null;

				try {
					// objfile = cloudFileManipulationservice.storeattachment(file);
					id = cloudFileManipulationservice.storecloudfilesreturnUUID(file, "parserfile");
				} catch (IOException | InvalidKeyException | NoSuchAlgorithmException | IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (id != null) {
					ELNFileAttachments attachment = new ELNFileAttachments();
					attachment.setFileid(id);
					attachment.setFilename(name);
					attachment.setFileextension(ext);
					attachment.setCreateby(lsuserMasterRepository.findByusercode(usercode));
					attachment.setCreatedate(currentdate);
					attachment.setIslargefile(islargefile);
					attachment.setMethodkey(methodkey);

					CloudParserFile objfile1 = new CloudParserFile();
					objfile1.setFileid(id);
					objfile1.setExtension(ext);
					objfile1.setFilename(file.getOriginalFilename());
					objfile1.setOriginalfilename(name);
					// objfile1.setInstrawdataurl(methobj.get(0).getInstrawdataurl());
					objfile1.setVersion(1);

					parserfile.add(objfile1);

					return attachment;
				}
			}
//			                 else {
//			                     // large file
//			                     String filenameval = "attach_"
//			                             + objorder.getBatchcode() + "_"
//			                             + objorder.getLsOrderattachments()
//			                                 .get(objorder.getLsOrderattachments().lastIndexOf(attachment))
//			                                 .getAttachmentcode()
//			                             + "_" + name;
//
//			                     String id = cloudFileManipulationservice.storeLargeattachment(filenameval, file);
//			                     if (id != null) {
//			                         attachment.setFileid(id);
//			                     }
//			                 }
			return null;
		}).filter(Objects::nonNull).toList();

		LSuserMaster username = lsuserMasterRepository.findByusercode(usercode);
		String name = username.getUsername();
		LScfttransaction list = new LScfttransaction();

		if (objorder != null && objorder.getELNFileAttachments() != null) {
			objorder.getELNFileAttachments().addAll(objattachment);
			elnFileattachmentsRepository.saveAll(objorder.getELNFileAttachments());
		} else if (objorder != null) {
			objorder.setELNFileAttachments(new ArrayList<ELNFileAttachments>());
			objorder.getELNFileAttachments().addAll(objattachment);
			elnFileattachmentsRepository.saveAll(objorder.getELNFileAttachments());
		}

		cloudparserfilerepository.saveAll(parserfile);
		if (objorder == null) {
			objattachment.forEach(att -> att.setBatchcode(objproto.getProtocolordercode()));
			list.setModuleName("Register Protocol Orders");
			list.setComments(name + " " + "Uploaded the attachement in Protocol ID: " + objproto.getProtoclordername()
					+ " " + "successfully");
			list.setActions("Insert");
			list.setSystemcoments("System Generated");
			list.setTableName("profile");
			try {
				list.setTransactiondate(commonfunction.getCurrentUtcTime());
			} catch (ParseException e) {

				e.printStackTrace();
			}
			list.setLsuserMaster(usercode);

		} else {
			objattachment.forEach(att -> att.setBatchcode(objorder.getBatchcode()));

			list.setModuleName("Register Task Orders & Execute");
			list.setComments(name + " " + "Uploaded the attachement in Order ID: " + objorder.getBatchid() + " "
					+ "successfully");
			list.setActions("Insert");
			list.setSystemcoments("System Generated");
			list.setTableName("profile");
			try {
				list.setTransactiondate(commonfunction.getCurrentUtcTime());
			} catch (ParseException e) {

				e.printStackTrace();
			}
			list.setLsuserMaster(usercode);

		}

		lscfttransactionRepository.save(list);

//		if (islargefile == 1) {
//			@SuppressWarnings("unlikely-arg-type")
//			String filenameval = "attach_"
//					+ objorder.getBatchcode() + "_" + objorder.getLsOrderattachments()
//							.get(objorder.getLsOrderattachments().lastIndexOf(objattachment)).getAttachmentcode()
//					+ "_" + filename;
//			String id = cloudFileManipulationservice.storeLargeattachment(filenameval, file);
//			if (id != null) {
//				objattachment.setFileid(id);
//			}
//
//			elnFileattachmentsRepository.saveAll(objorder.getELNFileAttachments());
//		}
		map.put("attachmentdetails", objattachment);

		return map;
	}

//	public Map<String, Object> CloudELNFileUploadattachments(MultipartFile[] file, Long batchcode, String[] filename,
//			String[] fileexe, Integer usercode, Date currentdate, Integer islargefile, Integer methodkey)
//			throws IOException, InvalidKeyException, NoSuchAlgorithmException, IllegalArgumentException {
//
//		Map<String, Object> map = new HashMap<>();
//
//		LSlogilablimsorderdetail objorder = lslogilablimsorderdetailRepository
//				.findByBatchcodeOrderByBatchcodeDesc(batchcode);
//		LSlogilabprotocoldetail objproto = LSlogilabprotocoldetailRepository.findByProtocolordercode(batchcode);
//
//		ELNFileAttachments objattachment = new ELNFileAttachments();
//
//		if (islargefile == 0) {
//			CloudOrderAttachment objfile = cloudFileManipulationservice.storeattachment(file);
//			if (objfile != null) {
//				objattachment.setFileid(objfile.getFileid());
//			}
//		}
//
//		objattachment.setFilename(filename);
//		objattachment.setFileextension(fileexe);
//		objattachment.setCreateby(lsuserMasterRepository.findByusercode(usercode));
//		objattachment.setCreatedate(currentdate);
//		objattachment.setIslargefile(islargefile);
//		objattachment.setMethodkey(methodkey);
//
//		LSuserMaster username = lsuserMasterRepository.findByusercode(usercode);
//		String name = username.getUsername();
//		LScfttransaction list = new LScfttransaction();
//
//		if (objorder != null && objorder.getELNFileAttachments() != null) {
//			objorder.getELNFileAttachments().add(objattachment);
//			elnFileattachmentsRepository.saveAll(objorder.getELNFileAttachments());
//		} else if (objorder != null) {
//			objorder.setELNFileAttachments(new ArrayList<ELNFileAttachments>());
//			objorder.getELNFileAttachments().add(objattachment);
//			elnFileattachmentsRepository.saveAll(objorder.getELNFileAttachments());
//		}
//
//		if (objorder == null) {
//			objattachment.setBatchcode(objproto.getProtocolordercode());
//			list.setModuleName("Register Protocol Orders");
//			list.setComments(name + " " + "Uploaded the attachement in Protocol ID: " + objproto.getProtoclordername()
//					+ " " + "successfully");
//			list.setActions("Insert");
//			list.setSystemcoments("System Generated");
//			list.setTableName("profile");
//			try {
//				list.setTransactiondate(commonfunction.getCurrentUtcTime());
//			} catch (ParseException e) {
//
//				e.printStackTrace();
//			}
//			list.setLsuserMaster(usercode);
//
//		} else {
//			objattachment.setBatchcode(objorder.getBatchcode());
//			list.setModuleName("Register Task Orders & Execute");
//			list.setComments(name + " " + "Uploaded the attachement in Order ID: " + objorder.getBatchid() + " "
//					+ "successfully");
//			list.setActions("Insert");
//			list.setSystemcoments("System Generated");
//			list.setTableName("profile");
//			try {
//				list.setTransactiondate(commonfunction.getCurrentUtcTime());
//			} catch (ParseException e) {
//
//				e.printStackTrace();
//			}
//			list.setLsuserMaster(usercode);
//
//		}
//
//		lscfttransactionRepository.save(list);
//
//		if (islargefile == 1) {
//			@SuppressWarnings("unlikely-arg-type")
//			String filenameval = "attach_"
//					+ objorder.getBatchcode() + "_" + objorder.getLsOrderattachments()
//							.get(objorder.getLsOrderattachments().lastIndexOf(objattachment)).getAttachmentcode()
//					+ "_" + filename;
//			String id = cloudFileManipulationservice.storeLargeattachment(filenameval, file);
//			if (id != null) {
//				objattachment.setFileid(id);
//			}
//
//			elnFileattachmentsRepository.saveAll(objorder.getELNFileAttachments());
//		}
//		map.put("attachmentdetails", objattachment);
//
//		return map;
//	}

	public Map<String, Object> Uploadelnfileattachments(MultipartFile[] files, Long batchcode, String[] filenames,
			String[] fileexes, Integer usercode, Date currentdate, Integer islargefile, Integer methodkey)
			throws IOException {

		Map<String, Object> map = new HashMap<>();

		LSlogilablimsorderdetail objorder = lslogilablimsorderdetailRepository
				.findByBatchcodeOrderByBatchcodeDesc(batchcode);
		LSlogilabprotocoldetail objproto = LSlogilabprotocoldetailRepository.findByProtocolordercode(batchcode);

		List<CloudParserFile> parserfile = new ArrayList<CloudParserFile>();

		List<ELNFileAttachments> objattachment = new ArrayList<>();

		objattachment = IntStream.range(0, files.length).mapToObj((int i) -> {
			MultipartFile file = files[i];
			String name = filenames[i];
			String ext = fileexes[i];

			if (islargefile == 0) {

				String id = null;

				try {
					id = fileStorageService.storeSQLFile(file, null, 0, file.getOriginalFilename());
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
				if (id != null) {
					ELNFileAttachments attachment = new ELNFileAttachments();
					attachment.setFileid(id);
					attachment.setFilename(name);
					attachment.setFileextension(ext);
					attachment.setCreateby(lsuserMasterRepository.findByusercode(usercode));
					attachment.setCreatedate(currentdate);
					attachment.setIslargefile(islargefile);
					attachment.setMethodkey(methodkey);

					CloudParserFile objfile1 = new CloudParserFile();
					objfile1.setFileid(id);
					objfile1.setExtension(ext);
					objfile1.setFilename(file.getOriginalFilename());
					objfile1.setOriginalfilename(name);
					// objfile1.setInstrawdataurl(methobj.get(0).getInstrawdataurl());
					objfile1.setVersion(1);

					parserfile.add(objfile1);

					return attachment;
				}
			}

			return null;
		}).filter(Objects::nonNull).toList();

		LSuserMaster username = lsuserMasterRepository.findByusercode(usercode);
		String name = username.getUsername();
		LScfttransaction list = new LScfttransaction();

		if (objorder != null && objorder.getELNFileAttachments() != null) {
			objorder.getELNFileAttachments().addAll(objattachment);
			elnFileattachmentsRepository.saveAll(objorder.getELNFileAttachments());
		} else if (objorder != null) {
			objorder.setELNFileAttachments(new ArrayList<ELNFileAttachments>());
			objorder.getELNFileAttachments().addAll(objattachment);
			elnFileattachmentsRepository.saveAll(objorder.getELNFileAttachments());
		}

		cloudparserfilerepository.saveAll(parserfile);

		if (objorder == null) {
			objattachment.forEach(att -> att.setBatchcode(objproto.getProtocolordercode()));
			list.setModuleName("Register Protocol Orders");
			list.setComments(name + " " + "Uploaded the attachement in Protocol ID: " + objproto.getProtoclordername()
					+ " " + "successfully");
			list.setActions("Insert");
			list.setSystemcoments("System Generated");
			list.setTableName("profile");
			try {
				list.setTransactiondate(commonfunction.getCurrentUtcTime());
			} catch (ParseException e) {

				e.printStackTrace();
			}
			list.setLsuserMaster(usercode);

		} else {

			for (int i = 0; i < objattachment.size(); i++) {
				ELNFileAttachments attachmentobj = objattachment.get(i);

				attachmentobj.setFileextension(fileexes[i]);
				attachmentobj.setFilename(filenames[i]);
				attachmentobj.setCreateby(lsuserMasterRepository.findByusercode(usercode));
				attachmentobj.setCreatedate(currentdate);
				attachmentobj.setBatchcode(objorder.getBatchcode());
				attachmentobj.setIslargefile(islargefile);
			}

			list.setModuleName("Register Task Orders & Execute");
			list.setComments(name + " " + "Uploaded the attachement in Order ID: " + objorder.getBatchid() + " "
					+ "successfully");
			list.setActions("Insert");
			list.setSystemcoments("System Generated");
			list.setTableName("profile");

			try {
				list.setTransactiondate(commonfunction.getCurrentUtcTime());
			} catch (ParseException e) {

				e.printStackTrace();
			}
			list.setLsuserMaster(usercode);
		}
		lscfttransactionRepository.save(list);
		if (objorder != null && objorder.getELNFileAttachments() != null) {
			objorder.getELNFileAttachments().addAll(objattachment);
		} else {
			objorder.setELNFileAttachments(new ArrayList<ELNFileAttachments>());
			objorder.getELNFileAttachments().addAll(objattachment);
		}

		map.put("attachmentdetails", objattachment);

		return map;

	}

	public LsOrderattachments downloadattachments(LsOrderattachments objattachments) {
		OrderAttachment objfile = fileManipulationservice.retrieveFile(objattachments);
		if (objfile != null) {
			objattachments.setFile(objfile.getFile());

		}
//		silent audit
		if (objattachments.getObjsilentaudit() != null) {
			objattachments.getObjsilentaudit().setTableName("LsOrderattachments");
			try {
				objattachments.getObjsilentaudit().setTransactiondate(commonfunction.getCurrentUtcTime());
			} catch (ParseException e) {

				e.printStackTrace();
			}
			lscfttransactionRepository.save(objattachments.getObjsilentaudit());
		}
		return objattachments;
	}

	public ELNFileAttachments downloadparserattachments(ELNFileAttachments objattachments) {
		OrderAttachment objfile = fileManipulationservice.retrieveFile(objattachments);
		if (objfile != null) {
			objattachments.setFile(objfile.getFile());

		}
//		silent audit
		if (objattachments.getObjsilentaudit() != null) {
			objattachments.getObjsilentaudit().setTableName("ELNFileAttachments");
			try {
				objattachments.getObjsilentaudit().setTransactiondate(commonfunction.getCurrentUtcTime());
			} catch (ParseException e) {

				e.printStackTrace();
			}
			lscfttransactionRepository.save(objattachments.getObjsilentaudit());
		}
		return objattachments;
	}

	public LsOrderattachments Clouddownloadattachments(LsOrderattachments objattachments) {
		CloudOrderAttachment objfile = cloudFileManipulationservice.retrieveFile(objattachments);
		if (objfile != null) {
			objattachments.setFile(objfile.getFile());

		}
//		silent audit
		if (objattachments.getObjsilentaudit() != null) {
			objattachments.getObjsilentaudit().setTableName("LsOrderattachments");
			try {
				objattachments.getObjsilentaudit().setTransactiondate(commonfunction.getCurrentUtcTime());
			} catch (ParseException e) {

				e.printStackTrace();
			}
			lscfttransactionRepository.save(objattachments.getObjsilentaudit());
		}
		return objattachments;
	}

	public LsOrderattachments Cloudparserdownloadattachments(LsOrderattachments objattachments) {
		CloudOrderAttachment objfile = cloudFileManipulationservice.retrieveFile(objattachments);
		if (objfile != null) {
			objattachments.setFile(objfile.getFile());

		}

		if (objattachments.getObjsilentaudit() != null) {
			objattachments.getObjsilentaudit().setTableName("LsOrderattachments");
			try {
				objattachments.getObjsilentaudit().setTransactiondate(commonfunction.getCurrentUtcTime());
			} catch (ParseException e) {

				e.printStackTrace();
			}
			lscfttransactionRepository.save(objattachments.getObjsilentaudit());
		}
		return objattachments;
	}

	public GridFsResource retrieveLargeFile(String fileid) throws IllegalStateException, IOException {
		return fileManipulationservice.retrieveLargeFile(fileid);
	}

	public InputStream retrieveColudLargeFile(String fileid) throws IOException {
		return cloudFileManipulationservice.retrieveLargeFile(fileid);
	}

	public InputStream retrieveColudParserFile(String fileid, String tenant) throws IOException {
		return cloudFileManipulationservice.retrieveCloudFile(fileid, tenant + "parserfile");
		// return cloudFileManipulationservice.retrieveLargeFile(fileid);
	}

	public LsOrderattachments deleteattachments(LsOrderattachments objattachments) {
		Response objresponse = new Response();
		Long deletecount = (long) -1;

		if (objattachments.getIslargefile() == 0) {
			fileManipulationservice.deleteattachments(objattachments.getFileid());
		} else {
			fileManipulationservice.deletelargeattachments(objattachments.getFileid());
			deletecount = (long) 1;
		}

		deletecount = lsOrderattachmentsRepository.deleteByAttachmentcode(objattachments.getAttachmentcode());

		if (deletecount > -1) {
			objresponse.setStatus(true);
		} else {
			objresponse.setStatus(false);
		}

		objattachments.setResponse(objresponse);
//		silent audit
		if (objattachments.getObjsilentaudit() != null) {
			objattachments.getObjsilentaudit().setTableName("LsOrderattachments");
			try {
				objattachments.getObjsilentaudit().setTransactiondate(commonfunction.getCurrentUtcTime());
			} catch (ParseException e) {

				e.printStackTrace();
			}
			lscfttransactionRepository.save(objattachments.getObjsilentaudit());
		}
		return objattachments;
	}

	public LsOrderattachments Clouddeleteattachments(LsOrderattachments objattachments) {
		Response objresponse = new Response();
		Long deletecount = (long) -1;

		if (objattachments.getIslargefile() == 0) {
			deletecount = cloudFileManipulationservice.deleteattachments(objattachments.getFileid());
		} else {
			cloudFileManipulationservice.deletelargeattachments(objattachments.getFileid());
			deletecount = (long) 1;
		}

		deletecount = lsOrderattachmentsRepository.deleteByAttachmentcode(objattachments.getAttachmentcode());

		if (deletecount > -1) {
			objresponse.setStatus(true);
		} else {
			objresponse.setStatus(false);
		}

		objattachments.setResponse(objresponse);

		if (objattachments.getObjsilentaudit() != null) {
			objattachments.getObjsilentaudit().setTableName("LsOrderattachments");
			try {
				objattachments.getObjsilentaudit().setTransactiondate(commonfunction.getCurrentUtcTime());
			} catch (ParseException e) {

				e.printStackTrace();
			}
			lscfttransactionRepository.save(objattachments.getObjsilentaudit());
		}
		return objattachments;
	}

	public Lsordershareto Insertshareorder(Lsordershareto objordershareto) {

//		Lsordershareto.LsordersharetoProjection existingshare = lsordersharetoRepository
//				.findBySharebyunifiedidAndSharetounifiedidAndOrdertypeAndSharebatchcode(
//						objordershareto.getSharebyunifiedid(), objordershareto.getSharetounifiedid(),
//						objordershareto.getOrdertype(), objordershareto.getSharebatchcode());
//		if (existingshare != null) {
//			objordershareto.setSharetocode(existingshare.getSharetocode());
//		}
		/** changes in sheet order share alert message **/
		List<Lsordershareto.LsordersharetoProjection> existingshare = lsordersharetoRepository
				.findBySharebyunifiedidAndSharetounifiedidAndOrdertypeAndSharebatchcode(
						objordershareto.getSharebyunifiedid(), objordershareto.getSharetounifiedid(),
						objordershareto.getOrdertype(), objordershareto.getSharebatchcode());

		if (!existingshare.isEmpty()) {

			Integer existingsharerights = existingshare.get(0).getSharerights();
			Integer newsharerights = objordershareto.getSharerights();
			Integer sharestatus = existingshare.get(0).getSharestatus();
			if (existingsharerights != null && newsharerights != null && existingsharerights.equals(newsharerights)
					&& sharestatus == 1) {
				Response objResponse = new Response();
				objResponse.setStatus(true);
				objResponse.setInformation("Orders.Sheetorders.IDS_ORDERALREADYSHARED");
				objordershareto.setResponse(objResponse);
				return objordershareto;
			} else {
				objordershareto.setSharetocode(existingshare.get(0).getSharetocode());
			}
		}

		objordershareto.setOrder(new LSlogilablimsorderdetail(objordershareto.getOrder().getBatchcode(),
				objordershareto.getOrder().getBatchid(), objordershareto.getOrder().getTestcode(),
				objordershareto.getOrder().getTestname(), objordershareto.getOrder().getCreatedtimestamp(),
				objordershareto.getOrder().getCompletedtimestamp(), objordershareto.getOrder().getFiletype(),
				objordershareto.getOrder().getLsworkflow(), objordershareto.getOrder().getProjectname(),
				objordershareto.getOrder().getSamplename(), objordershareto.getOrder().getFilename()));

		lsordersharetoRepository.save(objordershareto);

		String Details = "";
		String Notification = "";
		Notification = "SHAREORDER";

		Details = "{\"ordercode\":\"" + objordershareto.getOrder().getBatchcode() + "\", \"order\":\""
				+ objordershareto.getSharebatchid() + "\", \"sharedby\":\"" + objordershareto.getSharebyusername()
				+ "\", \"sharerights\":\"" + objordershareto.getSharerights() + "\"}";

		LSnotification objnotify = new LSnotification();
		// objnotify.setNotifationfrom(objordershareto.getOrder().getObjLoggeduser());
		objnotify.setNotifationfrom(objordershareto.getUsersharedby());
		objnotify.setNotifationto(objordershareto.getUsersharedon());
		objnotify.setNotificationdate(objordershareto.getSharedon());
		objnotify.setNotification(Notification);
		objnotify.setNotificationdetils(Details);
		objnotify.setIsnewnotification(1);
		objnotify.setNotificationpath("/registertask");
		objnotify.setNotificationfor(1);
		if (objordershareto.getSitecode() != null) {
			objnotify.setSitecode(objordershareto.getSitecode());
		}
		lsnotificationRepository.save(objnotify);

		return objordershareto;

	}

	public Map<String, Object> Insertshareorderby(Lsordersharedby objordershareby) {
		Map<String, Object> map = new HashMap<>();
		Lsordersharedby.LsordersharebyProjection existingshare = lsordersharedbyRepository
				.findBySharebyunifiedidAndSharetounifiedidAndOrdertypeAndSharebatchcode(
						objordershareby.getSharebyunifiedid(), objordershareby.getSharetounifiedid(),
						objordershareby.getOrdertype(), objordershareby.getSharebatchcode());
		if (existingshare != null) {
			objordershareby.setSharedbycode(existingshare.getSharedbycode());
		}

		objordershareby.setOrder(new LSlogilablimsorderdetail(objordershareby.getOrder().getBatchcode(),
				objordershareby.getOrder().getBatchid(), objordershareby.getOrder().getTestcode(),
				objordershareby.getOrder().getTestname(), objordershareby.getOrder().getCreatedtimestamp(),
				objordershareby.getOrder().getCompletedtimestamp(), objordershareby.getOrder().getFiletype(),
				objordershareby.getOrder().getLsworkflow(), objordershareby.getOrder().getProjectname(),
				objordershareby.getOrder().getSamplename(), objordershareby.getOrder().getFilename()));

		lsordersharedbyRepository.save(objordershareby);

		long sharedbycount = 0;

		sharedbycount = lsordersharedbyRepository
				.countBySharebyunifiedidAndOrdertypeAndSharestatusOrderBySharedbycodeDesc(
						objordershareby.getSharebyunifiedid(), objordershareby.getOrdertype(), 1);

		map.put("order", objordershareby);
		map.put("sharedbycount", sharedbycount);

		return map;
	}

	public List<Lsordersharedby> Getordersharedbyme(Lsordersharedby lsordersharedby) {
		return lsordersharedbyRepository.findBySharebyunifiedidAndOrdertypeAndSharestatusOrderBySharedbycodeDesc(
				lsordersharedby.getSharebyunifiedid(), lsordersharedby.getOrdertype(), 1);
	}

	public List<Lsordershareto> Getordersharetome(Lsordershareto lsordershareto) {
		return lsordersharetoRepository.findBySharetounifiedidAndOrdertypeAndSharestatusOrderBySharetocodeDesc(
				lsordershareto.getSharetounifiedid(), lsordershareto.getOrdertype(), 1);
	}

	public Lsordersharedby Unshareorderby(Lsordersharedby objordershareby) {
		Lsordersharedby existingshare = lsordersharedbyRepository.findBySharedbycode(objordershareby.getSharedbycode());

		List<LSlogilablimsorderdetail> oldorder = lslogilablimsorderdetailRepository
				.findBybatchcode(objordershareby.getSharebatchcode());

		existingshare.setSharestatus(0);
		existingshare.setUnsharedon(objordershareby.getUnsharedon());

		existingshare.setOrder(new LSlogilablimsorderdetail(oldorder.get(0).getBatchcode(),
				oldorder.get(0).getBatchid(), oldorder.get(0).getTestcode(), oldorder.get(0).getTestname(),
				oldorder.get(0).getCreatedtimestamp(), oldorder.get(0).getCompletedtimestamp(),
				oldorder.get(0).getFiletype(), oldorder.get(0).getLsworkflow(), oldorder.get(0).getProjectname(),
				oldorder.get(0).getSamplename(), oldorder.get(0).getFilename()));

		lsordersharedbyRepository.save(existingshare);

		return existingshare;
	}

	public Lsordershareto Unshareorderto(Lsordershareto lsordershareto) {
		Lsordershareto existingshare = lsordersharetoRepository.findBySharetocode(lsordershareto.getSharetocode());
		List<LSlogilablimsorderdetail> oldorder = lslogilablimsorderdetailRepository
				.findBybatchcode(lsordershareto.getSharebatchcode());

		existingshare.setSharestatus(0);
		existingshare.setUnsharedon(lsordershareto.getUnsharedon());
		existingshare.setSharedbycode(lsordershareto.getSharedbycode());

		existingshare.setOrder(new LSlogilablimsorderdetail(oldorder.get(0).getBatchcode(),
				oldorder.get(0).getBatchid(), oldorder.get(0).getTestcode(), oldorder.get(0).getTestname(),
				oldorder.get(0).getCreatedtimestamp(), oldorder.get(0).getCompletedtimestamp(),
				oldorder.get(0).getFiletype(), oldorder.get(0).getLsworkflow(), oldorder.get(0).getProjectname(),
				oldorder.get(0).getSamplename(), oldorder.get(0).getFilename()));
		lsordersharetoRepository.save(existingshare);

		return existingshare;
	}

	public Lsordersharedby GetsharedorderStatus(Lsordersharedby objorder) throws IOException, ParseException {

		LSlogilablimsorderdetail objorgorder = new LSlogilablimsorderdetail();

		objorgorder.setBatchcode(objorder.getSharebatchcode());
		objorgorder.setObjLoggeduser(objorder.getObjLoggeduser());
		objorgorder.setObjsilentaudit(objorder.getObjsilentaudit());
		objorgorder.setObjmanualaudit(objorder.getObjmanualaudit());
		objorgorder.setIsmultitenant(objorder.getIsmultitenant());

		objorder = lsordersharedbyRepository.findBySharedbycode(objorder.getSharedbycode());

//		objorder.setObjorder(objorgorder);

		return objorder;
	}

	public Lsordershareto GetsharedtomeorderStatus(Lsordershareto objorder) throws IOException, ParseException {

		LSlogilablimsorderdetail objorgorder = new LSlogilablimsorderdetail();

		objorgorder.setBatchcode(objorder.getSharebatchcode());
		objorgorder.setObjLoggeduser(objorder.getObjLoggeduser());
		objorgorder.setObjsilentaudit(objorder.getObjsilentaudit());
		objorgorder.setObjmanualaudit(objorder.getObjmanualaudit());
		objorgorder.setIsmultitenant(objorder.getIsmultitenant());

		return objorder;
	}

	public LSsamplefile GetResultsharedfileverContent(LSsamplefile objfile) throws IOException {
		return GetResultfileverContent(objfile);
	}

	public LSsamplefile SaveSharedResultfile(MultipartHttpServletRequest objfile) throws IOException {
		return SaveResultfile(objfile);
	}

	public LSlogilablimsorderdetail SharedCloudUploadattachments(MultipartFile file, Long batchcode, String filename,
			String fileexe, Integer usercode, Date currentdate, Integer islargefile)
			throws IOException, InvalidKeyException, NoSuchAlgorithmException, IllegalArgumentException {
		return CloudUploadattachments(file, batchcode, filename, fileexe, usercode, currentdate, islargefile);
	}

	public LSlogilablimsorderdetail SharedUploadattachments(MultipartFile file, Long batchcode, String filename,
			String fileexe, Integer usercode, Date currentdate, Integer islargefile) throws IOException {
		return Uploadattachments(file, batchcode, filename, fileexe, usercode, currentdate, islargefile);
	}

	public LsOrderattachments SharedClouddeleteattachments(LsOrderattachments objattachments) {
		return Clouddeleteattachments(objattachments);
	}

	public LsOrderattachments shareddeleteattachments(LsOrderattachments objattachments) {
		return deleteattachments(objattachments);
	}

	public LsOrderattachments SharedClouddownloadattachments(LsOrderattachments objattachments) {
		return Clouddownloadattachments(objattachments);
	}

	public LsOrderattachments Shareddownloadattachments(LsOrderattachments objattachments) {
		return downloadattachments(objattachments);
	}

	public InputStream sharedretrieveColudLargeFile(String fileid) throws IOException {
		return cloudFileManipulationservice.retrieveLargeFile(fileid);
	}

	public GridFsResource sharedretrieveLargeFile(String fileid) throws IllegalStateException, IOException {
		return fileManipulationservice.retrieveLargeFile(fileid);
	}

	public ResponseEntity<InputStreamResource> downloadattachmentsNonCloud(String param, String fileid)
			throws IOException {
		if (param == null) {
			return null;
		}
		LsOrderattachments objattach = lsOrderattachmentsRepository.findFirst1ByfileidOrderByAttachmentcodeDesc(fileid);

		HttpHeaders header = new HttpHeaders();
		header.set("Content-Disposition", "attachment; filename=" + objattach.getFilename());

		if (Integer.parseInt(param) == 0) {
			CloudOrderAttachment objfile = CloudOrderAttachmentRepository.findByFileid(fileid);
//			OrderAttachment objfile = fileManipulationservice.retrieveFile(objattach);

			InputStreamResource resource = null;
			byte[] content = objfile.getFile().getData();
			int size = content.length;
			InputStream is = null;
			try {
				is = new ByteArrayInputStream(content);
				resource = new InputStreamResource(is);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (is != null)
						is.close();
				} catch (Exception ex) {

				}
			}

			header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			header.setContentLength(size);
			return new ResponseEntity<>(resource, header, HttpStatus.OK);
		} else if (Integer.parseInt(param) == 1) {
			InputStream fileDtream = cloudFileManipulationservice.retrieveLargeFile(fileid);

			InputStreamResource resource = null;
			byte[] content = IOUtils.toByteArray(fileDtream);
			int size = content.length;
			InputStream is = null;
			try {
				is = new ByteArrayInputStream(content);
				resource = new InputStreamResource(is);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (is != null)
						is.close();
				} catch (Exception ex) {

				}
			}

			header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			header.setContentLength(size);
			return new ResponseEntity<>(resource, header, HttpStatus.OK);

		}

		return null;

	}

	public ResponseEntity<InputStreamResource> downloadparserattachmentsNonCloud(String param, String fileid)
			throws IOException {

		if (param == null) {
			return null;
		}

		ELNFileAttachments objattach = elnFileattachmentsRepository.findFirst1ByfileidOrderByAttachmentcodeDesc(fileid);
		HttpHeaders header = new HttpHeaders();
		header.set("Content-Disposition", "attachment; filename=" + objattach.getFilename());

		if (Integer.parseInt(param) == 0) {
			CloudOrderAttachment objfile = CloudOrderAttachmentRepository.findByFileid(fileid);
//			OrderAttachment objfile = fileManipulationservice.retrieveFile(objattach);

			InputStreamResource resource = null;
			byte[] content = objfile.getFile().getData();
			int size = content.length;
			InputStream is = null;
			try {
				is = new ByteArrayInputStream(content);
				resource = new InputStreamResource(is);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (is != null)
						is.close();
				} catch (Exception ex) {

				}
			}

			header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			header.setContentLength(size);
			return new ResponseEntity<>(resource, header, HttpStatus.OK);
		} else if (Integer.parseInt(param) == 1) {
			InputStream fileDtream = cloudFileManipulationservice.retrieveLargeFile(fileid);

			InputStreamResource resource = null;
			byte[] content = IOUtils.toByteArray(fileDtream);
			int size = content.length;
			InputStream is = null;
			try {
				is = new ByteArrayInputStream(content);
				resource = new InputStreamResource(is);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (is != null)
						is.close();
				} catch (Exception ex) {

				}
			}

			header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			header.setContentLength(size);
			return new ResponseEntity<>(resource, header, HttpStatus.OK);

		}

		return null;

	}

	public ResponseEntity<InputStreamResource> downloadattachments(String param, String fileid)
			throws IllegalStateException, IOException {

		if (param == null) {
			return null;
		}

		LsOrderattachments objattach = lsOrderattachmentsRepository.findFirst1ByfileidOrderByAttachmentcodeDesc(fileid);
		HttpHeaders header = new HttpHeaders();
		header.set("Content-Disposition", "attachment; filename=" + objattach.getFilename());

		if (Integer.parseInt(param) == 0) {
			OrderAttachment objfile = fileManipulationservice.retrieveFile(objattach);

			InputStreamResource resource = null;
			byte[] content = objfile.getFile().getData();
			int size = content.length;
			InputStream is = null;
			try {
				is = new ByteArrayInputStream(content);
				resource = new InputStreamResource(is);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (is != null)
						is.close();
				} catch (Exception ex) {

				}
			}

			header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			header.setContentLength(size);
			return new ResponseEntity<>(resource, header, HttpStatus.OK);
		} else if (Integer.parseInt(param) == 1) {
			GridFsResource gridFsFile = null;

			try {
				gridFsFile = retrieveLargeFile(fileid);
			} catch (IllegalStateException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
			System.out.println(gridFsFile.getContentType());
			header.setContentType(MediaType.parseMediaType(gridFsFile.getContentType()));
//			header.setContentLength(gridFsFile.getLength());
			try {
				return new ResponseEntity<>(new InputStreamResource(gridFsFile.getInputStream()), header,
						HttpStatus.OK);
			} catch (IllegalStateException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		return null;

	}

	public ResponseEntity<InputStreamResource> downloadelnparserattachments(String param, String fileid)
			throws IllegalStateException, IOException {

		if (param == null) {
			return null;
		}

		ELNFileAttachments objattach = elnFileattachmentsRepository.findFirst1ByfileidOrderByAttachmentcodeDesc(fileid);
		HttpHeaders header = new HttpHeaders();
		header.set("Content-Disposition", "attachment; filename=" + objattach.getFilename());

		if (Integer.parseInt(param) == 0) {
			OrderAttachment objfile = fileManipulationservice.retrieveFile(objattach);

			InputStreamResource resource = null;
			byte[] content = objfile.getFile().getData();
			int size = content.length;
			InputStream is = null;
			try {
				is = new ByteArrayInputStream(content);
				resource = new InputStreamResource(is);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (is != null)
						is.close();
				} catch (Exception ex) {

				}
			}

			header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			header.setContentLength(size);
			return new ResponseEntity<>(resource, header, HttpStatus.OK);
		} else if (Integer.parseInt(param) == 1) {
			GridFsResource gridFsFile = null;

			try {
				gridFsFile = retrieveLargeFile(fileid);
			} catch (IllegalStateException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
			System.out.println(gridFsFile.getContentType());
			header.setContentType(MediaType.parseMediaType(gridFsFile.getContentType()));
//			header.setContentLength(gridFsFile.getLength());
			return new ResponseEntity<>(new InputStreamResource(gridFsFile.getInputStream()), header, HttpStatus.OK);
		}
		return null;

	}

	public ResponseEntity<InputStreamResource> downloadparserattachments(String param, String fileid)
			throws IllegalStateException, IOException {

		if (param == null) {
			return null;
		}

		ELNFileAttachments objattach = elnFileattachmentsRepository.findFirst1ByfileidOrderByAttachmentcodeDesc(fileid);
		HttpHeaders header = new HttpHeaders();
		header.set("Content-Disposition", "attachment; filename=" + objattach.getFilename());

		if (Integer.parseInt(param) == 0) {
			OrderAttachment objfile = fileManipulationservice.retrieveFile(objattach);

			InputStreamResource resource = null;
			byte[] content = objfile.getFile().getData();
			int size = content.length;
			InputStream is = null;
			try {
				is = new ByteArrayInputStream(content);
				resource = new InputStreamResource(is);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (is != null)
						is.close();
				} catch (Exception ex) {

				}
			}

			header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			header.setContentLength(size);
			return new ResponseEntity<>(resource, header, HttpStatus.OK);
		} else if (Integer.parseInt(param) == 1) {
			GridFsResource gridFsFile = null;

			try {
				gridFsFile = retrieveLargeFile(fileid);
			} catch (IllegalStateException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
			System.out.println(gridFsFile.getContentType());
			header.setContentType(MediaType.parseMediaType(gridFsFile.getContentType()));
//			header.setContentLength(gridFsFile.getLength());
			return new ResponseEntity<>(new InputStreamResource(gridFsFile.getInputStream()), header, HttpStatus.OK);
		}
		return null;

	}

	public String getsampledata() {
		String jsondata = "{\r\n" + "    \"rowHeight\": 20,\r\n" + "    \"tags\": [],\r\n"
				+ "    \"columnWidth\": 64,\r\n" + "    \"fieldcount\": 0,\r\n" + "    \"activeSheet\": \"Sheet1\",\r\n"
				+ "    \"charts\": [],\r\n" + "    \"Batchcoordinates\": {\r\n" + "        \"resultdirection\": 1,\r\n"
				+ "        \"parameters\": []\r\n" + "    },\r\n" + "    \"names\": [],\r\n" + "    \"sheets\": [\r\n"
				+ "        {\r\n" + "            \"gridLinesColor\": null,\r\n"
				+ "            \"selection\": \"E5:E5\",\r\n" + "            \"name\": \"Sheet1\",\r\n"
				+ "            \"frozenColumns\": 0,\r\n" + "            \"showGridLines\": true,\r\n"
				+ "            \"defaultCellStyle\": {\r\n" + "                \"fontFamily\": \"Arial\",\r\n"
				+ "                \"fontSize\": \"12\"\r\n" + "            },\r\n"
				+ "            \"hyperlinks\": [],\r\n" + "            \"rows\": [\r\n" + "                {\r\n"
				+ "                    \"index\": 2,\r\n" + "                    \"cells\": [\r\n"
				+ "                        {\r\n" + "                            \"index\": 4,\r\n"
				+ "                            \"value\": \"nreee\"\r\n" + "                        }\r\n"
				+ "                    ]\r\n" + "                }\r\n" + "            ],\r\n"
				+ "            \"activeCell\": \"E5:E5\",\r\n" + "            \"drawings\": [],\r\n"
				+ "            \"mergedCells\": [],\r\n" + "            \"columns\": [],\r\n"
				+ "            \"frozenRows\": 0\r\n" + "        }\r\n" + "    ],\r\n" + "    \"images\": []\r\n" + "}";

		return jsondata;
	}

	public List<LsOrderSampleUpdate> GetOrderResourcesQuantitylst(LsOrderSampleUpdate objorder) {

		List<LsOrderSampleUpdate> sampleupdatelst = new ArrayList<LsOrderSampleUpdate>();
		if (objorder.getOrdersampleusedDetail() != null) {
			sampleupdatelst = lsOrderSampleUpdateRepository
					.findByOrdersampleusedDetail(objorder.getOrdersampleusedDetail());
		}
		return sampleupdatelst;
	}

	public Map<String, Object> SaveOrderResourcesQuantity(Map<String, Object> argobj) {

		Map<String, Object> objmap = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		LsOrderSampleUpdate ordersample = new LsOrderSampleUpdate();
		Lsrepositoriesdata lsrepositoriesdata = new Lsrepositoriesdata();
		if (argobj.containsKey("Ordersampleobj")) {
			ordersample = mapper.convertValue(argobj.get("Ordersampleobj"), LsOrderSampleUpdate.class);

			try {
				ordersample.setCreateddate(commonfunction.getCurrentUtcTime());
			} catch (ParseException e) {

				e.printStackTrace();
			}
			lsOrderSampleUpdateRepository.save(ordersample);
		}
		if (argobj.containsKey("LsrepositoriesdataSeletedObj")) {
			lsrepositoriesdata = mapper.convertValue(argobj.get("LsrepositoriesdataSeletedObj"),
					Lsrepositoriesdata.class);
			LsrepositoriesdataRepository.save(lsrepositoriesdata);
		}
		if (argobj.containsKey("LsrepositoriesObj")) {
			@SuppressWarnings("unused")
			Lsrepositories LsrepositoriesObj = mapper.convertValue(argobj.get("LsrepositoriesObj"),
					Lsrepositories.class);
		}

		objmap.put("ordersample", ordersample);
		objmap.put("lsrepositoriesdata", lsrepositoriesdata);

		return objmap;
	}

	public Map<String, Object> Consumableinventoryotification(Map<String, Object> argobj) {

		String Details = "";
		String Notification = "";
		ObjectMapper mapper = new ObjectMapper();
		LsOrderSampleUpdate ordersample = new LsOrderSampleUpdate();

		List<LSnotification> lstnotifications = new ArrayList<LSnotification>();
		Lsrepositoriesdata lsrepositoriesdata = new Lsrepositoriesdata();
		lsrepositoriesdata = mapper.convertValue(argobj.get("LsrepositoriesdataSeletedObj"), Lsrepositoriesdata.class);
		LsrepositoriesdataRepository.save(lsrepositoriesdata);
		LSuserMaster lsusermaster = new ObjectMapper().convertValue(argobj.get("usermaster"),
				new TypeReference<LSuserMaster>() {
				});

		ordersample.setLsusermaster(lsusermaster.getLsusermaster());

		LSnotification objnotify = new LSnotification();
		if (argobj.containsKey("Ordersampleobj")) {

			ordersample = mapper.convertValue(argobj.get("Ordersampleobj"), LsOrderSampleUpdate.class);
			if (ordersample.getInventoryused() == 20) {
				Notification = "INVENTORYCONSUMED";

				objnotify.setNotifationto(lsusermaster.getLsusermaster());

				Details = "{\"inventory\":\"" + lsrepositoriesdata.getInventoryid()
						+ lsrepositoriesdata.getRepositorydatacode() + "\", \"user\":\""
						+ lsrepositoriesdata.getAddedby() + "\"}";

				objnotify.setNotificationdate(ordersample.getCreateddate());
				objnotify.setNotification(Notification);
				objnotify.setNotificationdetils(Details);
				objnotify.setIsnewnotification(1);
				objnotify.setNotificationpath("/inventory");
				objnotify.setNotificationfor(1);
				if (lsusermaster.getLssitemaster().getSitecode() != null) {
					objnotify.setSitecode(lsusermaster.getLssitemaster().getSitecode());
				}
				lstnotifications.add(objnotify);
			}

			lsnotificationRepository.saveAll(lstnotifications);

		}
		return argobj;

	}

	public Map<String, Object> Outofstockinventoryotification(Map<String, Object> argobj) {

		String Details = "";
		String Notification = "";
		ObjectMapper mapper = new ObjectMapper();
		LsOrderSampleUpdate ordersample = new LsOrderSampleUpdate();
		ordersample = mapper.convertValue(argobj.get("Ordersampleobj"), LsOrderSampleUpdate.class);
		lsOrderSampleUpdateRepository.save(ordersample);

		List<UserProjection> ordersample1 = lsOrderSampleUpdateRepository
				.getDistinctRepositorydatacode(ordersample.getRepositorydatacode());

		ArrayList<LSnotification> lstnotifications = new ArrayList<LSnotification>();

		for (int i = 0; i < ordersample1.size(); i++) {
			LSnotification objnotify = new LSnotification();
			LSuserMaster objuser = lsuserMasterRepository.findByusercode(ordersample1.get(i).getUsercode());
			if (ordersample.getInventoryused() == 0) {
				Notification = "INVENTORYOUTOFSTOCK";

				Details = "{\"orderid\":\"" + ordersample1.get(i).getBatchname()

						+ "\"}";
				objnotify.setNotifationto(objuser);
				objnotify.setNotificationdate(ordersample.getCreateddate());
				objnotify.setNotification(Notification);
				objnotify.setNotificationdetils(Details);
				objnotify.setIsnewnotification(1);
				objnotify.setNotificationpath("/inventory");
				objnotify.setNotificationfor(1);
				lstnotifications.add(objnotify);
				if (argobj.get("usermaster") != null) {
					LSuserMaster lsusermaster = new ObjectMapper().convertValue(argobj.get("usermaster"),
							new TypeReference<LSuserMaster>() {
							});
					if (lsusermaster.getLssitemaster().getSitecode() != null) {
						objnotify.setSitecode(lsusermaster.getLssitemaster().getSitecode());
					}
				}
				objnotify = null;

			}
			i++;

		}

		lsnotificationRepository.saveAll(lstnotifications);
		return argobj;

	}

	public List<Lsrepositoriesdata> GetEditedOrderResources(Lsrepositoriesdata objorder) {

		return LsrepositoriesdataRepository.findByRepositorycodeAndSitecodeAndItemstatusOrderByRepositorydatacodeDesc(
				objorder.getRepositorycode(), objorder.getSitecode(), 1);
	}

	public String getsampledata(Long batchcode, Integer indexrow, Integer cellindex, Integer sheetval, String tagdata,
			String tagvalue, String samplevalue, String sampledetails, Integer lssamplefile, Integer multitenant)
			throws IOException {

		String Content = "";
		if (multitenant == 1) {
			CloudOrderCreation objCreation = cloudOrderCreationRepository.findTop1ById((long) lssamplefile);
			if (objCreation != null && objCreation.getContainerstored() == 0) {
				Content = objCreation.getContent();
			} else {
				Content = objCloudFileManipulationservice.retrieveCloudSheets(objCreation.getFileuid(),
						TenantContext.getCurrentTenant() + "ordercreation");
			}

		} else {
			OrderCreation objsavefile = mongoTemplate.findById(lssamplefile, OrderCreation.class);
			if (objsavefile != null) {
				Content = objsavefile.getContent();
			}
		}

		return Content;
	}

	public Map<String, List<Integer>> Getuserworkflow(LSusergroup lsusergroup) {
		Map<String, List<Integer>> Rtn_object = new HashMap<>();
		if (lsusergroup != null) {
			List<LSworkflowgroupmapping> lsworkflowgroupmapping = lsworkflowgroupmappingRepository
					.findBylsusergroup(lsusergroup);

			List<LSworkflow> lsworkflow = lsworkflowRepository
					.findByLsworkflowgroupmappingInAndStatus(lsworkflowgroupmapping, 1);

			List<Elnprotocolworkflowgroupmap> lstp_workflow = elnprotocolworkflowgroupmapRepository
					.findBylsusergroup(lsusergroup);
			Rtn_object.put("Protocol_Order",
					elnprotocolworkflowRepository.findByelnprotocolworkflowgroupmapInAndStatus(lstp_workflow, 1)
							.stream().map(Elnprotocolworkflow::getWorkflowcode).collect(Collectors.toList()));
			List<Integer> lstworkflow = new ArrayList<Integer>();
			if (lsworkflow != null && lsworkflow.size() > 0) {
				lstworkflow = lsworkflow.stream().map(LSworkflow::getWorkflowcode).collect(Collectors.toList());
			}
			Rtn_object.put("Sheet_Order", lstworkflow);

		} else {
//			List<Integer> lstworkflow = new ArrayList<Integer>();
			Rtn_object.put("Sheet_Order", Collections.emptyList());
			Rtn_object.put("Protocol_Order", Collections.emptyList());

		}
		return Rtn_object;
	}

	public Map<String, Object> Getuserprojects(LSuserMaster objuser) {
		if (objuser.getUsercode() != null) {
			Map<String, Object> objmap = new HashMap<>();
			List<LSuserteammapping> lstteammap = lsuserteammappingRepository
					.findByLsuserMasterAndTeamcodeNotNull(objuser);
			List<LSusersteam> lstteam = lsusersteamRepository.findByLsuserteammappingInAndLssitemaster(lstteammap,
					objuser.getLssitemaster());
			List<LSprojectmaster> lstprojectmaster = lsprojectmasterRepository.findByLsusersteamInAndStatus(lstteam, 1);

			List<Integer> lstproject = new ArrayList<Integer>();
			if (lstprojectmaster != null && lstprojectmaster.size() > 0) {
				lstproject = lstprojectmaster.stream().map(LSprojectmaster::getProjectcode)
						.collect(Collectors.toList());
			}

			List<Integer> lstteamcode = new ArrayList<Integer>();
			if (lstteam != null && lstteam.size() > 0) {
				lstteamcode = lstteam.stream().map(LSusersteam::getTeamcode).collect(Collectors.toList());
			}

			List<Integer> lstteamusercode = new ArrayList<Integer>();
			if (lstteammap != null && lstteammap.size() > 0 && !lstteamcode.isEmpty()) {
				List<LSuserMaster> lstusers = lsuserteammappingRepository.getLsuserMasterByTeamcode(lstteamcode);
				if (lstusers != null && lstusers.size() > 0) {
					lstteamusercode = lstusers.stream().map(LSuserMaster::getUsercode).collect(Collectors.toList());
				}
			}

			objmap.put("project", lstproject);
			objmap.put("team", lstteamcode);
			objmap.put("teamuser", lstteamusercode);
			return objmap;
		} else {
			Map<String, Object> objmap = new HashMap<>();
			return objmap;
		}
	}

	public Map<String, Object> Getinitialorders(LSlogilablimsorderdetail objorder) {
		Map<String, Object> mapOrders = new HashMap<String, Object>();
		List<Long> immutableNegativeValues = Arrays.asList(-3L, -22L);
		if (objorder.getLsuserMaster() != null && objorder.getLsuserMaster().getUsername() != null) {
			if (objorder.getLsuserMaster().getUsername().trim().toLowerCase().equals("administrator")) {
				mapOrders.put("orders", Getadministratororder(objorder));
				mapOrders.put("ordercount", lslogilablimsorderdetailRepository.count());
			} else {
				List<LSSheetOrderStructure> lstdir = new ArrayList<LSSheetOrderStructure>();
				if (objorder.getLstuserMaster().size() == 0) {
					lstdir = lsSheetOrderStructureRepository
							.findBySitemasterAndViewoptionAndDirectorycodeNotInOrCreatedbyAndViewoptionOrderByDirectorycode(
									objorder.getLsuserMaster().getLssitemaster(), 1, immutableNegativeValues,
									objorder.getLsuserMaster(), 2);
				} else {
					lstdir = lsSheetOrderStructureRepository
							.findBySitemasterAndViewoptionAndDirectorycodeNotInOrCreatedbyAndViewoptionOrSitemasterAndViewoptionAndCreatedbyInOrderByDirectorycode(
									objorder.getLsuserMaster().getLssitemaster(), 1, immutableNegativeValues,
									objorder.getLsuserMaster(), 2, objorder.getLsuserMaster().getLssitemaster(), 3,
									objorder.getLstuserMaster());
				}
				lstdir.addAll(lsSheetOrderStructureRepository.findByDirectorycodeIn(immutableNegativeValues));
				List<Long> directorycode = lstdir.stream().map(LSSheetOrderStructure::getDirectorycode)
						.collect(Collectors.toList());
				mapOrders.put("directorycode", directorycode);
				mapOrders.put("orders", Getuserorder(objorder, directorycode));
				mapOrders.put("ordercount", lslogilablimsorderdetailRepository
						.countByLsprojectmasterIn(objorder.getLsuserMaster().getLstproject()));
			}
		}
		return mapOrders;
	}

	public List<Logilabordermaster> Getremainingorders(LSlogilablimsorderdetail objorder) {
		if (objorder.getLsuserMaster().getUsername().trim().toLowerCase().equals("administrator")) {
			return Getadministratororder(objorder);
		} else {
			return Getuserorder(objorder, objorder.getLstdirectorycode());
		}
	}

	public List<Logilabordermaster> Getadministratororder(LSlogilablimsorderdetail objorder) {
		List<Logilabordermaster> lstorders = new ArrayList<Logilabordermaster>();
		if (objorder.getBatchcode() == 0) {
			lstorders = lslogilablimsorderdetailRepository.findFirst20ByOrderByBatchcodeDesc();
		} else {
			lstorders = lslogilablimsorderdetailRepository
					.findFirst20ByBatchcodeLessThanOrderByBatchcodeDesc(objorder.getBatchcode());
		}
		return lstorders;
	}

	public List<Logilabordermaster> Getuserorder(LSlogilablimsorderdetail objorder, List<Long> directorycode) {
		List<LSprojectmaster> lstproject = objorder.getLsuserMaster().getLstproject();
		List<Logilabordermaster> lstorders = new ArrayList<Logilabordermaster>();
		try {
			if (lstproject != null) {
				List<LSworkflow> lstworkflow = objorder.getLsuserMaster().getLstworkflow();
				if (objorder.getBatchcode() == 0) {
					if (directorycode.size() == 0) {
						lstorders = lslogilablimsorderdetailRepository
								.findFirst20ByLsprojectmasterInOrderByBatchcodeDesc(lstproject);
					} else {
						lstorders = lslogilablimsorderdetailRepository
								.findFirst20ByLsprojectmasterInOrDirectorycodeInOrderByBatchcodeDesc(lstproject,
										directorycode);
					}
				} else {
					if (directorycode.size() == 0) {
						lstorders = lslogilablimsorderdetailRepository
								.findFirst20ByBatchcodeLessThanAndLsprojectmasterInOrderByBatchcodeDesc(
										objorder.getBatchcode(), lstproject);
					} else {
						lstorders = lslogilablimsorderdetailRepository
								.findFirst20ByBatchcodeLessThanAndLsprojectmasterInOrBatchcodeLessThanAndDirectorycodeInOrderByBatchcodeDesc(
										objorder.getBatchcode(), lstproject, objorder.getBatchcode(), directorycode);

					}
				}
				if (lstworkflow.size() > 0) {
					lstorders.forEach(objord -> objord.setLstworkflow(lstworkflow));
				}

			}
			return lstorders;
		} catch (Exception e) {
			return lstorders;
		}

	}

	public Map<String, Object> uploadsheetimages(MultipartFile file, String originurl, String username, String sitecode)
			throws InvalidKeyException, NoSuchAlgorithmException, IllegalArgumentException {
		Map<String, Object> map = new HashMap<String, Object>();
		int multitenant = env.getProperty("app.datasource.eln.url").toLowerCase().contains("elntrail") == true ? 2 : -1;
		String id = null;
		try {
			id = cloudFileManipulationservice.storecloudfilesreturnUUID(file, "sheetimagestemp");

		} catch (IOException e) {

			e.printStackTrace();
		}

		final String getExtn = FilenameUtils.getExtension(file.getOriginalFilename()) == "" ? "png"
				: FilenameUtils.getExtension(file.getOriginalFilename());

		if (multitenant == 2) {
			map.put("link",
					originurl + "/Instrument/downloadsheetimagestemp/" + id + "/"
							+ commonfunction.getcontainername(multitenant, TenantContext.getCurrentTenant()) + "/"
							+ FilenameUtils.removeExtension(file.getOriginalFilename()) + "/" + getExtn);
		} else {
			map.put("link",
					originurl + "/Instrument/downloadsheetimagestemp/" + id + "/" + TenantContext.getCurrentTenant()
							+ "/" + FilenameUtils.removeExtension(file.getOriginalFilename()) + "/" + getExtn);
		}

		return map;
	}

	public Map<String, Object> uploadfilessheetfolder(MultipartFile file, String uid, Long directorycode,
			String filefor, String tenantid, Integer ismultitenant, Integer usercode, Integer sitecode,
			Date createddate, Integer fileviewfor) throws IOException, ParseException {
		Map<String, Object> map = new HashMap<String, Object>();
		LSsheetfolderfiles objfile = new LSsheetfolderfiles();
		objfile.setFilename(file.getOriginalFilename());
		objfile.setDirectorycode(directorycode);
		objfile.setFilefor(filefor);
		Response response = new Response();

//		Response response = validatefileexistonfolder(objfile);
//		if (response.getStatus()) {
		String uuID = "";
		if (ismultitenant == 1 || ismultitenant == 2) {
			uuID = cloudFileManipulationservice.storecloudfilesreturnwithpreUUID(file, "sheetfolderfiles", uid,
					ismultitenant);
		} else {
			uuID = fileManipulationservice.storeLargeattachmentwithpreuid(file.getOriginalFilename(), file, uid);
		}

		LSsheetfolderfiles parentobjattachment = lssheetfolderfilesRepository
				.findFirst1ByDirectorycodeAndFilenameOrderByFolderfilecode(directorycode, file.getOriginalFilename());

		LSsheetfolderfiles lsfiles = new LSsheetfolderfiles();
		lsfiles.setUuid(uuID);
		lsfiles.setFilesize(file.getSize());
		lsfiles.setDirectorycode(directorycode);

		if (parentobjattachment != null && file.getOriginalFilename() != null
				&& file.getOriginalFilename().lastIndexOf(".") > -1) {
			Integer versiondata = parentobjattachment.getVersion() != null ? parentobjattachment.getVersion() + 1 : 1;
			String originalname = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."));
			String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),
					file.getOriginalFilename().length());
			lsfiles.setFilename(originalname + "_V" + (versiondata) + extension);
			parentobjattachment.setVersion(versiondata);
			lssheetfolderfilesRepository.save(parentobjattachment);
		} else {
			lsfiles.setFilename(file.getOriginalFilename());
		}

		LSuserMaster lsuser = new LSuserMaster();
		lsuser.setUsercode(usercode);
		lsfiles.setCreateby(lsuser);
		LSSiteMaster lssite = new LSSiteMaster();
		lssite.setSitecode(sitecode);
		lsfiles.setLssitemaster(lssite);
		lsfiles.setFilefor(filefor);
		lsfiles.setCreatedtimestamp(createddate);
		lsfiles.setFileviewfor(fileviewfor);
		lsfiles.setVersion(0);

		lssheetfolderfilesRepository.save(lsfiles);

		if (directorycode != null) {
			List<LSSheetOrderStructure> structureobj = lsSheetOrderStructureRepository
					.findByDirectorycode(directorycode);
			if (!structureobj.isEmpty()) {
				structureobj.get(0).setDateModified(commonfunction.getCurrentUtcTime());
				structureobj.get(0).setModifiedby(lsuser);
			}
			lsSheetOrderStructureRepository.saveAll(structureobj);
		}

//		} else {
//			response.setInformation("IDS_INFO_FILEEXIST");
//		}
		response.setStatus(true);
		map.put("res", response);
		map.put("uid", uid);
		map.put("name", lsfiles.getFilename());
		return map;
	}

	public Map<String, Object> removefilessheetfolder(String uid, Long directorycode, String filefor, String tenantid,
			Integer ismultitenant, Integer usercode, Integer sitecode, Date createddate, Integer fileviewfor)
			throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("file", lssheetfolderfilesRepository.findByUuid(uid));
		lssheetfolderfilesRepository.deleteByUuid(uid);
		if (ismultitenant == 1 || ismultitenant == 2) {
			cloudFileManipulationservice.deletecloudFile(uid, "sheetfolderfiles");
		} else {
			fileManipulationservice.deletelargeattachments(uid);
		}
		map.put("uid", uid);
		return map;
	}

	public Map<String, Object> removemultifilessheetfolder(LSsheetfolderfiles[] files, Long directorycode,
			String filefor, String tenantid,

			Integer ismultitenant, Integer usercode, Integer sitecode, Date createddate, Integer fileviewfor)
			throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();

		List<LSsheetfolderfiles> lstfile = Arrays.asList(files);
		if (lstfile.size() > 0) {
			List<String> lstfilesid = lstfile.stream().map(LSsheetfolderfiles::getUuid).collect(Collectors.toList());

			lssheetfolderfilesRepository.deleteByUuidIn(lstfilesid);

			for (String uuididex : lstfilesid) {

				if (ismultitenant == 1 || ismultitenant == 2) {
					cloudFileManipulationservice.deletecloudFile(uuididex, "sheetfolderfiles");
				} else {
					fileManipulationservice.deletelargeattachments(uuididex);
				}

			}

		}

		map.put("lstfilesid", lstfile);
		return map;
	}

	public ByteArrayInputStream downloadsheetimages(String fileid, String tenant)
			throws FileNotFoundException, IOException {
		byte[] data = null;
		if (!"_".equals(tenant)) {
		TenantContext.setCurrentTenant(tenant);
		
		try {
			data = StreamUtils
					.copyToByteArray(cloudFileManipulationservice.retrieveCloudFile(fileid, tenant + "sheetimages"));
		} catch (IOException e) {

			e.printStackTrace();
			data = null;
		}

		if (data == null) {
			String[] arrOffiledid = fileid.split("_", 2);
			String Originalfieldid = arrOffiledid[0];
			try {
				data = StreamUtils.copyToByteArray(
						cloudFileManipulationservice.retrieveCloudFile(Originalfieldid, tenant + "sheetimages"));
			} catch (IOException e) {

				e.printStackTrace();
				data = null;
			}

			if (data == null) {
				try {
					data = StreamUtils.copyToByteArray(cloudFileManipulationservice.retrieveCloudFile(Originalfieldid,
							tenant + "sheetimagestemp"));
				} catch (IOException e) {

					e.printStackTrace();
					data = null;
				}
			}
		}
		
		}else {
			Fileimagestemp oldFile = FileimagestempRepository.findByFileid(fileid);
			Binary binary = oldFile.getFile();
			data = binary.getData();
		}
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		return bis;
	}

	public ByteArrayInputStream downloadsheetimagestemp(String fileid, String tenant)
			throws FileNotFoundException, IOException {
		TenantContext.setCurrentTenant(tenant);
		byte[] data = null;
		try {
			data = StreamUtils.copyToByteArray(
					cloudFileManipulationservice.retrieveCloudFile(fileid, tenant + "sheetimagestemp"));
		} catch (IOException e) {

			e.printStackTrace();
		}

		ByteArrayInputStream bis = new ByteArrayInputStream(data);

		return bis;
	}

	public Response removesheetimage(Map<String, String> body) {
		Response objresponse = new Response();
		String filid = body.get("fileid");
		cloudFileManipulationservice.deletecloudFile(filid, "sheetimages");
		objresponse.setStatus(true);
		return objresponse;
	}

	public boolean updatesheetimagesforversion(List<Map<String, String>> lstfiles) {
		for (Map<String, String> fileObj : lstfiles) {
			String copyfrom = fileObj.get("copyfrom");
			String copyto = fileObj.get("copyto");
			String isnew = fileObj.get("isnew");
//			int ismultitenant = Integer.parseInt(env.getProperty("ismultitenant"));
			int multitenant = env.getProperty("app.datasource.eln.url").toLowerCase().contains("elntrail") == true ? 2
					: -1;
			if (isnew.equals("true")) {
				cloudFileManipulationservice.movefiletoanothercontainerandremove(
						commonfunction.getcontainername(multitenant, TenantContext.getCurrentTenant())
								+ "sheetimagestemp",
						commonfunction.getcontainername(multitenant, TenantContext.getCurrentTenant()) + "sheetimages",
						copyfrom, copyto);
			}

			try {
				cloudFileManipulationservice.updateversionCloudFile(copyfrom,
						commonfunction.getcontainername(multitenant, TenantContext.getCurrentTenant()) + "sheetimages",
						copyto);
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		return true;
	}

	public boolean deletesheetimagesforversion(List<Map<String, String>> lstfiles) {
		for (Map<String, String> fileObj : lstfiles) {
			String fileid = fileObj.get("fieldid");

			cloudFileManipulationservice.deleteFile(fileid, TenantContext.getCurrentTenant() + "sheetimages");

			String[] arrOffiledid = fileid.split("_", 2);
			String Originalfieldid = arrOffiledid[0];

			cloudFileManipulationservice.deleteFile(Originalfieldid, TenantContext.getCurrentTenant() + "sheetimages");

			cloudFileManipulationservice.deleteFile(Originalfieldid,
					TenantContext.getCurrentTenant() + "sheetimagestemp");

		}
		return true;
	}

	public Map<String, Object> uploadsheetimagesSql(MultipartFile file, String originurl, String username,
			String sitecode) throws IOException {

		Map<String, Object> map = new HashMap<String, Object>();

		UUID objGUID = UUID.randomUUID();
		String randomUUIDString = objGUID.toString();

		LSfileimages fileObj = new LSfileimages();
		fileObj.setExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
		fileObj.setFileid(randomUUIDString);

		LSfileimagesRepository.save(fileObj);

		Fileimagestemp fileImg = new Fileimagestemp();

		fileImg.setId(fileObj.getFileimagecode());
		fileImg.setFileid(randomUUIDString);
		fileImg.setFile(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
		fileImg = FileimagestempRepository.insert(fileImg);

		final String getExtn = FilenameUtils.getExtension(file.getOriginalFilename()) == "" ? "png"
				: FilenameUtils.getExtension(file.getOriginalFilename());

		map.put("link", originurl + "/Instrument/downloadsheetimagestempsql/" + randomUUIDString + "/"
				+ FilenameUtils.removeExtension(file.getOriginalFilename()) + "/" + getExtn);

		return map;
	}

	public Fileimagestemp downloadsheetimagestempsql(String fileid) {
		return FileimagestempRepository.findByFileid(fileid);
	}

	public Fileimages downloadsheetimagessql(String fileid) {
		return FileimagesRepository.findByFileid(fileid);
	}

	public boolean updatesheetimagesforversionSql(List<Map<String, String>> lstfiles) throws IOException {
		for (Map<String, String> fileObj : lstfiles) {
			String fileid = fileObj.get("copyfrom");
			String newFileid = fileObj.get("copyto");
			String oldfileid = fileObj.get("oldfileid");
			String isnew = fileObj.get("isnew");

			Fileimagestemp oldFile = FileimagestempRepository.findByFileid(fileid);

			if (isnew != null && isnew.equals("true")) {

				Fileimages newFile = new Fileimages();

				newFile.setFile(oldFile.getFile());
				newFile.setFileid(fileid);
				newFile.setId(oldFile.getId());

				FileimagesRepository.save(newFile);
				FileimagestempRepository.deleteById(Integer.parseInt(oldFile.getFileid()));

			}
			Fileimages oldVerFile = FileimagesRepository.findByFileid(oldfileid);

			Integer id = oldFile == null ? oldVerFile.getId() : oldFile.getId();

			Fileimages newFile = new Fileimages();

			newFile.setFile(oldVerFile.getFile());
			newFile.setFileid(newFileid);
			newFile.setId(id);

			FileimagesRepository.save(newFile);

		}

		return true;
	}

	public boolean deletesheetimagesforversionSql(List<Map<String, String>> lstfiles) {
		for (Map<String, String> fileObj : lstfiles) {

			String fileid = fileObj.get("fieldid");

			FileimagesRepository.deleteById(fileid);

			String[] arrOffiledid = fileid.split("_", 2);
			String Originalfieldid = arrOffiledid[0];

			FileimagesRepository.deleteById(Originalfieldid);
			FileimagestempRepository.deleteById(Integer.parseInt(Originalfieldid));

		}
		return true;
	}

	public Map<String, Object> UploadLimsFile(MultipartFile file, Long batchcode, String filename) throws IOException {

		Map<String, Object> mapObj = new HashMap<String, Object>();

		LsSheetorderlimsrefrence objattachment = new LsSheetorderlimsrefrence();

		SheetorderlimsRefrence objfile = fileManipulationservice.storeLimsSheetRefrence(file);

		if (objfile != null) {
			objattachment.setFileid(objfile.getId());
		}

		objattachment.setFilename(filename);
		objattachment.setBatchcode(batchcode);
//		objattachment.setTestcode(testcode);

		lssheetorderlimsrefrenceRepository.save(objattachment);

		mapObj.put("elnSheet", objattachment);

		return mapObj;
	}

	public LsSheetorderlimsrefrence downloadSheetFromELN(LsSheetorderlimsrefrence objattachments) {

		SheetorderlimsRefrence objfile = fileManipulationservice.LimsretrieveELNsheet(objattachments);

		if (objfile != null) {
			objattachments.setFile(objfile.getFile());

		}

		return objattachments;
	}

	public Map<String, Object> GetLimsorderid(String orderid) {

		Map<String, Object> map = new HashMap<>();

		LSlogilablimsorderdetail objOrder = lslogilablimsorderdetailRepository.findByBatchid(orderid);

		if (objOrder != null) {
			map.put("ordercode", objOrder.getBatchcode());
		} else {
			map.put("ordercode", -1);
		}

		return map;
	}

	@SuppressWarnings("resource")
	public Map<String, Object> GetorderforlinkLIMS(LSlogilablimsorderdetail objorder)
			throws IllegalStateException, IOException {

		Map<String, Object> mapOrder = new HashMap<String, Object>();

		LSlogilablimsorderdetail objupdated = lslogilablimsorderdetailRepository.findByBatchid(objorder.getBatchid());

		if (objupdated.getLockeduser() != null) {
			objupdated.setIsLock(1);
		} else {
			objupdated.setLockeduser(objorder.getObjLoggeduser().getUsercode());
			objupdated.setLockedusername(objorder.getObjLoggeduser().getUsername());
			objupdated.setIsLock(1);
		}

		lslogilablimsorderdetailRepository.save(objupdated);

		if (objupdated.getLockeduser() != null && objorder.getObjLoggeduser() != null
				&& objupdated.getLockeduser().equals(objorder.getObjLoggeduser().getUsercode())) {
			objupdated.setIsLockbycurrentuser(1);
		} else {
			objupdated.setIsLockbycurrentuser(0);
		}

		if (objupdated.getFiletype() != 0 && objupdated.getOrderflag().toString().trim().equals("N")) {
			if (objupdated.getLsworkflow()
					.equals(lsworkflowRepository.findTopByAndLssitemasterAndStatusOrderByWorkflowcodeDesc(
							objorder.getObjLoggeduser().getLssitemaster(), 1))) {
				objupdated.setIsFinalStep(1);
			} else {
				objupdated.setIsFinalStep(0);
			}
		}

		if (objupdated.getFiletype() == 0) {
			objupdated.setLstestparameter(lStestparameterRepository.findByntestcode(objupdated.getTestcode()));
		}

		LSfile objFile = LSfileRepository.findByfilecode(objupdated.getFilecode());

		objFile.setIsmultitenant(objorder.getIsmultitenant());
		objupdated.setLsfile(objFile);

		if (objupdated.getLsfile() != null) {

			String contString = getfileoncode(objupdated.getLsfile());
			objupdated.getLsfile().setFilecontent(contString);

		}

		if (objupdated.getLssamplefile() != null) {

			if (objorder.getIsmultitenant() == 1) {
				CloudOrderCreation file = cloudOrderCreationRepository
						.findTop1ById((long) objupdated.getLssamplefile().getFilesamplecode());
				if (file != null) {
					objupdated.getLssamplefile().setFilecontent(file.getContent());
				}
			} else {

				String fileid = "order_" + objupdated.getLssamplefile().getFilesamplecode();
				GridFSFile largefile = gridFsTemplate.findOne(new Query(Criteria.where("filename").is(fileid)));
				if (largefile == null) {
					largefile = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(fileid)));
				}

				if (largefile != null) {
					GridFsResource resource = gridFsTemplate.getResource(largefile.getFilename());
					String filecontent = new BufferedReader(
							new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)).lines()
							.collect(Collectors.joining("\n"));
					objupdated.getLssamplefile().setFilecontent(filecontent);
				} else {

					OrderCreation file = mongoTemplate.findById(objupdated.getLssamplefile().getFilesamplecode(),
							OrderCreation.class);
					if (file != null) {
						objupdated.getLssamplefile().setFilecontent(file.getContent());
					}
				}
			}

		}

		mapOrder.put("order", objupdated);
		mapOrder.put("version",
				lssamplefileversionRepository.findByFilesamplecodeOrderByVersionnoDesc(objupdated.getLssamplefile()));

		return mapOrder;
	}

	public Map<String, Object> UploadLimsResultFile(MultipartFile file, Long batchcode, String filename)
			throws IOException {

		System.out.print("Inside UploadLimsFile");

		Map<String, Object> mapObj = new HashMap<String, Object>();

		LsResultlimsOrderrefrence objattachment = new LsResultlimsOrderrefrence();

		ResultorderlimsRefrence objfile = fileManipulationservice.storeResultLimsSheetRefrence(file);

		if (objfile != null) {
			objattachment.setFileid(objfile.getId());

			System.out.print("Inside UploadLimsFile filecode value " + batchcode.intValue());
		}

		objattachment.setFilename(filename);
		objattachment.setBatchid(filename);

		LsResultlimsOrderrefrenceRepository.save(objattachment);

		mapObj.put("elnSheet", objattachment);

		return mapObj;
	}

	public LSSheetOrderStructure Insertdirectory(LSSheetOrderStructure objdir) {
		Response objResponse = new Response();
		LSSheetOrderStructure lstdir = null;
		if (objdir.getDirectorycode() != null) {
			lstdir = lsSheetOrderStructureRepository
					.findByDirectorycodeAndParentdircodeAndDirectorynameNotAndSitemaster(objdir.getDirectorycode(),
							objdir.getParentdircode(), objdir.getDirectoryname(), objdir.getSitemaster());
		} else {
			lstdir = lsSheetOrderStructureRepository.findByDirectorynameIgnoreCaseAndParentdircodeAndSitemaster(
					objdir.getDirectoryname(), objdir.getParentdircode(), objdir.getSitemaster());
		}
		if (lstdir != null) {
			objResponse.setStatus(false);
			objResponse.setInformation("IDS_FolderExist");
		} else {
			objResponse.setStatus(true);
			objResponse.setInformation("IDS_FolderAdded");
		}
		objdir.setResponse(objResponse);
		return objdir;
	}

	public LSSheetOrderStructure Insertnewdirectory(LSSheetOrderStructure objdir) throws ParseException {
		System.out.println(objdir);
		lsSheetOrderStructureRepository.save(objdir);
		if (objdir.getParentdircode() != null) {
			List<LSSheetOrderStructure> structureobj = lsSheetOrderStructureRepository
					.findByDirectorycode(objdir.getParentdircode());
			if (!structureobj.isEmpty()) {
				structureobj.get(0).setDateModified(commonfunction.getCurrentUtcTime());
				structureobj.get(0).setModifiedby(objdir.getCreatedby());
				lsSheetOrderStructureRepository.saveAll(structureobj);
			}
		}
		return objdir;
	}

	public Map<String, Object> Getfoldersfororders(LSlogilablimsorderdetail objorder) {

		Map<String, Object> mapfolders = new HashMap<String, Object>();
		List<LSSheetOrderStructure> lstdir = new ArrayList<LSSheetOrderStructure>();
		List<Long> immutableNegativeValues = Arrays.asList(-3L, -22L);
		if (objorder.getLstuserMaster() == null) {
			lstdir = lsSheetOrderStructureRepository
					.findBySitemasterAndViewoptionAndDirectorycodeNotInOrCreatedbyAndSitemasterAndViewoptionOrCreatedbyAndSitemasterAndViewoptionOrderByDirectorycode(
							objorder.getLsuserMaster().getLssitemaster(), 1, immutableNegativeValues,
							objorder.getLsuserMaster(), objorder.getLsuserMaster().getLssitemaster(), 2,
							objorder.getLsuserMaster(), objorder.getLsuserMaster().getLssitemaster(), 3);
		} else {
			lstdir = lsSheetOrderStructureRepository
					.findBySitemasterAndViewoptionAndDirectorycodeNotInOrCreatedbyAndViewoptionOrSitemasterAndViewoptionAndCreatedbyInOrderByDirectorycode(
							objorder.getLsuserMaster().getLssitemaster(), 1, immutableNegativeValues,
							objorder.getLsuserMaster(), 2, objorder.getLsuserMaster().getLssitemaster(), 3,
							objorder.getLstuserMaster());
		}
		lstdir.addAll(lsSheetOrderStructureRepository.findByDirectorycodeIn(immutableNegativeValues));
//		if (objorder.getLstproject() != null && objorder.getLstproject().size() > 0) {
//			ArrayList<List<Object>> lsttest = new ArrayList<List<Object>>();
//			List<Integer> lsprojectcode = objorder.getLstproject().stream().map(LSprojectmaster::getProjectcode)
//					.collect(Collectors.toList());
//			List<LSprojectmaster> lstproject = lsprojectmasterRepository.findAll(lsprojectcode);
//			lsttest = lslogilablimsorderdetailRepository
//					.getLstestmasterlocalByOrderdisplaytypeAndLsprojectmasterInAndTestcodeIsNotNull(lsprojectcode);
//			mapfolders.put("tests", lsttest);
//			mapfolders.put("projects", lstproject);
//		} else {
//			mapfolders.put("tests", new ArrayList<Logilaborders>());
//			mapfolders.put("projects", new ArrayList<Projectmaster>());
//		}

//		List<Elnmaterial> Matireal_List = elnmaterialRepository
//				.findByNsitecodeOrderByNmaterialcodeDesc(objorder.getLsuserMaster().getLssitemaster().getSitecode());
//		List<Object> Material_List_Ordes = lslogilablimsorderdetailRepository
//				.getLstestmasterlocalAndmaterialByOrderdisplaytypeAndLSsamplemasterInAndTestcodeIsNotNull(
//						objorder.getLsuserMaster().getLssitemaster().getSitecode());
//		mapfolders.put("Materialtest", Material_List_Ordes);
//		mapfolders.put("Material", Matireal_List);

		mapfolders.put("directory", lstdir);

		return mapfolders;
	}

	public LSlogilablimsorderdetail UpdateFolderfororder(LSlogilablimsorderdetail order) {
		lslogilablimsorderdetailRepository.updatedirectory(order.getDirectorycode(), order.getBatchcode());
		return order;
	}

	public List<LSlogilablimsorderdetail> UpdateFolderfororders(LSlogilablimsorderdetail[] orderary) {
		List<LSlogilablimsorderdetail> order = Arrays.asList(orderary);
		if (order.size() > 0) {
			List<Long> lstorders = order.stream().map(LSlogilablimsorderdetail::getBatchcode)
					.collect(Collectors.toList());
			lslogilablimsorderdetailRepository.updatedirectory(order.get(0).getDirectorycode(), lstorders);
		}
		return order;
	}

	public List<Logilaborders> Getordersondirectory(LSSheetOrderStructure objdir) {
		List<Logilaborders> lstorder = new ArrayList<Logilaborders>();
		List<Logilaborders> lstorderstrcarray = new ArrayList<Logilaborders>();

		Date fromdate = objdir.getObjuser().getFromdate();
		Date todate = objdir.getObjuser().getTodate();
		Integer filetype = objdir.getFiletype();
		String orderflag = objdir.getOrderflag();
		Boolean iscancelled = objdir.isIscancelled();
		List<LSusersteam> userteams = objdir.getLsusersteam();
		Integer approval = objdir.getApprovelstatus() == null ? 0 : objdir.getApprovelstatus();
		List<LSSelectedTeam> selectedteamorders = new ArrayList<LSSelectedTeam>();
		selectedteamorders = userteams != null ? LSSelectedTeamRepository.findByUserteamIn(userteams)
				: selectedteamorders;
		List<LSprojectmaster> lstproject = lsprojectmasterRepository.findByLsusersteamIn(userteams);

		List<Long> selectedteambatchCodeList = (!selectedteamorders.isEmpty() && selectedteamorders != null)
				? selectedteamorders.stream().map(LSSelectedTeam::getBatchcode).filter(Objects::nonNull).distinct()
						.collect(Collectors.toList())
				: Collections.singletonList(-1L);

		if (filetype != null && filetype == -1 && objdir.getLsprojectmaster() == null && objdir.getTestcode() == null
				&& orderflag == null) {
			if (objdir.getLstuserMaster() == null) {

				if (objdir.getDirectorycode() == -3L || objdir.getDirectorycode() == -22L) {

					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndSitecodeOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, fromdate, todate, objdir.getCreatedby(),
									objdir.getSitemaster().getSitecode());
				} else {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterIsNullOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, fromdate, todate,
									objdir.getSitemaster().getSitecode(), lstproject, objdir.getDirectorycode(), 1,
									fromdate, todate, objdir.getSitemaster().getSitecode());

				}
				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterIsNullOrderByBatchcodeDesc(
								objdir.getDirectorycode(), 2, objdir.getCreatedby(), fromdate, todate,
								objdir.getSitemaster().getSitecode(), lstproject, objdir.getDirectorycode(), 2,
								objdir.getCreatedby(), fromdate, todate, objdir.getSitemaster().getSitecode(),
								objdir.getDirectorycode(), 3, objdir.getCreatedby(), fromdate, todate,
								objdir.getSitemaster().getSitecode(), lstproject, objdir.getDirectorycode(), 3,
								objdir.getCreatedby(), fromdate, todate, objdir.getSitemaster().getSitecode()));

			} else {
				if (objdir.getDirectorycode() == -3L || objdir.getDirectorycode() == -22L) {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndSitecodeOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, fromdate, todate, objdir.getCreatedby(),
									objdir.getSitemaster().getSitecode());
				} else {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, fromdate, todate,
									objdir.getSitemaster().getSitecode());

				}

				/** Changed for selection of needed project team feature **/
				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNullOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNotNullAndBatchcodeInOrderByBatchcodeDesc(
								objdir.getDirectorycode(), 2, objdir.getCreatedby(), fromdate, todate,
								objdir.getDirectorycode(), 3, fromdate, todate, objdir.getLstuserMaster(),
								objdir.getDirectorycode(), 3, fromdate, todate, objdir.getLstuserMaster(),
								selectedteambatchCodeList));
			}
		} else if (filetype != null && filetype == -1 && objdir.getLsprojectmaster() != null
				&& objdir.getLsprojectmaster().getProjectcode() != -1 && objdir.getTestcode() == null
				&& orderflag == null) {

			if (objdir.getLstuserMaster() == null) {
				if (objdir.getDirectorycode() == -3L || objdir.getDirectorycode() == -22L) {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndLsprojectmasterOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, fromdate, todate, objdir.getCreatedby(),
									objdir.getLsprojectmaster());
				} else {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, fromdate, todate, objdir.getLsprojectmaster());
				}

				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsselectedTeamIsNullAndLsprojectmasterOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsselectedTeamIsNotNullAndBatchcodeInAndLsprojectmasterOrderByBatchcodeDesc(
								objdir.getDirectorycode(), 2, objdir.getCreatedby(), fromdate, todate,
								objdir.getLsprojectmaster(), objdir.getDirectorycode(), 3, objdir.getCreatedby(),
								fromdate, todate, objdir.getLsprojectmaster(), objdir.getDirectorycode(), 3,
								objdir.getCreatedby(), fromdate, todate, selectedteambatchCodeList,
								objdir.getLsprojectmaster()));

			} else {
				if (objdir.getDirectorycode() == -3L || objdir.getDirectorycode() == -22L) {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndLsprojectmasterOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, fromdate, todate, objdir.getCreatedby(),
									objdir.getLsprojectmaster());
				} else {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, fromdate, todate, objdir.getLsprojectmaster());
				}

				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNullAndLsprojectmasterOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNotNullAndBatchcodeInAndLsprojectmasterOrderByBatchcodeDesc(
								objdir.getDirectorycode(), 2, objdir.getCreatedby(), fromdate, todate,
								objdir.getLsprojectmaster(), objdir.getDirectorycode(), 3, fromdate, todate,
								objdir.getLstuserMaster(), objdir.getLsprojectmaster(), objdir.getDirectorycode(), 3,
								fromdate, todate, objdir.getLstuserMaster(), selectedteambatchCodeList,
								objdir.getLsprojectmaster()));
			}
		} else if (filetype != null && filetype == -1 && objdir.getLsprojectmaster() != null
				&& objdir.getLsprojectmaster().getProjectcode() != -1 && objdir.getTestcode() != null
				&& orderflag == null) {

			if (objdir.getLstuserMaster() == null) {
				if (objdir.getDirectorycode() == -3L || objdir.getDirectorycode() == -22L) {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndLsprojectmasterAndTestcodeOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, fromdate, todate, objdir.getCreatedby(),
									objdir.getLsprojectmaster(), objdir.getTestcode());
				} else {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterAndTestcodeOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, fromdate, todate, objdir.getLsprojectmaster(),
									objdir.getTestcode());
				}

				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterAndTestcodeOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsselectedTeamIsNullAndLsprojectmasterAndTestcodeOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsselectedTeamIsNotNullAndBatchcodeInAndLsprojectmasterAndTestcodeOrderByBatchcodeDesc(
								objdir.getDirectorycode(), 2, objdir.getCreatedby(), fromdate, todate,
								objdir.getLsprojectmaster(), objdir.getTestcode(), objdir.getDirectorycode(), 3,
								objdir.getCreatedby(), fromdate, todate, objdir.getLsprojectmaster(),
								objdir.getTestcode(), objdir.getDirectorycode(), 3, objdir.getCreatedby(), fromdate,
								todate, selectedteambatchCodeList, objdir.getLsprojectmaster(), objdir.getTestcode()));

			} else {
				if (objdir.getDirectorycode() == -3L || objdir.getDirectorycode() == -22L) {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndLsprojectmasterAndTestcodeOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, fromdate, todate, objdir.getCreatedby(),
									objdir.getLsprojectmaster(), objdir.getTestcode());
				} else {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterAndTestcodeOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, fromdate, todate, objdir.getLsprojectmaster(),
									objdir.getTestcode());
				}

				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterAndTestcodeOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNullAndLsprojectmasterAndTestcodeOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNotNullAndBatchcodeInAndLsprojectmasterAndTestcodeOrderByBatchcodeDesc(
								objdir.getDirectorycode(), 2, objdir.getCreatedby(), fromdate, todate,
								objdir.getLsprojectmaster(), objdir.getTestcode(), objdir.getDirectorycode(), 3,
								fromdate, todate, objdir.getLstuserMaster(), objdir.getLsprojectmaster(),
								objdir.getTestcode(), objdir.getDirectorycode(), 3, fromdate, todate,
								objdir.getLstuserMaster(), selectedteambatchCodeList, objdir.getLsprojectmaster(),
								objdir.getTestcode()));

			}
		} else if (filetype != null && filetype == -1 && objdir.getTestcode() != null
				&& objdir.getLsprojectmaster() == null && orderflag == null) {
			if (objdir.getLstuserMaster() == null) {
				if (objdir.getDirectorycode() == -3L || objdir.getDirectorycode() == -22L) {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndTestcodeOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, fromdate, todate, objdir.getCreatedby(),
									objdir.getTestcode());
				} else {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterIsNullOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, fromdate, todate, objdir.getTestcode(), lstproject,
									objdir.getDirectorycode(), 1, fromdate, todate, objdir.getTestcode());
				}

				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsselectedTeamIsNullAndTestcodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsselectedTeamIsNullAndTestcodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsselectedTeamIsNotNullAndBatchcodeInAndTestcodeOrderByBatchcodeDesc(
								objdir.getDirectorycode(), 2, objdir.getCreatedby(), fromdate, todate,
								objdir.getTestcode(), lstproject, objdir.getDirectorycode(), 2, objdir.getCreatedby(),
								fromdate, todate, objdir.getTestcode(), objdir.getDirectorycode(), 3,
								objdir.getCreatedby(), fromdate, todate, objdir.getTestcode(), lstproject,
								objdir.getDirectorycode(), 3, objdir.getCreatedby(), fromdate, todate,
								objdir.getTestcode(), objdir.getDirectorycode(), 3, objdir.getCreatedby(), fromdate,
								todate, selectedteambatchCodeList, objdir.getTestcode()));

			} else {
				if (objdir.getDirectorycode() == -3L || objdir.getDirectorycode() == -22L) {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndTestcodeOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, fromdate, todate, objdir.getCreatedby(),
									objdir.getTestcode());
				} else {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndTestcodeOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, fromdate, todate, objdir.getTestcode());
				}

				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndTestcodeOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNullAndTestcodeOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNotNullAndBatchcodeInAndTestcodeOrderByBatchcodeDesc(
								objdir.getDirectorycode(), 2, objdir.getCreatedby(), fromdate, todate,
								objdir.getTestcode(), objdir.getDirectorycode(), 3, fromdate, todate,
								objdir.getLstuserMaster(), objdir.getTestcode(), objdir.getDirectorycode(), 3, fromdate,
								todate, objdir.getLstuserMaster(), selectedteambatchCodeList, objdir.getTestcode()));

			}
		} else if (filetype != null && filetype != -1 && objdir.getTestcode() == null
				&& objdir.getLsprojectmaster() == null && orderflag == null) {
			if (objdir.getLstuserMaster() == null) {
				if (objdir.getDirectorycode() == -3L || objdir.getDirectorycode() == -22L) {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, filetype, fromdate, todate, objdir.getCreatedby());
				} else {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterInOrDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterIsNullOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, filetype, fromdate, todate, lstproject,
									objdir.getDirectorycode(), 1, filetype, fromdate, todate);
				}

				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterInOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsselectedTeamIsNullAndLsprojectmasterInOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsselectedTeamIsNullAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsselectedTeamIsNotNullAndBatchcodeInOrderByBatchcodeDesc(
								objdir.getDirectorycode(), 2, objdir.getCreatedby(), filetype, fromdate, todate,
								lstproject, objdir.getDirectorycode(), 2, objdir.getCreatedby(), filetype, fromdate,
								todate, objdir.getDirectorycode(), 3, objdir.getCreatedby(), filetype, fromdate, todate,
								lstproject, objdir.getDirectorycode(), 3, objdir.getCreatedby(), filetype, fromdate,
								todate, objdir.getDirectorycode(), 3, objdir.getCreatedby(), filetype, fromdate, todate,
								selectedteambatchCodeList));
			} else {
				if (objdir.getDirectorycode() == -3L || objdir.getDirectorycode() == -22L) {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, filetype, fromdate, todate, objdir.getCreatedby());
				} else {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, filetype, fromdate, todate);
				}

				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenOrDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNullOrDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNotNullAndBatchcodeInOrderByBatchcodeDesc(
								objdir.getDirectorycode(), 2, objdir.getCreatedby(), filetype, fromdate, todate,
								objdir.getDirectorycode(), 3, filetype, fromdate, todate, objdir.getLstuserMaster(),
								objdir.getDirectorycode(), 3, filetype, fromdate, todate, objdir.getLstuserMaster(),
								selectedteambatchCodeList));

			}
		} else if (filetype != null && filetype != -1 && objdir.getTestcode() != null
				&& objdir.getLsprojectmaster() == null && orderflag == null) {
			if (objdir.getLstuserMaster() == null) {
				if (objdir.getDirectorycode() == -3L || objdir.getDirectorycode() == -22L) {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterAndTestcodeOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, filetype, fromdate, todate, objdir.getCreatedby(),
									objdir.getTestcode());
				} else {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndTestcodeOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, filetype, fromdate, todate, objdir.getTestcode());
				}

				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndTestcodeOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsselectedTeamIsNullAndTestcodeOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsselectedTeamIsNotNullAndBatchcodeInAndTestcodeOrderByBatchcodeDesc(
								objdir.getDirectorycode(), 2, objdir.getCreatedby(), filetype, fromdate, todate,
								objdir.getTestcode(), objdir.getDirectorycode(), 3, objdir.getCreatedby(), filetype,
								fromdate, todate, objdir.getTestcode(), objdir.getDirectorycode(), 3,
								objdir.getCreatedby(), filetype, fromdate, todate, selectedteambatchCodeList,
								objdir.getTestcode()));

			} else {
				if (objdir.getDirectorycode() == -3L || objdir.getDirectorycode() == -22L) {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterAndTestcodeOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, filetype, fromdate, todate, objdir.getCreatedby(),
									objdir.getTestcode());
				} else {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndTestcodeOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, filetype, fromdate, todate, objdir.getTestcode());
				}

				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndTestcodeOrDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNullAndTestcodeOrDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNotNullAndBatchcodeInAndTestcodeOrderByBatchcodeDesc(
								objdir.getDirectorycode(), 2, objdir.getCreatedby(), filetype, fromdate, todate,
								objdir.getTestcode(), objdir.getDirectorycode(), 3, filetype, fromdate, todate,
								objdir.getLstuserMaster(), objdir.getTestcode(), objdir.getDirectorycode(), 3, filetype,
								fromdate, todate, objdir.getLstuserMaster(), selectedteambatchCodeList,
								objdir.getTestcode()));

			}
		} else if (filetype != null && filetype != -1 && objdir.getTestcode() == null
				&& objdir.getLsprojectmaster() != null && orderflag == null) {
			if (objdir.getLstuserMaster() == null) {
				if (objdir.getDirectorycode() == -3L || objdir.getDirectorycode() == -22L) {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterAndLsprojectmasterOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, filetype, fromdate, todate, objdir.getCreatedby(),
									objdir.getLsprojectmaster());
				} else {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, filetype, fromdate, todate,
									objdir.getLsprojectmaster());
				}

				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsselectedTeamIsNullAndLsprojectmasterOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsselectedTeamIsNotNullAndBatchcodeInAndLsprojectmasterOrderByBatchcodeDesc(
								objdir.getDirectorycode(), 2, objdir.getCreatedby(), filetype, fromdate, todate,
								objdir.getLsprojectmaster(), objdir.getDirectorycode(), 3, objdir.getCreatedby(),
								filetype, fromdate, todate, objdir.getLsprojectmaster(), objdir.getDirectorycode(), 3,
								objdir.getCreatedby(), filetype, fromdate, todate, selectedteambatchCodeList,
								objdir.getLsprojectmaster()));

			} else {
				if (objdir.getDirectorycode() == -3L || objdir.getDirectorycode() == -22L) {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterAndLsprojectmasterOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, filetype, fromdate, todate, objdir.getCreatedby(),
									objdir.getLsprojectmaster());
				} else {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, filetype, fromdate, todate,
									objdir.getLsprojectmaster());
				}

				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterOrDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNullAndLsprojectmasterOrDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNotNullAndBatchcodeInAndLsprojectmasterOrderByBatchcodeDesc(
								objdir.getDirectorycode(), 2, objdir.getCreatedby(), filetype, fromdate, todate,
								objdir.getLsprojectmaster(), objdir.getDirectorycode(), 3, filetype, fromdate, todate,
								objdir.getLstuserMaster(), objdir.getLsprojectmaster(), objdir.getDirectorycode(), 3,
								filetype, fromdate, todate, objdir.getLstuserMaster(), selectedteambatchCodeList,
								objdir.getLsprojectmaster()));

			}
		} else if (filetype != null && filetype != -1 && objdir.getTestcode() != null
				&& objdir.getLsprojectmaster() != null && orderflag == null) {
			if (objdir.getLstuserMaster() == null) {
				if (objdir.getDirectorycode() == -3L || objdir.getDirectorycode() == -22L) {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterAndLsprojectmasterAndTestcodeOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, filetype, fromdate, todate, objdir.getCreatedby(),
									objdir.getLsprojectmaster(), objdir.getTestcode());
				} else {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterAndTestcodeOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, filetype, fromdate, todate,
									objdir.getLsprojectmaster(), objdir.getTestcode());
				}

				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterAndTestcodeOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsselectedTeamIsNullAndLsprojectmasterAndTestcodeOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsselectedTeamIsNotNullAndBatchcodeInAndLsprojectmasterAndTestcodeOrderByBatchcodeDesc(
								objdir.getDirectorycode(), 2, objdir.getCreatedby(), filetype, fromdate, todate,
								objdir.getLsprojectmaster(), objdir.getTestcode(), objdir.getDirectorycode(), 3,
								objdir.getCreatedby(), filetype, fromdate, todate, objdir.getLsprojectmaster(),
								objdir.getTestcode(), objdir.getDirectorycode(), 3, objdir.getCreatedby(), filetype,
								fromdate, todate, selectedteambatchCodeList, objdir.getLsprojectmaster(),
								objdir.getTestcode()));

			} else {
				if (objdir.getDirectorycode() == -3L || objdir.getDirectorycode() == -22L) {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterAndLsprojectmasterAndTestcodeOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, filetype, fromdate, todate, objdir.getCreatedby(),
									objdir.getLsprojectmaster(), objdir.getTestcode());
				} else {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterAndTestcodeOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, filetype, fromdate, todate,
									objdir.getLsprojectmaster(), objdir.getTestcode());
				}

				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterAndTestcodeOrDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNullAndLsprojectmasterAndTestcodeOrDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNotNullAndBatchcodeInAndLsprojectmasterAndTestcodeOrderByBatchcodeDesc(
								objdir.getDirectorycode(), 2, objdir.getCreatedby(), filetype, fromdate, todate,
								objdir.getLsprojectmaster(), objdir.getTestcode(), objdir.getDirectorycode(), 3,
								filetype, fromdate, todate, objdir.getLstuserMaster(), objdir.getLsprojectmaster(),
								objdir.getTestcode(), objdir.getDirectorycode(), 3, filetype, fromdate, todate,
								objdir.getLstuserMaster(), selectedteambatchCodeList, objdir.getLsprojectmaster(),
								objdir.getTestcode()));

			}
		}

		// orderflag
		else if (filetype != null && filetype == -1 && objdir.getLsprojectmaster() == null
				&& objdir.getTestcode() == null && orderflag != null) {
			if (objdir.getLstuserMaster() == null) {

				if (objdir.getDirectorycode() == -3L || objdir.getDirectorycode() == -22L) {

					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndSitecodeAndOrderflagOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, fromdate, todate, objdir.getCreatedby(),
									objdir.getSitemaster().getSitecode(), orderflag);
				} else {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeAndOrderflagAndLsprojectmasterInOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeAndOrderflagAndLsprojectmasterIsNullOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, fromdate, todate,
									objdir.getSitemaster().getSitecode(), orderflag, lstproject,
									objdir.getDirectorycode(), 1, fromdate, todate,
									objdir.getSitemaster().getSitecode(), orderflag);

				}
				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndOrderflagAndLsprojectmasterInOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndOrderflagAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndOrderflagAndLsprojectmasterInOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndOrderflagAndLsprojectmasterIsNullOrderByBatchcodeDesc(
								objdir.getDirectorycode(), 2, objdir.getCreatedby(), fromdate, todate,
								objdir.getSitemaster().getSitecode(), orderflag, lstproject, objdir.getDirectorycode(),
								2, objdir.getCreatedby(), fromdate, todate, objdir.getSitemaster().getSitecode(),
								orderflag, objdir.getDirectorycode(), 3, objdir.getCreatedby(), fromdate, todate,
								objdir.getSitemaster().getSitecode(), orderflag, lstproject, objdir.getDirectorycode(),
								3, objdir.getCreatedby(), fromdate, todate, objdir.getSitemaster().getSitecode(),
								orderflag));

			} else {
				if (objdir.getDirectorycode() == -3L || objdir.getDirectorycode() == -22L) {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndSitecodeAndOrderflagOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, fromdate, todate, objdir.getCreatedby(),
									objdir.getSitemaster().getSitecode(), orderflag);
				} else {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeAndOrderflagOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, fromdate, todate,
									objdir.getSitemaster().getSitecode(), orderflag);

				}

				/** Changed for selection of needed project team feature **/
				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNullAndOrderflagOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNotNullAndBatchcodeInAndOrderflagOrderByBatchcodeDesc(
								objdir.getDirectorycode(), 2, objdir.getCreatedby(), fromdate, todate, orderflag,
								objdir.getDirectorycode(), 3, fromdate, todate, objdir.getLstuserMaster(), orderflag,
								objdir.getDirectorycode(), 3, fromdate, todate, objdir.getLstuserMaster(),
								selectedteambatchCodeList, orderflag));
			}
		} else if (filetype != null && filetype == -1 && objdir.getLsprojectmaster() != null
				&& objdir.getLsprojectmaster().getProjectcode() != -1 && objdir.getTestcode() == null
				&& orderflag != null) {

			if (objdir.getLstuserMaster() == null) {
				if (objdir.getDirectorycode() == -3L || objdir.getDirectorycode() == -22L) {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndLsprojectmasterAndOrderflagOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, fromdate, todate, objdir.getCreatedby(),
									objdir.getLsprojectmaster(), orderflag);
				} else {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterAndOrderflagOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, fromdate, todate, objdir.getLsprojectmaster(),
									orderflag);
				}

				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterAndOrderflagOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsselectedTeamIsNullAndLsprojectmasterAndOrderflagOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsselectedTeamIsNotNullAndBatchcodeInAndLsprojectmasterAndOrderflagOrderByBatchcodeDesc(
								objdir.getDirectorycode(), 2, objdir.getCreatedby(), fromdate, todate,
								objdir.getLsprojectmaster(), orderflag, objdir.getDirectorycode(), 3,
								objdir.getCreatedby(), fromdate, todate, objdir.getLsprojectmaster(), orderflag,
								objdir.getDirectorycode(), 3, objdir.getCreatedby(), fromdate, todate,
								selectedteambatchCodeList, objdir.getLsprojectmaster(), orderflag));

			} else {
				if (objdir.getDirectorycode() == -3L || objdir.getDirectorycode() == -22L) {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndLsprojectmasterAndOrderflagOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, fromdate, todate, objdir.getCreatedby(),
									objdir.getLsprojectmaster(), orderflag);
				} else {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterAndOrderflagOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, fromdate, todate, objdir.getLsprojectmaster(),
									orderflag);
				}

				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterAndOrderflagOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNullAndLsprojectmasterAndOrderflagOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNotNullAndBatchcodeInAndLsprojectmasterAndOrderflagOrderByBatchcodeDesc(
								objdir.getDirectorycode(), 2, objdir.getCreatedby(), fromdate, todate,
								objdir.getLsprojectmaster(), orderflag, objdir.getDirectorycode(), 3, fromdate, todate,
								objdir.getLstuserMaster(), objdir.getLsprojectmaster(), orderflag,
								objdir.getDirectorycode(), 3, fromdate, todate, objdir.getLstuserMaster(),
								selectedteambatchCodeList, objdir.getLsprojectmaster(), orderflag));
			}
		} else if (filetype != null && filetype == -1 && objdir.getLsprojectmaster() != null
				&& objdir.getLsprojectmaster().getProjectcode() != -1 && objdir.getTestcode() != null
				&& orderflag != null) {

			if (objdir.getLstuserMaster() == null) {
				if (objdir.getDirectorycode() == -3L || objdir.getDirectorycode() == -22L) {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndLsprojectmasterAndTestcodeAndOrderflagOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, fromdate, todate, objdir.getCreatedby(),
									objdir.getLsprojectmaster(), objdir.getTestcode(), orderflag);
				} else {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterAndTestcodeAndOrderflagOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, fromdate, todate, objdir.getLsprojectmaster(),
									objdir.getTestcode(), orderflag);
				}

				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterAndTestcodeAndOrderflagOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsselectedTeamIsNullAndLsprojectmasterAndTestcodeAndOrderflagOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsselectedTeamIsNotNullAndBatchcodeInAndLsprojectmasterAndTestcodeAndOrderflagOrderByBatchcodeDesc(
								objdir.getDirectorycode(), 2, objdir.getCreatedby(), fromdate, todate,
								objdir.getLsprojectmaster(), objdir.getTestcode(), orderflag, objdir.getDirectorycode(),
								3, objdir.getCreatedby(), fromdate, todate, objdir.getLsprojectmaster(),
								objdir.getTestcode(), orderflag, objdir.getDirectorycode(), 3, objdir.getCreatedby(),
								fromdate, todate, selectedteambatchCodeList, objdir.getLsprojectmaster(),
								objdir.getTestcode(), orderflag));

			} else {
				if (objdir.getDirectorycode() == -3L || objdir.getDirectorycode() == -22L) {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndLsprojectmasterAndTestcodeAndOrderflagOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, fromdate, todate, objdir.getCreatedby(),
									objdir.getLsprojectmaster(), objdir.getTestcode(), orderflag);
				} else {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterAndTestcodeAndOrderflagOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, fromdate, todate, objdir.getLsprojectmaster(),
									objdir.getTestcode(), orderflag);
				}

				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterAndTestcodeAndOrderflagOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNullAndLsprojectmasterAndTestcodeAndOrderflagOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNotNullAndBatchcodeInAndLsprojectmasterAndTestcodeAndOrderflagOrderByBatchcodeDesc(
								objdir.getDirectorycode(), 2, objdir.getCreatedby(), fromdate, todate,
								objdir.getLsprojectmaster(), objdir.getTestcode(), orderflag, objdir.getDirectorycode(),
								3, fromdate, todate, objdir.getLstuserMaster(), objdir.getLsprojectmaster(),
								objdir.getTestcode(), orderflag, objdir.getDirectorycode(), 3, fromdate, todate,
								objdir.getLstuserMaster(), selectedteambatchCodeList, objdir.getLsprojectmaster(),
								objdir.getTestcode(), orderflag));

			}
		} else if (filetype != null && filetype == -1 && objdir.getTestcode() != null
				&& objdir.getLsprojectmaster() == null && orderflag != null) {
			if (objdir.getLstuserMaster() == null) {
				if (objdir.getDirectorycode() == -3L || objdir.getDirectorycode() == -22L) {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndTestcodeAndOrderflagOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, fromdate, todate, objdir.getCreatedby(),
									objdir.getTestcode(), orderflag);
				} else {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndTestcodeAndOrderflagOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, fromdate, todate, objdir.getTestcode(), orderflag);
				}

				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndTestcodeAndOrderflagOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsselectedTeamIsNullAndTestcodeAndOrderflagOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsselectedTeamIsNotNullAndBatchcodeInAndTestcodeAndOrderflagOrderByBatchcodeDesc(
								objdir.getDirectorycode(), 2, objdir.getCreatedby(), fromdate, todate,
								objdir.getTestcode(), orderflag, objdir.getDirectorycode(), 3, objdir.getCreatedby(),
								fromdate, todate, objdir.getTestcode(), orderflag, objdir.getDirectorycode(), 3,
								objdir.getCreatedby(), fromdate, todate, selectedteambatchCodeList,
								objdir.getTestcode(), orderflag));

			} else {
				if (objdir.getDirectorycode() == -3L || objdir.getDirectorycode() == -22L) {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterAndTestcodeAndOrderflagOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, fromdate, todate, objdir.getCreatedby(),
									objdir.getTestcode(), orderflag);
				} else {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndTestcodeAndOrderflagOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, fromdate, todate, objdir.getTestcode(), orderflag);
				}

				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndTestcodeAndOrderflagOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNullAndTestcodeAndOrderflagOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNotNullAndBatchcodeInAndTestcodeAndOrderflagOrderByBatchcodeDesc(
								objdir.getDirectorycode(), 2, objdir.getCreatedby(), fromdate, todate,
								objdir.getTestcode(), orderflag, objdir.getDirectorycode(), 3, fromdate, todate,
								objdir.getLstuserMaster(), objdir.getTestcode(), orderflag, objdir.getDirectorycode(),
								3, fromdate, todate, objdir.getLstuserMaster(), selectedteambatchCodeList,
								objdir.getTestcode(), orderflag));

			}
		} else if (filetype != null && filetype != -1 && objdir.getTestcode() == null
				&& objdir.getLsprojectmaster() == null && orderflag != null) {
			if (objdir.getLstuserMaster() == null) {
				if (objdir.getDirectorycode() == -3L || objdir.getDirectorycode() == -22L) {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, filetype, fromdate, todate, objdir.getCreatedby(),
									orderflag);
				} else {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndOrderflagOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, filetype, fromdate, todate, orderflag);
				}

				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndOrderflagOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsselectedTeamIsNullAndOrderflagOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsselectedTeamIsNotNullAndBatchcodeInAndOrderflagOrderByBatchcodeDesc(
								objdir.getDirectorycode(), 2, objdir.getCreatedby(), filetype, fromdate, todate,
								orderflag, objdir.getDirectorycode(), 3, objdir.getCreatedby(), filetype, fromdate,
								todate, orderflag, objdir.getDirectorycode(), 3, objdir.getCreatedby(), filetype,
								fromdate, todate, selectedteambatchCodeList, orderflag));

			} else {
				if (objdir.getDirectorycode() == -3L || objdir.getDirectorycode() == -22L) {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterAndOrderflagOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, filetype, fromdate, todate, objdir.getCreatedby(),
									orderflag);
				} else {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndOrderflagOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, filetype, fromdate, todate, orderflag);
				}

				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndOrderflagOrDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNullAndOrderflagOrDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNotNullAndBatchcodeInAndOrderflagOrderByBatchcodeDesc(
								objdir.getDirectorycode(), 2, objdir.getCreatedby(), filetype, fromdate, todate,
								orderflag, objdir.getDirectorycode(), 3, filetype, fromdate, todate,
								objdir.getLstuserMaster(), orderflag, objdir.getDirectorycode(), 3, filetype, fromdate,
								todate, objdir.getLstuserMaster(), selectedteambatchCodeList, orderflag));

			}
		} else if (filetype != null && filetype != -1 && objdir.getTestcode() != null
				&& objdir.getLsprojectmaster() == null && orderflag != null) {
			if (objdir.getLstuserMaster() == null) {
				if (objdir.getDirectorycode() == -3L || objdir.getDirectorycode() == -22L) {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterAndTestcodeAndOrderflagOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, filetype, fromdate, todate, objdir.getCreatedby(),
									objdir.getTestcode(), orderflag);
				} else {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndTestcodeAndOrderflagOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, filetype, fromdate, todate, objdir.getTestcode(),
									orderflag);
				}

				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndTestcodeAndOrderflagOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsselectedTeamIsNullAndTestcodeAndOrderflagOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsselectedTeamIsNotNullAndBatchcodeInAndTestcodeAndOrderflagOrderByBatchcodeDesc(
								objdir.getDirectorycode(), 2, objdir.getCreatedby(), filetype, fromdate, todate,
								objdir.getTestcode(), orderflag, objdir.getDirectorycode(), 3, objdir.getCreatedby(),
								filetype, fromdate, todate, objdir.getTestcode(), orderflag, objdir.getDirectorycode(),
								3, objdir.getCreatedby(), filetype, fromdate, todate, selectedteambatchCodeList,
								objdir.getTestcode(), orderflag));

			} else {
				if (objdir.getDirectorycode() == -3L || objdir.getDirectorycode() == -22L) {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterAndTestcodeAndOrderflagOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, filetype, fromdate, todate, objdir.getCreatedby(),
									objdir.getTestcode(), orderflag);
				} else {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndTestcodeAndOrderflagOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, filetype, fromdate, todate, objdir.getTestcode(),
									orderflag);
				}

				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndTestcodeAndOrderflagOrDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNullAndTestcodeAndOrderflagOrDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNotNullAndBatchcodeInAndTestcodeAndOrderflagOrderByBatchcodeDesc(
								objdir.getDirectorycode(), 2, objdir.getCreatedby(), filetype, fromdate, todate,
								objdir.getTestcode(), orderflag, objdir.getDirectorycode(), 3, filetype, fromdate,
								todate, objdir.getLstuserMaster(), objdir.getTestcode(), orderflag,
								objdir.getDirectorycode(), 3, filetype, fromdate, todate, objdir.getLstuserMaster(),
								selectedteambatchCodeList, objdir.getTestcode(), orderflag));

			}
		} else if (filetype != null && filetype != -1 && objdir.getTestcode() == null
				&& objdir.getLsprojectmaster() != null && orderflag != null) {
			if (objdir.getLstuserMaster() == null) {
				if (objdir.getDirectorycode() == -3L || objdir.getDirectorycode() == -22L) {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterAndLsprojectmasterAndOrderflagOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, filetype, fromdate, todate, objdir.getCreatedby(),
									objdir.getLsprojectmaster(), orderflag);
				} else {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterAndOrderflagOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, filetype, fromdate, todate,
									objdir.getLsprojectmaster(), orderflag);
				}

				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterAndOrderflagOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsselectedTeamIsNullAndLsprojectmasterAndOrderflagOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsselectedTeamIsNotNullAndBatchcodeInAndLsprojectmasterAndOrderflagOrderByBatchcodeDesc(
								objdir.getDirectorycode(), 2, objdir.getCreatedby(), filetype, fromdate, todate,
								objdir.getLsprojectmaster(), orderflag, objdir.getDirectorycode(), 3,
								objdir.getCreatedby(), filetype, fromdate, todate, objdir.getLsprojectmaster(),
								orderflag, objdir.getDirectorycode(), 3, objdir.getCreatedby(), filetype, fromdate,
								todate, selectedteambatchCodeList, objdir.getLsprojectmaster(), orderflag));

			} else {
				if (objdir.getDirectorycode() == -3L || objdir.getDirectorycode() == -22L) {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterAndLsprojectmasterAndOrderflagOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, filetype, fromdate, todate, objdir.getCreatedby(),
									objdir.getLsprojectmaster(), orderflag);
				} else {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterAndOrderflagOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, filetype, fromdate, todate,
									objdir.getLsprojectmaster(), orderflag);
				}

				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterAndOrderflagOrDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNullAndLsprojectmasterAndOrderflagOrDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNotNullAndBatchcodeInAndLsprojectmasterAndOrderflagOrderByBatchcodeDesc(
								objdir.getDirectorycode(), 2, objdir.getCreatedby(), filetype, fromdate, todate,
								objdir.getLsprojectmaster(), orderflag, objdir.getDirectorycode(), 3, filetype,
								fromdate, todate, objdir.getLstuserMaster(), objdir.getLsprojectmaster(), orderflag,
								objdir.getDirectorycode(), 3, filetype, fromdate, todate, objdir.getLstuserMaster(),
								selectedteambatchCodeList, objdir.getLsprojectmaster(), orderflag));

			}
		} else if (filetype != null && filetype != -1 && objdir.getTestcode() != null
				&& objdir.getLsprojectmaster() != null && orderflag != null) {
			if (objdir.getLstuserMaster() == null) {
				if (objdir.getDirectorycode() == -3L || objdir.getDirectorycode() == -22L) {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterAndLsprojectmasterAndTestcodeAndOrderflagOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, filetype, fromdate, todate, objdir.getCreatedby(),
									objdir.getLsprojectmaster(), objdir.getTestcode(), orderflag);
				} else {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterAndTestcodeAndOrderflagOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, filetype, fromdate, todate,
									objdir.getLsprojectmaster(), objdir.getTestcode(), orderflag);
				}

				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterAndTestcodeAndOrderflagOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsselectedTeamIsNullAndLsprojectmasterAndTestcodeAndOrderflagOrDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsselectedTeamIsNotNullAndBatchcodeInAndLsprojectmasterAndTestcodeAndOrderflagOrderByBatchcodeDesc(
								objdir.getDirectorycode(), 2, objdir.getCreatedby(), filetype, fromdate, todate,
								objdir.getLsprojectmaster(), objdir.getTestcode(), orderflag, objdir.getDirectorycode(),
								3, objdir.getCreatedby(), filetype, fromdate, todate, objdir.getLsprojectmaster(),
								objdir.getTestcode(), orderflag, objdir.getDirectorycode(), 3, objdir.getCreatedby(),
								filetype, fromdate, todate, selectedteambatchCodeList, objdir.getLsprojectmaster(),
								objdir.getTestcode(), orderflag));

			} else {
				if (objdir.getDirectorycode() == -3L || objdir.getDirectorycode() == -22L) {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterAndLsprojectmasterAndTestcodeAndOrderflagOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, filetype, fromdate, todate, objdir.getCreatedby(),
									objdir.getLsprojectmaster(), objdir.getTestcode(), orderflag);
				} else {
					lstorder = logilablimsorderdetailsRepository
							.findByDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterAndTestcodeAndOrderflagOrderByBatchcodeDesc(
									objdir.getDirectorycode(), 1, filetype, fromdate, todate,
									objdir.getLsprojectmaster(), objdir.getTestcode(), orderflag);
				}

				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeAndViewoptionAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterAndTestcodeAndOrderflagOrDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNullAndLsprojectmasterAndTestcodeAndOrderflagOrDirectorycodeAndViewoptionAndFiletypeAndCreatedtimestampBetweenAndLsuserMasterInAndLsselectedTeamIsNotNullAndBatchcodeInAndLsprojectmasterAndTestcodeAndOrderflagOrderByBatchcodeDesc(
								objdir.getDirectorycode(), 2, objdir.getCreatedby(), filetype, fromdate, todate,
								objdir.getLsprojectmaster(), objdir.getTestcode(), orderflag, objdir.getDirectorycode(),
								3, filetype, fromdate, todate, objdir.getLstuserMaster(), objdir.getLsprojectmaster(),
								objdir.getTestcode(), orderflag, objdir.getDirectorycode(), 3, filetype, fromdate,
								todate, objdir.getLstuserMaster(), selectedteambatchCodeList,
								objdir.getLsprojectmaster(), objdir.getTestcode(), orderflag));

			}
		}

//		lstorder.forEach(objorderDetail -> objorderDetail.setLstworkflow(objdir.getLstworkflow()));
		List<Integer> lstworkflowcode = new ArrayList<>();
		if(objdir.getLstworkflow() != null) {
			lstworkflowcode= objdir.getLstworkflow().stream().map(LSworkflow::getWorkflowcode)
					.collect(Collectors.toList());
		}
		List<LSworkflow> workflowobj = lsworkflowRepository
				.findByLssitemasterAndStatusOrderByWorkflowcodeAsc(objdir.getLsuserMaster().getLssitemaster(), 1);
		for (Logilaborders order : lstorder) {
			if (order.getLsworkflow() !=null && lstworkflowcode.contains(order.getLsworkflow().getWorkflowcode())) {
				int previousworkflowIndex = -1;
				if (order.getPrevwfw() != null) {
					previousworkflowIndex = IntStream.range(0, workflowobj.size())
							.filter(i -> workflowobj.get(i).getWorkflowcode() == order.getPrevwfw().getWorkflowcode())
							.findFirst().orElse(-1);
				}
				int currentworkflowIndex = -1;
				if (order.getLsworkflow() != null) {
					currentworkflowIndex = IntStream.range(0, workflowobj.size()).filter(
							i -> workflowobj.get(i).getWorkflowcode() == order.getLsworkflow().getWorkflowcode())
							.findFirst().orElse(-1);
				}
				boolean selfapproval = false;
				if (order.getLsworkflow() != null && Boolean.TRUE.equals(order.getLsworkflow().getSelfapproval())) {
					selfapproval = true;
				} else if (order.getLsworkflow() != null
						&& Boolean.FALSE.equals(order.getLsworkflow().getSelfapproval())) {
					Integer wau = order.getWau();
					int usercode = objdir.getLsuserMaster().getUsercode();

					if (((wau != null && wau == usercode
							&& (order.getApproved() != null && order.getApproved() == 2
									? previousworkflowIndex - 1 != currentworkflowIndex
									: previousworkflowIndex + 1 != currentworkflowIndex))
							|| (wau != null && wau != usercode)) || (previousworkflowIndex == -1 || wau == null)) {
						selfapproval = true;
					}
				}
				order.setCanuserprocess(selfapproval);
			} else {
				order.setCanuserprocess(false);
			}

		}

		if (!lstorder.isEmpty() && iscancelled != null && iscancelled && orderflag != null && orderflag.equals("N")) {
			lstorder = lstorder.stream().filter(o -> o.getOrdercancell() != null && o.getOrdercancell() == 1)
					.collect(Collectors.toCollection(ArrayList::new));
		} else if (!lstorder.isEmpty() && orderflag != null && orderflag.equals("N")) {
			lstorder = lstorder.stream().filter(o -> o.getOrdercancell() == null || o.getOrdercancell() != 1)
					.collect(Collectors.toCollection(ArrayList::new));
		}

		if (!lstorder.isEmpty() && objdir.getOrderflag() != null && objdir.getOrderflag().equals("R")
				&& approval == 3) {
			lstorder = lstorder.stream().filter(o -> o.getApprovelstatus() != null && o.getApprovelstatus() == 3)
					.collect(Collectors.toCollection(ArrayList::new));
		} else if (!lstorder.isEmpty() && objdir.getOrderflag() != null && objdir.getOrderflag().equals("R")
				&& approval != 0) {
			lstorder = lstorder.stream().filter(o -> o.getApprovelstatus() == null || o.getApprovelstatus() != 3)
					.collect(Collectors.toCollection(ArrayList::new));
		}

		if (objdir.getSearchCriteria() != null && objdir.getSearchCriteria().getContentsearchtype() != null
				&& objdir.getSearchCriteria().getContentsearch() != null) {

			lstorderstrcarray = GetordersondirectoryFilter(objdir, lstorder);
			return lstorderstrcarray;
		}

		return lstorder;

	}

	public List<LSSheetOrderStructure> Deletedirectories(LSSheetOrderStructure[] directories) {
		List<LSSheetOrderStructure> lstdirectories = Arrays.asList(directories);

		lstdirectories.forEach(structure -> {
			if (structure.getParentdircode() == -2) {
				lsSheetOrderStructureRepository.deleteById(structure.getDirectorycode());
				lslogilablimsorderdetailRepository.updateparentdirectory(structure.getDircodetomove(),
						structure.getDirectorycode());
				lssheetfolderfilesRepository.updatedirectorycode(structure.getDircodetomove(),
						structure.getDirectorycode());
			} else {
				lsSheetOrderStructureRepository.updatedirectory(structure.getParentdircode(), structure.getPath(),
						structure.getDirectorycode(), structure.getDirectoryname(),
						structure.getModifiedby().getUsercode(), structure.getDateModified());
			}
		});

		return lstdirectories;
	}

	public List<LSSheetOrderStructure> Deletemultidirectories(LSSheetOrderStructure[] directories) {
		List<LSSheetOrderStructure> lstdirectories = Arrays.asList(directories);

		lstdirectories.forEach(structure -> {
			if (structure.getParentdircode() == -2) {
				lsSheetOrderStructureRepository.deleteById(structure.getDirectorycode());
				lslogilablimsorderdetailRepository.updateparentdirectory(structure.getDircodetomove(),
						structure.getDirectorycode());
				lssheetfolderfilesRepository.updatedirectorycode(structure.getDircodetomove(),
						structure.getDirectorycode());
			} else {
				lsSheetOrderStructureRepository.updatedirectory(structure.getParentdircode(), structure.getPath(),
						structure.getDirectorycode(), structure.getDirectoryname(),
						structure.getModifiedby().getUsercode(), structure.getDateModified());
			}
		});
		return lstdirectories;
	}

	public LSSheetOrderStructure getMoveDirectory(LSSheetOrderStructure objdir) {
		Response objResponse = new Response();
		LSSheetOrderStructure lstdir = null;
		String dir = objdir.getDirectoryname();
		if (objdir.getDirectorycode() != null) {
			lstdir = lsSheetOrderStructureRepository.findByDirectorycodeAndParentdircodeAndDirectorynameNot(
					objdir.getDirectorycode(), objdir.getParentdircode(), objdir.getDirectoryname());
		} else {
			lstdir = lsSheetOrderStructureRepository.findByParentdircodeAndDirectoryname(objdir.getParentdircode(),
					objdir.getDirectoryname());
		}
		while (lstdir != null) {
			if (dir.charAt(dir.length() - 1) == ')') {
				char temp = dir.charAt(dir.length() - 2);
				int n = Character.getNumericValue(temp);
				n = n + 1;
				dir = dir.substring(0, dir.length() - 2) + n + dir.substring(dir.length() - 1);
				objdir.setDirectoryname(dir);
			} else {
				dir = dir + " (2)";
				objdir.setDirectoryname(dir);
			}
			lstdir = lsSheetOrderStructureRepository.findByParentdircodeAndDirectoryname(objdir.getParentdircode(),
					objdir.getDirectoryname());

		}
		objdir.setResponse(objResponse);
		return objdir;
	}

	public LSSheetOrderStructure Movedirectory(LSSheetOrderStructure directory) {
		lsSheetOrderStructureRepository.updatedirectory(directory.getParentdircode(), directory.getPath(),
				directory.getDirectorycode(), directory.getDirectoryname(), directory.getModifiedby().getUsercode(),
				directory.getDateModified());
		return directory;
	}

	public List<LSsamplefileversion> getlsorderfileversion(LSsamplefile objfile) {
		List<LSsamplefileversion> objList = new ArrayList<LSsamplefileversion>();
		if (objfile.getFilesamplecode() != null) {

			objList = lssamplefileversionRepository.findByFilesamplecodeOrderByVersionnoDesc(objfile);

		}
		return objList;
	}

	public List<LSlogilablimsorderdetail> GetAssignedtoUserorders(LSlogilablimsorderdetail order) {
		return lslogilablimsorderdetailRepository
				.findByOrderflagAndAssignedtoAndLockeduserIsNotNullOrderByBatchcodeDesc("N", order.getLsuserMaster());
	}

	public List<LogilabOrdermastersh> GetLockedOrders(LSlogilablimsorderdetail objorder) {

		if (objorder.getLsuserMaster().getUsername().equalsIgnoreCase("Administrator")) {
			List<LSMultisites> obj = LSMultisitesRepositery
					.findByLssiteMaster(objorder.getLsuserMaster().getLssitemaster());
			List<Integer> usercode = obj.parallelStream().map(LSMultisites::getUsercode).collect(Collectors.toList());
			return logilablimsorderdetailsRepository
					.findByOrderflagAndLockeduserIsNotNullAndLockeduserInAndAssignedtoIsNullOrderByBatchcodeDesc("N",
							usercode);
		} else {
			List<LogilabOrdermastersh> lstorder = new ArrayList<LogilabOrdermastersh>();
			List<Elnmaterial> nmaterialcode = elnmaterialRepository
					.findByNsitecode(objorder.getLsuserMaster().getLssitemaster().getSitecode());
			lstorder = lslogilablimsorderdetailRepository
					.findByOrderflagAndLsprojectmasterInAndAssignedtoIsNullAndLockeduserIsNotNullOrOrderflagAndLsprojectmasterIsNullAndAssignedtoIsNullAndLockeduserIsNotNull(
							"N", objorder.getLstproject(), "N");

			int chunkSize = Integer.parseInt(env.getProperty("lssamplecount"));
			int totalSamples = nmaterialcode.size();
			List<LogilabOrdermastersh> lstorderobj = IntStream.range(0, (totalSamples + chunkSize - 1) / chunkSize)
					.parallel().mapToObj(i -> {
						int startIndex = i * chunkSize;
						int endIndex = Math.min(startIndex + chunkSize, totalSamples);
						List<Elnmaterial> currentChunk = nmaterialcode.subList(startIndex, endIndex);
						List<LogilabOrdermastersh> orderChunk = new ArrayList<>();
						orderChunk.addAll(lslogilablimsorderdetailRepository
								.findByOrderflagAndLsprojectmasterIsNullAndElnmaterialInAndAssignedtoIsNullAndViewoptionAndLockeduserIsNotNull(
										"N", currentChunk, 1));
						orderChunk.addAll(lslogilablimsorderdetailRepository
								.findByOrderflagAndLsprojectmasterIsNullAndElnmaterialInAndAssignedtoIsNullAndViewoptionAndLsuserMasterAndLockeduserIsNotNullOrderByBatchcodeDesc(
										"N", currentChunk, 2, objorder.getLsuserMaster()));
						return orderChunk;
					}).flatMap(List::stream).collect(Collectors.toList());

			lstorderobj.addAll(lstorder);

			List<LogilabOrdermastersh> uniqueList = lstorderobj
					.stream().collect(Collectors.toMap(obj -> obj.getBi() != null ? obj.getBi() : obj.getBc(),
							obj -> obj, (existing, replacement) -> existing))
					.values().stream().collect(Collectors.toList());

			uniqueList.forEach(objorderDetail -> objorderDetail.setLw(objorder.getLstworkflow()));

			return uniqueList;

		}

	}

	public List<LSlogilablimsorderdetail> UnLockOrders(LSlogilablimsorderdetail[] lstOrder) {

		Response objResponse = new Response();
		List<LSlogilablimsorderdetail> lsOrder = Arrays.asList(lstOrder);
		try {
			List<Long> batcode = lsOrder.stream().map(LSlogilablimsorderdetail::getBatchcode)
					.collect(Collectors.toList());
			if (!lsOrder.isEmpty()) {
				logilablimsorderdetailsRepository.updateLockedUser(batcode);
			}

			objResponse.setStatus(true);
			objResponse.setInformation("Success");

		} catch (Exception e) {
			objResponse.setStatus(false);
			objResponse.setInformation("Failure");
		}
		lsOrder.forEach(item -> item.setResponse(objResponse));

		return lsOrder;
	}

	public Map<String, Object> GetSheetorderversions(Map<String, Object> objMap) throws ParseException {

		int filecode = (int) objMap.get("filesamplecode");

		Date currentDate = commonfunction.getCurrentUtcTime();
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		Calendar calto = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		Date fromDate =currentDate;
		Date toDate = currentDate;
		String info = (String) objMap.get("information");
		
		if ("1".equals(info)) {
		    cal.setTime(currentDate);
		} else if ("2".equals(info)) {
		    cal.setTime(currentDate);
		    cal.add(Calendar.DAY_OF_MONTH, -7);
		} else if ("3".equals(info)) {
		    cal.setTime(currentDate);
		    cal.add(Calendar.DAY_OF_MONTH, -30);
		}else {
			fromDate=(Date) objMap.get("fromdate");
			toDate=(Date) objMap.get("todate");
		}
		if(!"4".equals(info)) {
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			fromDate = cal.getTime();
			calto.set(Calendar.HOUR_OF_DAY, 23);
			calto.set(Calendar.MINUTE, 59);
			calto.set(Calendar.SECOND, 59);
			calto.set(Calendar.MILLISECOND, 999);
			toDate = calto.getTime();
		}
		
		
		
		Map<String, Object> objmap = new HashMap<>();
		LSsamplefile objfile = new LSsamplefile();
		objfile.setFilesamplecode(filecode);
		List<LSsamplefileversion> lstfilesamle = lssamplefileversionRepository
				.findByFilesamplecodeAndCreatedateBetweenOrderByVersionnoDesc(objfile,fromDate,toDate);

		objmap.put("lsorderversion", lstfilesamle);
		return objmap;

	}

	public Map<String, Object> Getfoldersforprotocolorders(LSlogilabprotocoldetail objusermaster) {
		List<Long> immutableNegativeValues = Arrays.asList(-3L, -22L);
		Map<String, Object> mapfolders = new HashMap<String, Object>();
		List<Lsprotocolorderstructure> lstdir = new ArrayList<Lsprotocolorderstructure>();
		if (objusermaster.getLstuserMaster() == null) {
			lstdir = lsprotocolorderStructurerepository
					.findBySitemasterAndViewoptionAndDirectorycodeNotInOrCreatedbyAndSitemasterAndViewoptionOrCreatedbyAndSitemasterAndViewoptionOrderByDirectorycode(
							objusermaster.getLsuserMaster().getLssitemaster(), 1, immutableNegativeValues,
							objusermaster.getLsuserMaster(), objusermaster.getLsuserMaster().getLssitemaster(), 2,
							objusermaster.getLsuserMaster(), objusermaster.getLsuserMaster().getLssitemaster(), 3);
		} else {
			lstdir = lsprotocolorderStructurerepository
					.findBySitemasterAndViewoptionAndDirectorycodeNotInOrCreatedbyAndSitemasterAndViewoptionOrSitemasterAndViewoptionAndCreatedbyInOrderByDirectorycode(
							objusermaster.getLsuserMaster().getLssitemaster(), 1, immutableNegativeValues,
							objusermaster.getLsuserMaster(), objusermaster.getLsuserMaster().getLssitemaster(), 2,
							objusermaster.getLsuserMaster().getLssitemaster(), 3, objusermaster.getLstuserMaster());
		}
		lstdir.addAll(lsprotocolorderStructurerepository.findByDirectorycodeIn(immutableNegativeValues));
		if (objusermaster.getLstproject() != null && objusermaster.getLstproject().size() > 0) {
			List<Integer> lsprojectcode = objusermaster.getLstproject().stream().map(LSprojectmaster::getProjectcode)
					.collect(Collectors.toList());
//			List<LSprojectmaster> lstproject = lsprojectmasterRepository.findAll(lsprojectcode);
			ArrayList<List<Object>> lsttest = LSlogilabprotocoldetailRepository
					.getLstestmasterlocalByOrderdisplaytypeAndLsprojectmasterInAndTestcodeIsNotNull(lsprojectcode);
			mapfolders.put("tests", lsttest);
//			mapfolders.put("projects", lstproject);

		} else {
			mapfolders.put("tests", new ArrayList<Logilaborders>());
//			mapfolders.put("projects", new ArrayList<Projectmaster>());
		}
//		List<Elnmaterial> Matireal_List = elnmaterialRepository.findByNsitecodeOrderByNmaterialcodeDesc(
//				objusermaster.getLsuserMaster().getLssitemaster().getSitecode());
//		List<Object> Material_List_Ordes = LSlogilabprotocoldetailRepository
//				.getLstestmasterlocalAndmaterialByOrderdisplaytypeAndLSsamplemasterInAndTestcodeIsNotNull(
//						objusermaster.getLsuserMaster().getLssitemaster().getSitecode());
//		mapfolders.put("Materialtest", Material_List_Ordes);
//		mapfolders.put("Material", Matireal_List);
		mapfolders.put("directory", lstdir);
		return mapfolders;
	}

	public Map<String, Object> Getuserorders(Map<String, LSuserMaster> objusers) {
		Map<String, Object> mapuserorders = new HashMap<String, Object>();

		ObjectMapper mapper = new ObjectMapper();
		LSuserMaster lsloginuser = mapper.convertValue(objusers.get("loginuser"), LSuserMaster.class);
		LSuserMaster lsselecteduser = mapper.convertValue(objusers.get("selecteduser"), LSuserMaster.class);
		Date fromdate = lsselecteduser.getObjuser().getFromdate();
		Date todate = lsselecteduser.getObjuser().getTodate();
		Integer directory = mapper.convertValue(objusers.get("directorycode"), Integer.class);
		Integer filetype = mapper.convertValue(objusers.get("filetype"), Integer.class);
		if (lsloginuser.getUsercode() == lsselecteduser.getUsercode()) {
			if (filetype == -1) {
//				mapuserorders.put("assigned", lslogilablimsorderdetailRepository
//						.findByAssignedtoAndCreatedtimestampBetweenOrderByBatchcodeDesc(lsloginuser, fromdate, todate));
				mapuserorders.put("sharebyme",
						lsordersharedbyRepository
								.findByUsersharedbyAndSharestatusAndSharedonBetweenOrderBySharedbycodeDesc(
										lsselecteduser, 1, fromdate, todate));
				mapuserorders.put("sharetome",
						lsordersharetoRepository
								.findByUsersharedonAndSharestatusAndSharedonBetweenOrderBySharetocodeDesc(
										lsselecteduser, 1, fromdate, todate));
			} else {
//				mapuserorders.put("assigned",
//						lslogilablimsorderdetailRepository
//								.findByAssignedtoAndFiletypeAndCreatedtimestampBetweenOrderByBatchcodeDesc(lsloginuser,
//										filetype, fromdate, todate));
				mapuserorders.put("sharebyme",
						lsordersharedbyRepository
								.findByUsersharedbyAndOrdertypeAndSharestatusAndSharedonBetweenOrderBySharedbycodeDesc(
										lsselecteduser, filetype, 1, fromdate, todate));
				mapuserorders.put("sharetome",
						lsordersharetoRepository
								.findByUsersharedonAndOrdertypeAndSharestatusAndSharedonBetweenOrderBySharetocodeDesc(
										lsselecteduser, filetype, 1, fromdate, todate));
			}
		} else {
			if (filetype == -1) {
				mapuserorders.put("assigned",
						lslogilablimsorderdetailRepository
								.findByAssignedtoAndLsuserMasterAndCreatedtimestampBetweenOrderByBatchcodeDesc(
										lsselecteduser, lsloginuser, fromdate, todate));
				mapuserorders.put("sharebyme", lsordersharedbyRepository
						.findByUsersharedbyAndUsersharedonAndSharestatusAndSharedonBetweenOrderBySharedbycodeDesc(
								lsloginuser, lsselecteduser, 1, fromdate, todate));
			} else {
				mapuserorders.put("assigned", lslogilablimsorderdetailRepository
						.findByAssignedtoAndLsuserMasterAndFiletypeAndCreatedtimestampBetweenOrderByBatchcodeDesc(
								lsselecteduser, lsloginuser, filetype, fromdate, todate));
				mapuserorders.put("sharebyme", lsordersharedbyRepository
						.findByUsersharedbyAndUsersharedonAndOrdertypeAndSharestatusAndSharedonBetweenOrderBySharedbycodeDesc(
								lsloginuser, lsselecteduser, filetype, 1, fromdate, todate));
			}
		}

		mapuserorders.put("directorycode", directory);

		return mapuserorders;
	}

	public Lsprotocolorderstructure Insertdirectoryonprotocol(Lsprotocolorderstructure objdir) {

		Response objResponse = new Response();
		Lsprotocolorderstructure lstdir = null;
		if (objdir.getDirectorycode() != null) {
			lstdir = lsprotocolorderStructurerepository
					.findByDirectorycodeAndParentdircodeAndDirectorynameNotAndSitemaster(objdir.getDirectorycode(),
							objdir.getParentdircode(), objdir.getDirectoryname(), objdir.getSitemaster());
		} else {
			lstdir = lsprotocolorderStructurerepository.findByParentdircodeAndDirectorynameIgnoreCaseAndSitemaster(
					objdir.getParentdircode(), objdir.getDirectoryname(), objdir.getSitemaster());
		}
		if (lstdir != null) {
			objResponse.setStatus(false);
			objResponse.setInformation("IDS_FolderExist");
		} else {
			objResponse.setStatus(true);
			objResponse.setInformation("IDS_FolderAdded");
		}
		objdir.setResponse(objResponse);
		return objdir;

	}

	public Lsprotocolorderstructure Insertnewdirectoryonprotocol(Lsprotocolorderstructure objdir)
			throws ParseException {

		lsprotocolorderStructurerepository.save(objdir);
		if (objdir.getParentdircode() != null) {
			List<Lsprotocolorderstructure> structureobj = lsprotocolorderStructurerepository
					.findByDirectorycode(objdir.getParentdircode());
			if (!structureobj.isEmpty()) {
				structureobj.get(0).setDateModified(commonfunction.getCurrentUtcTime());
				structureobj.get(0).setModifiedby(objdir.getCreatedby());
				lsprotocolorderStructurerepository.saveAll(structureobj);
			}
		}
		return objdir;

	}

	public List<Lsprotocolorderstructure> Deletedirectoriesonprotocol(Lsprotocolorderstructure[] directories) {

		List<Lsprotocolorderstructure> lstdirectories = Arrays.asList(directories);

		lstdirectories.forEach(structure -> {
			if (structure.getParentdircode() == -2) {
				lsprotocolorderStructurerepository.deleteById(structure.getDirectorycode());
				LSlogilabprotocoldetailRepository.updateparentdirectory(structure.getDircodetomove(),
						structure.getDirectorycode());
				lsprotocolfolderfilesRepository.updatedirectorycode(structure.getDircodetomove(),
						structure.getDirectorycode());
			} else {
				lsprotocolorderStructurerepository.updatedirectory(structure.getParentdircode(), structure.getPath(),
						structure.getDirectorycode(), structure.getDirectoryname(),
						structure.getModifiedby().getUsercode(), structure.getDateModified());
			}
		});

		return lstdirectories;

	}

	public Lsprotocolorderstructure Movedirectoryonprotocolorder(Lsprotocolorderstructure directory) {
		lsprotocolorderStructurerepository.updatedirectory(directory.getParentdircode(), directory.getPath(),
				directory.getDirectorycode(), directory.getDirectoryname(), directory.getModifiedby().getUsercode(),
				directory.getDateModified());
		return directory;
	}

	public Lsprotocolorderstructure getMoveDirectoryonprotocolorder(Lsprotocolorderstructure objdir) {

		Response objResponse = new Response();
		Lsprotocolorderstructure lstdir = null;
		String dir = objdir.getDirectoryname();
		if (objdir.getDirectorycode() != null) {
			lstdir = lsprotocolorderStructurerepository.findByDirectorycodeAndParentdircodeAndDirectorynameNot(
					objdir.getDirectorycode(), objdir.getParentdircode(), objdir.getDirectoryname());
		} else {
			lstdir = lsprotocolorderStructurerepository.findByParentdircodeAndDirectoryname(objdir.getParentdircode(),
					objdir.getDirectoryname());
		}
		while (lstdir != null) {
			if (dir.charAt(dir.length() - 1) == ')') {
				char temp = dir.charAt(dir.length() - 2);
				int n = Character.getNumericValue(temp);
				n = n + 1;
				dir = dir.substring(0, dir.length() - 2) + n + dir.substring(dir.length() - 1);
				objdir.setDirectoryname(dir);
			} else {
				dir = dir + " (2)";
				objdir.setDirectoryname(dir);
			}
			lstdir = lsprotocolorderStructurerepository.findByParentdircodeAndDirectoryname(objdir.getParentdircode(),
					objdir.getDirectoryname());

		}
		objdir.setResponse(objResponse);
		return objdir;

	}

	public List<LSlogilabprotocoldetail> UpdateFolderforprotocolorders(LSlogilabprotocoldetail[] orderary) {

		List<LSlogilabprotocoldetail> order = Arrays.asList(orderary);
		if (order.size() > 0) {
			List<Long> lstorders = order.stream().map(LSlogilabprotocoldetail::getProtocolordercode)
					.collect(Collectors.toList());
			LSlogilabprotocoldetailRepository.updatedirectory(order.get(0).getDirectorycode(), lstorders);
		}
		return order;

	}

	public Map<String, Object> Getprotocolordersondirectory(Lsprotocolorderstructure objdir) {

		Map<String, Object> retuobjts = new HashMap<>();
		Date fromdate = objdir.getObjuser().getFromdate();
		Date todate = objdir.getObjuser().getTodate();
		Integer protocoltype = objdir.getProtocoltype();
		List<LSlogilabprotocoldetail> retuobj = new ArrayList<LSlogilabprotocoldetail>();
		Boolean iscancelled = objdir.getIscancelled();
		Integer rejected = objdir.getRejected() == null ? 0 : objdir.getRejected();
		List<LSuserteammapping> lstteammap = lsuserteammappingRepository.findBylsuserMaster(objdir.getLsuserMaster());
		List<LSusersteam> lstteam = lsusersteamRepository.findByLsuserteammappingInAndLssitemaster(lstteammap,
				objdir.getLsuserMaster().getLssitemaster());

		List<LSprotocolselectedteam> selectedteamorders = lsprotoselectedteamRepo
				.findByUserteamInAndCreatedtimestampBetween(lstteam, fromdate, todate);

		List<LSprojectmaster> lstproject = lsprojectmasterRepository.findByLsusersteamIn(lstteam);

		List<Long> selectedteamprotcolorderList = (selectedteamorders != null && !selectedteamorders.isEmpty())
				? selectedteamorders.stream().map(LSprotocolselectedteam::getProtocolordercode).filter(Objects::nonNull)
						.distinct().collect(Collectors.toList())
				: Collections.singletonList(-1L);

		LSprojectmaster project = objdir.getLsprojectmaster();
		Integer testcode = objdir.getTestcode();

		if (protocoltype == -1 && objdir.getOrderflag() == null && objdir.getLsprojectmaster() == null
				&& objdir.getTestcode() == null) {

			System.out.print(objdir);
			if ((objdir.getLstuserMaster() == null) || objdir.getLstuserMaster().length == 0) {

//				retuobj = LSlogilabprotocoldetailRepository
//				.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrderByProtocolordercodeDesc(
//						objdir.getDirectorycode(), 1, fromdate, todate, objdir.getSitemaster().getSitecode(),
//						objdir.getDirectorycode(), 2, objdir.getCreatedby(), fromdate, todate,
//						objdir.getSitemaster().getSitecode(), objdir.getDirectorycode(), 3,
//						objdir.getCreatedby(), fromdate, todate, objdir.getSitemaster().getSitecode(), false,
//						true, selectedteamprotcolorderList, objdir.getDirectorycode(), 3, objdir.getCreatedby(),
//						fromdate, todate, objdir.getSitemaster().getSitecode());

				retuobj = LSlogilabprotocoldetailRepository
						.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndLsprojectmasterInOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndLsprojectmasterIsNullOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrderByProtocolordercodeDesc(
								objdir.getDirectorycode(), 1, fromdate, todate, objdir.getSitemaster().getSitecode(),
								lstproject, objdir.getDirectorycode(), 1, fromdate, todate,
								objdir.getSitemaster().getSitecode(), objdir.getDirectorycode(), 2,
								objdir.getCreatedby(), fromdate, todate, objdir.getSitemaster().getSitecode(),
								lstproject, objdir.getDirectorycode(), 2, objdir.getCreatedby(), fromdate, todate,
								objdir.getSitemaster().getSitecode(), objdir.getDirectorycode(), 3,
								objdir.getCreatedby(), fromdate, todate, objdir.getSitemaster().getSitecode(), false,
								lstproject, objdir.getDirectorycode(), 3, objdir.getCreatedby(), fromdate, todate,
								objdir.getSitemaster().getSitecode(), false, true, selectedteamprotcolorderList,
								objdir.getDirectorycode(), 3, objdir.getCreatedby(), fromdate, todate,
								objdir.getSitemaster().getSitecode());

			} else {

//		retuobj = LSlogilabprotocoldetailRepository
//				.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndSitecodeOrderByProtocolordercodeDesc(
//						objdir.getDirectorycode(), 1, fromdate, todate, objdir.getSitemaster().getSitecode(),
//						objdir.getDirectorycode(), 2, objdir.getCreatedby(), fromdate, todate,
//						objdir.getSitemaster().getSitecode(), objdir.getDirectorycode(), 3, fromdate, todate,
//						objdir.getLstuserMaster(), objdir.getSitemaster().getSitecode(), false, true,
//						selectedteamprotcolorderList, objdir.getDirectorycode(), 3, fromdate, todate,
//						objdir.getLstuserMaster(), objdir.getSitemaster().getSitecode());

				retuobj = LSlogilabprotocoldetailRepository
						.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndLsprojectmasterInOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndLsprojectmasterIsNullOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndSitecodeOrderByProtocolordercodeDesc(
								objdir.getDirectorycode(), 1, fromdate, todate, objdir.getSitemaster().getSitecode(),
								lstproject, objdir.getDirectorycode(), 1, fromdate, todate,
								objdir.getSitemaster().getSitecode(), objdir.getDirectorycode(), 2,
								objdir.getCreatedby(), fromdate, todate, objdir.getSitemaster().getSitecode(),
								lstproject, objdir.getDirectorycode(), 2, objdir.getCreatedby(), fromdate, todate,
								objdir.getSitemaster().getSitecode(), objdir.getDirectorycode(), 3, fromdate, todate,
								objdir.getLstuserMaster(), objdir.getSitemaster().getSitecode(), false, lstproject,
								objdir.getDirectorycode(), 3, fromdate, todate, objdir.getLstuserMaster(),
								objdir.getSitemaster().getSitecode(), false, true, selectedteamprotcolorderList,
								objdir.getDirectorycode(), 3, fromdate, todate, objdir.getLstuserMaster(),
								objdir.getSitemaster().getSitecode());
			}

		} else if (protocoltype == -1 && objdir.getOrderflag() == null && objdir.getTestcode() == null
				&& objdir.getLsprojectmaster() != null) {
			System.out.print(objdir);
			if ((objdir.getLstuserMaster() == null) || objdir.getLstuserMaster().length == 0) {

				retuobj = LSlogilabprotocoldetailRepository
						.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrderByProtocolordercodeDesc(
								objdir.getDirectorycode(), 1, fromdate, todate, objdir.getSitemaster().getSitecode(),
								objdir.getLsprojectmaster(), objdir.getDirectorycode(), 2, objdir.getCreatedby(),
								fromdate, todate, objdir.getSitemaster().getSitecode(), objdir.getLsprojectmaster(),
								objdir.getDirectorycode(), 3, objdir.getCreatedby(), fromdate, todate,
								objdir.getSitemaster().getSitecode(), false, objdir.getLsprojectmaster(), true,
								selectedteamprotcolorderList, objdir.getDirectorycode(), 3, objdir.getCreatedby(),
								fromdate, todate, objdir.getSitemaster().getSitecode(), objdir.getLsprojectmaster());

			} else {

				retuobj = LSlogilabprotocoldetailRepository
						.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndLsprojectmasterOrderByProtocolordercodeDesc(
								objdir.getDirectorycode(), 1, fromdate, todate, objdir.getSitemaster().getSitecode(),
								objdir.getLsprojectmaster(), objdir.getDirectorycode(), 2, objdir.getCreatedby(),
								fromdate, todate, objdir.getSitemaster().getSitecode(), objdir.getLsprojectmaster(),
								objdir.getDirectorycode(), 3, fromdate, todate, objdir.getLstuserMaster(),
								objdir.getSitemaster().getSitecode(), false, objdir.getLsprojectmaster(), true,
								selectedteamprotcolorderList, objdir.getDirectorycode(), 3, fromdate, todate,
								objdir.getLstuserMaster(), objdir.getSitemaster().getSitecode(),
								objdir.getLsprojectmaster());
			}

		} else if (protocoltype == -1 && objdir.getOrderflag() == null && objdir.getTestcode() != null
				&& objdir.getLsprojectmaster() == null) {
			System.out.print(objdir);
			if ((objdir.getLstuserMaster() == null) || objdir.getLstuserMaster().length == 0) {

				retuobj = LSlogilabprotocoldetailRepository
						.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndTestcodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndTestcodeAndLsprojectmasterIsNullOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrderByProtocolordercodeDesc(
								objdir.getDirectorycode(), 1, fromdate, todate, objdir.getSitemaster().getSitecode(),
								objdir.getTestcode(), lstproject, objdir.getDirectorycode(), 1, fromdate, todate,
								objdir.getSitemaster().getSitecode(), objdir.getTestcode(), objdir.getDirectorycode(),
								2, objdir.getCreatedby(), fromdate, todate, objdir.getSitemaster().getSitecode(),
								objdir.getTestcode(), lstproject, objdir.getDirectorycode(), 2, objdir.getCreatedby(),
								fromdate, todate, objdir.getSitemaster().getSitecode(), objdir.getTestcode(),
								objdir.getDirectorycode(), 3, objdir.getCreatedby(), fromdate, todate,
								objdir.getSitemaster().getSitecode(), false, objdir.getTestcode(), lstproject,
								objdir.getDirectorycode(), 3, objdir.getCreatedby(), fromdate, todate,
								objdir.getSitemaster().getSitecode(), false, objdir.getTestcode(), true,
								selectedteamprotcolorderList, objdir.getDirectorycode(), 3, objdir.getCreatedby(),
								fromdate, todate, objdir.getSitemaster().getSitecode(), objdir.getTestcode());

			} else {

				retuobj = LSlogilabprotocoldetailRepository
						.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndTestcodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndTestcodeAndLsprojectmasterIsNullOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTestcodeOrderByProtocolordercodeDesc(
								objdir.getDirectorycode(), 1, fromdate, todate, objdir.getSitemaster().getSitecode(),
								objdir.getTestcode(), lstproject, objdir.getDirectorycode(), 1, fromdate, todate,
								objdir.getSitemaster().getSitecode(), objdir.getTestcode(), objdir.getDirectorycode(),
								2, objdir.getCreatedby(), fromdate, todate, objdir.getSitemaster().getSitecode(),
								objdir.getTestcode(), lstproject, objdir.getDirectorycode(), 2, objdir.getCreatedby(),
								fromdate, todate, objdir.getSitemaster().getSitecode(), objdir.getTestcode(),
								objdir.getDirectorycode(), 3, fromdate, todate, objdir.getLstuserMaster(),
								objdir.getSitemaster().getSitecode(), false, objdir.getTestcode(), lstproject,
								objdir.getDirectorycode(), 3, fromdate, todate, objdir.getLstuserMaster(),
								objdir.getSitemaster().getSitecode(), false, objdir.getTestcode(), true,
								selectedteamprotcolorderList, objdir.getDirectorycode(), 3, fromdate, todate,
								objdir.getLstuserMaster(), objdir.getSitemaster().getSitecode(), objdir.getTestcode());
			}

		} else if (protocoltype == -1 && objdir.getOrderflag() == null && objdir.getTestcode() != null
				&& objdir.getLsprojectmaster() != null) {
			System.out.print(objdir);
			if ((objdir.getLstuserMaster() == null) || objdir.getLstuserMaster().length == 0) {

				retuobj = LSlogilabprotocoldetailRepository
						.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndTestcodeAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrderByProtocolordercodeDesc(
								objdir.getDirectorycode(), 1, fromdate, todate, objdir.getSitemaster().getSitecode(),
								objdir.getTestcode(), objdir.getLsprojectmaster(), objdir.getDirectorycode(), 2,
								objdir.getCreatedby(), fromdate, todate, objdir.getSitemaster().getSitecode(),
								objdir.getTestcode(), objdir.getLsprojectmaster(), objdir.getDirectorycode(), 3,
								objdir.getCreatedby(), fromdate, todate, objdir.getSitemaster().getSitecode(), false,
								objdir.getTestcode(), objdir.getLsprojectmaster(), true, selectedteamprotcolorderList,
								objdir.getDirectorycode(), 3, objdir.getCreatedby(), fromdate, todate,
								objdir.getSitemaster().getSitecode(), objdir.getTestcode(),
								objdir.getLsprojectmaster());

			} else {

				retuobj = LSlogilabprotocoldetailRepository
						.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndTestcodeAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTestcodeAndLsprojectmasterOrderByProtocolordercodeDesc(
								objdir.getDirectorycode(), 1, fromdate, todate, objdir.getSitemaster().getSitecode(),
								objdir.getTestcode(), objdir.getLsprojectmaster(), objdir.getDirectorycode(), 2,
								objdir.getCreatedby(), fromdate, todate, objdir.getSitemaster().getSitecode(),
								objdir.getTestcode(), objdir.getLsprojectmaster(), objdir.getDirectorycode(), 3,
								fromdate, todate, objdir.getLstuserMaster(), objdir.getSitemaster().getSitecode(),
								false, objdir.getTestcode(), objdir.getLsprojectmaster(), true,
								selectedteamprotcolorderList, objdir.getDirectorycode(), 3, fromdate, todate,
								objdir.getLstuserMaster(), objdir.getSitemaster().getSitecode(), objdir.getTestcode(),
								objdir.getLsprojectmaster());
			}

		}

		else if (protocoltype != -1 && objdir.getOrderflag() != null && objdir.getTestcode() == null
				&& objdir.getLsprojectmaster() == null) {
			if (objdir.getLstuserMaster().length == 0) {
				if (objdir.getRejected() != null) {

					retuobj = logilabprotocoldetailrepository
							.findByDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndLsprojectmasterInOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndLsprojectmasterIsNullOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrderByProtocolordercodeDesc(
									objdir.getDirectorycode(), 1, protocoltype, objdir.getOrderflag(), 1, fromdate,
									todate, objdir.getSitemaster().getSitecode(), lstproject, objdir.getDirectorycode(),
									1, protocoltype, objdir.getOrderflag(), 1, fromdate, todate,
									objdir.getSitemaster().getSitecode(), objdir.getDirectorycode(), 2, protocoltype,
									objdir.getOrderflag(), 1, objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), lstproject, objdir.getDirectorycode(), 2,
									protocoltype, objdir.getOrderflag(), 1, objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), objdir.getDirectorycode(), 3, protocoltype,
									objdir.getOrderflag(), 1, objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), false, lstproject, objdir.getDirectorycode(),
									3, protocoltype, objdir.getOrderflag(), 1, objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), false, true, selectedteamprotcolorderList,
									objdir.getDirectorycode(), 3, protocoltype, objdir.getOrderflag(), 1,
									objdir.getCreatedby(), fromdate, todate, objdir.getSitemaster().getSitecode());

				} else {

					retuobj = logilabprotocoldetailrepository
							.findByDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndLsprojectmasterInOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndLsprojectmasterIsNullOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrderByProtocolordercodeDesc(
									objdir.getDirectorycode(), 1, protocoltype, objdir.getOrderflag(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), lstproject, objdir.getDirectorycode(), 1,
									protocoltype, objdir.getOrderflag(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), objdir.getDirectorycode(), 2, protocoltype,
									objdir.getOrderflag(), objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), lstproject, objdir.getDirectorycode(), 2,
									protocoltype, objdir.getOrderflag(), objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), objdir.getDirectorycode(), 3, protocoltype,
									objdir.getOrderflag(), objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), false, lstproject, objdir.getDirectorycode(),
									3, protocoltype, objdir.getOrderflag(), objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), false, true, selectedteamprotcolorderList,
									objdir.getDirectorycode(), 3, protocoltype, objdir.getOrderflag(),
									objdir.getCreatedby(), fromdate, todate, objdir.getSitemaster().getSitecode());

				}
			} else {
				if (objdir.getRejected() != null) {

					retuobj = LSlogilabprotocoldetailRepository
							.findByDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenAndCreatebyInAndSitecodeOrderByProtocolordercodeDesc(
									objdir.getDirectorycode(), 1, protocoltype, objdir.getOrderflag(), 1, fromdate,
									todate, objdir.getSitemaster().getSitecode(), objdir.getDirectorycode(), 2,
									protocoltype, objdir.getOrderflag(), 1, objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), objdir.getDirectorycode(), 3, protocoltype,
									objdir.getOrderflag(), 1, fromdate, todate, objdir.getLstuserMaster(),
									objdir.getSitemaster().getSitecode(), false, true, selectedteamprotcolorderList,
									objdir.getDirectorycode(), 3, protocoltype, objdir.getOrderflag(), 1, fromdate,
									todate, objdir.getLstuserMaster(), objdir.getSitemaster().getSitecode());
				} else {

					retuobj = LSlogilabprotocoldetailRepository
							.findByDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenAndCreatebyInAndSitecodeOrderByProtocolordercodeDesc(
									objdir.getDirectorycode(), 1, protocoltype, objdir.getOrderflag(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), objdir.getDirectorycode(), 2, protocoltype,
									objdir.getOrderflag(), objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), objdir.getDirectorycode(), 3, protocoltype,
									objdir.getOrderflag(), fromdate, todate, objdir.getLstuserMaster(),
									objdir.getSitemaster().getSitecode(), false, true, selectedteamprotcolorderList,
									objdir.getDirectorycode(), 3, protocoltype, objdir.getOrderflag(), fromdate, todate,
									objdir.getLstuserMaster(), objdir.getSitemaster().getSitecode());
				}
			}
		} else if (protocoltype != -1 && objdir.getOrderflag() != null && objdir.getTestcode() == null
				&& objdir.getLsprojectmaster() != null) {
			if (objdir.getLstuserMaster().length == 0) {
				if (objdir.getRejected() != null) {

					retuobj = LSlogilabprotocoldetailRepository
							.findByDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrderByProtocolordercodeDesc(
									objdir.getDirectorycode(), 1, protocoltype, objdir.getOrderflag(), 1, fromdate,
									todate, objdir.getSitemaster().getSitecode(), project, objdir.getDirectorycode(), 2,
									protocoltype, objdir.getOrderflag(), 1, objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), project, objdir.getDirectorycode(), 3,
									protocoltype, objdir.getOrderflag(), 1, objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), false, project, true,
									selectedteamprotcolorderList, objdir.getDirectorycode(), 3, protocoltype,
									objdir.getOrderflag(), 1, objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), project);
				} else {

					retuobj = LSlogilabprotocoldetailRepository
							.findByDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrderByProtocolordercodeDesc(
									objdir.getDirectorycode(), 1, protocoltype, objdir.getOrderflag(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), project, objdir.getDirectorycode(), 2,
									protocoltype, objdir.getOrderflag(), objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), project, objdir.getDirectorycode(), 3,
									protocoltype, objdir.getOrderflag(), objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), false, project, true,
									selectedteamprotcolorderList, objdir.getDirectorycode(), 3, protocoltype,
									objdir.getOrderflag(), objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), project);
				}
			} else {
				if (objdir.getRejected() != null) {

					retuobj = LSlogilabprotocoldetailRepository
							.findByDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndLsprojectmasterOrderByProtocolordercodeDesc(
									objdir.getDirectorycode(), 1, protocoltype, objdir.getOrderflag(), 1, fromdate,
									todate, objdir.getSitemaster().getSitecode(), project, objdir.getDirectorycode(), 2,
									protocoltype, objdir.getOrderflag(), 1, objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), project, objdir.getDirectorycode(), 3,
									protocoltype, objdir.getOrderflag(), 1, fromdate, todate, objdir.getLstuserMaster(),
									objdir.getSitemaster().getSitecode(), false, project, true,
									selectedteamprotcolorderList, objdir.getDirectorycode(), 3, protocoltype,
									objdir.getOrderflag(), 1, fromdate, todate, objdir.getLstuserMaster(),
									objdir.getSitemaster().getSitecode(), project);
				} else {

					retuobj = LSlogilabprotocoldetailRepository
							.findByDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndLsprojectmasterOrderByProtocolordercodeDesc(
									objdir.getDirectorycode(), 1, protocoltype, objdir.getOrderflag(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), project, objdir.getDirectorycode(), 2,
									protocoltype, objdir.getOrderflag(), objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), project, objdir.getDirectorycode(), 3,
									protocoltype, objdir.getOrderflag(), fromdate, todate, objdir.getLstuserMaster(),
									objdir.getSitemaster().getSitecode(), false, project, true,
									selectedteamprotcolorderList, objdir.getDirectorycode(), 3, protocoltype,
									objdir.getOrderflag(), fromdate, todate, objdir.getLstuserMaster(),
									objdir.getSitemaster().getSitecode(), project);
				}
			}
		}

		else if (protocoltype != -1 && objdir.getOrderflag() != null && objdir.getTestcode() != null
				&& objdir.getLsprojectmaster() != null) {
			if (objdir.getLstuserMaster().length == 0) {
				if (objdir.getRejected() != null) {

					retuobj = logilabprotocoldetailrepository
							.findByDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndTestcodeAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrderByProtocolordercodeDesc(
									objdir.getDirectorycode(), 1, protocoltype, objdir.getOrderflag(), 1, fromdate,
									todate, objdir.getSitemaster().getSitecode(), testcode, project,
									objdir.getDirectorycode(), 2, protocoltype, objdir.getOrderflag(), 1,
									objdir.getCreatedby(), fromdate, todate, objdir.getSitemaster().getSitecode(),
									testcode, project, objdir.getDirectorycode(), 3, protocoltype,
									objdir.getOrderflag(), 1, objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), false, testcode, project, true,
									selectedteamprotcolorderList, objdir.getDirectorycode(), 3, protocoltype,
									objdir.getOrderflag(), 1, objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), testcode, project);
				} else {

					retuobj = logilabprotocoldetailrepository
							.findByDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndTestcodeAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrderByProtocolordercodeDesc(
									objdir.getDirectorycode(), 1, protocoltype, objdir.getOrderflag(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), testcode, project, objdir.getDirectorycode(),
									2, protocoltype, objdir.getOrderflag(), objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), testcode, project, objdir.getDirectorycode(),
									3, protocoltype, objdir.getOrderflag(), objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), false, testcode, project, true,
									selectedteamprotcolorderList, objdir.getDirectorycode(), 3, protocoltype,
									objdir.getOrderflag(), objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), testcode, project);
				}
			} else {
				if (objdir.getRejected() != null) {

					retuobj = logilabprotocoldetailrepository
							.findByDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndTestcodeAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTestcodeAndLsprojectmasterOrderByProtocolordercodeDesc(
									objdir.getDirectorycode(), 1, protocoltype, objdir.getOrderflag(), 1, fromdate,
									todate, objdir.getSitemaster().getSitecode(), testcode, project,
									objdir.getDirectorycode(), 2, protocoltype, objdir.getOrderflag(), 1,
									objdir.getCreatedby(), fromdate, todate, objdir.getSitemaster().getSitecode(),
									testcode, project, objdir.getDirectorycode(), 3, protocoltype,
									objdir.getOrderflag(), 1, fromdate, todate, objdir.getLstuserMaster(),
									objdir.getSitemaster().getSitecode(), false, testcode, project, true,
									selectedteamprotcolorderList, objdir.getDirectorycode(), 3, protocoltype,
									objdir.getOrderflag(), 1, fromdate, todate, objdir.getLstuserMaster(),
									objdir.getSitemaster().getSitecode(), testcode, project);
				} else {

					retuobj = logilabprotocoldetailrepository
							.findByDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndTestcodeAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTestcodeAndLsprojectmasterOrderByProtocolordercodeDesc(
									objdir.getDirectorycode(), 1, protocoltype, objdir.getOrderflag(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), testcode, project, objdir.getDirectorycode(),
									2, protocoltype, objdir.getOrderflag(), objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), testcode, project, objdir.getDirectorycode(),
									3, protocoltype, objdir.getOrderflag(), fromdate, todate, objdir.getLstuserMaster(),
									objdir.getSitemaster().getSitecode(), false, testcode, project, true,
									selectedteamprotcolorderList, objdir.getDirectorycode(), 3, protocoltype,
									objdir.getOrderflag(), fromdate, todate, objdir.getLstuserMaster(),
									objdir.getSitemaster().getSitecode(), testcode, project);
				}
			}
		}

		else if (protocoltype != -1 && objdir.getOrderflag() != null && objdir.getTestcode() != null
				&& objdir.getLsprojectmaster() == null) {
			if (objdir.getLstuserMaster().length == 0) {
				if (objdir.getRejected() != null) {

					retuobj = logilabprotocoldetailrepository
							.findByDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndTestcodeOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrderByProtocolordercodeDesc(
									objdir.getDirectorycode(), 1, protocoltype, objdir.getOrderflag(), 1, fromdate,
									todate, objdir.getSitemaster().getSitecode(), testcode, objdir.getDirectorycode(),
									2, protocoltype, objdir.getOrderflag(), 1, objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), testcode, objdir.getDirectorycode(), 3,
									protocoltype, objdir.getOrderflag(), 1, objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), false, testcode, true,
									selectedteamprotcolorderList, objdir.getDirectorycode(), 3, protocoltype,
									objdir.getOrderflag(), 1, objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), testcode);
				} else {

					retuobj = logilabprotocoldetailrepository
							.findByDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndTestcodeOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrderByProtocolordercodeDesc(
									objdir.getDirectorycode(), 1, protocoltype, objdir.getOrderflag(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), testcode, objdir.getDirectorycode(), 2,
									protocoltype, objdir.getOrderflag(), objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), testcode, objdir.getDirectorycode(), 3,
									protocoltype, objdir.getOrderflag(), objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), false, testcode, true,
									selectedteamprotcolorderList, objdir.getDirectorycode(), 3, protocoltype,
									objdir.getOrderflag(), objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), testcode);
				}
			} else {
				if (objdir.getRejected() != null) {

					retuobj = logilabprotocoldetailrepository
							.findByDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndTestcodeOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTestcodeOrderByProtocolordercodeDesc(
									objdir.getDirectorycode(), 1, protocoltype, objdir.getOrderflag(), 1, fromdate,
									todate, objdir.getSitemaster().getSitecode(), testcode, objdir.getDirectorycode(),
									2, protocoltype, objdir.getOrderflag(), 1, objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), testcode, objdir.getDirectorycode(), 3,
									protocoltype, objdir.getOrderflag(), 1, fromdate, todate, objdir.getLstuserMaster(),
									objdir.getSitemaster().getSitecode(), false, testcode, true,
									selectedteamprotcolorderList, objdir.getDirectorycode(), 3, protocoltype,
									objdir.getOrderflag(), 1, fromdate, todate, objdir.getLstuserMaster(),
									objdir.getSitemaster().getSitecode(), testcode);
				} else {

					retuobj = logilabprotocoldetailrepository
							.findByDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndTestcodeOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTestcodeOrderByProtocolordercodeDesc(
									objdir.getDirectorycode(), 1, protocoltype, objdir.getOrderflag(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), testcode, objdir.getDirectorycode(), 2,
									protocoltype, objdir.getOrderflag(), objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), testcode, objdir.getDirectorycode(), 3,
									protocoltype, objdir.getOrderflag(), fromdate, todate, objdir.getLstuserMaster(),
									objdir.getSitemaster().getSitecode(), false, testcode, true,
									selectedteamprotcolorderList, objdir.getDirectorycode(), 3, protocoltype,
									objdir.getOrderflag(), fromdate, todate, objdir.getLstuserMaster(),
									objdir.getSitemaster().getSitecode(), testcode);
				}
			}
		}

		else if (protocoltype == -1 && objdir.getOrderflag() != null && objdir.getTestcode() == null
				&& objdir.getLsprojectmaster() == null) {
			if (objdir.getLstuserMaster().length == 0) {
				if (objdir.getRejected() != null) {

					retuobj = logilabprotocoldetailrepository
							.findByDirectorycodeAndViewoptionAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndLsprojectmasterInOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndLsprojectmasterIsNullOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrderByProtocolordercodeDesc(
									objdir.getDirectorycode(), 1, objdir.getOrderflag(), 1, fromdate, todate,
									objdir.getSitemaster().getSitecode(), lstproject, objdir.getDirectorycode(), 1,
									objdir.getOrderflag(), 1, fromdate, todate, objdir.getSitemaster().getSitecode(),
									objdir.getDirectorycode(), 2, objdir.getOrderflag(), 1, objdir.getCreatedby(),
									fromdate, todate, objdir.getSitemaster().getSitecode(), lstproject,
									objdir.getDirectorycode(), 2, objdir.getOrderflag(), 1, objdir.getCreatedby(),
									fromdate, todate, objdir.getSitemaster().getSitecode(), objdir.getDirectorycode(),
									3, objdir.getOrderflag(), 1, objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), false, lstproject, objdir.getDirectorycode(),
									3, objdir.getOrderflag(), 1, objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), false, true, selectedteamprotcolorderList,
									objdir.getDirectorycode(), 3, objdir.getOrderflag(), 1, objdir.getCreatedby(),
									fromdate, todate, objdir.getSitemaster().getSitecode());

				} else {

					retuobj = logilabprotocoldetailrepository
							.findByDirectorycodeAndViewoptionAndOrderflagAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndOrderflagAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterInOrDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterIsNullOrDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndLsprojectmasterInOrDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndLsprojectmasterIsNullOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrderByProtocolordercodeDesc(
									objdir.getDirectorycode(), 1, objdir.getOrderflag(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), lstproject, objdir.getDirectorycode(), 1,
									objdir.getOrderflag(), fromdate, todate, objdir.getSitemaster().getSitecode(),
									objdir.getDirectorycode(), 2, objdir.getOrderflag(), objdir.getCreatedby(),
									fromdate, todate, objdir.getSitemaster().getSitecode(), lstproject,
									objdir.getDirectorycode(), 2, objdir.getOrderflag(), objdir.getCreatedby(),
									fromdate, todate, objdir.getSitemaster().getSitecode(), objdir.getDirectorycode(),
									3, objdir.getOrderflag(), objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), false, lstproject, objdir.getDirectorycode(),
									3, objdir.getOrderflag(), objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), false, true, selectedteamprotcolorderList,
									objdir.getDirectorycode(), 3, objdir.getOrderflag(), objdir.getCreatedby(),
									fromdate, todate, objdir.getSitemaster().getSitecode());

				}
			} else {
				if (objdir.getRejected() != null) {

					retuobj = logilabprotocoldetailrepository
							.findByDirectorycodeAndViewoptionAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndOrderflagAndRejectedAndCreatedtimestampBetweenAndCreatebyInAndSitecodeOrderByProtocolordercodeDesc(
									objdir.getDirectorycode(), 1, objdir.getOrderflag(), 1, fromdate, todate,
									objdir.getSitemaster().getSitecode(), objdir.getDirectorycode(), 2,
									objdir.getOrderflag(), 1, objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), objdir.getDirectorycode(), 3,
									objdir.getOrderflag(), 1, fromdate, todate, objdir.getLstuserMaster(),
									objdir.getSitemaster().getSitecode(), false, true, selectedteamprotcolorderList,
									objdir.getDirectorycode(), 3, objdir.getOrderflag(), 1, fromdate, todate,
									objdir.getLstuserMaster(), objdir.getSitemaster().getSitecode());
				} else {

					retuobj = logilabprotocoldetailrepository
							.findByDirectorycodeAndViewoptionAndOrderflagAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndOrderflagAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndOrderflagAndCreatedtimestampBetweenAndCreatebyInAndSitecodeOrderByProtocolordercodeDesc(
									objdir.getDirectorycode(), 1, objdir.getOrderflag(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), objdir.getDirectorycode(), 2,
									objdir.getOrderflag(), objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), objdir.getDirectorycode(), 3,
									objdir.getOrderflag(), fromdate, todate, objdir.getLstuserMaster(),
									objdir.getSitemaster().getSitecode(), false, true, selectedteamprotcolorderList,
									objdir.getDirectorycode(), 3, objdir.getOrderflag(), fromdate, todate,
									objdir.getLstuserMaster(), objdir.getSitemaster().getSitecode());
				}

			}
		} else if (protocoltype == -1 && objdir.getOrderflag() != null && objdir.getTestcode() == null
				&& objdir.getLsprojectmaster() != null) {
			if (objdir.getLstuserMaster().length == 0) {
				if (objdir.getRejected() != null) {

					retuobj = logilabprotocoldetailrepository
							.findByDirectorycodeAndViewoptionAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrderByProtocolordercodeDesc(
									objdir.getDirectorycode(), 1, objdir.getOrderflag(), 1, fromdate, todate,
									objdir.getSitemaster().getSitecode(), project, objdir.getDirectorycode(), 2,
									objdir.getOrderflag(), 1, objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), project, objdir.getDirectorycode(), 3,
									objdir.getOrderflag(), 1, objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), false, project, true,
									selectedteamprotcolorderList, objdir.getDirectorycode(), 3, objdir.getOrderflag(),
									1, objdir.getCreatedby(), fromdate, todate, objdir.getSitemaster().getSitecode(),
									project);

				} else {

					retuobj = logilabprotocoldetailrepository
							.findByDirectorycodeAndViewoptionAndOrderflagAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrderByProtocolordercodeDesc(
									objdir.getDirectorycode(), 1, objdir.getOrderflag(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), project, objdir.getDirectorycode(), 2,
									objdir.getOrderflag(), objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), project, objdir.getDirectorycode(), 3,
									objdir.getOrderflag(), objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), false, project, true,
									selectedteamprotcolorderList, objdir.getDirectorycode(), 3, objdir.getOrderflag(),
									objdir.getCreatedby(), fromdate, todate, objdir.getSitemaster().getSitecode(),
									project);
				}
			} else {
				if (objdir.getRejected() != null) {

					retuobj = logilabprotocoldetailrepository
							.findByDirectorycodeAndViewoptionAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndOrderflagAndRejectedAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndLsprojectmasterOrderByProtocolordercodeDesc(
									objdir.getDirectorycode(), 1, objdir.getOrderflag(), 1, fromdate, todate,
									objdir.getSitemaster().getSitecode(), project, objdir.getDirectorycode(), 2,
									objdir.getOrderflag(), 1, objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), project, objdir.getDirectorycode(), 3,
									objdir.getOrderflag(), 1, fromdate, todate, objdir.getLstuserMaster(),
									objdir.getSitemaster().getSitecode(), false, project, true,
									selectedteamprotcolorderList, objdir.getDirectorycode(), 3, objdir.getOrderflag(),
									1, fromdate, todate, objdir.getLstuserMaster(),
									objdir.getSitemaster().getSitecode(), project);
				} else {

					retuobj = logilabprotocoldetailrepository
							.findByDirectorycodeAndViewoptionAndOrderflagAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndOrderflagAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndOrderflagAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndLsprojectmasterOrderByProtocolordercodeDesc(
									objdir.getDirectorycode(), 1, objdir.getOrderflag(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), project, objdir.getDirectorycode(), 2,
									objdir.getOrderflag(), objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), project, objdir.getDirectorycode(), 3,
									objdir.getOrderflag(), fromdate, todate, objdir.getLstuserMaster(),
									objdir.getSitemaster().getSitecode(), false, project, true,
									selectedteamprotcolorderList, objdir.getDirectorycode(), 3, objdir.getOrderflag(),
									fromdate, todate, objdir.getLstuserMaster(), objdir.getSitemaster().getSitecode(),
									project);
				}

			}
		} else if (protocoltype == -1 && objdir.getOrderflag() != null && objdir.getTestcode() != null
				&& objdir.getLsprojectmaster() == null) {
			if (objdir.getLstuserMaster().length == 0) {
				if (objdir.getRejected() != null) {

					retuobj = logilabprotocoldetailrepository
							.findByDirectorycodeAndViewoptionAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndTestcodeOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrderByProtocolordercodeDesc(
									objdir.getDirectorycode(), 1, objdir.getOrderflag(), 1, fromdate, todate,
									objdir.getSitemaster().getSitecode(), testcode, objdir.getDirectorycode(), 2,
									objdir.getOrderflag(), 1, objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), testcode, objdir.getDirectorycode(), 3,
									objdir.getOrderflag(), 1, objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), false, testcode, true,
									selectedteamprotcolorderList, objdir.getDirectorycode(), 3, objdir.getOrderflag(),
									1, objdir.getCreatedby(), fromdate, todate, objdir.getSitemaster().getSitecode(),
									testcode);

				} else {

					retuobj = logilabprotocoldetailrepository
							.findByDirectorycodeAndViewoptionAndOrderflagAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndTestcodeOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrderByProtocolordercodeDesc(
									objdir.getDirectorycode(), 1, objdir.getOrderflag(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), testcode, objdir.getDirectorycode(), 2,
									objdir.getOrderflag(), objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), testcode, objdir.getDirectorycode(), 3,
									objdir.getOrderflag(), objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), false, testcode, true,
									selectedteamprotcolorderList, objdir.getDirectorycode(), 3, objdir.getOrderflag(),
									objdir.getCreatedby(), fromdate, todate, objdir.getSitemaster().getSitecode(),
									testcode);
				}
			} else {
				if (objdir.getRejected() != null) {

					retuobj = logilabprotocoldetailrepository
							.findByDirectorycodeAndViewoptionAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndTestcodeOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndOrderflagAndRejectedAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTestcodeOrderByProtocolordercodeDesc(
									objdir.getDirectorycode(), 1, objdir.getOrderflag(), 1, fromdate, todate,
									objdir.getSitemaster().getSitecode(), testcode, objdir.getDirectorycode(), 2,
									objdir.getOrderflag(), 1, objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), testcode, objdir.getDirectorycode(), 3,
									objdir.getOrderflag(), 1, fromdate, todate, objdir.getLstuserMaster(),
									objdir.getSitemaster().getSitecode(), false, testcode, true,
									selectedteamprotcolorderList, objdir.getDirectorycode(), 3, objdir.getOrderflag(),
									1, fromdate, todate, objdir.getLstuserMaster(),
									objdir.getSitemaster().getSitecode(), testcode);
				} else {

					retuobj = logilabprotocoldetailrepository
							.findByDirectorycodeAndViewoptionAndOrderflagAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndOrderflagAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndTestcodeOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndOrderflagAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTestcodeOrderByProtocolordercodeDesc(
									objdir.getDirectorycode(), 1, objdir.getOrderflag(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), testcode, objdir.getDirectorycode(), 2,
									objdir.getOrderflag(), objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), testcode, objdir.getDirectorycode(), 3,
									objdir.getOrderflag(), fromdate, todate, objdir.getLstuserMaster(),
									objdir.getSitemaster().getSitecode(), false, testcode, true,
									selectedteamprotcolorderList, objdir.getDirectorycode(), 3, objdir.getOrderflag(),
									fromdate, todate, objdir.getLstuserMaster(), objdir.getSitemaster().getSitecode(),
									testcode);
				}

			}
		}

		else if (protocoltype == -1 && objdir.getOrderflag() != null && objdir.getTestcode() != null
				&& objdir.getLsprojectmaster() != null) {
			if (objdir.getLstuserMaster().length == 0) {
				if (objdir.getRejected() != null) {

					retuobj = logilabprotocoldetailrepository
							.findByDirectorycodeAndViewoptionAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndTestcodeAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrderByProtocolordercodeDesc(
									objdir.getDirectorycode(), 1, objdir.getOrderflag(), 1, fromdate, todate,
									objdir.getSitemaster().getSitecode(), testcode, project, objdir.getDirectorycode(),
									2, objdir.getOrderflag(), 1, objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), testcode, project, objdir.getDirectorycode(),
									3, objdir.getOrderflag(), 1, objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), false, testcode, project, true,
									selectedteamprotcolorderList, objdir.getDirectorycode(), 3, objdir.getOrderflag(),
									1, objdir.getCreatedby(), fromdate, todate, objdir.getSitemaster().getSitecode(),
									testcode, project);

				} else {

					retuobj = logilabprotocoldetailrepository
							.findByDirectorycodeAndViewoptionAndOrderflagAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndTestcodeAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrderByProtocolordercodeDesc(
									objdir.getDirectorycode(), 1, objdir.getOrderflag(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), testcode, project, objdir.getDirectorycode(),
									2, objdir.getOrderflag(), objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), testcode, project, objdir.getDirectorycode(),
									3, objdir.getOrderflag(), objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), false, testcode, project, true,
									selectedteamprotcolorderList, objdir.getDirectorycode(), 3, objdir.getOrderflag(),
									objdir.getCreatedby(), fromdate, todate, objdir.getSitemaster().getSitecode(),
									testcode, project);
				}
			} else {
				if (objdir.getRejected() != null) {

					retuobj = logilabprotocoldetailrepository
							.findByDirectorycodeAndViewoptionAndOrderflagAndRejectedAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndOrderflagAndRejectedAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndTestcodeAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndOrderflagAndRejectedAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTestcodeAndLsprojectmasterOrderByProtocolordercodeDesc(
									objdir.getDirectorycode(), 1, objdir.getOrderflag(), 1, fromdate, todate,
									objdir.getSitemaster().getSitecode(), testcode, project, objdir.getDirectorycode(),
									2, objdir.getOrderflag(), 1, objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), testcode, project, objdir.getDirectorycode(),
									3, objdir.getOrderflag(), 1, fromdate, todate, objdir.getLstuserMaster(),
									objdir.getSitemaster().getSitecode(), false, testcode, project, true,
									selectedteamprotcolorderList, objdir.getDirectorycode(), 3, objdir.getOrderflag(),
									1, fromdate, todate, objdir.getLstuserMaster(),
									objdir.getSitemaster().getSitecode(), testcode, project);
				} else {

					retuobj = logilabprotocoldetailrepository
							.findByDirectorycodeAndViewoptionAndOrderflagAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndOrderflagAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndTestcodeAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndOrderflagAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTestcodeAndLsprojectmasterOrderByProtocolordercodeDesc(
									objdir.getDirectorycode(), 1, objdir.getOrderflag(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), testcode, project, objdir.getDirectorycode(),
									2, objdir.getOrderflag(), objdir.getCreatedby(), fromdate, todate,
									objdir.getSitemaster().getSitecode(), testcode, project, objdir.getDirectorycode(),
									3, objdir.getOrderflag(), fromdate, todate, objdir.getLstuserMaster(),
									objdir.getSitemaster().getSitecode(), false, testcode, project, true,
									selectedteamprotcolorderList, objdir.getDirectorycode(), 3, objdir.getOrderflag(),
									fromdate, todate, objdir.getLstuserMaster(), objdir.getSitemaster().getSitecode(),
									testcode, project);
				}

			}
		}

		else if (protocoltype != -1 && objdir.getOrderflag() == null && objdir.getTestcode() == null
				&& objdir.getLsprojectmaster() == null) {
			if (objdir.getLstuserMaster().length == 0) {

				retuobj = LSlogilabprotocoldetailRepository
						.findByDirectorycodeAndViewoptionAndProtocoltypeAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndProtocoltypeAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndProtocoltypeAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrderByProtocolordercodeDesc(
								objdir.getDirectorycode(), 1, protocoltype, fromdate, todate,
								objdir.getSitemaster().getSitecode(), objdir.getDirectorycode(), 2, protocoltype,
								objdir.getCreatedby(), fromdate, todate, objdir.getSitemaster().getSitecode(),
								objdir.getDirectorycode(), 3, protocoltype, objdir.getCreatedby(), fromdate, todate,
								objdir.getSitemaster().getSitecode(), false, true, selectedteamprotcolorderList,
								objdir.getDirectorycode(), 3, protocoltype, objdir.getCreatedby(), fromdate, todate,
								objdir.getSitemaster().getSitecode());
			} else {

				retuobj = LSlogilabprotocoldetailRepository
						.findByDirectorycodeAndViewoptionAndProtocoltypeAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndProtocoltypeAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeOrDirectorycodeAndViewoptionAndProtocoltypeAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndCreatedtimestampBetweenAndCreatebyInAndSitecodeOrderByProtocolordercodeDesc(
								objdir.getDirectorycode(), 1, protocoltype, fromdate, todate,
								objdir.getSitemaster().getSitecode(), objdir.getDirectorycode(), 2, protocoltype,
								objdir.getCreatedby(), fromdate, todate, objdir.getSitemaster().getSitecode(),
								objdir.getDirectorycode(), 3, protocoltype, fromdate, todate, objdir.getLstuserMaster(),
								objdir.getSitemaster().getSitecode(), false, true, selectedteamprotcolorderList,
								objdir.getDirectorycode(), 3, protocoltype, fromdate, todate, objdir.getLstuserMaster(),
								objdir.getSitemaster().getSitecode());
			}

		} else if (protocoltype != -1 && objdir.getOrderflag() == null && objdir.getTestcode() == null
				&& objdir.getLsprojectmaster() != null) {
			if (objdir.getLstuserMaster().length == 0) {

				retuobj = logilabprotocoldetailrepository
						.findByDirectorycodeAndViewoptionAndProtocoltypeAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrderByProtocolordercodeDesc(
								objdir.getDirectorycode(), 1, protocoltype, fromdate, todate,
								objdir.getSitemaster().getSitecode(), project, objdir.getDirectorycode(), 2,
								protocoltype, objdir.getCreatedby(), fromdate, todate,
								objdir.getSitemaster().getSitecode(), project, objdir.getDirectorycode(), 3,
								protocoltype, objdir.getCreatedby(), fromdate, todate,
								objdir.getSitemaster().getSitecode(), false, project, true,
								selectedteamprotcolorderList, objdir.getDirectorycode(), 3, protocoltype,
								objdir.getCreatedby(), fromdate, todate, objdir.getSitemaster().getSitecode(), project);
			} else {

				retuobj = logilabprotocoldetailrepository
						.findByDirectorycodeAndViewoptionAndProtocoltypeAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndLsprojectmasterOrderByProtocolordercodeDesc(
								objdir.getDirectorycode(), 1, protocoltype, fromdate, todate,
								objdir.getSitemaster().getSitecode(), project, objdir.getDirectorycode(), 2,
								protocoltype, objdir.getCreatedby(), fromdate, todate,
								objdir.getSitemaster().getSitecode(), project, objdir.getDirectorycode(), 3,
								protocoltype, fromdate, todate, objdir.getLstuserMaster(),
								objdir.getSitemaster().getSitecode(), false, project, true,
								selectedteamprotcolorderList, objdir.getDirectorycode(), 3, protocoltype, fromdate,
								todate, objdir.getLstuserMaster(), objdir.getSitemaster().getSitecode(), project);
			}

		} else if (protocoltype != -1 && objdir.getOrderflag() == null && objdir.getTestcode() != null
				&& objdir.getLsprojectmaster() == null) {
			if (objdir.getLstuserMaster().length == 0) {

				retuobj = logilabprotocoldetailrepository
						.findByDirectorycodeAndViewoptionAndProtocoltypeAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndProtocoltypeAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndProtocoltypeAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndTestcodeOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrderByProtocolordercodeDesc(
								objdir.getDirectorycode(), 1, protocoltype, fromdate, todate,
								objdir.getSitemaster().getSitecode(), testcode, objdir.getDirectorycode(), 2,
								protocoltype, objdir.getCreatedby(), fromdate, todate,
								objdir.getSitemaster().getSitecode(), testcode, objdir.getDirectorycode(), 3,
								protocoltype, objdir.getCreatedby(), fromdate, todate,
								objdir.getSitemaster().getSitecode(), false, testcode, true,
								selectedteamprotcolorderList, objdir.getDirectorycode(), 3, protocoltype,
								objdir.getCreatedby(), fromdate, todate, objdir.getSitemaster().getSitecode(),
								testcode);
			} else {

				retuobj = logilabprotocoldetailrepository
						.findByDirectorycodeAndViewoptionAndProtocoltypeAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndProtocoltypeAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeOrDirectorycodeAndViewoptionAndProtocoltypeAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndTestcodeOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTestcodeOrderByProtocolordercodeDesc(
								objdir.getDirectorycode(), 1, protocoltype, fromdate, todate,
								objdir.getSitemaster().getSitecode(), testcode, objdir.getDirectorycode(), 2,
								protocoltype, objdir.getCreatedby(), fromdate, todate,
								objdir.getSitemaster().getSitecode(), testcode, objdir.getDirectorycode(), 3,
								protocoltype, fromdate, todate, objdir.getLstuserMaster(),
								objdir.getSitemaster().getSitecode(), false, testcode, true,
								selectedteamprotcolorderList, objdir.getDirectorycode(), 3, protocoltype, fromdate,
								todate, objdir.getLstuserMaster(), objdir.getSitemaster().getSitecode(), testcode);
			}

		} else if (protocoltype != -1 && objdir.getOrderflag() == null && objdir.getTestcode() != null
				&& objdir.getLsprojectmaster() != null) {
			if (objdir.getLstuserMaster().length == 0) {

				retuobj = logilabprotocoldetailrepository
						.findByDirectorycodeAndViewoptionAndProtocoltypeAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTeamselectedAndTestcodeAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrderByProtocolordercodeDesc(
								objdir.getDirectorycode(), 1, protocoltype, fromdate, todate,
								objdir.getSitemaster().getSitecode(), testcode, project, objdir.getDirectorycode(), 2,
								protocoltype, objdir.getCreatedby(), fromdate, todate,
								objdir.getSitemaster().getSitecode(), testcode, project, objdir.getDirectorycode(), 3,
								protocoltype, objdir.getCreatedby(), fromdate, todate,
								objdir.getSitemaster().getSitecode(), false, testcode, project, true,
								selectedteamprotcolorderList, objdir.getDirectorycode(), 3, protocoltype,
								objdir.getCreatedby(), fromdate, todate, objdir.getSitemaster().getSitecode(), testcode,
								project);
			} else {

				retuobj = logilabprotocoldetailrepository
						.findByDirectorycodeAndViewoptionAndProtocoltypeAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndLsuserMasterAndCreatedtimestampBetweenAndSitecodeAndTestcodeAndLsprojectmasterOrDirectorycodeAndViewoptionAndProtocoltypeAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTeamselectedAndTestcodeAndLsprojectmasterOrTeamselectedAndProtocolordercodeInAndDirectorycodeAndViewoptionAndProtocoltypeAndCreatedtimestampBetweenAndCreatebyInAndSitecodeAndTestcodeAndLsprojectmasterOrderByProtocolordercodeDesc(
								objdir.getDirectorycode(), 1, protocoltype, fromdate, todate,
								objdir.getSitemaster().getSitecode(), testcode, project, objdir.getDirectorycode(), 2,
								protocoltype, objdir.getCreatedby(), fromdate, todate,
								objdir.getSitemaster().getSitecode(), testcode, project, objdir.getDirectorycode(), 3,
								protocoltype, fromdate, todate, objdir.getLstuserMaster(),
								objdir.getSitemaster().getSitecode(), false, testcode, project, true,
								selectedteamprotcolorderList, objdir.getDirectorycode(), 3, protocoltype, fromdate,
								todate, objdir.getLstuserMaster(), objdir.getSitemaster().getSitecode(), testcode,
								project);
			}

		}

		if (!retuobj.isEmpty() && iscancelled != null && iscancelled && objdir.getOrderflag() != null) {
			retuobj = retuobj.stream().filter(o -> o.getOrdercancell() != null && o.getOrdercancell() == 1)
					.collect(Collectors.toCollection(ArrayList::new));
		} else if (!retuobj.isEmpty() && objdir.getOrderflag() != null) {
			retuobj = retuobj.stream().filter(o -> o.getOrdercancell() == null || o.getOrdercancell() != 1)
					.collect(Collectors.toCollection(ArrayList::new));
		}

		if (!retuobj.isEmpty() && objdir.getOrderflag() != null && objdir.getOrderflag().equals("R") && rejected == 1) {
			retuobj = retuobj.stream().filter(o -> o.getRejected() != null && o.getRejected() == 1)
					.collect(Collectors.toCollection(ArrayList::new));
		} else if (!retuobj.isEmpty() && objdir.getOrderflag() != null && objdir.getOrderflag().equals("R")
				&& rejected == 0) {
			retuobj = retuobj.stream().filter(o -> o.getRejected() == null || o.getRejected() != 1)
					.collect(Collectors.toCollection(ArrayList::new));
		}

		List<Elnprotocolworkflow> workflowobj = elnprotocolworkflowRepository
				.findByLssitemasterAndStatusOrderByWorkflowcodeAsc(objdir.getLsuserMaster().getLssitemaster(), 1);
		retuobj.forEach(objorderDetail -> objorderDetail.setLstelnprotocolworkflow(objdir.getLstelnprotocolworkflow(),
				workflowobj, objdir.getLsuserMaster()));
		List<Long> protocolordercode = new ArrayList<>();
		if (retuobj.size() > 0 && objdir.getSearchCriteriaType() != null) {
			protocolordercode = retuobj.stream().map(LSlogilabprotocoldetail::getProtocolordercode)
					.collect(Collectors.toList());
			retuobjts.put("protocolordercodeslist", protocolordercode);
		}
		retuobjts.put("protocolorders", retuobj);

		return retuobjts;
	}

	public LSlogilabprotocoldetail UpdatesingleFolderforprotocolorder(LSlogilabprotocoldetail order) {
		LSlogilabprotocoldetailRepository.updatesingledirectory(order.getDirectorycode(), order.getProtocolordercode());
		return order;
	}

	public List<Logilaborders> Getordersonproject(LSlogilablimsorderdetail objorder) {

		List<Logilaborders> lstorder = new ArrayList<Logilaborders>();

		Date fromdate = objorder.getFromdate();
		Date todate = objorder.getTodate();
		Integer filetype = objorder.getFiletype();
		if ((filetype == -1 && objorder.getOrderflag() == null) || (filetype == null)) {
			lstorder = lslogilablimsorderdetailRepository
					.findByLsprojectmasterAndSitecodeAndCreatedtimestampBetweenAndDirectorycodeIsNullOrderByBatchcodeDesc(
							objorder.getLsprojectmaster(), objorder.getSitecode(), fromdate, todate);
		} else if (filetype == -1 && objorder.getOrderflag() != null) {

			lstorder = lslogilablimsorderdetailRepository
					.findByLsprojectmasterAndSitecodeAndCreatedtimestampBetweenAndDirectorycodeIsNullAndOrderflagOrderByBatchcodeDesc(
							objorder.getLsprojectmaster(), objorder.getSitecode(), fromdate, todate,
							objorder.getOrderflag());

		} else if (filetype == 4) {
			lstorder = lslogilablimsorderdetailRepository
					.findByLsprojectmasterAndSitecodeAndCreatedtimestampBetweenAndDirectorycodeIsNullAndFiletypeOrderByBatchcodeDesc(
							objorder.getLsprojectmaster(), objorder.getSitecode(), fromdate, todate, filetype);

		} else if ((objorder.getOrderflag() != null && objorder.getApprovelstatus() != null)
				&& (objorder.getApprovelstatus() == 3 || objorder.getApprovelstatus() == 1)) {
			lstorder = lslogilablimsorderdetailRepository
					.findByLsprojectmasterAndSitecodeAndCreatedtimestampBetweenAndDirectorycodeIsNullAndOrderflagAndApprovelstatusAndFiletypeOrderByBatchcodeDesc(
							objorder.getLsprojectmaster(), objorder.getSitecode(), fromdate, todate,
							objorder.getOrderflag(), objorder.getApprovelstatus(), filetype);
		} else {
			lstorder = lslogilablimsorderdetailRepository
					.findByLsprojectmasterAndSitecodeAndCreatedtimestampBetweenAndDirectorycodeIsNullAndOrderflagAndFiletypeOrderByBatchcodeDesc(
							objorder.getLsprojectmaster(), objorder.getSitecode(), fromdate, todate,
							objorder.getOrderflag(), filetype);

		}

//		if (filetype == null) {
//			lstorder = lslogilablimsorderdetailRepository
//					.findByLsprojectmasterAndLstestmasterlocalAndCreatedtimestampBetweenOrderByBatchcodeDesc(
//							objorder.getOrderflag(), objorder.getLsprojectmaster(), objorder.getLstestmasterlocal(),
//							fromdate, todate);
//		} else if (filetype == -1 && objorder.getOrderflag() == null) {
//			lstorder = lslogilablimsorderdetailRepository
//					.findByLsprojectmasterAndLstestmasterlocalAndCreatedtimestampBetweenOrderByBatchcodeDesc(
//							objorder.getLsprojectmaster(), objorder.getLstestmasterlocal(), fromdate, todate);
//		} else if (filetype == -1 && objorder.getOrderflag() != null) {
//			lstorder = lslogilablimsorderdetailRepository
//					.findByOrderflagAndLsprojectmasterAndLstestmasterlocalAndCreatedtimestampBetweenOrderByBatchcodeDesc(
//							objorder.getOrderflag(), objorder.getLsprojectmaster(), objorder.getLstestmasterlocal(),
//							fromdate, todate);
//		}
		// get sheet orders
//		else if (filetype == 4) {
//			lstorder = lslogilablimsorderdetailRepository
//					.findByLsprojectmasterAndFiletypeAndCreatedtimestampBetweenOrderByBatchcodeDesc(
//							objorder.getLsprojectmaster(), filetype, fromdate, todate);
//		}
//		else if (objorder.getOrderflag() == null) {
//			lstorder = lslogilablimsorderdetailRepository
//					.findByFiletypeAndLsprojectmasterAndLstestmasterlocalAndCreatedtimestampBetweenOrderByBatchcodeDesc(
//							filetype, objorder.getLsprojectmaster(), objorder.getLstestmasterlocal(), fromdate, todate);
//		} else if (objorder.getOrderflag() != null && objorder.getApprovelstatus() != null
//				&& objorder.getApprovelstatus() == 3) {
//			lstorder = lslogilablimsorderdetailRepository
//					.findByOrderflagAndApprovelstatusAndFiletypeAndLsprojectmasterAndLstestmasterlocalAndCreatedtimestampBetweenOrderByBatchcodeDesc(
//							objorder.getOrderflag(), objorder.getApprovelstatus(), filetype,
//							objorder.getLsprojectmaster(), objorder.getLstestmasterlocal(), fromdate, todate);
//		} else if (objorder.getOrderflag() != null && objorder.getApprovelstatus() != null
//				&& objorder.getApprovelstatus() == 1) {
//			lstorder = lslogilablimsorderdetailRepository
//					.findByOrderflagAndApprovelstatusAndFiletypeAndLsprojectmasterAndLstestmasterlocalAndCreatedtimestampBetweenOrderByBatchcodeDesc(
//							objorder.getOrderflag(), objorder.getApprovelstatus(), filetype,
//							objorder.getLsprojectmaster(), objorder.getLstestmasterlocal(), fromdate, todate);
//		} else {
//			lstorder = lslogilablimsorderdetailRepository
//					.findByOrderflagAndFiletypeAndLsprojectmasterAndLstestmasterlocalAndCreatedtimestampBetweenOrderByBatchcodeDesc(
//							objorder.getOrderflag(), filetype, objorder.getLsprojectmaster(),
//							objorder.getLstestmasterlocal(), fromdate, todate);
//		}

		lstorder.addAll(Getordersondirectory(objorder.getLssheetOrderStructure()));
		// }

		List<LSworkflow> workflowobj = lsworkflowRepository
				.findByLssitemasterAndStatusOrderByWorkflowcodeAsc(objorder.getLsuserMaster().getLssitemaster(), 1);
		if (objorder.getSearchCriteria() != null && objorder.getSearchCriteria().getContentsearchtype() != null
				&& objorder.getSearchCriteria().getContentsearch() != null) {

			lstorder = GetsampleordersonFilter(objorder, lstorder);

			lstorder.forEach(objorderDetail -> objorderDetail.setLstworkflow(objorder.getLstworkflow(), workflowobj,
					objorder.getLsuserMaster()));
			return lstorder;
		} else {
			lstorder.forEach(objorderDetail -> objorderDetail.setLstworkflow(objorder.getLstworkflow(), workflowobj,
					objorder.getLsuserMaster()));
			return lstorder;
		}
	}

	public List<Logilaborders> Getordersonsample(LSlogilablimsorderdetail objorder) {

		List<Logilaborders> lstorder = new ArrayList<Logilaborders>();

		Date fromdate = objorder.getFromdate();
		Date todate = objorder.getTodate();
		Integer filetype = objorder.getFiletype();
//		if (objorder.getSearchCriteria().getContentsearchtype() != null
//				&& objorder.getSearchCriteria().getContentsearch() != null) {
//			lstorder = GetordersonFilter(objorder);
//
//		}
		if (objorder.getSearchCriteria().getContentsearch() == null) {
			if (filetype == null) {

				lstorder = lslogilablimsorderdetailRepository
						.findByLssamplemasterAndViewoptionAndLstestmasterlocalAndOrderdisplaytypeAndCreatedtimestampBetweenOrLssamplemasterAndViewoptionAndLsuserMasterAndLstestmasterlocalAndFiletypeAndOrderdisplaytypeAndCreatedtimestampBetweenOrderByBatchcodeDesc(
								objorder.getLssamplemaster(), 1, objorder.getLstestmasterlocal(), 2, fromdate, todate,
								objorder.getLssamplemaster(), 2, objorder.getLsuserMaster(),
								objorder.getLstestmasterlocal(), filetype, 2, fromdate, todate);

			} else if (filetype == -1 && objorder.getOrderflag() == null) {
//				lstorder = lslogilablimsorderdetailRepository
//						.findByLssamplemasterAndViewoptionAndOrderdisplaytypeAndCreatedtimestampBetweenOrLssamplemasterAndViewoptionAndLsuserMasterAndOrderdisplaytypeAndCreatedtimestampBetweenOrderByBatchcodeDesc(
//								objorder.getLssamplemaster(), 1, 2, fromdate, todate, objorder.getLssamplemaster(), 2,
//								objorder.getLsuserMaster(), 2, fromdate, todate);
				lstorder = lslogilablimsorderdetailRepository
						.findByLssamplemasterAndViewoptionAndLstestmasterlocalAndOrderdisplaytypeAndCreatedtimestampBetweenOrLssamplemasterAndViewoptionAndLsuserMasterAndOrderdisplaytypeAndCreatedtimestampBetweenOrderByBatchcodeDesc(
								objorder.getLssamplemaster(), 1, objorder.getLstestmasterlocal(), 2, fromdate, todate,
								objorder.getLssamplemaster(), 2, objorder.getLsuserMaster(), 2, fromdate, todate);

			} else if (filetype == 0) {

				lstorder = lslogilablimsorderdetailRepository
						.findByOrderflagAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
								objorder.getOrderflag(), filetype, fromdate, todate);
			}

		} else if (filetype == -1 && objorder.getOrderflag() != null) {
			lstorder = lslogilablimsorderdetailRepository
					.findByOrderflagAndLssamplemasterAndViewoptionAndLstestmasterlocalAndOrderdisplaytypeAndCreatedtimestampBetweenOrLssamplemasterAndViewoptionAndLsuserMasterAndLstestmasterlocalAndOrderdisplaytypeAndCreatedtimestampBetweenOrderByBatchcodeDesc(
							objorder.getOrderflag(), objorder.getLssamplemaster(), 1, objorder.getLstestmasterlocal(),
							2, fromdate, todate, objorder.getLssamplemaster(), 2, objorder.getLsuserMaster(),
							objorder.getLstestmasterlocal(), 2, fromdate, todate);

		} else if (filetype == 4) {

			lstorder = lslogilablimsorderdetailRepository
					.findByLssamplemasterAndViewoptionAndFiletypeAndOrderdisplaytypeAndCreatedtimestampBetweenOrLssamplemasterAndViewoptionAndLsuserMasterAndFiletypeAndOrderdisplaytypeAndCreatedtimestampBetweenOrderByBatchcodeDesc(
							objorder.getLssamplemaster(), 1, filetype, 2, fromdate, todate,
							objorder.getLssamplemaster(), 2, objorder.getLsuserMaster(), filetype, 2, fromdate, todate);

		} else if (objorder.getOrderflag() == null && filetype == -1) {
//			lstorder = lslogilablimsorderdetailRepository
//					.findByLssamplemasterAndViewoptionAndLstestmasterlocalAndFiletypeAndOrderdisplaytypeAndCreatedtimestampBetweenOrLssamplemasterAndViewoptionAndLsuserMasterAndLstestmasterlocalAndFiletypeAndOrderdisplaytypeAndCreatedtimestampBetweenOrderByBatchcodeDesc(
//							objorder.getLssamplemaster(), 1, objorder.getLstestmasterlocal(), filetype, 2, fromdate,
//							todate, objorder.getLssamplemaster(), 2, objorder.getLsuserMaster(),
//							objorder.getLstestmasterlocal(), filetype, 2, fromdate, todate);

			lstorder = lslogilablimsorderdetailRepository
					.findByLssamplemasterAndViewoptionAndLstestmasterlocalAndOrderdisplaytypeAndCreatedtimestampBetweenOrLssamplemasterAndViewoptionAndLsuserMasterAndLstestmasterlocalAndOrderdisplaytypeAndCreatedtimestampBetweenOrderByBatchcodeDesc(
							objorder.getLssamplemaster(), 1, objorder.getLstestmasterlocal(), 2, fromdate, todate,
							objorder.getLssamplemaster(), 2, objorder.getLsuserMaster(),
							objorder.getLstestmasterlocal(), 2, fromdate, todate);

		} else if (objorder.getOrderflag() != null && objorder.getApprovelstatus() != null
				&& objorder.getApprovelstatus() == 3) {
			lstorder = lslogilablimsorderdetailRepository
					.findByOrderflagAndApprovelstatusAndLssamplemasterAndViewoptionAndLstestmasterlocalAndFiletypeAndOrderdisplaytypeAndCreatedtimestampBetweenOrLssamplemasterAndViewoptionAndLsuserMasterAndLstestmasterlocalAndFiletypeAndOrderdisplaytypeAndCreatedtimestampBetweenOrderByBatchcodeDesc(
							objorder.getOrderflag(), objorder.getApprovelstatus(), objorder.getLssamplemaster(), 1,
							objorder.getLstestmasterlocal(), filetype, 2, fromdate, todate,
							objorder.getLssamplemaster(), 2, objorder.getLsuserMaster(),
							objorder.getLstestmasterlocal(), filetype, 2, fromdate, todate);

		} else if (objorder.getOrderflag() != null && objorder.getApprovelstatus() != null
				&& objorder.getApprovelstatus() == 1) {
			lstorder = lslogilablimsorderdetailRepository
					.findByOrderflagAndApprovelstatusAndLssamplemasterAndViewoptionAndLstestmasterlocalAndFiletypeAndOrderdisplaytypeAndCreatedtimestampBetweenOrLssamplemasterAndViewoptionAndLsuserMasterAndLstestmasterlocalAndFiletypeAndOrderdisplaytypeAndCreatedtimestampBetweenOrderByBatchcodeDesc(
							objorder.getOrderflag(), objorder.getApprovelstatus(), objorder.getLssamplemaster(), 1,
							objorder.getLstestmasterlocal(), filetype, 2, fromdate, todate,
							objorder.getLssamplemaster(), 2, objorder.getLsuserMaster(),
							objorder.getLstestmasterlocal(), filetype, 2, fromdate, todate);

		}

		else {
			lstorder = lslogilablimsorderdetailRepository
					.findByLssamplemasterAndViewoptionAndLstestmasterlocalAndFiletypeAndOrderdisplaytypeAndCreatedtimestampBetweenOrLssamplemasterAndViewoptionAndLsuserMasterAndLstestmasterlocalAndFiletypeAndOrderdisplaytypeAndCreatedtimestampBetweenOrderByBatchcodeDesc(
							objorder.getLssamplemaster(), 1, objorder.getLstestmasterlocal(), filetype, 2, fromdate,
							todate, objorder.getLssamplemaster(), 2, objorder.getLsuserMaster(),
							objorder.getLstestmasterlocal(), filetype, 2, fromdate, todate);

		}

		if (objorder.getSearchCriteria() != null && objorder.getSearchCriteria().getContentsearchtype() != null
				&& objorder.getSearchCriteria().getContentsearch() != null) {

			lstorder = GetsampleordersonFilter(objorder, lstorder);
			List<LSworkflow> workflowobj = lsworkflowRepository
					.findByLssitemasterAndStatusOrderByWorkflowcodeAsc(objorder.getLsuserMaster().getLssitemaster(), 1);
			lstorder.forEach(objorderDetail -> objorderDetail.setLstworkflow(objorder.getLstworkflow(), workflowobj,
					objorder.getLsuserMaster()));
			return lstorder;
		} else {

			lstorder.forEach(objorderDetail -> objorderDetail.setCanuserprocess(true));
			return lstorder;
		}
	}

	public Map<String, Object> Getorderbyflaganduser(LSlogilablimsorderdetail objorder) {

		Map<String, Object> rtn_object = new HashMap<>();
		List<LSuserteammapping> lstteammap = lsuserteammappingRepository.findBylsuserMaster(objorder.getLsuserMaster());
		List<LSusersteam> lstteam = lsusersteamRepository.findByLsuserteammappingInAndLssitemaster(lstteammap,
				objorder.getLsuserMaster().getLssitemaster());
		List<LSprojectmaster> lstproject = lsprojectmasterRepository.findByLsusersteamIn(lstteam);

		List<Elnmaterial> nmaterialcode = elnmaterialRepository
				.findByNsitecode(objorder.getLsuserMaster().getLssitemaster().getSitecode());
		List<Logilaborderssh> lstorder = new ArrayList<Logilaborderssh>();
		Date fromdate = objorder.getFromdate();
		Date todate = objorder.getTodate();
		Integer testcode = objorder.getTestcode();
		Integer filetype = objorder.getFiletype();

		List<LSSelectedTeam> selectedteamorders = new ArrayList<LSSelectedTeam>();
		selectedteamorders = lstteam != null
				? LSSelectedTeamRepository.findByUserteamInAndCreatedtimestampBetween(lstteam, fromdate, todate)
				: selectedteamorders;

		// List<LSSelectedTeam> selectedteamorders =
		// LSSelectedTeamRepository.findByUserteamInAndCreatedtimestampBetween(lstteam,fromdate,todate);

		List<Long> selectedteambatchCodeList = (!selectedteamorders.isEmpty() && selectedteamorders != null)
				? selectedteamorders.stream().map(LSSelectedTeam::getBatchcode).filter(Objects::nonNull).distinct()
						.collect(Collectors.toList())
				: Collections.singletonList(-1L);

		List<Long> Directory_Code = objorder.getLstdirectorycode() != null ? objorder.getLstdirectorycode()
				: new ArrayList<Long>();

		List<Lsordershareto.LsordersharetoProjection> sharedtoobj = new ArrayList<Lsordershareto.LsordersharetoProjection>();
		List<Logilaborderssh> lstorder1 = new ArrayList<Logilaborderssh>();

		if (filetype != null && filetype == -1) {
			sharedtoobj = lsordersharetoRepository
					.findByUsersharedonAndSitecodeAndSharestatusAndSharedonBetweenOrderBySharetocodeDesc(
							objorder.getLsuserMaster(), objorder.getLsuserMaster().getLssitemaster().getSitecode(), 1,
							fromdate, todate);
		} else if (filetype != null) {
			sharedtoobj = lsordersharetoRepository
					.findByUsersharedonAndSitecodeAndOrdertypeAndSharestatusAndSharedonBetweenOrderBySharetocodeDesc(
							objorder.getLsuserMaster(), objorder.getLsuserMaster().getLssitemaster().getSitecode(),
							filetype, 1, fromdate, todate);
		}

		List<OrderShareInterface> lstorders = sharedtoobj.stream()
				.map(Lsordershareto.LsordersharetoProjection::getOrder).collect(Collectors.toList());

		List<Long> batchcode = lstorders.stream().map(OrderShareInterface::getBatchcode).collect(Collectors.toList());

		if (objorder.getFiletype() != null && objorder.getFiletype() == -1) {
			lstorder1 = lslogilablimsorderdetailRepository
					.findByOrderflagAndBatchcodeInOrderByBatchcodeDesc(objorder.getOrderflag(), batchcode);

		} else if (objorder.getFiletype() != null) {
			lstorder1 = lslogilablimsorderdetailRepository
					.findByOrderflagAndBatchcodeInAndFiletype(objorder.getOrderflag(), batchcode, filetype);
		}

		Set<Long> matchedBatchcodes = new HashSet<>();
		lstorder1.stream().map(Logilaborderssh::getBc).forEach(matchedBatchcodes::add);

		if (filetype == -1 && objorder.getOrderflag() == null) {
			lstorder = lslogilablimsorderdetailRepository
					.findByOrderflagAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndOrdercancellIsNull(
							objorder.getOrderflag(), lstproject, fromdate, todate);
			int chunkSize = Integer.parseInt(env.getProperty("lssamplecount"));
			int totalSamples = nmaterialcode.size();
			List<Logilaborderssh> lstorderobj = IntStream.range(0, (totalSamples + chunkSize - 1) / chunkSize)
					.parallel().mapToObj(i -> {
						int startIndex = i * chunkSize;
						int endIndex = Math.min(startIndex + chunkSize, totalSamples);
						List<Elnmaterial> currentChunk = nmaterialcode.subList(startIndex, endIndex);
						List<Logilaborderssh> orderChunk = new ArrayList<>();
						orderChunk.addAll(logilablimsorderdetailsRepository
								.findByOrderflagAndLsprojectmasterIsNullAndDirectorycodeIsNullAndElnmaterialInAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndOrdercancellIsNull(
										objorder.getOrderflag(), currentChunk, fromdate, todate, 1));
						orderChunk.addAll(logilablimsorderdetailsRepository
								.findByOrderflagAndLsprojectmasterIsNullAndDirectorycodeIsNullAndElnmaterialInAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndLsuserMasterAndOrdercancellIsNullOrderByBatchcodeDesc(
										objorder.getOrderflag(), currentChunk, fromdate, todate, 2,
										objorder.getLsuserMaster()));
						return orderChunk;
					}).flatMap(List::stream).collect(Collectors.toList());

			lstorder.addAll(lstorderobj);

		} else if (filetype == -1 && objorder.getOrderflag() != null) {

			lstorder = lslogilablimsorderdetailRepository
					.findByOrderflagAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndOrdercancellIsNull(
							objorder.getOrderflag(), lstproject, fromdate, todate);
			int chunkSize = Integer.parseInt(env.getProperty("lssamplecount"));
			int totalSamples = nmaterialcode.size();
			List<Logilaborderssh> lstorderobj = IntStream.range(0, (totalSamples + chunkSize - 1) / chunkSize)
					.parallel().mapToObj(i -> {
						int startIndex = i * chunkSize;
						int endIndex = Math.min(startIndex + chunkSize, totalSamples);
						List<Elnmaterial> currentChunk = nmaterialcode.subList(startIndex, endIndex);
						List<Logilaborderssh> orderChunk = new ArrayList<>();
						orderChunk.addAll(logilablimsorderdetailsRepository
								.findByOrderflagAndLsprojectmasterIsNullAndDirectorycodeIsNullAndElnmaterialInAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndOrdercancellIsNull(
										objorder.getOrderflag(), currentChunk, fromdate, todate, 1));
						orderChunk.addAll(logilablimsorderdetailsRepository
								.findByOrderflagAndLsprojectmasterIsNullAndDirectorycodeIsNullAndElnmaterialInAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndLsuserMasterAndOrdercancellIsNullOrderByBatchcodeDesc(
										objorder.getOrderflag(), currentChunk, fromdate, todate, 2,
										objorder.getLsuserMaster()));
						return orderChunk;
					}).flatMap(List::stream).collect(Collectors.toList());
			lstorder.addAll(lstorderobj);

			if (objorder.getLstuserMaster() != null) {
				/** existing **/
//				lstorder.addAll(lslogilablimsorderdetailRepository
//						.findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndOrdercancellIsNullOrderByBatchcodeDesc(
//								Directory_Code, 1, fromdate, todate, objorder.getOrderflag(), Directory_Code, 2,
//								objorder.getLsuserMaster(), fromdate, todate, objorder.getOrderflag(), Directory_Code,
//								3, fromdate, todate, objorder.getLstuserMaster(), objorder.getOrderflag()));

				/** changed for project team selection **/
				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndOrdercancellIsNullAndTeamselectedOrTeamselectedAndBatchcodeInAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndOrdercancellIsNullOrderByBatchcodeDesc(
								Directory_Code, 1, fromdate, todate, objorder.getOrderflag(), Directory_Code, 2,
								objorder.getLsuserMaster(), fromdate, todate, objorder.getOrderflag(), Directory_Code,
								3, fromdate, todate, objorder.getLstuserMaster(), objorder.getOrderflag(), false, true,
								selectedteambatchCodeList, Directory_Code, 3, fromdate, todate,
								objorder.getLstuserMaster(), objorder.getOrderflag()));

			} else {
				/** existing **/
//				lstorder.addAll(lslogilablimsorderdetailRepository
//						.findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndOrdercancellIsNullOrderByBatchcodeDesc(
//								Directory_Code, 1, fromdate, todate, objorder.getOrderflag(), Directory_Code, 2,
//								objorder.getLsuserMaster(), fromdate, todate, objorder.getOrderflag(), Directory_Code,
//								3, objorder.getLsuserMaster(), fromdate, todate, objorder.getOrderflag()));

				/** changed for project team selection **/
				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndOrdercancellIsNullAndTeamselectedOrTeamselectedAndBatchcodeInAndDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndOrdercancellIsNullOrderByBatchcodeDesc(
								Directory_Code, 1, fromdate, todate, objorder.getOrderflag(), Directory_Code, 2,
								objorder.getLsuserMaster(), fromdate, todate, objorder.getOrderflag(), Directory_Code,
								3, objorder.getLsuserMaster(), fromdate, todate, objorder.getOrderflag(), false, true,
								selectedteambatchCodeList, Directory_Code, 3, objorder.getLsuserMaster(), fromdate,
								todate, objorder.getOrderflag()));

			}
		} else if (objorder.getOrderflag() != null && objorder.getApprovelstatus() != null
				&& objorder.getApprovelstatus() == 3) {

			lstorder = lslogilablimsorderdetailRepository
					.findByOrderflagAndApprovelstatusAndLsprojectmasterInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndOrdercancellIsNull(
							objorder.getOrderflag(), objorder.getApprovelstatus(), lstproject, filetype, fromdate,
							todate);

			int chunkSize = Integer.parseInt(env.getProperty("lssamplecount"));
			int totalSamples = nmaterialcode.size();
			List<Logilaborderssh> lstorderobj = IntStream.range(0, (totalSamples + chunkSize - 1) / chunkSize)
					.parallel().mapToObj(i -> {
						int startIndex = i * chunkSize;
						int endIndex = Math.min(startIndex + chunkSize, totalSamples);
						List<Elnmaterial> currentChunk = nmaterialcode.subList(startIndex, endIndex);
						List<Logilaborderssh> orderChunk = new ArrayList<>();
						orderChunk.addAll(logilablimsorderdetailsRepository
								.findByOrderflagAndApprovelstatusAndLsprojectmasterIsNullAndDirectorycodeIsNullAndElnmaterialInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndOrdercancellIsNull(
										objorder.getOrderflag(), objorder.getApprovelstatus(), currentChunk, filetype,
										fromdate, todate, 1));
						orderChunk.addAll(logilablimsorderdetailsRepository
								.findByOrderflagAndApprovelstatusAndLsprojectmasterIsNullAndDirectorycodeIsNullAndElnmaterialInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndLsuserMasterAndOrdercancellIsNullOrderByBatchcodeDesc(
										objorder.getOrderflag(), objorder.getApprovelstatus(), currentChunk, filetype,
										fromdate, todate, 2, objorder.getLsuserMaster()));
						return orderChunk;
					}).flatMap(List::stream).collect(Collectors.toList()); // kumu
			lstorder.addAll(lstorderobj);
			if (objorder.getLstuserMaster() != null) {
				/** existing **/
//				lstorder.addAll(lslogilablimsorderdetailRepository
//						.findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrderByBatchcodeDesc(
//								Directory_Code, 1, fromdate, todate, objorder.getOrderflag(), filetype,
//								objorder.getApprovelstatus(), Directory_Code, 2, objorder.getLsuserMaster(), fromdate,
//								todate, objorder.getOrderflag(), filetype, objorder.getApprovelstatus(), Directory_Code,
//								3, fromdate, todate, objorder.getLstuserMaster(), objorder.getOrderflag(), filetype,
//								objorder.getApprovelstatus()));

				/** changed for project team selection **/
				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullAndTeamselectedOrTeamselectedAndBatchcodeInAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrderByBatchcodeDesc(
								Directory_Code, 1, fromdate, todate, objorder.getOrderflag(), filetype,
								objorder.getApprovelstatus(), Directory_Code, 2, objorder.getLsuserMaster(), fromdate,
								todate, objorder.getOrderflag(), filetype, objorder.getApprovelstatus(), Directory_Code,
								3, fromdate, todate, objorder.getLstuserMaster(), objorder.getOrderflag(), filetype,
								objorder.getApprovelstatus(), false, true, selectedteambatchCodeList, Directory_Code, 3,
								fromdate, todate, objorder.getLstuserMaster(), objorder.getOrderflag(), filetype,
								objorder.getApprovelstatus()));

			} else {

				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullAndTeamselectedOrTeamselectedAndBatchcodeInAndDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrderByBatchcodeDesc(
								Directory_Code, 1, fromdate, todate, objorder.getOrderflag(), filetype,
								objorder.getApprovelstatus(), Directory_Code, 2, objorder.getLsuserMaster(), fromdate,
								todate, objorder.getOrderflag(), filetype, objorder.getApprovelstatus(), Directory_Code,
								3, objorder.getLsuserMaster(), fromdate, todate, objorder.getOrderflag(), filetype,
								objorder.getApprovelstatus(), false, true, selectedteambatchCodeList, Directory_Code, 3,
								objorder.getLsuserMaster(), fromdate, todate, objorder.getOrderflag(), filetype,
								objorder.getApprovelstatus()));

			}

		} else if (objorder.getOrderflag() != null && objorder.getApprovelstatus() != null
				&& objorder.getApprovelstatus() == 1) {

			lstorder = lslogilablimsorderdetailRepository
					.findByOrderflagAndApprovelstatusAndLsprojectmasterInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndOrdercancellIsNull(
							objorder.getOrderflag(), objorder.getApprovelstatus(), lstproject, filetype, fromdate,
							todate);

			int chunkSize = Integer.parseInt(env.getProperty("lssamplecount"));
			int totalSamples = nmaterialcode.size();
			List<Logilaborderssh> lstorderobj = IntStream.range(0, (totalSamples + chunkSize - 1) / chunkSize)
					.parallel().mapToObj(i -> {
						int startIndex = i * chunkSize;
						int endIndex = Math.min(startIndex + chunkSize, totalSamples);
						List<Elnmaterial> currentChunk = nmaterialcode.subList(startIndex, endIndex);
						List<Logilaborderssh> orderChunk = new ArrayList<>();
						orderChunk.addAll(logilablimsorderdetailsRepository
								.findByOrderflagAndApprovelstatusAndLsprojectmasterIsNullAndDirectorycodeIsNullAndElnmaterialInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndOrdercancellIsNull(
										objorder.getOrderflag(), objorder.getApprovelstatus(), currentChunk, filetype,
										fromdate, todate, 1));
						orderChunk.addAll(logilablimsorderdetailsRepository
								.findByOrderflagAndApprovelstatusAndLsprojectmasterIsNullAndDirectorycodeIsNullAndElnmaterialInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndLsuserMasterAndOrdercancellIsNullOrderByBatchcodeDesc(
										objorder.getOrderflag(), objorder.getApprovelstatus(), currentChunk, filetype,
										fromdate, todate, 2, objorder.getLsuserMaster()));
						return orderChunk;
					}).flatMap(List::stream).collect(Collectors.toList());
			lstorder.addAll(lstorderobj);

			if (objorder.getLstuserMaster() != null) {
				/** existing **/
//				lstorder.addAll(lslogilablimsorderdetailRepository
//						.findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrderByBatchcodeDesc(
//								Directory_Code, 1, fromdate, todate, objorder.getOrderflag(), filetype,
//								objorder.getApprovelstatus(), Directory_Code, 2, objorder.getLsuserMaster(), fromdate,
//								todate, objorder.getOrderflag(), filetype, objorder.getApprovelstatus(), Directory_Code,
//								3, fromdate, todate, objorder.getLstuserMaster(), objorder.getOrderflag(), filetype,
//								objorder.getApprovelstatus()));
				/** changed for p.team selection **/
				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullAndTeamselectedOrTeamselectedAndBatchcodeInAndDirectorycodeInAndViewoptionAndLsuserMasterInAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrderByBatchcodeDesc(
								Directory_Code, 1, fromdate, todate, objorder.getOrderflag(), filetype,
								objorder.getApprovelstatus(), Directory_Code, 2, objorder.getLsuserMaster(), fromdate,
								todate, objorder.getOrderflag(), filetype, objorder.getApprovelstatus(), Directory_Code,
								3, fromdate, todate, objorder.getLstuserMaster(), objorder.getOrderflag(), filetype,
								objorder.getApprovelstatus(), false, true, selectedteambatchCodeList, Directory_Code, 3,
								objorder.getLstuserMaster(), fromdate, todate, objorder.getOrderflag(), filetype,
								objorder.getApprovelstatus()));

			} else {
				/** existing **/
//				lstorder.addAll(lslogilablimsorderdetailRepository
//						.findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrderByBatchcodeDesc(
//								Directory_Code, 1, fromdate, todate, objorder.getOrderflag(), filetype,
//								objorder.getApprovelstatus(), Directory_Code, 2, objorder.getLsuserMaster(), fromdate,
//								todate, objorder.getOrderflag(), filetype, objorder.getApprovelstatus(), Directory_Code,
//								3, objorder.getLsuserMaster(), fromdate, todate, objorder.getOrderflag(), filetype,
//								objorder.getApprovelstatus()));

				/** changed for p.team selection **/
				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullAndTeamselectedOrTeamselectedAndBatchcodeInAndDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndApprovelstatusAndOrdercancellIsNullOrderByBatchcodeDesc(
								Directory_Code, 1, fromdate, todate, objorder.getOrderflag(), filetype,
								objorder.getApprovelstatus(), Directory_Code, 2, objorder.getLsuserMaster(), fromdate,
								todate, objorder.getOrderflag(), filetype, objorder.getApprovelstatus(), Directory_Code,
								3, objorder.getLsuserMaster(), fromdate, todate, objorder.getOrderflag(), filetype,
								objorder.getApprovelstatus(), true, true, selectedteambatchCodeList, Directory_Code, 3,
								objorder.getLsuserMaster(), fromdate, todate, objorder.getOrderflag(), filetype,
								objorder.getApprovelstatus()));

			}

		} else if (testcode != null && testcode != -1 && objorder.getLsprojectmaster() == null) {
			lstorder = lslogilablimsorderdetailRepository
					.findByOrderflagAndLsprojectmasterInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndTestcodeAndOrdercancellIsNullOrderByBatchcodeDesc(
							objorder.getOrderflag(), lstproject, filetype, fromdate, todate, testcode);
			int chunkSize = Integer.parseInt(env.getProperty("lssamplecount"));
			int totalSamples = nmaterialcode.size();
			List<Logilaborderssh> lstorderobj = IntStream.range(0, (totalSamples + chunkSize - 1) / chunkSize)
					.parallel().mapToObj(i -> {
						int startIndex = i * chunkSize;
						int endIndex = Math.min(startIndex + chunkSize, totalSamples);
						List<Elnmaterial> currentChunk = nmaterialcode.subList(startIndex, endIndex);
						List<Logilaborderssh> orderChunk = new ArrayList<>();
						orderChunk.addAll(logilablimsorderdetailsRepository
								.findByOrderflagAndTestcodeAndLsprojectmasterIsNullAndDirectorycodeIsNullAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndElnmaterialInOrOrderflagAndTestcodeAndLsprojectmasterIsNullAndDirectorycodeIsNullAndElnmaterialInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoption(
										objorder.getOrderflag(), testcode, filetype, fromdate, todate, currentChunk,
										objorder.getOrderflag(), testcode, currentChunk, filetype, fromdate, todate,
										1));
						orderChunk.addAll(logilablimsorderdetailsRepository
								.findByOrderflagAndTestcodeAndLsprojectmasterIsNullAndDirectorycodeIsNullAndElnmaterialInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndLsuserMasterOrderByBatchcodeDesc(
										objorder.getOrderflag(), testcode, currentChunk, filetype, fromdate, todate, 2,
										objorder.getLsuserMaster()));
						return orderChunk;
					}).flatMap(List::stream).collect(Collectors.toList());
			lstorder.addAll(lstorderobj);

			if (objorder.getLstuserMaster() != null) {
//				lstorder.addAll(lslogilablimsorderdetailRepository
//						.findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndTestcodeOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndTestcodeOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndTestcodeOrderByBatchcodeDesc(
//								Directory_Code, 1, fromdate, todate, objorder.getOrderflag(), filetype, testcode,
//								Directory_Code, 2, objorder.getLsuserMaster(), fromdate, todate,
//								objorder.getOrderflag(), filetype, testcode, Directory_Code, 3, fromdate, todate,
//								objorder.getLstuserMaster(), objorder.getOrderflag(), filetype, testcode));

				/** changed for p.team selection **/
				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndTestcodeOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndTestcodeOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndTestcodeAndTeamselectedOrTeamselectedAndBatchcodeInAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndTestcodeOrderByBatchcodeDesc(
								Directory_Code, 1, fromdate, todate, objorder.getOrderflag(), filetype, testcode,
								Directory_Code, 2, objorder.getLsuserMaster(), fromdate, todate,
								objorder.getOrderflag(), filetype, testcode, Directory_Code, 3, fromdate, todate,
								objorder.getLstuserMaster(), objorder.getOrderflag(), filetype, testcode, false, true,
								selectedteambatchCodeList, Directory_Code, 3, fromdate, todate,
								objorder.getLstuserMaster(), objorder.getOrderflag(), filetype, testcode));

			} else {

//				lstorder.addAll(lslogilablimsorderdetailRepository
//						.findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndTestcodeOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndTestcodeOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndTestcodeOrderByBatchcodeDesc(
//								Directory_Code, 1, fromdate, todate, objorder.getOrderflag(), filetype, testcode,
//								Directory_Code, 2, objorder.getLsuserMaster(), fromdate, todate,
//								objorder.getOrderflag(), filetype, testcode, Directory_Code, 3,
//								objorder.getLsuserMaster(), fromdate, todate, objorder.getOrderflag(), filetype,
//								testcode));

				/** change for p.team selection **/

				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndTestcodeOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndTestcodeOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndTestcodeAndTeamselectedOrTeamselectedAndBatchcodeInAndDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndTestcodeOrderByBatchcodeDesc(
								Directory_Code, 1, fromdate, todate, objorder.getOrderflag(), filetype, testcode,
								Directory_Code, 2, objorder.getLsuserMaster(), fromdate, todate,
								objorder.getOrderflag(), filetype, testcode, Directory_Code, 3,
								objorder.getLsuserMaster(), fromdate, todate, objorder.getOrderflag(), filetype,
								testcode, false, true, selectedteambatchCodeList, Directory_Code, 3,
								objorder.getLsuserMaster(), fromdate, todate, objorder.getOrderflag(), filetype,
								testcode));

			}

			matchedBatchcodes.clear();
			lstorder1.stream().filter(order -> order.getTc().equals(testcode)).map(Logilaborderssh::getBc)
					.forEach(matchedBatchcodes::add);

		} else if (objorder.getLsprojectmaster() != null && objorder.getLsprojectmaster().getProjectcode() != -1
				&& testcode != null && testcode != -1) {

			lstorder = lslogilablimsorderdetailRepository
					.findByOrderflagAndLsprojectmasterInAndTestcodeAndFiletypeAndLsprojectmasterAndCreatedtimestampBetweenAndAssignedtoIsNull(
							objorder.getOrderflag(), lstproject, testcode, filetype, objorder.getLsprojectmaster(),
							fromdate, todate);

			int chunkSize = Integer.parseInt(env.getProperty("lssamplecount"));
			int totalSamples = nmaterialcode.size();
			List<Logilaborderssh> lstorderobj = IntStream.range(0, (totalSamples + chunkSize - 1) / chunkSize)
					.parallel().mapToObj(i -> {
						int startIndex = i * chunkSize;
						int endIndex = Math.min(startIndex + chunkSize, totalSamples);
						List<Elnmaterial> currentChunk = nmaterialcode.subList(startIndex, endIndex);
						List<Logilaborderssh> orderChunk = new ArrayList<>();
						orderChunk.addAll(logilablimsorderdetailsRepository
								.findByOrderflagAndLsprojectmasterIsNullAndTestcodeAndElnmaterialInAndFiletypeAndLsprojectmasterAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoption(
										objorder.getOrderflag(), testcode, currentChunk, filetype,
										objorder.getLsprojectmaster(), fromdate, todate, 1));
						orderChunk.addAll(logilablimsorderdetailsRepository
								.findByOrderflagAndLsprojectmasterIsNullAndTestcodeAndElnmaterialInAndFiletypeAndLsprojectmasterAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndLsuserMasterOrderByBatchcodeDesc(
										objorder.getOrderflag(), testcode, currentChunk, filetype,
										objorder.getLsprojectmaster(), fromdate, todate, 2,
										objorder.getLsuserMaster()));
						return orderChunk;
					}).flatMap(List::stream).collect(Collectors.toList());
			lstorder.addAll(lstorderobj);

			matchedBatchcodes.clear();
			lstorder1.stream()
					.filter(order -> order.getTc().equals(testcode) && order.getPn() != null
							&& order.getPn().equals(objorder.getLsprojectmaster().getProjectname()))
					.map(Logilaborderssh::getBc).forEach(matchedBatchcodes::add);
		} else if (objorder.getLsprojectmaster() != null && objorder.getLsprojectmaster().getProjectcode() != -1) {
			lstorder = LogilablimsorderdetailsRepository
					.findByOrderflagAndFiletypeAndLsprojectmasterAndCreatedtimestampBetweenAndAssignedtoIsNull(
							objorder.getOrderflag(), filetype, objorder.getLsprojectmaster(), fromdate, todate);

			matchedBatchcodes.clear();
			lstorder1.stream()
					.filter(order -> order.getPn() != null
							&& order.getPn().equals(objorder.getLsprojectmaster().getProjectname()))
					.map(Logilaborderssh::getBc).forEach(matchedBatchcodes::add);
		} else if (filetype == 0 && objorder.getOrderflag() != null) {
			lstorder = lslogilablimsorderdetailRepository
					.findByCreatedtimestampBetweenAndFiletypeAndOrderflagAndAssignedtoIsNullOrderByBatchcodeDesc(
							fromdate, todate, filetype, objorder.getOrderflag());
		}

		else {
			if (objorder.getLstuserMaster() != null) {
				lstorder = logilablimsorderdetailsRepository
						.findByOrderflagAndLsprojectmasterInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndOrdercancellIsNullOrderByBatchcodeDesc(
								objorder.getOrderflag(), lstproject, filetype, fromdate, todate);

				int chunkSize = Integer.parseInt(env.getProperty("lssamplecount"));
				int totalSamples = nmaterialcode.size();

				List<Logilaborderssh> lstorderobj = IntStream.range(0, (totalSamples + chunkSize - 1) / chunkSize)
						.parallel().mapToObj(i -> {
							int startIndex = i * chunkSize;
							int endIndex = Math.min(startIndex + chunkSize, totalSamples);
							List<Elnmaterial> currentChunk = nmaterialcode.subList(startIndex, endIndex);

							List<Logilaborderssh> orderChunk = new ArrayList<>();

							orderChunk.addAll(logilablimsorderdetailsRepository
									.findByOrderflagAndLsprojectmasterIsNullAndDirectorycodeIsNullAndElnmaterialInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndLsuserMasterAndOrdercancellIsNull(
											objorder.getOrderflag(), currentChunk, filetype, fromdate, todate, 2,
											objorder.getLsuserMaster()));

							orderChunk.addAll(logilablimsorderdetailsRepository
									.findByOrderflagAndLsprojectmasterIsNullAndDirectorycodeIsNullAndElnmaterialInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndOrdercancellIsNull(
											objorder.getOrderflag(), currentChunk, filetype, fromdate, todate, 1));

							/** existing **/
//							orderChunk.addAll(lslogilablimsorderdetailRepository
//									.findByOrderflagAndLsprojectmasterIsNullAndDirectorycodeIsNullAndElnmaterialInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndLsuserMasterAndOrdercancellIsNullOrderByBatchcodeDesc(
//											objorder.getOrderflag(), currentChunk, filetype, fromdate, todate, 3,
//											objorder.getLsuserMaster()));

							/** change for p.team selection **/
							orderChunk.addAll(logilablimsorderdetailsRepository
									.findByOrderflagAndLsprojectmasterIsNullAndElnmaterialInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndOrdercancellIsNullAndTeamselectedOrTeamselectedAndBatchcodeInAndOrderflagAndLsprojectmasterIsNullAndElnmaterialInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndOrdercancellIsNullOrderByBatchcodeDesc(
											objorder.getOrderflag(), currentChunk, filetype, fromdate, todate, 3, false,
											true, selectedteambatchCodeList, objorder.getOrderflag(), currentChunk,
											filetype, fromdate, todate, 3));

							return orderChunk;
						}).flatMap(List::stream).collect(Collectors.toList());

				lstorder.addAll(lstorderobj);

				if (Directory_Code != null) { // change now
					/** existing **/
//					lstorder.addAll(lslogilablimsorderdetailRepository
//							.findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullAndOrdercancellIsNullOrderByBatchcodeDesc(
//									Directory_Code, 1, fromdate, todate, objorder.getOrderflag(), filetype,
//									Directory_Code, 2, objorder.getLsuserMaster(), fromdate, todate,objorder.getOrderflag(), filetype,
//									Directory_Code, 3, fromdate, todate,objorder.getLstuserMaster(), objorder.getOrderflag(), filetype));
					/** Changed for p.team selection **/

					lstorder.addAll(logilablimsorderdetailsRepository
							.findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullAndOrdercancellIsNullOrTeamselectedAndBatchcodeInAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndElnmaterialIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndTeamselectedAndAssignedtoIsNullAndElnmaterialIsNullAndOrdercancellIsNullOrderByBatchcodeDesc(
									Directory_Code, 1, fromdate, todate, objorder.getOrderflag(), filetype,
									Directory_Code, 2, objorder.getLsuserMaster(), fromdate, todate,
									objorder.getOrderflag(), filetype, true, selectedteambatchCodeList, Directory_Code,
									3, fromdate, todate, objorder.getLstuserMaster(), objorder.getOrderflag(), filetype,
									Directory_Code, 3, fromdate, todate, objorder.getLstuserMaster(),
									objorder.getOrderflag(), filetype, false));
				}

				lstorder.forEach(objorderDetail -> objorderDetail.setLw(objorder.getLstworkflow()));

				lstorder.stream().map(Logilaborderssh::getBc).collect(Collectors.toList());

			} else {
				lstorder = lslogilablimsorderdetailRepository
						.findByOrderflagAndLsprojectmasterInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNull(
								objorder.getOrderflag(), lstproject, filetype, fromdate, todate);

				lstorder.addAll(logilablimsorderdetailsRepository
						.findByOrderflagAndLsprojectmasterIsNullAndDirectorycodeIsNullAndElnmaterialInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndLsuserMasterAndOrdercancellIsNull(
								objorder.getOrderflag(), nmaterialcode, filetype, fromdate, todate, 2,
								objorder.getLsuserMaster()));

				lstorder.addAll(logilablimsorderdetailsRepository
						.findByOrderflagAndLsprojectmasterIsNullAndDirectorycodeIsNullAndElnmaterialInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndOrdercancellIsNull(
								objorder.getOrderflag(), nmaterialcode, filetype, fromdate, todate, 1));

				/** existing **/
//				lstorder.addAll(lslogilablimsorderdetailRepository
//						.findByOrderflagAndLsprojectmasterIsNullAndDirectorycodeIsNullAndElnmaterialInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndLsuserMasterAndOrdercancellIsNullOrderByBatchcodeDesc(
//								objorder.getOrderflag(), nmaterialcode, filetype, fromdate, todate, 3,
//								objorder.getLsuserMaster()));

				/** changed for p.team selection **/
				lstorder.addAll(logilablimsorderdetailsRepository
						.findByOrderflagAndLsprojectmasterIsNullAndDirectorycodeIsNullAndElnmaterialInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndLsuserMasterAndOrdercancellIsNullAndTeamselectedOrTeamselectedAndBatchcodeInAndOrderflagAndLsprojectmasterIsNullAndDirectorycodeIsNullAndElnmaterialInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndOrdercancellIsNullOrderByBatchcodeDesc(
								objorder.getOrderflag(), nmaterialcode, filetype, fromdate, todate, 3,
								objorder.getLsuserMaster(), false, true, selectedteambatchCodeList,
								objorder.getOrderflag(), nmaterialcode, filetype, fromdate, todate, 3));

				if (Directory_Code != null) {
					/** existing **/
//					lstorder.addAll(lslogilablimsorderdetailRepository
//							.findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullOrderByBatchcodeDesc(
//									Directory_Code, 1, fromdate, todate, objorder.getOrderflag(), filetype,
//									Directory_Code, 2, objorder.getLsuserMaster(), fromdate, todate,
//									objorder.getOrderflag(), filetype, Directory_Code, 3, objorder.getLsuserMaster(),
//									fromdate, todate, objorder.getOrderflag(), filetype));
					/** changed for p.team selection **/
					lstorder.addAll(logilablimsorderdetailsRepository
							.findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullAndTeamselectedOrTeamselectedAndBatchcodeInAndDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullOrderByBatchcodeDesc(
									Directory_Code, 1, fromdate, todate, objorder.getOrderflag(), filetype,
									Directory_Code, 2, objorder.getLsuserMaster(), fromdate, todate,
									objorder.getOrderflag(), filetype, Directory_Code, 3, objorder.getLsuserMaster(),
									fromdate, todate, objorder.getOrderflag(), filetype, false, true,
									selectedteambatchCodeList, Directory_Code, 3, objorder.getLsuserMaster(), fromdate,
									todate, objorder.getOrderflag(), filetype));

				}

			}

		}

		if (objorder.getSearchCriteria().getContentsearchtype() != null
				&& objorder.getSearchCriteria().getContentsearch() != null) {
			List<Long> lstBatchcode = lstorder.stream().map(Logilaborderssh::getBc).collect(Collectors.toList());
			if (lstBatchcode != null && lstBatchcode.size() > 0) {
				lstorder = Onsearchordercontent(lstBatchcode, objorder);
			}
		}
		List<Lsordershareto.LsordersharetoProjection> filteredSharedTo = sharedtoobj.stream()
				.filter(obj -> matchedBatchcodes.contains(obj.getSharebatchcode())).collect(Collectors.toList());
		if (!lstorder.isEmpty()) {
			lstorder = lstorder.stream().filter(o -> o.getOc() == null || o.getOc() != 1)
					.collect(Collectors.toCollection(ArrayList::new));
			if (objorder.getFilefor().equals("ECO") || objorder.getFilefor().equals("RCO")
					|| objorder.getFilefor().equals("MCO") || objorder.getFilefor().equals("SCO")) {
				lstorder = lstorder.stream().filter(o -> o.getAs() == null || o.getAs() != 3)
						.collect(Collectors.toCollection(ArrayList::new));
				lstorder1 = lstorder1.stream().filter(o -> o.getAs() == null || o.getAs() != 3)
						.collect(Collectors.toCollection(ArrayList::new));

				filteredSharedTo = filteredSharedTo.stream().filter(obj -> obj.getOrder() != null
						&& (obj.getOrder().getApprovelstatus() == null || obj.getOrder().getApprovelstatus() != 3))
						.collect(Collectors.toList());
			}
//			else if(objorder.getFilefor().equals("RJO")) {
//				lstorder = lstorder.stream()
//		                   .filter(o -> o.getAs() != null && o.getAs() == 3)
//		                   .collect(Collectors.toCollection(ArrayList::new));
//				lstorder1 =lstorder1.stream()
//		                   .filter(o -> o.getAs() != null && o.getAs() == 3)
//		                   .collect(Collectors.toCollection(ArrayList::new));
//				
//				filteredSharedTo =filteredSharedTo.stream()
//		                .filter(obj -> obj.getOrder() !=null && obj.getOrder().getApprovelstatus() != null && obj.getOrder().getApprovelstatus() == 3)
//		                .collect(Collectors.toList());
//				
//			}
		}
		List<LSworkflow> workflowobj = lsworkflowRepository
				.findByLssitemasterAndStatusOrderByWorkflowcodeAsc(objorder.getLsuserMaster().getLssitemaster(), 1);

		lstorder.forEach(objorderDetail -> objorderDetail.setLw(objorder.getLstworkflow(), workflowobj,
				objorder.getLsuserMaster()));

		rtn_object.put("Orders", lstorder);

		rtn_object.put("sharedmeorders", lstorder1.stream().filter(obj1 -> matchedBatchcodes.contains(obj1.getBc()))
				.collect(Collectors.toList()));
		rtn_object.put("sharedtome", filteredSharedTo);

//		rtn_object.get("Orders")
		return rtn_object;

	}

	public List<Logilaborderssh> Onsearchordercontent(List<Long> lstBatchcode, LSlogilablimsorderdetail objorder) {
		List<Integer> lstsamplefilecode = new ArrayList<Integer>();
		lstsamplefilecode = lssamplefileRepository.getFilesamplecodeByBatchcodeIn(lstBatchcode);
		List<Logilaborderssh> lstorder = new ArrayList<Logilaborderssh>();
		List<LSsamplefile> idList = new ArrayList<LSsamplefile>();
		if (lstsamplefilecode != null && lstsamplefilecode.size() > 0) {
			idList = getsamplefileIds(lstsamplefilecode, objorder.getSearchCriteria(), objorder.getIsmultitenant());
			if (idList != null && idList.size() > 0) {

				if (objorder.getFiletype() == -1 && objorder.getOrderflag() != null) {
					lstorder = lslogilablimsorderdetailRepository
							.findByCreatedtimestampBetweenAndOrderflagAndLssamplefileIn(objorder.getFromdate(),
									objorder.getTodate(), objorder.getOrderflag(), idList);
				} else if (objorder.getFiletype() == -1 && objorder.getOrderflag() == null) {
					lstorder = lslogilablimsorderdetailRepository.findByLssamplefileInAndCreatedtimestampBetween(idList,
							objorder.getFromdate(), objorder.getTodate());
				}

				else if (objorder.getOrderflag() == null) {
					lstorder = lslogilablimsorderdetailRepository
							.findByFiletypeAndLssamplefileInAndCreatedtimestampBetween(objorder.getFiletype(), idList,
									objorder.getFromdate(), objorder.getTodate());
				} else {
					lstorder = lslogilablimsorderdetailRepository
							.findByOrderflagAndFiletypeAndLssamplefileInAndCreatedtimestampBetween(
									objorder.getOrderflag(), objorder.getFiletype(), idList, objorder.getFromdate(),
									objorder.getTodate());
				}

			}

		}

		return lstorder;
	}

	public Map<String, Object> Getprotocolordersonproject(LSlogilabprotocoldetail objorder) {
		Map<String, Object> retuobjts = new HashMap<>();
		List<Logilabprotocolorders> lstorder = new ArrayList<Logilabprotocolorders>();
		Date fromdate = objorder.getFromdate();
		Date todate = objorder.getTodate();
		Integer protocoltype = objorder.getProtocoltype();
//		if (protocoltype == -1 && objorder.getOrderflag() == null) {
//			lstorder = LSlogilabprotocoldetailRepository
//					.findByLsprojectmasterAndTestcodeAndOrderdisplaytypeAndAssignedtoIsNullAndCreatedtimestampBetweenOrLsprojectmasterAndTestcodeAndOrderdisplaytypeAndAssignedtoIsNullAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
//							objorder.getLsprojectmaster(), objorder.getTestcode(), 1, fromdate, todate,
//							objorder.getLsprojectmaster(), objorder.getTestcode(), 2, fromdate, todate);
//		} else if (protocoltype != -1 && objorder.getOrderflag() != null) {
//			if (objorder.getRejected() != null) {
//				lstorder = LSlogilabprotocoldetailRepository
//						.findByLsprojectmasterAndTestcodeAndOrderdisplaytypeAndAssignedtoIsNullAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
//								objorder.getLsprojectmaster(), objorder.getTestcode(), 1, protocoltype,
//								objorder.getOrderflag(), 1, fromdate, todate);
//			} else {
//				lstorder = LSlogilabprotocoldetailRepository
//						.findByLsprojectmasterAndTestcodeAndOrderdisplaytypeAndAssignedtoIsNullAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
//								objorder.getLsprojectmaster(), objorder.getTestcode(), 1, protocoltype,
//								objorder.getOrderflag(), fromdate, todate);
//			}
//		} else if (protocoltype == -1 && objorder.getOrderflag() != null) {
//
//			if (objorder.getRejected() != null) {
//				lstorder = LSlogilabprotocoldetailRepository
//						.findByLsprojectmasterAndTestcodeAndOrderdisplaytypeAndAssignedtoIsNullAndOrderflagAndRejectedAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
//								objorder.getLsprojectmaster(), objorder.getTestcode(), 1, objorder.getOrderflag(), 1,
//								fromdate, todate);
//			}
//
//			lstorder = LSlogilabprotocoldetailRepository
//					.findByLsprojectmasterAndTestcodeAndOrderdisplaytypeAndAssignedtoIsNullAndOrderflagAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
//							objorder.getLsprojectmaster(), objorder.getTestcode(), 1, objorder.getOrderflag(), fromdate,
//							todate);
//		} else if (protocoltype != -1 && objorder.getOrderflag() == null) {
//			lstorder = LSlogilabprotocoldetailRepository
//					.findByLsprojectmasterAndTestcodeAndOrderdisplaytypeAndAssignedtoIsNullAndProtocoltypeAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
//							objorder.getLsprojectmaster(), objorder.getTestcode(), 1, protocoltype, fromdate, todate);
//		}

		if (protocoltype == -1 && objorder.getOrderflag() == null) {
			lstorder = LSlogilabprotocoldetailRepository
					.findByLsprojectmasterAndDirectorycodeIsNullAndOrderdisplaytypeAndAssignedtoIsNullAndCreatedtimestampBetweenOrLsprojectmasterAndDirectorycodeIsNullAndOrderdisplaytypeAndAssignedtoIsNullAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
							objorder.getLsprojectmaster(), 1, fromdate, todate, objorder.getLsprojectmaster(), 2,
							fromdate, todate);
		} else if (protocoltype != -1 && objorder.getOrderflag() != null) {
			if (objorder.getRejected() != null) {
				lstorder = LSlogilabprotocoldetailRepository
						.findByLsprojectmasterAndDirectorycodeIsNullAndOrderdisplaytypeAndAssignedtoIsNullAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
								objorder.getLsprojectmaster(), 1, protocoltype, objorder.getOrderflag(), 1, fromdate,
								todate);
			} else {
				lstorder = LSlogilabprotocoldetailRepository
						.findByLsprojectmasterAndDirectorycodeIsNullAndOrderdisplaytypeAndAssignedtoIsNullAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
								objorder.getLsprojectmaster(), 1, protocoltype, objorder.getOrderflag(), fromdate,
								todate);
			}
		} else if (protocoltype == -1 && objorder.getOrderflag() != null) {

			if (objorder.getRejected() != null) {
				lstorder = LSlogilabprotocoldetailRepository
						.findByLsprojectmasterAndDirectorycodeIsNullAndOrderdisplaytypeAndAssignedtoIsNullAndOrderflagAndRejectedAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
								objorder.getLsprojectmaster(), 1, objorder.getOrderflag(), 1, fromdate, todate);
			}

			lstorder = LSlogilabprotocoldetailRepository
					.findByLsprojectmasterAndDirectorycodeIsNullAndOrderdisplaytypeAndAssignedtoIsNullAndOrderflagAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
							objorder.getLsprojectmaster(), 1, objorder.getOrderflag(), fromdate, todate);
		} else if (protocoltype != -1 && objorder.getOrderflag() == null) {
			lstorder = LSlogilabprotocoldetailRepository
					.findByLsprojectmasterAndDirectorycodeIsNullAndOrderdisplaytypeAndAssignedtoIsNullAndProtocoltypeAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
							objorder.getLsprojectmaster(), 1, protocoltype, fromdate, todate);
		}

//		lstorder.forEach(objorderDetail -> objorderDetail.setLstworkflow(objorder.getLstworkflow()));
		Map<String, Object> obj = Getprotocolordersondirectory(objorder.getLsprotocolorderstructure());
		if (obj.containsKey("protocolorders")) {
			@SuppressWarnings("unchecked")
			List<LSlogilabprotocoldetail> protocolOrders = (List<LSlogilabprotocoldetail>) obj.get("protocolorders");
			lstorder.addAll(protocolOrders.stream().map(lsOrderDetail -> new Logilabprotocolorders(
					lsOrderDetail.getProtocolordercode(), lsOrderDetail.getTeamcode(),
					lsOrderDetail.getProtoclordername(), lsOrderDetail.getOrderflag(), lsOrderDetail.getProtocoltype(),
					lsOrderDetail.getCreatedtimestamp(), lsOrderDetail.getCompletedtimestamp(),
					lsOrderDetail.getLsprotocolmaster(), lsOrderDetail.getlSprotocolworkflow(),
					lsOrderDetail.getSample(), lsOrderDetail.getLsprojectmaster(), lsOrderDetail.getKeyword(),
					lsOrderDetail.getDirectorycode(), lsOrderDetail.getCreateby(), lsOrderDetail.getAssignedto(),
					lsOrderDetail.getLsrepositoriesdata(), lsOrderDetail.getLsrepositories(),
					lsOrderDetail.getElnmaterial(), lsOrderDetail.getElnmaterialinventory(),
					lsOrderDetail.getApproved(), lsOrderDetail.getRejected(), lsOrderDetail.getOrdercancell(),
					lsOrderDetail.getViewoption(), lsOrderDetail.getOrderstarted(), lsOrderDetail.getOrderstartedby(),
					lsOrderDetail.getOrderstartedon(), lsOrderDetail.getLockeduser(), lsOrderDetail.getLockedusername(),
					lsOrderDetail.getVersionno(), lsOrderDetail.getElnprotocolworkflow(),
					lsOrderDetail.getLsordernotification(), lsOrderDetail.getLsautoregister(),
					lsOrderDetail.getRepeat(), lsOrderDetail.getSentforapprovel(), lsOrderDetail.getApprovelaccept(),
					lsOrderDetail.getAutoregistercount(), lsOrderDetail.getLsuserMaster(),
					lsOrderDetail.getSequenceid(), lsOrderDetail.getModifiedby(), lsOrderDetail.getModifieddate()))
					.collect(Collectors.toList()));

		}

		lstorder.forEach(
				objorderDetail -> objorderDetail.setLstelnprotocolworkflow(objorder.getLstelnprotocolworkflow()));
		List<Long> protocolordercode = new ArrayList<>();
		if (lstorder.size() > 0 && objorder.getSearchCriteriaType() != null) {
			protocolordercode = lstorder.stream().map(Logilabprotocolorders::getProtocolordercode)
					.collect(Collectors.toList());
			retuobjts.put("protocolordercodeslist", protocolordercode);
		}
		retuobjts.put("protocolorders", lstorder);

		return retuobjts;

	}

	public Map<String, Object> Getuserprotocolorders(Map<String, Object> objusers) {
		Map<String, Object> mapuserorders = new HashMap<String, Object>();

		ObjectMapper mapper = new ObjectMapper();
		LSuserMaster lsloginuser = mapper.convertValue(objusers.get("loginuser"), LSuserMaster.class);
		LSuserMaster lsselecteduser = mapper.convertValue(objusers.get("selecteduser"), LSuserMaster.class);
		Date fromdate = lsselecteduser.getObjuser().getFromdate();
		Date todate = lsselecteduser.getObjuser().getTodate();
		Integer directory = mapper.convertValue(objusers.get("directorycode"), Integer.class);
		LSuserMaster lsselectedfulluser = lsuserMasterRepository.findByusercode(lsselecteduser.getUsercode());
		Integer protocoltype = mapper.convertValue(objusers.get("protocoltype"), Integer.class);
		String Orderflag = null;
		if (objusers.get("Orderflag") != null) {
			Orderflag = mapper.convertValue(objusers.get("Orderflag"), String.class);
		}

		if (lsloginuser.getUsercode() == lsselecteduser.getUsercode()) {
			if (protocoltype == -1 && Orderflag == null) {
				mapuserorders.put("assigned",
						LSlogilabprotocoldetailRepository
								.findByAssignedtoAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(lsloginuser,
										fromdate, todate));
				mapuserorders.put("sharebyme", lsprotocolordersharedbyRepository
						.findBySharebyunifiedidAndSharestatusAndSharedonBetweenOrderBySharedbytoprotocolordercodeDesc(
								lsselectedfulluser.getUnifieduserid(), 1, fromdate, todate));
				mapuserorders.put("sharetome", lsprotocolordersharetoRepository
						.findBySharetounifiedidAndSharestatusAndSharedonBetweenOrderBySharetoprotocolordercodeDesc(
								lsselectedfulluser.getUnifieduserid(), 1, fromdate, todate));

			} else if (protocoltype != -1 && Orderflag != null) {
				mapuserorders.put("assigned", LSlogilabprotocoldetailRepository
						.findByAssignedtoAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
								lsloginuser, protocoltype, Orderflag, fromdate, todate));
				mapuserorders.put("sharebyme", lsprotocolordersharedbyRepository
						.findBySharebyunifiedidAndProtocoltypeAndOrderflagAndSharestatusAndSharedonBetweenOrderBySharedbytoprotocolordercodeDesc(
								lsselectedfulluser.getUnifieduserid(), protocoltype, Orderflag, 1, fromdate, todate));
				mapuserorders.put("sharetome", lsprotocolordersharetoRepository
						.findBySharetounifiedidAndProtocoltypeAndOrderflagAndSharestatusAndSharedonBetweenOrderBySharetoprotocolordercodeDesc(
								lsselectedfulluser.getUnifieduserid(), protocoltype, Orderflag, 1, fromdate, todate));

			} else if (protocoltype == -1 && Orderflag != null) {
				mapuserorders.put("assigned",
						LSlogilabprotocoldetailRepository
								.findByAssignedtoAndOrderflagAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
										lsloginuser, Orderflag, fromdate, todate));
				mapuserorders.put("sharebyme", lsprotocolordersharedbyRepository
						.findBySharebyunifiedidAndOrderflagAndSharestatusAndSharedonBetweenOrderBySharedbytoprotocolordercodeDesc(
								lsselectedfulluser.getUnifieduserid(), Orderflag, 1, fromdate, todate));
				mapuserorders.put("sharetome", lsprotocolordersharetoRepository
						.findBySharetounifiedidAndOrderflagAndSharestatusAndSharedonBetweenOrderBySharetoprotocolordercodeDesc(
								lsselectedfulluser.getUnifieduserid(), Orderflag, 1, fromdate, todate));
			} else if (protocoltype != -1 && Orderflag == null) {
				mapuserorders.put("assigned",
						LSlogilabprotocoldetailRepository
								.findByAssignedtoAndProtocoltypeAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
										lsloginuser, protocoltype, fromdate, todate));
				mapuserorders.put("sharebyme", lsprotocolordersharedbyRepository
						.findBySharebyunifiedidAndProtocoltypeAndSharestatusAndSharedonBetweenOrderBySharedbytoprotocolordercodeDesc(
								lsselectedfulluser.getUnifieduserid(), protocoltype, 1, fromdate, todate));
				mapuserorders.put("sharetome", lsprotocolordersharetoRepository
						.findBySharetounifiedidAndProtocoltypeAndSharestatusAndSharedonBetweenOrderBySharetoprotocolordercodeDesc(
								lsselectedfulluser.getUnifieduserid(), protocoltype, 1, fromdate, todate));
			}

		} else {
			if (protocoltype == -1 && Orderflag == null) {
				mapuserorders.put("assigned",
						LSlogilabprotocoldetailRepository
								.findByAssignedtoAndLsuserMasterAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
										lsselectedfulluser, lsloginuser, fromdate, todate));
				mapuserorders.put("sharebyme", lsprotocolordersharedbyRepository
						.findBySharebyunifiedidAndSharetounifiedidAndSharestatusAndSharedonBetweenOrderBySharedbytoprotocolordercodeDesc(
								lsloginuser.getUnifieduserid(), lsselectedfulluser.getUnifieduserid(), 1, fromdate,
								todate));
			} else if (protocoltype != -1 && Orderflag != null) {
				mapuserorders.put("assigned", LSlogilabprotocoldetailRepository
						.findByAssignedtoAndProtocoltypeAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
								lsselectedfulluser, protocoltype, Orderflag, lsloginuser, fromdate, todate));
				mapuserorders.put("sharebyme", lsprotocolordersharedbyRepository
						.findBySharebyunifiedidAndProtocoltypeAndOrderflagAndSharetounifiedidAndSharestatusAndSharedonBetweenOrderBySharedbytoprotocolordercodeDesc(
								lsloginuser.getUnifieduserid(), protocoltype, Orderflag,
								lsselectedfulluser.getUnifieduserid(), 1, fromdate, todate));
			} else if (protocoltype == -1 && Orderflag != null) {
				mapuserorders.put("assigned", LSlogilabprotocoldetailRepository
						.findByAssignedtoAndOrderflagAndLsuserMasterAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
								lsselectedfulluser, Orderflag, lsloginuser, fromdate, todate));
				mapuserorders.put("sharebyme", lsprotocolordersharedbyRepository
						.findBySharebyunifiedidAndOrderflagAndSharetounifiedidAndSharestatusAndSharedonBetweenOrderBySharedbytoprotocolordercodeDesc(
								lsloginuser.getUnifieduserid(), Orderflag, lsselectedfulluser.getUnifieduserid(), 1,
								fromdate, todate));
			} else if (protocoltype != -1 && Orderflag == null) {
				mapuserorders.put("assigned", LSlogilabprotocoldetailRepository
						.findByAssignedtoAndProtocoltypeAndLsuserMasterAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
								lsselectedfulluser, protocoltype, lsloginuser, fromdate, todate));
				mapuserorders.put("sharebyme", lsprotocolordersharedbyRepository
						.findBySharebyunifiedidAndProtocoltypeAndSharetounifiedidAndSharestatusAndSharedonBetweenOrderBySharedbytoprotocolordercodeDesc(
								lsloginuser.getUnifieduserid(), protocoltype, lsselectedfulluser.getUnifieduserid(), 1,
								fromdate, todate));
			}
		}

		if (objusers.containsKey("searchCriteriaType")) {
			List<Long> protocolordercodeonassigned = new ArrayList<>();
			List<Long> protocolordercodesharebyme = new ArrayList<>();
			List<Long> protocolordercodesharetome = new ArrayList<>();
			List<Map<String, Object>> passmap = new ArrayList<Map<String, Object>>();
			Map<String, Object> passorderlistmap = new HashMap<String, Object>();
			if (mapuserorders.get("assigned") != null) {
				List<LSlogilabprotocoldetail> obj = mapper.convertValue(mapuserorders.get("assigned"),
						new TypeReference<List<LSlogilabprotocoldetail>>() {
						});
				protocolordercodeonassigned = obj.stream().map(LSlogilabprotocoldetail::getProtocolordercode)
						.collect(Collectors.toList());
				passorderlistmap.put("assigned", protocolordercodeonassigned);

			}
			if (mapuserorders.get("sharebyme") != null) {
				List<Lsprotocolordersharedby> obj = mapper.convertValue(mapuserorders.get("sharebyme"),
						new TypeReference<List<Lsprotocolordersharedby>>() {
						});
				protocolordercodesharebyme = obj.stream().map(Lsprotocolordersharedby::getShareprotocolordercode)
						.collect(Collectors.toList());
				passorderlistmap.put("sharebyme", protocolordercodesharebyme);
			}

			if (mapuserorders.get("sharetome") != null) {
				List<Lsprotocolordershareto> obj = mapper.convertValue(mapuserorders.get("sharetome"),
						new TypeReference<List<Lsprotocolordershareto>>() {
						});
				protocolordercodesharetome = obj.stream().map(Lsprotocolordershareto::getShareprotocolordercode)
						.collect(Collectors.toList());
				passorderlistmap.put("sharetome", protocolordercodesharetome);
			}
			passmap.add(passorderlistmap);
			mapuserorders = ProtocolMasterService.getAllProtocolStepLstonsharedorder(
					mapper.convertValue(objusers.get("ismultitenant"), Integer.class),
					mapper.convertValue(objusers.get("searchcontent"), String.class),
					mapper.convertValue(objusers.get("sitecode"), Integer.class), passmap);

		}

		mapuserorders.put("directorycode", directory);

		return mapuserorders;
	}

	public Map<String, Object> Getprotocolordersonsample(LSlogilabprotocoldetail objorder) {
		List<LSlogilabprotocoldetail> lstorder = new ArrayList<LSlogilabprotocoldetail>();
		Map<String, Object> retuobjts = new HashMap<>();
		Date fromdate = objorder.getFromdate();
		Date todate = objorder.getTodate();
		Integer protocoltype = objorder.getProtocoltype();
		if (protocoltype == -1 && objorder.getOrderflag() == null) {
//			lstorder = LSlogilabprotocoldetailRepository
//					.findByLssamplemasterAndViewoptionAndTestcodeAndOrderdisplaytypeAndCreatedtimestampBetweenOrLssamplemasterAndViewoptionAndLsuserMasterAndTestcodeAndOrderdisplaytypeAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
//							objorder.getLssamplemaster(), 1, objorder.getTestcode(), 2, fromdate, todate,
//							objorder.getLssamplemaster(), 2, objorder.getLsuserMaster(), objorder.getTestcode(), 2,
//							fromdate, todate);

			lstorder = LSlogilabprotocoldetailRepository
					.findByLssamplemasterAndViewoptionAndTestcodeAndOrderdisplaytypeAndCreatedtimestampBetweenOrLssamplemasterAndViewoptionAndTestcodeAndOrderdisplaytypeAndCreatedtimestampBetweenOrLssamplemasterAndViewoptionAndLsuserMasterAndTestcodeAndOrderdisplaytypeAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
							objorder.getLssamplemaster(), 1, objorder.getTestcode(), 2, fromdate, todate,
							objorder.getLssamplemaster(), 1, objorder.getTestcode(), 1, fromdate, todate,
							objorder.getLssamplemaster(), 2, objorder.getLsuserMaster(), objorder.getTestcode(), 2,
							fromdate, todate);
		} else if (protocoltype != -1 && objorder.getOrderflag() != null) {
			if (objorder.getRejected() != null) {
				lstorder = LSlogilabprotocoldetailRepository
						.findByLssamplemasterAndViewoptionAndTestcodeAndOrderdisplaytypeAndCreatedtimestampBetweenOrLssamplemasterAndViewoptionAndLsuserMasterAndTestcodeAndOrderdisplaytypeAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
								objorder.getLssamplemaster(), 1, objorder.getTestcode(), 2, fromdate, todate,
								objorder.getLssamplemaster(), 2, objorder.getLsuserMaster(), objorder.getTestcode(), 2,
								protocoltype, objorder.getOrderflag(), 1, fromdate, todate);
			} else {
				lstorder = LSlogilabprotocoldetailRepository
						.findByLssamplemasterAndViewoptionAndTestcodeAndOrderdisplaytypeAndCreatedtimestampBetweenOrLssamplemasterAndViewoptionAndLsuserMasterAndTestcodeAndOrderdisplaytypeAndProtocoltypeAndOrderflagAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
								objorder.getLssamplemaster(), 1, objorder.getTestcode(), 2, fromdate, todate,
								objorder.getLssamplemaster(), 2, objorder.getLsuserMaster(), objorder.getTestcode(), 2,
								protocoltype, objorder.getOrderflag(), fromdate, todate);
			}

		} else if (protocoltype == -1 && objorder.getOrderflag() != null) {
			if (objorder.getRejected() != null) {
				lstorder = LSlogilabprotocoldetailRepository
						.findByLssamplemasterAndViewoptionAndTestcodeAndOrderdisplaytypeAndCreatedtimestampBetweenOrLssamplemasterAndViewoptionAndLsuserMasterAndTestcodeAndOrderdisplaytypeAndOrderflagAndRejectedAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
								objorder.getLssamplemaster(), 1, objorder.getTestcode(), 2, fromdate, todate,
								objorder.getLssamplemaster(), 2, objorder.getLsuserMaster(), objorder.getTestcode(), 2,
								objorder.getOrderflag(), 1, fromdate, todate);

			} else {
				lstorder = LSlogilabprotocoldetailRepository
						.findByLssamplemasterAndViewoptionAndTestcodeAndOrderdisplaytypeAndCreatedtimestampBetweenOrLssamplemasterAndViewoptionAndLsuserMasterAndTestcodeAndOrderdisplaytypeAndOrderflagAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
								objorder.getLssamplemaster(), 1, objorder.getTestcode(), 2, fromdate, todate,
								objorder.getLssamplemaster(), 2, objorder.getLsuserMaster(), objorder.getTestcode(), 2,
								objorder.getOrderflag(), fromdate, todate);
			}
		} else if (protocoltype != -1 && objorder.getOrderflag() == null) {
			lstorder = LSlogilabprotocoldetailRepository
					.findByLssamplemasterAndViewoptionAndTestcodeAndOrderdisplaytypeAndCreatedtimestampBetweenOrLssamplemasterAndViewoptionAndLsuserMasterAndTestcodeAndOrderdisplaytypeAndProtocoltypeAndCreatedtimestampBetweenOrderByProtocolordercodeDesc(
							objorder.getLssamplemaster(), 1, objorder.getTestcode(), 2, fromdate, todate,
							objorder.getLssamplemaster(), 2, objorder.getLsuserMaster(), objorder.getTestcode(), 2,
							protocoltype, fromdate, todate);
		}
//		lstorder.forEach(objorderDetail -> objorderDetail.setLstworkflow(objorder.getLstworkflow()));
//		lstorder.forEach(
//				objorderDetail -> objorderDetail.setLstelnprotocolworkflow(objorder.getLstelnprotocolworkflow()));
		List<Long> protocolordercode = new ArrayList<>();
		if (lstorder.size() > 0 && objorder.getSearchCriteriaType() != null) {
			protocolordercode = lstorder.stream().map(LSlogilabprotocoldetail::getProtocolordercode)
					.collect(Collectors.toList());
			retuobjts.put("protocolordercodeslist", protocolordercode);
		}
		retuobjts.put("protocolorders", lstorder);

		return retuobjts;
	}

	public Map<String, Object> Getprotocolorderbyflaganduser(LSlogilabprotocoldetail objorder) {

		Map<String, Object> retuobjts = new HashMap<>();
		List<LSuserteammapping> lstteammap = lsuserteammappingRepository.findBylsuserMaster(objorder.getLsuserMaster());
		List<LSusersteam> lstteam = lsusersteamRepository.findByLsuserteammappingInAndLssitemaster(lstteammap,
				objorder.getLsuserMaster().getLssitemaster());
		List<LSprojectmaster> lstproject = lsprojectmasterRepository.findByLsusersteamIn(lstteam);
		List<LogilabProtocolOrderssh> lstorder = new ArrayList<LogilabProtocolOrderssh>();
		Date fromdate = objorder.getFromdate();
		Date todate = objorder.getTodate();
		Integer protocoltype = objorder.getProtocoltype();

		// SequenceTable seqobj =
		// sequenceTableRepository.findBySequencecodeOrderBySequencecode(2);
		// Boolean Applicationseq = seqobj.getSequenceview().equals(2) ? true : false;

		List<LSprotocolselectedteam> selectedteamorders = lsprotoselectedteamRepo
				.findByUserteamInAndCreatedtimestampBetween(lstteam, fromdate, todate);

		List<Long> selectedteamprotcolorderList = (selectedteamorders != null && !selectedteamorders.isEmpty())
				? selectedteamorders.stream().map(LSprotocolselectedteam::getProtocolordercode).filter(Objects::nonNull)
						.distinct().collect(Collectors.toList())
				: Collections.singletonList(-1L);

//		List<LSlogilabprotocoldetail> retuobj = new ArrayList<LSlogilabprotocoldetail>();
//		List<LSsamplemaster> lstsample = lssamplemasterrepository.findSamplecodeAndSamplenameBystatusAndLssitemaster(1,
//				objorder.getLsuserMaster().getLssitemaster());
		List<Elnmaterial> nmaterialcode = elnmaterialRepository
				.findByNsitecode(objorder.getLsuserMaster().getLssitemaster().getSitecode());
		List<Integer> userlist = objorder.getLstuserMaster() != null
				? objorder.getLstuserMaster().stream().map(LSuserMaster::getUsercode).collect(Collectors.toList())
				: new ArrayList<Integer>();

		int chunkSize = Integer.parseInt(env.getProperty("lssamplecount"));
		int totalSamples = nmaterialcode.size();
//		List<Long> immutableNegativeValues = Arrays.asList(-3L, -22L);
//		List<Lsprotocolorderstructure> lstdir;
//		if (objorder.getLstuserMaster() == null) {
//			lstdir = lsprotocolorderStructurerepository
//					.findBySitemasterAndViewoptionAndDirectorycodeNotInOrCreatedbyAndViewoptionOrCreatedbyAndViewoptionOrderByDirectorycode(
//							objorder.getLsuserMaster().getLssitemaster(), 1, immutableNegativeValues,
//							objorder.getLsuserMaster(), 2, objorder.getLsuserMaster(), 3);
//		} else {
//			lstdir = lsprotocolorderStructurerepository
//					.findBySitemasterAndViewoptionAndDirectorycodeNotInOrCreatedbyAndViewoptionOrSitemasterAndViewoptionAndCreatedbyInOrderByDirectorycode(
//							objorder.getLsuserMaster().getLssitemaster(), 1, immutableNegativeValues,
//							objorder.getLsuserMaster(), 2, objorder.getLsuserMaster().getLssitemaster(), 3,
//							objorder.getLstuserMaster());
//		}
//		lstdir.addAll(lsprotocolorderStructurerepository.findByDirectorycodeIn(immutableNegativeValues));
//		List<Long> Directory_Code = lstdir.stream().map(Lsprotocolorderstructure::getDirectorycode)
//				.collect(Collectors.toList());
//		List<LSlogilabprotocoldetail> kumu=LSlogilabprotocoldetailRepository.getallrecords();
		List<Long> Directory_Code = objorder.getLstdirectorycode() != null ? objorder.getLstdirectorycode()
				: new ArrayList<Long>();

		List<Lsprotocolordershareto.ProtocolOrderShareToInterface> sharedtoobj = new ArrayList<Lsprotocolordershareto.ProtocolOrderShareToInterface>();
		List<LogilabProtocolOrderssh> lstorder1 = new ArrayList<LogilabProtocolOrderssh>();

		if (protocoltype != null && protocoltype == -1) {
			sharedtoobj = lsprotocolordersharetoRepository
					.findBySharetounifiedidAndSitecodeAndSharestatusAndSharedonBetweenOrderBySharetoprotocolordercodeDesc(
							objorder.getLsuserMaster().getUnifieduserid(),
							objorder.getLsuserMaster().getLssitemaster().getSitecode(), 1, fromdate, todate);
		} else if (protocoltype != null) {
			sharedtoobj = lsprotocolordersharetoRepository
					.findBySharetounifiedidAndSitecodeAndProtocoltypeAndSharestatusAndSharedonBetweenOrderBySharetoprotocolordercodeDesc(
							objorder.getLsuserMaster().getUnifieduserid(),
							objorder.getLsuserMaster().getLssitemaster().getSitecode(), protocoltype, 1, fromdate,
							todate);
		}

		List<ProtocolOrderShareInterface> lstorders = sharedtoobj.stream()
				.map(Lsprotocolordershareto.ProtocolOrderShareToInterface::getProtocolorders)
				.collect(Collectors.toList());

		List<Long> protocolordercodes = lstorders.stream().map(ProtocolOrderShareInterface::getProtocolordercode)
				.collect(Collectors.toList());

		if (objorder.getProtocoltype() != null && objorder.getProtocoltype() == -1) {
			lstorder1 = LSlogilabprotocoldetailRepository
					.findByOrderflagAndProtocolordercodeInOrderByProtocolordercodeDesc(objorder.getOrderflag(),
							protocolordercodes);

		} else if (objorder.getProtocoltype() != null) {
			lstorder1 = LSlogilabprotocoldetailRepository.findByOrderflagAndProtocolordercodeInAndProtocoltype(
					objorder.getOrderflag(), protocolordercodes, protocoltype);
		}

		Set<Long> matchedBatchcodes = new HashSet<>();
		lstorder1.stream().map(LogilabProtocolOrderssh::getPc).forEach(matchedBatchcodes::add);

		if (objorder.getTestcode() == null && objorder.getLsprojectmaster() == null && objorder.getRejected() == null) {
			lstorder.addAll(LSlogilabprotocoldetailRepository
					.findByOrderflagAndLsprojectmasterInAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndOrdercancellIsNull(
							objorder.getOrderflag(), lstproject, protocoltype, fromdate, todate));

//			lstorder.addAll(LSlogilabprotocoldetailRepository
//					.findByOrderflagAndLsprojectmasterInAndProtocoltypeAndCreatedtimestampBetween(
//							objorder.getOrderflag(), lstproject, protocoltype, fromdate, todate));

			List<LogilabProtocolOrderssh> lstorderobj = IntStream.range(0, (totalSamples + chunkSize - 1) / chunkSize)
					.parallel().mapToObj(i -> {
						int startIndex = i * chunkSize;
						int endIndex = Math.min(startIndex + chunkSize, totalSamples);
						List<Elnmaterial> currentChunk = nmaterialcode.subList(startIndex, endIndex);
						List<LogilabProtocolOrderssh> orderChunk = new ArrayList<>();
//						AndElnmaterialIn
						orderChunk.addAll(LSlogilabprotocoldetailRepository
								.findByOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndElnmaterialInAndViewoptionAndOrdercancellIsNull(
										objorder.getOrderflag(), protocoltype, fromdate, todate, currentChunk, 1));
						orderChunk.addAll(LSlogilabprotocoldetailRepository
								.findByOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndElnmaterialInAndViewoptionAndCreatebyAndOrdercancellIsNull(
										objorder.getOrderflag(), protocoltype, fromdate, todate, currentChunk, 2,
										objorder.getLsuserMaster().getUsercode()));
						/* existing */
//						orderChunk.addAll(LSlogilabprotocoldetailRepository
//								.findByOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndElnmaterialInAndViewoptionAndCreatebyInAndOrdercancellIsNullOrderByProtocolordercodeDesc(
//										objorder.getOrderflag(), protocoltype, fromdate, todate, currentChunk, 3,
//										userlist));

						orderChunk.addAll(LSlogilabprotocoldetailRepository
								.findByOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndElnmaterialInAndViewoptionAndCreatebyInAndTeamselectedAndOrdercancellIsNullOrTeamselectedAndProtocolordercodeInAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndElnmaterialInAndViewoptionAndCreatebyInAndOrdercancellIsNullOrderByProtocolordercodeDesc(
										objorder.getOrderflag(), protocoltype, fromdate, todate, currentChunk, 3,
										userlist, false, true, selectedteamprotcolorderList, objorder.getOrderflag(),
										protocoltype, fromdate, todate, currentChunk, 3, userlist));

						return orderChunk;
					}).flatMap(List::stream).collect(Collectors.toList());
			lstorder.addAll(lstorderobj);

			if (objorder.getLstuserMaster() == null || objorder.getLstuserMaster().size() == 0) {
				/** existing */
//				lstorder.addAll(LSlogilabprotocoldetailRepository
//						.findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndAssignedtoIsNullAndOrdercancellIsNullOrderByProtocolordercodeDesc(
//								Directory_Code, 1, fromdate, todate, objorder.getOrderflag(), protocoltype,
//								Directory_Code, 2, objorder.getLsuserMaster(), fromdate, todate,
//								objorder.getOrderflag(), protocoltype, Directory_Code, 3, objorder.getLsuserMaster(),
//								fromdate, todate, objorder.getOrderflag(), protocoltype));

				lstorder.addAll(LSlogilabprotocoldetailRepository
						.findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndElnmaterialIsNullAndProtocoltypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndElnmaterialIsNullAndProtocoltypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndElnmaterialIsNullAndProtocoltypeAndAssignedtoIsNullAndOrdercancellIsNullAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndElnmaterialIsNullAndProtocoltypeAndAssignedtoIsNullAndOrdercancellIsNullOrderByProtocolordercodeDesc(
								Directory_Code, 1, fromdate, todate, objorder.getOrderflag(), protocoltype,
								Directory_Code, 2, objorder.getLsuserMaster(), fromdate, todate,
								objorder.getOrderflag(), protocoltype, Directory_Code, 3, objorder.getLsuserMaster(),
								fromdate, todate, objorder.getOrderflag(), protocoltype, false, true,
								selectedteamprotcolorderList, Directory_Code, 3, objorder.getLsuserMaster(), fromdate,
								todate, objorder.getOrderflag(), protocoltype));

			} else {// now
				/* existing */
//				lstorder.addAll(LSlogilabprotocoldetailRepository
//						.findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndElnmaterialIsNullAndProtocoltypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndElnmaterialIsNullAndProtocoltypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndOrderflagAndLsprojectmasterIsNullAndElnmaterialIsNullAndProtocoltypeAndAssignedtoIsNullAndOrdercancellIsNullOrderByProtocolordercodeDesc(
//								Directory_Code, 1, fromdate, todate, objorder.getOrderflag(), protocoltype,
//								Directory_Code, 2, objorder.getLsuserMaster(), fromdate, todate,
//								objorder.getOrderflag(), protocoltype, Directory_Code, 3, fromdate, todate, userlist,
//								objorder.getOrderflag(), protocoltype));
				/* change for p.team selection in VO=3 */
				lstorder.addAll(LSlogilabprotocoldetailRepository
						.findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndElnmaterialIsNullAndProtocoltypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndElnmaterialIsNullAndProtocoltypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndAssignedtoIsNullAndElnmaterialIsNullAndOrdercancellIsNullAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndOrderflagAndLsprojectmasterIsNullAndElnmaterialIsNullAndProtocoltypeAndAssignedtoIsNullAndOrdercancellIsNullOrderByProtocolordercodeDesc(
								Directory_Code, 1, fromdate, todate, objorder.getOrderflag(), protocoltype,
								Directory_Code, 2, objorder.getLsuserMaster(), fromdate, todate,
								objorder.getOrderflag(), protocoltype, Directory_Code, 3, fromdate, todate, userlist,
								objorder.getOrderflag(), protocoltype, false, true, selectedteamprotcolorderList,
								Directory_Code, 3, fromdate, todate, userlist, objorder.getOrderflag(), protocoltype));

				// if(Applicationseq) {
				// lstorder.forEach(objorderDetail ->
				// objorderDetail.setPn(objorderDetail.getSid()));
				// }

			}

		} else if (objorder.getTestcode() == null && objorder.getLsprojectmaster() == null
				&& objorder.getRejected() != null) {

			List<LogilabProtocolOrderssh> lstorderobj = IntStream.range(0, (totalSamples + chunkSize - 1) / chunkSize)
					.parallel().mapToObj(i -> {
						int startIndex = i * chunkSize;
						int endIndex = Math.min(startIndex + chunkSize, totalSamples);
						List<Elnmaterial> currentChunk = nmaterialcode.subList(startIndex, endIndex);
						List<LogilabProtocolOrderssh> orderChunk = new ArrayList<>();
						orderChunk = LSlogilabprotocoldetailRepository
								.findByOrderflagAndRejectedAndLsprojectmasterInAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullOrOrderflagAndRejectedAndLsprojectmasterIsNullAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndElnmaterialInOrderByProtocolordercodeDesc(
										objorder.getOrderflag(), 1, lstproject, protocoltype, fromdate, todate,
										objorder.getOrderflag(), 1, protocoltype, fromdate, todate, currentChunk);
						return orderChunk;
					}).flatMap(List::stream).collect(Collectors.toList());
			lstorder.addAll(lstorderobj);

			if (objorder.getLstuserMaster().size() == 0) {
				/** existing */
//				lstorder.addAll(LSlogilabprotocoldetailRepository
//						.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedOrderByProtocolordercodeDesc(
//								Directory_Code, 1, fromdate, todate, objorder.getOrderflag(), protocoltype, 1,
//								Directory_Code, 2, objorder.getLsuserMaster(), fromdate, todate,
//								objorder.getOrderflag(), protocoltype, 1, Directory_Code, 3, objorder.getLsuserMaster(),
//								fromdate, todate, objorder.getOrderflag(), protocoltype, 1));

				lstorder.addAll(LSlogilabprotocoldetailRepository
						.findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedOrderByProtocolordercodeDesc(
								Directory_Code, 1, fromdate, todate, objorder.getOrderflag(), protocoltype, 1,
								Directory_Code, 2, objorder.getLsuserMaster(), fromdate, todate,
								objorder.getOrderflag(), protocoltype, 1, Directory_Code, 3, objorder.getLsuserMaster(),
								fromdate, todate, objorder.getOrderflag(), protocoltype, 1, false, true,
								selectedteamprotcolorderList, Directory_Code, 3, objorder.getLsuserMaster(), fromdate,
								todate, objorder.getOrderflag(), protocoltype, 1));
			} else {
				lstorder.addAll(LSlogilabprotocoldetailRepository
						.findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedOrderByProtocolordercodeDesc(
								Directory_Code, 1, fromdate, todate, objorder.getOrderflag(), protocoltype, 1,
								Directory_Code, 2, objorder.getLsuserMaster(), fromdate, todate,
								objorder.getOrderflag(), protocoltype, 1, Directory_Code, 3, fromdate, todate, userlist,
								objorder.getOrderflag(), protocoltype, 1, false, true, selectedteamprotcolorderList,
								Directory_Code, 3, fromdate, todate, userlist, objorder.getOrderflag(), protocoltype,
								1));

			}

		} else if (objorder.getTestcode() == null && objorder.getLsprojectmaster() != null
				&& objorder.getRejected() == null) {

			List<LogilabProtocolOrderssh> lstorderobj = IntStream.range(0, (totalSamples + chunkSize - 1) / chunkSize)
					.parallel().mapToObj(i -> {
						int startIndex = i * chunkSize;
						int endIndex = Math.min(startIndex + chunkSize, totalSamples);
						List<Elnmaterial> currentChunk = nmaterialcode.subList(startIndex, endIndex);
						List<LogilabProtocolOrderssh> orderChunk = new ArrayList<>();
						orderChunk = LSlogilabprotocoldetailRepository
								.findByOrderflagAndLsprojectmasterAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullOrOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndElnmaterialInOrderByProtocolordercodeDesc(
										objorder.getOrderflag(), objorder.getLsprojectmaster(), protocoltype, fromdate,
										todate, objorder.getOrderflag(), protocoltype, fromdate, todate, currentChunk);
						return orderChunk;
					}).flatMap(List::stream).collect(Collectors.toList());
			lstorder.addAll(lstorderobj);

			matchedBatchcodes.clear();
			lstorder1.stream()
					.filter(order -> order.getPn() != null
							&& order.getPn().equals(objorder.getLsprojectmaster().getProjectname()))
					.map(LogilabProtocolOrderssh::getPc).forEach(matchedBatchcodes::add);

		} else if (objorder.getTestcode() != null && objorder.getLsprojectmaster() == null
				&& objorder.getRejected() == null) {

			List<LogilabProtocolOrderssh> lstorderobj = IntStream.range(0, (totalSamples + chunkSize - 1) / chunkSize)
					.parallel().mapToObj(i -> {
						int startIndex = i * chunkSize;
						int endIndex = Math.min(startIndex + chunkSize, totalSamples);
						List<Elnmaterial> currentChunk = nmaterialcode.subList(startIndex, endIndex);
						List<LogilabProtocolOrderssh> orderChunk = new ArrayList<>();
						orderChunk = LSlogilabprotocoldetailRepository
								.findByOrderflagAndLsprojectmasterInAndTestcodeAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullOrOrderflagAndLsprojectmasterIsNullAndTestcodeAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndElnmaterialInOrderByProtocolordercodeDesc(
										objorder.getOrderflag(), lstproject, objorder.getTestcode(), protocoltype,
										fromdate, todate, objorder.getOrderflag(), objorder.getTestcode(), protocoltype,
										fromdate, todate, currentChunk);
						return orderChunk;
					}).flatMap(List::stream).collect(Collectors.toList());
			lstorder.addAll(lstorderobj);

			if (objorder.getLstuserMaster().size() == 0) {
				/** existing */
//				lstorder.addAll(LSlogilabprotocoldetailRepository
//						.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndTestcodeOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndTestcodeOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndTestcodeOrderByProtocolordercodeDesc(
//								Directory_Code, 1, fromdate, todate, objorder.getOrderflag(), protocoltype,
//								objorder.getTestcode(), Directory_Code, 2, objorder.getLsuserMaster(), fromdate, todate,
//								objorder.getOrderflag(), protocoltype, objorder.getTestcode(), Directory_Code, 3,
//								objorder.getLsuserMaster(), fromdate, todate, objorder.getOrderflag(), protocoltype,
//								objorder.getTestcode()));
				/** change for p.team selection in vo */

				lstorder.addAll(LSlogilabprotocoldetailRepository
						.findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndTestcodeOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndTestcodeOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndTestcodeAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndTestcodeOrderByProtocolordercodeDesc(
								Directory_Code, 1, fromdate, todate, objorder.getOrderflag(), protocoltype,
								objorder.getTestcode(), Directory_Code, 2, objorder.getLsuserMaster(), fromdate, todate,
								objorder.getOrderflag(), protocoltype, objorder.getTestcode(), Directory_Code, 3,
								objorder.getLsuserMaster(), fromdate, todate, objorder.getOrderflag(), protocoltype,
								objorder.getTestcode(), false, true, selectedteamprotcolorderList, Directory_Code, 3,
								objorder.getLsuserMaster(), fromdate, todate, objorder.getOrderflag(), protocoltype,
								objorder.getTestcode()));
			} else {
				/** existing */
//				lstorder.addAll(LSlogilabprotocoldetailRepository
//						.findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndTestcodeOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndTestcodeOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndTestcodeOrderByProtocolordercodeDesc(
//								Directory_Code, 1, fromdate, todate, objorder.getOrderflag(), protocoltype,
//								objorder.getTestcode(), Directory_Code, 2, objorder.getLsuserMaster(), fromdate, todate,
//								objorder.getOrderflag(), protocoltype, objorder.getTestcode(), Directory_Code, 3,
//								fromdate, todate, userlist, objorder.getOrderflag(), protocoltype,
//								objorder.getTestcode()));
				/** change for p.team selection in vo */
				lstorder.addAll(LSlogilabprotocoldetailRepository
						.findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndTestcodeOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndTestcodeOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndTestcodeAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndTestcodeOrderByProtocolordercodeDesc(
								Directory_Code, 1, fromdate, todate, objorder.getOrderflag(), protocoltype,
								objorder.getTestcode(), Directory_Code, 2, objorder.getLsuserMaster(), fromdate, todate,
								objorder.getOrderflag(), protocoltype, objorder.getTestcode(), Directory_Code, 3,
								fromdate, todate, userlist, objorder.getOrderflag(), protocoltype,
								objorder.getTestcode(), false, true, selectedteamprotcolorderList, Directory_Code, 3,
								fromdate, todate, userlist, objorder.getOrderflag(), protocoltype,
								objorder.getTestcode()));

			}

			matchedBatchcodes.clear();
			lstorder1.stream().filter(order -> order.getTc().equals(objorder.getTestcode()))
					.map(LogilabProtocolOrderssh::getPc).forEach(matchedBatchcodes::add);

		} else if (objorder.getTestcode() != null && objorder.getLsprojectmaster() != null
				&& objorder.getRejected() == null) {

			List<LogilabProtocolOrderssh> lstorderobj = IntStream.range(0, (totalSamples + chunkSize - 1) / chunkSize)
					.parallel().mapToObj(i -> {
						int startIndex = i * chunkSize;
						int endIndex = Math.min(startIndex + chunkSize, totalSamples);
						List<Elnmaterial> currentChunk = nmaterialcode.subList(startIndex, endIndex);
						List<LogilabProtocolOrderssh> orderChunk = new ArrayList<>();
						orderChunk = LSlogilabprotocoldetailRepository
								.findByOrderflagAndLsprojectmasterAndTestcodeAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullOrOrderflagAndLsprojectmasterIsNullAndTestcodeAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndElnmaterialInOrderByProtocolordercodeDesc(
										objorder.getOrderflag(), objorder.getLsprojectmaster(), objorder.getTestcode(),
										protocoltype, fromdate, todate, objorder.getOrderflag(), objorder.getTestcode(),
										protocoltype, fromdate, todate, currentChunk);
						return orderChunk;
					}).flatMap(List::stream).collect(Collectors.toList());
			lstorder.addAll(lstorderobj);

			matchedBatchcodes.clear();
			lstorder1.stream()
					.filter(order -> order.getTc().equals(objorder.getTestcode()) && order.getPn() != null
							&& order.getPn().equals(objorder.getLsprojectmaster().getProjectname()))
					.map(LogilabProtocolOrderssh::getPc).forEach(matchedBatchcodes::add);

		} else if (objorder.getTestcode() != null && objorder.getLsprojectmaster() == null
				&& objorder.getRejected() != null) {

			List<LogilabProtocolOrderssh> lstorderobj = IntStream.range(0, (totalSamples + chunkSize - 1) / chunkSize)
					.parallel().mapToObj(i -> {
						int startIndex = i * chunkSize;
						int endIndex = Math.min(startIndex + chunkSize, totalSamples);
						List<Elnmaterial> currentChunk = nmaterialcode.subList(startIndex, endIndex);
						List<LogilabProtocolOrderssh> orderChunk = new ArrayList<>();
						orderChunk = LSlogilabprotocoldetailRepository
								.findByOrderflagAndRejectedAndLsprojectmasterInAndTestcodeAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullOrOrderflagAndRejectedAndLsprojectmasterIsNullAndTestcodeAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndElnmaterialInOrderByProtocolordercodeDesc(
										objorder.getOrderflag(), 1, lstproject, objorder.getTestcode(), protocoltype,
										fromdate, todate, objorder.getOrderflag(), 1, objorder.getTestcode(),
										protocoltype, fromdate, todate, currentChunk);
						return orderChunk;
					}).flatMap(List::stream).collect(Collectors.toList());
			lstorder.addAll(lstorderobj);

			if (objorder.getLstuserMaster() == null || objorder.getLstuserMaster().size() == 0) {
				/** existing */
//				lstorder.addAll(LSlogilabprotocoldetailRepository
//						.findByDirectorycodeAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedAndTestcodeOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedAndTestcodeOrDirectorycodeAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedAndTestcodeOrderByProtocolordercodeDesc(
//								Directory_Code, 1, fromdate, todate, objorder.getOrderflag(), protocoltype, 1,
//								objorder.getTestcode(), Directory_Code, 2, objorder.getLsuserMaster(), fromdate, todate,
//								objorder.getOrderflag(), protocoltype, 1, objorder.getTestcode(), Directory_Code, 3,
//								objorder.getLsuserMaster(), fromdate, todate, objorder.getOrderflag(), protocoltype, 1,
//								objorder.getTestcode()));
				/** change for p.team selection in vo */
				lstorder.addAll(LSlogilabprotocoldetailRepository
						.findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedAndTestcodeOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedAndTestcodeOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedAndTestcodeAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedAndTestcodeOrderByProtocolordercodeDesc(
								Directory_Code, 1, fromdate, todate, objorder.getOrderflag(), protocoltype, 1,
								objorder.getTestcode(), Directory_Code, 2, objorder.getLsuserMaster(), fromdate, todate,
								objorder.getOrderflag(), protocoltype, 1, objorder.getTestcode(), Directory_Code, 3,
								objorder.getLsuserMaster(), fromdate, todate, objorder.getOrderflag(), protocoltype, 1,
								objorder.getTestcode(), false, true, selectedteamprotcolorderList, Directory_Code, 3,
								objorder.getLsuserMaster(), fromdate, todate, objorder.getOrderflag(), protocoltype, 1,
								objorder.getTestcode()));
			} else {
				/** existing */
//				lstorder.addAll(LSlogilabprotocoldetailRepository
//						.findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedAndTestcodeOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedAndTestcodeOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedAndTestcodeOrderByProtocolordercodeDesc(
//								Directory_Code, 1, fromdate, todate, objorder.getOrderflag(), protocoltype, 1,
//								Directory_Code, objorder.getTestcode(), 2, objorder.getLsuserMaster(), fromdate, todate,
//								objorder.getOrderflag(), protocoltype, 1, objorder.getTestcode(), Directory_Code, 3,
//								fromdate, todate, userlist, objorder.getOrderflag(), protocoltype, 1,
//								objorder.getTestcode()));
				/** change for p.team selection in vo */
				lstorder.addAll(LSlogilabprotocoldetailRepository
						.findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedAndTestcodeOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedAndTestcodeOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedAndTestcodeAndTeamselectedOrTeamselectedAndProtocolordercodeInAndDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndCreatebyInAndOrderflagAndLsprojectmasterIsNullAndProtocoltypeAndRejectedAndTestcodeOrderByProtocolordercodeDesc(
								Directory_Code, 1, fromdate, todate, objorder.getOrderflag(), protocoltype, 1,
								objorder.getTestcode(), Directory_Code, 2, objorder.getLsuserMaster(), fromdate, todate,
								objorder.getOrderflag(), protocoltype, 1, objorder.getTestcode(), Directory_Code, 3,
								fromdate, todate, userlist, objorder.getOrderflag(), protocoltype, 1,
								objorder.getTestcode(), false, true, selectedteamprotcolorderList, Directory_Code, 3,
								fromdate, todate, userlist, objorder.getOrderflag(), protocoltype, 1,
								objorder.getTestcode()));

			}

		} else if (objorder.getTestcode() == null && objorder.getLsprojectmaster() != null
				&& objorder.getRejected() != null) {

			List<LogilabProtocolOrderssh> lstorderobj = IntStream.range(0, (totalSamples + chunkSize - 1) / chunkSize)
					.parallel().mapToObj(i -> {
						int startIndex = i * chunkSize;
						int endIndex = Math.min(startIndex + chunkSize, totalSamples);
						List<Elnmaterial> currentChunk = nmaterialcode.subList(startIndex, endIndex);
						List<LogilabProtocolOrderssh> orderChunk = new ArrayList<>();
						orderChunk = LSlogilabprotocoldetailRepository
								.findByOrderflagAndRejectedAndLsprojectmasterAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullOrOrderflagAndRejectedAndLsprojectmasterIsNullAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndElnmaterialInOrderByProtocolordercodeDesc(
										objorder.getOrderflag(), 1, objorder.getLsprojectmaster(), protocoltype,
										fromdate, todate, objorder.getOrderflag(), 1, protocoltype, fromdate, todate,
										currentChunk);
						return orderChunk;
					}).flatMap(List::stream).collect(Collectors.toList());
			lstorder.addAll(lstorderobj);

		} else if (objorder.getTestcode() != null && objorder.getLsprojectmaster() != null
				&& objorder.getRejected() != null) {

			List<LogilabProtocolOrderssh> lstorderobj = IntStream.range(0, (totalSamples + chunkSize - 1) / chunkSize)
					.parallel().mapToObj(i -> {
						int startIndex = i * chunkSize;
						int endIndex = Math.min(startIndex + chunkSize, totalSamples);
						List<Elnmaterial> currentChunk = nmaterialcode.subList(startIndex, endIndex);
						List<LogilabProtocolOrderssh> orderChunk = new ArrayList<>();
						orderChunk = LSlogilabprotocoldetailRepository
								.findByOrderflagAndRejectedAndLsprojectmasterAndTestcodeAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullOrOrderflagAndRejectedAndLsprojectmasterIsNullAndTestcodeAndProtocoltypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndElnmaterialInOrderByProtocolordercodeDesc(
										objorder.getOrderflag(), 1, objorder.getLsprojectmaster(),
										objorder.getTestcode(), protocoltype, fromdate, todate, objorder.getOrderflag(),
										1, objorder.getTestcode(), protocoltype, fromdate, todate, currentChunk);
						return orderChunk;
					}).flatMap(List::stream).collect(Collectors.toList());
			lstorder.addAll(lstorderobj);

		}

		List<Lsprotocolordershareto.ProtocolOrderShareToInterface> filteredSharedTo = sharedtoobj.stream()
				.filter(obj -> matchedBatchcodes.contains(obj.getShareprotocolordercode()))
				.collect(Collectors.toList());
		if (!lstorder.isEmpty()) {
			lstorder = lstorder.stream().filter(o -> o.getOc() == null || o.getOc() != 1)
					.collect(Collectors.toCollection(ArrayList::new));

			if (objorder.getFilefor().equals("ECO") || objorder.getFilefor().equals("DCO")) {
				lstorder = lstorder.stream().filter(o -> o.getR() == null || o.getR() != 1)
						.collect(Collectors.toCollection(ArrayList::new));
			}

//			filteredSharedTo = filteredSharedTo.stream().filter(obj -> obj.getProtocolorders() != null
//					&& (obj.getProtocolorders().getApprovelstatus() == null || obj.getProtocolorders().getApprovelstatus() != 3))
//					.collect(Collectors.toList());
//			else if (objorder.getFilefor().equals("RJO")) {
//				lstorder = lstorder.stream().filter(o -> o.getR() != null && o.getR() == 1)
//						.collect(Collectors.toCollection(ArrayList::new));
//			}

		}

		List<Elnprotocolworkflow> workflowobj = elnprotocolworkflowRepository
				.findByLssitemasterAndStatusOrderByWorkflowcodeAsc(objorder.getLsuserMaster().getLssitemaster(), 1);

		lstorder.forEach(objorderDetail -> objorderDetail.setLsepw(objorder.getLstelnprotocolworkflow(), workflowobj,
				objorder.getLsuserMaster()));
		List<Long> protocolordercode = new ArrayList<>();
		if (lstorder.size() > 0 && objorder.getSearchCriteriaType() != null) {
			protocolordercode = lstorder.stream().map(LogilabProtocolOrderssh::getPc).collect(Collectors.toList());
			retuobjts.put("protocolordercodeslist", protocolordercode);
		}
		retuobjts.put("protocolorders", lstorder);

		retuobjts.put("sharedmeorders", lstorder1.stream().filter(obj1 -> matchedBatchcodes.contains(obj1.getPc()))
				.collect(Collectors.toList()));
		retuobjts.put("sharedtome", filteredSharedTo);

		return retuobjts;

	}

	public List<LSsheetfolderfiles> Getfilesforfolder(LSsheetfolderfiles objfiles) throws Exception {
		List<LSsheetfolderfiles> lstfiles = new ArrayList<LSsheetfolderfiles>();
		try {
			lstfiles = lssheetfolderfilesRepository
					.findByDirectorycodeAndLssitemasterAndFileforAndCreatedtimestampBetweenOrderByFolderfilecode(
							objfiles.getDirectorycode(), objfiles.getLssitemaster(), objfiles.getFilefor(),
							objfiles.getFromdate(), objfiles.getTodate());
			return lstfiles;
		} catch (Exception e) {
			return lstfiles;
		}

//		List<LSsheetfolderfiles> lstfiles = new ArrayList<LSsheetfolderfiles>();
//
//		lstfiles = lssheetfolderfilesRepository
//				.findByDirectorycodeAndFileforOrderByFolderfilecode(objfiles.getDirectorycode(), objfiles.getFilefor());
//
//		return lstfiles;
	}

	public Response validatefileexistonfolder(LSsheetfolderfiles objfile) {
		Response response = new Response();
		long filecount = lssheetfolderfilesRepository.countByDirectorycodeAndFileforAndFilename(
				objfile.getDirectorycode(), objfile.getFilefor(), objfile.getFilename());
		if (filecount > 0) {
			response.setStatus(false);
		} else {
			response.setStatus(true);
		}
		return response;
	}

	public ResponseEntity<Resource> downloadsheetfileforfolder(Integer multitenant, String tenant,
			String fileid) throws IOException {
		HttpHeaders header = new HttpHeaders();
		boolean valid = false;
		//header.set("Content-Disposition", "attachment; filename=" + fileid);
		header.set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + fileid + "\"");
	InputStreamResource resource = null;
	byte[] content;
		if (multitenant == 1) {
			byte[] documentBytes = objCloudFileManipulationservice.retrieveCloudReportFile(tenant + "sheetfolderfiles",
					fileid);
			String jsonString = new String(documentBytes, StandardCharsets.UTF_8);
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				objectMapper.readTree(jsonString);
				valid = true;
			} catch (Exception e) {
				valid = false;
			}
			if (valid) {
				JsonNode jsonNode = objectMapper.readTree(jsonString);
				JsonNode sheets = jsonNode.path("sheets");
//				Workbook workbook = new XSSFWorkbook();
				 Workbook workbook = DocumenteditorService.buildWorkbookFromKendoJson(jsonString);
				
//				for (JsonNode sheetNode : sheets) {
//					String sheetName = sheetNode.path("name").asText();
//					JsonNode rows = sheetNode.path("rows");
//					Sheet sheet = workbook.createSheet(sheetName);
//					int rowIndex = 0;
//					for (JsonNode rowNode : rows) {
//						Row row = sheet.createRow(rowIndex++);
//						JsonNode cells = rowNode.path("cells");
//						int colIndex = 0;
//						for (JsonNode cellNode : cells) {
//							Cell cell = row.createCell(colIndex++);
//							if (cellNode.has("value")) {
//								if (cellNode.get("value").isNumber()) {
//									cell.setCellValue(cellNode.get("value").asDouble());
//								} else {
//									cell.setCellValue(cellNode.get("value").asText());
//								}
//							}
//						}
//					}
//				}
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				workbook.write(bos);
				content = bos.toByteArray();
				InputStream inputStream = new ByteArrayInputStream(content);
				resource = new InputStreamResource(inputStream);
				header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				workbook.close();
			} else {
				InputStream fileStream = cloudFileManipulationservice.retrieveCloudFile(fileid,
						tenant + "sheetfolderfiles");
				content = IOUtils.toByteArray(fileStream);
				int size = content.length;
				InputStream is = null;
				try {
					is = new ByteArrayInputStream(content);
					resource = new InputStreamResource(is);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (is != null)
							is.close();
					} catch (Exception ex) {

					}
				}

				header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				header.setContentLength(size);
			}
			return new ResponseEntity<>(resource, header, HttpStatus.OK);
		} else {
			GridFsResource gridFsFile = null;

			try {
				gridFsFile = retrieveLargeFile(fileid);
			} catch (IllegalStateException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
			System.out.println(gridFsFile.getContentType());
			header.setContentType(MediaType.parseMediaType(gridFsFile.getContentType()));
			header.setContentLength(gridFsFile.contentLength());
			return new ResponseEntity<>(gridFsFile, header, HttpStatus.OK);
		}
	}

	public Map<String, Object> downloadsheetfilefordocx(Integer multitenant, String tenant, String fileid,
			String screenname, String ontabkey) throws Exception {
		Map<String, Object> mapObj = new HashMap<>();
		String containerName = "";
		if (multitenant == 1) {
			if (screenname.equals("Sheet")) {
				containerName = tenant + "sheetfolderfiles";
			} else if (screenname.equals("Protocol")) {
				containerName = tenant + "protocolfolderfiles";
			} else {
				if (ontabkey.isEmpty() || ontabkey.equals("protocolfile")) {
					containerName = tenant + "protocolfiles";
				} else if (ontabkey.equals("sheet")) {
					containerName = tenant + "sheetfolderfiles";
				} else {
					containerName = tenant + "protocolfolderfiles";
				}
			}
			if (screenname.equals("Sheet") || screenname.equals("Protocol") || ontabkey.isEmpty()
					|| ontabkey.equals("sheet") || ontabkey.equals("protocol") || ontabkey.equals("protocolfile")) {
				byte[] documentBytes = objCloudFileManipulationservice.retrieveCloudReportFile(containerName, fileid);
				MockMultipartFile mockMultipartFile = new MockMultipartFile("tempFileName", documentBytes);
				Map<String, String> mapObj1 = documenteditorService.importFile(mockMultipartFile);
				mapObj.put("templatecontent", mapObj1);
				mapObj.put("status", true);
			} else {
				InputStream fileDtream = cloudFileManipulationservice.retrieveLargeFile(fileid);
				byte[] content = IOUtils.toByteArray(fileDtream);
				MockMultipartFile mockMultipartFile = new MockMultipartFile("tempFileName", content);
				Map<String, String> mapObj1 = documenteditorService.importFile(mockMultipartFile);
				mapObj.put("templatecontent", mapObj1);
				mapObj.put("status", true);
			}
		}else {		
	        GridFsResource gridFsFile = retrieveLargeFile(fileid);		        
	        InputStream fileDtream = gridFsFile.getInputStream();
			byte[] content = IOUtils.toByteArray(fileDtream);
			MockMultipartFile mockMultipartFile = new MockMultipartFile("tempFileName", content);
			Map<String, String> mapObj1 = documenteditorService.importFile(mockMultipartFile);
			mapObj.put("templatecontent", mapObj1);
			mapObj.put("status", true);
		}
		return mapObj;
	}

	public List<LSsheetfolderfiles> Getaddedfilesforfolder(List<String> lstuuid) {
		List<LSsheetfolderfiles> lstfiles = new ArrayList<LSsheetfolderfiles>();
		lstfiles = lssheetfolderfilesRepository.findByUuidInOrderByFolderfilecode(lstuuid);

		return lstfiles;
	}

	public List<Logilaborders> GetordersonFilter(LSlogilablimsorderdetail objorder) {
		List<Logilaborders> lstorder = new ArrayList<Logilaborders>();
		List<Long> lstBatchcode = new ArrayList<Long>();
		List<Integer> lstsamplefilecode = new ArrayList<Integer>();
		List<LSsamplefile> idList = new ArrayList<LSsamplefile>();

		if (objorder.getSearchCriteria().getContentsearchtype() != null
				&& objorder.getSearchCriteria().getContentsearch() != null) {

			if (objorder.getFiletype() == -1) {
				lstBatchcode = lslogilablimsorderdetailRepository.getBatchcodeAndcreateddate(objorder.getFromdate(),
						objorder.getTodate());
			} else {
				lstBatchcode = lslogilablimsorderdetailRepository.getBatchcodeonFiletypeAndcreateddate(
						objorder.getFiletype(), objorder.getFromdate(), objorder.getTodate());
			}

			if (lstBatchcode != null && lstBatchcode.size() > 0) {
				lstsamplefilecode = lssamplefileRepository.getFilesamplecodeByBatchcodeIn(lstBatchcode);
			}

			if (lstsamplefilecode != null && lstsamplefilecode.size() > 0) {
				idList = getsamplefileIds(lstsamplefilecode, objorder.getSearchCriteria(), objorder.getIsmultitenant());
				if (idList != null && idList.size() > 0) {

					if (objorder.getFiletype() == -1 && objorder.getOrderflag() != null) {
						lstorder = lslogilablimsorderdetailRepository
								.findByOrderflagAndCreatedtimestampBetweenAndLssamplefileIn(objorder.getOrderflag(),
										objorder.getFromdate(), objorder.getTodate(), idList);
					} else if (objorder.getFiletype() == -1 && objorder.getOrderflag() == null) {
						lstorder = lslogilablimsorderdetailRepository.findByCreatedtimestampBetweenAndLssamplefileIn(
								objorder.getFromdate(), objorder.getTodate(), idList);
					}

					else if (objorder.getOrderflag() == null) {
						lstorder = lslogilablimsorderdetailRepository
								.findByFiletypeAndCreatedtimestampBetweenAndLssamplefileIn(objorder.getFiletype(),
										objorder.getFromdate(), objorder.getTodate(), idList);
					} else {
						lstorder = lslogilablimsorderdetailRepository
								.findByOrderflagAndFiletypeAndCreatedtimestampBetweenAndLssamplefileIn(
										objorder.getOrderflag(), objorder.getFiletype(), objorder.getFromdate(),
										objorder.getTodate(), idList);
					}

				}
			}

		}

		return lstorder;

	}

	public List<LSsheetfolderfiles> UpdateFolderforfiles(LSsheetfolderfiles[] files) throws Exception {
		List<LSsheetfolderfiles> lstfile = Arrays.asList(files);
		if (lstfile.size() > 0) {
			List<Integer> lstfilesid = lstfile.stream().map(LSsheetfolderfiles::getFolderfilecode)
					.collect(Collectors.toList());
			lssheetfolderfilesRepository.updatedirectory(lstfile.get(0).getDirectorycode(), lstfilesid);
		}
		return lstfile;
	}

	public LSsheetfolderfiles UpdateFolderforfile(LSsheetfolderfiles file) throws Exception {
		lssheetfolderfilesRepository.updatedirectory(file.getDirectorycode(), file.getFolderfilecode());
		return file;
	}

	public List<Logilaborderssh> Getordersonassignedandmyorders(Map<String, Object> objusers) {
		ObjectMapper obj = new ObjectMapper();
		LSlogilablimsorderdetail lslogilablimsorderdetail = obj.convertValue(objusers.get("lslogilablimsorderdetail"),
				new TypeReference<LSlogilablimsorderdetail>() {
				});
		List<Logilaborderssh> lstorder = new ArrayList<Logilaborderssh>();
		Integer filetype = lslogilablimsorderdetail.getFiletype();
		String Orderflag = null;

		LSprojectmaster proselected = lslogilablimsorderdetail.getLsprojectmaster();
		Integer testselected = lslogilablimsorderdetail.getTestcode();

		if (objusers.containsKey("orderflag")) {
			Orderflag = obj.convertValue(objusers.get("orderflag"), String.class);
		}

		if (lslogilablimsorderdetail.getOrderflag().equals("A")) {

			if (filetype == -1 && Orderflag == null && proselected == null && testselected == null) {
				lstorder = logilablimsorderdetailsRepository
						.findByLsuserMasterAndSitecodeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullOrderByBatchcodeDesc(
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getSitecode(),
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getFromdate(),
								lslogilablimsorderdetail.getTodate());
			} else if (filetype == -1 && Orderflag == null && proselected != null && testselected == null) {
				lstorder = logilablimsorderdetailsRepository
						.findByLsuserMasterAndSitecodeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndLsprojectmasterOrderByBatchcodeDesc(
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getSitecode(),
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getFromdate(),
								lslogilablimsorderdetail.getTodate(), proselected);
			} else if (filetype == -1 && Orderflag == null && proselected == null && testselected != null) {
				lstorder = logilablimsorderdetailsRepository
						.findByLsuserMasterAndSitecodeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndTestcodeOrderByBatchcodeDesc(
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getSitecode(),
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getFromdate(),
								lslogilablimsorderdetail.getTodate(), testselected);
			} else if (filetype == -1 && Orderflag == null && proselected != null && testselected != null) {
				lstorder = logilablimsorderdetailsRepository
						.findByLsuserMasterAndSitecodeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndTestcodeAndLsprojectmasterOrderByBatchcodeDesc(
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getSitecode(),
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getFromdate(),
								lslogilablimsorderdetail.getTodate(), testselected, proselected);
			}

			else if (filetype != -1 && Orderflag != null && proselected == null && testselected == null) {
				lstorder = logilablimsorderdetailsRepository
						.findByLsuserMasterAndSitecodeAndFiletypeAndOrderflagAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullOrderByBatchcodeDesc(
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getSitecode(),
								lslogilablimsorderdetail.getFiletype(), Orderflag,
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getFromdate(),
								lslogilablimsorderdetail.getTodate());

			} else if (filetype != -1 && Orderflag != null && proselected != null && testselected == null) {
				lstorder = logilablimsorderdetailsRepository
						.findByLsuserMasterAndSitecodeAndFiletypeAndOrderflagAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndLsprojectmasterOrderByBatchcodeDesc(
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getSitecode(),
								lslogilablimsorderdetail.getFiletype(), Orderflag,
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getFromdate(),
								lslogilablimsorderdetail.getTodate(), proselected);
			} else if (filetype != -1 && Orderflag != null && proselected == null && testselected != null) {
				lstorder = logilablimsorderdetailsRepository
						.findByLsuserMasterAndSitecodeAndFiletypeAndOrderflagAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndTestcodeOrderByBatchcodeDesc(
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getSitecode(),
								lslogilablimsorderdetail.getFiletype(), Orderflag,
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getFromdate(),
								lslogilablimsorderdetail.getTodate(), testselected);
			} else if (filetype != -1 && Orderflag != null && proselected != null && testselected != null) {
				lstorder = logilablimsorderdetailsRepository
						.findByLsuserMasterAndSitecodeAndFiletypeAndOrderflagAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndLsprojectmasterAndTestcodeOrderByBatchcodeDesc(
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getSitecode(),
								lslogilablimsorderdetail.getFiletype(), Orderflag,
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getFromdate(),
								lslogilablimsorderdetail.getTodate(), proselected, testselected);
			}

			else if (filetype == -1 && Orderflag != null && proselected == null && testselected == null) {
				lstorder = logilablimsorderdetailsRepository
						.findByLsuserMasterAndSitecodeAndOrderflagAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullOrderByBatchcodeDesc(
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getSitecode(),
								Orderflag, lslogilablimsorderdetail.getLsuserMaster(),
								lslogilablimsorderdetail.getFromdate(), lslogilablimsorderdetail.getTodate());

			} else if (filetype == -1 && Orderflag != null && proselected != null && testselected == null) {
				lstorder = logilablimsorderdetailsRepository
						.findByLsuserMasterAndSitecodeAndOrderflagAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndLsprojectmasterOrderByBatchcodeDesc(
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getSitecode(),
								Orderflag, lslogilablimsorderdetail.getLsuserMaster(),
								lslogilablimsorderdetail.getFromdate(), lslogilablimsorderdetail.getTodate(),
								proselected);

			} else if (filetype == -1 && Orderflag != null && proselected == null && testselected != null) {
				lstorder = logilablimsorderdetailsRepository
						.findByLsuserMasterAndSitecodeAndOrderflagAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndTestcodeOrderByBatchcodeDesc(
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getSitecode(),
								Orderflag, lslogilablimsorderdetail.getLsuserMaster(),
								lslogilablimsorderdetail.getFromdate(), lslogilablimsorderdetail.getTodate(),
								testselected);

			} else if (filetype == -1 && Orderflag != null && proselected != null && testselected != null) {
				lstorder = logilablimsorderdetailsRepository
						.findByLsuserMasterAndSitecodeAndOrderflagAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndTestcodeAndLsprojectmasterOrderByBatchcodeDesc(
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getSitecode(),
								Orderflag, lslogilablimsorderdetail.getLsuserMaster(),
								lslogilablimsorderdetail.getFromdate(), lslogilablimsorderdetail.getTodate(),
								testselected, proselected);

			}

			else if (filetype != -1 && Orderflag == null && proselected == null && testselected == null) {
				lstorder = logilablimsorderdetailsRepository
						.findByLsuserMasterAndSitecodeAndFiletypeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullOrderByBatchcodeDesc(
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getSitecode(),
								lslogilablimsorderdetail.getFiletype(), lslogilablimsorderdetail.getLsuserMaster(),
								lslogilablimsorderdetail.getFromdate(), lslogilablimsorderdetail.getTodate());
			} else if (filetype != -1 && Orderflag == null && proselected != null && testselected == null) {
				lstorder = logilablimsorderdetailsRepository
						.findByLsuserMasterAndSitecodeAndFiletypeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndLsprojectmasterOrderByBatchcodeDesc(
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getSitecode(),
								lslogilablimsorderdetail.getFiletype(), lslogilablimsorderdetail.getLsuserMaster(),
								lslogilablimsorderdetail.getFromdate(), lslogilablimsorderdetail.getTodate(),
								proselected);
			} else if (filetype != -1 && Orderflag == null && proselected == null && testselected != null) {
				lstorder = logilablimsorderdetailsRepository
						.findByLsuserMasterAndSitecodeAndFiletypeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndTestcodeOrderByBatchcodeDesc(
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getSitecode(),
								lslogilablimsorderdetail.getFiletype(), lslogilablimsorderdetail.getLsuserMaster(),
								lslogilablimsorderdetail.getFromdate(), lslogilablimsorderdetail.getTodate(),
								testselected);
			} else if (filetype != -1 && Orderflag == null && proselected != null && testselected != null) {
				lstorder = logilablimsorderdetailsRepository
						.findByLsuserMasterAndSitecodeAndFiletypeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndTestcodeAndLsprojectmasterOrderByBatchcodeDesc(
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getSitecode(),
								lslogilablimsorderdetail.getFiletype(), lslogilablimsorderdetail.getLsuserMaster(),
								lslogilablimsorderdetail.getFromdate(), lslogilablimsorderdetail.getTodate(),
								testselected, proselected);
			}

		} else if (lslogilablimsorderdetail.getOrderflag().equals("M")) {

			if (filetype == -1 && Orderflag == null && proselected == null && testselected == null) {
				lstorder = logilablimsorderdetailsRepository
						.findByAssignedtoAndSitecodeAndCreatedtimestampBetweenOrderByBatchcodeDesc(
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getSitecode(),
								lslogilablimsorderdetail.getFromdate(), lslogilablimsorderdetail.getTodate());
			} else if (filetype == -1 && Orderflag == null && proselected != null && testselected == null) {
				lstorder = logilablimsorderdetailsRepository
						.findByAssignedtoAndSitecodeAndCreatedtimestampBetweenAndLsprojectmasterOrderByBatchcodeDesc(
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getSitecode(),
								lslogilablimsorderdetail.getFromdate(), lslogilablimsorderdetail.getTodate(),
								proselected);
			} else if (filetype == -1 && Orderflag == null && proselected == null && testselected != null) {
				lstorder = logilablimsorderdetailsRepository
						.findByAssignedtoAndSitecodeAndCreatedtimestampBetweenAndTestcodeOrderByBatchcodeDesc(
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getSitecode(),
								lslogilablimsorderdetail.getFromdate(), lslogilablimsorderdetail.getTodate(),
								testselected);
			} else if (filetype == -1 && Orderflag == null && proselected != null && testselected != null) {
				lstorder = logilablimsorderdetailsRepository
						.findByAssignedtoAndSitecodeAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterOrderByBatchcodeDesc(
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getSitecode(),
								lslogilablimsorderdetail.getFromdate(), lslogilablimsorderdetail.getTodate(),
								testselected, proselected);
			}

			else if (filetype != -1 && Orderflag != null && proselected == null && testselected == null) {
				lstorder = logilablimsorderdetailsRepository
						.findByAssignedtoAndSitecodeAndFiletypeAndOrderflagAndCreatedtimestampBetweenOrderByBatchcodeDesc(
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getSitecode(),
								lslogilablimsorderdetail.getFiletype(), Orderflag,
								lslogilablimsorderdetail.getFromdate(), lslogilablimsorderdetail.getTodate());
			} else if (filetype != -1 && Orderflag != null && proselected != null && testselected == null) {
				lstorder = logilablimsorderdetailsRepository
						.findByAssignedtoAndSitecodeAndFiletypeAndOrderflagAndCreatedtimestampBetweenAndLsprojectmasterOrderByBatchcodeDesc(
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getSitecode(),
								lslogilablimsorderdetail.getFiletype(), Orderflag,
								lslogilablimsorderdetail.getFromdate(), lslogilablimsorderdetail.getTodate(),
								proselected);
			} else if (filetype != -1 && Orderflag != null && proselected == null && testselected != null) {
				lstorder = logilablimsorderdetailsRepository
						.findByAssignedtoAndSitecodeAndFiletypeAndOrderflagAndCreatedtimestampBetweenAndTestcodeOrderByBatchcodeDesc(
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getSitecode(),
								lslogilablimsorderdetail.getFiletype(), Orderflag,
								lslogilablimsorderdetail.getFromdate(), lslogilablimsorderdetail.getTodate(),
								testselected);
			} else if (filetype != -1 && Orderflag != null && proselected != null && testselected != null) {
				lstorder = logilablimsorderdetailsRepository
						.findByAssignedtoAndSitecodeAndFiletypeAndOrderflagAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterOrderByBatchcodeDesc(
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getSitecode(),
								lslogilablimsorderdetail.getFiletype(), Orderflag,
								lslogilablimsorderdetail.getFromdate(), lslogilablimsorderdetail.getTodate(),
								testselected, proselected);
			}

			else if (filetype == -1 && Orderflag != null && proselected == null && testselected == null) {
				lstorder = logilablimsorderdetailsRepository
						.findByAssignedtoAndSitecodeAndOrderflagAndCreatedtimestampBetweenOrderByBatchcodeDesc(
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getSitecode(),
								Orderflag, lslogilablimsorderdetail.getFromdate(),
								lslogilablimsorderdetail.getTodate());
			} else if (filetype == -1 && Orderflag != null && proselected == null && testselected != null) {
				lstorder = logilablimsorderdetailsRepository
						.findByAssignedtoAndSitecodeAndOrderflagAndCreatedtimestampBetweenAndTestcodeOrderByBatchcodeDesc(
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getSitecode(),
								Orderflag, lslogilablimsorderdetail.getFromdate(), lslogilablimsorderdetail.getTodate(),
								testselected);
			} else if (filetype == -1 && Orderflag != null && proselected != null && testselected == null) {
				lstorder = logilablimsorderdetailsRepository
						.findByAssignedtoAndSitecodeAndOrderflagAndCreatedtimestampBetweenAndLsprojectmasterOrderByBatchcodeDesc(
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getSitecode(),
								Orderflag, lslogilablimsorderdetail.getFromdate(), lslogilablimsorderdetail.getTodate(),
								proselected);
			} else if (filetype == -1 && Orderflag != null && proselected != null && testselected != null) {
				lstorder = logilablimsorderdetailsRepository
						.findByAssignedtoAndSitecodeAndOrderflagAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterOrderByBatchcodeDesc(
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getSitecode(),
								Orderflag, lslogilablimsorderdetail.getFromdate(), lslogilablimsorderdetail.getTodate(),
								testselected, proselected);

			} else if (filetype != -1 && Orderflag == null && proselected == null && testselected == null) {
				lstorder = logilablimsorderdetailsRepository
						.findByAssignedtoAndSitecodeAndFiletypeAndCreatedtimestampBetweenOrderByBatchcodeDesc(
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getSitecode(),
								lslogilablimsorderdetail.getFiletype(), lslogilablimsorderdetail.getFromdate(),
								lslogilablimsorderdetail.getTodate());
			} else if (filetype != -1 && Orderflag == null && proselected == null && testselected != null) {
				lstorder = logilablimsorderdetailsRepository
						.findByAssignedtoAndSitecodeAndFiletypeAndCreatedtimestampBetweenAndTestcodeOrderByBatchcodeDesc(
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getSitecode(),
								lslogilablimsorderdetail.getFiletype(), lslogilablimsorderdetail.getFromdate(),
								lslogilablimsorderdetail.getTodate(), testselected);
			} else if (filetype != -1 && Orderflag == null && proselected != null && testselected == null) {
				lstorder = logilablimsorderdetailsRepository
						.findByAssignedtoAndSitecodeAndFiletypeAndCreatedtimestampBetweenAndLsprojectmasterOrderByBatchcodeDesc(
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getSitecode(),
								lslogilablimsorderdetail.getFiletype(), lslogilablimsorderdetail.getFromdate(),
								lslogilablimsorderdetail.getTodate(), proselected);
			} else if (filetype != -1 && Orderflag == null && proselected != null && testselected != null) {
				lstorder = logilablimsorderdetailsRepository
						.findByAssignedtoAndSitecodeAndFiletypeAndCreatedtimestampBetweenAndTestcodeAndLsprojectmasterOrderByBatchcodeDesc(
								lslogilablimsorderdetail.getLsuserMaster(), lslogilablimsorderdetail.getSitecode(),
								lslogilablimsorderdetail.getFiletype(), lslogilablimsorderdetail.getFromdate(),
								lslogilablimsorderdetail.getTodate(), testselected, proselected);
			}
		}

		if (lslogilablimsorderdetail.getSearchCriteria() != null
				&& lslogilablimsorderdetail.getSearchCriteria().getContentsearchtype() != null
				&& lslogilablimsorderdetail.getSearchCriteria().getContentsearch() != null) {

			lstorder = GetmyordersonFilter(lslogilablimsorderdetail, lstorder, Orderflag);

			lstorder.forEach(objorderDetail -> objorderDetail.setLw(lslogilablimsorderdetail.getLstworkflow()));
			return lstorder;
		} else {

			lstorder.forEach(objorderDetail -> objorderDetail.setLw(lslogilablimsorderdetail.getLstworkflow()));
			return lstorder;
		}
	}

	public Map<String, Object> Getusersharedorders(Map<String, Object> objusers) {

		Map<String, Object> mapuserorders = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();

		LSuserMaster lsselecteduser = mapper.convertValue(objusers.get("selecteduser"), LSuserMaster.class);
		Date fromdate = lsselecteduser.getObjuser().getFromdate();
		Date todate = lsselecteduser.getObjuser().getTodate();
		Long directory = mapper.convertValue(objusers.get("directorycode"), Long.class);
		Integer filetype = mapper.convertValue(objusers.get("filetype"), Integer.class);

		Integer selecttest = mapper.convertValue(objusers.get("testcode"), Integer.class);
		LSprojectmaster selectproject = mapper.convertValue(objusers.get("lsprojectmaster"), LSprojectmaster.class);
		Integer ismultitenant = mapper.convertValue(objusers.get("ismultitenant"), Integer.class);

		List<OrderShareInterface> orderlst = new ArrayList<OrderShareInterface>();

		LSlogilablimsorderdetail logisearch = mapper.convertValue(objusers.get("lslogilablimsorderdetail"),
				LSlogilablimsorderdetail.class);
//		 new TypeReference<List<LSfile>>()
		List<LSworkflow> lstworkflow = mapper.convertValue(objusers.get("lstworkflow"),
				new TypeReference<List<LSworkflow>>() {
				});

//(List<LSworkflow>) objusers.get("lstworkflow");

		List<Lsordersharedby.LsordersharebyProjection> sharedbyobj = new ArrayList<Lsordersharedby.LsordersharebyProjection>();
		List<Lsordershareto.LsordersharetoProjection> sharedtoobj = new ArrayList<Lsordershareto.LsordersharetoProjection>();
		List<Integer> lstworkflowcode = lstworkflow.stream().map(LSworkflow::getWorkflowcode)
				.collect(Collectors.toList());
		List<LSworkflow> workflowobj = lsworkflowRepository
				.findByLssitemasterAndStatusOrderByWorkflowcodeAsc(lsselecteduser.getLssitemaster(), 1);

		if (objusers.get("filefor").equals("OSBM")) {
			if (filetype == -1 && selecttest == null && selectproject == null) {

				sharedbyobj = lsordersharedbyRepository
						.findByUsersharedbyAndSitecodeAndSharestatusAndSharedonBetweenOrderBySharedbycodeDesc(
								lsselecteduser, lsselecteduser.getLssitemaster().getSitecode(), 1, fromdate, todate);

//				mapuserorders.put("sharebyme", sharedbyobj);
			} else if (filetype == -1 && selecttest != null && selectproject == null) {

				sharedbyobj = lsordersharedbyRepository
						.findByUsersharedbyAndSitecodeAndSharestatusAndSharedonBetweenAndOrder_TestcodeOrderBySharedbycodeDesc(
								lsselecteduser, lsselecteduser.getLssitemaster().getSitecode(), 1, fromdate, todate,
								selecttest);

//				mapuserorders.put("sharebyme", sharedbyobj);
			} else if (filetype == -1 && selecttest == null && selectproject != null) {

				sharedbyobj = lsordersharedbyRepository
						.findByUsersharedbyAndSitecodeAndSharestatusAndSharedonBetweenAndOrder_LsprojectmasterOrderBySharedbycodeDesc(
								lsselecteduser, lsselecteduser.getLssitemaster().getSitecode(), 1, fromdate, todate,
								selectproject);

//				mapuserorders.put("sharebyme", sharedbyobj);
			} else if (filetype == -1 && selecttest != null && selectproject != null) {

				sharedbyobj = lsordersharedbyRepository
						.findByUsersharedbyAndSitecodeAndSharestatusAndSharedonBetweenAndOrder_LsprojectmasterAndOrder_TestcodeOrderBySharedbycodeDesc(
								lsselecteduser, lsselecteduser.getLssitemaster().getSitecode(), 1, fromdate, todate,
								selectproject, selecttest);

//				mapuserorders.put("sharebyme", sharedbyobj);
			}

			else if (filetype != -1 && selecttest == null && selectproject == null) {

				sharedbyobj = lsordersharedbyRepository
						.findByUsersharedbyAndSitecodeAndOrdertypeAndSharestatusAndSharedonBetweenOrderBySharedbycodeDesc(
								lsselecteduser, lsselecteduser.getLssitemaster().getSitecode(), filetype, 1, fromdate,
								todate);

//				mapuserorders.put("sharebyme", sharedbyobj);
			} else if (filetype != -1 && selecttest != null && selectproject == null) {

				sharedbyobj = lsordersharedbyRepository
						.findByUsersharedbyAndSitecodeAndOrdertypeAndSharestatusAndSharedonBetweenAndOrder_TestcodeOrderBySharedbycodeDesc(
								lsselecteduser, lsselecteduser.getLssitemaster().getSitecode(), filetype, 1, fromdate,
								todate, selecttest);

//				mapuserorders.put("sharebyme", sharedbyobj);
			} else if (filetype != -1 && selecttest == null && selectproject != null) {
				sharedbyobj = lsordersharedbyRepository
						.findByUsersharedbyAndSitecodeAndOrdertypeAndSharestatusAndSharedonBetweenAndOrder_LsprojectmasterOrderBySharedbycodeDesc(
								lsselecteduser, lsselecteduser.getLssitemaster().getSitecode(), filetype, 1, fromdate,
								todate, selectproject);

//				mapuserorders.put("sharebyme", sharedbyobj);
			} else if (filetype != -1 && selecttest != null && selectproject != null) {
				sharedbyobj = lsordersharedbyRepository
						.findByUsersharedbyAndSitecodeAndOrdertypeAndSharestatusAndSharedonBetweenAndOrder_TestcodeAndOrder_LsprojectmasterOrderBySharedbycodeDesc(
								lsselecteduser, lsselecteduser.getLssitemaster().getSitecode(), filetype, 1, fromdate,
								todate, selecttest, selectproject);

			}
			for (Lsordersharedby.LsordersharebyProjection order : sharedbyobj) {
				processOrderCanUserProcess(order.getOrder(), workflowobj, lstworkflowcode, lsselecteduser);
			}
			mapuserorders.put("sharebyme", sharedbyobj);

		} else if (objusers.get("filefor").equals("OSTM")) {
			if (filetype == -1 && selecttest == null && selectproject == null) {
				sharedtoobj = lsordersharetoRepository
						.findByUsersharedonAndSitecodeAndSharestatusAndSharedonBetweenOrderBySharetocodeDesc(
								lsselecteduser, lsselecteduser.getLssitemaster().getSitecode(), 1, fromdate, todate);
//				mapuserorders.put("sharetome", sharedtoobj);
			} else if (filetype == -1 && selecttest != null && selectproject == null) {
				sharedtoobj = lsordersharetoRepository
						.findByUsersharedonAndSitecodeAndSharestatusAndSharedonBetweenAndOrder_TestcodeOrderBySharetocodeDesc(
								lsselecteduser, lsselecteduser.getLssitemaster().getSitecode(), 1, fromdate, todate,
								selecttest);
//				mapuserorders.put("sharetome", sharedtoobj);
			} else if (filetype == -1 && selecttest == null && selectproject != null) {
				sharedtoobj = lsordersharetoRepository
						.findByUsersharedonAndSitecodeAndSharestatusAndSharedonBetweenAndOrder_LsprojectmasterOrderBySharetocodeDesc(
								lsselecteduser, lsselecteduser.getLssitemaster().getSitecode(), 1, fromdate, todate,
								selectproject);
//				mapuserorders.put("sharetome", sharedtoobj);
			} else if (filetype == -1 && selecttest != null && selectproject != null) {
				sharedtoobj = lsordersharetoRepository
						.findByUsersharedonAndSitecodeAndSharestatusAndSharedonBetweenAndOrder_LsprojectmasterAndOrder_TestcodeOrderBySharetocodeDesc(
								lsselecteduser, lsselecteduser.getLssitemaster().getSitecode(), 1, fromdate, todate,
								selectproject, selecttest);
//				mapuserorders.put("sharetome", sharedtoobj);
			}

			else if (filetype != -1 && selecttest == null && selectproject == null) {
				sharedtoobj = lsordersharetoRepository
						.findByUsersharedonAndSitecodeAndOrdertypeAndSharestatusAndSharedonBetweenOrderBySharetocodeDesc(
								lsselecteduser, lsselecteduser.getLssitemaster().getSitecode(), filetype, 1, fromdate,
								todate);
//				mapuserorders.put("sharetome", sharedtoobj);
			} else if (filetype != -1 && selecttest != null && selectproject == null) {
				sharedtoobj = lsordersharetoRepository
						.findByUsersharedonAndSitecodeAndOrdertypeAndSharestatusAndSharedonBetweenAndOrder_TestcodeOrderBySharetocodeDesc(
								lsselecteduser, lsselecteduser.getLssitemaster().getSitecode(), filetype, 1, fromdate,
								todate, selecttest);
//				mapuserorders.put("sharetome", sharedtoobj);
			} else if (filetype != -1 && selecttest == null && selectproject != null) {
				sharedtoobj = lsordersharetoRepository
						.findByUsersharedonAndSitecodeAndOrdertypeAndSharestatusAndSharedonBetweenAndOrder_LsprojectmasterOrderBySharetocodeDesc(
								lsselecteduser, lsselecteduser.getLssitemaster().getSitecode(), filetype, 1, fromdate,
								todate, selectproject);
//				mapuserorders.put("sharetome", sharedtoobj);
			} else if (filetype != -1 && selecttest != null && selectproject != null) {
				sharedtoobj = lsordersharetoRepository
						.findByUsersharedonAndSitecodeAndOrdertypeAndSharestatusAndSharedonBetweenAndOrder_LsprojectmasterAndOrder_TestcodeOrderBySharetocodeDesc(
								lsselecteduser, lsselecteduser.getLssitemaster().getSitecode(), filetype, 1, fromdate,
								todate, selectproject, selecttest);
//				mapuserorders.put("sharetome", sharedtoobj);
			}
			for (Lsordershareto.LsordersharetoProjection order : sharedtoobj) {
				processOrderCanUserProcess(order.getOrder(), workflowobj, lstworkflowcode, lsselecteduser);
			}
			mapuserorders.put("sharetome", sharedtoobj);
		}

		List<Lsordersharedby.LsordersharebyProjection> finalsharedbylist = new ArrayList<Lsordersharedby.LsordersharebyProjection>();
		List<Lsordershareto.LsordersharetoProjection> finalsharedtolist = new ArrayList<Lsordershareto.LsordersharetoProjection>();

		if (!sharedbyobj.isEmpty()) {
			List<OrderShareInterface> lstorder = sharedbyobj.stream()
					.map(Lsordersharedby.LsordersharebyProjection::getOrder).collect(Collectors.toList());

			if (logisearch.getSearchCriteria() != null && logisearch.getSearchCriteria().getContentsearchtype() != null
					&& logisearch.getSearchCriteria().getContentsearch() != null) {

				LSlogilablimsorderdetail orderdetail = new LSlogilablimsorderdetail();
				orderdetail.setFromdate(fromdate);
				orderdetail.setTodate(todate);
				orderdetail.setIsmultitenant(ismultitenant);
				orderdetail.setSearchCriteria(logisearch.getSearchCriteria());
				orderdetail.setFiletype(filetype);
				orderdetail.setDirectorycode(directory);

				orderlst = GetsharedordersonFilter(orderdetail, lstorder);

				final List<OrderShareInterface> finalOrderlst = orderlst;

				finalsharedbylist = sharedbyobj.stream()
						.filter(srow1 -> finalOrderlst.stream()
								.anyMatch(detailrow -> srow1.getSharebatchcode().equals(detailrow.getBatchcode())))
						.collect(Collectors.toList());

			}
		}

		else {
			List<OrderShareInterface> lstorder = sharedtoobj.stream()
					.map(Lsordershareto.LsordersharetoProjection::getOrder).collect(Collectors.toList());

			if (logisearch.getSearchCriteria() != null && logisearch.getSearchCriteria().getContentsearchtype() != null
					&& logisearch.getSearchCriteria().getContentsearch() != null) {

				LSlogilablimsorderdetail orderdetails = new LSlogilablimsorderdetail();

				orderdetails.setFromdate(fromdate);
				orderdetails.setTodate(todate);
				orderdetails.setIsmultitenant(ismultitenant);
				orderdetails.setSearchCriteria(logisearch.getSearchCriteria());
				orderdetails.setFiletype(filetype);
				orderdetails.setDirectorycode(directory);

				orderlst = GetsharedordersonFilter(orderdetails, lstorder);

				final List<OrderShareInterface> finalOrderlst = orderlst;

				finalsharedtolist = sharedtoobj.stream()
						.filter(srow1 -> finalOrderlst.stream()
								.anyMatch(detailrow -> srow1.getSharebatchcode().equals(detailrow.getBatchcode())))
						.collect(Collectors.toList());

			}
		}

		if (logisearch.getSearchCriteria() != null && logisearch.getSearchCriteria().getContentsearchtype() != null
				&& logisearch.getSearchCriteria().getContentsearch() != null) {
//			logisearch
//			lstorder.forEach(objorderDetail -> objorderDetail.setLstworkflow(objorder.getLstworkflow()));

//			finalsharedbylist.forEach(order -> order.getOrder().se);
			if (!finalsharedbylist.isEmpty()) {
				map.put("sharebyme", finalsharedbylist);
				map.put("directorycode", directory);
				return map;
			} else {
				map.put("sharetome", finalsharedtolist);
				map.put("directorycode", directory);
				return map;
			}
		} else {
			mapuserorders.put("directorycode", directory);
			return mapuserorders;
		}
	}

	private void processOrderCanUserProcess(OrderShareInterface order, List<LSworkflow> workflowobj,
			List<Integer> lstworkflowcode, LSuserMaster logisearch) {

		if (order.getLsworkflow()!=null && lstworkflowcode.contains(order.getLsworkflow().getWorkflowcode())) {
			int previousworkflowIndex = -1;
			if (order.getPreviousapprovedworkflow() != null) {
				previousworkflowIndex = IntStream.range(0, workflowobj.size()).filter(i -> workflowobj.get(i)
						.getWorkflowcode() == order.getPreviousapprovedworkflow().getWorkflowcode()).findFirst()
						.orElse(-1);
			}
			int currentworkflowIndex = -1;
			if (order.getLsworkflow() != null) {
				currentworkflowIndex = IntStream.range(0, workflowobj.size())
						.filter(i -> workflowobj.get(i).getWorkflowcode() == order.getLsworkflow().getWorkflowcode())
						.findFirst().orElse(-1);
			}
			boolean selfapproval = false;
			if (order.getLsworkflow() != null && Boolean.TRUE.equals(order.getLsworkflow().getSelfapproval())) {
				selfapproval = true;
			} else if (order.getLsworkflow() != null && Boolean.FALSE.equals(order.getLsworkflow().getSelfapproval())) {
				Integer wau = order.getWorkflowapprovedusercode();
				int usercode = logisearch.getUsercode();

				if (((wau != null && wau == usercode
						&& (order.getApproved() != null && order.getApproved() == 2
								? previousworkflowIndex - 1 != currentworkflowIndex
								: previousworkflowIndex + 1 != currentworkflowIndex))
						|| (wau != null && wau != usercode)) || (previousworkflowIndex == -1 || wau == null)) {
					selfapproval = true;
				}
			}
			order.setCanuserprocess(selfapproval);
		} else {
			order.setCanuserprocess(false);
		}
	}

	public List<LSSheetOrderStructure> Updateparentforfolder(LSSheetOrderStructure[] folders) {
		List<LSSheetOrderStructure> lstfolders = Arrays.asList(folders);
		List<LSSheetOrderStructure> existinglist = new ArrayList<LSSheetOrderStructure>();
		if (lstfolders.size() > 0) {
			List<Long> lstfoldersid = lstfolders.stream().map(LSSheetOrderStructure::getDirectorycode)
					.collect(Collectors.toList());
			existinglist = lsSheetOrderStructureRepository.findByDirectorycodeIn(lstfoldersid);
			if (existinglist != null) {
				existinglist = existinglist.stream().map(folder -> {
					Long directorycode = folder.getDirectorycode();
					Optional<LSSheetOrderStructure> objfolder = lstfolders.stream()
							.filter(argfolder -> argfolder.getDirectorycode().equals(directorycode)).findFirst();
					if (objfolder != null) {
						folder.setParentdircode(objfolder.get().getParentdircode());
						folder.setPath(objfolder.get().getPath());
					}
					return folder;
				}).collect(Collectors.toList());
				lsSheetOrderStructureRepository.saveAll(existinglist);
			}
		}
		return existinglist;
	}

	public Response validateprotocolexistonfolder(LSprotocolfolderfiles objfile) {
		Response response = new Response();
		long filecount = lsprotocolfolderfilesRepository.countByDirectorycodeAndFileforAndFilename(
				objfile.getDirectorycode(), objfile.getFilefor(), objfile.getFilename());

		if (filecount > 0) {
			response.setStatus(false);
		} else {
			response.setStatus(true);
		}
		return response;
	}

	public Map<String, Object> uploadfilesprotocolfolder(MultipartFile file, String uid, Long directorycode,
			String filefor, String tenantid, Integer ismultitenant, Integer usercode, Integer sitecode,
			Date createddate, Integer fileviewfor) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		LSprotocolfolderfiles objfile = new LSprotocolfolderfiles();
		objfile.setFilename(file.getOriginalFilename());
		objfile.setDirectorycode(directorycode);
		objfile.setFilefor(filefor);
		Response response = new Response();
//		Response response = validateprotocolexistonfolder(objfile);
//		if (response.getStatus()) {
		String uuID = "";
		if (ismultitenant == 1 || ismultitenant == 2) {
			uuID = cloudFileManipulationservice.storecloudfilesreturnwithpreUUID(file, "protocolfolderfiles", uid,
					ismultitenant);
		} else {
			uuID = fileManipulationservice.storeLargeattachmentwithpreuid(file.getOriginalFilename(), file, uid);
		}

		LSprotocolfolderfiles parentobjattachment = lsprotocolfolderfilesRepository
				.findFirst1ByDirectorycodeAndFilenameOrderByFolderfilecode(directorycode, file.getOriginalFilename());

		LSprotocolfolderfiles lsfiles = new LSprotocolfolderfiles();
		lsfiles.setUuid(uuID);
		lsfiles.setFilesize(file.getSize());
		lsfiles.setDirectorycode(directorycode);
		if (parentobjattachment != null && file.getOriginalFilename() != null
				&& file.getOriginalFilename().lastIndexOf(".") > -1) {
			Integer versiondata = parentobjattachment.getVersion() != null ? parentobjattachment.getVersion() + 1 : 1;
			String originalname = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."));
			String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),
					file.getOriginalFilename().length());
			lsfiles.setFilename(originalname + "_V" + (versiondata) + extension);
			parentobjattachment.setVersion(versiondata);
			lsprotocolfolderfilesRepository.save(parentobjattachment);
		} else {
			lsfiles.setFilename(file.getOriginalFilename());
		}
		LSuserMaster lsuser = new LSuserMaster();
		lsuser.setUsercode(usercode);
		lsfiles.setCreateby(lsuser);
		LSSiteMaster lssite = new LSSiteMaster();
		lssite.setSitecode(sitecode);
		lsfiles.setLssitemaster(lssite);
		lsfiles.setFilefor(filefor);
		lsfiles.setCreatedtimestamp(createddate);
		lsfiles.setFileviewfor(fileviewfor);

		lsprotocolfolderfilesRepository.save(lsfiles);

//		} else {
//			response.setInformation("IDS_INFO_FILEEXIST");
//		}
		response.setStatus(true);
		map.put("res", response);
		map.put("uid", uid);
		map.put("name", lsfiles.getFilename());
		return map;
	}

	public List<LSprotocolfolderfiles> Getfilesforprotocolfolder(LSprotocolfolderfiles objfiles) {

		List<LSprotocolfolderfiles> lstfiles = new ArrayList<LSprotocolfolderfiles>();

//		lstfiles = lsprotocolfolderfilesRepository
//				.findByDirectorycodeAndFileforAndCreatedtimestampBetweenOrderByFolderfilecode(
//						objfiles.getDirectorycode(), objfiles.getFilefor(), objfiles.getFromdate(),
//						objfiles.getTodate());

		lstfiles = lsprotocolfolderfilesRepository
				.findByDirectorycodeAndFileforAndCreatedtimestampBetweenAndLssitemasterOrderByFolderfilecode(
						objfiles.getDirectorycode(), objfiles.getFilefor(), objfiles.getFromdate(),
						objfiles.getTodate(), objfiles.getLssitemaster());

		return lstfiles;
	}

	public ResponseEntity<InputStreamResource> downloadprotocolfileforfolder(Integer multitenant, String tenant,
			String fileid) throws IOException {
		HttpHeaders header = new HttpHeaders();
		header.set("Content-Disposition", "attachment; filename=" + fileid);

		if (multitenant == 1) {
			InputStream fileStream = cloudFileManipulationservice.retrieveCloudFile(fileid,
					tenant + "protocolfolderfiles");
			InputStreamResource resource = null;
			byte[] content = IOUtils.toByteArray(fileStream);
			int size = content.length;
			InputStream is = null;
			try {
				is = new ByteArrayInputStream(content);
				resource = new InputStreamResource(is);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (is != null)
						is.close();
				} catch (Exception ex) {

				}
			}

			header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			header.setContentLength(size);
			return new ResponseEntity<>(resource, header, HttpStatus.OK);
		} else {
			GridFsResource gridFsFile = null;

			try {
				gridFsFile = retrieveLargeFile(fileid);
			} catch (IllegalStateException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
			System.out.println(gridFsFile.getContentType());
			header.setContentType(MediaType.parseMediaType(gridFsFile.getContentType()));
//			header.setContentLength(gridFsFile.getLength());
			return new ResponseEntity<>(new InputStreamResource(gridFsFile.getInputStream()), header, HttpStatus.OK);
		}
	}

	public Map<String, Object> deleteprotocolfilesforfolder(String uid, Long directorycode, String filefor,
			String tenantid, Integer ismultitenant, Integer usercode, Integer sitecode, Date createddate,
			Integer fileviewfor) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("file", lsprotocolfolderfilesRepository.findByUuid(uid));
		lsprotocolfolderfilesRepository.deleteByUuid(uid);
		if (ismultitenant == 1 || ismultitenant == 2) {
			cloudFileManipulationservice.deletecloudFile(uid, "protocolfolderfiles");
		} else {
			fileManipulationservice.deletelargeattachments(uid);
		}
		map.put("uid", uid);
		return map;
	}

	public List<LSprotocolfolderfiles> UpdateprotocolFolderforfiles(LSprotocolfolderfiles[] files) {
		List<LSprotocolfolderfiles> lstfile = Arrays.asList(files);
		if (lstfile.size() > 0) {
			List<Integer> lstfilesid = lstfile.stream().map(LSprotocolfolderfiles::getFolderfilecode)
					.collect(Collectors.toList());
			lsprotocolfolderfilesRepository.updatedirectory(lstfile.get(0).getDirectorycode(), lstfilesid);
		}
		return lstfile;
	}

	public List<Lsprotocolorderstructure> Updateprotocolparentforfolder(Lsprotocolorderstructure[] folders) {
		List<Lsprotocolorderstructure> lstfolders = Arrays.asList(folders);
		List<Lsprotocolorderstructure> existinglist = new ArrayList<Lsprotocolorderstructure>();
		if (lstfolders.size() > 0) {
			List<Long> lstfoldersid = lstfolders.stream().map(Lsprotocolorderstructure::getDirectorycode)
					.collect(Collectors.toList());
			existinglist = lsprotocolorderStructurerepository.findByDirectorycodeIn(lstfoldersid);
			if (existinglist != null) {
				existinglist = existinglist.stream().map(folder -> {
					Optional<Lsprotocolorderstructure> objfolder = lstfolders.stream().filter(
							argfolder -> Objects.equals(argfolder.getDirectorycode(), folder.getDirectorycode()))
							.findFirst();
					folder.setParentdircode(objfolder.get().getParentdircode());
					folder.setPath(objfolder.get().getPath());
					return folder;
				}).collect(Collectors.toList());
				lsprotocolorderStructurerepository.saveAll(existinglist);
			}
		}
		return existinglist;
	}

	public LSprotocolfolderfiles UpdateprotocolFolderforfile(LSprotocolfolderfiles file) {
		lsprotocolfolderfilesRepository.updatedirectory(file.getDirectorycode(), file.getFolderfilecode());
		return file;
	}

	public List<LSprotocolfolderfiles> Getaddedprotocolfilesforfolder(List<String> lstuuid) {
		List<LSprotocolfolderfiles> lstfiles = new ArrayList<LSprotocolfolderfiles>();
		lstfiles = lsprotocolfolderfilesRepository.findByUuidInOrderByFolderfilecode(lstuuid);
		return lstfiles;
	}

	public LSlogilablimsorderdetail cancelprotocolorder(LSlogilablimsorderdetail body) {

		LSlogilablimsorderdetail obj = lslogilablimsorderdetailRepository
				.findByBatchcodeOrderByBatchcodeDesc(body.getBatchcode());
		obj.setOrdercancell(body.getOrdercancell());
		obj.setObjLoggeduser(body.getObjLoggeduser());
		lslogilablimsorderdetailRepository.save(obj);
		body.setSequenceid(obj.getSequenceid());
		try {
			updateNotificationForCancelOrder(obj);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		Number batchid = body.getBatchcode();
		lsActiveWidgetsRepository.updateRetirestatus(batchid);
		return body;
	}

	private void updateNotificationForCancelOrder(LSlogilablimsorderdetail objorder) throws ParseException {
		String Details = "";
		String Notifiction = "";

		String batchid = "";

		batchid = objorder.getSequenceid() != null ? objorder.getSequenceid() : objorder.getBatchid();

		int workflowcode = objorder.getLsworkflow() != null ? objorder.getLsworkflow().getWorkflowcode() : -1;
		String workflowname = objorder.getLsworkflow() != null ? objorder.getLsworkflow().getWorkflowname() : "";

		if (objorder != null && objorder.getLsprojectmaster() != null
				&& objorder.getLsprojectmaster().getLsusersteam() != null && objorder.getAssignedto() == null) {

			if (lsusersteamRepository.findByteamcode(objorder.getLsprojectmaster().getLsusersteam().getTeamcode())
					.getLsuserteammapping() != null
					&& lsusersteamRepository
							.findByteamcode(objorder.getLsprojectmaster().getLsusersteam().getTeamcode())
							.getLsuserteammapping().size() > 0) {

				Notifiction = "ORDERCANCEL";

				Details = "{\"ordercode\":\"" + objorder.getBatchcode() + "\", \"order\":\"" + batchid
						+ "\", \"currentworkflow\":\"" + workflowname + "\", \"currentworkflowcode\":\"" + workflowcode
						+ "\", \"notifyFor\":\"" + 2 + "\", \"user\":\"" + objorder.getObjLoggeduser().getUsername()
						+ "\"}";

				List<LSuserteammapping> lstusers = lsusersteamRepository
						.findByteamcode(objorder.getLsprojectmaster().getLsusersteam().getTeamcode())
						.getLsuserteammapping();
				List<LSnotification> lstnotifications = new ArrayList<LSnotification>();
				for (int i = 0; i < lstusers.size(); i++) {
					if (!(objorder.getObjLoggeduser().getUsercode()
							.equals(lstusers.get(i).getLsuserMaster().getUsercode()))
							&& !(objorder.getLsuserMaster().getUsercode()
									.equals(lstusers.get(i).getLsuserMaster().getUsercode()))) {
						LSnotification objnotify = new LSnotification();
						objnotify.setNotifationfrom(objorder.getObjLoggeduser());
						objnotify.setNotifationto(lstusers.get(i).getLsuserMaster());
						objnotify.setNotificationdate(commonfunction.getCurrentUtcTime());
						objnotify.setNotification(Notifiction);
						objnotify.setNotificationdetils(Details);
						objnotify.setIsnewnotification(1);
						objnotify.setNotificationpath("/registertask");
						objnotify.setNotificationfor(2);
						if (objorder.getSitecode() != null) {
							objnotify.setSitecode(objorder.getSitecode());
						}
						lstnotifications.add(objnotify);
					}
				}

				lsnotificationRepository.saveAll(lstnotifications);
			}
		}

		Notifiction = "ORDERCANCEL";

		Details = "{\"ordercode\":\"" + objorder.getBatchcode() + "\", \"order\":\"" + batchid
				+ "\", \"currentworkflow\":\"" + workflowname + "\", \"currentworkflowcode\":\"" + workflowcode
				+ "\", \"notifyFor\":\"" + 1 + "\", \"user\":\"" + objorder.getObjLoggeduser().getUsername() + "\"}";

//		LSuserMaster createby = lsuserMasterRepository.findByusercode(objorder.getLsuserMaster().getUsercode());
		LSnotification objnotify = new LSnotification();
		objnotify.setNotifationfrom(objorder.getObjLoggeduser());
		objnotify.setNotifationto(objorder.getLsuserMaster());
		objnotify.setNotificationdate(commonfunction.getCurrentUtcTime());
		objnotify.setNotification(Notifiction);
		objnotify.setNotificationdetils(Details);
		objnotify.setIsnewnotification(1);
		objnotify.setNotificationpath("/registertask");
		objnotify.setNotificationfor(1);
		if (objorder.getSitecode() != null) {
			objnotify.setSitecode(objorder.getSitecode());
		}
		lsnotificationRepository.save(objnotify);
		batchid = null;

	}

	public Map<String, Object> removemultifilessheetfolderonprotocol(LSprotocolfolderfiles[] files, Long directorycode,
			String filefor, String tenantid, Integer ismultitenant, Integer usercode, Integer sitecode,
			Date createddate, Integer fileviewfor) {
		Map<String, Object> map = new HashMap<String, Object>();

		List<LSprotocolfolderfiles> lstfile = Arrays.asList(files);
		if (lstfile.size() > 0) {
			List<String> lstfilesid = lstfile.stream().map(LSprotocolfolderfiles::getUuid).collect(Collectors.toList());

			lsprotocolfolderfilesRepository.deleteByUuidIn(lstfilesid);

			for (String uuididex : lstfilesid) {

				if (ismultitenant == 1 || ismultitenant == 2) {
					cloudFileManipulationservice.deletecloudFile(uuididex, "protocolfolderfiles");
				} else {
					fileManipulationservice.deletelargeattachments(uuididex);
				}

			}

		}

		map.put("lstfilesid", lstfile);
		return map;
	}

	public Map<String, Object> Getfoldersfordashboard(LSuserMaster lsusermaster) {
		Map<String, Object> mapfolders = new HashMap<String, Object>();
		List<Long> immutableNegativeValues = Arrays.asList(-3L, -22L);
		List<Lsprotocolorderstructure> lstdirpro = new ArrayList<Lsprotocolorderstructure>();
		if (lsusermaster.getActiveusercode() != null && lsusermaster.getActiveusercode() == 2) {
			if (lsusermaster.getUsernotify() == null) {
				lstdirpro = lsprotocolorderStructurerepository
						.findBySitemasterAndViewoptionAndDirectorycodeNotInOrCreatedbyAndViewoptionOrCreatedbyAndViewoptionOrderByDirectorycode(
								lsusermaster.getActiveusersite(), 1, immutableNegativeValues, lsusermaster, 2,
								lsusermaster, 3);
			} else {
				lstdirpro = lsprotocolorderStructurerepository
						.findBySitemasterAndViewoptionAndDirectorycodeNotInOrCreatedbyAndViewoptionOrSitemasterAndViewoptionAndCreatedbyInOrderByDirectorycode(
								lsusermaster.getActiveusersite(), 1, immutableNegativeValues, lsusermaster, 2,
								lsusermaster.getActiveusersite(), 3, lsusermaster.getUsernotify());
			}
			lstdirpro.addAll(lsprotocolorderStructurerepository.findByDirectorycodeIn(immutableNegativeValues));
			mapfolders.put("directorypro", lstdirpro);
		} else if (lsusermaster.getActiveusercode() != null && lsusermaster.getActiveusercode() == 1) {
			List<LSSheetOrderStructure> lstdir = new ArrayList<LSSheetOrderStructure>();

			if (lsusermaster.getUsernotify() == null) {
				lstdir = lsSheetOrderStructureRepository
						.findBySitemasterAndViewoptionAndDirectorycodeNotInOrCreatedbyAndViewoptionOrCreatedbyAndViewoptionOrderByDirectorycode(
								lsusermaster.getActiveusersite(), 1, immutableNegativeValues, lsusermaster, 2,
								lsusermaster, 3);
			} else {
				lstdir = lsSheetOrderStructureRepository
						.findBySitemasterAndViewoptionAndDirectorycodeNotInOrCreatedbyAndViewoptionOrSitemasterAndViewoptionAndCreatedbyInOrderByDirectorycode(
								lsusermaster.getActiveusersite(), 1, immutableNegativeValues, lsusermaster, 2,
								lsusermaster.getActiveusersite(), 3, lsusermaster.getUsernotify());

			}
			lstdir.addAll(lsSheetOrderStructureRepository.findByDirectorycodeIn(immutableNegativeValues));
			mapfolders.put("directory", lstdir);
		} else {
			List<LSSheetOrderStructure> lstdir = new ArrayList<LSSheetOrderStructure>();
			List<LSuserteammapping> lstteammap = lsuserteammappingRepository
					.findByLsuserMasterAndTeamcodeNotNull(lsusermaster);
			List<LSusersteam> lstteam = lsusersteamRepository.findByLsuserteammappingInAndLssitemaster(lstteammap,
					lsusermaster.getLssitemaster());
			List<Integer> lstteamcode = new ArrayList<Integer>();
			if (lstteam != null && lstteam.size() > 0) {
				lstteamcode = lstteam.stream().map(LSusersteam::getTeamcode).collect(Collectors.toList());
			}
			List<LSuserMaster> lstusers = new ArrayList<LSuserMaster>();
			if (lstteammap != null && lstteammap.size() > 0 && !lstteamcode.isEmpty()) {
				lstusers = lsuserteammappingRepository.getLsuserMasterByTeamcode(lstteamcode);

			}
			if (lstusers == null || lstusers.isEmpty()) {
				lstdir = lsSheetOrderStructureRepository
						.findBySitemasterAndViewoptionAndDirectorycodeNotInOrCreatedbyAndViewoptionOrCreatedbyAndViewoptionOrderByDirectorycode(
								lsusermaster.getActiveusersite(), 1, immutableNegativeValues, lsusermaster, 2,
								lsusermaster, 3);
			} else {
				lstdir = lsSheetOrderStructureRepository
						.findBySitemasterAndViewoptionAndDirectorycodeNotInOrCreatedbyInAndViewoptionOrSitemasterAndViewoptionAndCreatedbyInOrderByDirectorycode(
								lsusermaster.getActiveusersite(), 1, immutableNegativeValues, lstusers, 2,
								lsusermaster.getActiveusersite(), 3, lstusers);

			}
			lstdir.addAll(lsSheetOrderStructureRepository.findByDirectorycodeIn(immutableNegativeValues));
			if (lstusers == null || lstusers.isEmpty()) {
				lstdirpro = lsprotocolorderStructurerepository
						.findBySitemasterAndViewoptionAndDirectorycodeNotInOrCreatedbyAndViewoptionOrCreatedbyAndViewoptionOrderByDirectorycode(
								lsusermaster.getActiveusersite(), 1, immutableNegativeValues, lsusermaster, 2,
								lsusermaster, 3);
			} else {
				lstdirpro = lsprotocolorderStructurerepository
						.findBySitemasterAndViewoptionAndDirectorycodeNotInOrCreatedbyInAndViewoptionOrSitemasterAndViewoptionAndCreatedbyInOrderByDirectorycode(
								lsusermaster.getActiveusersite(), 1, immutableNegativeValues, lstusers, 2,
								lsusermaster.getActiveusersite(), 3, lstusers);
			}
			lstdirpro.addAll(lsprotocolorderStructurerepository.findByDirectorycodeIn(immutableNegativeValues));
			mapfolders.put("directorypro", lstdirpro);
			mapfolders.put("directory", lstdir);

		}
		if (!lsusermaster.isReset()) {
			if (lsusermaster.getLstproject() != null && lsusermaster.getLstproject().size() > 0) {
				ArrayList<List<Object>> lsttest = new ArrayList<List<Object>>();
				List<Integer> lsprojectcode = lsusermaster.getLstproject().stream().map(LSprojectmaster::getProjectcode)
						.collect(Collectors.toList());
				List<LSprojectmaster> lstproject = lsprojectmasterRepository.findAllById(lsprojectcode);
				lsttest = lslogilablimsorderdetailRepository
						.getLstestmasterlocalByOrderdisplaytypeAndLsprojectmasterInAndTestcodeIsNotNull(lsprojectcode);

				mapfolders.put("tests", lsttest);
				mapfolders.put("projects", lstproject);
			} else {
				mapfolders.put("tests", new ArrayList<Logilaborders>());
				mapfolders.put("projects", new ArrayList<Projectmaster>());
			}
			List<Elnmaterial> Matireal_List = elnmaterialRepository
					.findByNsitecodeOrderByNmaterialcodeDesc(lsusermaster.getLssitemaster().getSitecode());
			List<Object> Material_List_Ordes = LSlogilabprotocoldetailRepository
					.getLstestmasterlocalAndmaterialByOrderdisplaytypeAndLSsamplemasterInAndTestcodeIsNotNull(
							lsusermaster.getLssitemaster().getSitecode());
			mapfolders.put("Materialtest", Material_List_Ordes);
			mapfolders.put("Material", Matireal_List);
		}

		return mapfolders;
	}

	public void onDeleteforCancel(List<String> lstuuid, String screen) {
		if (!lstuuid.isEmpty()) {
			if (screen.equals("sheet")) {
				lssheetfolderfilesRepository.removeForFile(lstuuid);
			} else if (screen.equals("protocol")) {
				lsprotocolfolderfilesRepository.removeForFile(lstuuid);
			}
		}

	}

	public Map<String, Object> getimagesforlink(Map<String, Object> obj) {
		Map<String, Object> rtnobj = new HashMap<String, Object>();
		Integer onTabKey = (Integer) obj.get("ontabkey");
		long directorycode = ((Number) obj.get("directorycode")).longValue();
		Integer uploadtype = (Integer) obj.get("uploadtype");
		if (uploadtype == 1) {
			String jpql = onTabKey == 1
					? "SELECT f FROM LSsheetfolderfiles f WHERE (f.directorycode=:directorycode) And (f.filename LIKE LOWER(:png) OR f.filename LIKE :jpg OR f.filename LIKE :jpeg)"
					: "SELECT f FROM LSprotocolfolderfiles f WHERE (f.directorycode=:directorycode) AND (f.filename LIKE LOWER(:png) OR f.filename LIKE :jpg OR f.filename LIKE :jpeg)";
			TypedQuery<LSsheetfolderfiles> query1;
			TypedQuery<LSprotocolfolderfiles> query2;
			if (onTabKey == 1) {
				query1 = entityManager.createQuery(jpql, LSsheetfolderfiles.class);
				query1.setParameter("png", "%.png%");
				query1.setParameter("jpg", "%.jpg%");
				query1.setParameter("jpeg", "%.jpeg%");
				query1.setParameter("directorycode", directorycode);
				rtnobj.put("LSsheetfolderfiles", query1.getResultList());
			} else {
				query2 = entityManager.createQuery(jpql, LSprotocolfolderfiles.class);
				query2.setParameter("png", "%.png%");
				query2.setParameter("jpg", "%.jpg%");
				query2.setParameter("jpeg", "%.jpeg%");
				query2.setParameter("directorycode", directorycode);
				rtnobj.put("LSprotocolfolderfiles", query2.getResultList());
			}
		} else {
			String filefor = (String) obj.get("filefor");
			if (onTabKey == 1) {
				List<LSsheetfolderfiles> lstfiles = new ArrayList<LSsheetfolderfiles>();
				lstfiles = lssheetfolderfilesRepository.findByDirectorycodeAndFilefor(directorycode, filefor);
				rtnobj.put("LSsheetfolderfiles", lstfiles);
			} else {
				List<LSprotocolfolderfiles> lstfiles = new ArrayList<LSprotocolfolderfiles>();
				lstfiles = lsprotocolfolderfilesRepository.findByDirectorycodeAndFilefor(directorycode, filefor);
				rtnobj.put("LSprotocolfolderfiles", lstfiles);
			}
		}
		return rtnobj;
	}

	public List<Logilaborders> Getordersonmaterial(LSlogilablimsorderdetail objorder) {
		List<Logilaborders> lstorder = new ArrayList<Logilaborders>();

		Date fromdate = objorder.getFromdate();
		Date todate = objorder.getTodate();
		Integer filetype = objorder.getFiletype();
		List<LSprojectmaster> lstproject = objorder.getLstproject();
		if (filetype == 0) {
			return new ArrayList<Logilaborders>();
		} else if (filetype == -1 && objorder.getOrderflag() == null) {
//			lstorder = lslogilablimsorderdetailRepository
//					.findByElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenOrderByBatchcodeDesc(
//							objorder.getElnmaterial(), objorder.getLstestmasterlocal(), fromdate, todate);
			lstorder = logilablimsorderdetailsRepository
					.findByLsprojectmasterInAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenOrLsprojectmasterIsNullAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndViewoptionOrLsprojectmasterIsNullAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndViewoptionAndLsuserMasterOrLsprojectmasterIsNullAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndViewoptionAndLsuserMasterInOrderByBatchcodeDesc(
							lstproject, objorder.getElnmaterial(), objorder.getLstestmasterlocal(), fromdate, todate,
							objorder.getElnmaterial(), objorder.getLstestmasterlocal(), fromdate, todate, 1,
							objorder.getElnmaterial(), objorder.getLstestmasterlocal(), fromdate, todate, 2,
							objorder.getLsuserMaster(), objorder.getElnmaterial(), objorder.getLstestmasterlocal(),
							fromdate, todate, 3, objorder.getLstuserMaster());
		} else if (filetype != -1 && objorder.getOrderflag() != null) {
			if (objorder.getApprovelstatus() != null) {
//				lstorder = lslogilablimsorderdetailRepository
//						.findByElnmaterialAndLstestmasterlocalAndFiletypeAndOrderflagAndApprovelstatusAndCreatedtimestampBetweenOrderByBatchcodeDesc(
//								objorder.getElnmaterial(), objorder.getLstestmasterlocal(), filetype,
//								objorder.getOrderflag(), objorder.getApprovelstatus(), fromdate, todate);
				lstorder = logilablimsorderdetailsRepository
						.findByLsprojectmasterInAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndFiletypeAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndViewoptionAndFiletypeAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndViewoptionAndLsuserMasterAndFiletypeAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndViewoptionAndLsuserMasterInAndFiletypeAndOrderflagAndApprovelstatusOrderByBatchcodeDesc(
								lstproject, objorder.getElnmaterial(), objorder.getLstestmasterlocal(), fromdate,
								todate, filetype, objorder.getOrderflag(), objorder.getApprovelstatus(),
								objorder.getElnmaterial(), objorder.getLstestmasterlocal(), fromdate, todate, 1,
								filetype, objorder.getOrderflag(), objorder.getApprovelstatus(),
								objorder.getElnmaterial(), objorder.getLstestmasterlocal(), fromdate, todate, 2,
								objorder.getLsuserMaster(), filetype, objorder.getOrderflag(),
								objorder.getApprovelstatus(), objorder.getElnmaterial(),
								objorder.getLstestmasterlocal(), fromdate, todate, 3, objorder.getLstuserMaster(),
								filetype, objorder.getOrderflag(), objorder.getApprovelstatus());
			} else {
//				lstorder = lslogilablimsorderdetailRepository
//						.findByElnmaterialAndLstestmasterlocalAndFiletypeAndOrderflagAndCreatedtimestampBetweenOrderByBatchcodeDesc(
//								objorder.getElnmaterial(), objorder.getLstestmasterlocal(), filetype,
//								objorder.getOrderflag(), fromdate, todate);

				lstorder = logilablimsorderdetailsRepository
						.findByLsprojectmasterInAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndFiletypeAndOrderflagOrLsprojectmasterIsNullAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndViewoptionAndFiletypeAndOrderflagOrLsprojectmasterIsNullAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndViewoptionAndLsuserMasterAndFiletypeAndOrderflagOrLsprojectmasterIsNullAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndViewoptionAndLsuserMasterInAndFiletypeAndOrderflagOrderByBatchcodeDesc(
								lstproject, objorder.getElnmaterial(), objorder.getLstestmasterlocal(), fromdate,
								todate, filetype, objorder.getOrderflag(), objorder.getElnmaterial(),
								objorder.getLstestmasterlocal(), fromdate, todate, 1, filetype, objorder.getOrderflag(),
								objorder.getElnmaterial(), objorder.getLstestmasterlocal(), fromdate, todate, 2,
								objorder.getLsuserMaster(), filetype, objorder.getOrderflag(),
								objorder.getElnmaterial(), objorder.getLstestmasterlocal(), fromdate, todate, 3,
								objorder.getLstuserMaster(), filetype, objorder.getOrderflag());
			}

		} else if (filetype == -1 && objorder.getOrderflag() != null) {
			if (objorder.getApprovelstatus() != null) {

//				lstorder = lslogilablimsorderdetailRepository
//						.findByElnmaterialAndLstestmasterlocalAndOrderflagAndApprovelstatusAndCreatedtimestampBetweenOrderByBatchcodeDesc(
//								objorder.getElnmaterial(), objorder.getLstestmasterlocal(), objorder.getOrderflag(),
//								objorder.getApprovelstatus(), fromdate, todate);

				lstorder = logilablimsorderdetailsRepository
						.findByLsprojectmasterInAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndViewoptionAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndViewoptionAndLsuserMasterAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndViewoptionAndLsuserMasterInAndOrderflagAndApprovelstatusOrderByBatchcodeDesc(
								lstproject, objorder.getElnmaterial(), objorder.getLstestmasterlocal(), fromdate,
								todate, objorder.getOrderflag(), objorder.getApprovelstatus(),
								objorder.getElnmaterial(), objorder.getLstestmasterlocal(), fromdate, todate, 1,
								objorder.getOrderflag(), objorder.getApprovelstatus(), objorder.getElnmaterial(),
								objorder.getLstestmasterlocal(), fromdate, todate, 2, objorder.getLsuserMaster(),
								objorder.getOrderflag(), objorder.getApprovelstatus(), objorder.getElnmaterial(),
								objorder.getLstestmasterlocal(), fromdate, todate, 3, objorder.getLstuserMaster(),
								objorder.getOrderflag(), objorder.getApprovelstatus());
			} else {
//				lstorder = lslogilablimsorderdetailRepository
//						.findByElnmaterialAndLstestmasterlocalAndOrderflagAndCreatedtimestampBetweenOrderByBatchcodeDesc(
//								objorder.getElnmaterial(), objorder.getLstestmasterlocal(), objorder.getOrderflag(),
//								fromdate, todate);
				lstorder = logilablimsorderdetailsRepository
						.findByLsprojectmasterInAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndOrderflagOrLsprojectmasterIsNullAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndViewoptionAndOrderflagOrLsprojectmasterIsNullAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndViewoptionAndLsuserMasterAndOrderflagOrLsprojectmasterIsNullAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndViewoptionAndLsuserMasterInAndOrderflagOrderByBatchcodeDesc(
								lstproject, objorder.getElnmaterial(), objorder.getLstestmasterlocal(), fromdate,
								todate, objorder.getOrderflag(), objorder.getElnmaterial(),
								objorder.getLstestmasterlocal(), fromdate, todate, 1, objorder.getOrderflag(),
								objorder.getElnmaterial(), objorder.getLstestmasterlocal(), fromdate, todate, 2,
								objorder.getLsuserMaster(), objorder.getOrderflag(), objorder.getElnmaterial(),
								objorder.getLstestmasterlocal(), fromdate, todate, 3, objorder.getLstuserMaster(),
								objorder.getOrderflag());
			}
		} else if (filetype != -1 && objorder.getOrderflag() == null) {
//			lstorder = lslogilablimsorderdetailRepository
//					.findByElnmaterialAndLstestmasterlocalAndFiletypeAndCreatedtimestampBetweenOrderByBatchcodeDesc(
//							objorder.getElnmaterial(), objorder.getLstestmasterlocal(), filetype, fromdate, todate);

			lstorder = logilablimsorderdetailsRepository
					.findByLsprojectmasterInAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndFiletypeOrLsprojectmasterIsNullAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndViewoptionAndFiletypeOrLsprojectmasterIsNullAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndViewoptionAndLsuserMasterAndFiletypeOrLsprojectmasterIsNullAndElnmaterialAndLstestmasterlocalAndCreatedtimestampBetweenAndViewoptionAndLsuserMasterInAndFiletypeOrderByBatchcodeDesc(
							lstproject, objorder.getElnmaterial(), objorder.getLstestmasterlocal(), fromdate, todate,
							filetype, objorder.getElnmaterial(), objorder.getLstestmasterlocal(), fromdate, todate, 1,
							filetype, objorder.getElnmaterial(), objorder.getLstestmasterlocal(), fromdate, todate, 2,
							objorder.getLsuserMaster(), filetype, objorder.getElnmaterial(),
							objorder.getLstestmasterlocal(), fromdate, todate, 3, objorder.getLstuserMaster(),
							filetype);
		}
		if (objorder.getSearchCriteria() != null && objorder.getSearchCriteria().getContentsearchtype() != null
				&& objorder.getSearchCriteria().getContentsearch() != null) {

			lstorder = GetsampleordersonFilter(objorder, lstorder);

			lstorder.forEach(objorderDetail -> objorderDetail.setLstworkflow(objorder.getLstworkflow()));
			return lstorder;
		} else {

			lstorder.forEach(objorderDetail -> objorderDetail.setCanuserprocess(true));
			return lstorder;
		}

	}

	public Map<String, Object> Getprotocolordersonmaterial(LSlogilabprotocoldetail objorder) {
		List<Logilabprotocolorders> lstorder = new ArrayList<Logilabprotocolorders>();
		Map<String, Object> retuobjts = new HashMap<>();
		Date fromdate = objorder.getFromdate();
		Date todate = objorder.getTodate();
		Integer protocoltype = objorder.getProtocoltype();
		List<LSprojectmaster> lstproject = objorder.getLstproject();
		List<Integer> userlist = objorder.getLstuserMaster() != null
				? objorder.getLstuserMaster().stream().map(LSuserMaster::getUsercode).collect(Collectors.toList())
				: new ArrayList<Integer>();
		if (protocoltype == -1 && objorder.getOrderflag() == null) {

//			lstorder = LSlogilabprotocoldetailRepository.findByElnmaterialAndTestcodeAndCreatedtimestampBetween(
//					objorder.getElnmaterial(), objorder.getTestcode(), fromdate, todate);

			lstorder = LSlogilabprotocoldetailRepository
					.findByLsprojectmasterInAndElnmaterialAndTestcodeAndCreatedtimestampBetweenOrLsprojectmasterIsNullAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndViewoptionOrLsprojectmasterIsNullAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndViewoptionAndLsuserMasterOrLsprojectmasterIsNullAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndViewoptionAndCreatebyIn(
							lstproject, objorder.getElnmaterial(), objorder.getTestcode(), fromdate, todate,
							objorder.getElnmaterial(), objorder.getTestcode(), fromdate, todate, 1,
							objorder.getElnmaterial(), objorder.getTestcode(), fromdate, todate, 2,
							objorder.getLsuserMaster(), objorder.getElnmaterial(), objorder.getTestcode(), fromdate,
							todate, 3, userlist);

		} else if (protocoltype != -1 && objorder.getOrderflag() != null) {
			if (objorder.getRejected() != null) {

//				lstorder = LSlogilabprotocoldetailRepository
//						.findByElnmaterialAndTestcodeAndProtocoltypeAndOrderflagAndRejectedAndCreatedtimestampBetween(
//								objorder.getElnmaterial(), objorder.getTestcode(), protocoltype,
//								objorder.getOrderflag(), 1, fromdate, todate);

				lstorder = LSlogilabprotocoldetailRepository
						.findByLsprojectmasterInAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndProtocoltypeAndOrderflagAndRejectedOrLsprojectmasterIsNullAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndViewoptionAndProtocoltypeAndOrderflagAndRejectedOrLsprojectmasterIsNullAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndViewoptionAndLsuserMasterAndProtocoltypeAndOrderflagAndRejectedOrLsprojectmasterIsNullAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndViewoptionAndCreatebyInAndProtocoltypeAndOrderflagAndRejected(
								lstproject, objorder.getElnmaterial(), objorder.getTestcode(), fromdate, todate,
								protocoltype, objorder.getOrderflag(), 1, objorder.getElnmaterial(),
								objorder.getTestcode(), fromdate, todate, 1, protocoltype, objorder.getOrderflag(), 1,
								objorder.getElnmaterial(), objorder.getTestcode(), fromdate, todate, 2,
								objorder.getLsuserMaster(), protocoltype, objorder.getOrderflag(), 1,
								objorder.getElnmaterial(), objorder.getTestcode(), fromdate, todate, 3, userlist,
								protocoltype, objorder.getOrderflag(), 1);

			} else {

//				lstorder = LSlogilabprotocoldetailRepository
//						.findByElnmaterialAndTestcodeAndProtocoltypeAndOrderflagAndCreatedtimestampBetween(
//								objorder.getElnmaterial(), objorder.getTestcode(), protocoltype,
//								objorder.getOrderflag(), fromdate, todate);

				lstorder = LSlogilabprotocoldetailRepository
						.findByLsprojectmasterInAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndProtocoltypeAndOrderflagOrLsprojectmasterIsNullAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndViewoptionAndProtocoltypeAndOrderflagOrLsprojectmasterIsNullAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndViewoptionAndLsuserMasterAndProtocoltypeAndOrderflagOrLsprojectmasterIsNullAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndViewoptionAndCreatebyInAndProtocoltypeAndOrderflag(
								lstproject, objorder.getElnmaterial(), objorder.getTestcode(), fromdate, todate,
								protocoltype, objorder.getOrderflag(), objorder.getElnmaterial(),
								objorder.getTestcode(), fromdate, todate, 1, protocoltype, objorder.getOrderflag(),
								objorder.getElnmaterial(), objorder.getTestcode(), fromdate, todate, 2,
								objorder.getLsuserMaster(), protocoltype, objorder.getOrderflag(),
								objorder.getElnmaterial(), objorder.getTestcode(), fromdate, todate, 3, userlist,
								protocoltype, objorder.getOrderflag());

			}

		} else if (protocoltype == -1 && objorder.getOrderflag() != null) {
			if (objorder.getRejected() != null) {

//				lstorder = LSlogilabprotocoldetailRepository
//						.findByElnmaterialAndTestcodeAndOrderflagAndRejectedAndCreatedtimestampBetween(
//								objorder.getElnmaterial(), objorder.getTestcode(), objorder.getOrderflag(), 1, fromdate,
//								todate);

				lstorder = LSlogilabprotocoldetailRepository
						.findByLsprojectmasterInAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndOrderflagAndRejectedOrLsprojectmasterIsNullAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndViewoptionAndOrderflagAndRejectedOrLsprojectmasterIsNullAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndViewoptionAndLsuserMasterAndOrderflagAndRejectedOrLsprojectmasterIsNullAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndViewoptionAndCreatebyInAndOrderflagAndRejected(
								lstproject, objorder.getElnmaterial(), objorder.getTestcode(), fromdate, todate,
								objorder.getOrderflag(), 1, objorder.getElnmaterial(), objorder.getTestcode(), fromdate,
								todate, 1, objorder.getOrderflag(), 1, objorder.getElnmaterial(),
								objorder.getTestcode(), fromdate, todate, 2, objorder.getLsuserMaster(),
								objorder.getOrderflag(), 1, objorder.getElnmaterial(), objorder.getTestcode(), fromdate,
								todate, 3, userlist, objorder.getOrderflag(), 1);

			} else {
//				lstorder = LSlogilabprotocoldetailRepository
//						.findByElnmaterialAndTestcodeAndOrderflagAndCreatedtimestampBetween(objorder.getElnmaterial(),
//								objorder.getTestcode(), objorder.getOrderflag(), fromdate, todate);

				lstorder = LSlogilabprotocoldetailRepository
						.findByLsprojectmasterInAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndOrderflagOrLsprojectmasterIsNullAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndViewoptionAndOrderflagOrLsprojectmasterIsNullAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndViewoptionAndLsuserMasterAndOrderflagOrLsprojectmasterIsNullAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndViewoptionAndCreatebyInAndOrderflag(
								lstproject, objorder.getElnmaterial(), objorder.getTestcode(), fromdate, todate,
								objorder.getOrderflag(), objorder.getElnmaterial(), objorder.getTestcode(), fromdate,
								todate, 1, objorder.getOrderflag(), objorder.getElnmaterial(), objorder.getTestcode(),
								fromdate, todate, 2, objorder.getLsuserMaster(), objorder.getOrderflag(),
								objorder.getElnmaterial(), objorder.getTestcode(), fromdate, todate, 3, userlist,
								objorder.getOrderflag());

			}
		} else if (protocoltype != -1 && objorder.getOrderflag() == null) {
//			lstorder = LSlogilabprotocoldetailRepository
//					.findByElnmaterialAndTestcodeAndProtocoltypeAndCreatedtimestampBetween(objorder.getElnmaterial(),
//							objorder.getTestcode(), protocoltype, fromdate, todate);
			lstorder = LSlogilabprotocoldetailRepository
					.findByLsprojectmasterInAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndProtocoltypeOrLsprojectmasterIsNullAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndViewoptionAndProtocoltypeOrLsprojectmasterIsNullAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndViewoptionAndLsuserMasterAndProtocoltypeOrLsprojectmasterIsNullAndElnmaterialAndTestcodeAndCreatedtimestampBetweenAndViewoptionAndCreatebyInAndProtocoltype

					(lstproject, objorder.getElnmaterial(), objorder.getTestcode(), fromdate, todate, protocoltype,
							objorder.getElnmaterial(), objorder.getTestcode(), fromdate, todate, 1, protocoltype,
							objorder.getElnmaterial(), objorder.getTestcode(), fromdate, todate, 2,
							objorder.getLsuserMaster(), protocoltype, objorder.getElnmaterial(), objorder.getTestcode(),
							fromdate, todate, 3, userlist, protocoltype);

		}
		Map<String, Object> obj = Getprotocolordersondirectory(objorder.getLsprotocolorderstructure());
		if (obj.containsKey("protocolorders")) {
			@SuppressWarnings("unchecked")
			List<LSlogilabprotocoldetail> protocolOrders = (List<LSlogilabprotocoldetail>) obj.get("protocolorders");
			lstorder.addAll(protocolOrders.stream().map(lsOrderDetail -> new Logilabprotocolorders(
					lsOrderDetail.getProtocolordercode(), lsOrderDetail.getTeamcode(),
					lsOrderDetail.getProtoclordername(), lsOrderDetail.getOrderflag(), lsOrderDetail.getProtocoltype(),
					lsOrderDetail.getCreatedtimestamp(), lsOrderDetail.getCompletedtimestamp(),
					lsOrderDetail.getLsprotocolmaster(), lsOrderDetail.getlSprotocolworkflow(),
					lsOrderDetail.getSample(), lsOrderDetail.getLsprojectmaster(), lsOrderDetail.getKeyword(),
					lsOrderDetail.getDirectorycode(), lsOrderDetail.getCreateby(), lsOrderDetail.getAssignedto(),
					lsOrderDetail.getLsrepositoriesdata(), lsOrderDetail.getLsrepositories(),
					lsOrderDetail.getElnmaterial(), lsOrderDetail.getElnmaterialinventory(),
					lsOrderDetail.getApproved(), lsOrderDetail.getRejected(), lsOrderDetail.getOrdercancell(),
					lsOrderDetail.getViewoption(), lsOrderDetail.getOrderstarted(), lsOrderDetail.getOrderstartedby(),
					lsOrderDetail.getOrderstartedon(), lsOrderDetail.getLockeduser(), lsOrderDetail.getLockedusername(),
					lsOrderDetail.getVersionno(), lsOrderDetail.getElnprotocolworkflow(),
					lsOrderDetail.getLsordernotification(), lsOrderDetail.getLsautoregister(),
					lsOrderDetail.getRepeat(), lsOrderDetail.getSentforapprovel(), lsOrderDetail.getApprovelaccept(),
					lsOrderDetail.getAutoregistercount(), lsOrderDetail.getLsuserMaster(),
					lsOrderDetail.getSequenceid(), lsOrderDetail.getModifiedby(), lsOrderDetail.getModifieddate()))
					.collect(Collectors.toList()));

		}

		lstorder.forEach(
				objorderDetail -> objorderDetail.setLstelnprotocolworkflow(objorder.getLstelnprotocolworkflow()));
		List<Long> protocolordercode = new ArrayList<>();
		if (lstorder.size() > 0 && objorder.getSearchCriteriaType() != null) {
			protocolordercode = lstorder.stream().map(Logilabprotocolorders::getProtocolordercode)
					.collect(Collectors.toList());
			retuobjts.put("protocolordercodeslist", protocolordercode);
		}
		retuobjts.put("protocolorders", lstorder);

		return retuobjts;
	}

//	public List<Logilaborderssh> Getcancelledordes(LSlogilablimsorderdetail objdir) {
//		List<Logilaborderssh> lstorders = new ArrayList<Logilaborderssh>();
////		List<Logilaborders> lstorderstrcarray = new ArrayList<Logilaborders>();
//		Date fromdate = objdir.getFromdate();
//		Date todate = objdir.getTodate();
//		Integer filetype = objdir.getFiletype();
//		String orderflag = objdir.getOrderflag();
//		Integer rejected = objdir.getRejected();
//		List<LSprojectmaster> lstproject = objdir.getLstproject();
//
//		if (filetype == -1 && orderflag == null && rejected == null) {
//			lstorders = lslogilablimsorderdetailRepository
//					.findByOrdercancellAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellOrAssignedtoAndCreatedtimestampBetweenAndOrdercancell(
//							1, lstproject, fromdate, todate, 1, objdir.getLsuserMaster(), fromdate, todate, 1, 2,
//							objdir.getLsuserMaster(), fromdate, todate, 1, 3, objdir.getLsuserMaster(), fromdate,
//							todate, 1, objdir.getLsuserMaster(), objdir.getLsuserMaster(), fromdate, todate, 1,
//							objdir.getLsuserMaster(), fromdate, todate, 1);
//
//		} else if (filetype != -1 && orderflag == null && rejected == null) {
//			lstorders = lslogilablimsorderdetailRepository
//					.findByOrdercancellAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndFiletypeOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndFiletypeOrAssignedtoAndCreatedtimestampBetweenAndOrdercancellAndFiletype(
//							1, lstproject, fromdate, todate, filetype, 1, objdir.getLsuserMaster(), fromdate, todate, 1,
//							filetype, 2, objdir.getLsuserMaster(), fromdate, todate, 1, filetype, 3,
//							objdir.getLsuserMaster(), fromdate, todate, 1, filetype, objdir.getLsuserMaster(),
//							objdir.getLsuserMaster(), fromdate, todate, 1, filetype, objdir.getLsuserMaster(), fromdate,
//							todate, 1, filetype);
//		} else if (filetype != -1 && orderflag != null && rejected == null) {
//			lstorders = lslogilablimsorderdetailRepository
//					.findByOrdercancellAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndFiletypeAndOrderflagOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndOrderflagOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndOrderflagOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndOrderflagOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndFiletypeAndOrderflagOrAssignedtoAndCreatedtimestampBetweenAndOrdercancellAndFiletypeAndOrderflag(
//							1, lstproject, fromdate, todate, filetype, orderflag, 1, objdir.getLsuserMaster(), fromdate,
//							todate, 1, filetype, orderflag, 2, objdir.getLsuserMaster(), fromdate, todate, 1, filetype,
//							orderflag, 3, objdir.getLsuserMaster(), fromdate, todate, 1, filetype, orderflag,
//							objdir.getLsuserMaster(), objdir.getLsuserMaster(), fromdate, todate, 1, filetype,
//							orderflag, objdir.getLsuserMaster(), fromdate, todate, 1, filetype, orderflag);
//		} else if (filetype != -1 && orderflag == null && rejected != null) {
//			lstorders = lslogilablimsorderdetailRepository
//					.findByOrdercancellAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndFiletypeAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndApprovelstatusOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndFiletypeAndApprovelstatusOrAssignedtoAndCreatedtimestampBetweenAndOrdercancellAndFiletypeAndApprovelstatus(
//							1, lstproject, fromdate, todate, filetype, 3, 1, objdir.getLsuserMaster(), fromdate, todate,
//							1, filetype, 3, 2, objdir.getLsuserMaster(), fromdate, todate, 1, filetype, 3, 3,
//							objdir.getLsuserMaster(), fromdate, todate, 1, filetype, 3, objdir.getLsuserMaster(),
//							objdir.getLsuserMaster(), fromdate, todate, 1, filetype, 3, objdir.getLsuserMaster(),
//							fromdate, todate, 1, filetype, 3);
//		} else if (filetype == -1 && orderflag != null && rejected == null) {
//			lstorders = lslogilablimsorderdetailRepository
//					.findByOrdercancellAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndOrderflagOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndOrderflagOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndOrderflagOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndOrderflagOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndOrderflagOrAssignedtoAndCreatedtimestampBetweenAndOrdercancellAndOrderflag(
//							1, lstproject, fromdate, todate, orderflag, 1, objdir.getLsuserMaster(), fromdate, todate,
//							1, orderflag, 2, objdir.getLsuserMaster(), fromdate, todate, 1, orderflag, 3,
//							objdir.getLsuserMaster(), fromdate, todate, 1, orderflag, objdir.getLsuserMaster(),
//							objdir.getLsuserMaster(), fromdate, todate, 1, orderflag, objdir.getLsuserMaster(),
//							fromdate, todate, 1, orderflag);
//		} else if (filetype == -1 && orderflag != null && rejected != null) {
//			lstorders = lslogilablimsorderdetailRepository
//					.findByOrdercancellAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndOrderflagAndApprovelstatusOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndOrderflagAndApprovelstatusOrAssignedtoAndCreatedtimestampBetweenAndOrdercancellAndOrderflagAndApprovelstatus(
//							1, lstproject, fromdate, todate, orderflag, 3, 1, objdir.getLsuserMaster(), fromdate,
//							todate, 1, orderflag, 3, 2, objdir.getLsuserMaster(), fromdate, todate, 1, orderflag, 3, 3,
//							objdir.getLsuserMaster(), fromdate, todate, 1, orderflag, 3, objdir.getLsuserMaster(),
//							objdir.getLsuserMaster(), fromdate, todate, 1, orderflag, 3, objdir.getLsuserMaster(),
//							fromdate, todate, 1, orderflag, 3);
//		} else if (filetype == -1 && orderflag == null && rejected != null) {
//			lstorders = lslogilablimsorderdetailRepository
//					.findByOrdercancellAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndApprovelstatusOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndApprovelstatusOrAssignedtoAndCreatedtimestampBetweenAndOrdercancellAndApprovelstatus(
//							1, lstproject, fromdate, todate, 3, 1, objdir.getLsuserMaster(), fromdate, todate, 1, 3, 2,
//							objdir.getLsuserMaster(), fromdate, todate, 1, 3, 3, objdir.getLsuserMaster(), fromdate,
//							todate, 1, 3, objdir.getLsuserMaster(), objdir.getLsuserMaster(), fromdate, todate, 1, 3,
//							objdir.getLsuserMaster(), fromdate, todate, 1, 3);
//		} else {
//			lstorders = lslogilablimsorderdetailRepository
//					.findByOrdercancellAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndFiletypeAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndOrderflagAndApprovelstatusOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndFiletypeAndOrderflagAndApprovelstatusOrAssignedtoAndCreatedtimestampBetweenAndOrdercancellAndFiletypeAndOrderflagAndApprovelstatus(
//							1, lstproject, fromdate, todate, filetype, orderflag, 3, 1, objdir.getLsuserMaster(),
//							fromdate, todate, 1, filetype, orderflag, 3, 2, objdir.getLsuserMaster(), fromdate, todate,
//							1, filetype, orderflag, 3, 3, objdir.getLsuserMaster(), fromdate, todate, 1, filetype,
//							orderflag, 3, objdir.getLsuserMaster(), objdir.getLsuserMaster(), fromdate, todate, 1,
//							filetype, orderflag, 3, objdir.getLsuserMaster(), fromdate, todate, 1, filetype, orderflag,
//							3);
//		}
//		if (objdir.getSearchCriteria() != null && objdir.getSearchCriteria().getContentsearchtype() != null
//				&& objdir.getSearchCriteria().getContentsearch() != null) {
//
//			lstorders = GetmyordersonFilter(objdir, lstorders, orderflag);
//
//			lstorders.forEach(objorderDetail -> objorderDetail.setLw(objdir.getLstworkflow()));
//			return lstorders;
//		} else {
//
//			lstorders.forEach(objorderDetail -> objorderDetail.setLw(objdir.getLstworkflow()));
//			return lstorders;
//		}
//	}

	public List<Logilaborderssh> Getcancelledordes(LSlogilablimsorderdetail objdir) {
		List<Logilaborderssh> lstorders = new ArrayList<Logilaborderssh>();
//		List<Logilaborders> lstorderstrcarray = new ArrayList<Logilaborders>();
		Date fromdate = objdir.getFromdate();
		Date todate = objdir.getTodate();
		Integer filetype = objdir.getFiletype();
		String orderflag = objdir.getOrderflag();
		Integer rejected = objdir.getRejected();
		Integer site = objdir.getSitecode();
		List<LSprojectmaster> lstproject = objdir.getLstproject();
		LSprojectmaster proselected = objdir.getLsprojectmaster();
		Integer testselected = objdir.getTestcode();

		List<LSuserteammapping> lstteammap = lsuserteammappingRepository.findBylsuserMaster(objdir.getLsuserMaster());
		List<LSusersteam> lstteam = lsusersteamRepository.findByLsuserteammappingInAndLssitemaster(lstteammap,
				objdir.getLsuserMaster().getLssitemaster());
		List<LSSelectedTeam> selectedteamorders = LSSelectedTeamRepository
				.findByUserteamInAndCreatedtimestampBetween(lstteam, fromdate, todate);
		List<Long> selectedteambatchCodeList = (selectedteamorders != null && !selectedteamorders.isEmpty())
				? selectedteamorders.stream().map(LSSelectedTeam::getBatchcode).filter(Objects::nonNull).distinct()
						.collect(Collectors.toList())
				: Collections.singletonList(-1L);

		if (filetype == -1 && orderflag == null && rejected == null && proselected == null && testselected == null) {
			lstorders = LogilablimsorderdetailsRepository
					.findByOrdercancellAndSitecodeAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndTeamselectedOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullOrLsuserMasterAndSitecodeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellOrAssignedtoAndSitecodeAndCreatedtimestampBetweenAndOrdercancell(
							1, site, lstproject, fromdate, todate, 1, site, objdir.getLsuserMaster(), fromdate, todate,
							1, 2, site, objdir.getLsuserMaster(), fromdate, todate, 1, 3, site,
							objdir.getLstuserMaster(), fromdate, todate, 1, false, 3, site, true,
							selectedteambatchCodeList, fromdate, todate, 1, objdir.getLsuserMaster(), site,
							objdir.getLsuserMaster(), fromdate, todate, 1, objdir.getLsuserMaster(), site, fromdate,
							todate, 1);
		} else if (filetype == -1 && orderflag == null && rejected == null && proselected != null
				&& testselected == null) {
			lstorders = LogilablimsorderdetailsRepository
					.findByOrdercancellAndSitecodeAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndLsprojectmasterOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndLsprojectmasterOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndLsprojectmasterOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndTeamselectedAndLsprojectmasterOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndLsprojectmasterOrLsuserMasterAndSitecodeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndLsprojectmasterOrAssignedtoAndSitecodeAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmaster(
							1, site, lstproject, fromdate, todate, proselected, 1, site, objdir.getLsuserMaster(),
							fromdate, todate, 1, proselected, 2, site, objdir.getLsuserMaster(), fromdate, todate, 1,
							proselected, 3, site, objdir.getLstuserMaster(), fromdate, todate, 1, false, proselected, 3,
							site, true, selectedteambatchCodeList, fromdate, todate, 1, proselected,
							objdir.getLsuserMaster(), site, objdir.getLsuserMaster(), fromdate, todate, 1, proselected,
							objdir.getLsuserMaster(), site, fromdate, todate, 1, proselected);
		} else if (filetype == -1 && orderflag == null && rejected == null && proselected == null
				&& testselected != null) {
			lstorders = LogilablimsorderdetailsRepository
					.findByOrdercancellAndSitecodeAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndTeamselectedAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndTestcodeOrLsuserMasterAndSitecodeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndTestcodeOrAssignedtoAndSitecodeAndCreatedtimestampBetweenAndOrdercancellAndTestcode(
							1, site, lstproject, fromdate, todate, testselected, 1, site, objdir.getLsuserMaster(),
							fromdate, todate, 1, testselected, 2, site, objdir.getLsuserMaster(), fromdate, todate, 1,
							testselected, 3, site, objdir.getLstuserMaster(), fromdate, todate, 1, false, testselected,
							3, site, true, selectedteambatchCodeList, fromdate, todate, 1, testselected,
							objdir.getLsuserMaster(), site, objdir.getLsuserMaster(), fromdate, todate, 1, testselected,
							objdir.getLsuserMaster(), site, fromdate, todate, 1, testselected);
		} else if (filetype == -1 && orderflag == null && rejected == null && proselected != null
				&& testselected != null) {
			lstorders = LogilablimsorderdetailsRepository
					.findByOrdercancellAndSitecodeAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndTestcodeAndLsprojectmasterOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndTestcodeAndLsprojectmasterOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndTestcodeAndLsprojectmasterOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndTeamselectedAndTestcodeAndLsprojectmasterOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndTestcodeAndLsprojectmasterOrLsuserMasterAndSitecodeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndTestcodeAndLsprojectmasterOrAssignedtoAndSitecodeAndCreatedtimestampBetweenAndOrdercancellAndTestcodeAndLsprojectmaster(
							1, site, lstproject, fromdate, todate, testselected, proselected, 1, site,
							objdir.getLsuserMaster(), fromdate, todate, 1, testselected, proselected, 2, site,
							objdir.getLsuserMaster(), fromdate, todate, 1, testselected, proselected, 3, site,
							objdir.getLstuserMaster(), fromdate, todate, 1, false, testselected, proselected, 3, site,
							true, selectedteambatchCodeList, fromdate, todate, 1, testselected, proselected,
							objdir.getLsuserMaster(), site, objdir.getLsuserMaster(), fromdate, todate, 1, testselected,
							proselected, objdir.getLsuserMaster(), site, fromdate, todate, 1, testselected,
							proselected);
		}

		else if (filetype != -1 && orderflag == null && rejected == null && proselected == null
				&& testselected == null) {
			lstorders = LogilablimsorderdetailsRepository
					.findByOrdercancellAndSitecodeAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndFiletypeOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndTeamselectedOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeOrLsuserMasterAndSitecodeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndFiletypeOrAssignedtoAndSitecodeAndCreatedtimestampBetweenAndOrdercancellAndFiletype(
							1, site, lstproject, fromdate, todate, filetype, 1, site, objdir.getLsuserMaster(),
							fromdate, todate, 1, filetype, 2, site, objdir.getLsuserMaster(), fromdate, todate, 1,
							filetype, 3, site, objdir.getLstuserMaster(), fromdate, todate, 1, filetype, false, 3, site,
							true, selectedteambatchCodeList, fromdate, todate, 1, filetype, objdir.getLsuserMaster(),
							site, objdir.getLsuserMaster(), fromdate, todate, 1, filetype, objdir.getLsuserMaster(),
							site, fromdate, todate, 1, filetype);

		}

		else if (filetype != -1 && orderflag == null && rejected == null && proselected != null
				&& testselected == null) {
			lstorders = LogilablimsorderdetailsRepository
					.findByOrdercancellAndSitecodeAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndFiletypeAndLsprojectmasterOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndLsprojectmasterOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndLsprojectmasterOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndTeamselectedAndLsprojectmasterOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndLsprojectmasterOrLsuserMasterAndSitecodeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndFiletypeAndLsprojectmasterOrAssignedtoAndSitecodeAndCreatedtimestampBetweenAndOrdercancellAndFiletypeAndLsprojectmaster(
							1, site, lstproject, fromdate, todate, filetype, proselected, 1, site,
							objdir.getLsuserMaster(), fromdate, todate, 1, filetype, proselected, 2, site,
							objdir.getLsuserMaster(), fromdate, todate, 1, filetype, proselected, 3, site,
							objdir.getLstuserMaster(), fromdate, todate, 1, filetype, false, proselected, 3, site, true,
							selectedteambatchCodeList, fromdate, todate, 1, filetype, proselected,
							objdir.getLsuserMaster(), site, objdir.getLsuserMaster(), fromdate, todate, 1, filetype,
							proselected, objdir.getLsuserMaster(), site, fromdate, todate, 1, filetype, proselected);
		}

		else if (filetype != -1 && orderflag == null && rejected == null && proselected == null
				&& testselected != null) {
			lstorders = LogilablimsorderdetailsRepository
					.findByOrdercancellAndSitecodeAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndFiletypeAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndTeamselectedAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndTestcodeOrLsuserMasterAndSitecodeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndFiletypeAndTestcodeOrAssignedtoAndSitecodeAndCreatedtimestampBetweenAndOrdercancellAndFiletypeAndTestcode(
							1, site, lstproject, fromdate, todate, filetype, testselected, 1, site,
							objdir.getLsuserMaster(), fromdate, todate, 1, filetype, testselected, 2, site,
							objdir.getLsuserMaster(), fromdate, todate, 1, filetype, testselected, 3, site,
							objdir.getLstuserMaster(), fromdate, todate, 1, filetype, false, testselected, 3, site,
							true, selectedteambatchCodeList, fromdate, todate, 1, filetype, testselected,
							objdir.getLsuserMaster(), site, objdir.getLsuserMaster(), fromdate, todate, 1, filetype,
							testselected, objdir.getLsuserMaster(), site, fromdate, todate, 1, filetype, testselected);
		}

		else if (filetype != -1 && orderflag == null && rejected == null && proselected != null
				&& testselected != null) {
			lstorders = LogilablimsorderdetailsRepository
					.findByOrdercancellAndSitecodeAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndFiletypeAndTestcodeAndLsprojectmasterOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndTestcodeAndLsprojectmasterOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndTestcodeAndLsprojectmasterOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndTeamselectedAndTestcodeAndLsprojectmasterOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndTestcodeAndLsprojectmasterOrLsuserMasterAndSitecodeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndFiletypeAndTestcodeAndLsprojectmasterOrAssignedtoAndSitecodeAndCreatedtimestampBetweenAndOrdercancellAndFiletypeAndTestcodeAndLsprojectmaster(
							1, site, lstproject, fromdate, todate, filetype, testselected, proselected, 1, site,
							objdir.getLsuserMaster(), fromdate, todate, 1, filetype, testselected, proselected, 2, site,
							objdir.getLsuserMaster(), fromdate, todate, 1, filetype, testselected, proselected, 3, site,
							objdir.getLstuserMaster(), fromdate, todate, 1, filetype, false, testselected, proselected,
							3, site, true, selectedteambatchCodeList, fromdate, todate, 1, filetype, testselected,
							proselected, objdir.getLsuserMaster(), site, objdir.getLsuserMaster(), fromdate, todate, 1,
							filetype, testselected, proselected, objdir.getLsuserMaster(), site, fromdate, todate, 1,
							filetype, testselected, proselected);
		}

		else if (filetype != -1 && orderflag != null && rejected == null) {
			lstorders = LogilablimsorderdetailsRepository
					.findByOrdercancellAndSitecodeAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndFiletypeAndOrderflagOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndOrderflagOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndOrderflagOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndOrderflagAndTeamselectedOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndOrderflagOrLsuserMasterAndSitecodeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndFiletypeAndOrderflagOrAssignedtoAndSitecodeAndCreatedtimestampBetweenAndOrdercancellAndFiletypeAndOrderflag(
							1, site, lstproject, fromdate, todate, filetype, orderflag, 1, site,
							objdir.getLsuserMaster(), fromdate, todate, 1, filetype, orderflag, 2, site,
							objdir.getLsuserMaster(), fromdate, todate, 1, filetype, orderflag, 3, site,
							objdir.getLstuserMaster(), fromdate, todate, 1, filetype, orderflag, false, 3, site, true,
							selectedteambatchCodeList, fromdate, todate, 1, filetype, orderflag,
							objdir.getLsuserMaster(), site, objdir.getLsuserMaster(), fromdate, todate, 1, filetype,
							orderflag, objdir.getLsuserMaster(), site, fromdate, todate, 1, filetype, orderflag);
		} else if (filetype != -1 && orderflag == null && rejected != null) {
			lstorders = LogilablimsorderdetailsRepository
					.findByOrdercancellAndSitecodeAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndFiletypeAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndApprovelstatusAndTeamselectedOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndApprovelstatusOrLsuserMasterAndSitecodeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndFiletypeAndApprovelstatusOrAssignedtoAndSitecodeAndCreatedtimestampBetweenAndOrdercancellAndFiletypeAndApprovelstatus(
							1, site, lstproject, fromdate, todate, filetype, 3, 1, site, objdir.getLsuserMaster(),
							fromdate, todate, 1, filetype, 3, 2, site, objdir.getLsuserMaster(), fromdate, todate, 1,
							filetype, 3, 3, site, objdir.getLstuserMaster(), fromdate, todate, 1, filetype, 3, false, 3,
							site, true, selectedteambatchCodeList, fromdate, todate, 1, filetype, 3,
							objdir.getLsuserMaster(), site, objdir.getLsuserMaster(), fromdate, todate, 1, filetype, 3,
							objdir.getLsuserMaster(), site, fromdate, todate, 1, filetype, 3);

		} else if (filetype == -1 && orderflag != null && rejected == null) {
			lstorders = LogilablimsorderdetailsRepository
					.findByOrdercancellAndSitecodeAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndOrderflagOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndOrderflagOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndOrderflagOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndOrderflagAndTeamselectedOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndOrderflagOrLsuserMasterAndSitecodeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndOrderflagOrAssignedtoAndSitecodeAndCreatedtimestampBetweenAndOrdercancellAndOrderflag(
							1, site, lstproject, fromdate, todate, orderflag, 1, site, objdir.getLsuserMaster(),
							fromdate, todate, 1, orderflag, 2, site, objdir.getLsuserMaster(), fromdate, todate, 1,
							orderflag, 3, site, objdir.getLstuserMaster(), fromdate, todate, 1, orderflag, false, 3,
							site, true, selectedteambatchCodeList, fromdate, todate, 1, orderflag,
							objdir.getLsuserMaster(), site, objdir.getLsuserMaster(), fromdate, todate, 1, orderflag,
							objdir.getLsuserMaster(), site, fromdate, todate, 1, orderflag);
		} else if (filetype == -1 && orderflag != null && rejected != null) {
			lstorders = LogilablimsorderdetailsRepository
					.findByOrdercancellAndSitecodeAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndOrderflagAndApprovelstatusAndTeamselectedOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndOrderflagAndApprovelstatusOrLsuserMasterAndSitecodeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndOrderflagAndApprovelstatusOrAssignedtoAndSitecodeAndCreatedtimestampBetweenAndOrdercancellAndOrderflagAndApprovelstatus(
							1, site, lstproject, fromdate, todate, orderflag, 3, 1, site, objdir.getLsuserMaster(),
							fromdate, todate, 1, orderflag, 3, 2, site, objdir.getLsuserMaster(), fromdate, todate, 1,
							orderflag, 3, 3, site, objdir.getLstuserMaster(), fromdate, todate, 1, orderflag, 3, false,
							3, site, true, selectedteambatchCodeList, fromdate, todate, 1, orderflag, 3,
							objdir.getLsuserMaster(), site, objdir.getLsuserMaster(), fromdate, todate, 1, orderflag, 3,
							objdir.getLsuserMaster(), site, fromdate, todate, 1, orderflag, 3);
		} else if (filetype == -1 && orderflag == null && rejected != null) {
			lstorders = LogilablimsorderdetailsRepository
					.findByOrdercancellAndSitecodeAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndApprovelstatusAndTeamselectedOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndApprovelstatusOrLsuserMasterAndSitecodeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndApprovelstatusOrAssignedtoAndSitecodeAndCreatedtimestampBetweenAndOrdercancellAndApprovelstatus(
							1, site, lstproject, fromdate, todate, 3, 1, site, objdir.getLsuserMaster(), fromdate,
							todate, 1, 3, 2, site, objdir.getLsuserMaster(), fromdate, todate, 1, 3, 3, site,
							objdir.getLstuserMaster(), fromdate, todate, 1, 3, false, 3, site, true,
							selectedteambatchCodeList, fromdate, todate, 1, 3, objdir.getLsuserMaster(), site,
							objdir.getLsuserMaster(), fromdate, todate, 1, 3, objdir.getLsuserMaster(), site, fromdate,
							todate, 1, 3);
		} else {
			lstorders = LogilablimsorderdetailsRepository
					.findByOrdercancellAndSitecodeAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndFiletypeAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterAndCreatedtimestampBetweenAndOrdercancellAndAssignedtoIsNullAndFiletypeAndOrderflagAndApprovelstatusOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndLsuserMasterInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndOrderflagAndApprovelstatusAndTeamselectedOrLsprojectmasterIsNullAndViewoptionAndSitecodeAndTeamselectedAndBatchcodeInAndCreatedtimestampBetweenAndOrdercancellAndLsprojectmasterIsNullAndAssignedtoIsNullAndFiletypeAndOrderflagAndApprovelstatusOrLsuserMasterAndSitecodeAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndOrdercancellAndFiletypeAndOrderflagAndApprovelstatusOrAssignedtoAndSitecodeAndCreatedtimestampBetweenAndOrdercancellAndFiletypeAndOrderflagAndApprovelstatus(
							1, site, lstproject, fromdate, todate, filetype, orderflag, 3, 1, site,
							objdir.getLsuserMaster(), fromdate, todate, 1, filetype, orderflag, 3, 2, site,
							objdir.getLsuserMaster(), fromdate, todate, 1, filetype, orderflag, 3, 3, site,
							objdir.getLstuserMaster(), fromdate, todate, 1, filetype, orderflag, 3, false, 3, site,
							true, selectedteambatchCodeList, fromdate, todate, 1, filetype, orderflag, 3,
							objdir.getLsuserMaster(), site, objdir.getLsuserMaster(), fromdate, todate, 1, filetype,
							orderflag, 3, objdir.getLsuserMaster(), site, fromdate, todate, 1, filetype, orderflag, 3);
		}

		if (objdir.getSearchCriteria() != null && objdir.getSearchCriteria().getContentsearchtype() != null
				&& objdir.getSearchCriteria().getContentsearch() != null) {

			lstorders = GetmyordersonFilter(objdir, lstorders, orderflag);

			lstorders.forEach(objorderDetail -> objorderDetail.setLw(objdir.getLstworkflow()));
			return lstorders;
		} else {

			lstorders.forEach(objorderDetail -> objorderDetail.setLw(objdir.getLstworkflow()));
			return lstorders;
		}
	}

	public LSlogilablimsorderdetail sendapprovel(LSlogilablimsorderdetail objdir) {
		List<LSlogilablimsorderdetail> logiobj = new ArrayList<LSlogilablimsorderdetail>();
		logiobj = logilablimsorderdetailsRepository.getOrderDetails(objdir.getBatchcode());
		logiobj.get(0).setSentforapprovel(objdir.getSentforapprovel());
		logiobj.get(0).setApprovelaccept(objdir.getApprovelaccept());
		logiobj.get(0).setCanuserprocess(false);
		logilablimsorderdetailsRepository.save(logiobj.get(0));

		String screen = "Sheet Order";
		String Notification = "SENDFORAPPROVEL";

		LSuserMaster notifyfrom = logiobj.get(0).getLsuserMaster();
		LSuserMaster notifyto = logiobj.get(0).getAssignedto();
		try {
			sendnotification(logiobj.get(0), Notification, screen, notifyto, notifyfrom);
		} catch (ParseException e) {

			e.printStackTrace();
		}

		return logiobj.get(0);
	}

	public LSlogilablimsorderdetail acceptapprovel(LSlogilablimsorderdetail objdir) throws ParseException {
		LSlogilablimsorderdetail logiobj = new LSlogilablimsorderdetail();
		logiobj = lslogilablimsorderdetailRepository.findByBatchcodeOrderByBatchcodeDesc(objdir.getBatchcode());
		String screen = "Sheet Order";

		if (objdir.getApprovelaccept().equals("3")) {
			logiobj.setApprovelaccept(objdir.getApprovelaccept());
			logiobj.setApprovelstatus(objdir.getApprovelstatus());
			logiobj.setOrderflag("R");
			logiobj.setDirectorycode(null);
			logiobj.setCompletedtimestamp(commonfunction.getCurrentUtcTime());

			String Notification = "REJECTALERT";

			LSuserMaster notifyfrom = logiobj.getAssignedto();
			LSuserMaster notifyto = logiobj.getLsuserMaster();

			sendnotification(logiobj, Notification, screen, notifyto, notifyfrom);

		} else if (objdir.getApprovelaccept().equals("2")) {
			logiobj.setApprovelaccept(objdir.getApprovelaccept());

			logiobj.setSentforapprovel(objdir.getSentforapprovel());

			String Notification = "RETURNALERT";

			LSuserMaster notifyfrom = logiobj.getAssignedto();
			LSuserMaster notifyto = logiobj.getLsuserMaster();

			sendnotification(logiobj, Notification, screen, notifyto, notifyfrom);

		} else {

			logiobj.setApprovelaccept(objdir.getApprovelaccept());

			String Notification = "APPROVEALERT";

			LSuserMaster notifyfrom = logiobj.getAssignedto();
			LSuserMaster notifyto = logiobj.getLsuserMaster();
			sendnotification(logiobj, Notification, screen, notifyto, notifyfrom);

		}
		lslogilablimsorderdetailRepository.save(logiobj);

		return logiobj;
	}

	public void sendnotification(LSlogilablimsorderdetail objdir, String Notification, String screen,
			LSuserMaster notifyfrom, LSuserMaster notifyto) throws ParseException {
		String batchid = "";

		batchid = objdir.getSequenceid() != null ? objdir.getSequenceid() : objdir.getBatchid();

		LSnotification LSnotification = new LSnotification();

		String Details = "{\"ordercode\" :\"" + objdir.getBatchcode() + "\",\"order\" :\"" + batchid + "\",\"user\":\""
				+ objdir.getLsuserMaster().getUsername() + "\",\"notifyto\":\"" + objdir.getAssignedto().getUsername()
				+ "\"}";

		LSnotification.setIsnewnotification(1);
		LSnotification.setNotification(Notification);
		LSnotification.setNotificationdate(commonfunction.getCurrentUtcTime());
		LSnotification.setNotificationdetils(Details);
		LSnotification.setNotificationpath(screen.equals("Sheet Order") ? "/registertask" : "/Protocolorder");
		LSnotification.setNotifationfrom(notifyfrom);
		LSnotification.setNotifationto(notifyto);
		LSnotification.setRepositorycode(0);
		LSnotification.setRepositorydatacode(0);
		LSnotification.setNotificationfor(1);
		if (objdir.getSitecode() != null) {
			LSnotification.setSitecode(objdir.getSitecode());
		}
		lsnotificationRepository.save(LSnotification);
		batchid = null;

	}

	public LSlogilablimsorderdetail stopautoregister(LSlogilablimsorderdetail objdir) throws ParseException {
		LSlogilablimsorderdetail logiobj = new LSlogilablimsorderdetail();

		logiobj = lslogilablimsorderdetailRepository.findByBatchcodeOrderByBatchcodeDesc(objdir.getBatchcode());

		logiobj.setRepeat(objdir.getRepeat());
		logiobj.setAutoregistercount(0);
		lslogilablimsorderdetailRepository.save(logiobj);

		List<LsAutoregister> autoobj = lsautoregisterrepo.findByBatchcodeAndScreen(objdir.getBatchcode(),
				"Sheet_Order");
		if (!autoobj.isEmpty()) {
			autoobj.get(0).setRepeat(objdir.getRepeat());
			autoobj.get(0).setStoptime(commonfunction.getCurrentUtcTime());
			logiobj.setLsautoregisterorders(autoobj.get(0));
			logiobj.setRepeat(false);
			logiobj.setCanuserprocess(true);
			lsautoregisterrepo.saveAll(autoobj);
			String timerId = autoobj.get(0).getTimerIdname();
			if (timerId != null) {
				stopTimer(timerId);

			}
		}
		lslogilablimsorderdetailRepository.save(logiobj);

		return logiobj;
	}

	public LSlogilabprotocoldetail stopprotoautoregister(LSlogilabprotocoldetail objdir) throws ParseException {
		LSlogilabprotocoldetail logiobj = new LSlogilabprotocoldetail();
		logiobj = LSlogilabprotocoldetailRepository.findByProtocolordercodeAndProtoclordername(
				objdir.getProtocolordercode(), objdir.getProtoclordername());

		logiobj.setRepeat(objdir.getRepeat());
		LSlogilabprotocoldetailRepository.save(logiobj);

		List<LsAutoregister> autoobj = lsautoregisterrepo.findByBatchcodeAndScreen(objdir.getProtocolordercode(),
				objdir.getLsautoregister().getScreen());
		if (!autoobj.isEmpty()) {
			autoobj.get(0).setRepeat(objdir.getRepeat());
			autoobj.get(0).setStoptime(commonfunction.getCurrentUtcTime());
			lsautoregisterrepo.saveAll(autoobj);
		}
		LSlogilabprotocoldetailRepository.save(logiobj);
		return logiobj;
	}

	public List<LSlogilablimsorderdetail> getsingleorder(LSlogilablimsorderdetail body) {
		List<LSlogilablimsorderdetail> obj = LogilablimsorderdetailsRepository
				.findByBatchcodeInOrderByBatchcodeAsc(Arrays.asList(body.getBatchcode()));
		return obj;
	}

	public LSlogilablimsorderdetail Getsingleorder(LSlogilablimsorderdetail objorder) {
		return lslogilablimsorderdetailRepository.findByBatchcodeOrderByBatchcodeDesc(objorder.getBatchcode());
	}

//	public void saveResulttags(Lsresulttags objorder) {
//		lsresulttagsRepository.save(objorder);
//	}

	public List<ProjectOrTaskOrMaterialView> Suggesionforfolder(Map<String, Object> searchobj) {
		String Searchkey = "%" + searchobj.get("Searchkey") + "%";
		Integer sitecode = (Integer) searchobj.get("sitecode");
		List<ProjectOrTaskOrMaterialView> rtnobj = lsprojectmasterRepository
				.getProjectOrTaskOrMaterialSearchBased(Searchkey, sitecode);
		return rtnobj;
	}

	public Map<String, Object> Getordersonfiles(LSfile[] objfiles) {
//		List<LSfile> files = Arrays.asList(objfiles);
		Map<String, Object> mapRtnObj = new HashMap<String, Object>();
//		List<Logilaborders> orders = lslogilablimsorderdetailRepository.findByLsfileIn(files);
//		List<Long> batchcode = orders.stream().map(Logilaborders::getBatchcode)
//				.collect(Collectors.toList());
//		mapRtnObj.put("orders", orders);
//		mapRtnObj.put("results", lsresultforordersRepository.findByBatchcodeInOrderByIdDesc(batchcode));
//		mapRtnObj.put("tags", lsresulttagsRepository.findByOrderidInOrderByIdDesc(batchcode));
		return mapRtnObj;
	}

	public Map<String, Object> Getordersonfiles(Map<String, Object> mapObj) throws ParseException {

		ObjectMapper objm = new ObjectMapper();

		List<LSfile> files = objm.convertValue(mapObj.get("filelist"), new TypeReference<List<LSfile>>() {
		});

		// Extract fromdate and todate
		Long fromdateStr = (Long) mapObj.get("fromdate");
		Long todateStr = (Long) mapObj.get("todate");

		Date fromdate = new Date(fromdateStr); // formatter.parse(fromdateStr);
		Date todate = new Date(todateStr);

		// Set the end of the day for todate
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(todate);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		todate = calendar.getTime();

//		List<LSfile> files = Arrays.asList(objfiles);
		Map<String, Object> mapRtnObj = new HashMap<String, Object>();
		List<Logilaborders> orders = logilablimsorderdetailsRepository
				.findByOrderflagAndLsfileInAndCreatedtimestampBetweenAndAssignedtoIsNullAndOrdercancellIsNullOrderByBatchcodeDesc(
						"R", files, fromdate, todate);
		List<Long> batchcode = orders.stream().map(Logilaborders::getBatchcode).collect(Collectors.toList());
		List<Integer> filecode = files.stream().map(LSfile::getFilecode).collect(Collectors.toList());

		mapRtnObj.put("orders", orders);
		mapRtnObj.put("results", lsresultforordersRepository.findByBatchcodeInOrderByIdDesc(batchcode));
		mapRtnObj.put("tags", reportfileRepository.findByBatchcodeInOrderByIdDesc(batchcode));
		mapRtnObj.put("files", LSfileRepository.findByFilecodeIn(filecode));

		return mapRtnObj;
	}

	public List<LSlogilablimsorderdetail> GetOrdersbyuseronDetailview(LSlogilablimsorderdetail obj) {
		List<LSlogilablimsorderdetail> lstorders = new ArrayList<LSlogilablimsorderdetail>();

		lstorders = logilablimsorderdetailsRepository
				.findByFiletypeAndOrderflagAndLsprojectmasterInAndApprovelstatusNotAndCompletedtimestampBetweenAndAssignedtoIsNullOrderByBatchcodeDesc(
						obj.getFiletype(), "R", obj.getLstproject(), 3, obj.getFromdate(), obj.getTodate());
		return lstorders;
	}

	public Map<String, Object> Getorderbyflaganduseronstart(LSlogilablimsorderdetail objorder) {
		Map<String, Object> rtn_object = new HashMap<>();
		List<LSuserteammapping> lstteammap = lsuserteammappingRepository.findBylsuserMaster(objorder.getLsuserMaster());
		List<LSusersteam> lstteam = lsusersteamRepository.findByLsuserteammappingInAndLssitemaster(lstteammap,
				objorder.getLsuserMaster().getLssitemaster());
		List<LSprojectmaster> lstproject = lsprojectmasterRepository.findByLsusersteamIn(lstteam);
		List<Elnmaterial> nmaterialcode = elnmaterialRepository
				.findByNsitecode(objorder.getLsuserMaster().getLssitemaster().getSitecode());
		List<Logilaborderssh> lstorder = new ArrayList<Logilaborderssh>();
		Date fromdate = objorder.getFromdate();
		Date todate = objorder.getTodate();
		Integer filetype = objorder.getFiletype();
		List<LSSheetOrderStructure> lstdir;
		long Directorycode_Not = -3L;
		if (objorder.getLstuserMaster() == null) {
			lstdir = lsSheetOrderStructureRepository
					.findBySitemasterAndViewoptionOrCreatedbyAndViewoptionOrCreatedbyAndViewoptionAndDirectorycodeNotOrderByDirectorycode(
							objorder.getLsuserMaster().getLssitemaster(), 1, objorder.getLsuserMaster(), 2,
							objorder.getLsuserMaster(), 3, Directorycode_Not);
		} else {
			lstdir = lsSheetOrderStructureRepository
					.findBySitemasterAndViewoptionOrCreatedbyAndViewoptionOrSitemasterAndViewoptionAndCreatedbyInAndDirectorycodeNotOrderByDirectorycode(
							objorder.getLsuserMaster().getLssitemaster(), 1, objorder.getLsuserMaster(), 2,
							objorder.getLsuserMaster().getLssitemaster(), 3, objorder.getLstuserMaster(),
							Directorycode_Not);
		}
		List<Long> Directory_Code = lstdir.stream().map(LSSheetOrderStructure::getDirectorycode)
				.filter(code -> code > 0).collect(Collectors.toList());
		if (objorder.getLstuserMaster() != null) {
			lstorder = logilablimsorderdetailsRepository
					.findByOrderflagAndLsprojectmasterInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndOrdercancellIsNullOrderByBatchcodeDesc(
							objorder.getOrderflag(), lstproject, filetype, fromdate, todate);
			int chunkSize = Integer.parseInt(env.getProperty("lssamplecount"));
			int totalSamples = nmaterialcode.size();

			List<Logilaborderssh> lstorderobj = IntStream.range(0, (totalSamples + chunkSize - 1) / chunkSize)
					.parallel().mapToObj(i -> {
						int startIndex = i * chunkSize;
						int endIndex = Math.min(startIndex + chunkSize, totalSamples);
						List<Elnmaterial> currentChunk = nmaterialcode.subList(startIndex, endIndex);

						List<Logilaborderssh> orderChunk = new ArrayList<>();

						orderChunk.addAll(logilablimsorderdetailsRepository
								.findByOrderflagAndLsprojectmasterIsNullAndDirectorycodeIsNullAndElnmaterialInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndLsuserMasterAndOrdercancellIsNull(
										objorder.getOrderflag(), currentChunk, filetype, fromdate, todate, 2,
										objorder.getLsuserMaster()));

						orderChunk.addAll(logilablimsorderdetailsRepository
								.findByOrderflagAndLsprojectmasterIsNullAndDirectorycodeIsNullAndElnmaterialInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndOrdercancellIsNull(
										objorder.getOrderflag(), currentChunk, filetype, fromdate, todate, 1));

						orderChunk.addAll(logilablimsorderdetailsRepository
								.findByOrderflagAndLsprojectmasterIsNullAndDirectorycodeIsNullAndElnmaterialInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndLsuserMasterAndOrdercancellIsNullOrderByBatchcodeDesc(
										objorder.getOrderflag(), currentChunk, filetype, fromdate, todate, 3,
										objorder.getLsuserMaster()));

						return orderChunk;
					}).flatMap(List::stream).collect(Collectors.toList());

			lstorder.addAll(lstorderobj);

			if (Directory_Code != null) {
				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullAndOrdercancellIsNullOrDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsuserMasterInAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullAndOrdercancellIsNullOrderByBatchcodeDesc(
								Directory_Code, 1, fromdate, todate, objorder.getOrderflag(), filetype, Directory_Code,
								2, objorder.getLsuserMaster(), fromdate, todate, objorder.getOrderflag(), filetype,
								Directory_Code, 3, fromdate, todate, objorder.getLstuserMaster(),
								objorder.getOrderflag(), filetype));

			}

			lstorder.forEach(objorderDetail -> objorderDetail.setLw(objorder.getLstworkflow()));

		} else {
			lstorder = lslogilablimsorderdetailRepository
					.findByOrderflagAndLsprojectmasterInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNull(
							objorder.getOrderflag(), lstproject, filetype, fromdate, todate);

			lstorder.addAll(logilablimsorderdetailsRepository
					.findByOrderflagAndLsprojectmasterIsNullAndDirectorycodeIsNullAndElnmaterialInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndLsuserMasterAndOrdercancellIsNull(
							objorder.getOrderflag(), nmaterialcode, filetype, fromdate, todate, 2,
							objorder.getLsuserMaster()));

			lstorder.addAll(logilablimsorderdetailsRepository
					.findByOrderflagAndLsprojectmasterIsNullAndDirectorycodeIsNullAndElnmaterialInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndOrdercancellIsNull(
							objorder.getOrderflag(), nmaterialcode, filetype, fromdate, todate, 1));

			lstorder.addAll(logilablimsorderdetailsRepository
					.findByOrderflagAndLsprojectmasterIsNullAndDirectorycodeIsNullAndElnmaterialInAndFiletypeAndCreatedtimestampBetweenAndAssignedtoIsNullAndViewoptionAndLsuserMasterAndOrdercancellIsNullOrderByBatchcodeDesc(
							objorder.getOrderflag(), nmaterialcode, filetype, fromdate, todate, 3,
							objorder.getLsuserMaster()));

			if (Directory_Code != null) {
				lstorder.addAll(logilablimsorderdetailsRepository
						.findByDirectorycodeInAndViewoptionAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullOrDirectorycodeInAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndLsprojectmasterIsNullAndOrderflagAndFiletypeAndAssignedtoIsNullOrderByBatchcodeDesc(
								Directory_Code, 1, fromdate, todate, objorder.getOrderflag(), filetype, Directory_Code,
								2, objorder.getLsuserMaster(), fromdate, todate, objorder.getOrderflag(), filetype,
								Directory_Code, 3, objorder.getLsuserMaster(), fromdate, todate,
								objorder.getOrderflag(), filetype));
			}

		}

		if (objorder.getSearchCriteria().getContentsearchtype() != null
				&& objorder.getSearchCriteria().getContentsearch() != null) {
			List<Long> lstBatchcode = lstorder.stream().map(Logilaborderssh::getBc).collect(Collectors.toList());
			if (lstBatchcode != null && lstBatchcode.size() > 0) {
				lstorder = Onsearchordercontent(lstBatchcode, objorder);
			}
		}

		lstorder.forEach(objorderDetail -> objorderDetail.setLw(objorder.getLstworkflow()));
		rtn_object.put("Orders", lstorder);
		return rtn_object;

	}

	public ResponseEntity<Object> insertOrderLink(LsOrderLinks objorder) throws ParseException {
		objorder.setCreateddate(commonfunction.getCurrentUtcTime());
		objorder.setNstatus(1);
		lsorderlinkrepository.save(objorder);
		return new ResponseEntity<>(lsorderlinkrepository.findByBatchcodeAndNstatus(objorder.getBatchcode(), 1),
				HttpStatus.OK);
	}

	public ResponseEntity<Object> getLinksOnOrder(LsOrderLinks objorder) {
		return new ResponseEntity<>(lsorderlinkrepository.findByBatchcodeAndNstatus(objorder.getBatchcode(), 1),
				HttpStatus.OK);
	}

	public ResponseEntity<Object> deleteLinkforOrder(LsOrderLinks objorder) {
		lsorderlinkrepository.delete(objorder);
		return new ResponseEntity<>(lsorderlinkrepository.findByBatchcodeAndNstatus(objorder.getBatchcode(), 1),
				HttpStatus.OK);
	}

	public List<LSSelectedTeam> Getselectedteam(Long batchcode) {

		List<LSSelectedTeam> selectedteam = LSSelectedTeamRepository.findByBatchcode(batchcode);

		return selectedteam;
	}

	public List<LSprotocolselectedteam> Getprotoselectedteam(Long protocolordercode) {

		List<LSprotocolselectedteam> selectedteam = lsprotoselectedteamRepo.findByProtocolordercode(protocolordercode);

		return selectedteam;
	}

	public ResponseEntity<Object> GetAllorders(LSuserMaster objuser) {
		List<LogilabOrdermastersh> lstorders = new ArrayList<LogilabOrdermastersh>();
		Date fromdate = objuser.getObjuser().getFromdate();
		Date todate = objuser.getObjuser().getTodate();

		List<LSuserteammapping> teammapping = lsuserteammappingRepository.findByLsuserMasterAndTeamcodeNotNull(objuser);
		List<LSusersteam> userteamlist = lsusersteamRepository.findByLsuserteammappingIn(teammapping);
		List<LSSelectedTeam> selectedorders = LSSelectedTeamRepository
				.findByUserteamInAndCreatedtimestampBetween(userteamlist, fromdate, todate);
		List<Long> selectedteambatchCodeList = new ArrayList<>();
		selectedteambatchCodeList = selectedteambatchCodeList.isEmpty() ? Collections.singletonList(-1L)
				: selectedteambatchCodeList;
		if (selectedorders != null && !selectedorders.isEmpty()) {
			selectedteambatchCodeList = selectedorders.stream().map(LSSelectedTeam::getBatchcode)
					.filter(Objects::nonNull).distinct().collect(Collectors.toList());
		}
//		lstorders = lslogilablimsorderdetailRepository.getLSlogilablimsorderdetaildashboardforallorders("N",
//				0, fromdate, todate, objuser, 1, 2, 3, objuser.getUsernotify(), objuser.getLssitemaster(),
//				 3,"R",selectedteambatchCodeList);
		lstorders = logilablimsorderdetailsRepository.findByCreatedtimestampBetweenAndSitecodeOrderByBatchcodeDesc(
				fromdate, todate, objuser.getLssitemaster().getSitecode());
		return new ResponseEntity<>(lstorders, HttpStatus.OK);
	}

	public List<Map<String, Object>> GetSheettagdataonOrdercode(Long batchcode)
			throws JsonMappingException, JsonProcessingException {
		lsreportfile tags = reportfileRepository.findByBatchcode(batchcode);

		return GetSheettagdata(tags);
	}

	public List<Map<String, Object>> GetSheettagdataonSequenceid(String sequenceid)
			throws JsonMappingException, JsonProcessingException {

		LSlogilablimsorderdetail order = logilablimsorderdetailsRepository.findBySequenceid(sequenceid);

		if (order == null) {
			return new ArrayList<Map<String, Object>>();
		}

		lsreportfile tags = reportfileRepository.findByBatchcode(order.getBatchcode());

		return GetSheettagdata(tags);
	}

	public List<Map<String, Object>> GetSheettagdata(lsreportfile tags)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		List<Map<String, Object>> tagdetails = new ArrayList<Map<String, Object>>();

		if (tags != null && tags.getContent() != null) {
			List<TagStructure> listtags = objectMapper.readValue(tags.getContent(),
					new TypeReference<List<TagStructure>>() {
					});
			listtags.stream().forEach(tag -> {

				List<String[]> listvalue = new ArrayList<String[]>();
				List<String> listfinalvalue = new ArrayList<String>();
				try {
					if (tag.getTagvalue() != null) {
						if (tag.getTagvalue().startsWith("[") && tag.getTagvalue().endsWith("]")) {
							listvalue = objectMapper.readValue(tag.getTagvalue(), new TypeReference<List<String[]>>() {
							});

							listvalue.stream().forEach(tagval -> {
								if (tagval != null && tagval.length > 0) {
									listfinalvalue.add(tagval[0]);
								}
							});
						} else {
							listfinalvalue.add(tag.getTagvalue());
						}

						for (int data = 0; data < listfinalvalue.size(); data++) {
							String datavalue = listfinalvalue.get(data);
							if (tagdetails != null && tagdetails.size() > data && tagdetails.get(data) != null) {
								tagdetails.get(data).put(tag.getTagname(), datavalue);
							} else {
								Map<String, Object> tagmap = new HashMap<>();
								tagmap.put(tag.getTagname(), datavalue);
								tagdetails.add(tagmap);
							}
						}

					}

				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			});

		}
		return tagdetails;
	}

	public LogilabOrderDetails GetorderlockStatus(LSlogilablimsorderdetail objorder)
			throws IOException, ParseException {

		LogilabOrderDetails objupdatedorder = logilablimsorderdetailsRepository
				.findByBatchcode(objorder.getBatchcode());
		if (objupdatedorder.getLockeduser() != null && objorder.getObjLoggeduser() != null
				&& objupdatedorder.getLockeduser().equals(objorder.getObjLoggeduser().getUsercode())) {
			objupdatedorder.setIsLockbycurrentuser(1);
		} else {
			objupdatedorder.setIsLockbycurrentuser(0);
		}
		objupdatedorder.setIsLock(1);
		return objupdatedorder;

	}

	public LSlogilablimsorderdetail getordercontent(LSlogilablimsorderdetail order) throws IOException {
		if (order.getLssamplefile() != null) {
			if (order.getIsmultitenant() == 1 || order.getIsmultitenant() == 2) {
				CloudOrderCreation objCreation = cloudOrderCreationRepository
						.findTop1ById((long) order.getLssamplefile().getFilesamplecode());
				if (objCreation != null && objCreation.getContainerstored() == 0) {
					order.getLssamplefile().setFilecontent(objCreation.getContent());
				} else {
					order.getLssamplefile().setFilecontent(objCloudFileManipulationservice.retrieveCloudSheets(
							objCreation.getFileuid(),
							commonfunction.getcontainername(order.getIsmultitenant(), TenantContext.getCurrentTenant())
									+ "ordercreation"));
				}
			} else {
				GridFSFile largefile = gridFsTemplate.findOne(new Query(
						Criteria.where("filename").is("order_" + order.getLssamplefile().getFilesamplecode())));
				if (largefile == null) {
					largefile = gridFsTemplate.findOne(new Query(
							Criteria.where("_id").is("order_" + order.getLssamplefile().getFilesamplecode())));
				}
				if (largefile != null) {
					GridFsResource resource = gridFsTemplate.getResource(largefile.getFilename());
					order.getLssamplefile().setFilecontent(
							new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))
									.lines().collect(Collectors.joining("\n")));
				} else {

					if (mongoTemplate.findById(order.getLssamplefile().getFilesamplecode(),
							OrderCreation.class) != null) {
						order.getLssamplefile()
								.setFilecontent(mongoTemplate
										.findById(order.getLssamplefile().getFilesamplecode(), OrderCreation.class)
										.getContent());
					}
				}
			}
		}
		return order;
	}

	public Map<String, Object> GetRejectedorderbyflaganduser(LSlogilablimsorderdetail objorder) {
		Map<String, Object> rtn_object = new HashMap<>();
		List<LSuserteammapping> lstteammap = lsuserteammappingRepository.findBylsuserMaster(objorder.getLsuserMaster());
		List<LSusersteam> lstteam = lsusersteamRepository.findByLsuserteammappingInAndLssitemaster(lstteammap,
				objorder.getLsuserMaster().getLssitemaster());
		List<LSprojectmaster> lstproject = lsprojectmasterRepository.findByLsusersteamIn(lstteam);

//		List<Elnmaterial> nmaterialcode = elnmaterialRepository
//				.findByNsitecode(objorder.getLsuserMaster().getLssitemaster().getSitecode());
		List<Logilaborderssh> lstorder = new ArrayList<Logilaborderssh>();
		Date fromdate = objorder.getFromdate();
		Date todate = objorder.getTodate();
		Integer filterprojects = objorder.getLsprojectmaster() != null ? objorder.getLsprojectmaster().getProjectcode()
				: -1;
		Integer testcode = objorder.getTestcode() != null ? objorder.getTestcode() : -1;
		Integer filetype = objorder.getFiletype();
		LSuserMaster objuser = objorder.getLsuserMaster();
		List<LSuserMaster> userlist = objorder.getLstuserMaster();
		if (userlist == null || userlist.isEmpty()) {
			userlist = new ArrayList<>();
			userlist.add(objorder.getLsuserMaster());
		}

		if (filetype == -1 && testcode == -1 && filterprojects == -1) {
			lstorder = LogilablimsorderdetailsRepository
					.findByLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusAndAssignedtoIsNullAndSitecodeOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusAndAssignedtoIsNullAndSitecodeOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusAndAssignedtoIsNullAndSitecodeOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusAndAssignedtoIsNullAndSitecodeOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndApprovelstatusAndSitecodeOrAssignedtoAndCreatedtimestampBetweenAndApprovelstatusAndSitecode(
							1, objuser, fromdate, todate, 3, objuser.getLssitemaster().getSitecode(), 2, objuser,
							fromdate, todate, 3, objuser.getLssitemaster().getSitecode(), 3, objuser, fromdate, todate,
							3, objuser.getLssitemaster().getSitecode(), 3, objuser, fromdate, todate, 3,
							objuser.getLssitemaster().getSitecode(), objuser, objuser, fromdate, todate, 3,
							objuser.getLssitemaster().getSitecode(), objuser, fromdate, todate, 3,
							objuser.getLssitemaster().getSitecode());
			if (lstproject != null && !lstproject.isEmpty()) {
				lstorder.addAll(LogilablimsorderdetailsRepository
						.findByApprovelstatusAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndSitecode(
								3, lstproject, fromdate, todate, objuser.getLssitemaster().getSitecode()));
			}

		} else if (filetype != -1 && testcode == -1 && filterprojects == -1) {
			lstorder = LogilablimsorderdetailsRepository
					.findByLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusAndAssignedtoIsNullAndSitecodeAndFiletypeOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusAndAssignedtoIsNullAndSitecodeAndFiletypeOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusAndAssignedtoIsNullAndSitecodeAndFiletypeOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusAndAssignedtoIsNullAndSitecodeAndFiletypeOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndApprovelstatusAndSitecodeAndFiletypeOrAssignedtoAndCreatedtimestampBetweenAndApprovelstatusAndSitecodeAndFiletype(
							1, objuser, fromdate, todate, 3, objuser.getLssitemaster().getSitecode(), filetype, 2,
							objuser, fromdate, todate, 3, objuser.getLssitemaster().getSitecode(), filetype, 3, objuser,
							fromdate, todate, 3, objuser.getLssitemaster().getSitecode(), filetype, 3, objuser,
							fromdate, todate, 3, objuser.getLssitemaster().getSitecode(), filetype, objuser, objuser,
							fromdate, todate, 3, objuser.getLssitemaster().getSitecode(), filetype, objuser, fromdate,
							todate, 3, objuser.getLssitemaster().getSitecode(), filetype);
			if (lstproject != null && !lstproject.isEmpty()) {
				lstorder.addAll(LogilablimsorderdetailsRepository
						.findByApprovelstatusAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndSitecodeAndFiletype(
								3, lstproject, fromdate, todate, objuser.getLssitemaster().getSitecode(), filetype));
			}

		} else if (filetype == -1 && testcode != -1 && filterprojects == -1) {
			lstorder = LogilablimsorderdetailsRepository
					.findByLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusAndAssignedtoIsNullAndSitecodeAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusAndAssignedtoIsNullAndSitecodeAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusAndAssignedtoIsNullAndSitecodeAndTestcodeOrLsprojectmasterIsNullAndViewoptionAndLsuserMasterAndCreatedtimestampBetweenAndApprovelstatusAndAssignedtoIsNullAndSitecodeAndTestcodeOrLsuserMasterAndAssignedtoNotAndCreatedtimestampBetweenAndAssignedtoNotNullAndApprovelstatusAndSitecodeAndTestcodeOrAssignedtoAndCreatedtimestampBetweenAndApprovelstatusAndSitecodeAndTestcode(
							1, objuser, fromdate, todate, 3, objuser.getLssitemaster().getSitecode(), testcode, 2,
							objuser, fromdate, todate, 3, objuser.getLssitemaster().getSitecode(), testcode, 3, objuser,
							fromdate, todate, 3, objuser.getLssitemaster().getSitecode(), testcode, 3, objuser,
							fromdate, todate, 3, objuser.getLssitemaster().getSitecode(), testcode, objuser, objuser,
							fromdate, todate, 3, objuser.getLssitemaster().getSitecode(), testcode, objuser, fromdate,
							todate, 3, objuser.getLssitemaster().getSitecode(), testcode);
			if (lstproject != null && !lstproject.isEmpty()) {
				lstorder.addAll(LogilablimsorderdetailsRepository
						.findByApprovelstatusAndLsprojectmasterInAndCreatedtimestampBetweenAndAssignedtoIsNullAndSitecodeAndTestcode(
								3, lstproject, fromdate, todate, objuser.getLssitemaster().getSitecode(), testcode));
			}

		} else if (filetype == -1 && testcode == -1 && filterprojects != -1) {

			lstorder.addAll(LogilablimsorderdetailsRepository
					.findByApprovelstatusAndLsprojectmasterAndCreatedtimestampBetweenAndAssignedtoIsNullAndSitecode(3,
							objorder.getLsprojectmaster(), fromdate, todate, objuser.getLssitemaster().getSitecode()));

		} else {
			lstorder.addAll(LogilablimsorderdetailsRepository
					.findByApprovelstatusAndLsprojectmasterAndCreatedtimestampBetweenAndAssignedtoIsNullAndSitecodeAndTestcodeAndFiletype(
							3, objorder.getLsprojectmaster(), fromdate, todate, objuser.getLssitemaster().getSitecode(),
							testcode, filetype));
		}

		if (objorder.getSearchCriteria().getContentsearchtype() != null
				&& objorder.getSearchCriteria().getContentsearch() != null) {
			List<Long> lstBatchcode = lstorder.stream().map(Logilaborderssh::getBc).collect(Collectors.toList());
			if (lstBatchcode != null && lstBatchcode.size() > 0) {
				lstorder = Onsearchordercontent(lstBatchcode, objorder);
			}
		}

		rtn_object.put("Orders", lstorder);
		return rtn_object;
	}

}
