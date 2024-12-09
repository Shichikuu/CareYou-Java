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
        String sql = "SELECT * FROM programs WHERE fundraiserId = ?";
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
        String sql = "SELECT * FROM comments c JOIN transactions t WHERE t.programId = ?";
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

    public int createProgram(int userId, String title, String name, String beneficiary, String desc, int target) {
        String sql = "INSERT INTO programs (fundraiserId, programTitle, fundraiserName, beneficiaryName, programDesc, programTarget, startDate, programRaised, withdrawn, programStatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, userId);
            stmt.setString(2, title);
            stmt.setString(3, name);
            stmt.setString(4, beneficiary);
            stmt.setString(5, desc);
            stmt.setInt(6, target);
            stmt.setDate(7, new java.sql.Date(new Date().getTime()));
            stmt.setInt(8, 0);
            stmt.setInt(9, 0);
            stmt.setString(10, "Not Finished");
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int programID = generatedKeys.getInt(1);
                    return programID;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void updateProgramRaised(int programID, int amount) {
        String sql = "UPDATE programs SET programRaised = programRaised + ? WHERE programId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, amount);
            stmt.setInt(2, programID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProgramStatus(int programID, String status) {
        String sql = "UPDATE programs SET programStatus = ? WHERE programId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, programID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProgramWithdrawn(int programID, int amount) {
        String sql = "UPDATE programs SET withdrawn = withdrawn + ? WHERE programId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, amount);
            stmt.setInt(2, programID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProgram(int programID) {
        String sql = "DELETE FROM programs WHERE programId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, programID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProgram(Program program) {
        String sql = "UPDATE programs SET programTitle = ?, programDesc = ?, programTarget = ?, startDate = ?, withdrawn = ? WHERE programId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, program.getProgramTitle());
            stmt.setString(2, program.getProgramDesc());
            stmt.setInt(3, program.getProgramTarget());
            stmt.setDate(4, new java.sql.Date(program.getStartDate().getTime()));
            stmt.setInt(5, program.getWithdrawn());
            stmt.setInt(6, program.getProgramID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
