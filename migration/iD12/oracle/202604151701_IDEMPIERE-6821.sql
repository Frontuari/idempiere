-- IDEMPIERE-6820 Add multi tenant support to SSO (DAD-209)
SELECT register_migration_script('202604151701_IDEMPIERE-6821.sql') FROM dual;

-- Apr 15, 2026, 17:03:00 PM GMT-04:00
alter table ad_sequence add column islimitcontrolno bpchar(1) DEFAULT 'N'::bpchar NOT NULL
;

-- Apr 15, 2026, 17:03:00 PM GMT-04:00
alter table ad_sequence add column limitseqno numeric(10) DEFAULT NULL::numeric null
;

-- Apr 15, 2026, 17:03:00 PM GMT-04:00
alter table ad_sequence add column qtybeforenotified numeric(10) DEFAULT NULL::numeric null
;

-- Apr 15, 2026, 17:03:00 PM GMT-04:00
alter table ad_sequence_no add column limitseqno numeric(10) DEFAULT NULL::numeric null
;

-- Apr 15, 2026, 17:03:00 PM GMT-04:00
alter table ad_sequence_no add column qtybeforenotified numeric(10) DEFAULT NULL::numeric null
;

-- Apr 15, 2026, 17:03:00 PM GMT-04:00
INSERT INTO ad_element (ad_element_id,ad_client_id,ad_org_id,isactive,created,createdby,updated,updatedby,columnname,entitytype,"name",printname,description,help,po_name,po_printname,po_description,po_help,ad_element_uu,placeholder) VALUES
	 (203965,0,0,'Y','2024-12-18 18:06:50.383',100,'2025-01-23 12:49:01.626',100,'IsLimitControlNo','D','Limit Control No','Limit Control No','Limit the control number generated',NULL,NULL,NULL,NULL,NULL,'4d3f89b5-78da-459f-a541-7232707c4698',NULL)
;

-- Apr 15, 2026, 17:03:00 PM GMT-04:00
INSERT INTO ad_element (ad_element_id,ad_client_id,ad_org_id,isactive,created,createdby,updated,updatedby,columnname,entitytype,"name",printname,description,help,po_name,po_printname,po_description,po_help,ad_element_uu,placeholder) VALUES
	 (203966,0,0,'Y','2024-12-18 18:06:50.445',100,'2025-01-23 12:49:02.056',100,'LimitSeqNo','D','Limit Sequence No','Limit Sequence No','Limit of Control Sequence No',NULL,NULL,NULL,NULL,NULL,'0d7970ff-4544-4a1a-8aac-d6e8da084c72',NULL)
;

-- Apr 15, 2026, 17:03:00 PM GMT-04:00
INSERT INTO ad_element (ad_element_id,ad_client_id,ad_org_id,isactive,created,createdby,updated,updatedby,columnname,entitytype,"name",printname,description,help,po_name,po_printname,po_description,po_help,ad_element_uu,placeholder) VALUES
	 (203967,0,0,'Y','2024-12-18 18:06:50.489',100,'2025-01-23 12:49:02.524',100,'QtyBeforeNotified','D','Quantity Before Notified','Quantity Before Notified','Quantity Before Notified',NULL,NULL,NULL,NULL,NULL,'14e975ec-553a-4fab-bd95-4ed14d21dca7',NULL)
;

