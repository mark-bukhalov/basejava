package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        int index = findIndexByUuid(r.getUuid());
        if (size >= 10000) {
            System.out.println("Хранилище переполненно");
        }
        if (index < 0) {
            storage[size] = r;
            size++;
        } else {
            System.out.printf("Резюме с uuid %s уже существует%n", r.getUuid());
        }
    }

    public Resume get(String uuid) {
        int index = findIndexByUuid(uuid);
        if (index >= 0) {
            return storage[index];
        } else {
            printErorResumeNotFound(uuid);
            return null;
        }
    }

    public void update(Resume resume) {
        int index = findIndexByUuid(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
        } else {
            printErorResumeNotFound(resume.getUuid());
        }
    }

    public void delete(String uuid) {
        int index = findIndexByUuid(uuid);
        if (index >= 0) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            printErorResumeNotFound(uuid);
        }
    }

    private int findIndexByUuid(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    private void printErorResumeNotFound(String uuid) {
        System.out.printf("Резюме с uuid %s не найдено%n", uuid);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}
