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
public class AdapterTable extends ArrayAdapter<JSONObject> {
    private final Context context;
    private List<JSONObject> mGames = new ArrayList<JSONObject>();

    public AdapterTable(Context context, List<JSONObject> games) {
        super(context, R.layout.list_team_table, games);
        this.context = context;
        this.mGames = games;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_team_table, parent, false);

        TextView name = (TextView) rowView.findViewById(R.id.name);
        TextView games = (TextView) rowView.findViewById(R.id.games);
        TextView goals = (TextView) rowView.findViewById(R.id.goals);
        TextView wins = (TextView) rowView.findViewById(R.id.wins);
        TextView points = (TextView) rowView.findViewById(R.id.points);

        try {
            name.setText(mGames.get(position).getString("name"));
            games.setText(mGames.get(position).getString("games"));
            goals.setText(mGames.get(position).getString("goals"));
            wins.setText(mGames.get(position).getString("wins"));
            points.setText(mGames.get(position).getString("points"));
        } catch (Exception e) {

        }

        return rowView;
    }
}
