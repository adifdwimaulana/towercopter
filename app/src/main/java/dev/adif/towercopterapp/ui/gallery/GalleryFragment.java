package dev.adif.towercopterapp.ui.gallery;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Set;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import dev.adif.towercopterapp.R;
import dev.adif.towercopterapp.ui.home.HomeFragment;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    // Bluetooth Config
    final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    int REQUEST_ENABLE_BT = 0;
    int REQUEST_DISCOVERABLE_BT = 0;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        Switch switchBtn = (Switch) root.findViewById(R.id.switchBtn);
        final TextView deviceName = (TextView) root.findViewById(R.id.deviceList);

        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    if(!mBluetoothAdapter.isEnabled()){
                        Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBTIntent, REQUEST_ENABLE_BT);
                        // REQUEST_ENABLE_BT = 1;
                    }
                    if(!mBluetoothAdapter.isDiscovering()){
                        Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                        startActivityForResult(enableBTIntent, REQUEST_DISCOVERABLE_BT);
                        // REQUEST_DISCOVERABLE_BT = 1;
                    }

                    // Paired Devices
                     Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();
                     int i = 1;
                     for(BluetoothDevice device : devices){
                         deviceName.append("\n" + i + "." + " " + device.getName() + ", " + device);
                         Log.d("Device ", device.getName());
                         i++;
                     }

//                    mBluetoothAdapter.startDiscovery();
//                    BroadcastReceiver mReceiver = new BroadcastReceiver() {
//                        @Override
//                        public void onReceive(Context context, Intent intent) {
//                            String action = intent.getAction();
//
//                            if(BluetoothDevice.ACTION_FOUND.equals(action)){
//                                Set<BluetoothDevice> device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                                for(BluetoothDevice d : device){
//                                    Log.d("Device: ", d.getName());
//                                    Toast.makeText(getContext(), "Device: " + d.getName(), Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        }
//                    };
//
//                    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//                    getActivity().registerReceiver(mReceiver, filter);

                    Toast.makeText(getContext(), "Bluetooth Enabled!", Toast.LENGTH_SHORT).show();
                } else {
                    mBluetoothAdapter.disable();
                    // REQUEST_ENABLE_BT = 0;
                    // REQUEST_DISCOVERABLE_BT = 0;
                    Toast.makeText(getContext(), "Bluetooth Disabled!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }
}