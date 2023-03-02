package com.urise.webapp.storage.strategy;

import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ResumeSerializer {
    public abstract void doWrite(Resume r, OutputStream os) throws IOException;

    public abstract Resume doRead(InputStream is) throws IOException;
}
