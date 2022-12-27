package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    protected void insertResume(Resume r, int index) {
        STORAGE[size] = r;
    }

    protected void deleteResume(int index) {
        STORAGE[index] = STORAGE[size - 1];
        STORAGE[size - 1] = null;
    }

    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (STORAGE[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }


}
