package org.neogroup.warp.data.query.parsers;

import org.neogroup.warp.data.query.Query;
import org.neogroup.warp.data.query.QueryStatement;

import java.util.ArrayList;
import java.util.List;

public class DefaultQueryParser extends QueryParser {

    @Override
    public Query parseQuery(String query) {
        List<String> tokens = getQueryTokens(query);
        return null;
    }

    private List<String> getQueryTokens(String query) {
        List<String> tokens = new ArrayList<>();
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
                            tokens.add(token.toString());
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
                        tokens.add(token.toString());
                        token = null;
                    }
                    else {
                        token.append(character);
                    }
                }
            }

            if (token == null) {
                if (!Character.isWhitespace(character)) {
                    if (character == QueryStatement.OPENING_PARENTHESIS || character == QueryStatement.CLOSING_PARENTHESIS || character == QueryStatement.COMMA) {
                        tokens.add(String.valueOf(character));
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
        if (token != null) {
            tokens.add(token.toString());
        }
        return tokens;
    }
}