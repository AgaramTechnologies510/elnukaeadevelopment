package com.agaram.eln.primary.controller.socket;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.agaram.eln.primary.service.socket.SocketService;
import com.agaram.eln.primary.socketmodel.ProtocolEnableDetails;

@Controller
public class SocketController {
	
	@Autowired
    private SocketService socketservice;
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@MessageMapping("/enableprotocolsocket")
	public void protocolenable(ProtocolEnableDetails message) throws Exception {
	    ProtocolEnableDetails result = socketservice.protocolenable(message);
	    messagingTemplate.convertAndSend(message.getSendid(), result);
	}

//	@MessageMapping("/enableprotocolsocket")
//	@SendTo("/notebook/protocolenable")
//	public ProtocolEnableDetails protocolenable(ProtocolEnableDetails message) throws Exception {
//	    return socketservice.protocolenable(message);
//	}
	
	@MessageMapping("/disableprotocolsocket")
	public void protocoldisable(ProtocolEnableDetails message) throws Exception {
	    ProtocolEnableDetails result = socketservice.protocoldisable(message);
	    messagingTemplate.convertAndSend(message.getSendid(), result);
	}
	
	@MessageMapping("/updatesheetsocket")
	@SendTo("/notebook/updatesheetdata")
	public ProtocolEnableDetails updatesheetdata(ProtocolEnableDetails message) throws Exception { 
	    return socketservice.updatesheetdata(message);
	}
	
//	@MessageMapping("/disableprotocolsocket")
//	@SendTo("/notebook/protocoldisable")
//	public ProtocolEnableDetails protocoldisable(ProtocolEnableDetails message) throws Exception { 
//	    return socketservice.protocoldisable(message);
//	}
	
	@MessageMapping("/validateeditorsocket")
	@SendTo("/notebook/validateeditor")
	public List<ProtocolEnableDetails> Validateeditor(String[] lsteditorids)throws Exception { 
	    return socketservice.Validateeditor(lsteditorids);
	}
	
	@MessageMapping("/validateheadersocket")
	@SendTo("/notebook/validateheader")
	public List<ProtocolEnableDetails> validateheader(String[] lsteditorids)throws Exception { 
	    return socketservice.validateheader(lsteditorids);
	}
	
	@MessageMapping("/validatefieldssocket")
	@SendTo("/notebook/validatefields")
	public List<ProtocolEnableDetails> validatefields(String[] lsteditorids)throws Exception { 
	    return socketservice.validatefields(lsteditorids);
	}
	
	@MessageMapping("/enableeditornamesocket")
	@SendTo("/notebook/enableeditorname")
	public ProtocolEnableDetails enableeditorname(ProtocolEnableDetails message) throws Exception {
		return socketservice.enableeditorname(message);
	}
	
	@MessageMapping("/updateeditornamesocket")
	@SendTo("/notebook/updateeditorname")
	public ProtocolEnableDetails updateeditorname(ProtocolEnableDetails message) throws Exception {
	    return socketservice.updateeditorname(message);
	}
	
	@MessageMapping("/Validateeditornamessocket")
	@SendTo("/notebook/Validateeditornames")
	public List<ProtocolEnableDetails> Validateeditornames(String[] lsteditorids)throws Exception { 
	    return socketservice.Validateeditornames(lsteditorids);
	}
	
	@MessageMapping("/removealltabidssocket")
	@SendTo("/notebook/removealltabids")
	public ProtocolEnableDetails removealltabids(ProtocolEnableDetails message) throws Exception {
	    return socketservice.removealltabids(message);
	}
	
	@MessageMapping("/changeprotocolfieldsocket")
//	@SendTo("/notebook/changeprotocolfield")
	public void changeprotocolfield(ProtocolEnableDetails message) throws Exception { 
		ProtocolEnableDetails result = socketservice.changeprotocolfield(message);
	    messagingTemplate.convertAndSend(message.getSendid(), result);
	}
	
	@MessageMapping("/enablefieldsocket")
//	@SendTo("/notebook/enablefield")
	public void enablefield(ProtocolEnableDetails message) throws Exception { 
		ProtocolEnableDetails result = socketservice.enablefield(message);
	    messagingTemplate.convertAndSend(message.getSendid(), result);
	}
	
	@MessageMapping("/addeditorsocket")
	@SendTo("/notebook/addeditor")
	public ProtocolEnableDetails addeditor(ProtocolEnableDetails message) throws Exception { 
	    return socketservice.addeditor(message);
	}
	
	@MessageMapping("/addeditoronindexsocket")
	@SendTo("/notebook/addeditoronindex")
	public ProtocolEnableDetails addeditoronindex(ProtocolEnableDetails message) throws Exception { 
	    return socketservice.addeditoronindex(message);
	}
	
