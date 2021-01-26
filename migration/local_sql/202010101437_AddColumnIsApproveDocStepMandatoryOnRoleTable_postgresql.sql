-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- 10/10/2020, 2:26:26 p. m. VET
INSERT INTO AD_Element (AD_Element_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,ColumnName,Name,PrintName,EntityType,AD_Element_UU) VALUES (5001,0,0,'Y',TO_TIMESTAMP('2020-10-10 14:26:26','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2020-10-10 14:26:26','YYYY-MM-DD HH24:MI:SS'),100,'IsApproveDocStepMandatory','Approve Document Step Mandatory','Approve Document Step Mandatory','D','18452d4b-809c-4806-8f67-ce870a78e2e2')
;

-- 10/10/2020, 2:28:04 p. m. VET
UPDATE AD_Element SET Description='Approve Document Step Mandatory', Help='If it is marked, it forces the verification of the approval step for the role, regardless of whether it can approve the document or not.',Updated=TO_TIMESTAMP('2020-10-10 14:28:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=5001
;

-- 10/10/2020, 2:28:04 p. m. VET
INSERT INTO AD_ELEMENT_TRL (AD_Element_ID,AD_Language,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Name,PrintName,Description,Help,IsTranslated,AD_Element_Trl_UU) VALUES (5001,'es_CO',0,0,'Y',TO_TIMESTAMP('2020-10-10 14:26:26','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2020-10-10 14:26:26','YYYY-MM-DD HH24:MI:SS'),100,'Approve Document Step Mandatory','Approve Document Step Mandatory','Approve Document Step Mandatory','If it is marked, it forces the verification of the approval step for the role, regardless of whether it can approve the document or not.','Y','680c1f23-35b0-4193-90f1-a87d53ec1997')
;

-- 10/10/2020, 2:28:04 p. m. VET
UPDATE AD_Column SET ColumnName='IsApproveDocStepMandatory', Name='Approve Document Step Mandatory', Description='Approve Document Step Mandatory', Help='If it is marked, it forces the verification of the approval step for the role, regardless of whether it can approve the document or not.', Placeholder=NULL WHERE AD_Element_ID=5001
;

-- 10/10/2020, 2:28:05 p. m. VET
UPDATE AD_Process_Para SET ColumnName='IsApproveDocStepMandatory', Name='Approve Document Step Mandatory', Description='Approve Document Step Mandatory', Help='If it is marked, it forces the verification of the approval step for the role, regardless of whether it can approve the document or not.', AD_Element_ID=5001 WHERE UPPER(ColumnName)='ISAPPROVEDOCSTEPMANDATORY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 10/10/2020, 2:28:05 p. m. VET
UPDATE AD_Process_Para SET ColumnName='IsApproveDocStepMandatory', Name='Approve Document Step Mandatory', Description='Approve Document Step Mandatory', Help='If it is marked, it forces the verification of the approval step for the role, regardless of whether it can approve the document or not.', Placeholder=NULL WHERE AD_Element_ID=5001 AND IsCentrallyMaintained='Y'
;

-- 10/10/2020, 2:28:05 p. m. VET
UPDATE AD_InfoColumn SET ColumnName='IsApproveDocStepMandatory', Name='Approve Document Step Mandatory', Description='Approve Document Step Mandatory', Help='If it is marked, it forces the verification of the approval step for the role, regardless of whether it can approve the document or not.', Placeholder=NULL WHERE AD_Element_ID=5001 AND IsCentrallyMaintained='Y'
;

-- 10/10/2020, 2:28:05 p. m. VET
UPDATE AD_Field SET Name='Approve Document Step Mandatory', Description='Approve Document Step Mandatory', Help='If it is marked, it forces the verification of the approval step for the role, regardless of whether it can approve the document or not.', Placeholder=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=5001) AND IsCentrallyMaintained='Y'
;

-- 10/10/2020, 2:32:06 p. m. VET
INSERT INTO AD_Column (AD_Column_ID,Version,Name,Description,Help,AD_Table_ID,ColumnName,DefaultValue,FieldLength,IsKey,IsParent,IsMandatory,IsTranslated,IsIdentifier,SeqNo,IsEncrypted,AD_Reference_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,EntityType,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,AD_Column_UU,IsAllowCopy,SeqNoSelection,IsToolbarButton,IsSecure,FKConstraintType,IsHtml) VALUES (1004301,0,'Approve Document Step Mandatory','Approve Document Step Mandatory','If it is marked, it forces the verification of the approval step for the role, regardless of whether it can approve the document or not.',156,'IsApproveDocStepMandatory','N',1,'N','N','N','N','N',0,'N',20,0,0,'Y',TO_TIMESTAMP('2020-10-10 14:32:05','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2020-10-10 14:32:05','YYYY-MM-DD HH24:MI:SS'),100,5001,'Y','N','D','N','N','N','Y','17f829fb-6807-41b9-8b25-82bfc2f33a23','Y',0,'N','N','N','N')
;

-- 10/10/2020, 2:32:06 p. m. VET
INSERT INTO AD_COLUMN_TRL (AD_Column_ID,AD_Language,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Name,IsTranslated,AD_Column_Trl_UU) VALUES (1004301,'es_CO',0,0,'Y',TO_TIMESTAMP('2020-10-10 14:26:26','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2020-10-10 14:26:26','YYYY-MM-DD HH24:MI:SS'),100,'Approve Document Step Mandatory','Y','680c1f23-35b0-4193-90f1-a87d53ec1997')
;

-- 10/10/2020, 2:32:09 p. m. VET
ALTER TABLE AD_Role ADD COLUMN IsApproveDocStepMandatory CHAR(1) DEFAULT 'N' CHECK (IsApproveDocStepMandatory IN ('Y','N'))
;

