package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertResume(Resume r, int index) {
        int indexIns = (index + 1) * -1;
        System.arraycopy(STORAGE, indexIns, STORAGE, indexIns + 1, size - indexIns);
        STORAGE[indexIns] = r;

    }

    @Override
    protected void deleteResume(int index) {
        System.arraycopy(STORAGE, index + 1, STORAGE, index, size - index - 1);
        STORAGE[size - 1] = null;
    }

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(STORAGE, 0, size, searchKey);
    }

}
