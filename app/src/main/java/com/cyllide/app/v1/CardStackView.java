package com.cyllide.app.v1;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class CardStackView extends Fragment {
    private static float CARDS_SWIPE_LENGTH = 250;
    private float originalX = 0;
    private float originalY = 0;
    private float startMoveX = 0;
    private float startMoveY = 0;

    public CardStackView() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.game_card_nifty50, container, false);

        rootView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                final float X = event.getRawX();
                final float Y =  event.getRawY();
                float deltaX = X - startMoveX;
                float deltaY = Y - startMoveY;
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        startMoveX = X;
                        startMoveY = Y;
                        break;
                    case MotionEvent.ACTION_UP:

                        container.getBackground().setColorFilter(ContextCompat.getColor(container.getContext(),R.color.colorPrimary), PorterDuff.Mode.DST);
                        if ( Math.abs(deltaY) < CARDS_SWIPE_LENGTH ) {
                            rootView.setX(originalX);
                            rootView.setY(originalY);
                        } else if ( deltaY > 0 ) {
                            onCardSwipeDown();
                        } else {
                            onCardSwipeUp();
                        }
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int newColor = 0;
                        if ( deltaY < 0 ) {
                            int rb = (int)(255+deltaY/10);
                            newColor = Color.argb(170, rb, 255, rb);
                        } else {
                            int gb = (int)(255-deltaY/10);
                            newColor = Color.argb(170, 255, gb, gb);
                        }
                        rootView.getBackground().setColorFilter(newColor, PorterDuff.Mode.DARKEN);
                        rootView.setTranslationY(deltaY);
                        break;
                }
                rootView.invalidate();
                return true;
            }
        });
        return rootView;
    }

    protected void onCardSwipeUp() {
        Log.i("CardStackView", "Swiped Up");
    }

    protected void onCardSwipeDown() {
        Log.i("CardStackView", "Swiped Down");
    }

}
