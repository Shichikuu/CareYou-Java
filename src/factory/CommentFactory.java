package factory;

import model.Comment;
import model.Transaction;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentFactory {
//    public Comment createComment(String content, String username, Transaction transaction) {
//        return new Comment(transaction.getTransactionID(), content, username, transaction.getAmount());
//    }

    public static Comment createCommentFromResultSet(ResultSet rs) throws SQLException {
        return new Comment(rs.getInt("TransactionID"), rs.getString("Content"), rs.getString("Username"), rs.getInt("Amount"));
    }
}
