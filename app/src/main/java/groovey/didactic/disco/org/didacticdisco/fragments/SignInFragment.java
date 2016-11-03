package groovey.didactic.disco.org.didacticdisco.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import groovey.didactic.disco.org.didacticdisco.R;

public class SignInFragment extends Fragment
{
    public static SignInFragment getInstance() {
        return new SignInFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        StrictMode.allowThreadDiskWrites(); //TODO: StrictMode gone after dev!
        StrictMode.allowThreadDiskReads();
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }
}
