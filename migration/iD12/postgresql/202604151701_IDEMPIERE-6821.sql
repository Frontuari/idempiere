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

CREATE SCHEMA IF NOT EXISTS security;

-- Corregido: PostgreSQL usa IF NOT EXISTS, no OR REPLACE para tablas.
CREATE TABLE IF NOT EXISTS security.change_log ( 
    log_id bigserial NOT NULL, 
    log_timestamp timestamptz DEFAULT now() NOT NULL, 
    "action" varchar(10) NOT NULL, 
    table_schema varchar(100) NOT NULL, 
    table_name varchar(100) NOT NULL, 
    ad_client_id numeric(10) NOT NULL, 
    old_data jsonb NULL, 
    new_data jsonb NULL, 
    session_user_name text NULL, 
    current_user_name text NULL, 
    client_address inet NULL, 
    client_port int4 NULL, 
    application_name text NULL, 
    query text NULL, 
    CONSTRAINT change_log_pkey PRIMARY KEY (log_id)
);

CREATE OR REPLACE FUNCTION security.fn_log_external_changes() RETURNS trigger LANGUAGE plpgsql SECURITY DEFINER AS $function$
DECLARE
    v_app_name TEXT;
    v_is_sotrx CHAR(1);
BEGIN
    v_app_name := current_setting('application_name', true);

    IF TG_TABLE_NAME = 'c_invoice' THEN
        v_is_sotrx := COALESCE(NEW.issotrx, OLD.issotrx);
    ELSE
        SELECT issotrx INTO v_is_sotrx FROM adempiere.c_invoice WHERE c_invoice_id = COALESCE(NEW.c_invoice_id, OLD.c_invoice_id);
    END IF;

    IF (v_app_name NOT ILIKE 'iDempiere%') AND (v_is_sotrx = 'Y') THEN
        INSERT INTO security.change_log (ad_client_id, action, table_schema, table_name, old_data, new_data, session_user_name, current_user_name, client_address, client_port, application_name, query)
        VALUES (COALESCE(NEW.ad_client_id, OLD.ad_client_id), TG_OP, TG_TABLE_SCHEMA, TG_TABLE_NAME, to_jsonb(OLD), CASE WHEN TG_OP = 'UPDATE' THEN to_jsonb(NEW) ELSE NULL END, session_user, current_user, inet_client_addr(), inet_client_port(), v_app_name, current_query());
    END IF;

    RETURN COALESCE(NEW, OLD);
END;
$function$;

-- Corregido: Eliminación previa para evitar errores de colisión
DROP TRIGGER IF EXISTS trg_audit_c_invoice ON adempiere.c_invoice;
CREATE TRIGGER trg_audit_c_invoice AFTER DELETE OR UPDATE ON adempiere.c_invoice FOR EACH ROW EXECUTE FUNCTION security.fn_log_external_changes();

DROP TRIGGER IF EXISTS trg_audit_c_invoiceline ON adempiere.c_invoiceline;
CREATE TRIGGER trg_audit_c_invoiceline AFTER DELETE OR UPDATE ON adempiere.c_invoiceline FOR EACH ROW EXECUTE FUNCTION security.fn_log_external_changes();

DROP TRIGGER IF EXISTS trg_audit_c_invoicetax ON adempiere.c_invoicetax;
CREATE TRIGGER trg_audit_c_invoicetax AFTER DELETE OR UPDATE ON adempiere.c_invoicetax FOR EACH ROW EXECUTE FUNCTION security.fn_log_external_changes();

create trigger trg_audit_c_invoice after
delete
    or
update
    on
    adempiere.c_invoice for each row execute function security.fn_log_external_changes();

create trigger trg_audit_c_invoiceline after
delete
    or
update
    on
    adempiere.c_invoiceline for each row execute function security.fn_log_external_changes();

create trigger trg_audit_c_invoicetax after
delete
    or
update
    on
    adempiere.c_invoicetax for each row execute function security.fn_log_external_changes();

