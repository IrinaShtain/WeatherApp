package com.shtain.irina.weatherapp.view.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shtain.irina.weatherapp.R;
import com.shtain.irina.weatherapp.utils.Constants;
import com.squareup.picasso.Picasso;

/**
 * Created by Irina Shtain on 03.04.2018.
 */
public class CustomWeatherComponent extends LinearLayout {
    private TextView tvTemp;
    private TextView tvDesc;
    private TextView tvHumidity;
    private TextView tvDate;
    private ImageView ivIcon;
    private Context mContext;

    public CustomWeatherComponent(Context context) {
        super(context);
        init(context);
    }

    public CustomWeatherComponent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomWeatherComponent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        mContext = context;
        setOrientation(LinearLayout.VERTICAL);
        View rootView = inflate(context, R.layout.compound_view, this);
        tvTemp = rootView.findViewById(R.id.tvTemp);
        tvDesc = rootView.findViewById(R.id.tvDesc);
        tvHumidity = rootView.findViewById(R.id.tvHumidity);
        tvDate = rootView.findViewById(R.id.tvDate);
        ivIcon = rootView.findViewById(R.id.ivIcon);

    }


    public String getTemp() {
        return tvTemp.getText().toString();
    }

    public String getHumidity() {
        return tvHumidity.getText().toString();
    }

    public String getDate() {
        return tvDate.getText().toString();
    }

    public String getTvDesc() {
        return tvDesc.getText().toString();
    }

    public void setTemp(String temp) {
        this.tvTemp.setText(temp);
    }

    public void setHumidity(String humidity) {
        this.tvHumidity.setText(humidity);
    }

    public void setDate(String date) {
        this.tvDate.setText(date);
    }

    public void setDesc(String desc) {
        this.tvDesc.setText(desc.toUpperCase());
    }

    public void setIcon(String iconUrl) {
        Picasso.with(mContext)
                .load(Constants.BASE_URL_IMAGE + iconUrl)
                .error(R.drawable.ic_sentiment_dissatisfied)
                .placeholder(R.drawable.ic_cloud_off)
                .into(ivIcon);
    }
}
