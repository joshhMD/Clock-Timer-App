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

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import android.os.SystemClock;

import com.example.clock_timer.R;

import java.util.Timer;
import java.util.TimerTask;


public class SecondFragment extends Fragment {
    TextView textView;
    TextView timer;
    Button start, pause, reset;
    long time, startTime, timeBuffer, updateTime = 0L;
    Handler handler;
    int seconds, minutes, milliseconds;
    int c = 0;

    String minuteString;
    String secondString;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    )
    {
        View fragmentSecondLayout = inflater.inflate(R.layout.fragment_second, container, false);
        textView = fragmentSecondLayout.findViewById(R.id.real_timer);

        return fragmentSecondLayout;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        final Button button = view.findViewById(R.id.button_start);
        final boolean[] rg = {false};

        timer = (TextView)view.findViewById(R.id.real_timer);
        start = (Button)view.findViewById(R.id.button_start);
        pause = (Button)view.findViewById(R.id.button_start);
        reset = (Button)view.findViewById(R.id.button_reset);

        handler = new Handler();

        view.findViewById(R.id.button_back).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        start.findViewById(R.id.button_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(rg[0] == true)
                {
                    //this is when timer is paused
                    button.setBackgroundColor(getResources().getColor(R.color.greenGO));
                    button.setText("Start");
                    rg[0] = false;
                    Toast myToast = Toast.makeText(getActivity(), R.string.toastStop, Toast.LENGTH_SHORT);
                    myToast.show();
                    timerStop(view);

                }
                else if(rg[0] == false)
                {
                    //this is when timer is going
                    button.setBackgroundColor(getResources().getColor(R.color.redStop));
                    button.setText("Pause");
                    rg[0] = true;
                    Toast myToast = Toast.makeText(getActivity(), R.string.toastStart, Toast.LENGTH_SHORT);
                    myToast.show();

                    timerStart(view);
                }
            }
        });

        reset.findViewById(R.id.button_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                button.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                button.setText("Start");
                rg[0] = false;
                Toast myToast = Toast.makeText(getActivity(), R.string.toastReset, Toast.LENGTH_SHORT);
                myToast.show();
                timerReset(view);
            }
        });

    }

    private void timerStart(View view)
    {
        startTime = SystemClock.uptimeMillis();
        handler.postDelayed(runnable, 0);
        reset.setEnabled(false);
    }

    private void timerStop(View view)
    {
        timeBuffer += time;
        handler.removeCallbacks(runnable);
        reset.setEnabled(true);
    }

    private void timerReset(View view)
    {
        time = 0L;
        startTime = 0L;
        timeBuffer = 0L;
        updateTime = 0L;
        seconds = 0;
        minutes = 0;
        milliseconds = 0;

        timer.setText("00:00:000");
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            time = SystemClock.uptimeMillis() - startTime;
            updateTime = timeBuffer + time;
            seconds = (int) (updateTime / 1000);
            minutes = seconds / 60;
            seconds = seconds % 60;
            milliseconds = (int) (updateTime % 1000);

            if(minutes < 10)
            {
                minuteString = "0" + Integer.toString(minutes);
            }
            else
            {
                minuteString = Integer.toString(minutes);
            }

            timer.setText("" + minuteString + ":" + String.format("%02d", seconds) + ":" + String.format("%03d", milliseconds));
            handler.postDelayed(this, 0);
        }
    };
}