-- adempiere.ftu_rv_changelog source

    CREATE OR REPLACE VIEW adempiere.ftu_rv_changelog
    AS SELECT l.ad_session_id,
        l.ad_changelog_id,
        t.tablename,
        l.record_id,
        c.columnname,
        l.oldvalue,
        l.newvalue,
        u.name,
        l.created,
        l.ad_org_id,
        ao.name AS orgname,
        u.ad_user_id,
        t.ad_table_id,
        c.ad_column_id
       FROM ad_changelog l
         JOIN ad_table t ON l.ad_table_id = t.ad_table_id
         JOIN ad_column c ON l.ad_column_id = c.ad_column_id
         JOIN ad_user u ON l.createdby = u.ad_user_id
         JOIN ad_org ao ON l.ad_org_id = ao.ad_org_id
      ORDER BY l.ad_session_id, l.ad_changelog_id, t.tablename, l.record_id, c.columnname;

CREATE OR REPLACE VIEW adempiere.ftu_rv_operationslog
AS WITH direccionfiscal AS (
         SELECT cbl.c_bpartner_location_id,
            (((((btrim(cl.address1::text) || COALESCE(' '::text || btrim(cl.address2::text), ''::text)) || COALESCE(' '::text || btrim(cl.address3::text), ''::text)) || COALESCE(' '::text || btrim(cl.address4::text), ''::text)) || COALESCE(', '::text || btrim(cl.city::text), ''::text)) || COALESCE(', '::text || btrim(cr.name::text), ''::text)) || COALESCE('. '::text || btrim(cl.postal::text), '.'::text) AS direccionfiscal
           FROM c_bpartner_location cbl
             LEFT JOIN c_location cl ON cbl.c_location_id = cl.c_location_id
             LEFT JOIN c_region cr ON cl.c_region_id = cr.c_region_id
             LEFT JOIN c_country cc2 ON cl.c_country_id = cc2.c_country_id
        ), docuser AS (
         SELECT au2.ad_user_id,
            au2.name,
            au2.description,
            cb2.taxid,
            cb2.name
           FROM ad_user au2
             LEFT JOIN c_bpartner cb2 ON cb2.c_bpartner_id = au2.c_bpartner_id
        )
 SELECT co.ad_client_id,
    co.ad_org_id,
    ac.name AS adclientname,
    ao.name AS orgname,
    co.documentno,
    co.c_doctypetarget_id AS c_doctype_id,
    cd.name AS documenttype,
    co.created,
    ( SELECT du.description
           FROM docuser du(ad_user_id, name, description, taxid, name_1)
          WHERE du.ad_user_id = co.createdby) AS createdby_name,
    co.updated,
    ( SELECT du.description
           FROM docuser du(ad_user_id, name, description, taxid, name_1)
          WHERE du.ad_user_id = co.updatedby) AS updatedby_name,
    co.dateacct,
    co.dateordered AS date_doc,
    cb.c_bpartner_id,
    cb.taxid,
    cb.name AS bpartner,
    ( SELECT df.direccionfiscal
           FROM direccionfiscal df
          WHERE co.c_bpartner_location_id = df.c_bpartner_location_id) AS bpartnerlocation,
    co.docstatus,
    ( SELECT atlf.name
           FROM ad_ref_list afl
             JOIN ad_ref_list_trl atlf ON atlf.ad_ref_list_id = afl.ad_ref_list_id
          WHERE afl.value::bpchar = co.docstatus
         LIMIT 1) AS docstatusname,
    currencyrate(100::numeric, 205::numeric, co.dateordered::timestamp with time zone, 1000000::numeric, co.ad_client_id, co.ad_org_id) AS rate,
    NULL::character varying AS lve_controlnumber,
    co.grandtotal,
    ( SELECT cc.iso_code
           FROM c_currency cc
          WHERE cc.c_currency_id = co.c_currency_id) AS iso_code
   FROM c_order co
     JOIN ad_client ac ON ac.ad_client_id = co.ad_client_id
     JOIN ad_org ao ON ao.ad_org_id = co.ad_org_id
     JOIN c_doctype cd ON cd.c_doctype_id = co.c_doctypetarget_id
     JOIN c_bpartner cb ON cb.c_bpartner_id = co.c_bpartner_id
