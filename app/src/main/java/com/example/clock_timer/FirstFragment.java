package com.example.clock_timer;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import android.os.Handler;
import android.widget.TextView;

import com.example.clock_timer.R;

import java.time.*;


public class FirstFragment extends Fragment
{
    TextView textview;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    )
    {
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getCurrentTime(view);
        blink();

        view.findViewById(R.id.button_timer).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                FirstFragmentDirections.ActionFirstFragmentToSecondFragment action =
                        FirstFragmentDirections.
                                actionFirstFragmentToSecondFragment("From FirstFragment");
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(action);
            }
        });

    }

    private void blink()
    {
        final Handler hander = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(550);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                hander.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if(textview.getVisibility() == View.VISIBLE)
                        {
                            textview.setVisibility(View.INVISIBLE);
                        } else
                        {
                            textview.setVisibility(View.VISIBLE);
                        }
                        blink();
                    }
                });
            }
        }).start();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getCurrentTime(final View view)
    {
        final String[] hours = {""};
        textview = view.findViewById(R.id.time2);

        final LocalDateTime current = LocalDateTime.now();

        final int[] hour = new int[1];
        hour[0] = current.getHour();
        final int minute;
        minute = current.getMinute();
        String sMinute = Integer.toString(minute);
        if(sMinute.length() == 1)
        {
            sMinute = "0" + Integer.toString(minute);
        }

        final Handler hander = new Handler();
        final String finalSMinute = sMinute;
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try {
                    Thread.sleep(550);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                hander.post(new Runnable()
                {
                    @Override
                    public void run()
                    {

                        if(hour[0] > 12)
                        {
                            hours[0] = "PM";
                            hour[0] = hour[0] - 12;
                        }
                        else
                        {
                            hours[0] = "AM";
                        }
                        textview.setText( hour[0] + ":" + finalSMinute + " " + hours[0]);
                        getCurrentTime(view);
                    }
                });
            }
        }).start();
    }
}