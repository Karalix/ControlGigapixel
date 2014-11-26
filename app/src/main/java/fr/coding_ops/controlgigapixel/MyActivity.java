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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class MyActivity extends Activity {

    private static final int REQUEST_ENABLE_BT = 0x0001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth non supporté", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Bluetooth marche grave bien !", Toast.LENGTH_SHORT).show();

            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }


            List<BluetoothDevice> pairedDevices = new ArrayList<BluetoothDevice>(mBluetoothAdapter.getBondedDevices());
            ListView listView = (ListView)findViewById(R.id.listBluetoothDevices);
            ArrayAdapter<BluetoothDevice> adapter = new ArrayAdapter<BluetoothDevice>(this.getApplicationContext(),
                    android.R.layout.simple_list_item_1, pairedDevices);
            listView.setAdapter(adapter);

        }
    }

}