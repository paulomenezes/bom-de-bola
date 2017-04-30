package menezes.paulo.bomdebola.fragments.manager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.List;

import menezes.paulo.bomdebola.R;
import menezes.paulo.bomdebola.listeners.JsonResponse;
import menezes.paulo.bomdebola.util.DownloadJsonAsyncTask;
import menezes.paulo.bomdebola.util.URLs;

/**
 * Created by Paulo Menezes on 24/07/2015.
 */
public class FragmentMyChampionships extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_manager_championship, container, false);
        final ListView rv = (ListView) linearLayout.findViewById(R.id.listView);

        new DownloadJsonAsyncTask(new JsonResponse() {
            @Override
            public void response(List<JSONObject> objectList) {
                String[] data = new String[objectList.size()];
                try {
                    for (int i = 0; i < objectList.size(); i++) {
                        data[i] = objectList.get(i).getString("name");
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.text_fragment_manager, data);

                    rv.setAdapter(adapter);
                    rv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        }
                    });
                } catch (Exception e) {

                }
            }
        }).execute(URLs.API_CHAMPIONSHIP);

        return linearLayout;
    }
}
