package fateczl.aps.aula11_mobile.view;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.sql.SQLException;
import java.util.List;

import fateczl.aps.aula11_mobile.R;
import fateczl.aps.aula11_mobile.controller.TimeController;
import fateczl.aps.aula11_mobile.model.Time;
import fateczl.aps.aula11_mobile.persistence.TimeDao;

public class TimeFragment extends Fragment {

    private View view;

    private EditText etCodigoTime, etNomeTime, etCidadeTime;
    private Button btnInserirTime, btnExcluirTime, btnModificarTime, btnBuscarTime, btnListarTime;
    private TextView tvListagemTime;

    private TimeController timeController;

    public TimeFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_time, container, false);

        //Edit Texts
        etCodigoTime = view.findViewById(R.id.etCodigoTime);
        etNomeTime = view.findViewById(R.id.etNomeTime);
        etCidadeTime = view.findViewById(R.id.etCidadeTime);

        //Buttons
        btnInserirTime = view.findViewById(R.id.btnInserirTime);
        btnExcluirTime = view.findViewById(R.id.btnExcluirTime);
        btnModificarTime = view.findViewById(R.id.btnModificarTime);
        btnBuscarTime = view.findViewById(R.id.btnBuscarTime);
        btnListarTime = view.findViewById(R.id.btnListarTime);

        //Text View
        tvListagemTime = view.findViewById(R.id.tvListagemTime);
        tvListagemTime.setMovementMethod(new ScrollingMovementMethod());

        timeController = new TimeController(new TimeDao(view.getContext()));

        btnInserirTime.setOnClickListener(click -> acaoInserir());
        btnModificarTime.setOnClickListener(click -> acaoModificar());
        btnExcluirTime.setOnClickListener(click -> acaoExcluir());
        btnBuscarTime.setOnClickListener(click -> acaoBuscar());
        btnListarTime.setOnClickListener(click -> acaoListar());

        return view;
    }

    private void acaoInserir() {
        try {
            Time time = montaTime();
            timeController.inserir(time);
            Toast.makeText(view.getContext(), "Time Inserido com Sucesso", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();
    }

    private void acaoModificar() {
        try {
            Time time = montaTime();
            timeController.modificar(time);
            Toast.makeText(view.getContext(), "Time Modificado com Sucesso com Sucesso", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();
    }

    private void acaoExcluir() {
        try {
            Time time = montaTime();
            timeController.deletar(time);
            Toast.makeText(view.getContext(), "Time Excluido com Sucesso", Toast.LENGTH_LONG).show();
            limpaCampos();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoBuscar() {
        try {
            Time time = montaTime();
            time = timeController.buscar(time);
            if(time.getNome() != null){
                preencheTime(time);
            }else{
                Toast.makeText(view.getContext(), "Time Não Encontrado", Toast.LENGTH_LONG).show();
                limpaCampos();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoListar() {
        try {
            List<Time> listaDeTimes = timeController.listar();
            StringBuffer stringBuffer = new StringBuffer();
            for(Time t : listaDeTimes){
                stringBuffer.append(t.toString() + "\n");
            }
            tvListagemTime.setText(stringBuffer.toString());

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG);
        }
    }

    private Time montaTime() {
        Time t = new Time();
        t.setCodigo(Integer.parseInt(etCodigoTime.getText().toString()));
        t.setNome(etNomeTime.getText().toString());
        t.setCidade(etCidadeTime.getText().toString());
        return t;

    }

    private void preencheTime(Time t) {
        etCodigoTime.setText(String.valueOf(t.getCodigo()));
        etNomeTime.setText(t.getNome());
        etCidadeTime.setText(t.getCidade());
    }

    private void limpaCampos() {
        etCodigoTime.setText("");
        etNomeTime.setText("");
        etCidadeTime.setText("");
    }


}