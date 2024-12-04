package factory;

import model.Comment;
import model.Transaction;
import model.User;

public class CommentFactory {
    public Comment createComment(String content, String username, Transaction transaction) {
        return new Comment(transaction.getTransactionID(), content, username, transaction.getUserID());
    }
}
