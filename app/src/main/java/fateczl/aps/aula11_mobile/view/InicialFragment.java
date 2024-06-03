package fateczl.aps.aula11_mobile.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fateczl.aps.aula11_mobile.R;

public class InicialFragment extends Fragment {

    private View view;
    private TextView tvInicial;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_inicial, container, false);
        tvInicial = view.findViewById(R.id.tvInicial);
        tvInicial.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        return view;
    }
}