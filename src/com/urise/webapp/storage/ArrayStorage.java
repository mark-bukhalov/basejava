package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void clear() {
        Arrays.fill(STORAGE, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        int index = findIndex(r.getUuid());
        if (size >= STORAGE_LIMIT) {
            System.out.println("Хранилище переполненно");
        }
        if (index > 0) {
            System.out.printf("Резюме с uuid %s уже существует%n", r.getUuid());
        } else {
            STORAGE[size] = r;
            size++;
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

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            STORAGE[index] = STORAGE[size - 1];
            STORAGE[size - 1] = null;
            size--;
        } else {
            printErorResumeNotFound(uuid);
        }
    }

    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (STORAGE[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(STORAGE, size);
    }

}
