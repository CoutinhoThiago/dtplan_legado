package br.com.sportingforlife.ui.treino;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProvider;

import br.com.sportingforlife.databinding.FragmentTreinoBinding;

public class TreinoFragment extends Fragment {

    private FragmentTreinoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TreinoViewModel dashboardViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TreinoViewModel.class);

        binding = FragmentTreinoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textTreino;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}