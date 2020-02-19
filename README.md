# File-Backup-Tools
This project will provide various tools to manage a file backup system. It is currently in its first basic version, and serves one purpose: to identify files deemed "extra" in a backup filesystem and delete them if wanted. The source folder described in this program is a folder that will be the template and the backup folder is where the extra files are being searched for; any file that is found in backup or in a folder inside of backup and is not found anywhere inside the source folder is flagged as extra.

Information on running in Terminal: 
- Install latest version of JDK from Oracle's website
- Change your working directory to be in the bin folder of this project
- Run the command: ```java BackupTools```
- Follow onscreen instructions. 


Disclaimer: Be careful! Run at your own risk, and only point to folders that you are ok with potentially losing data in if you want to test my project and be safe.
