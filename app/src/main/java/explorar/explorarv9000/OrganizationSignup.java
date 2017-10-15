package explorar.explorarv9000;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import model.Organization;

/**
 * Created by carregliu on 29/09/2017.
 */

public class OrganizationSignup extends OrganizationLogin {
    DbCreation helper = new DbCreation(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organiser_sign_up);
        Button creatacct = (Button)findViewById(R.id.organiser_sign_up_button);
        creatacct.setOnClickListener(this);
    }
    public void onClick(View v) {
        EditText organisation = (EditText) findViewById(R.id.organisation);
        EditText email = (EditText) findViewById(R.id.organiser_email);
        EditText password = (EditText) findViewById(R.id.organiser_password);
        String organisationstr = organisation.getText().toString();
        String emailstr = email.getText().toString();
        String passwordstr = password.getText().toString();
        if(v.getId() == (R.id.organiser_sign_up_button)) {
            if(organisationstr.equals("") || emailstr.equals("") || passwordstr.equals("")) {
                Toast.makeText(getApplicationContext(), "Fields Vaccant", Toast.LENGTH_LONG).show();
                return;
            } else {
                helper.insertOrganization(organisationstr, emailstr, passwordstr);
                Toast.makeText(getApplicationContext(), "Account Successfully Create", Toast.LENGTH_LONG).show();
                Intent i = new Intent(OrganizationSignup.this, OrganizationLogin.class);
                startActivity(i);
            }
        }
    }
}
