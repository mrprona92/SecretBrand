package com.badr.infodota.base.util.http;

/**
 * Created by Badr on 19.08.2015.
 */
public final class CharArrayBuffer {


    public static final int CR = 13; // <US-ASCII CR, carriage return (13)>
    public static final int LF = 10; // <US-ASCII LF, linefeed (10)>
    public static final int SP = 32; // <US-ASCII SP, space (32)>
    public static final int HT = 9;  // <US-ASCII HT, horizontal-tab (9)>
    private char[] buffer;
    private int len;

    /**
     * Creates an instance of {@link CharArrayBuffer} with the given initial
     * capacity.
     *
     * @param capacity the capacity
     */
    public CharArrayBuffer(int capacity) {
        super();
        if (capacity < 0) {
            throw new IllegalArgumentException("Buffer capacity may not be negative");
        }
        this.buffer = new char[capacity];
    }

    public static boolean isWhitespace(char ch) {
        return ch == SP || ch == HT || ch == CR || ch == LF;
    }

    private void expand(int newlen) {
        char newbuffer[] = new char[Math.max(this.buffer.length << 1, newlen)];
        System.arraycopy(this.buffer, 0, newbuffer, 0, this.len);
        this.buffer = newbuffer;
    }

    /**
     * Appends <code>len</code> chars to this buffer from the given source
     * array starting at index <code>off</code>. The capacity of the buffer
     * is increased, if necessary, to accommodate all <code>len</code> chars.
     *
     * @param b   the chars to be appended.
     * @param off the index of the first char to append.
     * @param len the number of chars to append.
     * @throws IndexOutOfBoundsException if <code>off</code> if out of
     *                                   range, <code>len</code> is negative, or
     *                                   <code>off</code> + <code>len</code> is out of range.
     */
    public void append(final char[] b, int off, int len) {
        if (b == null) {
            return;
        }
        if ((off < 0) || (off > b.length) || (len < 0) ||
                ((off + len) < 0) || ((off + len) > b.length)) {
            throw new IndexOutOfBoundsException();
        }
        if (len == 0) {
            return;
        }
        int newlen = this.len + len;
        if (newlen > this.buffer.length) {
            expand(newlen);
        }
        System.arraycopy(b, off, this.buffer, this.len, len);
        this.len = newlen;
    }

    /**
     * Appends chars of the given string to this buffer. The capacity of the
     * buffer is increased, if necessary, to accommodate all chars.
     *
     * @param str the string.
     */
    public void append(String str) {
        if (str == null) {
            str = "null";
        }
        int strlen = str.length();
        int newlen = this.len + strlen;
        if (newlen > this.buffer.length) {
            expand(newlen);
        }
        str.getChars(0, strlen, this.buffer, this.len);
        this.len = newlen;
    }

    /**
     * Appends <code>len</code> chars to this buffer from the given source
     * buffer starting at index <code>off</code>. The capacity of the
     * destination buffer is increased, if necessary, to accommodate all
     * <code>len</code> chars.
     *
     * @param b   the source buffer to be appended.
     * @param off the index of the first char to append.
     * @param len the number of chars to append.
     * @throws IndexOutOfBoundsException if <code>off</code> if out of
     *                                   range, <code>len</code> is negative, or
     *                                   <code>off</code> + <code>len</code> is out of range.
     */
    public void append(final CharArrayBuffer b, int off, int len) {
        if (b == null) {
            return;
        }
        append(b.buffer, off, len);
    }

    /**
     * Appends all chars to this buffer from the given source buffer starting
     * at index <code>0</code>. The capacity of the destination buffer is
     * increased, if necessary, to accommodate all {@link #length()} chars.
     *
     * @param b the source buffer to be appended.
     */
    public void append(final CharArrayBuffer b) {
        if (b == null) {
            return;
        }
        append(b.buffer, 0, b.len);
    }

    /**
     * Appends <code>ch</code> char to this buffer. The capacity of the buffer
     * is increased, if necessary, to accommodate the additional char.
     *
     * @param ch the char to be appended.
     */
    public void append(char ch) {
        int newlen = this.len + 1;
        if (newlen > this.buffer.length) {
            expand(newlen);
        }
        this.buffer[this.len] = ch;
        this.len = newlen;
    }

