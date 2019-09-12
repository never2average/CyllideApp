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
 * {@link BalanceSheetFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BalanceSheetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BalanceSheetFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    String[] years = { "2018", "2017", "2016"};
    String selectedYear = "2018";
    Spinner yearSelector;
    JSONObject volleyResponse;
    private Map<String,String> balanceSheetMap = new ArrayMap<>();
    RequestQueue balanceSheetQueue;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView currentCashEquivalent, selectedCashEquivalent, currentShortTermInvestment, selectedShortTermInvestment, currentNetRecievable, selectedNetRecievable, currentInventory, selectedInventory, currentOtherCurrentAssets, selectedOtherCurrentAssets, currentTotalCurrentAssets, selectedTotalCurrentAssets, currentLongTermInvestment, selectedLongTermInvestment, currentPPE, selectedPPE, currentGoodwill, selectedGoodwill, currentIntangibleAssets, selectedIntangibleAssets, currentAccumulatedAmortization, selectedAccumulatedAmortization, currentOtherAssets, selectedOtherAssets, currentDeferredLTAssetCharges, selectedDeferredLTAssetCharges, currentTotalAssets, selectedTotalAssets;
    TextView currentAccountsPayable, selectedAccountsPayable, currentSTDebt, selectedSTDebt, currentOtherCurLiabilities, selectedOtherCurLiabilities, currentTotalCurLiabilities, selectedTotalCurLiabilities, currentLTDebt, selectedLTDebt, currentOthLiabilities, selectedOthLiabilities, currentLTLiabilityCharges, selectedLTLiabilityCharges, currentMinorityInterest, selectedMinorityInterest, currentNegativeGoodwill, selectedNegativeGoodwill, currentTotalLiabilities, selectedTotalLiabilities;
    TextView currentMiscStockOptions, selectedMiscStockOptions, currentRedeemablePrefStock, selectedRedeemablePrefStock, currentPrefStock, selectedPrefStock, currentCommonStock, selectedCommonStock, currentRetainedEarnings, selectedRetainedEarnings, currentTreasuryStock, selectedTreasuryStock, currentCapitalSurplus, selectedCapitalSurplus, currentOthStockholderEquity, selectedOthStockholderEquity, currentTotStockholderEquity, selectedTotStockholderEquity, currentNetTangibleAssets, selectedNetTangibleAssets;

    private OnFragmentInteractionListener mListener;

    public BalanceSheetFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static BalanceSheetFragment newInstance(String param1, String param2) {
        BalanceSheetFragment fragment = new BalanceSheetFragment();
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
        balanceSheetVolley();
    }

    void balanceSheetVolley() {
        balanceSheetMap.put("ticker", AppConstants.currTicker);
        balanceSheetQueue = Volley.newRequestQueue(getContext());
        String url = getContext().getResources().getString(R.string.dataApiBaseURL)+"stocks/balance";
        StringRequest balanceSheetRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
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
                return balanceSheetMap;
            }
        };
        balanceSheetQueue.add(balanceSheetRequest);
    }


    void setData(){
        try {
            JSONObject currentObject = volleyResponse.getJSONObject("2019");
            JSONObject selectedObject = volleyResponse.getJSONObject(selectedYear);

            JSONObject currentAssetsObject = currentObject.getJSONObject("Current assets");
            JSONObject selectedAssetsObject = selectedObject.getJSONObject("Current assets");


            currentCashEquivalent.setText(currentAssetsObject.getString("Cash and cash equivalents"));
            selectedCashEquivalent.setText(selectedAssetsObject.getString("Cash and cash equivalents"));
            currentShortTermInvestment.setText(currentAssetsObject.getString("Short-term investments"));
            selectedShortTermInvestment.setText(selectedAssetsObject.getString("Short-term investments"));
            currentNetRecievable.setText(currentAssetsObject.getString("Net receivables"));
            selectedNetRecievable.setText(selectedAssetsObject.getString("Net receivables"));
            currentInventory.setText(currentAssetsObject.getString("Inventory"));
            selectedInventory.setText(selectedAssetsObject.getString("Inventory"));
            currentOtherCurrentAssets.setText(currentAssetsObject.getString("Other current assets"));
            selectedOtherCurrentAssets.setText(selectedAssetsObject.getString("Other current assets"));
            currentTotalCurrentAssets.setText(currentAssetsObject.getString("Total current assets"));
            selectedTotalCurrentAssets.setText(selectedAssetsObject.getString("Total current assets"));
            currentLongTermInvestment.setText(currentAssetsObject.getString("Long-term investments"));
            selectedLongTermInvestment.setText(selectedAssetsObject.getString("Long-term investments"));
            currentPPE.setText(currentAssetsObject.getString("Property plant and equipment"));
            selectedPPE.setText(selectedAssetsObject.getString("Property plant and equipment"));
            currentGoodwill.setText(currentAssetsObject.getString("Goodwill"));
            selectedGoodwill.setText(selectedAssetsObject.getString("Goodwill"));
            currentIntangibleAssets.setText(currentAssetsObject.getString("Intangible assets"));
            selectedIntangibleAssets.setText(selectedAssetsObject.getString("Intangible assets"));
            currentAccumulatedAmortization.setText(currentAssetsObject.getString("Accumulated amortisation"));
            selectedAccumulatedAmortization.setText(currentAssetsObject.getString("Accumulated amortisation"));
            currentOtherAssets.setText(currentAssetsObject.getString("Other assets"));
            selectedOtherAssets.setText(currentAssetsObject.getString("Other assets"));
            currentDeferredLTAssetCharges.setText(currentAssetsObject.getString("Deferred long-term asset charges"));
            selectedDeferredLTAssetCharges.setText(currentAssetsObject.getString("Deferred long-term asset charges"));
            currentTotalAssets.setText(currentAssetsObject.getString("Total assets"));
            selectedTotalAssets.setText(currentAssetsObject.getString("Total assets"));

            JSONObject currentLiabilitiesObject = currentObject.getJSONObject("Current liabilities");
            JSONObject selectedLiabilitiesObject = selectedObject.getJSONObject("Current liabilities");


            currentAccountsPayable.setText(currentLiabilitiesObject.getString("Accounts payable"));
            selectedAccountsPayable.setText(selectedLiabilitiesObject.getString("Accounts payable"));
            currentSTDebt.setText(currentLiabilitiesObject.getString("Short/current long-term debt"));
            selectedSTDebt.setText(selectedLiabilitiesObject.getString("Short/current long-term debt"));
            currentOtherCurLiabilities.setText(currentLiabilitiesObject.getString("Other current liabilities"));
            selectedOtherCurLiabilities.setText(selectedLiabilitiesObject.getString("Other current liabilities"));
            currentTotalCurLiabilities.setText(currentLiabilitiesObject.getString("Total current liabilities"));
            selectedTotalCurLiabilities.setText(selectedLiabilitiesObject.getString("Total current liabilities"));
            currentLTDebt.setText(currentLiabilitiesObject.getString("Long-term debt"));
            selectedLTDebt.setText(selectedLiabilitiesObject.getString("Long-term debt"));
            currentOthLiabilities.setText(currentLiabilitiesObject.getString("Other liabilities"));
            selectedOthLiabilities.setText(selectedLiabilitiesObject.getString("Other liabilities"));
            currentLTLiabilityCharges.setText(currentLiabilitiesObject.getString("Deferred long-term liability charges"));
            selectedLTLiabilityCharges.setText(selectedLiabilitiesObject.getString("Deferred long-term liability charges"));
            currentMinorityInterest.setText(currentLiabilitiesObject.getString("Minority interest"));
            selectedMinorityInterest.setText(selectedLiabilitiesObject.getString("Minority interest"));
            currentNegativeGoodwill.setText(currentLiabilitiesObject.getString("Negative goodwill"));
            selectedNegativeGoodwill.setText(selectedLiabilitiesObject.getString("Negative goodwill"));
            currentTotalLiabilities.setText(currentLiabilitiesObject.getString("Total liabilities"));
            selectedTotalLiabilities.setText(selectedLiabilitiesObject.getString("Total liabilities"));

            JSONObject currentStockholderEquity = currentObject.getJSONObject("Stockholders' equity");
            JSONObject selectedStockholderEquity = selectedObject.getJSONObject("Stockholders' equity");


            currentMiscStockOptions.setText(currentStockholderEquity.getString("Misc. Stock options warrants"));
            selectedMiscStockOptions.setText(selectedStockholderEquity.getString("Misc. Stock options warrants"));
            currentRedeemablePrefStock.setText(currentStockholderEquity.getString("Redeemable preferred stock"));
            selectedRedeemablePrefStock.setText(selectedStockholderEquity.getString("Redeemable preferred stock"));
            currentPrefStock.setText(currentStockholderEquity.getString("Preferred stock"));
            selectedPrefStock.setText(selectedStockholderEquity.getString("Preferred stock"));
            currentCommonStock.setText(currentStockholderEquity.getString("Common stock"));
            selectedCommonStock.setText(selectedStockholderEquity.getString("Common stock"));
            currentRetainedEarnings.setText(currentStockholderEquity.getString("Retained earnings"));
            selectedRetainedEarnings.setText(selectedStockholderEquity.getString("Retained earnings"));
            currentTreasuryStock.setText(currentStockholderEquity.getString("Treasury stock"));
            selectedTreasuryStock.setText(selectedStockholderEquity.getString("Treasury stock"));
            currentCapitalSurplus.setText(currentStockholderEquity.getString("Capital surplus"));
            selectedCapitalSurplus.setText(selectedStockholderEquity.getString("Capital surplus"));
            currentOthStockholderEquity.setText(currentStockholderEquity.getString("Other stockholder equity"));
            selectedOthStockholderEquity.setText(selectedStockholderEquity.getString("Other stockholder equity"));
            currentTotStockholderEquity.setText(currentStockholderEquity.getString("Total stockholder equity"));
            selectedTotStockholderEquity.setText(selectedStockholderEquity.getString("Total stockholder equity"));
            currentNetTangibleAssets.setText(currentStockholderEquity.getString("Net tangible assets"));
            selectedNetTangibleAssets.setText(selectedStockholderEquity.getString("Net tangible assets"));


        } catch (JSONException e) {
            e.printStackTrace();
        }




    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.balance_sheet_fragment, container, false);
        yearSelector = view.findViewById(R.id.bal_sheet_year_selector_spinner);
        yearSelector.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,years);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSelector.setAdapter(aa);

        currentCashEquivalent=view.findViewById(R.id.bal_sheet_cash_eq_cur_year);
        selectedCashEquivalent=view.findViewById(R.id.bal_sheet_cash_eq_selected_year);
        currentShortTermInvestment=view.findViewById(R.id.bal_sheet_st_investment_cur_year);
        selectedShortTermInvestment=view.findViewById(R.id.bal_sheet_st_investment_selected_year);
        currentNetRecievable=view.findViewById(R.id.bal_sheet_net_recv_cur_year);
        selectedNetRecievable=view.findViewById(R.id.bal_sheet_net_recv_selected_year);
        currentInventory=view.findViewById(R.id.bal_sheet_inventory_cur_year);
        selectedInventory=view.findViewById(R.id.bal_sheet_inventory_selected_year);
        currentOtherCurrentAssets=view.findViewById(R.id.bal_sheet_oth_cur_assets_cur_year);
        selectedOtherCurrentAssets=view.findViewById(R.id.bal_sheet_oth_cur_assets_selected_year);
        currentTotalCurrentAssets=view.findViewById(R.id.bal_sheet_tot_assets_cur_year);
        selectedTotalCurrentAssets=view.findViewById(R.id.bal_sheet_tot_assets_selected_year);
        currentLongTermInvestment=view.findViewById(R.id.bal_sheet_lt_investment_cur_year);
        selectedLongTermInvestment=view.findViewById(R.id.bal_sheet_lt_investment_selected_year);
        currentPPE=view.findViewById(R.id.bal_sheet_ppe_cur_year);
        selectedPPE=view.findViewById(R.id.bal_sheet_ppe_selected_year);
        currentGoodwill=view.findViewById(R.id.bal_sheet_goodwill_cur_year);
        selectedGoodwill=view.findViewById(R.id.bal_sheet_goodwill_selected_year);
        currentIntangibleAssets=view.findViewById(R.id.bal_sheet_intangible_assets_cur_year);
        selectedIntangibleAssets=view.findViewById(R.id.bal_sheet_intangible_assets_selected_year);
        currentAccumulatedAmortization=view.findViewById(R.id.bal_sheet_acc_amortization_cur_year);
        selectedAccumulatedAmortization=view.findViewById(R.id.bal_sheet_acc_amortization_selected_year);
        currentOtherAssets=view.findViewById(R.id.bal_sheet_oth_assets_cur_year);
        selectedOtherAssets=view.findViewById(R.id.bal_sheet_oth_assets_selected_year);
        currentDeferredLTAssetCharges=view.findViewById(R.id.bal_sheet_lt_asset_charges_cur_year);
        selectedDeferredLTAssetCharges=view.findViewById(R.id.bal_sheet_lt_asset_charges_selected_year);
        currentTotalAssets=view.findViewById(R.id.bal_sheet_tot_assets_cur_year);
        selectedTotalAssets=view.findViewById(R.id.bal_sheet_tot_assets_selected_year);


        currentAccountsPayable=view.findViewById(R.id.bal_sheet_acc_payable_cur_year);
        selectedAccountsPayable=view.findViewById(R.id.bal_sheet_acc_payable_selected_year);
        currentSTDebt=view.findViewById(R.id.bal_sheet_st_debt_cur_year);
        selectedSTDebt=view.findViewById(R.id.bal_sheet_st_debt_selected_year);
        currentOtherCurLiabilities=view.findViewById(R.id.bal_sheet_oth_cur_liabilities_cur_year);
        selectedOtherCurLiabilities=view.findViewById(R.id.bal_sheet_oth_cur_liabilities_selected_year);
        currentTotalCurLiabilities=view.findViewById(R.id.bal_sheet_tot_cur_liabilities_cur_year);
        selectedTotalCurLiabilities=view.findViewById(R.id.bal_sheet_tot_cur_liabilities_selected_year);
        currentLTDebt=view.findViewById(R.id.bal_sheet_lt_debt_cur_year);
        selectedLTDebt=view.findViewById(R.id.bal_sheet_lt_debt_selected_year);
        currentOthLiabilities=view.findViewById(R.id.bal_sheet_oth_liabilities_cur_year);
        selectedOthLiabilities=view.findViewById(R.id.bal_sheet_oth_liabilities_selected_year);
        currentLTLiabilityCharges=view.findViewById(R.id.bal_sheet_lt_liability_charges_cur_year);
        selectedLTLiabilityCharges=view.findViewById(R.id.bal_sheet_lt_liability_charges_selected_year);
        currentMinorityInterest=view.findViewById(R.id.bal_sheet_min_interest_cur_year);
        selectedMinorityInterest=view.findViewById(R.id.bal_sheet_min_interest_selected_year);
        currentNegativeGoodwill=view.findViewById(R.id.bal_sheet_neg_goodwill_cur_year);
        selectedNegativeGoodwill=view.findViewById(R.id.bal_sheet_neg_goodwill_selected_year);
        currentTotalLiabilities=view.findViewById(R.id.bal_sheet_tot_liabilities_cur_year);
        selectedTotalLiabilities=view.findViewById(R.id.bal_sheet_tot_liabilities_selected_year);


        currentMiscStockOptions=view.findViewById(R.id.bal_sheet_misc_stock_options_cur_year);
        selectedMiscStockOptions=view.findViewById(R.id.bal_sheet_misc_stock_options_selected_year);
        currentRedeemablePrefStock=view.findViewById(R.id.bal_sheet_red_preferred_stock_cur_year);
        selectedRedeemablePrefStock=view.findViewById(R.id.bal_sheet_red_preferred_stock_selected_year);
        currentPrefStock=view.findViewById(R.id.bal_sheet_preferred_stock_cur_year);
        selectedPrefStock=view.findViewById(R.id.bal_sheet_preferred_stock_selected_year);
        currentCommonStock=view.findViewById(R.id.bal_sheet_common_stock_cur_year);
        selectedCommonStock=view.findViewById(R.id.bal_sheet_common_stock_selected_year);
        currentRetainedEarnings=view.findViewById(R.id.bal_sheet_retained_earnings_cur_year);
        selectedRetainedEarnings=view.findViewById(R.id.bal_sheet_retained_earnings_selected_year);
        currentTreasuryStock=view.findViewById(R.id.bal_sheet_treasury_stock_cur_year);
        selectedTreasuryStock=view.findViewById(R.id.bal_sheet_treasury_stock_selected_year);
        currentCapitalSurplus=view.findViewById(R.id.bal_sheet_capital_surplus_cur_year);
        selectedCapitalSurplus=view.findViewById(R.id.bal_sheet_capital_surplus_selected_year);
        currentOthStockholderEquity=view.findViewById(R.id.bal_sheet_oth_stockholder_equity_cur_year);
        selectedOthStockholderEquity=view.findViewById(R.id.bal_sheet_oth_stockholder_equity_selected_year);
        currentTotStockholderEquity=view.findViewById(R.id.bal_sheet_tot_stockholder_equity_cur_year);
        selectedTotStockholderEquity=view.findViewById(R.id.bal_sheet_tot_stockholder_equity_selected_year);
        currentNetTangibleAssets=view.findViewById(R.id.bal_sheet_net_tangible_assets_cur_year);
        selectedNetTangibleAssets=view.findViewById(R.id.bal_sheet_net_tangible_assets_selected_year);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedYear = years[position];
        if(volleyResponse==null){
            balanceSheetVolley();
        }
        else{
            setData();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
