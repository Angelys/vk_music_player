package org.geekhub.vkPlayer.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockFragment;
import com.perm.kate.api.Api;
import com.perm.kate.api.Audio;
import com.perm.kate.api.KException;
import org.geekhub.vkPlayer.PlayerService;
import org.geekhub.vkPlayer.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import org.geekhub.vkPlayer.adapters.AudioAdapter;
import org.geekhub.vkPlayer.utils.Account;
import org.geekhub.vkPlayer.utils.Constants;
import org.holoeverywhere.widget.Toast;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;


public class MainFragment extends SherlockFragment {

	private View view;
    private Account user = new Account();
    private Api api;
    private ArrayList<Audio> audios = new ArrayList<Audio>();
    private ListView list;

    private String Tag = "Main_Fragment";    
    private static int count;
    
    final String LOG_TAG = "myLogs";
    
    private Button playButton;
  

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	Log.d(LOG_TAG, "--- MainFragment - onCreateView() --- ");
        view = inflater.inflate(R.layout.my_audio, container, false);
        
        Button btnPlay = (Button) view.findViewById(R.id.btnPlay);
        Button btnFwd = (Button) view.findViewById(R.id.btnFwd);
        Button btnRwd = (Button) view.findViewById(R.id.btnRwd);
        
        
        btnPlay.setOnClickListener(new OnClickListener() {
          public void onClick(View v) {
        	  Log.d(LOG_TAG, "--- MainFragment - PLAY PRESSED" + count);
        	  startPlay();
          }
        }); 
        btnRwd.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
          	  Log.d(LOG_TAG, "--- MainFragment - PLAY PRESSED" + count);
          	  count = count - 1;
          	  startPlay();
            }
          }); 
        btnFwd.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
          	  Log.d(LOG_TAG, "--- MainFragment - PLAY PRESSED" + count);
          	 count = count + 1;
          	  startPlay();
            }
          }); 
        
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	Log.d(LOG_TAG, "--- MainFragment - onActivityCreated(savedInstanceState) --- " + savedInstanceState);
        super.onActivityCreated(savedInstanceState);

        user.restore(getActivity().getApplicationContext());

        api = new Api(user.access_token, Constants.API_ID);

        if(null != savedInstanceState){
        	Log.d(LOG_TAG, "--- MainFragment - onActivityCreated() --- (null != savedInstanceState)");
            this.audios = (ArrayList<Audio>)savedInstanceState.getSerializable("audios");
        }
        list = (ListView)getView().findViewById(R.id.list_view);
        list.setAdapter(new AudioAdapter(getActivity(), audios ));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	
        	@Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
        		Log.d(LOG_TAG, "--- MainFragment - onItemClick() --- i = " + i + " l = " + l );
                 count = i;
                startPlay();
            }
        });
        updateList();       

    }

    public void onSaveInstanceState(Bundle out){
    	Log.d(LOG_TAG, "--- MainFragment - onSaveInstanceState(out) ---" + out);
        out.putSerializable("audios", this.audios);
    }

    private void startPlay(){
    	if(count==0){
    		count = 1;
    	}
    	Log.d(LOG_TAG, "--- MainFragment - startPlay() ---");
    	Audio a = audios.get(count);
    	Log.d(LOG_TAG, "--- MainFragment - startPlay() -audios.get(count) - count = " + count);

        if(PlayerService.INSTANCE != null){
        	Log.d(LOG_TAG, "--- MainFragment - startPlay() -(PlayerService.INSTANCE != null)");
            PlayerService.INSTANCE.onComplete = new Runnable(){
                @Override
                public void run() {
                	Log.d(LOG_TAG, "--- MainFragment - startPlay() -PlayerService.INSTANCE.onComplete = new Runnable()");
                    if(PlayerService.INSTANCE != null){
                    	Log.d(LOG_TAG, "--- MainFragment - startPlay() -run() - (PlayerService.INSTANCE != null)");
                        if(audios.get(count + 1) != null){
                        	Log.d(LOG_TAG, "--- MainFragment - startPlay() -run() - (audios.get(count + 1) != null)" + count);
                            PlayerService.INSTANCE.play(audios.get(count + 1));
                            count++;
                        }
                    }
                }
            };
            Log.d(LOG_TAG, "--- MainFragment - startPlay() -PlayerService.INSTANCE.play(a)");
            PlayerService.INSTANCE.play(a);
        }

    	
    }

    private void updateList(){
    	Log.d(LOG_TAG, "--- MainFragment - updateList()");
        new Thread(new Runnable() {
            @Override
            public void run() {
            	Log.d(LOG_TAG, "--- MainFragment - updateList() - new THREAD");
                getAudios();
                updateAdapter();
            }
        }).start();
    }    


    private void getAudios(){
    	Log.d(LOG_TAG, "--- MainFragment - getAudios()");
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
    	Log.d(LOG_TAG, "--- MainFragment - updateAdapter()");
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
            	Log.d(LOG_TAG, "--- MainFragment - updateAdapter() - run()");
                list.setAdapter(new AudioAdapter(getActivity(), audios));
            }
        });
    }

}
