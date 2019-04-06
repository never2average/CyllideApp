package com.cyllide.app.v1.stories;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyllide.app.v1.R;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import static android.content.ContentValues.TAG;


public class StoriesFragment extends Fragment {
    private RecyclerView storiesRV;

    String storiesTitle[]={"News1","News2","News3"};
    String storiesThumbnailSource[]={"https://www.desktopbackground.org/download/1366x768/2014/08/21/812557_civil-engineering-wallpapers_1600x1200_h.jpg","https://www.desktopbackground.org/download/1366x768/2014/08/21/812557_civil-engineering-wallpapers_1600x1200_h.jpg","https://www.desktopbackground.org/download/1366x768/2014/08/21/812557_civil-engineering-wallpapers_1600x1200_h.jpg"};
    String storiesDescription[]={"General Description shown in the other activity","General Description shown in the other activity","General Description shown in the other activity"};
    private List<StoriesModel> dummyData() {
        List<StoriesModel> data = new ArrayList<>(12);
        for (int i = 0; i < 3; i++) {
            data.add(new StoriesModel(storiesTitle[i],storiesThumbnailSource[i],storiesDescription[i],"","",""));
        }//data is the list of objects to be set in the list item
        return data;
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stories, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        storiesRV = view.findViewById(R.id.fragment_stories_rv);
        final Context context = getContext();
        SnapHelper snapHelper = new PagerSnapHelper();



        storiesRV.setHasFixedSize(true);
        storiesRV.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        snapHelper.attachToRecyclerView(storiesRV);

    }

    @Override
    public void onStart() {
        super.onStart();
        final Activity activity = getActivity();
            final Context context = getContext();
            Fresco.initialize(context);
            List<StoriesModel> storiesPart = dummyData();
        if (activity != null) {
            final StoriesAdapter mAdapter = new StoriesAdapter(storiesPart);
            storiesRV.setAdapter(mAdapter);
        } else {
            Log.e(TAG, "getActivity() returned null in onStart()");
        }
    }
}
