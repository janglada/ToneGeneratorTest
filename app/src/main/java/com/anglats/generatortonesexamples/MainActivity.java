package com.anglats.generatortonesexamples;

import java.lang.reflect.Field;

import android.app.Activity;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, ToneGenerator.MAX_VOLUME);

		// For simplicity use a LinearLayout instead of ListView +  adapter
		ScrollView sc = new ScrollView(this);
		LinearLayout main = new LinearLayout(this);
		main.setOrientation(LinearLayout.VERTICAL);


		/**
		 *
		 * Retrieve all static fields from ToneGenerator that start width "TONE_"
		 *
		 */
		Field[] declaredFields = ToneGenerator.class.getDeclaredFields();
		for (Field field : declaredFields) {
			if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
				if (field.getName().startsWith("TONE_")) {
					TextView tv = new TextView(this);
					tv.setText(field.getName());
					tv.setPadding(5, 5, 5, 5);
					tv.setTextSize(20);
					main.addView(tv, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
					try {
						tv.setTag(field.getInt(null));
						tv.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
								int tone = (int) view.getTag();
								tg.startTone(tone, 100);
							}
						});
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
		sc.addView(main, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		setContentView(sc);

	}

}
