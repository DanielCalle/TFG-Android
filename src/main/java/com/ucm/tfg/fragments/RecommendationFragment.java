package com.ucm.tfg.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.ucm.tfg.R;
import com.ucm.tfg.Session;
import com.ucm.tfg.Utils;
import com.ucm.tfg.activities.FilmActivity;
import com.ucm.tfg.adapters.PlanAdapter;
import com.ucm.tfg.adapters.RecommendationListAdapter;
import com.ucm.tfg.entities.Film;
import com.ucm.tfg.service.RecommendationService;
import com.ucm.tfg.service.Service;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecommendationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecommendationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecommendationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private SearchView searchView;
    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecommendationListAdapter recommendationListAdapter;

    public RecommendationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecommendationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecommendationFragment newInstance(String param1, String param2) {
        RecommendationFragment fragment = new RecommendationFragment();
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

        View view = inflater.inflate(R.layout.fragment_recommendation, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recommendations);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recommendationListAdapter = new RecommendationListAdapter(getActivity());
        recommendationListAdapter.addFilmOnClickListener((Film film, View v) -> {
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(
                            getActivity(),
                            Pair.create(v, "film_poster")
                    );
            Intent i = new Intent(getActivity(), FilmActivity.class);
            i.putExtra("film", film);
            startActivity(i, optionsCompat.toBundle());
        });
        recyclerView.setAdapter(recommendationListAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            updateRecommendations();
        });

        toolbar = getActivity().findViewById(R.id.toolbar);

        return view;
    }

    public void setActive() {
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.menu_recommendations);
        toolbar.setTitle(R.string.action_recommendations);
        searchView = (SearchView) toolbar.getMenu().findItem(R.id.action_search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!Utils.isNullOrEmpty(s)) {
                    Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        searchView.setOnCloseListener(() -> {
            updateRecommendations();
            return false;
        });

        updateRecommendations();
    }

    @Override
    public void onStart(){
        super.onStart();

        updateRecommendations();
    }

    private void updateRecommendations() {
        swipeRefreshLayout.setRefreshing(true);
        if (Session.user != null) {
            RecommendationService.getRecommendedFilms(getActivity(), Session.user.getId(), new Service.ClientResponse<ArrayList<Film>>() {
                @Override
                public void onSuccess(ArrayList<Film> result) {
                    recommendationListAdapter.setRecommendedData(result);
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
            RecommendationService.getTrendingFilms(getActivity(), new Service.ClientResponse<ArrayList<Film>>() {
                @Override
                public void onSuccess(ArrayList<Film> result) {
                    recommendationListAdapter.setTrendingData(result);
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
            RecommendationService.getPremiereFilms(getActivity(), new Service.ClientResponse<ArrayList<Film>>() {
                @Override
                public void onSuccess(ArrayList<Film> result) {
                    recommendationListAdapter.setPremiereData(result);
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
