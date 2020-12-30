--Azure SQL Server Queries

--truncate table SalesLT.LOC;

--truncate table SalesLT.MappingSheet;

select * FROM SalesLT.RefData where DEST_TABLE_NAME = 'LOCX';

select * from SalesLT.MappingSheet;

select * from SalesLT.LOC

