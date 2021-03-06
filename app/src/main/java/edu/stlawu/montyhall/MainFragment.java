package edu.stlawu.montyhall;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class MainFragment extends Fragment {

    public static final String PREF_NAME = "MontyHall";
    public static final String NEW_CLICKED = "NEWCLICKED";

    private OnFragmentInteractionListener mListener;

    MediaPlayer mpAbout;
    MediaPlayer mpContin;
    MediaPlayer mpNew;

    public MainFragment() {
        // Required empty public constructor
    }

    // onCreate gets called when the fragment is created
    // before the UI views are constructed
    // Initialize data needed for the fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mpAbout = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.about);
        mpContin = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.contin);
        mpNew = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.newgame);

        // New button views and Initialization
        final View newButton = rootView.findViewById(R.id.new_button);
        newButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 mpNew.start();

               SharedPreferences.Editor pref_ed =
                 getActivity().getSharedPreferences(
                  PREF_NAME, Context.MODE_PRIVATE).edit();
               pref_ed.putBoolean(NEW_CLICKED, true).apply();

                 Intent intent = new Intent(
                   getActivity(), GameActivity.class);
                 getActivity().startActivity(intent);
             }
         });

        // New continue views and Initialization
        final View continueGame = rootView.findViewById(R.id.continue_button);
        continueGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mpContin.start();
                // sharedPreferences
                SharedPreferences.Editor pref_ed = getActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();

                pref_ed.putBoolean(NEW_CLICKED, false).apply();
                pref_ed.putBoolean("WAITGAME", true).apply();

                Intent intent = new Intent(getActivity(), GameActivity.class);
                getActivity().startActivity(intent);
            }
        });

        final View aboutGame = rootView.findViewById(R.id.about_button);
        aboutGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mpAbout.start();
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.about_title_text);
                builder.setMessage(R.string.about);
                builder.setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int a) {
                                return;
                            }
                        });
                builder.show();
            }
        });

        return rootView;
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
