package it.giacomoosso.ripetizioneesamefinaleandroid;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements TimerFragment.OnTimeUpdate , RisultatiFragment.OnFragmentInteractionListener{
    private static final String TAG = "Activity";
    private static final String FRAGMENT_RISULATI = "Fragment Risultati";
    private static final String FRAGMENT_TIMER = "Fragment Timer";

    FragmentManager mFragmentManager = getSupportFragmentManager();
    RisultatiFragment mRisultatiFragment;
    TimerFragment mTimerFragment;
    TextView mTextTimer;

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Timer");

        mTextTimer=(TextView) findViewById(R.id.txt_time);
        //creazione fragment

        mTimerFragment = (TimerFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TIMER);
        //gestione ciclo di vita , se esiste il fragment non lo ricreo
        if(mTimerFragment == null) {
            FragmentTransaction vTrans = mFragmentManager.beginTransaction();
            mTimerFragment = TimerFragment.getInstance();
            vTrans.add(mTimerFragment, FRAGMENT_TIMER);
            vTrans.commit();
        }


        Button mStart = (Button) findViewById(R.id.btn_start);
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimerFragment.startTimer();
            }
        });

        Button mStop = (Button) findViewById(R.id.btn_stop);
        mStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              mTimerFragment.stopTimer();
                mTextTimer.setText("00:00:00");
            }
        });
    }


    @Override
    public void onTimeUpdate(final String aValue) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextTimer.setText(aValue);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.risultati, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.risultati) {
            android.support.v4.app.Fragment f = mFragmentManager.findFragmentById(R.id.container);
            setTitle("Risultati");

            if(! (f instanceof RisultatiFragment)) {
                FragmentTransaction vTrans = mFragmentManager.beginTransaction();
                mRisultatiFragment = RisultatiFragment.getInstance();
                vTrans.replace(R.id.container, mRisultatiFragment, FRAGMENT_RISULATI);
                vTrans.addToBackStack("risultati");
                vTrans.commit();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        setTitle("Timer");
        super.onBackPressed();
    }
}
