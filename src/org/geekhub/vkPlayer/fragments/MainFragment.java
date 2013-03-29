package org.geekhub.vkPlayer.fragments;

import android.media.MediaPlayer;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockFragment;
import com.perm.kate.api.Api;
import com.perm.kate.api.Audio;
import com.perm.kate.api.KException;
import org.geekhub.vkPlayer.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.geekhub.vkPlayer.adapters.AudioAdapter;
import org.geekhub.vkPlayer.utils.Account;
import org.geekhub.vkPlayer.utils.Constants;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;


public class MainFragment extends SherlockFragment{

	private View view;
    private Account user = new Account();
    private Api api;
    private ArrayList<Audio> audios = new ArrayList<Audio>();
    private ListView list;
    private static MediaPlayer mp = new MediaPlayer();
    private Audio p_a = new Audio();

    private String Tag = "Main_Fragment";    
    private static int count;
  

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_audio, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        user.restore(getActivity().getApplicationContext());

        api = new Api(user.access_token, Constants.API_ID);

        if(null != savedInstanceState){
            this.audios = (ArrayList<Audio>)savedInstanceState.getSerializable("audios");
            this.p_a = (Audio)savedInstanceState.getSerializable("p_audio");
        }

        list = (ListView)getView().findViewById(R.id.list_view);
        list.setAdapter(new AudioAdapter(getActivity(), audios ));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	
        	@Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                 count = i;
                startPlay();
            }
        });
        updateList();       

    }

    public void onSaveInstanceState(Bundle out)
    {
        out.putSerializable("audios", this.audios);
        out.putSerializable("p_audio", this.p_a);
    }

    private void startPlay(){
    	Audio a = audios.get(count);
    	if(p_a.aid == a.aid){
            if(mp.isPlaying()){
                mp.pause();
            } else {
                mp.start();
            }
        } else {
            mp.reset();

            try{
                mp.setDataSource(a.url);
                mp.prepare();
                p_a = a;
            } catch (IOException e){
                Log.d(Tag, "Audio url io exception");
            }
            mp.start();
            count = count + 1;
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer mp) {
					startPlay();
				}
			});
        }
    	
    }

    private void updateList(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                getAudios();
                updateAdapter();
            }
        }).start();
    }    


    private void getAudios(){
        try{
            audios = api.getAudio(user.user_id, null, null, null);
        } catch (IOException e){
            Log.d(Tag, "Connection lost");
        } catch (JSONException e){
            Log.d(Tag, "Response parse error");
        } catch (KException e){
            Log.d(Tag, "Kate exception");
        }
    }

    private void updateAdapter(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                list.setAdapter(new AudioAdapter(getActivity(), audios));
            }
        });
    }

}
