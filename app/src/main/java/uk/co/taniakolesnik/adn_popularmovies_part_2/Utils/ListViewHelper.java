package uk.co.taniakolesnik.adn_popularmovies_part_2.Utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by tetianakolesnik on 29/07/2018.
 */

public class ListViewHelper {

    //https://stackoverflow.com/questions/29225539/set-listview-height-dynamically-based-on-multiline-textview-inside-it
    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter adapter = listView.getAdapter();
        if (adapter != null) {

            int numberOfItems = adapter.getCount();

            int totalItemsHeight = 0;
            for (int i = 0; i < numberOfItems; i++) {
                View item = adapter.getView(i, null, listView);
                item.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                totalItemsHeight += item.getMeasuredHeight();
            }

            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);
            int totalPadding = listView.getPaddingTop() + listView.getPaddingBottom();

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight + totalPadding;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }
}


