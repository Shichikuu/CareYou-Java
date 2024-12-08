package factory;

import model.Program;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ProgramFactory {
    public Program createProgram(int programID, int fundraiserID, String programTitle, String programDesc, String fundraiserName, String beneficiaryName, int programTarget) {
        return new Program(programID, fundraiserID, programTitle, programDesc, fundraiserName, beneficiaryName, programTarget);
    }

    public static Program createProgramFromResultSet(ResultSet rs) throws SQLException {
        int programID = rs.getInt("programID");
        int fundraiserID = rs.getInt("fundraiserID");
        String programTitle = rs.getString("programTitle");
        String programDesc = rs.getString("programDesc");
        String programStatus = rs.getString("programStatus");
        String fundraiserName = rs.getString("fundraiserName");
        String beneficiaryName = rs.getString("beneficiaryName");
        int programTarget = rs.getInt("programTarget");
        int programRaised = rs.getInt("programRaised");
        Date startDate = rs.getDate("startDate");
        int withdrawn = rs.getInt("withdrawn");
        Program program = new Program(programID, fundraiserID, programTitle, programDesc, fundraiserName, beneficiaryName, programTarget);
        program.setProgramStatus(programStatus);
        program.setProgramRaised(programRaised);
        program.setStartDate(startDate);
        program.setWithdrawn(withdrawn);
        return program;
    }
}
