package com.pickachu.momma.fragments.DialogFragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.pickachu.momma.MommaApp;
import com.pickachu.momma.Network.NetworkManager;
import com.pickachu.momma.database.DbSingleton;
import com.pickachu.momma.database.SqliteHelper;

/**
 * Created by ishaangarg on 30/12/15.
 */
public class BaseDialogFragment extends DialogFragment {

    private NetworkManager networkManager;
    private SqliteHelper sqliteHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Light_NoTitleBar);
        sqliteHelper = DbSingleton.getInstance();
        networkManager = NetworkManager.newInstance(getActivity(), MommaApp.BASE_URL);
    }

    public SqliteHelper getSqliteHelper() {
        return sqliteHelper;
    }

    public void setSqliteHelper(SqliteHelper sqliteHelper) {
        this.sqliteHelper = sqliteHelper;
    }

    public NetworkManager getNetworkManager() {
        return networkManager;
    }

    public void setNetworkManager(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }
}