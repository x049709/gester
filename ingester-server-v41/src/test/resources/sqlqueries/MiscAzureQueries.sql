--Azure SQL Server Queries


--truncate table SalesLT.MappingSheet;

--truncate table SalesLT.RefData;
select * FROM SalesLT.RefData where DEST_TABLE_NAME = 'LOC';

select * from SalesLT.MappingSheet;

select distinct(INGESTION_RULE) from SalesLT.MappingSheet;
--update SalesLT.MappingSheet set INGESTION_RULE = 'INTEGER' where GENERIC_COL_NAME = 'inField007';
--update SalesLT.MappingSheet set INGESTION_RULE = 'LOOKUP' where GENERIC_COL_NAME = 'inField024';

--truncate table SalesLT.LOC;
select count(*) from SalesLT.LOC
select * from SalesLT.LOC


