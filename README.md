# Design Patterns Used in the Application

This application leverages several well-known design patterns to maintain a clean, extensible, and maintainable codebase. Specifically, it employs the **Template Method**, **Factory**, and **Singleton** patterns.

## 1. Template Method Pattern

**Where it's used:**
- **`Transaction` (Abstract Class)** and its subclasses (**`Donation`**, **`Withdrawal`**)

**What it does:**
The Template Method pattern defines the skeleton of an algorithm in an abstract class, allowing subclasses to provide their own implementations for certain steps without changing the overall process.

**Implementation details:**
- **`Transaction`** is an abstract class that defines `processTransaction(Program program)`, which outlines the steps:
  1. `validateTransaction(program)`
  2. `createTransactionHeader()`
  3. `executeTransaction(program)`
  4. `recordTransaction()`
  
- **`Donation`** and **`Withdrawal`** override the abstract methods `validateTransaction()`, `executeTransaction()`, and `recordTransaction()` to provide specialized logic.

This approach ensures each transaction type (donation or withdrawal) follows the same general process structure while allowing for specific differences in each step.

## 2. Factory Pattern

**Where it's used:**
- **`DonationFactory` and `WithdrawalFactort` classes implementing `TransactionFactory`**
- **`CommentFactory`**, **`ProgramFactory`**, **`UserFactory`**

**What it does:**
The Factory pattern separates object creation from the rest of the code, making it easy to change how objects are created without affecting the client code.

**Implementation details:**
- **`Donation`** and **`Withdrawal`** themselves implement the `TransactionFactory` interface, indicating that they can create transaction objects (or be involved in the creation logic for transactions).
- **`CommentFactory`**, **`ProgramFactory`**, and **`UserFactory`** handle the instantiation and configuration of `Comment`, `Program`, and `User` objects from data sources like database result sets.
  
By using factories, the code that needs these objects does not need to know the details of how they are constructed. If the implementation changes (e.g., different transaction subclasses, different user models), you only need to modify the factory, not the client code.

## 3. Singleton Pattern

**Where it's used:**
- **`DatabaseConnection`** 

**What it does:**
The Singleton pattern ensures only one instance of a class exists at runtime and provides a global point of access to it. This is particularly useful for resources that are expensive or unnecessary to create multiple times, such as database connections.

**Implementation details:**
- The **`DatabaseConnection`** class is implemented as a singleton. It has a `getInstance()` method that returns the single shared connection instance.
- This approach improves efficiency and resource management since you don't repeatedly create new connections.
- It also simplifies code by offering a straightforward way to obtain the database connection without passing it around manually.

---
