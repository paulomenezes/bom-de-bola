package menezes.paulo.bomdebola.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import menezes.paulo.bomdebola.R;

/**
 * Created by Paulo Menezes on 24/07/2015.
 */
public class AdapterCast  extends ArrayAdapter<JSONObject> {
    private final Context context;
    private List<JSONObject> mGames = new ArrayList<JSONObject>();

    public AdapterCast(Context context, List<JSONObject> games) {
        super(context, R.layout.list_team_cast, games);
        this.context = context;
        this.mGames = games;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_team_cast, parent, false);

        TextView name = (TextView) rowView.findViewById(R.id.name);
        TextView city = (TextView) rowView.findViewById(R.id.city);
        TextView number = (TextView) rowView.findViewById(R.id.number);

        try {
            name.setText(mGames.get(position).getString("name"));
            city.setText(mGames.get(position).getString("city"));
            number.setText("Camisa " + mGames.get(position).getString("number"));
        } catch (Exception e) {

        }

        return rowView;
    }
}

