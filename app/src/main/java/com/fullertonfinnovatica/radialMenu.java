package com.fullertonfinnovatica;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Toast;

/**
 * Created by HP on 03-10-2018.
 */
public class radialMenu extends View {

    //the number of slice
    private int mSlices = 4;

    //the angle of each slice
    private int degreeStep = 360 / mSlices;

    private int quarterDegreeMinus = -90;

    private float mOuterRadius;
    private float mInnerRadius;

    //using radius square to prevent square root calculation
    private float outerRadiusSquare;
    private float innerRadiusSquare;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF mSliceOval = new RectF();

    private static final double quarterCircle = Math.PI / 2;

    private float innerRadiusRatio = 0.25F;

    //color for the slices
    private int[] colors = new int[]{
            ContextCompat.getColor(getContext(),R.color.radial1),
            ContextCompat.getColor(getContext(),R.color.radial3),
            ContextCompat.getColor(getContext(),R.color.radial2),
            ContextCompat.getColor(getContext(), R.color.radial4)};

    //bitmap for the slices
    private Bitmap[] bitmaps = new Bitmap[]{
            ((BitmapDrawable) ContextCompat.getDrawable(getContext(),R.drawable.trolley)).getBitmap(),
            ((BitmapDrawable) ContextCompat.getDrawable(getContext(),R.drawable.debitcard)).getBitmap(),
            ((BitmapDrawable) ContextCompat.getDrawable(getContext(),R.drawable.network)).getBitmap(),
            ((BitmapDrawable) ContextCompat.getDrawable(getContext(),R.drawable.calculator)).getBitmap()};

    private int mCenterX;
    private int mCenterY;

    private OnSliceClickListener mOnSliceClickListener;
    private int mTouchSlop;

    private boolean mPressed;
    private float mLatestDownX;
    private float mLatestDownY;

    public interface OnSliceClickListener{
        void onSlickClick(int slicePosition);
    }

    public radialMenu(Context context){
        this(context, null);
    }

    public radialMenu(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public radialMenu(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);

        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mTouchSlop = viewConfiguration.getScaledTouchSlop();

        mPaint.setStrokeWidth(5);
    }

    public void setOnSliceClickListener(OnSliceClickListener onSliceClickListener){
        mOnSliceClickListener = onSliceClickListener;
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh){
        mCenterX = w / 2;
        mCenterY = h / 2;

        mOuterRadius = mCenterX > mCenterY ? mCenterY : mCenterX;
        mInnerRadius = mOuterRadius * innerRadiusRatio;

        outerRadiusSquare = mOuterRadius * mOuterRadius;
        innerRadiusSquare = mInnerRadius * mInnerRadius;

        mSliceOval.left = mCenterX - mOuterRadius;
        mSliceOval.right = mCenterX + mOuterRadius;
        mSliceOval.top = mCenterY - mOuterRadius;
        mSliceOval.bottom = mCenterY + mOuterRadius;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        float currX = event.getX();
        float currY = event.getY();



        switch(event.getActionMasked()){

            case MotionEvent.ACTION_DOWN:
                mLatestDownX = currX;
                mLatestDownY = currY;
                mPressed = true;

                break;

            case MotionEvent.ACTION_MOVE:
                if(Math.abs(currX - mLatestDownX) > mTouchSlop || Math.abs(currY - mLatestDownY) > mTouchSlop) mPressed = false;
                break;

            case MotionEvent.ACTION_UP:
                if(mPressed){
                    int dx = (int) currX - mCenterX;
                    int dy = (int) currY - mCenterY;
                    int distanceSquare = dx * dx + dy * dy;

                    //if the distance between touchpoint and centerpoint is smaller than outerRadius and longer than innerRadius, then we're in the clickable area
                    if(distanceSquare > innerRadiusSquare && distanceSquare < outerRadiusSquare){

                        //get the angle to detect which slice is currently being click
                        double angle = Math.atan2(dy, dx);

                        Log.d("Angle", String.valueOf(angle));

                        if(angle > -1.57 && angle < 0){
                            Toast.makeText(getContext(), "Inventory", Toast.LENGTH_SHORT).show();
                        }else if(angle > 1.57 && angle < 3.14){
                            Toast.makeText(getContext(), "Networking", Toast.LENGTH_SHORT).show();
                        }else if(angle > 0 && angle < 1.57){
                            Toast.makeText(getContext(), "Finances", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(), "Accounts", Toast.LENGTH_SHORT).show();
                        }
                        double rawSliceIndex = angle / (Math.PI * 2) * mSlices;
                        if(mOnSliceClickListener != null){
                            mOnSliceClickListener.onSlickClick((int) rawSliceIndex);
                        }

                    }
                }
                break;
        }
        return true;
    }

    @Override
    public void onDraw(Canvas canvas){
        int startAngle = quarterDegreeMinus;

        float[] x = new float[]{canvas.getWidth()*3/4,canvas.getWidth()*3/4,canvas.getWidth()/4,canvas.getWidth()/4};
        float[] y = new float[]{canvas.getHeight()/4,canvas.getHeight()*3/4,canvas.getHeight()*3/4,canvas.getHeight()/4};

        //draw slice
        for(int i = 0; i < mSlices; i++){
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(colors[i]);
            canvas.drawArc(mSliceOval, startAngle, degreeStep, true, mPaint);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(Color.WHITE);
            canvas.drawArc(mSliceOval, startAngle, degreeStep, true, mPaint);
            canvas.drawBitmap(bitmaps[i],
                    x[i]-bitmaps[i].getWidth()/2.5f,
                    y[i]-bitmaps[i].getHeight()/2.5f,
                    mPaint);
            startAngle += degreeStep;
        }

        //draw center circle
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(mCenterX, mCenterY, mInnerRadius, mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(mCenterX, mCenterY, mInnerRadius, mPaint);
    }
}