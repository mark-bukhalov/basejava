package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] STORAGE = new Resume[STORAGE_LIMIT];
    protected int size;

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return STORAGE[index];
        } else {
            printErorResumeNotFound(uuid);
            return null;
        }
    }

    protected abstract int findIndex(String uuid);

    protected void printErorResumeNotFound(String uuid) {
        System.out.printf("Резюме с uuid %s не найдено%n", uuid);
    }
}
