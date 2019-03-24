package com.example.kartikbhardwaj.bottom_navigation.Contests;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;


import com.example.kartikbhardwaj.bottom_navigation.Contests.PortfolioRV.PortfolioAdapter;
import com.example.kartikbhardwaj.bottom_navigation.Contests.PortfolioRV.PortfolioModel;
import com.example.kartikbhardwaj.bottom_navigation.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PortfolioPickerDialogFragment extends DialogFragment {

    PortfolioPickerClickListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (PortfolioPickerClickListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement PortfolioPickerClickListener");
        }
    }

    public interface PortfolioPickerClickListener{
        public  void onItemClick(PortfolioModel portfolio);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_portfolio_picker, null);
        builder.setView(dialogLayout);
        final RecyclerView itemList = dialogLayout.findViewById(R.id.portfolios);
        itemList.setLayoutManager(new LinearLayoutManager(getContext()));
        itemList.setAdapter(new PortfolioAdapter(dummyData()));
        dialogLayout.findViewById(R.id.close_btn_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return builder.create();
    }

    private List<PortfolioModel> dummyData() {
        ArrayList<PortfolioModel> data = new ArrayList<>();
        for(int i=0; i<5; i++)
            data.add(new PortfolioModel("Portfolio " + i , i*100));
        return data;
    }
}
