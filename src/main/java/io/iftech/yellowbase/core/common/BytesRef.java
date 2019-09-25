package io.iftech.yellowbase.core.common;

import com.google.common.base.Preconditions;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public final class BytesRef implements Comparable<BytesRef>, Cloneable {

    public static final byte[] EMTPY_BYTES = new byte[0];

    public byte[] bytes;

    public int length;

    public int offset;

    public BytesRef() {
        this.bytes = EMTPY_BYTES;
        this.offset = 0;
        this.length = 0;
    }

    public BytesRef(byte[] bytes, int offset, int length) {
        Preconditions.checkState(length >= 0,
            "length is negative");
        Preconditions.checkState(offset >= 0,
            "offset is negative");
        Preconditions.checkState(offset + length >= 0,
            "offset+length is negative");
        Preconditions.checkState(length <= bytes.length,
            "length is out of bounds");
        Preconditions.checkState(offset <= bytes.length,
            "offset out of bounds");
        Preconditions.checkState(offset + length <= bytes.length,
            "offset+length out of bounds");
        this.bytes = Preconditions.checkNotNull(bytes);
        this.offset = offset;
        this.length = length;
    }

    public BytesRef(byte[] bytes) {
        this(bytes, 0, bytes.length);
    }

    /**
     * Create UTF-8 encoded {@link BytesRef} from Unicode(UTF16) Java string
     * It is not as efficient as Lucene's implementation since {@link String#getBytes(Charset)}
     * always allocating a new byte[]
     *
     * @param charSequence Unicode {@link CharSequence}
     */
    public BytesRef(CharSequence charSequence) {
        this(charSequence.toString().getBytes(StandardCharsets.UTF_8));
    }

    public static BytesRef deepCopy(BytesRef other) {
        byte[] copy = Arrays.copyOfRange(other.bytes, other.offset, other.offset + other.length);
        return new BytesRef(copy, 0, other.length);
    }

    public final byte[] asRef() {
        return bytes;
    }

    @Override
    public BytesRef clone() {
        return new BytesRef(bytes, offset, length);
    }

    @Override
    public int compareTo(@NotNull BytesRef other) {
        return Arrays.compareUnsigned(this.bytes, this.offset, this.offset + this.length,
            other.bytes, other.offset, other.offset + other.length);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BytesRef bytesRef = (BytesRef) o;
        return length == bytesRef.length &&
            offset == bytesRef.offset &&
            Arrays.equals(bytes, bytesRef.bytes);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(length, offset);
        result = 31 * result + Arrays.hashCode(bytes);
        return result;
    }
}