-- Apr 15, 2026, 17:03:00 PM GMT-04:00
INSERT INTO ad_column (ad_column_id,ad_client_id,ad_org_id,isactive,created,updated,createdby,updatedby,"name",description,help,"version",entitytype,columnname,ad_table_id,ad_reference_id,ad_reference_value_id,ad_val_rule_id,fieldlength,defaultvalue,iskey,isparent,ismandatory,isupdateable,readonlylogic,isidentifier,seqno,istranslated,isencrypted,callout,vformat,valuemin,valuemax,isselectioncolumn,ad_element_id,ad_process_id,issyncdatabase,isalwaysupdateable,columnsql,mandatorylogic,infofactoryclass,isautocomplete,isallowlogging,formatpattern,ad_column_uu,isallowcopy,seqnoselection,istoolbarbutton,issecure,ad_chart_id,fkconstraintname,fkconstrainttype,pa_dashboardcontent_id,placeholder,ishtml,ad_val_rule_lookup_id,ad_infowindow_id,alwaysupdatablelogic) VALUES
	 (217167,0,0,'Y','2024-12-18 18:06:50.380','2025-01-23 12:49:01.949',100,100,'Limit Control No','Limit the control number generated',NULL,0,'D','IsLimitControlNo',115,20,NULL,NULL,1,'N','N','N','Y','Y',NULL,'N',0,'N','N',NULL,NULL,NULL,NULL,'N',203965,NULL,'Y','N',NULL,NULL,NULL,'N','Y',NULL,'6863db10-f8fe-4635-9cb3-f25bea2c5f01','N',0,'N','N',NULL,NULL,'N',NULL,NULL,'N',NULL,NULL,NULL)
;

-- Apr 15, 2026, 17:03:00 PM GMT-04:00
INSERT INTO ad_column (ad_column_id,ad_client_id,ad_org_id,isactive,created,updated,createdby,updatedby,"name",description,help,"version",entitytype,columnname,ad_table_id,ad_reference_id,ad_reference_value_id,ad_val_rule_id,fieldlength,defaultvalue,iskey,isparent,ismandatory,isupdateable,readonlylogic,isidentifier,seqno,istranslated,isencrypted,callout,vformat,valuemin,valuemax,isselectioncolumn,ad_element_id,ad_process_id,issyncdatabase,isalwaysupdateable,columnsql,mandatorylogic,infofactoryclass,isautocomplete,isallowlogging,formatpattern,ad_column_uu,isallowcopy,seqnoselection,istoolbarbutton,issecure,ad_chart_id,fkconstraintname,fkconstrainttype,pa_dashboardcontent_id,placeholder,ishtml,ad_val_rule_lookup_id,ad_infowindow_id,alwaysupdatablelogic) VALUES
	 (217168,0,0,'Y','2024-12-18 18:06:50.444','2025-01-23 12:49:02.332',100,100,'Limit Sequence No','Limit of Control Sequence No',NULL,0,'D','LimitSeqNo',115,11,NULL,NULL,22,NULL,'N','N','N','Y',NULL,'N',0,'N','N',NULL,NULL,NULL,NULL,'N',203966,NULL,'Y','N',NULL,NULL,NULL,'N','Y',NULL,'968a1982-3c10-48c5-9fe7-99fd64854c30','N',0,'N','N',NULL,NULL,'N',NULL,NULL,'N',NULL,NULL,NULL)
;

-- Apr 15, 2026, 17:03:00 PM GMT-04:00
INSERT INTO ad_column (ad_column_id,ad_client_id,ad_org_id,isactive,created,updated,createdby,updatedby,"name",description,help,"version",entitytype,columnname,ad_table_id,ad_reference_id,ad_reference_value_id,ad_val_rule_id,fieldlength,defaultvalue,iskey,isparent,ismandatory,isupdateable,readonlylogic,isidentifier,seqno,istranslated,isencrypted,callout,vformat,valuemin,valuemax,isselectioncolumn,ad_element_id,ad_process_id,issyncdatabase,isalwaysupdateable,columnsql,mandatorylogic,infofactoryclass,isautocomplete,isallowlogging,formatpattern,ad_column_uu,isallowcopy,seqnoselection,istoolbarbutton,issecure,ad_chart_id,fkconstraintname,fkconstrainttype,pa_dashboardcontent_id,placeholder,ishtml,ad_val_rule_lookup_id,ad_infowindow_id,alwaysupdatablelogic) VALUES
	 (217169,0,0,'Y','2024-12-18 18:06:50.488','2025-01-23 12:49:02.903',100,100,'Quantity Before Notified','Quantity Before Notified',NULL,0,'D','QtyBeforeNotified',115,11,NULL,NULL,22,NULL,'N','N','N','Y',NULL,'N',0,'N','N',NULL,NULL,NULL,NULL,'N',203967,NULL,'Y','N',NULL,NULL,NULL,'N','Y',NULL,'2760dbbc-b361-4d58-9afa-0abb4038b0b0','N',0,'N','N',NULL,NULL,'N',NULL,NULL,'N',NULL,NULL,NULL)
