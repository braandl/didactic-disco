package groovey.didactic.disco.org.didacticdisco;

import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import groovey.didactic.disco.org.didacticdisco.fragments.GameFragment;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        android.app.FragmentManager.enableDebugLogging(false);
        /*transaction.setCustomAnimations(R.anim.card_slide_in, 0, 0,
                                        R.anim.card_slide_out);*/
        transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        transaction.replace(R.id.content_frame, GameFragment.getInstance());
        transaction.commit();
    }
}
