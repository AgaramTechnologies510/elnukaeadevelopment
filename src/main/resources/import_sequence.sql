


--kumu1 start 
-- barcodemaster_seq
DO
$do$
DECLARE
   _kind char;
   _max_barcodeno bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'barcodemaster_seq';

   SELECT COALESCE(MAX(barcodeno), 0) INTO _max_barcodeno
   FROM barcodemaster;

   IF _kind IS NULL THEN
      CREATE SEQUENCE barcodemaster_seq;
      IF _max_barcodeno > 0 THEN
         PERFORM setval('barcodemaster_seq', _max_barcodeno);
           RAISE NOTICE '2';
      ELSE
         RAISE NOTICE 'Sequence created with default start value (no data in table).';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_barcodeno > 0 THEN
         PERFORM setval('barcodemaster_seq', _max_barcodeno);
         RAISE NOTICE 'Sequence updated to %', _max_barcodeno;
      ELSE
         RAISE NOTICE 'Sequence exists, but no data to update.';
      END IF;
   ELSE
      RAISE NOTICE 'Object named barcodemaster_seq exists but is not a sequence.';
   END IF;
END
$do$;


-- cloudlsprotocolorderversionstep_seq
DO
$do$
DECLARE
   _kind char;
   _max_idversioncode bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'cloudlsprotocolorderversionstep_seq';

   SELECT COALESCE(MAX(idversioncode), 0) INTO _max_idversioncode
   FROM cloudlsprotocolorderversionstep;

   IF _kind IS NULL THEN
      CREATE SEQUENCE cloudlsprotocolorderversionstep_seq;
      IF _max_idversioncode > 0 THEN
         PERFORM setval('cloudlsprotocolorderversionstep_seq', _max_idversioncode);
           RAISE NOTICE '3';
      ELSE
         RAISE NOTICE 'Sequence created with default start value (no data in table).';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_idversioncode > 0 THEN
         PERFORM setval('cloudlsprotocolorderversionstep_seq', _max_idversioncode);
         RAISE NOTICE 'Sequence updated to %', _max_idversioncode;
      ELSE
         RAISE NOTICE 'Sequence exists, but no data to update.';
      END IF;
   ELSE
      RAISE NOTICE 'Object named cloudlsprotocolorderversionstep_seq exists but is not a sequence.';
   END IF;
END
$do$;


-- cloudlsprotocolversionstep_seq
DO
$do$
DECLARE
   _kind char;
   _max_idversioncode bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'cloudlsprotocolversionstep_seq';

   SELECT COALESCE(MAX(idversioncode), 0) INTO _max_idversioncode
   FROM cloudlsprotocolversionstep;

   IF _kind IS NULL THEN
      CREATE SEQUENCE cloudlsprotocolversionstep_seq;
      IF _max_idversioncode > 0 THEN
         PERFORM setval('cloudlsprotocolversionstep_seq', _max_idversioncode);
           RAISE NOTICE '4';
      ELSE
         RAISE NOTICE 'Sequence created with default start value (no data in table).';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_idversioncode > 0 THEN
         PERFORM setval('cloudlsprotocolversionstep_seq', _max_idversioncode);
         RAISE NOTICE 'Sequence updated to %', _max_idversioncode;
      ELSE
         RAISE NOTICE 'Sequence exists, but no data to update.';
      END IF;
   ELSE
      RAISE NOTICE 'Object named cloudlsprotocolversionstep_seq exists but is not a sequence.';
   END IF;
END
$do$;


-- cloudparserfile_seq
DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'cloudparserfile_seq';

   SELECT COALESCE(MAX(parserfilecode), 0) INTO _max_value
   FROM cloudparserfile;

   IF _kind IS NULL THEN
      CREATE SEQUENCE cloudparserfile_seq;
      IF _max_value > 0 THEN
         PERFORM setval('cloudparserfile_seq', _max_value);
           RAISE NOTICE '5';
      ELSE
         RAISE NOTICE 'Sequence created with default start value (no data in table).';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('cloudparserfile_seq', _max_value);
         RAISE NOTICE 'Sequence updated to %', _max_value;
      ELSE
         RAISE NOTICE 'Sequence exists, but no data to update.';
      END IF;
   ELSE
      RAISE NOTICE 'Object named cloudparserfile_seq exists but is not a sequence.';
   END IF;
END
$do$;


-- datasourceconfig_seq

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'datasourceconfig_seq';

   SELECT COALESCE(MAX(id), 0) INTO _max_value
   FROM datasourceconfig;

   IF _kind IS NULL THEN
      CREATE SEQUENCE datasourceconfig_seq;
      IF _max_value > 0 THEN
         PERFORM setval('datasourceconfig_seq', _max_value);
           RAISE NOTICE '6';
      ELSE
         RAISE NOTICE 'Sequence created with default start value (no data in table).';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('datasourceconfig_seq', _max_value);
         RAISE NOTICE 'Sequence updated to %', _max_value;
      ELSE
         RAISE NOTICE 'Sequence exists, but no data to update.';
      END IF;
   ELSE
      RAISE NOTICE 'Object named datasourceconfig_seq exists but is not a sequence.';
   END IF;
END
$do$;


--kumu1 End 

-- kumu2 start 
-- elnfileattachments_seq
DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'elnfileattachments_seq';

   SELECT COALESCE(MAX(attachmentcode), 0) INTO _max_value
   FROM elnfileattachments;

   IF _kind IS NULL THEN
      CREATE SEQUENCE elnfileattachments_seq;
      IF _max_value > 0 THEN
         PERFORM setval('elnfileattachments_seq', _max_value);
           RAISE NOTICE '7';
      ELSE
         RAISE NOTICE 'Sequence created with default start value (no data in table).';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('elnfileattachments_seq', _max_value);
         RAISE NOTICE 'Sequence updated to %', _max_value;
      ELSE
         RAISE NOTICE 'Sequence exists, but no data to update.';
      END IF;
   ELSE
      RAISE NOTICE 'Object named elnfileattachments_seq exists but is not a sequence.';
   END IF;
END
$do$;


-- elnmaterial_nmaterialcode_seq
DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'elnmaterial_nmaterialcode_seq';

   SELECT COALESCE(MAX(nmaterialcode), 0) INTO _max_value
   FROM elnmaterial;

   IF _kind IS NULL THEN
      CREATE SEQUENCE elnmaterial_nmaterialcode_seq;
      IF _max_value > 0 THEN
         PERFORM setval('elnmaterial_nmaterialcode_seq', _max_value);
            RAISE NOTICE '8';
      ELSE
         RAISE NOTICE 'Sequence created with default start value (no data in table).';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('elnmaterial_nmaterialcode_seq', _max_value);
         RAISE NOTICE 'Sequence updated to %', _max_value;
      ELSE
         RAISE NOTICE 'Sequence exists, but no data to update.';
      END IF;
   ELSE
      RAISE NOTICE 'Object named elnmaterial_nmaterialcode_seq exists but is not a sequence.';
   END IF;
END
$do$;


-- elnmaterialinventory_nmaterialinventorycode_seq
DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'elnmaterialinventory_nmaterialinventorycode_seq';

   SELECT COALESCE(MAX(nmaterialinventorycode), 0) INTO _max_value
   FROM elnmaterialinventory;

   IF _kind IS NULL THEN
      CREATE SEQUENCE elnmaterialinventory_nmaterialinventorycode_seq;
      IF _max_value > 0 THEN
         PERFORM setval('elnmaterialinventory_nmaterialinventorycode_seq', _max_value);
           RAISE NOTICE '9';
      ELSE
         RAISE NOTICE 'Sequence created with default start value (no data in table).';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('elnmaterialinventory_nmaterialinventorycode_seq', _max_value);
         RAISE NOTICE 'Sequence updated to %', _max_value;
      ELSE
         RAISE NOTICE 'Sequence exists, but no data to update.';
      END IF;
   ELSE
      RAISE NOTICE 'Object named elnmaterialinventory_nmaterialinventorycode_seq exists but is not a sequence.';
   END IF;
END
$do$;


-- elnprotocoltemplateworkflowgroupmap_seq
DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'elnprotocoltemplateworkflowgroupmap_seq';

   SELECT COALESCE(MAX(workflowmapid), 0) INTO _max_value
   FROM elnprotocoltemplateworkflowgroupmap;

   IF _kind IS NULL THEN
      CREATE SEQUENCE elnprotocoltemplateworkflowgroupmap_seq;
      IF _max_value > 0 THEN
         PERFORM setval('elnprotocoltemplateworkflowgroupmap_seq', _max_value);
           RAISE NOTICE '10';
      ELSE
         RAISE NOTICE 'Sequence created with default start value (no data in table).';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('elnprotocoltemplateworkflowgroupmap_seq', _max_value);
         RAISE NOTICE 'Sequence updated to %', _max_value;
      ELSE
         RAISE NOTICE 'Sequence exists, but no data to update.';
      END IF;
   ELSE
      RAISE NOTICE 'Object named elnprotocoltemplateworkflowgroupmap_seq exists but is not a sequence.';
   END IF;
END
$do$;


-- elnprotocolworkflow_seq
DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT c.relkind INTO _kind
   FROM pg_class c
   WHERE c.relname = 'elnprotocolworkflow_seq'
   LIMIT 1;

   SELECT COALESCE(MAX(workflowcode), 0) INTO _max_value
   FROM elnprotocolworkflow;

   IF _kind IS NULL THEN
      CREATE SEQUENCE elnprotocolworkflow_seq;
      IF _max_value > 0 THEN
         PERFORM setval('elnprotocolworkflow_seq', _max_value);
         RAISE NOTICE '11';
      ELSE
         RAISE NOTICE 'Sequence created with default start value (no data in table).';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('elnprotocolworkflow_seq', _max_value);
         RAISE NOTICE 'Sequence updated to %', _max_value;
      ELSE
         RAISE NOTICE 'Sequence exists, but no data to update.';
      END IF;
   ELSE
      RAISE NOTICE 'Object named elnprotocolworkflow_seq exists but is not a sequence.';
   END IF;
