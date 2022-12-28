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

    final public void save(Resume r) {
        int index = findIndex(r.getUuid());
        if (size >= STORAGE_LIMIT) {
            System.out.println("Хранилище переполненно");
        }
        if (index > 0) {
            System.out.printf("Резюме с uuid %s уже существует%n", r.getUuid());
        } else {
            insertResume(r, index);
            size++;
        }
    }

    final public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return STORAGE[index];
        } else {
            printErorResumeNotFound(uuid);
            return null;
        }
    }

    final public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            STORAGE[index] = resume;
        } else {
            printErorResumeNotFound(resume.getUuid());
        }
    }

    final public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            deleteResume(index);
            size--;
        } else {
            printErorResumeNotFound(uuid);
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(STORAGE, 7);
    }

    protected void printErorResumeNotFound(String uuid) {
        System.out.printf("Резюме с uuid %s не найдено%n", uuid);
    }

    protected abstract void insertResume(Resume r, int index);

    protected abstract void deleteResume(int index);

    protected abstract int findIndex(String uuid);
}
