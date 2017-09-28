package com.example.igor.miriapp;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.hardware.camera2.params.ColorSpaceTransform;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;
import 	android.os.Handler;
public class MainActivity extends AppCompatActivity {
    private OutputStream outputStream;
    ImageButton upButton, downButton, leftButton, rightButton;
    final BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
    BluetoothDevice device = null;
    BluetoothSocket socket = null;
    final int bluetoothRequest = 1;
    final int bluetoothPair = 2;
    boolean connect = false;
    private static String MAC = null;
    private boolean pressed;
    UUID meuUUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    private Handler mHandler;
    FloatingActionButton bluetoothBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        upButton = (ImageButton) findViewById(R.id.upBtn);
        downButton = (ImageButton) findViewById(R.id.downBtn);
        leftButton = (ImageButton) findViewById(R.id.leftBtn);
        rightButton = (ImageButton) findViewById(R.id.rightBtn);
        bluetoothBtn = (FloatingActionButton) findViewById(R.id.bluetoothButton);
        if (bluetooth == null) {
            Toast.makeText(getApplicationContext(), "Seu dispositivo não suporta bluetooth", Toast.LENGTH_LONG).show();
            finish();
        }
        //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //      .setAction("Action", null).show();
        else {
            if (!bluetooth.isEnabled()) {
                Intent ativaBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(ativaBluetooth, bluetoothRequest);
            }
        }
        bluetoothBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (connect) {
                    //disconnect
                    try {
                        socket.close();
                        bluetoothBtn.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        connect = false;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //connect
                    Intent abreLista = new Intent(MainActivity.this, DeviceList.class);
                    startActivityForResult(abreLista, bluetoothPair);
                }


            }
        });


        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (connect) {
                    //while(upButton.isPressed()){
                    try {
                        BlockOthers(upButton,false);
                        outputStream.write("u".getBytes());
                        //Toast.makeText(getApplicationContext(), "u enviado", Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        //Toast.makeText(getApplicationContext(), "u n enviado", Toast.LENGTH_LONG).show();
                    }//}
                    BlockOthers(upButton,true);
                }
            }
        });
/*
        upButton.setOnTouchListener(new View.OnTouchListener() {


            @Override public boolean onTouch(View v, MotionEvent event) {
                if(event.getPointerCount()>1){
                    return true;
                }
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mHandler != null) return true;
                        BlockOthers(upButton,false);
                        mHandler = new Handler();
                        mHandler.postDelayed(mAction, 500);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mHandler == null) return true;
                        mHandler.removeCallbacks(mAction);
                        mHandler = null;
                        BlockOthers(upButton,true);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        if (mHandler == null) return true;
                        mHandler.removeCallbacks(mAction);
                        mHandler = null;
                        BlockOthers(upButton,true);
                        break;
                }
                return false;
            }

            Runnable mAction = new Runnable() {
                @Override public void run() {
                    if (connect) {
                        try {
                            outputStream.write("u".getBytes());
                            //Toast.makeText(getApplicationContext(), "u enviado", Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                            //Toast.makeText(getApplicationContext(), "u n enviado", Toast.LENGTH_LONG).show();
                        }
                    }
                    mHandler.postDelayed(this, 500);
                }
            };
        });

*/
        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (connect) {
                    // while(downButton.isPressed()){
                    try {
                        BlockOthers(downButton,false);
                        outputStream.write("d".getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }//}
                    BlockOthers(downButton,true);
                }
            }
        });
/*
        downButton.setOnTouchListener(new View.OnTouchListener() {

            @Override public boolean onTouch(View v, MotionEvent event) {
                if(event.getPointerCount()>1){
                    return true;
                }
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mHandler != null) return true;
                        BlockOthers(downButton,false);
                        mHandler = new Handler();
                        mHandler.postDelayed(mAction, 500);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mHandler == null) return true;
                        mHandler.removeCallbacks(mAction);
                        mHandler = null;
                        BlockOthers(downButton,true);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        if (mHandler == null) return true;
                        mHandler.removeCallbacks(mAction);
                        mHandler = null;
                        BlockOthers(downButton,true);
                        break;
                }
                return false;
            }

            Runnable mAction = new Runnable() {
                @Override public void run() {
                    if (connect) {
                        try {
                            outputStream.write("d".getBytes());
                            //Toast.makeText(getApplicationContext(), "d enviado", Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                            //Toast.makeText(getApplicationContext(), "d n enviado", Toast.LENGTH_LONG).show();
                        }
                    }
                    mHandler.postDelayed(this, 500);
                }
            };
        });
*/

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (connect) {
                    //           while(leftButton.isPressed()){
                    try {
                        BlockOthers(leftButton,false);
                        outputStream.write("l".getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }//}
                    BlockOthers(leftButton,true);
                }
            }
        });
