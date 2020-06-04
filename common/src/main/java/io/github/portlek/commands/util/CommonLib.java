/*
 * MIT License
 *
 * Copyright (c) 2020 Hasan Demirtaş
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.portlek.commands.util;

import org.jetbrains.annotations.NotNull;

public final class CommonLib {

    public static final String EMPTY = "";

    private CommonLib() {
    }

    public static boolean startsWithIgnoreCase(final CharSequence str, final CharSequence prefix) {
        return CommonLib.startsWith(str, prefix, true);
    }

    @NotNull
    @SafeVarargs
    public static <T> String join(final T... elements) {
        return CommonLib.join(elements, null);
    }

    @NotNull
    public static String join(@NotNull final Object[] array, final char separator) {
        return CommonLib.join(array, separator, 0, array.length);
    }

    @NotNull
    public static String join(@NotNull final long[] array, final char separator) {
        return CommonLib.join(array, separator, 0, array.length);
    }

    @NotNull
    public static String join(@NotNull final int[] array, final char separator) {
        return CommonLib.join(array, separator, 0, array.length);
    }

    @NotNull
    public static String join(@NotNull final short[] array, final char separator) {
        return CommonLib.join(array, separator, 0, array.length);
    }

    @NotNull
    public static String join(@NotNull final byte[] array, final char separator) {
        return CommonLib.join(array, separator, 0, array.length);
    }

    @NotNull
    public static String join(@NotNull final char[] array, final char separator) {
        return CommonLib.join(array, separator, 0, array.length);
    }

    @NotNull
    public static String join(@NotNull final float[] array, final char separator) {
        return CommonLib.join(array, separator, 0, array.length);
    }

    @NotNull
    public static String join(@NotNull final double[] array, final char separator) {
        return CommonLib.join(array, separator, 0, array.length);
    }

    @NotNull
    public static String join(@NotNull final Object[] array, final char separator, final int startIndex,
                              final int endIndex) {
        final int noOfItems = endIndex - startIndex;
        if (noOfItems <= 0) {
            return CommonLib.EMPTY;
        }
        final StringBuilder buf = new StringBuilder(noOfItems << 4);
        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            buf.append(array[i]);
        }
        return buf.toString();
    }

    static boolean regionMatches(final CharSequence cs, final boolean ignoreCase, final int thisStart,
                                 final CharSequence substring, final int start, final int length) {
        if (cs instanceof String && substring instanceof String) {
            return ((String) cs).regionMatches(ignoreCase, thisStart, (String) substring, start, length);
        }
        int index1 = thisStart;
        int index2 = start;
        int tmpLen = length;

        // Extract these first so we detect NPEs the same as the java.lang.String version
        final int srcLen = cs.length() - thisStart;
        final int otherLen = substring.length() - start;

        // Check for invalid parameters
        if (thisStart < 0 || start < 0 || length < 0) {
            return false;
        }

        // Check that the regions are long enough
        if (srcLen < length || otherLen < length) {
            return false;
        }

        while (tmpLen-- > 0) {
            final char c1 = cs.charAt(index1++);
            final char c2 = substring.charAt(index2++);

            if (c1 == c2) {
                continue;
            }

            if (!ignoreCase) {
                return false;
            }

            // The same check as in String.regionMatches():
            if (Character.toUpperCase(c1) != Character.toUpperCase(c2)
                && Character.toLowerCase(c1) != Character.toLowerCase(c2)) {
                return false;
            }
        }

        return true;
    }

    private static boolean startsWith(final CharSequence str, final CharSequence prefix, final boolean ignoreCase) {
        if (str == null || prefix == null) {
            return str == null && prefix == null;
        }
        if (prefix.length() > str.length()) {
            return false;
        }
        return CommonLib.regionMatches(str, ignoreCase, 0, prefix, 0, prefix.length());
    }

}
