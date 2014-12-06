package fr.coding_ops.controlgigapixel;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zerokol.views.JoystickView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class MyActivity extends Activity {

    private static final int REQUEST_ENABLE_BT = 0x0001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        //Here we check if the device has bluetooth capability
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
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

            //Here we fill the listView with paired bluetooth devices
            List<BluetoothDevice> pairedDevices = new ArrayList<BluetoothDevice>(mBluetoothAdapter.getBondedDevices());
            //ListView listView = (ListView)findViewById(R.id.listBluetoothDevices);
            //ArrayAdapter<BluetoothDevice> adapter = new ArrayAdapter<BluetoothDevice>(this.getApplicationContext(),
            //        android.R.layout.simple_list_item_1, pairedDevices);
            //listView.setAdapter(adapter);

            JoystickView joystickView = (JoystickView)findViewById(R.id.joystickView) ;
            final TextView joystickState = (TextView)findViewById(R.id.joystickState);

            //Event listener that always returns the variation of the angle in degrees, motion power in percentage and direction of movement
            joystickView.setOnJoystickMoveListener(new JoystickView.OnJoystickMoveListener() {
                @Override
                public void onValueChanged(int i, int i2, int i3) {

                    joystickState.setText("Zob : "+String.valueOf(i)+" "+String.valueOf(i2)+" "+String.valueOf(i3));
                }
            }, JoystickView.DEFAULT_LOOP_INTERVAL);


        }
    }

}