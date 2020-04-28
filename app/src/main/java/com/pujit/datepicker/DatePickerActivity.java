package com.pujit.datepicker;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;
import com.michaldrabik.classicmaterialtimepicker.CmtpTimeDialogFragment;
import com.michaldrabik.classicmaterialtimepicker.CmtpTimePickerView;
import com.michaldrabik.classicmaterialtimepicker.OnTime12PickedListener;
import com.michaldrabik.classicmaterialtimepicker.model.CmtpTime12;
import com.vivekkaushik.datepicker.DatePickerTimeline;
import com.vivekkaushik.datepicker.OnDateSelectedListener;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static androidx.core.util.Preconditions.checkArgument;

public class DatePickerActivity extends AppCompatActivity implements View.OnClickListener, DatePickerListener {

    private HorizontalPicker dpDateLine;
    private TextView tvDate, tvFrom, tvFromm, tvTo, tvToo, tvMonths;
    private ImageView imgRight, imgLeft;
    private DateTime currentTime;
    private Date date;
    private Integer currentMonth;
    private CmtpTime12 minCheck;
    private FragmentManager fragmentManager;
    private CmtpTimePickerView cmtpTimePickerView;
    private CmtpTimeDialogFragment cmtpTimeDialogFragment = CmtpTimeDialogFragment.newInstance();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initControls();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initControls() {

        initListeners();
        datePickerTimeline();
    }


    private void datePickerTimeline() {
        date = (Calendar.getInstance().getTime());
        dpDateLine.setDate(DateTime.now());
//        dpDateLine.setSelected(true);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initListeners() {
        dpDateLine = findViewById(R.id.dpDateLine);
        imgLeft = findViewById(R.id.imgLeft);
        imgRight = findViewById(R.id.imgRight);
        tvMonths = findViewById(R.id.tvMonths);
        dpDateLine.setListener(this).init();
        dpDateLine.setDateSelectedColor(Color.RED).setDateSelectedTextColor(Color.BLUE).setDayOfWeekTextColor(Color.BLUE);
        dpDateLine.setDate(new DateTime());
        tvDate = findViewById(R.id.tvDate);
        tvFromm = findViewById(R.id.tvFromm);
        tvToo = findViewById(R.id.tvToo);
        tvTo = findViewById(R.id.tvTo);
        tvFrom = findViewById(R.id.tvFrom);
        cmtpTimePickerView = findViewById(R.id.cmtpTimePickerView);
        fragmentManager = this.getSupportFragmentManager();
        cmtpTimeDialogFragment.setInitialTime12(10, 10, CmtpTime12.PmAm.AM);
        tvTo.setOnClickListener(this);
        tvFrom.setOnClickListener(this);
        imgLeft.setOnClickListener(this);
        imgRight.setOnClickListener(this);
    }

