package com.example.recyclerview;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS=new int[]{android.R.attr.listDivider};
    public static final int HORIZONTAL_LIST=LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST=LinearLayoutManager.VERTICAL;
    private Drawable Divider;
    private int Orientation;

    public DividerItemDecoration(Context context,int orientation) {
        final TypedArray array=context.obtainStyledAttributes(ATTRS);
        Divider=array.getDrawable(0);
        array.recycle();
        setOrientation(orientation);
    }

    private void setOrientation(int orientation) {
        if (orientation!=HORIZONTAL_LIST&&orientation!=VERTICAL_LIST){
            throw new IllegalArgumentException("invalia orientation");
        }
        Orientation=orientation;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (Orientation==VERTICAL_LIST){
            drawVertical(c,parent);
        }else {
            drawHorizontal(c,parent);
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        final int left=parent.getPaddingLeft();
        final int right=parent.getWidth()-parent.getPaddingRight();
        final int childCount=parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child=parent.getChildAt(i);
            final RecyclerView.LayoutParams params= (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top=child.getBottom()+params.bottomMargin;
            final  int bottom=top+Divider.getIntrinsicHeight();
            Divider.setBounds(left,top,right,bottom);
            Divider.draw(c);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top=parent.getPaddingTop();
        final int bottom=parent.getHeight()-parent.getPaddingBottom();
        final int childCount=parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child=parent.getChildAt(i);
            final RecyclerView.LayoutParams params= (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left=child.getRight()+params.rightMargin;
            final  int right=left+Divider.getIntrinsicHeight();
            Divider.setBounds(left,top,right,bottom);
            Divider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (Orientation==VERTICAL_LIST){
            outRect.set(0,0,0,Divider.getIntrinsicHeight());
        }else {
            outRect.set(0,0,Divider.getIntrinsicWidth(),0);
        }
    }
}
