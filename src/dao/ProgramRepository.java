package dao;

import factory.CommentFactory;
import factory.ProgramFactory;
import model.Comment;
import model.Program;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProgramRepository {

    private static ProgramRepository instance;
    private Connection connection;

    private ProgramRepository() throws SQLException {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public static ProgramRepository getInstance() throws SQLException {
        if (instance == null) {
            instance = new ProgramRepository();
        }
        return instance;
    }

    public List<Program> getProgramsByUserID(int userID) {
        List<Program> programs = new ArrayList<>();
        String sql = "SELECT * FROM programs WHERE fundraiserID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Program program = ProgramFactory.createProgramFromResultSet(rs);
                    programs.add(program);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return programs;
    }

    public List<Program> getAllPrograms() {
        List<Program> programs = new ArrayList<>();
        String sql = "SELECT * FROM programs";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Program program = ProgramFactory.createProgramFromResultSet(rs);
                programs.add(program);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return programs;
    }

    public List<Comment> getCommentsByProgramId(int programId) {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM comments WHERE programID = ? ORDER BY commentDate ASC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, programId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Comment comment = CommentFactory.createCommentFromResultSet(rs);
                    comments.add(comment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

}