UNION ALL
 SELECT ci.ad_client_id,
    ci.ad_org_id,
    ac.name AS adclientname,
    ao.name AS orgname,
    ci.documentno,
    ci.c_doctypetarget_id AS c_doctype_id,
    cd.name AS documenttype,
    ci.created,
    ( SELECT du.description
           FROM docuser du(ad_user_id, name, description, taxid, name_1)
          WHERE du.ad_user_id = ci.createdby) AS createdby_name,
    ci.updated,
    ( SELECT du.description
           FROM docuser du(ad_user_id, name, description, taxid, name_1)
          WHERE du.ad_user_id = ci.updatedby) AS updatedby_name,
    ci.dateacct,
    ci.dateinvoiced AS date_doc,
    cb.c_bpartner_id,
    cb.taxid,
    cb.name AS bpartner,
    ( SELECT df.direccionfiscal
           FROM direccionfiscal df
          WHERE ci.c_bpartner_location_id = df.c_bpartner_location_id) AS bpartnerlocation,
    ci.docstatus,
    ( SELECT atlf.name
           FROM ad_ref_list afl
             JOIN ad_ref_list_trl atlf ON atlf.ad_ref_list_id = afl.ad_ref_list_id
          WHERE afl.value::text = ci.docstatus::text
         LIMIT 1) AS docstatusname,
    currencyrate(100::numeric, 205::numeric, ci.dateinvoiced::timestamp with time zone, 1000000::numeric, ci.ad_client_id, ci.ad_org_id) AS rate,
    ci.lve_controlnumber,
    ci.grandtotal,
    ( SELECT cc.iso_code
           FROM c_currency cc
          WHERE cc.c_currency_id = ci.c_currency_id) AS iso_code
   FROM c_invoice ci
     JOIN ad_client ac ON ac.ad_client_id = ci.ad_client_id
     JOIN ad_org ao ON ao.ad_org_id = ci.ad_org_id
     JOIN c_doctype cd ON cd.c_doctype_id = ci.c_doctypetarget_id
     JOIN c_bpartner cb ON cb.c_bpartner_id = ci.c_bpartner_id
UNION ALL
 SELECT mi.ad_client_id,
    mi.ad_org_id,
    ac.name AS adclientname,
    ao.name AS orgname,
    mi.documentno,
    mi.c_doctype_id,
    cd.name AS documenttype,
    mi.created,
    ( SELECT du.description
           FROM docuser du(ad_user_id, name, description, taxid, name_1)
          WHERE du.ad_user_id = mi.createdby) AS createdby_name,
    mi.updated,
    ( SELECT du.description
           FROM docuser du(ad_user_id, name, description, taxid, name_1)
          WHERE du.ad_user_id = mi.updatedby) AS updatedby_name,
    mi.dateacct,
    mi.movementdate AS date_doc,
    cb.c_bpartner_id,
    cb.taxid,
    cb.name AS bpartner,
    ( SELECT df.direccionfiscal
           FROM direccionfiscal df
          WHERE mi.c_bpartner_location_id = df.c_bpartner_location_id) AS bpartnerlocation,
    mi.docstatus,
    ( SELECT atlf.name
           FROM ad_ref_list afl
             JOIN ad_ref_list_trl atlf ON atlf.ad_ref_list_id = afl.ad_ref_list_id
          WHERE afl.value::bpchar = mi.docstatus
         LIMIT 1) AS docstatusname,
    currencyrate(100::numeric, 205::numeric, mi.movementdate::timestamp with time zone, 1000000::numeric, mi.ad_client_id, mi.ad_org_id) AS rate,
    mi.lve_controlnumber,
    0 AS grandtotal,
    ''::bpchar AS iso_code
   FROM m_inout mi
     JOIN ad_client ac ON ac.ad_client_id = mi.ad_client_id
     JOIN ad_org ao ON ao.ad_org_id = mi.ad_org_id
     JOIN c_doctype cd ON cd.c_doctype_id = mi.c_doctype_id
     JOIN c_bpartner cb ON cb.c_bpartner_id = mi.c_bpartner_id
