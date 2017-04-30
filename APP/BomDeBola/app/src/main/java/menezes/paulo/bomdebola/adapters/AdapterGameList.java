package menezes.paulo.bomdebola.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import menezes.paulo.bomdebola.R;
import menezes.paulo.bomdebola.models.Game;

/**
 * Created by Paulo Menezes on 24/07/2015.
 */
public class AdapterGameList extends ArrayAdapter<Game> {
    private final Context context;
    private List<Game> mGames = new ArrayList<Game>();

    public AdapterGameList(Context context, List<Game> games) {
        super(context, R.layout.list_games, games);
        this.context = context;
        this.mGames = games;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_games, parent, false);

        return rowView;
    }
}
