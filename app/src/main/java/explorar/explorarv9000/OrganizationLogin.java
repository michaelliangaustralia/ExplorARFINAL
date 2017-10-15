package explorar.explorarv9000;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import model.Organization;

/**
 * Created by carregliu on 29/09/2017.
 */

public class OrganizationLogin extends MainActivity implements View.OnClickListener {
    DbCreation helper = new DbCreation(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organiser_login);

        Button login = (Button) findViewById(R.id.organiser_login_button);
        login.setOnClickListener(this); // calling onClick() method
        Button signup = (Button) findViewById(R.id.organiser_login_sign_up);
        signup.setOnClickListener(this); // calling onClick() method

    }


    public void onClick(View v) {

        EditText a = (EditText) findViewById(R.id.organiser_login);
        String email = a.getText().toString();
        EditText b = (EditText) findViewById(R.id.organiser_password);
        String pass = b.getText().toString();

        if (v.getId() == (R.id.organiser_login_button)) {
            if (helper.searchoPassword(email, pass)) {
                Toast.makeText(OrganizationLogin.this,
                        "Successfully Logged In", Toast.LENGTH_LONG)
                        .show();
                Intent i = new Intent(OrganizationLogin.this, OrganizerHome.class);
                startActivity(i);
            } else {
                Toast.makeText(OrganizationLogin.this,
                        "Invalid username or password",
                        Toast.LENGTH_LONG).show();
            }

        }

        if (v.getId() == (R.id.organiser_login_sign_up)) {
                Intent i = new Intent(OrganizationLogin.this, OrganizationSignup.class);
                startActivity(i);
            }

        }


    }