UNION ALL
 SELECT mo.ad_client_id,
    mo.ad_org_id,
    ac.name AS adclientname,
    ao.name AS orgname,
    mo.documentno,
    mo.c_doctype_id,
    cd.name AS documenttype,
    mo.created,
    ( SELECT du.description
           FROM docuser du(ad_user_id, name, description, taxid, name_1)
          WHERE du.ad_user_id = mo.createdby) AS createdby_name,
    mo.updated,
    ( SELECT du.description
           FROM docuser du(ad_user_id, name, description, taxid, name_1)
          WHERE du.ad_user_id = mo.updatedby) AS updatedby_name,
    mo.movementdate AS dateacct,
    mo.movementdate AS date_doc,
    cb.c_bpartner_id,
    cb.taxid,
    cb.name AS bpartner,
    ( SELECT df.direccionfiscal
           FROM direccionfiscal df
          WHERE mo.c_bpartner_location_id = df.c_bpartner_location_id) AS bpartnerlocation,
    mo.docstatus,
    ( SELECT atlf.name
           FROM ad_ref_list afl
             JOIN ad_ref_list_trl atlf ON atlf.ad_ref_list_id = afl.ad_ref_list_id
          WHERE afl.value::bpchar = mo.docstatus
         LIMIT 1) AS docstatusname,
    currencyrate(100::numeric, 205::numeric, mo.movementdate::timestamp with time zone, 1000000::numeric, mo.ad_client_id, mo.ad_org_id) AS rate,
    mo.lve_controlnumber,
    0 AS grandtotal,
    ''::bpchar AS iso_code
   FROM m_movement mo
     JOIN ad_client ac ON ac.ad_client_id = mo.ad_client_id
     JOIN ad_org ao ON ao.ad_org_id = mo.ad_org_id
     JOIN c_doctype cd ON cd.c_doctype_id = mo.c_doctype_id
     LEFT JOIN c_bpartner cb ON cb.c_bpartner_id = mo.c_bpartner_id;

