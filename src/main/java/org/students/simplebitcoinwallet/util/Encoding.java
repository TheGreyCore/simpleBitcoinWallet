package org.students.simplebitcoinwallet.util;

import org.students.simplebitcoinwallet.exceptions.encoding.InvalidHexStringException;

/**
 * Metaclass for encoding related code
 */
public class Encoding {
    /**
     * Converts the byte array into hexadecimal string
     * @param bytes represents the array of bytes to use for encoding
     * @return a hexadecimal string representing the byte array
     */
    public static String toHexString(byte[] bytes) {
        final char[] hexSymbols = "0123456789abcdef".toCharArray();

        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) {
            int val = b & 0xff;
            stringBuilder.append(hexSymbols[val >> 4]);
            stringBuilder.append(hexSymbols[val & 0x0f]);
        }

        return stringBuilder.toString();
    }

    /**
     * Converts given hexadecimal string to an array of bytes if possible.
     * @param hexString specifies the hexadecimal string to decode. The string is assumed to only contain lowercase numerical or lowercase `abcdef` characters.
     *                  Other-wise the method call will fail with InvalidHexStringException.
     * @return array of decoded bytes
     * @throws InvalidHexStringException when the
     */
    public static byte[] hexStringToBytes(String hexString) throws InvalidHexStringException {
        validateHexStringOrException(hexString);
        byte[] bytes = new byte[hexString.length() / 2];

        for (int i = 0; i < hexString.length(); i += 2) {
            int b = hexSymbolToInt(hexString.charAt(i)) * 16 + hexSymbolToInt(hexString.charAt(i+1));
            bytes[i / 2] = (byte)(b & 0xff);
        }

        return bytes;
    }

    private static void validateHexStringOrException(String hexString) throws InvalidHexStringException {
        // length must be multiple of 2 requirement
        if (hexString.length() % 2 != 0)
            throw new InvalidHexStringException("HexString length must be multiple of 2");

        // allowed symbol requirements
        for (char c : hexString.toCharArray()) {
            if (c < '0' || (c > '9' && c < 'a') || c > 'z')
                throw new InvalidHexStringException("Invalid symbol '" + c + "' in hexString ");
        }
    }

    private static int hexSymbolToInt(char hexSymbol) {
        return switch (hexSymbol) {
            case '1' -> 1;
            case '2' -> 2;
            case '3' -> 3;
            case '4' -> 4;
            case '5' -> 5;
            case '6' -> 6;
            case '7' -> 7;
            case '8' -> 8;
            case '9' -> 9;
            case 'a' -> 10;
            case 'b' -> 11;
            case 'c' -> 12;
            case 'd' -> 13;
            case 'e' -> 14;
            case 'f' -> 15;
            default -> 0;
        };
    }
}
