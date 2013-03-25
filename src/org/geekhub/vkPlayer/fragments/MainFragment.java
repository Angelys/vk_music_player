package org.geekhub.vkPlayer.fragments;

import java.util.Collection;

import org.geekhub.vkPlayer.R;
import org.geekhub.vkPlayer.utils.Account;

import com.perm.kate.api.Api;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class MainFragment extends Fragment{
	
	private View view;
	Api api;
	Account account;
	Context context;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_fragment, container, false);
        return view;
        
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity().getApplicationContext();
    }

    private void audioGet() {
        //Общение с сервером в отдельном потоке чтобы не блокировать UI поток (Long uid, Long gid, Long album_id, Collection<Long> aids)
        new Thread(){
            @Override
            public void run(){
                try {
                    api.getAudio(account.user_id, null, null, null);
                    //Показать сообщение в UI потоке 
                    runOnUiThread(successRunnable);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    
    Runnable successRunnable=new Runnable(){
        @Override
        public void run() {
            Toast.makeText(context, "Запись успешно добавлена", Toast.LENGTH_LONG).show();
        }
    };
}
