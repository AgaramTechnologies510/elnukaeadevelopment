package com.agaram.eln.primary.service.socket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.agaram.eln.primary.socketmodel.ProtocolEnableDetails;

@Service
public class SocketService {
	
	List<ProtocolEnableDetails> lstprotocoleditors = new ArrayList<ProtocolEnableDetails>();
	List<ProtocolEnableDetails> lstprotocolheaders = new ArrayList<ProtocolEnableDetails>();
	List<ProtocolEnableDetails> lstprotocolfields = new ArrayList<ProtocolEnableDetails>();
	
	public ProtocolEnableDetails protocolenable(ProtocolEnableDetails message)
	{
		lstprotocoleditors = (ArrayList<ProtocolEnableDetails>) lstprotocoleditors.stream().filter(editor -> editor.getEditorid() == message.getEditorid() 
				&& editor.getProtocolcode() == message.getProtocolcode() && editor.getTenantid() == message.getTenantid()).collect(Collectors.toList());
		lstprotocoleditors.add(message);
		return message; 
	}
	
	public ProtocolEnableDetails protocoldisable(ProtocolEnableDetails message)
	{
		lstprotocoleditors = (ArrayList<ProtocolEnableDetails>) lstprotocoleditors.stream().filter(editor -> editor.getEditorid() == message.getEditorid()
				&& editor.getProtocolcode() == message.getProtocolcode() && editor.getTenantid() == message.getTenantid()).collect(Collectors.toList());
		return message; 
	}
	
	public ProtocolEnableDetails updatesheetdata(ProtocolEnableDetails message)
	{
		lstprotocoleditors = (ArrayList<ProtocolEnableDetails>) lstprotocoleditors.stream().filter(editor -> editor.getEditorid() == message.getEditorid()
				&& editor.getProtocolcode() == message.getProtocolcode() && editor.getTenantid() == message.getTenantid()).collect(Collectors.toList());
		return message; 
	}
	
	public List<ProtocolEnableDetails> Validateeditor(String[] lsteditorids)
	{
		return (ArrayList<ProtocolEnableDetails>) lstprotocoleditors.stream().filter(editor -> Arrays.stream(lsteditorids)
                .anyMatch(editor.getEditorid()::equals)).collect(Collectors.toList());
	}
	
	public List<ProtocolEnableDetails> validateheader(String[] lsteditorids)
	{
		return (ArrayList<ProtocolEnableDetails>) lstprotocolheaders.stream().filter(editor -> Arrays.stream(lsteditorids)
                .anyMatch((editor.getEditorid()::equals)) || Arrays.stream(lsteditorids)
                .anyMatch((editor.getStepid()::equals)) || Arrays.stream(lsteditorids)
                .anyMatch((editor.getSectionid()::equals))).collect(Collectors.toList());
	}
	
	public List<ProtocolEnableDetails> validatefields(String[] lstfields)
	{
		return (ArrayList<ProtocolEnableDetails>) lstprotocolfields.stream().filter(editor -> Arrays.stream(lstfields)
                .anyMatch(editor.getFieldkeyid()::equals)).collect(Collectors.toList());
	}
	
	public ProtocolEnableDetails enableeditorname(ProtocolEnableDetails message)
	{
		lstprotocolheaders = (ArrayList<ProtocolEnableDetails>) lstprotocolheaders.stream().filter(editor -> editor.getEditorid() == message.getEditorid()
				&& editor.getProtocolcode() == message.getProtocolcode() && editor.getTenantid() == message.getTenantid()).collect(Collectors.toList());
		lstprotocolheaders.add(message);
		return message;
	}
	
	public ProtocolEnableDetails updateeditorname(ProtocolEnableDetails message)
	{
		lstprotocolheaders = (ArrayList<ProtocolEnableDetails>) lstprotocolheaders.stream().filter(editor -> editor.getEditorid() == message.getEditorid()
				&& editor.getProtocolcode() == message.getProtocolcode() && editor.getTenantid() == message.getTenantid()).collect(Collectors.toList());
		return message;
	}
	
	public List<ProtocolEnableDetails> Validateeditornames(String[] lsteditorids)
	{
		return (ArrayList<ProtocolEnableDetails>) lstprotocolheaders.stream().filter(editor -> Arrays.stream(lsteditorids)
                .anyMatch(editor.getEditorid()::equals)).collect(Collectors.toList());
	}
	
	public ProtocolEnableDetails removealltabids(ProtocolEnableDetails message)
	{
		lstprotocoleditors = (ArrayList<ProtocolEnableDetails>) lstprotocoleditors.stream().filter(editor -> editor.getTabid() == message.getTabid()).collect(Collectors.toList());
		lstprotocolheaders = (ArrayList<ProtocolEnableDetails>) lstprotocolheaders.stream().filter(editor -> editor.getTabid() == message.getTabid()).collect(Collectors.toList());
		return message; 
	}
	
	public ProtocolEnableDetails changeprotocolfield(ProtocolEnableDetails message)
	{
		lstprotocolfields = (ArrayList<ProtocolEnableDetails>) lstprotocolfields.stream().filter(editor -> editor.getFieldkeyid() == message.getFieldkeyid()
				&& editor.getProtocolcode() == message.getProtocolcode() && editor.getTenantid() == message.getTenantid()).collect(Collectors.toList());
		return message; 
	}
	
