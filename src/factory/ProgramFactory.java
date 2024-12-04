package factory;

import model.Program;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ProgramFactory {
    public Program createProgram(int programID, int fundraiserID, String programTitle, String programDesc, String programTopic, String fundraiserName, String beneficiaryName, String programType, int programTarget, Date endDate) {
        return new Program(programID, fundraiserID, programTitle, programDesc, programTopic, fundraiserName, beneficiaryName, programType, programTarget, endDate);
    }

    public static Program createProgramFromResultSet(ResultSet rs) throws SQLException {
        int programID = rs.getInt("programID");
        int fundraiserID = rs.getInt("fundraiserID");
        String programTitle = rs.getString("programTitle");
        String programDesc = rs.getString("programDesc");
        String programStatus = rs.getString("programStatus");
        String programTopic = rs.getString("programTopic");
        String fundraiserName = rs.getString("fundraiserName");
        String beneficiaryName = rs.getString("beneficiaryName");
        String programType = rs.getString("programType");
        int programTarget = rs.getInt("programTarget");
        int programRaised = rs.getInt("programRaised");
        Date startDate = rs.getDate("startDate");
        Date endDate = rs.getDate("endDate");
        int withdrawn = rs.getInt("withdrawn");
        return new Program(programID, fundraiserID, programTitle, programDesc, programTopic, fundraiserName, beneficiaryName, programType, programTarget, endDate);
    }
}