CREATE OR REPLACE VIEW adempiere.ftu_rv_dni
    AS SELECT h.ad_client_id,
        h.ad_org_id,
        l.isactive,
        l.created,
        l.createdby,
        l.updated,
        l.updatedby,
        h.m_inout_id AS ftu_rv_dni_id,
        h.issotrx,
        h.documentno,
        h.docaction,
        h.docstatus,
        h.posted,
        h.processed,
        h.c_doctype_id,
        h.description,
        h.c_order_id,
        h.dateordered,
        h.movementtype,
        h.movementdate,
        h.dateacct,
        h.c_bpartner_id,
        h.c_bpartner_location_id,
        h.ad_user_id,
        h.salesrep_id,
        h.m_warehouse_id,
        h.poreference,
        h.deliveryrule,
        h.freightcostrule,
        h.freightamt,
        h.deliveryviarule,
        h.m_shipper_id,
        h.priorityrule,
        h.dateprinted,
        h.nopackages,
        h.pickdate,
        h.shipdate,
        h.trackingno,
        h.ad_orgtrx_id,
        h.c_project_id,
        h.c_campaign_id,
        h.c_activity_id,
        h.user1_id,
        h.user2_id,
        h.datereceived,
        h.isapproved,
        h.isindispute,
        l.m_inoutline_id,
        l.line,
        l.description AS linedescription,
        l.c_orderline_id,
        l.m_locator_id,
        l.m_product_id,
        l.c_uom_id,
        l.m_attributesetinstance_id,
        productattribute(l.m_attributesetinstance_id) AS productattribute,
        pasi.m_attributeset_id,
        pasi.m_lot_id,
        pasi.guaranteedate,
        pasi.lot,
        pasi.serno,
        l.movementqty,
        l.qtyentered,
        l.isdescription,
        l.confirmedqty,
        l.pickedqty,
        l.scrappedqty,
        l.targetqty,
        loc.value AS locatorvalue,
        loc.x,
        loc.y,
        loc.z,
        h.c_charge_id AS m_inout_c_charge_id,
        h.chargeamt,
        h.c_invoice_id AS m_inout_c_invoice_id,
        h.createconfirm,
        h.created AS m_inout_created,
        h.createdby AS m_inout_createdby,
        h.createfrom,
        h.createpackage,
        h.dropship_bpartner_id,
        h.dropship_location_id,
        h.dropship_user_id,
        h.generateto,
        h.isactive AS m_inout_isactive,
        h.isdropship,
        h.isintransit,
        h.isprinted,
        h.m_rma_id,
        h.processedon,
        h.processing,
        h.ref_inout_id,
        h.reversal_id,
        h.sendemail,
        h.updated AS m_inout_updated,
        h.updatedby AS m_inout_updatedby,
        h.volume,
        h.weight,
        l.ad_org_id AS m_inoutline_ad_org_id,
        l.ad_orgtrx_id AS m_inoutline_ad_orgtrx_id,
        l.c_activity_id AS m_inoutline_c_activity_id,
        l.c_campaign_id AS m_inoutline_c_campaign_id,
        l.c_charge_id AS m_inoutline_c_charge_id,
        l.c_project_id AS m_inoutline_c_project_id,
        l.c_projectphase_id,
        l.c_projecttask_id,
        l.isinvoiced,
        l.m_rmaline_id,
        l.processed AS m_inoutline_processed,
        l.ref_inoutline_id,
        l.reversalline_id,
        l.user1_id AS m_inoutline_user1_id,
        l.user2_id AS m_inoutline_user2_id,
        loc.ad_org_id AS m_locator_ad_org_id,
        loc.isactive AS m_locator_isactive,
        loc.isdefault,
        loc.m_warehouse_id AS m_locator_m_warehouse_id,
        loc.priorityno,
        pasi.ad_org_id AS m_asi_ad_org_id,
        pasi.created AS m_asi_created,
        pasi.createdby AS m_asi_createdby,
        pasi.description AS m_asi_description,
        pasi.isactive AS m_asi_isactive,
        pasi.updated AS m_asi_updated,
        pasi.updatedby AS m_asi_updatedby,
        cb.ftu_bpartnertype_id,
        cbl.c_salesregion_id,
        COALESCE(ol.priceactual, 0::numeric) AS priceactual,
        COALESCE(ol.priceactual, 0::numeric) * l.movementqty AS linenetamt,
        ol.c_currency_id,
        h.lve_controlnumber,
        0::numeric AS ftu_deliveryrute_id,
        (to_char(h.movementdate, 'yy'::text) || to_char(h.movementdate, 'mm'::text)) ||
            CASE
                WHEN EXTRACT(day FROM h.movementdate) <= 15::numeric THEN 'Q1'::text
                ELSE 'Q2'::text
            END AS fiscal_period,
            CASE
                WHEN EXTRACT(day FROM h.movementdate) <= 15::numeric THEN 'Q1'::text
                ELSE 'Q2'::text
            END AS fiscal_period_tipe,
        h.m_inout_id
       FROM m_inout h
         JOIN m_inoutline l ON h.m_inout_id = l.m_inout_id
         LEFT JOIN c_orderline ol ON l.c_orderline_id = ol.c_orderline_id
         LEFT JOIN c_order co ON ol.c_order_id = co.c_order_id
         LEFT JOIN m_locator loc ON l.m_locator_id = loc.m_locator_id
         LEFT JOIN c_bpartner cb ON h.c_bpartner_id = cb.c_bpartner_id
         LEFT JOIN c_bpartner_location cbl ON cb.c_bpartner_id = cbl.c_bpartner_id
         LEFT JOIN m_attributesetinstance pasi ON l.m_attributesetinstance_id = pasi.m_attributesetinstance_id
         LEFT JOIN c_doctype dt ON h.c_doctype_id = dt.c_doctype_id
      WHERE ol.qtydelivered > ol.qtyinvoiced AND co.issotrx = 'Y'::bpchar AND dt.isaffectedbook = 'Y'::bpchar;

