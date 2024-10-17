import ast.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleSqlParser {

    /*
    1. tokenize sql
    2. parses select from tokens
    */
    private static final String[] KEYWORDS = {"SELECT", "FROM", "WHERE", "AVG"};
    private static final String REGEX =
            "\\s*(" +                // Optional whitespace
                    "SELECT|" +              // Keywords
                    "FROM|" +
                    "WHERE|" +
                    "AVG|" +
                    ">|" +                   // Operators
                    "\\(|\\)|" +             // Parentheses
                    ",|" +                   // Comma
                    "[a-zA-Z_]\\w*|" +       // Identifiers
                    "\\d+|" +                // Numbers
                    "'[^']*'" +              // String literals
                    ")\\s*";                 // Optional whitespace

    public SqlNode parse(String sql) throws ParseException {
        List<String> tokens = tokenize(sql);
        System.out.println("Tokens: " + tokens);
        return parseSelect(tokens);
    }

    private List<String> tokenize(String sql) {
        List<String> tokens = new ArrayList<>();
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(sql);

        while (matcher.find()) {
            String token = matcher.group(1);
            for (String keyword : KEYWORDS) {
                if (keyword.equalsIgnoreCase(token)) {
                    token = keyword; // Normalize keyword case
                    break;
                }
            }
            tokens.add(token);
        }

        return tokens;
    }

    private SqlNode parseSelect(List<String> tokens) throws ParseException {
        int idx = 0;
        if (!tokens.get(idx).equalsIgnoreCase("SELECT")) {
            throw new ParseException("Invalid SQL! ('SELECT' not found!)");
        }
        idx++;

        // Parse columns
        List<SqlNode> columns = new ArrayList<>();
        while (idx < tokens.size() && !tokens.get(idx).equalsIgnoreCase("FROM")) {
            String token = tokens.get(idx);
            if (token.equals(",")) {
                idx++;
                continue;
            }
            columns.add(new IdentifierNode(token));
            idx++;
        }

        if (idx >= tokens.size() || !tokens.get(idx).equalsIgnoreCase("FROM")) {
            throw new ParseException("Invalid SQL! ('FROM' not found!)");
        }
        idx++; // Skip 'FROM'

        if (idx >= tokens.size()) {
            throw new ParseException("Invalid SQL! ('Table' not found!)");
        }

        String table = tokens.get(idx);
        FromNode from = new FromNode(table);
        idx++;

        // Parse WHERE clause if exists
        WhereNode where = null;
        if (idx < tokens.size() && tokens.get(idx).equalsIgnoreCase("WHERE")) {
            idx++; // Skip 'WHERE'
            SqlNode condition = parseCondition(tokens, idx);
            where = new WhereNode(condition);
        }

        return new SelectNode(columns, from, where);
    }

    private SqlNode parseCondition(List<String> tokens, int idx) throws ParseException {
        if (idx >= tokens.size()) {
            throw new ParseException("Invalid SQL! ('WHERE' clause incomplete!)");
        }

        String leftOperand = tokens.get(idx++);
        if (idx >= tokens.size()) {
            throw new ParseException("Invalid SQL! ('WHERE' clause incomplete!)");
        }

        String operator = tokens.get(idx++);
        if (idx >= tokens.size()) {
            throw new ParseException("Invalid SQL! ('WHERE' clause incomplete!)");
        }

        String token = tokens.get(idx);
        SqlNode rightOperand;

        if (token.equals("(")) {
            // Subquery
            idx++; // Skip '('
            // Find the matching ')'
            int start = idx;
            int parenCount = 1;
            while (idx < tokens.size() && parenCount > 0) {
                if (tokens.get(idx).equals("(")) parenCount++;
                if (tokens.get(idx).equals(")")) parenCount--;
                idx++;
            }

            if (parenCount != 0) {
                throw new ParseException("Invalid SQL! ('WHERE' clause has unmatched parentheses)");
            }

            List<String> subQueryTokens = tokens.subList(start, idx - 1);
            SimpleSqlParser subParser = new SimpleSqlParser();
            rightOperand = subParser.parseSelect(subQueryTokens);
        } else {
            // Simple identifier or value
            rightOperand = new IdentifierNode(token);
            idx++;
        }

        return new ComparisonNode(leftOperand, operator, rightOperand);
    }

    public static class ParseException extends Exception {
        public ParseException(String message) {
            super(message);
        }
    }
}
