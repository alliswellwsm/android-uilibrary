package android.alliswell.wsm.uilibrarydemo;

import android.alliswell.wsm.uilibrary.activity.BaseToobarActivity;
import android.os.Bundle;

public class MainActivity extends BaseToobarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setToobarTitle(R.string.app_name);
    }
}
