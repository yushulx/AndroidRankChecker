package com.dynamsoft.rankchecker;

import main.java.google.pagerank.PageRank;

import com.alexarank.AlexaRank;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	private Button mButton;
	private TextView mResults;
	private EditText mURL;
	private static final String mNewline = "\n";
	private ProgressDialog mProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mButton = (Button) findViewById(R.id.btSearch);
		mButton.setOnClickListener(this);
		mURL = (EditText) findViewById(R.id.url);
		mResults = (TextView) findViewById(R.id.results);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String url = mURL.getText().toString();
		String tmpURL = url.trim();
		if (url != null && !url.equals("")) {
			if (!url.startsWith("http")) {
				tmpURL = "http://" + url;
			}
			final String finalURL = tmpURL;
			final String logURL = url;
			mProgressDialog = ProgressDialog.show(this,
					getString(R.string.process_dialog_title),
					getString(R.string.process_dialog_content));
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					final int pageRank = PageRank.get(finalURL);
					final int alexRank = AlexaRank.getAlexaRank(finalURL);
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							mResults.append(logURL + ": PageRank = " + pageRank
									+ "; Alexa rank = " + alexRank + mNewline);
							mURL.setText("");
							mProgressDialog.dismiss();
						}

					});

				}

			}).start();
		}
	}

}