END
$do$;


--kumu 2 end 

--kumu 3 start
-- elnprotocolworkflowgroupmap_seq
DO
$$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'elnprotocolworkflowgroupmap_seq'
   LIMIT 1;

   SELECT COALESCE(MAX(workflowmapid), 0) INTO _max_value
   FROM elnprotocolworkflowgroupmap;

   IF _kind IS NULL THEN
      CREATE SEQUENCE elnprotocolworkflowgroupmap_seq;
      RAISE NOTICE 'Sequence created';
   END IF;

   IF _max_value > 0 THEN
      PERFORM setval('elnprotocolworkflowgroupmap_seq', _max_value, true);
      RAISE NOTICE 'Sequence value set to %', _max_value;
   ELSE
      -- Optional: reset to 1 but mark as not-called-yet
      PERFORM setval('elnprotocolworkflowgroupmap_seq', 1, false);
      RAISE NOTICE 'Sequence reset to 1';
   END IF;
END
$$;

-- elnresultequipment_nresultequipmentcode_seq
DO
$$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'elnresultequipment_nresultequipmentcode_seq'
   LIMIT 1;

   SELECT COALESCE(MAX(nresultequipmentcode), 0) INTO _max_value
   FROM elnresultequipment;

   IF _kind IS NULL THEN
      CREATE SEQUENCE elnresultequipment_nresultequipmentcode_seq;
      RAISE NOTICE 'Sequence created';
   END IF;

   IF _max_value > 0 THEN
      PERFORM setval('elnresultequipment_nresultequipmentcode_seq', _max_value, true);
      RAISE NOTICE 'Sequence value set to %', _max_value;
   ELSE
      -- Optional: reset to 1 but mark as not-called-yet
      PERFORM setval('elnresultequipment_nresultequipmentcode_seq', 1, false);
      RAISE NOTICE 'Sequence reset to 1';
   END IF;
END
$$;

-- elnresultusedmaterial_nresultusedmaterialcode_seq
DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'elnresultusedmaterial_nresultusedmaterialcode_seq'
   LIMIT 1;

   SELECT COALESCE(MAX(nresultusedmaterialcode), 0) INTO _max_value
   FROM elnresultusedmaterial;

   IF _kind IS NULL THEN
      CREATE SEQUENCE elnresultusedmaterial_nresultusedmaterialcode_seq;
      IF _max_value > 0 THEN
         PERFORM setval('elnresultusedmaterial_nresultusedmaterialcode_seq', _max_value);
      END IF;
      RAISE NOTICE '14';
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('elnresultusedmaterial_nresultusedmaterialcode_seq', _max_value);
      END IF;
      RAISE NOTICE 'Sequence updated to %', _max_value;
   ELSE
      RAISE NOTICE 'Object named elnresultusedmaterial_nresultusedmaterialcode_seq exists but is not a sequence.';
   END IF;
END
$do$;

-- email_seq
DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'email_seq'
   LIMIT 1;

   SELECT COALESCE(MAX(id), 0) INTO _max_value
   FROM Email;

   IF _kind IS NULL THEN
      CREATE SEQUENCE email_seq;
      IF _max_value > 0 THEN
         PERFORM setval('email_seq', _max_value);
      END IF;
      RAISE NOTICE '15';
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('email_seq', _max_value);
      END IF;
      RAISE NOTICE 'Sequence updated to %', _max_value;
   ELSE
      RAISE NOTICE 'Object named email_seq exists but is not a sequence.';
   END IF;
END
$do$;

-- equipment_nequipmentcode_seq
DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'equipment_nequipmentcode_seq'
   LIMIT 1;

   SELECT COALESCE(MAX(nequipmentcode), 0) INTO _max_value
   FROM equipment;

   IF _kind IS NULL THEN
      CREATE SEQUENCE equipment_nequipmentcode_seq;
      IF _max_value > 0 THEN
         PERFORM setval('equipment_nequipmentcode_seq', _max_value);
      END IF;
      RAISE NOTICE '16';
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('equipment_nequipmentcode_seq', _max_value);
      END IF;
      RAISE NOTICE 'Sequence updated to %', _max_value;
   ELSE
      RAISE NOTICE 'Object named equipment_nequipmentcode_seq exists but is not a sequence.';
   END IF;
END
$do$;

-- equipmentcategory_nequipmentcatcode_seq
DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'equipmentcategory_nequipmentcatcode_seq'
   LIMIT 1;

   SELECT COALESCE(MAX(nequipmentcatcode), 0) INTO _max_value
   FROM equipmentcategory;

   IF _kind IS NULL THEN
      CREATE SEQUENCE equipmentcategory_nequipmentcatcode_seq;
      IF _max_value > 0 THEN
         PERFORM setval('equipmentcategory_nequipmentcatcode_seq', _max_value);
      END IF;
      RAISE NOTICE '17';
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('equipmentcategory_nequipmentcatcode_seq', _max_value);
      END IF;
      RAISE NOTICE 'Sequence updated to %', _max_value;
   ELSE
      RAISE NOTICE 'Object named equipmentcategory_nequipmentcatcode_seq exists but is not a sequence.';
   END IF;
END
$do$;

-- equipmenthistory_nequipmenthistorycode_seq
DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'equipmenthistory_nequipmenthistorycode_seq'
   LIMIT 1;

   SELECT COALESCE(MAX(nequipmenthistorycode), 0) INTO _max_value
   FROM equipmenthistory;

   IF _kind IS NULL THEN
      CREATE SEQUENCE equipmenthistory_nequipmenthistorycode_seq;
      IF _max_value > 0 THEN
         PERFORM setval('equipmenthistory_nequipmenthistorycode_seq', _max_value);
      END IF;
      RAISE NOTICE '18';
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('equipmenthistory_nequipmenthistorycode_seq', _max_value);
      END IF;
      RAISE NOTICE 'Sequence updated to %', _max_value;
   ELSE
      RAISE NOTICE 'Object named equipmenthistory_nequipmenthistorycode_seq exists but is not a sequence.';
   END IF;
END
$do$;

-- equipmenttype_nequipmenttypecode_seq
DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'equipmenttype_nequipmenttypecode_seq'
   LIMIT 1;

   SELECT COALESCE(MAX(nequipmenttypecode), 0) INTO _max_value
   FROM equipmenttype;

   IF _kind IS NULL THEN
      CREATE SEQUENCE equipmenttype_nequipmenttypecode_seq;
      IF _max_value > 0 THEN
         PERFORM setval('equipmenttype_nequipmenttypecode_seq', _max_value);
      END IF;
      RAISE NOTICE '19';
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('equipmenttype_nequipmenttypecode_seq', _max_value);
      END IF;
      RAISE NOTICE 'Sequence updated to %', _max_value;
   ELSE
      RAISE NOTICE 'Object named equipmenttype_nequipmenttypecode_seq exists but is not a sequence.';
   END IF;
END
$do$;

