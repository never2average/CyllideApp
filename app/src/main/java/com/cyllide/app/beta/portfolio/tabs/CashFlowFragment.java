package com.cyllide.app.beta.portfolio.tabs;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.beta.AppConstants;
import com.cyllide.app.beta.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CashFlowFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CashFlowFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CashFlowFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String[] years = { "2018", "2017", "2016"};
    String selectedYear = "2018";
    Spinner yearSelector;
    JSONObject volleyResponse;
    private Map<String,String> cashflowMap = new ArrayMap<>();
    RequestQueue cashflowQueue;
    TextView currentNetIncome, selectedNetIncome, currentDepreciation, selectedDepreciation, currentAdjNetIncome, selectedAdjNetIncome, currentChangesAccRec, selectedChangesAccRec, currentChangesLiabilities, selectedChangesLiabilities, currentChangesInventory, selectedChangesInventory, currentChangesOperations, selectedChangesOperations, currentTotalCFOps, selectedTotalCFOps;
    TextView currentCapitalExpenditure, selectedCapitalExpenditure, currentInvestments, selectedInvestments, currentCashflowOtherINV, selectedCashflowOtherINV, currentTotalCashflowINV, selectedTotalCashflowINV;
    TextView currentDividendsPaid, selectedDividendsPaid, currentSalePurchaseStock, selectedSalePurchaseStock, currentNetBorrowings, selectedNetBorrowings, currentOtherFinancialActivities, selectedOtherFinancialActivities, currentTotalFinancialCashFlow, selectedTotalFinancialCashFlow, currentExchangeRateEffect, selectedExchangeRateEffect, currentChangeCashEquivalent, selectedChangeCashEquivalent;
    private OnFragmentInteractionListener mListener;

    public CashFlowFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForumChartsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CashFlowFragment newInstance(String param1, String param2) {
        CashFlowFragment fragment = new CashFlowFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        cashFlowStatementVolley();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cash_flow, container, false);
        yearSelector = view.findViewById(R.id.cflow_year_selector);
        yearSelector.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,years);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSelector.setAdapter(aa);
        currentNetIncome=view.findViewById(R.id.cflow_net_income_cur_year);
        selectedNetIncome=view.findViewById(R.id.cflow_net_income_selected_year);
        currentDepreciation=view.findViewById(R.id.cflow_depreciation_cur_year);
        selectedDepreciation=view.findViewById(R.id.cflow_depreciation_selected_year);
        currentAdjNetIncome=view.findViewById(R.id.cflow_adj_net_income_cur_year);
        selectedAdjNetIncome=view.findViewById(R.id.cflow_adj_net_income_selected_year);
        currentChangesAccRec=view.findViewById(R.id.cflow_chg_acc_rec_cur_year);
        selectedChangesAccRec=view.findViewById(R.id.cflow_chg_acc_rec_selected_year);
        currentChangesLiabilities=view.findViewById(R.id.cflow_chg_liabilities_cur_year);
        selectedChangesLiabilities=view.findViewById(R.id.cflow_chg_liabilities_selected_year);
        currentChangesInventory=view.findViewById(R.id.cflow_chg_inventory_cur_year);
        selectedChangesInventory=view.findViewById(R.id.cflow_chg_inventory_selected_year);
        currentChangesOperations=view.findViewById(R.id.cflow_chg_ops_cur_year);
        selectedChangesOperations=view.findViewById(R.id.cflow_chg_ops_selected_year);
        currentTotalCFOps=view.findViewById(R.id.cflow_total_ops_cur_year);
        selectedTotalCFOps=view.findViewById(R.id.cflow_total_ops_selected_year);


        currentCapitalExpenditure=view.findViewById(R.id.cflow_capital_exp_cur_year);
        selectedCapitalExpenditure=view.findViewById(R.id.cflow_capital_exp_selected_year);
        currentInvestments=view.findViewById(R.id.cflow_investments_cur_year);
        selectedInvestments=view.findViewById(R.id.cflow_investments_selected_year);
        currentCashflowOtherINV = view.findViewById(R.id.cflow_inv_act_cur_year);
        selectedCashflowOtherINV = view.findViewById(R.id.cflow_inv_act_selected_year);
        currentTotalCashflowINV = view.findViewById(R.id.cflow_total_inv_cur_year);
        selectedTotalCashflowINV = view.findViewById(R.id.cflow_total_inv_selected_year);

        currentDividendsPaid=view.findViewById(R.id.cflow_dividends_paid_cur_year);
        selectedDividendsPaid=view.findViewById(R.id.cflow_dividends_paid_selected_year);
        currentSalePurchaseStock=view.findViewById(R.id.cflow_stock_sale_purchase_cur_year);
        selectedSalePurchaseStock=view.findViewById(R.id.cflow_stock_sale_purchase_selected_year);
        currentNetBorrowings=view.findViewById(R.id.cflow_net_borrowings_cur_year);
        selectedNetBorrowings=view.findViewById(R.id.cflow_net_borrowings_selected_year);
        currentOtherFinancialActivities=view.findViewById(R.id.cflow_fin_other_cur_year);
        selectedOtherFinancialActivities=view.findViewById(R.id.cflow_fin_other_selected_year);
        currentTotalFinancialCashFlow=view.findViewById(R.id.cflow_total_fin_cur_year);
        selectedTotalFinancialCashFlow=view.findViewById(R.id.cflow_total_fin_selected_year);
        currentExchangeRateEffect=view.findViewById(R.id.cflow_effect_exchange_rate_cur_year);
        selectedExchangeRateEffect=view.findViewById(R.id.cflow_effect_exchange_rate_selected_year);
        currentChangeCashEquivalent=view.findViewById(R.id.cflow_cash_change_cur_year);
        selectedChangeCashEquivalent=view.findViewById(R.id.cflow_cash_change_selected_year);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedYear = years[position];
        if(volleyResponse==null){
            cashFlowStatementVolley();
        }
        else{
            setData();
        }
    }

    private void setData() {
        if(volleyResponse!=null){
            try{
                JSONObject currObject = volleyResponse.getJSONObject("2019");
                JSONObject selectedObject = volleyResponse.getJSONObject(selectedYear);

                JSONObject currOPSCFO = currObject.getJSONObject("Cash Flow on Operations");
                JSONObject selectedOPSCFO = selectedObject.getJSONObject("Cash Flow on Operations");


                currentNetIncome.setText(currOPSCFO.getString("Net income"));
                selectedNetIncome.setText(selectedOPSCFO.getString("Net income"));
                currentDepreciation.setText(currOPSCFO.getString("Depreciation"));
                selectedDepreciation.setText(selectedOPSCFO.getString("Depreciation"));
                currentAdjNetIncome.setText(currOPSCFO.getString("Adjustments to net income"));
                selectedAdjNetIncome.setText(selectedOPSCFO.getString("Adjustments to net income"));
                currentChangesAccRec.setText(currOPSCFO.getString("Changes in accounts receivable"));
                selectedChangesAccRec.setText(selectedOPSCFO.getString("Changes in accounts receivable"));
                currentChangesLiabilities.setText(currOPSCFO.getString("Changes in liabilities"));
                selectedChangesLiabilities.setText(selectedOPSCFO.getString("Changes in liabilities"));
                currentChangesInventory.setText(currOPSCFO.getString("Changes in inventory"));
                selectedChangesInventory.setText(selectedOPSCFO.getString("Changes in inventory"));
                currentChangesOperations.setText(currOPSCFO.getString("Changes in other operating activities"));
                selectedChangesOperations.setText(selectedOPSCFO.getString("Changes in other operating activities"));
                currentTotalCFOps.setText(currOPSCFO.getString("Total cash flow from operating activities"));
                selectedTotalCFOps.setText(selectedOPSCFO.getString("Total cash flow from operating activities"));


                JSONObject currINVCFO = currObject.getJSONObject("Cash Flow in Investment");
                JSONObject selectedINVCFO = selectedObject.getJSONObject("Cash Flow in Investment");


                currentCapitalExpenditure.setText(currINVCFO.getString("Capital expenditure"));
                selectedCapitalExpenditure.setText(selectedINVCFO.getString("Capital expenditure"));
                currentInvestments.setText(currINVCFO.getString("Investments"));
                selectedInvestments.setText(selectedINVCFO.getString("Investments"));
                currentCashflowOtherINV.setText(currINVCFO.getString("Other cash flow from investment activities"));
                selectedCashflowOtherINV.setText(selectedINVCFO.getString("Other cash flow from investment activities"));
                currentTotalCashflowINV.setText(currINVCFO.getString("Total cash flow from investment activities"));
                selectedTotalCashflowINV.setText(selectedINVCFO.getString("Total cash flow from investment activities"));

                JSONObject currFINCFO = currObject.getJSONObject("Cash Flow in Financing Activities");
                JSONObject selectedFINCFO = selectedObject.getJSONObject("Cash Flow in Financing Activities");

                currentDividendsPaid.setText(currFINCFO.getString("Dividends paid"));
                selectedDividendsPaid.setText(selectedFINCFO.getString("Dividends paid"));
                currentSalePurchaseStock.setText(currFINCFO.getString("Sale purchase of stock"));
                selectedSalePurchaseStock.setText(selectedFINCFO.getString("Sale purchase of stock"));
                currentNetBorrowings.setText(currFINCFO.getString("Net borrowings"));
                selectedNetBorrowings.setText(selectedFINCFO.getString("Net borrowings"));
                currentOtherFinancialActivities.setText(currFINCFO.getString("Other cash flow from financing activities"));
                selectedOtherFinancialActivities.setText(selectedFINCFO.getString("Other cash flow from financing activities"));
                currentTotalFinancialCashFlow.setText(currFINCFO.getString("Total cash flow from financing activities"));
                selectedTotalFinancialCashFlow.setText(selectedFINCFO.getString("Total cash flow from financing activities"));
                currentExchangeRateEffect.setText(currFINCFO.getString("Effect of exchange rate changes"));
                selectedExchangeRateEffect.setText(selectedFINCFO.getString("Effect of exchange rate changes"));
                currentChangeCashEquivalent.setText(currFINCFO.getString("Change in cash and cash equivalents"));
                selectedChangeCashEquivalent.setText(selectedFINCFO.getString("Change in cash and cash equivalents"));

            }catch (JSONException e){
                e.printStackTrace();
            }
        }

    }

    private void cashFlowStatementVolley() {
        cashflowMap.put("ticker", AppConstants.currTicker);
        cashflowQueue = Volley.newRequestQueue(getContext());
        String url = getContext().getResources().getString(R.string.dataApiBaseURL)+"stocks/cashflow";
        StringRequest incomeRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    volleyResponse = new JSONObject(response);
                    setData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String,String> getHeaders(){
                return cashflowMap;
            }
        };
        cashflowQueue.add(incomeRequest);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
