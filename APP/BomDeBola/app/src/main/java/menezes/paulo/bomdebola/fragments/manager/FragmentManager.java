package menezes.paulo.bomdebola.fragments.manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import menezes.paulo.bomdebola.R;
import menezes.paulo.bomdebola.StartGameActivity;

/**
 * Created by Paulo Menezes on 24/07/2015.
 */
public class FragmentManager extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout rv = (LinearLayout) inflater.inflate(R.layout.fragment_manager_home, container, false);

        Button start = (Button) rv.findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), StartGameActivity.class));
            }
        });
        Button message = (Button) rv.findViewById(R.id.message);
        Button help = (Button) rv.findViewById(R.id.help);

        return rv;
    }
}
