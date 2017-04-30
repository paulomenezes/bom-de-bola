package menezes.paulo.bomdebola.fragments.team;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.List;

import menezes.paulo.bomdebola.ProfileActivity;
import menezes.paulo.bomdebola.R;
import menezes.paulo.bomdebola.adapters.AdapterCast;
import menezes.paulo.bomdebola.listeners.JsonResponse;
import menezes.paulo.bomdebola.models.User;
import menezes.paulo.bomdebola.util.DownloadJsonAsyncTask;
import menezes.paulo.bomdebola.util.URLs;

/**
 * Created by Paulo Menezes on 24/07/2015.
 */
public class FragmentCast extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final LinearLayout rv = (LinearLayout) inflater.inflate(R.layout.fragment_team_cast, container, false);

        new DownloadJsonAsyncTask(new JsonResponse() {
            @Override
            public void response(final List<JSONObject> objectList) {
                if (objectList != null) {
                    ListView listView = (ListView) rv.findViewById(R.id.listView);
                    listView.setAdapter(new AdapterCast(getActivity(), objectList));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent i = new Intent(getActivity(), ProfileActivity.class);
                            User u = new User();
                            u.load(objectList.get(position));
                            i.putExtra("user", u);
                            startActivity(i);
                        }
                    });
                }
            }
        }).execute(URLs.API_TEAM_CAST + "");

        return rv;
    }
}