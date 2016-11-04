package groovey.didactic.disco.org.didacticdisco;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import groovey.didactic.disco.org.didacticdisco.fragments.SignInFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        android.app.FragmentManager.enableDebugLogging(false);
        /*transaction.setCustomAnimations(R.anim.card_slide_in, 0, 0,
                                        R.anim.card_slide_out);*/
        transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        transaction.replace(R.id.content_frame, SignInFragment.getInstance());
        transaction.commit();
    }
}
