package project.iiitd.loginsample;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoginSignupActivity extends AppCompatActivity implements ToAndFro{

    private FragmentTransaction mFragmentTransaction;
    private FragmentManager mFragementManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

        Fragment login_fragment = new Login_fragment();
        this.setDefaultFragment(login_fragment);

    }

    private void setDefaultFragment(Fragment defaultFragment)
    {
        this.replaceFragment(defaultFragment);
    }

    public void replaceFragment(Fragment destFragment)
    {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.dynamic_fragment, destFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void ChangeFragment(Fragment toChange){
        replaceFragment(toChange);
    }
}
