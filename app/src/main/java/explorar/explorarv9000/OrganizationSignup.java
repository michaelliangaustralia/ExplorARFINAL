package explorar.explorarv9000;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
    }
//    public void onSignUpClick(View v) {
//        if(v.getId() == (R.id.button2));
//        EditText organisation = (EditText)findViewById(R.id.organisation);
//        EditText email = (EditText)findViewById(R.id.organiser_email);
//        EditText password = (EditText)findViewById(R.id.organiser_password);
//        String organisationstr = organisation.getText().toString();
//        String emailstr = email.getText().toString();
//        String passwordstr = password.getText().toString();
//        Organization o = new Organization();
//        o.setoName(organisationstr);
//        o.setoEmail(emailstr);
//        o.setoPassword(passwordstr);
//        helper.insertOrganization(o);
//    }

}
