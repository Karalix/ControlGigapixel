package fr.coding_ops.controlgigapixel;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zerokol.views.JoystickView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class MyActivity extends Activity {

    private List<String> mArrayAdapter = new ArrayList<String>();
    private static final int REQUEST_ENABLE_BT = 0x0001;
    private BluetoothAdapter mBluetoothAdapter = null ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        //Here we check if the device has bluetooth capability
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth non supporté", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Bluetooth marche grave bien !", Toast.LENGTH_SHORT).show();

            //Here we check if bluetooth is enabled or not
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }

            BroadcastReceiver mReceiver = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    // When discovery finds a device
                    if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                        // Get the BluetoothDevice object from the Intent
                        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                        // Add the name and address to an array adapter to show in a ListView
                        Toast.makeText(getApplication(), device.getName() + "\n" + device.getAddress(), Toast.LENGTH_SHORT).show();
                    }
                }
            };

            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mReceiver, filter);

//            mBluetoothAdapter.startDiscovery();

            //Here we fill the listView with paired bluetooth devices
            ListView listView = (ListView)findViewById(R.id.listBluetoothDevices);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getApplicationContext(),
                    android.R.layout.simple_list_item_1, mArrayAdapter);
            listView.setAdapter(adapter);

/*
            JoystickView joystickView = (JoystickView)findViewById(R.id.joystickView) ;
            final TextView joystickState = (TextView)findViewById(R.id.joystickState);

            //Event listener that always returns the variation of the angle in degrees, motion power in percentage and direction of movement
            joystickView.setOnJoystickMoveListener(new JoystickView.OnJoystickMoveListener() {
                @Override
                public void onValueChanged(int i, int i2, int i3) {

                    joystickState.setText("Angle : "+String.valueOf(i)+"\nMotion : "+String.valueOf(i2)+"\nDirection :  "+String.valueOf(i3));
                }
            }, JoystickView.DEFAULT_LOOP_INTERVAL);
*/

        }
    }

    public void startDiscover(View view)
    {
        if(mBluetoothAdapter != null) {
            mBluetoothAdapter.startDiscovery();
        }
    }

}