package com.udacity.bakingapp;

import android.app.NotificationManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.udacity.bakingapp.model.Step;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StepFragment extends Fragment {


    @BindView(R.id.exo_player_view)
    SimpleExoPlayerView exoPlayerView;
    @BindView(R.id.recipe_step_short_description)
    TextView recipeStepShortDescription;
    @BindString(R.string.app_name)
    String appName;

    private Unbinder unbinder;
    private Context context;

    private SimpleExoPlayer exoPlayer;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;

    Step step = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step, parent, false);
        unbinder = ButterKnife.bind(this, rootView);
        context = getContext();
        return  rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        setDescription();
        String url = step.getVideoUrl();
        if(url.isEmpty()){
            exoPlayerView.setVisibility(View.GONE);
        }else {
            Uri uri = Uri.parse(url);
            initializeMediaSession();
            initializePlayer(uri);
        }
    }
    private void setDescription(){
        if(step == null) {
            return;
        }
        if(step.getDescription() == null){
            return;
        }
        if(recipeStepShortDescription == null){
            return;
        }
        recipeStepShortDescription.setText(step.getDescription());
    }

    public void bind(Step step){
        this.step = step;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
//        mMediaSession.setActive(false);
    }

    //Copied from lesson from Udacity
    private void initializePlayer(Uri mediaUri) {

        // Create an instance of the ExoPlayer.
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
        exoPlayerView.setPlayer(exoPlayer);

        // Set the ExoPlayer.EventListener to this activity.
//            exoPlayer.addListener(context);

        // Prepare the MediaSource.
        String userAgent = Util.getUserAgent(context, appName);
        MediaSource mediaSource = new ExtractorMediaSource(
                mediaUri,
                new DefaultDataSourceFactory(
                        context,
                        userAgent),
                new DefaultExtractorsFactory(),
                null,
                null);
        exoPlayer.prepare(mediaSource);
//        exoPlayer.setPlayWhenReady(true);
        exoPlayer.setPlayWhenReady(false);

    }


    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(context, this.getClass().getSimpleName());

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
        //        mMediaSession.setActive(true);
        mMediaSession.setActive(false);

    }

    //Copied from lesson from Udacity
    private void releasePlayer() {
//        mNotificationManager.cancelAll();
        if(exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    /**
     * Media Session Callbacks, where all external clients control the player.
     */
    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            exoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            exoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            exoPlayer.seekTo(0);
        }
    }
}