--kumu end 
DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'helpdocument_seq';

   SELECT COALESCE(MAX(id), 0) INTO _max_value
   FROM helpdocument;

   IF _kind IS NULL THEN
      CREATE SEQUENCE helpdocument_seq;
      IF _max_value > 0 THEN
         PERFORM setval('helpdocument_seq', _max_value);
          RAISE NOTICE '20';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('helpdocument_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named helpdocument_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'inventorybarcodemap_seq';

   SELECT COALESCE(MAX(barcodemapid), 0) INTO _max_value
   FROM inventorybarcodemap;

   IF _kind IS NULL THEN
      CREATE SEQUENCE inventorybarcodemap_seq;
      IF _max_value > 0 THEN
         PERFORM setval('inventorybarcodemap_seq', _max_value);
         RAISE NOTICE '21';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('inventorybarcodemap_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named inventorybarcodemap_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsactivewidgets_seq';

   SELECT COALESCE(MAX(activewidgetscode), 0) INTO _max_value
   FROM lsactivewidgets;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsactivewidgets_seq;
      IF _max_value > 0 THEN
         PERFORM setval('lsactivewidgets_seq', _max_value);
         RAISE NOTICE '22';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsactivewidgets_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsactivewidgets_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsactivity_seq';

   SELECT COALESCE(MAX(activitycode), 0) INTO _max_value
   FROM lsactivity;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsactivity_seq;
      IF _max_value > 0 THEN
         PERFORM setval('lsactivity_seq', _max_value);
         RAISE NOTICE '23';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsactivity_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsactivity_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsaudittrailconfiguration_seq';

   SELECT COALESCE(MAX(auditcofigcode), 0) INTO _max_value
   FROM lsaudittrailconfiguration;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsaudittrailconfiguration_seq;
      IF _max_value > 0 THEN
         PERFORM setval('lsaudittrailconfiguration_seq', _max_value);
         RAISE NOTICE '24';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsaudittrailconfiguration_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsaudittrailconfiguration_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsbatchdetails_seq';

   SELECT COALESCE(MAX(batchdetailcode), 0) INTO _max_value
   FROM lsbatchdetails;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsbatchdetails_seq;
      IF _max_value > 0 THEN
         PERFORM setval('lsbatchdetails_seq', _max_value);
         RAISE NOTICE '25';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsbatchdetails_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsbatchdetails_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lscentralisedusers_seq';

   SELECT COALESCE(MAX(centralisedusercode), 0) INTO _max_value
   FROM lscentralisedusers;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lscentralisedusers_seq;
      IF _max_value > 0 THEN
         PERFORM setval('lscentralisedusers_seq', _max_value);
          RAISE NOTICE '26';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lscentralisedusers_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lscentralisedusers_seq exists but is not a sequence.';
   END IF;
END
$do$;

-- kumu 4 End 

-- kumu 5 start 
--DO
-- $do$
-- DECLARE
--    _kind char;
--    _max_value bigint;
-- BEGIN
--    SELECT relkind INTO _kind
--    FROM pg_class
--    WHERE relname = 'lscfrarchivehistory_seq';

--    SELECT COALESCE(MAX(archivecode), 0) INTO _max_value
--    FROM lscfrarchivehistory;

--    IF _kind IS NULL THEN
--       CREATE SEQUENCE lscfrarchivehistory_seq;
--       IF _max_value > 0 THEN
--          PERFORM setval('lscfrarchivehistory_seq', _max_value);
--           RAISE NOTICE '27';
--       END IF;
--    ELSIF _kind = 'S' THEN
--       IF _max_value > 0 THEN
--          PERFORM setval('lscfrarchivehistory_seq', _max_value);
--       END IF;
--    ELSE
--       RAISE NOTICE 'Object named lscfrarchivehistory_seq exists but is not a sequence.';
--    END IF;
-- END
-- $do$;

DO
$do$
DECLARE
   _kind char;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lscfrtransactiononorder_seq';

   IF _kind IS NULL THEN
      CREATE SEQUENCE lscfrtransactiononorder_seq;
       RAISE NOTICE '28';
   ELSIF _kind = 'S' THEN
      -- Do nothing (sequence already exists)
   ELSE
      RAISE NOTICE 'Object named lscfrtransactiononorder_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lscfrtransactiononorder_seq';

   SELECT COALESCE(MAX(serialno), 0) INTO _max_value
   FROM lscfrtransactiononorder;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lscfrtransactiononorder_seq;
      IF _max_value > 0 THEN
         PERFORM setval('lscfrtransactiononorder_seq', _max_value);
          RAISE NOTICE '29';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lscfrtransactiononorder_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lscfrtransactiononorder_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsconfiguration_seq';

   SELECT COALESCE(MAX(serialno), 0) INTO _max_value
   FROM lsconfiguration;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsconfiguration_seq;
      IF _max_value > 0 THEN
         PERFORM setval('lsconfiguration_seq', _max_value);
          RAISE NOTICE '30';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsconfiguration_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsconfiguration_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsdocdirectory_seq';

   SELECT COALESCE(MAX(docdirectorycode), 0) INTO _max_value
   FROM lsdocdirectory;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsdocdirectory_seq;
      IF _max_value > 0 THEN
         PERFORM setval('lsdocdirectory_seq', _max_value);
           RAISE NOTICE '31';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsdocdirectory_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsdocdirectory_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsdocreports_seq';

   SELECT COALESCE(MAX(docReportsCode), 0) INTO _max_value
   FROM lsdocreports;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsdocreports_seq;
      IF _max_value > 0 THEN
         PERFORM setval('lsdocreports_seq', _max_value);
           RAISE NOTICE '32';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsdocreports_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsdocreports_seq exists but is not a sequence.';
   END IF;
END
$do$;

--kumu 5 End

--kumu 6 Start
DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsdocreportsversionhistory_seq';

   SELECT COALESCE(MAX(docReportsversionhistoryCode), 0) INTO _max_value
   FROM lsdocreportsversionhistory;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsdocreportsversionhistory_seq;
      IF _max_value > 0 THEN
         PERFORM setval('lsdocreportsversionhistory_seq', _max_value);
           RAISE NOTICE '33';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsdocreportsversionhistory_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsdocreportsversionhistory_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lselninstfieldmapping_seq';

   SELECT COALESCE(MAX(instfieldmapcode), 0) INTO _max_value
   FROM lselninstfieldmapping;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lselninstfieldmapping_seq;
      IF _max_value > 0 THEN
         PERFORM setval('lselninstfieldmapping_seq', _max_value);
           RAISE NOTICE '34';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lselninstfieldmapping_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lselninstfieldmapping_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lselninstrumentmapping_seq';

   SELECT COALESCE(MAX(instrumentmapcode), 0) INTO _max_value
   FROM lselninstrumentmapping;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lselninstrumentmapping_seq;
      IF _max_value > 0 THEN
         PERFORM setval('lselninstrumentmapping_seq', _max_value);
           RAISE NOTICE '35';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lselninstrumentmapping_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lselninstrumentmapping_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsequipmentmap_seq';

   SELECT COALESCE(MAX(equipmentcode), 0) INTO _max_value
   FROM lsequipmentmap;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsequipmentmap_seq;
      IF _max_value > 0 THEN
         PERFORM setval('lsequipmentmap_seq', _max_value);
           RAISE NOTICE '36';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsequipmentmap_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsequipmentmap_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsfileimages_seq';

   SELECT COALESCE(MAX(fileimagecode), 0) INTO _max_value
   FROM lsfileimages;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsfileimages_seq;
      IF _max_value > 0 THEN
         PERFORM setval('lsfileimages_seq', _max_value);
           RAISE NOTICE '37';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsfileimages_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsfileimages_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsfilemapbarcode_seq';

   SELECT COALESCE(MAX(filebarcode), 0) INTO _max_value
   FROM lsfilemapbarcode;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsfilemapbarcode_seq;
      IF _max_value > 0 THEN
         PERFORM setval('lsfilemapbarcode_seq', _max_value);
           RAISE NOTICE '38';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsfilemapbarcode_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsfilemapbarcode_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsfilemethod_seq';

   SELECT COALESCE(MAX(filemethodcode), 0) INTO _max_value
   FROM lsfilemethod;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsfilemethod_seq;
      IF _max_value > 0 THEN
         PERFORM setval('lsfilemethod_seq', _max_value);
           RAISE NOTICE '39';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsfilemethod_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsfilemethod_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsfileparameter_seq';

   SELECT COALESCE(MAX(fileparametercode), 0) INTO _max_value
   FROM lsfileparameter;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsfileparameter_seq;
      IF _max_value > 0 THEN
         PERFORM setval('lsfileparameter_seq', _max_value);
           RAISE NOTICE '40';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsfileparameter_seq', _max_value);
           RAISE NOTICE '40';
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsfileparameter_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsfilesharedby_seq';

   SELECT COALESCE(MAX(sharedbytofilecode), 0) INTO _max_value
   FROM lsfilesharedby;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsfilesharedby_seq;
      IF _max_value > 0 THEN
         PERFORM setval('lsfilesharedby_seq', _max_value);
           RAISE NOTICE '41';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsfilesharedby_seq', _max_value);
           RAISE NOTICE '41';
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsfilesharedby_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsfileshareto_seq';

   SELECT COALESCE(MAX(sharetofilecode), 0) INTO _max_value
   FROM lsfileshareto;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsfileshareto_seq;
        RAISE NOTICE '42';
      IF _max_value > 0 THEN
         PERFORM setval('lsfileshareto_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsfileshareto_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsfileshareto_seq exists but is not a sequence.';
   END IF;
END
$do$;

--kumu 6 End

--kumu 7 Start
DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsfiletest_seq';

   SELECT COALESCE(MAX(filetestcode), 0) INTO _max_value
   FROM lsfiletest;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsfiletest_seq;
        RAISE NOTICE '43';
      IF _max_value > 0 THEN
         PERFORM setval('lsfiletest_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsfiletest_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsfiletest_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsinstrumentcategory_seq';

   SELECT COALESCE(MAX(instrumentcatcode), 0) INTO _max_value
   FROM lsinstrumentcategory;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsinstrumentcategory_seq;
        RAISE NOTICE '44';
      IF _max_value > 0 THEN
         PERFORM setval('lsinstrumentcategory_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsinstrumentcategory_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsinstrumentcategory_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lslogbooksampleupdates_seq';

   SELECT COALESCE(MAX(logbooksamplecode), 0) INTO _max_value
   FROM lslogbooksampleupdates;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lslogbooksampleupdates_seq;
        RAISE NOTICE '45';
      IF _max_value > 0 THEN
         PERFORM setval('lslogbooksampleupdates_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lslogbooksampleupdates_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lslogbooksampleupdates_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lslogilabprotocolsteps_seq';

   SELECT COALESCE(MAX(protocolorderstepcode), 0) INTO _max_value
   FROM lslogilabprotocolsteps;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lslogilabprotocolsteps_seq;
        RAISE NOTICE '46';
      IF _max_value > 0 THEN
         PERFORM setval('lslogilabprotocolsteps_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lslogilabprotocolsteps_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lslogilabprotocolsteps_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsmappedtags_seq';

   SELECT COALESCE(MAX(tagcode), 0) INTO _max_value
   FROM lsmappedtags;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsmappedtags_seq;
        RAISE NOTICE '47';
      IF _max_value > 0 THEN
         PERFORM setval('lsmappedtags_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsmappedtags_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsmappedtags_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsmappedtemplate_seq';

   SELECT COALESCE(MAX(templatecode), 0) INTO _max_value
   FROM lsmappedtemplate;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsmappedtemplate_seq;
        RAISE NOTICE '48';
      IF _max_value > 0 THEN
         PERFORM setval('lsmappedtemplate_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsmappedtemplate_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsmappedtemplate_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsmaterialmap_seq';

   SELECT COALESCE(MAX(lsmaterialcode), 0) INTO _max_value
   FROM lsmaterialmap;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsmaterialmap_seq;
        RAISE NOTICE '49';
      IF _max_value > 0 THEN
         PERFORM setval('lsmaterialmap_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsmaterialmap_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsmaterialmap_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsorderattachments_seq';

   SELECT COALESCE(MAX(attachmentcode), 0) INTO _max_value
   FROM lsorderattachments;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsorderattachments_seq;
        RAISE NOTICE '50';
      IF _max_value > 0 THEN
         PERFORM setval('lsorderattachments_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsorderattachments_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsorderattachments_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsorderlinks_seq';

   SELECT COALESCE(MAX(norderlinkcode), 0) INTO _max_value
   FROM lsorderlinks;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsorderlinks_seq;
      RAISE NOTICE '51';
      IF _max_value > 0 THEN
         PERFORM setval('lsorderlinks_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsorderlinks_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsorderlinks_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsordersampleupdate_seq';

   SELECT COALESCE(MAX(ordersamplecode), 0) INTO _max_value
   FROM lsordersampleupdate;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsordersampleupdate_seq;
      RAISE NOTICE '52';
      IF _max_value > 0 THEN
         PERFORM setval('lsordersampleupdate_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsordersampleupdate_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsordersampleupdate_seq exists but is not a sequence.';
   END IF;
END
$do$;

--kumu 7 End
--kumu 8 Start
DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsordersharedby_seq';

   SELECT COALESCE(MAX(sharedbycode), 0) INTO _max_value
   FROM lsordersharedby;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsordersharedby_seq;
      RAISE NOTICE '53';
      IF _max_value > 0 THEN
         PERFORM setval('lsordersharedby_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsordersharedby_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsordersharedby_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsordershareto_seq';

   SELECT COALESCE(MAX(sharetocode), 0) INTO _max_value
   FROM lsordershareto;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsordershareto_seq;
      RAISE NOTICE '54';
      IF _max_value > 0 THEN
         PERFORM setval('lsordershareto_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsordershareto_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsordershareto_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsorderworkflowhistory_seq';

   SELECT COALESCE(MAX(historycode), 0) INTO _max_value
   FROM lsorderworkflowhistory;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsorderworkflowhistory_seq;
      RAISE NOTICE '55';
      IF _max_value > 0 THEN
         PERFORM setval('lsorderworkflowhistory_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsorderworkflowhistory_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsorderworkflowhistory_seq exists but is not a sequence.';
   END IF;
END
$do$;
--------------------------------------------------------- santhosh-----------------------------------------------------------------------------------------
DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsparsedparameters_seq';

   SELECT COALESCE(MAX(parsedparametercode), 0) INTO _max_value
   FROM lsparsedparameters;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsparsedparameters_seq;
      RAISE NOTICE '56';
      IF _max_value > 0 THEN
         PERFORM setval('lsparsedparameters_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsparsedparameters_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsparsedparameters_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lspasswordhistorydetails_seq';

   SELECT COALESCE(MAX(passwordcode), 0) INTO _max_value
   FROM lspasswordhistorydetails;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lspasswordhistorydetails_seq;
      RAISE NOTICE '57';
      IF _max_value > 0 THEN
         PERFORM setval('lspasswordhistorydetails_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lspasswordhistorydetails_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lspasswordhistorydetails_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lspasswordpolicy_seq';

   SELECT COALESCE(MAX(policycode), 0) INTO _max_value
   FROM lspasswordpolicy;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lspasswordpolicy_seq;
      RAISE NOTICE '58';
      IF _max_value > 0 THEN
         PERFORM setval('lspasswordpolicy_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lspasswordpolicy_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lspasswordpolicy_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lspreferences_seq';

   SELECT COALESCE(MAX(serialno), 0) INTO _max_value
   FROM lspreferences;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lspreferences_seq;
      RAISE NOTICE '59';
      IF _max_value > 0 THEN
         PERFORM setval('lspreferences_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lspreferences_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lspreferences_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsprojectmaster_seq';

   SELECT COALESCE(MAX(projectcode), 0) INTO _max_value
   FROM lsprojectmaster;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsprojectmaster_seq;
      RAISE NOTICE '60';
      IF _max_value > 0 THEN
         PERFORM setval('lsprojectmaster_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsprojectmaster_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsprojectmaster_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsprotocolfiles_seq';

   SELECT COALESCE(MAX(protocolstepfilecode), 0) INTO _max_value
   FROM lsprotocolfiles;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsprotocolfiles_seq;
      RAISE NOTICE '61';
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolfiles_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolfiles_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsprotocolfiles_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsprotocolimages_seq';

   SELECT COALESCE(MAX(protocolstepimagecode), 0) INTO _max_value
   FROM lsprotocolimages;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsprotocolimages_seq;
      RAISE NOTICE '62';
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolimages_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolimages_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsprotocolimages_seq exists but is not a sequence.';
   END IF;
END
$do$;

--kumu 8 End 

--kumu 9 Start
DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsprotocolmaster_seq';

   SELECT COALESCE(MAX(protocolmastercode), 0) INTO _max_value
   FROM lsprotocolmaster;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsprotocolmaster_seq;
      RAISE NOTICE '63';
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolmaster_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolmaster_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsprotocolmaster_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsprotocolmastertest_seq';

   SELECT COALESCE(MAX(protocoltestcode), 0) INTO _max_value
   FROM lsprotocolmastertest;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsprotocolmastertest_seq;
      RAISE NOTICE '64';
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolmastertest_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolmastertest_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsprotocolmastertest_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsprotocolorderfiles_seq';

   SELECT COALESCE(MAX(protocolorderstepfilecode), 0) INTO _max_value
   FROM lsprotocolorderfiles;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsprotocolorderfiles_seq;
      RAISE NOTICE '65';
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolorderfiles_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolorderfiles_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsprotocolorderfiles_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsprotocolorderimages_seq';

   SELECT COALESCE(MAX(protocolorderstepimagecode), 0) INTO _max_value
   FROM lsprotocolorderimages;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsprotocolorderimages_seq;
      RAISE NOTICE '66';
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolorderimages_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolorderimages_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsprotocolorderimages_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsprotocolordersampleupdates_seq';

   SELECT COALESCE(MAX(protocolsamplecode), 0) INTO _max_value
   FROM lsprotocolordersampleupdates;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsprotocolordersampleupdates_seq;
      RAISE NOTICE '67';
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolordersampleupdates_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolordersampleupdates_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsprotocolordersampleupdates_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsprotocolordersharedby_seq';

   SELECT COALESCE(MAX(sharedbytoprotocolordercode), 0) INTO _max_value
   FROM lsprotocolordersharedby;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsprotocolordersharedby_seq;
      RAISE NOTICE '68';
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolordersharedby_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolordersharedby_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsprotocolordersharedby_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsprotocolordershareto_seq';

   SELECT COALESCE(MAX(sharetoprotocolordercode), 0) INTO _max_value
   FROM lsprotocolordershareto;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsprotocolordershareto_seq;
      RAISE NOTICE '69';
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolordershareto_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolordershareto_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsprotocolordershareto_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsprotocolorderworkflowhistory_seq';

   SELECT COALESCE(MAX(historycode), 0) INTO _max_value
   FROM lsprotocolorderworkflowhistory;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsprotocolorderworkflowhistory_seq;
      RAISE NOTICE '70';
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolorderworkflowhistory_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolorderworkflowhistory_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsprotocolorderworkflowhistory_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsprotocolorderversion_seq';

   SELECT COALESCE(MAX(protocolorderversioncode), 0) INTO _max_value
   FROM lsprotocolorderversion;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsprotocolorderversion_seq;
      RAISE NOTICE '71';
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolorderversion_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolorderversion_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsprotocolorderversion_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsprotocolordervideos_seq';

   SELECT COALESCE(MAX(protocolorderstepvideoscode), 0) INTO _max_value
   FROM lsprotocolordervideos;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsprotocolordervideos_seq;
       RAISE NOTICE '72';
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolordervideos_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolordervideos_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsprotocolordervideos_seq exists but is not a sequence.';
   END IF;
END
$do$;


--kumu 9 End

--kumu 10 start

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsprotocolsampleupdates_seq';

   SELECT COALESCE(MAX(protocolsamplecode), 0) INTO _max_value
   FROM lsprotocolsampleupdates;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsprotocolsampleupdates_seq;
       RAISE NOTICE '72';
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolsampleupdates_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolsampleupdates_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsprotocolsampleupdates_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsprotocolsharedby_seq';

   SELECT COALESCE(MAX(sharedbytoprotocolcode), 0) INTO _max_value
   FROM lsprotocolsharedby;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsprotocolsharedby_seq;
       RAISE NOTICE '73';
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolsharedby_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolsharedby_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsprotocolsharedby_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsprotocolstep_seq';

   SELECT COALESCE(MAX(protocolstepcode), 0) INTO _max_value
   FROM lsprotocolstep;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsprotocolstep_seq;
       RAISE NOTICE '74';
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolstep_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolstep_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsprotocolstep_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsprotocolstepversion_seq';

   SELECT COALESCE(MAX(protocolstepversioncode), 0) INTO _max_value
   FROM lsprotocolstepversion;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsprotocolstepversion_seq;
       RAISE NOTICE '75';
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolstepversion_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolstepversion_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsprotocolstepversion_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsprotocolupdates_seq';

   SELECT COALESCE(MAX(protocolcode), 0) INTO _max_value
   FROM lsprotocolupdates;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsprotocolupdates_seq;
       RAISE NOTICE '76';
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolupdates_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolupdates_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsprotocolupdates_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsprotocolversion_seq';

   SELECT COALESCE(MAX(protocolversioncode), 0) INTO _max_value
   FROM lsprotocolversion;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsprotocolversion_seq;
       RAISE NOTICE '77';
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolversion_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolversion_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsprotocolversion_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsprotocolvideos_seq';

   SELECT COALESCE(MAX(protocolstepvideoscode), 0) INTO _max_value
   FROM lsprotocolvideos;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsprotocolvideos_seq;
       RAISE NOTICE '80';
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolvideos_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolvideos_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsprotocolvideos_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsprotocolworkflowgroupmap_seq';

   SELECT COALESCE(MAX(workflowmapid), 0) INTO _max_value
   FROM lsprotocolworkflowgroupmap;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsprotocolworkflowgroupmap_seq;
      RAISE NOTICE '81';
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolworkflowgroupmap_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolworkflowgroupmap_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsprotocolworkflowgroupmap_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsprotocolworkflowhistory_seq';

   SELECT COALESCE(MAX(historycode), 0) INTO _max_value
   FROM lsprotocolworkflowhistory;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsprotocolworkflowhistory_seq;
      RAISE NOTICE '82';
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolworkflowhistory_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsprotocolworkflowhistory_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsprotocolworkflowhistory_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsresultlimsorderrefrence_seq';

   SELECT COALESCE(MAX(refrencecode), 0) INTO _max_value
   FROM LsResultlimsOrderrefrence;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsresultlimsorderrefrence_seq;
      RAISE NOTICE '83';
      IF _max_value > 0 THEN
         PERFORM setval('lsresultlimsorderrefrence_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsresultlimsorderrefrence_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsresultlimsorderrefrence_seq exists but is not a sequence.';
   END IF;
END
$do$;

--kumu 10 End

--kumu 11 start 

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lssampleresult_seq';

   SELECT COALESCE(MAX(sampleresultcode), 0) INTO _max_value
   FROM lssampleresult;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lssampleresult_seq;
      RAISE NOTICE '84';
      IF _max_value > 0 THEN
         PERFORM setval('lssampleresult_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lssampleresult_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lssampleresult_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lssheetupdates_seq';

   SELECT COALESCE(MAX(sheetcode), 0) INTO _max_value
   FROM lssheetupdates;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lssheetupdates_seq;
      RAISE NOTICE '85';
      IF _max_value > 0 THEN
         PERFORM setval('lssheetupdates_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lssheetupdates_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lssheetupdates_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lssheetworkflowgroupmap_seq';

   SELECT COALESCE(MAX(workflowmapid), 0) INTO _max_value
   FROM lssheetworkflowgroupmap;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lssheetworkflowgroupmap_seq;
      RAISE NOTICE '86';
      IF _max_value > 0 THEN
         PERFORM setval('lssheetworkflowgroupmap_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lssheetworkflowgroupmap_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lssheetworkflowgroupmap_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lssheetworkflowhistory_seq';

   SELECT COALESCE(MAX(historycode), 0) INTO _max_value
   FROM lssheetworkflowhistory;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lssheetworkflowhistory_seq;
      RAISE NOTICE '87';
      IF _max_value > 0 THEN
         PERFORM setval('lssheetworkflowhistory_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lssheetworkflowhistory_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lssheetworkflowhistory_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lstestmasterlocal_seq';

   SELECT COALESCE(MAX(testcode), 0) INTO _max_value
   FROM lstestmasterlocal;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lstestmasterlocal_seq;
      RAISE NOTICE '88';
      IF _max_value > 0 THEN
         PERFORM setval('lstestmasterlocal_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lstestmasterlocal_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lstestmasterlocal_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsunmappedtags_seq';

   SELECT COALESCE(MAX(tagcode), 0) INTO _max_value
   FROM lsunmappedtags;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsunmappedtags_seq;
      RAISE NOTICE '89';
      IF _max_value > 0 THEN
         PERFORM setval('lsunmappedtags_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsunmappedtags_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsunmappedtags_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsusersteam_seq';

   SELECT COALESCE(MAX(teamcode), 0) INTO _max_value
   FROM lsusersteam;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsusersteam_seq;
      RAISE NOTICE '90';
      IF _max_value > 0 THEN
         PERFORM setval('lsusersteam_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsusersteam_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsusersteam_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsuserteammapping_seq';

   SELECT COALESCE(MAX(userteammapcode), 0) INTO _max_value
   FROM lsuserteammapping;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsuserteammapping_seq;
       RAISE NOTICE '91';
      IF _max_value > 0 THEN
         PERFORM setval('lsuserteammapping_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsuserteammapping_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsuserteammapping_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'manufacturer_nmanufcode_seq';

   SELECT COALESCE(MAX(nmanufcode), 0) INTO _max_value
   FROM manufacturer;

   IF _kind IS NULL THEN
      CREATE SEQUENCE manufacturer_nmanufcode_seq;
       RAISE NOTICE '92';
      IF _max_value > 0 THEN
         PERFORM setval('manufacturer_nmanufcode_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('manufacturer_nmanufcode_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named manufacturer_nmanufcode_seq exists but is not a sequence.';
   END IF;
END
$do$;
--kumu 11 End

--kumu 12 Start 

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'materialattachments_seq';

   SELECT COALESCE(MAX(nmaterialattachcode), 0) INTO _max_value
   FROM materialattachments;

   IF _kind IS NULL THEN
      CREATE SEQUENCE materialattachments_seq;
       RAISE NOTICE '93';
      IF _max_value > 0 THEN
         PERFORM setval('materialattachments_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('materialattachments_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named materialattachments_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'materialinventory_nmaterialinventorycode_seq';

   SELECT COALESCE(MAX(nmaterialinventorycode), 0) INTO _max_value
   FROM materialinventory;

   IF _kind IS NULL THEN
      CREATE SEQUENCE materialinventory_nmaterialinventorycode_seq;
       RAISE NOTICE '94';
      IF _max_value > 0 THEN
         PERFORM setval('materialinventory_nmaterialinventorycode_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('materialinventory_nmaterialinventorycode_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named materialinventory_nmaterialinventorycode_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'materialinventorylinks_seq';

   SELECT COALESCE(MAX(nmaterialinventorylinkcode), 0) INTO _max_value
   FROM materialinventorylinks;

   IF _kind IS NULL THEN
      CREATE SEQUENCE materialinventorylinks_seq;
       RAISE NOTICE '95';
      IF _max_value > 0 THEN
         PERFORM setval('materialinventorylinks_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('materialinventorylinks_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named materialinventorylinks_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'materialinventorytransaction_nmaterialinventtranscode_seq';

   SELECT COALESCE(MAX(nmaterialinventtranscode), 0) INTO _max_value
   FROM materialinventorytransaction;

   IF _kind IS NULL THEN
      CREATE SEQUENCE materialinventorytransaction_nmaterialinventtranscode_seq;
       RAISE NOTICE '96';
      IF _max_value > 0 THEN
         PERFORM setval('materialinventorytransaction_nmaterialinventtranscode_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('materialinventorytransaction_nmaterialinventtranscode_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named materialinventorytransaction_nmaterialinventtranscode_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'materiallinks_seq';

   SELECT COALESCE(MAX(materiallinkcode), 0) INTO _max_value
   FROM materiallinks;

   IF _kind IS NULL THEN
      CREATE SEQUENCE materiallinks_seq;
       RAISE NOTICE '97';
      IF _max_value > 0 THEN
         PERFORM setval('materiallinks_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('materiallinks_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named materiallinks_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'materialprojecthistory_seq';

   SELECT COALESCE(MAX(materialprojectcode), 0) INTO _max_value
   FROM materialprojecthistory;

   IF _kind IS NULL THEN
      CREATE SEQUENCE materialprojecthistory_seq;
       RAISE NOTICE '98';
      IF _max_value > 0 THEN
         PERFORM setval('materialprojecthistory_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('materialprojecthistory_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named materialprojecthistory_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'methodversion_mvno_seq';

   SELECT COALESCE(MAX(mvno), 0) INTO _max_value
   FROM methodversion;

   IF _kind IS NULL THEN
      CREATE SEQUENCE methodversion_mvno_seq;
       RAISE NOTICE '99';
      IF _max_value > 0 THEN
         PERFORM setval('methodversion_mvno_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('methodversion_mvno_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named methodversion_mvno_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'notification_seq';

   SELECT COALESCE(MAX(notificationid), 0) INTO _max_value
   FROM notification;

   IF _kind IS NULL THEN
      CREATE SEQUENCE notification_seq;
       RAISE NOTICE '100';
      IF _max_value > 0 THEN
         PERFORM setval('notification_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('notification_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named notification_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'parity_paritykey_seq';

   SELECT COALESCE(MAX(paritykey), 0) INTO _max_value
   FROM parity;

   IF _kind IS NULL THEN
      CREATE SEQUENCE parity_paritykey_seq;
       RAISE NOTICE '101';
      IF _max_value > 0 THEN
         PERFORM setval('parity_paritykey_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('parity_paritykey_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named parity_paritykey_seq exists but is not a sequence.';
   END IF;
END
$do$;

-- DO
-- $do$
-- DECLARE
--    _kind char;
-- BEGIN
--    SELECT relkind INTO _kind
--    FROM pg_class
--    WHERE relname = 'rctcpfiledetails_seq';

--    IF _kind IS NULL THEN
--       CREATE SEQUENCE rctcpfiledetails_seq;
--        RAISE NOTICE '102';
--    ELSIF _kind = 'S' THEN
--       -- Sequence already exists, do nothing
--    ELSE
--       RAISE NOTICE 'Object named rctcpfiledetails_seq exists but is not a sequence.';
--    END IF;
-- END
-- $do$;


--kumu 12 end 

--kumu 13 start

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'reportsversion_seq';

SELECT COALESCE(MAX(reportversioncode), 0) INTO _max_value
   FROM ReportsVersion;

   IF _kind IS NULL THEN
      CREATE SEQUENCE reportsversion_seq;
       RAISE NOTICE '106';
      IF _max_value > 0 THEN
         PERFORM setval('reportsversion_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('reportsversion_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named reportsversion_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'reporttemplateversion_seq';

   SELECT COALESCE(MAX(templateversioncode), 0) INTO _max_value
   FROM reportTemplateVersion;

   IF _kind IS NULL THEN
      CREATE SEQUENCE reporttemplateversion_seq;
       RAISE NOTICE '106';
      IF _max_value > 0 THEN
         PERFORM setval('reporttemplateversion_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('reporttemplateversion_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named reporttemplateversion_seq exists but is not a sequence.';
   END IF;
END
$do$;



DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'resultusedmaterial_nresultusedmaterialcode_seq';

   SELECT COALESCE(MAX(nresultusedmaterialcode), 0) INTO _max_value
   FROM resultusedmaterial;

   IF _kind IS NULL THEN
      CREATE SEQUENCE resultusedmaterial_nresultusedmaterialcode_seq;
       RAISE NOTICE '109';
      IF _max_value > 0 THEN
         PERFORM setval('resultusedmaterial_nresultusedmaterialcode_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('resultusedmaterial_nresultusedmaterialcode_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named resultusedmaterial_nresultusedmaterialcode_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'sampleattachments_seq';

   SELECT COALESCE(MAX(nsampleattachcode), 0) INTO _max_value
   FROM sampleattachments;

   IF _kind IS NULL THEN
      CREATE SEQUENCE sampleattachments_seq;
       RAISE NOTICE '110';
      IF _max_value > 0 THEN
         PERFORM setval('sampleattachments_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('sampleattachments_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named sampleattachments_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'samplebarcodemap_seq';

   SELECT COALESCE(MAX(barcodemapid), 0) INTO _max_value
   FROM samplebarcodemap;

   IF _kind IS NULL THEN
      CREATE SEQUENCE samplebarcodemap_seq;
       RAISE NOTICE '111';
      IF _max_value > 0 THEN
         PERFORM setval('samplebarcodemap_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('samplebarcodemap_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named samplebarcodemap_seq exists but is not a sequence.';
   END IF;
END
$do$;
DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'samplelinesplit_samplelinesplitkey_seq';

   SELECT COALESCE(MAX(samplelinesplitkey), 0) INTO _max_value
   FROM samplelinesplit;

   IF _kind IS NULL THEN
      CREATE SEQUENCE samplelinesplit_samplelinesplitkey_seq;
       RAISE NOTICE '112';
      IF _max_value > 0 THEN
         PERFORM setval('samplelinesplit_samplelinesplitkey_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('samplelinesplit_samplelinesplitkey_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named samplelinesplit_samplelinesplitkey_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'samplelinks_seq';

   SELECT COALESCE(MAX(nsamplelinkcode), 0) INTO _max_value
   FROM samplelinks;

   IF _kind IS NULL THEN
      CREATE SEQUENCE samplelinks_seq;
       RAISE NOTICE '112';
      IF _max_value > 0 THEN
         PERFORM setval('samplelinks_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('samplelinks_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named samplelinks_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'sampleprojecthistory_seq';

   SELECT COALESCE(MAX(sampleprojectcode), 0) INTO _max_value
   FROM sampleprojecthistory;

   IF _kind IS NULL THEN
      CREATE SEQUENCE sampleprojecthistory_seq;
       RAISE NOTICE '113';
      IF _max_value > 0 THEN
         PERFORM setval('sampleprojecthistory_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('sampleprojecthistory_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named sampleprojecthistory_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'samplestoragemapping_seq';

   SELECT COALESCE(MAX(mappedid), 0) INTO _max_value
   FROM samplestoragemapping;

   IF _kind IS NULL THEN
      CREATE SEQUENCE samplestoragemapping_seq;
       RAISE NOTICE '114';
      IF _max_value > 0 THEN
         PERFORM setval('samplestoragemapping_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('samplestoragemapping_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named samplestoragemapping_seq exists but is not a sequence.';
   END IF;
END
$do$;


-- kumu - without sequence change in pojo


DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'screenmaster_seq';

   SELECT COALESCE(MAX(screencode), 0) INTO _max_value
   FROM ScreenMaster;

   IF _kind IS NULL THEN
      CREATE SEQUENCE screenmaster_seq;
       RAISE NOTICE '116';
      IF _max_value > 0 THEN
         PERFORM setval('screenmaster_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('screenmaster_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named screenmaster_seq exists but is not a sequence.';
   END IF;
END
$do$;


DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'selectedinventorymapped_seq';

   SELECT COALESCE(MAX(mappedid), 0) INTO _max_value
   FROM selectedinventorymapped;

   IF _kind IS NULL THEN
      CREATE SEQUENCE selectedinventorymapped_seq;
       RAISE NOTICE '117';
      IF _max_value > 0 THEN
         PERFORM setval('selectedinventorymapped_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('selectedinventorymapped_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named selectedinventorymapped_seq exists but is not a sequence.';
   END IF;
END
$do$;




DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsfileversion_seq';

   SELECT COALESCE(MAX(fileversioncode), 0) INTO _max_value
   FROM LSfileversion;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsfileversion_seq;
        RAISE NOTICE '124';
      IF _max_value > 0 THEN
         PERFORM setval('lsfileversion_seq', _max_value);
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsfileversion_seq', _max_value);
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsfileversion_seq exists but is not a sequence.';
   END IF;
END
$do$;

--kumu 13 End

--kumu 14 Srart 


DO
$do$
DECLARE
   _kind char;
   _max_barcodeno bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsusersettings_seq';

   SELECT COALESCE(MAX(userid), 0) INTO _max_barcodeno
   FROM Lsusersettings;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsusersettings_seq;
      IF _max_barcodeno > 0 THEN
         PERFORM setval('lsusersettings_seq', _max_barcodeno);
           RAISE NOTICE '125';
      ELSE
         RAISE NOTICE 'Sequence created with default start value (no data in table).';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_barcodeno > 0 THEN
         PERFORM setval('lsusersettings_seq', _max_barcodeno);
         RAISE NOTICE 'Sequence updated to %', _max_barcodeno;
      ELSE
         RAISE NOTICE 'Sequence exists, but no data to update.';
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsusersettings_seq exists but is not a sequence.';
   END IF;
END
$do$;


DO
$do$
DECLARE
   _kind char;
   _max_barcodeno bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsorderattachmentfiles_seq';

   SELECT COALESCE(MAX(id), 0) INTO _max_barcodeno
   FROM LSOrderAttachmentfiles;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsorderattachmentfiles_seq;
      IF _max_barcodeno > 0 THEN
         PERFORM setval('lsorderattachmentfiles_seq', _max_barcodeno);
           RAISE NOTICE '126';
      ELSE
         RAISE NOTICE 'Sequence created with default start value (no data in table).';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_barcodeno > 0 THEN
         PERFORM setval('lsorderattachmentfiles_seq', _max_barcodeno);
         RAISE NOTICE 'Sequence updated to %', _max_barcodeno;
      ELSE
         RAISE NOTICE 'Sequence exists, but no data to update.';
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsorderattachmentfiles_seq exists but is not a sequence.';
   END IF;
END
$do$;



DO
$do$
DECLARE
   _kind char;
   _max_barcodeno bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsprotocolmethod_sequence';

   SELECT COALESCE(MAX(protocolmethodcode), 0) INTO _max_barcodeno
   FROM LSprotocolmethod;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsprotocolmethod_sequence;
      IF _max_barcodeno > 0 THEN
         PERFORM setval('lsprotocolmethod_sequence', _max_barcodeno);
           RAISE NOTICE '128';
      ELSE
         RAISE NOTICE 'Sequence created with default start value (no data in table).';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_barcodeno > 0 THEN
         PERFORM setval('lsprotocolmethod_sequence', _max_barcodeno);
         RAISE NOTICE 'Sequence updated to %', _max_barcodeno;
      ELSE
         RAISE NOTICE 'Sequence exists, but no data to update.';
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsprotocolmethod_sequence exists but is not a sequence.';
   END IF;
END
$do$;



DO
$do$
DECLARE
   _kind char;
   _max_barcodeno bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsprotocolorderstepversion_seq';

   SELECT COALESCE(MAX(protocolorderstepversioncode), 0) INTO _max_barcodeno
   FROM LSprotocolorderstepversion;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsprotocolorderstepversion_seq;
      IF _max_barcodeno > 0 THEN
         PERFORM setval('lsprotocolorderstepversion_seq', _max_barcodeno);
           RAISE NOTICE '129';
      ELSE
         RAISE NOTICE 'Sequence created with default start value (no data in table).';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_barcodeno > 0 THEN
         PERFORM setval('lsprotocolorderstepversion_seq', _max_barcodeno);
         RAISE NOTICE 'Sequence updated to %', _max_barcodeno;
      ELSE
         RAISE NOTICE 'Sequence exists, but no data to update.';
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsprotocolorderstepversion_seq exists but is not a sequence.';
   END IF;
END
$do$;


DO
$do$
DECLARE
   _kind char;
   _max_barcodeno bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsprotocolworkflow_seq';

   SELECT COALESCE(MAX(workflowcode), 0) INTO _max_barcodeno
   FROM lsprotocolworkflow;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsprotocolworkflow_seq;
      IF _max_barcodeno > 0 THEN
         PERFORM setval('lsprotocolworkflow_seq', _max_barcodeno);
           RAISE NOTICE '130';
      ELSE
         RAISE NOTICE 'Sequence created with default start value (no data in table).';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_barcodeno > 0 THEN
         PERFORM setval('lsprotocolworkflow_seq', _max_barcodeno);
         RAISE NOTICE 'Sequence updated to %', _max_barcodeno;
      ELSE
         RAISE NOTICE 'Sequence exists, but no data to update.';
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsprotocolworkflow_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_barcodeno bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lssamplemaster_seq';

   SELECT COALESCE(MAX(samplecode), 0) INTO _max_barcodeno
   FROM LSsamplemaster;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lssamplemaster_seq;
      IF _max_barcodeno > 0 THEN
         PERFORM setval('lssamplemaster_seq', _max_barcodeno);
           RAISE NOTICE '131';
      ELSE
         RAISE NOTICE 'Sequence created with default start value (no data in table).';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_barcodeno > 0 THEN
         PERFORM setval('lssamplemaster_seq', _max_barcodeno);
         RAISE NOTICE 'Sequence updated to %', _max_barcodeno;
      ELSE
         RAISE NOTICE 'Sequence exists, but no data to update.';
      END IF;
   ELSE
      RAISE NOTICE 'Object named lssamplemaster_seq exists but is not a sequence.';
   END IF;
END
$do$;



DO
$do$
DECLARE
   _kind char;
   _max_barcodeno bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsworkflowgroupmapping_seq';

   SELECT COALESCE(MAX(workflowmapid), 0) INTO _max_barcodeno
   FROM LSworkflowgroupmapping;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsworkflowgroupmapping_seq;
      IF _max_barcodeno > 0 THEN
         PERFORM setval('lsworkflowgroupmapping_seq', _max_barcodeno);
           RAISE NOTICE '132';
      ELSE
         RAISE NOTICE 'Sequence created with default start value (no data in table).';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_barcodeno > 0 THEN
         PERFORM setval('lsworkflowgroupmapping_seq', _max_barcodeno);
         RAISE NOTICE 'Sequence updated to %', _max_barcodeno;
      ELSE
         RAISE NOTICE 'Sequence exists, but no data to update.';
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsworkflowgroupmapping_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_barcodeno bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsactiveuser_seq';

   SELECT COALESCE(MAX(activeusercode), 0) INTO _max_barcodeno
   FROM LSactiveuser;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsactiveuser_seq;
      IF _max_barcodeno > 0 THEN
         PERFORM setval('lsactiveuser_seq', _max_barcodeno);
           RAISE NOTICE '133';
      ELSE
         RAISE NOTICE 'Sequence created with default start value (no data in table).';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_barcodeno > 0 THEN
         PERFORM setval('lsactiveuser_seq', _max_barcodeno);
         RAISE NOTICE 'Sequence updated to %', _max_barcodeno;
      ELSE
         RAISE NOTICE 'Sequence exists, but no data to update.';
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsactiveuser_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_barcodeno bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsnotification_seq';

   SELECT COALESCE(MAX(notificationcode), 0) INTO _max_barcodeno
   FROM LSnotification;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsnotification_seq;
      IF _max_barcodeno > 0 THEN
         PERFORM setval('lsnotification_seq', _max_barcodeno);
           RAISE NOTICE '134';
      ELSE
         RAISE NOTICE 'Sequence created with default start value (no data in table).';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_barcodeno > 0 THEN
         PERFORM setval('lsnotification_seq', _max_barcodeno);
         RAISE NOTICE 'Sequence updated to %', _max_barcodeno;
      ELSE
         RAISE NOTICE 'Sequence exists, but no data to update.';
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsnotification_seq exists but is not a sequence.';
   END IF;
END
$do$;


DO
$do$
DECLARE
   _kind char;
   _max_barcodeno bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lscfttransaction_sequence';

   SELECT COALESCE(MAX(serialno), 0) INTO _max_barcodeno
   FROM LScfttransaction;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lscfttransaction_sequence;
      IF _max_barcodeno > 0 THEN
         PERFORM setval('lscfttransaction_sequence', _max_barcodeno);
           RAISE NOTICE '137';
      ELSE
         RAISE NOTICE 'Sequence created with default start value (no data in table).';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_barcodeno > 0 THEN
         PERFORM setval('lscfttransaction_sequence', _max_barcodeno);
         RAISE NOTICE 'Sequence updated to %', _max_barcodeno;
      ELSE
         RAISE NOTICE 'Sequence exists, but no data to update.';
      END IF;
   ELSE
      RAISE NOTICE 'Object named lscfttransaction_sequence exists but is not a sequence.';
   END IF;
END
$do$;


DO
$do$
DECLARE
   _kind char;
   _max_barcodeno bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'parserignorechars_parserignorecharskey_seq';

   SELECT COALESCE(MAX(parserignorecharskey), 0) INTO _max_barcodeno
   FROM parserignorechars;

   IF _kind IS NULL THEN
      CREATE SEQUENCE parserignorechars_parserignorecharskey_seq;
      IF _max_barcodeno > 0 THEN
         PERFORM setval('parserignorechars_parserignorecharskey_seq', _max_barcodeno);
           RAISE NOTICE '189';
      ELSE
         RAISE NOTICE 'Sequence created with default start value (no data in table).';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_barcodeno > 0 THEN
         PERFORM setval('parserignorechars_parserignorecharskey_seq', _max_barcodeno);
         RAISE NOTICE 'Sequence updated to %', _max_barcodeno;
      ELSE
         RAISE NOTICE 'Sequence exists, but no data to update.';
      END IF;
   ELSE
      RAISE NOTICE 'Object named parserignorechars_parserignorecharskey_seq exists but is not a sequence.';
   END IF;
END
$do$;




DO
$do$
DECLARE
   _kind char;
   _max_barcodeno bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'reportdesignerstructure_directorycode_seq';

   SELECT COALESCE(MAX(directorycode), 0) INTO _max_barcodeno
   FROM ReportDesignerStructure;

   IF _kind IS NULL THEN
      CREATE SEQUENCE reportdesignerstructure_directorycode_seq;
      IF _max_barcodeno > 0 THEN
         PERFORM setval('reportdesignerstructure_directorycode_seq', _max_barcodeno);
           RAISE NOTICE '198';
      ELSE
         RAISE NOTICE 'Sequence created with default start value (no data in table).';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_barcodeno > 0 THEN
         PERFORM setval('reportdesignerstructure_directorycode_seq', _max_barcodeno);
         RAISE NOTICE 'Sequence updated to %', _max_barcodeno;
      ELSE
         RAISE NOTICE 'Sequence exists, but no data to update.';
      END IF;
   ELSE
      RAISE NOTICE 'Object named reportdesignerstructure_directorycode_seq exists but is not a sequence.';
   END IF;
END
$do$;


DO
$do$
DECLARE
   _kind char;
   _max_barcodeno bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lsprotocolshareto_sharetoprotocolcode_seq';

   SELECT COALESCE(MAX(sharetoprotocolcode), 0) INTO _max_barcodeno
   FROM Lsprotocolshareto;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsprotocolshareto_sharetoprotocolcode_seq;
      IF _max_barcodeno > 0 THEN
         PERFORM setval('lsprotocolshareto_sharetoprotocolcode_seq', _max_barcodeno);
           RAISE NOTICE '202';
      ELSE
         RAISE NOTICE 'Sequence created with default start value (no data in table).';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_barcodeno > 0 THEN
         PERFORM setval('lsprotocolshareto_sharetoprotocolcode_seq', _max_barcodeno);
         RAISE NOTICE 'Sequence updated to %', _max_barcodeno;
      ELSE
         RAISE NOTICE 'Sequence exists, but no data to update.';
      END IF;
   ELSE
      RAISE NOTICE 'Object named lsprotocolshareto_sharetoprotocolcode_seq exists but is not a sequence.';
   END IF;
END
$do$;


DO
$do$
DECLARE
   _kind char;
   _max_barcodeno bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lssamplefile_filesamplecode_seq';

   SELECT COALESCE(MAX(filesamplecode), 0) INTO _max_barcodeno
   FROM LSsamplefile;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lssamplefile_filesamplecode_seq;
      IF _max_barcodeno > 0 THEN
         PERFORM setval('lssamplefile_filesamplecode_seq', _max_barcodeno);
           RAISE NOTICE '220';
      ELSE
         RAISE NOTICE 'Sequence created with default start value (no data in table).';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_barcodeno > 0 THEN
         PERFORM setval('lssamplefile_filesamplecode_seq', _max_barcodeno);
         RAISE NOTICE 'Sequence updated to %', _max_barcodeno;
      ELSE
         RAISE NOTICE 'Sequence exists, but no data to update.';
      END IF;
   ELSE
      RAISE NOTICE 'Object named lssamplefile_filesamplecode_seq exists but is not a sequence.';
   END IF;
END
$do$;

DO
$do$
DECLARE
   _kind char;
   _max_barcodeno bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lssamplefileversion_filesamplecodeversion_seq';

   SELECT COALESCE(MAX(filesamplecodeversion), 0) INTO _max_barcodeno
   FROM LSsamplefileversion;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lssamplefileversion_filesamplecodeversion_seq;
      IF _max_barcodeno > 0 THEN
         PERFORM setval('lssamplefileversion_filesamplecodeversion_seq', _max_barcodeno);
           RAISE NOTICE '221';
      ELSE
         RAISE NOTICE 'Sequence created with default start value (no data in table).';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_barcodeno > 0 THEN
         PERFORM setval('lssamplefileversion_filesamplecodeversion_seq', _max_barcodeno);
         RAISE NOTICE 'Sequence updated to %', _max_barcodeno;
      ELSE
         RAISE NOTICE 'Sequence exists, but no data to update.';
      END IF;
   ELSE
      RAISE NOTICE 'Object named lssamplefileversion_filesamplecodeversion_seq exists but is not a sequence.';
   END IF;
END
$do$;


DO
$do$
DECLARE
   _kind char;
   _max_barcodeno bigint;
BEGIN
   SELECT relkind INTO _kind
   FROM pg_class
   WHERE relname = 'lslogbooks_seq';

    SELECT COALESCE(MAX(logbookcode), 0) INTO _max_barcodeno
   FROM Lslogbooks;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lslogbooks_seq;
      IF _max_barcodeno > 0 THEN
         PERFORM setval('lslogbooks_seq', _max_barcodeno);
           RAISE NOTICE '233';
      ELSE
         RAISE NOTICE 'Sequence created with default start value (no data in table).';
      END IF;
   ELSIF _kind = 'S' THEN
      IF _max_barcodeno > 0 THEN
         PERFORM setval('lslogbooks_seq', _max_barcodeno);
         RAISE NOTICE 'Sequence updated to %', _max_barcodeno;
      ELSE
         RAISE NOTICE 'Sequence exists, but no data to update.';
      END IF;
   ELSE
      RAISE NOTICE 'Object named lslogbooks_seq exists but is not a sequence.';
   END IF;
END
$do$;

    
DO
$do$
DECLARE
  _kind char;
  _max_printerid bigint;
BEGIN
  SELECT relkind INTO _kind
  FROM pg_class
  WHERE relname = 'printerdetails_printid_seq';

  SELECT COALESCE(MAX(printerId), 0) INTO _max_printerid
  FROM printerdetails;

  IF _kind IS NULL THEN
     CREATE SEQUENCE printerdetails_printid_seq;
     IF _max_printerid > 0 THEN
        PERFORM setval('printerdetails_printid_seq', _max_printerid);
          RAISE NOTICE '2';
     ELSE
        RAISE NOTICE 'Sequence created with default start value (no data in table).';
     END IF;
  ELSIF _kind = 'S' THEN
     IF _max_printerid > 0 THEN
        PERFORM setval('printerdetails_printid_seq', _max_printerid);
        RAISE NOTICE 'Sequence updated to %', _max_printerid;
     ELSE
        RAISE NOTICE 'Sequence exists, but no data to update.';
     END IF;
   ELSE
     RAISE NOTICE 'Object named printerdetails_printid_seq exists but is not a sequence.';
  END IF;
END
$do$;

DO
$do$
DECLARE
  _kind char;
 _max_printerid bigint;
BEGIN
  SELECT relkind INTO _kind
  FROM pg_class
  WHERE relname = 'printjob_printid_seq';

  SELECT COALESCE(MAX(printId), 0) INTO _max_printerid
  FROM PrintJob;

 IF _kind IS NULL THEN
     CREATE SEQUENCE printjob_printid_seq;
     IF _max_printerid > 0 THEN
        PERFORM setval('printjob_printid_seq', _max_printerid);
          RAISE NOTICE '2';
     ELSE
        RAISE NOTICE 'Sequence created with default start value (no data in table).';
     END IF;
  ELSIF _kind = 'S' THEN
     IF _max_printerid > 0 THEN
        PERFORM setval('printjob_printid_seq', _max_printerid);
        RAISE NOTICE 'Sequence updated to %', _max_printerid;
     ELSE
        RAISE NOTICE 'Sequence exists, but no data to update.';
     END IF;
  ELSE
     RAISE NOTICE 'Object named printjob_printid_seq exists but is not a sequence.';
  END IF;
END
$do$; 

----------- lsfileelnmethod-------------------
DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind
   INTO _kind
   FROM pg_class
   WHERE relname = 'lsfileelnmethod_fileelnmethodcode_seq';

   SELECT COALESCE(MAX(fileelnmethodcode), 0)
   INTO _max_value
   FROM LSfileelnmethod;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsfileelnmethod_fileelnmethodcode_seq;
      IF _max_value > 0 THEN
         PERFORM setval('lsfileelnmethod_fileelnmethodcode_seq', _max_value);
      END IF;

   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsfileelnmethod_fileelnmethodcode_seq', _max_value);
      END IF;

   ELSE
      RAISE NOTICE 'Object named lsfileelnmethod_fileelnmethodcode_seq exists but is not a sequence.';
   END IF;
END
$do$;

-------------------------lsorderelnmethod --------------------------------
DO
$do$
DECLARE
   _kind char;
   _max_value bigint;
BEGIN
   SELECT relkind
   INTO _kind
   FROM pg_class
   WHERE relname = 'lsorderelnmethod_seq';

   SELECT COALESCE(MAX(orderelnmethodcode), 0)
   INTO _max_value
   FROM LSOrderElnMethod;

   IF _kind IS NULL THEN
      CREATE SEQUENCE lsorderelnmethod_seq;
      IF _max_value > 0 THEN
         PERFORM setval('lsorderelnmethod_seq', _max_value);
      END IF;

   ELSIF _kind = 'S' THEN
      IF _max_value > 0 THEN
         PERFORM setval('lsorderelnmethod_seq', _max_value);
      END IF;

   ELSE
      RAISE NOTICE 'Object named lsorderelnmethod_seq exists but is not a sequence.';
   END IF;
END
$do$;


DO $$
DECLARE
    seq_value   BIGINT;
    max_value   BIGINT;
    next_value  BIGINT;
BEGIN
    -------------------------------------------------------------------------
    -- 1. Get current sequence value (if sequence exists)
    -------------------------------------------------------------------------
    IF EXISTS (
        SELECT 1
        FROM pg_class
        WHERE relkind = 'S'
          AND relname = 'orderdetail_seq'
    ) THEN
        SELECT last_value
        INTO seq_value
        FROM orderdetail_seq;
    ELSE
        seq_value := 0;
    END IF;

    -------------------------------------------------------------------------
    -- 2. Get MAX(batchcode) from table
    -------------------------------------------------------------------------
    SELECT COALESCE(MAX(batchcode), 0)
    INTO max_value
    FROM lslogilablimsorderdetail;

    -------------------------------------------------------------------------
    -- 3. Compute next_value (same logic as SQL Server)
    -------------------------------------------------------------------------
    IF max_value = 0 THEN
        -- Case A: table empty → start from 1000000
        next_value := 1000000;
    ELSE
        IF length(max_value::text) >= 7 THEN
            -- Case B: length ≥ 7 → valid → next = max + 1
            next_value := max_value + 1;
        ELSE
            -- Case C: length < 7 → convert to 7-digit series
            next_value := 1000000 + max_value + 1;
        END IF;
    END IF;

    -------------------------------------------------------------------------
    -- 4. Create or restart sequence
    -------------------------------------------------------------------------
    IF EXISTS (
        SELECT 1
        FROM pg_class
        WHERE relkind = 'S'
          AND relname = 'orderdetail_seq'
    ) THEN
        EXECUTE format(
            'ALTER SEQUENCE orderdetail_seq RESTART WITH %s',
            next_value
        );
    ELSE
        EXECUTE format(
            'CREATE SEQUENCE orderdetail_seq START WITH %s INCREMENT BY 1',
            next_value
        );
    END IF;

    RAISE NOTICE 'Sequence updated → New start value = %', next_value;
END $$;

DO $$
DECLARE
    seq_value   BIGINT;
    max_value   BIGINT;
    next_value  BIGINT;
BEGIN
    ----------------------------------------------------------------------------
    -- 1. Get current sequence value (if sequence exists)
    ----------------------------------------------------------------------------
    IF EXISTS (
        SELECT 1
        FROM pg_class
        WHERE relkind = 'S'
          AND relname = 'orderdetailprotocol_seq'
    ) THEN
        SELECT last_value
        INTO seq_value
        FROM orderdetailprotocol_seq;
    ELSE
        seq_value := 0;
    END IF;

    ----------------------------------------------------------------------------
    -- 2. Get MAX(protocolordercode) from table
    ----------------------------------------------------------------------------
    SELECT COALESCE(MAX(protocolordercode), 0)
    INTO max_value
    FROM lslogilabprotocoldetail;

    ----------------------------------------------------------------------------
    -- 3. Compute next_value (same logic as SQL Server)
    ----------------------------------------------------------------------------
    IF max_value = 0 THEN
        -- Case A: table empty → start from 1000000
        next_value := 1000000;
    ELSE
        IF length(max_value::text) >= 7 THEN
            -- Case B: length ≥ 7 → valid → next = max + 1
            next_value := max_value + 1;
        ELSE
            -- Case C: length < 7 → convert to 7-digit series
            next_value := 1000000 + max_value + 1;
        END IF;
    END IF;

    ----------------------------------------------------------------------------
    -- 4. Reset or create sequence
    ----------------------------------------------------------------------------
    IF EXISTS (
        SELECT 1
        FROM pg_class
        WHERE relkind = 'S'
          AND relname = 'orderdetailprotocol_seq'
    ) THEN
        EXECUTE format(
            'ALTER SEQUENCE orderdetailprotocol_seq RESTART WITH %s',
            next_value
        );
    ELSE
        EXECUTE format(
            'CREATE SEQUENCE orderdetailprotocol_seq START WITH %s INCREMENT BY 1',
            next_value
        );
    END IF;

    RAISE NOTICE 'Sequence updated → New start value = %', next_value;
END $$;


