package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertResume(Resume r, Integer index) {
        int indexIns = (index + 1) * -1;
        System.arraycopy(storage, indexIns, storage, indexIns + 1, size - indexIns);
        storage[indexIns] = r;

    }

    @Override
    protected void deleteResume(Integer index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
        storage[size - 1] = null;
    }

    @Override
    protected Object findSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
