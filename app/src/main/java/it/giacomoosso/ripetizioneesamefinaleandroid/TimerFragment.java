package it.giacomoosso.ripetizioneesamefinaleandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class TimerFragment extends Fragment {
    private static final String TAG = "timerFrag";

    private OnTimeUpdate mListener;
    public interface OnTimeUpdate {
        void onTimeUpdate(String value);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mListener.onTimeUpdate(intent.getStringExtra(TAG));
        }

    };

    private MyAsyncTimer mMyAsyncTimer;

    public TimerFragment() {
        // Required empty public constructor
    }
    public static TimerFragment getInstance(){
        return new TimerFragment();
    }
    public static TimerFragment newInstance() {
        TimerFragment fragment = new TimerFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mReceiver, new IntentFilter("TIMER"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    //fragment senza interfaccia grafica
        return null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTimeUpdate) {
            mListener = (OnTimeUpdate) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void startTimer(){
        if(mMyAsyncTimer==null || mMyAsyncTimer.isCancelled()){
            mMyAsyncTimer = new MyAsyncTimer();
            mMyAsyncTimer.execute();
        }
    }

    public void stopTimer(){
        if(!mMyAsyncTimer.isCancelled()) {
            mMyAsyncTimer.cancel(true);
        }
    }


    private class MyAsyncTimer extends AsyncTask<Void, Integer, String> {

        @Override
        protected String doInBackground(Void... params) {
            int vIndex = 0;

            while (!isCancelled()) {
                //viene chiamato ogni secondo
                publishProgress(vIndex);
                vIndex++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException aE) {
                    aE.printStackTrace();
                }
            }
            return "Finish";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            int tempValue = values[0];
            int hour = tempValue / 3600;
            tempValue = (tempValue % 3600);
            int minutes = tempValue /60;
            tempValue = (tempValue % 60);

            //lancia un intent locale contente la nuova stringa ovvero il tempo corretto
            LocalBroadcastManager.getInstance(getContext()).sendBroadcast(new Intent("TIMER").putExtra(TAG, "" + hour + ":" + minutes + ":" + tempValue));

//            onTimerValue("" + hour + ":" + minutes + ":" + tempValue);

            Log.d(TAG, "onProgressUpdate: " + values[0]);
        }

    }

}
