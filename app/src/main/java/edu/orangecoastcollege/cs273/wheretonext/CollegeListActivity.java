package edu.orangecoastcollege.cs273.wheretonext;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

import java.util.List;

/**
 * Creates a list of colleges and holds UI allowing the user to add additional colleges to the list
 */
public class CollegeListActivity extends AppCompatActivity {

    private DBHelper db;
    private List<College> collegesList;
    private CollegeListAdapter collegesListAdapter;
    private ListView collegesListView;

    /**
     * Called on loading of the activity, instantiates the {@link DBHelper}
     * connects the list to the database
     * Connects variables to respective views in layout
     * attaches list adapter wto list view
     * feeds list to list adapter
     *
     * @param savedInstanceState Bundle of data if the layout has previously been run
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_list);

//        this.deleteDatabase(DBHelper.DATABASE_NAME);
        db = new DBHelper(this);

        // TODO: Comment this section out once the colleges below have been added to the database,
        // TODO: so they are not added multiple times (prevent duplicate entries)
//        db.addCollege(new College("UC Berkeley", 42082, 14068, 4.7f, "ucb.png"));
//        db.addCollege(new College("UC Irvine", 31551, 15026.47, 4.3f, "uci.png"));
//        db.addCollege(new College("UC Los Angeles", 43301, 25308, 4.5f, "ucla.png"));
//        db.addCollege(new College("UC San Diego", 33735, 20212, 4.4f, "ucsd.png"));
//        db.addCollege(new College("CSU Fullerton", 38948, 6437, 4.5f, "csuf.png"));
//        db.addCollege(new College("CSU Long Beach", 37430, 6452, 4.4f, "csulb.png"));

        // TODO:  Fill the collegesList with all Colleges from the database
        collegesList = db.getAllColleges();
        // TODO:  Connect the list adapter with the list
        collegesListAdapter = new CollegeListAdapter(this, R.layout.college_list_item, collegesList);
        // TODO:  Set the list view to use the list adapter
        collegesListView = (ListView) findViewById(R.id.collegeListView);
        collegesListView.setAdapter(collegesListAdapter);
    }

    /**
     * Is called on click of a item in the list
     * Shows more detail about the {@link College}
     * @param view Item which was clicked in the list
     */
    public void viewCollegeDetails(View view) {
        // TODO: Implement the view college details using an Intent
        Intent intent = new Intent(this, CollegeDetailsActivity.class);
        LinearLayout selectedItem = (LinearLayout) view;
        College selectedCollege = (College) selectedItem.getTag();

        intent.putExtra("Name", selectedCollege.getName());
        intent.putExtra("Population", selectedCollege.getPopulation());
        intent.putExtra("Tuition", selectedCollege.getTuition());
        intent.putExtra("Rating", selectedCollege.getRating());
        intent.putExtra("ImageName", selectedCollege.getImageName());
        startActivity(intent);
    }

    /**
     * Adds a {@link College} to the list from data given in the various views
     *
     * @param view Add College button
     */
    public void addCollege(View view) {
        // TODO: Implement the code for when the user clicks on the addCollegeButton
        EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
        EditText populationEditText = (EditText) findViewById(R.id.populationEditText);
        EditText tuitionEditText = (EditText) findViewById(R.id.tuitionEditText);
        RatingBar collegeRatingBar = (RatingBar)findViewById(R.id.collegeRatingBar);
        College newCollege = new College(
                nameEditText.getText().toString(),
                Integer.valueOf(populationEditText.getText().toString()),
                Double.valueOf(tuitionEditText.getText().toString()),
                collegeRatingBar.getRating());
        db.addCollege(newCollege);
        collegesList.add(newCollege);
        collegesListAdapter.notifyDataSetChanged();

        nameEditText.setText("");
        populationEditText.setText("");
        tuitionEditText.setText("");
        collegeRatingBar.setRating(0.0f);

    }

}
