package menezes.paulo.bomdebola.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import java.util.ArrayList;

import menezes.paulo.bomdebola.ChampionshipAddActivity;
import menezes.paulo.bomdebola.R;
import menezes.paulo.bomdebola.models.Team;

/**
 * Created by Paulo Menezes on 24/07/2015.
 */
public class AdapterListCheckbox extends ArrayAdapter<Team> {

    private ArrayList<Team> countryList;
    private Context mContext;

    public AdapterListCheckbox(Context context, int textViewResourceId,
                           ArrayList<Team> countryList) {
        super(context, textViewResourceId, countryList);
        this.countryList = new ArrayList<Team>();
        this.countryList.addAll(countryList);
        this.mContext = context;
    }

    private class ViewHolder {
        CheckBox name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        Log.v("ConvertView", String.valueOf(position));

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.list_checkbox, null);

            holder = new ViewHolder();
            holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
            holder.name.setText(countryList.get(position).getName());
            holder.name.setTag(countryList.get(position).getId());
            convertView.setTag(holder);

            holder.name.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    for (int i = 0; i < countryList.size(); i++) {
                        if (countryList.get(i).getName().equals(cb.getText())) {
                            ChampionshipAddActivity.selected.add(countryList.get(i).getId());
                            break;
                        }
                    }
                }
            });
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(countryList.get(position).getName());
        holder.name.setTag(countryList.get(position).getId());

        return convertView;
    }
}
