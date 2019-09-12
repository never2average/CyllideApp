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
 * {@link IncomeStatementFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IncomeStatementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IncomeStatementFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RequestQueue incomeQueue;
    private Map<String,String> incomeMap=new ArrayMap<>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    String[] years = { "2018", "2017", "2016"};
    private String mParam2;
    String selectedYear = "2018";
    Spinner yearSelector;
    JSONObject volleyResponse;
    TextView currentTotalRevenue, selectedTotalRevenue,currentCostOfRevenue, selectedCostOfRevenue,currentGrossProfit, selectedGrossProfit;
    TextView currentResearchDevelopment, selectedResearchDevelopment, currentSellingGenAdmin, selectedSellingGenAdmin, currentNonRecurring, selectedNonRecurring, currentOtherExpenses, selectedOtherExpenses, currentTotalOpExpenses, selectedTotalOpExpenses, currentOpIncLoss, selectedOpIncLoss;
    TextView currentNetIncome, selectedNetIncome, currentPreferredStock, selectedPreferredStock, currentCommonshares, selectedCommonShares;
    TextView currentDiscontinuedOperations, selectedDiscontinuedOperations,  currentExtraOrdinaryItems, selectedExtraOrdinaryItems, currentAccountChanges, selectedAccountChanges, currentOtherItems, selectedOtherItems;
    TextView currentTotalOtherIncome, selectedTotalOtherIncome, currentEBIT, selectedEBIT, currentInterestExp, selectedInterestExp, currentIBT, selectedIBT, currentIncomeTaxExpense, selectedIncomeTaxExpense, currentMinorityInterest, selectedMinorityInterest, currentNetOpsIncome, selectedNetOpsIncome;
    private OnFragmentInteractionListener mListener;

    public IncomeStatementFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OptionsChartsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IncomeStatementFragment newInstance(String param1, String param2) {
        IncomeStatementFragment fragment = new IncomeStatementFragment();
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
        incomeStatementVolley();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.income_statement_fragment, container, false);
        yearSelector = view.findViewById(R.id.incst_year_selector_spinner);
        yearSelector.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,years);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSelector.setAdapter(aa);
        currentTotalRevenue = view.findViewById(R.id.incst_total_revenue_cur_year);
        selectedTotalRevenue = view.findViewById(R.id.incst_total_revenue_selected_year);
        currentCostOfRevenue = view.findViewById(R.id.incst_cost_of_revenue_cur_year);
        selectedCostOfRevenue = view.findViewById(R.id.incst_cost_of_revenue_selected_year);
        currentGrossProfit = view.findViewById(R.id.incst_gross_profit_cur_year);
        selectedGrossProfit = view.findViewById(R.id.incst_gross_profit_selected_year);

        currentResearchDevelopment=view.findViewById(R.id.incst_rnd_cur_year);
        selectedResearchDevelopment=view.findViewById(R.id.incst_rnd_selected_year);
        currentSellingGenAdmin=view.findViewById(R.id.incst_sga_cur_year);
        selectedSellingGenAdmin=view.findViewById(R.id.incst_sga_selected_year);
        currentNonRecurring=view.findViewById(R.id.incst_non_rec_cur_year);
        selectedNonRecurring=view.findViewById(R.id.incst_non_rec_selected_year);
        currentOtherExpenses=view.findViewById(R.id.incst_others_cur_year);
        selectedOtherExpenses=view.findViewById(R.id.incst_others_selected_year);
        currentTotalOpExpenses=view.findViewById(R.id.incst_total_op_exp_cur_year);
        selectedTotalOpExpenses=view.findViewById(R.id.incst_total_op_exp_selected_year);
        currentOpIncLoss=view.findViewById(R.id.incst_op_inc_cur_year);
        selectedOpIncLoss=view.findViewById(R.id.incst_op_inc_selected_year);


        currentTotalOtherIncome=view.findViewById(R.id.incst_tot_oth_inc_cur_year);
        selectedTotalOtherIncome=view.findViewById(R.id.incst_tot_oth_inc_selected_year);
        currentEBIT=view.findViewById(R.id.incst_ebit_cur_year);
        selectedEBIT=view.findViewById(R.id.incst_ebit_selected_year);
        currentInterestExp=view.findViewById(R.id.incst_interest_cur_year);
        selectedInterestExp=view.findViewById(R.id.incst_interest_selected_year);
        currentIBT=view.findViewById(R.id.incst_ibt_cur_year);
        selectedIBT=view.findViewById(R.id.incst_ibt_selected_year);
        currentIncomeTaxExpense=view.findViewById(R.id.incst_inc_tax_exp_cur_year);
        selectedIncomeTaxExpense=view.findViewById(R.id.incst_inc_tax_exp_selected_year);
        currentMinorityInterest=view.findViewById(R.id.incst_min_int_cur_year);
        selectedMinorityInterest=view.findViewById(R.id.incst_min_int_selected_year);
        currentNetOpsIncome=view.findViewById(R.id.incst_net_inc_ops_cur_year);
        selectedNetOpsIncome=view.findViewById(R.id.incst_net_inc_ops_selected_year);


        currentDiscontinuedOperations=view.findViewById(R.id.incst_discont_ops_cur_year);
        selectedDiscontinuedOperations=view.findViewById(R.id.incst_discont_ops_selected_year);
        currentExtraOrdinaryItems=view.findViewById(R.id.incst_extraordinary_cur_year);
        selectedExtraOrdinaryItems=view.findViewById(R.id.incst_extraordinary_selected_year);
        currentAccountChanges=view.findViewById(R.id.incst_acc_charges_cur_year);
        selectedAccountChanges=view.findViewById(R.id.incst_acc_charges_selected_year);
        currentOtherItems=view.findViewById(R.id.incst_other_items_cur_year);
        selectedOtherItems=view.findViewById(R.id.incst_other_items_selected_year);

        currentNetIncome=view.findViewById(R.id.incst_net_inc_cur_year);
        selectedNetIncome=view.findViewById(R.id.incst_net_inc_selected_year);
        currentPreferredStock=view.findViewById(R.id.incst_stk_adj_cur_year);
        selectedPreferredStock=view.findViewById(R.id.incst_stk_adj_selected_year);
        currentCommonshares=view.findViewById(R.id.incst_net_inc_cmn_shares_cur_year);
        selectedCommonShares=view.findViewById(R.id.incst_net_inc_cmn_shares_selected_year);

        incomeStatementVolley();
        return view;
    }

    private void incomeStatementVolley() {
        incomeMap.put("ticker", AppConstants.currTicker);
        incomeQueue = Volley.newRequestQueue(getContext());
        String url = getContext().getResources().getString(R.string.dataApiBaseURL)+"stocks/income";
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
                return incomeMap;
            }
        };
        incomeQueue.add(incomeRequest);
    }
    public void setData(){
        if(volleyResponse!=null){
            try {
                JSONObject currObject = volleyResponse.getJSONObject("2019");
                JSONObject selectedObject = volleyResponse.getJSONObject(selectedYear);

                currentTotalRevenue.setText(currObject.getJSONObject("Revenue").getString("Total revenue"));
                selectedTotalRevenue.setText(selectedObject.getJSONObject("Revenue").getString("Total revenue"));
                currentCostOfRevenue.setText(currObject.getJSONObject("Revenue").getString("Cost of revenue"));
                selectedCostOfRevenue.setText(selectedObject.getJSONObject("Revenue").getString("Cost of revenue"));
                currentGrossProfit.setText(currObject.getJSONObject("Revenue").getString("Gross profit"));
                selectedGrossProfit.setText(selectedObject.getJSONObject("Revenue").getString("Gross profit"));

                currentResearchDevelopment.setText(currObject.getJSONObject("Operating expenses").getString("Research development"));
                selectedResearchDevelopment.setText(selectedObject.getJSONObject("Operating expenses").getString("Research development"));
                currentSellingGenAdmin.setText(currObject.getJSONObject("Operating expenses").getString("Selling general and administrative"));
                selectedSellingGenAdmin.setText(selectedObject.getJSONObject("Operating expenses").getString("Selling general and administrative"));
                currentNonRecurring.setText(currObject.getJSONObject("Operating expenses").getString("Non-recurring"));
                selectedNonRecurring.setText(selectedObject.getJSONObject("Operating expenses").getString("Non-recurring"));
                currentOtherExpenses.setText(currObject.getJSONObject("Operating expenses").getString("Others"));
                selectedOtherExpenses.setText(selectedObject.getJSONObject("Operating expenses").getString("Others"));
                currentTotalOpExpenses.setText(currObject.getJSONObject("Operating expenses").getString("Total operating expenses"));
                selectedTotalOpExpenses.setText(selectedObject.getJSONObject("Operating expenses").getString("Total operating expenses"));
                currentOpIncLoss.setText(currObject.getJSONObject("Operating expenses").getString("Operating income or loss"));
                selectedOpIncLoss.setText(selectedObject.getJSONObject("Operating expenses").getString("Operating income or loss"));

                currentTotalOtherIncome.setText(currObject.getJSONObject("Income from continuing operations").getString("Total other income/expenses net"));
                selectedTotalOtherIncome.setText(selectedObject.getJSONObject("Income from continuing operations").getString("Total other income/expenses net"));
                currentEBIT.setText(currObject.getJSONObject("Income from continuing operations").getString("Earnings before interest and taxes"));
                selectedEBIT.setText(selectedObject.getJSONObject("Income from continuing operations").getString("Earnings before interest and taxes"));
                currentInterestExp.setText(currObject.getJSONObject("Income from continuing operations").getString("Interest expense"));
                selectedInterestExp.setText(selectedObject.getJSONObject("Income from continuing operations").getString("Interest expense"));
                currentIBT.setText(currObject.getJSONObject("Income from continuing operations").getString("Income before tax"));
                selectedIBT.setText(selectedObject.getJSONObject("Income from continuing operations").getString("Income before tax"));
                currentIncomeTaxExpense.setText(currObject.getJSONObject("Income from continuing operations").getString("Income tax expense"));
                selectedIncomeTaxExpense.setText(selectedObject.getJSONObject("Income from continuing operations").getString("Income tax expense"));
                currentMinorityInterest.setText(currObject.getJSONObject("Income from continuing operations").getString("Minority interest"));
                selectedMinorityInterest.setText(selectedObject.getJSONObject("Income from continuing operations").getString("Minority interest"));
                currentNetOpsIncome.setText(currObject.getJSONObject("Income from continuing operations").getString("Net income from continuing ops"));
                selectedNetOpsIncome.setText(selectedObject.getJSONObject("Income from continuing operations").getString("Net income from continuing ops"));

                currentDiscontinuedOperations.setText(currObject.getJSONObject("Non-recurring events").getString("Discontinued operations"));
                selectedDiscontinuedOperations.setText(selectedObject.getJSONObject("Non-recurring events").getString("Discontinued operations"));
                currentExtraOrdinaryItems.setText(currObject.getJSONObject("Non-recurring events").getString("Extraordinary items"));
                selectedExtraOrdinaryItems.setText(selectedObject.getJSONObject("Non-recurring events").getString("Extraordinary items"));
                currentAccountChanges.setText(currObject.getJSONObject("Non-recurring events").getString("Effect of accounting changes"));
                selectedAccountChanges.setText(selectedObject.getJSONObject("Non-recurring events").getString("Effect of accounting changes"));
                currentOtherItems.setText(currObject.getJSONObject("Non-recurring events").getString("Other items"));
                selectedOtherItems.setText(selectedObject.getJSONObject("Non-recurring events").getString("Other items"));

                currentNetIncome.setText(currObject.getJSONObject("Net income").getString("Net income"));
                selectedNetIncome.setText(selectedObject.getJSONObject("Net income").getString("Net income"));
                currentPreferredStock.setText(currObject.getJSONObject("Net income").getString("Preferred stock and other adjustments"));
                selectedPreferredStock.setText(selectedObject.getJSONObject("Net income").getString("Preferred stock and other adjustments"));
                currentCommonshares.setText(currObject.getJSONObject("Net income").getString("Net income applicable to common shares"));
                selectedCommonShares.setText(selectedObject.getJSONObject("Net income").getString("Net income applicable to common shares"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
            incomeStatementVolley();
        }
        else{
            setData();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
