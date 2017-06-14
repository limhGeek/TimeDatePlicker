package com.ui.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.limh.agile.dialog.TimeDatePicker;
import com.limh.agile.wheelview.DateChangeInterface;

public class MainActivity extends AppCompatActivity {
    TextView txtDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeDatePicker timeDatePicker = new TimeDatePicker(MainActivity.this);
                timeDatePicker.setShowLabel(true, true, true, true, true);
                timeDatePicker.setSyncUpdate(true);
                timeDatePicker.setDialog(new DateChangeInterface() {
                    @Override
                    public void onChanged(int year, int month, int day, int hour, int minute) {
                        Log.e("date", year + "/" + month + "/" + day + " " + hour + ":" + minute);
                        txtDate.setText(year + "/" + month + "/" + day + " " + hour + ":" + minute);
                    }
                });
                timeDatePicker.show();
            }
        });
    }
}
