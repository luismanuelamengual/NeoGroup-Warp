package org.neogroup.warp.data.query.parsers;

import org.neogroup.warp.data.query.Query;

import java.util.ArrayList;
import java.util.List;

public class DefaultQueryParser extends QueryParser {

    private static char OPENING_PARENTHESIS = '(';
    private static char CLOSING_PARENTHESIS = ')';
    private static char COMMA = ',';
    private static char QUOTATION_MARK = '\'';
    private static char DOUBLE_QUOTATION_MARK = '"';
    private static char BACK_SLASH = '\\';

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
                    if (character == BACK_SLASH) {
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
                    if (Character.isWhitespace(character) || character == COMMA ||character == OPENING_PARENTHESIS || character == CLOSING_PARENTHESIS) {
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
                    if (character == OPENING_PARENTHESIS || character == CLOSING_PARENTHESIS || character == COMMA) {
                        tokens.add(String.valueOf(character));
                    } else {
                        token = new StringBuilder();
                        if (character == QUOTATION_MARK || character == DOUBLE_QUOTATION_MARK) {
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