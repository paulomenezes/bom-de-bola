package menezes.paulo.bomdebola.fragments.home;

import android.app.ProgressDialog;
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
import menezes.paulo.bomdebola.adapters.AdapterFragmentHomeGames;
import menezes.paulo.bomdebola.listeners.JsonResponse;
import menezes.paulo.bomdebola.util.DownloadJsonAsyncTask;
import menezes.paulo.bomdebola.util.JsonParser;
import menezes.paulo.bomdebola.util.URLs;

/**
 * Created by Paulo Menezes on 24/07/2015.
 */
public class FragmentGames extends Fragment {
    private static List<JSONObject> mObjectList;

    String games = "[{\"_id\":\"55b3085f96898f985780a809\",\"date\":\"25/7/2015 0:54:7\",\"score02\":\"1\",\"score01\":\"2\",\"idManager\":\"55b2a1cf5b45823042a5c5c6\",\"idField\":\"55b1f3f51e8c8f782b752631\",\"idChampionship\":\"55b2292574c09b7c2880640b\",\"idTeam02\":\"55b24965d5ae8cdc3bab618d\",\"idTeam01\":\"55b24965d5ae8cdc3bab617a\",\"__v\":0},{\"_id\":\"55b32c1eee2806305796d29e\",\"date\":\"26/7/2015 3:26:38\",\"idManager\":\"55b2a1cf5b45823042a5c5c6\",\"idField\":\"55b1f3f51e8c8f782b752631\",\"idChampionship\":\"55b2292574c09b7c2880640b\",\"idTeam02\":\"55b24965d5ae8cdc3bab618d\",\"idTeam01\":\"55b24965d5ae8cdc3bab617a\",\"__v\":0,\"score01\":\"3\",\"score02\":\"2\"},{\"_id\":\"55b347b5353dc3ac1823024e\",\"date\":\"26/7/2015 5:24:21\",\"idManager\":\"55b2a1cf5b45823042a5c5c6\",\"idField\":\"55b1f3f51e8c8f782b752631\",\"idChampionship\":\"55b2292574c09b7c2880640b\",\"idTeam02\":\"55b24965d5ae8cdc3bab618d\",\"idTeam01\":\"55b24965d5ae8cdc3bab617a\",\"__v\":0,\"score01\":\"3\",\"score02\":\"2\"},{\"_id\":\"55b3482f353dc3ac1823024f\",\"date\":\"26/7/2015 5:26:23\",\"idManager\":\"55b2a1cf5b45823042a5c5c6\",\"idField\":\"55b1f3f51e8c8f782b752631\",\"idChampionship\":\"55b2292574c09b7c2880640b\",\"idTeam02\":\"55b24965d5ae8cdc3bab618d\",\"idTeam01\":\"55b24965d5ae8cdc3bab617a\",\"__v\":0,\"score01\":\"2\",\"score02\":\"3\"}]";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_home_news, container, false);

        mObjectList = JsonParser.getObjects(games);

        setupRecyclerView(rv);
        return rv;
    }

    private void setupRecyclerView(final RecyclerView recyclerView) {
        if (mObjectList == null) {
            final ProgressDialog mDialog = ProgressDialog.show(getActivity(), "", getResources().getString(R.string.msg_loading));

            new DownloadJsonAsyncTask(new JsonResponse() {
                @Override
                public void response(List<JSONObject> objectList) {
                    mObjectList = objectList;

                    mDialog.dismiss();

                    recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
                    recyclerView.setAdapter(new AdapterFragmentHomeGames(getActivity(), objectList, recyclerView));
                }
            }).execute(URLs.API_GAME);
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            recyclerView.setAdapter(new AdapterFragmentHomeGames(getActivity(), mObjectList, recyclerView));
        }
    }
}