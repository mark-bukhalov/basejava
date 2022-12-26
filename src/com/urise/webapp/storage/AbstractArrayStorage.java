package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] STORAGE = new Resume[STORAGE_LIMIT];
    protected int size;

    public void clear() {
        Arrays.fill(STORAGE, 0, size, null);
        size = 0;
    }

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

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            STORAGE[index] = resume;
        } else {
            printErorResumeNotFound(resume.getUuid());
        }
    }

    protected abstract int findIndex(String uuid);

    public Resume[] getAll() {
        return Arrays.copyOf(STORAGE, 15);
    }

    protected void printErorResumeNotFound(String uuid) {
        System.out.printf("Резюме с uuid %s не найдено%n", uuid);
    }
}