;

-- Apr 15, 2026, 17:03:00 PM GMT-04:00
INSERT INTO ad_column (ad_column_id,ad_client_id,ad_org_id,isactive,created,updated,createdby,updatedby,"name",description,help,"version",entitytype,columnname,ad_table_id,ad_reference_id,ad_reference_value_id,ad_val_rule_id,fieldlength,defaultvalue,iskey,isparent,ismandatory,isupdateable,readonlylogic,isidentifier,seqno,istranslated,isencrypted,callout,vformat,valuemin,valuemax,isselectioncolumn,ad_element_id,ad_process_id,issyncdatabase,isalwaysupdateable,columnsql,mandatorylogic,infofactoryclass,isautocomplete,isallowlogging,formatpattern,ad_column_uu,isallowcopy,seqnoselection,istoolbarbutton,issecure,ad_chart_id,fkconstraintname,fkconstrainttype,pa_dashboardcontent_id,placeholder,ishtml,ad_val_rule_lookup_id,ad_infowindow_id,alwaysupdatablelogic) VALUES
	 (217170,0,0,'Y','2024-12-18 18:07:06.508','2025-01-23 12:49:03.478',100,100,'Limit Sequence No','Limit of Control Sequence No',NULL,0,'D','LimitSeqNo',122,11,NULL,NULL,22,NULL,'N','N','N','Y',NULL,'N',0,'N','N',NULL,NULL,NULL,NULL,'N',203966,NULL,'Y','N',NULL,NULL,NULL,'N','Y',NULL,'a5a5b20d-0050-45f9-8dda-30bbd51cc61f','N',0,'N','N',NULL,NULL,'N',NULL,NULL,'N',NULL,NULL,NULL)
;

-- Apr 15, 2026, 17:03:00 PM GMT-04:00
INSERT INTO ad_column (ad_column_id,ad_client_id,ad_org_id,isactive,created,updated,createdby,updatedby,"name",description,help,"version",entitytype,columnname,ad_table_id,ad_reference_id,ad_reference_value_id,ad_val_rule_id,fieldlength,defaultvalue,iskey,isparent,ismandatory,isupdateable,readonlylogic,isidentifier,seqno,istranslated,isencrypted,callout,vformat,valuemin,valuemax,isselectioncolumn,ad_element_id,ad_process_id,issyncdatabase,isalwaysupdateable,columnsql,mandatorylogic,infofactoryclass,isautocomplete,isallowlogging,formatpattern,ad_column_uu,isallowcopy,seqnoselection,istoolbarbutton,issecure,ad_chart_id,fkconstraintname,fkconstrainttype,pa_dashboardcontent_id,placeholder,ishtml,ad_val_rule_lookup_id,ad_infowindow_id,alwaysupdatablelogic) VALUES
	 (217171,0,0,'Y','2024-12-18 18:07:06.540','2025-01-23 12:49:03.765',100,100,'Quantity Before Notified','Quantity Before Notified',NULL,0,'D','QtyBeforeNotified',122,11,NULL,NULL,22,NULL,'N','N','N','Y',NULL,'N',0,'N','N',NULL,NULL,NULL,NULL,'N',203967,NULL,'Y','N',NULL,NULL,NULL,'N','Y',NULL,'97d7128c-c791-4b3d-b0ca-e97cf144d757','N',0,'N','N',NULL,NULL,'N',NULL,NULL,'N',NULL,NULL,null)
;