    /**
     * Appends <code>len</code> bytes to this buffer from the given source
     * array starting at index <code>off</code>. The capacity of the buffer
     * is increased, if necessary, to accommodate all <code>len</code> bytes.
     * <p/>
     * The bytes are converted to chars using simple cast.
     *
     * @param b   the bytes to be appended.
     * @param off the index of the first byte to append.
     * @param len the number of bytes to append.
     * @throws IndexOutOfBoundsException if <code>off</code> if out of
     *                                   range, <code>len</code> is negative, or
     *                                   <code>off</code> + <code>len</code> is out of range.
     */
    public void append(final byte[] b, int off, int len) {
        if (b == null) {
            return;
        }
        if ((off < 0) || (off > b.length) || (len < 0) ||
                ((off + len) < 0) || ((off + len) > b.length)) {
            throw new IndexOutOfBoundsException();
        }
        if (len == 0) {
            return;
        }
        int oldlen = this.len;
        int newlen = oldlen + len;
        if (newlen > this.buffer.length) {
            expand(newlen);
        }
        for (int i1 = off, i2 = oldlen; i2 < newlen; i1++, i2++) {
            this.buffer[i2] = (char) (b[i1] & 0xff);
        }
        this.len = newlen;
    }

    /**
     * Appends chars of the textual representation of the given object to this
     * buffer. The capacity of the buffer is increased, if necessary, to
     * accommodate all chars.
     *
     * @param obj the object.
     */
    public void append(final Object obj) {
        append(String.valueOf(obj));
    }

    /**
     * Clears content of the buffer. The underlying char array is not resized.
     */
    public void clear() {
        this.len = 0;
    }

    /**
     * Converts the content of this buffer to an array of chars.
     *
     * @return char array
     */
    public char[] toCharArray() {
        char[] b = new char[this.len];
        if (this.len > 0) {
            System.arraycopy(this.buffer, 0, b, 0, this.len);
        }
        return b;
    }

    /**
     * Returns the <code>char</code> value in this buffer at the specified
     * index. The index argument must be greater than or equal to
     * <code>0</code>, and less than the length of this buffer.
     *
     * @param i the index of the desired char value.
     * @return the char value at the specified index.
     * @throws IndexOutOfBoundsException if <code>index</code> is
     *                                   negative or greater than or equal to {@link #length()}.
     */
    public char charAt(int i) {
        return this.buffer[i];
    }

    /**
     * Returns reference to the underlying char array.
     *
     * @return the char array.
     */
    public char[] buffer() {
        return this.buffer;
    }

    /**
     * Returns the current capacity. The capacity is the amount of storage
     * available for newly appended chars, beyond which an allocation will
     * occur.
     *
     * @return the current capacity
     */
    public int capacity() {
        return this.buffer.length;
    }

    /**
     * Returns the length of the buffer (char count).
     *
     * @return the length of the buffer
     */
    public int length() {
        return this.len;
    }

    /**
     * Ensures that the capacity is at least equal to the specified minimum.
     * If the current capacity is less than the argument, then a new internal
     * array is allocated with greater capacity. If the <code>required</code>
     * argument is non-positive, this method takes no action.
     *
     * @param required the minimum required capacity.
     */
    public void ensureCapacity(int required) {
        if (required <= 0) {
            return;
        }
        int available = this.buffer.length - this.len;
        if (required > available) {
            expand(this.len + required);
        }
    }

    /**
     * Sets the length of the buffer. The new length value is expected to be
     * less than the current capacity and greater than or equal to
     * <code>0</code>.
     *
     * @param len the new length
     * @throws IndexOutOfBoundsException if the
     *                                   <code>len</code> argument is greater than the current
     *                                   capacity of the buffer or less than <code>0</code>.
     */
    public void setLength(int len) {
        if (len < 0 || len > this.buffer.length) {
            throw new IndexOutOfBoundsException();
        }
        this.len = len;
    }

    /**
     * Returns <code>true</code> if this buffer is empty, that is, its
     * {@link #length()} is equal to <code>0</code>.
     *
     * @return <code>true</code> if this buffer is empty, <code>false</code>
     * otherwise.
     */
    public boolean isEmpty() {
        return this.len == 0;
    }

