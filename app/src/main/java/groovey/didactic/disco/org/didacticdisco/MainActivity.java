package groovey.didactic.disco.org.didacticdisco;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import groovey.didactic.disco.org.didacticdisco.fragments.GameFragment;
import groovey.didactic.disco.org.didacticdisco.fragments.SignInFragment;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        android.app.FragmentManager.enableDebugLogging(false);
        /*transaction.setCustomAnimations(R.anim.card_slide_in, 0, 0,
                                        R.anim.card_slide_out);*/
        transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        transaction.replace(R.id.content_frame, SignInFragment.getInstance());
        transaction.commit();
    }
}
