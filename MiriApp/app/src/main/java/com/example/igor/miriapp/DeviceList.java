package com.example.igor.miriapp;

import android.Manifest;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Intent;

import java.io.Console;
import java.util.Set;

/**
 * Created by Igor on 07/12/2016.
 */

public class DeviceList extends ListActivity {
    BluetoothAdapter meuBluetoothAdapter2 = null;
    static String  MAC = null;
    ArrayAdapter<String> ArrayBluetooth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        ArrayBluetooth = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        meuBluetoothAdapter2 = BluetoothAdapter.getDefaultAdapter();
        if (meuBluetoothAdapter2.isDiscovering()) {
            meuBluetoothAdapter2.cancelDiscovery();
        }

        IntentFilter filter = new IntentFilter();

        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, filter);
        int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
        meuBluetoothAdapter2.startDiscovery();

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
    // Create a BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the name and address to an array adapter to show in a ListView
                ArrayBluetooth.add(device.getName() + "\n" + device.getAddress());
                setListAdapter(ArrayBluetooth);
            }
            else{
                if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){

                }
            }

        }

    };

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);

        super.onDestroy();
    }
}
