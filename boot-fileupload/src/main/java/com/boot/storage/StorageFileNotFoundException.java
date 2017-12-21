package com.boot.storage;

/**
 *
 * @author Administrator
 * @date 2017/11/9
 */
public class StorageFileNotFoundException  extends StorageException{
    public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
