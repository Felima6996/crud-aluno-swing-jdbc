package edu.curso;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlunoDAO {

    public int inserir(Aluno aluno) throws SQLException {
        String sql = "INSERT INTO aluno (ra, nome, idade, nascimento) VALUES (?, ?, ?, ?)";

        try (Connection con = Conexao.obter();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, aluno.getRa());
            ps.setString(2, aluno.getNome());
            ps.setInt(3, aluno.getIdade());
            ps.setDate(4, Date.valueOf(aluno.getNascimento()));

            return ps.executeUpdate();
        }
    }

    public int atualizar(Aluno aluno) throws SQLException {
        String sql = "UPDATE aluno SET nome = ?, idade = ?, nascimento = ? WHERE ra = ?";

        try (Connection con = Conexao.obter();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, aluno.getNome());
            ps.setInt(2, aluno.getIdade());
            ps.setDate(3, Date.valueOf(aluno.getNascimento()));
            ps.setString(4, aluno.getRa());

            return ps.executeUpdate();
        }
    }

    public int remover(String ra) throws SQLException {
        String sql = "DELETE FROM aluno WHERE ra = ?";

        try (Connection con = Conexao.obter();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ra);

            return ps.executeUpdate();
        }
    }

    public Aluno pesquisar(String ra) throws SQLException {
        String sql = "SELECT ra, nome, idade, nascimento FROM aluno WHERE ra = ?";

        try (Connection con = Conexao.obter();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ra);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Aluno(
                            rs.getString("ra"),
                            rs.getString("nome"),
                            rs.getInt("idade"),
                            rs.getDate("nascimento").toLocalDate());
                }
                return null;
            }
        }
    }
}