    @SuppressLint("RestrictedApi")
    String getDayOfMonthSuffix(final int n) {
        checkArgument(n >= 1 && n <= 31, "illegal day of month: " + n);
        if (n >= 11 && n <= 13) {
            return "th";
        }
        switch (n % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }


    private void tvFrom() {


        cmtpTimeDialogFragment.setOnTime12PickedListener(new OnTime12PickedListener() {
            @Override
            public void onTimePicked(CmtpTime12 cmtpTime12) {
                minCheck = cmtpTime12;
                tvFromm.setText(String.valueOf(cmtpTime12) + "      to");
                tvFrom.setText(String.valueOf(cmtpTime12));
                if (String.valueOf(cmtpTime12.getHour()).equals("12")) {
                    tvTo.setText("1:" + String.valueOf(cmtpTime12.getMinute() + ":") + String.valueOf(cmtpTime12.getPmAm()));
                    tvToo.setText("1:" + String.valueOf(cmtpTime12.getMinute() + ":") + String.valueOf(cmtpTime12.getPmAm()));
                } else {
                    tvTo.setText(String.valueOf(cmtpTime12.getHour() + 1 + ":") + String.valueOf(cmtpTime12.getMinute() + ":") + String.valueOf(cmtpTime12.getPmAm()));
                    tvToo.setText(String.valueOf(cmtpTime12.getHour() + 1 + ":") + String.valueOf(cmtpTime12.getMinute() + ":") + String.valueOf(cmtpTime12.getPmAm()));
                }
                cmtpTimePickerView.setTime(cmtpTime12);
            }
        });
        cmtpTimeDialogFragment.show(fragmentManager, "From");
    }

    private void tvTo() {
        cmtpTimeDialogFragment.setOnTime12PickedListener(new OnTime12PickedListener() {
            @Override
            public void onTimePicked(CmtpTime12 cmtpTime12) {
                if (String.valueOf(cmtpTime12).equals(tvFrom.getText().toString())) {
                    if (String.valueOf(cmtpTime12.getHour()).equals("12")) {
                        tvTo.setText("1:" + String.valueOf(cmtpTime12.getMinute() + ":") + String.valueOf(cmtpTime12.getPmAm()));
                        tvToo.setText("1:" + String.valueOf(cmtpTime12.getMinute() + ":") + String.valueOf(cmtpTime12.getPmAm()));
                    } else {
                        if (Integer.parseInt(String.valueOf(cmtpTime12.getHour())) < (Integer.parseInt(String.valueOf(minCheck.getHour())))) {
                            tvTo.setText(String.valueOf(minCheck.getHour() + 1 + ":") + String.valueOf(minCheck.getMinute() + " :") + String.valueOf(cmtpTime12.getPmAm()));
                            tvToo.setText(String.valueOf(minCheck.getHour() + 1 + ":") + String.valueOf(minCheck.getMinute() + ":") + String.valueOf(cmtpTime12.getPmAm()));
                        } else {
                            if (Integer.parseInt(String.valueOf(cmtpTime12.getMinute())) < (Integer.parseInt(String.valueOf(minCheck.getMinute())))) {
                                tvTo.setText(String.valueOf(cmtpTime12.getHour() + 1 + ":") + String.valueOf(minCheck.getMinute() + ":") + String.valueOf(cmtpTime12.getPmAm()));
                                tvToo.setText(String.valueOf(cmtpTime12.getHour() + 1 + ":") + String.valueOf(minCheck.getMinute() + ":") + String.valueOf(cmtpTime12.getPmAm()));
                            } else {
                                tvTo.setText(String.valueOf(cmtpTime12.getHour() + 1 + ":") + String.valueOf(cmtpTime12.getMinute() + ":") + String.valueOf(cmtpTime12.getPmAm()));
                                tvToo.setText(String.valueOf(cmtpTime12.getHour() + 1 + ":") + String.valueOf(cmtpTime12.getMinute() + ":") + String.valueOf(cmtpTime12.getPmAm()));
                            }
                        }
                    }
                } else if (String.valueOf(cmtpTime12.getHour()).equals("12")) {
                    tvTo.setText("1:" + String.valueOf(cmtpTime12.getMinute() + ":") + String.valueOf(cmtpTime12.getPmAm()));
                    tvToo.setText("1:" + String.valueOf(cmtpTime12.getMinute() + ":") + String.valueOf(cmtpTime12.getPmAm()));
                } else if (Integer.parseInt(String.valueOf(cmtpTime12.getHour())) < (Integer.parseInt(String.valueOf(minCheck.getHour())))) {
                    tvTo.setText(String.valueOf(minCheck.getHour() + 1 + ":") + String.valueOf(minCheck.getMinute() + " :") + String.valueOf(cmtpTime12.getPmAm()));
                    tvToo.setText(String.valueOf(minCheck.getHour() + 1 + ":") + String.valueOf(minCheck.getMinute() + ":") + String.valueOf(cmtpTime12.getPmAm()));
                } else if (Integer.parseInt(String.valueOf(cmtpTime12.getMinute())) < (Integer.parseInt(String.valueOf(minCheck.getMinute()))) && Integer.parseInt(String.valueOf(cmtpTime12.getHour())) < (Integer.parseInt(String.valueOf(minCheck.getHour())))) {
                    tvTo.setText(String.valueOf(cmtpTime12.getHour() + 1 + ":") + String.valueOf(minCheck.getMinute() + ":") + String.valueOf(cmtpTime12.getPmAm()));
                    tvToo.setText(String.valueOf(cmtpTime12.getHour() + 1 + ":") + String.valueOf(minCheck.getMinute() + ":") + String.valueOf(cmtpTime12.getPmAm()));
                } else {
                    tvTo.setText(String.valueOf(cmtpTime12.getHour() + 1 + ":") + String.valueOf(cmtpTime12.getMinute() + ":") + String.valueOf(cmtpTime12.getPmAm()));
                    tvToo.setText(String.valueOf(cmtpTime12.getHour() + 1 + ":") + String.valueOf(cmtpTime12.getMinute() + ":") + String.valueOf(cmtpTime12.getPmAm()));
                }

            }
        });
        cmtpTimeDialogFragment.show(fragmentManager, "To");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tvTo) {
            tvTo();
        } else if (view.getId() == R.id.tvFrom) {
            tvFrom();
        } else if (view.getId() == R.id.imgRight) {
            moveRight();
        } else if (view.getId() == R.id.imgLeft) {
            moveLeft();
        }
    }

    private void moveLeft() {
        if (currentTime.getMonthOfYear()>=date.getMonth()){
            currentTime = new DateTime(currentTime.getYear(), (currentTime.getMonthOfYear()-1), currentTime.getDayOfMonth(), 0, 0);
            dpDateLine.setDate(currentTime);
        }else {
            dpDateLine.setDate(new DateTime());
        }

    }

    private void moveRight() {
        if (currentTime.getMonthOfYear()>=date.getMonth()){
            if (currentTime.getMonthOfYear()==13){
                dpDateLine.setDate(new DateTime());
            }else {

                currentTime = new DateTime(currentTime.getYear(), currentTime.getMonthOfYear() + 1, currentTime.getDayOfMonth(), 0, 0);
                dpDateLine.setDate(currentTime);
            }
        }else {
            dpDateLine.setDate(new DateTime());
        }

    }

    @Override
    public void onDateSelected(DateTime dateSelected) {
//        Calendar calendar;
//        calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
//        calendar.set(year, month, day);

        currentMonth = (dateSelected.getMonthOfYear());
        Date date = new Date(dateSelected.getYear(), dateSelected.getMonthOfYear() - 1, dateSelected.getDayOfMonth());
        String d = dateFormat.format(date);
        tvDate.setText(d + getDayOfMonthSuffix(dateSelected.getDayOfMonth()) + ",");
        String month = monthFormat.format(date);
        tvMonths.setText(month);
        currentTime = dateSelected;

    }
}
