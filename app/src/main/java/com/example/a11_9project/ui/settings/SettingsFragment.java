package com.example.a11_9project.ui.settings;

import static android.content.Context.ACCOUNT_SERVICE;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.a11_9project.MainActivity;
import com.example.a11_9project.R;
import com.example.a11_9project.databinding.FragmentCalendarBinding;
import com.example.a11_9project.databinding.FragmentSettingsBinding;
import com.example.a11_9project.databinding.NavHeaderMainBinding;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.firebase.auth.FirebaseUser;

import java.io.InputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;

public class SettingsFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener {

        private static final String TAG = "GoogleFragment";
        private int RC_SIGN_IN = (int) (Math.random()*100);
        //private GoogleApiClient mGoogleApiClient;
        private SignInButton signInButton;
        private Button signOutButton;
        private Button disconnectButton;
        private LinearLayout signOutView;
        private TextView mStatusTextView, mName, mEmail;
        private ProgressDialog mProgressDialog;
        private ImageView imgProfilePic;
        private NavHeaderMainBinding binding;
        TextView nameView, emailView;
        private MutableLiveData<String> mText, eText;
        private GoogleSignInClient mGoogleSignInClient;

        private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
        private static final String TOKENS_DIRECTORY_PATH = "tokens";

        /**
        * Global instance of the scopes required by this quickstart.
        * If modifying these scopes, delete your previously saved tokens/ folder.
        */
        private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR_READONLY);
        private static final String CREDENTIALS_FILE_PATH = "/credentials.json";






    @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);

            //NavigationView navigationView = getActivity().findViewById(R.id.nav_view);


            // Configure sign-in to request the user's ID, email address, and basic
            // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();

            // Build a GoogleApiClient with access to the Google Sign-In API and the
            // options specified by gso.
            mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
            //mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
            //        .enableAutoManage(getActivity() /* FragmentActivity */, this /* OnConnectionFailedListener */)
            //        .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
            //        .build();

            /**


            AccountManager manager = AccountManager.get(this.getContext());
            Account[] accounts = manager.getAccountsByType("com.google");
            List<String> possibleEmails = new LinkedList<String>();

            for (Account account : accounts) {
                // TODO: Check possibleEmail against an email regex or treat
                // account.name as an email address only for certain account.type
                // values.
                possibleEmails.add(account.name);
            }

            if (!possibleEmails.isEmpty() && possibleEmails.get(0) != null) {
                String email = possibleEmails.get(0);
                String[] parts = email.split("@");
                if (parts.length > 0 && parts[0] != null) {

                }
            }

            AccountManager manager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
            Account[] list = manager.getAccounts();
            String gmail = null;

            for(Account account: list)
            {
                if(account.type.equalsIgnoreCase("com.google"))
                {
                    gmail = account.name;
                    break;
                }
            }*/










        }

        public String getMailId() {
            String strGmail = null;
            try {
                Account[] accounts = AccountManager.get(this.getContext()).getAccounts();
                Log.e("PIKLOG", "Size: " + accounts.length);
                for (Account account : accounts) {

                    String possibleEmail = account.name;
                    String type = account.type;

                    if (type.equals("com.google")) {

                        strGmail = possibleEmail;
                        Log.e("PIKLOG", "Emails: " + strGmail);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                strGmail = null;
            }

            return strGmail;
        }

        public String getUsername() {
            List<String> possibleEmails = null;
            try {
                AccountManager manager = AccountManager.get(this.getContext());
                Account[] accounts = manager.getAccountsByType("com.google");
                possibleEmails = new LinkedList<>();

                for (Account account : accounts) {
                    // TODO: Check possibleEmail against an email regex or treat
                    // account.name as an email address only for certain account.type
                    // values.
                    possibleEmails.add(account.name);
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (possibleEmails != null) {
                    possibleEmails.clear();
                }
            }

            if (possibleEmails != null) {
                if (!possibleEmails.isEmpty() && possibleEmails.get(0) != null) {
                    String email = possibleEmails.get(0);
                    String[] parts = email.split("@");
                    if (parts.length > 0 && parts[0] != null) {
                        return parts[0];

                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }


        @Override
        public void onStart() {
            super.onStart();

            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());
            updateUI(account);
            /*

            OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
            if (opr.isDone()) {
                // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
                // and the GoogleSignInResult will be available instantly.
                Log.d(TAG, "Got cached sign-in");
                GoogleSignInResult result = opr.get();
                handleSignInResult(result);
            } else {
                // If the user has not previously signed in on this device or the sign-in has expired,
                // this asynchronous branch will attempt to sign in the user silently.  Cross-device
                // single sign-on will occur in this branch.
                showProgressDialog();
                opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                    @Override
                    public void onResult(GoogleSignInResult googleSignInResult) {
                        hideProgressDialog();
                        handleSignInResult(googleSignInResult);
                    }
                });
            }

             */


        }

        @Override
        public void onPause() {
            super.onPause();
            //mGoogleApiClient.stopAutoManage(getActivity());
            //mGoogleApiClient.disconnect();
        }


        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_settings, parent, false);
            NavigationView navigationView = getActivity().findViewById(R.id.nav_view);

            signInButton = (SignInButton) v.findViewById(R.id.sign_in_button);
            signOutButton = (Button) v.findViewById(R.id.sign_out_button);
            //imgProfilePic = (ImageView) v.findViewById(R.id.img_profile_pic);

            /*
            mStatusTextView = (TextView) v.findViewById(R.id.status);
            Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.user_default);
            imgProfilePic.setImageBitmap(ImageHelper.getRoundedCornerBitmap(getContext(),icon, 200, 200, 200, false, false, false, false));

            */
            View headView = navigationView.getHeaderView(0);
            TextView emailView = headView.findViewById(R.id.textView);
            TextView nameView = headView.findViewById(R.id.PersonName);
            mText = new MutableLiveData<>();
            eText = new MutableLiveData<>();
            mText.observe(getViewLifecycleOwner(), new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    nameView.setText(s);
                }
            });


            eText.observe( getViewLifecycleOwner(), new Observer<String>() {
                @Override
                public void onChanged(@Nullable String s) {
                    emailView.setText(s);
                }
            });
            mText.setValue("Sign in with Google");
            eText.setValue("In the Settings Tab");

            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*
                    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                    startActivityForResult(signInIntent, RC_SIGN_IN);

                     */
                    switch (v.getId()) {
                        case R.id.sign_in_button:
                            signIn();
                            break;
                    }
                }

            });


            signOutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.sign_out_button:
                            signOut();
                            break;
                    }

                    /*
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                            new ResultCallback<Status>() {
                                @Override
                                public void onResult(Status status) {
                                    updateUI(false);
                                }
                            });

                     */
                }
            });





            return v;
        }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mText.setValue("Sign in with Google");
                        eText.setValue("In the Settings tab");
                        updateUI(null);
                    }
                });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }


        private void handleSignInResult(GoogleSignInResult result) {
            Log.d(TAG, "handleSignInResult:" + result.isSuccess());
            if (result.isSuccess()) {
                // Signed in successfully, show authenticated UI.
                GoogleSignInAccount acct = result.getSignInAccount();
                mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
                //Similarly you can get the email and photourl using acct.getEmail() and  acct.getPhotoUrl()
                mText.setValue(acct.getGivenName());
                eText.setValue(acct.getEmail());
                //if(acct.getPhotoUrl() != null)
                    //new LoadProfileImage(imgProfilePic).execute(acct.getPhotoUrl().toString());

                updateUI(true);
            } else {
                // Signed out, show unauthenticated UI.
                updateUI(false);
            }
        }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }




        private void updateUI(boolean signedIn) {
            if (signedIn) {
                signInButton.setVisibility(View.GONE);
                signOutButton.setVisibility(View.VISIBLE);
                //nameView.setText(getUsername());
                //emailView.setText(getMailId());
            } else {
                //mStatusTextView.setText(R.string.signed_out);
                //Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.user_default);
                //imgProfilePic.setImageBitmap(ImageHelper.getRoundedCornerBitmap(getContext(),icon, 200, 200, 200, false, false, false, false));
                signInButton.setVisibility(View.VISIBLE);
                signOutButton.setVisibility(View.GONE);
                //nameView.setText("Sign in to Google");
                //emailView.setText("in the Settings Tab");
            }
        }

        private void updateUI(GoogleSignInAccount acct) {
            if (acct != null) {
                signInButton.setVisibility(View.GONE);
                signOutButton.setVisibility(View.VISIBLE);
                mText.setValue(acct.getDisplayName());
                eText.setValue(acct.getEmail());
            } else {
                //mStatusTextView.setText(R.string.signed_out);
                //Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.user_default);
                //imgProfilePic.setImageBitmap(ImageHelper.getRoundedCornerBitmap(getContext(),icon, 200, 200, 200, false, false, false, false));
                signInButton.setVisibility(View.VISIBLE);
                signOutButton.setVisibility(View.GONE);
                mText.setValue("Sign in with Google");
                eText.setValue("In the Settings Tab");
            }
        }



        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {
            // An unresolvable error has occurred and Google APIs (including Sign-In) will not
            // be available.
            Log.d(TAG, "onConnectionFailed:" + connectionResult);
        }

        private void showProgressDialog() {
            if (mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(getActivity());
                mProgressDialog.setMessage(getString(R.string.loading));
                mProgressDialog.setIndeterminate(true);
            }

            mProgressDialog.show();
        }

        private void hideProgressDialog() {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.hide();
            }

        }


        /**
         * Background Async task to load user profile picture from url
         * */
        private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
            ImageView bmImage;

            public LoadProfileImage(ImageView bmImage) {
                this.bmImage = bmImage;
            }

            protected Bitmap doInBackground(String... uri) {
                String url = uri[0];
                Bitmap mIcon11 = null;
                try {
                    InputStream in = new java.net.URL(url).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                return mIcon11;
            }

            protected void onPostExecute(Bitmap result) {

                if (result != null) {


                    //Bitmap resized = Bitmap.createScaledBitmap(result,200,200, true);
                    //bmImage.setImageBitmap(ImageHelper.getRoundedCornerBitmap(getContext(),resized,250,200,200, false, false, false, false));

                }
            }
        }



    /*

    private com.example.a11_9project.ui.settings.SettingsViewModel SettingsViewModel;
    private FragmentSettingsBinding binding;
    // Reference to the activity
    private SettingsFragmentListener callBack;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleApiClient googleApiClient;
    SignInButton signInButton;
    GoogleSignInOptions gso;

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public interface SettingsFragmentListener {
        public void signIn();


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);
        //GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(SettingsViewModel);
        //updateUI(account);


        // Build a GoogleSignInClient with the options specified by gso.
        //mGoogleSignInClient = GoogleSignIn.getClient(SettingsViewModel, gso);
    }


    // Inflate the view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("SettingsFragment", "in onCreateView");

        return inflater.inflate(R.layout.fragment_settings, container, false);
    }


    @Override
    public void onAttach(Activity activity) {
        Log.d("HomeFragment", "in onAttach");
        super.onAttach(activity);

        // Check to make sure the activity implements the listener interface.
        try {
            callBack = (SettingsFragmentListener) activity;
        } catch (ClassCastException e) {
            Log.d("HomeFragment", String.format("%s does not implement HomeFragmentListener",activity));
        }
    }

    // Register view listener
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d("HomeFragment", "in onViewCreated");
        super.onViewCreated(view, savedInstanceState);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = com.google.android.gms.auth.api.signin.GoogleSignIn.getClient(SettingsViewModel, gso);

        // This fragment implements on click listener, so just set to this.
        signInButton = view.findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                        startActivityForResult(signInIntent, RC_SIGN_IN);
                        break;
                    default:
                        break;
                }
            }
        });


    }

    private void updateUI(GoogleSignInAccount user) {

    }

     */


    /*
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel =
                new ViewModelProvider(this).get(com.example.a11_9project.ui.settings.SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SignInButton signInButton = getActivity().findViewById(R.id.sign_in_button).setOnClickListener(this);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        final TextView textView = binding.textSettings;
        SettingsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            // ...
    }

     */
}