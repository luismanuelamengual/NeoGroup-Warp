package org.neogroup.warp.data.query.parsers;

import org.neogroup.warp.data.query.*;

import java.util.ArrayList;
import java.util.List;

public class DefaultQueryParser extends QueryParser {

    @Override
    public Query parseQuery(String query) throws QueryParseException{
        List<QueryToken> tokens = getQueryTokens(query);
        if (tokens.isEmpty()) {
            throw new QueryParseException("No tokens found in query");
        }
        QueryToken firstToken = tokens.get(0);
        if (firstToken.getType() != QueryTokenType.WORD) {
            throw new QueryParseException("Unexpected query token \"" + firstToken.getValue() + "\"");
        }
        Query parsedQuery = null;
        switch (firstToken.getValue().toString()) {
            case QueryStatement.SELECT:
                parsedQuery = createSelectQuery(tokens);
                break;
            case QueryStatement.INSERT:
                parsedQuery = createInsertQuery(tokens);
                break;
            case QueryStatement.UPDATE:
                parsedQuery = createUpdateQuery(tokens);
                break;
            case QueryStatement.DELETE:
                parsedQuery = createDeleteQuery(tokens);
                break;
        }
        return parsedQuery;
    }

    private SelectQuery createSelectQuery(List<QueryToken> tokens) {
        return null;
    }

    private InsertQuery createInsertQuery(List<QueryToken> tokens) {
        throw new UnsupportedOperationException();
    }

    private UpdateQuery createUpdateQuery(List<QueryToken> tokens) {
        throw new UnsupportedOperationException();
    }

    private DeleteQuery createDeleteQuery(List<QueryToken> tokens) {
        throw new UnsupportedOperationException();
    }

    private enum QueryTokenType {
        WORD,
        VALUE,
        OPENING_PARENTHESIS,
        CLOSING_PARENTHESIS,
        COMMA
    }

    private class QueryToken {
        private final QueryTokenType type;
        private final Object value;

        public QueryToken(QueryTokenType type, Object value) {
            this.type = type;
            this.value = value;
        }

        public QueryTokenType getType() {
            return type;
        }

        public Object getValue() {
            return value;
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }

    private List<QueryToken> getQueryTokens(String query) {
        List<QueryToken> tokens = new ArrayList<>();
        StringBuilder token = null;
        int pos = 0;
        char tokenQuotationCharacter = 0;
        while (pos < query.length()) {
            char character = query.charAt(pos);

            if (token != null) {
                if (tokenQuotationCharacter != 0) {
                    if (character == QueryStatement.BACK_SLASH) {
                        pos++;
                        token.append(query.charAt(pos));
                    }
                    else {
                        if (character == tokenQuotationCharacter) {
                            tokens.add(new QueryToken(QueryTokenType.VALUE, token.toString()));
                            token = null;
                            tokenQuotationCharacter = 0;
                            pos++;
                            continue;
                        }
                        else {
                            token.append(character);
                        }
                    }
                }
                else {
                    if (Character.isWhitespace(character) || character == QueryStatement.COMMA ||character == QueryStatement.OPENING_PARENTHESIS || character == QueryStatement.CLOSING_PARENTHESIS) {
                        String tokenValueStr = token.toString();
                        try {
                            tokens.add(new QueryToken(QueryTokenType.VALUE, Double.parseDouble(tokenValueStr)));
                        }
                        catch (Throwable ex) {
                            tokens.add(new QueryToken(QueryTokenType.WORD, tokenValueStr));
                        }
                        token = null;
                    }
                    else {
                        token.append(character);
                    }
                }
            }

            if (token == null) {
                if (!Character.isWhitespace(character)) {
                    if (character == QueryStatement.OPENING_PARENTHESIS) {
                        tokens.add(new QueryToken(QueryTokenType.OPENING_PARENTHESIS, String.valueOf(character)));
                    } else if (character == QueryStatement.CLOSING_PARENTHESIS) {
                        tokens.add(new QueryToken(QueryTokenType.CLOSING_PARENTHESIS, String.valueOf(character)));
                    } else if (character == QueryStatement.COMMA) {
                        tokens.add(new QueryToken(QueryTokenType.COMMA, String.valueOf(character)));
                    } else {
                        token = new StringBuilder();
                        if (character == QueryStatement.QUOTATION_MARK || character == QueryStatement.DOUBLE_QUOTATION_MARK) {
                            tokenQuotationCharacter = character;
                        }
                        else {
                            token.append(character);
                        }
                    }
                }
            }
            pos++;
        }
        if (token != null && tokenQuotationCharacter == 0) {
            String tokenValueStr = token.toString();
            try {
                tokens.add(new QueryToken(QueryTokenType.VALUE, Double.parseDouble(tokenValueStr)));
            }
            catch (Throwable ex) {
                tokens.add(new QueryToken(QueryTokenType.WORD, tokenValueStr));
            }
        }
        return tokens;
    }
}