package com.carryxyh.file;

import java.util.List;

/**
 * FileOperator
 * We use String as the file content. For cache-check, performance and disk space are not the first things we
 * need to consider. We need to ensure the readability of the file.
 *
 * @author xiuyuhang [carryxyh@apache.org]
 * @since 2020-04-28
 */
public interface FileOperator {

    String filePath();

    void appendData(String data);

    void writeData(List<String> data);

    List<String> loadData();
}