CREATE OR REPLACE VIEW adempiere.ftu_rv_operationslog
AS WITH direccionfiscal AS (
         SELECT cbl.c_bpartner_location_id,
            (((((btrim(cl.address1::text) || COALESCE(' '::text || btrim(cl.address2::text), ''::text)) || COALESCE(' '::text || btrim(cl.address3::text), ''::text)) || COALESCE(' '::text || btrim(cl.address4::text), ''::text)) || COALESCE(', '::text || btrim(cl.city::text), ''::text)) || COALESCE(', '::text || btrim(cr.name::text), ''::text)) || COALESCE('. '::text || btrim(cl.postal::text), '.'::text) AS direccionfiscal
           FROM c_bpartner_location cbl
             LEFT JOIN c_location cl ON cbl.c_location_id = cl.c_location_id
             LEFT JOIN c_region cr ON cl.c_region_id = cr.c_region_id
             LEFT JOIN c_country cc2 ON cl.c_country_id = cc2.c_country_id
        ), docuser AS (
         SELECT au2.ad_user_id,
            au2.name,
            au2.description,
            cb2.taxid,
            cb2.name
           FROM ad_user au2
             LEFT JOIN c_bpartner cb2 ON cb2.c_bpartner_id = au2.c_bpartner_id
        )
 SELECT co.ad_client_id,
    co.ad_org_id,
    ac.name AS adclientname,
    ao.name AS orgname,
    co.documentno,
    co.c_doctypetarget_id AS c_doctype_id,
    cd.name AS documenttype,
    co.created,
    ( SELECT du.description
           FROM docuser du(ad_user_id, name, description, taxid, name_1)
          WHERE du.ad_user_id = co.createdby) AS createdby_name,
    co.updated,
    ( SELECT du.description
           FROM docuser du(ad_user_id, name, description, taxid, name_1)
          WHERE du.ad_user_id = co.updatedby) AS updatedby_name,
    co.dateacct,
    co.dateordered AS date_doc,
    cb.c_bpartner_id,
    cb.taxid,
    cb.name AS bpartner,
    ( SELECT df.direccionfiscal
           FROM direccionfiscal df
          WHERE co.c_bpartner_location_id = df.c_bpartner_location_id) AS bpartnerlocation,
    co.docstatus,
    ( SELECT atlf.name
           FROM ad_ref_list afl
             JOIN ad_ref_list_trl atlf ON atlf.ad_ref_list_id = afl.ad_ref_list_id
          WHERE afl.value::bpchar = co.docstatus
         LIMIT 1) AS docstatusname,
    currencyrate(100::numeric, 205::numeric, co.dateordered::timestamp with time zone, 1000000::numeric, co.ad_client_id, co.ad_org_id) AS rate,
    NULL::character varying AS lve_controlnumber,
    co.grandtotal,
    ( SELECT cc.iso_code
           FROM c_currency cc
          WHERE cc.c_currency_id = co.c_currency_id) AS iso_code
   FROM c_order co
     JOIN ad_client ac ON ac.ad_client_id = co.ad_client_id
     JOIN ad_org ao ON ao.ad_org_id = co.ad_org_id
     JOIN c_doctype cd ON cd.c_doctype_id = co.c_doctypetarget_id
     JOIN c_bpartner cb ON cb.c_bpartner_id = co.c_bpartner_id
