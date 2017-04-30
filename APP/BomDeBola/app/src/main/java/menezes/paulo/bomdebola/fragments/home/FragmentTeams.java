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
import menezes.paulo.bomdebola.adapters.AdapterFragmentHomeTeams;
import menezes.paulo.bomdebola.listeners.JsonResponse;
import menezes.paulo.bomdebola.util.DownloadJsonAsyncTask;
import menezes.paulo.bomdebola.util.JsonParser;
import menezes.paulo.bomdebola.util.URLs;

/**
 * Created by Paulo Menezes on 24/07/2015.
 */
public class FragmentTeams extends Fragment {

    public static List<JSONObject> sObjectList;
    private String teams = "[{\"_id\":\"55b24965d5ae8cdc3bab617a\",\"idManager\":\"55b042260b14b2103327144e\",\"rpa\":\"2\",\"name\":\"Futebol E Recreação Santiago\",\"__v\":0,\"idChampionship\":[\"55b2292574c09b7c2880640b\",\"55b2292574c09b7c2880640c\"]},{\"_id\":\"55b24965d5ae8cdc3bab617c\",\"idManager\":\"55b042260b14b2103327144e\",\"rpa\":\"2\",\"name\":\"BOCA JUNIORS DE LINHA DO TIRO\",\"__v\":0,\"idChampionship\":[\"55b2292574c09b7c2880640c\"]},{\"_id\":\"55b24965d5ae8cdc3bab617d\",\"idManager\":\"55b042260b14b2103327144e\",\"rpa\":\"2\",\"name\":\"Futebol E Recreação Santiago 3\",\"__v\":0,\"idChampionship\":[\"55b2292574c09b7c2880640d\"]},{\"_id\":\"55b24965d5ae8cdc3bab617e\",\"idManager\":\"55b042260b14b2103327144e\",\"rpa\":\"4\",\"name\":\"Grêmio Da Horta\",\"__v\":0,\"idChampionship\":[\"55b2292574c09b7c2880640d\"]},{\"_id\":\"55b24965d5ae8cdc3bab6180\",\"idManager\":\"55b042260b14b2103327144e\",\"rpa\":\"2\",\"name\":\"os meninos da vila f.c\",\"__v\":0,\"idChampionship\":[\"55b2292574c09b7c2880640d\"]},{\"_id\":\"55b24965d5ae8cdc3bab6183\",\"idManager\":\"55b042260b14b2103327144e\",\"rpa\":\"1\",\"name\":\"Clube Recreativo Fernandinho\",\"__v\":0,\"idChampionship\":[\"55b2292574c09b7c2880640d\"]},{\"_id\":\"55b24965d5ae8cdc3bab6185\",\"idManager\":\"55b042260b14b2103327144e\",\"rpa\":\"5\",\"name\":\"TOPA TUDO FUTEBOL CLUBE\",\"__v\":0,\"idChampionship\":[\"55b2292574c09b7c2880640d\"]},{\"_id\":\"55b24965d5ae8cdc3bab6188\",\"idManager\":\"55b042260b14b2103327144e\",\"rpa\":\"1\",\"name\":\"Geração Futuro\",\"__v\":0,\"idChampionship\":[\"55b2292574c09b7c2880640b\"]},{\"_id\":\"55b24965d5ae8cdc3bab618a\",\"idManager\":\"55b042260b14b2103327144e\",\"rpa\":\"3\",\"name\":\"Escolinha do Guima\",\"__v\":0,\"idChampionship\":[\"55b2292574c09b7c2880640b\",\"55b2292574c09b7c2880640c\"]},{\"_id\":\"55b24965d5ae8cdc3bab618d\",\"idManager\":\"55b042260b14b2103327144e\",\"rpa\":\"5\",\"name\":\"Amovicar Futebol Clube\",\"__v\":0,\"idChampionship\":[\"55b2292574c09b7c2880640d\"]}]";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_home_news, container, false);

        sObjectList = JsonParser.getObjects(teams);

        setupRecyclerView(rv);
        return rv;
    }

    private void setupRecyclerView(final RecyclerView recyclerView) {
        if (sObjectList == null) {
            final ProgressDialog dialog = ProgressDialog.show(getActivity(), "", getActivity().getResources().getString(R.string.msg_loading));
            new DownloadJsonAsyncTask(new JsonResponse() {
                @Override
                public void response(List<JSONObject> objectList) {
                    sObjectList = objectList;

                    dialog.dismiss();

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