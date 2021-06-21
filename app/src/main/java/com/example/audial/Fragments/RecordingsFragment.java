package com.example.audial.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.audial.R;
import com.example.audial.Listener.onSelectListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecordingsFragment extends Fragment implements onSelectListener {

    View view;

    File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/VRecorder");


    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_recordings,container,false);


        displayFiles();
        return view;



    }


    private void displayFiles() {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_records);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        List<File> fileList = new ArrayList<>(findFile(path));
        RecAdapter recAdapter = new RecAdapter(getContext(), fileList, this);
        recyclerView.setAdapter(recAdapter);
    }

    public ArrayList<File> findFile(File file){
        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();

        assert files != null;
        for(File singleFile : files){
            if (singleFile.getName().toLowerCase().endsWith(".amr")){
                arrayList.add(singleFile);
            }
        }
        return arrayList;
    }

    @Override
    public void onSelected(File file) {
        Uri uri = FileProvider.getUriForFile(requireContext(), requireContext().getApplicationContext().getPackageName() + ".provider",file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri,"audio/x-wav");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        requireContext().startActivity(intent);
    }

    @Deprecated
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            displayFiles();
        }
    }
}
