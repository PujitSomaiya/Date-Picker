package com.pujit.datepicker;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.michaldrabik.classicmaterialtimepicker.CmtpDateDialogFragment;
import com.michaldrabik.classicmaterialtimepicker.CmtpTimeDialogFragment;
import com.michaldrabik.classicmaterialtimepicker.CmtpTimePickerView;
import com.michaldrabik.classicmaterialtimepicker.OnTime12PickedListener;
import com.michaldrabik.classicmaterialtimepicker.model.CmtpTime12;
import com.vivekkaushik.datepicker.DatePickerTimeline;
import com.vivekkaushik.datepicker.OnDateSelectedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static androidx.core.util.Preconditions.checkArgument;

public class DatePickerActivity extends AppCompatActivity {

    private DatePickerTimeline dpDateLine;
    private TextView tvDate,tvFrom,tvFromm,tvTo,tvToo;
    private Date date;
    private FragmentManager fragmentManager;
    private CmtpTimeDialogFragment cmtpTimeDialogFragment=CmtpTimeDialogFragment.newInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            initControls();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void initControls() throws ParseException {

        initListeners();
        datePickerTimeline();
    }




    private void datePickerTimeline() throws ParseException {
        date = (Calendar.getInstance().getTime());
        dpDateLine.setInitialDate(date.getYear(), date.getMonth(), date.getDate());
        dpDateLine.setSelected(true);
        dpDateLine.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int dayOfWeek) {
                Calendar calendar;
                calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d");
                calendar.set(year, month, day);
                Date date=new Date(year,month,day);
                String d = dateFormat.format(date);
                tvDate.setText(d+getDayOfMonthSuffix(day)+",");
            }

            @Override
            public void onDisabledDateSelected(int year, int month, int day, int dayOfWeek, boolean isDisabled) {

            }
        });

    }

    private void initListeners() {
        dpDateLine = findViewById(R.id.dpDateLine);
        tvDate = findViewById(R.id.tvDate);
        tvFromm = findViewById(R.id.tvFromm);
        tvToo = findViewById(R.id.tvToo);
        tvTo = findViewById(R.id.tvTo);
        tvFrom = findViewById(R.id.tvFrom);
        CmtpTimePickerView cmtpTimePickerView = findViewById(R.id.cmtpTimePickerView);
        fragmentManager=this.getSupportFragmentManager();
        cmtpTimeDialogFragment.setInitialTime12(10,10, CmtpTime12.PmAm.AM);
        tvFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvFrom();
            }
        });
        tvTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvTo();
            }
        });

    }
    @SuppressLint("RestrictedApi")
    String getDayOfMonthSuffix(final int n) {
        checkArgument(n >= 1 && n <= 31, "illegal day of month: " + n);
        if (n >= 11 && n <= 13) {
            return "th";
        }
        switch (n % 10) {
            case 1:  return "st";
            case 2:  return "nd";
            case 3:  return "rd";
            default: return "th";
        }
    }



    private void tvFrom() {


        cmtpTimeDialogFragment.setOnTime12PickedListener(new OnTime12PickedListener() {
            @Override
            public void onTimePicked(CmtpTime12 cmtpTime12) {
                tvFromm.setText(String.valueOf(cmtpTime12)+"      to");
                tvFrom.setText(String.valueOf(cmtpTime12));
            }
        });
        cmtpTimeDialogFragment.show( fragmentManager,"From");
    }

    private void tvTo() {
        cmtpTimeDialogFragment.setInitialTime12(10,10, CmtpTime12.PmAm.AM);
        cmtpTimeDialogFragment.setOnTime12PickedListener(new OnTime12PickedListener() {
            @Override
            public void onTimePicked(CmtpTime12 cmtpTime12) {
                tvToo.setText(String.valueOf(cmtpTime12));
                tvTo.setText(String.valueOf(cmtpTime12));
            }
        });
        cmtpTimeDialogFragment.show( fragmentManager,"T0");
    }
}
