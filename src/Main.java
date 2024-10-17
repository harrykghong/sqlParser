import ast.SqlNode;

public class Main {
    public static void main(String[] args) {
        String sql = "SELECT name, age FROM users WHERE age > 30";
        SimpleSqlParser sqlParser = new SimpleSqlParser();
        try{
            SqlNode ast = sqlParser.parse(sql);
//            将访问者传入accept fucntion中
            ast.accept(new PrintVisitor());
        }catch (SimpleSqlParser.ParseException e){
            System.err.println("Error: " + e.getMessage());
        }

    }
}