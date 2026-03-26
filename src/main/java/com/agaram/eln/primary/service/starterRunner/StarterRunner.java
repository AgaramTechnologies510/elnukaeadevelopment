package com.agaram.eln.primary.service.starterRunner;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import com.agaram.eln.primary.commonfunction.commonfunction;
import com.agaram.eln.primary.config.TenantContext;
import com.agaram.eln.primary.model.cfr.LScfttransaction;
import com.agaram.eln.primary.model.cloudFileManip.CloudOrderCreation;
import com.agaram.eln.primary.model.cloudFileManip.CloudOrderVersion;
import com.agaram.eln.primary.model.cloudFileManip.CloudSheetCreation;
import com.agaram.eln.primary.model.cloudProtocol.CloudLsLogilabprotocolstepInfo;
import com.agaram.eln.primary.model.general.OrderCreation;
import com.agaram.eln.primary.model.general.SheetCreation;
import com.agaram.eln.primary.model.instrumentDetails.LSOrdernotification;
import com.agaram.eln.primary.model.instrumentDetails.LSlogilablimsorderdetail;
import com.agaram.eln.primary.model.instrumentDetails.LsAutoregister;
import com.agaram.eln.primary.model.protocols.LSlogilabprotocoldetail;
import com.agaram.eln.primary.model.protocols.LSprotocolmaster;
import com.agaram.eln.primary.model.protocols.LSprotocolordersampleupdates;
import com.agaram.eln.primary.model.protocols.LSprotocolsampleupdates;
import com.agaram.eln.primary.model.protocols.LsLogilabprotocolstepInfo;
import com.agaram.eln.primary.model.sequence.SequenceTable;
import com.agaram.eln.primary.model.sequence.SequenceTableProjectLevel;
import com.agaram.eln.primary.model.sequence.SequenceTableTaskLevel;
import com.agaram.eln.primary.model.sheetManipulation.LSfile;
import com.agaram.eln.primary.model.sheetManipulation.LSsamplefile;
import com.agaram.eln.primary.model.sheetManipulation.LSsamplefileversion;
import com.agaram.eln.primary.model.sheetManipulation.Notification;
import com.agaram.eln.primary.model.usermanagement.LSSiteMaster;
import com.agaram.eln.primary.model.usermanagement.LSnotification;
import com.agaram.eln.primary.model.usermanagement.LSuserMaster;
import com.agaram.eln.primary.repository.cfr.LScfttransactionRepository;
import com.agaram.eln.primary.repository.cloudFileManip.CloudOrderCreationRepository;
import com.agaram.eln.primary.repository.cloudFileManip.CloudOrderVersionRepository;
import com.agaram.eln.primary.repository.cloudFileManip.CloudSheetCreationRepository;
import com.agaram.eln.primary.repository.instrumentDetails.LSlogilablimsorderdetailRepository;
import com.agaram.eln.primary.repository.instrumentDetails.LSordernotificationRepository;
import com.agaram.eln.primary.repository.instrumentDetails.LsAutoregisterRepository;
import com.agaram.eln.primary.repository.protocol.ElnprotocolworkflowRepository;
import com.agaram.eln.primary.repository.protocol.LSProtocolMasterRepository;
import com.agaram.eln.primary.repository.protocol.LSlogilabprotocoldetailRepository;
import com.agaram.eln.primary.repository.protocol.LSprotocolordersampleupdatesRepository;
import com.agaram.eln.primary.repository.protocol.LSprotocolsampleupdatesRepository;
import com.agaram.eln.primary.repository.sequence.SequenceTableRepository;
import com.agaram.eln.primary.repository.sheetManipulation.LSfileRepository;
import com.agaram.eln.primary.repository.sheetManipulation.LSsamplefileRepository;
import com.agaram.eln.primary.repository.sheetManipulation.LSsamplefileversionRepository;
import com.agaram.eln.primary.repository.sheetManipulation.NotificationRepository;
import com.agaram.eln.primary.repository.usermanagement.LSSiteMasterRepository;
import com.agaram.eln.primary.repository.usermanagement.LSnotificationRepository;
import com.agaram.eln.primary.repository.usermanagement.LSuserMasterRepository;
import com.agaram.eln.primary.service.cloudFileManip.CloudFileManipulationservice;
import com.agaram.eln.primary.service.instrumentDetails.InstrumentService;
import com.agaram.eln.primary.service.protocol.ProtocolService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.gridfs.model.GridFSFile;

@Service
public class StarterRunner {

	private Map<Integer, TimerTask> scheduledTasks = new ConcurrentHashMap<>();
    
	@Autowired
	private NotificationRepository notificationRepository;
	
	@Autowired
	private ElnprotocolworkflowRepository elnprotocolworkflowRepository;

	@Autowired
	private LSnotificationRepository lSnotificationRepository;

	@Autowired
	private LSprotocolsampleupdatesRepository LSprotocolsampleupdatesRepository;

	@Autowired
	private LSSiteMasterRepository LSSiteMasterRepository;

	
	@Autowired
	private LSprotocolordersampleupdatesRepository lsprotocolordersampleupdatesRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private LSlogilabprotocoldetailRepository lslogilabprotocoldetailrepo;
	
	@Autowired
	private LSordernotificationRepository lsordernotificationrepo;

	@Autowired
	private LSnotificationRepository lsnotificationrepo;
	
	@Autowired
	private LsAutoregisterRepository lsautorepo;
	
	@Autowired
	private LSuserMasterRepository usermasterrepo;
	
	@Autowired
	private LSlogilablimsorderdetailRepository lslogilablimsorderdetailrepo;
	
	@Autowired
	private LSfileRepository lsfilerepo;

	@Autowired
	private CloudFileManipulationservice objCloudFileManipulationservice;
	
	@Autowired
	private GridFsTemplate gridFsTemplate;
	
	@Autowired
	private CloudSheetCreationRepository cloudSheetCreationRepository;
	
	@Autowired
	private LSsamplefileversionRepository lssamplefileversionrepo;
	
	@Autowired
	private CloudOrderVersionRepository cloudorderversionrepo;
	
	@Autowired
	private LSProtocolMasterRepository LSProtocolMasterRepositoryObj;
	
	@Autowired
	private LSsamplefileRepository lssamplefileRepo;
	
	@Autowired
	private CloudOrderCreationRepository  cloudOrderCreationRepository;
	
	@Autowired
	private LScfttransactionRepository lscfttransactionrepo;
	
	@Autowired
	private LSProtocolMasterRepository lsprotocolmasterrepo;
	
	@Autowired
	private SequenceTableRepository sequencetableRepository;
	@Autowired
	private InstrumentService instrumentService;
	
	@Autowired
	private ProtocolService protocolService;
	
