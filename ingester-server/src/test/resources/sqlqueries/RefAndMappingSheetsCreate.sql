CREATE TABLE [SalesLT].[RefData](
	[DEST_TABLE_NAME] [nvarchar](20) NOT NULL,
	[REF_DATA_CLASS] [nvarchar](20) NOT NULL,
	[REF_DATA_CLASS_KEY] [nvarchar](20) NOT NULL,
	[REF_DATA_CLASS_VALUE] [nvarchar](20) NOT NULL,
	[DATE_ADDED] [datetime] NOT NULL);

DROP TABLE [SalesLT].[MappingSheet];
CREATE TABLE [SalesLT].[MappingSheet](
	[SOURCE_ENTITY] [nvarchar](50) NOT NULL,
	[SOURCE_COL_NAME] [nvarchar](50) NOT NULL,
	[GENERIC_COL_NAME] [nvarchar](50) NOT NULL,
	[TARGET_ENTITY] [nvarchar](50) NOT NULL,
	[TARGET_COL_NAME] [nvarchar](50) NOT NULL,
	[REQUIRED_FLAG] [nvarchar](1) NOT NULL,
	[INGESTION_RULE] [nvarchar](50) NOT NULL,
	[DATE_ADDED] [datetime] NOT NULL DEFAULT CURRENT_TIMESTAMP);
