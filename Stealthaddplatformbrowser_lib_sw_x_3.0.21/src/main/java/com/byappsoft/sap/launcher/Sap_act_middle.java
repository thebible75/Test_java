package com.byappsoft.sap.launcher;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import static com.byappsoft.sap.main.Sap_act_middle_controller.EXTRA_START_NUMBER;
import static com.byappsoft.sap.main.Sap_act_middle_controller.NT_001;
import static com.byappsoft.sap.main.Sap_act_middle_controller.NT_002;
import static com.byappsoft.sap.main.Sap_act_middle_controller.NT_003;
import static com.byappsoft.sap.main.Sap_act_middle_controller.NT_004;
import static com.byappsoft.sap.main.Sap_act_middle_controller.NT_005;
import static com.byappsoft.sap.main.Sap_act_middle_controller.clearMiddle;
import static com.byappsoft.sap.main.Sap_act_middle_controller.showNotfoundPackagePopup;
import static com.byappsoft.sap.main.Sap_act_middle_controller.startAlarm;
import static com.byappsoft.sap.main.Sap_act_middle_controller.startCalendar;
import static com.byappsoft.sap.main.Sap_act_middle_controller.startCall;
import static com.byappsoft.sap.main.Sap_act_middle_controller.startCamera;
import static com.byappsoft.sap.main.Sap_act_middle_controller.startSetting;

public class Sap_act_middle extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		startPackage(getIntent());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		startPackage(intent);
	}

	/**
	 * Setting Custom Notification Bar
	 * @param intent
	 *
	 */

	private void startPackage(Intent intent){
		try{
			if(intent!=null){
				String NT_NUMBER = intent.getStringExtra(EXTRA_START_NUMBER);
				if(NT_NUMBER!=null && NT_NUMBER.isEmpty()==false){
					switch (NT_NUMBER){
						//-- Call
						case NT_001:
							startCall(this, NT_001);
//							startCustom(getPackageName(), "com.byappsoft.sap.browser.Sap_MainActivity");
							break;
						//-- Camera
						case NT_002:
							startCamera(this, NT_002);
							break;
						//-- Calendar
						case NT_003:
							startCalendar(this, NT_003);
							break;
						//-- Alarm
						case NT_004:
							startAlarm(this, NT_004);
							break;
						//-- Settings
						case NT_005:
							startSetting(this, NT_005);
							break;
						default:
							clearMiddle(this);
							break;
					}
				}else{
					clearMiddle(this);
				}
			}else{
				clearMiddle(this);
			}
		}catch (Exception e){
			clearMiddle(this);
		}
	}

	/**
	 *
	 * @param pkgName			- Pass in the name of the application package you want to run.
	 * @param activityName		- Pass the name of the activity path to be executed.
	 *
	 *  Example
	 *                             pkgName 			: com.byappsoft.sap.launcher
	 *                             activityName     : com.byappsoft.sap.browser.Sap_MainActivity
	 */
	private void startCustom(String pkgName, String activityName){
		try{
			ComponentName compName = new ComponentName(pkgName, activityName);
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.setComponent(compName);
			startActivity(intent);
			finish();
		}catch (Exception e){
			showNotfoundPackagePopup(this, pkgName);
		}
	}

}