    /**
     * Returns <code>true</code> if this buffer is full, that is, its
     * {@link #length()} is equal to its {@link #capacity()}.
     *
     * @return <code>true</code> if this buffer is full, <code>false</code>
     * otherwise.
     */
    public boolean isFull() {
        return this.len == this.buffer.length;
    }

    /**
     * Returns the index within this buffer of the first occurrence of the
     * specified character, starting the search at the specified
     * <code>beginIndex</code> and finishing at <code>endIndex</code>.
     * If no such character occurs in this buffer within the specified bounds,
     * <code>-1</code> is returned.
     * <p/>
     * There is no restriction on the value of <code>beginIndex</code> and
     * <code>endIndex</code>. If <code>beginIndex</code> is negative,
     * it has the same effect as if it were zero. If <code>endIndex</code> is
     * greater than {@link #length()}, it has the same effect as if it were
     * {@link #length()}. If the <code>beginIndex</code> is greater than
     * the <code>endIndex</code>, <code>-1</code> is returned.
     *
     * @param ch         the char to search for.
     * @param beginIndex the index to start the search from.
     * @param endIndex   the index to finish the search at.
     * @return the index of the first occurrence of the character in the buffer
     * within the given bounds, or <code>-1</code> if the character does
     * not occur.
     */
    public int indexOf(int ch, int beginIndex, int endIndex) {
        if (beginIndex < 0) {
            beginIndex = 0;
        }
        if (endIndex > this.len) {
            endIndex = this.len;
        }
        if (beginIndex > endIndex) {
            return -1;
        }
        for (int i = beginIndex; i < endIndex; i++) {
            if (this.buffer[i] == ch) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns the index within this buffer of the first occurrence of the
     * specified character, starting the search at <code>0</code> and finishing
     * at {@link #length()}. If no such character occurs in this buffer within
     * those bounds, <code>-1</code> is returned.
     *
     * @param ch the char to search for.
     * @return the index of the first occurrence of the character in the
     * buffer, or <code>-1</code> if the character does not occur.
     */
    public int indexOf(int ch) {
        return indexOf(ch, 0, this.len);
    }

    /**
     * Returns a substring of this buffer. The substring begins at the specified
     * <code>beginIndex</code> and extends to the character at index
     * <code>endIndex - 1</code>.
     *
     * @param beginIndex the beginning index, inclusive.
     * @param endIndex   the ending index, exclusive.
     * @return the specified substring.
     * @throws IndexOutOfBoundsException if the
     *                                   <code>beginIndex</code> is negative, or
     *                                   <code>endIndex</code> is larger than the length of this
     *                                   buffer, or <code>beginIndex</code> is larger than
     *                                   <code>endIndex</code>.
     */
    public String substring(int beginIndex, int endIndex) {
        if (beginIndex < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (endIndex > this.len) {
            throw new IndexOutOfBoundsException();
        }
        if (beginIndex > endIndex) {
            throw new IndexOutOfBoundsException();
        }
        return new String(this.buffer, beginIndex, endIndex - beginIndex);
    }

    /**
     * Returns a substring of this buffer with leading and trailing whitespace
     * omitted. The substring begins with the first non-whitespace character
     * from <code>beginIndex</code> and extends to the last
     * non-whitespace character with the index lesser than
     * <code>endIndex</code>.
     *
     * @param beginIndex the beginning index, inclusive.
     * @param endIndex   the ending index, exclusive.
     * @return the specified substring.
     * @throws IndexOutOfBoundsException if the
     *                                   <code>beginIndex</code> is negative, or
     *                                   <code>endIndex</code> is larger than the length of this
     *                                   buffer, or <code>beginIndex</code> is larger than
     *                                   <code>endIndex</code>.
     */
    public String substringTrimmed(int beginIndex, int endIndex) {
        if (beginIndex < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (endIndex > this.len) {
            throw new IndexOutOfBoundsException();
        }
        if (beginIndex > endIndex) {
            throw new IndexOutOfBoundsException();
        }
        while (beginIndex < endIndex && isWhitespace(this.buffer[beginIndex])) {
            beginIndex++;
        }
        while (endIndex > beginIndex && isWhitespace(this.buffer[endIndex - 1])) {
            endIndex--;
        }
        return new String(this.buffer, beginIndex, endIndex - beginIndex);
    }

    public String toString() {
        return new String(this.buffer, 0, this.len);
    }

}