UNION ALL
 SELECT ci.ad_client_id,
    ci.ad_org_id,
    ac.name AS adclientname,
    ao.name AS orgname,
    ci.documentno,
    ci.c_doctypetarget_id AS c_doctype_id,
    cd.name AS documenttype,
    ci.created,
    ( SELECT du.description
           FROM docuser du(ad_user_id, name, description, taxid, name_1)
          WHERE du.ad_user_id = ci.createdby) AS createdby_name,
    ci.updated,
    ( SELECT du.description
           FROM docuser du(ad_user_id, name, description, taxid, name_1)
          WHERE du.ad_user_id = ci.updatedby) AS updatedby_name,
    ci.dateacct,
    ci.dateinvoiced AS date_doc,
    cb.c_bpartner_id,
    cb.taxid,
    cb.name AS bpartner,
    ( SELECT df.direccionfiscal
           FROM direccionfiscal df
          WHERE ci.c_bpartner_location_id = df.c_bpartner_location_id) AS bpartnerlocation,
    ci.docstatus,
    ( SELECT atlf.name
           FROM ad_ref_list afl
             JOIN ad_ref_list_trl atlf ON atlf.ad_ref_list_id = afl.ad_ref_list_id
          WHERE afl.value::text = ci.docstatus::text
         LIMIT 1) AS docstatusname,
    currencyrate(100::numeric, 205::numeric, ci.dateinvoiced::timestamp with time zone, 1000000::numeric, ci.ad_client_id, ci.ad_org_id) AS rate,
    ci.lve_controlnumber,
    ci.grandtotal,
    ( SELECT cc.iso_code
           FROM c_currency cc
          WHERE cc.c_currency_id = ci.c_currency_id) AS iso_code
   FROM c_invoice ci
     JOIN ad_client ac ON ac.ad_client_id = ci.ad_client_id
     JOIN ad_org ao ON ao.ad_org_id = ci.ad_org_id
     JOIN c_doctype cd ON cd.c_doctype_id = ci.c_doctypetarget_id
     JOIN c_bpartner cb ON cb.c_bpartner_id = ci.c_bpartner_id
UNION ALL
 SELECT mi.ad_client_id,
    mi.ad_org_id,
    ac.name AS adclientname,
    ao.name AS orgname,
    mi.documentno,
    mi.c_doctype_id,
    cd.name AS documenttype,
    mi.created,
    ( SELECT du.description
           FROM docuser du(ad_user_id, name, description, taxid, name_1)
          WHERE du.ad_user_id = mi.createdby) AS createdby_name,
    mi.updated,
    ( SELECT du.description
           FROM docuser du(ad_user_id, name, description, taxid, name_1)
          WHERE du.ad_user_id = mi.updatedby) AS updatedby_name,
    mi.dateacct,
    mi.movementdate AS date_doc,
    cb.c_bpartner_id,
    cb.taxid,
    cb.name AS bpartner,
    ( SELECT df.direccionfiscal
           FROM direccionfiscal df
          WHERE mi.c_bpartner_location_id = df.c_bpartner_location_id) AS bpartnerlocation,
    mi.docstatus,
    ( SELECT atlf.name
           FROM ad_ref_list afl
             JOIN ad_ref_list_trl atlf ON atlf.ad_ref_list_id = afl.ad_ref_list_id
          WHERE afl.value::bpchar = mi.docstatus
         LIMIT 1) AS docstatusname,
    currencyrate(100::numeric, 205::numeric, mi.movementdate::timestamp with time zone, 1000000::numeric, mi.ad_client_id, mi.ad_org_id) AS rate,
    mi.lve_controlnumber,
    0 AS grandtotal,
    ''::bpchar AS iso_code
   FROM m_inout mi
     JOIN ad_client ac ON ac.ad_client_id = mi.ad_client_id
     JOIN ad_org ao ON ao.ad_org_id = mi.ad_org_id
     JOIN c_doctype cd ON cd.c_doctype_id = mi.c_doctype_id
     JOIN c_bpartner cb ON cb.c_bpartner_id = mi.c_bpartner_id
