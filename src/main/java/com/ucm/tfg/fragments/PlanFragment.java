package com.ucm.tfg.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.ucm.tfg.R;
import com.ucm.tfg.Session;
import com.ucm.tfg.Utils;
import com.ucm.tfg.activities.MainActivity;
import com.ucm.tfg.activities.PlanActivity;
import com.ucm.tfg.adapters.PlanAdapter;
import com.ucm.tfg.adapters.PlanFriendsAdapter;
import com.ucm.tfg.entities.Film;
import com.ucm.tfg.entities.Friendship;
import com.ucm.tfg.entities.Plan;
import com.ucm.tfg.entities.User;
import com.ucm.tfg.service.FilmService;
import com.ucm.tfg.service.PlanService;
import com.ucm.tfg.service.Service;
import com.ucm.tfg.service.UserFilmService;
import com.ucm.tfg.service.UserService;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlanFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlanFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private PlanAdapter planAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    private OnFragmentInteractionListener mListener;

    public PlanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlanFragment newInstance(String param1, String param2) {
        PlanFragment fragment = new PlanFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_plan, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.plans);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        planAdapter = new PlanAdapter(getActivity());
        planAdapter.addPlanOnClickListener((Plan p, PlanAdapter.RecyclerViewHolder recyclerViewHolder) -> {
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(
                            getActivity(),
                            Pair.create(recyclerViewHolder.image, "film_poster")
                    );
            Intent i = new Intent(getActivity(), PlanActivity.class);
            i.putExtra("plan", p);
            startActivity(i, optionsCompat.toBundle());
        });

        recyclerView.setAdapter(planAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            updatePlans();
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        updatePlans();
    }


    private void updatePlans() {
        swipeRefreshLayout.setRefreshing(true);
        String user = getActivity().getSharedPreferences(Session.SESSION_FILE, 0).getString(Session.USER, null);
        if (!Utils.isNullOrEmpty(user)) {
            UserService.getUserPlansById(getActivity(), user, new Service.ClientResponse<ArrayList<Plan>>() {
                @Override
                public void onSuccess(ArrayList<Plan> result) {
                    planAdapter.setPlansData(result);
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }
    }

    private void searchPlans(String title) {
        if (!Utils.isNullOrEmpty(title)) {
            swipeRefreshLayout.setRefreshing(true);
            PlanService.searchPlansByTitle(getActivity(), title, new Service.ClientResponse<ArrayList<Plan>>() {
                @Override
                public void onSuccess(ArrayList<Plan> result) {
                    planAdapter.setPlansData(result);
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }
    }

    public void setSearchView(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!Utils.isNullOrEmpty(s)) {
                    searchPlans(s);
                }
                return false;
            }
        });

        searchView.setOnCloseListener(() -> {
            updatePlans();
            return false;
        });
    }

    private void updateFriendsPlans() {
        swipeRefreshLayout.setRefreshing(true);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(Session.SESSION_FILE, 0);
        String userUuid = sharedPreferences.getString(Session.USER, null);
        UserService.getFriendsPlans(getActivity(), userUuid, new Service.ClientResponse<ArrayList<Plan>>() {
            @Override
            public void onSuccess(ArrayList<Plan> result) {
                planAdapter.setFriendsPlansData(result);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
