package com.byappsoft.sap.launcher;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.res.ResourcesCompat;

import com.byappsoft.sap.R;
import com.byappsoft.sap.main.Sap_act_main;
import com.byappsoft.sap.utils.Sap_Func;

import java.util.Locale;

public class Sap_act_main_launcher{

	private static AlertDialog mDialog;

	@SuppressLint({"InflateParams", "ObsoleteSdkInt"})
	public static void initsapStart(final Context context, final String sAgkey, final boolean NOTIBA, final boolean URLSEARCH) {

		try {

			if (Build.VERSION.SDK_INT >= 16) {

				Sap_Func.initkeypreference(context);

				if (!NOTIBA) {
					Sap_act_main.initSapStartapp(context, sAgkey, false, URLSEARCH);
					return;
				}

				if (mDialog!= null && mDialog.isShowing()) {
					mDialog.dismiss();
					mDialog = null;
				}

				if (!Sap_Func.isNotibarPopState(context)) {

					AlertDialog.Builder 	builder 	= new AlertDialog.Builder(context);
					LayoutInflater 			inflater 	= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					View 					layout 		= inflater.inflate(R.layout.lay_sap_act_pop, null);
					ImageView 				bgImg 		= layout.findViewById(R.id.sap_alert_popup_bg);


					Drawable imgDrawable;
					switch (Locale.getDefault().getLanguage()) {
						case "ko":
							imgDrawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.res_sap_notiba_img_ko, null);
							break;
						case "zh":
							imgDrawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.res_sap_notiba_img_cn, null);
							break;
						case "vi":
							imgDrawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.res_sap_notiba_img_vi, null);
							break;
						case "ja":
							imgDrawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.res_sap_notiba_img_ja, null);
							break;
						default:
							imgDrawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.res_sap_notiba_img_en, null);
							break;
					}
					bgImg.setImageDrawable(imgDrawable);


					layout.findViewById(R.id.sap_res_btn_ok).setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							Sap_Func.setNotibarPopState(context, true);
							Sap_act_main.initSapStartapp(context, sAgkey, true, URLSEARCH);
							mDialog.dismiss();
							mDialog = null;
						}
					});

					layout.findViewById(R.id.sap_res_btn_can).setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							Sap_Func.setNotibarPopState(context, true);
							Sap_act_main.initSapStartapp(context, sAgkey, false, URLSEARCH);
							mDialog.dismiss();
							mDialog = null;
						}
					});

					builder.setView(layout);
					mDialog = builder.create();

					mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
						@Override
						public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
							return keyCode == KeyEvent.KEYCODE_BACK;
						}
					});
					mDialog.show();

				} else {
					Sap_act_main.initSapStartapp(context, sAgkey, Sap_Func.isNotiBarState(context), URLSEARCH);
				}

			}

		} catch (Exception ignored) {
		}

	}

}
