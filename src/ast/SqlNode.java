package ast;

public abstract class SqlNode {
    public abstract <T> T accept(SqlVisitor<T> visitor);
}
