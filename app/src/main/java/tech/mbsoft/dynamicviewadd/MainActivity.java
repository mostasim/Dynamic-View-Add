package tech.mbsoft.dynamicviewadd;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //View will be added in this container
        LinearLayout container = findViewById(R.id.container);

        View[] childViews = new View[3]; //we can have multiple child views
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Adding multiple views
        for (int v = 0; v < childViews.length; v++) {
            childViews[v] = inflater.inflate(R.layout.model_year_list_item, null);
            TextView title = childViews[v].findViewById(R.id.year_title);
            title.setText("Year " + 201 + "" + v);
            ListView listView = childViews[v].findViewById(R.id.car_list_model_year);

            //Adding multiple Data Item for individual list view
            ArrayList<String> l = new ArrayList<>();
            for (int i = 0; i <= v + 5; i++) {
                l.add("Year 201" + i);
            }
            ArrayAdapter<String> arrayAdapter =
                    new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, l);

            listView.setAdapter(arrayAdapter);

            //Get the list view height by its total child item
            int list_height = getListViewHeight(listView);
            Log.d(TAG, "onCreate: l" + list_height);

            //Set the height of list view by layout params after set adapter
            ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, list_height);
            childViews[v].findViewById(R.id.car_list_model_year).setLayoutParams(layoutParams);
            container.addView(childViews[v]);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(MainActivity.this, "" + view.getId() + "Pos: " + i + " L" + l, Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    /**
     * @param list get the Height of ListView
     * @return height of the listView
     * @author Mostasim Billah
     * {@link} https://stackoverflow.com/questions/12411060/get-listview-height-after-setadapter
     */
    private int getListViewHeight(ListView list) {
        ListAdapter adapter = list.getAdapter();

        int listviewHeight = 0;

        list.measure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        listviewHeight = list.getMeasuredHeight() * adapter.getCount() + (adapter.getCount() * list.getDividerHeight());

        return listviewHeight;
    }
}
