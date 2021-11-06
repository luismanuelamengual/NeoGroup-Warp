package org.neogroup.warp.http;

import java.util.*;

public class MultipartItemsReader {

    private static final byte CR = 0x0D;
    private static final byte LF = 0x0A;
    private static final byte DASH = 0x2D;

    private byte[] content;

    private byte[] boundary;

    private int index;

    public MultipartItemsReader(byte[] content, byte[] boundary) {
        this.content = content;
        this.boundary = boundary;
    }

    public List<MultipartItem> readItems () {
        index = 0;
        List<MultipartItem> items = new ArrayList<>();
        MultipartItem item;
        while ((item = readItem()) != null) {
            items.add(item);
        }
        return items;
    }

    private MultipartItem readItem () {
        index += 2;
        index += boundary.length;
        MultipartItem item = null;
        if (content[index] != DASH || content[index + 1] != DASH) {
            index += 2;
            Map<String, String> headers = readItemHeaders();
            byte[] contents = readItemContent();
            item = new MultipartItem(headers, contents);
        }
        return item;
    }

    private Map<String, String> readItemHeaders () {
        Map<String, String> headers = new HashMap<>();
        int headerIndex = index;
        while (index < content.length) {
            if (content[index] == CR && content[index+1] == LF) {
                if (index == headerIndex) {
                    index += 2;
                    break;
                }
                else {
                    byte[] headerContentBytes = Arrays.copyOfRange(content, headerIndex, index);
                    String headerContent = new String(headerContentBytes);
                    int headerSeparatorIndex = headerContent.indexOf(':');
                    String headerName = headerContent.substring(0, headerSeparatorIndex).trim();
                    String headerValue = headerContent.substring(headerSeparatorIndex+1).trim();
                    headers.put(headerName, headerValue);
                    index += 2;
                    headerIndex = index;
                }
            }
            else {
                index++;
            }
        }
        return headers;
    }

    private byte[] readItemContent () {
        byte[] itemContents = null;
        int contentIndex = index;
        while (index < content.length) {
            if (content[index] == CR && content[index+1] == LF && content[index+2] == DASH && content[index+3] == DASH) {
                boolean boundaryFound = true;
                int contentBoundaryIndex = index + 4;
                for (int boundaryIndex = 0; boundaryIndex < boundary.length && contentBoundaryIndex < content.length; boundaryIndex++, contentBoundaryIndex++) {
                    if (content[contentBoundaryIndex] != boundary[boundaryIndex]) {
                        boundaryFound = false;
                        break;
                    }
                }
                if (boundaryFound) {
                    itemContents = Arrays.copyOfRange(content, contentIndex, index);
                    index += 2;
                    break;
                } else {
                    index += 4;
                }
            } else {
                index++;
            }
        }
        return itemContents;
    }
}