-- 10/10/2020, 2:32:28 p. m. VET
INSERT INTO AD_Field (AD_Field_ID,Name,Description,Help,AD_Tab_ID,AD_Column_ID,IsDisplayed,DisplayLength,SeqNo,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,IsReadOnly,IsCentrallyMaintained,EntityType,AD_Field_UU,IsDisplayedGrid,SeqNoGrid,XPosition,ColumnSpan) VALUES (1001876,'Approve Document Step Mandatory','Approve Document Step Mandatory','If it is marked, it forces the verification of the approval step for the role, regardless of whether it can approve the document or not.',119,1004301,'Y',1,450,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2020-10-10 14:32:28','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2020-10-10 14:32:28','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','D','75f8fe62-34a3-4a5e-b3de-56b3ae487018','Y',450,2,2)
;

-- 10/10/2020, 2:32:57 p. m. VET
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=160, AD_Reference_Value_ID=NULL, AD_Val_Rule_ID=NULL, XPosition=6, ColumnSpan=1, IsToolbarButton=NULL,Updated=TO_TIMESTAMP('2020-10-10 14:32:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=1001876
;

-- 10/10/2020, 2:32:57 p. m. VET
UPDATE AD_Field SET SeqNo=170, AD_Reference_Value_ID=NULL, AD_Val_Rule_ID=NULL, IsToolbarButton=NULL,Updated=TO_TIMESTAMP('2020-10-10 14:32:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=205947
;

-- 10/10/2020, 2:32:57 p. m. VET
UPDATE AD_Field SET SeqNo=180, AD_Reference_Value_ID=NULL, AD_Val_Rule_ID=NULL, IsToolbarButton=NULL,Updated=TO_TIMESTAMP('2020-10-10 14:32:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11003
;

-- 10/10/2020, 2:32:57 p. m. VET
UPDATE AD_Field SET SeqNo=190, AD_Reference_Value_ID=NULL, AD_Val_Rule_ID=NULL, IsToolbarButton=NULL,Updated=TO_TIMESTAMP('2020-10-10 14:32:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5227
;

-- 10/10/2020, 2:32:57 p. m. VET
UPDATE AD_Field SET SeqNo=200, AD_Reference_Value_ID=NULL, AD_Val_Rule_ID=NULL, IsToolbarButton=NULL,Updated=TO_TIMESTAMP('2020-10-10 14:32:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=202366
;

-- 10/10/2020, 2:32:57 p. m. VET
UPDATE AD_Field SET SeqNo=210, AD_Reference_Value_ID=NULL, AD_Val_Rule_ID=NULL, IsToolbarButton=NULL,Updated=TO_TIMESTAMP('2020-10-10 14:32:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=10813
;

-- 10/10/2020, 2:32:57 p. m. VET
UPDATE AD_Field SET SeqNo=220, AD_Reference_Value_ID=NULL, AD_Val_Rule_ID=NULL, IsToolbarButton=NULL,Updated=TO_TIMESTAMP('2020-10-10 14:32:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11257
;

-- 10/10/2020, 2:32:58 p. m. VET
UPDATE AD_Field SET SeqNo=230, AD_Reference_Value_ID=NULL, AD_Val_Rule_ID=NULL, IsToolbarButton=NULL,Updated=TO_TIMESTAMP('2020-10-10 14:32:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=8312
;

-- 10/10/2020, 2:32:58 p. m. VET
UPDATE AD_Field SET SeqNo=240, AD_Reference_Value_ID=NULL, AD_Val_Rule_ID=NULL, IsToolbarButton=NULL,Updated=TO_TIMESTAMP('2020-10-10 14:32:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=8310
;

-- 10/10/2020, 2:32:58 p. m. VET
UPDATE AD_Field SET SeqNo=250, AD_Reference_Value_ID=NULL, AD_Val_Rule_ID=NULL, IsToolbarButton=NULL,Updated=TO_TIMESTAMP('2020-10-10 14:32:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=8313
;

-- 10/10/2020, 2:32:58 p. m. VET
UPDATE AD_Field SET SeqNo=260, AD_Reference_Value_ID=NULL, AD_Val_Rule_ID=NULL, IsToolbarButton=NULL,Updated=TO_TIMESTAMP('2020-10-10 14:32:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=8314
;

-- 10/10/2020, 2:32:58 p. m. VET
UPDATE AD_Field SET SeqNo=270, AD_Reference_Value_ID=NULL, AD_Val_Rule_ID=NULL, IsToolbarButton=NULL,Updated=TO_TIMESTAMP('2020-10-10 14:32:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=8311
;

-- 10/10/2020, 2:32:58 p. m. VET
UPDATE AD_Field SET SeqNo=280, AD_Reference_Value_ID=NULL, AD_Val_Rule_ID=NULL, IsToolbarButton=NULL,Updated=TO_TIMESTAMP('2020-10-10 14:32:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11006
;

-- 10/10/2020, 2:32:58 p. m. VET
UPDATE AD_Field SET SeqNo=290, AD_Reference_Value_ID=NULL, AD_Val_Rule_ID=NULL, IsToolbarButton=NULL,Updated=TO_TIMESTAMP('2020-10-10 14:32:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=12367
;

-- 10/10/2020, 2:32:58 p. m. VET
UPDATE AD_Field SET SeqNo=300, AD_Reference_Value_ID=NULL, AD_Val_Rule_ID=NULL, IsToolbarButton=NULL,Updated=TO_TIMESTAMP('2020-10-10 14:32:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=12368
;

-- 10/10/2020, 2:32:58 p. m. VET
UPDATE AD_Field SET SeqNo=310, AD_Reference_Value_ID=NULL, AD_Val_Rule_ID=NULL, IsToolbarButton=NULL,Updated=TO_TIMESTAMP('2020-10-10 14:32:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11256
;

-- 10/10/2020, 2:32:58 p. m. VET
UPDATE AD_Field SET SeqNo=320, AD_Reference_Value_ID=NULL, AD_Val_Rule_ID=NULL, IsToolbarButton=NULL,Updated=TO_TIMESTAMP('2020-10-10 14:32:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=50168
;

-- 10/10/2020, 2:32:58 p. m. VET
UPDATE AD_Field SET SeqNo=330, AD_Reference_Value_ID=NULL, AD_Val_Rule_ID=NULL, IsToolbarButton=NULL,Updated=TO_TIMESTAMP('2020-10-10 14:32:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=50178
;

-- 10/10/2020, 2:32:59 p. m. VET
UPDATE AD_Field SET SeqNo=340, AD_Reference_Value_ID=NULL, AD_Val_Rule_ID=NULL, IsToolbarButton=NULL,Updated=TO_TIMESTAMP('2020-10-10 14:32:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=50176
;

-- 10/10/2020, 2:32:59 p. m. VET
UPDATE AD_Field SET SeqNo=350, AD_Reference_Value_ID=NULL, AD_Val_Rule_ID=NULL, IsToolbarButton=NULL,Updated=TO_TIMESTAMP('2020-10-10 14:32:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=50170
;

-- 10/10/2020, 2:32:59 p. m. VET
UPDATE AD_Field SET SeqNo=360, AD_Reference_Value_ID=NULL, AD_Val_Rule_ID=NULL, IsToolbarButton=NULL,Updated=TO_TIMESTAMP('2020-10-10 14:32:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=50174
;

-- 10/10/2020, 2:32:59 p. m. VET
UPDATE AD_Field SET SeqNo=370, AD_Reference_Value_ID=NULL, AD_Val_Rule_ID=NULL, IsToolbarButton=NULL,Updated=TO_TIMESTAMP('2020-10-10 14:32:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=50173
;

-- 10/10/2020, 2:32:59 p. m. VET
UPDATE AD_Field SET SeqNo=380, AD_Reference_Value_ID=NULL, AD_Val_Rule_ID=NULL, IsToolbarButton=NULL,Updated=TO_TIMESTAMP('2020-10-10 14:32:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=50172
;

-- 10/10/2020, 2:32:59 p. m. VET
UPDATE AD_Field SET SeqNo=390, AD_Reference_Value_ID=NULL, AD_Val_Rule_ID=NULL, IsToolbarButton=NULL,Updated=TO_TIMESTAMP('2020-10-10 14:32:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=50175
;

-- 10/10/2020, 2:32:59 p. m. VET
UPDATE AD_Field SET SeqNo=400, AD_Reference_Value_ID=NULL, AD_Val_Rule_ID=NULL, IsToolbarButton=NULL,Updated=TO_TIMESTAMP('2020-10-10 14:32:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=50169
;

-- 10/10/2020, 2:32:59 p. m. VET
UPDATE AD_Field SET SeqNo=410, AD_Reference_Value_ID=NULL, AD_Val_Rule_ID=NULL, IsToolbarButton=NULL,Updated=TO_TIMESTAMP('2020-10-10 14:32:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=50177
;

-- 10/10/2020, 2:33:15 p. m. VET
UPDATE AD_COLUMN SET AD_Element_id = (SELECT AD_Element_ID FROM AD_ELEMENT e WHERE UPPER(AD_COLUMN.ColumnName)=UPPER(e.ColumnName)) WHERE AD_Element_ID IS NULL
;

-- 10/10/2020, 2:33:15 p. m. VET
UPDATE AD_COLUMN SET ColumnName=e.ColumnName,Name=e.Name,Description=e.Description,Help=e.Help,Placeholder=e.Placeholder, Updated = statement_timestamp() FROM AD_ELEMENT e WHERE AD_COLUMN.AD_Element_ID=e.AD_Element_ID AND EXISTS (SELECT 1 FROM AD_ELEMENT e WHERE AD_COLUMN.AD_Element_ID=e.AD_Element_ID AND (AD_COLUMN.ColumnName <> e.ColumnName OR AD_COLUMN.Name <> e.Name OR COALESCE(AD_COLUMN.Description,' ') <> COALESCE(e.Description,' ') OR COALESCE(AD_COLUMN.Help,' ') <> COALESCE(e.Help,' ') OR COALESCE(AD_COLUMN.Placeholder,' ') <> COALESCE(e.Placeholder,' ')))
;

-- 10/10/2020, 2:33:15 p. m. VET
UPDATE AD_INFOCOLUMN SET ColumnName=e.ColumnName,Name=e.Name,Description=e.Description,Help=e.Help,Placeholder=e.Placeholder, Updated = statement_timestamp() FROM AD_ELEMENT e WHERE AD_INFOCOLUMN.AD_Element_ID=e.AD_Element_ID AND AD_INFOCOLUMN.IsCentrallyMaintained='Y' AND AD_INFOCOLUMN.IsActive='Y' AND EXISTS (SELECT 1 FROM AD_ELEMENT e WHERE AD_INFOCOLUMN.AD_Element_ID=e.AD_Element_ID AND (AD_INFOCOLUMN.ColumnName <> e.ColumnName OR AD_INFOCOLUMN.Name <> e.Name OR COALESCE(AD_INFOCOLUMN.Description,' ') <> COALESCE(e.Description,' ') OR COALESCE(AD_INFOCOLUMN.Help,' ') <> COALESCE(e.Help,' ') OR COALESCE(AD_INFOCOLUMN.Placeholder,' ') <> COALESCE(e.Placeholder,' ')))
;

-- 10/10/2020, 2:33:15 p. m. VET
UPDATE AD_FIELD SET Name=e.Name,Description=e.Description,Help=e.Help,Placeholder=e.Placeholder, Updated = statement_timestamp() FROM AD_ELEMENT e, AD_COLUMN c WHERE e.AD_Element_ID=c.AD_Element_ID AND c.AD_Column_ID=AD_FIELD.AD_Column_ID AND AD_FIELD.IsCentrallyMaintained='Y' AND AD_FIELD.IsActive='Y' AND EXISTS (SELECT 1 FROM AD_ELEMENT e, AD_COLUMN c WHERE AD_FIELD.AD_Column_ID=c.AD_Column_ID AND c.AD_Element_ID=e.AD_Element_ID AND c.AD_Process_ID IS NULL AND (AD_FIELD.Name <> e.Name OR COALESCE(AD_FIELD.Description,' ') <> COALESCE(e.Description,' ') OR COALESCE(AD_FIELD.Help,' ') <> COALESCE(e.Help,' ') OR COALESCE(AD_FIELD.Placeholder,' ') <> COALESCE(e.Placeholder,' ')))
;

-- 10/10/2020, 2:33:15 p. m. VET
UPDATE AD_FIELD_TRL SET Name = (SELECT e.Name FROM AD_ELEMENT_TRL e, AD_COLUMN c, AD_FIELD f WHERE e.AD_LANGUAGE=AD_FIELD_TRL.AD_LANGUAGE AND e.AD_Element_ID=c.AD_Element_ID AND c.AD_Column_ID=f.AD_Column_ID AND f.AD_Field_ID=AD_FIELD_TRL.AD_Field_ID), Description = (SELECT e.Description FROM AD_ELEMENT_TRL e, AD_COLUMN c, AD_FIELD f WHERE e.AD_LANGUAGE=AD_FIELD_TRL.AD_LANGUAGE AND e.AD_Element_ID=c.AD_Element_ID AND c.AD_Column_ID=f.AD_Column_ID AND f.AD_Field_ID=AD_FIELD_TRL.AD_Field_ID), Help = (SELECT e.Help FROM AD_ELEMENT_TRL e, AD_COLUMN c, AD_FIELD f WHERE e.AD_LANGUAGE=AD_FIELD_TRL.AD_LANGUAGE AND e.AD_Element_ID=c.AD_Element_ID AND c.AD_Column_ID=f.AD_Column_ID AND f.AD_Field_ID=AD_FIELD_TRL.AD_Field_ID), Placeholder = (SELECT e.Placeholder FROM AD_ELEMENT_TRL e, AD_COLUMN c, AD_FIELD f WHERE e.AD_LANGUAGE=AD_FIELD_TRL.AD_LANGUAGE AND e.AD_Element_ID=c.AD_Element_ID AND c.AD_Column_ID=f.AD_Column_ID AND f.AD_Field_ID=AD_FIELD_TRL.AD_Field_ID), IsTranslated = (SELECT e.IsTranslated FROM AD_ELEMENT_TRL e, AD_COLUMN c, AD_FIELD f WHERE e.AD_LANGUAGE=AD_FIELD_TRL.AD_LANGUAGE AND e.AD_Element_ID=c.AD_Element_ID AND c.AD_Column_ID=f.AD_Column_ID AND f.AD_Field_ID=AD_FIELD_TRL.AD_Field_ID), Updated = statement_timestamp() WHERE EXISTS (SELECT 1 FROM AD_FIELD f, AD_ELEMENT_TRL e, AD_COLUMN c WHERE AD_FIELD_TRL.AD_Field_ID=f.AD_Field_ID AND f.AD_Column_ID=c.AD_Column_ID AND c.AD_Element_ID=e.AD_Element_ID AND c.AD_Process_ID IS NULL AND AD_FIELD_TRL.AD_LANGUAGE=e.AD_LANGUAGE AND f.IsCentrallyMaintained='Y' AND f.IsActive='Y' AND (AD_FIELD_TRL.Name <> e.Name OR COALESCE(AD_FIELD_TRL.Description,' ') <> COALESCE(e.Description,' ') OR COALESCE(AD_FIELD_TRL.Help,' ') <> COALESCE(e.Help,' ') OR COALESCE(AD_FIELD_TRL.Placeholder,' ') <> COALESCE(e.Placeholder,' ')))
;

-- 10/10/2020, 2:33:19 p. m. VET
UPDATE AD_FIELD SET Name = (SELECT e.PO_Name FROM AD_ELEMENT e, AD_COLUMN c WHERE e.AD_Element_ID=c.AD_Element_ID AND c.AD_Column_ID=AD_FIELD.AD_Column_ID), Description = (SELECT e.PO_Description FROM AD_ELEMENT e, AD_COLUMN c WHERE e.AD_Element_ID=c.AD_Element_ID AND c.AD_Column_ID=AD_FIELD.AD_Column_ID), Help = (SELECT e.PO_Help FROM AD_ELEMENT e, AD_COLUMN c WHERE e.AD_Element_ID=c.AD_Element_ID AND c.AD_Column_ID=AD_FIELD.AD_Column_ID), Updated = statement_timestamp() WHERE AD_FIELD.IsCentrallyMaintained='Y' AND AD_FIELD.IsActive='Y' AND EXISTS (SELECT 1 FROM AD_ELEMENT e, AD_COLUMN c WHERE AD_FIELD.AD_Column_ID=c.AD_Column_ID AND c.AD_Element_ID=e.AD_Element_ID AND c.AD_Process_ID IS NULL AND (AD_FIELD.Name <> e.PO_Name OR COALESCE(AD_FIELD.Description,' ') <> COALESCE(e.PO_Description,' ') OR COALESCE(AD_FIELD.Help,' ') <> COALESCE(e.PO_Help,' ')) AND e.PO_Name IS NOT NULL) AND EXISTS (SELECT 1 FROM AD_TAB t, AD_WINDOW w WHERE AD_FIELD.AD_Tab_ID=t.AD_Tab_ID AND t.AD_Window_ID=w.AD_Window_ID AND w.IsSOTrx='N')
;

-- 10/10/2020, 2:33:19 p. m. VET
UPDATE AD_FIELD_TRL SET Name = (SELECT e.PO_Name FROM AD_ELEMENT_TRL e, AD_COLUMN c, AD_FIELD f WHERE e.AD_LANGUAGE=AD_FIELD_TRL.AD_LANGUAGE AND e.AD_Element_ID=c.AD_Element_ID AND c.AD_Column_ID=f.AD_Column_ID AND f.AD_Field_ID=AD_FIELD_TRL.AD_Field_ID), Description = (SELECT e.PO_Description FROM AD_ELEMENT_TRL e, AD_COLUMN c, AD_FIELD f WHERE e.AD_LANGUAGE=AD_FIELD_TRL.AD_LANGUAGE AND e.AD_Element_ID=c.AD_Element_ID AND c.AD_Column_ID=f.AD_Column_ID AND f.AD_Field_ID=AD_FIELD_TRL.AD_Field_ID), Help = (SELECT e.PO_Help FROM AD_ELEMENT_TRL e, AD_COLUMN c, AD_FIELD f WHERE e.AD_LANGUAGE=AD_FIELD_TRL.AD_LANGUAGE AND e.AD_Element_ID=c.AD_Element_ID AND c.AD_Column_ID=f.AD_Column_ID AND f.AD_Field_ID=AD_FIELD_TRL.AD_Field_ID), IsTranslated = (SELECT e.IsTranslated FROM AD_ELEMENT_TRL e, AD_COLUMN c, AD_FIELD f WHERE e.AD_LANGUAGE=AD_FIELD_TRL.AD_LANGUAGE AND e.AD_Element_ID=c.AD_Element_ID AND c.AD_Column_ID=f.AD_Column_ID AND f.AD_Field_ID=AD_FIELD_TRL.AD_Field_ID), Updated = statement_timestamp() WHERE EXISTS (SELECT 1 FROM AD_FIELD f, AD_ELEMENT_TRL e, AD_COLUMN c WHERE AD_FIELD_TRL.AD_Field_ID=f.AD_Field_ID AND f.AD_Column_ID=c.AD_Column_ID AND c.AD_Element_ID=e.AD_Element_ID AND c.AD_Process_ID IS NULL AND AD_FIELD_TRL.AD_LANGUAGE=e.AD_LANGUAGE AND f.IsCentrallyMaintained='Y' AND f.IsActive='Y' AND (AD_FIELD_TRL.Name <> e.PO_Name OR COALESCE(AD_FIELD_TRL.Description,' ') <> COALESCE(e.PO_Description,' ') OR COALESCE(AD_FIELD_TRL.Help,' ') <> COALESCE(e.PO_Help,' ')) AND e.PO_Name IS NOT NULL) AND EXISTS (SELECT 1 FROM AD_FIELD f, AD_TAB t, AD_WINDOW w WHERE AD_FIELD_TRL.AD_Field_ID=f.AD_Field_ID AND f.AD_Tab_ID=t.AD_Tab_ID AND t.AD_Window_ID=w.AD_Window_ID AND w.IsSOTrx='N')
;

-- 10/10/2020, 2:33:19 p. m. VET
UPDATE AD_FIELD SET Name = (SELECT p.Name FROM AD_PROCESS p, AD_COLUMN c WHERE p.AD_Process_ID=c.AD_Process_ID AND c.AD_Column_ID=AD_FIELD.AD_Column_ID), Description = (SELECT p.Description FROM AD_PROCESS p, AD_COLUMN c WHERE p.AD_Process_ID=c.AD_Process_ID AND c.AD_Column_ID=AD_FIELD.AD_Column_ID), Help = (SELECT p.Help FROM AD_PROCESS p, AD_COLUMN c WHERE p.AD_Process_ID=c.AD_Process_ID AND c.AD_Column_ID=AD_FIELD.AD_Column_ID), Updated = statement_timestamp() WHERE AD_FIELD.IsCentrallyMaintained='Y' AND AD_FIELD.IsActive='Y' AND EXISTS (SELECT 1 FROM AD_PROCESS p, AD_COLUMN c WHERE c.AD_Process_ID=p.AD_Process_ID AND AD_FIELD.AD_Column_ID=c.AD_Column_ID AND (AD_FIELD.Name<>p.Name OR COALESCE(AD_FIELD.Description,' ')<>COALESCE(p.Description,' ') OR COALESCE(AD_FIELD.Help,' ')<>COALESCE(p.Help,' ')))
;

-- 10/10/2020, 2:33:20 p. m. VET
UPDATE AD_FIELD_TRL SET Name = (SELECT p.Name FROM AD_PROCESS_TRL p, AD_COLUMN c, AD_FIELD f WHERE p.AD_Process_ID=c.AD_Process_ID AND c.AD_Column_ID=f.AD_Column_ID AND f.AD_Field_ID=AD_FIELD_TRL.AD_Field_ID AND p.AD_LANGUAGE=AD_FIELD_TRL.AD_LANGUAGE), Description = (SELECT p.Description FROM AD_PROCESS_TRL p, AD_COLUMN c, AD_FIELD f WHERE p.AD_Process_ID=c.AD_Process_ID AND c.AD_Column_ID=f.AD_Column_ID AND f.AD_Field_ID=AD_FIELD_TRL.AD_Field_ID AND p.AD_LANGUAGE=AD_FIELD_TRL.AD_LANGUAGE), Help = (SELECT p.Help FROM AD_PROCESS_TRL p, AD_COLUMN c, AD_FIELD f WHERE p.AD_Process_ID=c.AD_Process_ID AND c.AD_Column_ID=f.AD_Column_ID AND f.AD_Field_ID=AD_FIELD_TRL.AD_Field_ID AND p.AD_LANGUAGE=AD_FIELD_TRL.AD_LANGUAGE), IsTranslated = (SELECT p.IsTranslated FROM AD_PROCESS_TRL p, AD_COLUMN c, AD_FIELD f WHERE p.AD_Process_ID=c.AD_Process_ID AND c.AD_Column_ID=f.AD_Column_ID AND f.AD_Field_ID=AD_FIELD_TRL.AD_Field_ID AND p.AD_LANGUAGE=AD_FIELD_TRL.AD_LANGUAGE), Updated = statement_timestamp() WHERE EXISTS (SELECT 1 FROM AD_PROCESS_TRL p, AD_COLUMN c, AD_FIELD f WHERE c.AD_Process_ID=p.AD_Process_ID AND f.AD_Column_ID=c.AD_Column_ID AND f.AD_Field_ID=AD_FIELD_TRL.AD_Field_ID AND p.AD_LANGUAGE=AD_FIELD_TRL.AD_LANGUAGE AND f.IsCentrallyMaintained='Y' AND f.IsActive='Y' AND (AD_FIELD_TRL.Name<>p.Name OR COALESCE(AD_FIELD_TRL.Description,' ')<>COALESCE(p.Description,' ') OR COALESCE(AD_FIELD_TRL.Help,' ')<>COALESCE(p.Help,' ')))
;

-- 10/10/2020, 2:33:20 p. m. VET
UPDATE AD_PROCESS_PARA SET ColumnName = (SELECT e.ColumnName FROM AD_ELEMENT e WHERE UPPER(e.ColumnName)=UPPER(AD_PROCESS_PARA.ColumnName)) WHERE AD_PROCESS_PARA.IsCentrallyMaintained='Y' AND AD_PROCESS_PARA.IsActive='Y' AND EXISTS (SELECT 1 FROM AD_ELEMENT e WHERE UPPER(e.ColumnName)=UPPER(AD_PROCESS_PARA.ColumnName) AND e.ColumnName<>AD_PROCESS_PARA.ColumnName)
;

-- 10/10/2020, 2:33:20 p. m. VET
UPDATE AD_PROCESS_PARA SET IsCentrallyMaintained = 'N' WHERE IsCentrallyMaintained <> 'N' AND NOT EXISTS (SELECT 1 FROM AD_ELEMENT e WHERE AD_PROCESS_PARA.ColumnName=e.ColumnName)
;

-- 10/10/2020, 2:33:20 p. m. VET
UPDATE AD_PROCESS_PARA SET Name = (SELECT e.Name FROM AD_ELEMENT e WHERE e.ColumnName=AD_PROCESS_PARA.ColumnName), Description = (SELECT e.Description FROM AD_ELEMENT e WHERE e.ColumnName=AD_PROCESS_PARA.ColumnName), Help = (SELECT e.Help FROM AD_ELEMENT e WHERE e.ColumnName=AD_PROCESS_PARA.ColumnName), Placeholder = (SELECT e.Placeholder FROM AD_ELEMENT e WHERE e.ColumnName=AD_PROCESS_PARA.ColumnName), Updated = statement_timestamp() WHERE AD_PROCESS_PARA.IsCentrallyMaintained='Y' AND AD_PROCESS_PARA.IsActive='Y' AND EXISTS (SELECT 1 FROM AD_ELEMENT e WHERE e.ColumnName=AD_PROCESS_PARA.ColumnName AND (AD_PROCESS_PARA.Name <> e.Name OR COALESCE(AD_PROCESS_PARA.Description,' ') <> COALESCE(e.Description,' ') OR COALESCE(AD_PROCESS_PARA.Help,' ') <> COALESCE(e.Help,' ') OR COALESCE(AD_PROCESS_PARA.Placeholder,' ') <> COALESCE(e.Placeholder,' ')))
;

-- 10/10/2020, 2:33:21 p. m. VET
UPDATE AD_PROCESS_PARA_TRL SET Name = (SELECT et.Name FROM AD_ELEMENT_TRL et, AD_ELEMENT e, AD_PROCESS_PARA f WHERE et.AD_LANGUAGE=AD_PROCESS_PARA_TRL.AD_LANGUAGE AND et.AD_Element_ID=e.AD_Element_ID AND e.ColumnName=f.ColumnName AND f.AD_Process_Para_ID=AD_PROCESS_PARA_TRL.AD_Process_Para_ID), Description = (SELECT et.Description FROM AD_ELEMENT_TRL et, AD_ELEMENT e, AD_PROCESS_PARA f WHERE et.AD_LANGUAGE=AD_PROCESS_PARA_TRL.AD_LANGUAGE AND et.AD_Element_ID=e.AD_Element_ID AND e.ColumnName=f.ColumnName AND f.AD_Process_Para_ID=AD_PROCESS_PARA_TRL.AD_Process_Para_ID), Help = (SELECT et.Help FROM AD_ELEMENT_TRL et, AD_ELEMENT e, AD_PROCESS_PARA f WHERE et.AD_LANGUAGE=AD_PROCESS_PARA_TRL.AD_LANGUAGE AND et.AD_Element_ID=e.AD_Element_ID AND e.ColumnName=f.ColumnName AND f.AD_Process_Para_ID=AD_PROCESS_PARA_TRL.AD_Process_Para_ID), Placeholder = (SELECT et.Placeholder FROM AD_ELEMENT_TRL et, AD_ELEMENT e, AD_PROCESS_PARA f WHERE et.AD_LANGUAGE=AD_PROCESS_PARA_TRL.AD_LANGUAGE AND et.AD_Element_ID=e.AD_Element_ID AND e.ColumnName=f.ColumnName AND f.AD_Process_Para_ID=AD_PROCESS_PARA_TRL.AD_Process_Para_ID), IsTranslated = (SELECT et.IsTranslated FROM AD_ELEMENT_TRL et, AD_ELEMENT e, AD_PROCESS_PARA f WHERE et.AD_LANGUAGE=AD_PROCESS_PARA_TRL.AD_LANGUAGE AND et.AD_Element_ID=e.AD_Element_ID AND e.ColumnName=f.ColumnName AND f.AD_Process_Para_ID=AD_PROCESS_PARA_TRL.AD_Process_Para_ID), Updated = statement_timestamp() WHERE EXISTS (SELECT 1 FROM AD_ELEMENT_TRL et, AD_ELEMENT e, AD_PROCESS_PARA f WHERE et.AD_LANGUAGE=AD_PROCESS_PARA_TRL.AD_LANGUAGE AND et.AD_Element_ID=e.AD_Element_ID AND e.ColumnName=f.ColumnName AND f.AD_Process_Para_ID=AD_PROCESS_PARA_TRL.AD_Process_Para_ID AND f.IsCentrallyMaintained='Y' AND f.IsActive='Y' AND (AD_PROCESS_PARA_TRL.Name <> et.Name OR COALESCE(AD_PROCESS_PARA_TRL.Description,' ') <> COALESCE(et.Description,' ') OR COALESCE(AD_PROCESS_PARA_TRL.Help,' ') <> COALESCE(et.Help,' ') OR COALESCE(AD_PROCESS_PARA_TRL.Placeholder,' ') <> COALESCE(et.Placeholder,' ')))
;

-- 10/10/2020, 2:33:23 p. m. VET
UPDATE AD_INFOCOLUMN_TRL SET Name = (SELECT et.Name FROM AD_ELEMENT_TRL et, AD_ELEMENT e, AD_INFOCOLUMN f WHERE et.AD_LANGUAGE=AD_INFOCOLUMN_TRL.AD_LANGUAGE AND et.AD_Element_ID=e.AD_Element_ID AND e.ColumnName=f.ColumnName AND f.AD_InfoColumn_ID=AD_INFOCOLUMN_TRL.AD_InfoColumn_ID), Description = (SELECT et.Description FROM AD_ELEMENT_TRL et, AD_ELEMENT e, AD_INFOCOLUMN f WHERE et.AD_LANGUAGE=AD_INFOCOLUMN_TRL.AD_LANGUAGE AND et.AD_Element_ID=e.AD_Element_ID AND e.ColumnName=f.ColumnName AND f.AD_InfoColumn_ID=AD_INFOCOLUMN_TRL.AD_InfoColumn_ID), Help = (SELECT et.Help FROM AD_ELEMENT_TRL et, AD_ELEMENT e, AD_INFOCOLUMN f WHERE et.AD_LANGUAGE=AD_INFOCOLUMN_TRL.AD_LANGUAGE AND et.AD_Element_ID=e.AD_Element_ID AND e.ColumnName=f.ColumnName AND f.AD_InfoColumn_ID=AD_INFOCOLUMN_TRL.AD_InfoColumn_ID), Placeholder = (SELECT et.Placeholder FROM AD_ELEMENT_TRL et, AD_ELEMENT e, AD_INFOCOLUMN f WHERE et.AD_LANGUAGE=AD_INFOCOLUMN_TRL.AD_LANGUAGE AND et.AD_Element_ID=e.AD_Element_ID AND e.ColumnName=f.ColumnName AND f.AD_InfoColumn_ID=AD_INFOCOLUMN_TRL.AD_InfoColumn_ID), IsTranslated = (SELECT et.IsTranslated FROM AD_ELEMENT_TRL et, AD_ELEMENT e, AD_INFOCOLUMN f WHERE et.AD_LANGUAGE=AD_INFOCOLUMN_TRL.AD_LANGUAGE AND et.AD_Element_ID=e.AD_Element_ID AND e.ColumnName=f.ColumnName AND f.AD_InfoColumn_ID=AD_INFOCOLUMN_TRL.AD_InfoColumn_ID), Updated = statement_timestamp() WHERE EXISTS (SELECT 1 FROM AD_ELEMENT_TRL et, AD_ELEMENT e, AD_INFOCOLUMN f WHERE et.AD_LANGUAGE=AD_INFOCOLUMN_TRL.AD_LANGUAGE AND et.AD_Element_ID=e.AD_Element_ID AND e.ColumnName=f.ColumnName AND f.AD_InfoColumn_ID=AD_INFOCOLUMN_TRL.AD_InfoColumn_ID AND f.IsCentrallyMaintained='Y' AND f.IsActive='Y' AND (AD_INFOCOLUMN_TRL.Name <> et.Name OR COALESCE(AD_INFOCOLUMN_TRL.Description,' ') <> COALESCE(et.Description,' ') OR COALESCE(AD_INFOCOLUMN_TRL.Help,' ') <> COALESCE(et.Help,' ') OR COALESCE(AD_INFOCOLUMN_TRL.Placeholder,' ') <> COALESCE(et.Placeholder,' ')))
;

-- 10/10/2020, 2:33:23 p. m. VET
UPDATE AD_WF_NODE SET Name = (SELECT w.Name FROM AD_WINDOW w WHERE w.AD_Window_ID=AD_WF_NODE.AD_Window_ID), Description = (SELECT w.Description FROM AD_WINDOW w WHERE w.AD_Window_ID=AD_WF_NODE.AD_Window_ID), Help = (SELECT w.Help FROM AD_WINDOW w WHERE w.AD_Window_ID=AD_WF_NODE.AD_Window_ID) WHERE AD_WF_NODE.IsCentrallyMaintained = 'Y' AND EXISTS (SELECT 1 FROM AD_WINDOW w WHERE w.AD_Window_ID=AD_WF_NODE.AD_Window_ID AND (w.Name <> AD_WF_NODE.Name OR COALESCE(w.Description,' ') <> COALESCE(AD_WF_NODE.Description,' ') OR COALESCE(w.Help,' ') <> COALESCE(AD_WF_NODE.Help,' ')))
;

-- 10/10/2020, 2:33:23 p. m. VET
UPDATE AD_WF_NODE_TRL SET Name = (SELECT t.Name FROM AD_WINDOW_TRL t, AD_WF_NODE n WHERE AD_WF_NODE_TRL.AD_WF_Node_ID=n.AD_WF_Node_ID AND n.AD_Window_ID=t.AD_Window_ID AND AD_WF_NODE_TRL.AD_LANGUAGE=t.AD_LANGUAGE), Description = (SELECT t.Description FROM AD_WINDOW_TRL t, AD_WF_NODE n WHERE AD_WF_NODE_TRL.AD_WF_Node_ID=n.AD_WF_Node_ID AND n.AD_Window_ID=t.AD_Window_ID AND AD_WF_NODE_TRL.AD_LANGUAGE=t.AD_LANGUAGE), Help = (SELECT t.Help FROM AD_WINDOW_TRL t, AD_WF_NODE n WHERE AD_WF_NODE_TRL.AD_WF_Node_ID=n.AD_WF_Node_ID AND n.AD_Window_ID=t.AD_Window_ID AND AD_WF_NODE_TRL.AD_LANGUAGE=t.AD_LANGUAGE) WHERE EXISTS (SELECT 1 FROM AD_WINDOW_TRL t, AD_WF_NODE n WHERE AD_WF_NODE_TRL.AD_WF_Node_ID=n.AD_WF_Node_ID AND n.AD_Window_ID=t.AD_Window_ID AND AD_WF_NODE_TRL.AD_LANGUAGE=t.AD_LANGUAGE AND n.IsCentrallyMaintained='Y' AND n.IsActive='Y' AND (AD_WF_NODE_TRL.Name <> t.Name OR COALESCE(AD_WF_NODE_TRL.Description,' ') <> COALESCE(t.Description,' ') OR COALESCE(AD_WF_NODE_TRL.Help,' ') <> COALESCE(t.Help,' ')))
;

-- 10/10/2020, 2:33:23 p. m. VET
UPDATE AD_WF_NODE SET Name=f.Name,Description=f.Description,Help=f.Help FROM AD_FORM f WHERE f.AD_Form_ID=AD_WF_NODE.AD_Form_ID AND AD_WF_NODE.IsCentrallyMaintained = 'Y' AND EXISTS (SELECT 1 FROM AD_FORM f WHERE f.AD_Form_ID=AD_WF_NODE.AD_Form_ID AND (f.Name <> AD_WF_NODE.Name OR COALESCE(f.Description,' ') <> COALESCE(AD_WF_NODE.Description,' ') OR COALESCE(f.Help,' ') <> COALESCE(AD_WF_NODE.Help,' ')))
;

-- 10/10/2020, 2:33:23 p. m. VET
UPDATE AD_WF_NODE_TRL SET Name=t.Name,Description=t.Description,Help=t.Help FROM AD_FORM_TRL t, AD_WF_NODE n WHERE AD_WF_NODE_TRL.AD_WF_Node_ID=n.AD_WF_Node_ID AND n.AD_Form_ID=t.AD_Form_ID AND AD_WF_NODE_TRL.AD_LANGUAGE=t.AD_LANGUAGE AND EXISTS (SELECT 1 FROM AD_FORM_TRL t, AD_WF_NODE n WHERE AD_WF_NODE_TRL.AD_WF_Node_ID=n.AD_WF_Node_ID AND n.AD_Form_ID=t.AD_Form_ID AND AD_WF_NODE_TRL.AD_LANGUAGE=t.AD_LANGUAGE AND n.IsCentrallyMaintained='Y' AND n.IsActive='Y' AND (AD_WF_NODE_TRL.Name <> t.Name OR COALESCE(AD_WF_NODE_TRL.Description,' ') <> COALESCE(t.Description,' ') OR COALESCE(AD_WF_NODE_TRL.Help,' ') <> COALESCE(t.Help,' ')))
;

-- 10/10/2020, 2:33:23 p. m. VET
UPDATE AD_WF_NODE SET Name=f.Name,Description=f.Description,Help=f.Help FROM AD_PROCESS f WHERE f.AD_Process_ID=AD_WF_NODE.AD_Process_ID AND AD_WF_NODE.IsCentrallyMaintained = 'Y' AND EXISTS (SELECT 1 FROM AD_PROCESS f WHERE f.AD_Process_ID=AD_WF_NODE.AD_Process_ID AND (f.Name <> AD_WF_NODE.Name OR COALESCE(f.Description,' ') <> COALESCE(AD_WF_NODE.Description,' ') OR COALESCE(f.Help,' ') <> COALESCE(AD_WF_NODE.Help,' ')))
;

-- 10/10/2020, 2:33:23 p. m. VET
UPDATE AD_WF_NODE_TRL SET Name=t.Name,Description=t.Description,Help=t.Help FROM AD_PROCESS_TRL t, AD_WF_NODE n WHERE AD_WF_NODE_TRL.AD_WF_Node_ID=n.AD_WF_Node_ID AND n.AD_Process_ID=t.AD_Process_ID AND AD_WF_NODE_TRL.AD_LANGUAGE=t.AD_LANGUAGE AND EXISTS (SELECT 1 FROM AD_PROCESS_TRL t, AD_WF_NODE n WHERE AD_WF_NODE_TRL.AD_WF_Node_ID=n.AD_WF_Node_ID AND n.AD_Process_ID=t.AD_Process_ID AND AD_WF_NODE_TRL.AD_LANGUAGE=t.AD_LANGUAGE AND n.IsCentrallyMaintained='Y' AND n.IsActive='Y' AND (AD_WF_NODE_TRL.Name <> t.Name OR COALESCE(AD_WF_NODE_TRL.Description,' ') <> COALESCE(t.Description,' ') OR COALESCE(AD_WF_NODE_TRL.Help,' ') <> COALESCE(t.Help,' ')))
;

-- 10/10/2020, 2:33:23 p. m. VET
UPDATE AD_PRINTFORMATITEM SET Name = (SELECT e.Name FROM AD_ELEMENT e, AD_COLUMN c WHERE e.AD_Element_ID=c.AD_Element_ID AND c.AD_Column_ID=AD_PRINTFORMATITEM.AD_Column_ID) WHERE AD_PRINTFORMATITEM.IsCentrallyMaintained='Y' AND EXISTS (SELECT 1 FROM AD_ELEMENT e, AD_COLUMN c WHERE e.AD_Element_ID=c.AD_Element_ID AND c.AD_Column_ID=AD_PRINTFORMATITEM.AD_Column_ID AND e.Name<>AD_PRINTFORMATITEM.Name) AND EXISTS (SELECT 1 FROM AD_CLIENT WHERE AD_Client_ID=AD_PRINTFORMATITEM.AD_Client_ID AND IsMultiLingualDocument='Y')
;

-- 10/10/2020, 2:33:24 p. m. VET
UPDATE AD_PRINTFORMATITEM SET PrintName = (SELECT e.PrintName FROM AD_ELEMENT e, AD_COLUMN c WHERE e.AD_Element_ID=c.AD_Element_ID AND c.AD_Column_ID=AD_PRINTFORMATITEM.AD_Column_ID) WHERE AD_PRINTFORMATITEM.IsCentrallyMaintained='Y' AND EXISTS (SELECT 1 FROM AD_ELEMENT e, AD_COLUMN c, AD_PRINTFORMAT pf WHERE e.AD_Element_ID=c.AD_Element_ID AND c.AD_Column_ID=AD_PRINTFORMATITEM.AD_Column_ID AND LENGTH(AD_PRINTFORMATITEM.PrintName) > 0 AND e.PrintName<>AD_PRINTFORMATITEM.PrintName AND pf.AD_PrintFormat_ID=AD_PRINTFORMATITEM.AD_PrintFormat_ID AND pf.IsForm='N' AND IsTableBased='Y') AND EXISTS (SELECT 1 FROM AD_CLIENT WHERE AD_Client_ID=AD_PRINTFORMATITEM.AD_Client_ID AND IsMultiLingualDocument='Y')
;

-- 10/10/2020, 2:33:24 p. m. VET
UPDATE AD_PRINTFORMATITEM_TRL SET Name = (SELECT e.Name FROM AD_ELEMENT_TRL e, AD_COLUMN c, AD_PRINTFORMATITEM p WHERE e.AD_LANGUAGE=AD_PRINTFORMATITEM_TRL.AD_LANGUAGE AND e.AD_Element_ID=c.AD_Element_ID AND c.AD_Column_ID=p.AD_Column_ID AND p.AD_PrintFormatItem_ID=AD_PRINTFORMATITEM_TRL.AD_PrintFormatItem_ID), Updated = statement_timestamp() WHERE EXISTS (SELECT 1 FROM AD_PRINTFORMATITEM p, AD_ELEMENT_TRL e, AD_COLUMN c WHERE AD_PRINTFORMATITEM_TRL.AD_PrintFormatItem_ID=p.AD_PrintFormatItem_ID AND p.AD_Column_ID=c.AD_Column_ID AND c.AD_Element_ID=e.AD_Element_ID AND c.AD_Process_ID IS NULL AND AD_PRINTFORMATITEM_TRL.AD_LANGUAGE=e.AD_LANGUAGE AND p.IsCentrallyMaintained='Y' AND p.IsActive='Y' AND (AD_PRINTFORMATITEM_TRL.Name <> e.Name))
;

-- 10/10/2020, 2:33:24 p. m. VET
UPDATE AD_PRINTFORMATITEM_TRL SET PrintName = (SELECT e.PrintName FROM AD_ELEMENT_TRL e, AD_COLUMN c, AD_PRINTFORMATITEM pfi WHERE e.AD_LANGUAGE=AD_PRINTFORMATITEM_TRL.AD_LANGUAGE AND e.AD_Element_ID=c.AD_Element_ID AND c.AD_Column_ID=pfi.AD_Column_ID AND pfi.AD_PrintFormatItem_ID=AD_PRINTFORMATITEM_TRL.AD_PrintFormatItem_ID) WHERE EXISTS (SELECT 1 FROM AD_ELEMENT_TRL e, AD_COLUMN c, AD_PRINTFORMATITEM pfi, AD_PRINTFORMAT pf WHERE e.AD_LANGUAGE=AD_PRINTFORMATITEM_TRL.AD_LANGUAGE AND e.AD_Element_ID=c.AD_Element_ID AND c.AD_Column_ID=pfi.AD_Column_ID AND pfi.AD_PrintFormatItem_ID=AD_PRINTFORMATITEM_TRL.AD_PrintFormatItem_ID AND pfi.IsCentrallyMaintained='Y' AND LENGTH(pfi.PrintName) > 0 AND (e.PrintName<>AD_PRINTFORMATITEM_TRL.PrintName OR AD_PRINTFORMATITEM_TRL.PrintName IS NULL) AND pf.AD_PrintFormat_ID=pfi.AD_PrintFormat_ID AND pf.IsForm='N' AND IsTableBased='Y') AND EXISTS (SELECT 1 FROM AD_CLIENT WHERE AD_Client_ID=AD_PRINTFORMATITEM_TRL.AD_Client_ID AND IsMultiLingualDocument='Y')
;

-- 10/10/2020, 2:33:24 p. m. VET
UPDATE AD_PRINTFORMATITEM_TRL SET PrintName = (SELECT pfi.PrintName FROM AD_PRINTFORMATITEM pfi WHERE pfi.AD_PrintFormatItem_ID=AD_PRINTFORMATITEM_TRL.AD_PrintFormatItem_ID) WHERE EXISTS (SELECT 1 FROM AD_PRINTFORMATITEM pfi, AD_PRINTFORMAT pf WHERE pfi.AD_PrintFormatItem_ID=AD_PRINTFORMATITEM_TRL.AD_PrintFormatItem_ID AND pfi.IsCentrallyMaintained='Y' AND LENGTH(pfi.PrintName) > 0 AND pfi.PrintName<>AD_PRINTFORMATITEM_TRL.PrintName AND pf.AD_PrintFormat_ID=pfi.AD_PrintFormat_ID AND pf.IsForm='N' AND pf.IsTableBased='Y') AND EXISTS (SELECT 1 FROM AD_CLIENT WHERE AD_Client_ID=AD_PRINTFORMATITEM_TRL.AD_Client_ID AND IsMultiLingualDocument='N')
;

-- 10/10/2020, 2:33:24 p. m. VET
UPDATE AD_PRINTFORMATITEM_TRL SET PrintName = NULL WHERE PrintName IS NOT NULL AND EXISTS (SELECT 1 FROM AD_PRINTFORMATITEM pfi WHERE pfi.AD_PrintFormatItem_ID=AD_PRINTFORMATITEM_TRL.AD_PrintFormatItem_ID AND pfi.IsCentrallyMaintained='Y' AND (LENGTH (pfi.PrintName) = 0 OR pfi.PrintName IS NULL))
;

-- 10/10/2020, 2:33:24 p. m. VET
UPDATE AD_MENU SET Name = (SELECT Name FROM AD_WINDOW w WHERE AD_MENU.AD_Window_ID=w.AD_Window_ID), Description = (SELECT Description FROM AD_WINDOW w WHERE AD_MENU.AD_Window_ID=w.AD_Window_ID) WHERE AD_MENU.AD_Window_ID IS NOT NULL AND AD_MENU."action" = 'W' AND AD_MENU.IsCentrallyMaintained='Y' AND AD_MENU.IsActive='Y'
;

-- 10/10/2020, 2:33:25 p. m. VET
UPDATE AD_MENU_TRL SET Name = (SELECT wt.Name FROM AD_WINDOW_TRL wt, AD_MENU m WHERE AD_MENU_TRL.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Window_ID=wt.AD_Window_ID AND AD_MENU_TRL.AD_LANGUAGE=wt.AD_LANGUAGE), Description = (SELECT wt.Description FROM AD_WINDOW_TRL wt, AD_MENU m WHERE AD_MENU_TRL.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Window_ID=wt.AD_Window_ID AND AD_MENU_TRL.AD_LANGUAGE=wt.AD_LANGUAGE), IsTranslated = (SELECT wt.IsTranslated FROM AD_WINDOW_TRL wt, AD_MENU m WHERE AD_MENU_TRL.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Window_ID=wt.AD_Window_ID AND AD_MENU_TRL.AD_LANGUAGE=wt.AD_LANGUAGE) WHERE EXISTS (SELECT 1 FROM AD_WINDOW_TRL wt, AD_MENU m WHERE AD_MENU_TRL.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Window_ID=wt.AD_Window_ID AND AD_MENU_TRL.AD_LANGUAGE=wt.AD_LANGUAGE AND m.AD_Window_ID IS NOT NULL AND m."action" = 'W' AND m.IsCentrallyMaintained='Y' AND m.IsActive='Y')
;

-- 10/10/2020, 2:33:25 p. m. VET
UPDATE AD_MENU SET Name = (SELECT p.Name FROM AD_PROCESS p WHERE AD_MENU.AD_Process_ID=p.AD_Process_ID), Description = (SELECT p.Description FROM AD_PROCESS p WHERE AD_MENU.AD_Process_ID=p.AD_Process_ID) WHERE AD_MENU.AD_Process_ID IS NOT NULL AND AD_MENU."action" IN ('R', 'P') AND AD_MENU.IsCentrallyMaintained='Y' AND AD_MENU.IsActive='Y'
;

-- 10/10/2020, 2:33:25 p. m. VET
UPDATE AD_MENU_TRL SET Name = (SELECT pt.Name FROM AD_PROCESS_TRL pt, AD_MENU m WHERE AD_MENU_TRL.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Process_ID=pt.AD_Process_ID AND AD_MENU_TRL.AD_LANGUAGE=pt.AD_LANGUAGE), Description = (SELECT pt.Description FROM AD_PROCESS_TRL pt, AD_MENU m WHERE AD_MENU_TRL.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Process_ID=pt.AD_Process_ID AND AD_MENU_TRL.AD_LANGUAGE=pt.AD_LANGUAGE), IsTranslated = (SELECT pt.IsTranslated FROM AD_PROCESS_TRL pt, AD_MENU m WHERE AD_MENU_TRL.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Process_ID=pt.AD_Process_ID AND AD_MENU_TRL.AD_LANGUAGE=pt.AD_LANGUAGE) WHERE EXISTS (SELECT 1 FROM AD_PROCESS_TRL pt, AD_MENU m WHERE AD_MENU_TRL.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Process_ID=pt.AD_Process_ID AND AD_MENU_TRL.AD_LANGUAGE=pt.AD_LANGUAGE AND m.AD_Process_ID IS NOT NULL AND m."action" IN ('R', 'P') AND m.IsCentrallyMaintained='Y' AND m.IsActive='Y')
;

-- 10/10/2020, 2:33:25 p. m. VET
UPDATE AD_MENU SET Name = (SELECT Name FROM AD_FORM f WHERE AD_MENU.AD_Form_ID=f.AD_Form_ID), Description = (SELECT Description FROM AD_FORM f WHERE AD_MENU.AD_Form_ID=f.AD_Form_ID) WHERE AD_MENU.AD_Form_ID IS NOT NULL AND AD_MENU."action" = 'X' AND AD_MENU.IsCentrallyMaintained='Y' AND AD_MENU.IsActive='Y'
;

-- 10/10/2020, 2:33:25 p. m. VET
UPDATE AD_MENU_TRL SET Name = (SELECT ft.Name FROM AD_FORM_TRL ft, AD_MENU m WHERE AD_MENU_TRL.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Form_ID=ft.AD_Form_ID AND AD_MENU_TRL.AD_LANGUAGE=ft.AD_LANGUAGE), Description = (SELECT ft.Description FROM AD_FORM_TRL ft, AD_MENU m WHERE AD_MENU_TRL.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Form_ID=ft.AD_Form_ID AND AD_MENU_TRL.AD_LANGUAGE=ft.AD_LANGUAGE), IsTranslated = (SELECT ft.IsTranslated FROM AD_FORM_TRL ft, AD_MENU m WHERE AD_MENU_TRL.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Form_ID=ft.AD_Form_ID AND AD_MENU_TRL.AD_LANGUAGE=ft.AD_LANGUAGE) WHERE EXISTS (SELECT 1 FROM AD_FORM_TRL ft, AD_MENU m WHERE AD_MENU_TRL.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Form_ID=ft.AD_Form_ID AND AD_MENU_TRL.AD_LANGUAGE=ft.AD_LANGUAGE AND m.AD_Form_ID IS NOT NULL AND m."action" = 'X' AND m.IsCentrallyMaintained='Y' AND m.IsActive='Y')
;

-- 10/10/2020, 2:33:25 p. m. VET
UPDATE AD_MENU SET Name = (SELECT p.Name FROM AD_WORKFLOW p WHERE AD_MENU.AD_Workflow_ID=p.AD_Workflow_ID), Description = (SELECT p.Description FROM AD_WORKFLOW p WHERE AD_MENU.AD_Workflow_ID=p.AD_Workflow_ID) WHERE AD_MENU.AD_Workflow_ID IS NOT NULL AND AD_MENU."action" = 'F' AND AD_MENU.IsCentrallyMaintained='Y' AND AD_MENU.IsActive='Y'
;

-- 10/10/2020, 2:33:25 p. m. VET
UPDATE AD_MENU_TRL SET Name = (SELECT pt.Name FROM AD_WORKFLOW_TRL pt, AD_MENU m WHERE AD_MENU_TRL.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Workflow_ID=pt.AD_Workflow_ID AND AD_MENU_TRL.AD_LANGUAGE=pt.AD_LANGUAGE), Description = (SELECT pt.Description FROM AD_WORKFLOW_TRL pt, AD_MENU m WHERE AD_MENU_TRL.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Workflow_ID=pt.AD_Workflow_ID AND AD_MENU_TRL.AD_LANGUAGE=pt.AD_LANGUAGE), IsTranslated = (SELECT pt.IsTranslated FROM AD_WORKFLOW_TRL pt, AD_MENU m WHERE AD_MENU_TRL.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Workflow_ID=pt.AD_Workflow_ID AND AD_MENU_TRL.AD_LANGUAGE=pt.AD_LANGUAGE) WHERE EXISTS (SELECT 1 FROM AD_WORKFLOW_TRL pt, AD_MENU m WHERE AD_MENU_TRL.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Workflow_ID=pt.AD_Workflow_ID AND AD_MENU_TRL.AD_LANGUAGE=pt.AD_LANGUAGE AND m.AD_Workflow_ID IS NOT NULL AND m."action" = 'F' AND m.IsCentrallyMaintained='Y' AND m.IsActive='Y')
;

-- 10/10/2020, 2:33:25 p. m. VET
UPDATE AD_MENU SET Name = (SELECT Name FROM AD_TASK f WHERE AD_MENU.AD_Task_ID=f.AD_Task_ID), Description = (SELECT Description FROM AD_TASK f WHERE AD_MENU.AD_Task_ID=f.AD_Task_ID) WHERE AD_MENU.AD_Task_ID IS NOT NULL AND AD_MENU."action" = 'T' AND AD_MENU.IsCentrallyMaintained='Y' AND AD_MENU.IsActive='Y'
;

-- 10/10/2020, 2:33:25 p. m. VET
UPDATE AD_MENU_TRL SET Name = (SELECT ft.Name FROM AD_TASK_TRL ft, AD_MENU m WHERE AD_MENU_TRL.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Task_ID=ft.AD_Task_ID AND AD_MENU_TRL.AD_LANGUAGE=ft.AD_LANGUAGE), Description = (SELECT ft.Description FROM AD_TASK_TRL ft, AD_MENU m WHERE AD_MENU_TRL.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Task_ID=ft.AD_Task_ID AND AD_MENU_TRL.AD_LANGUAGE=ft.AD_LANGUAGE), IsTranslated = (SELECT ft.IsTranslated FROM AD_TASK_TRL ft, AD_MENU m WHERE AD_MENU_TRL.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Task_ID=ft.AD_Task_ID AND AD_MENU_TRL.AD_LANGUAGE=ft.AD_LANGUAGE) WHERE EXISTS (SELECT 1 FROM AD_TASK_TRL ft, AD_MENU m WHERE AD_MENU_TRL.AD_Menu_ID=m.AD_Menu_ID AND m.AD_Task_ID=ft.AD_Task_ID AND AD_MENU_TRL.AD_LANGUAGE=ft.AD_LANGUAGE AND m.AD_Task_ID IS NOT NULL AND m."action" = 'T' AND m.IsCentrallyMaintained='Y' AND m.IsActive='Y')
;

-- 10/10/2020, 2:33:25 p. m. VET
UPDATE AD_MENU SET Name = (SELECT Name FROM AD_InfoWindow f WHERE AD_MENU.AD_InfoWindow_ID=f.AD_InfoWindow_ID), Description = (SELECT Description FROM AD_InfoWindow f WHERE AD_MENU.AD_InfoWindow_ID=f.AD_InfoWindow_ID) WHERE AD_MENU.AD_InfoWindow_ID IS NOT NULL AND AD_MENU."action" = 'I' AND AD_MENU.IsCentrallyMaintained='Y' AND AD_MENU.IsActive='Y'
;

-- 10/10/2020, 2:33:25 p. m. VET
UPDATE AD_MENU_TRL SET Name = (SELECT ft.Name FROM AD_InfoWindow_TRL ft, AD_MENU m WHERE AD_MENU_TRL.AD_Menu_ID=m.AD_Menu_ID AND m.AD_InfoWindow_ID=ft.AD_InfoWindow_ID AND AD_MENU_TRL.AD_LANGUAGE=ft.AD_LANGUAGE), Description = (SELECT ft.Description FROM AD_InfoWindow_TRL ft, AD_MENU m WHERE AD_MENU_TRL.AD_Menu_ID=m.AD_Menu_ID AND m.AD_InfoWindow_ID=ft.AD_InfoWindow_ID AND AD_MENU_TRL.AD_LANGUAGE=ft.AD_LANGUAGE), IsTranslated = (SELECT ft.IsTranslated FROM AD_InfoWindow_TRL ft, AD_MENU m WHERE AD_MENU_TRL.AD_Menu_ID=m.AD_Menu_ID AND m.AD_InfoWindow_ID=ft.AD_InfoWindow_ID AND AD_MENU_TRL.AD_LANGUAGE=ft.AD_LANGUAGE) WHERE EXISTS (SELECT 1 FROM AD_InfoWindow_TRL ft, AD_MENU m WHERE AD_MENU_TRL.AD_Menu_ID=m.AD_Menu_ID AND m.AD_InfoWindow_ID=ft.AD_InfoWindow_ID AND AD_MENU_TRL.AD_LANGUAGE=ft.AD_LANGUAGE AND m.AD_InfoWindow_ID IS NOT NULL AND m."action" = 'I' AND m.IsCentrallyMaintained='Y' AND m.IsActive='Y')
;

-- 10/10/2020, 2:33:25 p. m. VET
UPDATE AD_COLUMN_TRL SET Name = (SELECT e.Name FROM AD_COLUMN c INNER JOIN AD_ELEMENT_TRL e ON (c.AD_Element_ID=e.AD_Element_ID) WHERE AD_COLUMN_TRL.AD_Column_ID=c.AD_Column_ID AND AD_COLUMN_TRL.AD_LANGUAGE=e.AD_LANGUAGE) WHERE EXISTS (SELECT 1 FROM AD_COLUMN c INNER JOIN AD_ELEMENT_TRL e ON (c.AD_Element_ID=e.AD_Element_ID) WHERE AD_COLUMN_TRL.AD_Column_ID=c.AD_Column_ID AND AD_COLUMN_TRL.AD_LANGUAGE=e.AD_LANGUAGE AND AD_COLUMN_TRL.Name<>e.Name)
;

-- 10/10/2020, 2:33:25 p. m. VET
UPDATE AD_TABLE SET Name=e.Name,Description=e.Description FROM AD_ELEMENT e WHERE AD_TABLE.TableName||'_ID'=e.ColumnName AND EXISTS (SELECT 1 FROM AD_ELEMENT e WHERE AD_TABLE.TableName||'_ID'=e.ColumnName AND AD_TABLE.Name<>e.Name)
;

-- 10/10/2020, 2:33:25 p. m. VET
UPDATE AD_TABLE_TRL SET Name = (SELECT e.Name FROM AD_TABLE t INNER JOIN AD_ELEMENT ex ON (t.TableName||'_ID'=ex.ColumnName) INNER JOIN AD_ELEMENT_TRL e ON (ex.AD_Element_ID=e.AD_Element_ID) WHERE AD_TABLE_TRL.AD_Table_ID=t.AD_Table_ID AND AD_TABLE_TRL.AD_LANGUAGE=e.AD_LANGUAGE) WHERE EXISTS (SELECT 1 FROM AD_TABLE t INNER JOIN AD_ELEMENT ex ON (t.TableName||'_ID'=ex.ColumnName) INNER JOIN AD_ELEMENT_TRL e ON (ex.AD_Element_ID=e.AD_Element_ID) WHERE AD_TABLE_TRL.AD_Table_ID=t.AD_Table_ID AND AD_TABLE_TRL.AD_LANGUAGE=e.AD_LANGUAGE AND AD_TABLE_TRL.Name<>e.Name)
;

-- 10/10/2020, 2:33:25 p. m. VET
UPDATE AD_TABLE SET Name=e.Name||' Trl',Description=e.Description FROM AD_ELEMENT e WHERE SUBSTR(AD_TABLE.TableName,1,LENGTH(AD_TABLE.TableName)-4)||'_ID'=e.ColumnName AND AD_TABLE.TableName LIKE '%_Trl' AND EXISTS (SELECT 1 FROM AD_ELEMENT e WHERE SUBSTR(AD_TABLE.TableName,1,LENGTH(AD_TABLE.TableName)-4)||'_ID'=e.ColumnName AND AD_TABLE.Name<>e.Name)
;

-- 10/10/2020, 2:33:25 p. m. VET
UPDATE AD_TABLE_TRL SET Name = (SELECT e.Name || ' **' FROM AD_TABLE t INNER JOIN AD_ELEMENT ex ON (SUBSTR(t.TableName,1,LENGTH(t.TableName)-4)||'_ID'=ex.ColumnName) INNER JOIN AD_ELEMENT_TRL e ON (ex.AD_Element_ID=e.AD_Element_ID) WHERE AD_TABLE_TRL.AD_Table_ID=t.AD_Table_ID AND AD_TABLE_TRL.AD_LANGUAGE=e.AD_LANGUAGE) WHERE EXISTS (SELECT 1 FROM AD_TABLE t INNER JOIN AD_ELEMENT ex ON (SUBSTR(t.TableName,1,LENGTH(t.TableName)-4)||'_ID'=ex.ColumnName) INNER JOIN AD_ELEMENT_TRL e ON (ex.AD_Element_ID=e.AD_Element_ID) WHERE AD_TABLE_TRL.AD_Table_ID=t.AD_Table_ID AND AD_TABLE_TRL.AD_LANGUAGE=e.AD_LANGUAGE AND t.TableName LIKE '%_Trl' AND AD_TABLE_TRL.Name<>e.Name)
;

