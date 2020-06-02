package dev.adif.towercopterapp.ui.gallery;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVERABLE_BT = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        Switch switchBtn = (Switch) root.findViewById(R.id.switchBtn);

        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    if(!mBluetoothAdapter.isEnabled()){
                        Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBTIntent, REQUEST_ENABLE_BT);
                    }
                    if(!mBluetoothAdapter.isDiscovering()){
                        Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                        startActivityForResult(enableBTIntent, REQUEST_DISCOVERABLE_BT);
                    }
                    Toast.makeText(getContext(), "Bluetooth Enabled!", Toast.LENGTH_SHORT).show();
                } else {
                    mBluetoothAdapter.disable();
                    Toast.makeText(getContext(), "Bluetooth Disabled!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }
}