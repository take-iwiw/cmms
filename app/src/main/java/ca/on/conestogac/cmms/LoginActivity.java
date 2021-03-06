package ca.on.conestogac.cmms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity {

    Button buttonLoginLogin;
    EditText userID, password;
    public int integerAccessLevel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // disable unnecessary menu in Login screen
        MenuItem item = menu.findItem(R.id.action_back);
        item.setVisible(false);
        item = menu.findItem(R.id.action_home);
        item.setVisible(false);
        return true;
    }


    public void onClickLogin(View view) {
        userID= (EditText) findViewById(R.id.userID);
        password= (EditText) findViewById(R.id.password);
        buttonLoginLogin= (Button) findViewById(R.id.buttonLoginLogin);

        User.getInstance().userID = userID.getText().toString();
        User.getInstance().password = password.getText().toString();

        JSONObject jsonParam = new JSONObject();
        try{
            jsonParam.put("userID", User.getInstance().userID);
            jsonParam.put("password", User.getInstance().password);
        } catch (JSONException e) {
            Utility.logDebug(e.getMessage());
        }
        callAPI("Login", jsonParam);
    }

    @Override
    void onAPIResponse(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            String result = jsonObject.getString("result");
            if (result.compareTo(ValueConstants.RET_OK) != 0 ) {
                // do something if needed when error happens
            } else {
                if(jsonObject.has("accessLevel")){
                    try {
                        integerAccessLevel = jsonObject.getInt("accessLevel");
                        User.getInstance().accessLevel = integerAccessLevel;
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                    catch(JSONException e){
                        Utility.logError(e.getMessage());
                    }
                }
            }
        } catch (JSONException e) {
            Utility.logError(e.getMessage());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
