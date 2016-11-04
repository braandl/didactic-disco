package groovey.didactic.disco.org.didacticdisco.fragments;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import groovey.didactic.disco.org.didacticdisco.DiscoApplication;
import groovey.didactic.disco.org.didacticdisco.R;
import groovey.didactic.disco.org.didacticdisco.data.Session;
import groovey.didactic.disco.org.didacticdisco.services.LocationTrackerService;

public class SignInFragment extends DialogFragment implements TextView.OnEditorActionListener
{

    private static final int PERMISSION_REQUEST_FINE_LOCATION = 1;

    @BindView(R.id.username) TextView username;
    @Inject                  Session  session;

    public static SignInFragment getInstance()
    {
        return new SignInFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ((DiscoApplication) getActivity().getApplicationContext()).getAppComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        ButterKnife.bind(this, view);
        username.setOnEditorActionListener(this);

        return view;
    }

    @OnClick(R.id.action_start)
    void onClick()
    {
        verifyUsername();
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionID, KeyEvent key)
    {
        // Reset errors.
        username.setError(null);

        if (actionID == R.id.login || actionID == EditorInfo.IME_NULL)
        {
            verifyUsername();
            return true;
        }

        return false;
    }

    private void verifyUsername()
    {
        if (TextUtils.isEmpty(username.getText()))
        {// Check for a valid username.
            username.setError(getString(R.string.error_field_required));
            username.requestFocus();
        } else {
            session.set(R.string.key_username, username.getText().toString());
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
            transaction.replace(R.id.content_frame, GameFragment.getInstance());
            transaction.commit();
        }
    }


    private boolean hasLocationPermission() {
        return ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void getLocationPermissions() {
        requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_REQUEST_FINE_LOCATION);
    }

    @Override
    public void onRequestPermissionsResult(int code, @Nullable String permissions[], @Nullable int[] res) {
        switch (code) {
            case PERMISSION_REQUEST_FINE_LOCATION:
                getActivity().startService(new Intent(getActivity(), LocationTrackerService.class));
        }
    }
}
