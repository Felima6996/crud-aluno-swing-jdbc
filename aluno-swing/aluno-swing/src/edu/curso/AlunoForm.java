package edu.curso;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class AlunoForm extends JFrame {

    private static final DateTimeFormatter FORMATO =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    private final AlunoDAO dao = new AlunoDAO();

    private final JTextField txtRa = new JTextField(20);
    private final JTextField txtNome = new JTextField(20);
    private final JTextField txtIdade = new JTextField(20);
    private final JTextField txtNascimento = new JTextField(20);

    private final JButton btnInserir = new JButton("Inserir");
    private final JButton btnAtualizar = new JButton("Atualizar");
    private final JButton btnRemover = new JButton("Remover");
    private final JButton btnPesquisar = new JButton("Pesquisar");

    public AlunoForm() {
        super("Cadastro de Alunos");

        JPanel campos = new JPanel(new GridLayout(4, 2, 5, 5));
        campos.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        campos.add(new JLabel("RA:"));
        campos.add(txtRa);
        campos.add(new JLabel("Nome:"));
        campos.add(txtNome);
        campos.add(new JLabel("Idade:"));
        campos.add(txtIdade);
        campos.add(new JLabel("Nascimento (dd/MM/aaaa):"));
        campos.add(txtNascimento);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        botoes.add(btnInserir);
        botoes.add(btnAtualizar);
        botoes.add(btnRemover);
        botoes.add(btnPesquisar);

        add(campos, BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);

        btnInserir.addActionListener(e -> inserir());
        btnAtualizar.addActionListener(e -> atualizar());
        btnRemover.addActionListener(e -> remover());
        btnPesquisar.addActionListener(e -> pesquisar());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void inserir() {
        Aluno aluno = lerFormulario();
        if (aluno == null) {
            return;
        }

        try {
            dao.inserir(aluno);
            mensagem("Aluno inserido com sucesso.");
            limpar();
        } catch (SQLException ex) {
            if (ex.getSQLState() != null && ex.getSQLState().startsWith("23")) {
                erro("Já existe um aluno com este RA.");
            } else {
                erro("Erro ao inserir: " + ex.getMessage());
            }
        }
    }

    private void atualizar() {
        Aluno aluno = lerFormulario();
        if (aluno == null) {
            return;
        }

        try {
            if (dao.atualizar(aluno) > 0) {
                mensagem("Aluno atualizado com sucesso.");
                limpar();
            } else {
                erro("Nenhum aluno encontrado com este RA.");
            }
        } catch (SQLException ex) {
            erro("Erro ao atualizar: " + ex.getMessage());
        }
    }

    private void remover() {
        String ra = lerRa();
        if (ra == null) {
            return;
        }

        try {
            if (dao.remover(ra) > 0) {
                mensagem("Aluno removido com sucesso.");
                limpar();
            } else {
                erro("Nenhum aluno encontrado com este RA.");
            }
        } catch (SQLException ex) {
            erro("Erro ao remover: " + ex.getMessage());
        }
    }

    private void pesquisar() {
        String ra = lerRa();
        if (ra == null) {
            return;
        }

        try {
            Aluno aluno = dao.pesquisar(ra);
            if (aluno == null) {
                erro("Nenhum aluno encontrado com este RA.");
                return;
            }
            txtNome.setText(aluno.getNome());
            txtIdade.setText(String.valueOf(aluno.getIdade()));
            txtNascimento.setText(aluno.getNascimento().format(FORMATO));
        } catch (SQLException ex) {
            erro("Erro ao pesquisar: " + ex.getMessage());
        }
    }

    private String lerRa() {
        String ra = txtRa.getText().trim();
        if (ra.isEmpty() || ra.length() > 5) {
            erro("Informe um RA com até 5 caracteres.");
            return null;
        }
        return ra;
    }

    private Aluno lerFormulario() {
        String ra = lerRa();
        if (ra == null) {
            return null;
        }

        String nome = txtNome.getText().trim();
        if (nome.isEmpty() || nome.length() > 100) {
            erro("Informe um nome com até 100 caracteres.");
            return null;
        }

        int idade;
        try {
            idade = Integer.parseInt(txtIdade.getText().trim());
            if (idade < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            erro("Informe uma idade válida.");
            return null;
        }

        LocalDate nascimento;
        try {
            nascimento = LocalDate.parse(txtNascimento.getText().trim(), FORMATO);
        } catch (Exception ex) {
            erro("Data de nascimento inválida. Use o formato dd/MM/aaaa.");
            return null;
        }

        return new Aluno(ra, nome, idade, nascimento);
    }

    private void limpar() {
        txtRa.setText("");
        txtNome.setText("");
        txtIdade.setText("");
        txtNascimento.setText("");
        txtRa.requestFocus();
    }

    private void mensagem(String texto) {
        JOptionPane.showMessageDialog(this, texto, "Aviso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void erro(String texto) {
        JOptionPane.showMessageDialog(this, texto, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AlunoForm().setVisible(true));
    }
}