	@MessageMapping("/editordeletesocket")
	@SendTo("/notebook/editordelete")
	public ProtocolEnableDetails editordelete(ProtocolEnableDetails message) throws Exception { 
	    return socketservice.editordelete(message);
	}
	
	@MessageMapping("/updateprotocolsocket")
	@SendTo("/notebook/updateprotocol")
	public ProtocolEnableDetails updateprotocol(ProtocolEnableDetails message) throws Exception { 
	    return socketservice.updateprotocol(message);
	}
	
	@MessageMapping("/addstepsocket")
	@SendTo("/notebook/addstep")
	public ProtocolEnableDetails addstep(ProtocolEnableDetails message) throws Exception { 
	    return socketservice.addstep(message);
	}
	
	@MessageMapping("/addsteponindexsocket")
	@SendTo("/notebook/addsteponindex")
	public ProtocolEnableDetails addsteponindex(ProtocolEnableDetails message) throws Exception { 
	    return socketservice.addsteponindex(message);
	}
	
	@MessageMapping("/addsectionsocket")
	@SendTo("/notebook/addsection")
	public ProtocolEnableDetails addsection(ProtocolEnableDetails message) throws Exception { 
	    return socketservice.addsection(message);
	}
	
	@MessageMapping("/addsectiononindexsocket")
	@SendTo("/notebook/addsectiononindex")
	public ProtocolEnableDetails addsectiononindex(ProtocolEnableDetails message) throws Exception { 
	    return socketservice.addsectiononindex(message);
	}
	
	@MessageMapping("/stepdeletesocket")
	@SendTo("/notebook/stepdelete")
	public ProtocolEnableDetails stepdelete(ProtocolEnableDetails message) throws Exception { 
	    return socketservice.stepdelete(message);
	}
	
	@MessageMapping("/sectiondeletesocket")
	@SendTo("/notebook/sectiondelete")
	public ProtocolEnableDetails sectiondelete(ProtocolEnableDetails message) throws Exception { 
	    return socketservice.sectiondelete(message);
	}
	
	@MessageMapping("/updatestepnamesocket")
	@SendTo("/notebook/updatestepname")
	public ProtocolEnableDetails updatestepname(ProtocolEnableDetails message) throws Exception {
	    return socketservice.updatestepname(message);
	}
	
	@MessageMapping("/enablestepnamesocket")
	@SendTo("/notebook/enablestepname")
	public ProtocolEnableDetails enablestepname(ProtocolEnableDetails message) throws Exception {
		return socketservice.enablestepname(message);
	}
	
	@MessageMapping("/updatesectionnamesocket")
	@SendTo("/notebook/updatesectionname")
	public ProtocolEnableDetails updatesectionname(ProtocolEnableDetails message) throws Exception {
	    return socketservice.updatesectionname(message);
	}
	
	@MessageMapping("/enablesectionnamesocket")
	@SendTo("/notebook/enablesectionname")
	public ProtocolEnableDetails enablesectionname(ProtocolEnableDetails message) throws Exception {
		return socketservice.enablesectionname(message);
	}
	
	@MessageMapping("/updatepimagesocket")
	@SendTo("/notebook/updateaddedimage")
	public ProtocolEnableDetails updatepimagesocket(ProtocolEnableDetails message) throws Exception { 
	    return socketservice.updatepimagesocket(message);
	}
	
	@MessageMapping("/updatefileocket")
	@SendTo("/notebook/updateaddedfile")
	public ProtocolEnableDetails updateaddedfile(ProtocolEnableDetails message) throws Exception { 
	    return socketservice.updateaddedfile(message);
	}
	
	@MessageMapping("/updatewellplatesocket")
	@SendTo("/notebook/updateaddedwellplate")
	public ProtocolEnableDetails updateaddedwellplate(ProtocolEnableDetails message) throws Exception { 
	    return socketservice.updateaddedwellplate(message);
	}
	
	@MessageMapping("/updatesignaturesocket")
	@SendTo("/notebook/updatesignaturedata")
	public ProtocolEnableDetails updatesignaturedata(ProtocolEnableDetails message) throws Exception { 
	    return socketservice.updatesignaturedata(message);
	}
	
	@MessageMapping("/updatechecklistsocket")
	@SendTo("/notebook/updatechecklist")
	public ProtocolEnableDetails updatechecklist(ProtocolEnableDetails message) throws Exception { 
	    return socketservice.updatechecklist(message);
	}
	
	@MessageMapping("/updatetablesocket")
	@SendTo("/notebook/updatetabledata")
	public ProtocolEnableDetails updatetabledata(ProtocolEnableDetails message) throws Exception { 
	    return socketservice.updatetabledata(message);
	}
	
	@MessageMapping("/updateresourcesocket")
	public void updateresourcedata(ProtocolEnableDetails message) throws Exception { 
		ProtocolEnableDetails result = socketservice.updateresourcedata(message);
	    messagingTemplate.convertAndSend(message.getSendid(), result);
	}
}
