CREATE TABLE User_
    (
    UserID SERIAL PRIMARY KEY,
    Username VARCHAR(20),
    Password VARCHAR(20),
    Name VARCHAR(50),
    Firstname VARCHAR(50),
    DateCreated TIMESTAMP,
    DateDeleted TIMESTAMP,
    DateDeactivated TIMESTAMP,
    Active BOOLEAN,
    Deleted BOOLEAN,
    Quota BIGINT,
    Bandwidth BIGINT,
    LanguageID INT,
    Timezone INT,
    Notes TEXT,
    BackupClient SMALLINT,
    FreeTrial BOOLEAN
    );
    
CREATE TABLE Contact
    (
    ContactID SERIAL PRIMARY KEY,
    Name VARCHAR(50),
    Firstname VARCHAR(50),
    Email VARCHAR(250),
    UserID INT
    );
    
ALTER TABLE Contact ADD FOREIGN KEY(UserID) REFERENCES User_;

CREATE TABLE Client
    (
    ClientID SERIAL PRIMARY KEY,
    GUID VARCHAR(50),
    UserID INT
    );

ALTER TABLE Client ADD FOREIGN KEY(UserID) REFERENCES User_;    
    
CREATE TABLE BackupSet
    (
    BackupSetID SERIAL PRIMARY KEY,
    ClientID INT,
    Name VARCHAR(250),
    DateCreated TIMESTAMP,
    DateDeleted TIMESTAMP,
    DateDeactivated TIMESTAMP,
    Active BOOLEAN,
    Deleted BOOLEAN,
    Retention BOOLEAN,
    FilePermissions BOOLEAN,
    ContinuousProtection BOOLEAN,
    CPInterval INT,
    Type SMALLINT,
    IsDeltas BOOLEAN
    );

CREATE TABLE BackupSetItem
    (
    BackupSetItemID SERIAL PRIMARY KEY,
    BackupSetID INT,
    Root CHARACTER VARYING(3),   
    Path TEXT,
    Type SMALLINT,
    IsFolder BOOLEAN,
    Backup BOOLEAN,
    Deleted BOOLEAN
    );
    
ALTER TABLE BackupSetItem ADD FOREIGN KEY(BackupSetID) REFERENCES BackupSet;
 
CREATE TABLE Backup
    (
    BackupID SERIAL PRIMARY KEY,
    BackupSetID INT,
    Name VARCHAR(250),
    Datetime TIMESTAMP,
    DatetimeEnded TIMESTAMP,
    ServerID INT    
    );
    
ALTER TABLE Backup ADD FOREIGN KEY(BackupSetID) REFERENCES BackupSet;
    
CREATE TABLE BackupItem
    (
    BackupItemID SERIAL PRIMARY KEY,
    BackupID INT,
    BackupSetItemID INT,
    Root CHARACTER VARYING(3),
    Path TEXT,
    Type SMALLINT,
    Datetime TIMESTAMP,
    Position BIGINT,
    Length BIGINT,
    IsFolder BOOLEAN,
    IsDeltas BOOLEAN
    );

ALTER TABLE BackupItem ADD FOREIGN KEY(BackupID) REFERENCES Backup;
ALTER TABLE BackupItem ADD FOREIGN KEY(BackupSetItemID) REFERENCES BackupSetItem;

CREATE TABLE Restore
    (
    RestoreID SERIAL PRIMARY KEY,
    Datetime TIMESTAMP,
    DatetimeEnded TIMESTAMP,
    RestorePath TEXT,
    Overwrite BOOLEAN,
    RestorePermissions BOOLEAN
    );

ALTER TABLE Restore ADD FOREIGN KEY(BackupID) REFERENCES Backup;    
    
CREATE TABLE RestoreItem
    (
    RestoreItemID SERIAL PRIMARY KEY,
    RestoreID INT,
    Path TEXT,
    Type SMALLINT,
    Datetime TIMESTAMP
    );
    
ALTER TABLE RestoreItem ADD FOREIGN KEY(RestoreID) REFERENCES Restore;
    
CREATE TABLE Server
    (
    ServerID SERIAL PRIMARY KEY,
    IP VARCHAR(50),
    Name VARCHAR(50),
    Login VARCHAR(50),
    Password VARCHAR(50),
    BackupPath varchar(250)
    );
    
CREATE TABLE Schedule
    (
    ScheduleID SERIAL PRIMARY KEY,
    Name VARCHAR(50),
    DaysOfWeek BIGINT,
    Time INT,
    BackupSetID INT
    );
   
ALTER TABLE Schedule ADD FOREIGN KEY(BackupSetID) REFERENCES BackupSet;    
    
CREATE TABLE Filter
    (
    FilterID SERIAL PRIMARY KEY,
    Name VARCHAR(50),
    Include BOOLEAN,
    TopPath VARCHAR(250),
    BackupSetID INT
    );

ALTER TABLE Filter ADD FOREIGN KEY(BackupSetID) REFERENCES BackupSet;    
    
CREATE TABLE Criteria
    (
    CriteriaID SERIAL PRIMARY KEY,
    Pattern VARCHAR(50),
    Type SMALLINT,
    FilterID INT 	
    );

ALTER TABLE Criteria ADD FOREIGN KEY(FilterID) REFERENCES Filter;    
    
CREATE TABLE IPRestriction
    (
    IPRestrictionID SERIAL PRIMARY KEY,
    FromIP VARCHAR(50),
    ToIP VARCHAR(50),
    BackupSetID INT
    );

ALTER TABLE IPRestriction ADD FOREIGN KEY(BackupSetID) REFERENCES BackupSet;    
    
CREATE TABLE Settings
    (
    SettingsID SERIAL PRIMARY KEY,
    SmtpServer VARCHAR(250),
    SmtpLogin VARCHAR(50),
    SmtpPassword VARCHAR(50),
    ReportSenderName VARCHAR(250),
    ReportSenderEmail VARCHAR(250),
    SendWelcomeEmail BOOLEAN,
    SendPasswordInEmail BOOLEAN,
    PermissionsRetention BOOLEAN,
    MovedRetention BOOLEAN,
    EnableFreeTrial BOOLEAN,
    FreeTrialQuota INT,
    FTSuspendAfterDays INT,
    FTRemoveAfterDays INT    
    );
    
CREATE TABLE Language
    (
    LanguageID SERIAL PRIMARY KEY,
    Code VARCHAR(2),
    Name VARCHAR(50)
    );
    
CREATE TABLE LanguageSettings
    (
    LanguageSettingsID SERIAL PRIMARY KEY,
    SettingsID INT,
    LanguageID INT
    );

ALTER TABLE LanguageSettings ADD FOREIGN KEY(SettingsID) REFERENCES Settings;
ALTER TABLE LanguageSettings ADD FOREIGN KEY(LanguageID) REFERENCES Language;
    
CREATE TABLE Module
    (
    ModuleID SERIAL PRIMARY KEY,
    Name VARCHAR(50)
    );
    
CREATE TABLE TrialModuleSettings
    (
    TrialModuleSettingsID SERIAL PRIMARY KEY,
    SettingsID INT,
    ModuleID INT
    );
    
ALTER TABLE TrialModuleSettings ADD FOREIGN KEY(SettingsID) REFERENCES Settings;
ALTER TABLE TrialModuleSettings ADD FOREIGN KEY(ModuleID) REFERENCES Module;