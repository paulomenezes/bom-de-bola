package menezes.paulo.bomdebola.fragments.manager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONObject;

import java.util.List;

import menezes.paulo.bomdebola.R;
import menezes.paulo.bomdebola.adapters.AdapterFragmentHomeTeams;
import menezes.paulo.bomdebola.listeners.JsonResponse;
import menezes.paulo.bomdebola.util.DownloadJsonAsyncTask;
import menezes.paulo.bomdebola.util.URLs;

/**
 * Created by Paulo Menezes on 24/07/2015.
 */
public class FragmentMyTeams extends Fragment {
    public static List<JSONObject> sObjectList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_home_news, container, false);
        setupRecyclerView(rv);
        return rv;
    }

    private void setupRecyclerView(final RecyclerView recyclerView) {
        if (sObjectList == null) {
            new DownloadJsonAsyncTask(new JsonResponse() {
                @Override
                public void response(List<JSONObject> objectList) {
                    sObjectList = objectList;

                    recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
                    recyclerView.setAdapter(new AdapterFragmentHomeTeams(getActivity(), objectList, recyclerView));
                }
            }).execute(URLs.API_TEAM);
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            recyclerView.setAdapter(new AdapterFragmentHomeTeams(getActivity(), sObjectList, recyclerView));
        }
    }
}
