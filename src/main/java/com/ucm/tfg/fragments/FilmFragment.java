package com.ucm.tfg.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Toast;

import com.ucm.tfg.R;
import com.ucm.tfg.Session;
import com.ucm.tfg.Utils;
import com.ucm.tfg.activities.FilmActivity;
import com.ucm.tfg.adapters.FilmAdapter;
import com.ucm.tfg.entities.Film;
import com.ucm.tfg.requests.FilmRequest;
import com.ucm.tfg.requests.Request;
import com.ucm.tfg.requests.UserRequest;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FilmFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FilmFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilmFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;
    private FilmAdapter filmAdapter;
    private SearchView searchView;
    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;

    public FilmFragment() {
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
    public static FilmFragment newInstance(String param1, String param2) {
        FilmFragment fragment = new FilmFragment();
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

        View view = inflater.inflate(R.layout.fragment_film, container, false);

        GridView gridView = (GridView) view.findViewById(R.id.films);

        // Creating the adapter for each film
        filmAdapter = new FilmAdapter(getActivity());
        // When click on a film, redirects to the film activity
        filmAdapter.addFilmOnClickListener((Film film, View v) -> {
            // this is used for an animation effect when changing interfaces
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(
                            getActivity(),
                            Pair.create(v, "film_poster")
                    );
            Intent i = new Intent(getActivity(), FilmActivity.class);
            i.putExtra("film", film);
            startActivity(i, optionsCompat.toBundle());
        });
        gridView.setAdapter(filmAdapter);

        // Swipe down action -> refresh data
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            updateFilms();
        });

        toolbar = getActivity().findViewById(R.id.toolbar);
        updateFilms();
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();

        updateFilms();
    }

    @Override
    // Determines if the fragment is visible to the user
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (toolbar != null) {
                // Clearing toolbar and changes the menu for this fragment
                toolbar.getMenu().clear();
                toolbar.inflateMenu(R.menu.menu_films);
                toolbar.setTitle(R.string.action_films);
                // Action search
                searchView = (SearchView) toolbar.getMenu().findItem(R.id.action_search).getActionView();

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        if (!Utils.isNullOrEmpty(s)) {
                            searchFilms(s);
                        }
                        return false;
                    }
                });

                searchView.setOnCloseListener(() -> {
                    updateFilms();
                    return false;
                });
            }
        }
    }

    private void updateFilms() {
        long userId = getActivity().getSharedPreferences(Session.SESSION_FILE, 0).getLong(Session.USER, 0);
        if (userId != 0) {
            swipeRefreshLayout.setRefreshing(true);
            UserRequest.getUserFilmsById(getActivity(), userId, new Request.ClientResponse<ArrayList<Film>>() {
                @Override
                public void onSuccess(ArrayList<Film> result) {
                    filmAdapter.setData(result);
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

    private void searchFilms(String name) {
        if (!Utils.isNullOrEmpty(name)) {
            swipeRefreshLayout.setRefreshing(true);
            FilmRequest.searchFilmsByName(getActivity(), name, new Request.ClientResponse<ArrayList<Film>>() {
                @Override
                public void onSuccess(ArrayList<Film> result) {
                    filmAdapter.setData(result);
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

        void onFragmentLoaded();

    }
}