	Date currentdate = null;
    Date gettoDate=null;
    Date getfromDate=null;
    private static String defaultContent = "{\"activeSheet\":\"Sheet1\",\"sheets\":[{\"name\":\"Sheet1\",\"rows\":[],\"columns\":[],\"selection\":\"A1:A1\",\"activeCell\":\"A1:A1\",\"frozenRows\":0,\"frozenColumns\":0,\"showGridLines\":true,\"gridLinesColor\":null,\"mergedCells\":[],\"hyperlinks\":[],\"defaultCellStyle\":{\"fontFamily\":\"Arial\",\"fontSize\":\"12\"},\"drawings\":[]}],\"names\":[],\"columnWidth\":64,\"rowHeight\":20,\"images\":[],\"charts\":[],\"tags\":[],\"fieldcount\":0,\"Batchcoordinates\":{\"resultdirection\":1,\"parameters\":[]}}";
	
    
    public void executeOnStartup() throws SQLException {
//        System.out.println("Task executed on startup");
//        checkAndScheduleReminders();
//        checkAndScheduleRemindersforOrders();
//        checkAndScheduleautoOrderRegister();
//        checkAndScheduleProtocolautoRegister();   
    	
    	System.out.println("Task executed on startup");
        new Thread(() -> {
			try {
				System.out.println("auto register for sheet");
				checkAndScheduleautoOrderRegister();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
        
        new Thread(() -> {
			try {
				System.out.println("reminder alert concept");
				checkAndScheduleReminders();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
    
        
        new Thread(() -> {
			try {
				System.out.println("reminder for orders");
				checkAndScheduleRemindersforOrders();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
        
      
        new Thread(() -> {
			try {
				System.out.println("auto register for protocol");
				checkAndScheduleProtocolautoRegister();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
        
    }

    public void getCurrentUTCDate() throws Exception {
    	currentdate = commonfunction.getCurrentUtcTime();
	}
    
    public void gettofromdate() {

        LocalDateTime localDateTime = LocalDateTime.now();
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
         gettoDate = Date.from(instant);
         
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY , +12);
        gettoDate=calendar.getTime();
      
//        LocalDateTime  previousDate = localDateTime.minusDays(1);
//        Instant preinstant = previousDate.atZone(ZoneId.systemDefault()).toInstant();
 //       getfromDate=Date.from(preinstant);  
        
        Calendar fromcalendar = Calendar.getInstance();
        fromcalendar.add(Calendar.HOUR_OF_DAY, -12);
        getfromDate=fromcalendar.getTime();
    }

    public void checkAndScheduleReminders() throws SQLException {
    	// Get current date and time
        LocalDateTime localDateTime = LocalDateTime.now();
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();

        // Convert to java.sql.Date
        Date toDate = Date.from(instant);

        // Get the current date and subtract one day
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(toDate);
        calendar.add(Calendar.DAY_OF_MONTH, -1);  // Subtract one day
        Date fromDate = new Date(calendar.getTimeInMillis());
        
        List<Notification> lstNotify = notificationRepository.findByStatusAndCautiondateBetween(1, fromDate, toDate);
        
        List<LSnotification> lstnotifList = new ArrayList<LSnotification>();
        
        if(!lstNotify.isEmpty()) {
        	
        	lstNotify.stream().peek(objNotification->{
        		try {
        			objNotification.setStatus(0);
        			lstnotifList.add(scheduleNotificationIfDue(objNotification));
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}).collect(Collectors.toList());
        	
        	lSnotificationRepository.saveAll(lstnotifList);
        	notificationRepository.saveAll(lstNotify);
        }
    }

    private LSnotification scheduleNotificationIfDue(Notification objNotification) throws SQLException {
        Date cautionDate = objNotification.getCautiondate();
        Instant caution = cautionDate.toInstant();
        LocalDateTime cautionTime = LocalDateTime.ofInstant(caution, ZoneId.systemDefault());
        LocalDateTime currentTime = LocalDateTime.now();

        if (cautionTime.isAfter(currentTime)) {
            Duration duration = Duration.between(currentTime, cautionTime);
            long delay = duration.toMillis();
            scheduleNotification(objNotification, delay);
        }else {
        	return executeNotificationPop(objNotification);
        }
		return null;
    } 

    private void scheduleNotification(Notification objNotification, long delay) {
    	TimerTask task = new TimerTask() {
            @SuppressWarnings("unlikely-arg-type")
			@Override
            public void run() {
                try {
                    executeNotificationPopScheduled(objNotification);
                } catch (SQLException e) {
                    e.printStackTrace(); // Consider logging this properly
                }
                scheduledTasks.remove(objNotification.getNotificationid());
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, delay);
        scheduledTasks.put(objNotification.getNotificationid().intValue(), task);
    }
    
    public void executeNotificationPopScheduled(Notification notification) throws SQLException {
    	 
    	LocalDateTime localDateTime = LocalDateTime.now();
    	Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
    	Date cDate = Date.from(instant);
         
        String details = "{\"ordercode\" :\"" + notification.getOrderid() + "\",\"order\" :\""
                + notification.getBatchid() + "\",\"description\":\"" + notification.getDescription()
                + "\",\"screen\":\"" + notification.getScreen() + "\"}";
        
        String path = notification.getScreen().equals("Sheet Order") ? "/registertask" : "/Protocolorder";
        
        LSuserMaster LSuserMaster = new LSuserMaster();
		LSuserMaster.setUsercode(notification.getUsercode());
        
        LSnotification LSnotification = new LSnotification();
        
        LSnotification.setIsnewnotification(1);
		LSnotification.setNotification("CAUTIONALERT");
		LSnotification.setNotificationdate(notification.getCurrentdate());
		LSnotification.setNotificationdetils(details);
		LSnotification.setNotificationpath(path);
		LSnotification.setNotifationfrom(LSuserMaster);
		LSnotification.setNotifationto(LSuserMaster);
		LSnotification.setRepositorycode(0);
		LSnotification.setRepositorydatacode(0);
		LSnotification.setNotificationfor(1);
		LSnotification.setNotificationdate(cDate);
		
		lSnotificationRepository.save(LSnotification);
    }

    public LSnotification executeNotificationPop(Notification notification) throws SQLException {
		
    	LocalDateTime localDateTime = LocalDateTime.now();
    	Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
    	Date cDate = Date.from(instant);
         
        String details = "{\"ordercode\" :\"" + notification.getOrderid() + "\",\"order\" :\""
                + notification.getBatchid() + "\",\"description\":\"" + notification.getDescription()
                + "\",\"screen\":\"" + notification.getScreen() + "\"}";
        
        String path = notification.getScreen().equals("Sheet Order") ? "/registertask" : "/Protocolorder";
        
        LSuserMaster LSuserMaster = new LSuserMaster();
		LSuserMaster.setUsercode(notification.getUsercode());
        
        LSnotification LSnotification = new LSnotification();
        
        LSnotification.setIsnewnotification(1);
		LSnotification.setNotification("CAUTIONALERT");
		LSnotification.setNotificationdate(notification.getCurrentdate());
		LSnotification.setNotificationdetils(details);
		LSnotification.setNotificationpath(path);
		LSnotification.setNotifationfrom(LSuserMaster);
		LSnotification.setNotifationto(LSuserMaster);
		LSnotification.setRepositorycode(0);
		LSnotification.setRepositorydatacode(0);
		LSnotification.setNotificationfor(1);
		LSnotification.setNotificationdate(cDate);
    	
    	return LSnotification;
    }

    public void checkAndScheduleRemindersforOrders() throws SQLException {

        LocalDateTime localDateTime = LocalDateTime.now();
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();

        Date toDate = Date.from(instant);
        
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY , +12);
        toDate=calendar.getTime();
       
        calendar.add(Calendar.HOUR_OF_DAY , -24);
        Date fromDate = calendar.getTime();

        calendar.add(Calendar.HOUR_OF_DAY , -23);
        Date overduefromDate = calendar.getTime();
        
        List<LSOrdernotification> cautionorderslist = lsordernotificationrepo.findByCautiondateBetweenAndCautionstatus(fromDate, toDate,1);
//        List<LSOrdernotification> dueorderslist = lsordernotificationrepo.findByDuedateBetweenAndDuestatus(fromDate, toDate,1);
//        List<LSOrdernotification> overdueorderslist = lsordernotificationrepo.findDuedateBetweenAndOverduestatus(overduefromDate, toDate,1);

        List<LSOrdernotification> dueorderslist = lsordernotificationrepo.findByDuestatusAndDuedateBetween(1,fromDate, toDate);
        List<LSOrdernotification> overdueorderslist = lsordernotificationrepo.findByOverduestatusAndDuedateBetween(1,overduefromDate, toDate);
        
        if(!cautionorderslist.isEmpty()) {
        	cautionorderslist.stream().forEach(cautionindexorders->{
        		scheduleNotificationForCaution(cautionindexorders);
        	});
        }
        if(!dueorderslist.isEmpty()) {
        	 dueorderslist.stream().forEach(dueindexorders->{
        		 scheduleNotificationForDue(dueindexorders);
        	});
        }
		if(!overdueorderslist.isEmpty()) {
			overdueorderslist.stream().forEach(overdueindexorders->{
				 scheduleNotificationForOver(overdueindexorders);
			});
		}
    }

    @SuppressWarnings("deprecation")
	private void scheduleNotificationForOver(LSOrdernotification objNotification) {
    	Date overDueDate = objNotification.getDuedate();
    	overDueDate.setMinutes(overDueDate.getMinutes()+5);
        Instant overdue = overDueDate.toInstant();
        
        LocalDateTime overdueTime = LocalDateTime.ofInstant(overdue, ZoneId.systemDefault());
        LocalDateTime currentTime = LocalDateTime.now();

        
        if (overdueTime.isAfter(currentTime)) {
            Duration duration = Duration.between(currentTime, overdueTime);
            long delay = duration.toMillis();
            scheduleOverdueNotification(objNotification, delay);
        } else {
	        TimerTask task = new TimerTask() {
	            @SuppressWarnings("unlikely-arg-type")
				@Override
	            public void run() {
	                
	                	try {
							executeoverduenotification(objNotification);
						} catch (ParseException | SQLException | InterruptedException e) {
							
							e.printStackTrace();
						}
	               
	                scheduledTasks.remove(objNotification.getNotificationcode());
	            }
	        };
	        Timer timer = new Timer();
	        timer.schedule(task, 30000);
	        scheduledTasks.put(objNotification.getNotificationcode().intValue(), task);
        }
    }
    
    private void scheduleOverdueNotification(LSOrdernotification objNotification, long delay) {
    	if(objNotification.getIscompleted() == null || objNotification.getIscompleted() == false){
	        TimerTask task = new TimerTask() {
	            @SuppressWarnings("unlikely-arg-type")
				@Override
	            public void run() {
	                try {
	                	executeoverduenotification(objNotification);
	                } catch (ParseException | SQLException | InterruptedException e) {
	                    e.printStackTrace(); // Consider logging this properly
	                }
	                scheduledTasks.remove(objNotification.getNotificationcode());
	            }
	        };
	        Timer timer = new Timer();
	        timer.schedule(task, delay);
	        scheduledTasks.put(objNotification.getNotificationcode().intValue(), task);
    	}
    }
    
    
    private void scheduleNotificationForDue(LSOrdernotification objNotification) {
    	Date dueDate = objNotification.getDuedate();
        Instant due = dueDate.toInstant();
        
        LocalDateTime dueTime = LocalDateTime.ofInstant(due, ZoneId.systemDefault());
        LocalDateTime currentTime = LocalDateTime.now();

       
        if (dueTime.isAfter(currentTime)) {
            Duration duration = Duration.between(currentTime, dueTime);
            long delay = duration.toMillis();
            scheduleDueNotification(objNotification, delay);
        }else {
	        TimerTask task = new TimerTask() {
	            @SuppressWarnings("unlikely-arg-type")
				@Override
	            public void run() {
	                	try {
							executeduedatenotification(objNotification);
						} catch (ParseException | InterruptedException | SQLException e) {
							
							e.printStackTrace();
						}
	                scheduledTasks.remove(objNotification.getNotificationcode());
	            }
	        };
	        Timer timer = new Timer();
	        timer.schedule(task, 20000);
	        scheduledTasks.put(objNotification.getNotificationcode().intValue(), task);
        }
    }
    
    private void scheduleDueNotification(LSOrdernotification objNotification, long delay) {
    	if(objNotification.getIscompleted() == null || objNotification.getIscompleted() == false){
	        TimerTask task = new TimerTask() {
	            @SuppressWarnings("unlikely-arg-type")
				@Override
	            public void run() {
	                try {
	                	executeduedatenotification(objNotification);
	                } catch (ParseException | InterruptedException | SQLException e) {
	                    e.printStackTrace(); // Consider logging this properly
	                }
	                scheduledTasks.remove(objNotification.getNotificationcode());
	            }
	        };
	        Timer timer = new Timer();
	        timer.schedule(task, delay);
	        scheduledTasks.put(objNotification.getNotificationcode().intValue(), task);
    	}
    }
    
    private void scheduleNotificationForCaution(LSOrdernotification objNotification) {
        Date cautionDate = objNotification.getCautiondate();
        Instant caution = cautionDate.toInstant();
        LocalDateTime cautionTime = LocalDateTime.ofInstant(caution, ZoneId.systemDefault());
        LocalDateTime currentTime = LocalDateTime.now();

       
        if (cautionTime.isAfter(currentTime)) {
            Duration duration = Duration.between(currentTime, cautionTime);
            long delay = duration.toMillis();
            scheduleCautionNotification(objNotification, delay);
        }else {
	        TimerTask task = new TimerTask() {
	            @SuppressWarnings("unlikely-arg-type")
				@Override
	            public void run() {
	             
	                    try {
					    	executecautiondatenotification(objNotification);
						} catch (ParseException | InterruptedException | SQLException e) {
							
							e.printStackTrace();
						}
	               
	                scheduledTasks.remove(objNotification.getNotificationcode());
	            }
	        };
	        Timer timer = new Timer();
	        timer.schedule(task, 10000);
	        scheduledTasks.put(objNotification.getNotificationcode().intValue(), task);
        }
    }

    private void scheduleCautionNotification(LSOrdernotification objNotification, long delay) {
    	if(objNotification.getIscompleted() == null || objNotification.getIscompleted() == false){
	        TimerTask task = new TimerTask() {
	            @SuppressWarnings("unlikely-arg-type")
				@Override
	            public void run() {
	                try {
	                	executecautiondatenotification(objNotification);
	                } catch (ParseException | InterruptedException | SQLException e) {
	                    e.printStackTrace(); // Consider logging this properly
	                }
	                scheduledTasks.remove(objNotification.getNotificationcode());
	            }
	        };
	        Timer timer = new Timer();
	        timer.schedule(task, delay);
	        scheduledTasks.put(objNotification.getNotificationcode().intValue(), task);
    	}
    }

    public void executeduedatenotification(LSOrdernotification objNotification) throws ParseException, InterruptedException, SQLException {
    	LSOrdernotification notobj = lsordernotificationrepo.findByBatchcodeAndScreen(objNotification.getBatchcode(), objNotification.getScreen());	
    	LSlogilablimsorderdetail order = null;
    	LSlogilabprotocoldetail protocolorder = null;
    	
    	if(objNotification.getScreen().equals("sheetorder")) {
    	    order = lslogilablimsorderdetailrepo.findByBatchcodeOrderByBatchcodeDesc(objNotification.getBatchcode());
    	}else {
    	    protocolorder = lslogilabprotocoldetailrepo.findByProtocolordercode(objNotification.getBatchcode());
    	}
    	
    	int cancel;
    	int approvelstatus;
    	
    	List<LSOrdernotification> ordernotifylist = new ArrayList<LSOrdernotification>();
    	List<LSnotification> lstnotifications = new ArrayList<LSnotification>();

    	LSuserMaster assigneduser = new LSuserMaster();
    	
    	Date dueDate = objNotification.getDuedate();
    	Instant due = dueDate.toInstant();
    	
    	LocalDateTime dueTime = LocalDateTime.ofInstant(due, ZoneId.systemDefault());
    	LocalDate duedate = dueTime.toLocalDate();
    	
    	LSnotification LSnotification = new LSnotification();
    	
    	String Details = "{\"ordercode\" :\"" + objNotification.getBatchcode() 
        + "\",\"order\" :\"" + objNotification.getBatchid() 
        + "\",\"date\" :\"" + duedate 
        + "\",\"screen\":\"" + objNotification.getScreen() 
    	+ "\"}";
    	
    	String path = objNotification.getScreen().equals("sheetorder") ? "/registertask" : "/Protocolorder";
    	
    	
    	if(order==null) {
    		 cancel = protocolorder.getOrdercancell() == null ? 0 : protocolorder.getOrdercancell();
    		 approvelstatus = protocolorder.getApprovelstatus()== null ? 0 :protocolorder.getApprovelstatus();
    		 assigneduser=protocolorder.getAssignedto();
    		   if(protocolorder.getAssignedto()!=null) {
    			   LSnotification.setIsnewnotification(1);
    				LSnotification.setNotification("ORDERONDUEALERT");
    				LSnotification.setNotificationdate(new Date());
    				LSnotification.setNotificationdetils(Details);
    				LSnotification.setNotificationpath(path);
    				LSnotification.setNotifationfrom(assigneduser);
    				LSnotification.setNotifationto(assigneduser);
    				LSnotification.setRepositorycode(0);
    				LSnotification.setRepositorydatacode(0);
    				LSnotification.setNotificationfor(1);
    				lstnotifications.add(LSnotification);	
    		   }
    	}else {
    	   cancel = order.getOrdercancell() == null ? 0 : order.getOrdercancell();
    	   approvelstatus = order.getApprovelstatus()== null ? 0 :order.getApprovelstatus();
    	   
    	   assigneduser=order.getAssignedto();
    	   if(order.getAssignedto()!=null) {
    		   LSnotification.setIsnewnotification(1);
    			LSnotification.setNotification("ORDERONDUEALERT");
    			LSnotification.setNotificationdate(new Date());
    			LSnotification.setNotificationdetils(Details);
    			LSnotification.setNotificationpath(path);
    			LSnotification.setNotifationfrom(assigneduser);
    			LSnotification.setNotifationto(assigneduser);
    			LSnotification.setRepositorycode(0);
    			LSnotification.setRepositorydatacode(0);
    			LSnotification.setNotificationfor(1);
    			lstnotifications.add(LSnotification);	
    	   }
    	}
    	 
    	if((notobj.getIscompleted() == null || notobj.getIscompleted() == false) && 
    			(cancel == 0) && (approvelstatus != 3)){
    		
    		LSuserMaster LSuserMaster = new LSuserMaster();
    		LSuserMaster.setUsercode(objNotification.getUsercode());
    		
    		
    				if(objNotification.getDuestatus() == 1) {
    				        LSnotification LSnotification1 = new LSnotification();

    						LSnotification1.setIsnewnotification(1);
    						LSnotification1.setNotification("ORDERONDUEALERT");
    						LSnotification1.setNotificationdate(new Date());
    						LSnotification1.setNotificationdetils(Details);
    						LSnotification1.setNotificationpath(path);
    						LSnotification1.setNotifationfrom(LSuserMaster);
    						LSnotification1.setNotifationto(LSuserMaster);
    						LSnotification1.setRepositorycode(0);
    						LSnotification1.setRepositorydatacode(0);
    						LSnotification1.setNotificationfor(1);
    	
    						objNotification.setDuestatus(0);
    						ordernotifylist.add(objNotification);
    						lstnotifications.add(LSnotification1);				
    				        }
    					lsnotificationrepo.saveAll(lstnotifications);
    					lsordernotificationrepo.saveAll(ordernotifylist);
    					//notifyoverduedays(objNotification);
    					
    	}
    }
    
    public void executecautiondatenotification(LSOrdernotification objNotification) throws ParseException, InterruptedException, SQLException {
    	LSOrdernotification notobj = lsordernotificationrepo.findByBatchcodeAndScreen(objNotification.getBatchcode(), objNotification.getScreen());	
		LSlogilablimsorderdetail order = null;
		LSlogilabprotocoldetail protocolorder = null;
			
		if(objNotification.getScreen().equals("sheetorder")) {
		    order = lslogilablimsorderdetailrepo.findByBatchcodeOrderByBatchcodeDesc(objNotification.getBatchcode());
		}else {
			protocolorder = lslogilabprotocoldetailrepo.findByProtocolordercode(objNotification.getBatchcode());
		}
			
		int cancel;
		int approvelstatus;
			
		Date cautionDate = objNotification.getCautiondate();
		Instant caution = cautionDate.toInstant();
		LocalDateTime cautionTime = LocalDateTime.ofInstant(caution, ZoneId.systemDefault());
		LocalDate cautiondate = cautionTime.toLocalDate();
		
		LSuserMaster assigneduser = new LSuserMaster();
		List<LSnotification> lstnotifications = new ArrayList<LSnotification>();
		LSnotification LSnotification = new LSnotification();
		
		String Details = "{\"ordercode\" :\"" + objNotification.getBatchcode() 
        + "\",\"order\" :\"" + objNotification.getBatchid() 
        + "\",\"date\" :\"" + cautiondate
        + "\",\"screen\":\"" + objNotification.getScreen() 
		+ "\"}";
        String path = objNotification.getScreen().equals("sheetorder") ? "/registertask" : "/Protocolorder"; 


		if(order==null) {
			cancel = protocolorder.getOrdercancell() == null ? 0 : protocolorder.getOrdercancell();
	        approvelstatus = protocolorder.getApprovelstatus()== null ? 0 :protocolorder.getApprovelstatus();
	        
	        if(protocolorder.getAssignedto()!= null) {
		        assigneduser=protocolorder.getAssignedto();
		        
		        LSnotification.setIsnewnotification(1);
				LSnotification.setNotification("ORDERCAUTIONALERT");
				LSnotification.setNotificationdate(new Date());
				LSnotification.setNotificationdetils(Details);
				LSnotification.setNotificationpath(path);
				LSnotification.setNotifationfrom(assigneduser);
				LSnotification.setNotifationto(assigneduser);
				LSnotification.setRepositorycode(0);
				LSnotification.setRepositorydatacode(0);
				LSnotification.setNotificationfor(1);
				lstnotifications.add(LSnotification);	
	       }
		}else {
		    cancel = order.getOrdercancell() == null ? 0 : order.getOrdercancell();
			approvelstatus = order.getApprovelstatus()== null ? 0 :order.getApprovelstatus();
			
			if(order.getAssignedto() != null) {
				assigneduser=order.getAssignedto();
				
				LSnotification.setIsnewnotification(1);
				LSnotification.setNotification("ORDERCAUTIONALERT");
				LSnotification.setNotificationdate(new Date());
				LSnotification.setNotificationdetils(Details);
				LSnotification.setNotificationpath(path);
				LSnotification.setNotifationfrom(assigneduser);
				LSnotification.setNotifationto(assigneduser);
				LSnotification.setRepositorycode(0);
				LSnotification.setRepositorydatacode(0);
				LSnotification.setNotificationfor(1);
				lstnotifications.add(LSnotification);	
				
			}
		}
			 
		 
		if((notobj.getIscompleted() == null || notobj.getIscompleted() == false) && 
				(cancel == 0) && (approvelstatus != 3)){
			
			LSuserMaster LSuserMaster = new LSuserMaster();
			LSuserMaster.setUsercode(objNotification.getUsercode());
			
			List<LSOrdernotification> ordernotifylist = new ArrayList<LSOrdernotification>();
			
					if(objNotification.getCautionstatus() == 1) {
	
						LSnotification LSnotification1 = new LSnotification();
							LSnotification1.setIsnewnotification(1);
							LSnotification1.setNotification("ORDERCAUTIONALERT");
							LSnotification1.setNotificationdate(new Date());
							LSnotification1.setNotificationdetils(Details);
							LSnotification1.setNotificationpath(path);
							LSnotification1.setNotifationfrom(LSuserMaster);
							LSnotification1.setNotifationto(LSuserMaster);
							LSnotification1.setRepositorycode(0);
							LSnotification1.setRepositorydatacode(0);
							LSnotification1.setNotificationfor(1);
	
							objNotification.setCautionstatus(0);
							ordernotifylist.add(objNotification);
							lstnotifications.add(LSnotification1);				
					        }
					
			lsnotificationrepo.saveAll(lstnotifications);
			lsordernotificationrepo.saveAll(ordernotifylist);
//			/notifyoverduedays(objNotification);
		  }
    }

  public void executeoverduenotification(LSOrdernotification objNotification) throws ParseException, SQLException, InterruptedException {
    	
    	LSOrdernotification notobj = lsordernotificationrepo.findByBatchcodeAndScreen(objNotification.getBatchcode(), objNotification.getScreen());	
    	LSlogilablimsorderdetail order = null;
    	LSlogilabprotocoldetail protocolorder = null;
    	
    	if(objNotification.getScreen().equals("sheetorder")) {
    	    order = lslogilablimsorderdetailrepo.findByBatchcodeOrderByBatchcodeDesc(objNotification.getBatchcode());
    	}else {
    	    protocolorder = lslogilabprotocoldetailrepo.findByProtocolordercode(objNotification.getBatchcode());
    	}
    	
    	int cancel;
    	int approvelstatus;
    	
    	List<LSOrdernotification> ordernotifylist = new ArrayList<LSOrdernotification>();
    	List<LSnotification> lstnotifications = new ArrayList<LSnotification>();
    	LSnotification LSnotification = new LSnotification();
    	
    	LSuserMaster assigneduser = new LSuserMaster();

    	Date dueDate = objNotification.getDuedate();
    	Instant due = dueDate.toInstant();
    	
    	LocalDateTime dueTime = LocalDateTime.ofInstant(due, ZoneId.systemDefault());
    	LocalDate duedate = dueTime.toLocalDate();
    	
    	String path = objNotification.getScreen().equals("sheetorder") ? "/registertask" : "/Protocolorder";
    	
    	String Details = "{\"ordercode\" :\"" + objNotification.getBatchcode() 
        + "\",\"order\" :\"" + objNotification.getBatchid()
        + "\",\"days\" :\"" + objNotification.getOverduedays()
        + "\",\"date\" :\"" + duedate
        + "\",\"screen\":\"" + objNotification.getScreen() 
    	+ "\"}";
    	
    	if(order==null) {
    		 cancel = protocolorder.getOrdercancell() == null ? 0 : protocolorder.getOrdercancell();
    		 approvelstatus = protocolorder.getApprovelstatus()== null ? 0 :protocolorder.getApprovelstatus();
    		 
    		 assigneduser = protocolorder.getAssignedto();
    		 if(protocolorder.getAssignedto()!=null) {
    			 
    			 LSnotification.setIsnewnotification(1);
    			 LSnotification.setNotification("ORDEROVERDUEALERT");
    			 LSnotification.setNotificationdate(new Date());
    			 LSnotification.setNotificationdetils(Details);
    			 LSnotification.setNotificationpath(path);
    			 LSnotification.setNotifationfrom(assigneduser);
    			 LSnotification.setNotifationto(assigneduser);
    			 LSnotification.setRepositorycode(0);
    			 LSnotification.setRepositorydatacode(0);
    			 LSnotification.setNotificationfor(1);
    			 lstnotifications.add(LSnotification);	
    		 }
    	}else {
    	   cancel = order.getOrdercancell() == null ? 0 : order.getOrdercancell();
    	   approvelstatus = order.getApprovelstatus()== null ? 0 :order.getApprovelstatus();
    	   
    	   assigneduser=order.getAssignedto();
    	   if(order.getAssignedto()!=null) {
    		   
    		    LSnotification.setIsnewnotification(1);
    			LSnotification.setNotification("ORDEROVERDUEALERT");
    			LSnotification.setNotificationdate(new Date());
    			LSnotification.setNotificationdetils(Details);
    			LSnotification.setNotificationpath(path);
    			LSnotification.setNotifationfrom(assigneduser);
    			LSnotification.setNotifationto(assigneduser);
    			LSnotification.setRepositorycode(0);
    			LSnotification.setRepositorydatacode(0);
    			LSnotification.setNotificationfor(1);
    			lstnotifications.add(LSnotification);	
    	   }
    	}
    	 
    	 
    	if((notobj.getIscompleted() == null || notobj.getIscompleted() == false) && 
    			(cancel == 0) && (approvelstatus != 3)){
    	
    		LSuserMaster LSuserMaster = new LSuserMaster();
    		LSuserMaster.setUsercode(objNotification.getUsercode());
    		
    		
    				if(objNotification.getOverduestatus() == 1) {
    					//if(indexoverdueorders.getOverduestatus() == 1) {
    						
    					    LSnotification LSnotification1 = new LSnotification();
    						LSnotification1.setIsnewnotification(1);
    						LSnotification1.setNotification("ORDEROVERDUEALERT");
    						LSnotification1.setNotificationdate(new Date());
    						LSnotification1.setNotificationdetils(Details);
    						LSnotification1.setNotificationpath(path);
    						LSnotification1.setNotifationfrom(LSuserMaster);
    						LSnotification1.setNotifationto(LSuserMaster);
    						LSnotification1.setRepositorycode(0);
    						LSnotification1.setRepositorydatacode(0);
    						LSnotification1.setNotificationfor(1);
    						
    						objNotification.setIsduedateexhausted(true);
    						objNotification.setOverduestatus(0);
    						
    						ordernotifylist.add(objNotification);
    						lstnotifications.add(LSnotification1);				
    				        }
    				
    	
    		lsnotificationrepo.saveAll(lstnotifications);
    		lsordernotificationrepo.saveAll(ordernotifylist);
    		//notifyoverduedays(objNotification);
    	}

    }
  
    public void insernotification(String Details,String notification,String path,int usercode,Date cDate) throws InterruptedException {
    	
    	LSuserMaster LSuserMaster = new LSuserMaster();
		LSuserMaster.setUsercode(usercode);
		   	
    	LSnotification LSnotification = new LSnotification();
    	
    	LSnotification.setIsnewnotification(1);
		LSnotification.setNotification(notification);
		LSnotification.setNotificationdate(new Date());
		LSnotification.setNotificationdetils(Details);
		LSnotification.setNotificationpath(path);
		LSnotification.setNotifationfrom(LSuserMaster);
		LSnotification.setNotifationto(LSuserMaster);
		LSnotification.setRepositorycode(0);
		LSnotification.setRepositorydatacode(0);
		LSnotification.setNotificationfor(1);
		lsnotificationrepo.save(LSnotification);
    
}

    public void checkAndScheduleautoOrderRegister() throws Exception {
    	
        //String checkrepeat = "SELECT * FROM LSlogilablimsorderdetail WHERE repeat = true and autoregistercount > 0";
       	List<LSlogilablimsorderdetail> orderobj = lslogilablimsorderdetailrepo.findByRepeatAndAutoregistercountGreaterThan(true,0);
      	
       	orderobj.stream().peek(orderobjindex->{
    		try {
    			checkinsheetrange(orderobjindex); 
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}).collect(Collectors.toList());
         
    }
    
    private void checkinsheetrange(LSlogilablimsorderdetail orderobj) throws Exception {
    	gettofromdate();
    	//getCurrentUTCDate();

    	List<LsAutoregister> autoobj = lsautorepo.findByBatchcodeAndScreen(orderobj.getBatchcode(), "Sheet_Order");
   		// String autoregquery = "SELECT * FROM Lsautoregister WHERE batchcode=? and screen=?";

           orderobj.setLsautoregisterorders(autoobj.get(0));
                       
           Date Autoregdate = autoobj.get(0).getAutocreatedate();
           Instant auto = Autoregdate.toInstant();
           LocalDateTime autoTime = LocalDateTime.ofInstant(auto, ZoneId.systemDefault());
             	        
           Instant fromdate = getfromDate.toInstant();
           LocalDateTime fromdateTime = LocalDateTime.ofInstant(fromdate, ZoneId.systemDefault());
             	        
           Instant todate = gettoDate.toInstant();
           LocalDateTime todateTime = LocalDateTime.ofInstant(todate, ZoneId.systemDefault());
             	
           if(autoTime.isAfter(fromdateTime) && autoTime.isBefore(todateTime)) {
                scheduleAutoRegisteration(orderobj,autoTime);
           }	 
               
      } 
    
private void scheduleAutoRegisteration(LSlogilablimsorderdetail orderobj,LocalDateTime autoTime) throws SQLException, IOException, ParseException {
    	
        LSuserMaster userobj = usermasterrepo.findByusercode(orderobj.getLsuserMaster().getUsercode());
            
             //  String lsuserquery = "SELECT * FROM Lsusermaster WHERE usercode=? ";
              
        orderobj.setLsuserMaster(userobj);

    	if(orderobj.getLsautoregisterorders() != null) { 
	        LocalDateTime currentTime = LocalDateTime.now();
	
	        if (autoTime.isAfter(currentTime)) {
	            Duration duration = Duration.between(currentTime, autoTime);
	            long delay = duration.toMillis();
	            scheduleForAutoRegOrders(orderobj, delay);
	        }else {
	       
	        	try {
					ExecuteAutoRegistration(orderobj);
				} catch (ParseException e) {
					
					e.printStackTrace();
				} catch (SQLException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
	        }
    	}
    }
    
 private void scheduleForAutoRegOrders(LSlogilablimsorderdetail orderobj, long delay) {
	
	int batchcode = orderobj.getBatchcode().intValue();

	if (scheduledTasks.containsKey(batchcode)) {
        System.out.println("Task already scheduled for batch ID: " + batchcode);
        return;
    }
	
	if((orderobj.getRepeat()!=null || orderobj.getRepeat() != false) && orderobj.getLsautoregisterorders()!=null) {
		TimerTask task = new TimerTask() {
            
			@Override
            public void run() {
                try {
                	ExecuteAutoRegistration(orderobj);
                }  catch (SQLException | ParseException | IOException e) {
					
					e.printStackTrace();
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}finally {
					scheduledTasks.remove(batchcode);
				}
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, delay);
        scheduledTasks.put(batchcode, task);
	}
}

 public void ExecuteAutoRegistration(LSlogilablimsorderdetail objorder)throws ParseException, SQLException, IOException, InterruptedException {
 	if((objorder.getRepeat()!=null || objorder.getRepeat() != false) && objorder.getLsautoregisterorders()!=null) {
 		Date currentdate = commonfunction.getCurrentUtcTime();
 		LSlogilablimsorderdetail objorder1 = null;
 		
 		List<LSlogilablimsorderdetail> oldrecord = lslogilablimsorderdetailrepo.findBybatchcode(objorder.getBatchcode());
 		objorder1=oldrecord.get(0);

 		objorder1.setRepeat(false);
// 		lslogilablimsorderdetailrepo.save(objorder1);
 		lslogilablimsorderdetailrepo.updateRepeat(false,objorder1.getBatchcode());
		
		objorder1.setLsuserMaster(objorder.getLsuserMaster());
		objorder1.setLsautoregisterorders(objorder.getLsautoregisterorders());
        	    
	    	
	    if(objorder1.getLsautoregisterorders()!= null) {	
			LsAutoregister auditregdetails =  getautoregisterdetails(objorder1.getLsautoregisterorders(),"Sheet_Order");
			objorder1.setLsautoregisterorders(auditregdetails);		
		}
		   
	    if (objorder1.getLsfile() != null && objorder1.getLsautoregisterorders()!= null) {
	    			
	    	getlsfiledata(objorder1);
	    	CloudSheetCreation cloudobject=getsheetcreationdata(objorder1);
	    		
	        updatesheetordercontent(objorder1,cloudobject,currentdate);
	    
	        objorder1.setBatchcode(null);
	        objorder1.setBatchid(null);
	        objorder1.setOrderflag("N");
	        Integer autoregistercount = objorder1.getAutoregistercount() - 1;
		    objorder1.setRepeat(autoregistercount == 0 ? false : true);
		    objorder1.setSequenceid(objorder1.getLsautoregisterorders().getSequenceFormat());
		    objorder1.setAutoregistercount(autoregistercount);
		   			
		    lslogilablimsorderdetailrepo.save(objorder1);
		    
		    String Batchid = "ELN" + objorder1.getBatchcode();
		    if (objorder1.getFiletype() == 3) {
		       	Batchid = "RESEARCH" + objorder1.getBatchcode();
		    } else if (objorder1.getFiletype() == 4) {
		       	Batchid = "EXCEL" + objorder1.getBatchcode();
		    } else if (objorder1.getFiletype() == 5) {
		       	Batchid = "VALIDATE" + objorder1.getBatchcode();
		    }
		    SequenceTableProjectLevel objprojectseq = new SequenceTableProjectLevel();
			SequenceTableTaskLevel objtaskseq = new SequenceTableTaskLevel();
			SequenceTable seqorder = instrumentService.validateandupdatesheetordersequencenumber(objorder1, objprojectseq,
					objtaskseq);
			boolean isrest = false;
			seqorder = commonfunction.ResetSequence(seqorder, isrest);
		    SequenceTable sqa = sequencetableRepository.findBySequencecodeOrderBySequencecode(1);
			String seqid = instrumentService.GetAutoSequences(objorder1, sqa, objprojectseq, objtaskseq);
			seqid = sqa.getSequenceview().equals(1) ? Batchid : seqid;
			instrumentService.updatesequence(1, objorder1);
			objorder1.setSequenceid(seqid);
		    objorder1.setBatchid(Batchid);
		    objorder1.getLsautoregisterorders().setBatchcode(objorder1.getBatchcode());
		    lsautorepo.save(objorder1.getLsautoregisterorders());
		    
		    lslogilablimsorderdetailrepo.sevaluesBybatchcode(seqid, Batchid,
					autoregistercount == 0 ? false : true, autoregistercount, objorder1.getBatchcode(),
							objorder1.getLssamplefile().getFilesamplecode());
		    //lslogilablimsorderdetailrepo.save(objorder1);
		    
		    lssamplefileRepo.setbatchcodeOnsamplefile(objorder1.getBatchcode(),
					objorder1.getLssamplefile().getFilesamplecode());
		       		
		    if(objorder1.getRepeat() == true && objorder1.getLsautoregisterorders()!=null) {
		            	   
		        Date Autoregdate = objorder1.getLsautoregisterorders().getAutocreatedate();
		        Instant auto = Autoregdate.toInstant();
		        LocalDateTime autoTime = LocalDateTime.ofInstant(auto, ZoneId.systemDefault());
		        scheduleAutoRegisteration(objorder1,autoTime);
		            	    
		        Autoregdate=null;
		        auto=null;
		        autoTime=null;
		    }
		            
				String comments = "order: "+objorder1.getBatchcode()+" is now autoregistered";
				String screen="IDS_SCN_SHEETORDERS";
				int sitecode=1;
				int usercode = objorder1.getLsuserMaster().getUsercode();
				insertaudit(comments,screen,sitecode,usercode,currentdate);
						
				comments="";
				screen="";
			
	    		}
	    	
 	   }
 }
 public void insertaudit(String comments,String screen,int site,int usercode,Date currentdate) throws SQLException, InterruptedException {
 	
	 String systemcomments = "Audittrail.Audittrailhistory.Audittype.IDS_AUDIT_SYSTEMGENERATED";         
	 LScfttransaction auditobj = new LScfttransaction();
	 
	 auditobj.setModuleName(screen);
	 auditobj.setActions("IDS_TSK_REGISTERED");
	 auditobj.setManipulatetype("IDS_AUDIT_INSERTORDERS");
	 auditobj.setTransactiondate(currentdate);
	 auditobj.setComments(comments);
	 auditobj.setLssitemaster(site);
	 auditobj.setSystemcoments(systemcomments);
	 auditobj.setLsuserMaster(usercode);
	 
     systemcomments="";
     lscfttransactionrepo.save(auditobj);
     
 }
 public LsAutoregister getautoregisterdetails (LsAutoregister lsautoregister,String screen) throws SQLException, InterruptedException, ParseException {

	    Date currentdate = commonfunction.getCurrentUtcTime();
	    LsAutoregister autoobj = new LsAutoregister();
	    
	    if(lsautoregister.getTimespan().equals("Days")) {
				
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentdate);
			calendar.add(Calendar.DAY_OF_MONTH, lsautoregister.getInterval());
			Date futureDate = calendar.getTime();  
			autoobj.setAutocreatedate(futureDate);
			   
		}else if(lsautoregister.getTimespan().equals("Week")) {
	
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentdate);
			calendar.add(Calendar.DAY_OF_MONTH, (lsautoregister.getInterval()*7));
			Date futureDate = calendar.getTime();   
			autoobj.setAutocreatedate(futureDate);
		}else {
				
		    Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentdate);
			calendar.add(Calendar.HOUR_OF_DAY,(lsautoregister.getInterval()));
			Date futureDate = calendar.getTime();   
			autoobj.setAutocreatedate(futureDate);
			        
//				    Calendar calendar = Calendar.getInstance();
//			        calendar.setTime(currentdate);
//			       // calendar.add(Calendar.HOUR_OF_DAY,(autoorder.get(0).getInterval()));
//			        calendar.add(Calendar.MINUTE , (4));
//			        Date futureDate = calendar.getTime();   
//			        autoobj.setAutocreatedate(futureDate);
			 }
			
			autoobj.setScreen(screen);
			autoobj.setIsautoreg(true);
			autoobj.setInterval(lsautoregister.getInterval());
			autoobj.setTimespan(lsautoregister.getTimespan());
			autoobj.setIsmultitenant(lsautoregister.getIsmultitenant());
			autoobj.setRepeat(true);
			autoobj.setRegcode(null);
//			autoobj.setTimerIdname(lsautoregister.getTimerIdname());
			autoobj.setSequenceFormat(lsautoregister.getSequenceFormat());
			//autoobj.setDelayinminutes(lsautoregister.getDelayinminutes());
//			Calendar autocalendar = Calendar.getInstance();
//	        autocalendar.setTime(autoobj.getAutocreatedate());
//	        Date autocreateddate = autocalendar.getTime(); 
	        
//	        String autoregtablequery = "Insert into lsautoregister (autocreatedate,interval,isautoreg,"
//				+ "ismultitenant,repeat,screen,timespan) Values (?,?,?,?,?,?,?)";
	
			List<LsAutoregister> autoreg = new ArrayList<LsAutoregister>();
			autoreg.add(lsautorepo.save(autoobj));
	        
		return autoreg.get(0);
	//}
}

 public void getlsfiledata(LSlogilablimsorderdetail objorder) throws SQLException {
 	
 	List<LSfile> fileobj = lsfilerepo.findByFilecodeOrderByFilecodeDesc(objorder.getLsfile().getFilecode());
 	objorder.setLsfile(fileobj.get(0));

 } 

 public CloudSheetCreation getsheetcreationdata(LSlogilablimsorderdetail objorder  )throws SQLException {
 	
 	CloudSheetCreation objCreation = cloudSheetCreationRepository.findTop1ById((long)objorder.getLsfile().getFilecode());
 
 	return objCreation;
 }
 
 @SuppressWarnings("resource")
public void updatesheetordercontent(LSlogilablimsorderdetail objorder,CloudSheetCreation cloudobject,Date currentdate) throws IOException, SQLException, ParseException {
 	
			String Content = "";
	    	 if ((objorder.getLsfile().getApproved() != null && objorder.getLsfile().getApproved() == 1)
						|| (objorder.getFiletype() == 5)) {
	         	 if (objorder.getLsautoregisterorders().getIsmultitenant() == 1 || objorder.getLsautoregisterorders().getIsmultitenant() == 2) {
	
						if (cloudobject != null) {
							if (cloudobject.getContainerstored() == 0) {
								Content = cloudobject.getContent();
							} else {
								Content = objCloudFileManipulationservice.retrieveCloudSheets(cloudobject.getFileuid(),
										commonfunction.getcontainername(objorder.getLsautoregisterorders().getIsmultitenant(), commonfunction.getcontainername(objorder.getLsautoregisterorders().getIsmultitenant(),
												TenantContext.getCurrentTenant()))
										 + "sheetcreation");
							}
						} else {
							Content = objorder.getLsfile().getFilecontent();
						}
					} 
	         	 else 
					{
	
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
							if (mongoTemplate.findById(objorder.getLsfile().getFilecode(), SheetCreation.class) != null) {
								Content = mongoTemplate.findById(objorder.getLsfile().getFilecode(), SheetCreation.class)
										.getContent();
							} else {
								Content = objorder.getLsfile().getFilecontent();
							}
						}
	
					}
	         	 
	          }else {
					if (objorder.getFiletype() != 4 && objorder.getLsfile().getFilecode() != 1) {
						Integer lastapprovedvesrion = objorder.getLsfile().getVersionno() > 1
								? (objorder.getLsfile().getVersionno() - 1)
								: objorder.getLsfile().getVersionno();
						objorder.getLsfile().setVersionno(lastapprovedvesrion);
						objorder.getLsfile().setIsmultitenant(objorder.getIsmultitenant());
						objorder.getLsfile().setIsmultitenant(objorder.getIsmultitenant());
						//Content = fileService.GetfileverContent(objorder.getLsfile());
					}
				}
	    	 if (Content == null || Content.equals("")) {
					Content = defaultContent;
				}
	    	 
	    	 //if (objorder.getLssamplefile().getLssamplefileversion() != null) {

	 			String Contentversion = Content;
	 			
	 			LSsamplefileversion versobj = new LSsamplefileversion();
	 			versobj.setCreatedate(currentdate);
	 			versobj.setVersionname("version_1");
	 			versobj.setVersionno(1);
	 			versobj.setCreatebyuser(objorder.getLsuserMaster());
	 			
		 			LSuserMaster obj = new LSuserMaster();
		 			obj.setCreateddate(currentdate);
//	 			versobj.setCreatebyuser(obj);
	 			
	 			List<LSsamplefileversion> sampleobj = new ArrayList<LSsamplefileversion>();
	 			sampleobj.add(lssamplefileversionrepo.save(versobj));
	 			
//	 			String insertversion = "INSERT INTO LSsamplefileversion(createdate,versionno,versionname,createbyuser_usercode,batchcode)"
//	 					+ " VALUES (?,1,'version_1',?,0) ";   
	 			
	 			
//	 			try (PreparedStatement pst = con.prepareStatement(insertversion, Statement.RETURN_GENERATED_KEYS)) {
//	                pst.setTimestamp(1, new Timestamp(currentdate.getTime()));
//	                pst.setInt(2, objorder.getLsuserMaster().getUsercode());
//	                
//	                int affectedRows=pst.executeUpdate();
//	                   
//	                   if (affectedRows > 0) {
//	                	   ResultSet rs = pst.getGeneratedKeys();
//	                       if (rs.next()) {
//	                           int sampleversioncode = rs.getInt(1);
//	                           System.out.println("Inserted record's sampleversioncode: " + sampleversioncode);
//	                           versobj.setFilesamplecodeversion(sampleversioncode);
//	                       }
//	                   } else {
//	                       System.out.println("No record inserted.");
//	                   }
//	 			}
//	 			insertversion="";
	 			
	 			updateorderversioncontent(Contentversion, versobj,
	 					objorder.getLsautoregisterorders().getIsmultitenant());

	 			Contentversion = null;
	 		//}
	 			
	 			List<LSsamplefileversion> versionlist = new ArrayList<>();
	 			versionlist.add(versobj);
	    	 
	    	 
	    		 LSsamplefile sampobj = new LSsamplefile();
	    		 sampobj.setCreatedate(currentdate);
	    		 sampobj.setVersionno(1);
	    		 sampobj.setCreatebyuser(objorder.getLsuserMaster());
	    		 sampobj.setBatchcode(0);
	    		 sampobj.setProcessed(0);
	    		 sampobj.setLssamplefileversion(versionlist);
	    		 
	    		 
	 			 List<LSsamplefile> samplefileobj = new ArrayList<LSsamplefile>();
	 			 samplefileobj.add( lssamplefileRepo.save(sampobj));
	 			 objorder.setLssamplefile(samplefileobj.get(0));
	 			
//	    		 String insertsample = "INSERT INTO LSsamplefile(createdate,versionno,createbyuser_usercode,batchcode,processed)"
//		 					+ " VALUES (?,1,?,0,0) ";   
//		 			try (PreparedStatement pst = con.prepareStatement(insertsample, Statement.RETURN_GENERATED_KEYS)) {
//		                pst.setTimestamp(1, new Timestamp(currentdate.getTime()));
//		                pst.setInt(2, objorder.getLsuserMaster().getUsercode());
//		                
//		                int affectedRows=pst.executeUpdate();
//		                   
//		                   if (affectedRows > 0) {
//		                	   ResultSet rs = pst.getGeneratedKeys();
//		                       if (rs.next()) {
//		                           int filesamplecode = rs.getInt(1);
//		                           System.out.println("Inserted record's filesamplecode: " + filesamplecode);
//		                           sampobj.setFilesamplecode(filesamplecode);
//		                       }
//		                   } else {
//		                       System.out.println("No record inserted.");
//		                   }
//		 			}
		 			
		 			//objorder.setLssamplefile(sampobj);		
		 			
		 			if (objorder.getLssamplefile() != null) {
			       		updateordercontent(Content, objorder.getLssamplefile(), objorder.getLsautoregisterorders().getIsmultitenant());
			        }
 	}
 
 public void updateorderversioncontent(String Content, LSsamplefileversion objfile, Integer ismultitenant)
			throws IOException, SQLException {
	 	
	 	if (ismultitenant == 1 || ismultitenant == 2) {
	 		
			Map<String, Object> objMap = objCloudFileManipulationservice.storecloudSheetsreturnwithpreUUID(Content,
					commonfunction.getcontainername(ismultitenant, TenantContext.getCurrentTenant()) + "orderversion");
			String fileUUID = (String) objMap.get("uuid");
			String fileURI = objMap.get("uri").toString();

			CloudOrderVersion objsavefile = new CloudOrderVersion();
			if (objfile.getFilesamplecodeversion() != null) {
				objsavefile.setId((long) objfile.getFilesamplecodeversion());
			} else {
				objsavefile.setId(1);
			}
//			objsavefile.setContent(Content);
			objsavefile.setFileuri(fileURI);
			objsavefile.setFileuid(fileUUID);
			objsavefile.setContainerstored(1);

			cloudorderversionrepo.save(objsavefile);

			objsavefile = null;
		} else {

			GridFSFile largefile = gridFsTemplate.findOne(
					new Query(Criteria.where("filename").is("orderversion_" + objfile.getFilesamplecodeversion())));
			if (largefile != null) {
				gridFsTemplate.delete(
						new Query(Criteria.where("filename").is("orderversion_" + objfile.getFilesamplecodeversion())));
			}
			String contentType = "text/plain; charset=UTF-8";
			gridFsTemplate.store(new ByteArrayInputStream(Content.getBytes(StandardCharsets.UTF_8)),
					"orderversion_" + objfile.getFilesamplecodeversion(), contentType);

		}
	 }
 public void updateordercontent(String Content, LSsamplefile objfile, Integer ismultitenant) throws IOException {

		String contentParams = "";
		String contentValues = "";

		Map<String, Object> objContent = commonfunction.getParamsAndValues(Content);

		contentValues = (String) objContent.get("values");
		contentParams = (String) objContent.get("parameters");

		if (ismultitenant == 1 || ismultitenant == 2) {

			Map<String, Object> objMap = objCloudFileManipulationservice.storecloudSheetsreturnwithpreUUID(Content,
					commonfunction.getcontainername(ismultitenant, TenantContext.getCurrentTenant()) + "ordercreation");
			String fileUUID = (String) objMap.get("uuid");
			String fileURI = objMap.get("uri").toString();

			CloudOrderCreation objsavefile = new CloudOrderCreation();
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
			String contentType = "text/plain; charset=UTF-8";
			gridFsTemplate.store(new ByteArrayInputStream(Content.getBytes(StandardCharsets.UTF_8)),
					"order_" + objfile.getFilesamplecode(), contentType);

			objsavefile = null;
		}

		contentParams = null;
		contentValues = null;
		objContent = null;
	}

 public void checkAndScheduleProtocolautoRegister() throws Exception {
 	
	 List<LSlogilabprotocoldetail> orderobj = lslogilabprotocoldetailrepo.findByRepeatAndAutoregistercountGreaterThan(true,0);
	 orderobj.stream().peek(orderobjindex->{
 		try {
 			checkinprotorange(orderobjindex); 
			} catch (Exception e) {
				e.printStackTrace();
			}
 	}).collect(Collectors.toList());
//	 checkinprotorange(orderobj);
    
 }
 
 private void checkinprotorange(LSlogilabprotocoldetail orderobj) throws Exception {
 	
 	gettofromdate();
 	//getCurrentUTCDate();
 	
 		 List<LsAutoregister> autoobj = lsautorepo.findByBatchcodeAndScreen(orderobj.getProtocolordercode(), "Protocol_Order");
 		 
            orderobj.setLsautoregisterorder(autoobj.get(0));
            orderobj.setLsautoregister(autoobj.get(0));      
                  	 
            Date Autoregdate = autoobj.get(0).getAutocreatedate();
         	Instant auto = Autoregdate.toInstant();
         	LocalDateTime autoTime = LocalDateTime.ofInstant(auto, ZoneId.systemDefault());
         	        
         	Instant fromdate = getfromDate.toInstant();
         	LocalDateTime fromdateTime = LocalDateTime.ofInstant(fromdate, ZoneId.systemDefault());
         	        
         	Instant todate = gettoDate.toInstant();
         	LocalDateTime todateTime = LocalDateTime.ofInstant(todate, ZoneId.systemDefault());
         	
            if(autoTime.isAfter(fromdateTime) && autoTime.isBefore(todateTime)) {
            	;
                 scheduleProtocolAutoRegisteration(orderobj,autoTime);
            }
                  
            Autoregdate=null;
		    auto=null;
		    autoTime=null;
       }    
          
 private void scheduleProtocolAutoRegisteration(LSlogilabprotocoldetail orderobj,LocalDateTime autoTime) throws SQLException, IOException {	 
 	
	  //LSprotocolmaster protocolmasterobj = null;

      List<LSprotocolmaster> protomaster = lsprotocolmasterrepo.findByProtocolmastercode(orderobj.getLsprotocolmaster().getProtocolmastercode());

          
      orderobj.setLsprotocolmaster(protomaster.get(0));
 
	  if(orderobj.getLsautoregister() != null) { 

       LocalDateTime currentTime = LocalDateTime.now();

       if (autoTime.isAfter(currentTime)) {
           Duration duration = Duration.between(currentTime, autoTime);
           long delay = duration.toMillis();
           scheduleForProtocolAutoRegOrders(orderobj, delay);
       }else {
       	
       	try {
				ExecuteProtocolAutoRegistration(orderobj);
			} catch (ParseException e) {
				
				e.printStackTrace();
			} catch (SQLException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
       
       }
	}
}    
 	
 private void scheduleForProtocolAutoRegOrders(LSlogilabprotocoldetail orderobj, long delay) {
 	
 	int protocolordercode = orderobj.getProtocolordercode().intValue();

 	if (scheduledTasks.containsKey(protocolordercode)) {
         System.out.println("Task already scheduled for protocolordercode: " + protocolordercode);
         return;
     }
 	
 	if((orderobj.getRepeat()!=null || orderobj.getRepeat() != false) && orderobj.getLsautoregister()!=null) {
 		TimerTask task = new TimerTask() {
	            
				@Override
	            public void run() {
	                try {
	                	ExecuteProtocolAutoRegistration(orderobj);
	                }  catch (SQLException | ParseException | IOException e) {
						
						e.printStackTrace();
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}
	                finally {
	                scheduledTasks.remove(protocolordercode);
					}
	            }
	        };
	        Timer timer = new Timer();
	        timer.schedule(task, delay);
	        scheduledTasks.put(protocolordercode, task);
 	}

 }
 
 @SuppressWarnings("resource")
public void ExecuteProtocolAutoRegistration(LSlogilabprotocoldetail lSlogilabprotocoldetail)throws ParseException, SQLException, IOException, InterruptedException {
	
  if (lSlogilabprotocoldetail.getAutoregistercount()!=null && lSlogilabprotocoldetail.getAutoregistercount() > 0) {
	  
	 Date currentdate = commonfunction.getCurrentUtcTime();
	 LSlogilabprotocoldetail lSlogilabprotocoldetail1 = lslogilabprotocoldetailrepo.findByProtocolordercode(lSlogilabprotocoldetail.getProtocolordercode());
	 Integer autoregistercount=lSlogilabprotocoldetail1.getAutoregistercount()-1;
	 lSlogilabprotocoldetail1.setLsprotocolmaster(lSlogilabprotocoldetail.getLsprotocolmaster());
   	 lSlogilabprotocoldetail1.setLsautoregister(lSlogilabprotocoldetail.getLsautoregister());
   	 
   	 lSlogilabprotocoldetail1.setRepeat(false);
//   	 lslogilabprotocoldetailrepo.save(lSlogilabprotocoldetail1);
   	lslogilabprotocoldetailrepo.updateRepeat(false,lSlogilabprotocoldetail1.getProtocolordercode());
   	 
	List<LsAutoregister> autoorder = lsautorepo.findByBatchcodeAndScreen(lSlogilabprotocoldetail1.getProtocolordercode(),lSlogilabprotocoldetail1.getLsautoregister().getScreen());
	Integer Ismultitenant = autoorder.get(0).getIsmultitenant();
         
   	if(lSlogilabprotocoldetail1.getLsautoregister()!= null) {
		LsAutoregister auditregdetails =  getautoregisterdetails(lSlogilabprotocoldetail1.getLsautoregister(),"Protocol_Order");
		lSlogilabprotocoldetail1.setLsautoregister(auditregdetails);	
	}
   	 
   	lSlogilabprotocoldetail1.setCreatedtimestamp(currentdate);
   	
   	if (lSlogilabprotocoldetail1 != null) {
		lSlogilabprotocoldetail1.setVersionno(0);

		if (lSlogilabprotocoldetail1.getProtocoltype() == 2
				&& lSlogilabprotocoldetail1.getLsprotocolmaster().getProtocolmastercode() == -2) {
			LSprotocolmaster lsprotocolmasterobj = LSProtocolMasterRepositoryObj.findByDefaulttemplate(1);
			if (lsprotocolmasterobj == null) {
				LSprotocolmaster lsprotocolmaster = new LSprotocolmaster();
				lsprotocolmaster.setProtocolmastername("Default Protocol");
				lsprotocolmaster.setStatus(0);
				lsprotocolmaster.setCreatedby(lSlogilabprotocoldetail1.getCreateby());
				lsprotocolmaster.setCreatedate(lSlogilabprotocoldetail1.getCreatedtimestamp());
				lsprotocolmaster.setLssitemaster(lSlogilabprotocoldetail1.getSitecode());
				LSProtocolMasterRepositoryObj.save(lsprotocolmaster);
				lSlogilabprotocoldetail1.setLsprotocolmaster(lsprotocolmaster);
			} else {
				lSlogilabprotocoldetail1.setLsprotocolmaster(lsprotocolmasterobj);
			}

		}

//		if(lSlogilabprotocoldetail1.getOrdercancell() != null) {
//			lSlogilabprotocoldetail1.setOrdercancell(null);
//		}
		 
		lSlogilabprotocoldetail1.setProtoclordername(null);
		lSlogilabprotocoldetail1.setProtocolordercode(null);
         
		lSlogilabprotocoldetail1.setRepeat(autoregistercount == 0 ? false : true);
		lSlogilabprotocoldetail1.setAutoregistercount(autoregistercount);
		lSlogilabprotocoldetail1.setSequenceid(lSlogilabprotocoldetail.getSequenceid());
		lslogilabprotocoldetailrepo.save(lSlogilabprotocoldetail1);

		
		String Content = "";
		String Defaultcontent = "{\"protocolname\":\"\",\"AI\":{\"value\":{\"data\":[]}},\"abstract\":{\"value\":\"\"},\"matrices\":{\"value\":\"\"},\"material\":{\"value\":\"\"},\"sections\":[],\"result\":{}}";

		if (lSlogilabprotocoldetail1.getProtocolordercode() != null) {

			String ProtocolOrderName = "ELN" + lSlogilabprotocoldetail1.getProtocolordercode();

			lSlogilabprotocoldetail1.setProtoclordername(ProtocolOrderName);

			lSlogilabprotocoldetail1.setOrderflag("N");

//			List<LSprotocolstep> lstSteps = LSProtocolStepRepositoryObj.findByProtocolmastercodeAndStatus(
//					lSlogilabprotocoldetail1.getLsprotocolmaster().getProtocolmastercode(), 1);

//			List<LSlogilabprotocolsteps> lststep1 = new ObjectMapper().convertValue(lstSteps,
//					new TypeReference<List<LSlogilabprotocolsteps>>() {
//					});
			List<CloudLsLogilabprotocolstepInfo> objinfo = new ArrayList<CloudLsLogilabprotocolstepInfo>();
			List<LsLogilabprotocolstepInfo> objmongoinfo = new ArrayList<LsLogilabprotocolstepInfo>();
			
				LSprotocolmaster lsprotocolmasterobj = LSProtocolMasterRepositoryObj.findByprotocolmastercode(
						lSlogilabprotocoldetail1.getLsprotocolmaster().getProtocolmastercode());
				if (Ismultitenant == 1
						|| Ismultitenant == 2) {
					if (lsprotocolmasterobj.getContainerstored() == null
							&& lSlogilabprotocoldetail1.getContent() != null
							&& !lSlogilabprotocoldetail1.getContent().isEmpty()) {
					
						try {
							JSONObject protocolJson = new JSONObject(lSlogilabprotocoldetail1.getContent());
							protocolJson.put("protocolname", lSlogilabprotocoldetail1.getProtoclordername());
							updateProtocolOrderContent(protocolJson.toString(), lSlogilabprotocoldetail1,
									lSlogilabprotocoldetail1.getIsmultitenant());
						} catch (IOException e) {
							
							e.printStackTrace();
						}
					} else {
						try {
							Content = objCloudFileManipulationservice.retrieveCloudSheets(
									lsprotocolmasterobj.getFileuid(),
									commonfunction.getcontainername(Ismultitenant,
											TenantContext.getCurrentTenant()) + "protocol");
							JSONObject protocolJson = new JSONObject(Content);
							protocolJson.put("protocolname", lSlogilabprotocoldetail1.getProtoclordername());
							updateProtocolOrderContent(protocolJson.toString(), lSlogilabprotocoldetail1,
									Ismultitenant);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				} else {


					GridFSFile data = gridFsTemplate.findOne(new Query(Criteria.where("filename")
							.is("protocol_" + lsprotocolmasterobj.getProtocolmastercode())));
					if (data == null) {
						Content = Defaultcontent;
					}
					
					if (data == null && lSlogilabprotocoldetail.getContent() != null
							&& !lSlogilabprotocoldetail.getContent().isEmpty()) {
						JSONObject protocolJson = new JSONObject(lSlogilabprotocoldetail.getContent());
						protocolJson.put("protocolname", lSlogilabprotocoldetail.getProtoclordername());
						Content = protocolJson.toString();
					} else if (data != null) {
						GridFsResource resource = gridFsTemplate.getResource(data.getFilename());
						Content = new BufferedReader(
								new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))
								.lines().collect(Collectors.joining("\n"));
					}

					if (gridFsTemplate.findOne(new Query(Criteria.where("filename").is(
							"protocolorder_" + lSlogilabprotocoldetail1.getProtocolordercode()))) == null) {
						try {
							ByteArrayInputStream inputStream = new ByteArrayInputStream(
									Content.getBytes(StandardCharsets.UTF_8));
							String contentType = "text/plain; charset=UTF-8";
							gridFsTemplate.store(inputStream,
									"protocolorder_" + lSlogilabprotocoldetail1.getProtocolordercode(),
									contentType);

						} catch (Exception e) {
							System.out.println(
									"error protocoldata lslogilabprotocoldetail content update mongodb"
											+ lSlogilabprotocoldetail.getProtocolordercode());
						}
					}
				
				
				}
				
			
			if (objinfo.size() != 0) {
				lSlogilabprotocoldetail1.setCloudLsLogilabprotocolstepInfo(objinfo);
			} else if (objmongoinfo.size() != 0) {
				lSlogilabprotocoldetail1.setLsLogilabprotocolstepInfo(objmongoinfo);
			}

			List<LSprotocolsampleupdates> lstsamplelst = LSprotocolsampleupdatesRepository.findByProtocolmastercode(
					lSlogilabprotocoldetail1.getLsprotocolmaster().getProtocolmastercode());

			List<LSprotocolordersampleupdates> protocolordersample = new ObjectMapper().convertValue(lstsamplelst,
					new TypeReference<List<LSprotocolordersampleupdates>>() {
					});

			for (LSprotocolordersampleupdates samplelist : protocolordersample) {

				samplelist.setProtocolordercode(lSlogilabprotocoldetail1.getProtocolordercode());
				lsprotocolordersampleupdatesRepository.save(samplelist);
			}

			LSSiteMaster site = LSSiteMasterRepository.findBysitecode(lSlogilabprotocoldetail1.getSitecode());

			lSlogilabprotocoldetail1.setElnprotocolworkflow(
					elnprotocolworkflowRepository.findTopByAndLssitemasterOrderByWorkflowcodeAsc(site));

			
			lSlogilabprotocoldetail1.setLstelnprotocolworkflow(lSlogilabprotocoldetail1.getLstelnprotocolworkflow());
	
			lSlogilabprotocoldetail1.setOrdercancell(null);
			SequenceTableProjectLevel objprojectseq = new SequenceTableProjectLevel();
			SequenceTableTaskLevel objtaskseq = new SequenceTableTaskLevel();
			SequenceTable seqorder = protocolService.validateandupdatesheetordersequencenumber(lSlogilabprotocoldetail1, objprojectseq,
					objtaskseq);
			boolean isrest = false;
			seqorder = commonfunction.ResetSequence(seqorder, isrest);
			SequenceTable sqa = sequencetableRepository.findBySequencecodeOrderBySequencecode(2);
			String seqid = protocolService.GetAutoSequences(lSlogilabprotocoldetail1, sqa, objprojectseq, objtaskseq);
			lSlogilabprotocoldetail1.setRepeat(autoregistercount == 0 ? false : true);
			lSlogilabprotocoldetail1.setAutoregistercount(autoregistercount);

			seqid = sqa.getSequenceview().equals(1) ? lSlogilabprotocoldetail1.getProtoclordername()
					: seqid;
			lslogilabprotocoldetailrepo.sevaluesByProtocolordercode(seqid,
					autoregistercount == 0 ? false : true, autoregistercount,
					lSlogilabprotocoldetail1.getProtoclordername(),
					lSlogilabprotocoldetail1.getProtocolordercode());
			
//			lslogilabprotocoldetailrepo.save(lSlogilabprotocoldetail1);
			
			if(lSlogilabprotocoldetail1.getLsautoregister()!= null) {
				lSlogilabprotocoldetail1.getLsautoregister().setBatchcode(lSlogilabprotocoldetail1.getProtocolordercode());
				lsautorepo.save(lSlogilabprotocoldetail1.getLsautoregister());

//				lSlogilabprotocoldetail1.setRepeat(autoregistercount==0?false:true);
//				lSlogilabprotocoldetail1.setAutoregistercount(autoregistercount);
//				lslogilabprotocoldetailrepo.save(lSlogilabprotocoldetail1);
				
				Instant auto = lSlogilabprotocoldetail1.getLsautoregister().getAutocreatedate().toInstant();
		        LocalDateTime autoTime = LocalDateTime.ofInstant(auto, ZoneId.systemDefault());
				if(autoregistercount>0 && lSlogilabprotocoldetail1.getRepeat()) {
				  scheduleProtocolAutoRegisteration(lSlogilabprotocoldetail1 ,autoTime);
				}
			}
			
			
		}	
	}
	
  }
 
 }		 
 
 public void updateProtocolOrderContent(String Content, LSlogilabprotocoldetail objOrder, Integer ismultitenant)
			throws IOException {

		if (ismultitenant == 1 || ismultitenant == 2) {

			Map<String, Object> objMap = objCloudFileManipulationservice.storecloudSheetsreturnwithpreUUID(Content,
					commonfunction.getcontainername(ismultitenant, TenantContext.getCurrentTenant()) + "protocolorder");
			String fileUUID = (String) objMap.get("uuid");
			String fileURI = objMap.get("uri").toString();

			objOrder.setFileuri(fileURI);
			objOrder.setFileuid(fileUUID);
			objOrder.setContainerstored(1);
			lslogilabprotocoldetailrepo.save(objOrder);

		} else {

		}
	}
}




