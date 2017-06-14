package com.limh.agile.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.limh.agile.R;
import com.limh.agile.utils.DateUtils;
import com.limh.agile.wheelview.DateChangeInterface;
import com.limh.agile.wheelview.NumericWheelAdapter;
import com.limh.agile.wheelview.OnWheelChangedListener;
import com.limh.agile.wheelview.OnWheelScrollListener;
import com.limh.agile.wheelview.WheelView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2017/6/6.
 */

public class TimeDatePicker extends Dialog implements View.OnClickListener, OnWheelChangedListener {
    private WheelView wvDay;
    private DateChangeInterface dateChangeInterface;

    private List<String> years;
    private List<String> months;
    private List<String> days;
    private List<String> hours;
    private List<String> minutes;
    private int year, month, day, hour, minute;
    private boolean isYear = true;
    private boolean isMonth = true;
    private boolean isDay = true;
    private boolean isHour = true;
    private boolean isMinute = true;
    private boolean syncUpdate = true;

    public TimeDatePicker(Context context) {
        super(context, R.style.dialog);
        initDatas();
    }

    /**
     * 是否实时更新数据
     *
     * @param syncUpdate true:实时更新  false:按下确定再刷新
     */
    public void setSyncUpdate(boolean syncUpdate) {
        this.syncUpdate = syncUpdate;
    }

    private boolean isSyncUpdate() {
        return syncUpdate;
    }

    public void setShowLabel(boolean isYear, boolean isMonth, boolean isDay, boolean isHour, boolean isMinute) {
        this.isYear = isYear;
        this.isMonth = isMonth;
        this.isDay = isDay;
        this.isHour = isHour;
        this.isMinute = isMinute;
    }

    public void setDialog(DateChangeInterface dateChangeInterface) {
        this.dateChangeInterface = dateChangeInterface;
        View mView;
        mView = LayoutInflater.from(getContext()).inflate(R.layout.view_dialog_date, null);
        TextView txtYes = (TextView) mView.findViewById(R.id.txt_dialog_yes);
        txtYes.setOnClickListener(this);
        TextView txtCancel = (TextView) mView.findViewById(R.id.txt_dialog_cancel);
        txtCancel.setOnClickListener(this);
        WheelView wvYear = (WheelView) mView.findViewById(R.id.wv_year);
        if (!isYear) {
            wvYear.setVisibility(View.GONE);
        } else {
            wvYear.setAdapter(new NumericWheelAdapter(years));
            wvYear.setCurrentItem(50);
            wvYear.addChangingListener(this);
        }
        WheelView wvMonth = (WheelView) mView.findViewById(R.id.wv_month);
        if (!isMonth) {
            wvMonth.setVisibility(View.GONE);
        } else {
            wvMonth.setAdapter(new NumericWheelAdapter(months));
            wvMonth.setCurrentItem(month - 1);
            wvMonth.addChangingListener(this);
            wvMonth.addScrollingListener(new OnWheelScrollListener() {
                @Override
                public void onScrollingStarted(View view) {

                }

                @Override
                public void onScrollingFinished(View view) {
                    days.clear();
                    for (int i = 0; i < DateUtils.getDaysOfMonth(year, month); i++) {
                        days.add(formatSuff(1 + i));
                    }
                    wvDay.setAdapter(new NumericWheelAdapter(days));
                    wvDay.setCurrentItem(0);
                }
            });
        }
        wvDay = (WheelView) mView.findViewById(R.id.wv_day);
        if (!isDay) {
            wvDay.setVisibility(View.GONE);
        } else {
            wvDay.setAdapter(new NumericWheelAdapter(days));
            wvDay.setCurrentItem(day - 1);
            wvDay.addChangingListener(this);
        }

        WheelView wvHour = (WheelView) mView.findViewById(R.id.wv_hour);
        if (!isHour) {
            wvHour.setVisibility(View.GONE);
        } else {
            wvHour.setAdapter(new NumericWheelAdapter(hours));
            wvHour.setCurrentItem(hour);
            wvHour.addChangingListener(this);
        }

        WheelView wvMinute = (WheelView) mView.findViewById(R.id.wv_minute);
        if (!isMinute) {
            wvMinute.setVisibility(View.GONE);
        } else {
            wvMinute.setAdapter(new NumericWheelAdapter(minutes));
            wvMinute.setCurrentItem(minute);
            wvMinute.addChangingListener(this);
        }
        super.setContentView(mView);
    }

    private void initDatas() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
        years = new ArrayList<>();
        for (int i = -50; i < 50; i++) {
            years.add(formatSuff(year + i));
        }
        months = new ArrayList<>();
        for (int i = 0; i < 12; i++)
            months.add(formatSuff(1 + i));
        days = new ArrayList<>();
        for (int i = 0; i < DateUtils.getDaysOfMonth(year, month); i++) {
            days.add(formatSuff(1 + i));
        }
        hours = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            hours.add(formatSuff(i));
        }
        minutes = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            minutes.add(formatSuff(i));
        }
    }

    private String formatSuff(int num) {
        return num < 10 ? ("0" + num) : "" + num;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.txt_dialog_yes) {
            dateChangeInterface.onChanged(year, month, day, hour, minute);
        }
        if (isShowing())
            dismiss();
    }

    @Override
    public void onChanged(View context, int oldValue, int newValue) {
        int id = context.getId();
        if (id == R.id.wv_year) {
            year = Integer.parseInt(years.get(newValue));
        } else if (id == R.id.wv_month) {
            month = Integer.parseInt(months.get(newValue));
        } else if (id == R.id.wv_day) {
            day = Integer.parseInt(days.get(newValue));
        } else if (id == R.id.wv_hour) {
            hour = Integer.parseInt(hours.get(newValue));
        } else if (id == R.id.wv_minute) {
            minute = Integer.parseInt(minutes.get(newValue));
        }
        if (isSyncUpdate())
            dateChangeInterface.onChanged(year, month, day, hour, minute);
    }
}
