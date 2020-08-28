# File-Backup-Tools
This project will provide various tools to manage a file backup system. It is currently in its first basic version, and serves one purpose: to identify files deemed "extra" in a backup filesystem and delete them if wanted. The source folder described in this program is a folder that will be the template and the backup folder is where the extra files are being searched for; any file that is found in backup or in a folder inside of backup and is not found anywhere inside the source folder is flagged as extra.

The first version of what I am calling "Smart Traversal" has now been merged into the master branch. Users should experience quicker load times when searching for extra files in most cases, and the command and user interaction has been streamlined. 

## Information on running in Terminal: 
- Install latest version of JDK from Oracle's website
- Change your working directory to be in the bin folder of this project
- Run the command: ```java ui.BackupTools```
- Follow onscreen instructions. 

## Current commands available to the user:
- ```help```: Prints a list of commands the user has available to them.
- ```quit```: Stops execution of the program.
- ```extra files```: Command for finding extra files in either the "source" or "backup" file directories (read above for explanation of meaning of "extra files").
- ```search```: Allows search for a specific filename specified by the user in either the source or backup directories.
- ```duplicate files```: Command for finding duplicate files in either the "source" or "backup" file directories.
- ```backup files```: Command for backing up files from the "source" to "backup" (files not found in backup will be copied from source).

## Known Bugs:
- If you try to give the root of a hard drive as a source or backup folder, the program will crash. Right now the only workaround is to put everything you want into a folder at the top of the drive and then point to that folder in the script.

Disclaimer: Be careful! Run at your own risk, and only point to folders that you are ok with potentially losing data in if you want to test my project and be safe.
