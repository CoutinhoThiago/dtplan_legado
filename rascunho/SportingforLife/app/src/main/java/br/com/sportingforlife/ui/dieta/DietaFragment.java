package br.com.sportingforlife.ui.dieta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProvider;

import br.com.sportingforlife.databinding.FragmentDietaBinding;

public class DietaFragment extends Fragment {

    private FragmentDietaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DietaViewModel homeViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DietaViewModel.class);

        binding = FragmentDietaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDieta;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}