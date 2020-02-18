package com.bahaa.github.util;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
  private int space;
  private int span;
  private boolean isVertical;

  public SpacesItemDecoration(int space, int span, boolean isVertical) {
    this.space = space;
    this.span = span;
    this.isVertical = isVertical;
  }

  @Override
  public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
    outRect.left = space/2;
    outRect.right = space/2;
    outRect.bottom = space;

    // Add top margin only for the first item to avoid double space between items
    if (span == 1){
      if (parent.getChildLayoutPosition(view) == 0 && span == 1 && isVertical) {
        outRect.top = space;
      } else {
        outRect.top = 0;
      }
    }

    if (span > 1){
      if (parent.getChildLayoutPosition(view) <= (span - 1) && isVertical) {
        outRect.top = space;
      } else {
        outRect.top = 0;
      }
    }

  }

}