/*
        leftButton.setOnTouchListener(new View.OnTouchListener() {

            @Override public boolean onTouch(View v, MotionEvent event) {
                if(event.getPointerCount()>1){
                    return true;
                }
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mHandler != null) return true;
                        BlockOthers(rightButton,false);
                        mHandler = new Handler();
                        mHandler.postDelayed(mAction, 500);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mHandler == null) return true;
                        mHandler.removeCallbacks(mAction);
                        mHandler = null;
                        BlockOthers(leftButton,true);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        if (mHandler == null) return true;
                        mHandler.removeCallbacks(mAction);
                        mHandler = null;
                        BlockOthers(leftButton,true);
                        break;
                }
                return false;
            }

            Runnable mAction = new Runnable() {
                @Override public void run() {
                    if (connect) {
                        try {
                            outputStream.write("l".getBytes());
                            //Toast.makeText(getApplicationContext(), "l enviado", Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                            //Toast.makeText(getApplicationContext(), "l n enviado", Toast.LENGTH_LONG).show();
                        }
                    }
                    mHandler.postDelayed(this, 500);
                }
            };
        });
*/

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (connect) {
//                    while (rightButton.isPressed()){
                    try {
                        BlockOthers(rightButton,false);
                        outputStream.write("r".getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }//}
                    BlockOthers(rightButton,true);
                }
            }
        });
/*
        rightButton.setOnTouchListener(new View.OnTouchListener() {

            @Override public boolean onTouch(View v, MotionEvent event) {
                if(event.getPointerCount()>1){
                    return true;
                }
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mHandler != null) return true;
                        BlockOthers(rightButton,false);
                        mHandler = new Handler();
                        mHandler.postDelayed(mAction, 500);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mHandler == null) return true;
                        mHandler.removeCallbacks(mAction);
                        mHandler = null;
                        BlockOthers(rightButton,true);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        if (mHandler == null) return true;
                        mHandler.removeCallbacks(mAction);
                        mHandler = null;
                        BlockOthers(rightButton,true);
                        break;
                }
                return false;
            }

            Runnable mAction = new Runnable() {
                @Override public void run() {
                    if (connect) {
                        try {
                            outputStream.write("r".getBytes());
                            //Toast.makeText(getApplicationContext(), "r enviado", Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                            //Toast.makeText(getApplicationContext(), "r n enviado", Toast.LENGTH_LONG).show();
                        }
                    }
                    mHandler.postDelayed(this, 500);
                }
            };      });
*/

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            if (findViewById(R.id.content_main).getVisibility() == View.VISIBLE) {
                findViewById(R.id.content_main).setVisibility(View.INVISIBLE);
                findViewById(R.id.info_main).setVisibility(View.VISIBLE);
                item.setTitle("VOLTAR");
            } else {
                findViewById(R.id.info_main).setVisibility(View.INVISIBLE);
                findViewById(R.id.content_main).setVisibility(View.VISIBLE);
                item.setTitle("INFO");
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case bluetoothRequest:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(getApplicationContext(), "Bluetooth Ativado", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "O Bluetooth NÃO Foi Ativado, encerrando a aplicação", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            case bluetoothPair:
                if (resultCode == RESULT_OK) {
                    MAC = data.getExtras().getString(DeviceList.MAC);
                    //  Toast.makeText(getApplicationContext(),"MAC: "+MAC,Toast.LENGTH_LONG).show();
                    device = bluetooth.getRemoteDevice(MAC);
                    try {
                        socket = device.createRfcommSocketToServiceRecord(meuUUID);
                        socket.connect();
                        outputStream = socket.getOutputStream();
                        Toast.makeText(getApplicationContext(), "Conectado com: " + MAC, Toast.LENGTH_LONG).show();
                        connect = true;
                        bluetoothBtn.setBackgroundTintList(ColorStateList.valueOf(Color.BLUE));
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(), "Ocorreu um erro \n " + e, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Conexão não estabelecida", Toast.LENGTH_LONG).show();

                }
        }
    }
    public void BlockOthers(ImageButton b, boolean f){
        leftButton.setEnabled((b==leftButton)||f);
        rightButton.setEnabled((b==rightButton)||f);
        downButton.setEnabled((b==downButton)||f);
        upButton.setEnabled((b==upButton)||f);

        /*   if(b == upButton){
            leftButton.setEnabled(f);
            rightButton.setEnabled(f);
            downButton.setEnabled(f);
        }else{
            if(b== downButton){
                rightButton.setEnabled(f);
                leftButton.setEnabled(f);
                upButton.setEnabled(f);
            }
            else{
                if(b== leftButton){
                    upButton.setEnabled(f);
                    downButton.setEnabled(f);
                    rightButton.setEnabled(f);
                }else{
                    if(b==rightButton)
                        upButton.setEnabled(f);
                    downButton.setEnabled(f);
                    leftButton.setEnabled(f);
                }
            }
        }
   */ }
}