	public ProtocolEnableDetails enablefield(ProtocolEnableDetails message)
	{
		lstprotocolfields = (ArrayList<ProtocolEnableDetails>) lstprotocolfields.stream().filter(editor -> editor.getFieldkeyid() == message.getFieldkeyid()
				&& editor.getProtocolcode() == message.getProtocolcode() && editor.getTenantid() == message.getTenantid()).collect(Collectors.toList());
		lstprotocolfields.add(message);
		return message;
	}
	
	public ProtocolEnableDetails addeditor(ProtocolEnableDetails message)
	{
		return message;
	}
	
	public ProtocolEnableDetails addeditoronindex(ProtocolEnableDetails message)
	{
		return message;
	}
	
	public ProtocolEnableDetails editordelete(ProtocolEnableDetails message)
	{
		return message;
	}
	
	public ProtocolEnableDetails updateprotocol(ProtocolEnableDetails message)
	{
		return message;
	}
	
	public ProtocolEnableDetails addstep(ProtocolEnableDetails message)
	{
		return message;
	}
	
	public ProtocolEnableDetails addsteponindex(ProtocolEnableDetails message)
	{
		return message;
	}
	
	public ProtocolEnableDetails addsection(ProtocolEnableDetails message)
	{
		return message;
	}
	
	public ProtocolEnableDetails addsectiononindex(ProtocolEnableDetails message)
	{
		return message;
	}
	
	public ProtocolEnableDetails stepdelete(ProtocolEnableDetails message)
	{
		return message;
	}
	
	public ProtocolEnableDetails sectiondelete(ProtocolEnableDetails message)
	{
		return message;
	}
	
	public ProtocolEnableDetails updatestepname(ProtocolEnableDetails message)
	{
		lstprotocolheaders = (ArrayList<ProtocolEnableDetails>) lstprotocolheaders.stream().filter(editor -> editor.getStepid() == message.getStepid()
				&& editor.getProtocolcode() == message.getProtocolcode() && editor.getTenantid() == message.getTenantid()).collect(Collectors.toList());
		return message;
	}
	
	public ProtocolEnableDetails enablestepname(ProtocolEnableDetails message)
	{
		lstprotocolheaders = (ArrayList<ProtocolEnableDetails>) lstprotocolheaders.stream().filter(editor -> editor.getStepid() == message.getStepid()
				&& editor.getProtocolcode() == message.getProtocolcode() && editor.getTenantid() == message.getTenantid()).collect(Collectors.toList());
		lstprotocolheaders.add(message);
		return message;
	}
	
	public ProtocolEnableDetails updatesectionname(ProtocolEnableDetails message)
	{
		lstprotocolheaders = (ArrayList<ProtocolEnableDetails>) lstprotocolheaders.stream().filter(editor -> editor.getSectionid() == message.getSectionid()
				&& editor.getProtocolcode() == message.getProtocolcode() && editor.getTenantid() == message.getTenantid()).collect(Collectors.toList());
		return message;
	}
	
	public ProtocolEnableDetails enablesectionname(ProtocolEnableDetails message)
	{
		lstprotocolheaders = (ArrayList<ProtocolEnableDetails>) lstprotocolheaders.stream().filter(editor -> editor.getSectionid() == message.getSectionid()
				&& editor.getProtocolcode() == message.getProtocolcode() && editor.getTenantid() == message.getTenantid()).collect(Collectors.toList());
		lstprotocolheaders.add(message);
		return message;
	}

	public ProtocolEnableDetails updatepimagesocket(ProtocolEnableDetails message) {
		return message;
	}

	public ProtocolEnableDetails updateaddedfile(ProtocolEnableDetails message) {
		return message;
	}

	public ProtocolEnableDetails updateaddedwellplate(ProtocolEnableDetails message) {
		return message;
	}
	
	public ProtocolEnableDetails updatesignaturedata(ProtocolEnableDetails message)
	{
		lstprotocoleditors = (ArrayList<ProtocolEnableDetails>) lstprotocoleditors.stream().filter(editor -> editor.getEditorid() == message.getEditorid()
				&& editor.getProtocolcode() == message.getProtocolcode() && editor.getTenantid() == message.getTenantid()).collect(Collectors.toList());
		return message; 
	}
	
	public ProtocolEnableDetails updatechecklist(ProtocolEnableDetails message)
	{
		lstprotocoleditors = (ArrayList<ProtocolEnableDetails>) lstprotocoleditors.stream().filter(editor -> editor.getEditorid() == message.getEditorid()
				&& editor.getProtocolcode() == message.getProtocolcode() && editor.getTenantid() == message.getTenantid()).collect(Collectors.toList());
		return message; 
	}
	
	public ProtocolEnableDetails updatetabledata(ProtocolEnableDetails message)
	{
		lstprotocoleditors = (ArrayList<ProtocolEnableDetails>) lstprotocoleditors.stream().filter(editor -> editor.getEditorid() == message.getEditorid()
				&& editor.getProtocolcode() == message.getProtocolcode() && editor.getTenantid() == message.getTenantid()).collect(Collectors.toList());
		return message; 
	}
	
	public ProtocolEnableDetails updateresourcedata(ProtocolEnableDetails message) {
		return message;
	}
}
