package ca.ghost_team.sapp.navigation;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ca.ghost_team.sapp.MainActivity;
import ca.ghost_team.sapp.R;
import ca.ghost_team.sapp.adapter.ListMessageAdapter;
import ca.ghost_team.sapp.adapter.MessageAdapter;
import ca.ghost_team.sapp.databinding.LayoutMessageBinding;
import ca.ghost_team.sapp.viewmodel.MessageViewModel;

public class Message extends Fragment {
    private static final String TAG = Message.class.getSimpleName();
    private LayoutMessageBinding binding;
    private RecyclerView recyclerViewListMessage;
    private ListMessageAdapter mMessageAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_message, container, false);
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((MainActivity)context).setTitle(R.string.message);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Associer le champ
        recyclerViewListMessage = binding.recyclerViewListMessage;


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewListMessage.setLayoutManager(linearLayoutManager);

        // Init Adapter
        mMessageAdapter = new ListMessageAdapter(getActivity());
        recyclerViewListMessage.setAdapter(mMessageAdapter);
        MessageViewModel messageViewModel = new ViewModelProvider(this).get(MessageViewModel.class);

        messageViewModel.getAllMessagesReceiver().observe(getViewLifecycleOwner(), conversation -> {
            mMessageAdapter.addConversation(conversation);
            mMessageAdapter.notifyDataSetChanged();

            Log.i(TAG, "RecyclerView Message correct");
        });

    }
}
