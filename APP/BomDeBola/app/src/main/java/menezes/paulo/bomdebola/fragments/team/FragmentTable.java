package menezes.paulo.bomdebola.fragments.team;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.List;

import menezes.paulo.bomdebola.R;
import menezes.paulo.bomdebola.adapters.AdapterTable;
import menezes.paulo.bomdebola.listeners.JsonResponse;
import menezes.paulo.bomdebola.util.DownloadJsonAsyncTask;
import menezes.paulo.bomdebola.util.URLs;

/**
 * Created by Paulo Menezes on 24/07/2015.
 */
public class FragmentTable extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final LinearLayout rv = (LinearLayout) inflater.inflate(R.layout.fragment_team_table, container, false);

        new DownloadJsonAsyncTask(new JsonResponse() {
            @Override
            public void response(List<JSONObject> objectList) {
                if (objectList != null) {
                    String[] idTeam = new String[objectList.size()];
                    Integer[] fields = new Integer[4];

                    try {
                        for (int i = 0; i < objectList.size(); i++) {
                            if (objectList.get(i).has("score01")) {
                                idTeam[0] = objectList.get(i).getString("idTeam01");
                                fields[0]++;

                                idTeam[1] = objectList.get(i).getString("idTeam02");
                                fields[1]++;
                            }
                        }
                    } catch (Exception e) {

                    }

                    ListView listView = (ListView) rv.findViewById(R.id.listView);
                    listView.setAdapter(new AdapterTable(getActivity(), objectList));
                }
            }
        }).execute(URLs.API_GAME_TABLE);

        return rv;
    }
}
