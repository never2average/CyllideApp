package com.cyllide.app.v1.contests;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.cyllide.app.v1.contests.positionsRV.Positions2;
import com.cyllide.app.v1.contests.positionsRV.PositionsAdapter;
import com.cyllide.app.v1.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PortfolioViewerDialogFragment extends DialogFragment {



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_portfolio_viewer, null);
        builder.setView(dialogLayout);
        final RecyclerView itemList = dialogLayout.findViewById(R.id.positions);
        itemList.setLayoutManager(new LinearLayoutManager(getContext()));
        itemList.setAdapter(new PositionsAdapter(dummyData()));
        dialogLayout.findViewById(R.id.close_btn_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return builder.create();
    }

    private List<Positions2> dummyData() {
        ArrayList<Positions2> data = new ArrayList<>();
        for(int i=0; i<5; i++)
            data.add(new Positions2("Position "+ (i+1), i*1000, (i*8)%100));
        return data;
    }
}
