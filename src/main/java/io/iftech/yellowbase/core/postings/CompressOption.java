package io.iftech.yellowbase.core.postings;

public final class CompressOption {

    enum CompressAlgorithm {
        ZLIB,
        RAW, // no compression
    }

    public CompressAlgorithm algorithm;

    public CompressOption(CompressAlgorithm algorithm) {
        this.algorithm = algorithm;
    }
}
