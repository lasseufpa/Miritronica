package com.example.igor.miriapp;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Intent;

import java.util.Set;

/**
 * Created by Igor on 07/12/2016.
 */

public class DeviceList extends ListActivity {
    BluetoothAdapter meuBluetoothAdapter2 = null;
    static String  MAC = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        ArrayAdapter<String> ArrayBluetooth = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        meuBluetoothAdapter2 = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> dispositivosPareados = meuBluetoothAdapter2.getBondedDevices();
        if(dispositivosPareados.size() >0){
            for (BluetoothDevice dispositivo : dispositivosPareados){
                String nome= dispositivo.getName();
                String macB= dispositivo.getAddress();
                ArrayBluetooth.add(nome+"\n"+macB);

            }
        }
        setListAdapter(ArrayBluetooth);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String info= ((TextView) v).getText().toString();
        String macAddress = info.substring(info.length() -17);
        Intent retornaAdress = new Intent();
        retornaAdress.putExtra(MAC,macAddress);
        setResult(RESULT_OK,retornaAdress);
        finish();
    }
}
