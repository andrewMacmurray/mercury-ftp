# Mercury FTP

Java FTP server


## Mandatory Commands

https://www.iana.org/assignments/ftp-commands-extensions/ftp-commands-extensions.xhtml

- [ ] ABOR - Abort
- [ ] ACCT - Account
- [ ] APPE - Append 
- [X] CWD - Change Working Directory 
- [ ] DELE - Delete File 
- [ ] HELP - Help
- [X] LIST - List 
- [ ] MODE - Transfer Mode 
- [X] NLST - Name List 
- [ ] NOOP - No-Op (Ping)
- [X] PASS - Password 
- [ ] PASV - Passive Mode 
- [X] PORT - Data Port 
- [X] QUIT - Logout 
- [ ] REIN - Reinitialize 
- [ ] REST - Restart 
- [X] RETR - Retrieve 
- [ ] RNFR - Rename From
- [ ] RNTO - Rename To
- [ ] SITE - Site Parameters
- [ ] STAT - Status
- [X] STOR - Store 
- [ ] STRU - File Structure 
- [ ] TYPE - Representation Type 
- [X] USER - User Name


## Core Storing and Retrieving Operations

- STORE (`STOR`) - Causes the server-DTP to accept the data transferred via the data connection and to store the data as a file at the server site. If a file exists at that site it will be replaced by the new one.

- RETRIEVE (`RETR`) - Causes the server-DTP to transfer a copy of the file specified at the pathname to a client. The status and contents of the file at the server site shall be unaffected.

- STORE UNIQUE (`STOU`) - Behaves like `STOR` but file must have a unique name to that directory (name must be included in the `205` transfer started response).

- APPEND (`APPE`) - Causes the Server-DTP to store a copy of the file at the specified server site. If the pathname exists the data should be appended to that file, otherwise a new one created.

## FTP Server Return Codes

https://en.wikipedia.org/wiki/List_of_FTP_server_return_codes

Response Status

+ `1XX` - Positive Preliminary Reply (requested action started, expect another reply soon)
+ `2XX` - Positive Completion Reply (good news, action complete)
+ `3XX` - Positive Intermediate Reply (command accepted but need some more info pls)
+ `4XX` - Transient Negative Completion Reply (command not accepted, please try again)
+ `5XX` - Permanent Negative Completion Reply (invalid command, please correct)

Response Info

+ `X0X` - Syntax errors
+ `X1X` - Information (usually replies to requests for information such as status or help)
+ `X2X` - Connections (replies referring to the control and data connections)
+ `X3X` - Authentication and Accounting
+ `X5X` - File System

### Common Codes

+ `150` - File status OK, about to open a data connection.
+ `250` - Requested file action OK
+ `200` - Command OK
+ `212` - Directory Status
+ `213` - File Status
+ `331` - User name OK, need password
+ `332` - Need account for login
+ `350` - Requested file action pending further information
+ `450` - File unavailable
+ `425` - Can't open data connection
+ `425` - Connection closed, transfer aborted
+ `500` - Syntax error
+ `530` - User not logged in
+ `502` - Command not implemented


