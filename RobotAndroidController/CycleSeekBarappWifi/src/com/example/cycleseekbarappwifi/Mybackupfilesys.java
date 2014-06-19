package com.example.cycleseekbarappwifi;

import android.app.backup.BackupAgentHelper;
import android.app.backup.SharedPreferencesBackupHelper;

public class Mybackupfilesys extends BackupAgentHelper{
	public void onCreate(){
		SharedPreferencesBackupHelper myfile=new  SharedPreferencesBackupHelper(this,"user_preferences");
		addHelper("prefs",myfile);
	}
}