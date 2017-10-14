package explorar.explorarv9000;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by carregliu on 29/09/2017.
 */

public class OrganizerHome extends OrganizationLogin {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organiser_home);
        String name = getIntent().getStringExtra("Username");
        TextView tv = (TextView)findViewById(R.id.organiser_login);
        tv.setText(name);
    }
    public void onButtonClick(View v) {
        if(v.getId() == (R.id.organiser_home_create_event_button));
        Intent i = new Intent(OrganizerHome.this, CreateEvent.class);
        startActivity(i);
    }
//    public void onButton1Click(View v) {
//        if(v.getId() == (R.id.organiser_home_list_event_button));
//        Intent i = new Intent(OrganizerHome.this, CustomList.class);
//        startActivity(i);
//    }
}