UNION ALL
 SELECT mo.ad_client_id,
    mo.ad_org_id,
    ac.name AS adclientname,
    ao.name AS orgname,
    mo.documentno,
    mo.c_doctype_id,
    cd.name AS documenttype,
    mo.created,
    ( SELECT du.description
           FROM docuser du(ad_user_id, name, description, taxid, name_1)
          WHERE du.ad_user_id = mo.createdby) AS createdby_name,
    mo.updated,
    ( SELECT du.description
           FROM docuser du(ad_user_id, name, description, taxid, name_1)
          WHERE du.ad_user_id = mo.updatedby) AS updatedby_name,
    mo.movementdate AS dateacct,
    mo.movementdate AS date_doc,
    cb.c_bpartner_id,
    cb.taxid,
    cb.name AS bpartner,
    ( SELECT df.direccionfiscal
           FROM direccionfiscal df
          WHERE mo.c_bpartner_location_id = df.c_bpartner_location_id) AS bpartnerlocation,
    mo.docstatus,
    ( SELECT atlf.name
           FROM ad_ref_list afl
             JOIN ad_ref_list_trl atlf ON atlf.ad_ref_list_id = afl.ad_ref_list_id
          WHERE afl.value::bpchar = mo.docstatus
         LIMIT 1) AS docstatusname,
    currencyrate(100::numeric, 205::numeric, mo.movementdate::timestamp with time zone, 1000000::numeric, mo.ad_client_id, mo.ad_org_id) AS rate,
    mo.lve_controlnumber,
    0 AS grandtotal,
    ''::bpchar AS iso_code
   FROM m_movement mo
     JOIN ad_client ac ON ac.ad_client_id = mo.ad_client_id
     JOIN ad_org ao ON ao.ad_org_id = mo.ad_org_id
     JOIN c_doctype cd ON cd.c_doctype_id = mo.c_doctype_id
     LEFT JOIN c_bpartner cb ON cb.c_bpartner_id = mo.c_bpartner_id;

CREATE OR REPLACE VIEW adempiere.v_ad_session_report
AS SELECT s.ad_session_id,
    s.ad_session_uu,
    s.ad_client_id,
    s.ad_org_id,
    org.name AS org_name,
    s.isactive,
    s.created,
    s.createdby,
    u1.name AS createdby_name,
    s.updated,
    s.updatedby,
    u2.name AS updatedby_name,
    s.websession,
    s.remote_addr,
    s.remote_host,
    s.processed,
    s.description,
    s.ad_role_id,
    r.name AS role_name,
    s.logindate,
    s.servername
   FROM ad_session s
     LEFT JOIN ad_user u1 ON s.createdby = u1.ad_user_id
     LEFT JOIN ad_user u2 ON s.updatedby = u2.ad_user_id
     LEFT JOIN ad_role r ON s.ad_role_id = r.ad_role_id
     LEFT JOIN ad_org org ON s.ad_org_id = org.ad_org_id;

CREATE OR REPLACE VIEW adempiere.vw_ad_user_basic_security
AS SELECT ad_client_id,
    ad_org_id,
    name,
    description,
    value,
    dateaccountlocked,
    failedlogincount,
    datelastlogin,
    datepasswordchanged,
    islocked
   FROM ad_user
  WHERE isactive = 'Y'::bpchar

CREATE OR REPLACE VIEW adempiere.ftu_rv_dbchangelog
AS SELECT log_id AS ftu_rv_dbchangelog_id,
    log_timestamp AS datetrx,
    action,
    table_schema AS tableschema,
    table_name AS tablename,
    ad_client_id,
    0 AS ad_org_id,
    old_data,
    new_data,
    session_user_name,
    current_user_name,
    client_address,
    client_port,
    application_name,
    query,
    old_data ->> 'documentno'::text AS old_documentno,
    (old_data ->> 'grandtotal'::text)::numeric AS old_grandtotal,
    old_data ->> 'docstatus'::text AS old_docstatus,
    old_data ->> 'dateinvoiced'::text AS old_dateinvoiced,
    new_data ->> 'documentno'::text AS new_documentno,
    (new_data ->> 'grandtotal'::text)::numeric AS new_grandtotal,
    new_data ->> 'docstatus'::text AS new_docstatus,
    new_data ->> 'dateinvoiced'::text AS new_dateinvoiced
   FROM security.change_log;