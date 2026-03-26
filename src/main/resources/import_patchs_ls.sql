ALTER TABLE LSsamplefileversion ALTER COLUMN filecontent TYPE text;

update lsusermaster set lockcount = 0 where lockcount = 5 and userstatus != 'Locked';

update lsusermaster set loginfrom = '0' where loginfrom IS NULL;

INSERT into LSusergrouprightsmaster (orderno, displaytopic, modulename, sallow, screate, sdelete,sedit, status,sequenceorder)VALUES (46, 'Pending', 'Protocol Order And Register', '0', '0', 'NA', 'NA', '1,0,0',46) ON CONFLICT(orderno)DO NOTHING;

INSERT into LSusergrouprightsmaster (orderno, displaytopic, modulename, sallow, screate, sdelete, sedit, status,sequenceorder)VALUES (47, 'Completed', 'Protocol Order And Register', '0', '0', 'NA', 'NA', '1,0,0',47) ON CONFLICT(orderno)DO NOTHING;

INSERT into LSusergrouprightsmaster (orderno, displaytopic, modulename, sallow, screate, sdelete, sedit, status,sequenceorder)VALUES (48, 'ELN Protocol Order', 'Protocol Order And Register', '0', '0', 'NA', 'NA', '1,0,0',48) ON CONFLICT(orderno)DO NOTHING;

INSERT into LSusergrouprightsmaster (orderno, displaytopic, modulename, sallow, screate, sdelete, sedit, status,sequenceorder)VALUES (49, 'Dynamic Protocol Order', 'Protocol Order And Register', '0', '0', 'NA', 'NA', '1,0,0',49) ON CONFLICT(orderno)DO NOTHING;

INSERT into LSusergrouprightsmaster (orderno, displaytopic, modulename, sallow, screate, sdelete, sedit, status,sequenceorder)VALUES (50, 'New', 'Protocols', '0', '0', 'NA', 'NA', '1,0,0',50) ON CONFLICT(orderno)DO NOTHING;

update lsusergrouprights set sedit=1 where lsusergrouprights.displaytopic = 'User Group' and lsusergrouprights.sedit='NA';

update lsusergrouprightsmaster set sedit=1 where lsusergrouprightsmaster.orderno=17 and lsusergrouprightsmaster.sedit='NA';

update lssheetworkflow set lssitemaster_sitecode =1 where lssheetworkflow.workflowname='New' and lssheetworkflow.lssitemaster_sitecode is null;

UPDATE lsworkflow set lssitemaster_sitecode=1 where lsworkflow.workflowname = 'New' and lsworkflow.lssitemaster_sitecode is null;

UPDATE  lsusermaster SET userretirestatus=0 WHERE userretirestatus is null;

--update LSusergrouprightsmaster set sallow='1',screate='1',sdelete='1',sedit='1' where sallow='0' or screate='0' or sdelete='0' or sedit='0';
update LSusergrouprightsmaster set sallow='0' where sallow='1';
update LSusergrouprightsmaster set screate='0' where  screate='1';
update LSusergrouprightsmaster set sdelete='0' where sdelete='1';
update LSusergrouprightsmaster set sedit='0' where sedit='1';

update LSfileversion set modifiedby_usercode= (select  modifiedby_usercode from LSfileversion where modifiedby_usercode is not null and modifieddate is not null order by modifieddate asc LIMIT  1 ), modifieddate= (select createdate from LSfileversion where modifiedby_usercode is not null and modifieddate is not null order by modifieddate asc LIMIT  1 ) where modifiedby_usercode is null or modifieddate is  null and versionname='version_1';

--UPDATE LSprotocolmaster SET createby_usercode = createdby where createby_usercode is null;
ALTER TABLE IF Exists lsprotocolmaster DROP COLUMN IF Exists createby_usercode;
ALTER TABLE IF Exists LSprotocolmaster ADD COLUMN IF NOT EXISTS versionno integer;
update LSprotocolmaster set versionno=0 where versionno is null;


INSERT into LSfields (fieldcode, createby, createdate, fieldorderno, fieldtypecode, isactive, level01code, level01name, level02code, level02name, level03code, level03name, level04code, level04name, siteID) VALUES (64, NULL, NULL, 24, 3, 1, 'G1', 'ID_GENERAL', '24', 'ID_GENERAL', 24, 'ID_GENERAL', 'G24', 'Software Name', 1) on conflict (fieldcode) do nothing;

INSERT into LSfields (fieldcode, createby, createdate, fieldorderno, fieldtypecode, isactive, level01code, level01name, level02code, level02name, level03code, level03name, level04code, level04name, siteID) VALUES (63, NULL, NULL, 24, 3, 1, 'G1', 'ID_GENERAL', '25', 'ID_GENERAL', 25, 'ID_GENERAL', 'G25', 'Version Number', 1) on conflict (fieldcode) do nothing;


INSERT into LSfields (fieldcode, createby, createdate, fieldorderno, fieldtypecode, isactive, level01code, level01name, level02code, level02name, level03code, level03name, level04code, level04name, siteID) VALUES (57, NULL, NULL, 18, 3, 1, 'G1', 'ID_GENERAL', '18', 'ID_GENERAL', 18, 'ID_GENERAL', 'G18', 'Resource Detail', 1) on conflict (fieldcode) do nothing;
INSERT into LSfields (fieldcode, createby, createdate, fieldorderno, fieldtypecode, isactive, level01code, level01name, level02code, level02name, level03code, level03name, level04code, level04name, siteID) VALUES (58, NULL, NULL, 19, 3, 1, 'G1', 'ID_GENERAL', '19', 'ID_GENERAL', 19, 'ID_GENERAL', 'G19', 'Notification', 1) on conflict (fieldcode) do nothing;
INSERT into LSfields (fieldcode, createby, createdate, fieldorderno, fieldtypecode, isactive, level01code, level01name, level02code, level02name, level03code, level03name, level04code, level04name, siteID) VALUES (59, NULL, NULL, 19, 3, 1, 'G1', 'ID_GENERAL', '20', 'ID_GENERAL', 20, 'ID_GENERAL', 'G20', 'Multiselect combobox', 1) on conflict (fieldcode) do nothing;

update LSaudittrailconfigmaster set ordersequnce=3 where screenname= 'Register Orders & Execute' and taskname='Save';

update LSaudittrailconfigmaster set ordersequnce=4 where screenname= 'Register Orders & Execute' and taskname='Send to Lims';

update LSaudittrailconfigmaster set ordersequnce=5 where screenname= 'Register Orders & Execute' and taskname='Complete task';

update LSaudittrailconfigmaster set ordersequnce=6 where screenname= 'Register Orders & Execute' and taskname='Lock';

update LSaudittrailconfigmaster set ordersequnce=7 where screenname= 'Register Orders & Execute' and taskname='Unlock';

update LSaudittrailconfigmaster set ordersequnce=8 where screenname= 'Sheet Creation' and taskname='Save';

update LSaudittrailconfigmaster set ordersequnce=9 where screenname= 'Sheet Creation' and taskname='Save As';

update LSaudittrailconfigmaster set ordersequnce=10 where screenname = 'Protocol Order And Register' and taskname='Register Protocol';

update LSaudittrailconfigmaster set ordersequnce=11 where screenname = 'Protocol Order And Register' and taskname='Process Protocol';

update LSaudittrailconfigmaster set ordersequnce=12 where screenname = 'Add Protocol' and taskname='Submit';

update LSaudittrailconfigmaster set ordersequnce=13 where screenname = 'New Step' and taskname='Submit';

update LSaudittrailconfigmaster set ordersequnce=14 where screenname = 'Share with Team' and taskname='Submit';

update LSaudittrailconfigmaster set ordersequnce=15 where screenname = 'Delete' and taskname='Submit';

update LSaudittrailconfigmaster set ordersequnce=16 where screenname = 'Protocols' and taskname='Export to PDF';

update LSaudittrailconfigmaster set ordersequnce=17 where screenname= 'Test Master' and taskname='Save';

update LSaudittrailconfigmaster set ordersequnce=18 where screenname= 'Test Master' and taskname='Delete';

update LSaudittrailconfigmaster set ordersequnce=19 where screenname= 'Project Master' and taskname='Save';

update LSaudittrailconfigmaster set ordersequnce=20 where screenname = 'Project Master' and taskname='Delete';

update LSaudittrailconfigmaster set ordersequnce=21 where screenname = 'Sample Master' and taskname='Save';

update LSaudittrailconfigmaster set ordersequnce=22 where screenname = 'Sample Master' and taskname='Delete';

update LSaudittrailconfigmaster set ordersequnce=23 where screenname = 'Instrument Master' and taskname='Save';

update LSaudittrailconfigmaster set ordersequnce=24 where screenname = 'Instrument Master' and taskname='Delete';

update LSaudittrailconfigmaster set ordersequnce=25 where screenname = 'Sheet Setting' and taskname='Save';

update LSaudittrailconfigmaster set ordersequnce=26 where screenname = 'Site Master' and taskname='Save';

update LSaudittrailconfigmaster set ordersequnce=27 where screenname = 'Site Master' and taskname='Active/Deactive';

update LSaudittrailconfigmaster set ordersequnce=28 where screenname = 'User Group' and taskname='ADD NEW GROUP';

update LSaudittrailconfigmaster set ordersequnce=29 where screenname = 'User Group' and taskname='Edit';

update LSaudittrailconfigmaster set ordersequnce=30 where screenname = 'User Group' and taskname='Active/Deactive';

update LSaudittrailconfigmaster set ordersequnce=31 where screenname = 'User Master' and taskname='ADD USER';

update LSaudittrailconfigmaster set ordersequnce=32 where screenname = 'User Master' and taskname='Edit';

update LSaudittrailconfigmaster set ordersequnce=33 where screenname = 'User Master' and taskname='Unlock';

update LSaudittrailconfigmaster set ordersequnce=34 where screenname = 'User Master' and taskname='Active/Deactive';

update LSaudittrailconfigmaster set ordersequnce=35 where screenname = 'User Master' and taskname='Reset Password';

update LSaudittrailconfigmaster set ordersequnce=36 where screenname = 'User Master' and taskname='Import ADS';

update LSaudittrailconfigmaster set ordersequnce=37 where screenname = 'User Master' and taskname='Connect';

update LSaudittrailconfigmaster set ordersequnce=38 where screenname = 'User Rights' and taskname='Save';

update LSaudittrailconfigmaster set ordersequnce=39 where screenname = 'Project Team' and taskname='Save';

update LSaudittrailconfigmaster set ordersequnce=40 where screenname = 'Project Team' and taskname='Delete';

update LSaudittrailconfigmaster set ordersequnce=41 where screenname = 'Order Workflow' and taskname='Save';

update LSaudittrailconfigmaster set ordersequnce=42 where screenname = 'Order Workflow' and taskname='Delete';

update LSaudittrailconfigmaster set ordersequnce=43 where screenname = 'Sheet Workflow' and taskname='Save';

update LSaudittrailconfigmaster set ordersequnce=44 where screenname = 'Sheet Workflow' and taskname='Delete';

update LSaudittrailconfigmaster set ordersequnce=45 where screenname = 'Domain' and taskname='Save';

update LSaudittrailconfigmaster set ordersequnce=46 where screenname = 'Domain' and taskname='Delete';

update LSaudittrailconfigmaster set ordersequnce=47 where screenname = 'Password Policy' and taskname='Save';

update LSaudittrailconfigmaster set ordersequnce=48 where screenname = 'Change Password' and taskname='Submit';

update LSaudittrailconfigmaster set ordersequnce=49 where screenname = 'Audit Trail History' and taskname='Review History';

update LSaudittrailconfigmaster set ordersequnce=50 where screenname = 'Audit Trail History' and taskname='Create Archive';

update LSaudittrailconfigmaster set ordersequnce=51 where screenname = 'Audit Trail History' and taskname='Open Archive';

update LSaudittrailconfigmaster set ordersequnce=52 where screenname = 'Audit Trail History' and taskname='Export';

update LSaudittrailconfigmaster set ordersequnce=53 where screenname = 'CFR Settings' and taskname='Save';

update LSaudittrailconfigmaster set ordersequnce=54 where screenname = 'CFR Settings' and taskname='Delete';

update LSaudittrailconfigmaster set ordersequnce=55 where screenname = 'Audit Trial Configuration' and taskname='Save';

update LSaudittrailconfigmaster set ordersequnce=56 where screenname = 'Reports' and taskname='Save';

update LSaudittrailconfigmaster set ordersequnce=57 where screenname = 'Reports' and taskname='Save As';

update LSaudittrailconfigmaster set ordersequnce=58 where screenname = 'Reports' and taskname='Directory Save';

update LSaudittrailconfigmaster set ordersequnce=59 where screenname = 'Reports' and taskname='New Template';

update LSaudittrailconfigmaster set ordersequnce=60 where screenname = 'Reports' and taskname='Generate Report';

update LSaudittrailconfigmaster set ordersequnce=61 where screenname = 'Reports' and taskname='Configure';

update LSaudittrailconfigmaster set screenname='Protocols',taskname='Add Protocol',ordersequnce=12 where serialno=58;
update LSaudittrailconfigmaster set screenname='Protocols',taskname='New Step',ordersequnce=13 where serialno=59;
update LSaudittrailconfigmaster set screenname='Protocols',taskname='Share with Team',ordersequnce=14 where serialno=60;
update LSaudittrailconfigmaster set screenname='Protocols',taskname='Delete',ordersequnce=15 where serialno=61;


update LSaudittrailconfigmaster set ordersequnce=16 where serialno=63;
update LSaudittrailconfigmaster set ordersequnce= 10 where serialno=64;
update LSaudittrailconfigmaster set ordersequnce=11 where serialno=65;

INSERT into parsermethod (parsermethodkey, parsermethodname, parsermethodtype) VALUES (1, N'Datablock', 1) ON CONFLICT(parsermethodkey)DO NOTHING;
INSERT into parsermethod (parsermethodkey, parsermethodname, parsermethodtype) VALUES (2, N'Beginrowmatch', 1) ON CONFLICT(parsermethodkey)DO NOTHING;
INSERT into parsermethod (parsermethodkey, parsermethodname, parsermethodtype) VALUES (3, N'Endrowmatch', 1) ON CONFLICT(parsermethodkey)DO NOTHING;
INSERT into parsermethod (parsermethodkey, parsermethodname, parsermethodtype) VALUES (4, N'Endcolmatch', 1) ON CONFLICT(parsermethodkey)DO NOTHING;
INSERT into parsermethod (parsermethodkey, parsermethodname, parsermethodtype) VALUES (5, N'None', 2) ON CONFLICT(parsermethodkey)DO NOTHING;
INSERT into parsermethod (parsermethodkey, parsermethodname, parsermethodtype) VALUES (6, N'Merge', 2) ON CONFLICT(parsermethodkey)DO NOTHING;
INSERT into parsermethod (parsermethodkey, parsermethodname, parsermethodtype) VALUES (7, N'Split', 2) ON CONFLICT(parsermethodkey)DO NOTHING;
INSERT into parsermethod (parsermethodkey, parsermethodname, parsermethodtype) VALUES (8, N'Shift', 2) ON CONFLICT(parsermethodkey)DO NOTHING;

INSERT into instrumenttype(insttypekey,insttypename,status) VALUES(1,'File',1) ON CONFLICT(insttypekey)DO NOTHING;
INSERT into instrumenttype(insttypekey,insttypename,status) VALUES(2,'RS232',1) ON CONFLICT(insttypekey)DO NOTHING;
INSERT into instrumenttype(insttypekey,insttypename,status) VALUES(3,'TCP/IP',1) ON CONFLICT(insttypekey)DO NOTHING;

ALTER TABLE IF Exists CloudLSprotocolversionstep ADD COLUMN IF NOT EXISTS versionname varchar(100);
ALTER TABLE IF Exists CloudLSprotocolversionstep ADD COLUMN IF NOT EXISTS protocolmastercode integer;

update lsaudittrailconfigmaster set manualaudittrail = 0;

update lsusergrouprightsmaster set modulename='Sheet Templates' where orderno=10;
update lsusergrouprights set modulename='Sheet Templates'  where displaytopic='Sheet Creation';

update lsusergrouprightsmaster set modulename='Protocol Templates' where orderno=50;
update lsusergrouprights set modulename='Protocol Templates' where modulename='Protocols';

INSERT into LSusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder) VALUES (51, 'Protocol Templates', 'Protocol Templates', '0', 'NA', 'NA', 'NA', '0,0,0',51) ON CONFLICT(orderno)DO NOTHING;



update lsusergrouprightsmaster set displaytopic ='Task Master' where displaytopic ='Test Master' and modulename = 'Base Master';

update lsusergrouprights set displaytopic ='Task Master' where displaytopic = 'Test Master' and modulename = 'Base Master';

ALTER TABLE IF Exists Lsrepositories ADD COLUMN IF NOT EXISTS unit varchar(250);
 
INSERT into LSusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder) VALUES (53, 'Parser', 'Parser', '0', 'NA', 'NA', 'NA', '1,0,0',55) ON CONFLICT(orderno)DO NOTHING;

INSERT into LSusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder) VALUES (54, 'Delimiters', 'Parser', '0', '0', '0', '0', '1,1,1',56) ON CONFLICT(orderno)DO NOTHING;
INSERT into LSusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder) VALUES (55, 'Method Delimiters', 'Parser', '0', '0', '0', '0', '1,1,1',57) ON CONFLICT(orderno)DO NOTHING;
INSERT into LSusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder) VALUES (56, 'Method Master', 'Parser', '0', '0', '0', '0', '1,1,1',58) ON CONFLICT(orderno)DO NOTHING;

update lsusergrouprightsmaster set displaytopic ='New Step' where orderno=50;

update lsusergrouprights set displaytopic ='New Step' where modulename='Protocol Templates' and displaytopic ='New';


INSERT into LSusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder) VALUES (57, 'Export to pdf', 'Protocol Templates', '0', '0', 'NA', 'NA', '1,0,0',53) ON CONFLICT(orderno)DO NOTHING;

update lsusergrouprightsmaster set displaytopic ='New Template' where displaytopic='Template Designing' and modulename='Reports';
update lsusergrouprightsmaster set displaytopic ='New Template' where displaytopic='Template Designing' and modulename='Reports';

INSERT into LSusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder) VALUES (58, 'New Document', 'Reports', '0', '0', 'NA', 'NA', '1,0,0',45) ON CONFLICT(orderno)DO NOTHING;

DELETE FROM parserignorechars
WHERE ignorechars NOT IN (
    '--',
    E'\n\n',
    '♀',
    E'\n'
);


--INSERT INTO parserignorechars(parserignorecharskey,ignorechars)SELECT '--' WHERE NOT EXISTS (SELECT ignorechars FROM parserignorechars WHERE ignorechars = '--');

INSERT INTO parserignorechars (parserignorecharskey, ignorechars) SELECT 1, '--' WHERE NOT EXISTS ( SELECT 1 FROM parserignorechars WHERE ignorechars = '--');

INSERT INTO parserignorechars(parserignorecharskey,ignorechars)SELECT 2,'↵↵' WHERE NOT EXISTS (SELECT ignorechars FROM parserignorechars WHERE ignorechars = '↵↵');

INSERT INTO parserignorechars(parserignorecharskey,ignorechars)SELECT 3,'♀' WHERE NOT EXISTS (SELECT ignorechars FROM parserignorechars WHERE ignorechars = '♀');

INSERT INTO parserignorechars(parserignorecharskey , ignorechars)SELECT 4,'↵' WHERE NOT EXISTS (SELECT ignorechars FROM parserignorechars WHERE ignorechars = '↵');



INSERT INTO delimiter (delimitername,actualdelimiter,status,usercode) SELECT 'None', 'None', 1, 1 WHERE NOT EXISTS (SELECT delimitername FROM delimiter WHERE delimitername = 'None');

INSERT INTO delimiter (delimitername,actualdelimiter,status,usercode) SELECT 'Result without space', '[\s]+', 1, 1 WHERE NOT EXISTS (SELECT delimitername FROM delimiter WHERE delimitername = 'Result without space');

INSERT INTO delimiter (delimitername,actualdelimiter,status,usercode) SELECT 'Result with space', '\s\s+', 1, 1 WHERE NOT EXISTS (SELECT delimitername FROM delimiter WHERE delimitername = 'Result with space');

INSERT INTO delimiter (delimitername,actualdelimiter,status,usercode) SELECT 'Colon', ':', 1, 1 WHERE NOT EXISTS (SELECT delimitername FROM delimiter WHERE delimitername = 'Colon');

INSERT INTO delimiter (delimitername,actualdelimiter,status,usercode) SELECT 'Comma', ',', 1, 1 WHERE NOT EXISTS (SELECT delimitername FROM delimiter WHERE delimitername = 'Comma');

INSERT INTO delimiter (delimitername,actualdelimiter,status,usercode) SELECT 'Space', ' ', 1, 1 WHERE NOT EXISTS (SELECT delimitername FROM delimiter WHERE delimitername = 'Space');

INSERT INTO delimiter (delimitername,actualdelimiter,status,usercode) SELECT 'Split Dot', '[.]', 1, 1 WHERE NOT EXISTS (SELECT delimitername FROM delimiter WHERE delimitername = 'Split Dot');

INSERT INTO delimiter (delimitername,actualdelimiter,status,usercode) SELECT 'Merge Dot', '.', 1, 1 WHERE NOT EXISTS (SELECT delimitername FROM delimiter WHERE delimitername = 'Merge Dot');

INSERT INTO delimiter (delimitername,actualdelimiter,status,usercode) SELECT 'Slash', '/', 1, 1 WHERE NOT EXISTS (SELECT delimitername FROM delimiter WHERE delimitername = 'Slash');

update LSusergrouprightsmaster set status='0,0,0' where orderno=53;

update LSusergrouprightsmaster set sedit='NA' where orderno in (54,55,56);

INSERT into LSusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder)VALUES (59, 'Assigned Orders', 'Register Task Orders & Execute', '0', 'NA', 'NA', 'NA', '1,0,0',62) ON CONFLICT(orderno)DO NOTHING;

INSERT into LSusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder) VALUES (60, 'My Orders', 'Register Task Orders & Execute', '0', 'NA', 'NA', 'NA', '1,0,0',63) ON CONFLICT(orderno)DO NOTHING;

INSERT into LSusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder) VALUES (61, 'Orders Shared By Me', 'Register Task Orders & Execute', '0', 'NA', 'NA', 'NA', '1,0,0',64) ON CONFLICT(orderno)DO NOTHING;

INSERT into LSusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder) VALUES (62, 'Orders Shared To Me', 'Register Task Orders & Execute', '0', 'NA', 'NA', 'NA', '1,0,0',65)ON CONFLICT(orderno)DO NOTHING;

delete FROM LSusergrouprights WHERE displaytopic = 'Assigned Orders' and modulename = 'Register Task Orders & Execute' and usergroupid_usergroupcode = 1;
INSERT into LSusergrouprights(displaytopic,modulename,createdby, createdon, sallow, screate, sdelete, sedit, lssitemaster_sitecode, usergroupid_usergroupcode) values ('Assigned Orders', 'Register Task Orders & Execute', 'administrator', CAST('2020-02-21T14:50:55.727' AS date), '1', 'NA', 'NA', 'NA', 1,1);

delete FROM LSusergrouprights WHERE displaytopic = 'My Orders' and modulename = 'Register Task Orders & Execute' and usergroupid_usergroupcode = 1;
INSERT into LSusergrouprights(displaytopic,modulename,createdby, createdon, sallow, screate, sdelete, sedit, lssitemaster_sitecode, usergroupid_usergroupcode) values ('My Orders', 'Register Task Orders & Execute', 'administrator', CAST('2020-02-21T14:50:55.727' AS date), '1', 'NA', 'NA', 'NA', 1,1 );

delete FROM LSusergrouprights WHERE displaytopic = 'Orders Shared By Me' and modulename = 'Register Task Orders & Execute' and usergroupid_usergroupcode = 1;
INSERT into LSusergrouprights (displaytopic, modulename, createdby, createdon, sallow, screate, sdelete, sedit, lssitemaster_sitecode, usergroupid_usergroupcode) SELECT 'Orders Shared By Me', 'Register Task Orders & Execute', 'administrator', CAST('2020-02-21T14:50:55.727' AS date), '1', 'NA', 'NA', 'NA', 1,1 WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_ORDERSHAREDBYME' and usergroupid_usergroupcode = 1) ;

delete FROM LSusergrouprights WHERE displaytopic = 'Orders Shared To Me' and modulename = 'Register Task Orders & Execute' and usergroupid_usergroupcode = 1;
INSERT into LSusergrouprights (displaytopic, modulename, createdby, createdon, sallow, screate, sdelete, sedit, lssitemaster_sitecode, usergroupid_usergroupcode) SELECT 'Orders Shared To Me', 'Register Task Orders & Execute', 'administrator', CAST('2020-02-21T14:50:55.727' AS date), '1', 'NA', 'NA', 'NA', 1,1 WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_ORDERSHAREDTOME' and usergroupid_usergroupcode = 1) ;

delete FROM LSusergrouprights WHERE displaytopic = 'Pending Work' and modulename = 'Register Task Orders & Execute' and usergroupid_usergroupcode = 1;
INSERT into LSusergrouprights (displaytopic, modulename, createdby, createdon, sallow, screate, sdelete, sedit, lssitemaster_sitecode, usergroupid_usergroupcode) SELECT 'Pending Work', 'Register Task Orders & Execute', 'administrator', CAST('2020-02-21T14:50:55.727' AS date), '1', '1', 'NA', 'NA', 1,1 WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_PENDINGWORK' and usergroupid_usergroupcode = 1) ;

delete FROM LSusergrouprights WHERE displaytopic = 'Completed Work' and modulename = 'Register Task Orders & Execute' and usergroupid_usergroupcode = 1;
INSERT into LSusergrouprights (createdby, createdon, displaytopic, modifiedby, modifiedon, modulename, sallow, screate, sdelete, sedit, lssitemaster_sitecode, usergroupid_usergroupcode) SELECT 'administrator', CAST('2020-02-21T14:50:55.727' AS date), 'Completed Work', NULL, NULL, 'Register Task Orders & Execute', '1         ', '1         ', 'NA', 'NA', 1, 1 WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_COMPLETEDWORK' and usergroupid_usergroupcode = 1) ;

delete FROM LSusergrouprights WHERE displaytopic = 'Sheet Evaluation' and modulename = 'Register Task Orders & Execute' and usergroupid_usergroupcode = 1;
INSERT into LSusergrouprights (displaytopic, modulename, createdby, createdon, sallow, screate, sdelete, sedit, lssitemaster_sitecode, usergroupid_usergroupcode) SELECT 'Sheet Evaluation', 'Register Task Orders & Execute', 'administrator', CAST('2020-02-21T14:50:55.727' AS date), '1', '1', 'NA', 'NA', 1,1 WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SHEETEVALUATION' and usergroupid_usergroupcode = 1) ;

delete FROM LSusergrouprights WHERE displaytopic = 'ELN Task Order' and modulename = 'Register Task Orders & Execute' and usergroupid_usergroupcode = 1;
INSERT into LSusergrouprights (createdby, createdon, displaytopic, modifiedby, modifiedon, modulename, sallow, screate, sdelete, sedit, lssitemaster_sitecode, usergroupid_usergroupcode) SELECT 'administrator', CAST('2020-02-21T14:50:55.727' AS date), 'ELN Task Order', NULL, NULL, 'Register Task Orders & Execute', '1         ', '1         ', 'NA', 'NA', 1, 1 WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_ELNTASKORDER' and usergroupid_usergroupcode = 1) ;

delete FROM LSusergrouprights WHERE displaytopic = 'Research Activity Order' and modulename = 'Register Task Orders & Execute' and usergroupid_usergroupcode = 1;
INSERT into LSusergrouprights (displaytopic, modulename, createdby, createdon, sallow, screate, sdelete, sedit, lssitemaster_sitecode, usergroupid_usergroupcode) SELECT 'Research Activity Order', 'Register Task Orders & Execute', 'administrator', CAST('2020-02-21T14:50:55.727' AS date), '1', '1', 'NA', 'NA', 1,1 WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_RESEARCHACTIVITY' and usergroupid_usergroupcode = 1) ;

delete FROM LSusergrouprights WHERE displaytopic = 'Site Master' and modulename = 'UserManagement' and usergroupid_usergroupcode = 1;
INSERT into LSusergrouprights(displaytopic,modulename,createdby, createdon, sallow, screate, sdelete, sedit, lssitemaster_sitecode, usergroupid_usergroupcode) values ( 'Site Master','UserManagement','administrator', CAST('2022-01-21 00:00:00.000' AS date),'1','1','1','1',1,1);

delete FROM LSusergrouprights WHERE displaytopic = 'Retire' and modulename = 'User Master' and usergroupid_usergroupcode = 1;
INSERT into LSusergrouprights (displaytopic, modulename, createdby, createdon, sallow, screate, sdelete, sedit, lssitemaster_sitecode, usergroupid_usergroupcode) SELECT 'Retire','User Master','administrator', CAST('2022-01-21 00:00:00.000' AS date),'1','1','NA','NA',1,1 WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_RETIRE' and usergroupid_usergroupcode = 1) ;

ALTER TABLE IF Exists lslogilablimsorderdetail ADD COLUMN IF NOT EXISTS filecode int;

ALTER TABLE IF Exists NOTIFICATION ADD COLUMN IF NOT EXISTS batchid varchar(250);

delete FROM LSusergrouprights WHERE displaytopic = 'Create Archive' and modulename = 'AuditTrail History' and usergroupid_usergroupcode = 1;
INSERT into LSusergrouprights (createdby, createdon, displaytopic, modifiedby, modifiedon, modulename, sallow, screate, sdelete, sedit, lssitemaster_sitecode, usergroupid_usergroupcode) SELECT 'administrator', CAST('2020-02-21T14:50:55.727' AS date), 'Create Archive', NULL, NULL, 'AuditTrail History', '1         ', 'NA', 'NA', 'NA', 1, 1 WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_CREATEARCHIVE' and usergroupid_usergroupcode = 1) ;

delete FROM LSusergrouprights WHERE displaytopic = 'Review' and modulename = 'AuditTrail History' and usergroupid_usergroupcode = 1;
INSERT into LSusergrouprights (displaytopic, modulename, createdby, createdon, sallow, screate, sdelete, sedit, lssitemaster_sitecode, usergroupid_usergroupcode) SELECT 'Review','AuditTrail History','administrator', CAST('2022-01-21 00:00:00.000' AS date),'1','1','1','1',1,1 WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_REVIEW' and usergroupid_usergroupcode = 1) ;

update lslogilabprotocoldetail set completedtimestamp = current_timestamp where  orderflag='R' and completedtimestamp IS NULL;

update lsusergrouprights set sallow='1',screate='1',sdelete='1',sedit='1' where modulename='Protocol Templates' and displaytopic ='Protocol Templates';

ALTER TABLE IF Exists lsprotocolmaster ADD COLUMN IF NOT EXISTS defaulttemplate Integer;

update lsusergrouprightsmaster set modulename='Parser' where displaytopic='Instrument Master'and modulename='Base Master';

update lsusergrouprights set modulename='Parser' where displaytopic='Instrument Master'and modulename='Base Master';

update lsusergrouprights set sallow='1',screate='1',sdelete='1',sedit='1' where modulename='Protocol Templates' and displaytopic ='Protocol Templates';

update lsusergrouprights set sallow='1',screate='1' where modulename='Protocol Templates' and displaytopic ='New Step' and screate='NA';

update lsusergrouprightsmaster set sallow='0',screate='0',sdelete='0',sedit='0' where modulename = 'Protocol Templates' and displaytopic ='Protocol Templates';

ALTER TABLE IF Exists lslogilablimsorderdetail ADD COLUMN IF NOT EXISTS Keyword varchar(250);

update lsfile set filenameuser = 'Default Template' where filecode = 1;

ALTER TABLE IF Exists LSpreferences ADD COLUMN IF NOT EXISTS valueencrypted varchar(250);

ALTER TABLE IF Exists lslogilablimsorderdetail ADD COLUMN IF NOT EXISTS lockedusername varchar(50);

INSERT into LSusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder) VALUES (65, 'Inventory', 'Base Master', '0', '0', '0', '0', '1,1,1',70) ON CONFLICT(orderno)DO NOTHING;
INSERT into LSusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder) VALUES (66, 'Add repository', 'Inventory', 'NA', '0', 'NA', 'NA', '1,0,0',71) ON CONFLICT(orderno)DO NOTHING;
INSERT into LSusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder) VALUES (67, 'Edit repository', 'Inventory', 'NA', '0', 'NA', 'NA', '1,0,0',71) ON CONFLICT(orderno)DO NOTHING;
INSERT into LSusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder) VALUES (68, 'Instrument Category', 'Parser', '0', '0', '0', '0', '1,1,1',72) ON CONFLICT(orderno)DO NOTHING;

update lsusergrouprightsmaster set sedit = '0' where modulename = 'Parser' and displaytopic != 'Parser';
update lsusergrouprights set sedit = '0' where modulename = 'Parser' and displaytopic != 'Parser' and sedit='NA';

update lsusergrouprightsmaster set modulename = 'Templates' where modulename ='Protocol Templates';
update lsusergrouprightsmaster set modulename = 'Templates' where modulename ='Sheet Templates';
update lsusergrouprights set modulename = 'Templates' where modulename ='Protocol Templates';
update lsusergrouprights set modulename = 'Templates' where modulename ='Sheet Templates';

update lsusergrouprights set sallow = '0' where modulename= 'Sheet Settings' and displaytopic = 'LIMS Test  Order';
update lsusergrouprightsmaster set sallow = '0' where modulename= 'Sheet Settings' and displaytopic = 'LIMS Test  Order';

update lsusergrouprightsmaster set displaytopic = 'Sheet Templates' where displaytopic = 'Sheet Creation';
update lsusergrouprights set displaytopic = 'Sheet Templates' where displaytopic = 'Sheet Creation';

ALTER TABLE lsnotification ALTER COLUMN CreatedTimeStamp TYPE timestamp without time zone;

INSERT into LSusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder) VALUES (69, 'Templates Shared By Me', 'Templates', '0', 'NA', 'NA', 'NA', '0,0,0',73) ON CONFLICT(orderno)DO NOTHING;
INSERT into LSusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder) VALUES (70, 'Templates Shared To Me', 'Templates', '0', 'NA', 'NA', 'NA', '0,0,0',74) ON CONFLICT(orderno)DO NOTHING;

INSERT into LSusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder)VALUES (71, 'Sheet', 'Sheet Settings', '0', 'NA', 'NA', 'NA', '0,0,0',75) ON CONFLICT(orderno)DO NOTHING;
INSERT into LSusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder)VALUES (72, 'Protocol', 'Sheet Settings', '0', 'NA', 'NA', 'NA', '0,0,0',76) ON CONFLICT(orderno)DO NOTHING;

INSERT into LSaudittrailconfigmaster (serialno, manualaudittrail, modulename, ordersequnce, screenname, taskname) VALUES (62,0,'Parser',59,'Delimiter','Save') ON CONFLICT(serialno)DO NOTHING;
INSERT into LSaudittrailconfigmaster (serialno, manualaudittrail, modulename, ordersequnce, screenname, taskname) VALUES (63,0,'Parser',60,'Delimiter','Edit')ON CONFLICT(serialno)DO NOTHING;
INSERT into LSaudittrailconfigmaster (serialno, manualaudittrail, modulename, ordersequnce, screenname, taskname) VALUES (64,0,'Parser',61,'Delimiter','Delete')ON CONFLICT(serialno)DO NOTHING;
INSERT into LSaudittrailconfigmaster (serialno, manualaudittrail, modulename, ordersequnce, screenname, taskname) VALUES (65,0,'Parser',62,'MethodDelimiter','Save')ON CONFLICT(serialno)DO NOTHING;
INSERT into LSaudittrailconfigmaster (serialno, manualaudittrail, modulename, ordersequnce, screenname, taskname) VALUES (66,0,'Parser',63,'MethodDelimiter','Edit')ON CONFLICT(serialno)DO NOTHING;
INSERT into LSaudittrailconfigmaster (serialno, manualaudittrail, modulename, ordersequnce, screenname, taskname) VALUES (67,0,'Parser',64,'MethodDelimiter','Delete')ON CONFLICT(serialno)DO NOTHING;
INSERT into LSaudittrailconfigmaster (serialno, manualaudittrail, modulename, ordersequnce, screenname, taskname) VALUES (68,0,'Parser',65,'MethodMaster','Save')ON CONFLICT(serialno)DO NOTHING;
INSERT into LSaudittrailconfigmaster (serialno, manualaudittrail, modulename, ordersequnce, screenname, taskname) VALUES (69,0,'Parser',66,'MethodMaster','Edit')ON CONFLICT(serialno)DO NOTHING;
INSERT into LSaudittrailconfigmaster (serialno, manualaudittrail, modulename, ordersequnce, screenname, taskname) VALUES (70,0,'Parser',67,'MethodMaster','Delete')ON CONFLICT(serialno)DO NOTHING;

insert into lsaudittrailconfigmaster (serialno, manualaudittrail, modulename, ordersequnce, screenname, taskname) values(71,0,'Base Master',68,'Repository','Save')ON CONFLICT(serialno)DO NOTHING;
insert into lsaudittrailconfigmaster (serialno, manualaudittrail, modulename, ordersequnce, screenname, taskname) values(72,0,'Base Master',69,'Repository','Edit')ON CONFLICT(serialno)DO NOTHING;
insert into lsaudittrailconfigmaster (serialno, manualaudittrail, modulename, ordersequnce, screenname, taskname) values(73,0,'Base Master',70,'Inventory','Save')ON CONFLICT(serialno)DO NOTHING;
insert into lsaudittrailconfigmaster (serialno, manualaudittrail, modulename, ordersequnce, screenname, taskname) values(74,0,'Base Master',71,'Inventory','Edit')ON CONFLICT(serialno)DO NOTHING;
insert into lsaudittrailconfigmaster (serialno, manualaudittrail, modulename, ordersequnce, screenname, taskname)values(75,0,'Base Master',72,'Inventory','Delete')ON CONFLICT(serialno)DO NOTHING;

insert into lsaudittrailconfigmaster (serialno, manualaudittrail, modulename, ordersequnce, screenname, taskname) values(76,0,'Register Orders & Execute',8,'Register Orders & Execute','Parse Data')ON CONFLICT(serialno)DO NOTHING;

update LSusergrouprightsmaster set sallow = '0' where displaytopic = 'Add repository' and sallow= 'NA';
update LSusergrouprightsmaster set sallow = '0' where displaytopic = 'Edit repository' and sallow= 'NA';

update LSusergrouprights set sallow = '0' where displaytopic = 'Add repository' and sallow= 'NA';
update LSusergrouprights set sallow = '0' where displaytopic = 'Edit repository' and sallow= 'NA';

update lsusergrouprightsmaster set displaytopic = 'Audit trail configuration' where displaytopic = 'Audit Trail Configuration';
update lsusergrouprightsmaster set displaytopic = 'Audit trail history' where displaytopic = 'Audit Trail History';

update lsusergrouprights set displaytopic = 'Audit trail configuration' where displaytopic = 'Audit Trail Configuration';
update lsusergrouprights set displaytopic = 'Audit trail history' where displaytopic = 'Audit Trail History';

delete from lsusergrouprightsmaster where modulename = 'Register Task Orders & Execute' and displaytopic in ('Assigned Orders','My Orders');

delete from lsusergrouprights where modulename = 'Register Task Orders & Execute' and displaytopic in ('Assigned Orders','My Orders');

ALTER TABLE IF Exists LSusergrouprights ADD COLUMN IF NOT EXISTS sequenceorder integer;
 
update LSusergrouprightsmaster set sequenceorder = 1 where modulename = 'Dash Board';
update LSusergrouprightsmaster set sequenceorder = 2 where modulename = 'Register Task Orders & Execute';
update LSusergrouprightsmaster set sequenceorder = 3 where modulename = 'Protocol Order And Register';
update LSusergrouprightsmaster set sequenceorder = 4 where modulename = 'Templates';
update LSusergrouprightsmaster set sequenceorder = 5 where modulename = 'Sheet Settings';
update LSusergrouprightsmaster set sequenceorder = 6 where modulename = 'Base Master';
update LSusergrouprightsmaster set sequenceorder = 7 where modulename = 'UserManagement';
update LSusergrouprightsmaster set sequenceorder = 8 where modulename = 'User Group';
update LSusergrouprightsmaster set sequenceorder = 9 where modulename = 'User Master';
update LSusergrouprightsmaster set sequenceorder = 10 where modulename = 'AuditTrail History';
update LSusergrouprightsmaster set sequenceorder = 11 where modulename = 'Reports';
update LSusergrouprightsmaster set sequenceorder = 12 where modulename = 'Parser';
update LSusergrouprightsmaster set sequenceorder = 13 where modulename = 'Inventory';

update LSusergrouprights set sequenceorder = 1 where modulename = 'Dash Board';
update LSusergrouprights set sequenceorder = 2 where modulename = 'Register Task Orders & Execute';
update LSusergrouprights set sequenceorder = 3 where modulename = 'Protocol Order And Register';
update LSusergrouprights set sequenceorder = 4 where modulename = 'Templates';
update LSusergrouprights set sequenceorder = 5 where modulename = 'Sheet Settings';
update LSusergrouprights set sequenceorder = 6 where modulename = 'Base Master';
update LSusergrouprights set sequenceorder = 7 where modulename = 'UserManagement';
update LSusergrouprights set sequenceorder = 8 where modulename = 'User Group';
update LSusergrouprights set sequenceorder = 9 where modulename = 'User Master';
update LSusergrouprights set sequenceorder = 10 where modulename = 'AuditTrail History';
update LSusergrouprights set sequenceorder = 11 where modulename = 'Reports';
update LSusergrouprights set sequenceorder = 12 where modulename = 'Parser';
update LSusergrouprights set sequenceorder = 13 where modulename = 'Inventory';

ALTER TABLE IF Exists LSaudittrailconfiguration ADD COLUMN IF NOT EXISTS ordersequnce integer;

update LSaudittrailconfigmaster set ordersequnce = 1 where modulename = 'Register Orders & Execute';
update LSaudittrailconfigmaster set ordersequnce = 2 where modulename = 'Protocol Order And Register';
update LSaudittrailconfigmaster set ordersequnce = 3 where modulename = 'Sheet Creation';
update LSaudittrailconfigmaster set ordersequnce = 4 where modulename = 'Protocols';
update LSaudittrailconfigmaster set ordersequnce = 5 where modulename = 'Sheet Setting';
update LSaudittrailconfigmaster set ordersequnce = 6 where modulename = 'Base Master';
update LSaudittrailconfigmaster set ordersequnce = 7 where modulename = 'User Management';
update LSaudittrailconfigmaster set ordersequnce = 10 where modulename = 'Audit Trail';
update LSaudittrailconfigmaster set ordersequnce = 11 where modulename = 'Reports';
update LSaudittrailconfigmaster set ordersequnce = 12 where modulename = 'Parser';

update LSaudittrailconfiguration set ordersequnce = 1 where modulename = 'Register Orders & Execute';
update LSaudittrailconfiguration set ordersequnce = 2 where modulename = 'Protocol Order And Register';
update LSaudittrailconfiguration set ordersequnce = 3 where modulename = 'Sheet Creation';
update LSaudittrailconfiguration set ordersequnce = 4 where modulename = 'Protocols';
update LSaudittrailconfiguration set ordersequnce = 5 where modulename = 'Sheet Setting';
update LSaudittrailconfiguration set ordersequnce = 6 where modulename = 'Base Master';
update LSaudittrailconfiguration set ordersequnce = 7 where modulename = 'User Management';
update LSaudittrailconfiguration set ordersequnce = 10 where modulename = 'Audit Trail';
update LSaudittrailconfiguration set ordersequnce = 11 where modulename = 'Reports';
update LSaudittrailconfiguration set ordersequnce = 12 where modulename = 'Parser';

update LSusergrouprightsmaster set displaytopic = 'Pending Work' where displaytopic = 'Pending' and sequenceorder = 3;
update LSusergrouprightsmaster set displaytopic = 'Completed Work' where displaytopic = 'Completed' and sequenceorder = 3;
update LSusergrouprights set displaytopic = 'Pending Work' where displaytopic = 'Pending' and sequenceorder = 3;
update LSusergrouprights set displaytopic = 'Completed Work' where displaytopic = 'Completed' and sequenceorder = 3;

INSERT into LSusergrouprightsmaster (orderno, displaytopic, modulename, sallow, screate, sdelete,sedit, status,sequenceorder) SELECT 80, 'Orders Shared By Me', 'Protocol Order And Register', '0', 'NA', 'NA', '0', '0,0,1',3 WHERE NOT EXISTS (select * from LSusergrouprightsmaster where displaytopic = 'Orders Shared By Me' and modulename = 'Protocol Order And Register') ON CONFLICT(orderno)DO NOTHING;; 

--INSERT into LSusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,sequenceorder) SELECT 'Orders Shared By Me', 'Protocol Order And Register', 'administrator', '1', 'NA', 'NA', '1', 1,1,3  WHERE NOT EXISTS (select * from LSusergrouprights where displaytopic = 'Orders Shared By Me' and modulename = 'Protocol Order And Register' and usergroupid_usergroupcode = 1); 

INSERT into LSusergrouprightsmaster (orderno, displaytopic, modulename, sallow, screate, sdelete,sedit, status,sequenceorder) SELECT 81, 'Orders Shared To Me', 'Protocol Order And Register', '0', 'NA', 'NA', '0', '0,0,1',3 WHERE NOT EXISTS (select * from LSusergrouprightsmaster where displaytopic = 'Orders Shared To Me' and modulename = 'Protocol Order And Register') ON CONFLICT(orderno)DO NOTHING;; 

--INSERT into LSusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,sequenceorder) SELECT 'Orders Shared To Me', 'Protocol Order And Register', 'administrator', '1', 'NA', 'NA', '1', 1,1,3  WHERE NOT EXISTS (select * from LSusergrouprights where displaytopic = 'Orders Shared To Me' and modulename = 'Protocol Order And Register' and usergroupid_usergroupcode = 1);

delete from LSaudittrailconfigmaster where modulename = 'Parser' ; 
update LSusergrouprightsmaster set sedit='0' where displaytopic = 'User Group' and modulename= 'UserManagement';

insert into LSpreferences (serialno,tasksettings,valuesettings) values(4,'ELNparser','1') on conflict(serialno) do nothing;
insert into LSpreferences (serialno,tasksettings,valuesettings) values(1,'WebParser','InActive') on conflict(serialno) do nothing;
insert into LSpreferences (serialno,tasksettings,valuesettings) values(2,'ConCurrentUser','InActive') on conflict(serialno) do nothing;
insert into LSpreferences (serialno,tasksettings,valuesettings) values(3,'MainFormUser','InActive') on conflict(serialno) do nothing;

INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder,screenname) VALUES (96, 'IDS_TSK_SHEETORDEREXPORT', 'IDS_MDL_ORDERS', '0', 'NA', 'NA', 'NA', '0,0,0',15,'IDS_SCN_SHEETORDERS') ON CONFLICT(orderno)DO NOTHING;
INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder,screenname) VALUES (91, 'IDS_TSK_MOVEORDERS', 'IDS_MDL_ORDERS', '0', 'NA', 'NA', 'NA', '1,0,0',14,'IDS_SCN_SHEETORDERS') ON CONFLICT(orderno)DO NOTHING;
INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder,screenname) VALUES (111, 'IDS_TSK_UPLOADPROTOCOLORDER', 'IDS_MDL_ORDERS', '0', 'NA', 'NA', 'NA', '0,0,0',88,'IDS_SCN_PROTOCOLORDERS') ON CONFLICT(orderno)DO NOTHING;
INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder,screenname) VALUES (92, 'IDS_TSK_MOVEORDERSPROTOCOL', 'IDS_MDL_ORDERS', '0', 'NA', 'NA', 'NA', '1,0,0',21,'IDS_SCN_PROTOCOLORDERS') ON CONFLICT(orderno)DO NOTHING;
INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder,screenname) VALUES (88, 'IDS_TSK_SHEETTEMPEXPORT', 'IDS_MDL_TEMPLATES', '0', 'NA', 'NA', 'NA', '0,0,0',22,'IDS_SCN_SHEETTEMPLATE') ON CONFLICT(orderno)DO NOTHING;
INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder) VALUES (84, 'IDS_TSK_PROTOCOLTEMPSHAREBYME', 'IDS_MDL_TEMPLATES', '0', 'NA', 'NA', 'NA', '0,0,1',4) ON CONFLICT(orderno)DO NOTHING;
INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder) VALUES (83, 'IDS_TSK_PROTOCOLTEMPSHARETOME', 'IDS_MDL_TEMPLATES', '0', 'NA', 'NA', 'NA', '0,0,1',4) ON CONFLICT(orderno)DO NOTHING;
INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder) VALUES (95, 'IDS_SCN_UNLOCKORDERS', 'IDS_MDL_MASTERS', '0', 'NA', 'NA', 'NA', '0,0,1',4) ON CONFLICT(orderno)DO NOTHING;
INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder,screenname) VALUES (99, 'IDS_SCN_UNITMASTER', 'IDS_MDL_INVENTORY', '0', '0', '0', '0', '1,1,1',78,'IDS_SCN_UNITMASTER') ON CONFLICT(orderno)DO NOTHING;
INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder,screenname) VALUES (100, 'IDS_TSK_DOWNLOADPDFEXCEL', 'IDS_MDL_INVENTORY', '0', 'NA', 'NA', 'NA', '0,0,0',79,'IDS_SCN_UNITMASTER') ON CONFLICT(orderno)DO NOTHING;
INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder,screenname) VALUES (101, 'IDS_SCN_SECTIONMASTER', 'IDS_MDL_INVENTORY', '0', '0', '0', '0', '1,1,1',80,'IDS_SCN_SECTIONMASTER') ON CONFLICT(orderno)DO NOTHING;
INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder,screenname) VALUES (102, 'IDS_TSK_DOWNLOADPDFEXCELSECTION', 'IDS_MDL_INVENTORY', '0', 'NA', 'NA', 'NA', '0,0,0',81,'IDS_SCN_SECTIONMASTER') ON CONFLICT(orderno)DO NOTHING;
INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder,screenname) VALUES (103, 'IDS_SCN_STORAGELOCATION', 'IDS_MDL_INVENTORY', '0', '0', '0', '0', '1,1,1',82,'IDS_SCN_STORAGELOCATION') ON CONFLICT(orderno)DO NOTHING;
INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder,screenname) VALUES (104, 'IDS_SCN_MATERIALCATEGORY', 'IDS_MDL_INVENTORY', '0', '0', '0', '0', '1,1,1',83,'IDS_SCN_MATERIALCATEGORY') ON CONFLICT(orderno)DO NOTHING;
INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder,screenname) VALUES (105, 'IDS_TSK_DOWNLOADMATERILACATEGORY', 'IDS_MDL_INVENTORY', '0', 'NA', 'NA', 'NA', '0,0,0',84,'IDS_SCN_MATERIALCATEGORY') ON CONFLICT(orderno)DO NOTHING;
INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder,screenname) VALUES (106, 'IDS_SCN_MATERIAL', 'IDS_MDL_INVENTORY', '0', '0', 'NA', 'NA', '1,0,0',85,'IDS_SCN_MATERIAL') ON CONFLICT(orderno)DO NOTHING;
INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder,screenname) VALUES (107, 'IDS_SCN_MATERIALINVENTORY', 'IDS_MDL_INVENTORY', '0', '0', 'NA', 'NA', '1,0,0',86,'IDS_SCN_MATERIALINVENTORY') ON CONFLICT(orderno)DO NOTHING;
INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder,screenname) VALUES (97, 'IDS_TSK_ADDLOGBOOK', 'IDS_MDL_LOGBOOK', '0', 'NA', 'NA', 'NA', '0,0,0',44,'IDS_SCN_LOGBOOK') ON CONFLICT(orderno)DO NOTHING;
INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder,screenname) VALUES (98, 'IDS_TSK_EDITLOGBOOK', 'IDS_MDL_LOGBOOK', '0', 'NA', 'NA', 'NA', '0,0,0',44,'IDS_SCN_LOGBOOK') ON CONFLICT(orderno)DO NOTHING;
INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder,screenname) VALUES (112, 'IDS_SCN_LOGBOOK', 'IDS_MDL_LOGBOOK', '0', 'NA', 'NA', 'NA', '0,0,0',44,'IDS_SCN_LOGBOOK') ON CONFLICT(orderno)DO NOTHING;
INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder,screenname) VALUES (108, 'IDS_TSK_RETIRELOGBOOK', 'IDS_MDL_LOGBOOK', '0', 'NA', 'NA', 'NA', '0,0,0',87,'IDS_SCN_LOGBOOK') ON CONFLICT(orderno)DO NOTHING;
INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder,screenname) VALUES (109, 'IDS_TSK_REVIEWLOGBOOK', 'IDS_MDL_LOGBOOK', '0', 'NA', 'NA', 'NA', '0,0,0',88,'IDS_SCN_LOGBOOK') ON CONFLICT(orderno)DO NOTHING;
INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder,screenname) VALUES (110, 'IDS_TSK_UPLOADSHEETORDER', 'IDS_MDL_ORDERS', '0', 'NA', 'NA', 'NA', '0,0,0',88,'IDS_SCN_SHEETORDERS') ON CONFLICT(orderno)DO NOTHING;
INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder,screenname) VALUES (89, 'IDS_TSK_IMPORTDOCX', 'IDS_MDL_REPORTS', '0', 'NA', 'NA', 'NA', '1,0,0',65,'IDS_SCN_REPORTS') ON CONFLICT(orderno)DO NOTHING;
INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder,screenname) VALUES (90, 'IDS_TSK_OPENREPORT', 'IDS_MDL_REPORTS', '0', 'NA', 'NA', 'NA', '1,0,0',66,'IDS_SCN_REPORTS') ON CONFLICT(orderno)DO NOTHING;
INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder,screenname) VALUES (113, 'IDS_TSK_LIMSTESTORDER', 'IDS_MDL_TEMPLATES', '0', '0', 'NA', 'NA', '1,0,0',66,'IDS_SCN_TEMPLATEMAPPING') ON CONFLICT(orderno)DO NOTHING;
INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder,screenname) VALUES (114, 'IDS_TSK_ELNANDRESEARCH', 'IDS_MDL_TEMPLATES', '0', '0', 'NA', 'NA', '1,0,0',66,'IDS_SCN_TEMPLATEMAPPING') ON CONFLICT(orderno)DO NOTHING;
 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_TEMPLATEOVERVIEW', 'IDS_MDL_DASHBOARD', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_DASHBOARD' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_TEMPLATEOVERVIEW' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_DASHBOARDINVENTORY', 'IDS_MDL_DASHBOARD', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_DASHBOARD'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_DASHBOARDINVENTORY' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_NEWFOLDER', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SHEETORDERS'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_NEWFOLDER' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_MOVEORDERS', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SHEETORDERS'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_MOVEORDERS' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_SHEETORDEREXPORT', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SHEETORDERS'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SHEETORDEREXPORT' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_FOLDERCREATIONPROTOCOL', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLORDERS'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_FOLDERCREATIONPROTOCOL' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_PENDINGWORKPROTOCOL', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLORDERS'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_PENDINGWORKPROTOCOL' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_COMPLETEDWORKPROTOCOL', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLORDERS'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_COMPLETEDWORKPROTOCOL' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_MOVEORDERSPROTOCOL', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLORDERS'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_MOVEORDERSPROTOCOL' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_TEMPLATESHAREDBYME', 'IDS_MDL_TEMPLATES', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SHEETTEMPLATE'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_TEMPLATESHAREDBYME' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_SCN_SHEETTEMPLATE', 'IDS_MDL_TEMPLATES', 'administrator', '1', '1', 'NA', '1', 1,1,'IDS_SCN_SHEETTEMPLATE' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_SCN_SHEETTEMPLATE' and usergroupid_usergroupcode = 1) ;
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_TEMPLATESHAREDTOME', 'IDS_MDL_TEMPLATES', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SHEETTEMPLATE'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_TEMPLATESHAREDTOME' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_SHEETTEMPEXPORT', 'IDS_MDL_TEMPLATES', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SHEETTEMPLATE'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SHEETTEMPEXPORT' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_SCN_PROTOCOLTEMPLATE', 'IDS_MDL_TEMPLATES', 'administrator', '1', '1', 'NA', '1', 1,1,'IDS_SCN_PROTOCOLTEMPLATE'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_SCN_PROTOCOLTEMPLATE' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_EXPORTPDF', 'IDS_MDL_TEMPLATES', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLTEMPLATE' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_EXPORTPDF' and usergroupid_usergroupcode = 1) ;
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_PROTOCOLTEMPSHAREBYME', 'IDS_MDL_TEMPLATES', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLTEMPLATE'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_PROTOCOLTEMPSHAREBYME' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_PROTOCOLTEMPSHARETOME', 'IDS_MDL_TEMPLATES', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLTEMPLATE'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_PROTOCOLTEMPSHARETOME' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_SHEET', 'IDS_MDL_TEMPLATES', 'administrator', '1', '1', 'NA', 'NA', 1,1,'IDS_SCN_TEMPLATEMAPPING'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SHEET' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_PROTOCOL', 'IDS_MDL_TEMPLATES', 'administrator', '1', '1', 'NA', 'NA', 1,1,'IDS_SCN_TEMPLATEMAPPING'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_PROTOCOL' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_SCN_INVENTORY', 'IDS_MDL_MASTERS', 'administrator', '1', '1', 'NA', '1', 1,1,'IDS_SCN_INVENTORY'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_SCN_INVENTORY' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_ADDREPO', 'IDS_MDL_MASTERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_INVENTORY'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_ADDREPO' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_EDITREPO', 'IDS_MDL_MASTERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_INVENTORY'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_EDITREPO' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_SCN_LOGBOOK', 'IDS_MDL_MASTERS', 'administrator', '1', '1', '1', '1', 1,1,'IDS_SCN_LOGBOOK'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_SCN_LOGBOOK' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_ADDLOGBOOK', 'IDS_MDL_MASTERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_LOGBOOK'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_ADDLOGBOOK' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_EDITLOGBOOK', 'IDS_MDL_MASTERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_LOGBOOK'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_EDITLOGBOOK' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_SCN_USERMASTER', 'IDS_MDL_SETUP', 'administrator', '1', '1', 'NA', '1', 1,1,'IDS_SCN_USERMASTER'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_SCN_USERMASTER' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_UNLOCK', 'IDS_MDL_SETUP', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_USERMASTER'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_UNLOCK' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_RESETPASSWORD', 'IDS_MDL_SETUP', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_USERMASTER'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_RESETPASSWORD' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_RETIRE', 'IDS_MDL_SETUP', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_USERMASTER'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_RETIRE' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_IMPORTADS', 'IDS_MDL_SETUP', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_USERMASTER'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_IMPORTADS' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_NEWDOCUMENT', 'IDS_MDL_REPORTS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_REPORTS' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_NEWDOCUMENT' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_NEWTEMP', 'IDS_MDL_REPORTS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_REPORTS'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_NEWTEMP' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_OPENREPORT', 'IDS_MDL_REPORTS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_REPORTS'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_OPENREPORT' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_IMPORTDOCX', 'IDS_MDL_REPORTS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_REPORTS'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_IMPORTDOCX' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_SCN_PARSER', 'IDS_MDL_PARSER', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PARSER' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_SCN_PARSER' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_SCN_INSTRUMENTCATEGORY', 'IDS_MDL_PARSER', 'administrator', '1', '1', '1', '1', 1,1,'IDS_SCN_INSTRUMENTCATEGORY'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_SCN_INSTRUMENTCATEGORY' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_SCN_DELIMITER', 'IDS_MDL_PARSER', 'administrator', '1', '1', '1', '1', 1,1,'IDS_SCN_DELIMITER' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_SCN_DELIMITER' and usergroupid_usergroupcode = 1); 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_SCN_METHODDELIMITER', 'IDS_MDL_PARSER', 'administrator', '1', '1', '1', '1', 1,1,'IDS_SCN_METHODDELIMITER' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_SCN_METHODDELIMITER' and usergroupid_usergroupcode = 1); 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_SCN_METHODMASTER', 'IDS_MDL_PARSER', 'administrator', '1', '1', '1', '1', 1,1,'IDS_SCN_METHODMASTER' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_SCN_METHODMASTER' and usergroupid_usergroupcode = 1);
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_SCN_SHEETORDERS', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SHEETORDERS'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_SCN_SHEETORDERS' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_SCN_PROTOCOLORDERS', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLORDERS'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_SCN_PROTOCOLORDERS' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_ORDERSHAREDBYMEPROTOCOL', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLORDERS'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_ORDERSHAREDBYMEPROTOCOL' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_ORDERSHAREDTOMEPROTOCOL', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLORDERS'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_ORDERSHAREDTOMEPROTOCOL' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_ACTDEACTUSERMASTER', 'IDS_MDL_SETUP', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_USERMASTER'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_ACTDEACTUSERMASTER' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_FOLDERCREATION', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SHEETORDERS'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_FOLDERCREATION' and usergroupid_usergroupcode = 1) ; 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_SCN_UNITMASTER', 'IDS_MDL_INVENTORY', 'administrator', '1', '1', '1', '1', 1,1,'IDS_SCN_UNITMASTER'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_SCN_UNITMASTER' and usergroupid_usergroupcode = 1); 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_DOWNLOADPDFEXCEL', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_UNITMASTER'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_DOWNLOADPDFEXCEL' and usergroupid_usergroupcode = 1); 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_SCN_SECTIONMASTER', 'IDS_MDL_INVENTORY', 'administrator', '1', '1', '1', '1', 1,1,'IDS_SCN_SECTIONMASTER'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_SCN_SECTIONMASTER' and usergroupid_usergroupcode = 1); 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_DOWNLOADPDFEXCELSECTION', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SECTIONMASTER'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_DOWNLOADPDFEXCELSECTION' and usergroupid_usergroupcode = 1); 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_SCN_STORAGELOCATION', 'IDS_MDL_INVENTORY', 'administrator', '1', '1', '1', '1', 1,1,'IDS_SCN_STORAGELOCATION'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_SCN_STORAGELOCATION' and usergroupid_usergroupcode = 1); 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_SCN_MATERIALCATEGORY', 'IDS_MDL_INVENTORY', 'administrator', '1', '1', '1', '1', 1,1,'IDS_SCN_MATERIALCATEGORY'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_SCN_MATERIALCATEGORY' and usergroupid_usergroupcode = 1);
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_DOWNLOADMATERILACATEGORY', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_MATERIALCATEGORY'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_DOWNLOADMATERILACATEGORY' and usergroupid_usergroupcode = 1);  
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_SCN_MATERIAL', 'IDS_MDL_INVENTORY', 'administrator', '1', '1', 'NA', 'NA', 1,1,'IDS_SCN_MATERIAL'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_SCN_MATERIAL' and usergroupid_usergroupcode = 1);
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_SCN_MATERIALINVENTORY', 'IDS_MDL_INVENTORY', 'administrator', '1', '1', 'NA', 'NA', 1,1,'IDS_SCN_MATERIALINVENTORY'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_SCN_MATERIALINVENTORY' and usergroupid_usergroupcode = 1);
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_RETIRELOGBOOK', 'IDS_MDL_LOGBOOK', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_LOGBOOK'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_RETIRELOGBOOK' and usergroupid_usergroupcode = 1);  
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_REVIEWLOGBOOK', 'IDS_MDL_LOGBOOK', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_LOGBOOK'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_REVIEWLOGBOOK' and usergroupid_usergroupcode = 1);    
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_UPLOADSHEETORDER', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SHEETORDERS'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_UPLOADSHEETORDER' and usergroupid_usergroupcode = 1);    
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_UPLOADPROTOCOLORDER', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLORDERS'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_UPLOADPROTOCOLORDER' and usergroupid_usergroupcode = 1);    
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_SCN_UNLOCKORDERS', 'IDS_MDL_MASTERS', 'administrator', '1', 'NA', 'NA', '1', 1,1,'IDS_SCN_UNLOCKORDERS'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_SCN_UNLOCKORDERS' and usergroupid_usergroupcode = 1);    
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_SCN_DOMAIN', 'IDS_MDL_SETUP', 'administrator', '1', '1', '1', '1', 1,1,'IDS_SCN_DOMAIN'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_SCN_DOMAIN' and usergroupid_usergroupcode = 1);    
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_ELNANDRESEARCH', 'IDS_MDL_TEMPLATES', 'administrator', '1', '1', 'NA', 'NA', 1,1,'IDS_SCN_TEMPLATEMAPPING'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_ELNANDRESEARCH' and usergroupid_usergroupcode = 1);    
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_LIMSTESTORDER', 'IDS_MDL_TEMPLATES', 'administrator', '1', '1', 'NA', 'NA', 1,1,'IDS_SCN_TEMPLATEMAPPING'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_LIMSTESTORDER' and usergroupid_usergroupcode = 1);    


update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_PARAMETEROVERVIEW' where displaytopic='Parameter OverView';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_ORDEROVERVIEW' where displaytopic='Order Overview';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_ACTIVITIES' where displaytopic='Activities';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_PARAMETERUSAGE' where displaytopic='Parameter Usage';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_PENDINGWORK' where displaytopic='Pending Work';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_COMPLETEDWORK' where displaytopic='Completed Work';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_LIMSTASKORDER' where displaytopic='LIMS Task Order';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_ELNTASKORDER' where displaytopic='ELN Task Order';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_RESEARCHACTIVITY' where displaytopic='Research Activity Order';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_MANAGEEXCEL' where displaytopic='Manage Excel';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_SHEETEVALUATION' where displaytopic='Sheet Evaluation';
update lsusergrouprightsmaster set displaytopic = 'IDS_SCN_SHEETTEMPLATE' where displaytopic='Sheet Templates';
update lsusergrouprightsmaster set displaytopic = 'IDS_SCN_TASKMASTER' where displaytopic='Task Master';
update lsusergrouprightsmaster set displaytopic = 'IDS_SCN_PROJECTMASTER' where displaytopic='Project Master';
update lsusergrouprightsmaster set displaytopic = 'IDS_SCN_SAMPLEMASTER' where displaytopic='Sample Master';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_INSTRUMENTMASTER' where displaytopic='Instrument Master';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_LIMSTESTORDER' where displaytopic='LIMS Test  Order';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_ELNANDRESEARCH' where displaytopic='ELN & Research Activity Test Order';
update lsusergrouprightsmaster set displaytopic = 'IDS_SCN_USERMASTER' where displaytopic='User Master';
update lsusergrouprightsmaster set displaytopic = 'IDS_SCN_USERGROUP' where displaytopic='User Group';
update lsusergrouprightsmaster set displaytopic = 'IDS_SCN_USERRIGHTS' where displaytopic='User Rights';
update lsusergrouprightsmaster set displaytopic = 'IDS_SCN_PROJECTTEAM' where displaytopic='Project Team';
update lsusergrouprightsmaster set displaytopic = 'IDS_SCN_ORDERWORKLOW' where displaytopic='Order Workflow';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_ACTIVEUSER' where displaytopic='Active User';
update lsusergrouprightsmaster set displaytopic = 'IDS_SCN_SHEETWORKFLOW' where displaytopic='Sheet Workflow';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_DOMAIN' where displaytopic='Domain';
update lsusergrouprightsmaster set displaytopic = 'IDS_SCN_PASSWORDPOLICY' where displaytopic='Password Policy';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_ACTDEACT' where displaytopic='ACTIVATE/DEACTIVATE';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_UNLOCK' where displaytopic='Unlock';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_RESETPASSWORD' where displaytopic='Reset Password';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_IMPORTADS' where displaytopic='Import ADS';
update lsusergrouprightsmaster set displaytopic = 'IDS_SCN_AUDITTRAILHISTORY' where displaytopic='Audit trail history';
update lsusergrouprightsmaster set displaytopic = 'IDS_SCN_CFRSETTINGS' where displaytopic='CFR Settings';
update lsusergrouprightsmaster set displaytopic = 'IDS_SCN_AUDITTRAILCONFIG' where displaytopic='Audit trail configuration';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_REVIEWHISTORY' where displaytopic='Review History';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_REVIEW' where displaytopic='Review';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_CREATEARCHIVE' where displaytopic='Create Archive';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_OPENARCHIVE' where displaytopic='Open Archive';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_EXPORT' where displaytopic='Export';
update lsusergrouprightsmaster set displaytopic = 'IDS_SCN_REPORT' where displaytopic='Reports';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_GENERATEREPORT' where displaytopic='Generate Reports';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_NEWTEMP' where displaytopic='New Template';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_ELNPROTOCOL' where displaytopic='ELN Protocol Order';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_DYNAMICPROTOCOL' where displaytopic='Dynamic Protocol Order';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_NEWSTEP' where displaytopic='New Step';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_UNLOCKORDERS' where displaytopic='Unlock Orders';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_ORDERSHAREDBYME' where displaytopic='Orders Shared By Me';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_ORDERSHAREDTOME' where displaytopic='Orders Shared To Me';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_TEMPLATESHAREDBYME' where displaytopic='Templates Shared By Me';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_TEMPLATESHAREDTOME' where displaytopic='Templates Shared To Me';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_EXPORTPDF' where displaytopic='Export to pdf';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_SHEET' where displaytopic='Sheet';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_PROTOCOL' where displaytopic='Protocol';
update lsusergrouprightsmaster set displaytopic = 'IDS_SCN_TEMPLATEMAPPING' where displaytopic='Sheet Setting';
update lsusergrouprightsmaster set displaytopic = 'IDS_SCN_INVENTORY' where displaytopic='Inventory';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_RETIRE' where displaytopic='Retire';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_NEWDOCUMENT' where displaytopic='New Document';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_INSTRUMENTCATEGORY' where displaytopic='Instrument Category';
update lsusergrouprightsmaster set displaytopic = 'IDS_SCN_PARSER' where displaytopic='Parser';
update lsusergrouprightsmaster set displaytopic = 'IDS_SCN_DELIMITER' where displaytopic='Delimiters';
update lsusergrouprightsmaster set displaytopic = 'IDS_SCN_METHODDELIMITER' where displaytopic='Method Delimiters';
update lsusergrouprightsmaster set displaytopic = 'IDS_SCN_METHODMASTER' where displaytopic='Method Master';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_ADDREPO' where displaytopic='Add repository';
update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_EDITREPO' where displaytopic='Edit repository';
update lsusergrouprightsmaster set displaytopic = 'IDS_SCN_PROTOCOLTEMPLATE' where displaytopic='Protocol Templates';
update lsusergrouprightsmaster set displaytopic='IDS_TSK_ASSIGNEDORDERS' where displaytopic='Assigned Orders';
update lsusergrouprightsmaster set displaytopic='IDS_TSK_MYORDERS' where displaytopic='My Orders';
update lsusergrouprightsmaster set displaytopic='IDS_SCN_TEMPLATEWORKFLOW' where displaytopic='IDS_SCN_SHEETWORKFLOW';
update lsusergrouprightsmaster set displaytopic='IDS_SCN_AUDITTRAILHIS' where displaytopic='IDS_SCN_AUDITTRAILHISTORY';
update lsusergrouprightsmaster set displaytopic='IDS_TSK_TEMPLATEOVERVIEW' where displaytopic='IDS_TSK_PARAMETEROVERVIEW';
update lsusergrouprightsmaster set displaytopic='IDS_TSK_ORDERSHAREDBYMEPROTOCOL' where orderno=80;
update lsusergrouprightsmaster set displaytopic='IDS_TSK_ORDERSHAREDTOMEPROTOCOL' where orderno=81;
update lsusergrouprightsmaster set displaytopic='IDS_TSK_PENDINGWORKPROTOCOL' where orderno=46;
update lsusergrouprightsmaster set displaytopic='IDS_TSK_COMPLETEDWORK' where orderno=47;
update lsusergrouprightsmaster set displaytopic='IDS_TSK_ACTDEACTUSERMASTER' where orderno=22;
update lsusergrouprightsmaster set displaytopic='IDS_SCN_PROTOCOLTEMPLATE' where orderno=51;
update lsusergrouprightsmaster set displaytopic='IDS_SCN_INSTRUMENTMASTER' where displaytopic='IDS_TSK_INSTRUMENTMASTER';
update lsusergrouprightsmaster set displaytopic = 'IDS_SCN_REPORTS' where displaytopic='IDS_SCN_REPORT';
update lsusergrouprightsmaster set displaytopic='IDS_TSK_DASHBOARDINVENTORY' where displaytopic='IDS_TSK_PARAMETERUSAGE';
update lsusergrouprightsmaster set displaytopic='IDS_SCN_SHEETORDERS' where displaytopic='IDS_TSK_PENDINGWORK';
update lsusergrouprightsmaster set displaytopic='IDS_TSK_FOLDERCREATION' where displaytopic='IDS_TSK_COMPLETEDWORK';
update lsusergrouprightsmaster set displaytopic='IDS_SCN_PROTOCOLORDERS' where displaytopic='IDS_TSK_PENDINGWORKPROTOCOL';

update lsusergrouprightsmaster set modulename='IDS_MDL_ORDERS' where modulename='Register Task Orders & Execute';
update lsusergrouprightsmaster set modulename='IDS_MDL_DASHBOARD' where modulename='Dash Board';
update lsusergrouprightsmaster set modulename='IDS_MDL_ORDERS' where modulename='Protocol Order And Register';
update lsusergrouprightsmaster set modulename='IDS_MDL_TEMPLATES' where modulename in ('Templates','Protocols');
update lsusergrouprightsmaster set modulename='IDS_MDL_AUDITTRAIL' where modulename='AuditTrail History';
update lsusergrouprightsmaster set modulename='IDS_MDL_REPORTS' where modulename='Reports';
update lsusergrouprightsmaster set modulename='IDS_MDL_MASTERS' where modulename in ('Base Master','Inventory');
update lsusergrouprightsmaster set modulename='IDS_MDL_PARSER' where modulename='Parser';
update lsusergrouprightsmaster set modulename='IDS_MDL_TEMPLATES' where orderno in (14,15,71,72);
update lsusergrouprightsmaster set modulename='IDS_MDL_ORDERS' where orderno in (46,47) and displaytopic='IDS_TSK_MOVEORDERSPROTOCOL';
update lsusergrouprightsmaster set modulename='IDS_MDL_SETUP' where orderno in (16,17,18,20,21,23,22,24,52,40,43,19,41);
update lsusergrouprightsmaster set modulename='IDS_MDL_DASHBOARD' where orderno in (1,3,87);
update lsusergrouprightsmaster set modulename='IDS_MDL_ORDERS' where orderno in (7,8,9,37,38,61,62,93,85);
update lsusergrouprightsmaster set modulename='IDS_MDL_MASTERS' where orderno=78;
update lsusergrouprightsmaster set modulename='IDS_MDL_SETUP' where modulename='UserManagement';


update lsusergrouprightsmaster set screenname='IDS_SCN_DASHBOARD' where orderno in (1,2,3,4);
update lsusergrouprightsmaster set screenname='IDS_SCN_SHEETORDERS' where orderno in (5,6,7,8,9,37,38,61,62);
update lsusergrouprightsmaster set screenname='IDS_SCN_PROTOCOLORDERS' where orderno in (46,47,48,49,80,81);
update lsusergrouprightsmaster set screenname='IDS_SCN_SHEETTEMPLATE' where orderno in (10,69,70);
update lsusergrouprightsmaster set screenname='IDS_SCN_PROTOCOLTEMPLATE' where orderno in (50,51,57,83,84);
update lsusergrouprightsmaster set screenname='IDS_SCN_TEMPLATEMAPPING' where orderno in (14,15,71,72,82);
update lsusergrouprightsmaster set screenname='IDS_SCN_TASKMASTER' where orderno=11;
update lsusergrouprightsmaster set screenname='IDS_SCN_PROJECTMASTER' where orderno=12;
update lsusergrouprightsmaster set screenname='IDS_SCN_SAMPLEMASTER' where orderno=13;
update lsusergrouprightsmaster set screenname='IDS_SCN_INVENTORY' where orderno in (65,66,67);
update lsusergrouprightsmaster set screenname='IDS_SCN_UNLOCKORDERS' where orderno=95;
update lsusergrouprightsmaster set screenname='IDS_SCN_USERGROUP' where orderno in(17,21);
update lsusergrouprightsmaster set screenname='IDS_SCN_USERMASTER' where orderno in(16,52,23,24,40,22);
update lsusergrouprightsmaster set screenname='IDS_SCN_ACTIVEUSER' where orderno=44;
update lsusergrouprightsmaster set screenname='IDS_SCN_PASSWORDPOLICY' where orderno=43;
update lsusergrouprightsmaster set screenname='IDS_SCN_PROJECTTEAM' where orderno=19;
update lsusergrouprightsmaster set screenname='IDS_SCN_AUDITTRAILHIS' where orderno in(25,28,29,30,31,32);
update lsusergrouprightsmaster set screenname='IDS_SCN_DOMAIN' where orderno=42;
update lsusergrouprightsmaster set screenname='IDS_SCN_USERRIGHTS' where orderno=18;
update lsusergrouprightsmaster set screenname='IDS_SCN_CFRSETTINGS' where orderno=26;
update lsusergrouprightsmaster set screenname='IDS_SCN_AUDITTRAILCONFIG' where orderno=27;
update lsusergrouprightsmaster set screenname='IDS_SCN_REPORTS' where orderno in (34,35,45,58);
update lsusergrouprightsmaster set screenname='IDS_SCN_METHODMASTER' where orderno=56;
update lsusergrouprightsmaster set screenname='IDS_SCN_PARSER' where orderno=53;
update lsusergrouprightsmaster set screenname='IDS_SCN_DELIMITER' where orderno=54;
update lsusergrouprightsmaster set screenname='IDS_SCN_METHODDELIMITER' where orderno=55;
update lsusergrouprightsmaster set screenname='IDS_SCN_INSTRUMENTMASTER' where orderno=39;
update lsusergrouprightsmaster set screenname='IDS_SCN_INSTRUMENTCATEGORY' where orderno=68;
update lsusergrouprightsmaster set screenname='IDS_SCN_TEMPLATEWORKFLOW' where orderno=41;
update lsusergrouprightsmaster set screenname='IDS_SCN_ORDERWORKLOW' where orderno=20;


update lsusergrouprightsmaster set sequenceorder=1 where orderno =2;
update lsusergrouprightsmaster set sequenceorder=2 where orderno =1;
update lsusergrouprightsmaster set sequenceorder=3 where orderno =3;
update lsusergrouprightsmaster set sequenceorder=4 where orderno =4;
update lsusergrouprightsmaster set sequenceorder=5 where orderno =7;
update lsusergrouprightsmaster set sequenceorder=6 where orderno =5;
update lsusergrouprightsmaster set sequenceorder=7 where orderno =6;
update lsusergrouprightsmaster set sequenceorder=8 where orderno =8;
update lsusergrouprightsmaster set sequenceorder=9 where orderno =9;
update lsusergrouprightsmaster set sequenceorder=10 where orderno =37;
update lsusergrouprightsmaster set sequenceorder=11 where orderno =38;
update lsusergrouprightsmaster set sequenceorder=12 where orderno =110;
update lsusergrouprightsmaster set sequenceorder=13 where orderno =61;
update lsusergrouprightsmaster set sequenceorder=14 where orderno =62;
update lsusergrouprightsmaster set sequenceorder=15 where orderno =96;
update lsusergrouprightsmaster set sequenceorder=16 where orderno =91;
update lsusergrouprightsmaster set sequenceorder=17 where orderno =46;
update lsusergrouprightsmaster set sequenceorder=18 where orderno =47;
update lsusergrouprightsmaster set sequenceorder=19 where orderno =48;
update lsusergrouprightsmaster set sequenceorder=20 where orderno =49;
update lsusergrouprightsmaster set sequenceorder=21 where orderno =111;
update lsusergrouprightsmaster set sequenceorder=22 where orderno =80;
update lsusergrouprightsmaster set sequenceorder=23 where orderno =81;
update lsusergrouprightsmaster set sequenceorder=24 where orderno =92;
update lsusergrouprightsmaster set sequenceorder=25 where orderno =10;
update lsusergrouprightsmaster set sequenceorder=26 where orderno =69;
update lsusergrouprightsmaster set sequenceorder=27 where orderno =70;
update lsusergrouprightsmaster set sequenceorder=28 where orderno =88;
update lsusergrouprightsmaster set sequenceorder=29 where orderno =51;
update lsusergrouprightsmaster set sequenceorder=30 where orderno =50;
update lsusergrouprightsmaster set sequenceorder=31 where orderno =57;
update lsusergrouprightsmaster set sequenceorder=32 where orderno =84;
update lsusergrouprightsmaster set sequenceorder=33 where orderno =83;
update lsusergrouprightsmaster set sequenceorder=34 where orderno =113;
update lsusergrouprightsmaster set sequenceorder=35 where orderno =114;
update lsusergrouprightsmaster set sequenceorder=36 where orderno =71;
update lsusergrouprightsmaster set sequenceorder=37 where orderno =72;
update lsusergrouprightsmaster set sequenceorder=38 where orderno =14;
update lsusergrouprightsmaster set sequenceorder=39 where orderno =11;
update lsusergrouprightsmaster set sequenceorder=40 where orderno =12;
update lsusergrouprightsmaster set sequenceorder=41 where orderno =13;
update lsusergrouprightsmaster set sequenceorder=42 where orderno =65;
update lsusergrouprightsmaster set sequenceorder=43 where orderno =66;
update lsusergrouprightsmaster set sequenceorder=44 where orderno =67;
update lsusergrouprightsmaster set sequenceorder=45 where orderno =95;
update lsusergrouprightsmaster set sequenceorder=46 where orderno =17;
update lsusergrouprightsmaster set sequenceorder=47 where orderno =21;
update lsusergrouprightsmaster set sequenceorder=48 where orderno =16;
update lsusergrouprightsmaster set sequenceorder=49 where orderno =23;
update lsusergrouprightsmaster set sequenceorder=50 where orderno =22;
update lsusergrouprightsmaster set sequenceorder=51 where orderno =24;
update lsusergrouprightsmaster set sequenceorder=52 where orderno =52;
update lsusergrouprightsmaster set sequenceorder=53 where orderno =40;
update lsusergrouprightsmaster set sequenceorder=54 where orderno =18;
update lsusergrouprightsmaster set sequenceorder=55 where orderno =19;
update lsusergrouprightsmaster set sequenceorder=56 where orderno =20;
update lsusergrouprightsmaster set sequenceorder=57 where orderno =41;
update lsusergrouprightsmaster set sequenceorder=58 where orderno =43;
update lsusergrouprightsmaster set sequenceorder=59 where orderno =25;
update lsusergrouprightsmaster set sequenceorder=60 where orderno =28;
update lsusergrouprightsmaster set sequenceorder=61 where orderno =29;
update lsusergrouprightsmaster set sequenceorder=62 where orderno =30;
update lsusergrouprightsmaster set sequenceorder=63 where orderno =31;
update lsusergrouprightsmaster set sequenceorder=64 where orderno =32;
update lsusergrouprightsmaster set sequenceorder=65 where orderno =26;
update lsusergrouprightsmaster set sequenceorder=66 where orderno =27;
update lsusergrouprightsmaster set sequenceorder=67 where orderno =34;
update lsusergrouprightsmaster set sequenceorder=68 where orderno =58;
update lsusergrouprightsmaster set sequenceorder=69 where orderno =45;
update lsusergrouprightsmaster set sequenceorder=70 where orderno =35;
update lsusergrouprightsmaster set sequenceorder=71 where orderno =90;
update lsusergrouprightsmaster set sequenceorder=72 where orderno =89;
update lsusergrouprightsmaster set sequenceorder=73 where orderno =53;
update lsusergrouprightsmaster set sequenceorder=74 where orderno =68;
update lsusergrouprightsmaster set sequenceorder=75 where orderno =39;
update lsusergrouprightsmaster set sequenceorder=76 where orderno =54;
update lsusergrouprightsmaster set sequenceorder=77 where orderno =55;
update lsusergrouprightsmaster set sequenceorder=78 where orderno =56;
update lsusergrouprightsmaster set sequenceorder=79 where orderno =99;
update lsusergrouprightsmaster set sequenceorder=80 where orderno =100;
update lsusergrouprightsmaster set sequenceorder=81 where orderno =101;
update lsusergrouprightsmaster set sequenceorder=82 where orderno =102;
update lsusergrouprightsmaster set sequenceorder=83 where orderno =103;
update lsusergrouprightsmaster set sequenceorder=84 where orderno =104;
update lsusergrouprightsmaster set sequenceorder=85 where orderno =105;
update lsusergrouprightsmaster set sequenceorder=86 where orderno =106;
update lsusergrouprightsmaster set sequenceorder=87 where orderno =107;
update lsusergrouprightsmaster set sequenceorder=88 where orderno =112;
update lsusergrouprightsmaster set sequenceorder=89 where orderno =97;
update lsusergrouprightsmaster set sequenceorder=90 where orderno =98;
update lsusergrouprightsmaster set sequenceorder=91 where orderno =108;
update lsusergrouprightsmaster set sequenceorder=92 where orderno =109;

update lsusergrouprights set displaytopic = 'IDS_TSK_UNLOCKORDERS' where displaytopic='Unlock Orders';
update lsusergrouprights set displaytopic = 'IDS_TSK_PARAMETERUSAGE' where displaytopic='Parameter OverView';
update lsusergrouprights set displaytopic = 'IDS_TSK_ORDEROVERVIEW' where displaytopic='Order Overview';
update lsusergrouprights set displaytopic = 'IDS_TSK_ACTIVITIES' where displaytopic='Activities';
update lsusergrouprights set displaytopic = 'IDS_TSK_PARAMETERUSAGE' where displaytopic='Parameter Usage';
update lsusergrouprights set displaytopic = 'IDS_TSK_LIMSTASKORDER' where displaytopic='LIMS Task Order';
update lsusergrouprights set displaytopic = 'IDS_TSK_MANAGEEXCEL' where displaytopic='Manage Excel';
update lsusergrouprights set displaytopic = 'IDS_TSK_ORDERSHAREDBYME' where displaytopic='Orders Shared By Me';
update lsusergrouprights set displaytopic = 'IDS_TSK_ORDERSHAREDTOME' where displaytopic='Orders Shared To Me';
update lsusergrouprights set displaytopic = 'IDS_TSK_PENDINGWORK' where displaytopic='Pending Work';
update lsusergrouprights set displaytopic = 'IDS_TSK_COMPLETEDWORK' where displaytopic='Completed Work';
update lsusergrouprights set displaytopic = 'IDS_TSK_SHEETEVALUATION' where displaytopic='Sheet Evaluation';
update lsusergrouprights set displaytopic = 'IDS_TSK_ELNTASKORDER' where displaytopic='ELN Task Order';
update lsusergrouprights set displaytopic = 'IDS_TSK_RESEARCHACTIVITY' where displaytopic='Research Activity Order';
update lsusergrouprights set displaytopic = 'IDS_TSK_ELNPROTOCOL' where displaytopic='ELN Protocol Order';
update lsusergrouprights set displaytopic = 'IDS_TSK_DYNAMICPROTOCOL' where displaytopic='Dynamic Protocol Order';
update lsusergrouprights set displaytopic = 'IDS_TSK_NEWSTEP' where displaytopic='New Step';
update lsusergrouprights set displaytopic = 'IDS_SCN_SHEETTEMPLATE' where displaytopic='Sheet Templates';
update lsusergrouprights set displaytopic = 'IDS_TSK_ELNANDRESEARCH' where displaytopic='ELN & Research Activity Test Order';
update lsusergrouprights set displaytopic = 'IDS_TSK_LIMSTESTORDER' where displaytopic='LIMS Test  Order';
update lsusergrouprights set displaytopic = 'IDS_SCN_PROJECTMASTER' where displaytopic='Project Master';
update lsusergrouprights set displaytopic = 'IDS_SCN_SAMPLEMASTER' where displaytopic='Sample Master';
update lsusergrouprights set displaytopic = 'IDS_SCN_TASKMASTER' where displaytopic='Task Master';
update lsusergrouprights set displaytopic = 'IDS_SCN_USERMASTER' where displaytopic='User Master';
update lsusergrouprights set displaytopic = 'IDS_SCN_USERRIGHTS' where displaytopic='User Rights';
update lsusergrouprights set displaytopic = 'IDS_SCN_PROJECTTEAM' where displaytopic='Project Team';
update lsusergrouprights set displaytopic = 'IDS_SCN_ORDERWORKLOW' where displaytopic='Order Workflow';
update lsusergrouprights set displaytopic = 'IDS_TSK_ACTIVEUSER' where displaytopic='Active User';
update lsusergrouprights set displaytopic = 'IDS_SCN_SHEETWORKFLOW' where displaytopic='Sheet Workflow';
update lsusergrouprights set displaytopic = 'IDS_TSK_DOMAIN' where displaytopic='Domain';
update lsusergrouprights set displaytopic = 'IDS_SCN_PASSWORDPOLICY' where displaytopic='Password Policy';
update lsusergrouprights set displaytopic = 'IDS_SCN_USERGROUP' where displaytopic='User Group';
update lsusergrouprights set displaytopic = 'IDS_SCN_SITEMASTER' where displaytopic='Site Master';
update lsusergrouprights set displaytopic = 'IDS_TSK_ACTDEACT' where displaytopic='ACTIVATE/DEACTIVATE';
update lsusergrouprights set displaytopic = 'IDS_TSK_UNLOCK' where displaytopic='Unlock';
update lsusergrouprights set displaytopic = 'IDS_TSK_RESETPASSWORD' where displaytopic='Reset Password';
update lsusergrouprights set displaytopic = 'IDS_TSK_IMPORTADS' where displaytopic='Import ADS';
update lsusergrouprights set displaytopic = 'IDS_TSK_RETIRE' where displaytopic='Retire';
update lsusergrouprights set displaytopic = 'IDS_SCN_CFRSETTINGS' where displaytopic='CFR Settings';
update lsusergrouprights set displaytopic = 'IDS_TSK_REVIEWHISTORY' where displaytopic='Review History';
update lsusergrouprights set displaytopic = 'IDS_TSK_OPENARCHIVE' where displaytopic='Open Archive';
update lsusergrouprights set displaytopic = 'IDS_TSK_EXPORT' where displaytopic='Export';
update lsusergrouprights set displaytopic = 'IDS_SCN_AUDITTRAILCONFIG' where displaytopic='Audit trail configuration';
update lsusergrouprights set displaytopic = 'IDS_SCN_AUDITTRAILHISTORY' where displaytopic='Audit trail history';
update lsusergrouprights set displaytopic = 'IDS_TSK_REVIEW' where displaytopic='Review';
update lsusergrouprights set displaytopic = 'IDS_TSK_GENERATEREPORT' where displaytopic='Generate Reports';
update lsusergrouprights set displaytopic = 'IDS_TSK_TEMPLATEDESIGN' where displaytopic='Template Designing';
update lsusergrouprights set displaytopic = 'IDS_TSK_LIMSTASKORDER' where displaytopic='LIMS Task Order';
update lsusergrouprights set displaytopic = 'IDS_TSK_EXPORTPDF' where displaytopic='Export to pdf';
update lsusergrouprights set displaytopic = 'IDS_TSK_PROTOCOL' where displaytopic='Protocol';
update lsusergrouprights set displaytopic = 'IDS_SCN_REPORTS' where displaytopic='Reports';
update lsusergrouprights set displaytopic = 'IDS_TSK_TEMPLATESHAREDBYME' where displaytopic='Templates Shared By Me';
update lsusergrouprights set displaytopic = 'IDS_TSK_TEMPLATESHAREDTOME' where displaytopic='Templates Shared To Me';
update lsusergrouprights set displaytopic = 'IDS_TSK_SHEET' where displaytopic='Sheet';
update lsusergrouprights set displaytopic = 'IDS_SCN_METHODDELIMITER' where displaytopic='Method Delimiters';
update lsusergrouprights set displaytopic = 'IDS_SCN_METHODMASTER' where displaytopic='Method Master';
update lsusergrouprights set displaytopic = 'IDS_TSK_INSTRUMENTCATEGORY' where displaytopic='Instrument Category';
update lsusergrouprights set displaytopic = 'IDS_SCN_DELIMITER' where displaytopic='Delimiters';
update lsusergrouprights set displaytopic = 'IDS_SCN_PARSER' where displaytopic='Parser';
update lsusergrouprights set displaytopic = 'IDS_TSK_INSTRUMENTMASTER' where displaytopic='Instrument Master';
update lsusergrouprights set displaytopic = 'IDS_TSK_ADDREPO' where displaytopic='Add repository';
update lsusergrouprights set displaytopic = 'IDS_TSK_EDITREPO' where displaytopic='Edit repository';
update lsusergrouprights set displaytopic = 'IDS_SCN_TEMPLATEMAPPING' where displaytopic='Sheet Setting';
update lsusergrouprights set displaytopic='IDS_SCN_INSTRUMENTMASTER' where displaytopic='IDS_TSK_INSTRUMENTMASTER';
update lsusergrouprights set displaytopic='IDS_SCN_INSTRUMENTCATEGORY' where displaytopic='IDS_TSK_INSTRUMENTCATEGORY';
update lsusergrouprights set displaytopic = 'IDS_TSK_NEWDOCUMENT' where displaytopic='New Document';
update lsusergrouprights set displaytopic = 'IDS_SCN_REPORTS' where displaytopic='IDS_SCN_REPORT';
update lsusergrouprights set displaytopic='IDS_TSK_CREATEARCHIVE' where displaytopic='Create Archive';
update lsusergrouprights set displaytopic='IDS_SCN_TEMPLATEWORKFLOW' where displaytopic='IDS_SCN_SHEETWORKFLOW';
update lsusergrouprights set displaytopic='IDS_SCN_AUDITTRAILHIS' where displaytopic='IDS_SCN_AUDITTRAILHISTORY';

update lsusergrouprights set modulename='IDS_MDL_SETUP' where displaytopic in ('IDS_SCN_USERGROUP','IDS_SCN_ORDERWORKLOW','IDS_SCN_TEMPLATEWORKFLOW','IDS_SCN_PASSWORDPOLICY','IDS_SCN_PROJECTTEAM','IDS_SCN_USERRIGHTS');
update lsusergrouprights set modulename='IDS_MDL_DASHBOARD' where displaytopic in ('IDS_TSK_TEMPLATEOVERVIEW','IDS_SCN_DASHBOARD','IDS_TSK_ACTIVITIES','IDS_TSK_ORDEROVERVIEW','IDS_TSK_DASHBOARDINVENTORY');
update lsusergrouprights set modulename='IDS_MDL_REPORTS' where displaytopic IN ('IDS_TSK_NEWTEMP','IDS_TSK_NEWDOCUMENT');
update lsusergrouprights set modulename='IDS_MDL_PARSER' where displaytopic in ('IDS_SCN_INSTRUMENTMASTER','IDS_SCN_PARSER','IDS_SCN_METHODDELIMITER','IDS_SCN_DELIMITER');
update lsusergrouprights set modulename='IDS_MDL_TEMPLATES' where displaytopic in ('IDS_TSK_TEMPLATESHAREDTOME','IDS_TSK_TEMPLATESHAREDBYME','IDS_TSK_EXPORTPDF','IDS_TSK_NEWSTEP','IDS_SCN_SHEETTEMPLATE','IDS_SCN_PROTOCOLTEMPLATE');
update lsusergrouprights set modulename='IDS_MDL_AUDITTRAIL' where displaytopic in('IDS_TSK_REVIEW','IDS_TSK_EXPORT','IDS_TSK_OPENARCHIVE','IDS_SCN_AUDITHISTORY','IDS_SCN_AUDITTRAILCONFIG');
update lsusergrouprights set modulename='IDS_MDL_MASTERS' where displaytopic in ('IDS_TSK_ADDREPO','IDS_TSK_EDITREPO','IDS_SCN_UNLOCKORDERS','IDS_SCN_TASKMASTER','IDS_SCN_SAMPLEMASTER','IDS_SCN_PROJECTMASTER','IDS_SCN_INVENTORY');
update lsusergrouprights set modulename='IDS_MDL_ORDERS' where displaytopic in ('IDS_SCN_SHEETORDERS','IDS_TSK_FOLDERCREATION','IDS_TSK_ELNTASKORDER','IDS_TSK_RESEARCHACTIVITY','IDS_TSK_MANAGEEXCEL','IDS_TSK_SHEETEVALUATION','IDS_TSK_ORDERSHAREDBYME','IDS_TSK_ORDERSHAREDTOME','IDS_TSK_PENDINGWORK','IDS_TSK_MOVEORDERSPROTOCOL') and usergroupid_usergroupcode=1;
update lsusergrouprights set modulename='IDS_MDL_ORDERS',screenname='IDS_SCN_PROTOCOLORDERS' where displaytopic in ('IDS_TSK_ELNPROTOCOL','IDS_TSK_DYNAMICPROTOCOL');
update lsusergrouprights set modulename='IDS_MDL_AUDITTRAIL' where displaytopic in ('IDS_SCN_CFRSETTINGS','IDS_TSK_CREATEARCHIVE','IDS_TSK_REVIEWHISTORY','IDS_SCN_AUDITTRAILHIS');
update lsusergrouprights set modulename='IDS_MDL_SETUP' where displaytopic  IN ('IDS_SCN_ACTIVEUSER','IDS_SCN_DOMAIN','IDS_TSK_IMPORTADS','IDS_TSK_RETIRE');
update lsusergrouprights set modulename='IDS_MDL_ORDERS' where displaytopic in ('IDS_TSK_ORDERSHAREDBYME','IDS_TSK_ORDERSHAREDTOME','IDS_TSK_SHEETEVALUATION','IDS_TSK_ELNTASKORDER','IDS_TSK_RESEARCHACTIVITY','IDS_TSK_MANAGEEXCEL');
update lsusergrouprights set modulename='IDS_MDL_LOGBOOK' where screenname='IDS_SCN_LOGBOOK';
update lsusergrouprights set modulename='IDS_MDL_ORDERS' where displaytopic='IDS_TSK_MOVEORDERSPROTOCOL';
update lsusergrouprights set modulename='IDS_MDL_SETUP' where modulename in ('UserManagement','User Master');
update lsusergrouprights set modulename='IDS_MDL_ORDERS' where modulename ='Protocol Order And Register';
update lsusergrouprights set modulename='IDS_MDL_PARSER' where modulename='Parser';
update lsusergrouprights set modulename='IDS_MDL_REPORTS' where modulename='Reports';
update lsusergrouprights set modulename='IDS_MDL_DASHBOARD' where modulename='Dash Board';
update lsusergrouprights set modulename='IDS_MDL_MASTERS' where modulename in ('Base Master','Inventory');
update lsusergrouprights set modulename='IDS_MDL_ORDERS' where displaytopic in ('IDS_SCN_SHEETORDERS','IDS_TSK_FOLDERCREATION','IDS_TSK_ELNTASKORDER','IDS_TSK_RESEARCHACTIVITY','IDS_TSK_MANAGEEXCEL','IDS_TSK_SHEETEVALUATION','IDS_TSK_ORDERSHAREDBYME','IDS_TSK_ORDERSHAREDTOME','IDS_TSK_PENDINGWORK') and usergroupid_usergroupcode=1;
update lsusergrouprights set modulename='IDS_MDL_ORDERS' where modulename='Register Task Orders & Execute';
update lsusergrouprights set modulename='IDS_MDL_TEMPLATES' where modulename='Sheet Settings';
update lsusergrouprights set modulename='IDS_MDL_AUDITTRAIL' where modulename='AuditTrail History';

update lsusergrouprights set screenname='IDS_SCN_USERMASTER' where displaytopic in ('IDS_TSK_UNLOCK','IDS_SCN_USERMASTER','IDS_TSK_RESETPASSWORD','IDS_TSK_RETIRE','IDS_TSK_IMPORTADS');
update lsusergrouprights set screenname='IDS_SCN_DASHBOARD' where displaytopic='IDS_SCN_DASHBOARD';
update lsusergrouprights set screenname='IDS_SCN_DASHBOARD' where displaytopic in ('IDS_TSK_ORDEROVERVIEW','IDS_TSK_ACTIVITIES');
update lsusergrouprights set screenname='IDS_SCN_SHEETORDERS' where displaytopic  in ('IDS_TSK_LIMSTASKORDER','IDS_TSK_COMPLETEDWORK','IDS_TSK_ELNTASKORDER','IDS_TSK_RESEARCHACTIVITY','IDS_TSK_MANAGEEXCEL','IDS_TSK_SHEETEVALUATION','IDS_TSK_ORDERSHAREDBYME','IDS_TSK_ORDERSHAREDTOME','IDS_TSK_PENDINGWORK');
update lsusergrouprights set screenname='IDS_SCN_PROTOCOLORDERS' where displaytopic in ('IDS_TSK_ELNPROTOCOL','IDS_TSK_DYNAMICPROTOCOL');
update lsusergrouprights set screenname='IDS_SCN_SHEETTEMPLATE' where displaytopic='IDS_SCN_SHEETTEMPLATE' ;
update lsusergrouprights set screenname='IDS_SCN_PROTOCOLTEMPLATE' where displaytopic in ('IDS_TSK_NEWSTEP','IDS_TSK_PROTOCOLTEMPSHAREBYME','IDS_TSK_PROTOCOLTEMPSHARETOME') ;
update lsusergrouprights set screenname='IDS_SCN_TEMPLATEMAPPING' where displaytopic='IDS_TSK_LIMSTESTORDER' ;
update lsusergrouprights set screenname='IDS_SCN_TASKMASTER' where displaytopic='IDS_SCN_TASKMASTER' ;
update lsusergrouprights set screenname='IDS_SCN_PROJECTMASTER' where displaytopic='IDS_SCN_PROJECTMASTER' ;
update lsusergrouprights set screenname='IDS_SCN_SAMPLEMASTER' where displaytopic='IDS_SCN_SAMPLEMASTER' ;
update lsusergrouprights set screenname='IDS_SCN_UNLOCKORDERS' where displaytopic='IDS_SCN_UNLOCKORDERS' ;
update lsusergrouprights set screenname='IDS_SCN_USERGROUP' where displaytopic='IDS_SCN_USERGROUP' ;
--update lsusergrouprights set screenname='IDS_SCN_USERGROUP' where displaytopic='IDS_TSK_ACTDEACT' ;
update lsusergrouprights set screenname='IDS_SCN_USERMASTER' where displaytopic  in ('IDS_TSK_ACTDEACTUSERMASTER','IDS_TSK_RETIRE','IDS_TSK_IMPORTADS') ;
update lsusergrouprights set screenname='IDS_SCN_USERRIGHTS' where displaytopic='IDS_SCN_USERRIGHTS' ;
update lsusergrouprights set screenname='IDS_SCN_PROJECTTEAM' where displaytopic='IDS_SCN_PROJECTTEAM' ;
update lsusergrouprights set screenname='IDS_SCN_ORDERWORKLOW' where displaytopic='IDS_SCN_ORDERWORKLOW' ;
update lsusergrouprights set screenname='IDS_SCN_TEMPLATEWORKFLOW' where displaytopic='IDS_SCN_TEMPLATEWORKFLOW' ;
update lsusergrouprights set screenname='IDS_SCN_PASSWORDPOLICY' where displaytopic='IDS_SCN_PASSWORDPOLICY' ;
update lsusergrouprights set screenname='IDS_SCN_AUDITTRAILHIS' where displaytopic in ('IDS_SCN_AUDITTRAILHIS','IDS_TSK_REVIEWHISTORY','IDS_TSK_REVIEW','IDS_TSK_CREATEARCHIVE','IDS_TSK_OPENARCHIVE','IDS_TSK_EXPORT') ;
update lsusergrouprights set screenname='IDS_SCN_AUDITTRAILCONFIG' where displaytopic='IDS_SCN_AUDITTRAILCONFIG' ;
update lsusergrouprights set screenname='IDS_SCN_CFRSETTINGS' where displaytopic='IDS_SCN_CFRSETTINGS' ;
update lsusergrouprights set screenname='IDS_SCN_REPORTS' where displaytopic in ('IDS_SCN_REPORTS','IDS_TSK_GENERATEREPORT') ;
update lsusergrouprights set screenname='IDS_SCN_INSTRUMENTMASTER' where displaytopic='IDS_SCN_INSTRUMENTMASTER' ;
update lsusergrouprights set screenname='IDS_SCN_SITEMASTER' where displaytopic='IDS_SCN_SITEMASTER' ;
update lsusergrouprights set screenname='IDS_SCN_ACTIVEUSER' where displaytopic='IDS_SCN_ACTIVEUSER' ;
update lsusergrouprights set screenname='IDS_SCN_DOMAIN' where displaytopic='IDS_SCN_DOMAIN' ;



update lsusergrouprights set sequenceorder=1 where displaytopic ='IDS_TSK_ORDEROVERVIEW' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=2 where displaytopic ='IDS_TSK_TEMPLATEOVERVIEW' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=3 where displaytopic ='IDS_TSK_ACTIVITIES' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=4 where displaytopic ='IDS_TSK_DASHBOARDINVENTORY' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=5 where displaytopic ='IDS_SCN_SHEETORDERS' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=6 where displaytopic ='IDS_TSK_FOLDERCREATION' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=7 where displaytopic ='IDS_TSK_ELNTASKORDER' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=8 where displaytopic ='IDS_TSK_RESEARCHACTIVITY' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=9 where displaytopic ='IDS_TSK_MANAGEEXCEL' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=10 where displaytopic ='IDS_TSK_SHEETEVALUATION' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=11 where displaytopic ='IDS_TSK_UPLOADSHEETORDER' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=12 where displaytopic ='IDS_TSK_ORDERSHAREDBYME' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=13 where displaytopic ='IDS_TSK_ORDERSHAREDTOME' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=14 where displaytopic ='IDS_TSK_MOVEORDERS' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=15 where displaytopic ='IDS_TSK_SHEETORDEREXPORT' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=16 where displaytopic ='IDS_SCN_PROTOCOLORDERS' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=17 where displaytopic ='IDS_TSK_FOLDERCREATIONPROTOCOL' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=18 where displaytopic ='IDS_TSK_ELNPROTOCOL' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=19 where displaytopic ='IDS_TSK_DYNAMICPROTOCOL' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=20 where displaytopic ='IDS_TSK_UPLOADPROTOCOLORDER' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=21 where displaytopic ='IDS_TSK_ORDERSHAREDBYMEPROTOCOL' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=22 where displaytopic ='IDS_TSK_ORDERSHAREDTOMEPROTOCOL' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=23 where displaytopic ='IDS_TSK_MOVEORDERSPROTOCOL' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=24 where displaytopic ='IDS_SCN_SHEETTEMPLATE' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=25 where displaytopic ='IDS_TSK_TEMPLATESHAREDBYME' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=26 where displaytopic ='IDS_TSK_TEMPLATESHAREDTOME' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=27 where displaytopic ='IDS_TSK_SHEETTEMPEXPORT' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=28 where displaytopic ='IDS_SCN_PROTOCOLTEMPLATE' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=29 where displaytopic ='IDS_TSK_NEWSTEP' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=30 where displaytopic ='IDS_TSK_EXPORTPDF' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=31 where displaytopic ='IDS_TSK_PROTOCOLTEMPSHAREBYME' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=32 where displaytopic ='IDS_TSK_PROTOCOLTEMPSHARETOME' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=33 where displaytopic ='IDS_TSK_LIMSTESTORDER' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=34 where displaytopic ='IDS_TSK_ELNANDRESEARCH' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=35 where displaytopic ='IDS_TSK_SHEET' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=36 where displaytopic ='IDS_TSK_PROTOCOL' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=37 where displaytopic ='IDS_TSK_LIMSTESTORDER' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=38 where displaytopic ='IDS_SCN_TASKMASTER' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=39 where displaytopic ='IDS_SCN_PROJECTMASTER' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=40 where displaytopic ='IDS_SCN_SAMPLEMASTER' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=41 where displaytopic ='IDS_SCN_INVENTORY' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=42 where displaytopic ='IDS_TSK_ADDREPO' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=43 where displaytopic ='IDS_TSK_EDITREPO' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=44 where displaytopic ='IDS_SCN_UNLOCKORDERS' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=45 where displaytopic ='IDS_SCN_USERGROUP' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=46 where displaytopic ='IDS_TSK_ACTDEACT' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=47 where displaytopic ='IDS_SCN_USERMASTER' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=48 where displaytopic ='IDS_TSK_UNLOCK' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=49 where displaytopic ='IDS_TSK_ACTDEACTUSERMASTER' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=50 where displaytopic ='IDS_TSK_RESETPASSWORD' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=51 where displaytopic ='IDS_TSK_RETIRE' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=52 where displaytopic ='IDS_TSK_IMPORTADS' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=53 where displaytopic ='IDS_SCN_USERRIGHTS' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=54 where displaytopic ='IDS_SCN_PROJECTTEAM' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=55 where displaytopic ='IDS_SCN_ORDERWORKLOW' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=56 where displaytopic ='IDS_SCN_TEMPLATEWORKFLOW' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=57 where displaytopic ='IDS_SCN_PASSWORDPOLICY' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=58 where displaytopic ='IDS_SCN_AUDITTRAILHIS' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=59 where displaytopic ='IDS_TSK_REVIEWHISTORY' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=60 where displaytopic ='IDS_TSK_REVIEW' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=61 where displaytopic ='IDS_TSK_CREATEARCHIVE' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=62 where displaytopic ='IDS_TSK_OPENARCHIVE' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=63 where displaytopic ='IDS_TSK_EXPORT' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=64 where displaytopic ='IDS_SCN_CFRSETTINGS' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=65 where displaytopic ='IDS_SCN_AUDITTRAILCONFIG' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=66 where displaytopic ='IDS_SCN_REPORTS' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=67 where displaytopic ='IDS_TSK_NEWDOCUMENT' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=68 where displaytopic ='IDS_TSK_NEWTEMP' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=69 where displaytopic ='IDS_TSK_GENERATEREPORT' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=70 where displaytopic ='IDS_TSK_OPENREPORT' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=71 where displaytopic ='IDS_TSK_IMPORTDOCX' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=72 where displaytopic ='IDS_SCN_PARSER' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=73 where displaytopic ='IDS_SCN_INSTRUMENTCATEGORY' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=74 where displaytopic ='IDS_SCN_INSTRUMENTMASTER' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=75 where displaytopic ='IDS_SCN_DELIMITER' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=76 where displaytopic ='IDS_SCN_METHODDELIMITER' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=77 where displaytopic ='IDS_SCN_METHODMASTER' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=78 where displaytopic ='IDS_SCN_UNITMASTER' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=79 where displaytopic ='IDS_TSK_DOWNLOADPDFEXCEL' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=80 where displaytopic ='IDS_SCN_SECTIONMASTER' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=81 where displaytopic ='IDS_TSK_DOWNLOADPDFEXCELSECTION' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=82 where displaytopic ='IDS_SCN_STORAGELOCATION' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=83 where displaytopic ='IDS_SCN_MATERIALCATEGORY' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=84 where displaytopic ='IDS_TSK_DOWNLOADMATERILACATEGORY' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=85 where displaytopic ='IDS_SCN_MATERIAL' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=86 where displaytopic ='IDS_SCN_MATERIALINVENTORY' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=87 where displaytopic ='IDS_SCN_LOGBOOK' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=88 where displaytopic ='IDS_TSK_ADDLOGBOOK' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=89 where displaytopic ='IDS_TSK_EDITLOGBOOK' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=90 where displaytopic ='IDS_TSK_RETIRELOGBOOK' and usergroupid_usergroupcode=1;
update lsusergrouprights set sequenceorder=91 where displaytopic ='IDS_TSK_REVIEWLOGBOOK' and usergroupid_usergroupcode=1;

update lsusergrouprightsmaster set status='1,1,0' where displaytopic='IDS_SCN_PROTOCOLTEMPLATE';
update lsusergrouprightsmaster set screate='NA' where orderno in (5,6,46,47,50,57,66,67,40,18,20,21,43,27,58,45,35);
update lsusergrouprightsmaster set screate='0',sedit='0' where orderno in (51,112);
update lsusergrouprightsmaster set screate='0' where orderno in (71,72);
update lsusergrouprightsmaster set sedit='0' where orderno in (11,12,13,95,106,107,112);
update lsusergrouprightsmaster set sdelete='NA' where orderno in (65,19);
update lsusergrouprightsmaster set sdelete='0' where orderno in (26,106,107,112);
update lsusergrouprightsmaster set sedit='NA' where orderno =53;
update lsusergrouprightsmaster set sedit='0' where orderno in (54,55,56);
update lsusergrouprightsmaster set sdelete='NA',sedit='NA' where orderno=112;

update lsusergrouprights set sedit='1' where modulename='IDS_MDL_PARSER';

update lsusergrouprights set sdelete='0' where displaytopic in ('IDS_SCN_CFRSETTINGS','IDS_SCN_MATERIAL') and sdelete='NA';
update lsusergrouprights set sdelete='1' where displaytopic in ('IDS_SCN_CFRSETTINGS','IDS_SCN_MATERIAL') and usergroupid_usergroupcode=1;
update lsusergrouprights set sdelete='NA' where displaytopic in('IDS_SCN_INVENTORY','IDS_SCN_PROJECTTEAM','IDS_TSK_REVIEW','IDS_TSK_REVIEWHISTORY','IDS_TSK_CREATEARCHIVE','IDS_SCN_LOGBOOK');
update lsusergrouprights set sedit='0' where displaytopic in ('IDS_SCN_TASKMASTER','IDS_SCN_PROJECTMASTER','IDS_SCN_SAMPLEMASTER','IDS_SCN_UNLOCKORDERS','IDS_SCN_MATERIAL','IDS_SCN_MATERIALINVENTORY') and sedit='NA';
update lsusergrouprights set sedit='1' where displaytopic in ('IDS_SCN_TASKMASTER','IDS_SCN_PROJECTMASTER','IDS_SCN_SAMPLEMASTER','IDS_SCN_UNLOCKORDERS','IDS_SCN_MATERIAL','IDS_SCN_MATERIALINVENTORY','IDS_SCN_LOGBOOK','IDS_SCN_INSTRUMENTMASTER') and usergroupid_usergroupcode=1;
update lsusergrouprights set screate='1' where displaytopic in ('IDS_TSK_SHEET','IDS_TSK_PROTOCOL','IDS_SCN_LOGBOOK','IDS_TSK_ELNANDRESEARCH','IDS_TSK_LIMSTESTORDER') and usergroupid_usergroupcode=1;
update lsusergrouprights set screate='NA' where displaytopic in ('IDS_SCN_SHEETORDERS','IDS_SCN_PROTOCOLORDERS','IDS_TSK_NEWSTEP','IDS_TSK_EXPORTPDF','IDS_TSK_ADDREPO','IDS_TSK_EDITREPO','IDS_TSK_IMPORTADS','IDS_TSK_NEWDOCUMENT','IDS_TSK_NEWTEMP','IDS_TSK_GENERATEREPORT','IDS_TSK_RETIRE','IDS_TSK_REVIEW','IDS_TSK_REVIEWHISTORY','IDS_TSK_CREATEARCHIVE');
update lsusergrouprights set sedit='NA' where displaytopic in ('IDS_TSK_REVIEW','IDS_TSK_REVIEWHISTORY','IDS_TSK_CREATEARCHIVE','IDS_SCN_LOGBOOK');
update lsusergrouprights set sallow='1' where displaytopic in ('IDS_TSK_ELNANDRESEARCH','IDS_TSK_LIMSTESTORDER') and usergroupid_usergroupcode=1;
UPDATE lsusergrouprights SET screate='0' WHERE displaytopic in ('IDS_TSK_SHEET','IDS_TSK_PROTOCOL','IDS_SCN_LOGBOOK') and screate='NA'  and usergroupid_usergroupcode!=1;


delete from lsusergrouprightsmaster where orderno in (42,44,7,14,15);
delete from lsusergrouprights where displaytopic in ('IDS_TSK_PARAMETERUSAGE','IDS_TSK_ACTIVEUSER','IDS_TSK_DOMAIN','IDS_TSK_TEMPLATEDESIGN');

Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(81,0,'IDS_MDL_LOGBOOK',6,'IDS_SCN_LOGBOOK','IDS_TSK_SAVE') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(82,0,'IDS_MDL_LOGBOOK',6,'IDS_SCN_LOGBOOK','IDS_TSK_EDIT') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(83,0,'IDS_MDL_LOGBOOK',6,'IDS_SCN_LOGBOOK','IDS_TSK_DELETE') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(84,0,'IDS_MDL_PARSER',12,'IDS_SCN_INSTRUMENTCATEGORY','IDS_TSK_SAVE') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(85,0,'IDS_MDL_PARSER',12,'IDS_SCN_INSTRUMENTCATEGORY','IDS_TSK_DELETE') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(86,0,'IDS_MDL_PARSER',12,'IDS_SCN_INSTRUMENTMASTER','IDS_TSK_SAVE') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(87,0,'IDS_MDL_PARSER',12,'IDS_SCN_INSTRUMENTMASTER','IDS_TSK_EDIT') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(88,0,'IDS_MDL_PARSER',12,'IDS_SCN_INSTRUMENTMASTER','IDS_TSK_DELETE') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(89,0,'IDS_MDL_PARSER',12,'IDS_SCN_DELIMITER','IDS_TSK_SAVE') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(90,0,'IDS_MDL_PARSER',12,'IDS_SCN_DELIMITER','IDS_TSK_EDIT') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(91,0,'IDS_MDL_PARSER',12,'IDS_SCN_DELIMITER','IDS_TSK_DELETE') ON CONFLICT(serialno)DO NOTHING;
Insert into LSaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(92,0,'IDS_MDL_PARSER',12,'IDS_SCN_METHODDELIMITER','IDS_TSK_SAVE') ON CONFLICT(serialno)DO NOTHING;
Insert into LSaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(93,0,'IDS_MDL_PARSER',12,'IDS_SCN_METHODDELIMITER','IDS_TSK_EDIT') ON CONFLICT(serialno)DO NOTHING;
Insert into LSaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(94,0,'IDS_MDL_PARSER',12,'IDS_SCN_METHODDELIMITER','IDS_TSK_DELETE') ON CONFLICT(serialno)DO NOTHING;
Insert into LSaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(95,0,'IDS_MDL_PARSER',12,'IDS_SCN_METHODMASTER','IDS_TSK_SAVE') ON CONFLICT(serialno)DO NOTHING;
Insert into LSaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(96,0,'IDS_MDL_PARSER',12,'IDS_SCN_METHODMASTER','IDS_TSK_EDIT') ON CONFLICT(serialno)DO NOTHING;
Insert into LSaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(97,0,'IDS_MDL_PARSER',12,'IDS_SCN_METHODMASTER','IDS_TSK_DELETE') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(98,0,'IDS_MDL_ORDERS',1,'IDS_SCN_SHEETORDERS','IDS_TSK_EXPORT') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(99,0,'IDS_MDL_ORDERS',1,'IDS_SCN_SHEETORDERS','IDS_TSK_EXPORTASJSON') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(100,0,'IDS_MDL_ORDERS',1,'IDS_SCN_SHEETORDERS','IDS_TSK_NEWFOLDER') ON CONFLICT(serialno)DO NOTHING;
 Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(101,0,'IDS_MDL_ORDERS',1,'IDS_SCN_SHEETORDERS','IDS_TSK_MOVEORDER') ON CONFLICT(serialno)DO NOTHING;
 Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(102,0,'IDS_MDL_ORDERS',1,'IDS_SCN_SHEETORDERS','IDS_TSK_UPLOAD') ON CONFLICT(serialno)DO NOTHING;
 Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(109,0,'IDS_MDL_ORDERS',2,'IDS_SCN_PROTOCOLORDERS','IDS_TSK_NEWFOLDER') ON CONFLICT(serialno)DO NOTHING;
 Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(111,0,'IDS_MDL_ORDERS',2,'IDS_SCN_PROTOCOLORDERS','IDS_TSK_UPLOAD') ON CONFLICT(serialno)DO NOTHING;
 Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(112,0,'IDS_MDL_ORDERS',2,'IDS_SCN_PROTOCOLORDERS','IDS_TSK_EXPORT') ON CONFLICT(serialno)DO NOTHING;
 Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(113,0,'IDS_MDL_TEMPLATES',3,'IDS_SCN_SHEETTEMPLATES','IDS_TSK_NEWTEMPLATE') ON CONFLICT(serialno)DO NOTHING;
 Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(114,0,'IDS_MDL_TEMPLATES',3,'IDS_SCN_SHEETTEMPLATES','IDS_TSK_EXPORT') ON CONFLICT(serialno)DO NOTHING;
 Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(115,0,'IDS_MDL_MASTERS',6,'IDS_SCN_UNLOCKORDERS','IDS_TSK_UNLOCK') ON CONFLICT(serialno)DO NOTHING;
 Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(116,0,'IDS_MDL_SETUP',7,'IDS_SCN_USERMASTER','IDS_TSK_RETIRE') ON CONFLICT(serialno)DO NOTHING;
 Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(117,0,'IDS_MDL_AUDITTRAIL',10,'IDS_SCN_AUDITTRAILHIS','IDS_TSK_REVIEW') ON CONFLICT(serialno)DO NOTHING;
 --Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(118,0,'IDS_MDL_INVENTORY',13,'IDS_SCN_UNITMASTER','IDS_TSK_SAVE') ON CONFLICT(serialno)DO NOTHING;
 --Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(119,0,'IDS_MDL_INVENTORY',13,'IDS_SCN_UNITMASTER','IDS_TSK_DELETE') ON CONFLICT(serialno)DO NOTHING;
 Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(120,0,'IDS_MDL_INVENTORY',13,'IDS_SCN_SECTIONMASTER','IDS_TSK_SAVE') ON CONFLICT(serialno)DO NOTHING;
 Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(121,0,'IDS_MDL_INVENTORY',13,'IDS_SCN_SECTIONMASTER','IDS_TSK_DELETE') ON CONFLICT(serialno)DO NOTHING;
 Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(122,0,'IDS_MDL_INVENTORY',13,'IDS_SCN_STORAGELOCATION','IDS_TSK_SAVE') ON CONFLICT(serialno)DO NOTHING;
 Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(123,0,'IDS_MDL_INVENTORY',13,'IDS_SCN_STORAGELOCATION','IDS_TSK_DELETE') ON CONFLICT(serialno)DO NOTHING;
 Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(124,0,'IDS_MDL_INVENTORY',13,'IDS_SCN_MATERIALCATEGORY','IDS_TSK_SAVE') ON CONFLICT(serialno)DO NOTHING;
 Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(125,0,'IDS_MDL_INVENTORY',13,'IDS_SCN_MATERIALCATEGORY','IDS_TSK_DELETE') ON CONFLICT(serialno)DO NOTHING;
 Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(126,0,'IDS_MDL_INVENTORY',13,'IDS_SCN_MATERIAL','IDS_TSK_SAVE') ON CONFLICT(serialno)DO NOTHING;
 Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(127,0,'IDS_MDL_INVENTORY',13,'IDS_SCN_MATERIAL','IDS_TSK_DELETE') ON CONFLICT(serialno)DO NOTHING;
 Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(128,0,'IDS_MDL_INVENTORY',13,'IDS_SCN_MATERIALINVENTORY','IDS_TSK_SAVE') ON CONFLICT(serialno)DO NOTHING;
 Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(129,0,'IDS_MDL_INVENTORY',13,'IDS_SCN_MATERIALINVENTORY','IDS_TSK_DELETE') ON CONFLICT(serialno)DO NOTHING;
 Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(130,0,'IDS_MDL_ORDERS',2,'IDS_SCN_PROTOCOLORDERS','IDS_TSK_MOVEORDER') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(132,0,'IDS_MDL_LOGBOOK',10,'IDS_SCN_LOGBOOK','IDS_TSK_ADDLOGBOOK') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(133,0,'IDS_MDL_LOGBOOK',10,'IDS_SCN_LOGBOOK','IDS_TSK_EDITLOGBOOK') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(134,0,'IDS_MDL_LOGBOOK',10,'IDS_SCN_LOGBOOK','IDS_TSK_REVIEWLOGBOOK') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(135,0,'IDS_MDL_LOGBOOK',10,'IDS_SCN_LOGBOOK','IDS_TSK_RETIRELOGBOOK') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(52,0,'IDS_MDL_ORDERS',10,'IDS_SCN_PROTOCOLORDERS','IDS_TSK_SAVE') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(53,0,'IDS_MDL_ORDERS',10,'IDS_SCN_PROTOCOLORDERS','IDS_TSK_COMPLETETASK') ON CONFLICT(serialno)DO NOTHING;


update lsaudittrailconfigmaster set taskname = 'IDS_TSK_SAVE' where taskname='Save';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_SENDTOLIMS' where taskname= 'Send to Lims';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_PROCESSORDER' where taskname='Process Order';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_REGISTER' where taskname='Register';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_REGPROTOCOL' where taskname='Register Protocol';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_PROCESSPROTOCOL' where taskname='Process Protocol';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_COMPLETETASK' where taskname='Complete task';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_DELETE' where taskname ='Delete';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_ADDNEWGROUP' where taskname='ADD NEW GROUP';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_EDIT' where taskname='Edit';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_ACTDEACT' where taskname ='Active/Deactive';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_ADDUSER' where taskname='ADD USER';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_UNLOCK' where taskname ='Unlock';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_RESETPASSWORD' where taskname='Reset Password';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_SUBMIT' where taskname='Submit';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_REVIEWHIS' where taskname='Review History';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_CREATEARCHIVE' where taskname='Create Archive';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_OPENARCHIVE' where taskname='Open Archive';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_EXPORT' where taskname='Export';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_SAVEAS' where taskname='Save As';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_LOCK' where taskname='Lock';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_ADDPROTOCOL' where taskname='Add Protocol';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_NEWSTEP' where taskname='New Step';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_SHAREWITHTEAM' where taskname='Share with Team';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_PARSEDATA' where taskname='Parse Data';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_IMPORTADS' where taskname='Import ADS';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_CONNECT' where taskname='Connect';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_NEWTEMP' where taskname='New Template';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_GENERATEREPORT' where taskname='Generate Report';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_CONFIGURE' where taskname='Configure';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_EXPORTPDF' where taskname='Export to PDF';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_DIRECTORYSAVE' where taskname='Directory Save';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_SAVE' where taskname='Save';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_SENDTOLIMS' where taskname= 'Send to Lims';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_PROCESSORDER' where taskname='Process Order';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_REGISTER' where taskname='Register';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_REGPROTOCOL' where taskname='Register Protocol';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_PROCESSPROTOCOL' where taskname='Process Protocol';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_COMPLETETASK' where taskname='Complete task';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_DELETE' where taskname ='Delete';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_ADDNEWGROUP' where taskname='ADD NEW GROUP';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_EDIT' where taskname='Edit';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_ACTDEACT' where taskname ='Active/Deactive';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_ADDUSER' where taskname='ADD USER';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_UNLOCK' where taskname ='Unlock';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_RESETPASSWORD' where taskname='Reset Password';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_SUBMIT' where taskname='Submit';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_REVIEWHIS' where taskname='Review History';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_CREATEARCHIVE' where taskname='Create Archive';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_OPENARCHIVE' where taskname='Open Archive';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_EXPORT' where taskname='Export';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_SAVEAS' where taskname='Save As';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_LOCK' where taskname='Lock';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_ADDPROTOCOL' where taskname='Add Protocol';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_NEWSTEP' where taskname='New Step';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_SHAREWITHTEAM' where taskname='Share with Team';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_PARSEDATA' where taskname='Parse Data';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_IMPORTADS' where taskname='Import ADS';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_CONNECT' where taskname='Connect';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_NEWTEMP' where taskname='New Template';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_GENERATEREPORT' where taskname='Generate Report';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_CONFIGURE' where taskname='Configure';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_EXPORTPDF' where taskname='Export to PDF';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_DIRECTORYSAVE' where taskname='Directory Save';
update lsaudittrailconfigmaster set taskname='IDS_TSK_ADDREPO' where serialno=71;
update lsaudittrailconfigmaster set taskname='IDS_TSK_EDITREPO' where serialno=72;
update lsaudittrailconfigmaster set taskname='IDS_TSK_EXPORTPDF' where serialno=61;
update lsaudittrailconfigmaster set taskname='IDS_TSK_SHEETSHARE' where serialno=99;
update lsaudittrailconfigmaster set taskname='IDS_TSK_CHANGEPASSWORD',modulename='IDS_MDL_SETUP',screenname='IDS_SCN_USERMASTER' where taskname='IDS_TSK_OPENORDER';
update lsaudittrailconfigmaster set taskname='IDS_TSK_PROTOCOLSHARE' where serialno=107;
update lsaudittrailconfigmaster set taskname='IDS_TSK_SHEETTEMPSHARE',screenname='IDS_SCN_SHEETTEMPLATES',modulename='IDS_MDL_TEMPLATES' where serialno=108;
update lsaudittrailconfigmaster set taskname='IDS_TSK_RETIRELOG' where serialno=83;

update lsaudittrailconfigmaster set modulename='IDS_MDL_ORDERS',screenname='IDS_SCN_SHEETORDERS' where serialno=101;
update lsaudittrailconfigmaster set modulename='IDS_MDL_SETUP' where taskname='IDS_TSK_CHANGEPASSWORD';
update lsaudittrailconfigmaster set modulename='IDS_MDL_ORDERS' where modulename in ('Register Orders & Execute','Protocol Order And Register');
update lsaudittrailconfigmaster set modulename='IDS_MDL_TEMPLATES' where modulename in ('Protocols','Sheet Creation','Sheet Setting');
update lsaudittrailconfigmaster set modulename='IDS_MDL_MASTERS' where modulename ='Base Master';
update lsaudittrailconfigmaster set modulename='IDS_MDL_SETUP' where modulename ='User Management';
update lsaudittrailconfigmaster set modulename='IDS_MDL_AUDITTRAIL' where modulename ='Audit Trail';
update lsaudittrailconfigmaster set modulename='IDS_MDL_REPORTS' where modulename ='Reports';
update lsaudittrailconfigmaster set modulename='IDS_MDL_ORDERS',screenname='IDS_SCN_SHEETORDERS' where serialno=101;

update lsaudittrailconfigmaster set screenname='IDS_SCN_SHEETORDERS' where screenname='Register Orders & Execute';
update lsaudittrailconfigmaster set screenname='IDS_SCN_SHEETTEMPLATES' where screenname='Sheet Creation';
update lsaudittrailconfigmaster set screenname='IDS_SCN_TASKMASTER' where screenname='Test Master';
update lsaudittrailconfigmaster set screenname='IDS_SCN_PROJECTMASTER' where screenname='Project Master';
update lsaudittrailconfigmaster set screenname='IDS_SCN_SAMPLEMASTER' where screenname='Sample Master';
update lsaudittrailconfigmaster set screenname='IDS_SCN_INSTRUMENTMASTER' where screenname='Instrument Master';
update lsaudittrailconfigmaster set screenname='IDS_SCN_USERGROUP' where screenname='User Group';
update lsaudittrailconfigmaster set screenname='IDS_SCN_USERMASTER' where screenname='User Master';
update lsaudittrailconfigmaster set screenname='IDS_SCN_USERRIGHTS' where screenname='User Rights';
update lsaudittrailconfigmaster set screenname='IDS_SCN_PROJECTTEAM' where screenname='Project Team';
update lsaudittrailconfigmaster set screenname='IDS_SCN_ORDERWORKFLOW' where screenname='Order Workflow';
update lsaudittrailconfigmaster set screenname='IDS_SCN_SHEETWORKFLOW' where screenname='Sheet Workflow';
update lsaudittrailconfigmaster set screenname='IDS_SCN_DOMAIN' where screenname='Domain';
update lsaudittrailconfigmaster set screenname='IDS_SCN_PASSWORDPOLICY' where screenname='Password Policy';
update lsaudittrailconfigmaster set screenname='IDS_SCN_CHANGEPASSWORD' where screenname='Change Password';
update lsaudittrailconfigmaster set screenname='IDS_SCN_AUDITTRAILHIS' where screenname='Audit Trail History';
update lsaudittrailconfigmaster set screenname='IDS_SCN_CFRSETTINGS' where screenname='CFR Settings';
update lsaudittrailconfigmaster set screenname='IDS_SCN_PROTOCOLORDERS' where screenname='Protocol Order And Register';
update lsaudittrailconfigmaster set screenname='IDS_SCN_PROTOCOLTEMP' where screenname='Protocols';
update lsaudittrailconfigmaster set screenname='IDS_SCN_TEMPLATEMAPPING' where screenname='Sheet Setting';
update lsaudittrailconfigmaster set screenname='IDS_SCN_REPOSITORY' where screenname='Repository';
update lsaudittrailconfigmaster set screenname='IDS_SCN_INVENTORY' where screenname='Inventory';
update lsaudittrailconfigmaster set screenname='IDS_SCN_SITEMASTER' where screenname='Site Master';
update lsaudittrailconfigmaster set screenname='IDS_SCN_AUDITTRAILCONFIG' where screenname='Audit Trial Configuration';
update lsaudittrailconfigmaster set screenname='IDS_SCN_DELIMITER' where screenname='Delimiter';
update lsaudittrailconfigmaster set screenname='IDS_SCN_REPORTS' where screenname='Reports';
update lsaudittrailconfigmaster set screenname='IDS_SCN_USERMASTER' where screenname='IDS_SCN_CHANGEPASSWORD';

delete from lsaudittrailconfigmaster where serialno in (52,53,105,108,131,30,31,63);


update lsaudittrailconfiguration set screenname='IDS_SCN_SHEETORDERS' WHERE screenname='Register Orders & Execute';
update lsaudittrailconfiguration set screenname='IDS_SCN_PROTOCOLORDERS' WHERE screenname='Protocol Order And Register';
update lsaudittrailconfiguration set screenname='IDS_SCN_INSTRUMENTMASTER' WHERE screenname='Instrument Master';
update lsaudittrailconfiguration set screenname='IDS_SCN_SAMPLEMASTER' WHERE screenname='Sample Master';
update lsaudittrailconfiguration set screenname='IDS_SCN_TASKMASTER' WHERE screenname='Test Master';
update lsaudittrailconfiguration set screenname='IDS_SCN_PROJECTMASTER' WHERE screenname='Project Master';
update lsaudittrailconfiguration set screenname='IDS_SCN_INVENTORY' WHERE screenname='Inventory';
update lsaudittrailconfiguration set screenname='IDS_SCN_REPOSITORY' WHERE screenname='Repository';
update lsaudittrailconfiguration set screenname='IDS_SCN_USERMASTER' WHERE screenname='User Master';
update lsaudittrailconfiguration set screenname='IDS_SCN_ORDERWORKFLOW' WHERE screenname='Order Workflow';
update lsaudittrailconfiguration set screenname='IDS_SCN_SITEMASTER' WHERE screenname='Site Master';
update lsaudittrailconfiguration set screenname='IDS_SCN_SHEETWORKFLOW' WHERE screenname='Sheet Workflow';
update lsaudittrailconfiguration set screenname='IDS_SCN_USERGROUP' WHERE screenname='User Group';
update lsaudittrailconfiguration set screenname='IDS_SCN_PROJECTTEAM' WHERE screenname='Project Team';
update lsaudittrailconfiguration set screenname='IDS_SCN_USERRIGHTS' WHERE screenname='User Rights';
update lsaudittrailconfiguration set screenname='IDS_SCN_DOMAIN' WHERE screenname='Domain';
update lsaudittrailconfiguration set screenname='IDS_SCN_PASSWORDPOLICY' WHERE screenname='Password Policy';
update lsaudittrailconfiguration set screenname='IDS_SCN_CHANGEPASSWORD' WHERE screenname='Change Password';
update lsaudittrailconfiguration set screenname='IDS_SCN_AUDITTRAILHIS' WHERE screenname='Audit Trail History';
update lsaudittrailconfiguration set screenname='IDS_SCN_CFRSETTINGS' WHERE screenname='CFR Settings';
update lsaudittrailconfiguration set screenname='IDS_SCN_AUDITTRAILCONFIG' WHERE screenname='Audit Trial Configuration';
update lsaudittrailconfiguration set screenname='IDS_SCN_SHEETTEMPLATES' WHERE screenname='Sheet Creation';
update lsaudittrailconfiguration set screenname='IDS_SCN_PROTOCOLTEMP' WHERE screenname='Protocols';
update lsaudittrailconfiguration set screenname='IDS_SCN_REPORTS' WHERE screenname='Reports';
update lsaudittrailconfiguration set screenname='IDS_SCN_TEMPLATEMAPPING' WHERE screenname='Sheet Setting';
update lsaudittrailconfiguration set screenname='IDS_SCN_DELIMITER' WHERE screenname='Delimiter';
update lsaudittrailconfiguration set screenname='IDS_SCN_METHODDELIMITER' WHERE screenname='MethodDelimiter';
update lsaudittrailconfiguration set screenname='IDS_SCN_METHODMASTER' WHERE screenname='MethodMaster';
update lsaudittrailconfiguration set screenname='IDS_SCN_REPORTS' where modulename='IDS_MDL_REPORTS';

update lsaudittrailconfiguration set taskname = 'IDS_TSK_SAVE' where taskname='Save';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_SENDTOLIMS' where taskname='Send to Lims';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_PROCESSORDER' where taskname='Process Order';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_REGISTER' where taskname='Register';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_REGPROTOCOL' where taskname='Register Protocol';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_PROCESSPROTOCOL' where taskname='Process Protocol';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_COMPLETETASK' where taskname='Complete task';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_DELETE' where taskname ='Delete';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_ADDNEWGROUP' where taskname='ADD NEW GROUP';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_EDIT' where taskname='Edit';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_ACTDEACT' where taskname ='Active/Deactive';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_ADDUSER' where taskname='ADD USER';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_UNLOCK' where taskname ='Unlock';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_RESETPASSWORD' where taskname='Reset Password';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_SUBMIT' where taskname='Submit';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_REVIEWHIS' where taskname='Review History';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_CREATEARCHIVE' where taskname='Create Archive';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_OPENARCHIVE' where taskname='Open Archive';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_EXPORT' where taskname='Export';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_SAVEAS' where taskname='Save As';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_LOCK' where taskname='Lock';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_ADDPROTOCOL' where taskname='Add Protocol';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_NEWSTEP' where taskname='New Step';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_SHAREWITHTEAM' where taskname='Share with Team';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_PARSEDATA' where taskname='Parse Data';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_IMPORTADS' where taskname='Import ADS';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_CONNECT' where taskname='Connect';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_NEWTEMP' where taskname='New Template';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_GENERATEREPORT' where taskname='Generate Report';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_CONFIGURE' where taskname='Configure';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_DIRECTORYSAVE' where taskname='Directory Save';

update lsaudittrailconfigmaster set screenname='IDS_SCN_INVENTORY' where serialno in (71,72);
update lsaudittrailconfigmaster set modulename='IDS_MDL_INVENTORY' where screenname='IDS_SCN_INVENTORY';
update lsaudittrailconfigmaster set screenname='IDS_SCN_TEMPLATEWORKFLOW' where screenname='IDS_SCN_SHEETWORKFLOW';
update lsaudittrailconfiguration set screenname='IDS_SCN_TEMPLATEWORKFLOW' where screenname='IDS_SCN_SHEETWORKFLOW';

delete from lsaudittrailconfiguration where screenname='IDS_SCN_INVENTORY';
delete from lsaudittrailconfigmaster where screenname='IDS_SCN_INVENTORY';
delete from lsaudittrailconfiguration where taskname='IDS_TSK_SUBMIT' and screenname='IDS_SCN_USERMASTER';

Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(136,0,'IDS_MDL_TEMPLATES',6,'IDS_SCN_SHEETTEMPLATES','IDS_TSK_EDITSHEET') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(137,0,'IDS_MDL_TEMPLATES',6,'IDS_SCN_PROTOCOLTEMP','IDS_TSK_EDITPROTOCOL') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(138,0,'IDS_MDL_MASTERS',6,'IDS_SCN_TASKMASTER','IDS_TSK_EDITTASK') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(139,0,'IDS_MDL_MASTERS',6,'IDS_SCN_PROJECTMASTER','IDS_TSK_EDITPROJECT') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(140,0,'IDS_MDL_MASTERS',6,'IDS_SCN_SAMPLEMASTER','IDS_TSK_EDITSAMPLE') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(141,0,'IDS_MDL_INVENTORY',6,'IDS_SCN_UNITMASTER','IDS_TSK_EDIT') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(142,0,'IDS_MDL_INVENTORY',6,'IDS_SCN_SECTIONMASTER','IDS_TSK_EDIT') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(143,0,'IDS_MDL_INVENTORY',6,'IDS_SCN_STORAGELOCATION','IDS_TSK_EDIT') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(144,0,'IDS_MDL_INVENTORY',6,'IDS_SCN_MATERIALCATEGORY','IDS_TSK_EDIT') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(145,0,'IDS_MDL_INVENTORY',6,'IDS_SCN_MATERIAL','IDS_TSK_EDIT') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(146,0,'IDS_MDL_INVENTORY',6,'IDS_SCN_MATERIALINVENTORY','IDS_TSK_EDIT') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(147,0,'IDS_MDL_PARSER',6,'IDS_SCN_INSTRUMENTCATEGORY','IDS_TSK_EDIT') ON CONFLICT(serialno)DO NOTHING;


update lsaudittrailconfigmaster set ordersequnce=1 where serialno=100;
 update lsaudittrailconfigmaster set ordersequnce=2 where serialno=2;
 update lsaudittrailconfigmaster set ordersequnce=3 where serialno=102;
 update lsaudittrailconfigmaster set ordersequnce=4 where serialno=38;
 update lsaudittrailconfigmaster set ordersequnce=5 where serialno=40;
 update lsaudittrailconfigmaster set ordersequnce=6 where serialno=98;
 update lsaudittrailconfigmaster set ordersequnce=7 where serialno=99;
 update lsaudittrailconfigmaster set ordersequnce=8 where serialno=1;
 update lsaudittrailconfigmaster set ordersequnce=9 where serialno=101;
 update lsaudittrailconfigmaster set ordersequnce=11 where serialno=76;
 update lsaudittrailconfigmaster set ordersequnce=12 where serialno=39;
 update lsaudittrailconfigmaster set ordersequnce=13 where serialno=64;
 update lsaudittrailconfigmaster set ordersequnce=14 where serialno=109;
 update lsaudittrailconfigmaster set ordersequnce=15 where serialno=77;
 update lsaudittrailconfigmaster set ordersequnce=16 where serialno=111;
 update lsaudittrailconfigmaster set ordersequnce=17 where serialno=52;
 update lsaudittrailconfigmaster set ordersequnce=18 where serialno=53;
 update lsaudittrailconfigmaster set ordersequnce=19 where serialno=112;
 update lsaudittrailconfigmaster set ordersequnce=20 where serialno=65;
 update lsaudittrailconfigmaster set ordersequnce=21 where serialno=130;
 update lsaudittrailconfigmaster set ordersequnce=22 where serialno=107;
 update lsaudittrailconfigmaster set ordersequnce=23 where serialno=113;
 update lsaudittrailconfigmaster set ordersequnce=24 where serialno=136;
 update lsaudittrailconfigmaster set ordersequnce=25 where serialno=3;
 update lsaudittrailconfigmaster set ordersequnce=26 where serialno=41;
 update lsaudittrailconfigmaster set ordersequnce=27 where serialno=114;
 update lsaudittrailconfigmaster set ordersequnce=28 where serialno=108;
 update lsaudittrailconfigmaster set ordersequnce=29 where serialno=58;
 update lsaudittrailconfigmaster set ordersequnce=30 where serialno=137;
 update lsaudittrailconfigmaster set ordersequnce=31 where serialno=59;
 update lsaudittrailconfigmaster set ordersequnce=31 where serialno=61;
 update lsaudittrailconfigmaster set ordersequnce=32 where serialno=60;
 update lsaudittrailconfigmaster set ordersequnce=33 where serialno=10;
 update lsaudittrailconfigmaster set ordersequnce=34 where serialno=4;
 update lsaudittrailconfigmaster set ordersequnce=35 where serialno=138;
 update lsaudittrailconfigmaster set ordersequnce=36 where serialno=5;
 update lsaudittrailconfigmaster set ordersequnce=37 where serialno=7;
 update lsaudittrailconfigmaster set ordersequnce=38 where serialno=139;
 update lsaudittrailconfigmaster set ordersequnce=39 where serialno=6;
 update lsaudittrailconfigmaster set ordersequnce=40 where serialno=9;
 update lsaudittrailconfigmaster set ordersequnce=41 where serialno=140;
 update lsaudittrailconfigmaster set ordersequnce=42 where serialno=8;
 update lsaudittrailconfigmaster set ordersequnce=43 where serialno=30;
 update lsaudittrailconfigmaster set ordersequnce=44 where serialno=115;
 update lsaudittrailconfigmaster set ordersequnce=45 where serialno=50;
 update lsaudittrailconfigmaster set ordersequnce=46 where serialno=51;
 update lsaudittrailconfigmaster set ordersequnce=47 where serialno=11;
 update lsaudittrailconfigmaster set ordersequnce=48 where serialno=12;
 update lsaudittrailconfigmaster set ordersequnce=49 where serialno=13;
 update lsaudittrailconfigmaster set ordersequnce=50 where serialno=14;
 update lsaudittrailconfigmaster set ordersequnce=51 where serialno=15;
 update lsaudittrailconfigmaster set ordersequnce=52 where serialno=16;
 update lsaudittrailconfigmaster set ordersequnce=53 where serialno=17;
 update lsaudittrailconfigmaster set ordersequnce=54 where serialno=18;
 update lsaudittrailconfigmaster set ordersequnce=55 where serialno=116;
 update lsaudittrailconfigmaster set ordersequnce=56 where serialno=54;
 update lsaudittrailconfigmaster set ordersequnce=57 where serialno=55;
 update lsaudittrailconfigmaster set ordersequnce=58 where serialno=131;
 update lsaudittrailconfigmaster set ordersequnce=59 where serialno=19;
 update lsaudittrailconfigmaster set ordersequnce=60 where serialno=20;
 update lsaudittrailconfigmaster set ordersequnce=61 where serialno=21;
 update lsaudittrailconfigmaster set ordersequnce=62 where serialno=22;
 update lsaudittrailconfigmaster set ordersequnce=63 where serialno=56;
 update lsaudittrailconfigmaster set ordersequnce=64 where serialno=32;
 update lsaudittrailconfigmaster set ordersequnce=65 where serialno=57;
 update lsaudittrailconfigmaster set ordersequnce=66 where serialno=33;
 update lsaudittrailconfigmaster set ordersequnce=67 where serialno=34;
 update lsaudittrailconfigmaster set ordersequnce=68 where serialno=35;
 update lsaudittrailconfigmaster set ordersequnce=69 where serialno=84;
 update lsaudittrailconfigmaster set ordersequnce=70 where serialno=147;
 update lsaudittrailconfigmaster set ordersequnce=71 where serialno=85;
 update lsaudittrailconfigmaster set ordersequnce=72 where serialno=86;
 update lsaudittrailconfigmaster set ordersequnce=73 where serialno=87;
 update lsaudittrailconfigmaster set ordersequnce=74 where serialno=88;
 update lsaudittrailconfigmaster set ordersequnce=75 where serialno=89;
 update lsaudittrailconfigmaster set ordersequnce=76 where serialno=90;
 update lsaudittrailconfigmaster set ordersequnce=77 where serialno=91;
 update lsaudittrailconfigmaster set ordersequnce=78 where serialno=92;
 update lsaudittrailconfigmaster set ordersequnce=79 where serialno=93;
 update lsaudittrailconfigmaster set ordersequnce=80 where serialno=94;
 update lsaudittrailconfigmaster set ordersequnce=81 where serialno=95;
 update lsaudittrailconfigmaster set ordersequnce=82 where serialno=96;
 update lsaudittrailconfigmaster set ordersequnce=83 where serialno=97;
 update lsaudittrailconfigmaster set ordersequnce=84 where serialno=23;
 update lsaudittrailconfigmaster set ordersequnce=85 where serialno=117;
 update lsaudittrailconfigmaster set ordersequnce=86 where serialno=24;
 update lsaudittrailconfigmaster set ordersequnce=87 where serialno=25;
 update lsaudittrailconfigmaster set ordersequnce=88 where serialno=26;
 update lsaudittrailconfigmaster set ordersequnce=89 where serialno=27;
 update lsaudittrailconfigmaster set ordersequnce=90 where serialno=28;
 update lsaudittrailconfigmaster set ordersequnce=91 where serialno=42;
 update lsaudittrailconfigmaster set ordersequnce=92 where serialno=47;
 update lsaudittrailconfigmaster set ordersequnce=93 where serialno=49;
 update lsaudittrailconfigmaster set ordersequnce=94 where serialno=45;
 update lsaudittrailconfigmaster set ordersequnce=95 where serialno=48;
 update lsaudittrailconfigmaster set ordersequnce=96 where serialno=43;
 update lsaudittrailconfigmaster set ordersequnce=97 where serialno=46;
 update lsaudittrailconfigmaster set ordersequnce=98 where serialno=118;
 update lsaudittrailconfigmaster set ordersequnce=99 where serialno=141;
 update lsaudittrailconfigmaster set ordersequnce=100 where serialno=119;
 update lsaudittrailconfigmaster set ordersequnce=107 where serialno=120;
 update lsaudittrailconfigmaster set ordersequnce=108 where serialno=142;
 update lsaudittrailconfigmaster set ordersequnce=109 where serialno=121;
 update lsaudittrailconfigmaster set ordersequnce=110 where serialno=122;
 update lsaudittrailconfigmaster set ordersequnce=111 where serialno=143;
 update lsaudittrailconfigmaster set ordersequnce=112 where serialno=123;
 update lsaudittrailconfigmaster set ordersequnce=113 where serialno=124;
 update lsaudittrailconfigmaster set ordersequnce=114 where serialno=144;
 update lsaudittrailconfigmaster set ordersequnce=115 where serialno=125;
 update lsaudittrailconfigmaster set ordersequnce=116 where serialno=126;
 update lsaudittrailconfigmaster set ordersequnce=117 where serialno=145;
 update lsaudittrailconfigmaster set ordersequnce=118 where serialno=127;
 update lsaudittrailconfigmaster set ordersequnce=119 where serialno=128;
 update lsaudittrailconfigmaster set ordersequnce=120 where serialno=146;
 update lsaudittrailconfigmaster set ordersequnce=121 where serialno=129;
 update lsaudittrailconfigmaster set ordersequnce=122 where serialno=132;
 update lsaudittrailconfigmaster set ordersequnce=123 where serialno=133;
 update lsaudittrailconfigmaster set ordersequnce=124 where serialno=135;
 update lsaudittrailconfigmaster set ordersequnce=125 where serialno=134;
 update lsaudittrailconfigmaster set ordersequnce=126 where serialno=81;
 update lsaudittrailconfigmaster set ordersequnce=127 where serialno=82;
 update lsaudittrailconfigmaster set ordersequnce=128 where serialno=83;
 
update lsaudittrailconfiguration set ordersequnce=1 where screenname='IDS_SCN_SHEETORDERS';
update lsaudittrailconfiguration set ordersequnce=2 where screenname='IDS_SCN_PROTOCOLORDERS';
update lsaudittrailconfiguration set ordersequnce=3 where screenname='IDS_SCN_SHEETTEMPLATES';
update lsaudittrailconfiguration set ordersequnce=4 where screenname='IDS_SCN_PROTOCOLTEMP';
update lsaudittrailconfiguration set ordersequnce=5 where screenname='IDS_SCN_TEMPLATEMAPPING';
update lsaudittrailconfiguration set ordersequnce=6 where screenname='IDS_SCN_TASKMASTER';
update lsaudittrailconfiguration set ordersequnce=7 where screenname='IDS_SCN_PROJECTMASTER';
update lsaudittrailconfiguration set ordersequnce=8 where screenname='IDS_SCN_SAMPLEMASTER';
update lsaudittrailconfiguration set ordersequnce=9 where screenname='IDS_SCN_UNLOCKORDERS';
update lsaudittrailconfiguration set ordersequnce=10 where screenname='IDS_SCN_SITEMASTER';
update lsaudittrailconfiguration set ordersequnce=11 where screenname='IDS_SCN_USERGROUP';
update lsaudittrailconfiguration set ordersequnce=12 where screenname='IDS_SCN_USERMASTER';
update lsaudittrailconfiguration set ordersequnce=13 where screenname='IDS_SCN_USERRIGHTS';
update lsaudittrailconfiguration set ordersequnce=14 where screenname='IDS_SCN_PROJECTTEAM';
update lsaudittrailconfiguration set ordersequnce=15 where screenname='IDS_SCN_ORDERWORKFLOW';
update lsaudittrailconfiguration set ordersequnce=16 where screenname='IDS_SCN_TEMPLATEWORKFLOW';
update lsaudittrailconfiguration set ordersequnce=17 where screenname='IDS_SCN_DOMAIN';
update lsaudittrailconfiguration set ordersequnce=18 where screenname='IDS_SCN_PASSWORDPOLICY';
update lsaudittrailconfiguration set ordersequnce=19 where screenname='IDS_SCN_INSTRUMENTCATEGORY';
update lsaudittrailconfiguration set ordersequnce=20 where screenname='IDS_SCN_INSTRUMENTMASTER';
update lsaudittrailconfiguration set ordersequnce=21 where screenname='IDS_SCN_DELIMITER';
update lsaudittrailconfiguration set ordersequnce=22 where screenname='IDS_SCN_METHODDELIMITER';
update lsaudittrailconfiguration set ordersequnce=23 where screenname='IDS_SCN_METHODMASTER';
update lsaudittrailconfiguration set ordersequnce=24 where screenname='IDS_SCN_AUDITTRAILHIS';
update lsaudittrailconfiguration set ordersequnce=25 where screenname='IDS_SCN_CFRSETTINGS';
update lsaudittrailconfiguration set ordersequnce=26 where screenname='IDS_SCN_AUDITTRAILCONFIG';
update lsaudittrailconfiguration set ordersequnce=27 where screenname='IDS_SCN_REPORTS';
update lsaudittrailconfiguration set ordersequnce=28 where screenname='IDS_SCN_UNITMASTER';
update lsaudittrailconfiguration set ordersequnce=32 where screenname='IDS_SCN_SECTIONMASTER';
update lsaudittrailconfiguration set ordersequnce=33 where screenname='IDS_SCN_STORAGELOCATION';
update lsaudittrailconfiguration set ordersequnce=34 where screenname='IDS_SCN_MATERIALCATEGORY';
update lsaudittrailconfiguration set ordersequnce=35 where screenname='IDS_SCN_MATERIAL';
update lsaudittrailconfiguration set ordersequnce=36 where screenname='IDS_SCN_MATERIALINVENTORY';
update lsaudittrailconfiguration set ordersequnce=37 where screenname='IDS_SCN_INVENTORY';
update lsaudittrailconfiguration set ordersequnce=38 where screenname='IDS_SCN_LOGBOOK';

delete from lsaudittrailconfigmaster where serialno in (82,83,36);
delete from lsaudittrailconfiguration where taskname in ('IDS_TSK_RETIRELOG','IDS_TSK_EDIT') and modulename='IDS_MDL_LOGBOOK';

update lsaudittrailconfigmaster set taskname='IDS_TSK_TASKSAVE' where serialno=4;
update lsaudittrailconfigmaster set taskname='IDS_TSK_PROJECTSAVE' where serialno=7;
update lsaudittrailconfigmaster set taskname='IDS_TSK_SAMPLESAVE' where serialno=9;
update lsaudittrailconfigmaster set taskname='IDS_TSK_DELETED' where serialno in (21,34,56,57,28);
update lsaudittrailconfigmaster set taskname='IDS_TSK_ADD' where serialno in (81,84,86,89,92,95,126,128,118,120,122,124);

update lsaudittrailconfiguration set taskname='IDS_TSK_TASKSAVE' where screenname='IDS_SCN_TASKMASTER' and taskname='IDS_TSK_SAVE';
update lsaudittrailconfiguration set taskname='IDS_TSK_PROJECTSAVE' where screenname='IDS_SCN_PROJECTMASTER' and taskname='IDS_TSK_SAVE';
update lsaudittrailconfiguration set taskname='IDS_TSK_SAMPLESAVE' where screenname='IDS_SCN_SAMPLEMASTER' and taskname='IDS_TSK_SAVE';
update lsaudittrailconfiguration set taskname='IDS_TSK_DELETED' where screenname in ('IDS_SCN_DOMAIN','IDS_SCN_PROJECTTEAM','IDS_SCN_TEMPLATEWORKFLOW','IDS_SCN_ORDERWORKFLOW','IDS_SCN_CFRSETTINGS') and taskname='IDS_TSK_DELETE';
update lsaudittrailconfiguration set taskname='IDS_TSK_ADD' where modulename in ('IDS_MDL_PARSER','IDS_MDL_LOGBOOK','IDS_MDL_INVENTORY') and taskname='IDS_TSK_SAVE';

update lsusergrouprightsmaster set status='1,0,0' where orderno in (71,72);

update LStestmasterlocal set teststatus = 'A' where status = 1;
update LStestmasterlocal set teststatus = 'D' where status = -1;

update lsprojectmaster set projectstatus = 'A' where status = 1;
update lsprojectmaster set projectstatus = 'D' where status = -1;

update lssamplemaster set samplestatus = 'A' where status = 1;
update lssamplemaster set samplestatus = 'D' where status = -1;

--update samplestoragelocation set sitekey = 1 where sitekey = -1;
--update materialcategory set nsitecode = 1 where materialcategory.nsitecode = -1;
update material set nsitecode = 1 where nsitecode is NULL;
update materialinventory set nsitecode = 1 where nsitecode is NULL;
ALTER TABLE IF Exists lslogbooks ALTER COLUMN logbookcode TYPE numeric(17,0) USING logbookcode::numeric;

ALTER TABLE IF Exists lslogbooks ADD COLUMN IF NOT EXISTS reviewedby character varying(255);
ALTER TABLE IF Exists lslogbooks ADD COLUMN IF NOT EXISTS revieweddate timestamp without time zone;


ALTER TABLE IF Exists delimiter ADD COLUMN IF NOT EXISTS defaultvalue integer;

update delimiter set defaultvalue = 1 where delimitername='None'and defaultvalue is null;

update delimiter set defaultvalue = 1 where delimitername='Result without space'and defaultvalue is null;

update delimiter set defaultvalue = 1 where delimitername='Result with space'and defaultvalue is null;

update delimiter set defaultvalue = 1 where delimitername='Colon' and defaultvalue is null;

update delimiter set defaultvalue = 1 where delimitername='Comma'  and defaultvalue is null;

update delimiter set defaultvalue = 1 where delimitername='Space' and defaultvalue is null;

update delimiter set defaultvalue = 1 where delimitername='Split Dot' and defaultvalue is null;

update delimiter set defaultvalue = 1 where delimitername='Merge Dot' and defaultvalue is null;

update delimiter set defaultvalue = 1 where delimitername='Slash' and defaultvalue is null;

ALTER TABLE IF Exists method ADD COLUMN IF NOT EXISTS filename varchar(255);

update method set filename = instrawdataurl where filename is null;

update delimiter set lssitemaster_sitecode=1 where lssitemaster_sitecode is null;
update methoddelimiter set lssitemaster_sitecode=1 where lssitemaster_sitecode is null;
update instrumentcategory set lssitemaster_sitecode=1 where lssitemaster_sitecode is null;

--ALTER TABLE IF Exists methoddelimiter ADD COLUMN IF NOT EXISTS defaultvalue integer;

update methoddelimiter set defaultvalue =1 where delimiterkey = 1 and parsermethodkey =1;

--datablock

INSERT into methoddelimiter (status, usercode, delimiterkey, parsermethodkey,defaultvalue,lssitemaster_sitecode) SELECT 1,1,2,1,1,1 WHERE NOT EXISTS (select * from methoddelimiter where delimiterkey = 2 and parsermethodkey =1 and lssitemaster_sitecode=1);

INSERT into methoddelimiter (status, usercode, delimiterkey, parsermethodkey,defaultvalue,lssitemaster_sitecode) SELECT 1,1,3,1,1,1 WHERE NOT EXISTS (select * from methoddelimiter where delimiterkey = 3 and parsermethodkey =1 and lssitemaster_sitecode=1);

INSERT into methoddelimiter (status, usercode, delimiterkey, parsermethodkey,defaultvalue,lssitemaster_sitecode) SELECT 1,1,5,1,1,1 WHERE NOT EXISTS (select * from methoddelimiter where delimiterkey = 5 and parsermethodkey =1 and lssitemaster_sitecode=1);

--for split

INSERT into methoddelimiter (status, usercode, delimiterkey, parsermethodkey,defaultvalue,lssitemaster_sitecode) SELECT 1,1,2,7,1,1 WHERE NOT EXISTS (select * from methoddelimiter where delimiterkey = 2 and parsermethodkey =7 and lssitemaster_sitecode=1);

INSERT into methoddelimiter (status, usercode, delimiterkey, parsermethodkey,defaultvalue,lssitemaster_sitecode) SELECT 1,1,3,7,1,1 WHERE NOT EXISTS (select * from methoddelimiter where delimiterkey = 3 and parsermethodkey =7 and lssitemaster_sitecode=1);

INSERT into methoddelimiter (status, usercode, delimiterkey, parsermethodkey,defaultvalue,lssitemaster_sitecode) SELECT 1,1,4,7,1,1 WHERE NOT EXISTS (select * from methoddelimiter where delimiterkey = 4 and parsermethodkey =7 and lssitemaster_sitecode=1);

INSERT into methoddelimiter (status, usercode, delimiterkey, parsermethodkey,defaultvalue,lssitemaster_sitecode) SELECT 1,1,5,7,1,1 WHERE NOT EXISTS (select * from methoddelimiter where delimiterkey = 5 and parsermethodkey =7 and lssitemaster_sitecode=1);

INSERT into methoddelimiter (status, usercode, delimiterkey, parsermethodkey,defaultvalue,lssitemaster_sitecode) SELECT 1,1,6,7,1,1 WHERE NOT EXISTS (select * from methoddelimiter where delimiterkey = 6 and parsermethodkey =7 and lssitemaster_sitecode=1);

INSERT into methoddelimiter (status, usercode, delimiterkey, parsermethodkey,defaultvalue,lssitemaster_sitecode) SELECT 1,1,7,7,1,1 WHERE NOT EXISTS (select * from methoddelimiter where delimiterkey = 7 and parsermethodkey =7 and lssitemaster_sitecode=1);

INSERT into methoddelimiter (status, usercode, delimiterkey, parsermethodkey,defaultvalue,lssitemaster_sitecode) SELECT 1,1,8,7,1,1 WHERE NOT EXISTS (select * from methoddelimiter where delimiterkey = 8 and parsermethodkey =7 and lssitemaster_sitecode=1);

INSERT into methoddelimiter (status, usercode, delimiterkey, parsermethodkey,defaultvalue,lssitemaster_sitecode) SELECT 1,1,9,7,1,1 WHERE NOT EXISTS (select * from methoddelimiter where delimiterkey = 9 and parsermethodkey =7 and lssitemaster_sitecode=1);

INSERT into methoddelimiter (status, usercode, delimiterkey, parsermethodkey,defaultvalue,lssitemaster_sitecode) SELECT 1,1,1,7,1,1 WHERE NOT EXISTS (select * from methoddelimiter where delimiterkey = 1 and parsermethodkey =7 and lssitemaster_sitecode=1);

---merge

INSERT into methoddelimiter (status, usercode, delimiterkey, parsermethodkey,defaultvalue,lssitemaster_sitecode) SELECT 1,1,8,6,1,1 WHERE NOT EXISTS (select * from methoddelimiter where delimiterkey = 8 and parsermethodkey =6 and lssitemaster_sitecode=1);

INSERT into methoddelimiter (status, usercode, delimiterkey, parsermethodkey,defaultvalue,lssitemaster_sitecode) SELECT 1,1,9,6,1,1 WHERE NOT EXISTS (select * from methoddelimiter where delimiterkey = 9 and parsermethodkey =6 and lssitemaster_sitecode=1);

INSERT into methoddelimiter (status, usercode, delimiterkey, parsermethodkey,defaultvalue,lssitemaster_sitecode) SELECT 1,1,4,6,1,1 WHERE NOT EXISTS (select * from methoddelimiter where delimiterkey = 4 and parsermethodkey =6 and lssitemaster_sitecode=1);

INSERT into methoddelimiter (status, usercode, delimiterkey, parsermethodkey,defaultvalue,lssitemaster_sitecode) SELECT 1,1,5,6,1,1 WHERE NOT EXISTS (select * from methoddelimiter where delimiterkey = 5 and parsermethodkey =6 and lssitemaster_sitecode=1);

INSERT into methoddelimiter (status, usercode, delimiterkey, parsermethodkey,defaultvalue,lssitemaster_sitecode) SELECT 1,1,6,6,1,1 WHERE NOT EXISTS (select * from methoddelimiter where delimiterkey = 6 and parsermethodkey =6 and lssitemaster_sitecode=1);

INSERT into methoddelimiter (status, usercode, delimiterkey, parsermethodkey,defaultvalue,lssitemaster_sitecode) SELECT 1,1,1,6,1,1 WHERE NOT EXISTS (select * from methoddelimiter where delimiterkey = 1 and parsermethodkey =6 and lssitemaster_sitecode=1);

update delimiter set delimiterstatus='A' where status = 1;
update delimiter set delimiterstatus='D' where status = -1;

update instrumentmaster set inststatus='A' where status = 1;
update instrumentmaster set inststatus='D' where status = -1;

update methoddelimiter set methoddelimiterstatus='A' where status = 1;
update methoddelimiter set methoddelimiterstatus='D' where status = -1;

update method set methodstatus='A' where status = 1;
update method set methodstatus='D' where status = -1;

update instrumentcategory set instcategorystatus='A' where status = 1;
update instrumentcategory set instcategorystatus='D' where status = -1;

update cloudparserfile set version = 1 where version is null;
update method set version = 1 where version is null;

update lspasswordpolicy set idletime=15 where idletime is null;
update lspasswordpolicy set idletimeshowcheck=1 where idletimeshowcheck=null;

update lsfields set isactive=1  where level04code='G14';

delete from lsusergrouprights where displaytopic='IDS_TSK_DASHBOARDINVENTORY';
delete from lsusergrouprightsmaster where displaytopic='IDS_TSK_DASHBOARDINVENTORY';

update lsusergrouprightsmaster set modulename='IDS_MDL_SETUP' where modulename='IDS_MDL_PARSER';
update lsusergrouprights set modulename='IDS_MDL_SETUP' where modulename='IDS_MDL_PARSER';

INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder,screenname) VALUES (117, 'IDS_TSK_LIMSTASKORDER', 'IDS_MDL_ORDERS', '0', '0', 'NA', 'NA', '1,0,0', 4,'IDS_SCN_SHEETORDERS') ON CONFLICT(orderno)DO NOTHING;
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_LIMSTASKORDER', 'IDS_MDL_ORDERS', 'administrator', '1', '1', 'NA', 'NA', 1,1,'IDS_SCN_SHEETORDERS' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_LIMSTASKORDER' and usergroupid_usergroupcode = 1) ;

update lsusergrouprights set sedit='NA' where sedit ='1'and  displaytopic='IDS_TSK_ORDERSHAREDBYMEPROTOCOL';
update lsusergrouprights set sedit='NA' where sedit ='1'and  displaytopic='IDS_TSK_ORDERSHAREDTOMEPROTOCOL';

delete from lsusergrouprightsmaster where displaytopic ='IDS_TSK_DOWNLOADPDFEXCEL';
delete from lsusergrouprights where displaytopic='IDS_TSK_DOWNLOADPDFEXCEL';
delete from lsusergrouprightsmaster where displaytopic='IDS_TSK_DOWNLOADPDFEXCELSECTION';
delete from lsusergrouprights where displaytopic='IDS_TSK_DOWNLOADPDFEXCELSECTION';
delete from lsusergrouprightsmaster where displaytopic='IDS_TSK_DOWNLOADMATERILACATEGORY';
delete from lsusergrouprights where displaytopic='IDS_TSK_DOWNLOADMATERILACATEGORY';
update lsusergrouprights set sedit='NA' where displaytopic='IDS_SCN_UNLOCKORDERS';
update lsusergrouprightsmaster set sedit='NA' where sedit ='0'and  displaytopic='IDS_SCN_UNLOCKORDERS';
update lsusergrouprights set screate='0' where screate ='NA' and  displaytopic='IDS_SCN_UNLOCKORDERS';
update lsusergrouprightsmaster set screate='0' where screate='NA' and  displaytopic='IDS_SCN_UNLOCKORDERS';
update lsusergrouprights set screate='1' where displaytopic='IDS_SCN_UNLOCKORDERS' and usergroupid_usergroupcode=1;

update lsusergrouprightsmaster set sdelete='NA', sedit='NA' where displaytopic='IDS_SCN_LOGBOOK';
update lsusergrouprightsmaster set screate='0' where screate ='NA' and displaytopic='IDS_SCN_AUDITTRAILCONFIG';
--update lsusergrouprights set screate='0' where screate ='NA' and displaytopic='IDS_SCN_AUDITTRAILCONFIG';
update lsusergrouprights set screate='1' where displaytopic='IDS_SCN_AUDITTRAILCONFIG' and usergroupid_usergroupcode=1;
update lsusergrouprightsmaster set screate='0' where screate ='NA' and displaytopic='IDS_SCN_USERRIGHTS';
update lsusergrouprightsmaster set screate='0' where screate ='NA' and displaytopic='IDS_SCN_ORDERWORKLOW';
update lsusergrouprightsmaster set screate='0' where screate ='NA' and displaytopic='IDS_SCN_TEMPLATEWORKFLOW';
update lsusergrouprightsmaster set screate='0' where screate ='NA' and displaytopic='IDS_SCN_PASSWORDPOLICY';
--update lsusergrouprights set screate='0' where screate ='NA' and displaytopic='IDS_SCN_USERRIGHTS';
--update lsusergrouprights set screate='0' where screate ='NA' and displaytopic='IDS_SCN_ORDERWORKLOW';
--update lsusergrouprights set screate='0' where screate ='NA' and displaytopic='IDS_SCN_TEMPLATEWORKFLOW';
--update lsusergrouprights set screate='0' where screate ='NA' and displaytopic='IDS_SCN_PASSWORDPOLICY';
update lsusergrouprights set screate='1' where displaytopic='IDS_SCN_USERRIGHTS' and usergroupid_usergroupcode=1;
update lsusergrouprights set screate='1' where displaytopic='IDS_SCN_ORDERWORKLOW' and usergroupid_usergroupcode=1;
update lsusergrouprights set screate='1' where displaytopic='IDS_SCN_TEMPLATEWORKFLOW' and usergroupid_usergroupcode=1;
update lsusergrouprights set screate='1' where displaytopic='IDS_SCN_PASSWORDPOLICY' and usergroupid_usergroupcode=1;
update lsusergrouprightsmaster set sdelete='0' where sdelete='NA' and displaytopic='IDS_SCN_PROJECTTEAM';
update lsusergrouprights set sdelete='1' where displaytopic='IDS_SCN_PROJECTTEAM';

--INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder,screenname) VALUES (120, 'IDS_SCN_MATERIALTYPE', 'IDS_MDL_INVENTORY', '0', 'NA', 'NA', 'NA', '0,0,0',97,'IDS_SCN_MATERIALTYPE') ON CONFLICT(orderno)DO NOTHING;
--INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_SCN_MATERIALTYPE', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_MATERIALTYPE'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_SCN_MATERIALTYPE' and usergroupid_usergroupcode = 1);

update lsaudittrailconfigmaster set modulename='IDS_MDL_SETUP' where lsaudittrailconfigmaster.modulename='IDS_MDL_PARSER';

/*
INSERT INTO parserignorechars(parserignorecharskey,ignorechars)SELECT 5,'µ' WHERE NOT EXISTS (SELECT ignorechars FROM parserignorechars WHERE ignorechars = 'µ');
INSERT INTO parserignorechars(parserignorecharskey,ignorechars)SELECT 6,'µµ' WHERE NOT EXISTS (SELECT ignorechars FROM parserignorechars WHERE ignorechars = 'µµ');
INSERT INTO parserignorechars(parserignorecharskey,ignorechars)SELECT 7,'µµµ' WHERE NOT EXISTS (SELECT ignorechars FROM parserignorechars WHERE ignorechars = 'µµµ');
INSERT INTO parserignorechars(parserignorecharskey,ignorechars)SELECT 8,'µµµµ' WHERE NOT EXISTS (SELECT ignorechars FROM parserignorechars WHERE ignorechars = 'µµµµµ');
INSERT INTO parserignorechars(parserignorecharskey,ignorechars)SELECT 9,'µµµµµ' WHERE NOT EXISTS (SELECT ignorechars FROM parserignorechars WHERE ignorechars = 'µµµµµµ');

INSERT INTO parserignorechars(parserignorecharskey,ignorechars)SELECT 10,'â†â†' WHERE NOT EXISTS (SELECT ignorechars FROM parserignorechars WHERE ignorechars = 'â†â†');
INSERT INTO parserignorechars(parserignorecharskey,ignorechars)SELECT 11,'â†â' WHERE NOT EXISTS (SELECT ignorechars FROM parserignorechars WHERE ignorechars = 'â†â');
INSERT INTO parserignorechars(parserignorecharskey,ignorechars)SELECT 12,'ââ' WHERE NOT EXISTS (SELECT ignorechars FROM parserignorechars WHERE ignorechars = 'ââ');
INSERT INTO parserignorechars(parserignorecharskey,ignorechars)SELECT 13,'âââ' WHERE NOT EXISTS (SELECT ignorechars FROM parserignorechars WHERE ignorechars = 'âââ');

INSERT INTO parserignorechars(parserignorecharskey,ignorechars)SELECT 14,'â†µâ†µâ' WHERE NOT EXISTS (SELECT ignorechars FROM parserignorechars WHERE ignorechars = 'â†µâ†µâ');
INSERT INTO parserignorechars(parserignorecharskey,ignorechars)SELECT 15,'â†µ' WHERE NOT EXISTS (SELECT ignorechars FROM parserignorechars WHERE ignorechars = 'â†µ');

INSERT INTO parserignorechars(parserignorecharskey,ignorechars)SELECT 16,'â†µâ†µâ†µâ†µ' WHERE NOT EXISTS (SELECT ignorechars FROM parserignorechars WHERE ignorechars = 'â†µâ†µâ†µâ†µ');
INSERT INTO parserignorechars(parserignorecharskey,ignorechars)SELECT 17,'â†µâ' WHERE NOT EXISTS (SELECT ignorechars FROM parserignorechars WHERE ignorechars = 'â†µâ');

 INSERT INTO parserignorechars(parserignorecharskey,ignorechars)SELECT 18,'â†' WHERE NOT EXISTS (SELECT ignorechars FROM parserignorechars WHERE ignorechars = 'â†');
 INSERT INTO parserignorechars(parserignorecharskey,ignorechars)SELECT 19,'â' WHERE NOT EXISTS (SELECT ignorechars FROM parserignorechars WHERE ignorechars = 'â');
 */
 
INSERT into datatype (datatypekey,datatypename) SELECT 1,'string' WHERE NOT EXISTS (select * from datatype where datatypename = 'string');

 INSERT into datatype (datatypekey,datatypename) SELECT 2,'Integer' WHERE NOT EXISTS (select * from datatype where datatypename = 'Integer');

 INSERT into datatype (datatypekey,datatypename) SELECT 3,'Number' WHERE NOT EXISTS (select * from datatype where datatypename = 'Number');
 
delete from lsusergrouprightsmaster where displaytopic='IDS_SCN_INVENTORY';
delete from lsusergrouprightsmaster where displaytopic='IDS_TSK_ADDREPO';
delete from lsusergrouprightsmaster where displaytopic='IDS_TSK_EDITREPO';

delete from lsusergrouprights where displaytopic='IDS_SCN_SITEMASTER' and usergroupid_usergroupcode=1;
delete from lsusergrouprights where displaytopic='IDS_SCN_DOMAIN' and usergroupid_usergroupcode=1;
delete from lsusergrouprights where displaytopic='IDS_SCN_ACTIVEUSER' and usergroupid_usergroupcode=1;
delete from lsusergrouprights where displaytopic='IDS_TSK_ACTDEACTSITE' and usergroupid_usergroupcode=1;

INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder,screenname) VALUES (80, 'IDS_TSK_ORDERSHAREDBYMEPROTOCOL', 'IDS_MDL_ORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 20,'IDS_SCN_PROTOCOLORDERS') ON CONFLICT(orderno)DO NOTHING;
INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder,screenname) VALUES (81, 'IDS_TSK_ORDERSHAREDTOMEPROTOCOL', 'IDS_MDL_ORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 21,'IDS_SCN_PROTOCOLORDERS') ON CONFLICT(orderno)DO NOTHING;
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_ORDERSHAREDBYMEPROTOCOL', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLORDERS' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_ORDERSHAREDBYMEPROTOCOL' and usergroupid_usergroupcode = 1) ;
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_ORDERSHAREDTOMEPROTOCOL', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLORDERS' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_ORDERSHAREDTOMEPROTOCOL' and usergroupid_usergroupcode = 1) ;

update lsusergrouprightsmaster set displaytopic='IDS_TSK_FOLDERCREATIONPROTOCOL' where displaytopic='IDS_TSK_FOLDERCREATION' and screenname='IDS_SCN_PROTOCOLORDERS';
update lsusergrouprightsmaster set displaytopic='IDS_SCN_INSTRUMENTCATEGORY' where displaytopic='IDS_TSK_INSTRUMENTCATEGORY' and screenname='IDS_SCN_INSTRUMENTCATEGORY';
update lsusergrouprights set displaytopic='IDS_TSK_FOLDERCREATIONPROTOCOL' where displaytopic='IDS_TSK_FOLDERCREATION' and screenname='IDS_SCN_PROTOCOLORDERS';
update lsusergrouprights set displaytopic='IDS_SCN_INSTRUMENTCATEGORY' where displaytopic='IDS_TSK_INSTRUMENTCATEGORY' and screenname='IDS_SCN_INSTRUMENTCATEGORY';

update lsusergrouprightsmaster set sequenceorder=1 where displaytopic='IDS_TSK_ORDEROVERVIEW';
update lsusergrouprightsmaster set sequenceorder=2 where displaytopic='IDS_TSK_TEMPLATEOVERVIEW';
update lsusergrouprightsmaster set sequenceorder=3 where displaytopic='IDS_TSK_ACTIVITIES';
update lsusergrouprightsmaster set sequenceorder=4 where displaytopic='IDS_TSK_LIMSTASKORDER';
update lsusergrouprightsmaster set sequenceorder=5 where displaytopic='IDS_TSK_FOLDERCREATION';
update lsusergrouprightsmaster set sequenceorder=6 where displaytopic='IDS_TSK_ELNTASKORDER';
update lsusergrouprightsmaster set sequenceorder=7 where displaytopic='IDS_TSK_RESEARCHACTIVITY';
update lsusergrouprightsmaster set sequenceorder=8 where displaytopic='IDS_TSK_MANAGEEXCEL';
update lsusergrouprightsmaster set sequenceorder=9 where displaytopic='IDS_TSK_SHEETEVALUATION';
update lsusergrouprightsmaster set sequenceorder=10 where displaytopic='IDS_TSK_UPLOADSHEETORDER';
update lsusergrouprightsmaster set sequenceorder=11 where displaytopic='IDS_TSK_ORDERSHAREDBYME';
update lsusergrouprightsmaster set sequenceorder=12 where displaytopic='IDS_TSK_ORDERSHAREDTOME';
update lsusergrouprightsmaster set sequenceorder=13 where displaytopic='IDS_TSK_SHEETORDEREXPORT';
update lsusergrouprightsmaster set sequenceorder=14 where displaytopic='IDS_TSK_MOVEORDERS';
update lsusergrouprightsmaster set sequenceorder=15 where displaytopic='IDS_SCN_PROTOCOLORDERS';
update lsusergrouprightsmaster set sequenceorder=16 where displaytopic='IDS_TSK_FOLDERCREATIONPROTOCOL';
update lsusergrouprightsmaster set sequenceorder=17 where displaytopic='IDS_TSK_ELNPROTOCOL';
update lsusergrouprightsmaster set sequenceorder=18 where displaytopic='IDS_TSK_DYNAMICPROTOCOL';
update lsusergrouprightsmaster set sequenceorder=19 where displaytopic='IDS_TSK_UPLOADPROTOCOLORDER';
update lsusergrouprightsmaster set sequenceorder=20 where displaytopic='IDS_TSK_ORDERSHAREDBYMEPROTOCOL';
update lsusergrouprightsmaster set sequenceorder=21 where displaytopic='IDS_TSK_ORDERSHAREDTOMEPROTOCOL';
update lsusergrouprightsmaster set sequenceorder=22 where displaytopic='IDS_TSK_MOVEORDERSPROTOCOL';
update lsusergrouprightsmaster set sequenceorder=23 where displaytopic='IDS_SCN_SHEETTEMPLATE';
update lsusergrouprightsmaster set sequenceorder=24 where displaytopic='IDS_TSK_TEMPLATESHAREDBYME';
update lsusergrouprightsmaster set sequenceorder=25 where displaytopic='IDS_TSK_TEMPLATESHAREDTOME';
update lsusergrouprightsmaster set sequenceorder=26 where displaytopic='IDS_TSK_SHEETTEMPEXPORT';
update lsusergrouprightsmaster set sequenceorder=27 where displaytopic='IDS_SCN_PROTOCOLTEMPLATE';
update lsusergrouprightsmaster set sequenceorder=28 where displaytopic='IDS_TSK_NEWSTEP';
update lsusergrouprightsmaster set sequenceorder=29 where displaytopic='IDS_TSK_PROTOCOLTEMPSHAREBYME';
update lsusergrouprightsmaster set sequenceorder=30 where displaytopic='IDS_TSK_PROTOCOLTEMPSHARETOME';
update lsusergrouprightsmaster set sequenceorder=31 where displaytopic='IDS_TSK_EXPORTPDF';
update lsusergrouprightsmaster set sequenceorder=32 where displaytopic='IDS_TSK_SHEET';
update lsusergrouprightsmaster set sequenceorder=33 where displaytopic='IDS_TSK_PROTOCOL';
update lsusergrouprightsmaster set sequenceorder=34 where displaytopic='IDS_TSK_LIMSTESTORDER';
update lsusergrouprightsmaster set sequenceorder=35 where displaytopic='IDS_TSK_ELNANDRESEARCH';
update lsusergrouprightsmaster set sequenceorder=36 where displaytopic='IDS_SCN_TASKMASTER';
update lsusergrouprightsmaster set sequenceorder=37 where displaytopic='IDS_SCN_PROJECTMASTER';
update lsusergrouprightsmaster set sequenceorder=38 where displaytopic='IDS_SCN_SAMPLEMASTER';
update lsusergrouprightsmaster set sequenceorder=39 where displaytopic='IDS_SCN_UNLOCKORDERS';
update lsusergrouprightsmaster set sequenceorder=40 where displaytopic='IDS_SCN_USERGROUP';
update lsusergrouprightsmaster set sequenceorder=41 where displaytopic='IDS_TSK_ACTDEACT';
update lsusergrouprightsmaster set sequenceorder=42 where displaytopic='IDS_SCN_USERMASTER';
update lsusergrouprightsmaster set sequenceorder=43 where displaytopic='IDS_TSK_UNLOCK';
update lsusergrouprightsmaster set sequenceorder=44 where displaytopic='IDS_TSK_ACTDEACTUSERMASTER';
update lsusergrouprightsmaster set sequenceorder=45 where displaytopic='IDS_TSK_RESETPASSWORD';
update lsusergrouprightsmaster set sequenceorder=46 where displaytopic='IDS_TSK_RETIRE';
update lsusergrouprightsmaster set sequenceorder=47 where displaytopic='IDS_TSK_IMPORTADS';
update lsusergrouprightsmaster set sequenceorder=48 where displaytopic='IDS_SCN_USERRIGHTS';
update lsusergrouprightsmaster set sequenceorder=49 where displaytopic='IDS_SCN_PROJECTTEAM';
update lsusergrouprightsmaster set sequenceorder=50 where displaytopic='IDS_SCN_ORDERWORKLOW';
update lsusergrouprightsmaster set sequenceorder=51 where displaytopic='IDS_SCN_TEMPLATEWORKFLOW';
update lsusergrouprightsmaster set sequenceorder=52 where displaytopic='IDS_SCN_PASSWORDPOLICY';
update lsusergrouprightsmaster set sequenceorder=53 where displaytopic='IDS_SCN_PARSER';
update lsusergrouprightsmaster set sequenceorder=54 where displaytopic='IDS_SCN_INSTRUMENTCATEGORY';
update lsusergrouprightsmaster set sequenceorder=55 where displaytopic='IDS_SCN_INSTRUMENTMASTER';
update lsusergrouprightsmaster set sequenceorder=56 where displaytopic='IDS_SCN_DELIMITER';
update lsusergrouprightsmaster set sequenceorder=57 where displaytopic='IDS_SCN_METHODDELIMITER';
update lsusergrouprightsmaster set sequenceorder=58 where displaytopic='IDS_SCN_METHODMASTER';
update lsusergrouprightsmaster set sequenceorder=59 where displaytopic='IDS_SCN_AUDITTRAILHISTORY';
update lsusergrouprightsmaster set sequenceorder=60 where displaytopic='IDS_TSK_REVIEWHISTORY';
update lsusergrouprightsmaster set sequenceorder=61 where displaytopic='IDS_TSK_REVIEW';
update lsusergrouprightsmaster set sequenceorder=62 where displaytopic='IDS_TSK_CREATEARCHIVE';
update lsusergrouprightsmaster set sequenceorder=63 where displaytopic='IDS_TSK_OPENARCHIVE';
update lsusergrouprightsmaster set sequenceorder=64 where displaytopic='IDS_TSK_EXPORT';
update lsusergrouprightsmaster set sequenceorder=65 where displaytopic='IDS_SCN_CFRSETTINGS';
update lsusergrouprightsmaster set sequenceorder=66 where displaytopic='IDS_SCN_AUDITTRAILCONFIG';
update lsusergrouprightsmaster set sequenceorder=67 where displaytopic='IDS_SCN_REPORTS';
update lsusergrouprightsmaster set sequenceorder=68 where displaytopic='IDS_TSK_NEWDOCUMENT';
update lsusergrouprightsmaster set sequenceorder=69 where displaytopic='IDS_TSK_NEWTEMP';
update lsusergrouprightsmaster set sequenceorder=70 where displaytopic='IDS_TSK_GENERATEREPORT';
update lsusergrouprightsmaster set sequenceorder=71 where displaytopic='IDS_TSK_OPENREPORT';
update lsusergrouprightsmaster set sequenceorder=72 where displaytopic='IDS_TSK_IMPORTDOCX';
update lsusergrouprightsmaster set sequenceorder=73 where displaytopic='IDS_SCN_MATERIALTYPE';
update lsusergrouprightsmaster set sequenceorder=74 where displaytopic='IDS_SCN_UNITMASTER';
update lsusergrouprightsmaster set sequenceorder=75 where displaytopic='IDS_SCN_SECTIONMASTER';
update lsusergrouprightsmaster set sequenceorder=76 where displaytopic='IDS_SCN_STORAGELOCATION';
update lsusergrouprightsmaster set sequenceorder=77 where displaytopic='IDS_SCN_MATERIALCATEGORY';
update lsusergrouprightsmaster set sequenceorder=78 where displaytopic='IDS_SCN_MATERIAL';
update lsusergrouprightsmaster set sequenceorder=79 where displaytopic='IDS_SCN_MATERIALINVENTORY';
update lsusergrouprightsmaster set sequenceorder=83 where displaytopic='IDS_SCN_LOGBOOK';
update lsusergrouprightsmaster set sequenceorder=84 where displaytopic='IDS_TSK_ADDLOGBOOK';
update lsusergrouprightsmaster set sequenceorder=85 where displaytopic='IDS_TSK_EDITLOGBOOK';
update lsusergrouprightsmaster set sequenceorder=86 where displaytopic='IDS_TSK_RETIRELOGBOOK';
update lsusergrouprightsmaster set sequenceorder=87 where displaytopic='IDS_TSK_REVIEWLOGBOOK';

update lsusergrouprights set sequenceorder=1 where displaytopic='IDS_TSK_ORDEROVERVIEW';
update lsusergrouprights set sequenceorder=2 where displaytopic='IDS_TSK_TEMPLATEOVERVIEW';
update lsusergrouprights set sequenceorder=3 where displaytopic='IDS_TSK_ACTIVITIES';
update lsusergrouprights set sequenceorder=4 where displaytopic='IDS_TSK_LIMSTASKORDER';
update lsusergrouprights set sequenceorder=5 where displaytopic='IDS_TSK_FOLDERCREATION';
update lsusergrouprights set sequenceorder=6 where displaytopic='IDS_TSK_ELNTASKORDER';
update lsusergrouprights set sequenceorder=7 where displaytopic='IDS_TSK_RESEARCHACTIVITY';
update lsusergrouprights set sequenceorder=8 where displaytopic='IDS_TSK_MANAGEEXCEL';
update lsusergrouprights set sequenceorder=9 where displaytopic='IDS_TSK_SHEETEVALUATION';
update lsusergrouprights set sequenceorder=10 where displaytopic='IDS_TSK_UPLOADSHEETORDER';
update lsusergrouprights set sequenceorder=11 where displaytopic='IDS_TSK_ORDERSHAREDBYME';
update lsusergrouprights set sequenceorder=12 where displaytopic='IDS_TSK_ORDERSHAREDTOME';
update lsusergrouprights set sequenceorder=13 where displaytopic='IDS_TSK_SHEETORDEREXPORT';
update lsusergrouprights set sequenceorder=14 where displaytopic='IDS_TSK_MOVEORDERS';
update lsusergrouprights set sequenceorder=15 where displaytopic='IDS_SCN_PROTOCOLORDERS';
update lsusergrouprights set sequenceorder=16 where displaytopic='IDS_TSK_FOLDERCREATIONPROTOCOL';
update lsusergrouprights set sequenceorder=17 where displaytopic='IDS_TSK_ELNPROTOCOL';
update lsusergrouprights set sequenceorder=18 where displaytopic='IDS_TSK_DYNAMICPROTOCOL';
update lsusergrouprights set sequenceorder=19 where displaytopic='IDS_TSK_UPLOADPROTOCOLORDER';
update lsusergrouprights set sequenceorder=20 where displaytopic='IDS_TSK_ORDERSHAREDBYMEPROTOCOL';
update lsusergrouprights set sequenceorder=21 where displaytopic='IDS_TSK_ORDERSHAREDTOMEPROTOCOL';
update lsusergrouprights set sequenceorder=22 where displaytopic='IDS_TSK_MOVEORDERSPROTOCOL';
update lsusergrouprights set sequenceorder=23 where displaytopic='IDS_SCN_SHEETTEMPLATE';
update lsusergrouprights set sequenceorder=24 where displaytopic='IDS_TSK_TEMPLATESHAREDBYME';
update lsusergrouprights set sequenceorder=25 where displaytopic='IDS_TSK_TEMPLATESHAREDTOME';
update lsusergrouprights set sequenceorder=26 where displaytopic='IDS_TSK_SHEETTEMPEXPORT';
update lsusergrouprights set sequenceorder=27 where displaytopic='IDS_SCN_PROTOCOLTEMPLATE';
update lsusergrouprights set sequenceorder=28 where displaytopic='IDS_TSK_NEWSTEP';
update lsusergrouprights set sequenceorder=29 where displaytopic='IDS_TSK_PROTOCOLTEMPSHAREBYME';
update lsusergrouprights set sequenceorder=30 where displaytopic='IDS_TSK_PROTOCOLTEMPSHARETOME';
update lsusergrouprights set sequenceorder=31 where displaytopic='IDS_TSK_EXPORTPDF';
update lsusergrouprights set sequenceorder=32 where displaytopic='IDS_TSK_SHEET';
update lsusergrouprights set sequenceorder=33 where displaytopic='IDS_TSK_PROTOCOL';
update lsusergrouprights set sequenceorder=34 where displaytopic='IDS_TSK_LIMSTESTORDER';
update lsusergrouprights set sequenceorder=35 where displaytopic='IDS_TSK_ELNANDRESEARCH';
update lsusergrouprights set sequenceorder=36 where displaytopic='IDS_SCN_TASKMASTER';
update lsusergrouprights set sequenceorder=37 where displaytopic='IDS_SCN_PROJECTMASTER';
update lsusergrouprights set sequenceorder=38 where displaytopic='IDS_SCN_SAMPLEMASTER';
update lsusergrouprights set sequenceorder=39 where displaytopic='IDS_SCN_UNLOCKORDERS';
update lsusergrouprights set sequenceorder=40 where displaytopic='IDS_SCN_USERGROUP';
update lsusergrouprights set sequenceorder=41 where displaytopic='IDS_TSK_ACTDEACT';
update lsusergrouprights set sequenceorder=42 where displaytopic='IDS_SCN_USERMASTER';
update lsusergrouprights set sequenceorder=43 where displaytopic='IDS_TSK_UNLOCK';
update lsusergrouprights set sequenceorder=44 where displaytopic='IDS_TSK_ACTDEACTUSERMASTER';
update lsusergrouprights set sequenceorder=45 where displaytopic='IDS_TSK_RESETPASSWORD';
update lsusergrouprights set sequenceorder=46 where displaytopic='IDS_TSK_RETIRE';
update lsusergrouprights set sequenceorder=47 where displaytopic='IDS_TSK_IMPORTADS';
update lsusergrouprights set sequenceorder=48 where displaytopic='IDS_SCN_USERRIGHTS';
update lsusergrouprights set sequenceorder=49 where displaytopic='IDS_SCN_PROJECTTEAM';
update lsusergrouprights set sequenceorder=50 where displaytopic='IDS_SCN_ORDERWORKLOW';
update lsusergrouprights set sequenceorder=51 where displaytopic='IDS_SCN_TEMPLATEWORKFLOW';
update lsusergrouprights set sequenceorder=52 where displaytopic='IDS_SCN_PASSWORDPOLICY';
update lsusergrouprights set sequenceorder=53 where displaytopic='IDS_SCN_PARSER';
update lsusergrouprights set sequenceorder=54 where displaytopic='IDS_SCN_INSTRUMENTCATEGORY';
update lsusergrouprights set sequenceorder=55 where displaytopic='IDS_SCN_INSTRUMENTMASTER';
update lsusergrouprights set sequenceorder=56 where displaytopic='IDS_SCN_DELIMITER';
update lsusergrouprights set sequenceorder=57 where displaytopic='IDS_SCN_METHODDELIMITER';
update lsusergrouprights set sequenceorder=58 where displaytopic='IDS_SCN_METHODMASTER';
update lsusergrouprights set sequenceorder=59 where displaytopic='IDS_SCN_AUDITTRAILHISTORY';
update lsusergrouprights set sequenceorder=60 where displaytopic='IDS_TSK_REVIEWHISTORY';
update lsusergrouprights set sequenceorder=61 where displaytopic='IDS_TSK_REVIEW';
update lsusergrouprights set sequenceorder=62 where displaytopic='IDS_TSK_CREATEARCHIVE';
update lsusergrouprights set sequenceorder=63 where displaytopic='IDS_TSK_OPENARCHIVE';
update lsusergrouprights set sequenceorder=64 where displaytopic='IDS_TSK_EXPORT';
update lsusergrouprights set sequenceorder=65 where displaytopic='IDS_SCN_CFRSETTINGS';
update lsusergrouprights set sequenceorder=66 where displaytopic='IDS_SCN_AUDITTRAILCONFIG';
update lsusergrouprights set sequenceorder=67 where displaytopic='IDS_SCN_REPORTS';
update lsusergrouprights set sequenceorder=68 where displaytopic='IDS_TSK_NEWDOCUMENT';
update lsusergrouprights set sequenceorder=69 where displaytopic='IDS_TSK_NEWTEMP';
update lsusergrouprights set sequenceorder=70 where displaytopic='IDS_TSK_GENERATEREPORT';
update lsusergrouprights set sequenceorder=71 where displaytopic='IDS_TSK_OPENREPORT';
update lsusergrouprights set sequenceorder=72 where displaytopic='IDS_TSK_IMPORTDOCX';
update lsusergrouprights set sequenceorder=73 where displaytopic='IDS_SCN_MATERIALTYPE';
update lsusergrouprights set sequenceorder=74 where displaytopic='IDS_SCN_UNITMASTER';
update lsusergrouprights set sequenceorder=75 where displaytopic='IDS_SCN_SECTIONMASTER';
update lsusergrouprights set sequenceorder=76 where displaytopic='IDS_SCN_STORAGELOCATION';
update lsusergrouprights set sequenceorder=77 where displaytopic='IDS_SCN_MATERIALCATEGORY';
update lsusergrouprights set sequenceorder=78 where displaytopic='IDS_SCN_MATERIAL';
update lsusergrouprights set sequenceorder=79 where displaytopic='IDS_SCN_MATERIALINVENTORY';
update lsusergrouprights set sequenceorder=83 where displaytopic='IDS_SCN_LOGBOOK';
update lsusergrouprights set sequenceorder=84 where displaytopic='IDS_TSK_ADDLOGBOOK';
update lsusergrouprights set sequenceorder=85 where displaytopic='IDS_TSK_EDITLOGBOOK';
update lsusergrouprights set sequenceorder=86 where displaytopic='IDS_TSK_RETIRELOGBOOK';
update lsusergrouprights set sequenceorder=87 where displaytopic='IDS_TSK_REVIEWLOGBOOK';

update lsusersteam set projectteamstatus='A' where status=1;
update lsusersteam set projectteamstatus='D' where status=-1;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, createdon, sallow, screate, sdelete, sedit, lssitemaster_sitecode, usergroupid_usergroupcode,screenname) values ( 'IDS_SCN_SITEMASTER','IDS_MDL_SETUP','administrator', CAST('2022-01-21 00:00:00.000' AS date),'1','1','1','1',1,1,'IDS_SCN_SITEMASTER') ON CONFLICT(orderno)DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, createdon, sallow, screate, sdelete, sedit, lssitemaster_sitecode, usergroupid_usergroupcode,screenname) values ( 'IDS_SCN_DOMAIN','IDS_MDL_SETUP','administrator', CAST('2022-01-21 00:00:00.000' AS date),'1','1','1','1',1,1,'IDS_SCN_DOMAIN')ON CONFLICT(orderno)DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, createdon, sallow, screate, sdelete, sedit, lssitemaster_sitecode, usergroupid_usergroupcode,screenname) values ( 'IDS_SCN_ACTIVEUSER','IDS_MDL_SETUP','administrator', CAST('2022-01-21 00:00:00.000' AS date),'1','1','1','1',1,1,'IDS_SCN_ACTIVEUSER')ON CONFLICT(orderno)DO NOTHING;
update lsusergrouprights set sedit ='1' where displaytopic='IDS_TSK_FOLDERCREATION' and sedit='NA' and usergroupid_usergroupcode =1;
update lsusergrouprights set sedit ='0' where displaytopic='IDS_TSK_FOLDERCREATION' and sedit='NA' and usergroupid_usergroupcode !=1;
update lsusergrouprights set sdelete ='NA' where displaytopic='IDS_TSK_FOLDERCREATION';
update lsusergrouprights set sedit ='1' where displaytopic='IDS_TSK_FOLDERCREATIONPROTOCOL' and sedit='NA' and usergroupid_usergroupcode =1;
update lsusergrouprights set sedit ='0' where displaytopic='IDS_TSK_FOLDERCREATIONPROTOCOL' and sedit='NA' and usergroupid_usergroupcode !=1;
update lsusergrouprights set sdelete ='NA' where displaytopic='IDS_TSK_FOLDERCREATION';
update lsusergrouprightsmaster set sedit ='0',sdelete='NA' where displaytopic='IDS_TSK_FOLDERCREATION';
update lsusergrouprightsmaster set sedit ='0',sdelete='NA' where displaytopic='IDS_TSK_FOLDERCREATIONPROTOCOL';

ALTER TABLE IF Exists materialinventory ALTER COLUMN jsondata TYPE text;
ALTER TABLE IF Exists materialinventory ALTER COLUMN jsonuidata TYPE text;
ALTER TABLE IF Exists materialinventorytransaction ALTER COLUMN jsondata TYPE text;
ALTER TABLE IF Exists materialinventorytransaction ALTER COLUMN jsonuidata TYPE text;

INSERT INTO transactionstatus (ntranscode, stransstatus, jsondata, nstatus) VALUES (47, 'Receive', '{"salertdisplaystatus": {"en-US": "Received", "ru-RU": "Получила", "tg-TG": "Гирифтанд"}, "stransdisplaystatus": {"en-US": "Received", "ru-RU": "Получила", "tg-TG": "Гирифтанд"}, "sactiondisplaystatus": {"en-US": "Receive", "ru-RU": "Получать", "tg-TG": "Гирифтан"}}', 1) on conflict(ntranscode) do nothing;
INSERT INTO transactionstatus (ntranscode, stransstatus, jsondata, nstatus) VALUES (48, 'Issue', '{"salertdisplaystatus": {"en-US": "Issued", "ru-RU": "Изданный", "tg-TG": "Нашр шуда"}, "stransdisplaystatus": {"en-US": "Issued", "ru-RU": "Изданный", "tg-TG": "Нашр шуда"}, "sactiondisplaystatus": {"en-US": "Issue", "ru-RU": "Проблема", "tg-TG": "Чоп"}}', 1)on conflict(ntranscode) do nothing;

INSERT INTO materialinventorytype (ninventorytypecode, jsondata, ndefaultstatus, nstatus) VALUES (1, '{"sinventorytypename": {"en-US": "Inhouse", "ru-RU": "Внутренний", "tg-TG": "Дар хона"}}', 3, 1)on conflict(ninventorytypecode) do nothing;
INSERT INTO materialinventorytype (ninventorytypecode, jsondata, ndefaultstatus, nstatus) VALUES (2, '{"sinventorytypename": {"en-US": "Outside", "ru-RU": "За пределами", "tg-TG": "Дар берун"}}', 4, 1)on conflict(ninventorytypecode) do nothing;

-- INSERT INTO materialtype (nmaterialtypecode, jsondata, ndefaultstatus, nsitecode, nstatus) VALUES (-1, '{"sdescription": "NA", "smaterialtypename": {"en-US": "NA", "ru-RU": "нет данных", "tg-TG": "НА"}}', 4, -1, 1)on conflict (nmaterialtypecode) do nothing; 
-- INSERT INTO materialtype (nmaterialtypecode, jsondata, ndefaultstatus, nsitecode, nstatus) VALUES (2, '{"prefix": "V", "sdescription": "Volumetric Type", "needSectionwise": 3, "smaterialtypename": {"en-US": "Volumetric Type", "ru-RU": "Объемный тип", "tg-TG": "Навъи ҳаҷмӣ"}, "ismaterialSectionneed": 4}', 4, -1, 1)on conflict (nmaterialtypecode) do nothing; 
-- INSERT INTO materialtype (nmaterialtypecode, jsondata, ndefaultstatus, nsitecode, nstatus) VALUES (3, '{"prefix": "M", "sdescription": "Material Inventory Type", "needSectionwise": 3, "smaterialtypename": {"en-US": "Material Inventory Type", "ru-RU": "Тип инвентаризации материалов", "tg-TG": "Навъи инвентаризатсияи мавод"}, "ismaterialSectionneed": 3}', 4, -1, 1)on conflict (nmaterialtypecode) do nothing; 
-- INSERT INTO materialtype (nmaterialtypecode, jsondata, ndefaultstatus, nsitecode, nstatus) VALUES (1, '{"prefix": "S", "sdescription": "Standared Type", "needSectionwise": 3, "smaterialtypename": {"en-US": "Standard Type", "ru-RU": "Стандартный тип", "tg-TG": "Навъи стандартӣ"}, "ismaterialSectionneed": 3}', 3, -1, 1)on conflict (nmaterialtypecode) do nothing; 
-- INSERT INTO materialtype (nmaterialtypecode, jsondata, ndefaultstatus, nsitecode, nstatus) VALUES (4, '{"prefix": "I", "sdescription": "IQC Standard Material Type", "needSectionwise": 3, "smaterialtypename": {"en-US": "IQC Standard Material Type", "ru-RU": "Стандартный тип материала IQC", "tg-TG": "Стандарти намуди маводи IQC"}, "ismaterialSectionneed": 4}', 4, -1, 1)on conflict (nmaterialtypecode) do nothing; 

INSERT INTO period (nperiodcode, jsondata, ndefaultstatus, nsitecode, nstatus,speriodname) VALUES (-1, '{"ndata": -1, "nmaxlength": 0, "speriodname": {"en-US": "NA", "ru-RU": "нет данных", "tg-TG": "НА"}, "sdescription": "NA"}', 4, -1, 1,'NA') on conflict(nperiodcode)do nothing;
INSERT INTO period (nperiodcode, jsondata, ndefaultstatus, nsitecode, nstatus,speriodname) VALUES (1, '{"ndata": 1, "nmaxlength": 5, "speriodname": {"en-US": "Minutes", "ru-RU": "Минуты", "tg-TG": "Дакикахо"}, "sdescription": ""}', 4, -1, 1,'Minutes') on conflict(nperiodcode)do nothing;;
INSERT INTO period (nperiodcode, jsondata, ndefaultstatus, nsitecode, nstatus,speriodname) VALUES (2, '{"ndata": 60, "nmaxlength": 4, "speriodname": {"en-US": "Hours", "ru-RU": "Часы", "tg-TG": "Соатхо"}, "sdescription": ""}', 3, -1, 1,'Hours') on conflict(nperiodcode)do nothing;;
INSERT INTO period (nperiodcode, jsondata, ndefaultstatus, nsitecode, nstatus,speriodname) VALUES (3, '{"ndata": 10080, "nmaxlength": 0, "speriodname": {"en-US": "Weeks", "ru-RU": "Недели", "tg-TG": "Ҳафтаҳо"}, "sdescription": ""}', 4, -1, 1,'Weeks') on conflict(nperiodcode)do nothing;;
INSERT INTO period (nperiodcode, jsondata, ndefaultstatus, nsitecode, nstatus,speriodname) VALUES (4, '{"ndata": 1440, "nmaxlength": 3, "speriodname": {"en-US": "Days", "ru-RU": "Дни", "tg-TG": "Рузхо"}, "sdescription": ""}', 4, -1, 1,'Days') on conflict(nperiodcode)do nothing;;
INSERT INTO period (nperiodcode, jsondata, ndefaultstatus, nsitecode, nstatus,speriodname) VALUES (5, '{"ndata": 43200, "nmaxlength": 2, "speriodname": {"en-US": "Month", "ru-RU": "Месяц", "tg-TG": "Мох"}, "sdescription": ""}', 4, -1, 1,'Month') on conflict(nperiodcode)do nothing;;
INSERT INTO period (nperiodcode, jsondata, ndefaultstatus, nsitecode, nstatus,speriodname) VALUES (6, '{"ndata": 525600, "nmaxlength": 1, "speriodname": {"en-US": "Years", "ru-RU": "Годы", "tg-TG": "Солхо"}, "sdescription": ""}', 4, -1, 1,'Years') on conflict(nperiodcode)do nothing;;
INSERT INTO period (nperiodcode, jsondata, ndefaultstatus, nsitecode, nstatus,speriodname) VALUES (7, '{"ndata": 0, "nmaxlength": 0, "speriodname": {"en-US": "Day Without Hours", "ru-RU": "День без часов", "tg-TG": "Рӯзи бе соат"}, "sdescription": ""}', 4, -1, 1,'Day Without Hours') on conflict(nperiodcode)do nothing;;
INSERT INTO period (nperiodcode, jsondata, ndefaultstatus, nsitecode, nstatus,speriodname) VALUES (8, '{"ndata": -1, "nmaxlength": 0, "speriodname": {"en-US": "Never", "ru-RU": "Никогда", "tg-TG": "Ҳеҷ гоҳ"}, "sdescription": "Never"}', 4, -1, 1,'Never') on conflict(nperiodcode)do nothing;;

update instrumenttype set status = -1 where instrumenttype.insttypename='RS232'  and status =1;
update instrumenttype set status = -1 where instrumenttype.insttypename='TCP/IP' and status =1;

update lsaudittrailconfigmaster set modulename='IDS_MDL_SETUP' where lsaudittrailconfigmaster.modulename='IDS_MDL_PARSER';
update lsaudittrailconfiguration set modulename='IDS_MDL_SETUP' where modulename='IDS_MDL_PARSER';

update lsaudittrailconfigmaster set taskname='IDS_TSK_DELETEPARSER' where serialno in (85,88,91,94,97);
update lsaudittrailconfiguration set taskname='IDS_TSK_DELETEPARSER' where modulename='IDS_MDL_SETUP' and taskname='IDS_TSK_DELETE';

update lsusergrouprights set screate='1' where displaytopic='IDS_TSK_FOLDERCREATION' and screate='NA';
update lsusergrouprights set screate='1' where displaytopic='IDS_TSK_FOLDERCREATIONPROTOCOL' and screate='NA';
update lsusergrouprights set screate='1' where displaytopic='IDS_TSK_ELNTASKORDER' and screate='NA';
update lsusergrouprightsmaster set screate='0' where displaytopic='IDS_TSK_FOLDERCREATION' and screate='NA';
update lsusergrouprightsmaster set screate='0' where displaytopic='IDS_TSK_FOLDERCREATIONPROTOCOL' and screate='NA';

INSERT INTO delimiter (delimitername,actualdelimiter,status,usercode,defaultvalue) SELECT 'Tab', '\t', 1, 1,1 WHERE NOT EXISTS (SELECT delimitername FROM delimiter WHERE delimitername = 'Tab');

update delimiter set lssitemaster_sitecode = NULL where defaultvalue=1;
update methoddelimiter set lssitemaster_sitecode = NULL where defaultvalue=1;

update parsermethod set parsermethodname='Endrowmatch' where parsermethodname='rowmatch';
update parsermethod set parsermethodname='Endcolmatch' where parsermethodname='colmatch';
update lssheetworkflow set status=1 where status is null;


update delimiter set createddate='2020-05-15 00:00:00.0000000' where createddate is Null;

update methoddelimiter set createddate='2020-05-15 00:00:00.0000000' where createddate is Null;

--INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder,screenname) VALUES (121, 'IDS_SCN_GRADEMASTER', 'IDS_MDL_INVENTORY','0', '0', '0', '0', '1,1,1',80,'IDS_SCN_GRADEMASTER') ON CONFLICT(orderno)DO NOTHING;
--INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_SCN_GRADEMASTER', 'IDS_MDL_INVENTORY', 'administrator', '1', '1', '1', '1', 1,1,'IDS_SCN_GRADEMASTER'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_SCN_GRADEMASTER' and usergroupid_usergroupcode = 1);
--INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder,screenname) VALUES (122, 'IDS_SCN_SUPPLIER', 'IDS_MDL_INVENTORY','0', '0', '0', '0', '1,1,1',81,'IDS_SCN_SUPPLIER') ON CONFLICT(orderno)DO NOTHING;
--INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_SCN_SUPPLIER', 'IDS_MDL_INVENTORY', 'administrator', '1', '1', '1', '1', 1,1,'IDS_SCN_SUPPLIER'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_SCN_SUPPLIER' and usergroupid_usergroupcode = 1);
--INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder,screenname) VALUES (123, 'IDS_SCN_MANUFACTURER', 'IDS_MDL_INVENTORY','0', '0', '0', '0', '1,1,1',82,'IDS_SCN_MANUFACTURER') ON CONFLICT(orderno)DO NOTHING;
--INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_SCN_MANUFACTURER', 'IDS_MDL_INVENTORY', 'administrator', '1', '1', '1', '1', 1,1,'IDS_SCN_MANUFACTURER'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_SCN_MANUFACTURER' and usergroupid_usergroupcode = 1);

Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(150,0,'IDS_MDL_INVENTORY',101,'IDS_SCN_GRADEMASTER','IDS_TSK_SAVEGRADE') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(151,0,'IDS_MDL_INVENTORY',102,'IDS_SCN_GRADEMASTER','IDS_TSK_DELETEGRADE') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(152,0,'IDS_MDL_INVENTORY',103,'IDS_SCN_SUPPLIER','IDS_TSK_SAVESUPPLIER') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(153,0,'IDS_MDL_INVENTORY',104,'IDS_SCN_SUPPLIER','IDS_TSK_DELETESUPPLIER') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(154,0,'IDS_MDL_INVENTORY',105,'IDS_SCN_MANUFACTURER','IDS_TSK_SAVEMANUFACTURER') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(155,0,'IDS_MDL_INVENTORY',106,'IDS_SCN_MANUFACTURER','IDS_TSK_DELETEMANUFACTURER') ON CONFLICT(serialno)DO NOTHING;

update lsusergrouprightsmaster set sequenceorder=80 where displaytopic ='IDS_SCN_GRADEMASTER' ;
update lsusergrouprightsmaster set sequenceorder=81 where displaytopic ='IDS_SCN_SUPPLIER' ;
update lsusergrouprightsmaster set sequenceorder=82 where displaytopic ='IDS_SCN_MANUFACTURER' ;

update LSpreferences set valuesettings='Active',valueencrypted ='FrPnPlV4QlIH23zy6DkkeA==' where valuesettings='InActive' and valueencrypted is null and serialno=3;

update lsaudittrailconfigmaster set ordersequnce=100 where taskname ='IDS_TSK_SAVEGRADE' ;
update lsaudittrailconfigmaster set ordersequnce=101 where taskname ='IDS_TSK_DELETEGRADE' ;
update lsaudittrailconfigmaster set ordersequnce=103 where taskname ='IDS_TSK_SAVESUPPLIER' ;
update lsaudittrailconfigmaster set ordersequnce=104 where taskname ='IDS_TSK_DELETESUPPLIER' ;
update lsaudittrailconfigmaster set ordersequnce=105 where taskname ='IDS_TSK_SAVEMANUFACTURER' ;
update lsaudittrailconfigmaster set ordersequnce=106 where taskname ='IDS_TSK_DELETEMANUFACTURER' ;

update lsaudittrailconfiguration set ordersequnce=29 where screenname ='IDS_SCN_GRADEMASTER' ;
update lsaudittrailconfiguration set ordersequnce=30 where screenname ='IDS_SCN_SUPPLIER' ;
update lsaudittrailconfiguration set ordersequnce=31 where screenname ='IDS_SCN_MANUFACTURER' ;

delete from datatype where datatypename='Integer';
delete from lsusergrouprightsmaster where displaytopic in('IDS_SCN_REPORTS','IDS_TSK_NEWDOCUMENT','IDS_TSK_NEWTEMP','IDS_TSK_GENERATEREPORT','IDS_TSK_OPENREPORT','IDS_TSK_IMPORTDOCX');
delete from lsusergrouprights where displaytopic in('IDS_SCN_REPORTS','IDS_TSK_NEWDOCUMENT','IDS_TSK_NEWTEMP','IDS_TSK_GENERATEREPORT','IDS_TSK_OPENREPORT','IDS_TSK_IMPORTDOCX');

INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder,screenname) VALUES (34, 'IDS_SCN_REPORTDESIGNERNEWFOLDER', 'IDS_MDL_REPORTS', '0', '0', 'NA', 'NA', '0,0,0',67,'IDS_SCN_REPORTDESIGNER') ON CONFLICT(orderno)DO NOTHING;
INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder,screenname) VALUES (58, 'IDS_SCN_REPORTDESIGNER', 'IDS_MDL_REPORTS', '0', '0', 'NA', 'NA', '0,0,0',68,'IDS_SCN_REPORTDESIGNER') ON CONFLICT(orderno)DO NOTHING;
INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder,screenname) VALUES (45, 'IDS_SCN_REPORTVIEWERNEWFOLDER', 'IDS_MDL_REPORTS', '0', '0', 'NA', 'NA', '0,0,0',69,'IDS_SCN_REPORTVIEWER') ON CONFLICT(orderno)DO NOTHING; 
INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename, sallow, screate,sdelete, sedit, status,sequenceorder,screenname) VALUES (35, 'IDS_SCN_REPORTVIEWER', 'IDS_MDL_REPORTS', '0', '0', 'NA', 'NA', '0,0,0',70,'IDS_SCN_REPORTVIEWER') ON CONFLICT(orderno)DO NOTHING; 
 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_SCN_REPORTDESIGNERNEWFOLDER', 'IDS_MDL_REPORTS', 'administrator', '1', '1', 'NA', 'NA', 1,1,'IDS_SCN_REPORTDESIGNER'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_SCN_REPORTDESIGNERNEWFOLDER' and usergroupid_usergroupcode = 1);
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_SCN_REPORTDESIGNER', 'IDS_MDL_REPORTS', 'administrator', '1', '1', 'NA', 'NA', 1,1,'IDS_SCN_REPORTDESIGNER'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_SCN_REPORTDESIGNER' and usergroupid_usergroupcode = 1);
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_SCN_REPORTVIEWERNEWFOLDER', 'IDS_MDL_REPORTS', 'administrator', '1', '1', 'NA', 'NA', 1,1,'IDS_SCN_REPORTVIEWER'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_SCN_REPORTVIEWERNEWFOLDER' and usergroupid_usergroupcode = 1);
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_SCN_REPORTVIEWER', 'IDS_MDL_REPORTS', 'administrator', '1', '1', 'NA', 'NA', 1,1,'IDS_SCN_REPORTVIEWER'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_SCN_REPORTVIEWER' and usergroupid_usergroupcode = 1); 

update lsusergrouprightsmaster set sdelete='0' where sdelete='NA' and displaytopic='IDS_SCN_ORDERWORKLOW';
update lsusergrouprights set sdelete='1' where displaytopic='IDS_SCN_ORDERWORKLOW' and usergroupid_usergroupcode=1;
update lsusergrouprights set sdelete='0' where sdelete='NA' and displaytopic='IDS_SCN_ORDERWORKLOW' and usergroupid_usergroupcode!=1;
update lsusergrouprightsmaster set sdelete='0' where sdelete='NA' and displaytopic='IDS_SCN_TEMPLATEWORKFLOW';
update lsusergrouprights set sdelete='1' where displaytopic='IDS_SCN_TEMPLATEWORKFLOW' and usergroupid_usergroupcode=1;
update lsusergrouprights set sdelete='0' where sdelete='NA' and displaytopic='IDS_SCN_TEMPLATEWORKFLOW' and usergroupid_usergroupcode!=1;
update lsusergrouprightsmaster set screate='0' where screate='NA' and displaytopic='IDS_SCN_MATERIALTYPE';
update lsusergrouprights set screate='1' where displaytopic='IDS_SCN_MATERIALTYPE' and usergroupid_usergroupcode=1;
update lsusergrouprights set screate='0' where screate='NA' and displaytopic='IDS_SCN_MATERIALTYPE' and usergroupid_usergroupcode!=1;

Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(44,0,'IDS_MDL_REPORTS',98,'IDS_SCN_REPORTVIEWER','IDS_TSK_SAVE') ON CONFLICT(serialno)DO NOTHING;

update LSaudittrailconfigmaster set taskname='IDS_TSK_RDEWFOLDER' WHERE serialno=47;
update LSaudittrailconfigmaster set taskname='IDS_TSK_RDOPEN' where serialno=49;
update LSaudittrailconfigmaster set taskname='IDS_TSK_SAVE' where serialno=45;
update LSaudittrailconfigmaster set taskname='IDS_TSK_RDEWFOLDER' where serialno=48;
update LSaudittrailconfigmaster set taskname='IDS_TSK_RDOPEN' where serialno=43;
update LSaudittrailconfigmaster set taskname='IDS_TSK_CONTINUE' where serialno=46;

update LSaudittrailconfigmaster set screenname='IDS_SCN_REPORTDESIGNER' WHERE serialno in (47,49,45);
update LSaudittrailconfigmaster set screenname='IDS_SCN_REPORTVIEWER' WHERE serialno in (48,43,46);

update LSaudittrailconfigmaster set ordersequnce=99 where serialno=118;
update LSaudittrailconfigmaster set ordersequnce=100 where serialno=141;
update LSaudittrailconfigmaster set ordersequnce=101 where serialno=150;
update LSaudittrailconfigmaster set ordersequnce=102 where serialno=119;
update LSaudittrailconfigmaster set ordersequnce=103 where serialno=151;
update LSaudittrailconfigmaster set ordersequnce=104 where serialno=152;
update LSaudittrailconfigmaster set ordersequnce=105 where serialno=153;
update LSaudittrailconfigmaster set ordersequnce=106 where serialno=154;
update LSaudittrailconfigmaster set ordersequnce=107 where serialno=155;
update LSaudittrailconfigmaster set ordersequnce=108 where serialno=120;
update LSaudittrailconfigmaster set ordersequnce=109 where serialno=142;
update LSaudittrailconfigmaster set ordersequnce=110 where serialno=121;
update LSaudittrailconfigmaster set ordersequnce=111 where serialno=122;
update LSaudittrailconfigmaster set ordersequnce=112 where serialno=143;
update LSaudittrailconfigmaster set ordersequnce=113 where serialno=123;
update LSaudittrailconfigmaster set ordersequnce=114 where serialno=124;
update LSaudittrailconfigmaster set ordersequnce=115 where serialno=144;
update LSaudittrailconfigmaster set ordersequnce=116 where serialno=125;
update LSaudittrailconfigmaster set ordersequnce=117 where serialno=126;
update LSaudittrailconfigmaster set ordersequnce=118 where serialno=145;
update LSaudittrailconfigmaster set ordersequnce=119 where serialno=127;
update LSaudittrailconfigmaster set ordersequnce=120 where serialno=128;
update LSaudittrailconfigmaster set ordersequnce=121 where serialno=146;
update LSaudittrailconfigmaster set ordersequnce=122 where serialno=129;
update LSaudittrailconfigmaster set ordersequnce=123 where serialno=132;
update LSaudittrailconfigmaster set ordersequnce=124 where serialno=133;
update LSaudittrailconfigmaster set ordersequnce=125 where serialno=135;
update LSaudittrailconfigmaster set ordersequnce=126 where serialno=134;
update LSaudittrailconfigmaster set ordersequnce=127 where serialno=81;

update LSusersteam set createdate=modifieddate where createdate is Null;

ALTER TABLE IF EXISTS materialinventory ALTER COLUMN expirydate TYPE timestamp without time zone;

INSERT INTO lsmultisites (usercode, lssitemaster_sitecode, defaultsiteMaster) SELECT lsusermaster.usercode, lssitemaster.sitecode, 1 FROM lsusermaster JOIN lssitemaster ON lsusermaster.lssitemaster_sitecode = lssitemaster.sitecode WHERE (SELECT COUNT(*) FROM lsmultisites) = 0;

update method set converterstatus = 1 where converterstatus is Null;

--INSERT INTO Elnprotocolworkflow SELECT * FROM LSworkflow WHERE (SELECT COUNT(*) FROM Elnprotocolworkflow) = 0 AND (SELECT COUNT(*) FROM LSworkflow) != 0;

--INSERT INTO elnprotocolworkflowgroupmap SELECT * FROM LSworkflowgroupmapping WHERE (SELECT COUNT(*) FROM elnprotocolworkflowgroupmap) = 0  AND (SELECT COUNT(*) FROM LSworkflowgroupmapping) != 0 AND workflowcode IS NOT NULL AND lsusergroup_usergroupcode IS NOT NULL;


-- INSERT INTO Elnprotocolworkflow
-- SELECT * FROM LSworkflow
-- WHERE (
--   SELECT COUNT(*)
--   FROM Elnprotocolworkflow
-- ) = 0
-- AND (
--   SELECT COUNT(*)
--   FROM LSworkflow
-- ) != 0
-- AND (
--   SELECT COUNT(*)
--   FROM elnprotocolworkflowgroupmap
-- ) = 0
-- AND (
--   SELECT COUNT(DISTINCT lsworkflow_workflowcode)
--   FROM lslogilabprotocoldetail
--   WHERE lsworkflow_workflowcode IS NOT NULL
-- ) != 0;

-- INSERT INTO elnprotocolworkflowgroupmap
-- SELECT * FROM LSworkflowgroupmapping
-- WHERE workflowcode IS NOT NULL
--   AND lsusergroup_usergroupcode IS NOT NULL
--   AND (
--     SELECT COUNT(*)
--     FROM LSworkflowgroupmapping
--   ) != 0
--   AND workflowcode IN (
--     SELECT distinct lsworkflow_workflowcode
--     FROM lslogilabprotocoldetail
--     WHERE lsworkflow_workflowcode IS NOT NULL
--   );

UPDATE lslogilabprotocoldetail SET elnprotocolworkflow_workflowcode = lsworkflow_workflowcode WHERE (SELECT COUNT(*) FROM lslogilabprotocoldetail WHERE elnprotocolworkflow_workflowcode IS NOT NULL) = 0;

-- INSERT INTO ElnprotocolTemplateworkflow (workflowcode, status, workflowname, lssitemaster_sitecode)SELECT workflowcode, status, workflowname, lssitemaster_sitecode FROM LSsheetworkflow WHERE (SELECT COUNT(*) FROM ElnprotocolTemplateworkflow) = 0AND (SELECT COUNT(*)FROM LSsheetworkflow) != 0;

-- INSERT INTO ElnprotocolTemplateworkflowgroupmap SELECT * FROM LSsheetworkflowgroupmap WHERE (SELECT COUNT(*) FROM ElnprotocolTemplateworkflowgroupmap) = 0 AND (SELECT COUNT(*) FROM LSsheetworkflowgroupmap WHERE workflowcode IS NOT NULL AND lsusergroup_usergroupcode IS NOT NULL ) != 0;


--WITH inserted_workflow AS (INSERT INTO ElnprotocolTemplateworkflow SELECT workflowcode, status, workflowname, lssitemaster_sitecode FROM LSsheetworkflow WHERE (SELECT COUNT(*) FROM ElnprotocolTemplateworkflow) = 0 AND (SELECT COUNT(*) FROM LSsheetworkflow) != 0 AND (SELECT COUNT(DISTINCT lssheetworkflow_workflowcode) FROM lsprotocolmaster WHERE lssheetworkflow_workflowcode IS NOT NULL) != 0 RETURNING workflowcode) INSERT INTO ElnprotocolTemplateworkflowgroupmap SELECT * FROM LSsheetworkflowgroupmap WHERE workflowcode IN (SELECT workflowcode FROM inserted_workflow) AND lsusergroup_usergroupcode IS NOT NULL AND (SELECT COUNT(*) FROM LSsheetworkflowgroupmap) != 0;


ALTER TABLE ElnprotocolTemplateworkflow ALTER COLUMN workflowname TYPE varchar(255);

ALTER TABLE elnprotocoltemplateworkflowgroupmap ALTER COLUMN workflowmapid SET DEFAULT nextval('elnprotocoltemplateworkflowgroupmap_seq');

INSERT INTO elnprotocoltemplateworkflow (workflowcode, status, workflowname, lssitemaster_sitecode)
SELECT 
    CAST(ls.workflowcode AS INTEGER),
    ls.status,
    ls.workflowname,
    ls.lssitemaster_sitecode
FROM lssheetworkflow ls
WHERE 
    NOT EXISTS (SELECT 1 FROM elnprotocoltemplateworkflow)
    AND EXISTS (SELECT 1 FROM lssheetworkflow)
    AND EXISTS (
        SELECT 1 
        FROM lsprotocolmaster 
        WHERE lssheetworkflow_workflowcode IS NOT NULL
    );

INSERT INTO elnprotocoltemplateworkflowgroupmap (workflowcode, lsusergroup_usergroupcode)
SELECT 
    CAST(lsg.workflowcode AS INTEGER),
    lsg.lsusergroup_usergroupcode
FROM lssheetworkflowgroupmap lsg
WHERE 
    CAST(lsg.workflowcode AS INTEGER) IN (
        SELECT workflowcode FROM elnprotocoltemplateworkflow
    )
    AND lsg.lsusergroup_usergroupcode IS NOT NULL
ON CONFLICT DO NOTHING;

UPDATE LSprotocolorderworkflowhistory SET elnprotocolworkflow_workflowcode = lsworkflow_workflowcode WHERE elnprotocolworkflow_workflowcode IS NULL OR elnprotocolworkflow_workflowcode = 0;

-- INSERT INTO ElnprotocolTemplateworkflow (workflowcode, status, workflowname, lssitemaster_sitecode) SELECT workflowcode, status, workflowname, lssitemaster_sitecode FROM LSsheetworkflow WHERE (SELECT COUNT(*) FROM ElnprotocolTemplateworkflow) = 0 AND (SELECT COUNT(*) FROM LSsheetworkflow) != 0;

--INSERT INTO ElnprotocolTemplateworkflowgroupmap SELECT * FROM LSsheetworkflowgroupmap WHERE workflowcode IS NOT NULL AND lsusergroup_usergroupcode IS NOT NULL AND (SELECT COUNT(*) FROM ElnprotocolTemplateworkflowgroupmap) = 0 AND (SELECT COUNT(*) FROM LSsheetworkflowgroupmap) != 0 AND (SELECT COUNT(*) FROM ElnprotocolTemplateworkflow) != 0;

UPDATE lsprotocolworkflowhistory SET elnprotocoltemplateworkflow_workflowcode = lssheetworkflow_workflowcode WHERE ( SELECT COUNT(*) FROM lsprotocolworkflowhistory WHERE elnprotocoltemplateworkflow_workflowcode IS NOT NULL) = 0;

UPDATE lsprotocolmaster SET elnprotocoltemplateworkflow_workflowcode = lssheetworkflow_workflowcode WHERE ( SELECT COUNT(*) FROM lsprotocolmaster WHERE elnprotocoltemplateworkflow_workflowcode IS NOT NULL ) = 0;

ALTER TABLE IF Exists lsactiveuser ADD Column IF NOT EXISTS removeinititated BOOLEAN;
update LSactiveuser set removeinititated =false where removeinititated is null;
ALTER TABLE IF Exists LSprotocolfiles ADD Column IF NOT EXISTS islinkfile boolean;
ALTER TABLE IF Exists LSprotocolimages ADD Column IF NOT EXISTS islinkimage boolean;
update Notification set screen='Sheet Order' where screen is null;
INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename,screenname, sallow, screate,sdelete, sedit, status,sequenceorder) VALUES (124, 'IDS_TSK_RETIRE', 'IDS_MDL_TEMPLATES', 'IDS_SCN_SHEETTEMPLATE','0', 'NA', 'NA', 'NA', '0,0,0',88) ON CONFLICT(orderno)DO NOTHING;
INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename,screenname, sallow, screate,sdelete, sedit, status,sequenceorder) VALUES (125, 'IDS_TSK_RETIRE', 'IDS_MDL_TEMPLATES', 'IDS_SCN_PROTOCOLTEMPLATE','0', 'NA', 'NA', 'NA', '0,0,0',89) ON CONFLICT(orderno)DO NOTHING;
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_RETIRE', 'IDS_MDL_TEMPLATES', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SHEETTEMPLATE'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_RETIRE' and screenname='IDS_SCN_SHEETTEMPLATE' and usergroupid_usergroupcode = 1);    
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_RETIRE', 'IDS_MDL_TEMPLATES', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLTEMPLATE'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_RETIRE' and screenname='IDS_SCN_PROTOCOLTEMPLATE' and usergroupid_usergroupcode = 1); 

-- Drop the existing foreign key constraint
ALTER TABLE lsorderattachments
DROP CONSTRAINT IF EXISTS fk701k777d2da33pkkl6lnamavi;

-- Add a new foreign key constraint referencing elnmaterial table
ALTER TABLE lsorderattachments
ADD CONSTRAINT fk701k777d2da33pkkl6lnamavi
FOREIGN KEY (nmaterialcode)
REFERENCES elnmaterial (nmaterialcode);


-- Drop the existing foreign key constraint
--ALTER TABLE lsorderattachments
--DROP CONSTRAINT IF EXISTS fkbvkyp7mg7pxs4oxsg45bmnj6l;

-- Add a new foreign key constraint referencing elnmaterialinventory table
--ALTER TABLE lsorderattachments
--ADD CONSTRAINT fkbvkyp7mg7pxs4oxsg45bmnj6l
--FOREIGN KEY (nmaterialinventorycode)
--REFERENCES elnmaterialinventory (nmaterialinventorycode);

-- Attempt to insert into samplestoragelocation, but only if a record with samplestoragelocationkey = -1 does not exist
INSERT INTO public.samplestoragelocation(
    samplestoragelocationkey, createdby, createddate, samplestoragelocationname, sitekey, status)
VALUES (-1, 'Administrator', now(), 'Default', 1, 1)
ON CONFLICT (samplestoragelocationkey) DO NOTHING;

-- Attempt to insert into samplestorageversion, but only if a record with samplestorageversionkey = -1 does not exist
INSERT INTO public.samplestorageversion(
    samplestorageversionkey, approvalstatus, createdby, createddate, jsonbresult, versionno, samplestoragelocationkey)
VALUES (-1, 1, 'Administrator', now(), '[{"text":"Default node","expanded":true,"editable":false,"root":true,"id":"e9052aac-b470-44e0-a2de-5652f7475b67"}]', 1, -1)
ON CONFLICT (samplestorageversionkey) DO NOTHING;

-- Drop the existing foreign key constraint
ALTER TABLE materialinventory
DROP CONSTRAINT IF EXISTS fk7yqljj9kujvyn17yn9y0lwiqn;

-- Drop the existing foreign key constraint
ALTER TABLE selectedinventorymapped
DROP CONSTRAINT IF EXISTS fk7yqljj9kujvyn17yn9y0lwiqn;

update lsusergrouprightsmaster set sequenceorder=1 where orderno =2;
update lsusergrouprightsmaster set sequenceorder=2 where orderno =1;
update lsusergrouprightsmaster set sequenceorder=3 where orderno =3;
update lsusergrouprightsmaster set sequenceorder=4 where orderno =93;
update lsusergrouprightsmaster set sequenceorder=5 where orderno =85;
update lsusergrouprightsmaster set sequenceorder=6 where orderno =8;
update lsusergrouprightsmaster set sequenceorder=7 where orderno =9;
update lsusergrouprightsmaster set sequenceorder=8 where orderno =37;
update lsusergrouprightsmaster set sequenceorder=9 where orderno =38;
update lsusergrouprightsmaster set sequenceorder=10 where orderno =110;
update lsusergrouprightsmaster set sequenceorder=11 where orderno =61;
update lsusergrouprightsmaster set sequenceorder=12 where orderno =62;
update lsusergrouprightsmaster set sequenceorder=13 where orderno =96;
update lsusergrouprightsmaster set sequenceorder=14 where orderno =91;
update lsusergrouprightsmaster set sequenceorder=15 where orderno =94;
update lsusergrouprightsmaster set sequenceorder=16 where orderno =86;
update lsusergrouprightsmaster set sequenceorder=17 where orderno =48;
update lsusergrouprightsmaster set sequenceorder=18 where orderno =49;
update lsusergrouprightsmaster set sequenceorder=19 where orderno =111;
update lsusergrouprightsmaster set sequenceorder=20 where orderno =80;
update lsusergrouprightsmaster set sequenceorder=21 where orderno =81;
update lsusergrouprightsmaster set sequenceorder=22 where orderno =92;
update lsusergrouprightsmaster set sequenceorder=23 where orderno =10;
update lsusergrouprightsmaster set sequenceorder=24 where orderno =69;
update lsusergrouprightsmaster set sequenceorder=25 where orderno =70;
update lsusergrouprightsmaster set sequenceorder=26 where orderno =88;
update lsusergrouprightsmaster set sequenceorder=27 where orderno =124;
update lsusergrouprightsmaster set sequenceorder=28 where orderno =51;
update lsusergrouprightsmaster set sequenceorder=29 where orderno =50;
update lsusergrouprightsmaster set sequenceorder=30 where orderno =84;
update lsusergrouprightsmaster set sequenceorder=31 where orderno =83;
update lsusergrouprightsmaster set sequenceorder=32 where orderno =57;
update lsusergrouprightsmaster set sequenceorder=33 where orderno =125;
update lsusergrouprightsmaster set sequenceorder=34 where orderno =71;
update lsusergrouprightsmaster set sequenceorder=35 where orderno =72;
update lsusergrouprightsmaster set sequenceorder=36 where orderno =13;
update lsusergrouprightsmaster set sequenceorder=37 where orderno =78;
update lsusergrouprightsmaster set sequenceorder=38 where orderno =17;
update lsusergrouprightsmaster set sequenceorder=39 where orderno =21;
update lsusergrouprightsmaster set sequenceorder=40 where orderno =16;
update lsusergrouprightsmaster set sequenceorder=41 where orderno =23;
update lsusergrouprightsmaster set sequenceorder=42 where orderno =22;
update lsusergrouprightsmaster set sequenceorder=43 where orderno =24;
update lsusergrouprightsmaster set sequenceorder=44 where orderno =52;
update lsusergrouprightsmaster set sequenceorder=45 where orderno =18;
update lsusergrouprightsmaster set sequenceorder=46 where orderno =12;
update lsusergrouprightsmaster set sequenceorder=47 where orderno =19;
update lsusergrouprightsmaster set sequenceorder=48 where orderno =11;
update lsusergrouprightsmaster set sequenceorder=49 where orderno =20;
update lsusergrouprightsmaster set sequenceorder=50 where orderno =41;
update lsusergrouprightsmaster set sequenceorder=51 where orderno =43;
update lsusergrouprightsmaster set sequenceorder=52 where orderno =68;
update lsusergrouprightsmaster set sequenceorder=53 where orderno =39;
update lsusergrouprightsmaster set sequenceorder=54 where orderno =54;
update lsusergrouprightsmaster set sequenceorder=55 where orderno =55;
update lsusergrouprightsmaster set sequenceorder=56 where orderno =56;
update lsusergrouprightsmaster set sequenceorder=57 where orderno =25;
update lsusergrouprightsmaster set sequenceorder=58 where orderno =28;
update lsusergrouprightsmaster set sequenceorder=59 where orderno =29;
update lsusergrouprightsmaster set sequenceorder=60 where orderno =30;
update lsusergrouprightsmaster set sequenceorder=61 where orderno =31;
update lsusergrouprightsmaster set sequenceorder=62 where orderno =32;
update lsusergrouprightsmaster set sequenceorder=63 where orderno =26;
update lsusergrouprightsmaster set sequenceorder=64 where orderno =27;
update lsusergrouprightsmaster set sequenceorder=65 where orderno =34;
update lsusergrouprightsmaster set sequenceorder=66 where orderno =58;
update lsusergrouprightsmaster set sequenceorder=67 where orderno =45;
update lsusergrouprightsmaster set sequenceorder=68 where orderno =35;
update lsusergrouprightsmaster set sequenceorder=69 where orderno =120;
update lsusergrouprightsmaster set sequenceorder=70 where orderno =99;
update lsusergrouprightsmaster set sequenceorder=71 where orderno =65;
update lsusergrouprightsmaster set sequenceorder=72 where orderno =66;
update lsusergrouprightsmaster set sequenceorder=73 where orderno =67;
update lsusergrouprightsmaster set sequenceorder=74 where orderno =101;
update lsusergrouprightsmaster set sequenceorder=75 where orderno =103;
update lsusergrouprightsmaster set sequenceorder=76 where orderno =104;
update lsusergrouprightsmaster set sequenceorder=77 where orderno =106;
update lsusergrouprightsmaster set sequenceorder=78 where orderno =107;
update lsusergrouprightsmaster set sequenceorder=79 where orderno =121;
update lsusergrouprightsmaster set sequenceorder=80 where orderno =122;
update lsusergrouprightsmaster set sequenceorder=81 where orderno =123;
update lsusergrouprightsmaster set sequenceorder=82 where orderno =95;
update lsusergrouprightsmaster set sequenceorder=83 where orderno =97;
update lsusergrouprightsmaster set sequenceorder=84 where orderno =98;
update lsusergrouprightsmaster set sequenceorder=85 where orderno =108;
update lsusergrouprightsmaster set sequenceorder=86 where orderno =109;

Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(156,0,'IDS_MDL_TEMPLATES',128,'IDS_SCN_SHEETTEMPLATES','IDS_TSK_RETIRE') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(157,0,'IDS_MDL_TEMPLATES',129,'IDS_SCN_PROTOCOLTEMP','IDS_TSK_RETIRE') ON CONFLICT(serialno)DO NOTHING;

update lsaudittrailconfigmaster set ordersequnce=27 where serialno=114;
update lsaudittrailconfigmaster set ordersequnce=28 where serialno=156;
update lsaudittrailconfigmaster set ordersequnce=29 where serialno=58;
update lsaudittrailconfigmaster set ordersequnce=30 where serialno=137;
update lsaudittrailconfigmaster set ordersequnce=31 where serialno=59;
update lsaudittrailconfigmaster set ordersequnce=32 where serialno=61;
update lsaudittrailconfigmaster set ordersequnce=33 where serialno=157;
update lsaudittrailconfigmaster set ordersequnce=34 where serialno=60;
update lsaudittrailconfigmaster set ordersequnce=35 where serialno=10;
update lsaudittrailconfigmaster set ordersequnce=36 where serialno=4;
update lsaudittrailconfigmaster set ordersequnce=37 where serialno=138;
update lsaudittrailconfigmaster set ordersequnce=38 where serialno=5;
update lsaudittrailconfigmaster set ordersequnce=39 where serialno=7;
update lsaudittrailconfigmaster set ordersequnce=40 where serialno=139;
update lsaudittrailconfigmaster set ordersequnce=41 where serialno=6;
update lsaudittrailconfigmaster set ordersequnce=42 where serialno=9;
update lsaudittrailconfigmaster set ordersequnce=43 where serialno=140;
update lsaudittrailconfigmaster set ordersequnce=44 where serialno=8;
update lsaudittrailconfigmaster set ordersequnce=45 where serialno=115;
update lsaudittrailconfigmaster set ordersequnce=46 where serialno=50;
update lsaudittrailconfigmaster set ordersequnce=47 where serialno=51;
update lsaudittrailconfigmaster set ordersequnce=48 where serialno=11;
update lsaudittrailconfigmaster set ordersequnce=49 where serialno=12;
update lsaudittrailconfigmaster set ordersequnce=50 where serialno=13;
update lsaudittrailconfigmaster set ordersequnce=51 where serialno=14;
update lsaudittrailconfigmaster set ordersequnce=52 where serialno=15;
update lsaudittrailconfigmaster set ordersequnce=53 where serialno=16;
update lsaudittrailconfigmaster set ordersequnce=54 where serialno=17;
update lsaudittrailconfigmaster set ordersequnce=55 where serialno=18;
update lsaudittrailconfigmaster set ordersequnce=56 where serialno=116;
update lsaudittrailconfigmaster set ordersequnce=57 where serialno=54;
update lsaudittrailconfigmaster set ordersequnce=58 where serialno=55;
update lsaudittrailconfigmaster set ordersequnce=59 where serialno=19;
update lsaudittrailconfigmaster set ordersequnce=60 where serialno=20;
update lsaudittrailconfigmaster set ordersequnce=61 where serialno=21;
update lsaudittrailconfigmaster set ordersequnce=62 where serialno=22;
update lsaudittrailconfigmaster set ordersequnce=63 where serialno=56;
update lsaudittrailconfigmaster set ordersequnce=64 where serialno=32;
update lsaudittrailconfigmaster set ordersequnce=65 where serialno=57;
update lsaudittrailconfigmaster set ordersequnce=66 where serialno=33;
update lsaudittrailconfigmaster set ordersequnce=67 where serialno=34;
update lsaudittrailconfigmaster set ordersequnce=68 where serialno=35;
update lsaudittrailconfigmaster set ordersequnce=69 where serialno=84;
update lsaudittrailconfigmaster set ordersequnce=70 where serialno=147;
update lsaudittrailconfigmaster set ordersequnce=71 where serialno=85;
update lsaudittrailconfigmaster set ordersequnce=72 where serialno=86;
update lsaudittrailconfigmaster set ordersequnce=73 where serialno=87;
update lsaudittrailconfigmaster set ordersequnce=74 where serialno=88;
update lsaudittrailconfigmaster set ordersequnce=75 where serialno=89;
update lsaudittrailconfigmaster set ordersequnce=76 where serialno=90;
update lsaudittrailconfigmaster set ordersequnce=77 where serialno=91;
update lsaudittrailconfigmaster set ordersequnce=78 where serialno=92;
update lsaudittrailconfigmaster set ordersequnce=79 where serialno=93;
update lsaudittrailconfigmaster set ordersequnce=80 where serialno=94;
update lsaudittrailconfigmaster set ordersequnce=81 where serialno=95;
update lsaudittrailconfigmaster set ordersequnce=82 where serialno=96;
update lsaudittrailconfigmaster set ordersequnce=83 where serialno=97;
update lsaudittrailconfigmaster set ordersequnce=84 where serialno=23;
update lsaudittrailconfigmaster set ordersequnce=85 where serialno=117;
update lsaudittrailconfigmaster set ordersequnce=86 where serialno=24;
update lsaudittrailconfigmaster set ordersequnce=87 where serialno=25;
update lsaudittrailconfigmaster set ordersequnce=88 where serialno=26;
update lsaudittrailconfigmaster set ordersequnce=89 where serialno=27;
update lsaudittrailconfigmaster set ordersequnce=90 where serialno=28;
update lsaudittrailconfigmaster set ordersequnce=91 where serialno=42;
update lsaudittrailconfigmaster set ordersequnce=92 where serialno=47;
update lsaudittrailconfigmaster set ordersequnce=93 where serialno=49;
update lsaudittrailconfigmaster set ordersequnce=94 where serialno=45;
update lsaudittrailconfigmaster set ordersequnce=95 where serialno=48;
update lsaudittrailconfigmaster set ordersequnce=96 where serialno=43;
update lsaudittrailconfigmaster set ordersequnce=97 where serialno=46;
update lsaudittrailconfigmaster set ordersequnce=98 where serialno=44;
update lsaudittrailconfigmaster set ordersequnce=99 where serialno=118;
update lsaudittrailconfigmaster set ordersequnce=100 where serialno=141;
update lsaudittrailconfigmaster set ordersequnce=101 where serialno=150;
update lsaudittrailconfigmaster set ordersequnce=102 where serialno=119;
update lsaudittrailconfigmaster set ordersequnce=103 where serialno=151;
update lsaudittrailconfigmaster set ordersequnce=104 where serialno=152;
update lsaudittrailconfigmaster set ordersequnce=105 where serialno=153;
update lsaudittrailconfigmaster set ordersequnce=106 where serialno=154;
update lsaudittrailconfigmaster set ordersequnce=107 where serialno=155;
update lsaudittrailconfigmaster set ordersequnce=108 where serialno=120;
update lsaudittrailconfigmaster set ordersequnce=109 where serialno=142;
update lsaudittrailconfigmaster set ordersequnce=110 where serialno=121;
update lsaudittrailconfigmaster set ordersequnce=111 where serialno=122;
update lsaudittrailconfigmaster set ordersequnce=112 where serialno=143;
update lsaudittrailconfigmaster set ordersequnce=113 where serialno=123;
update lsaudittrailconfigmaster set ordersequnce=114 where serialno=124;
update lsaudittrailconfigmaster set ordersequnce=115 where serialno=144;
update lsaudittrailconfigmaster set ordersequnce=116 where serialno=125;
update lsaudittrailconfigmaster set ordersequnce=117 where serialno=126;
update lsaudittrailconfigmaster set ordersequnce=118 where serialno=145;
update lsaudittrailconfigmaster set ordersequnce=119 where serialno=127;
update lsaudittrailconfigmaster set ordersequnce=120 where serialno=128;
update lsaudittrailconfigmaster set ordersequnce=121 where serialno=146;
update lsaudittrailconfigmaster set ordersequnce=122 where serialno=129;
update lsaudittrailconfigmaster set ordersequnce=123 where serialno=132;
update lsaudittrailconfigmaster set ordersequnce=124 where serialno=133;
update lsaudittrailconfigmaster set ordersequnce=125 where serialno=135;
update lsaudittrailconfigmaster set ordersequnce=126 where serialno=134;
update lsaudittrailconfigmaster set ordersequnce=127 where serialno=81;


update lsusergrouprightsmaster set sequenceorder = 48 where sequenceorder=37 and orderno=12;
update lsusergrouprightsmaster set sequenceorder = 50 where sequenceorder=36 and orderno=11;

update lsusergrouprightsmaster set sequenceorder = 36 where sequenceorder=38 and orderno=13;
update lsusergrouprightsmaster set sequenceorder = 37 where sequenceorder=39 and orderno=95;
update lsusergrouprightsmaster set sequenceorder = 38 where sequenceorder=40 and orderno=17;
update lsusergrouprightsmaster set sequenceorder = 39 where sequenceorder=41 and orderno=21;
update lsusergrouprightsmaster set sequenceorder = 40 where sequenceorder=42 and orderno=16;
update lsusergrouprightsmaster set sequenceorder = 41 where sequenceorder=43 and orderno=23;
update lsusergrouprightsmaster set sequenceorder = 42 where sequenceorder=44 and orderno=22;
update lsusergrouprightsmaster set sequenceorder = 43 where sequenceorder=45 and orderno=24;
update lsusergrouprightsmaster set sequenceorder = 44 where sequenceorder=46 and orderno=52;
update lsusergrouprightsmaster set sequenceorder = 45 where sequenceorder=47 and orderno=40;

update lsusergrouprightsmaster set sequenceorder = 46 where sequenceorder=48 and orderno=18;
update lsusergrouprightsmaster set sequenceorder = 45 where sequenceorder=47 and orderno=40;
update lsusergrouprightsmaster set sequenceorder = 45 where sequenceorder=47 and orderno=40;

update lsusergrouprightsmaster set sequenceorder = 47 where sequenceorder=48 and orderno=12;
update lsusergrouprightsmaster set sequenceorder = 48 where sequenceorder=49 and orderno=19;
update lsusergrouprightsmaster set sequenceorder = 49 where sequenceorder=50 and orderno=11;

update lsusergrouprightsmaster set modulename='IDS_MDL_SETUP' where screenname='IDS_SCN_PROJECTMASTER';
update lsusergrouprightsmaster set modulename='IDS_MDL_SETUP' where screenname='IDS_SCN_PROJECTTEAM';																		 
update lsusergrouprightsmaster set modulename='IDS_MDL_SETUP' where screenname='IDS_SCN_TASKMASTER';																		 

update lsusergrouprights set modulename='IDS_MDL_ORDERS'  where screenname='IDS_SCN_UNLOCKORDERS';
update lsusergrouprightsmaster set modulename='IDS_MDL_ORDERS'  where screenname='IDS_SCN_UNLOCKORDERS';

update lsusergrouprightsmaster set sequenceorder='23' where sequenceorder='37' and orderno='95';
update lsusergrouprightsmaster set sequenceorder='24' where sequenceorder='23' and orderno='10';
update lsusergrouprightsmaster set sequenceorder='25' where sequenceorder='24' and orderno='69';
update lsusergrouprightsmaster set sequenceorder='26' where sequenceorder='25' and orderno='70';
update lsusergrouprightsmaster set sequenceorder='27' where sequenceorder='26' and orderno='88';
update lsusergrouprightsmaster set sequenceorder='28' where sequenceorder='27' and orderno='51';
update lsusergrouprightsmaster set sequenceorder='29' where sequenceorder='28' and orderno='50';
update lsusergrouprightsmaster set sequenceorder='30' where sequenceorder='29' and orderno='84';
update lsusergrouprightsmaster set sequenceorder='31' where sequenceorder='30' and orderno='83';
update lsusergrouprightsmaster set sequenceorder='32' where sequenceorder='31' and orderno='57';
update lsusergrouprightsmaster set sequenceorder='33' where sequenceorder='32' and orderno='71';
update lsusergrouprightsmaster set sequenceorder='34' where sequenceorder='33' and orderno='72';
update lsusergrouprightsmaster set sequenceorder='35' where sequenceorder='34' and orderno='113';
update lsusergrouprightsmaster set sequenceorder='36' where sequenceorder='35' and orderno='114';
update lsusergrouprightsmaster set sequenceorder='37' where sequenceorder='36' and orderno='13';

update lsusergrouprights set modulename='IDS_MDL_ORDERS'  where screenname='IDS_SCN_UNLOCKORDERS';
update lsusergrouprights set modulename='IDS_MDL_SETUP' where screenname='IDS_SCN_PROJECTMASTER';
update lsusergrouprights set modulename='IDS_MDL_SETUP' where screenname='IDS_SCN_PROJECTTEAM';																		 
update lsusergrouprights set modulename='IDS_MDL_SETUP' where screenname='IDS_SCN_TASKMASTER';																		 

update lsaudittrailconfigmaster set ordersequnce=23 where ordersequnce=45 and serialno=115;

update lsaudittrailconfigmaster set ordersequnce=24 where ordersequnce=23 and serialno=113;
update lsaudittrailconfigmaster set ordersequnce=25 where ordersequnce=24 and serialno=136;
update lsaudittrailconfigmaster set ordersequnce=26 where ordersequnce=25 and serialno=3;
update lsaudittrailconfigmaster set ordersequnce=27 where ordersequnce=26 and serialno=41;
update lsaudittrailconfigmaster set ordersequnce=28 where ordersequnce=27 and serialno=114;
update lsaudittrailconfigmaster set ordersequnce=29 where ordersequnce=28 and serialno=156;
update lsaudittrailconfigmaster set ordersequnce=30 where ordersequnce=29 and serialno=58;
update lsaudittrailconfigmaster set ordersequnce=31 where ordersequnce=30 and serialno=137;
update lsaudittrailconfigmaster set ordersequnce=32 where ordersequnce=31 and serialno=59;
update lsaudittrailconfigmaster set ordersequnce=33 where ordersequnce=32 and serialno=61;
update lsaudittrailconfigmaster set ordersequnce=34 where ordersequnce=33 and serialno=157;
update lsaudittrailconfigmaster set ordersequnce=35 where ordersequnce=34 and serialno=60;
update lsaudittrailconfigmaster set ordersequnce=36 where ordersequnce=35 and serialno=10;
update lsaudittrailconfigmaster set ordersequnce=37 where ordersequnce=36 and serialno=4;
update lsaudittrailconfigmaster set ordersequnce=38 where ordersequnce=37 and serialno=138;
update lsaudittrailconfigmaster set ordersequnce=39 where ordersequnce=38 and serialno=5;

update lsaudittrailconfigmaster set ordersequnce=40 where ordersequnce=39 and serialno=7;
update lsaudittrailconfigmaster set ordersequnce=41 where ordersequnce=40 and serialno=139;
update lsaudittrailconfigmaster set ordersequnce=42 where ordersequnce=41 and serialno=6;
update lsaudittrailconfigmaster set ordersequnce=43 where ordersequnce=42 and serialno=9;
update lsaudittrailconfigmaster set ordersequnce=44 where ordersequnce=43 and serialno=140;
update lsaudittrailconfigmaster set ordersequnce=45 where ordersequnce=44 and serialno=8;

update lsaudittrailconfigmaster set modulename='IDS_MDL_ORDERS' where screenname='IDS_SCN_UNLOCKORDERS';

ALTER TABLE IF Exists lsactiveuser ADD Column IF NOT EXISTS removeinititated BOOLEAN;
ALTER TABLE IF Exists LSprotocolfiles ADD Column IF NOT EXISTS islinkfile boolean ;
ALTER TABLE IF Exists LSprotocolimages ADD Column IF NOT EXISTS islinkimage boolean;
update LSactiveuser set removeinititated =false where removeinititated is null;

ALTER TABLE IF Exists lslogilablimsorderdetail drop Column IF EXISTS duedate;

ALTER TABLE IF Exists lsfile ADD Column IF NOT EXISTS retirestatus integer;
update lsfile set retirestatus=0 where retirestatus is null;

ALTER TABLE IF Exists LSprotocolmaster ADD Column IF NOT EXISTS retirestatus integer;
update LSprotocolmaster set retirestatus=0 where retirestatus is null;

ALTER TABLE IF Exists LSlogilablimsorderdetail ADD Column IF NOT EXISTS ordersaved integer;
update LSlogilablimsorderdetail set ordersaved=0 where ordersaved is null;
ALTER TABLE IF Exists lsordernotification drop Column IF EXISTS status;

update Lsfileshareto set retirestatus=0 where retirestatus is null;
update Lsfilesharedby set retirestatus=0 where retirestatus is null;
update Lsprotocolshareto set retirestatus=0 where retirestatus is null;
update Lsprotocolsharedby set retirestatus=0 where retirestatus is null;

update lsactivewidgets set cancelstatus=0 where cancelstatus is null;

ALTER TABLE lstestmaster ALTER COLUMN description TYPE TEXT;
ALTER TABLE lssampleresult ALTER COLUMN result TYPE TEXT;

ALTER TABLE lsautoregister DROP CONSTRAINT IF EXISTS fkmdkcpw98le9x1bhlt3qi0py1k;

    -- Attempt to insert the new record
INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename,screenname, sallow, screate,sdelete, sedit, status,sequenceorder) VALUES (126, 'IDS_TSK_EQUIPMENTTYPE', 'IDS_MDL_INVENTORY', 'IDS_SCN_EQUIPMENT','0', '0', 'NA', 'NA', '0,0,0',82) ON CONFLICT(orderno)DO NOTHING;

-- Attempt to insert the new record
INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename,screenname, sallow, screate,sdelete, sedit, status,sequenceorder) VALUES (127, 'IDS_SCN_EQUIPMENTCATEGORY', 'IDS_MDL_INVENTORY', 'IDS_SCN_EQUIPMENT','0', '0', '0', '0', '0,0,0',83) ON CONFLICT(orderno)DO NOTHING;

-- Attempt to insert the new record
INSERT into lsusergrouprightsmaster(orderno, displaytopic, modulename,screenname, sallow, screate,sdelete, sedit, status,sequenceorder) VALUES (128, 'IDS_TSK_EQUIPMENTMASTER', 'IDS_MDL_INVENTORY', 'IDS_SCN_EQUIPMENTMASTER','0', '0', '0', '0', '0,0,0',84) ON CONFLICT(orderno)DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_EQUIPMENTTYPE', 'IDS_MDL_INVENTORY', 'administrator', '1', '1', 'NA', 'NA', 1,1,'IDS_SCN_EQUIPMENT'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_EQUIPMENTTYPE' and screenname='IDS_SCN_EQUIPMENT' and usergroupid_usergroupcode = 1); 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_SCN_EQUIPMENTCATEGORY', 'IDS_MDL_INVENTORY', 'administrator', '1', '1', '1', '1', 1,1,'IDS_SCN_EQUIPMENT'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_SCN_EQUIPMENTCATEGORY' and screenname='IDS_SCN_EQUIPMENT' and usergroupid_usergroupcode = 1); 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_EQUIPMENTMASTER', 'IDS_MDL_INVENTORY', 'administrator', '1', '1', '1', '1', 1,1,'IDS_SCN_EQUIPMENTMASTER'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_EQUIPMENTMASTER' and screenname='IDS_SCN_EQUIPMENTMASTER' and usergroupid_usergroupcode = 1); 

update lsusergrouprightsmaster set screenname = 'IDS_SCN_INVENTORY' where screenname != 'IDS_SCN_EQUIPMENT' and screenname != 'IDS_SCN_EQUIPMENTMASTER' and modulename = 'IDS_MDL_INVENTORY';
update lsusergrouprights set screenname = 'IDS_SCN_INVENTORY' where screenname != 'IDS_SCN_EQUIPMENT' and screenname != 'IDS_SCN_EQUIPMENTMASTER' and modulename = 'IDS_MDL_INVENTORY';

update lsusergrouprightsmaster set screenname = 'IDS_SCN_MATERIAL' where displaytopic = 'IDS_SCN_MATERIAL';
update lsusergrouprightsmaster set screenname = 'IDS_SCN_MATERIALCATEGORY' where displaytopic = 'IDS_SCN_MATERIALCATEGORY';
update lsusergrouprightsmaster set screenname = 'IDS_SCN_STORAGELOCATION' where displaytopic = 'IDS_SCN_STORAGELOCATION';
update lsusergrouprightsmaster set screenname = 'IDS_SCN_SECTIONMASTER' where displaytopic = 'IDS_SCN_SECTIONMASTER';
update lsusergrouprightsmaster set screenname = 'IDS_SCN_UNITMASTER' where displaytopic = 'IDS_SCN_UNITMASTER';
update lsusergrouprightsmaster set screenname = 'IDS_SCN_MATERIALTYPE' where displaytopic = 'IDS_SCN_MATERIALTYPE';
update lsusergrouprightsmaster set screenname = 'IDS_SCN_MATERIALINVENTORY' where displaytopic = 'IDS_SCN_MATERIALINVENTORY';
update lsusergrouprightsmaster set screenname = 'IDS_SCN_GRADEMASTER' where displaytopic = 'IDS_SCN_GRADEMASTER';
update lsusergrouprightsmaster set screenname = 'IDS_SCN_SUPPLIER' where displaytopic = 'IDS_SCN_SUPPLIER';
update lsusergrouprightsmaster set screenname = 'IDS_SCN_MANUFACTURER' where displaytopic = 'IDS_SCN_MANUFACTURER';

update lsusergrouprights set screenname = 'IDS_SCN_MATERIAL' where displaytopic = 'IDS_SCN_MATERIAL';
update lsusergrouprights set screenname = 'IDS_SCN_MATERIALCATEGORY' where displaytopic = 'IDS_SCN_MATERIALCATEGORY';
update lsusergrouprights set screenname = 'IDS_SCN_STORAGELOCATION' where displaytopic = 'IDS_SCN_STORAGELOCATION';
update lsusergrouprights set screenname = 'IDS_SCN_SECTIONMASTER' where displaytopic = 'IDS_SCN_SECTIONMASTER';
update lsusergrouprights set screenname = 'IDS_SCN_UNITMASTER' where displaytopic = 'IDS_SCN_UNITMASTER';
update lsusergrouprights set screenname = 'IDS_SCN_MATERIALTYPE' where displaytopic = 'IDS_SCN_MATERIALTYPE';
update lsusergrouprights set screenname = 'IDS_SCN_MATERIALINVENTORY' where displaytopic = 'IDS_SCN_MATERIALINVENTORY';
update lsusergrouprights set screenname = 'IDS_SCN_GRADEMASTER' where displaytopic = 'IDS_SCN_GRADEMASTER';
update lsusergrouprights set screenname = 'IDS_SCN_SUPPLIER' where displaytopic = 'IDS_SCN_SUPPLIER';
update lsusergrouprights set screenname = 'IDS_SCN_MANUFACTURER' where displaytopic = 'IDS_SCN_MANUFACTURER';

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (133, 'IDS_TSK_ACTIVIWIDGET', 'IDS_MDL_DASHBOARD', 'IDS_SCN_DASHBOARD', '0', 'NA', 'NA', 'NA', '0,0,0', 4) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_ACTIVIWIDGET', 'IDS_MDL_DASHBOARD', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_DASHBOARD'  WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_ACTIVIWIDGET' and screenname='IDS_SCN_DASHBOARD' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (134, 'IDS_TSK_CANCELORDER', 'IDS_MDL_ORDERS', 'IDS_SCN_SHEETORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 6) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_CANCELORDER', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SHEETORDERS' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_CANCELORDER' and screenname='IDS_SCN_SHEETORDERS' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (135, 'IDS_TSK_AUTOREGORDER', 'IDS_MDL_ORDERS', 'IDS_SCN_SHEETORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 7) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_AUTOREGORDER', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SHEETORDERS' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_AUTOREGORDER' and screenname='IDS_SCN_SHEETORDERS' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (136, 'IDS_TSK_DUEDATE', 'IDS_MDL_ORDERS', 'IDS_SCN_SHEETORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 8) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_DUEDATE', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SHEETORDERS' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_DUEDATE' and screenname='IDS_SCN_SHEETORDERS' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (137, 'IDS_TSK_NEWFOLDER', 'IDS_MDL_ORDERS', 'IDS_SCN_SHEETORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 9) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_NEWFOLDER', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SHEETORDERS' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_NEWFOLDER' and screenname='IDS_SCN_SHEETORDERS' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (138, 'IDS_TSK_CANCELORDER', 'IDS_MDL_ORDERS', 'IDS_SCN_PROTOCOLORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 23) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_CANCELORDER', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLORDERS' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_CANCELORDER' and screenname='IDS_SCN_PROTOCOLORDERS' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (139, 'IDS_TSK_AUTOREGORDER', 'IDS_MDL_ORDERS', 'IDS_SCN_PROTOCOLORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 24) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_AUTOREGORDER', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLORDERS' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_AUTOREGORDER' and screenname='IDS_SCN_PROTOCOLORDERS' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (140, 'IDS_TSK_DUEDATE', 'IDS_MDL_ORDERS', 'IDS_SCN_PROTOCOLORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 25) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_DUEDATE', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLORDERS' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_DUEDATE' and screenname='IDS_SCN_PROTOCOLORDERS' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (141, 'IDS_TSK_SHARE', 'IDS_MDL_ORDERS', 'IDS_SCN_PROTOCOLORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 26) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_SHARE', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLORDERS' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SHARE' and screenname='IDS_SCN_PROTOCOLORDERS' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (142, 'IDS_TSK_COPYLINK', 'IDS_MDL_ORDERS', 'IDS_SCN_PROTOCOLORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 27) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_COPYLINK', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLORDERS' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_COPYLINK' and screenname='IDS_SCN_PROTOCOLORDERS' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (143, 'IDS_TSK_SHARE', 'IDS_MDL_ORDERS', 'IDS_SCN_SHEETORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 10) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_SHARE', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SHEETORDERS' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SHARE' and screenname='IDS_SCN_SHEETORDERS' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (144, 'IDS_TSK_COPYLINK', 'IDS_MDL_ORDERS', 'IDS_SCN_SHEETORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 11) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_COPYLINK', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SHEETORDERS' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_COPYLINK' and screenname='IDS_SCN_SHEETORDERS' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (145, 'IDS_TSK_FOLDERDEL', 'IDS_MDL_ORDERS', 'IDS_SCN_PROTOCOLORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 28) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_FOLDERDEL', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLORDERS' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_FOLDERDEL' and screenname='IDS_SCN_PROTOCOLORDERS' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (146, 'IDS_TSK_FOLDERDEL', 'IDS_MDL_ORDERS', 'IDS_SCN_SHEETORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 12) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_FOLDERDEL', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SHEETORDERS' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_FOLDERDEL' and screenname='IDS_SCN_SHEETORDERS' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (147, 'IDS_TSK_ADDSEC', 'IDS_MDL_TEMPLATES', 'IDS_SCN_PROTOCOLTEMPLATE', '0', 'NA', 'NA', 'NA', '0,0,0', 48) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_ADDSEC', 'IDS_MDL_TEMPLATES', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLTEMPLATE' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_ADDSEC' and screenname='IDS_SCN_PROTOCOLTEMPLATE' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (148, 'IDS_TSK_ADDRESULT', 'IDS_MDL_TEMPLATES', 'IDS_SCN_PROTOCOLTEMPLATE', '0', 'NA', 'NA', 'NA', '0,0,0', 49) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_ADDRESULT', 'IDS_MDL_TEMPLATES', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLTEMPLATE' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_ADDRESULT' and screenname='IDS_SCN_PROTOCOLTEMPLATE' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (149, 'IDS_TSK_ADDREFF', 'IDS_MDL_TEMPLATES', 'IDS_SCN_PROTOCOLTEMPLATE', '0', 'NA', 'NA', 'NA', '0,0,0', 50) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_ADDREFF', 'IDS_MDL_TEMPLATES', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLTEMPLATE' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_ADDREFF' and screenname='IDS_SCN_PROTOCOLTEMPLATE' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (150, 'IDS_TSK_ADDRESMATERIAL', 'IDS_MDL_TEMPLATES', 'IDS_SCN_PROTOCOLTEMPLATE', '0', 'NA', 'NA', 'NA', '0,0,0', 51) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_ADDRESMATERIAL', 'IDS_MDL_TEMPLATES', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLTEMPLATE' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_ADDRESMATERIAL' and screenname='IDS_SCN_PROTOCOLTEMPLATE' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (151, 'IDS_TSK_ADDRESEQUIPMENT', 'IDS_MDL_TEMPLATES', 'IDS_SCN_PROTOCOLTEMPLATE', '0', 'NA', 'NA', 'NA', '0,0,0', 52) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_ADDRESEQUIPMENT', 'IDS_MDL_TEMPLATES', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLTEMPLATE' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_ADDRESEQUIPMENT' and screenname='IDS_SCN_PROTOCOLTEMPLATE' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (152, 'IDS_TSK_ADDSTEP', 'IDS_MDL_TEMPLATES', 'IDS_SCN_PROTOCOLTEMPLATE', '0', 'NA', 'NA', 'NA', '0,0,0', 53) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_ADDSTEP', 'IDS_MDL_TEMPLATES', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLTEMPLATE' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_ADDSTEP' and screenname='IDS_SCN_PROTOCOLTEMPLATE' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (153, 'IDS_TSK_ADDEDITOR', 'IDS_MDL_TEMPLATES', 'IDS_SCN_PROTOCOLTEMPLATE', '0', 'NA', 'NA', 'NA', '0,0,0', 54) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_ADDEDITOR', 'IDS_MDL_TEMPLATES', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLTEMPLATE' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_ADDEDITOR' and screenname='IDS_SCN_PROTOCOLTEMPLATE' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (154, 'IDS_TSK_IMPORTJSON', 'IDS_MDL_TEMPLATES', 'IDS_SCN_PROTOCOLTEMPLATE', '0', 'NA', 'NA', 'NA', '0,0,0', 55) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_IMPORTJSON', 'IDS_MDL_TEMPLATES', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLTEMPLATE' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_IMPORTJSON' and screenname='IDS_SCN_PROTOCOLTEMPLATE' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (155, 'IDS_TSK_EXPORTJSON', 'IDS_MDL_TEMPLATES', 'IDS_SCN_PROTOCOLTEMPLATE', '0', 'NA', 'NA', 'NA', '0,0,0', 56) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_EXPORTJSON', 'IDS_MDL_TEMPLATES', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLTEMPLATE' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_EXPORTJSON' and screenname='IDS_SCN_PROTOCOLTEMPLATE' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (156, 'IDS_TSK_ADDRESULT', 'IDS_MDL_ORDERS', 'IDS_SCN_PROTOCOLORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 29) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_ADDRESULT', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLORDERS' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_ADDRESULT' and screenname='IDS_SCN_PROTOCOLORDERS' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (157, 'IDS_TSK_ADDREFF', 'IDS_MDL_ORDERS', 'IDS_SCN_PROTOCOLORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 30) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_ADDREFF', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLORDERS' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_ADDREFF' and screenname='IDS_SCN_PROTOCOLORDERS' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (158, 'IDS_TSK_ADDRESMATERIAL', 'IDS_MDL_ORDERS', 'IDS_SCN_PROTOCOLORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 31) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_ADDRESMATERIAL', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLORDERS' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_ADDRESMATERIAL' and screenname='IDS_SCN_PROTOCOLORDERS' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (159, 'IDS_TSK_ADDRESEQUIPMENT', 'IDS_MDL_ORDERS', 'IDS_SCN_PROTOCOLORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 32) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_ADDRESEQUIPMENT', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLORDERS' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_ADDRESEQUIPMENT' and screenname='IDS_SCN_PROTOCOLORDERS' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (160, 'IDS_TSK_ADDSTEP', 'IDS_MDL_ORDERS', 'IDS_SCN_PROTOCOLORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 33) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_ADDSTEP', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLORDERS' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_ADDSTEP' and screenname='IDS_SCN_PROTOCOLORDERS' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (161, 'IDS_TSK_ADDEDITOR', 'IDS_MDL_ORDERS', 'IDS_SCN_PROTOCOLORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 34) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_ADDEDITOR', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLORDERS' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_ADDEDITOR' and screenname='IDS_SCN_PROTOCOLORDERS' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (162, 'IDS_TSK_EXPORTJSON', 'IDS_MDL_ORDERS', 'IDS_SCN_PROTOCOLORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 35) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_EXPORTJSON', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLORDERS' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_EXPORTJSON' and screenname='IDS_SCN_PROTOCOLORDERS' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (163, 'IDS_TSK_ADDSEC', 'IDS_MDL_ORDERS', 'IDS_SCN_PROTOCOLORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 36) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_ADDSEC', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLORDERS' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_ADDSEC' and screenname='IDS_SCN_PROTOCOLORDERS' and usergroupid_usergroupcode = 1); 

ALTER TABLE IF Exists lsautoregister ADD Column IF NOT EXISTS stoptime timestamp without time zone;

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (164, 'IDS_TSK_ACTDEACT', 'IDS_MDL_INVENTORY', 'IDS_TSK_EQUIPMENTMASTER', '0', 'NA', 'NA', 'NA', '0,0,0', 42) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_ACTDEACT', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_TSK_EQUIPMENTMASTER' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_ACTDEACT' and screenname='IDS_TSK_EQUIPMENTMASTER' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (165, 'IDS_TSK_CALIBRATE', 'IDS_MDL_INVENTORY', 'IDS_TSK_EQUIPMENTMASTER', '0', 'NA', 'NA', 'NA', '0,0,0', 116) ON CONFLICT (orderno) DO NOTHING;   

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_CALIBRATE', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_EQUIPMENTMASTER' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_CALIBRATE' and screenname='IDS_SCN_EQUIPMENTMASTER' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (166, 'IDS_TSK_MAINTANN', 'IDS_MDL_INVENTORY', 'IDS_TSK_EQUIPMENTMASTER', '0', 'NA', 'NA', 'NA', '0,0,0', 117) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_MAINTANN', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_EQUIPMENTMASTER' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_MAINTANN' and screenname='IDS_SCN_EQUIPMENTMASTER' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (167, 'IDS_SCN_MATERIALTYPEPARAMS', 'IDS_MDL_INVENTORY', 'IDS_SCN_MATERIALTYPEPARAMS', '0', '0', 'NA', 'NA', '0,0,0', 112) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_SCN_MATERIALTYPEPARAMS', 'IDS_MDL_INVENTORY', 'administrator', '1', '1', 'NA', 'NA', 1,1,'IDS_SCN_MATERIALTYPEPARAMS' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_SCN_MATERIALTYPEPARAMS' and screenname='IDS_SCN_MATERIALTYPEPARAMS' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (168, 'IDS_SCN_SUPPLIER', 'IDS_MDL_INVENTORY', 'IDS_SCN_SUPPLIER', '0', '0', '0', '0', '0,0,0', 113) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_SCN_SUPPLIER', 'IDS_MDL_INVENTORY', 'administrator', '1', '1', '1', '1', 1,1,'IDS_SCN_SUPPLIER' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_SCN_SUPPLIER' and screenname='IDS_SCN_SUPPLIER' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (169, 'IDS_SCN_MANUFACTURER', 'IDS_MDL_INVENTORY', 'IDS_SCN_MANUFACTURER', '0', '0', '0', '0', '0,0,0', 114) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_SCN_MANUFACTURER', 'IDS_MDL_INVENTORY', 'administrator', '1', '1', '1', '1', 1,1,'IDS_SCN_MANUFACTURER' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_SCN_MANUFACTURER' and screenname='IDS_SCN_MANUFACTURER' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) VALUES (170, 'IDS_SCN_GRADEMASTER', 'IDS_MDL_INVENTORY', 'IDS_SCN_GRADEMASTER', '0', '0', '0', '0', '0,0,0', 115) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_SCN_GRADEMASTER', 'IDS_MDL_INVENTORY', 'administrator', '1', '1', '1', '1', 1,1,'IDS_SCN_GRADEMASTER' WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_SCN_GRADEMASTER' and screenname='IDS_SCN_GRADEMASTER' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder)  VALUES (171, 'IDS_TSK_SHARE', 'IDS_MDL_TEMPLATES', 'IDS_SCN_SHEETTEMPLATE', '0', 'NA', 'NA', 'NA', '0,0,0', 115) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_SHARE', 'IDS_MDL_TEMPLATES', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SHEETTEMPLATE' 
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SHARE' and screenname='IDS_SCN_SHEETTEMPLATE' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (172, 'IDS_TSK_SHOWVERSION', 'IDS_MDL_TEMPLATES', 'IDS_SCN_SHEETTEMPLATE', '0', 'NA', 'NA', 'NA', '0,0,0', 115) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_SHOWVERSION', 'IDS_MDL_TEMPLATES', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SHEETTEMPLATE' 
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SHOWVERSION' and screenname='IDS_SCN_SHEETTEMPLATE' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (173, 'IDS_TSK_TRANHISTORY', 'IDS_MDL_TEMPLATES', 'IDS_SCN_SHEETTEMPLATE', '0', 'NA', 'NA', 'NA', '0,0,0', 115) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_TRANHISTORY', 'IDS_MDL_TEMPLATES', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SHEETTEMPLATE' 
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_TRANHISTORY' and screenname='IDS_SCN_SHEETTEMPLATE' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (174, 'IDS_TSK_SHARE', 'IDS_MDL_TEMPLATES', 'IDS_SCN_PROTOCOLTEMPLATE', '0', 'NA', 'NA', 'NA', '0,0,0', 115) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_SHARE', 'IDS_MDL_TEMPLATES', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLTEMPLATE' 
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SHARE' and screenname='IDS_SCN_PROTOCOLTEMPLATE' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (175, 'IDS_TSK_SHOWVERSION', 'IDS_MDL_TEMPLATES', 'IDS_SCN_PROTOCOLTEMPLATE', '0', 'NA', 'NA', 'NA', '0,0,0', 115) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_SHOWVERSION', 'IDS_MDL_TEMPLATES', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLTEMPLATE' 
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SHOWVERSION' and screenname='IDS_SCN_PROTOCOLTEMPLATE' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (176, 'IDS_TSK_TRANHISTORY', 'IDS_MDL_TEMPLATES', 'IDS_SCN_PROTOCOLTEMPLATE', '0', 'NA', 'NA', 'NA', '0,0,0', 115) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_TRANHISTORY', 'IDS_MDL_TEMPLATES', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLTEMPLATE' 
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_TRANHISTORY' and screenname='IDS_SCN_PROTOCOLTEMPLATE' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (177, 'IDS_TSK_SHARE', 'IDS_MDL_ORDERS', 'IDS_SCN_SHEETORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 115) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_SHARE', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SHEETORDERS' 
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SHARE' and screenname='IDS_SCN_SHEETORDERS' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (178, 'IDS_TSK_SHOWVERSION', 'IDS_MDL_ORDERS', 'IDS_SCN_SHEETORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 115) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_SHOWVERSION', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SHEETORDERS' 
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SHOWVERSION' and screenname='IDS_SCN_SHEETORDERS' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (179, 'IDS_TSK_TRANHISTORY', 'IDS_MDL_ORDERS', 'IDS_SCN_SHEETORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 115) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_TRANHISTORY', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SHEETORDERS' 
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_TRANHISTORY' and screenname='IDS_SCN_SHEETORDERS' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (180, 'IDS_TSK_SHARE', 'IDS_MDL_ORDERS', 'IDS_SCN_PROTOCOLORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 115) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_SHARE', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLORDERS' 
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SHARE' and screenname='IDS_SCN_PROTOCOLORDERS' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (181, 'IDS_TSK_SHOWVERSION', 'IDS_MDL_ORDERS', 'IDS_SCN_PROTOCOLORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 115) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_SHOWVERSION', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLORDERS' 
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SHOWVERSION' and screenname='IDS_SCN_PROTOCOLORDERS' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (182, 'IDS_TSK_TRANHISTORY', 'IDS_MDL_ORDERS', 'IDS_SCN_PROTOCOLORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 115) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_TRANHISTORY', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLORDERS' 
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_TRANHISTORY' and screenname='IDS_SCN_PROTOCOLORDERS' and usergroupid_usergroupcode = 1); 

ALTER TABLE IF Exists elnresultusedmaterial ADD Column IF NOT EXISTS isreturn Integer;

update elnresultusedmaterial set isreturn = 0 where isreturn is null;

Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(185,0,'IDS_MDL_INVENTORY',131,'IDS_SCN_EQUIPMENTTYPE','IDS_TSK_SAVE') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(186,0,'IDS_MDL_INVENTORY',132,'IDS_SCN_EQUIPMENTTYPE','IDS_TSK_EDIT') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(187,0,'IDS_MDL_INVENTORY',133,'IDS_SCN_EQUIPMENTCATEGORY','IDS_TSK_SAVE') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(188,0,'IDS_MDL_INVENTORY',134,'IDS_SCN_EQUIPMENTCATEGORY','IDS_TSK_EDIT') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(189,0,'IDS_MDL_INVENTORY',135,'IDS_SCN_EQUIPMENTCATEGORY','IDS_TSK_RETIRE') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(190,0,'IDS_MDL_INVENTORY',136,'IDS_SCN_EQUIPMENT','IDS_TSK_SAVE') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(191,0,'IDS_MDL_INVENTORY',137,'IDS_SCN_EQUIPMENT','IDS_TSK_EDIT') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(192,0,'IDS_MDL_INVENTORY',138,'IDS_SCN_EQUIPMENT','IDS_TSK_RETIRE') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(193,0,'IDS_MDL_INVENTORY',139,'IDS_SCN_EQUIPMENT','IDS_TSK_ACT/DCT') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(194,0,'IDS_MDL_INVENTORY',140,'IDS_SCN_EQUIPMENT','IDS_TSK_CALIBRATE') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(195,0,'IDS_MDL_INVENTORY',141,'IDS_SCN_EQUIPMENT','IDS_TSK_MAINTAINE') ON CONFLICT(serialno)DO NOTHING;

UPDATE lsaudittrailconfigmaster
SET ordersequnce = CASE
    WHEN screenname = 'IDS_SCN_SHEETORDERS' THEN 1
    WHEN screenname = 'IDS_SCN_PROTOCOLORDERS' THEN 2
    WHEN screenname = 'IDS_SCN_UNLOCKORDERS' THEN 3

    WHEN screenname = 'IDS_SCN_SHEETTEMPLATES' THEN 4
    WHEN screenname = 'IDS_SCN_PROTOCOLTEMP' THEN 5
    WHEN screenname = 'IDS_SCN_TEMPLATEMAPPING' THEN 6

    WHEN screenname = 'IDS_SCN_SAMPLEMASTER' THEN 7

    WHEN screenname = 'IDS_SCN_USERGROUP' THEN 8
    WHEN screenname = 'IDS_SCN_SITEMASTER' THEN 9
    WHEN screenname = 'IDS_SCN_USERMASTER' THEN 10
    WHEN screenname = 'IDS_SCN_USERRIGHTS' THEN 11
    WHEN screenname = 'IDS_SCN_PROJECTMASTER' THEN 12
    WHEN screenname = 'IDS_SCN_PROJECTTEAM' THEN 13
    WHEN screenname = 'IDS_SCN_TASKMASTER' THEN 14
    WHEN screenname = 'IDS_SCN_ORDERWORKFLOW' THEN 15
    WHEN screenname = 'IDS_SCN_TEMPLATEWORKFLOW' THEN 16
    WHEN screenname = 'IDS_SCN_DOMAIN' THEN 17
    WHEN screenname = 'IDS_SCN_PASSWORDPOLICY' THEN 18

    WHEN screenname = 'IDS_SCN_INSTRUMENTCATEGORY' THEN 19
    WHEN screenname = 'IDS_SCN_INSTRUMENTMASTER' THEN 20
    WHEN screenname = 'IDS_SCN_DELIMITER' THEN 21
    WHEN screenname = 'IDS_SCN_METHODDELIMITER' THEN 22
    WHEN screenname = 'IDS_SCN_METHODMASTER' THEN 23

    WHEN screenname = 'IDS_SCN_AUDITTRAILHIS' THEN 24
    WHEN screenname = 'IDS_SCN_AUDITTRAILCONFIG' THEN 25
    WHEN screenname = 'IDS_SCN_CFRSETTINGS' THEN 26

    WHEN screenname = 'IDS_SCN_REPORTDESIGNER' THEN 27
    WHEN screenname = 'IDS_SCN_REPORTVIEWER' THEN 28

    WHEN screenname = 'IDS_SCN_MATERIALCATEGORY' THEN 29
    WHEN screenname = 'IDS_SCN_STORAGELOCATION' THEN 30
    WHEN screenname = 'IDS_SCN_MATERIALTYPE' THEN 31
    WHEN screenname = 'IDS_SCN_UNITMASTER' THEN 32
    WHEN screenname = 'IDS_SCN_GRADEMASTER' THEN 33
    WHEN screenname = 'IDS_SCN_SUPPLIER' THEN 34
    WHEN screenname = 'IDS_SCN_MANUFACTURER' THEN 35
    WHEN screenname = 'IDS_SCN_SECTIONMASTER' THEN 36
    WHEN screenname = 'IDS_SCN_INVENTORY' THEN 37
    WHEN screenname = 'IDS_SCN_MATERIAL' THEN 38
    WHEN screenname = 'IDS_SCN_MATERIALINVENTORY' THEN 39

    WHEN screenname = 'IDS_SCN_EQUIPMENTTYPE' THEN 40
    WHEN screenname = 'IDS_SCN_EQUIPMENTCATEGORY' THEN 41
    WHEN screenname = 'IDS_SCN_EQUIPMENT' THEN 42

    WHEN screenname = 'IDS_MDL_LOGBOOK' THEN 43
    ELSE ordersequnce -- Retain the current value if no match
END;

UPDATE lsaudittrailconfiguration
SET ordersequnce = CASE
    WHEN screenname = 'IDS_SCN_SHEETORDERS' THEN 1
    WHEN screenname = 'IDS_SCN_PROTOCOLORDERS' THEN 2
    WHEN screenname = 'IDS_SCN_UNLOCKORDERS' THEN 3

    WHEN screenname = 'IDS_SCN_SHEETTEMPLATES' THEN 4
    WHEN screenname = 'IDS_SCN_PROTOCOLTEMP' THEN 5
    WHEN screenname = 'IDS_SCN_TEMPLATEMAPPING' THEN 6

    WHEN screenname = 'IDS_SCN_SAMPLEMASTER' THEN 7

    WHEN screenname = 'IDS_SCN_USERGROUP' THEN 8
    WHEN screenname = 'IDS_SCN_SITEMASTER' THEN 9
    WHEN screenname = 'IDS_SCN_USERMASTER' THEN 10
    WHEN screenname = 'IDS_SCN_USERRIGHTS' THEN 11
    WHEN screenname = 'IDS_SCN_PROJECTMASTER' THEN 12
    WHEN screenname = 'IDS_SCN_PROJECTTEAM' THEN 13
    WHEN screenname = 'IDS_SCN_TASKMASTER' THEN 14
    WHEN screenname = 'IDS_SCN_ORDERWORKFLOW' THEN 15
    WHEN screenname = 'IDS_SCN_TEMPLATEWORKFLOW' THEN 16
    WHEN screenname = 'IDS_SCN_DOMAIN' THEN 17
    WHEN screenname = 'IDS_SCN_PASSWORDPOLICY' THEN 18

    WHEN screenname = 'IDS_SCN_INSTRUMENTCATEGORY' THEN 19
    WHEN screenname = 'IDS_SCN_INSTRUMENTMASTER' THEN 20
    WHEN screenname = 'IDS_SCN_DELIMITER' THEN 21
    WHEN screenname = 'IDS_SCN_METHODDELIMITER' THEN 22
    WHEN screenname = 'IDS_SCN_METHODMASTER' THEN 23

    WHEN screenname = 'IDS_SCN_AUDITTRAILHIS' THEN 24
    WHEN screenname = 'IDS_SCN_AUDITTRAILCONFIG' THEN 25
    WHEN screenname = 'IDS_SCN_CFRSETTINGS' THEN 26

    WHEN screenname = 'IDS_SCN_REPORTDESIGNER' THEN 27
    WHEN screenname = 'IDS_SCN_REPORTVIEWER' THEN 28

    WHEN screenname = 'IDS_SCN_MATERIALCATEGORY' THEN 29
    WHEN screenname = 'IDS_SCN_STORAGELOCATION' THEN 30
    WHEN screenname = 'IDS_SCN_MATERIALTYPE' THEN 31
    WHEN screenname = 'IDS_SCN_UNITMASTER' THEN 32
    WHEN screenname = 'IDS_SCN_GRADEMASTER' THEN 33
    WHEN screenname = 'IDS_SCN_SUPPLIER' THEN 34
    WHEN screenname = 'IDS_SCN_MANUFACTURER' THEN 35
    WHEN screenname = 'IDS_SCN_SECTIONMASTER' THEN 36
    WHEN screenname = 'IDS_SCN_INVENTORY' THEN 37
    WHEN screenname = 'IDS_SCN_MATERIAL' THEN 38
    WHEN screenname = 'IDS_SCN_MATERIALINVENTORY' THEN 39

    WHEN screenname = 'IDS_SCN_EQUIPMENTTYPE' THEN 40
    WHEN screenname = 'IDS_SCN_EQUIPMENTCATEGORY' THEN 41
    WHEN screenname = 'IDS_SCN_EQUIPMENT' THEN 42

    WHEN screenname = 'IDS_MDL_LOGBOOK' THEN 43
    ELSE ordersequnce -- Retain the current value if no match
END;

--update lsusergrouprightsmaster set screate = '0',sedit= '0' where  screenname = 'IDS_SCN_MATERIALTYPEPARAMS' and displaytopic = 'IDS_SCN_MATERIALTYPEPARAMS';
--update lsusergrouprights set screate = '0',sedit= '0' where  screenname = 'IDS_SCN_MATERIALTYPEPARAMS' and displaytopic = 'IDS_SCN_MATERIALTYPEPARAMS';

INSERT into LSfields (fieldcode, createby, createdate, fieldorderno, fieldtypecode, isactive, level01code, level01name, level02code, level02name, level03code, level03name, level04code, level04name, siteID) VALUES (61, NULL, NULL, 23, 3, 1, 'G1', 'ID_GENERAL', '23', 'ID_GENERAL', 23, 'ID_GENERAL', 'G23', 'Add Equipment', 1) on conflict (fieldcode) do nothing;

delete from LSusergrouprightsmaster where displaytopic = 'IDS_TSK_EQUIPMENTACT' and modulename = 'IDS_MDL_INVENTORY' and screenname = 'IDS_SCN_EQUIPMENTMASTER';
delete from LSusergrouprightsmaster where displaytopic = 'IDS_TSK_EQUIPMENTCAL' and modulename = 'IDS_MDL_INVENTORY' and screenname = 'IDS_SCN_EQUIPMENTMASTER';
delete from LSusergrouprightsmaster where displaytopic = 'IDS_TSK_EQUIPMENTMAINT' and modulename = 'IDS_MDL_INVENTORY' and screenname = 'IDS_SCN_EQUIPMENTMASTER';

delete from LSusergrouprights where displaytopic = 'IDS_TSK_EQUIPMENTACT' and modulename = 'IDS_MDL_INVENTORY' and screenname = 'IDS_SCN_EQUIPMENTMASTER';
delete from LSusergrouprights where displaytopic = 'IDS_TSK_EQUIPMENTCAL' and modulename = 'IDS_MDL_INVENTORY' and screenname = 'IDS_SCN_EQUIPMENTMASTER';
delete from LSusergrouprights where displaytopic = 'IDS_TSK_EQUIPMENTMAINT' and modulename = 'IDS_MDL_INVENTORY' and screenname = 'IDS_SCN_EQUIPMENTMASTER';

update LSusergrouprightsmaster set screenname = 'IDS_SCN_EQUIPMENTMASTER' where screenname = 'IDS_TSK_EQUIPMENTMASTER' and displaytopic ='IDS_TSK_ACTDEACT';
update LSusergrouprightsmaster set screenname = 'IDS_SCN_EQUIPMENTMASTER' where screenname = 'IDS_TSK_EQUIPMENTMASTER' and displaytopic ='IDS_TSK_CALIBRATE';
update LSusergrouprightsmaster set screenname = 'IDS_SCN_EQUIPMENTMASTER' where screenname = 'IDS_TSK_EQUIPMENTMASTER' and displaytopic ='IDS_TSK_MAINTANN';

update LSusergrouprights set screenname = 'IDS_SCN_EQUIPMENTMASTER' where screenname = 'IDS_TSK_EQUIPMENTMASTER' and displaytopic ='IDS_TSK_ACTDEACT';
update LSusergrouprights set screenname = 'IDS_SCN_EQUIPMENTMASTER' where screenname = 'IDS_TSK_EQUIPMENTMASTER' and displaytopic ='IDS_TSK_CALIBRATE';
update LSusergrouprights set screenname = 'IDS_SCN_EQUIPMENTMASTER' where screenname = 'IDS_TSK_EQUIPMENTMASTER' and displaytopic ='IDS_TSK_MAINTANN';

update lsusergrouprightsmaster set screenname = 'IDS_SCN_PARSER' where modulename = 'IDS_MDL_SETUP' and displaytopic = 'IDS_SCN_DELIMITER';
update lsusergrouprightsmaster set screenname = 'IDS_SCN_PARSER' where modulename = 'IDS_MDL_SETUP' and displaytopic = 'IDS_SCN_METHODDELIMITER';
update lsusergrouprightsmaster set screenname = 'IDS_SCN_PARSER' where modulename = 'IDS_MDL_SETUP' and displaytopic = 'IDS_SCN_METHODMASTER';
update lsusergrouprightsmaster set screenname = 'IDS_SCN_PARSER' where modulename = 'IDS_MDL_SETUP' and displaytopic = 'IDS_SCN_INSTRUMENTCATEGORY';
update lsusergrouprightsmaster set screenname = 'IDS_SCN_PARSER' where modulename = 'IDS_MDL_SETUP' and displaytopic = 'IDS_SCN_INSTRUMENTMASTER';

update lsusergrouprights set screenname = 'IDS_SCN_PARSER' where modulename = 'IDS_MDL_SETUP' and displaytopic = 'IDS_SCN_DELIMITER';
update lsusergrouprights set screenname = 'IDS_SCN_PARSER' where modulename = 'IDS_MDL_SETUP' and displaytopic = 'IDS_SCN_METHODDELIMITER';
update lsusergrouprights set screenname = 'IDS_SCN_PARSER' where modulename = 'IDS_MDL_SETUP' and displaytopic = 'IDS_SCN_METHODMASTER';
update lsusergrouprights set screenname = 'IDS_SCN_PARSER' where modulename = 'IDS_MDL_SETUP' and displaytopic = 'IDS_SCN_INSTRUMENTCATEGORY';
update lsusergrouprights set screenname = 'IDS_SCN_PARSER' where modulename = 'IDS_MDL_SETUP' and displaytopic = 'IDS_SCN_INSTRUMENTMASTER';


ALTER TABLE IF Exists lslogilabprotocoldetail ALTER COLUMN approvelaccept TYPE character varying;

ALTER TABLE lsordernotification ALTER COLUMN notificationcode TYPE BIGINT;

ALTER TABLE IF Exists lslogilablimsorderdetail ALTER COLUMN approvelaccept TYPE character varying;
ALTER TABLE IF Exists lslogilabprotocoldetail ALTER COLUMN approvelaccept TYPE character varying;

delete from period where speriodname in ('Never','NA','Minutes','Day Without Hours');
update lsusergrouprights set  screate='1',sedit = '1', sdelete = 'NA' where displaytopic = 'IDS_TSK_FOLDERCREATIONPROTOCOL' and usergroupid_usergroupcode = 1;
update lsusergrouprights set  screate='1',sedit = '1', sdelete = 'NA' where displaytopic = 'IDS_TSK_FOLDERCREATION' and usergroupid_usergroupcode = 1;

update lsusergrouprightsmaster set screenname = 'IDS_SCN_MATERIALTYPEPARAMS' where displaytopic = 'IDS_SCN_MATERIALTYPEPARAMS';
update lsusergrouprights set screenname = 'IDS_SCN_MATERIALTYPEPARAMS' where displaytopic = 'IDS_SCN_MATERIALTYPEPARAMS';

INSERT into LSfields (fieldcode, createby, createdate, fieldorderno, fieldtypecode, isactive, level01code, level01name, level02code, level02name, level03code, level03name, level04code, level04name, siteID) VALUES (60, NULL, NULL, 20, 3, 1, 'G1', 'ID_GENERAL', '21', 'ID_GENERAL', 21, 'ID_GENERAL', 'G21', 'Barcode', 1) on conflict (fieldcode) do nothing;
--INSERT into LSfields (fieldcode, createby, createdate, fieldorderno, fieldtypecode, isactive, level01code, level01name, level02code, level02name, level03code, level03name, level04code, level04name, siteID) VALUES (61, NULL, NULL, 21, 3, 1, 'G1', 'ID_GENERAL', '22', 'ID_GENERAL', 22, 'ID_GENERAL', 'G22', 'Formula Field', 1) on conflict (fieldcode) do nothing;


update lsusergrouprightsmaster set screenname = 'IDS_SCN_MATERIALTYPEPARAMS', sequenceorder= 31 where screenname = 'IDS_SCN_MATERIALTYPE' and displaytopic = 'IDS_SCN_MATERIALTYPE';
update lsusergrouprightsmaster set sequenceorder= 42 where screenname = 'IDS_SCN_INVENTORY' and displaytopic = 'IDS_TSK_ACTDEACT';

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (183, 'IDS_TSK_REPORTMAPPING', 'IDS_MDL_REPORTS', 'IDS_SCN_REPORTVIEWER', '0', '0', 'NA', 'NA', '0,0,0', 27) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_REPORTMAPPING', 'IDS_MDL_REPORTS', 'administrator', '1', '1', 'NA', 'NA', 1,1,'IDS_SCN_REPORTVIEWER' 
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_REPORTMAPPING' and screenname='IDS_SCN_REPORTVIEWER' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (184, 'IDS_TSK_SAVE', 'IDS_MDL_REPORTS', 'IDS_SCN_REPORTS', '0', 'NA', 'NA', 'NA', '0,0,0', 27) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) 
SELECT 'IDS_TSK_SAVE', 'IDS_MDL_REPORTS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_REPORTS' 
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SAVE' and screenname='IDS_SCN_REPORTS' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (185, 'IDS_TSK_INSERT', 'IDS_MDL_REPORTS', 'IDS_SCN_REPORTS', '0', 'NA', 'NA', 'NA', '0,0,0', 27) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) 
SELECT 'IDS_TSK_INSERT', 'IDS_MDL_REPORTS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_REPORTS' 
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_INSERT' and screenname='IDS_SCN_REPORTS' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (186, 'IDS_TSK_SAVE', 'IDS_MDL_REPORTS', 'IDS_SCN_REPORTVIEVER', '0', 'NA', 'NA', 'NA', '0,0,0', 27) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) 
SELECT 'IDS_TSK_SAVE', 'IDS_MDL_REPORTS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_REPORTVIEVER' 
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SAVE' and screenname='IDS_SCN_REPORTVIEVER' and usergroupid_usergroupcode = 1); 

UPDATE lsusergrouprights SET screenname = 'IDS_SCN_REPORTVIEVER' WHERE screenname = 'IDS_SCN_REPORTS' AND (displaytopic = 'IDS_SCN_REPORTVIEWER' OR displaytopic = 'IDS_SCN_REPORTVIEWERNEWFOLDER');
update lsusergrouprights set screenname = 'IDS_SCN_REPORTMAPPER' where screenname = 'IDS_SCN_REPORTS' and displaytopic = 'IDS_TSK_REPORTMAPPING';
update lsusergrouprights set screenname = 'IDS_SCN_REPORTMAPPER' where screenname = 'IDS_SCN_REPORTVIEWER' and displaytopic = 'IDS_TSK_REPORTMAPPING';

UPDATE lsusergrouprightsmaster SET screenname = 'IDS_SCN_REPORTVIEVER' WHERE screenname = 'IDS_SCN_REPORTS' AND (displaytopic = 'IDS_SCN_REPORTVIEWER' OR displaytopic = 'IDS_SCN_REPORTVIEWERNEWFOLDER');
update lsusergrouprightsmaster set screenname = 'IDS_SCN_REPORTMAPPER' where screenname = 'IDS_SCN_REPORTS' and displaytopic = 'IDS_TSK_REPORTMAPPING';
update lsusergrouprightsmaster set screenname = 'IDS_SCN_REPORTMAPPER' where screenname = 'IDS_SCN_REPORTVIEWER' and displaytopic = 'IDS_TSK_REPORTMAPPING';

UPDATE lsusergrouprights SET screenname = 'IDS_SCN_REPORTVIEVER' WHERE screenname = 'IDS_SCN_REPORTS' AND (displaytopic = 'IDS_SCN_REPORTVIEWER' OR displaytopic = 'IDS_SCN_REPORTVIEWERNEWFOLDER');
update lsusergrouprights set screenname = 'IDS_SCN_REPORTMAPPER' where screenname = 'IDS_SCN_REPORTS' and displaytopic = 'IDS_TSK_REPORTMAPPING';

UPDATE lsusergrouprightsmaster SET screenname = 'IDS_SCN_REPORTVIEVER' WHERE screenname = 'IDS_SCN_REPORTS' AND (displaytopic = 'IDS_SCN_REPORTVIEWER' OR displaytopic = 'IDS_SCN_REPORTVIEWERNEWFOLDER');
update lsusergrouprightsmaster set screenname = 'IDS_SCN_REPORTMAPPER' where screenname = 'IDS_SCN_REPORTVIEWER' and displaytopic = 'IDS_TSK_REPORTMAPPING';

update LSusergrouprightsmaster set screenname = 'IDS_SCN_REPORTVIEVER' where modulename = 'IDS_MDL_REPORTS' and screenname = 'IDS_SCN_REPORTVIEWER';
update LSusergrouprights set screenname = 'IDS_SCN_REPORTVIEVER' where modulename = 'IDS_MDL_REPORTS' and screenname = 'IDS_SCN_REPORTVIEWER';

update LSusergrouprightsmaster set screenname = 'IDS_SCN_REPORTS' where modulename = 'IDS_MDL_REPORTS' and screenname = 'IDS_SCN_REPORTDESIGNER';
update LSusergrouprights set screenname = 'IDS_SCN_REPORTS' where modulename = 'IDS_MDL_REPORTS' and screenname = 'IDS_SCN_REPORTDESIGNER';


INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (188, 'IDS_TSK_BARCODEMASTER', 'IDS_MDL_SETUP', 'IDS_SCN_BARCODEMASTER', '0', '0', '0', '0', '0,0,0', 20) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_BARCODEMASTER', 'IDS_MDL_SETUP', 'administrator', '1', '1', '1', '1', 1,1,'IDS_SCN_BARCODEMASTER' ,20
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_BARCODEMASTER' and screenname='IDS_SCN_BARCODEMASTER' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (193, 'IDS_TSK_BARCODESCANNER', 'IDS_MDL_INVENTORY', 'IDS_SCN_MATERIALINVENTORY', '0', 'NA', 'NA', 'NA', '0,0,0', 31) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_BARCODESCANNER', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_MATERIALINVENTORY' ,31
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_BARCODESCANNER' and screenname='IDS_SCN_MATERIALINVENTORY' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (189, 'IDS_TSK_RESTOCK', 'IDS_MDL_INVENTORY', 'IDS_SCN_MATERIALINVENTORY', '0', 'NA', 'NA', 'NA', '0,0,0', 31) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_RESTOCK', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_MATERIALINVENTORY' ,31
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_RESTOCK' and screenname='IDS_SCN_MATERIALINVENTORY' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (190, 'IDS_TSK_OPEN', 'IDS_MDL_INVENTORY', 'IDS_SCN_MATERIALINVENTORY', '0', 'NA', 'NA', 'NA', '0,0,0', 31) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_OPEN', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_MATERIALINVENTORY' ,31
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_OPEN' and screenname='IDS_SCN_MATERIALINVENTORY' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (191, 'IDS_TSK_RELEASE', 'IDS_MDL_INVENTORY', 'IDS_SCN_MATERIALINVENTORY', '0', 'NA', 'NA', 'NA', '0,0,0', 31) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_RELEASE', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_MATERIALINVENTORY' ,31
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_RELEASE' and screenname='IDS_SCN_MATERIALINVENTORY' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (192, 'IDS_TSK_DISPOSE', 'IDS_MDL_INVENTORY', 'IDS_SCN_MATERIALINVENTORY', '0', 'NA', 'NA', 'NA', '0,0,0', 31) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_DISPOSE', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_MATERIALINVENTORY' ,31
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_DISPOSE' and screenname='IDS_SCN_MATERIALINVENTORY' and usergroupid_usergroupcode = 1); 


INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (268, 'IDS_TSK_PROJECTTEAM', 'IDS_MDL_ORDERS', 'IDS_SCN_SHEETORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 2) ON CONFLICT (orderno) DO NOTHING;

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (269, 'IDS_TSK_PROJECTTEAM', 'IDS_MDL_ORDERS', 'IDS_SCN_PROTOCOLORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 3) ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname , sequenceorder) 
SELECT 'IDS_TSK_PROJECTTEAM', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SHEETORDERS',2  
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_PROJECTTEAM' and screenname='IDS_SCN_SHEETORDERS' and usergroupid_usergroupcode = 1);

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname , sequenceorder) 
SELECT 'IDS_TSK_PROJECTTEAM', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLORDERS'  ,3
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_PROJECTTEAM' and screenname='IDS_SCN_PROTOCOLORDERS' and usergroupid_usergroupcode = 1);

UPDATE lsusergrouprightsmaster SET sequenceorder = CASE
    WHEN screenname = 'IDS_SCN_DASHBOARD' THEN 1

    WHEN screenname = 'IDS_SCN_SHEETORDERS' THEN 2
    WHEN screenname = 'IDS_SCN_PROTOCOLORDERS' THEN 3
    WHEN screenname = 'IDS_SCN_UNLOCKORDERS' THEN 4

    WHEN screenname = 'IDS_SCN_SHEETTEMPLATE' THEN 5
    WHEN screenname = 'IDS_SCN_PROTOCOLTEMPLATE' THEN 6
    WHEN screenname = 'IDS_SCN_TEMPLATEMAPPING' THEN 7

    WHEN screenname = 'IDS_SCN_USERGROUP' THEN 8
    WHEN screenname = 'IDS_SCN_SITEMASTER' THEN 9
    WHEN screenname = 'IDS_SCN_USERMASTER' THEN 10
    WHEN screenname = 'IDS_SCN_USERRIGHTS' THEN 11
    WHEN screenname = 'IDS_SCN_PROJECTMASTER' THEN 12
    WHEN screenname = 'IDS_SCN_PROJECTTEAM' THEN 13
    WHEN screenname = 'IDS_SCN_TASKMASTER' THEN 14
    WHEN screenname = 'IDS_SCN_ORDERWORKLOW' THEN 15
    WHEN screenname = 'IDS_SCN_TEMPLATEWORKFLOW' THEN 16
    WHEN screenname = 'IDS_SCN_DOMAIN' THEN 17
    WHEN screenname = 'IDS_SCN_PASSWORDPOLICY' THEN 18

    WHEN screenname = 'IDS_SCN_DELIMITER' THEN 19
    WHEN screenname = 'IDS_SCN_INSTRUMENTCATEGORY' THEN 20
    WHEN screenname = 'IDS_SCN_INSTRUMENTMASTER' THEN 21
    WHEN screenname = 'IDS_SCN_METHODDELIMITER' THEN 22
    WHEN screenname = 'IDS_SCN_METHODMASTER' THEN 23

    WHEN screenname = 'IDS_SCN_AUDITTRAILHIS' THEN 24
    WHEN screenname = 'IDS_SCN_CFRSETTINGS' THEN 25
    WHEN screenname = 'IDS_SCN_AUDITTRAILCONFIG' THEN 26

    WHEN screenname = 'IDS_SCN_REPORTS' THEN 27
    WHEN screenname = 'IDS_SCN_REPORTVIEVER' THEN 28
    WHEN screenname = 'IDS_SCN_REPORTMAPPER' THEN 29

    WHEN screenname = 'IDS_SCN_MATERIAL' THEN 30
    WHEN screenname = 'IDS_SCN_MATERIALINVENTORY' THEN 31
    WHEN screenname = 'IDS_SCN_MATERIALTYPEPARAMS' THEN 32
    WHEN screenname = 'IDS_SCN_MATERIALCATEGORY' THEN 33
    WHEN screenname = 'IDS_SCN_GRADEMASTER' THEN 34
    WHEN screenname = 'IDS_SCN_SUPPLIER' THEN 35
    WHEN screenname = 'IDS_SCN_STORAGELOCATION' THEN 36
    WHEN screenname = 'IDS_SCN_SECTIONMASTER' THEN 37
    WHEN screenname = 'IDS_SCN_MANUFACTURER' THEN 38
    WHEN screenname = 'IDS_SCN_UNITMASTER' THEN 39
    WHEN screenname = 'IDS_SCN_MATERIAL' THEN 40
    WHEN screenname = 'IDS_SCN_MATERIALINVENTORY' THEN 41

    WHEN screenname = 'IDS_SCN_EQUIPMENT' THEN 42
    WHEN screenname = 'IDS_SCN_EQUIPMENTMASTER' THEN 43
    WHEN screenname = 'IDS_TSK_EQUIPMENTMASTER' THEN 44

    WHEN screenname = 'IDS_SCN_LOGBOOK' THEN 45
    ELSE sequenceorder -- Retain the current value if no match
END;

UPDATE lsusergrouprights SET sequenceorder = CASE
    WHEN screenname = 'IDS_SCN_DASHBOARD' THEN 1

    WHEN screenname = 'IDS_SCN_SHEETORDERS' THEN 2
    WHEN screenname = 'IDS_SCN_PROTOCOLORDERS' THEN 3
    WHEN screenname = 'IDS_SCN_UNLOCKORDERS' THEN 4

    WHEN screenname = 'IDS_SCN_SHEETTEMPLATE' THEN 5
    WHEN screenname = 'IDS_SCN_PROTOCOLTEMPLATE' THEN 6
    WHEN screenname = 'IDS_SCN_TEMPLATEMAPPING' THEN 7

    WHEN screenname = 'IDS_SCN_USERGROUP' THEN 8
    WHEN screenname = 'IDS_SCN_SITEMASTER' THEN 9
    WHEN screenname = 'IDS_SCN_USERMASTER' THEN 10
    WHEN screenname = 'IDS_SCN_USERRIGHTS' THEN 11
    WHEN screenname = 'IDS_SCN_PROJECTMASTER' THEN 12
    WHEN screenname = 'IDS_SCN_PROJECTTEAM' THEN 13
    WHEN screenname = 'IDS_SCN_TASKMASTER' THEN 14
    WHEN screenname = 'IDS_SCN_ORDERWORKLOW' THEN 15
    WHEN screenname = 'IDS_SCN_TEMPLATEWORKFLOW' THEN 16
    WHEN screenname = 'IDS_SCN_DOMAIN' THEN 17
    WHEN screenname = 'IDS_SCN_PASSWORDPOLICY' THEN 18

    WHEN screenname = 'IDS_SCN_DELIMITER' THEN 19
    WHEN screenname = 'IDS_SCN_INSTRUMENTCATEGORY' THEN 20
    WHEN screenname = 'IDS_SCN_INSTRUMENTMASTER' THEN 21
    WHEN screenname = 'IDS_SCN_METHODDELIMITER' THEN 22
    WHEN screenname = 'IDS_SCN_METHODMASTER' THEN 23

    WHEN screenname = 'IDS_SCN_AUDITTRAILHIS' THEN 24
    WHEN screenname = 'IDS_SCN_CFRSETTINGS' THEN 25
    WHEN screenname = 'IDS_SCN_AUDITTRAILCONFIG' THEN 26

    WHEN screenname = 'IDS_SCN_REPORTS' THEN 27
    WHEN screenname = 'IDS_SCN_REPORTVIEVER' THEN 28
    WHEN screenname = 'IDS_SCN_REPORTMAPPER' THEN 29

    WHEN screenname = 'IDS_SCN_MATERIAL' THEN 30
    WHEN screenname = 'IDS_SCN_MATERIALINVENTORY' THEN 31
    WHEN screenname = 'IDS_SCN_MATERIALTYPEPARAMS' THEN 32
    WHEN screenname = 'IDS_SCN_MATERIALCATEGORY' THEN 33
    WHEN screenname = 'IDS_SCN_GRADEMASTER' THEN 34
    WHEN screenname = 'IDS_SCN_SUPPLIER' THEN 35
    WHEN screenname = 'IDS_SCN_STORAGELOCATION' THEN 36
    WHEN screenname = 'IDS_SCN_SECTIONMASTER' THEN 37
    WHEN screenname = 'IDS_SCN_MANUFACTURER' THEN 38
    WHEN screenname = 'IDS_SCN_UNITMASTER' THEN 39
    WHEN screenname = 'IDS_SCN_MATERIAL' THEN 40
    WHEN screenname = 'IDS_SCN_MATERIALINVENTORY' THEN 41

    WHEN screenname = 'IDS_SCN_EQUIPMENT' THEN 42
    WHEN screenname = 'IDS_SCN_EQUIPMENTMASTER' THEN 43
    WHEN screenname = 'IDS_TSK_EQUIPMENTMASTER' THEN 44

    WHEN screenname = 'IDS_SCN_LOGBOOK' THEN 45
    ELSE sequenceorder -- Retain the current value if no match
END;

INSERT INTO public.screenmaster(screencode, screenname) VALUES (1, 'IDS_SCN_MATERIALTYPE') ON CONFLICT (screencode) DO NOTHING;

update lsusergrouprightsmaster set screenname = 'IDS_SCN_MATERIALINVENTORY' where screenname = 'IDS_SCN_INVENTORY' and displaytopic = 'IDS_TSK_DISPOSE' and modulename = 'IDS_MDL_INVENTORY';
update lsusergrouprights set screenname = 'IDS_SCN_MATERIALINVENTORY' where screenname = 'IDS_SCN_INVENTORY' and displaytopic = 'IDS_TSK_DISPOSE' and modulename = 'IDS_MDL_INVENTORY';

update lsusergrouprightsmaster set screenname = 'IDS_SCN_MATERIALINVENTORY' where screenname = 'IDS_SCN_INVENTORY' and displaytopic = 'IDS_TSK_BARCODESCANNER' and modulename = 'IDS_MDL_INVENTORY';
update lsusergrouprights set screenname = 'IDS_SCN_MATERIALINVENTORY' where screenname = 'IDS_SCN_INVENTORY' and displaytopic = 'IDS_TSK_BARCODESCANNER' and modulename = 'IDS_MDL_INVENTORY';

update lsusergrouprightsmaster set screenname = 'IDS_SCN_MATERIALINVENTORY' where screenname = 'IDS_SCN_INVENTORY' and displaytopic = 'IDS_TSK_RELEASE' and modulename = 'IDS_MDL_INVENTORY';
update lsusergrouprights set screenname = 'IDS_SCN_MATERIALINVENTORY' where screenname = 'IDS_SCN_INVENTORY' and displaytopic = 'IDS_TSK_RELEASE' and modulename = 'IDS_MDL_INVENTORY';

update lsusergrouprightsmaster set screenname = 'IDS_SCN_MATERIALINVENTORY' where screenname = 'IDS_SCN_INVENTORY' and displaytopic = 'IDS_TSK_OPEN' and modulename = 'IDS_MDL_INVENTORY';
update lsusergrouprights set screenname = 'IDS_SCN_MATERIALINVENTORY' where screenname = 'IDS_SCN_INVENTORY' and displaytopic = 'IDS_TSK_OPEN' and modulename = 'IDS_MDL_INVENTORY';

update lsusergrouprightsmaster set screenname = 'IDS_SCN_MATERIALINVENTORY' where screenname = 'IDS_SCN_INVENTORY' and displaytopic = 'IDS_TSK_RESTOCK' and modulename = 'IDS_MDL_INVENTORY';
update lsusergrouprights set screenname = 'IDS_SCN_MATERIALINVENTORY' where screenname = 'IDS_SCN_INVENTORY' and displaytopic = 'IDS_TSK_RESTOCK' and modulename = 'IDS_MDL_INVENTORY';

Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(185,0,'IDS_MDL_INVENTORY',131,'IDS_SCN_EQUIPMENTTYPE','IDS_TSK_SAVE') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(186,0,'IDS_MDL_INVENTORY',132,'IDS_SCN_EQUIPMENTTYPE','IDS_TSK_EDIT') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(187,0,'IDS_MDL_INVENTORY',133,'IDS_SCN_EQUIPMENTCATEGORY','IDS_TSK_SAVE') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(188,0,'IDS_MDL_INVENTORY',134,'IDS_SCN_EQUIPMENTCATEGORY','IDS_TSK_EDIT') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(189,0,'IDS_MDL_INVENTORY',135,'IDS_SCN_EQUIPMENTCATEGORY','IDS_TSK_RETIRE') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(190,0,'IDS_MDL_INVENTORY',136,'IDS_SCN_EQUIPMENT','IDS_TSK_SAVE') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(191,0,'IDS_MDL_INVENTORY',137,'IDS_SCN_EQUIPMENT','IDS_TSK_EDIT') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(192,0,'IDS_MDL_INVENTORY',138,'IDS_SCN_EQUIPMENT','IDS_TSK_RETIRE') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(193,0,'IDS_MDL_INVENTORY',139,'IDS_SCN_EQUIPMENT','IDS_TSK_ACT/DCT') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(194,0,'IDS_MDL_INVENTORY',140,'IDS_SCN_EQUIPMENT','IDS_TSK_CALIBRATE') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(195,0,'IDS_MDL_INVENTORY',141,'IDS_SCN_EQUIPMENT','IDS_TSK_MAINTAINE') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(180,0,'IDS_MDL_INVENTORY',126,'IDS_SCN_MATERIALMGMT','IDS_TSK_SAVE') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(181,0,'IDS_MDL_INVENTORY',127,'IDS_SCN_MATERIALMGMT','IDS_TSK_RESTOCK') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(182,0,'IDS_MDL_INVENTORY',128,'IDS_SCN_MATERIALMGMT','IDS_TSK_DISPOSE') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(183,0,'IDS_MDL_INVENTORY',129,'IDS_SCN_MATERIALMGMT','IDS_TSK_RELEASE') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(184,0,'IDS_MDL_INVENTORY',130,'IDS_SCN_MATERIALMGMT','IDS_TSK_OPENDATE') ON CONFLICT(serialno)DO NOTHING;

update lsusergrouprightsmaster set screenname = 'IDS_SCN_EQUIPMENTMASTER' where screenname = 'IDS_SCN_INVENTORY' and displaytopic = 'IDS_TSK_ACTDEACT' and modulename = 'IDS_MDL_INVENTORY';
update lsusergrouprights set screenname = 'IDS_SCN_EQUIPMENTMASTER' where screenname = 'IDS_SCN_INVENTORY' and displaytopic = 'IDS_TSK_ACTDEACT' and modulename = 'IDS_MDL_INVENTORY';

update lsusergrouprightsmaster set screenname = 'IDS_SCN_EQUIPMENTMASTER' where screenname = 'IDS_SCN_INVENTORY' and displaytopic = 'IDS_TSK_CALIBRATE' and modulename = 'IDS_MDL_INVENTORY';
update lsusergrouprights set screenname = 'IDS_SCN_EQUIPMENTMASTER' where screenname = 'IDS_SCN_INVENTORY' and displaytopic = 'IDS_TSK_CALIBRATE' and modulename = 'IDS_MDL_INVENTORY';

update lsusergrouprightsmaster set screenname = 'IDS_SCN_EQUIPMENTMASTER' where screenname = 'IDS_SCN_INVENTORY' and displaytopic = 'IDS_TSK_MAINTANN' and modulename = 'IDS_MDL_INVENTORY';
update lsusergrouprights set screenname = 'IDS_SCN_EQUIPMENTMASTER' where screenname = 'IDS_SCN_INVENTORY' and displaytopic = 'IDS_TSK_MAINTANN' and modulename = 'IDS_MDL_INVENTORY';

-- update materialtype set smaterialtypename = 'IQC Standard Material Type' where nmaterialtypecode = 4;
-- update materialtype set smaterialtypename = 'Volumetric Type' where nmaterialtypecode = 2;
-- update materialtype set smaterialtypename = 'Material Inventory Type' where nmaterialtypecode = 3;
-- update materialtype set smaterialtypename = 'Standared Type' where nmaterialtypecode = 1;

INSERT INTO public.screenmaster(screencode, screenname) VALUES (2, 'IDS_SCN_SHEETTEMPLATE') ON CONFLICT (screencode) DO NOTHING;

update lsusergrouprightsmaster set screate = '0', sedit = '0' where modulename = 'IDS_MDL_INVENTORY' and screenname = 'IDS_SCN_MATERIALTYPEPARAMS' and displaytopic = 'IDS_SCN_MATERIALTYPEPARAMS';
update lsusergrouprights set screate = '1', sedit = '1' where modulename = 'IDS_MDL_INVENTORY' and screenname = 'IDS_SCN_MATERIALTYPEPARAMS' and displaytopic = 'IDS_SCN_MATERIALTYPEPARAMS' and usergroupid_usergroupcode = 1;

UPDATE LScfrreasons SET createddate = CURRENT_TIMESTAMP WHERE createddate IS NULL;

UPDATE LScfrreasons SET status = 1 WHERE status IS NULL;

update instrumenttype set status = 1 where instrumenttype.insttypename='RS232'  and status =-1;
update instrumenttype set status = 1 where instrumenttype.insttypename='TCP/IP' and status =-1;

--DROP TABLE IF EXISTS rctcpresultfieldvalues;
--DROP TABLE IF EXISTS rctcpresultdetails;

insert into LSpreferences (serialno,tasksettings,valuesettings) values(6,'RegulatedIndustry','1') on conflict(serialno) do nothing;

UPDATE delimiter SET delimiterstatus = 'A' WHERE delimiterkey = 10 AND delimiterstatus IS NULL;


ALTER TABLE public.method ALTER COLUMN instmasterkey DROP NOT NULL;

ALTER TABLE public.rctcpresultdetails ALTER COLUMN instmasterkey DROP NOT NULL;

ALTER TABLE public.lsfileelnmethod ALTER COLUMN instmasterkey DROP NOT NULL;

ALTER TABLE public.lsorderelnmethod ALTER COLUMN instmasterkey DROP NOT NULL;
           
                   
ALTER TABLE IF Exists lslogilabprotocoldetail ADD COLUMN IF NOT EXISTS lstestmasterlocal_testcode integer;

ALTER TABLE IF Exists lslogilabprotocoldetail DROP CONSTRAINT IF EXISTS fk701k777d2da33pkkl6lnasathis;

ALTER TABLE lslogilabprotocoldetail ADD CONSTRAINT fk701k777d2da33pkkl6lnasathis FOREIGN KEY (lstestmasterlocal_testcode) 
REFERENCES lstestmasterlocal (testcode);

UPDATE lslogilabprotocoldetail SET lstestmasterlocal_testcode = testcode WHERE testcode IS not NULL;

INSERT into stopbits(stopbitkey,stopbitname,status) VALUES(1,'-1',1) ON CONFLICT(stopbitkey)DO NOTHING;
INSERT into stopbits(stopbitkey,stopbitname,status) VALUES(2,'0',1) ON CONFLICT(stopbitkey)DO NOTHING;
INSERT into stopbits(stopbitkey,stopbitname,status) VALUES(3,'1',1) ON CONFLICT(stopbitkey)DO NOTHING;
INSERT into stopbits(stopbitkey,stopbitname,status) VALUES(4,'1.5',1) ON CONFLICT(stopbitkey)DO NOTHING;
INSERT into stopbits(stopbitkey,stopbitname,status) VALUES(5,'2',1) ON CONFLICT(stopbitkey)DO NOTHING;

INSERT into parity(paritykey,parityname,status) VALUES(1,'NONE',1) ON CONFLICT(paritykey)DO NOTHING;
INSERT into parity(paritykey,parityname,status) VALUES(2,'ODD',1) ON CONFLICT(paritykey)DO NOTHING;
INSERT into parity(paritykey,parityname,status) VALUES(3,'EVEN',1) ON CONFLICT(paritykey)DO NOTHING;

INSERT into handshake(handshakekey,handshakename,status) VALUES(1,'NONE',1) ON CONFLICT(handshakekey)DO NOTHING;
INSERT into handshake(handshakekey,handshakename,status) VALUES(2,'Xon_Xoff',1) ON CONFLICT(handshakekey)DO NOTHING;
INSERT into handshake(handshakekey,handshakename,status) VALUES(3,'RTS_CTS',1) ON CONFLICT(handshakekey)DO NOTHING;
INSERT into handshake(handshakekey,handshakename,status) VALUES(4,'BOTH',1) ON CONFLICT(handshakekey)DO NOTHING;

INSERT into resultsamplefrom(resultsamplekey,resultsamplename,status) VALUES(1,'IFACER',1) ON CONFLICT(resultsamplekey)DO NOTHING;
INSERT into resultsamplefrom(resultsamplekey,resultsamplename,status) VALUES(2,'LimsTestOrder',1) ON CONFLICT(resultsamplekey)DO NOTHING;
INSERT into resultsamplefrom(resultsamplekey,resultsamplename,status) VALUES(3,'DataFileName',1) ON CONFLICT(resultsamplekey)DO NOTHING;

INSERT into instrumenttype(insttypekey,insttypename,status) VALUES(4,'TCP_SERVER',1) ON CONFLICT(insttypekey)DO NOTHING;
INSERT into instrumenttype(insttypekey,insttypename,status) VALUES(5,'ICPMODBUS',1) ON CONFLICT(insttypekey)DO NOTHING;

INSERT into conversiontype(conversiontypekey,conversiontypename,status) VALUES(1,'NONE',1) ON CONFLICT(conversiontypekey)DO NOTHING;
INSERT into conversiontype(conversiontypekey,conversiontypename,status) VALUES(2,'Temperature_Celcius',1) ON CONFLICT(conversiontypekey)DO NOTHING;
INSERT into conversiontype(conversiontypekey,conversiontypename,status) VALUES(3,'Temperature_Farenheit',1) ON CONFLICT(conversiontypekey)DO NOTHING;

ALTER TABLE IF Exists materialtype ADD COLUMN IF NOT EXISTS usageoption Integer;

update materialtype set usageoption = 1 where usageoption IS NULL;

CREATE TABLE IF NOT EXISTS public.materialattachments
(
    nmaterialattachcode integer NOT NULL,
    createddate timestamp without time zone,
    fileextension character varying(10) COLLATE pg_catalog."default",
    fileid character varying(250) COLLATE pg_catalog."default",
    filename character varying(250) COLLATE pg_catalog."default",
    nmaterialcatcode integer,
    nmaterialcode integer,
    nmaterialtypecode integer,
    nsitecode integer,
    nstatus integer,
    createby_usercode integer,
    CONSTRAINT materialattachments_pkey PRIMARY KEY (nmaterialattachcode),
    CONSTRAINT fk133yuho3utecv6hl5rkkqrnlt FOREIGN KEY (nmaterialcode)
        REFERENCES public.elnmaterial (nmaterialcode) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkmctfht2luiya3aem7u8lp0xo9 FOREIGN KEY (createby_usercode)
        REFERENCES public.lsusermaster (usercode) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;


INSERT into sequencetable(sequencecode, screenname, resetperiod,sequenceview,sequenceformat,applicationsequence,sequenceday,sequencemonth,sequenceyear,seperator) VALUES (3,'IDS_SCN_MATERIALMGNT',1,1,'{"t":{"t":"ELN"},"s":{"l":"6"}}',-1,0,0,0,'_') ON CONFLICT (sequencecode) DO NOTHING;
INSERT into sequencetable(sequencecode, screenname, resetperiod,sequenceview,sequenceformat,applicationsequence,sequenceday,sequencemonth,sequenceyear,seperator) VALUES (4,'IDS_SCN_EQUIPMENTMGNT',1,1,'{"t":{"t":"ELN"},"s":{"l":"6"}}',-1,0,0,0,'_') ON CONFLICT (sequencecode) DO NOTHING;
INSERT into sequencetable(sequencecode, screenname, resetperiod,sequenceview,sequenceformat,applicationsequence,sequenceday,sequencemonth,sequenceyear,seperator) VALUES (5,'IDS_SCN_SAMPLEMGNT',1,1,'{"t":{"t":"ELN"},"s":{"l":"6"}}',-1,0,0,0,'_') ON CONFLICT (sequencecode) DO NOTHING;

INSERT into sequencetable(sequencecode, screenname, resetperiod,sequenceview,sequenceformat,applicationsequence,sequenceday,sequencemonth,sequenceyear) VALUES (1,'IDS_SCN_SHEETORDERS',1,1,'{"t":{"t":"ELN"},"s":{"l":"6"}}',-1,0,0,0) ON CONFLICT (sequencecode) DO NOTHING; 
INSERT into sequencetable(sequencecode, screenname, resetperiod,sequenceview,sequenceformat,applicationsequence,sequenceday,sequencemonth,sequenceyear) VALUES (2,'IDS_SCN_PROTOCOLORDERS',1,1,'{"t":{"t":"ELN"},"s":{"l":"6"}}',-1,0,0,0) ON CONFLICT (sequencecode) DO NOTHING; 

INSERT INTO tblADSSettings (ldaplocationid,createddate,groupname,lastsyncdate,ldaplocation,ldapserverdomainname,ldapstatus)VALUES ('L0001', CURRENT_DATE, 'Domain Guests',NULL ,'LDAP://192.168.0.250/OU=Users,DC=CORPAGARAM,DC=COM','corpagaram.com', 1)ON CONFLICT (ldaplocationid) DO NOTHING;


-------------------------------------------
---GENRAL

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_GLOBALSEARCH', 'IDS_MDL_GENRAL', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_GENRAL' ,0
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_GLOBALSEARCH' and screenname='IDS_SCN_GENRAL' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (210, 'IDS_TSK_GLOBALSEARCH', 'IDS_MDL_GENRAL', 'IDS_SCN_GENRAL', '0', 'NA', 'NA', 'NA', '0,0,0', 0) 
ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_SWITCHSITE', 'IDS_MDL_GENRAL', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_GENRAL' ,0
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SWITCHSITE' and screenname='IDS_SCN_GENRAL' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (211, 'IDS_TSK_SWITCHSITE', 'IDS_MDL_GENRAL', 'IDS_SCN_GENRAL', '0', 'NA', 'NA', 'NA', '0,0,0', 0) 
ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_SWITCHROLE', 'IDS_MDL_GENRAL', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_GENRAL' ,0
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SWITCHROLE' and screenname='IDS_SCN_GENRAL' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (212, 'IDS_TSK_SWITCHROLE', 'IDS_MDL_GENRAL', 'IDS_SCN_GENRAL', '0', 'NA', 'NA', 'NA', '0,0,0', 0) 
ON CONFLICT (orderno) DO NOTHING;
-------------------------------------------
---DASHBOARD
----Orer overview into Recent order -- name change only
---Template overview Recent Template -- name change only
-------------------------------------------
---inventory parameters
-- update lsusergrouprights set sdelete = '1' where displaytopic = 'IDS_SCN_MATERIALTYPEPARAMS' and usergroupid_usergroupcode = 1 and sdelete = 'NA';
-- update lsusergrouprights set sdelete = '0' where displaytopic = 'IDS_SCN_MATERIALTYPEPARAMS' and usergroupid_usergroupcode != 1 and sdelete = 'NA';
-- update lsusergrouprightsmaster set sdelete = '0' where displaytopic = 'IDS_SCN_MATERIALTYPEPARAMS';
-----------------------------------------------
---SAMPLE CAT--
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_SAMPLECAT', 'IDS_MDL_INVENTORY', 'administrator', '1', '1', '1', '1', 1,1,'IDS_SCN_MATERIALTYPEPARAMS' ,44
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SAMPLECAT' and screenname='IDS_SCN_MATERIALTYPEPARAMS' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (213, 'IDS_TSK_SAMPLECAT', 'IDS_MDL_INVENTORY', 'IDS_SCN_MATERIALTYPEPARAMS', '0', '0', '0', '0', '0,0,0', 44) 
ON CONFLICT (orderno) DO NOTHING;
--------------------------------------------------
---- SAMPLE TYPE---
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_SAMPLETYPE', 'IDS_MDL_INVENTORY', 'administrator', '1', '1', '1', '1', 1,1,'IDS_SCN_MATERIALTYPEPARAMS' ,45
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SAMPLETYPE' and screenname='IDS_SCN_MATERIALTYPEPARAMS' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (214, 'IDS_TSK_SAMPLETYPE', 'IDS_MDL_INVENTORY', 'IDS_SCN_MATERIALTYPEPARAMS', '0', '0', '0', '0', '0,0,0', 45) 
ON CONFLICT (orderno) DO NOTHING;
---------------------------------------------------
------Sheet template----
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_NEW', 'IDS_MDL_TEMPLATES', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SHEETTEMPLATE' ,5
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_NEW' and screenname='IDS_SCN_SHEETTEMPLATE' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (215, 'IDS_TSK_NEW', 'IDS_MDL_TEMPLATES', 'IDS_SCN_SHEETTEMPLATE', '0', 'NA', 'NA', 'NA', '0,0,0', 5) 
ON CONFLICT (orderno) DO NOTHING;

---------------------------------------------------
------Protocol template----
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_NEW', 'IDS_MDL_TEMPLATES', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLTEMPLATE' ,6
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_NEW' and screenname='IDS_SCN_PROTOCOLTEMPLATE' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (216, 'IDS_TSK_NEW', 'IDS_MDL_TEMPLATES', 'IDS_SCN_PROTOCOLTEMPLATE', '0', 'NA', 'NA', 'NA', '0,0,0', 6) 
ON CONFLICT (orderno) DO NOTHING;

---------------------------------------------------
------Material management----

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_ADDCAT', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_MATERIALMGMT' ,46
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_ADDCAT' and screenname='IDS_SCN_MATERIALMGMT' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (217, 'IDS_TSK_ADDCAT', 'IDS_MDL_INVENTORY', 'IDS_SCN_MATERIALMGMT', '0', 'NA', 'NA', 'NA', '0,0,0', 46) 
ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_ADDTYP', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_MATERIALMGMT' ,46
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_ADDTYP' and screenname='IDS_SCN_MATERIALMGMT' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (218, 'IDS_TSK_ADDTYP', 'IDS_MDL_INVENTORY', 'IDS_SCN_MATERIALMGMT', '0', 'NA', 'NA', 'NA', '0,0,0', 46) 
ON CONFLICT (orderno) DO NOTHING;

---m
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_ADDMAT', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_MATERIALMGMT' ,46
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_ADDMAT' and screenname='IDS_SCN_MATERIALMGMT' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (219, 'IDS_TSK_ADDMAT', 'IDS_MDL_INVENTORY', 'IDS_SCN_MATERIALMGMT', '0', 'NA', 'NA', 'NA', '0,0,0', 46) 
ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_EDITMAT', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_MATERIALMGMT' ,46
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_EDITMAT' and screenname='IDS_SCN_MATERIALMGMT' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (220, 'IDS_TSK_EDITMAT', 'IDS_MDL_INVENTORY', 'IDS_SCN_MATERIALMGMT', '0', 'NA', 'NA', 'NA', '0,0,0', 46) 
ON CONFLICT (orderno) DO NOTHING;
---inv

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_CREATEINV', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_MATERIALMGMT' ,46
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_CREATEINV' and screenname='IDS_SCN_MATERIALMGMT' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (221, 'IDS_TSK_CREATEINV', 'IDS_MDL_INVENTORY', 'IDS_SCN_MATERIALMGMT', '0', 'NA', 'NA', 'NA', '0,0,0', 46) 
ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_VIEWINV', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_MATERIALMGMT' ,46
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_VIEWINV' and screenname='IDS_SCN_MATERIALMGMT' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (222, 'IDS_TSK_VIEWINV', 'IDS_MDL_INVENTORY', 'IDS_SCN_MATERIALMGMT', '0', 'NA', 'NA', 'NA', '0,0,0', 46) 
ON CONFLICT (orderno) DO NOTHING;
---
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_ADDSTOCK', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_MATERIALMGMT' ,46
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_ADDSTOCK' and screenname='IDS_SCN_MATERIALMGMT' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (223, 'IDS_TSK_ADDSTOCK', 'IDS_MDL_INVENTORY', 'IDS_SCN_MATERIALMGMT', '0', 'NA', 'NA', 'NA', '0,0,0', 46) 
ON CONFLICT (orderno) DO NOTHING;
------------------------------
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_CFMM', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_MATERIALMGMT' ,46
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_CFMM' and screenname='IDS_SCN_MATERIALMGMT' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (224, 'IDS_TSK_CFMM', 'IDS_MDL_INVENTORY', 'IDS_SCN_MATERIALMGMT', '0', 'NA', 'NA', 'NA', '0,0,0', 46) 
ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_CFINWARD', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_MATERIALMGMT' ,46
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_CFINWARD' and screenname='IDS_SCN_MATERIALMGMT' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (225, 'IDS_TSK_CFINWARD', 'IDS_MDL_INVENTORY', 'IDS_SCN_MATERIALMGMT', '0', 'NA', 'NA', 'NA', '0,0,0', 46) 
ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_IMPORT', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_MATERIALMGMT' ,46
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_IMPORT' and screenname='IDS_SCN_MATERIALMGMT' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (226, 'IDS_TSK_IMPORT', 'IDS_MDL_INVENTORY', 'IDS_SCN_MATERIALMGMT', '0', 'NA', 'NA', 'NA', '0,0,0', 46) 
ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_OPEN', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_MATERIALMGMT' ,46
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_OPEN' and screenname='IDS_SCN_MATERIALMGMT' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (232, 'IDS_TSK_OPEN', 'IDS_MDL_INVENTORY', 'IDS_SCN_MATERIALMGMT', '0', 'NA', 'NA', 'NA', '0,0,0', 46) 
ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_ADDATTACH', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_MATERIALMGMT' ,46
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_ADDATTACH' and screenname='IDS_SCN_MATERIALMGMT' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (234, 'IDS_TSK_ADDATTACH', 'IDS_MDL_INVENTORY', 'IDS_SCN_MATERIALMGMT', '0', 'NA', 'NA', 'NA', '0,0,0', 46) 
ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_ADDHYPLINK', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_MATERIALMGMT' ,46
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_ADDHYPLINK' and screenname='IDS_SCN_MATERIALMGMT' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (235, 'IDS_TSK_ADDHYPLINK', 'IDS_MDL_INVENTORY', 'IDS_SCN_MATERIALMGMT', '0', 'NA', 'NA', 'NA', '0,0,0', 46) 
ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_ASSIGNPROJ', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_MATERIALMGMT' ,46
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_ASSIGNPROJ' and screenname='IDS_SCN_MATERIALMGMT' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (236, 'IDS_TSK_ASSIGNPROJ', 'IDS_MDL_INVENTORY', 'IDS_SCN_MATERIALMGMT', '0', 'NA', 'NA', 'NA', '0,0,0', 46) 
ON CONFLICT (orderno) DO NOTHING;
----

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_RESTOCK', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_MATERIALMGMT' ,46
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_RESTOCK' and screenname='IDS_SCN_MATERIALMGMT' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (237, 'IDS_TSK_RESTOCK', 'IDS_MDL_INVENTORY', 'IDS_SCN_MATERIALMGMT', '0', 'NA', 'NA', 'NA', '0,0,0', 46) 
ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_RELEASE', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_MATERIALMGMT' ,46
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_RELEASE' and screenname='IDS_SCN_MATERIALMGMT' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (238, 'IDS_TSK_RELEASE', 'IDS_MDL_INVENTORY', 'IDS_SCN_MATERIALMGMT', '0', 'NA', 'NA', 'NA', '0,0,0', 46) 
ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_DISPOSE', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_MATERIALMGMT' ,46
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_DISPOSE' and screenname='IDS_SCN_MATERIALMGMT' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (239, 'IDS_TSK_DISPOSE', 'IDS_MDL_INVENTORY', 'IDS_SCN_MATERIALMGMT', '0', 'NA', 'NA', 'NA', '0,0,0', 46) 
ON CONFLICT (orderno) DO NOTHING;

--------------------------------
------sample management----

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_ADDCAT', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SAMPLEMGMT' ,47
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_ADDCAT' and screenname='IDS_SCN_SAMPLEMGMT' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (227, 'IDS_TSK_ADDCAT', 'IDS_MDL_INVENTORY', 'IDS_SCN_SAMPLEMGMT', '0', 'NA', 'NA', 'NA', '0,0,0', 47) 
ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_ADDTYP', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SAMPLEMGMT' ,47
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_ADDTYP' and screenname='IDS_SCN_SAMPLEMGMT' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (228, 'IDS_TSK_ADDTYP', 'IDS_MDL_INVENTORY', 'IDS_SCN_SAMPLEMGMT', '0', 'NA', 'NA', 'NA', '0,0,0', 47) 
ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_ADDSAMPLE', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SAMPLEMGMT' ,47
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_ADDSAMPLE' and screenname='IDS_SCN_SAMPLEMGMT' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (229, 'IDS_TSK_ADDSAMPLE', 'IDS_MDL_INVENTORY', 'IDS_SCN_SAMPLEMGMT', '0', 'NA', 'NA', 'NA', '0,0,0', 47) 
ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_EDITSAMPLE', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SAMPLEMGMT' ,47
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_EDITSAMPLE' and screenname='IDS_SCN_SAMPLEMGMT' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (230, 'IDS_TSK_EDITSAMPLE', 'IDS_MDL_INVENTORY', 'IDS_SCN_SAMPLEMGMT', '0', 'NA', 'NA', 'NA', '0,0,0', 47) 
ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_IMPORT', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SAMPLEMGMT' ,47
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_IMPORT' and screenname='IDS_SCN_SAMPLEMGMT' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (231, 'IDS_TSK_IMPORT', 'IDS_MDL_INVENTORY', 'IDS_SCN_SAMPLEMGMT', '0', 'NA', 'NA', 'NA', '0,0,0', 47) 
ON CONFLICT (orderno) DO NOTHING;

-- INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
-- SELECT 'IDS_TSK_OPEN', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SAMPLEMGMT' ,47
-- WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_OPEN' and screenname='IDS_SCN_SAMPLEMGMT' and usergroupid_usergroupcode = 1); 

-- INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
-- VALUES (233, 'IDS_TSK_OPEN', 'IDS_MDL_INVENTORY', 'IDS_SCN_SAMPLEMGMT', '0', 'NA', 'NA', 'NA', '0,0,0', 47) 
-- ON CONFLICT (orderno) DO NOTHING;
-------------
-- Report DEsigner
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_NEWTEMPLATE', 'IDS_MDL_REPORTS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_REPORTS' ,27
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_NEWTEMPLATE' and screenname='IDS_SCN_REPORTS' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (240, 'IDS_TSK_NEWTEMPLATE', 'IDS_MDL_REPORTS', 'IDS_SCN_REPORTS', '0', 'NA', 'NA', 'NA', '0,0,0', 27) 
ON CONFLICT (orderno) DO NOTHING;
-----REport viewer
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_NEWTEMPLATER', 'IDS_MDL_REPORTS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_REPORTVIEVER' ,46
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_NEWTEMPLATE' and screenname='IDS_SCN_REPORTVIEVER' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (241, 'IDS_TSK_NEWTEMPLATER', 'IDS_MDL_REPORTS', 'IDS_SCN_REPORTVIEVER', '0', 'NA', 'NA', 'NA', '0,0,0', 28) 
ON CONFLICT (orderno) DO NOTHING;

------Sheet template----
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_VISIBLITY', 'IDS_MDL_TEMPLATES', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SHEETTEMPLATE' ,5
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_VISIBLITY' and screenname='IDS_SCN_SHEETTEMPLATE' and usergroupid_usergroupcode = 1); 

ALTER TABLE IF Exists LSlogilabprotocoldetail ADD COLUMN IF NOT EXISTS modifieddate timestamp without time zone;
ALTER TABLE IF Exists LSlogilabprotocoldetail ADD COLUMN IF NOT EXISTS modifiedby character varying(255);

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (242, 'IDS_TSK_VISIBLITY', 'IDS_MDL_TEMPLATES', 'IDS_SCN_SHEETTEMPLATE', '0', 'NA', 'NA', 'NA', '0,0,0', 5) 
ON CONFLICT (orderno) DO NOTHING;
---------------------------------------------------
------Protocol template----
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_VISIBLITY', 'IDS_MDL_TEMPLATES', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLTEMPLATE' ,6
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_VISIBLITY' and screenname='IDS_SCN_PROTOCOLTEMPLATE' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (243, 'IDS_TSK_VISIBLITY', 'IDS_MDL_TEMPLATES', 'IDS_SCN_PROTOCOLTEMPLATE', '0', 'NA', 'NA', 'NA', '0,0,0', 6) 
ON CONFLICT (orderno) DO NOTHING;
---------------------------------------------------

ALTER TABLE IF Exists elnmaterialinventory ADD COLUMN IF NOT EXISTS sequenceid character varying(255);

-----Screen Righsts ---------
-----------------------------------------

---Dashboard

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_SCREENVIEW', 'IDS_MDL_DASHBOARD', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_DASHBOARD' ,1
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SCREENVIEW' and screenname='IDS_SCN_DASHBOARD' and usergroupid_usergroupcode = 1); 


    INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
    VALUES (244, 'IDS_TSK_SCREENVIEW', 'IDS_MDL_DASHBOARD', 'IDS_SCN_DASHBOARD', '0', 'NA', 'NA', 'NA', '0,0,0', 1) 
    ON CONFLICT (orderno) DO NOTHING;
-------------------------------------------

---sheet order

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_SCREENVIEW', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SHEETORDERS' ,3
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SCREENVIEW' and screenname='IDS_SCN_SHEETORDERS' and usergroupid_usergroupcode = 1); 


    INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
    VALUES (245, 'IDS_TSK_SCREENVIEW', 'IDS_MDL_ORDERS', 'IDS_SCN_SHEETORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 3) 
    ON CONFLICT (orderno) DO NOTHING;

-------------------------------------------

---Protocol order

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_SCREENVIEW', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLORDERS' ,5
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SCREENVIEW' and screenname='IDS_SCN_PROTOCOLORDERS' and usergroupid_usergroupcode = 1); 


    INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
    VALUES (246, 'IDS_TSK_SCREENVIEW', 'IDS_MDL_ORDERS', 'IDS_SCN_PROTOCOLORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 5) 
    ON CONFLICT (orderno) DO NOTHING;

-------------------------------------------

---Unlock order

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_SCREENVIEW', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_UNLOCKORDERS' ,7
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SCREENVIEW' and screenname='IDS_SCN_UNLOCKORDERS' and usergroupid_usergroupcode = 1); 


    INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
    VALUES (247,'IDS_TSK_SCREENVIEW', 'IDS_MDL_ORDERS', 'IDS_SCN_UNLOCKORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 7) 
    ON CONFLICT (orderno) DO NOTHING;
-------------------------------------------

---Sheet Template

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_SCREENVIEW', 'IDS_MDL_TEMPLATES', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SHEETTEMPLATE' ,9
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SCREENVIEW' and screenname='IDS_SCN_SHEETTEMPLATE' and usergroupid_usergroupcode = 1); 


    INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
    VALUES (248, 'IDS_TSK_SCREENVIEW', 'IDS_MDL_TEMPLATES', 'IDS_SCN_SHEETTEMPLATE', '0', 'NA', 'NA', 'NA', '0,0,0', 9) 
    ON CONFLICT (orderno) DO NOTHING;
-------------------------------------------

---Protocol Template

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_SCREENVIEW', 'IDS_MDL_TEMPLATES', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLTEMPLATE' ,11
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SCREENVIEW' and screenname='IDS_SCN_PROTOCOLTEMPLATE' and usergroupid_usergroupcode = 1); 


    INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
    VALUES (249, 'IDS_TSK_SCREENVIEW', 'IDS_MDL_TEMPLATES', 'IDS_SCN_PROTOCOLTEMPLATE', '0', 'NA', 'NA', 'NA', '0,0,0', 11) 
    ON CONFLICT (orderno) DO NOTHING;

-------------------------------------------

---Template Mapping

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_SCREENVIEW', 'IDS_MDL_TEMPLATES', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_TEMPLATEMAPPING' ,13
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SCREENVIEW' and screenname='IDS_SCN_TEMPLATEMAPPING' and usergroupid_usergroupcode = 1); 


    INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
    VALUES (250, 'IDS_TSK_SCREENVIEW', 'IDS_MDL_TEMPLATES', 'IDS_SCN_TEMPLATEMAPPING', '0', 'NA', 'NA', 'NA', '0,0,0', 13) 
    ON CONFLICT (orderno) DO NOTHING;

-------------------------------------------

---INVENTORY------
------------------------------
---Material MGMT_

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_SCREENVIEW', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_MATERIALMGMT' ,15
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SCREENVIEW' and screenname='IDS_SCN_MATERIALMGMT' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (251, 'IDS_TSK_SCREENVIEW', 'IDS_MDL_INVENTORY', 'IDS_SCN_MATERIALMGMT', '0', 'NA', 'NA', 'NA', '0,0,0', 15) 
ON CONFLICT (orderno) DO NOTHING;
--------------------------------------------
--sheet order
 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_REGISTER', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SHEETORDERS' ,4
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_REGISTER' and screenname='IDS_SCN_SHEETORDERS' and usergroupid_usergroupcode = 1);
 
 
INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (268, 'IDS_TSK_REGISTER', 'IDS_MDL_ORDERS', 'IDS_SCN_SHEETORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 4) 
ON CONFLICT (orderno) DO NOTHING;
 
-------------------------------------------
 
---Protocol order
 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_REGISTER', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLORDERS' ,6
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_REGISTER' and screenname='IDS_SCN_PROTOCOLORDERS' and usergroupid_usergroupcode = 1);
 
 
INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (269, 'IDS_TSK_REGISTER', 'IDS_MDL_ORDERS', 'IDS_SCN_PROTOCOLORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 6) 
ON CONFLICT (orderno) DO NOTHING;
 
-----------------
-----------Sheet Order
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_EXPORTJSON', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SHEETORDERS' ,4
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_EXPORTJSON' and screenname='IDS_SCN_SHEETORDERS' and usergroupid_usergroupcode = 1);
 
 
INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (270, 'IDS_TSK_EXPORTJSON', 'IDS_MDL_ORDERS', 'IDS_SCN_SHEETORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 4) 
ON CONFLICT (orderno) DO NOTHING;
 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_EXPORTEXCEL', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SHEETORDERS' ,4
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_EXPORTEXCEL' and screenname='IDS_SCN_SHEETORDERS' and usergroupid_usergroupcode = 1);
 
 
INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (271, 'IDS_TSK_EXPORTEXCEL', 'IDS_MDL_ORDERS', 'IDS_SCN_SHEETORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 4) 
ON CONFLICT (orderno) DO NOTHING;
-------------------------------------------
--Sample MGMT

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_SCREENVIEW', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SAMPLEMGMT' ,17
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SCREENVIEW' and screenname='IDS_SCN_SAMPLEMGMT' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (300, 'IDS_TSK_SCREENVIEW', 'IDS_MDL_INVENTORY', 'IDS_SCN_SAMPLEMGMT', '0', 'NA', 'NA', 'NA', '0,0,0', 17) 
ON CONFLICT (orderno) DO NOTHING;
-------------------------------------------
-------------------------------------------
--Equipment MGMT

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_SCREENVIEW', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_EQUIPMENTMASTER' ,20
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SCREENVIEW' and screenname='IDS_SCN_EQUIPMENTMASTER' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (252, 'IDS_TSK_SCREENVIEW', 'IDS_MDL_INVENTORY', 'IDS_SCN_EQUIPMENTMASTER', '0', 'NA', 'NA', 'NA', '0,0,0', 20) 
ON CONFLICT (orderno) DO NOTHING;
-------------------------------------------

-------------------------------------------
----user group

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_SCREENVIEW', 'IDS_MDL_SETUP', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_USERGROUP' ,23
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SCREENVIEW' and screenname='IDS_SCN_USERGROUP' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (253, 'IDS_TSK_SCREENVIEW', 'IDS_MDL_SETUP', 'IDS_SCN_USERGROUP', '0', 'NA', 'NA', 'NA', '0,0,0', 23) 
ON CONFLICT (orderno) DO NOTHING;
-------------------------------------------
----user master

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_SCREENVIEW', 'IDS_MDL_SETUP', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_USERMASTER' ,25
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SCREENVIEW' and screenname='IDS_SCN_USERMASTER' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (254, 'IDS_TSK_SCREENVIEW', 'IDS_MDL_SETUP', 'IDS_SCN_USERMASTER', '0', 'NA', 'NA', 'NA', '0,0,0', 25) 
ON CONFLICT (orderno) DO NOTHING;
-------------------------------------------
----Label master

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_SCREENVIEW', 'IDS_MDL_SETUP', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_BARCODEMASTER' ,33
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SCREENVIEW' and screenname='IDS_SCN_BARCODEMASTER' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (255, 'IDS_TSK_SCREENVIEW', 'IDS_MDL_SETUP', 'IDS_SCN_BARCODEMASTER', '0', 'NA', 'NA', 'NA', '0,0,0', 33) 
ON CONFLICT (orderno) DO NOTHING;
-------------------------------------------
----Parser

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_SCREENVIEW', 'IDS_MDL_SETUP', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PARSER' ,35
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SCREENVIEW' and screenname='IDS_SCN_PARSER' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (256, 'IDS_TSK_SCREENVIEW', 'IDS_MDL_SETUP', 'IDS_SCN_PARSER', '0', 'NA', 'NA', 'NA', '0,0,0', 35) 
ON CONFLICT (orderno) DO NOTHING;
-------------------------------------------
----logbook

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_SCREENVIEW', 'IDS_MDL_LOGBOOK', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_LOGBOOK' ,41
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SCREENVIEW' and screenname='IDS_SCN_LOGBOOK' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (257, 'IDS_TSK_SCREENVIEW', 'IDS_MDL_LOGBOOK', 'IDS_SCN_LOGBOOK', '0', 'NA', 'NA', 'NA', '0,0,0', 41) 
ON CONFLICT (orderno) DO NOTHING;
-------------------------------------------
----REPORTS

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_SCREENVIEW', 'IDS_MDL_REPORTS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_REPORTS' ,43
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SCREENVIEW' and screenname='IDS_SCN_REPORTS' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (258, 'IDS_TSK_SCREENVIEW', 'IDS_MDL_REPORTS', 'IDS_SCN_REPORTS', '0', 'NA', 'NA', 'NA', '0,0,0', 43) 
ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_SCREENVIEW', 'IDS_MDL_REPORTS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_REPORTVIEVER' ,45
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SCREENVIEW' and screenname='IDS_SCN_REPORTVIEVER' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (259, 'IDS_TSK_SCREENVIEW', 'IDS_MDL_REPORTS', 'IDS_SCN_REPORTVIEVER', '0', 'NA', 'NA', 'NA', '0,0,0', 45) 
ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_SCREENVIEW', 'IDS_MDL_REPORTS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_REPORTMAPPER' ,47
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SCREENVIEW' and screenname='IDS_SCN_REPORTMAPPER' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (260, 'IDS_TSK_SCREENVIEW', 'IDS_MDL_REPORTS', 'IDS_SCN_REPORTMAPPER', '0', 'NA', 'NA', 'NA', '0,0,0', 47) 
ON CONFLICT (orderno) DO NOTHING;
-------------------------------------------
-------------------------------------------
----AUDIT

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_SCREENVIEW', 'IDS_MDL_AUDITTRAIL', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_AUDITTRAILHIS' ,50
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SCREENVIEW' and screenname='IDS_SCN_AUDITTRAILHIS' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (261, 'IDS_TSK_SCREENVIEW', 'IDS_MDL_AUDITTRAIL', 'IDS_SCN_AUDITTRAILHIS', '0', 'NA', 'NA', 'NA', '0,0,0', 50) 
ON CONFLICT (orderno) DO NOTHING;

------------------------------

update lsusergrouprights set screenname = 'IDS_SCN_MATERIALTYPEPARAMS' where screenname in
('IDS_SCN_MATERIALCATEGORY','IDS_SCN_GRADEMASTER','IDS_SCN_SUPPLIER','IDS_SCN_STORAGELOCATION','IDS_SCN_SECTIONMASTER',
'IDS_SCN_MANUFACTURER','IDS_SCN_UNITMASTER','IDS_SCN_SAMPLETYPE','IDS_SCN_SAMPLECAT');

update lsusergrouprightsmaster set screenname = 'IDS_SCN_MATERIALTYPEPARAMS' where screenname in
('IDS_SCN_MATERIALCATEGORY','IDS_SCN_GRADEMASTER','IDS_SCN_SUPPLIER','IDS_SCN_STORAGELOCATION','IDS_SCN_SECTIONMASTER',
'IDS_SCN_MANUFACTURER','IDS_SCN_UNITMASTER','IDS_SCN_SAMPLETYPE','IDS_SCN_SAMPLECAT');

update lsusergrouprightsmaster set sequenceorder = 0 where  modulename = 'IDS_MDL_GENRAL';

update lsusergrouprightsmaster set sequenceorder = 2 
where screenname = 'IDS_SCN_DASHBOARD' and modulename = 'IDS_MDL_DASHBOARD' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 4 
where screenname = 'IDS_SCN_SHEETORDERS' and modulename = 'IDS_MDL_ORDERS' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 6 
where screenname = 'IDS_SCN_PROTOCOLORDERS' and modulename = 'IDS_MDL_ORDERS' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 8 
where screenname = 'IDS_SCN_UNLOCKORDERS' and modulename = 'IDS_MDL_ORDERS' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 10 
where screenname = 'IDS_SCN_SHEETTEMPLATE' and modulename = 'IDS_MDL_TEMPLATES' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 12 
where screenname = 'IDS_SCN_PROTOCOLTEMPLATE' and modulename = 'IDS_MDL_TEMPLATES' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 14 
where screenname = 'IDS_SCN_TEMPLATEMAPPING' and modulename = 'IDS_MDL_TEMPLATES' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 16 
where screenname = 'IDS_SCN_MATERIALMGMT' and modulename = 'IDS_MDL_INVENTORY' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 18 
where screenname = 'IDS_SCN_SAMPLEMGMT' and modulename = 'IDS_MDL_INVENTORY' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 19 
where screenname = 'IDS_SCN_MATERIALTYPEPARAMS' and modulename = 'IDS_MDL_INVENTORY' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 21 
where screenname = 'IDS_SCN_EQUIPMENTMASTER' and modulename = 'IDS_MDL_INVENTORY' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 22 
where screenname = 'IDS_SCN_EQUIPMENT' and modulename = 'IDS_MDL_INVENTORY';

update lsusergrouprightsmaster set sequenceorder = 24 
where screenname = 'IDS_SCN_USERGROUP' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 26 
where screenname = 'IDS_SCN_USERMASTER' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 27 
where screenname = 'IDS_SCN_USERRIGHTS' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 28 
where screenname = 'IDS_SCN_PROJECTMASTER' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 29 
where screenname = 'IDS_SCN_PROJECTTEAM' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 30 
where screenname = 'IDS_SCN_TASKMASTER' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 31 
where screenname = 'IDS_SCN_ORDERWORKLOW' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 32 
where screenname = 'IDS_SCN_TEMPLATEWORKFLOW' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 34 
where screenname = 'IDS_SCN_BARCODEMASTER' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 36 
where screenname = 'IDS_SCN_PARSER' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 37 
where screenname = 'IDS_SCN_SITEMASTER' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 38 
where screenname = 'IDS_SCN_DOMAIN' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 39 
where screenname = 'IDS_SCN_ACTIVEUSER' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 40 
where screenname = 'IDS_SCN_PASSWORDPOLICY' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 42 
where screenname = 'IDS_SCN_LOGBOOK' and modulename = 'IDS_MDL_LOGBOOK' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 44 
where screenname = 'IDS_SCN_REPORTS' and modulename = 'IDS_MDL_REPORTS' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 46 
where screenname = 'IDS_SCN_REPORTVIEVER' and modulename = 'IDS_MDL_REPORTS' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 48 
where screenname = 'IDS_SCN_REPORTMAPPER' and modulename = 'IDS_MDL_REPORTS' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 50 
where screenname = 'IDS_SCN_AUDITTRAILHIS' and modulename = 'IDS_MDL_AUDITTRAIL' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 51 
where screenname = 'IDS_SCN_CFRSETTINGS' and modulename = 'IDS_MDL_AUDITTRAIL' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 52 
where screenname = 'IDS_SCN_AUDITTRAILCONFIG' and modulename = 'IDS_MDL_AUDITTRAIL' and displaytopic != 'IDS_TSK_SCREENVIEW';
-------------------------------------------------------------------------

update lsusergrouprightsmaster set sequenceorder = 0 where  modulename = 'IDS_MDL_GENRAL';
update lsusergrouprights set sequenceorder = 0 where  modulename = 'IDS_MDL_GENRAL';

update lsusergrouprights set sequenceorder = 2 
where screenname = 'IDS_SCN_DASHBOARD' and modulename = 'IDS_MDL_DASHBOARD' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 4 
where screenname = 'IDS_SCN_SHEETORDERS' and modulename = 'IDS_MDL_ORDERS' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 6 
where screenname = 'IDS_SCN_PROTOCOLORDERS' and modulename = 'IDS_MDL_ORDERS' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 8 
where screenname = 'IDS_SCN_UNLOCKORDERS' and modulename = 'IDS_MDL_ORDERS' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 10 
where screenname = 'IDS_SCN_SHEETTEMPLATE' and modulename = 'IDS_MDL_TEMPLATES' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 12 
where screenname = 'IDS_SCN_PROTOCOLTEMPLATE' and modulename = 'IDS_MDL_TEMPLATES' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 14 
where screenname = 'IDS_SCN_TEMPLATEMAPPING' and modulename = 'IDS_MDL_TEMPLATES' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 16 
where screenname = 'c' and modulename = 'IDS_MDL_INVENTORY' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 18 
where screenname = 'IDS_SCN_SAMPLEMGMT' and modulename = 'IDS_MDL_INVENTORY' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 19 
where screenname = 'IDS_SCN_MATERIALTYPEPARAMS' and modulename = 'IDS_MDL_INVENTORY' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 21 
where screenname = 'IDS_SCN_EQUIPMENTMASTER' and modulename = 'IDS_MDL_INVENTORY' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 22 
where screenname = 'IDS_SCN_EQUIPMENT' and modulename = 'IDS_MDL_INVENTORY';

update lsusergrouprights set sequenceorder = 24 
where screenname = 'IDS_SCN_USERGROUP' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 26 
where screenname = 'IDS_SCN_USERMASTER' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 27 
where screenname = 'IDS_SCN_USERRIGHTS' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 28 
where screenname = 'IDS_SCN_PROJECTMASTER' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 29 
where screenname = 'IDS_SCN_PROJECTTEAM' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 30 
where screenname = 'IDS_SCN_TASKMASTER' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 31 
where screenname = 'IDS_SCN_ORDERWORKLOW' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 32 
where screenname = 'IDS_SCN_TEMPLATEWORKFLOW' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 34 
where screenname = 'IDS_SCN_BARCODEMASTER' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 36 
where screenname = 'IDS_SCN_PARSER' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 37 
where screenname = 'IDS_SCN_SITEMASTER' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 38 
where screenname = 'IDS_SCN_DOMAIN' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 39 
where screenname = 'IDS_SCN_ACTIVEUSER' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 40 
where screenname = 'IDS_SCN_PASSWORDPOLICY' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 42 
where screenname = 'IDS_SCN_LOGBOOK' and modulename = 'IDS_MDL_LOGBOOK' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 44 
where screenname = 'IDS_SCN_REPORTS' and modulename = 'IDS_MDL_REPORTS' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 46 
where screenname = 'IDS_SCN_REPORTVIEVER' and modulename = 'IDS_MDL_REPORTS' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 48 
where screenname = 'IDS_SCN_REPORTMAPPER' and modulename = 'IDS_MDL_REPORTS' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 50 
where screenname = 'IDS_SCN_AUDITTRAILHIS' and modulename = 'IDS_MDL_AUDITTRAIL' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 51 
where screenname = 'IDS_SCN_CFRSETTINGS' and modulename = 'IDS_MDL_AUDITTRAIL' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 52 
where screenname = 'IDS_SCN_AUDITTRAILCONFIG' and modulename = 'IDS_MDL_AUDITTRAIL' and displaytopic != 'IDS_TSK_SCREENVIEW';

---------------------------
-------------------------
update lsusergrouprights set sequenceorder = 1 
where screenname = 'IDS_SCN_DASHBOARD' and modulename = 'IDS_MDL_DASHBOARD' and displaytopic = 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 3 
where screenname = 'IDS_SCN_SHEETORDERS' and modulename = 'IDS_MDL_ORDERS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 5 
where screenname = 'IDS_SCN_PROTOCOLORDERS' and modulename = 'IDS_MDL_ORDERS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 7 
where screenname = 'IDS_SCN_UNLOCKORDERS' and modulename = 'IDS_MDL_ORDERS' and displaytopic = 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 9 
where screenname = 'IDS_SCN_SHEETTEMPLATE' and modulename = 'IDS_MDL_TEMPLATES' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 11 
where screenname = 'IDS_SCN_PROTOCOLTEMPLATE' and modulename = 'IDS_MDL_TEMPLATES' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 13
where screenname = 'IDS_SCN_TEMPLATEMAPPING' and modulename = 'IDS_MDL_TEMPLATES' and displaytopic = 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 15 
where screenname = 'IDS_SCN_MATERIALMGMT' and modulename = 'IDS_MDL_INVENTORY' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 17 
where screenname = 'IDS_SCN_SAMPLEMGMT' and modulename = 'IDS_MDL_INVENTORY' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 20 
where screenname = 'IDS_SCN_EQUIPMENTMASTER' and modulename = 'IDS_MDL_INVENTORY' and displaytopic = 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 23 
where screenname = 'IDS_SCN_USERGROUP' and modulename = 'IDS_MDL_SETUP' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 25 
where screenname = 'IDS_SCN_USERMASTER' and modulename = 'IDS_MDL_SETUP' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 33 
where screenname = 'IDS_SCN_BARCODEMASTER' and modulename = 'IDS_MDL_SETUP' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 35 
where screenname = 'IDS_SCN_PARSER' and modulename = 'IDS_MDL_SETUP' and displaytopic = 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 41 
where screenname = 'IDS_SCN_LOGBOOK' and modulename = 'IDS_MDL_LOGBOOK' and displaytopic = 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 43 
where screenname = 'IDS_SCN_REPORTS' and modulename = 'IDS_MDL_REPORTS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 45 
where screenname = 'IDS_SCN_REPORTVIEVER' and modulename = 'IDS_MDL_REPORTS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 47 
where screenname = 'IDS_SCN_REPORTMAPPER' and modulename = 'IDS_MDL_REPORTS' and displaytopic = 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 49 
where screenname = 'IDS_SCN_AUDITTRAILHIS' and modulename = 'IDS_MDL_AUDITTRAIL' and displaytopic = 'IDS_TSK_SCREENVIEW';
-------------------------------------------

update lsusergrouprightsmaster set sequenceorder = 1 
where screenname = 'IDS_SCN_DASHBOARD' and modulename = 'IDS_MDL_DASHBOARD' and displaytopic = 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 3 
where screenname = 'IDS_SCN_SHEETORDERS' and modulename = 'IDS_MDL_ORDERS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 5 
where screenname = 'IDS_SCN_PROTOCOLORDERS' and modulename = 'IDS_MDL_ORDERS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 7 
where screenname = 'IDS_SCN_UNLOCKORDERS' and modulename = 'IDS_MDL_ORDERS' and displaytopic = 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 9 
where screenname = 'IDS_SCN_SHEETTEMPLATE' and modulename = 'IDS_MDL_TEMPLATES' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 11 
where screenname = 'IDS_SCN_PROTOCOLTEMPLATE' and modulename = 'IDS_MDL_TEMPLATES' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 13
where screenname = 'IDS_SCN_TEMPLATEMAPPING' and modulename = 'IDS_MDL_TEMPLATES' and displaytopic = 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 15 
where screenname = 'IDS_SCN_MATERIALMGMT' and modulename = 'IDS_MDL_INVENTORY' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 17 
where screenname = 'IDS_SCN_SAMPLEMGMT' and modulename = 'IDS_MDL_INVENTORY' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 20 
where screenname = 'IDS_SCN_EQUIPMENTMASTER' and modulename = 'IDS_MDL_INVENTORY' and displaytopic = 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 23 
where screenname = 'IDS_SCN_USERGROUP' and modulename = 'IDS_MDL_SETUP' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 25 
where screenname = 'IDS_SCN_USERMASTER' and modulename = 'IDS_MDL_SETUP' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 33 
where screenname = 'IDS_SCN_BARCODEMASTER' and modulename = 'IDS_MDL_SETUP' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 35 
where screenname = 'IDS_SCN_PARSER' and modulename = 'IDS_MDL_SETUP' and displaytopic = 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 41 
where screenname = 'IDS_SCN_LOGBOOK' and modulename = 'IDS_MDL_LOGBOOK' and displaytopic = 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 43 
where screenname = 'IDS_SCN_REPORTS' and modulename = 'IDS_MDL_REPORTS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 45 
where screenname = 'IDS_SCN_REPORTVIEVER' and modulename = 'IDS_MDL_REPORTS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 47 
where screenname = 'IDS_SCN_REPORTMAPPER' and modulename = 'IDS_MDL_REPORTS' and displaytopic = 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 49 
where screenname = 'IDS_SCN_AUDITTRAILHIS' and modulename = 'IDS_MDL_AUDITTRAIL' and displaytopic = 'IDS_TSK_SCREENVIEW';

---------------------------------
--IDS_SCN_MATERIALTYPEPARAMS MGMT

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_SCREENVIEW', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_MATERIALTYPEPARAMS' ,null
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SCREENVIEW' and screenname='IDS_SCN_MATERIALTYPEPARAMS' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (263, 'IDS_TSK_SCREENVIEW', 'IDS_MDL_INVENTORY', 'IDS_SCN_MATERIALTYPEPARAMS', '0', 'NA', 'NA', 'NA', '0,0,0', null) 
ON CONFLICT (orderno) DO NOTHING;

------------------------------------------------------------
--Equipment MGMT

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_SCREENVIEW', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_EQUIPMENT' ,null
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SCREENVIEW' and screenname='IDS_SCN_EQUIPMENT' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (264, 'IDS_TSK_SCREENVIEW', 'IDS_MDL_INVENTORY', 'IDS_SCN_EQUIPMENT', '0', 'NA', 'NA', 'NA', '0,0,0', null) 
ON CONFLICT (orderno) DO NOTHING;

-------------------------------------------------
----user Righst

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_SCREENVIEW', 'IDS_MDL_SETUP', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_USERRIGHTS' ,null
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SCREENVIEW' and screenname='IDS_SCN_USERRIGHTS' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (265, 'IDS_TSK_SCREENVIEW', 'IDS_MDL_SETUP', 'IDS_SCN_USERRIGHTS', '0', 'NA', 'NA', 'NA', '0,0,0', null) 
ON CONFLICT (orderno) DO NOTHING;
-------------------------------------------
----PRJ MNGMT

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_SCREENVIEW', 'IDS_MDL_SETUP', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROJECTMASTER' ,null
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SCREENVIEW' and screenname='IDS_SCN_PROJECTMASTER' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (266, 'IDS_TSK_SCREENVIEW', 'IDS_MDL_SETUP', 'IDS_SCN_PROJECTMASTER', '0', 'NA', 'NA', 'NA', '0,0,0', null) 
ON CONFLICT (orderno) DO NOTHING;
-------------------------------------------
----WORKFLOW

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_SCREENVIEW', 'IDS_MDL_SETUP', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_TEMPLATEWORKFLOW' ,null
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SCREENVIEW' and screenname='IDS_SCN_TEMPLATEWORKFLOW' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (267, 'IDS_TSK_SCREENVIEW', 'IDS_MDL_SETUP', 'IDS_SCN_TEMPLATEWORKFLOW', '0', 'NA', 'NA', 'NA', '0,0,0', null) 
ON CONFLICT (orderno) DO NOTHING;

---------------------
--update lsusergrouprightsmaster set screenname = 'IDS_SCN_PROJECTMASTER' where screenname in ('IDS_SCN_PROJECTTEAM','IDS_SCN_TASKMASTER','IDS_SCN_PROJECTMASTER');
--update lsusergrouprights set screenname = 'IDS_SCN_PROJECTMASTER' where screenname in ('IDS_SCN_PROJECTTEAM','IDS_SCN_TASKMASTER','IDS_SCN_PROJECTMASTER');

update lsusergrouprightsmaster set screenname = 'IDS_SCN_TEMPLATEWORKFLOW' where screenname in ('IDS_SCN_ORDERWORKLOW','IDS_SCN_TEMPLATEWORKFLOW');
update lsusergrouprights set screenname = 'IDS_SCN_TEMPLATEWORKFLOW' where screenname in ('IDS_SCN_ORDERWORKLOW','IDS_SCN_TEMPLATEWORKFLOW');
------------------------------------------
update lsusergrouprightsmaster set screenname = 'IDS_SCN_MATERIALMGMT' where screenname in ('IDS_SCN_MATERIALINVENTORY','IDS_SCN_MATERIAL');
update lsusergrouprights set screenname = 'IDS_SCN_MATERIALMGMT' where screenname in ('IDS_SCN_MATERIALINVENTORY','IDS_SCN_MATERIAL');
----------------------
update lsusergrouprightsmaster set sequenceorder = 20 where screenname = 'IDS_SCN_MATERIALTYPEPARAMS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 21 where screenname = 'IDS_SCN_MATERIALTYPEPARAMS' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 22 where screenname = 'IDS_SCN_EQUIPMENTMASTER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 23 where screenname = 'IDS_SCN_EQUIPMENTMASTER' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 24 where screenname = 'IDS_SCN_EQUIPMENT' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 25 where screenname = 'IDS_SCN_EQUIPMENT' and displaytopic != 'IDS_TSK_SCREENVIEW';
---------
--setup
update lsusergrouprightsmaster set sequenceorder = 26 where screenname = 'IDS_SCN_USERGROUP' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 27 where screenname = 'IDS_SCN_USERGROUP' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 28 where screenname = 'IDS_SCN_USERMASTER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 29 where screenname = 'IDS_SCN_USERMASTER' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 30 where screenname = 'IDS_SCN_USERRIGHTS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 31 where screenname = 'IDS_SCN_USERRIGHTS' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 32 where screenname = 'IDS_SCN_PROJECTMASTER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 33 where screenname = 'IDS_SCN_PROJECTMASTER' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 34 where screenname = 'IDS_SCN_TEMPLATEWORKFLOW' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 35 where screenname = 'IDS_SCN_TEMPLATEWORKFLOW' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 36 where screenname = 'IDS_SCN_PARSER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 37 where screenname = 'IDS_SCN_PARSER' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 38 where screenname = 'IDS_SCN_BARCODEMASTER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 39 where screenname = 'IDS_SCN_BARCODEMASTER' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 40 where screenname = 'IDS_SCN_PASSWORDPOLICY' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 41 where screenname = 'IDS_SCN_LOGBOOK' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 42 where screenname = 'IDS_SCN_LOGBOOK' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 43 where screenname = 'IDS_SCN_REPORTS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 44 where screenname = 'IDS_SCN_REPORTS' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 45 where screenname = 'IDS_SCN_REPORTVIEVER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 46 where screenname = 'IDS_SCN_REPORTVIEVER' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 47 where screenname = 'IDS_SCN_REPORTMAPPER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 48 where screenname = 'IDS_SCN_REPORTMAPPER' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 49 where screenname = 'IDS_SCN_AUDITTRAILHIS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 50 where screenname = 'IDS_SCN_AUDITTRAILHIS' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 20 where screenname = 'IDS_SCN_MATERIALTYPEPARAMS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 21 where screenname = 'IDS_SCN_MATERIALTYPEPARAMS' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 22 where screenname = 'IDS_SCN_EQUIPMENTMASTER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 23 where screenname = 'IDS_SCN_EQUIPMENTMASTER' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 24 where screenname = 'IDS_SCN_EQUIPMENT' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 25 where screenname = 'IDS_SCN_EQUIPMENT' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 26 where screenname = 'IDS_SCN_USERGROUP' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 27 where screenname = 'IDS_SCN_USERGROUP' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 28 where screenname = 'IDS_SCN_USERMASTER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 29 where screenname = 'IDS_SCN_USERMASTER' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 30 where screenname = 'IDS_SCN_USERRIGHTS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 31 where screenname = 'IDS_SCN_USERRIGHTS' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 32 where screenname = 'IDS_SCN_PROJECTMASTER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 33 where screenname = 'IDS_SCN_PROJECTMASTER' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 34 where screenname = 'IDS_SCN_TEMPLATEWORKFLOW' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 35 where screenname = 'IDS_SCN_TEMPLATEWORKFLOW' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 36 where screenname = 'IDS_SCN_PARSER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 37 where screenname = 'IDS_SCN_PARSER' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 38 where screenname = 'IDS_SCN_BARCODEMASTER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 39 where screenname = 'IDS_SCN_BARCODEMASTER' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 40 where screenname = 'IDS_SCN_PASSWORDPOLICY' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 41 where screenname = 'IDS_SCN_LOGBOOK' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 42 where screenname = 'IDS_SCN_LOGBOOK' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 43 where screenname = 'IDS_SCN_REPORTS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 44 where screenname = 'IDS_SCN_REPORTS' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 45 where screenname = 'IDS_SCN_REPORTVIEVER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 46 where screenname = 'IDS_SCN_REPORTVIEVER' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 47 where screenname = 'IDS_SCN_REPORTMAPPER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 48 where screenname = 'IDS_SCN_REPORTMAPPER' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 49 where screenname = 'IDS_SCN_AUDITTRAILHIS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 50 where screenname = 'IDS_SCN_AUDITTRAILHIS' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsaudittrailconfigmaster set taskname = 'IDS_TSK_SAVEI' where screenname = 'IDS_SCN_MATERIALINVENTORY' and taskname = 'IDS_TSK_SAVE';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_SAVEI' where screenname = 'IDS_SCN_MATERIALINVENTORY' and taskname = 'IDS_TSK_SAVE';

update lsaudittrailconfigmaster set taskname = 'IDS_TSK_SAVEM' where screenname = 'IDS_SCN_MATERIAL' and taskname = 'IDS_TSK_SAVE';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_SAVEM' where screenname = 'IDS_SCN_MATERIAL' and taskname = 'IDS_TSK_SAVE';

update lsaudittrailconfigmaster set taskname = 'IDS_TSK_EDITM' where screenname = 'IDS_SCN_MATERIAL' and taskname = 'IDS_TSK_EDIT';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_EDITM' where screenname = 'IDS_SCN_MATERIAL' and taskname = 'IDS_TSK_EDIT';

update lsaudittrailconfigmaster set screenname = 'IDS_SCN_MATERIALMGMT' where screenname in('IDS_SCN_MATERIALINVENTORY','IDS_SCN_MATERIAL');
update lsaudittrailconfiguration set screenname = 'IDS_SCN_MATERIALMGMT' where screenname in('IDS_SCN_MATERIALINVENTORY','IDS_SCN_MATERIAL');

Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(220,0,'IDS_MDL_INVENTORY',0,'IDS_SCN_SAMPLETYPE','IDS_TSK_EDIT') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(221,0,'IDS_MDL_INVENTORY',0,'IDS_SCN_SAMPLETYPE','IDS_TSK_SAVE') ON CONFLICT(serialno)DO NOTHING;

Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(222,0,'IDS_MDL_INVENTORY',0,'IDS_SCN_SAMPLECATEGORY','IDS_TSK_EDIT') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(223,0,'IDS_MDL_INVENTORY',0,'IDS_SCN_SAMPLECATEGORY','IDS_TSK_SAVE') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(224,0,'IDS_MDL_INVENTORY',0,'IDS_SCN_SAMPLECATEGORY','IDS_TSK_RETIRE') ON CONFLICT(serialno)DO NOTHING;

Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(225,0,'IDS_MDL_INVENTORY',0,'IDS_SCN_SAMPLEMGMT','IDS_TSK_EDITS') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(226,0,'IDS_MDL_INVENTORY',0,'IDS_SCN_SAMPLEMGMT','IDS_TSK_SAVES') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(227,0,'IDS_MDL_INVENTORY',0,'IDS_SCN_SAMPLEMGMT','IDS_TSK_OPENDATE') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(228,0,'IDS_MDL_INVENTORY',0,'IDS_SCN_SAMPLEMGMT','IDS_TSK_RELEASE') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(229,0,'IDS_MDL_INVENTORY',0,'IDS_SCN_SAMPLEMGMT','IDS_TSK_DISPOSE') ON CONFLICT(serialno)DO NOTHING;

update lsaudittrailconfigmaster set ordersequnce = 38 where screenname in('IDS_SCN_MATERIALMGMT');
update lsaudittrailconfigmaster set ordersequnce = 39 where screenname in('IDS_SCN_SAMPLEMGMT');
update lsaudittrailconfigmaster set ordersequnce = 40 where screenname in('IDS_SCN_SAMPLETYPE');
update lsaudittrailconfigmaster set ordersequnce = 41 where screenname in('IDS_SCN_SAMPLECATEGORY');

update lsaudittrailconfiguration set ordersequnce = 38 where screenname in('IDS_SCN_MATERIALMGMT');
update lsaudittrailconfiguration set ordersequnce = 39 where screenname in('IDS_SCN_SAMPLEMGMT');
update lsaudittrailconfiguration set ordersequnce = 40 where screenname in('IDS_SCN_SAMPLETYPE');
update lsaudittrailconfiguration set ordersequnce = 41 where screenname in('IDS_SCN_SAMPLECATEGORY');

update lsaudittrailconfigmaster set ordersequnce = 42 where screenname in('IDS_SCN_EQUIPMENTTYPE');
update lsaudittrailconfiguration set ordersequnce = 42 where screenname in('IDS_SCN_EQUIPMENTTYPE');

update lsaudittrailconfigmaster set ordersequnce = 43 where screenname in('IDS_SCN_EQUIPMENTCATEGORY');
update lsaudittrailconfiguration set ordersequnce = 43 where screenname in('IDS_SCN_EQUIPMENTCATEGORY');

update lsaudittrailconfigmaster set ordersequnce = 44 where screenname in('IDS_SCN_EQUIPMENT');
update lsaudittrailconfiguration set ordersequnce = 44 where screenname in('IDS_SCN_EQUIPMENT');

--delete from lsusergrouprights where displaytopic in('IDS_SCN_MATERIALINVENTORY','IDS_SCN_MATERIAL');
--delete from lsusergrouprightsmaster where displaytopic in('IDS_SCN_MATERIALINVENTORY','IDS_SCN_MATERIAL');

---sheet order

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_REGISTER', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SHEETORDERS' ,4
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_REGISTER' and screenname='IDS_SCN_SHEETORDERS' and usergroupid_usergroupcode = 1); 


INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (268, 'IDS_TSK_REGISTER', 'IDS_MDL_ORDERS', 'IDS_SCN_SHEETORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 4) 
ON CONFLICT (orderno) DO NOTHING;

-------------------------------------------

---Protocol order

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_REGISTER', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLORDERS' ,6
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_REGISTER' and screenname='IDS_SCN_PROTOCOLORDERS' and usergroupid_usergroupcode = 1); 


INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (269, 'IDS_TSK_REGISTER', 'IDS_MDL_ORDERS', 'IDS_SCN_PROTOCOLORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 6) 
ON CONFLICT (orderno) DO NOTHING;

-------------------------------------------
-----------Dashboard
-- INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
-- SELECT 'IDS_TSK_REGISTER', 'IDS_MDL_DASHBOARD', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_DASHBOARD' ,2
-- WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_REGISTER' and screenname='IDS_SCN_DASHBOARD' and usergroupid_usergroupcode = 1); 


-- INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
-- VALUES (270, 'IDS_TSK_REGISTER', 'IDS_MDL_DASHBOARD', 'IDS_SCN_DASHBOARD', '0', 'NA', 'NA', 'NA', '0,0,0', 2) 
-- ON CONFLICT (orderno) DO NOTHING;

-- INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
-- SELECT 'IDS_TSK_REGISTER', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_DASHBOARD' ,2
-- WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_REGISTER' and screenname='IDS_SCN_DASHBOARD' and usergroupid_usergroupcode = 1); 


-- INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
-- VALUES (271, 'IDS_TSK_REGISTER', 'IDS_MDL_ORDERS', 'IDS_SCN_DASHBOARD', '0', 'NA', 'NA', 'NA', '0,0,0', 2) 
-- ON CONFLICT (orderno) DO NOTHING;
--------------------------------------------

update lsnotification set notificationpath='/material' where notificationpath='/materialinventory';

-----------Sheet Order
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_EXPORTJSON', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SHEETORDERS' ,4
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_EXPORTJSON' and screenname='IDS_SCN_SHEETORDERS' and usergroupid_usergroupcode = 1);
 
INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (270, 'IDS_TSK_EXPORTJSON', 'IDS_MDL_ORDERS', 'IDS_SCN_SHEETORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 4) 
ON CONFLICT (orderno) DO NOTHING;
 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_EXPORTEXCEL', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SHEETORDERS' ,4
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_EXPORTEXCEL' and screenname='IDS_SCN_SHEETORDERS' and usergroupid_usergroupcode = 1);
 
INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (271, 'IDS_TSK_EXPORTEXCEL', 'IDS_MDL_ORDERS', 'IDS_SCN_SHEETORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 4) 
ON CONFLICT (orderno) DO NOTHING;


INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_REGISTER', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SHEETORDERS' ,4
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_REGISTER' and screenname='IDS_SCN_SHEETORDERS' and usergroupid_usergroupcode = 1);
 
 
INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (272, 'IDS_TSK_REGISTER', 'IDS_MDL_ORDERS', 'IDS_SCN_SHEETORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 4) 
ON CONFLICT (orderno) DO NOTHING;
 
-------------------------------------------
 
---Protocol order
 
INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_REGISTER', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROTOCOLORDERS' ,6
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_REGISTER' and screenname='IDS_SCN_PROTOCOLORDERS' and usergroupid_usergroupcode = 1);
 
 
INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (273, 'IDS_TSK_REGISTER', 'IDS_MDL_ORDERS', 'IDS_SCN_PROTOCOLORDERS', '0', 'NA', 'NA', 'NA', '0,0,0', 6) 
ON CONFLICT (orderno) DO NOTHING;
 
-----------------
----------------------
update lsusergrouprights set screenname = 'IDS_SCN_MATERIALTYPEPARAMS' where screenname in
('IDS_SCN_MATERIALCATEGORY','IDS_SCN_GRADEMASTER','IDS_SCN_SUPPLIER','IDS_SCN_STORAGELOCATION','IDS_SCN_SECTIONMASTER',
'IDS_SCN_MANUFACTURER','IDS_SCN_UNITMASTER','IDS_SCN_SAMPLETYPE','IDS_SCN_SAMPLECAT');

update lsusergrouprightsmaster set screenname = 'IDS_SCN_MATERIALTYPEPARAMS' where screenname in
('IDS_SCN_MATERIALCATEGORY','IDS_SCN_GRADEMASTER','IDS_SCN_SUPPLIER','IDS_SCN_STORAGELOCATION','IDS_SCN_SECTIONMASTER',
'IDS_SCN_MANUFACTURER','IDS_SCN_UNITMASTER','IDS_SCN_SAMPLETYPE','IDS_SCN_SAMPLECAT');

update lsusergrouprightsmaster set sequenceorder = 0 where  modulename = 'IDS_MDL_GENRAL';

update lsusergrouprightsmaster set sequenceorder = 2 
where screenname = 'IDS_SCN_DASHBOARD' and modulename = 'IDS_MDL_DASHBOARD' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 4 
where screenname = 'IDS_SCN_SHEETORDERS' and modulename = 'IDS_MDL_ORDERS' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 6 
where screenname = 'IDS_SCN_PROTOCOLORDERS' and modulename = 'IDS_MDL_ORDERS' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 8 
where screenname = 'IDS_SCN_UNLOCKORDERS' and modulename = 'IDS_MDL_ORDERS' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 10 
where screenname = 'IDS_SCN_SHEETTEMPLATE' and modulename = 'IDS_MDL_TEMPLATES' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 12 
where screenname = 'IDS_SCN_PROTOCOLTEMPLATE' and modulename = 'IDS_MDL_TEMPLATES' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 14 
where screenname = 'IDS_SCN_TEMPLATEMAPPING' and modulename = 'IDS_MDL_TEMPLATES' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 16 
where screenname = 'IDS_SCN_MATERIALMGMT' and modulename = 'IDS_MDL_INVENTORY' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 18 
where screenname = 'IDS_SCN_SAMPLEMGMT' and modulename = 'IDS_MDL_INVENTORY' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 19 
where screenname = 'IDS_SCN_MATERIALTYPEPARAMS' and modulename = 'IDS_MDL_INVENTORY' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 21 
where screenname = 'IDS_SCN_EQUIPMENTMASTER' and modulename = 'IDS_MDL_INVENTORY' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 22 
where screenname = 'IDS_SCN_EQUIPMENT' and modulename = 'IDS_MDL_INVENTORY';

update lsusergrouprightsmaster set sequenceorder = 24 
where screenname = 'IDS_SCN_USERGROUP' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 26 
where screenname = 'IDS_SCN_USERMASTER' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 27 
where screenname = 'IDS_SCN_USERRIGHTS' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 28 
where screenname = 'IDS_SCN_PROJECTMASTER' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 29 
where screenname = 'IDS_SCN_PROJECTTEAM' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 30 
where screenname = 'IDS_SCN_TASKMASTER' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 31 
where screenname = 'IDS_SCN_ORDERWORKLOW' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 32 
where screenname = 'IDS_SCN_TEMPLATEWORKFLOW' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 34 
where screenname = 'IDS_SCN_BARCODEMASTER' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 36 
where screenname = 'IDS_SCN_PARSER' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 37 
where screenname = 'IDS_SCN_SITEMASTER' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 38 
where screenname = 'IDS_SCN_DOMAIN' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 39 
where screenname = 'IDS_SCN_ACTIVEUSER' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 40 
where screenname = 'IDS_SCN_PASSWORDPOLICY' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 42 
where screenname = 'IDS_SCN_LOGBOOK' and modulename = 'IDS_MDL_LOGBOOK' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 44 
where screenname = 'IDS_SCN_REPORTS' and modulename = 'IDS_MDL_REPORTS' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 46 
where screenname = 'IDS_SCN_REPORTVIEVER' and modulename = 'IDS_MDL_REPORTS' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 48 
where screenname = 'IDS_SCN_REPORTMAPPER' and modulename = 'IDS_MDL_REPORTS' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 50 
where screenname = 'IDS_SCN_AUDITTRAILHIS' and modulename = 'IDS_MDL_AUDITTRAIL' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 51 
where screenname = 'IDS_SCN_CFRSETTINGS' and modulename = 'IDS_MDL_AUDITTRAIL' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 52 
where screenname = 'IDS_SCN_AUDITTRAILCONFIG' and modulename = 'IDS_MDL_AUDITTRAIL' and displaytopic != 'IDS_TSK_SCREENVIEW';
-------------------------------------------------------------------------

update lsusergrouprightsmaster set sequenceorder = 0 where  modulename = 'IDS_MDL_GENRAL';
update lsusergrouprights set sequenceorder = 0 where  modulename = 'IDS_MDL_GENRAL';

update lsusergrouprights set sequenceorder = 2 
where screenname = 'IDS_SCN_DASHBOARD' and modulename = 'IDS_MDL_DASHBOARD' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 4 
where screenname = 'IDS_SCN_SHEETORDERS' and modulename = 'IDS_MDL_ORDERS' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 6 
where screenname = 'IDS_SCN_PROTOCOLORDERS' and modulename = 'IDS_MDL_ORDERS' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 8 
where screenname = 'IDS_SCN_UNLOCKORDERS' and modulename = 'IDS_MDL_ORDERS' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 10 
where screenname = 'IDS_SCN_SHEETTEMPLATE' and modulename = 'IDS_MDL_TEMPLATES' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 12 
where screenname = 'IDS_SCN_PROTOCOLTEMPLATE' and modulename = 'IDS_MDL_TEMPLATES' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 14 
where screenname = 'IDS_SCN_TEMPLATEMAPPING' and modulename = 'IDS_MDL_TEMPLATES' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 16 
where screenname = 'IDS_SCN_MATERIALMGMT' and modulename = 'IDS_MDL_INVENTORY' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 18 
where screenname = 'IDS_SCN_SAMPLEMGMT' and modulename = 'IDS_MDL_INVENTORY' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 19 
where screenname = 'IDS_SCN_MATERIALTYPEPARAMS' and modulename = 'IDS_MDL_INVENTORY' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 21 
where screenname = 'IDS_SCN_EQUIPMENTMASTER' and modulename = 'IDS_MDL_INVENTORY' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 22 
where screenname = 'IDS_SCN_EQUIPMENT' and modulename = 'IDS_MDL_INVENTORY';

update lsusergrouprights set sequenceorder = 24 
where screenname = 'IDS_SCN_USERGROUP' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 26 
where screenname = 'IDS_SCN_USERMASTER' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 27 
where screenname = 'IDS_SCN_USERRIGHTS' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 28 
where screenname = 'IDS_SCN_PROJECTMASTER' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 29 
where screenname = 'IDS_SCN_PROJECTTEAM' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 30 
where screenname = 'IDS_SCN_TASKMASTER' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 31 
where screenname = 'IDS_SCN_ORDERWORKLOW' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 32 
where screenname = 'IDS_SCN_TEMPLATEWORKFLOW' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 34 
where screenname = 'IDS_SCN_BARCODEMASTER' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 36 
where screenname = 'IDS_SCN_PARSER' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 37 
where screenname = 'IDS_SCN_SITEMASTER' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 38 
where screenname = 'IDS_SCN_DOMAIN' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 39 
where screenname = 'IDS_SCN_ACTIVEUSER' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 40 
where screenname = 'IDS_SCN_PASSWORDPOLICY' and modulename = 'IDS_MDL_SETUP' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 42 
where screenname = 'IDS_SCN_LOGBOOK' and modulename = 'IDS_MDL_LOGBOOK' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 44 
where screenname = 'IDS_SCN_REPORTS' and modulename = 'IDS_MDL_REPORTS' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 46 
where screenname = 'IDS_SCN_REPORTVIEVER' and modulename = 'IDS_MDL_REPORTS' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 48 
where screenname = 'IDS_SCN_REPORTMAPPER' and modulename = 'IDS_MDL_REPORTS' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 50 
where screenname = 'IDS_SCN_AUDITTRAILHIS' and modulename = 'IDS_MDL_AUDITTRAIL' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 51 
where screenname = 'IDS_SCN_CFRSETTINGS' and modulename = 'IDS_MDL_AUDITTRAIL' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 52 
where screenname = 'IDS_SCN_AUDITTRAILCONFIG' and modulename = 'IDS_MDL_AUDITTRAIL' and displaytopic != 'IDS_TSK_SCREENVIEW';


update lsusergrouprights set sequenceorder = 1 
where screenname = 'IDS_SCN_DASHBOARD' and modulename = 'IDS_MDL_DASHBOARD' and displaytopic = 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 3 
where screenname = 'IDS_SCN_SHEETORDERS' and modulename = 'IDS_MDL_ORDERS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 5 
where screenname = 'IDS_SCN_PROTOCOLORDERS' and modulename = 'IDS_MDL_ORDERS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 7 
where screenname = 'IDS_SCN_UNLOCKORDERS' and modulename = 'IDS_MDL_ORDERS' and displaytopic = 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 9 
where screenname = 'IDS_SCN_SHEETTEMPLATE' and modulename = 'IDS_MDL_TEMPLATES' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 11 
where screenname = 'IDS_SCN_PROTOCOLTEMPLATE' and modulename = 'IDS_MDL_TEMPLATES' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 13
where screenname = 'IDS_SCN_TEMPLATEMAPPING' and modulename = 'IDS_MDL_TEMPLATES' and displaytopic = 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 15 
where screenname = 'IDS_SCN_MATERIALMGMT' and modulename = 'IDS_MDL_INVENTORY' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 17 
where screenname = 'IDS_SCN_SAMPLEMGMT' and modulename = 'IDS_MDL_INVENTORY' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 20 
where screenname = 'IDS_SCN_EQUIPMENTMASTER' and modulename = 'IDS_MDL_INVENTORY' and displaytopic = 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 23 
where screenname = 'IDS_SCN_USERGROUP' and modulename = 'IDS_MDL_SETUP' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 25 
where screenname = 'IDS_SCN_USERMASTER' and modulename = 'IDS_MDL_SETUP' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 33 
where screenname = 'IDS_SCN_BARCODEMASTER' and modulename = 'IDS_MDL_SETUP' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 35 
where screenname = 'IDS_SCN_PARSER' and modulename = 'IDS_MDL_SETUP' and displaytopic = 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 41 
where screenname = 'IDS_SCN_LOGBOOK' and modulename = 'IDS_MDL_LOGBOOK' and displaytopic = 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 43 
where screenname = 'IDS_SCN_REPORTS' and modulename = 'IDS_MDL_REPORTS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 45 
where screenname = 'IDS_SCN_REPORTVIEVER' and modulename = 'IDS_MDL_REPORTS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 47 
where screenname = 'IDS_SCN_REPORTMAPPER' and modulename = 'IDS_MDL_REPORTS' and displaytopic = 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 49 
where screenname = 'IDS_SCN_AUDITTRAILHIS' and modulename = 'IDS_MDL_AUDITTRAIL' and displaytopic = 'IDS_TSK_SCREENVIEW';
-------------------------------------------

update lsusergrouprightsmaster set sequenceorder = 1 
where screenname = 'IDS_SCN_DASHBOARD' and modulename = 'IDS_MDL_DASHBOARD' and displaytopic = 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 3 
where screenname = 'IDS_SCN_SHEETORDERS' and modulename = 'IDS_MDL_ORDERS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 5 
where screenname = 'IDS_SCN_PROTOCOLORDERS' and modulename = 'IDS_MDL_ORDERS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 7 
where screenname = 'IDS_SCN_UNLOCKORDERS' and modulename = 'IDS_MDL_ORDERS' and displaytopic = 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 9 
where screenname = 'IDS_SCN_SHEETTEMPLATE' and modulename = 'IDS_MDL_TEMPLATES' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 11 
where screenname = 'IDS_SCN_PROTOCOLTEMPLATE' and modulename = 'IDS_MDL_TEMPLATES' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 13
where screenname = 'IDS_SCN_TEMPLATEMAPPING' and modulename = 'IDS_MDL_TEMPLATES' and displaytopic = 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 15 
where screenname = 'IDS_SCN_MATERIALMGMT' and modulename = 'IDS_MDL_INVENTORY' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 17 
where screenname = 'IDS_SCN_SAMPLEMGMT' and modulename = 'IDS_MDL_INVENTORY' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 20 
where screenname = 'IDS_SCN_EQUIPMENTMASTER' and modulename = 'IDS_MDL_INVENTORY' and displaytopic = 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 23 
where screenname = 'IDS_SCN_USERGROUP' and modulename = 'IDS_MDL_SETUP' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 25 
where screenname = 'IDS_SCN_USERMASTER' and modulename = 'IDS_MDL_SETUP' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 33 
where screenname = 'IDS_SCN_BARCODEMASTER' and modulename = 'IDS_MDL_SETUP' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 35 
where screenname = 'IDS_SCN_PARSER' and modulename = 'IDS_MDL_SETUP' and displaytopic = 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 41 
where screenname = 'IDS_SCN_LOGBOOK' and modulename = 'IDS_MDL_LOGBOOK' and displaytopic = 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 43 
where screenname = 'IDS_SCN_REPORTS' and modulename = 'IDS_MDL_REPORTS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 45 
where screenname = 'IDS_SCN_REPORTVIEVER' and modulename = 'IDS_MDL_REPORTS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 47 
where screenname = 'IDS_SCN_REPORTMAPPER' and modulename = 'IDS_MDL_REPORTS' and displaytopic = 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 49 
where screenname = 'IDS_SCN_AUDITTRAILHIS' and modulename = 'IDS_MDL_AUDITTRAIL' and displaytopic = 'IDS_TSK_SCREENVIEW';
-----------------------------------------------------------------
update lsusergrouprightsmaster set screenname = 'IDS_SCN_MATERIALMGMT' where screenname in ('IDS_SCN_MATERIALINVENTORY','IDS_SCN_MATERIAL');
update lsusergrouprights set screenname = 'IDS_SCN_MATERIALMGMT' where screenname in ('IDS_SCN_MATERIALINVENTORY','IDS_SCN_MATERIAL');
-----------------------------------------

update lsusergrouprightsmaster set sequenceorder = 20 where screenname = 'IDS_SCN_MATERIALTYPEPARAMS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 21 where screenname = 'IDS_SCN_MATERIALTYPEPARAMS' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 22 where screenname = 'IDS_SCN_EQUIPMENTMASTER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 23 where screenname = 'IDS_SCN_EQUIPMENTMASTER' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 24 where screenname = 'IDS_SCN_EQUIPMENT' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 25 where screenname = 'IDS_SCN_EQUIPMENT' and displaytopic != 'IDS_TSK_SCREENVIEW';

---------
--setup
update lsusergrouprightsmaster set sequenceorder = 26 where screenname = 'IDS_SCN_USERGROUP' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 27 where screenname = 'IDS_SCN_USERGROUP' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 28 where screenname = 'IDS_SCN_USERMASTER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 29 where screenname = 'IDS_SCN_USERMASTER' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 30 where screenname = 'IDS_SCN_USERRIGHTS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 31 where screenname = 'IDS_SCN_USERRIGHTS' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 32 where screenname = 'IDS_SCN_PROJECTMASTER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 33 where screenname = 'IDS_SCN_PROJECTMASTER' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 34 where screenname = 'IDS_SCN_TEMPLATEWORKFLOW' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 35 where screenname = 'IDS_SCN_TEMPLATEWORKFLOW' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 36 where screenname = 'IDS_SCN_PARSER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 37 where screenname = 'IDS_SCN_PARSER' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 38 where screenname = 'IDS_SCN_BARCODEMASTER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 39 where screenname = 'IDS_SCN_BARCODEMASTER' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 40 where screenname = 'IDS_SCN_PASSWORDPOLICY' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 41 where screenname = 'IDS_SCN_LOGBOOK' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 42 where screenname = 'IDS_SCN_LOGBOOK' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 43 where screenname = 'IDS_SCN_REPORTS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 44 where screenname = 'IDS_SCN_REPORTS' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 45 where screenname = 'IDS_SCN_REPORTVIEVER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 46 where screenname = 'IDS_SCN_REPORTVIEVER' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 47 where screenname = 'IDS_SCN_REPORTMAPPER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 48 where screenname = 'IDS_SCN_REPORTMAPPER' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 49 where screenname = 'IDS_SCN_AUDITTRAILHIS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 50 where screenname = 'IDS_SCN_AUDITTRAILHIS' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 20 where screenname = 'IDS_SCN_MATERIALTYPEPARAMS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 21 where screenname = 'IDS_SCN_MATERIALTYPEPARAMS' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 22 where screenname = 'IDS_SCN_EQUIPMENTMASTER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 23 where screenname = 'IDS_SCN_EQUIPMENTMASTER' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 24 where screenname = 'IDS_SCN_EQUIPMENT' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 25 where screenname = 'IDS_SCN_EQUIPMENT' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 26 where screenname = 'IDS_SCN_USERGROUP' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 27 where screenname = 'IDS_SCN_USERGROUP' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 28 where screenname = 'IDS_SCN_USERMASTER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 29 where screenname = 'IDS_SCN_USERMASTER' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 30 where screenname = 'IDS_SCN_USERRIGHTS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 31 where screenname = 'IDS_SCN_USERRIGHTS' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 32 where screenname = 'IDS_SCN_PROJECTMASTER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 33 where screenname = 'IDS_SCN_PROJECTMASTER' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 34 where screenname = 'IDS_SCN_TEMPLATEWORKFLOW' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 35 where screenname = 'IDS_SCN_TEMPLATEWORKFLOW' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 36 where screenname = 'IDS_SCN_PARSER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 37 where screenname = 'IDS_SCN_PARSER' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 38 where screenname = 'IDS_SCN_BARCODEMASTER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 39 where screenname = 'IDS_SCN_BARCODEMASTER' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 40 where screenname = 'IDS_SCN_PASSWORDPOLICY' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 41 where screenname = 'IDS_SCN_LOGBOOK' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 42 where screenname = 'IDS_SCN_LOGBOOK' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 43 where screenname = 'IDS_SCN_REPORTS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 44 where screenname = 'IDS_SCN_REPORTS' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 45 where screenname = 'IDS_SCN_REPORTVIEVER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 46 where screenname = 'IDS_SCN_REPORTVIEVER' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 47 where screenname = 'IDS_SCN_REPORTMAPPER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 48 where screenname = 'IDS_SCN_REPORTMAPPER' and displaytopic != 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 49 where screenname = 'IDS_SCN_AUDITTRAILHIS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 50 where screenname = 'IDS_SCN_AUDITTRAILHIS' and displaytopic != 'IDS_TSK_SCREENVIEW';
-----------------------------------------------------------------
-------------------------------------------------
-- update lsusergrouprightsmaster set screenname = 'IDS_SCN_PROJECTMASTER' where screenname in
-- ('IDS_SCN_PROJECTTEAM','IDS_SCN_TASKMASTER','IDS_SCN_PROJECTMASTER');
-- update lsusergrouprights set screenname = 'IDS_SCN_PROJECTMASTER' where screenname in
-- ('IDS_SCN_PROJECTTEAM','IDS_SCN_TASKMASTER','IDS_SCN_PROJECTMASTER');

update lsusergrouprightsmaster set screenname = 'IDS_SCN_TEMPLATEWORKFLOW' where screenname in
('IDS_SCN_ORDERWORKLOW','IDS_SCN_TEMPLATEWORKFLOW');
update lsusergrouprights set screenname = 'IDS_SCN_TEMPLATEWORKFLOW' where screenname in
('IDS_SCN_ORDERWORKLOW','IDS_SCN_TEMPLATEWORKFLOW');
------------------------------------------
update lsusergrouprightsmaster set screenname = 'IDS_SCN_MATERIALMGMT' where screenname in ('IDS_SCN_MATERIALINVENTORY','IDS_SCN_MATERIAL');
update lsusergrouprights set screenname = 'IDS_SCN_MATERIALMGMT' where screenname in ('IDS_SCN_MATERIALINVENTORY','IDS_SCN_MATERIAL');
----------------------
update lsusergrouprightsmaster set sequenceorder = 20 where screenname = 'IDS_SCN_MATERIALTYPEPARAMS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 21 where screenname = 'IDS_SCN_MATERIALTYPEPARAMS' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 22 where screenname = 'IDS_SCN_EQUIPMENTMASTER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 23 where screenname = 'IDS_SCN_EQUIPMENTMASTER' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 24 where screenname = 'IDS_SCN_EQUIPMENT' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 25 where screenname = 'IDS_SCN_EQUIPMENT' and displaytopic != 'IDS_TSK_SCREENVIEW';
---------
--setup
update lsusergrouprightsmaster set sequenceorder = 26 where screenname = 'IDS_SCN_USERGROUP' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 27 where screenname = 'IDS_SCN_USERGROUP' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 28 where screenname = 'IDS_SCN_USERMASTER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 29 where screenname = 'IDS_SCN_USERMASTER' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 30 where screenname = 'IDS_SCN_USERRIGHTS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 31 where screenname = 'IDS_SCN_USERRIGHTS' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 32 where screenname = 'IDS_SCN_PROJECTMASTER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 33 where screenname = 'IDS_SCN_PROJECTMASTER' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 34 where screenname = 'IDS_SCN_TEMPLATEWORKFLOW' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 35 where screenname = 'IDS_SCN_TEMPLATEWORKFLOW' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 36 where screenname = 'IDS_SCN_PARSER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 37 where screenname = 'IDS_SCN_PARSER' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 38 where screenname = 'IDS_SCN_BARCODEMASTER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 39 where screenname = 'IDS_SCN_BARCODEMASTER' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 40 where screenname = 'IDS_SCN_PASSWORDPOLICY' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 41 where screenname = 'IDS_SCN_LOGBOOK' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 42 where screenname = 'IDS_SCN_LOGBOOK' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 43 where screenname = 'IDS_SCN_REPORTS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 44 where screenname = 'IDS_SCN_REPORTS' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 45 where screenname = 'IDS_SCN_REPORTVIEVER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 46 where screenname = 'IDS_SCN_REPORTVIEVER' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 47 where screenname = 'IDS_SCN_REPORTMAPPER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 48 where screenname = 'IDS_SCN_REPORTMAPPER' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprightsmaster set sequenceorder = 49 where screenname = 'IDS_SCN_AUDITTRAILHIS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprightsmaster set sequenceorder = 50 where screenname = 'IDS_SCN_AUDITTRAILHIS' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 20 where screenname = 'IDS_SCN_MATERIALTYPEPARAMS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 21 where screenname = 'IDS_SCN_MATERIALTYPEPARAMS' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 22 where screenname = 'IDS_SCN_EQUIPMENTMASTER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 23 where screenname = 'IDS_SCN_EQUIPMENTMASTER' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 24 where screenname = 'IDS_SCN_EQUIPMENT' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 25 where screenname = 'IDS_SCN_EQUIPMENT' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 26 where screenname = 'IDS_SCN_USERGROUP' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 27 where screenname = 'IDS_SCN_USERGROUP' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 28 where screenname = 'IDS_SCN_USERMASTER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 29 where screenname = 'IDS_SCN_USERMASTER' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 30 where screenname = 'IDS_SCN_USERRIGHTS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 31 where screenname = 'IDS_SCN_USERRIGHTS' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 32 where screenname = 'IDS_SCN_PROJECTMASTER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 33 where screenname = 'IDS_SCN_PROJECTMASTER' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 34 where screenname = 'IDS_SCN_TEMPLATEWORKFLOW' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 35 where screenname = 'IDS_SCN_TEMPLATEWORKFLOW' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 36 where screenname = 'IDS_SCN_PARSER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 37 where screenname = 'IDS_SCN_PARSER' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 38 where screenname = 'IDS_SCN_BARCODEMASTER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 39 where screenname = 'IDS_SCN_BARCODEMASTER' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 40 where screenname = 'IDS_SCN_PASSWORDPOLICY' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 41 where screenname = 'IDS_SCN_LOGBOOK' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 42 where screenname = 'IDS_SCN_LOGBOOK' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 43 where screenname = 'IDS_SCN_REPORTS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 44 where screenname = 'IDS_SCN_REPORTS' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 45 where screenname = 'IDS_SCN_REPORTVIEVER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 46 where screenname = 'IDS_SCN_REPORTVIEVER' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 47 where screenname = 'IDS_SCN_REPORTMAPPER' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 48 where screenname = 'IDS_SCN_REPORTMAPPER' and displaytopic != 'IDS_TSK_SCREENVIEW';

update lsusergrouprights set sequenceorder = 49 where screenname = 'IDS_SCN_AUDITTRAILHIS' and displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set sequenceorder = 50 where screenname = 'IDS_SCN_AUDITTRAILHIS' and displaytopic != 'IDS_TSK_SCREENVIEW';


update lsusergrouprightsmaster set screenname = 'IDS_SCN_MATERIALMGMT' where sequenceorder in (15,16);
update lsusergrouprightsmaster set screenname = 'IDS_SCN_SAMPLEMGMT' where sequenceorder in (17,18);
update lsusergrouprightsmaster set screenname = 'IDS_SCN_MATERIALTYPEPARAMS' where sequenceorder in (21);

update lsusergrouprights set screenname = 'IDS_SCN_MATERIALMGMT' where sequenceorder in (15,16);
update lsusergrouprights set screenname = 'IDS_SCN_SAMPLEMGMT' where sequenceorder in (17,18);
update lsusergrouprights set screenname = 'IDS_SCN_MATERIALTYPEPARAMS' where sequenceorder in (21);

update LSusergrouprights set sallow = '1' where sallow = '0' and usergroupid_usergroupcode = 1;
update LSusergrouprights set screate = '1' where screate = '0' and usergroupid_usergroupcode = 1;
update LSusergrouprights set sedit = '1' where sedit = '0' and usergroupid_usergroupcode = 1;
update LSusergrouprights set sdelete = '1' where sdelete = '0' and usergroupid_usergroupcode = 1;

update screenmaster set screenname = 'IDS_SCN_INVENTORY' where screencode = 1;
update sequencetable set seperator='_' where seperator Is Null;

delete from lsfields where level04code = 'G21';

ALTER TABLE IF EXISTS lsautoregister ALTER COLUMN regcode TYPE BIGINT;

ALTER TABLE IF EXISTS elnmaterialInventory ALTER COLUMN jsondata TYPE character varying USING jsondata::text;
ALTER TABLE sample ALTER COLUMN quantity TYPE DECIMAL(10,2);


----printer----
WITH next_serial AS (
    SELECT COALESCE(MAX(serialno), 0) + 1 AS new_serialno
    FROM lspreferences
)
INSERT INTO lspreferences (serialno, tasksettings, valueencrypted)
SELECT new_serialno, 'printerhost', 'http://192.168.0.81:5004'
FROM next_serial
WHERE NOT EXISTS (
    SELECT 1
    FROM lspreferences
    WHERE tasksettings = 'printerhost'
);


delete from lsusergrouprights where displaytopic in ('IDS_SCN_SHEETORDERS','IDS_SCN_PROTOCOLORDERS');
delete from lsusergrouprightsmaster where displaytopic in ('IDS_SCN_SHEETORDERS','IDS_SCN_PROTOCOLORDERS');

delete from lsusergrouprightsmaster where displaytopic = 'IDS_SCN_SECTIONMASTER';
delete from lsusergrouprights where displaytopic = 'IDS_SCN_SECTIONMASTER';

ALTER TABLE IF Exists lsusergrouprightsmaster ADD Column IF NOT EXISTS tasksequence INTEGER;
ALTER TABLE IF Exists lsusergrouprights ADD Column IF NOT EXISTS tasksequence INTEGER;

update lsusergrouprightsmaster set tasksequence = '0' where displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set tasksequence = '0' where displaytopic = 'IDS_TSK_SCREENVIEW';

INSERT INTO lsusergrouprightsmaster (
    orderno, displaytopic, modulename, screenname,
    sallow, screate, sdelete, sedit, status,
    sequenceorder, tasksequence
)
SELECT
    COALESCE((SELECT MAX(orderno) + 1 FROM lsusergrouprightsmaster), 1),
    'IDS_TSK_CLONEORDER',
    'IDS_MDL_ORDERS',
    'IDS_SCN_PROTOCOLORDERS',
    '0', 'NA', 'NA', 'NA', '0,0,0',
    6, 9
WHERE NOT EXISTS (
    SELECT 1
    FROM lsusergrouprightsmaster
    WHERE displaytopic = 'IDS_TSK_CLONEORDER'
      AND screenname = 'IDS_SCN_PROTOCOLORDERS'
      AND modulename = 'IDS_MDL_ORDERS'
      AND sequenceorder = 6
);

UPDATE lsusergrouprightsmaster AS l
SET tasksequence = v.tasksequence
FROM (
    VALUES
        ('IDS_SCN_GENRAL', 'IDS_TSK_GLOBALSEARCH', 1),
        ('IDS_SCN_GENRAL', 'IDS_TSK_SWITCHROLE', 2),
        ('IDS_SCN_GENRAL', 'IDS_TSK_SWITCHSITE', 3),

        ('IDS_SCN_DASHBOARD', 'IDS_TSK_ACTIVITIES', 1),
        ('IDS_SCN_DASHBOARD', 'IDS_TSK_ACTIVIWIDGET', 2),
        ('IDS_SCN_DASHBOARD', 'IDS_TSK_ORDEROVERVIEW', 3),
        ('IDS_SCN_DASHBOARD', 'IDS_TSK_TEMPLATEOVERVIEW', 4),
        ('IDS_SCN_DASHBOARD', 'IDS_TSK_REGISTER', 5),

        ('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_AUTOREGORDER'	,1),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_CANCELORDER'	,2),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_COPYLINK'	,3),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_DUEDATE'	,4),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_ELNTASKORDER'	,5),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_EXPORTEXCEL'	,6),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_EXPORTJSON'	,7),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_FOLDERCREATION'	,8),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_FOLDERDEL'	,9),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_MANAGEEXCEL'	,10),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_MOVEORDERS'	,11),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_NEWFOLDER'	,12),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_ORDERSHAREDBYME'	,13),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_ORDERSHAREDTOME'	,14),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_PROJECTTEAM'	,15),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_REGISTER'	,16),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_RESEARCHACTIVITY'	,17),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_SHARE'	,18),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_SHEETEVALUATION'	,20),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_SHEETORDEREXPORT'	,21),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_SHOWVERSION'	,22),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_TRANHISTORY'	,23),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_UPLOADSHEETORDER'	,24),

		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ADDEDITOR',	1),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ADDREFF',	2),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ADDRESEQUIPMENT',	3),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ADDRESMATERIAL',	4),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ADDRESULT',	5),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ADDSEC',	6),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ADDSTEP',	7),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_AUTOREGORDER',	8),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_CLONEORDER',	9),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_CANCELORDER',	10),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_COPYLINK',	11),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_DUEDATE',	12),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_DYNAMICPROTOCOL',	13),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ELNPROTOCOL',	14),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_EXPORTEXCEL',	15),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_EXPORTJSON',	16),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_FOLDERCREATIONPROTOCOL',	17),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_FOLDERDEL',	18),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_MOVEORDERSPROTOCOL',	19),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ORDERSHAREDBYMEPROTOCOL',	20),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ORDERSHAREDTOMEPROTOCOL',	21),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_PROJECTTEAM',	22),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_REGISTER',	23),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_SHARE',	24),
		('IDS_SCN_PROTOCOLORDERS' ,	'IDS_TSK_NEWFOLDER',25),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_SHOWVERSION',	26),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_TRANHISTORY',	27),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_UPLOADPROTOCOLORDER',	28),

		('IDS_SCN_UNLOCKORDERS','IDS_SCN_UNLOCKORDERS',	1),

		('IDS_SCN_SHEETTEMPLATE'	, 'IDS_SCN_SHEETTEMPLATE'	,1),
		('IDS_SCN_SHEETTEMPLATE'	, 'IDS_TSK_NEW'	,2),
		('IDS_SCN_SHEETTEMPLATE'	, 'IDS_TSK_RETIRE'	,3),
		('IDS_SCN_SHEETTEMPLATE'	, 'IDS_TSK_SHARE'	,4),
		('IDS_SCN_SHEETTEMPLATE'	, 'IDS_TSK_SHEETTEMPEXPORT'	,5),
		('IDS_SCN_SHEETTEMPLATE'	, 'IDS_TSK_SHOWVERSION'	,6),
		('IDS_SCN_SHEETTEMPLATE'	, 'IDS_TSK_TEMPLATESHAREDBYME'	,7),
		('IDS_SCN_SHEETTEMPLATE'	, 'IDS_TSK_TEMPLATESHAREDTOME'	,8),
		('IDS_SCN_SHEETTEMPLATE'	, 'IDS_TSK_TRANHISTORY'	,9),
		('IDS_SCN_SHEETTEMPLATE'	, 'IDS_TSK_VISIBLITY' ,10),
		('IDS_SCN_SHEETTEMPLATE'	, 'IDS_TSK_EDIT',11),

		('IDS_SCN_PROTOCOLTEMPLATE' ,	'IDS_SCN_PROTOCOLTEMPLATE'	,1),
		('IDS_SCN_PROTOCOLTEMPLATE' ,	'IDS_TSK_ADDEDITOR'	,2),
		('IDS_SCN_PROTOCOLTEMPLATE' ,	'IDS_TSK_ADDREFF'	,3),
		('IDS_SCN_PROTOCOLTEMPLATE' ,	'IDS_TSK_ADDRESEQUIPMENT'	,4),
		('IDS_SCN_PROTOCOLTEMPLATE' ,	'IDS_TSK_ADDRESMATERIAL'	,5),
		('IDS_SCN_PROTOCOLTEMPLATE' ,	'IDS_TSK_ADDRESULT'	,6),
		('IDS_SCN_PROTOCOLTEMPLATE' ,	'IDS_TSK_ADDSEC'	,7),
		('IDS_SCN_PROTOCOLTEMPLATE' ,	'IDS_TSK_ADDSTEP'	,8),
		('IDS_SCN_PROTOCOLTEMPLATE' ,	'IDS_TSK_EXPORTJSON'	,9),
		('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_EXPORTPDF'	,10),
		('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_IMPORTJSON'	,11),
		('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_NEW'	,12),
		('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_NEWSTEP'	,13),
		('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_PROTOCOLTEMPSHAREBYME'	,14),
		('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_PROTOCOLTEMPSHARETOME'	,15),
		('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_RETIRE'	,16),
		('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_SHARE'	,17),
		('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_SHOWVERSION'	,18),
		('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_TRANHISTORY'	,19),
		('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_VISIBLITY'	,20),

		('IDS_SCN_TEMPLATEMAPPING',	'IDS_TSK_PROTOCOL'	,1),
		('IDS_SCN_TEMPLATEMAPPING',	'IDS_TSK_SHEET'	,2),

		('IDS_SCN_MATERIALMGMT'	, 'IDS_TSK_ADDATTACH',	1),
		('IDS_SCN_MATERIALMGMT'	, 'IDS_TSK_ADDCAT',	2),
		('IDS_SCN_MATERIALMGMT'	, 'IDS_TSK_ADDHYPLINK',	3),
		('IDS_SCN_MATERIALMGMT'	, 'IDS_TSK_ADDMAT',	4),
		('IDS_SCN_MATERIALMGMT'	, 'IDS_TSK_ADDSTOCK',	5),
		('IDS_SCN_MATERIALMGMT'	, 'IDS_TSK_ADDTYP',	6),
		('IDS_SCN_MATERIALMGMT'	, 'IDS_TSK_ASSIGNPROJ',	7),
		('IDS_SCN_MATERIALMGMT'	, 'IDS_TSK_CFINWARD',	8),
		('IDS_SCN_MATERIALMGMT'	, 'IDS_TSK_CFMM',	9),
		('IDS_SCN_MATERIALMGMT'	, 'IDS_TSK_CREATEINV',	10),
		('IDS_SCN_MATERIALMGMT'	, 'IDS_TSK_DISPOSE',	11),
		('IDS_SCN_MATERIALMGMT'	, 'IDS_TSK_EDITMAT',	12),
		('IDS_SCN_MATERIALMGMT'	, 'IDS_TSK_IMPORT',	13),
		('IDS_SCN_MATERIALMGMT'	, 'IDS_TSK_OPEN',	14),
		('IDS_SCN_MATERIALMGMT'	, 'IDS_TSK_RELEASE',	15),
		('IDS_SCN_MATERIALMGMT'	, 'IDS_TSK_RESTOCK',	16),
		('IDS_SCN_MATERIALMGMT'	, 'IDS_TSK_VIEWINV',	17),
		('IDS_SCN_MATERIALMGMT',	'IDS_TSK_BARCODESCANNER', 18),

		('IDS_SCN_SAMPLEMGMT' ,	'IDS_TSK_ADDCAT',	1),
		('IDS_SCN_SAMPLEMGMT' ,	'IDS_TSK_ADDSAMPLE',	2),
		('IDS_SCN_SAMPLEMGMT' ,	'IDS_TSK_ADDTYP',	3),
		('IDS_SCN_SAMPLEMGMT' ,	'IDS_TSK_EDITSAMPLE',	4),
		('IDS_SCN_SAMPLEMGMT' ,	'IDS_TSK_IMPORT',	5),
		('IDS_SCN_SAMPLEMGMT' ,	'IDS_TSK_OPEN',	6),

		('IDS_SCN_MATERIALTYPEPARAMS' ,	'IDS_SCN_GRADEMASTER'	,1),
		('IDS_SCN_MATERIALTYPEPARAMS' ,	'IDS_SCN_MANUFACTURER'	,2),
		('IDS_SCN_MATERIALTYPEPARAMS' ,	'IDS_SCN_MATERIALCATEGORY'	,3),
		('IDS_SCN_MATERIALTYPEPARAMS' ,	'IDS_SCN_MATERIALTYPEPARAMS'	,4),
		('IDS_SCN_MATERIALTYPEPARAMS' ,	'IDS_SCN_STORAGELOCATION'	,6),
		('IDS_SCN_MATERIALTYPEPARAMS' ,	'IDS_SCN_SUPPLIER'	,7),
		('IDS_SCN_MATERIALTYPEPARAMS' ,	'IDS_SCN_UNITMASTER'	,8),
		('IDS_SCN_MATERIALTYPEPARAMS' ,	'IDS_TSK_SAMPLECAT'	,9),
		('IDS_SCN_MATERIALTYPEPARAMS' ,	'IDS_TSK_SAMPLETYPE' ,10),
		
		('IDS_SCN_EQUIPMENTMASTER' ,	'IDS_TSK_CALIBRATE',	2),
		('IDS_SCN_EQUIPMENTMASTER' ,	'IDS_TSK_EQUIPMENTACT',	3),
		('IDS_SCN_EQUIPMENTMASTER' ,	'IDS_TSK_EQUIPMENTCAL',	4),
		('IDS_SCN_EQUIPMENTMASTER' ,	'IDS_TSK_EQUIPMENTMAINT',	5),
		('IDS_SCN_EQUIPMENTMASTER' ,	'IDS_TSK_EQUIPMENTMASTER',	1),
		('IDS_SCN_EQUIPMENTMASTER' ,	'IDS_TSK_ACTDEACT',	6),
		('IDS_SCN_EQUIPMENTMASTER' ,	'IDS_TSK_MAINTANN',	7),

		('IDS_SCN_EQUIPMENT' ,	'IDS_SCN_EQUIPMENTCATEGORY'	,1),
		('IDS_SCN_EQUIPMENT' ,	'IDS_TSK_EQUIPMENTTYPE'	,2),

		('IDS_SCN_USERGROUP'	,'IDS_SCN_USERGROUP',	1),
		('IDS_SCN_USERGROUP',	'IDS_TSK_ACTDEACT',	2),

		('IDS_SCN_USERMASTER',	'IDS_SCN_USERMASTER'	,1),
		('IDS_SCN_USERMASTER',	'IDS_TSK_ACTDEACTUSERMASTER'	,2),
		('IDS_SCN_USERMASTER',	'IDS_TSK_RESETPASSWORD'	,3),
		('IDS_SCN_USERMASTER',	'IDS_TSK_RETIRE'	,4),
		('IDS_SCN_USERMASTER',	'IDS_TSK_UNLOCK'	,5),

		('IDS_SCN_USERRIGHTS',	'IDS_SCN_USERRIGHTS'	,1),

		('IDS_SCN_PROJECTMASTER',	'IDS_SCN_PROJECTMASTER'	,1),
		('IDS_SCN_PROJECTMASTER',	'IDS_SCN_PROJECTTEAM'	,2),
		('IDS_SCN_PROJECTMASTER',	'IDS_SCN_TASKMASTER'	,3),

		('IDS_SCN_TEMPLATEWORKFLOW',	'IDS_SCN_ORDERWORKLOW'	,1),
		('IDS_SCN_TEMPLATEWORKFLOW',	'IDS_SCN_TEMPLATEWORKFLOW'	,2),

		('IDS_SCN_PARSER',	'IDS_SCN_DELIMITER'	,1),
		('IDS_SCN_PARSER',	'IDS_SCN_INSTRUMENTCATEGORY'	,2),
		('IDS_SCN_PARSER',	'IDS_SCN_INSTRUMENTMASTER'	,3),
		('IDS_SCN_PARSER',	'IDS_SCN_METHODDELIMITER'	,4),
		('IDS_SCN_PARSER',	'IDS_SCN_METHODMASTER'	,5),

		('IDS_SCN_BARCODEMASTER',	'IDS_TSK_BARCODEMASTER',	1),

		('IDS_SCN_PASSWORDPOLICY',	'IDS_SCN_PASSWORDPOLICY',	1),

		('IDS_SCN_LOGBOOK',	'IDS_SCN_LOGBOOK'	,1),
		('IDS_SCN_LOGBOOK',	'IDS_TSK_ADDLOGBOOK'	,2),
		('IDS_SCN_LOGBOOK',	'IDS_TSK_EDITLOGBOOK'	,3),
		('IDS_SCN_LOGBOOK',	'IDS_TSK_RETIRELOGBOOK'	,4),
		('IDS_SCN_LOGBOOK',	'IDS_TSK_REVIEWLOGBOOK'	,5),

		('IDS_SCN_REPORTS',	'IDS_SCN_REPORTDESIGNER'	,1),
		('IDS_SCN_REPORTS',	'IDS_SCN_REPORTDESIGNERNEWFOLDER'	,2),
		('IDS_SCN_REPORTS',	'IDS_TSK_INSERT'	,3),
		('IDS_SCN_REPORTS',	'IDS_TSK_NEWTEMPLATE'	,4),
		('IDS_SCN_REPORTS',	'IDS_TSK_SAVE'	,5),

		('IDS_SCN_REPORTVIEVER'	,'IDS_SCN_REPORTVIEWER'	,1),
		('IDS_SCN_REPORTVIEVER'	,'IDS_SCN_REPORTVIEWERNEWFOLDER'	,2),
		('IDS_SCN_REPORTVIEVER'	,'IDS_TSK_NEWTEMPLATER'	,3),
		('IDS_SCN_REPORTVIEVER'	,'IDS_TSK_SAVE'	,4),

		('IDS_SCN_REPORTMAPPER'	,'IDS_TSK_REPORTMAPPING'	,1),

		('IDS_SCN_AUDITTRAILHIS',	'IDS_SCN_AUDITTRAILHIS'	,1),
		('IDS_SCN_AUDITTRAILHIS',	'IDS_TSK_CREATEARCHIVE'	,2),
		('IDS_SCN_AUDITTRAILHIS',	'IDS_TSK_EXPORT'	,3),
		('IDS_SCN_AUDITTRAILHIS',	'IDS_TSK_OPENARCHIVE'	,4),
		('IDS_SCN_AUDITTRAILHIS',	'IDS_TSK_REVIEW'	,5),
		('IDS_SCN_AUDITTRAILHIS',	'IDS_TSK_REVIEWHISTORY'	,6),

		('IDS_SCN_CFRSETTINGS',	'IDS_SCN_CFRSETTINGS'	,1),

		('IDS_SCN_AUDITTRAILCONFIG',	'IDS_SCN_AUDITTRAILCONFIG'	,1)

) AS v(screenname, displaytopic, tasksequence)
WHERE l.screenname = v.screenname AND l.displaytopic = v.displaytopic;

INSERT INTO lsusergrouprights (
  displaytopic, modulename, createdby, sallow, screate, sdelete, sedit,
  lssitemaster_sitecode, usergroupid_usergroupcode, screenname,
  sequenceorder, tasksequence
)
SELECT
  'IDS_TSK_CLONEORDER', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA',
  1, 1, 'IDS_SCN_PROTOCOLORDERS',
  6, 9
WHERE NOT EXISTS (
  SELECT 1
  FROM lsusergrouprights
  WHERE displaytopic = 'IDS_TSK_CLONEORDER'
    AND screenname = 'IDS_SCN_PROTOCOLORDERS'
    AND usergroupid_usergroupcode = 1
    AND lssitemaster_sitecode = 1
);

UPDATE lsusergrouprights AS l
SET tasksequence = v.tasksequence
FROM (
    VALUES
        ('IDS_SCN_GENRAL', 'IDS_TSK_GLOBALSEARCH', 1),
        ('IDS_SCN_GENRAL', 'IDS_TSK_SWITCHROLE', 2),
        ('IDS_SCN_GENRAL', 'IDS_TSK_SWITCHSITE', 3),

        ('IDS_SCN_DASHBOARD', 'IDS_TSK_ACTIVITIES', 1),
        ('IDS_SCN_DASHBOARD', 'IDS_TSK_ACTIVIWIDGET', 2),
        ('IDS_SCN_DASHBOARD', 'IDS_TSK_ORDEROVERVIEW', 3),
        ('IDS_SCN_DASHBOARD', 'IDS_TSK_TEMPLATEOVERVIEW', 4),
        ('IDS_SCN_DASHBOARD', 'IDS_TSK_REGISTER', 5),

        ('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_AUTOREGORDER'	,1),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_CANCELORDER'	,2),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_COPYLINK'	,3),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_DUEDATE'	,4),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_ELNTASKORDER'	,5),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_EXPORTEXCEL'	,6),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_EXPORTJSON'	,7),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_FOLDERCREATION'	,8),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_FOLDERDEL'	,9),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_MANAGEEXCEL'	,10),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_MOVEORDERS'	,11),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_NEWFOLDER'	,12),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_ORDERSHAREDBYME'	,13),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_ORDERSHAREDTOME'	,14),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_PROJECTTEAM'	,15),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_REGISTER'	,16),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_RESEARCHACTIVITY'	,17),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_SHARE'	,18),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_SHEETEVALUATION'	,20),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_SHEETORDEREXPORT'	,21),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_SHOWVERSION'	,22),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_TRANHISTORY'	,23),
		('IDS_SCN_SHEETORDERS' ,	'IDS_TSK_UPLOADSHEETORDER'	,24),

		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ADDEDITOR',	1),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ADDREFF',	2),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ADDRESEQUIPMENT',	3),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ADDRESMATERIAL',	4),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ADDRESULT',	5),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ADDSEC',	6),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ADDSTEP',	7),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_AUTOREGORDER',	8),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_CLONEORDER',	9),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_CANCELORDER',	10),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_COPYLINK',	11),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_DUEDATE',	12),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_DYNAMICPROTOCOL',	13),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ELNPROTOCOL',	14),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_EXPORTEXCEL',	15),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_EXPORTJSON',	16),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_FOLDERCREATIONPROTOCOL',	17),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_FOLDERDEL',	18),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_MOVEORDERSPROTOCOL',	19),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ORDERSHAREDBYMEPROTOCOL',	20),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ORDERSHAREDTOMEPROTOCOL',	21),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_PROJECTTEAM',	22),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_REGISTER',	23),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_SHARE',	24),
		('IDS_SCN_PROTOCOLORDERS' ,	'IDS_TSK_NEWFOLDER',25),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_SHOWVERSION',	26),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_TRANHISTORY',	27),
		('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_UPLOADPROTOCOLORDER',	28),

		('IDS_SCN_UNLOCKORDERS','IDS_SCN_UNLOCKORDERS',	1),

		('IDS_SCN_SHEETTEMPLATE'	, 'IDS_SCN_SHEETTEMPLATE'	,1),
		('IDS_SCN_SHEETTEMPLATE'	, 'IDS_TSK_NEW'	,2),
		('IDS_SCN_SHEETTEMPLATE'	, 'IDS_TSK_RETIRE'	,3),
		('IDS_SCN_SHEETTEMPLATE'	, 'IDS_TSK_SHARE'	,4),
		('IDS_SCN_SHEETTEMPLATE'	, 'IDS_TSK_SHEETTEMPEXPORT'	,5),
		('IDS_SCN_SHEETTEMPLATE'	, 'IDS_TSK_SHOWVERSION'	,6),
		('IDS_SCN_SHEETTEMPLATE'	, 'IDS_TSK_TEMPLATESHAREDBYME'	,7),
		('IDS_SCN_SHEETTEMPLATE'	, 'IDS_TSK_TEMPLATESHAREDTOME'	,8),
		('IDS_SCN_SHEETTEMPLATE'	, 'IDS_TSK_TRANHISTORY'	,9),
		('IDS_SCN_SHEETTEMPLATE'	, 'IDS_TSK_VISIBLITY' ,10),
		('IDS_SCN_SHEETTEMPLATE'	, 'IDS_TSK_EDIT',11),

		('IDS_SCN_PROTOCOLTEMPLATE' ,	'IDS_SCN_PROTOCOLTEMPLATE'	,1),
		('IDS_SCN_PROTOCOLTEMPLATE' ,	'IDS_TSK_ADDEDITOR'	,2),
		('IDS_SCN_PROTOCOLTEMPLATE' ,	'IDS_TSK_ADDREFF'	,3),
		('IDS_SCN_PROTOCOLTEMPLATE' ,	'IDS_TSK_ADDRESEQUIPMENT'	,4),
		('IDS_SCN_PROTOCOLTEMPLATE' ,	'IDS_TSK_ADDRESMATERIAL'	,5),
		('IDS_SCN_PROTOCOLTEMPLATE' ,	'IDS_TSK_ADDRESULT'	,6),
		('IDS_SCN_PROTOCOLTEMPLATE' ,	'IDS_TSK_ADDSEC'	,7),
		('IDS_SCN_PROTOCOLTEMPLATE' ,	'IDS_TSK_ADDSTEP'	,8),
		('IDS_SCN_PROTOCOLTEMPLATE' ,	'IDS_TSK_EXPORTJSON'	,9),
		('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_EXPORTPDF'	,10),
		('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_IMPORTJSON'	,11),
		('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_NEW'	,12),
		('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_NEWSTEP'	,13),
		('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_PROTOCOLTEMPSHAREBYME'	,14),
		('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_PROTOCOLTEMPSHARETOME'	,15),
		('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_RETIRE'	,16),
		('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_SHARE'	,17),
		('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_SHOWVERSION'	,18),
		('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_TRANHISTORY'	,19),
		('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_VISIBLITY'	,20),

		('IDS_SCN_TEMPLATEMAPPING',	'IDS_TSK_PROTOCOL'	,1),
		('IDS_SCN_TEMPLATEMAPPING',	'IDS_TSK_SHEET'	,2),

		('IDS_SCN_MATERIALMGMT'	, 'IDS_TSK_ADDATTACH',	1),
		('IDS_SCN_MATERIALMGMT'	, 'IDS_TSK_ADDCAT',	2),
		('IDS_SCN_MATERIALMGMT'	, 'IDS_TSK_ADDHYPLINK',	3),
		('IDS_SCN_MATERIALMGMT'	, 'IDS_TSK_ADDMAT',	4),
		('IDS_SCN_MATERIALMGMT'	, 'IDS_TSK_ADDSTOCK',	5),
		('IDS_SCN_MATERIALMGMT'	, 'IDS_TSK_ADDTYP',	6),
		('IDS_SCN_MATERIALMGMT'	, 'IDS_TSK_ASSIGNPROJ',	7),
		('IDS_SCN_MATERIALMGMT'	, 'IDS_TSK_CFINWARD',	8),
		('IDS_SCN_MATERIALMGMT'	, 'IDS_TSK_CFMM',	9),
		('IDS_SCN_MATERIALMGMT'	, 'IDS_TSK_CREATEINV',	10),
		('IDS_SCN_MATERIALMGMT'	, 'IDS_TSK_DISPOSE',	11),
		('IDS_SCN_MATERIALMGMT'	, 'IDS_TSK_EDITMAT',	12),
		('IDS_SCN_MATERIALMGMT'	, 'IDS_TSK_IMPORT',	13),
		('IDS_SCN_MATERIALMGMT'	, 'IDS_TSK_OPEN',	14),
		('IDS_SCN_MATERIALMGMT'	, 'IDS_TSK_RELEASE',	15),
		('IDS_SCN_MATERIALMGMT'	, 'IDS_TSK_RESTOCK',	16),
		('IDS_SCN_MATERIALMGMT'	, 'IDS_TSK_VIEWINV',	17),
		('IDS_SCN_MATERIALMGMT' , 'IDS_TSK_BARCODESCANNER', 18),

		('IDS_SCN_SAMPLEMGMT' ,	'IDS_TSK_ADDCAT',	1),
		('IDS_SCN_SAMPLEMGMT' ,	'IDS_TSK_ADDSAMPLE',	2),
		('IDS_SCN_SAMPLEMGMT' ,	'IDS_TSK_ADDTYP',	3),
		('IDS_SCN_SAMPLEMGMT' ,	'IDS_TSK_EDITSAMPLE',	4),
		('IDS_SCN_SAMPLEMGMT' ,	'IDS_TSK_IMPORT',	5),
		('IDS_SCN_SAMPLEMGMT' ,	'IDS_TSK_OPEN',	6),

		('IDS_SCN_MATERIALTYPEPARAMS' ,	'IDS_SCN_GRADEMASTER'	,1),
		('IDS_SCN_MATERIALTYPEPARAMS' ,	'IDS_SCN_MANUFACTURER'	,2),
		('IDS_SCN_MATERIALTYPEPARAMS' ,	'IDS_SCN_MATERIALCATEGORY'	,3),
		('IDS_SCN_MATERIALTYPEPARAMS' ,	'IDS_SCN_MATERIALTYPEPARAMS'	,4),
		('IDS_SCN_MATERIALTYPEPARAMS' ,	'IDS_SCN_STORAGELOCATION'	,6),
		('IDS_SCN_MATERIALTYPEPARAMS' ,	'IDS_SCN_SUPPLIER'	,7),
		('IDS_SCN_MATERIALTYPEPARAMS' ,	'IDS_SCN_UNITMASTER'	,8),
		('IDS_SCN_MATERIALTYPEPARAMS' ,	'IDS_TSK_SAMPLECAT'	,9),
		('IDS_SCN_MATERIALTYPEPARAMS' ,	'IDS_TSK_SAMPLETYPE' ,10),
		
		('IDS_SCN_EQUIPMENTMASTER' ,	'IDS_TSK_CALIBRATE',	2),
		('IDS_SCN_EQUIPMENTMASTER' ,	'IDS_TSK_EQUIPMENTACT',	3),
		('IDS_SCN_EQUIPMENTMASTER' ,	'IDS_TSK_EQUIPMENTCAL',	4),
		('IDS_SCN_EQUIPMENTMASTER' ,	'IDS_TSK_EQUIPMENTMAINT',	5),
		('IDS_SCN_EQUIPMENTMASTER' ,	'IDS_TSK_EQUIPMENTMASTER',	1),
		('IDS_SCN_EQUIPMENTMASTER' ,	'IDS_TSK_ACTDEACT',	6),
		('IDS_SCN_EQUIPMENTMASTER' ,	'IDS_TSK_MAINTANN',	7),

		('IDS_SCN_EQUIPMENT' ,	'IDS_SCN_EQUIPMENTCATEGORY'	,1),
		('IDS_SCN_EQUIPMENT' ,	'IDS_TSK_EQUIPMENTTYPE'	,2),

		('IDS_SCN_USERGROUP'	,'IDS_SCN_USERGROUP',	1),
		('IDS_SCN_USERGROUP',	'IDS_TSK_ACTDEACT',	2),

		('IDS_SCN_USERMASTER',	'IDS_SCN_USERMASTER'	,1),
		('IDS_SCN_USERMASTER',	'IDS_TSK_ACTDEACTUSERMASTER'	,2),
		('IDS_SCN_USERMASTER',	'IDS_TSK_RESETPASSWORD'	,3),
		('IDS_SCN_USERMASTER',	'IDS_TSK_RETIRE'	,4),
		('IDS_SCN_USERMASTER',	'IDS_TSK_UNLOCK'	,5),

		('IDS_SCN_USERRIGHTS',	'IDS_SCN_USERRIGHTS'	,1),

		('IDS_SCN_PROJECTMASTER',	'IDS_SCN_PROJECTMASTER'	,1),
		('IDS_SCN_PROJECTMASTER',	'IDS_SCN_PROJECTTEAM'	,2),
		('IDS_SCN_PROJECTMASTER',	'IDS_SCN_TASKMASTER'	,3),

		('IDS_SCN_TEMPLATEWORKFLOW',	'IDS_SCN_ORDERWORKLOW'	,1),
		('IDS_SCN_TEMPLATEWORKFLOW',	'IDS_SCN_TEMPLATEWORKFLOW'	,2),

		('IDS_SCN_PARSER',	'IDS_SCN_DELIMITER'	,1),
		('IDS_SCN_PARSER',	'IDS_SCN_INSTRUMENTCATEGORY'	,2),
		('IDS_SCN_PARSER',	'IDS_SCN_INSTRUMENTMASTER'	,3),
		('IDS_SCN_PARSER',	'IDS_SCN_METHODDELIMITER'	,4),
		('IDS_SCN_PARSER',	'IDS_SCN_METHODMASTER'	,5),

		('IDS_SCN_BARCODEMASTER',	'IDS_TSK_BARCODEMASTER',	1),

		('IDS_SCN_PASSWORDPOLICY',	'IDS_SCN_PASSWORDPOLICY',	1),

		('IDS_SCN_LOGBOOK',	'IDS_SCN_LOGBOOK'	,1),
		('IDS_SCN_LOGBOOK',	'IDS_TSK_ADDLOGBOOK'	,2),
		('IDS_SCN_LOGBOOK',	'IDS_TSK_EDITLOGBOOK'	,3),
		('IDS_SCN_LOGBOOK',	'IDS_TSK_RETIRELOGBOOK'	,4),
		('IDS_SCN_LOGBOOK',	'IDS_TSK_REVIEWLOGBOOK'	,5),

		('IDS_SCN_REPORTS',	'IDS_SCN_REPORTDESIGNER'	,1),
		('IDS_SCN_REPORTS',	'IDS_SCN_REPORTDESIGNERNEWFOLDER'	,2),
		('IDS_SCN_REPORTS',	'IDS_TSK_INSERT'	,3),
		('IDS_SCN_REPORTS',	'IDS_TSK_NEWTEMPLATE'	,4),
		('IDS_SCN_REPORTS',	'IDS_TSK_SAVE'	,5),

		('IDS_SCN_REPORTVIEVER'	,'IDS_SCN_REPORTVIEWER'	,1),
		('IDS_SCN_REPORTVIEVER'	,'IDS_SCN_REPORTVIEWERNEWFOLDER'	,2),
		('IDS_SCN_REPORTVIEVER'	,'IDS_TSK_NEWTEMPLATER'	,3),
		('IDS_SCN_REPORTVIEVER'	,'IDS_TSK_SAVE'	,4),

		('IDS_SCN_REPORTMAPPER'	,'IDS_TSK_REPORTMAPPING'	,1),

		('IDS_SCN_AUDITTRAILHIS',	'IDS_SCN_AUDITTRAILHIS'	,1),
		('IDS_SCN_AUDITTRAILHIS',	'IDS_TSK_CREATEARCHIVE'	,2),
		('IDS_SCN_AUDITTRAILHIS',	'IDS_TSK_EXPORT'	,3),
		('IDS_SCN_AUDITTRAILHIS',	'IDS_TSK_OPENARCHIVE'	,4),
		('IDS_SCN_AUDITTRAILHIS',	'IDS_TSK_REVIEW'	,5),
		('IDS_SCN_AUDITTRAILHIS',	'IDS_TSK_REVIEWHISTORY'	,6),

		('IDS_SCN_CFRSETTINGS',	'IDS_SCN_CFRSETTINGS'	,1),

		('IDS_SCN_AUDITTRAILCONFIG',	'IDS_SCN_AUDITTRAILCONFIG'	,1)

) AS v(screenname, displaytopic, tasksequence)
WHERE l.screenname = v.screenname AND l.displaytopic = v.displaytopic;

delete from materialgrade where nsitecode = -1 and ndefaultstatus = 4 and smaterialgradename in ('A','B','C','D');

update lsaudittrailconfigmaster set modulename = 'IDS_MDL_SETUP' where screenname in( 'IDS_SCN_PROJECTMASTER' ,'IDS_SCN_TASKMASTER');

delete from lsusergrouprightsmaster where screenname = 'IDS_SCN_SHEETORDERS' and displaytopic = 'IDS_TSK_NEWFOLDER';
delete from lsusergrouprights where screenname = 'IDS_SCN_SHEETORDERS' and displaytopic = 'IDS_TSK_NEWFOLDER';

delete from lsusergrouprightsmaster where modulename = 'IDS_MDL_INVENTORY' and screenname = 'IDS_SCN_INVENTORY' and displaytopic = 'IDS_SCN_INVENTORY';
delete from lsusergrouprights where modulename = 'IDS_MDL_INVENTORY' and screenname = 'IDS_SCN_INVENTORY' and displaytopic = 'IDS_SCN_INVENTORY';

delete from lsusergrouprightsmaster where modulename = 'IDS_MDL_INVENTORY' and screenname = 'IDS_SCN_INVENTORY' and displaytopic = 'IDS_TSK_ADDREPO';
delete from lsusergrouprights where modulename = 'IDS_MDL_INVENTORY' and screenname = 'IDS_SCN_INVENTORY' and displaytopic = 'IDS_TSK_ADDREPO';

delete from lsusergrouprightsmaster where modulename = 'IDS_MDL_INVENTORY' and screenname = 'IDS_SCN_INVENTORY' and displaytopic = 'IDS_TSK_EDITREPO';
delete from lsusergrouprights where modulename = 'IDS_MDL_INVENTORY' and screenname = 'IDS_SCN_INVENTORY' and displaytopic = 'IDS_TSK_EDITREPO';

update lsusergrouprights set sdelete = '1' where usergroupid_usergroupcode = 1 and screenname = 'IDS_SCN_MATERIALTYPEPARAMS' and displaytopic = 'IDS_SCN_MATERIALTYPEPARAMS';
update lsusergrouprightsmaster set sdelete = '0' where screenname = 'IDS_SCN_MATERIALTYPEPARAMS' and displaytopic = 'IDS_SCN_MATERIALTYPEPARAMS';

-- INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
-- SELECT 'IDS_TSK_EXPORTMATMGMT', 'IDS_MDL_INVENTORY', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_MATERIALMGMT' ,16
-- WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_EXPORTMATMGMT' and screenname='IDS_SCN_MATERIALMGMT' and usergroupid_usergroupcode = 1); 

-- INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
-- VALUES (301, 'IDS_TSK_EXPORTMATMGMT', 'IDS_MDL_INVENTORY', 'IDS_SCN_MATERIALMGMT', '0', 'NA', 'NA', 'NA', '0,0,0', 16) 
-- ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname) SELECT 'IDS_TSK_TRANHISTORY', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_SHEETORDERS' 
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_TRANHISTORY' and screenname='IDS_SCN_SHEETORDERS' and usergroupid_usergroupcode = 1); 

-- delete from lsusergrouprightsmaster where screenname = 'IDS_SCN_SHEETORDERS' and displaytopic = 'IDS_TSK_EXPORTEXCEL';
-- delete from lsusergrouprights where screenname = 'IDS_SCN_SHEETORDERS' and displaytopic = 'IDS_TSK_EXPORTEXCEL';

-- delete from lsusergrouprights where screenname = 'IDS_SCN_PROTOCOLTEMPLATE' and displaytopic = 'IDS_TSK_EXPORTPDF';
-- delete from lsusergrouprightsmaster where screenname = 'IDS_SCN_PROTOCOLTEMPLATE' and displaytopic = 'IDS_TSK_EXPORTPDF';

-- delete from lsusergrouprights where screenname = 'IDS_SCN_PROTOCOLTEMPLATE' and displaytopic = 'IDS_TSK_NEWSTEP';
-- delete from lsusergrouprightsmaster where screenname = 'IDS_SCN_PROTOCOLTEMPLATE' and displaytopic = 'IDS_TSK_NEWSTEP';

update lsusergrouprightsmaster set displaytopic = 'IDS_TSK_QUARANTINE' where displaytopic = 'IDS_TSK_RESTOCK' And modulename ='IDS_MDL_INVENTORY';
update lsusergrouprights set displaytopic = 'IDS_TSK_QUARANTINE' where displaytopic = 'IDS_TSK_RESTOCK' And modulename ='IDS_MDL_INVENTORY';

UPDATE lsusergrouprightsmaster set screate = '0' where screenname = 'IDS_SCN_REPORTVIEVER' and displaytopic = 'IDS_SCN_REPORTVIEWERNEWFOLDER';
UPDATE lsusergrouprights set screate = '0' where screenname = 'IDS_SCN_REPORTVIEVER' and displaytopic = 'IDS_SCN_REPORTVIEWERNEWFOLDER' and screate = 'NA' and sallow = '0';
UPDATE lsusergrouprights set screate = '1' where screenname = 'IDS_SCN_REPORTVIEVER' and displaytopic = 'IDS_SCN_REPORTVIEWERNEWFOLDER' and screate = 'NA' and sallow = '1';

UPDATE lsusergrouprightsmaster set sdelete = '0' where displaytopic = 'IDS_TSK_FOLDERCREATIONPROTOCOL';
UPDATE lsusergrouprights set sdelete = '0' where displaytopic = 'IDS_TSK_FOLDERCREATIONPROTOCOL' and sdelete = 'NA' and sallow = '0';
UPDATE lsusergrouprights set sdelete = '1' where displaytopic = 'IDS_TSK_FOLDERCREATIONPROTOCOL' and sdelete = 'NA' and sallow = '1';

UPDATE lsusergrouprightsmaster set sdelete = '0' where displaytopic = 'IDS_TSK_FOLDERCREATION';
UPDATE lsusergrouprights set sdelete = '0' where displaytopic = 'IDS_TSK_FOLDERCREATION' and sdelete = 'NA' and sallow = '0';
UPDATE lsusergrouprights set sdelete = '1' where displaytopic = 'IDS_TSK_FOLDERCREATION' and sdelete = 'NA' and sallow = '1';

UPDATE lsusergrouprightsmaster set sedit = 'NA' where displaytopic = 'IDS_TSK_ORDERSHAREDTOMEPROTOCOL';
UPDATE lsusergrouprights set sedit = 'NA' where displaytopic = 'IDS_TSK_ORDERSHAREDTOMEPROTOCOL';

UPDATE lsusergrouprightsmaster set sedit = 'NA' where displaytopic = 'IDS_TSK_ORDERSHAREDBYMEPROTOCOL';
UPDATE lsusergrouprights set sedit = 'NA' where displaytopic = 'IDS_TSK_ORDERSHAREDBYMEPROTOCOL';

UPDATE lsusergrouprightsmaster set sallow = '0' where screenname = 'IDS_SCN_REPORTS' and displaytopic = 'IDS_TSK_SAVE' and sallow = 'NA';
UPDATE lsusergrouprights set sallow = '0' where screenname = 'IDS_SCN_REPORTS' and displaytopic = 'IDS_TSK_SAVE' and sallow = 'NA';

UPDATE lsusergrouprightsmaster set sedit = '0' where displaytopic = 'IDS_TSK_EQUIPMENTTYPE' and sedit = 'NA';
UPDATE lsusergrouprights set sedit = '0' where displaytopic = 'IDS_TSK_EQUIPMENTTYPE' and sedit = 'NA' and screate = '0';
UPDATE lsusergrouprights set sedit = '1' where displaytopic = 'IDS_TSK_EQUIPMENTTYPE' and sedit = 'NA' and screate = '1';

 -- Insert new audit trail config only if not already present
INSERT INTO lsaudittrailconfigmaster (
  serialno, manualaudittrail, modulename, ordersequnce, screenname, taskname
)
SELECT
  COALESCE((SELECT MAX(serialno) + 1 FROM lsaudittrailconfigmaster), 1),
  0, 'IDS_MDL_ORDERS', 2, 'IDS_SCN_PROTOCOLORDERS', 'IDS_TSK_CLONEORDER'
WHERE NOT EXISTS (
  SELECT 1
  FROM lsaudittrailconfigmaster
  WHERE taskname = 'IDS_TSK_CLONEORDER'
    AND modulename = 'IDS_MDL_ORDERS'
    AND screenname = 'IDS_SCN_PROTOCOLORDERS'
);

INSERT INTO lsaudittrailconfigmaster (
  serialno, manualaudittrail, modulename, ordersequnce, screenname, taskname
)
SELECT
  COALESCE((SELECT MAX(serialno) + 1 FROM lsaudittrailconfigmaster), 1),
  0, 'IDS_MDL_ORDERS', 1, 'IDS_SCN_SHEETORDERS', 'IDS_TSK_OPENNEWTAB'
WHERE NOT EXISTS (
  SELECT 1
  FROM lsaudittrailconfigmaster
  WHERE taskname = 'IDS_TSK_OPENNEWTAB'
    AND modulename = 'IDS_MDL_ORDERS'
    AND screenname = 'IDS_SCN_SHEETORDERS'
);

INSERT INTO lsaudittrailconfigmaster (
  serialno, manualaudittrail, modulename, ordersequnce, screenname, taskname
)
SELECT
  COALESCE((SELECT MAX(serialno) + 1 FROM lsaudittrailconfigmaster), 1),
  0, 'IDS_MDL_ORDERS', 2, 'IDS_SCN_PROTOCOLORDERS', 'IDS_TSK_OPENNEWTAB'
WHERE NOT EXISTS (
  SELECT 1
  FROM lsaudittrailconfigmaster
  WHERE taskname = 'IDS_TSK_OPENNEWTAB'
    AND modulename = 'IDS_MDL_ORDERS'
    AND screenname = 'IDS_SCN_PROTOCOLORDERS'
);

UPDATE lsusergrouprightsmaster set screate = 'NA' where screenname = 'IDS_SCN_TEMPLATEMAPPING' and displaytopic = 'IDS_TSK_SHEET' and screate = '0';
UPDATE lsusergrouprights SET screate = 'NA' WHERE screenname = 'IDS_SCN_TEMPLATEMAPPING' AND displaytopic = 'IDS_TSK_SHEET' AND screate IN ('0', '1');
UPDATE lsusergrouprightsmaster set screate = 'NA' where screenname = 'IDS_SCN_TEMPLATEMAPPING' and displaytopic = 'IDS_TSK_PROTOCOL' and screate = '0';
UPDATE lsusergrouprights SET screate = 'NA' WHERE screenname = 'IDS_SCN_TEMPLATEMAPPING' AND displaytopic = 'IDS_TSK_PROTOCOL' AND screate IN ('0', '1');

UPDATE lslogilabprotocoldetail SET isclone = false WHERE isclone IS NULL AND EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'lslogilabprotocoldetail' AND column_name = 'isclone');
UPDATE LSlogilablimsorderdetail SET isclone = false WHERE isclone IS NULL AND EXISTS (SELECT 1 FROM information_schema.columns WHERE table_name = 'LSlogilablimsorderdetail' AND column_name = 'isclone');
update Elnprotocolworkflow set status = 1 where status is null;
update LSworkflow set status = 1 where status is null;

UPDATE lsusergrouprights set sedit = '1' where displaytopic = 'IDS_TSK_EQUIPMENTTYPE' and sedit = 'NA' and screate = '1';

UPDATE lsusergrouprights set sedit = '1' where displaytopic = 'IDS_TSK_EQUIPMENTTYPE' and sedit = 'NA' and screate = '1';

delete from lsusergrouprightsmaster where displaytopic = 'IDS_SCN_SECTIONMASTER';
delete from lsusergrouprights where displaytopic = 'IDS_SCN_SECTIONMASTER';

ALTER TABLE IF Exists lsusergrouprightsmaster ADD Column IF NOT EXISTS tasksequence INTEGER;
ALTER TABLE IF Exists lsusergrouprights ADD Column IF NOT EXISTS tasksequence INTEGER;

update lsusergrouprightsmaster set tasksequence = '0' where displaytopic = 'IDS_TSK_SCREENVIEW';
update lsusergrouprights set tasksequence = '0' where displaytopic = 'IDS_TSK_SCREENVIEW';



 -- Insert new audit trail config only if not already present
INSERT INTO lsaudittrailconfigmaster (
  serialno, manualaudittrail, modulename, ordersequnce, screenname, taskname
)
SELECT
  COALESCE((SELECT MAX(serialno) + 1 FROM lsaudittrailconfigmaster), 1),
  0, 'IDS_MDL_ORDERS', 1, 'IDS_SCN_SHEETORDERS', 'IDS_TSK_CLONEORDER'
WHERE NOT EXISTS (
  SELECT 1
  FROM lsaudittrailconfigmaster
  WHERE taskname = 'IDS_TSK_CLONEORDER'
    AND modulename = 'IDS_MDL_ORDERS'
    AND screenname = 'IDS_SCN_SHEETORDERS'
);




INSERT INTO lsusergrouprightsmaster (
    orderno, displaytopic, modulename, screenname,
    sallow, screate, sdelete, sedit, status,
    sequenceorder, tasksequence
)
SELECT
    COALESCE((SELECT MAX(orderno) + 1 FROM lsusergrouprightsmaster), 1),
    'IDS_TSK_CLONEORDER',
    'IDS_MDL_ORDERS',
    'IDS_SCN_SHEETORDERS',
    '0', 'NA', 'NA', 'NA', '0,0,0',
    4, 25
WHERE NOT EXISTS (
    SELECT 1
    FROM lsusergrouprightsmaster
    WHERE displaytopic = 'IDS_TSK_CLONEORDER'
      AND screenname = 'IDS_SCN_SHEETORDERS'
      AND modulename = 'IDS_MDL_ORDERS'
      AND sequenceorder = 4
);



INSERT INTO lsusergrouprights (
  displaytopic, modulename, createdby, sallow, screate, sdelete, sedit,
  lssitemaster_sitecode, usergroupid_usergroupcode, screenname,
  sequenceorder, tasksequence
)
SELECT
  'IDS_TSK_CLONEORDER', 'IDS_MDL_ORDERS', 'administrator', '1', 'NA', 'NA', 'NA',
  1, 1, 'IDS_SCN_SHEETORDERS',
  4, 25
WHERE NOT EXISTS (
  SELECT 1
  FROM lsusergrouprights
  WHERE displaytopic = 'IDS_TSK_CLONEORDER'
    AND screenname = 'IDS_SCN_SHEETORDERS'
    AND usergroupid_usergroupcode = 1
    AND lssitemaster_sitecode = 1
);


UPDATE lsusergrouprightsmaster AS l
SET tasksequence = v.tasksequence
FROM (
    VALUES
        ('IDS_SCN_GENRAL', 'IDS_TSK_GLOBALSEARCH', 1),
        ('IDS_SCN_GENRAL', 'IDS_TSK_SWITCHROLE', 2),
        ('IDS_SCN_GENRAL', 'IDS_TSK_SWITCHSITE', 3),

        ('IDS_SCN_DASHBOARD', 'IDS_TSK_ACTIVITIES', 1),
        ('IDS_SCN_DASHBOARD', 'IDS_TSK_ACTIVIWIDGET', 2),
        ('IDS_SCN_DASHBOARD', 'IDS_TSK_ORDEROVERVIEW', 3),
        ('IDS_SCN_DASHBOARD', 'IDS_TSK_TEMPLATEOVERVIEW', 4),
        ('IDS_SCN_DASHBOARD', 'IDS_TSK_REGISTER', 5),

        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_AUTOREGORDER'  ,1),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_CANCELORDER'   ,2),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_COPYLINK'  ,3),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_DUEDATE'   ,4),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_ELNTASKORDER'  ,5),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_EXPORTEXCEL'   ,6),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_EXPORTJSON'    ,7),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_FOLDERCREATION'    ,8),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_FOLDERDEL' ,9),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_MANAGEEXCEL'   ,10),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_MOVEORDERS'    ,11),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_NEWFOLDER' ,12),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_ORDERSHAREDBYME'   ,13),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_ORDERSHAREDTOME'   ,14),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_PROJECTTEAM'   ,15),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_REGISTER'  ,16),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_RESEARCHACTIVITY'  ,17),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_SHARE' ,18),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_SHEETEVALUATION'   ,20),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_SHEETORDEREXPORT'  ,21),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_SHOWVERSION'   ,22),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_TRANHISTORY'   ,23),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_UPLOADSHEETORDER'  ,24),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_CLONEORDER'  ,25),
        

        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ADDEDITOR',    1),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ADDREFF',  2),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ADDRESEQUIPMENT',  3),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ADDRESMATERIAL',   4),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ADDRESULT',    5),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ADDSEC',   6),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ADDSTEP',  7),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_AUTOREGORDER', 8),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_CANCELORDER',  9),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_COPYLINK', 10),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_DUEDATE',  11),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_DYNAMICPROTOCOL',  12),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ELNPROTOCOL',  13),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_EXPORTEXCEL',  14),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_EXPORTJSON',   15),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_FOLDERCREATIONPROTOCOL',   16),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_FOLDERDEL',    17),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_MOVEORDERSPROTOCOL',   18),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ORDERSHAREDBYMEPROTOCOL',  19),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ORDERSHAREDTOMEPROTOCOL',  20),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_PROJECTTEAM',  21),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_REGISTER', 22),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_SHARE',    23),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_NEWFOLDER',24),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_SHOWVERSION',  25),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_TRANHISTORY',  26),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_UPLOADPROTOCOLORDER',  27),

        ('IDS_SCN_UNLOCKORDERS','IDS_SCN_UNLOCKORDERS', 1),

        ('IDS_SCN_SHEETTEMPLATE'    , 'IDS_SCN_SHEETTEMPLATE'   ,1),
        ('IDS_SCN_SHEETTEMPLATE'    , 'IDS_TSK_NEW' ,2),
        ('IDS_SCN_SHEETTEMPLATE'    , 'IDS_TSK_RETIRE'  ,3),
        ('IDS_SCN_SHEETTEMPLATE'    , 'IDS_TSK_SHARE'   ,4),
        ('IDS_SCN_SHEETTEMPLATE'    , 'IDS_TSK_SHEETTEMPEXPORT' ,5),
        ('IDS_SCN_SHEETTEMPLATE'    , 'IDS_TSK_SHOWVERSION' ,6),
        ('IDS_SCN_SHEETTEMPLATE'    , 'IDS_TSK_TEMPLATESHAREDBYME'  ,7),
        ('IDS_SCN_SHEETTEMPLATE'    , 'IDS_TSK_TEMPLATESHAREDTOME'  ,8),
        ('IDS_SCN_SHEETTEMPLATE'    , 'IDS_TSK_TRANHISTORY' ,9),
        ('IDS_SCN_SHEETTEMPLATE'    , 'IDS_TSK_VISIBLITY' ,10),
        ('IDS_SCN_SHEETTEMPLATE'    , 'IDS_TSK_EDIT',11),

        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_SCN_PROTOCOLTEMPLATE'  ,1),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_ADDEDITOR' ,2),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_ADDREFF'   ,3),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_ADDRESEQUIPMENT'   ,4),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_ADDRESMATERIAL'    ,5),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_ADDRESULT' ,6),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_ADDSEC'    ,7),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_ADDSTEP'   ,8),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_EXPORTJSON'    ,9),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_EXPORTPDF' ,10),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_IMPORTJSON'    ,11),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_NEW'   ,12),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_NEWSTEP'   ,13),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_PROTOCOLTEMPSHAREBYME' ,14),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_PROTOCOLTEMPSHARETOME' ,15),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_RETIRE'    ,16),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_SHARE' ,17),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_SHOWVERSION'   ,18),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_TRANHISTORY'   ,19),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_VISIBLITY' ,20),

        ('IDS_SCN_TEMPLATEMAPPING', 'IDS_TSK_PROTOCOL'  ,1),
        ('IDS_SCN_TEMPLATEMAPPING', 'IDS_TSK_SHEET' ,2),

        ('IDS_SCN_MATERIALMGMT' , 'IDS_TSK_ADDATTACH',  1),
        ('IDS_SCN_MATERIALMGMT' , 'IDS_TSK_ADDCAT', 2),
        ('IDS_SCN_MATERIALMGMT' , 'IDS_TSK_ADDHYPLINK', 3),
        ('IDS_SCN_MATERIALMGMT' , 'IDS_TSK_ADDMAT', 4),
        ('IDS_SCN_MATERIALMGMT' , 'IDS_TSK_ADDSTOCK',   5),
        ('IDS_SCN_MATERIALMGMT' , 'IDS_TSK_ADDTYP', 6),
        ('IDS_SCN_MATERIALMGMT' , 'IDS_TSK_ASSIGNPROJ', 7),
        ('IDS_SCN_MATERIALMGMT' , 'IDS_TSK_CFINWARD',   8),
        ('IDS_SCN_MATERIALMGMT' , 'IDS_TSK_CFMM',   9),
        ('IDS_SCN_MATERIALMGMT' , 'IDS_TSK_CREATEINV',  10),
        ('IDS_SCN_MATERIALMGMT' , 'IDS_TSK_DISPOSE',    11),
        ('IDS_SCN_MATERIALMGMT' , 'IDS_TSK_EDITMAT',    12),
        ('IDS_SCN_MATERIALMGMT' , 'IDS_TSK_IMPORT', 13),
        ('IDS_SCN_MATERIALMGMT' , 'IDS_TSK_OPEN',   14),
        ('IDS_SCN_MATERIALMGMT' , 'IDS_TSK_RELEASE',    15),
        ('IDS_SCN_MATERIALMGMT' , 'IDS_TSK_RESTOCK',    16),
        ('IDS_SCN_MATERIALMGMT' , 'IDS_TSK_VIEWINV',    17),
        ('IDS_SCN_MATERIALMGMT',    'IDS_TSK_BARCODESCANNER', 18),

        ('IDS_SCN_SAMPLEMGMT' , 'IDS_TSK_ADDCAT',   1),
        ('IDS_SCN_SAMPLEMGMT' , 'IDS_TSK_ADDSAMPLE',    2),
        ('IDS_SCN_SAMPLEMGMT' , 'IDS_TSK_ADDTYP',   3),
        ('IDS_SCN_SAMPLEMGMT' , 'IDS_TSK_EDITSAMPLE',   4),
        ('IDS_SCN_SAMPLEMGMT' , 'IDS_TSK_IMPORT',   5),
        ('IDS_SCN_SAMPLEMGMT' , 'IDS_TSK_OPEN', 6),

        ('IDS_SCN_MATERIALTYPEPARAMS' , 'IDS_SCN_GRADEMASTER'   ,1),
        ('IDS_SCN_MATERIALTYPEPARAMS' , 'IDS_SCN_MANUFACTURER'  ,2),
        ('IDS_SCN_MATERIALTYPEPARAMS' , 'IDS_SCN_MATERIALCATEGORY'  ,3),
        ('IDS_SCN_MATERIALTYPEPARAMS' , 'IDS_SCN_MATERIALTYPEPARAMS'    ,4),
        ('IDS_SCN_MATERIALTYPEPARAMS' , 'IDS_SCN_STORAGELOCATION'   ,6),
        ('IDS_SCN_MATERIALTYPEPARAMS' , 'IDS_SCN_SUPPLIER'  ,7),
        ('IDS_SCN_MATERIALTYPEPARAMS' , 'IDS_SCN_UNITMASTER'    ,8),
        ('IDS_SCN_MATERIALTYPEPARAMS' , 'IDS_TSK_SAMPLECAT' ,9),
        ('IDS_SCN_MATERIALTYPEPARAMS' , 'IDS_TSK_SAMPLETYPE' ,10),
        
        ('IDS_SCN_EQUIPMENTMASTER' ,    'IDS_TSK_CALIBRATE',    2),
        ('IDS_SCN_EQUIPMENTMASTER' ,    'IDS_TSK_EQUIPMENTACT', 3),
        ('IDS_SCN_EQUIPMENTMASTER' ,    'IDS_TSK_EQUIPMENTCAL', 4),
        ('IDS_SCN_EQUIPMENTMASTER' ,    'IDS_TSK_EQUIPMENTMAINT',   5),
        ('IDS_SCN_EQUIPMENTMASTER' ,    'IDS_TSK_EQUIPMENTMASTER',  1),
        ('IDS_SCN_EQUIPMENTMASTER' ,    'IDS_TSK_ACTDEACT', 6),
        ('IDS_SCN_EQUIPMENTMASTER' ,    'IDS_TSK_MAINTANN', 7),

        ('IDS_SCN_EQUIPMENT' ,  'IDS_SCN_EQUIPMENTCATEGORY' ,1),
        ('IDS_SCN_EQUIPMENT' ,  'IDS_TSK_EQUIPMENTTYPE' ,2),

        ('IDS_SCN_USERGROUP'    ,'IDS_SCN_USERGROUP',   1),
        ('IDS_SCN_USERGROUP',   'IDS_TSK_ACTDEACT', 2),

        ('IDS_SCN_USERMASTER',  'IDS_SCN_USERMASTER'    ,1),
        ('IDS_SCN_USERMASTER',  'IDS_TSK_ACTDEACTUSERMASTER'    ,2),
        ('IDS_SCN_USERMASTER',  'IDS_TSK_RESETPASSWORD' ,3),
        ('IDS_SCN_USERMASTER',  'IDS_TSK_RETIRE'    ,4),
        ('IDS_SCN_USERMASTER',  'IDS_TSK_UNLOCK'    ,5),

        ('IDS_SCN_USERRIGHTS',  'IDS_SCN_USERRIGHTS'    ,1),

        ('IDS_SCN_PROJECTMASTER',   'IDS_SCN_PROJECTMASTER' ,1),
        ('IDS_SCN_PROJECTMASTER',   'IDS_SCN_PROJECTTEAM'   ,2),
        ('IDS_SCN_PROJECTMASTER',   'IDS_SCN_TASKMASTER'    ,3),

        ('IDS_SCN_TEMPLATEWORKFLOW',    'IDS_SCN_ORDERWORKLOW'  ,1),
        ('IDS_SCN_TEMPLATEWORKFLOW',    'IDS_SCN_TEMPLATEWORKFLOW'  ,2),

        ('IDS_SCN_PARSER',  'IDS_SCN_DELIMITER' ,1),
        ('IDS_SCN_PARSER',  'IDS_SCN_INSTRUMENTCATEGORY'    ,2),
        ('IDS_SCN_PARSER',  'IDS_SCN_INSTRUMENTMASTER'  ,3),
        ('IDS_SCN_PARSER',  'IDS_SCN_METHODDELIMITER'   ,4),
        ('IDS_SCN_PARSER',  'IDS_SCN_METHODMASTER'  ,5),

        ('IDS_SCN_BARCODEMASTER',   'IDS_TSK_BARCODEMASTER',    1),

        ('IDS_SCN_PASSWORDPOLICY',  'IDS_SCN_PASSWORDPOLICY',   1),

        ('IDS_SCN_LOGBOOK', 'IDS_SCN_LOGBOOK'   ,1),
        ('IDS_SCN_LOGBOOK', 'IDS_TSK_ADDLOGBOOK'    ,2),
        ('IDS_SCN_LOGBOOK', 'IDS_TSK_EDITLOGBOOK'   ,3),
        ('IDS_SCN_LOGBOOK', 'IDS_TSK_RETIRELOGBOOK' ,4),
        ('IDS_SCN_LOGBOOK', 'IDS_TSK_REVIEWLOGBOOK' ,5),

        ('IDS_SCN_REPORTS', 'IDS_SCN_REPORTDESIGNER'    ,1),
        ('IDS_SCN_REPORTS', 'IDS_SCN_REPORTDESIGNERNEWFOLDER'   ,2),
        ('IDS_SCN_REPORTS', 'IDS_TSK_INSERT'    ,3),
        ('IDS_SCN_REPORTS', 'IDS_TSK_NEWTEMPLATE'   ,4),
        ('IDS_SCN_REPORTS', 'IDS_TSK_SAVE'  ,5),

        ('IDS_SCN_REPORTVIEVER' ,'IDS_SCN_REPORTVIEWER' ,1),
        ('IDS_SCN_REPORTVIEVER' ,'IDS_SCN_REPORTVIEWERNEWFOLDER'    ,2),
        ('IDS_SCN_REPORTVIEVER' ,'IDS_TSK_NEWTEMPLATER' ,3),
        ('IDS_SCN_REPORTVIEVER' ,'IDS_TSK_SAVE' ,4),

        ('IDS_SCN_REPORTMAPPER' ,'IDS_TSK_REPORTMAPPING'    ,1),

        ('IDS_SCN_AUDITTRAILHIS',   'IDS_SCN_AUDITTRAILHIS' ,1),
        ('IDS_SCN_AUDITTRAILHIS',   'IDS_TSK_CREATEARCHIVE' ,2),
        ('IDS_SCN_AUDITTRAILHIS',   'IDS_TSK_EXPORT'    ,3),
        ('IDS_SCN_AUDITTRAILHIS',   'IDS_TSK_OPENARCHIVE'   ,4),
        ('IDS_SCN_AUDITTRAILHIS',   'IDS_TSK_REVIEW'    ,5),
        ('IDS_SCN_AUDITTRAILHIS',   'IDS_TSK_REVIEWHISTORY' ,6),

        ('IDS_SCN_CFRSETTINGS', 'IDS_SCN_CFRSETTINGS'   ,1),

        ('IDS_SCN_AUDITTRAILCONFIG',    'IDS_SCN_AUDITTRAILCONFIG'  ,1)

) AS v(screenname, displaytopic, tasksequence)
WHERE l.screenname = v.screenname AND l.displaytopic = v.displaytopic;

UPDATE lsusergrouprights AS l
SET tasksequence = v.tasksequence
FROM (
    VALUES
        ('IDS_SCN_GENRAL', 'IDS_TSK_GLOBALSEARCH', 1),
        ('IDS_SCN_GENRAL', 'IDS_TSK_SWITCHROLE', 2),
        ('IDS_SCN_GENRAL', 'IDS_TSK_SWITCHSITE', 3),

        ('IDS_SCN_DASHBOARD', 'IDS_TSK_ACTIVITIES', 1),
        ('IDS_SCN_DASHBOARD', 'IDS_TSK_ACTIVIWIDGET', 2),
        ('IDS_SCN_DASHBOARD', 'IDS_TSK_ORDEROVERVIEW', 3),
        ('IDS_SCN_DASHBOARD', 'IDS_TSK_TEMPLATEOVERVIEW', 4),
        ('IDS_SCN_DASHBOARD', 'IDS_TSK_REGISTER', 5),

        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_AUTOREGORDER'  ,1),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_CANCELORDER'   ,2),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_COPYLINK'  ,3),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_DUEDATE'   ,4),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_ELNTASKORDER'  ,5),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_EXPORTEXCEL'   ,6),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_EXPORTJSON'    ,7),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_FOLDERCREATION'    ,8),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_FOLDERDEL' ,9),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_MANAGEEXCEL'   ,10),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_MOVEORDERS'    ,11),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_NEWFOLDER' ,12),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_ORDERSHAREDBYME'   ,13),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_ORDERSHAREDTOME'   ,14),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_PROJECTTEAM'   ,15),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_REGISTER'  ,16),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_RESEARCHACTIVITY'  ,17),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_SHARE' ,18),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_SHEETEVALUATION'   ,20),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_SHEETORDEREXPORT'  ,21),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_SHOWVERSION'   ,22),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_TRANHISTORY'   ,23),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_UPLOADSHEETORDER'  ,24),
         ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_CLONEORDER'  ,25),

        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ADDEDITOR',    1),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ADDREFF',  2),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ADDRESEQUIPMENT',  3),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ADDRESMATERIAL',   4),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ADDRESULT',    5),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ADDSEC',   6),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ADDSTEP',  7),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_AUTOREGORDER', 8),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_CANCELORDER',  9),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_COPYLINK', 10),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_DUEDATE',  11),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_DYNAMICPROTOCOL',  12),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ELNPROTOCOL',  13),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_EXPORTEXCEL',  14),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_EXPORTJSON',   15),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_FOLDERCREATIONPROTOCOL',   16),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_FOLDERDEL',    17),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_MOVEORDERSPROTOCOL',   18),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ORDERSHAREDBYMEPROTOCOL',  19),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ORDERSHAREDTOMEPROTOCOL',  20),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_PROJECTTEAM',  21),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_REGISTER', 22),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_SHARE',    23),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_NEWFOLDER',24),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_SHOWVERSION',  25),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_TRANHISTORY',  26),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_UPLOADPROTOCOLORDER',  27),

        ('IDS_SCN_UNLOCKORDERS','IDS_SCN_UNLOCKORDERS', 1),

        ('IDS_SCN_SHEETTEMPLATE'    , 'IDS_SCN_SHEETTEMPLATE'   ,1),
        ('IDS_SCN_SHEETTEMPLATE'    , 'IDS_TSK_NEW' ,2),
        ('IDS_SCN_SHEETTEMPLATE'    , 'IDS_TSK_RETIRE'  ,3),
        ('IDS_SCN_SHEETTEMPLATE'    , 'IDS_TSK_SHARE'   ,4),
        ('IDS_SCN_SHEETTEMPLATE'    , 'IDS_TSK_SHEETTEMPEXPORT' ,5),
        ('IDS_SCN_SHEETTEMPLATE'    , 'IDS_TSK_SHOWVERSION' ,6),
        ('IDS_SCN_SHEETTEMPLATE'    , 'IDS_TSK_TEMPLATESHAREDBYME'  ,7),
        ('IDS_SCN_SHEETTEMPLATE'    , 'IDS_TSK_TEMPLATESHAREDTOME'  ,8),
        ('IDS_SCN_SHEETTEMPLATE'    , 'IDS_TSK_TRANHISTORY' ,9),
        ('IDS_SCN_SHEETTEMPLATE'    , 'IDS_TSK_VISIBLITY' ,10),
        ('IDS_SCN_SHEETTEMPLATE'    , 'IDS_TSK_EDIT',11),

        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_SCN_PROTOCOLTEMPLATE'  ,1),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_ADDEDITOR' ,2),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_ADDREFF'   ,3),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_ADDRESEQUIPMENT'   ,4),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_ADDRESMATERIAL'    ,5),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_ADDRESULT' ,6),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_ADDSEC'    ,7),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_ADDSTEP'   ,8),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_EXPORTJSON'    ,9),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_EXPORTPDF' ,10),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_IMPORTJSON'    ,11),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_NEW'   ,12),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_NEWSTEP'   ,13),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_PROTOCOLTEMPSHAREBYME' ,14),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_PROTOCOLTEMPSHARETOME' ,15),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_RETIRE'    ,16),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_SHARE' ,17),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_SHOWVERSION'   ,18),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_TRANHISTORY'   ,19),
        ('IDS_SCN_PROTOCOLTEMPLATE' ,   'IDS_TSK_VISIBLITY' ,20),

        ('IDS_SCN_TEMPLATEMAPPING', 'IDS_TSK_PROTOCOL'  ,1),
        ('IDS_SCN_TEMPLATEMAPPING', 'IDS_TSK_SHEET' ,2),

        ('IDS_SCN_MATERIALMGMT' , 'IDS_TSK_ADDATTACH',  1),
        ('IDS_SCN_MATERIALMGMT' , 'IDS_TSK_ADDCAT', 2),
        ('IDS_SCN_MATERIALMGMT' , 'IDS_TSK_ADDHYPLINK', 3),
        ('IDS_SCN_MATERIALMGMT' , 'IDS_TSK_ADDMAT', 4),
        ('IDS_SCN_MATERIALMGMT' , 'IDS_TSK_ADDSTOCK',   5),
        ('IDS_SCN_MATERIALMGMT' , 'IDS_TSK_ADDTYP', 6),
        ('IDS_SCN_MATERIALMGMT' , 'IDS_TSK_ASSIGNPROJ', 7),
        ('IDS_SCN_MATERIALMGMT' , 'IDS_TSK_CFINWARD',   8),
        ('IDS_SCN_MATERIALMGMT' , 'IDS_TSK_CFMM',   9),
        ('IDS_SCN_MATERIALMGMT' , 'IDS_TSK_CREATEINV',  10),
        ('IDS_SCN_MATERIALMGMT' , 'IDS_TSK_DISPOSE',    11),
        ('IDS_SCN_MATERIALMGMT' , 'IDS_TSK_EDITMAT',    12),
        ('IDS_SCN_MATERIALMGMT' , 'IDS_TSK_IMPORT', 13),
        ('IDS_SCN_MATERIALMGMT' , 'IDS_TSK_OPEN',   14),
        ('IDS_SCN_MATERIALMGMT' , 'IDS_TSK_RELEASE',    15),
        ('IDS_SCN_MATERIALMGMT' , 'IDS_TSK_RESTOCK',    16),
        ('IDS_SCN_MATERIALMGMT' , 'IDS_TSK_VIEWINV',    17),
        ('IDS_SCN_MATERIALMGMT' , 'IDS_TSK_BARCODESCANNER', 18),

        ('IDS_SCN_SAMPLEMGMT' , 'IDS_TSK_ADDCAT',   1),
        ('IDS_SCN_SAMPLEMGMT' , 'IDS_TSK_ADDSAMPLE',    2),
        ('IDS_SCN_SAMPLEMGMT' , 'IDS_TSK_ADDTYP',   3),
        ('IDS_SCN_SAMPLEMGMT' , 'IDS_TSK_EDITSAMPLE',   4),
        ('IDS_SCN_SAMPLEMGMT' , 'IDS_TSK_IMPORT',   5),
        ('IDS_SCN_SAMPLEMGMT' , 'IDS_TSK_OPEN', 6),

        ('IDS_SCN_MATERIALTYPEPARAMS' , 'IDS_SCN_GRADEMASTER'   ,1),
        ('IDS_SCN_MATERIALTYPEPARAMS' , 'IDS_SCN_MANUFACTURER'  ,2),
        ('IDS_SCN_MATERIALTYPEPARAMS' , 'IDS_SCN_MATERIALCATEGORY'  ,3),
        ('IDS_SCN_MATERIALTYPEPARAMS' , 'IDS_SCN_MATERIALTYPEPARAMS'    ,4),
        ('IDS_SCN_MATERIALTYPEPARAMS' , 'IDS_SCN_STORAGELOCATION'   ,6),
        ('IDS_SCN_MATERIALTYPEPARAMS' , 'IDS_SCN_SUPPLIER'  ,7),
        ('IDS_SCN_MATERIALTYPEPARAMS' , 'IDS_SCN_UNITMASTER'    ,8),
        ('IDS_SCN_MATERIALTYPEPARAMS' , 'IDS_TSK_SAMPLECAT' ,9),
        ('IDS_SCN_MATERIALTYPEPARAMS' , 'IDS_TSK_SAMPLETYPE' ,10),
        
        ('IDS_SCN_EQUIPMENTMASTER' ,    'IDS_TSK_CALIBRATE',    2),
        ('IDS_SCN_EQUIPMENTMASTER' ,    'IDS_TSK_EQUIPMENTACT', 3),
        ('IDS_SCN_EQUIPMENTMASTER' ,    'IDS_TSK_EQUIPMENTCAL', 4),
        ('IDS_SCN_EQUIPMENTMASTER' ,    'IDS_TSK_EQUIPMENTMAINT',   5),
        ('IDS_SCN_EQUIPMENTMASTER' ,    'IDS_TSK_EQUIPMENTMASTER',  1),
        ('IDS_SCN_EQUIPMENTMASTER' ,    'IDS_TSK_ACTDEACT', 6),
        ('IDS_SCN_EQUIPMENTMASTER' ,    'IDS_TSK_MAINTANN', 7),

        ('IDS_SCN_EQUIPMENT' ,  'IDS_SCN_EQUIPMENTCATEGORY' ,1),
        ('IDS_SCN_EQUIPMENT' ,  'IDS_TSK_EQUIPMENTTYPE' ,2),

        ('IDS_SCN_USERGROUP'    ,'IDS_SCN_USERGROUP',   1),
        ('IDS_SCN_USERGROUP',   'IDS_TSK_ACTDEACT', 2),

        ('IDS_SCN_USERMASTER',  'IDS_SCN_USERMASTER'    ,1),
        ('IDS_SCN_USERMASTER',  'IDS_TSK_ACTDEACTUSERMASTER'    ,2),
        ('IDS_SCN_USERMASTER',  'IDS_TSK_RESETPASSWORD' ,3),
        ('IDS_SCN_USERMASTER',  'IDS_TSK_RETIRE'    ,4),
        ('IDS_SCN_USERMASTER',  'IDS_TSK_UNLOCK'    ,5),

        ('IDS_SCN_USERRIGHTS',  'IDS_SCN_USERRIGHTS'    ,1),

        ('IDS_SCN_PROJECTMASTER',   'IDS_SCN_PROJECTMASTER' ,1),
        ('IDS_SCN_PROJECTMASTER',   'IDS_SCN_PROJECTTEAM'   ,2),
        ('IDS_SCN_PROJECTMASTER',   'IDS_SCN_TASKMASTER'    ,3),

        ('IDS_SCN_TEMPLATEWORKFLOW',    'IDS_SCN_ORDERWORKLOW'  ,1),
        ('IDS_SCN_TEMPLATEWORKFLOW',    'IDS_SCN_TEMPLATEWORKFLOW'  ,2),

        ('IDS_SCN_PARSER',  'IDS_SCN_DELIMITER' ,1),
        ('IDS_SCN_PARSER',  'IDS_SCN_INSTRUMENTCATEGORY'    ,2),
        ('IDS_SCN_PARSER',  'IDS_SCN_INSTRUMENTMASTER'  ,3),
        ('IDS_SCN_PARSER',  'IDS_SCN_METHODDELIMITER'   ,4),
        ('IDS_SCN_PARSER',  'IDS_SCN_METHODMASTER'  ,5),

        ('IDS_SCN_BARCODEMASTER',   'IDS_TSK_BARCODEMASTER',    1),

        ('IDS_SCN_PASSWORDPOLICY',  'IDS_SCN_PASSWORDPOLICY',   1),

        ('IDS_SCN_LOGBOOK', 'IDS_SCN_LOGBOOK'   ,1),
        ('IDS_SCN_LOGBOOK', 'IDS_TSK_ADDLOGBOOK'    ,2),
        ('IDS_SCN_LOGBOOK', 'IDS_TSK_EDITLOGBOOK'   ,3),
        ('IDS_SCN_LOGBOOK', 'IDS_TSK_RETIRELOGBOOK' ,4),
        ('IDS_SCN_LOGBOOK', 'IDS_TSK_REVIEWLOGBOOK' ,5),

        ('IDS_SCN_REPORTS', 'IDS_SCN_REPORTDESIGNER'    ,1),
        ('IDS_SCN_REPORTS', 'IDS_SCN_REPORTDESIGNERNEWFOLDER'   ,2),
        ('IDS_SCN_REPORTS', 'IDS_TSK_INSERT'    ,3),
        ('IDS_SCN_REPORTS', 'IDS_TSK_NEWTEMPLATE'   ,4),
        ('IDS_SCN_REPORTS', 'IDS_TSK_SAVE'  ,5),

        ('IDS_SCN_REPORTVIEVER' ,'IDS_SCN_REPORTVIEWER' ,1),
        ('IDS_SCN_REPORTVIEVER' ,'IDS_SCN_REPORTVIEWERNEWFOLDER'    ,2),
        ('IDS_SCN_REPORTVIEVER' ,'IDS_TSK_NEWTEMPLATER' ,3),
        ('IDS_SCN_REPORTVIEVER' ,'IDS_TSK_SAVE' ,4),

        ('IDS_SCN_REPORTMAPPER' ,'IDS_TSK_REPORTMAPPING'    ,1),

        ('IDS_SCN_AUDITTRAILHIS',   'IDS_SCN_AUDITTRAILHIS' ,1),
        ('IDS_SCN_AUDITTRAILHIS',   'IDS_TSK_CREATEARCHIVE' ,2),
        ('IDS_SCN_AUDITTRAILHIS',   'IDS_TSK_EXPORT'    ,3),
        ('IDS_SCN_AUDITTRAILHIS',   'IDS_TSK_OPENARCHIVE'   ,4),
        ('IDS_SCN_AUDITTRAILHIS',   'IDS_TSK_REVIEW'    ,5),
        ('IDS_SCN_AUDITTRAILHIS',   'IDS_TSK_REVIEWHISTORY' ,6),

        ('IDS_SCN_CFRSETTINGS', 'IDS_SCN_CFRSETTINGS'   ,1),

        ('IDS_SCN_AUDITTRAILCONFIG',    'IDS_SCN_AUDITTRAILCONFIG'  ,1)

) AS v(screenname, displaytopic, tasksequence)
WHERE l.screenname = v.screenname AND l.displaytopic = v.displaytopic;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_MOVEORDERS', 'IDS_MDL_REPORTS', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_REPORTS' ,44
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_MOVEORDERS' and screenname='IDS_SCN_REPORTS' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (303, 'IDS_TSK_MOVEORDERS', 'IDS_MDL_REPORTS', 'IDS_SCN_REPORTS', '0', 'NA', 'NA', 'NA', '0,0,0', 44) 
ON CONFLICT (orderno) DO NOTHING;

UPDATE lsusergrouprightsmaster AS l
SET tasksequence = v.tasksequence
FROM (
    VALUES
      ('IDS_SCN_REPORTS' , 'IDS_TSK_MOVEORDERS' ,2)
) AS v(screenname, displaytopic, tasksequence)
WHERE l.screenname = v.screenname AND l.displaytopic = v.displaytopic; 

UPDATE lsusergrouprights AS l
SET tasksequence = v.tasksequence
FROM (
    VALUES
      ('IDS_SCN_REPORTS' , 'IDS_TSK_MOVEORDERS' ,2)
) AS v(screenname, displaytopic, tasksequence)
WHERE l.screenname = v.screenname AND l.displaytopic = v.displaytopic;

UPDATE lsusergrouprightsmaster set sdelete = '0',sedit='0',screate='0' where displaytopic = 'IDS_SCN_REPORTDESIGNERNEWFOLDER';
UPDATE lsusergrouprights set sdelete = '0' where displaytopic = 'IDS_SCN_REPORTDESIGNERNEWFOLDER' and sdelete = 'NA' and sallow = '0';
UPDATE lsusergrouprights set sdelete = '1' where displaytopic = 'IDS_SCN_REPORTDESIGNERNEWFOLDER' and sdelete = 'NA' and sallow = '1';
UPDATE lsusergrouprights set sedit = '0' where displaytopic = 'IDS_SCN_REPORTDESIGNERNEWFOLDER' and sedit = 'NA' and sallow = '0';
UPDATE lsusergrouprights set sedit = '1' where displaytopic = 'IDS_SCN_REPORTDESIGNERNEWFOLDER' and sedit = 'NA' and sallow = '1';
UPDATE lsusergrouprights set screate = '0' where displaytopic = 'IDS_SCN_REPORTDESIGNERNEWFOLDER' and screate = 'NA' and sallow = '0';
UPDATE lsusergrouprights set screate = '1' where displaytopic = 'IDS_SCN_REPORTDESIGNERNEWFOLDER' and screate = 'NA' and sallow = '1';

INSERT INTO lsusergrouprightsmaster (
    orderno, displaytopic, modulename, screenname,
    sallow, screate, sdelete, sedit, status,
    sequenceorder, tasksequence
)
SELECT
    COALESCE((SELECT MAX(orderno) + 1 FROM lsusergrouprightsmaster), 1),
    'IDS_TSK_DATEPREFERENCE',
    'IDS_MDL_GENRAL',
    'IDS_SCN_GENRAL',
    '0', 'NA', 'NA', 'NA', '0,0,0',
    0, 4
WHERE NOT EXISTS (
    SELECT 1
    FROM lsusergrouprightsmaster
    WHERE displaytopic = 'IDS_TSK_DATEPREFERENCE'
      AND screenname = 'IDS_SCN_GENRAL'
      AND modulename = 'IDS_MDL_GENRAL'
      AND sequenceorder = 0
);



INSERT INTO lsusergrouprights (
  displaytopic, modulename, createdby, sallow, screate, sdelete, sedit,
  lssitemaster_sitecode, usergroupid_usergroupcode, screenname,
  sequenceorder, tasksequence
)
SELECT
  'IDS_TSK_DATEPREFERENCE', 'IDS_MDL_GENRAL', 'administrator', '1', 'NA', 'NA', 'NA',
  1, 1, 'IDS_SCN_GENRAL',
  0, 4
WHERE NOT EXISTS (
  SELECT 1
  FROM lsusergrouprights
  WHERE displaytopic = 'IDS_TSK_DATEPREFERENCE'
    AND screenname = 'IDS_SCN_GENRAL'
    AND usergroupid_usergroupcode = 1
    AND lssitemaster_sitecode = 1
);

UPDATE lsusergrouprights set screate = '1' where displaytopic = 'IDS_SCN_REPORTDESIGNERNEWFOLDER' and screate = 'NA' and sallow = '1';

UPDATE lsusergrouprightsmaster AS l
SET tasksequence = v.tasksequence
FROM (
    VALUES
       ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_AUTOREGORDER'  ,1),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_CANCELORDER'   ,2),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_COPYLINK'  ,3),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_DUEDATE'   ,4),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_ELNTASKORDER'  ,5),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_RESEARCHACTIVITY'  ,6),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_MANAGEEXCEL'   ,7),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_SHEETEVALUATION'   ,8),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_REGISTER'  ,9),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_EXPORTEXCEL'   ,10),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_EXPORTJSON'    ,11),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_FOLDERCREATION'    ,12),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_FOLDERDEL' ,13),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_MOVEORDERS'    ,14),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_NEWFOLDER' ,15),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_ORDERSHAREDBYME'   ,16),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_ORDERSHAREDTOME'   ,17),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_PROJECTTEAM'   ,18),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_SHARE' ,19),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_SHEETORDEREXPORT'  ,20),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_SHOWVERSION'   ,21),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_TRANHISTORY'   ,22),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_UPLOADSHEETORDER'  ,23),

        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_DYNAMICPROTOCOL',  12),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ELNPROTOCOL',  12),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_REGISTER', 13)
) AS v(screenname, displaytopic, tasksequence)
WHERE l.screenname = v.screenname AND l.displaytopic = v.displaytopic;

UPDATE lsusergrouprights AS l
SET tasksequence = v.tasksequence
FROM (
    VALUES
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_AUTOREGORDER'  ,1),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_CANCELORDER'   ,2),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_COPYLINK'  ,3),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_DUEDATE'   ,4),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_ELNTASKORDER'  ,5),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_RESEARCHACTIVITY'  ,6),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_MANAGEEXCEL'   ,7),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_SHEETEVALUATION'   ,8),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_REGISTER'  ,9),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_EXPORTEXCEL'   ,10),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_EXPORTJSON'    ,11),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_FOLDERCREATION'    ,12),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_FOLDERDEL' ,13),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_MOVEORDERS'    ,14),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_NEWFOLDER' ,15),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_ORDERSHAREDBYME'   ,16),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_ORDERSHAREDTOME'   ,17),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_PROJECTTEAM'   ,18),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_SHARE' ,19),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_SHEETORDEREXPORT'  ,20),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_SHOWVERSION'   ,21),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_TRANHISTORY'   ,22),
        ('IDS_SCN_SHEETORDERS' ,    'IDS_TSK_UPLOADSHEETORDER'  ,23),

        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_DYNAMICPROTOCOL',  12),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_ELNPROTOCOL',  12),
        ('IDS_SCN_PROTOCOLORDERS' , 'IDS_TSK_REGISTER', 13)

) AS v(screenname, displaytopic, tasksequence)
WHERE l.screenname = v.screenname AND l.displaytopic = v.displaytopic;

ALTER TABLE IF EXISTS lsprojectmaster ADD COLUMN IF NOT EXISTS description TEXT;

update lsaudittrailconfiguration set taskname = 'IDS_TSK_SAVEMATERIALCAT' where screenname = 'IDS_SCN_MATERIALCATEGORY' 
and taskname = 'IDS_TSK_ADD';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_SAVEMATERIALCAT' where screenname = 'IDS_SCN_MATERIALCATEGORY' 
and taskname = 'IDS_TSK_ADD';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_SAVEMATERIALCAT' where screenname = 'IDS_SCN_MATERIALCATEGORY' 
and taskname = 'IDS_TSK_SAVE';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_SAVEMATERIALCAT' where screenname = 'IDS_SCN_MATERIALCATEGORY' 
and taskname = 'IDS_TSK_SAVE';

update lsaudittrailconfiguration set taskname = 'IDS_TSK_EDITMATERIALCAT' where screenname = 'IDS_SCN_MATERIALCATEGORY' 
and taskname = 'IDS_TSK_EDIT';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_EDITMATERIALCAT' where screenname = 'IDS_SCN_MATERIALCATEGORY' 
and taskname = 'IDS_TSK_EDIT';

update lsaudittrailconfiguration set taskname = 'IDS_TSK_DELETEMATERIALCAT' where screenname = 'IDS_SCN_MATERIALCATEGORY' 
and taskname = 'IDS_TSK_DELETE';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_DELETEMATERIALCAT' where screenname = 'IDS_SCN_MATERIALCATEGORY' 
and taskname = 'IDS_TSK_DELETE';

update lsaudittrailconfiguration set taskname = 'IDS_TSK_SAVESTORAGE' where screenname = 'IDS_SCN_STORAGELOCATION' 
and taskname = 'IDS_TSK_SAVE';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_SAVESTORAGE' where screenname = 'IDS_SCN_STORAGELOCATION' 
and taskname = 'IDS_TSK_SAVE';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_DELETESTORAGE' where screenname = 'IDS_SCN_STORAGELOCATION' 
and taskname = 'IDS_TSK_RETIRE';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_DELETESTORAGE' where screenname = 'IDS_SCN_STORAGELOCATION' 
and taskname = 'IDS_TSK_RETIRE';

update lsaudittrailconfiguration set taskname = 'IDS_TSK_SAVESTORAGE' where screenname = 'IDS_SCN_STORAGELOCATION' 
and taskname = 'IDS_TSK_ADD';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_SAVESTORAGE' where screenname = 'IDS_SCN_STORAGELOCATION' 
and taskname = 'IDS_TSK_ADD';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_DELETESTORAGE' where screenname = 'IDS_SCN_STORAGELOCATION' 
and taskname = 'IDS_TSK_DELETE';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_DELETESTORAGE' where screenname = 'IDS_SCN_STORAGELOCATION' 
and taskname = 'IDS_TSK_DELETE';

update lsaudittrailconfiguration set taskname = 'IDS_TSK_EDITSTORAGE' where screenname = 'IDS_SCN_STORAGELOCATION' 
and taskname = 'IDS_TSK_EDIT';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_EDITSTORAGE' where screenname = 'IDS_SCN_STORAGELOCATION' 
and taskname = 'IDS_TSK_EDIT';

update lsaudittrailconfiguration set taskname = 'IDS_TSK_SAVEMATERIAL' where screenname = 'IDS_SCN_MATERIALMGMT' 
and taskname = 'IDS_TSK_SAVEM';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_SAVEMATERIAL' where screenname = 'IDS_SCN_MATERIALMGMT' 
and taskname = 'IDS_TSK_SAVEM';

update lsaudittrailconfiguration set taskname = 'IDS_TSK_EDITMATERIAL' where screenname = 'IDS_SCN_MATERIALMGMT' 
and taskname = 'IDS_TSK_EDITM';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_EDITMATERIAL' where screenname = 'IDS_SCN_MATERIALMGMT' 
and taskname = 'IDS_TSK_EDITM';

update lsaudittrailconfiguration set taskname = 'IDS_TSK_SAVEMATERIALINV' where screenname = 'IDS_SCN_MATERIALMGMT' 
and taskname = 'IDS_TSK_SAVEI';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_SAVEMATERIALINV' where screenname = 'IDS_SCN_MATERIALMGMT' 
and taskname = 'IDS_TSK_SAVEI';

update lsaudittrailconfiguration set taskname = 'IDS_TSK_EDITMATERIALINV' where screenname = 'IDS_SCN_MATERIALMGMT' 
and taskname = 'IDS_TSK_EDITI';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_EDITMATERIALINV' where screenname = 'IDS_SCN_MATERIALMGMT' 
and taskname = 'IDS_TSK_EDITI';

insert into LSpreferences (serialno,tasksettings,valuesettings) values(9,'ssologin','') on conflict(serialno) do nothing;

Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(231,0,'IDS_MDL_INVENTORY',33,'IDS_SCN_GRADEMASTER','IDS_TSK_EDIT') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(232,0,'IDS_MDL_INVENTORY',34,'IDS_SCN_SUPPLIER','IDS_TSK_EDIT') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(233,0,'IDS_MDL_INVENTORY',35,'IDS_SCN_MANUFACTURER','IDS_TSK_EDIT') ON CONFLICT(serialno)DO NOTHING;
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) values(234,0,'IDS_MDL_INVENTORY',40,'IDS_SCN_SAMPLETYPE','IDS_TSK_RETIRE') ON CONFLICT(serialno)DO NOTHING;

INSERT INTO lsaudittrailconfigmaster 
    (serialno, manualaudittrail, modulename, ordersequnce, screenname, taskname)
SELECT 235, 0, 'IDS_MDL_INVENTORY', 38, 'IDS_SCN_MATERIALMGMT', 'IDS_TSK_SAVEMATERIAL'
WHERE NOT EXISTS (
    SELECT 1 
    FROM lsaudittrailconfigmaster 
    WHERE modulename = 'IDS_MDL_INVENTORY'
      AND screenname = 'IDS_SCN_MATERIALMGMT'
      AND taskname = 'IDS_TSK_SAVEMATERIAL'
);

INSERT INTO lsaudittrailconfigmaster 
    (serialno, manualaudittrail, modulename, ordersequnce, screenname, taskname)
SELECT 236, 0, 'IDS_MDL_INVENTORY', 38, 'IDS_SCN_MATERIALMGMT', 'IDS_TSK_EDITMATERIAL'
WHERE NOT EXISTS (
    SELECT 1 
    FROM lsaudittrailconfigmaster 
    WHERE modulename = 'IDS_MDL_INVENTORY'
      AND screenname = 'IDS_SCN_MATERIALMGMT'
      AND taskname = 'IDS_TSK_EDITMATERIAL'
);

INSERT INTO lsaudittrailconfigmaster 
    (serialno, manualaudittrail, modulename, ordersequnce, screenname, taskname)
SELECT 237, 0, 'IDS_MDL_INVENTORY', 38, 'IDS_SCN_MATERIALMGMT', 'IDS_TSK_EDITMATERIALINV'
WHERE NOT EXISTS (
    SELECT 1 
    FROM lsaudittrailconfigmaster 
    WHERE modulename = 'IDS_MDL_INVENTORY'
      AND screenname = 'IDS_SCN_MATERIALMGMT'
      AND taskname = 'IDS_TSK_EDITMATERIALINV'
);

INSERT INTO lsaudittrailconfigmaster 
    (serialno, manualaudittrail, modulename, ordersequnce, screenname, taskname)
SELECT 238, 0, 'IDS_MDL_TEMPLATES', 4, 'IDS_SCN_SHEETTEMPLATES', 'IDS_TSK_SHEETTEMPSHARE'
WHERE NOT EXISTS (
    SELECT 1 
    FROM lsaudittrailconfigmaster 
    WHERE modulename = 'IDS_MDL_TEMPLATES'
      AND screenname = 'IDS_SCN_SHEETTEMPLATES'
      AND taskname = 'IDS_TSK_SHEETTEMPSHARE'
);


INSERT INTO lsaudittrailconfigmaster 
    (serialno, manualaudittrail, modulename, ordersequnce, screenname, taskname)
SELECT 239, 0, 'IDS_MDL_ORDERS', 2, 'IDS_SCN_PROTOCOLORDERS', 'IDS_TSK_PROTOCOLSHARE'
WHERE NOT EXISTS (
    SELECT 1 
    FROM lsaudittrailconfigmaster 
    WHERE modulename = 'IDS_MDL_ORDERS'
      AND screenname = 'IDS_SCN_PROTOCOLORDERS'
      AND taskname = 'IDS_TSK_PROTOCOLSHARE'
);

WITH maxval AS (
    SELECT COALESCE(MAX(serialno), 0) AS max_sn
    FROM lsaudittrailconfigmaster
)
INSERT INTO lsaudittrailconfigmaster
    (serialno, manualaudittrail, modulename, ordersequnce, screenname, taskname)
SELECT max_sn + 1, 0, 'IDS_MDL_SETUP', 10, 'IDS_SCN_USERMASTER', 'IDS_SCN_IMPORT'
FROM maxval
WHERE NOT EXISTS (
    SELECT 1
    FROM lsaudittrailconfigmaster
    WHERE modulename='IDS_MDL_SETUP'
      AND screenname='IDS_SCN_USERMASTER'
      AND taskname='IDS_SCN_IMPORT'
)
UNION ALL
SELECT max_sn + 2, 0, 'IDS_MDL_SETUP', 8, 'IDS_SCN_USERGROUP', 'IDS_SCN_IMPORT'
FROM maxval
WHERE NOT EXISTS (
    SELECT 1
    FROM lsaudittrailconfigmaster
    WHERE modulename='IDS_MDL_SETUP'
      AND screenname='IDS_SCN_USERGROUP'
      AND taskname='IDS_SCN_IMPORT'
)
UNION ALL
SELECT max_sn + 3, 0, 'IDS_MDL_INVENTORY', 38, 'IDS_SCN_MATERIALMGMT', 'IDS_SCN_IMPORT'
FROM maxval
WHERE NOT EXISTS (
    SELECT 1
    FROM lsaudittrailconfigmaster
    WHERE modulename='IDS_MDL_INVENTORY'
      AND screenname='IDS_SCN_MATERIALMGMT'
      AND taskname='IDS_SCN_IMPORT'
)
UNION ALL
SELECT max_sn + 4, 0, 'IDS_MDL_INVENTORY', 39, 'IDS_SCN_SAMPLEMGMT', 'IDS_SCN_IMPORT'
FROM maxval
WHERE NOT EXISTS (
    SELECT 1
    FROM lsaudittrailconfigmaster
    WHERE modulename='IDS_MDL_INVENTORY'
      AND screenname='IDS_SCN_SAMPLEMGMT'
      AND taskname='IDS_SCN_IMPORT'
)
UNION ALL
SELECT max_sn + 5, 0, 'IDS_MDL_INVENTORY', 44, 'IDS_SCN_EQUIPMENT', 'IDS_SCN_IMPORT'
FROM maxval
WHERE NOT EXISTS (
    SELECT 1
    FROM lsaudittrailconfigmaster
    WHERE modulename='IDS_MDL_INVENTORY'
      AND screenname='IDS_SCN_EQUIPMENT'
      AND taskname='IDS_SCN_IMPORT'
);

delete from lsusergrouprightsmaster where screenname = 'IDS_SCN_SHEETORDERS' and displaytopic = 'IDS_TSK_NEWFOLDER';

delete from lsusergrouprights where screenname = 'IDS_SCN_SHEETORDERS' and displaytopic = 'IDS_TSK_NEWFOLDER';
insert into LSpreferences (serialno,tasksettings,valuesettings) values(8,'ReactSpreadConfig','InActive') on conflict(serialno) do nothing;
alter table equipment alter column remarks type character varying(1000);

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, 
usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_RETIRE', 'IDS_MDL_SETUP', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_USERMASTER' ,29
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_RETIRE' and screenname='IDS_SCN_USERMASTER' and usergroupid_usergroupcode = 1); 
				  
update LSusergrouprightsmaster set sedit = '0' where sedit = '1';
update LSusergrouprightsmaster set sallow = '0' where sallow = '1';
update LSusergrouprightsmaster set screate = '0' where screate = '1';
update LSusergrouprightsmaster set sdelete = '0' where sdelete = '1';

update LSusergrouprights set sedit = '1' where sedit = '0' and usergroupid_usergroupcode = 1;
update LSusergrouprights set sallow = '1' where sallow = '0' and usergroupid_usergroupcode = 1;
update LSusergrouprights set screate = '1' where screate = '0' and usergroupid_usergroupcode = 1;
update LSusergrouprights set sdelete = '1' where sdelete = '0' and usergroupid_usergroupcode = 1;

Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) 
SELECT 270,0,'IDS_MDL_TEMPLATES',5,'IDS_SCN_PROTOCOLTEMP','IDS_TSK_RETIRE' WHERE NOT EXISTS (
SELECT 1  FROM lsaudittrailconfigmaster WHERE modulename = 'IDS_MDL_TEMPLATES' AND screenname = 'IDS_SCN_PROTOCOLTEMP' 
AND taskname = 'IDS_TSK_RETIRE');

Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) 
SELECT 271,0,'IDS_MDL_SETUP',10,'IDS_SCN_USERMASTER','IDS_TSK_RESETPASSWORD' WHERE NOT EXISTS (
SELECT 1  FROM lsaudittrailconfigmaster WHERE modulename = 'IDS_MDL_SETUP' AND screenname = 'IDS_SCN_USERMASTER' 
AND taskname = 'IDS_TSK_RESETPASSWORD');

--protocol order--------
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) 
SELECT 205,0,'IDS_MDL_ORDERS',2,'IDS_SCN_PROTOCOLORDERS','IDS_TSK_CANCELORDER' WHERE NOT EXISTS (
SELECT 1  FROM lsaudittrailconfigmaster WHERE modulename = 'IDS_MDL_ORDERS' AND screenname = 'IDS_SCN_PROTOCOLORDERS' 
AND taskname = 'IDS_TSK_CANCELORDER');

Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) 
SELECT 206,0,'IDS_MDL_ORDERS',2,'IDS_SCN_PROTOCOLORDERS','IDS_TSK_AUTOREGORDERCANCEL' WHERE NOT EXISTS (
SELECT 1  FROM lsaudittrailconfigmaster WHERE modulename = 'IDS_MDL_ORDERS' AND screenname = 'IDS_SCN_PROTOCOLORDERS' 
AND taskname = 'IDS_TSK_AUTOREGORDERCANCEL');

Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) 
SELECT 207,0,'IDS_MDL_ORDERS',2,'IDS_SCN_PROTOCOLORDERS','IDS_TSK_FOLDERDEL' WHERE NOT EXISTS (
SELECT 1  FROM lsaudittrailconfigmaster WHERE modulename = 'IDS_MDL_ORDERS' AND screenname = 'IDS_SCN_PROTOCOLORDERS' 
AND taskname = 'IDS_TSK_FOLDERDEL');

--sheet order--------
Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) 
SELECT 208,0,'IDS_MDL_ORDERS',1,'IDS_SCN_SHEETORDERS','IDS_TSK_CANCELORDER' WHERE NOT EXISTS (
SELECT 1  FROM lsaudittrailconfigmaster WHERE modulename = 'IDS_MDL_ORDERS' AND screenname = 'IDS_SCN_SHEETORDERS' 
AND taskname = 'IDS_TSK_CANCELORDER');

Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) 
SELECT 209,0,'IDS_MDL_ORDERS',1,'IDS_SCN_SHEETORDERS','IDS_TSK_AUTOREGORDERCANCEL' WHERE NOT EXISTS (
SELECT 1  FROM lsaudittrailconfigmaster WHERE modulename = 'IDS_MDL_ORDERS' AND screenname = 'IDS_SCN_SHEETORDERS' 
AND taskname = 'IDS_TSK_AUTOREGORDERCANCEL');

Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) 
SELECT 210,0,'IDS_MDL_ORDERS',1,'IDS_SCN_SHEETORDERS','IDS_TSK_FOLDERDEL' WHERE NOT EXISTS (
SELECT 1  FROM lsaudittrailconfigmaster WHERE modulename = 'IDS_MDL_ORDERS' AND screenname = 'IDS_SCN_SHEETORDERS' 
AND taskname = 'IDS_TSK_FOLDERDEL');

Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) 
SELECT 211,0,'IDS_MDL_REPORTS',29,'IDS_SCN_REPORTMAPPING','IDS_TSK_SAVE' WHERE NOT EXISTS (
SELECT 1  FROM lsaudittrailconfigmaster WHERE modulename = 'IDS_MDL_REPORTS' AND screenname = 'IDS_SCN_REPORTMAPPING' 
AND taskname = 'IDS_TSK_SAVE');

Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) 
SELECT 212,0,'IDS_MDL_REPORTS',28,'IDS_SCN_REPORTVIEWER','IDS_TSK_RDOPEN' WHERE NOT EXISTS (
SELECT 1  FROM lsaudittrailconfigmaster WHERE modulename = 'IDS_MDL_REPORTS' AND screenname = 'IDS_SCN_REPORTVIEWER' 
AND taskname = 'IDS_TSK_RDOPEN');

Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) 
SELECT 213,0,'IDS_MDL_INVENTORY',32,'IDS_SCN_MATERIALTYPE','IDS_TSK_SAVE' WHERE NOT EXISTS (
SELECT 1  FROM lsaudittrailconfigmaster WHERE modulename = 'IDS_MDL_INVENTORY' AND screenname = 'IDS_SCN_MATERIALTYPE' 
AND taskname = 'IDS_TSK_SAVE');

Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) 
SELECT 214,0,'IDS_MDL_INVENTORY',32,'IDS_SCN_MATERIALTYPE','IDS_TSK_EDIT' WHERE NOT EXISTS (
SELECT 1  FROM lsaudittrailconfigmaster WHERE modulename = 'IDS_MDL_INVENTORY' AND screenname = 'IDS_SCN_MATERIALTYPE' 
AND taskname = 'IDS_TSK_EDIT');

INSERT INTO lsaudittrailconfigmaster  (serialno, manualaudittrail, modulename, ordersequnce, screenname, taskname)
SELECT 250,0,'IDS_MDL_INVENTORY',33,'IDS_SCN_GRADEMASTER','IDS_TSK_EDIT'
WHERE NOT EXISTS (
    SELECT 1 
    FROM lsaudittrailconfigmaster 
    WHERE modulename = 'IDS_MDL_INVENTORY'
      AND screenname = 'IDS_SCN_GRADEMASTER'
      AND taskname = 'IDS_TSK_EDIT'
);

INSERT INTO lsaudittrailconfigmaster  (serialno, manualaudittrail, modulename, ordersequnce, screenname, taskname)
SELECT 251,0,'IDS_MDL_INVENTORY',34,'IDS_SCN_SUPPLIER','IDS_TSK_EDIT'
WHERE NOT EXISTS (
    SELECT 1 
    FROM lsaudittrailconfigmaster 
    WHERE modulename = 'IDS_MDL_INVENTORY'
      AND screenname = 'IDS_SCN_SUPPLIER'
      AND taskname = 'IDS_TSK_EDIT'
);

INSERT INTO lsaudittrailconfigmaster   (serialno, manualaudittrail, modulename, ordersequnce, screenname, taskname)
SELECT 252,0,'IDS_MDL_INVENTORY',35,'IDS_SCN_MANUFACTURER','IDS_TSK_EDIT'
WHERE NOT EXISTS (
    SELECT 1 
    FROM lsaudittrailconfigmaster 
    WHERE modulename = 'IDS_MDL_INVENTORY'
      AND screenname = 'IDS_SCN_MANUFACTURER'
      AND taskname = 'IDS_TSK_EDIT'
);

INSERT INTO lsaudittrailconfigmaster  (serialno, manualaudittrail, modulename, ordersequnce, screenname, taskname)
SELECT 253,0,'IDS_MDL_INVENTORY',40,'IDS_SCN_SAMPLETYPE','IDS_TSK_RETIRE'
WHERE NOT EXISTS (
    SELECT 1 
    FROM lsaudittrailconfigmaster 
    WHERE modulename = 'IDS_MDL_INVENTORY'
      AND screenname = 'IDS_SCN_SAMPLETYPE'
      AND taskname = 'IDS_TSK_RETIRE'
);

Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) 
SELECT 271,0,'IDS_MDL_ORDERS',2,'IDS_SCN_PROTOCOLORDERS','IDS_TSK_SAVE' WHERE NOT EXISTS (
SELECT 1  FROM lsaudittrailconfigmaster WHERE modulename = 'IDS_MDL_ORDERS' 
AND screenname = 'IDS_SCN_PROTOCOLORDERS' AND taskname = 'IDS_TSK_SAVE');

Insert into lsaudittrailconfigmaster (serialno,manualaudittrail,modulename,ordersequnce,screenname,taskname) 
SELECT 272,0,'IDS_MDL_LOGBOOK',14,'IDS_SCN_LOGBOOK','IDS_TSK_REVIEWLOGBOOK' WHERE NOT EXISTS (
SELECT 1  FROM lsaudittrailconfigmaster WHERE modulename = 'IDS_MDL_LOGBOOK' 
AND screenname = 'IDS_SCN_LOGBOOK' AND taskname = 'IDS_TSK_REVIEWLOGBOOK');


INSERT INTO lsaudittrailconfigmaster   (serialno, manualaudittrail, modulename, ordersequnce, screenname, taskname)
SELECT 160,0,'IDS_MDL_INVENTORY',33,'IDS_SCN_UNITMASTER','IDS_TSK_SAVE'
WHERE NOT EXISTS (
    SELECT 1 
    FROM lsaudittrailconfigmaster 
    WHERE modulename = 'IDS_MDL_INVENTORY'
      AND screenname = 'IDS_SCN_UNITMASTER'
      AND taskname = 'IDS_TSK_SAVE'
);

INSERT INTO lsaudittrailconfigmaster   (serialno, manualaudittrail, modulename, ordersequnce, screenname, taskname)
SELECT 161,0,'IDS_MDL_INVENTORY',33,'IDS_SCN_UNITMASTER','IDS_TSK_EDIT'
WHERE NOT EXISTS (
    SELECT 1 
    FROM lsaudittrailconfigmaster 
    WHERE modulename = 'IDS_MDL_INVENTORY'
      AND screenname = 'IDS_SCN_UNITMASTER'
      AND taskname = 'IDS_TSK_EDIT'
);

INSERT INTO lsaudittrailconfigmaster   (serialno, manualaudittrail, modulename, ordersequnce, screenname, taskname)
SELECT 162,0,'IDS_MDL_INVENTORY',33,'IDS_SCN_UNITMASTER','IDS_TSK_RETIRE'
WHERE NOT EXISTS (
    SELECT 1 
    FROM lsaudittrailconfigmaster 
    WHERE modulename = 'IDS_MDL_INVENTORY'
      AND screenname = 'IDS_SCN_UNITMASTER'
      AND taskname = 'IDS_TSK_RETIRE'
);

INSERT INTO lsaudittrailconfigmaster   (serialno, manualaudittrail, modulename, ordersequnce, screenname, taskname)
SELECT 163,0,'IDS_MDL_INVENTORY',32,'IDS_SCN_MATERIALTYPE','IDS_TSK_RETIRE'
WHERE NOT EXISTS (
    SELECT 1 
    FROM lsaudittrailconfigmaster 
    WHERE modulename = 'IDS_MDL_INVENTORY'
      AND screenname = 'IDS_SCN_MATERIALTYPE'
      AND taskname = 'IDS_TSK_RETIRE'
);

INSERT INTO lsaudittrailconfigmaster (serialno, manualaudittrail, modulename, ordersequnce, screenname, taskname)
SELECT 272, 0, 'IDS_MDL_SETUP', 18, 'IDS_SCN_BARCODEMASTER', 'IDS_TSK_SAVE'
WHERE NOT EXISTS (
    SELECT 1 
    FROM lsaudittrailconfigmaster 
    WHERE modulename = 'IDS_MDL_SETUP'
      AND screenname = 'IDS_SCN_BARCODEMASTER'
      AND taskname = 'IDS_TSK_SAVE'
);

INSERT INTO lsaudittrailconfigmaster (serialno, manualaudittrail, modulename, ordersequnce, screenname, taskname)
SELECT 274, 0, 'IDS_MDL_SETUP', 18, 'IDS_SCN_BARCODEMASTER', 'IDS_TSK_EDIT'
WHERE NOT EXISTS (
    SELECT 1 
    FROM lsaudittrailconfigmaster 
    WHERE modulename = 'IDS_MDL_SETUP'
      AND screenname = 'IDS_SCN_BARCODEMASTER'
      AND taskname = 'IDS_TSK_EDIT'
);

INSERT INTO lsaudittrailconfigmaster (serialno, manualaudittrail, modulename, ordersequnce, screenname, taskname)
SELECT 273, 0, 'IDS_MDL_SETUP', 18, 'IDS_SCN_BARCODEMASTER', 'IDS_TSK_DELETE'
WHERE NOT EXISTS (
    SELECT 1 
    FROM lsaudittrailconfigmaster 
    WHERE modulename = 'IDS_MDL_SETUP'
      AND screenname = 'IDS_SCN_BARCODEMASTER'
      AND taskname = 'IDS_TSK_DELETE'
);

UPDATE lsaudittrailconfigmaster
SET ordersequnce = CASE screenname
    WHEN 'IDS_SCN_SHEETORDERS'        THEN 1
    WHEN 'IDS_SCN_PROTOCOLORDERS'     THEN 2
    WHEN 'IDS_SCN_UNLOCKORDERS'       THEN 3
    WHEN 'IDS_SCN_SHEETTEMPLATES'     THEN 4
    WHEN 'IDS_SCN_PROTOCOLTEMP'       THEN 5
    WHEN 'IDS_SCN_TEMPLATEMAPPING'    THEN 6
    WHEN 'IDS_SCN_SAMPLEMASTER'       THEN 7
    WHEN 'IDS_SCN_USERGROUP'          THEN 8
    WHEN 'IDS_SCN_SITEMASTER'         THEN 9
    WHEN 'IDS_SCN_USERMASTER'         THEN 10
    WHEN 'IDS_SCN_USERRIGHTS'         THEN 11
    WHEN 'IDS_SCN_PROJECTMASTER'      THEN 12
    WHEN 'IDS_SCN_PROJECTTEAM'        THEN 13
    WHEN 'IDS_SCN_TASKMASTER'         THEN 14
    WHEN 'IDS_SCN_ORDERWORKFLOW'      THEN 15
    WHEN 'IDS_SCN_TEMPLATEWORKFLOW'   THEN 16
    WHEN 'IDS_SCN_DOMAIN'             THEN 17
    WHEN 'IDS_SCN_BARCODEMASTER'      THEN 18
    WHEN 'IDS_SCN_PASSWORDPOLICY'     THEN 19
    WHEN 'IDS_SCN_INSTRUMENTCATEGORY' THEN 20
    WHEN 'IDS_SCN_INSTRUMENTMASTER'   THEN 21
    WHEN 'IDS_SCN_DELIMITER'          THEN 22
    WHEN 'IDS_SCN_METHODDELIMITER'    THEN 23
    WHEN 'IDS_SCN_METHODMASTER'       THEN 24
    WHEN 'IDS_SCN_AUDITTRAILHIS'       THEN 25
    WHEN 'IDS_SCN_AUDITTRAILCONFIG'   THEN 26
    WHEN 'IDS_SCN_CFRSETTINGS'        THEN 27
    WHEN 'IDS_SCN_REPORTDESIGNER'     THEN 28
    WHEN 'IDS_SCN_REPORTVIEWER'       THEN 29
    WHEN 'IDS_SCN_MATERIALCATEGORY'   THEN 30
    WHEN 'IDS_SCN_STORAGELOCATION'    THEN 31
    WHEN 'IDS_SCN_MATERIALTYPE'       THEN 32
    WHEN 'IDS_SCN_UNITMASTER'         THEN 33
    WHEN 'IDS_SCN_GRADEMASTER'        THEN 34
    WHEN 'IDS_SCN_SUPPLIER'           THEN 35
    WHEN 'IDS_SCN_MANUFACTURER'       THEN 36
    WHEN 'IDS_SCN_SECTIONMASTER'      THEN 37
    WHEN 'IDS_SCN_INVENTORY'          THEN 38
    WHEN 'IDS_SCN_MATERIAL'           THEN 39
    WHEN 'IDS_SCN_MATERIALINVENTORY'  THEN 40
    WHEN 'IDS_SCN_EQUIPMENTTYPE'      THEN 41
    WHEN 'IDS_SCN_EQUIPMENTCATEGORY'  THEN 42
    WHEN 'IDS_SCN_EQUIPMENT'          THEN 43
    WHEN 'IDS_SCN_SAMPLETYPE'         THEN 44
    WHEN 'IDS_SCN_SAMPLECATEGORY'     THEN 45
    WHEN 'IDS_SCN_REPORTS'            THEN 46
    WHEN 'IDS_SCN_LOGBOOK'            THEN 47
    ELSE ordersequnce
END;

UPDATE lsaudittrailconfiguration
SET ordersequnce = CASE screenname
    WHEN 'IDS_SCN_SHEETORDERS'        THEN 1
    WHEN 'IDS_SCN_PROTOCOLORDERS'     THEN 2
    WHEN 'IDS_SCN_UNLOCKORDERS'       THEN 3
    WHEN 'IDS_SCN_SHEETTEMPLATES'     THEN 4
    WHEN 'IDS_SCN_PROTOCOLTEMP'       THEN 5
    WHEN 'IDS_SCN_TEMPLATEMAPPING'    THEN 6
    WHEN 'IDS_SCN_SAMPLEMASTER'       THEN 7
    WHEN 'IDS_SCN_USERGROUP'          THEN 8
    WHEN 'IDS_SCN_SITEMASTER'         THEN 9
    WHEN 'IDS_SCN_USERMASTER'         THEN 10
    WHEN 'IDS_SCN_USERRIGHTS'         THEN 11
    WHEN 'IDS_SCN_PROJECTMASTER'      THEN 12
    WHEN 'IDS_SCN_PROJECTTEAM'        THEN 13
    WHEN 'IDS_SCN_TASKMASTER'         THEN 14
    WHEN 'IDS_SCN_ORDERWORKFLOW'      THEN 15
    WHEN 'IDS_SCN_TEMPLATEWORKFLOW'   THEN 16
    WHEN 'IDS_SCN_DOMAIN'             THEN 17
    WHEN 'IDS_SCN_BARCODEMASTER'      THEN 18
    WHEN 'IDS_SCN_PASSWORDPOLICY'     THEN 19
    WHEN 'IDS_SCN_INSTRUMENTCATEGORY' THEN 20
    WHEN 'IDS_SCN_INSTRUMENTMASTER'   THEN 21
    WHEN 'IDS_SCN_DELIMITER'          THEN 22
    WHEN 'IDS_SCN_METHODDELIMITER'    THEN 23
    WHEN 'IDS_SCN_METHODMASTER'       THEN 24
    WHEN 'IDS_SCN_AUDITTRAILHIS'       THEN 25
    WHEN 'IDS_SCN_AUDITTRAILCONFIG'   THEN 26
    WHEN 'IDS_SCN_CFRSETTINGS'        THEN 27
    WHEN 'IDS_SCN_REPORTDESIGNER'     THEN 28
    WHEN 'IDS_SCN_REPORTVIEWER'       THEN 29
    WHEN 'IDS_SCN_MATERIALCATEGORY'   THEN 30
    WHEN 'IDS_SCN_STORAGELOCATION'    THEN 31
    WHEN 'IDS_SCN_MATERIALTYPE'       THEN 32
    WHEN 'IDS_SCN_UNITMASTER'         THEN 33
    WHEN 'IDS_SCN_GRADEMASTER'        THEN 34
    WHEN 'IDS_SCN_SUPPLIER'           THEN 35
    WHEN 'IDS_SCN_MANUFACTURER'       THEN 36
    WHEN 'IDS_SCN_SECTIONMASTER'      THEN 37
    WHEN 'IDS_SCN_INVENTORY'          THEN 38
    WHEN 'IDS_SCN_MATERIAL'           THEN 39
    WHEN 'IDS_SCN_MATERIALINVENTORY'  THEN 40
    WHEN 'IDS_SCN_EQUIPMENTTYPE'      THEN 41
    WHEN 'IDS_SCN_EQUIPMENTCATEGORY'  THEN 42
    WHEN 'IDS_SCN_SAMPLETYPE'         THEN 43
    WHEN 'IDS_SCN_SAMPLECATEGORY'     THEN 44
    WHEN 'IDS_SCN_REPORTS'            THEN 45
    WHEN 'IDS_SCN_LOGBOOK'            THEN 46
    ELSE ordersequnce
END;

DELETE FROM lsfields WHERE level04name = 'Software Name' AND fieldcode <> 64;
ALTER TABLE LSpasswordhistorydetails ALTER COLUMN passwordcode SET DEFAULT nextval('lspasswordhistorydetails_seq');
delete from lsusergrouprightsmaster where screenname = 'IDS_SCN_SAMPLEMASTER' and displaytopic = 'IDS_SCN_SAMPLEMASTER' and modulename = 'IDS_MDL_MASTERS';

delete from materialtype where smaterialtypename = 'IQC Standard Material Type' and nmaterialtypecode = 4 and ndefaultstatus = 4;
delete from materialtype where smaterialtypename = 'Volumetric Type' and nmaterialtypecode = 2 and ndefaultstatus = 4;
delete from materialtype where smaterialtypename = 'Material Inventory Type' and nmaterialtypecode = 3 and ndefaultstatus = 4;
delete from materialtype where smaterialtypename = 'Standared Type' and nmaterialtypecode = 1 and ndefaultstatus = 4;
delete from materialtype where nmaterialtypecode = -1;

--CREATE SEQUENCE if not exists materialtype_sequence; 
	
--ALTER TABLE materialtype ALTER COLUMN nmaterialtypecode SET DEFAULT nextval('materialtype_sequence');

DELETE FROM lsfields WHERE level04name = 'Software Name' AND fieldcode <> 64;
ALTER TABLE LSpasswordhistorydetails ALTER COLUMN passwordcode SET DEFAULT nextval('lspasswordhistorydetails_seq');
delete from lsusergrouprightsmaster where screenname = 'IDS_SCN_SAMPLEMASTER' and displaytopic = 'IDS_SCN_SAMPLEMASTER' and modulename = 'IDS_MDL_MASTERS';

insert into LSpreferences (serialno,tasksettings,valuesettings) values(9,'ssologin','') on conflict(serialno) do nothing;

ALTER TABLE selectedinventorymapped DROP CONSTRAINT IF EXISTS selectedinventorymapped_samplestoragelocationkey_key;

update LSusergrouprightsmaster set screenname = 'IDS_SCN_TASKMASTER' where displaytopic = 'IDS_SCN_TASKMASTER' and screenname = 'IDS_SCN_PROJECTMASTER';
update LSusergrouprightsmaster set screenname = 'IDS_SCN_PROJECTTEAM' where displaytopic = 'IDS_SCN_PROJECTTEAM' and screenname = 'IDS_SCN_PROJECTMASTER';

update lsusergrouprights set screenname = 'IDS_SCN_TASKMASTER' where displaytopic = 'IDS_SCN_TASKMASTER' and screenname = 'IDS_SCN_PROJECTMASTER';
update lsusergrouprights set screenname = 'IDS_SCN_PROJECTTEAM' where displaytopic = 'IDS_SCN_PROJECTTEAM' and screenname = 'IDS_SCN_PROJECTMASTER';

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_SCREENVIEW', 'IDS_MDL_SETUP', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_PROJECTTEAM' ,33
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SCREENVIEW' and screenname='IDS_SCN_PROJECTTEAM' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (307, 'IDS_TSK_SCREENVIEW', 'IDS_MDL_SETUP', 'IDS_SCN_PROJECTTEAM', '0', 'NA', 'NA', 'NA', '0,0,0', 33) 
ON CONFLICT (orderno) DO NOTHING;

INSERT into lsusergrouprights(displaytopic,modulename,createdby, sallow, screate, sdelete, sedit,lssitemaster_sitecode, usergroupid_usergroupcode,screenname,sequenceorder) 
SELECT 'IDS_TSK_SCREENVIEW', 'IDS_MDL_SETUP', 'administrator', '1', 'NA', 'NA', 'NA', 1,1,'IDS_SCN_TASKMASTER' ,33
WHERE NOT EXISTS (select * from lsusergrouprights where displaytopic = 'IDS_TSK_SCREENVIEW' and screenname='IDS_SCN_TASKMASTER' and usergroupid_usergroupcode = 1); 

INSERT INTO lsusergrouprightsmaster(orderno, displaytopic, modulename, screenname, sallow, screate, sdelete, sedit, status, sequenceorder) 
VALUES (308, 'IDS_TSK_SCREENVIEW', 'IDS_MDL_SETUP', 'IDS_SCN_TASKMASTER', '0', 'NA', 'NA', 'NA', '0,0,0', 33) 
ON CONFLICT (orderno) DO NOTHING;

update lsusergrouprightsmaster set screenname = 'IDS_SCN_TASKMASTER' where orderno = 308 and screenname = 'IDS_SCN_PROJECTMASTER';
update lsusergrouprightsmaster set screenname = 'IDS_SCN_PROJECTTEAM' where orderno = 307 and screenname = 'IDS_SCN_PROJECTMASTER';

ALTER TABLE samplestoragemapping DROP CONSTRAINT IF EXISTS samplestoragemapping_samplestoragelocationkey_key;

ALTER TABLE public.lsorderattachments
DROP CONSTRAINT IF EXISTS fk_lsorderattachments_elnmaterialinventory;
 
ALTER TABLE public.lsorderattachments
ADD CONSTRAINT fk_lsorderattachments_elnmaterialinventory
FOREIGN KEY (nmaterialinventorycode)
REFERENCES public.elnmaterialinventory (nmaterialinventorycode)
ON UPDATE NO ACTION
ON DELETE NO ACTION;

ALTER TABLE equipment ALTER COLUMN callibrationperiod TYPE character varying(1000);
ALTER TABLE equipment ALTER COLUMN callibrationvalue TYPE character varying(1000);
ALTER TABLE equipment ALTER COLUMN maintananceperiod TYPE character varying(1000);
ALTER TABLE equipment ALTER COLUMN manintanancevalue TYPE character varying(1000);
ALTER TABLE equipment ALTER COLUMN sequipmentid TYPE character varying(1000);
ALTER TABLE equipment ALTER COLUMN sequipmentname TYPE character varying(1000);
ALTER TABLE equipment ALTER COLUMN modifiedby TYPE character varying(1000);
ALTER TABLE equipment ALTER COLUMN remarks TYPE character varying(1000);
ALTER TABLE equipment ALTER COLUMN sequenceid TYPE character varying(1000);
ALTER TABLE equipment ALTER COLUMN sequipmentelectrodeno TYPE character varying(1000);
ALTER TABLE equipment ALTER COLUMN sequipmentlotno TYPE character varying(1000);
ALTER TABLE equipment ALTER COLUMN sequipmentmake TYPE character varying(1000);
ALTER TABLE equipment ALTER COLUMN sequipmentmodel TYPE character varying(1000);

update lsaudittrailconfigmaster set taskname = 'IDS_TSK_SAVEMATERIALINV'  where screenname = 'IDS_SCN_MATERIALMGMT' and taskname = 'IDS_TSK_SAVE';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_SAVEMATERIALINV'  where screenname = 'IDS_SCN_MATERIALMGMT' and taskname = 'IDS_TSK_SAVE';


	------Audit count issue fix (DIVYA DB)-----------
update lsaudittrailconfiguration set taskname = 'IDS_TSK_SAVE' where screenname = 'IDS_SCN_EQUIPMENT' and taskname = 'IDS_TSK_ADD';
UPDATE lsaudittrailconfigmaster SET taskname = 'IDS_TSK_SAVE' WHERE screenname = 'IDS_SCN_EQUIPMENT' AND taskname = 'IDS_TSK_ADD';
 
update lsaudittrailconfiguration set taskname = 'IDS_TSK_RETIRE'  where screenname = 'IDS_SCN_UNITMASTER' and taskname = 'IDS_TSK_DELETE';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_RETIRE'  where screenname = 'IDS_SCN_UNITMASTER' and taskname = 'IDS_TSK_DELETE';
update lsaudittrailconfiguration set taskname = 'IDS_TSK_SAVE'  where screenname = 'IDS_SCN_UNITMASTER' and taskname = 'IDS_TSK_ADD';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_SAVE'  where screenname = 'IDS_SCN_UNITMASTER' and taskname = 'IDS_TSK_ADD';
 
update lsaudittrailconfiguration set taskname = 'IDS_TSK_DELETE'  where screenname = 'IDS_SCN_PROJECTMASTER' and taskname = 'IDS_TSK_DELETEPARSER';
 
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_DELETE'  where screenname = 'IDS_SCN_PROJECTMASTER' and taskname = 'IDS_TSK_DELETEPARSER';
 
update lsaudittrailconfiguration set taskname = 'IDS_TSK_SAVE'  where screenname = 'IDS_SCN_SAMPLETYPE' and taskname = 'IDS_TSK_ADD';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_SAVE'  where screenname = 'IDS_SCN_SAMPLETYPE' and taskname = 'IDS_TSK_ADD';
 
update lsaudittrailconfiguration set taskname = 'IDS_TSK_SAVE'  where screenname = 'IDS_SCN_SAMPLECATEGORY' and taskname = 'IDS_TSK_ADD';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_SAVE'  where screenname = 'IDS_SCN_SAMPLECATEGORY' and taskname = 'IDS_TSK_ADD';
 
update lsaudittrailconfiguration set taskname = 'IDS_TSK_DELETE'  where screenname = 'IDS_SCN_TASKMASTER' and taskname = 'IDS_TSK_DELETEPARSER';
update lsaudittrailconfigmaster set taskname = 'IDS_TSK_DELETE'  where screenname = 'IDS_SCN_TASKMASTER' and taskname = 'IDS_TSK_DELETEPARSER';
 
update lsaudittrailconfiguration set taskname = 'IDS_TSK_SAVE' where screenname = 'IDS_SCN_MATERIALTYPE' and taskname = 'IDS_TSK_ADD';
UPDATE lsaudittrailconfigmaster SET taskname = 'IDS_TSK_SAVE' WHERE screenname = 'IDS_SCN_MATERIALTYPE' AND taskname = 'IDS_TSK_ADD';
 
update lsaudittrailconfiguration set taskname = 'IDS_TSK_SAVE' where screenname = 'IDS_SCN_BARCODEMASTER' and taskname = 'IDS_TSK_DELETEPARSER';
UPDATE lsaudittrailconfigmaster SET taskname = 'IDS_TSK_SAVE' WHERE screenname = 'IDS_SCN_BARCODEMASTER' AND taskname = 'IDS_TSK_DELETEPARSER';
 
update lsaudittrailconfiguration set taskname = 'IDS_TSK_SAVE' where screenname = 'IDS_SCN_EQUIPMENTCATEGORY' and taskname = 'IDS_TSK_ADD';
UPDATE lsaudittrailconfigmaster SET taskname = 'IDS_TSK_SAVE' WHERE screenname = 'IDS_SCN_EQUIPMENTCATEGORY' AND taskname = 'IDS_TSK_ADD';
 
update lsaudittrailconfiguration set taskname = 'IDS_TSK_SAVE' where screenname = 'IDS_SCN_EQUIPMENTTYPE' and taskname = 'IDS_TSK_ADD';
UPDATE lsaudittrailconfigmaster SET taskname = 'IDS_TSK_SAVE' WHERE screenname = 'IDS_SCN_EQUIPMENTTYPE' AND taskname = 'IDS_TSK_ADD';
-----------------------------------------------