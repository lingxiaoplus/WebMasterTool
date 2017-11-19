package com.lingxiao.webmastertool.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.lingxiao.webmastertool.R;

/**
 * Created by lingxiao on 17-11-18.
 */

public class CardLayout extends CardView{

    private TypedArray mTyepdArray;
    private TextView nameView,descView;

    public CardLayout(Context context) {
        this(context,null);
    }

    public CardLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }

    public CardLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs) {
        View.inflate(getContext(), R.layout.card_item,this);
        nameView = findViewById(R.id.tv_card_name);
        descView = findViewById(R.id.tv_card_desc);
        CardView cardView = findViewById(R.id.card_root);
        mTyepdArray = context.obtainStyledAttributes(attrs,
                R.styleable.CardLayout);
        int cardBackground = mTyepdArray.getColor(R.styleable.CardLayout_cardBackground,
                getResources().getColor(R.color.colorPrimary));
        String cardName = mTyepdArray.getString(R.styleable.CardLayout_cardName);
        String cardDesc = mTyepdArray.getString(R.styleable.CardLayout_cardDes);
        mTyepdArray.recycle();
        nameView.setText(cardName);
        descView.setText(cardDesc);
        cardView.setCardBackgroundColor(cardBackground);
    }
}
