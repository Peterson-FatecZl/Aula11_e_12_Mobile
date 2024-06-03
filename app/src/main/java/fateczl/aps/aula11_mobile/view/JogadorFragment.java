package fateczl.aps.aula11_mobile.view;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.sql.SQLException;
import java.util.List;

import fateczl.aps.aula11_mobile.R;
import fateczl.aps.aula11_mobile.controller.JogadorController;
import fateczl.aps.aula11_mobile.controller.TimeController;
import fateczl.aps.aula11_mobile.model.Jogador;
import fateczl.aps.aula11_mobile.model.Time;
import fateczl.aps.aula11_mobile.persistence.JogadorDao;
import fateczl.aps.aula11_mobile.persistence.TimeDao;

public class JogadorFragment extends Fragment {

    private View view;
    private EditText etIDJogador, etNomeJogador, etDataNascJogador, etAlturaJogador, etPesoJogador;
    private Button btnInserirJogador, btnExcluirJogador, btnModificarJogador, btnBuscarJogador, btnListarJogador;
    private TextView tvListagemJogador;
    private Spinner spTimesJogadores;
    private JogadorController jogadorController;
    private TimeController timeController;
    private List<Time> listaDeTimes;

    public JogadorFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_jogador, container, false);

        //Edit Texts
        etIDJogador = view.findViewById(R.id.etIdJogador);
        etNomeJogador = view.findViewById(R.id.etNomeJogador);
        etDataNascJogador = view.findViewById(R.id.etDataNascJogador);
        etAlturaJogador = view.findViewById(R.id.etAlturaJogador);
        etPesoJogador = view.findViewById(R.id.etPesoJogador);

        //Buttons
        btnInserirJogador = view.findViewById(R.id.btnInserirJogador);
        btnExcluirJogador = view.findViewById(R.id.btnExcluirJogador);
        btnModificarJogador = view.findViewById(R.id.btnModificarJogador);
        btnBuscarJogador = view.findViewById(R.id.btnBuscarJogador);
        btnListarJogador = view.findViewById(R.id.btnListarJogador);

        //Text View
        tvListagemJogador = view.findViewById(R.id.tvListagemJogador);
        tvListagemJogador.setMovementMethod(new ScrollingMovementMethod());

        spTimesJogadores = view.findViewById(R.id.spTimesJogadores);

        jogadorController = new JogadorController(new JogadorDao(view.getContext()));
        timeController = new TimeController(new TimeDao(view.getContext()));
        preencheSpinner();

        btnInserirJogador.setOnClickListener(click -> acaoInserir());
        btnModificarJogador.setOnClickListener(click -> acaoModificar());
        btnExcluirJogador.setOnClickListener(click -> acaoDeletar());
        btnBuscarJogador.setOnClickListener(click -> acaoBuscar());
        btnListarJogador.setOnClickListener(click -> acaoListar());


        return view;
    }

    private void preencheSpinner() {
        Time t0 = new Time();
        t0.setCodigo(0);
        t0.setNome("Selecione um Jogador");
        t0.setCidade("");

//        listaDeTimes
        try {
            listaDeTimes = timeController.listar();
            listaDeTimes.add(0, t0);
            ArrayAdapter adapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item, listaDeTimes);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spTimesJogadores.setAdapter(adapter);

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    private void acaoInserir() {
        try {
            int spinnerPosicao = spTimesJogadores.getSelectedItemPosition();
            if (spinnerPosicao > 0) {
                Jogador jogador = montaJogador();
                jogadorController.inserir(jogador);
                Toast.makeText(view.getContext(), "Jogador Inserido com Sucesso", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(view.getContext(), "Selecione um Time", Toast.LENGTH_LONG).show();
            }

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();
    }

    private void acaoModificar() {
        try {
            int spinnerPosicao = spTimesJogadores.getSelectedItemPosition();
            if (spinnerPosicao > 0) {
                Jogador jogador = montaJogador();
                jogadorController.modificar(jogador);
                Toast.makeText(view.getContext(), "Jogador Atualizado com Sucesso", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(view.getContext(), "Selecione um Time", Toast.LENGTH_LONG).show();
            }

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();
    }

    private void acaoDeletar() {
        try {
            Jogador jogador = montaJogador();
            jogadorController.deletar(jogador);
            Toast.makeText(view.getContext(), "Jogador Deletado com Sucesso", Toast.LENGTH_LONG).show();

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();
    }

    private void acaoBuscar() {
        try {
            Jogador jogador = montaJogador();
            listaDeTimes = timeController.listar();
            jogador = jogadorController.buscar(jogador);

            if (jogador.getNome() != null) {
                preenchaCampos(jogador);
            } else {
                Toast.makeText(view.getContext(), "Jogador Não Encontrado", Toast.LENGTH_LONG).show();
                limpaCampos();
            }

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoListar() {
        try {
            List<Jogador> listaDeJogadores = jogadorController.listar();
            StringBuffer stringBuffer = new StringBuffer();
            for (Jogador j : listaDeJogadores) {
                stringBuffer.append(j.toString() + "\n");
            }
            tvListagemJogador.setText(stringBuffer.toString());
            limpaCampos();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private Jogador montaJogador() {
        Jogador j = new Jogador();
        j.setId(Integer.parseInt(etIDJogador.getText().toString()));
        j.setNome(etNomeJogador.getText().toString());
        j.setDataNasc(etDataNascJogador.getText().toString());
        j.setTime((Time) spTimesJogadores.getSelectedItem());
        try{
            j.setPeso(Float.parseFloat(etPesoJogador.getText().toString()));
            j.setAltura(Float.parseFloat(etAlturaJogador.getText().toString()));
        }catch (Exception e){
            j.setPeso(0f);
            j.setAltura(0f);
        }
        return j;
    }

    private void preenchaCampos(Jogador j) {
        etIDJogador.setText(String.valueOf(j.getId()));
        etNomeJogador.setText(j.getNome());
        etDataNascJogador.setText(j.getDataNasc());
        etAlturaJogador.setText(String.valueOf(j.getAltura()));
        etPesoJogador.setText(String.valueOf(j.getPeso()));

        int ctd = 1;
        for (Time t : listaDeTimes) {
            if (t.getCodigo() == j.getTime().getCodigo()) {
                spTimesJogadores.setSelection(ctd);
            } else {
                ctd++;
            }
        }
        if (ctd > listaDeTimes.size()) {
            spTimesJogadores.setSelection(0);
        }
    }

    private void limpaCampos() {
        etIDJogador.setText("");
        etNomeJogador.setText("");
        etDataNascJogador.setText("");
        etAlturaJogador.setText("");
        etPesoJogador.setText("");
        spTimesJogadores.setSelection(0);

    }


}